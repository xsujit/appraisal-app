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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.AppraisalPk;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.services.AppraisalService;

@SessionAttributes(names={"appraisalCategory", "employee"})
@Controller
public class AppraisalController {
	
	@Autowired
	private AppraisalService appraisalService;

	@GetMapping("/appraisal")
	public String getAppraisal(Model model, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		
		if (model.containsAttribute("appraisal")) {
			return "appraisal-submit";
		}
		Appraisal existingAppraisal = appraisalService.getAppraisal(appraisalCategory, employee);
		if (existingAppraisal != null) {
			model.addAttribute("appraisal", existingAppraisal);
			return "appraisal-view";
		} else {
			Appraisal appraisal = new Appraisal();
			model.addAttribute(appraisal);
		}
		return "appraisal-submit";
	}
	
	@PostMapping("/appraisal")
	public String setAppraisal(@Valid @ModelAttribute Appraisal appraisal, BindingResult result, RedirectAttributes redirectAttributes, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		
		if (result.hasErrors()) {
			return "appraisal-submit";
		}
		appraisal.setAppraisalPk(new AppraisalPk(employee, appraisalCategory));
		appraisal.setProject(employee.getProject());
		DataAccessException dae = appraisalService.save(appraisal);
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", "Unable to persist data. Please contact the system administrator.");
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
	public String editAppraisal(Model model, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		if (model.containsAttribute("appraisal")) {
			return "appraisal-submit";
		}
		Appraisal existingAppraisal = appraisalService.getAppraisal(appraisalCategory, employee);
		if(existingAppraisal != null) {
			if(!existingAppraisal.isSignedOff()) {
				model.addAttribute("appraisal", existingAppraisal);
				return "appraisal-submit";	
			}
		}
		return "redirect:/error";
	}
	
	@PutMapping("/appraisal/edit")
	public String updateAppraisal(@Valid @ModelAttribute Appraisal appraisal, BindingResult result, RedirectAttributes redirectAttributes, Model model, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		
		if (result.hasErrors()) {
			return "appraisal-submit";
		}
		appraisal.setAppraisalPk(new AppraisalPk(employee, appraisalCategory));
		DataAccessException dae = appraisalService.update(appraisal);
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", "Unable to persist data. Please contact the system administrator.");
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
	public String setAppraisalSignOff(RedirectAttributes redirectAttributes, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		
		DataAccessException dae = appraisalService.signOff(appraisalCategory, employee);
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", "Unable to persist data. Please contact the system administrator.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			System.out.println("Data Access Exception in signoff: " + dae.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("message", "Appraisal signed off successfully.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/appraisal";
	}
}
