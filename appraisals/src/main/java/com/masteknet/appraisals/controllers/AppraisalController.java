package com.masteknet.appraisals.controllers;

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
import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.services.AppraisalService;

@Controller
public class AppraisalController extends AppraisalBase {
	
	@Autowired
	private AppraisalService appraisalService;
	
	@GetMapping("/appraisal")
	public String getAppraisal(Model model) {
		if (model.containsAttribute("appraisal")) {
			return "appraisal-submit";
		}
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
		DataAccessException dae = appraisalService.save(appraisal, getAppraisalCategory(), getLoggedInEmployee());
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", ERROR_MESSAGE);
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute(appraisal); // so that form data is not lost after redirect
			System.out.println("Data Access Exception in post: " + dae.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("message", "Appraisal saved successfully.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/appraisal";
	}
	
	@GetMapping("/appraisal/edit")
	public String editAppraisal(Model model) {
		
		if (model.containsAttribute("appraisal")) {
			return "appraisal-submit";
		}
		Appraisal existingAppraisal = appraisalService.getAppraisal(getLoggedInEmployee(), getAppraisalCategory());
		if(existingAppraisal != null) {
			if(!existingAppraisal.isSignedOff()) {
				model.addAttribute("appraisal", existingAppraisal);
				return "appraisal-submit";	
			}
		}
		return "redirect:/error";
	}
	
	@PutMapping("/appraisal/edit")
	public String updateAppraisal(@Valid @ModelAttribute Appraisal appraisal, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		
		if (result.hasErrors()) {
			return "appraisal-submit";
		}
		DataAccessException dae = appraisalService.update(appraisal, getAppraisalCategory(), getLoggedInEmployee());
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", ERROR_MESSAGE);
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute(appraisal); // so that form data is not lost after redirect
			System.out.println("Data Access Exception in put: " + dae.getMessage());
			return "redirect:/appraisal/edit";
		} else {
			redirectAttributes.addFlashAttribute("message", "Appraisal saved successfully.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/appraisal";
	}
	
	@GetMapping("/appraisal/sign-off")
	public String setAppraisalSignOff(RedirectAttributes redirectAttributes) {
		
		DataAccessException dae = appraisalService.signOff(getAppraisalCategory(), getLoggedInEmployee());
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", ERROR_MESSAGE);
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			System.out.println("Data Access Exception in signoff: " + dae.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("message", "Appraisal signed off successfully.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/appraisal";
	}

}
