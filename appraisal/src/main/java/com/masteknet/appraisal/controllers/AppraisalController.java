package com.masteknet.appraisal.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.masteknet.appraisal.entities.Appraisal;
import com.masteknet.appraisal.exceptions.AppraisalNotFoundException;
import com.masteknet.appraisal.services.AppraisalService;

@Controller
public class AppraisalController extends AppraisalBase {
	
	@Autowired
	private AppraisalService appraisalService;
	
	@GetMapping("/appraisal")
	public String getAppraisal(Model model) {

		Appraisal currentAppraisal = appraisalService.getAppraisal(getLoggedInEmployee(), getAppraisalCategory());
		if (currentAppraisal != null) {
			model.addAttribute("appraisal", currentAppraisal);
			return "appraisal-view";
		} else {
			Appraisal appraisal = new Appraisal();
			model.addAttribute(appraisal);
		}
		return "appraisal-submit";
	}
	
	@PostMapping("/appraisal")
	public String setAppraisal(@Valid @ModelAttribute Appraisal appraisal, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "appraisal-submit";
		}
		try {
			appraisalService.save(appraisal, getAppraisalCategory(), getLoggedInEmployee());
		} catch (DataAccessException dae) {
			result.rejectValue("description", "error.appraisal", ERROR_MESSAGE);
			return "appraisal-submit";
		}
		return "redirect:/appraisal?success=Appraisal+submitted+successfully";
	}
	
	@GetMapping("/appraisal/edit")
	public String editAppraisal(Model model) {
		
		Appraisal currentAppraisal = appraisalService.getAppraisal(getLoggedInEmployee(), getAppraisalCategory());
		if(currentAppraisal == null) {
			throw new AppraisalNotFoundException(getAppraisalCategory().toString()+getLoggedInEmployee().getId());
		} else {
			if(currentAppraisal.isSignedOff()) {
				return "redirect:/appraisal?error=Appraisal+already+signed+off.";	
			} else {
				model.addAttribute("appraisal", currentAppraisal);
				return "appraisal-submit";
			}
		}
	}
	
	@PutMapping("/appraisal/edit")
	public String updateAppraisal(@Valid @ModelAttribute Appraisal appraisal, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		
		if (result.hasErrors()) {
			return "appraisal-submit";
		}
		try {
			appraisalService.save(appraisal, getAppraisalCategory(), getLoggedInEmployee());
		} catch (DataAccessException dae) {
			result.rejectValue("description", "error.appraisal", ERROR_MESSAGE);
			return "appraisal-submit";
		}
		return "redirect:/appraisal?success=Appraisal+updated+successfully";
	}
	
	@GetMapping("/appraisal/sign-off")
	public String setAppraisalSignOff(RedirectAttributes redirectAttributes) {
		
		try {
			appraisalService.signOff(getAppraisalCategory(), getLoggedInEmployee());
		} catch (DataAccessException dae) {
			return "redirect:/appraisal?error=Unable+to+persist+data.+Please+contact+support.";
		}
		return "redirect:/appraisal?success=Appraisal+signedoff+successfully";
	}

}
