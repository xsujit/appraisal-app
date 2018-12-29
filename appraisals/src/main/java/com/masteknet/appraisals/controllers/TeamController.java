package com.masteknet.appraisals.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.Comment;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.models.Team;
import com.masteknet.appraisals.services.AppraisalCategoryService;
import com.masteknet.appraisals.services.AppraisalService;
import com.masteknet.appraisals.services.EmployeeService;
import com.masteknet.appraisals.services.TeamService;

@Controller
@SessionAttributes(names={"appraisalCategory", "employee"})
public class TeamController {

	public static final String ERROR_MESSAGE = "Unable to persist data. Please contact the system administrator."; 
	
	@Autowired
	private TeamService teamService;
	@Autowired
	private AppraisalService appraisalService;
	@Autowired
	private AppraisalCategoryService appraisalCategoryService;
	@Autowired
	private EmployeeService employeeService;
	
	private AppraisalCategory getAppraisalCategory(byte type, LocalDate year) { // private
		return appraisalCategoryService.getAppraisalCategory(type, year);
	}
	
	private Employee getEmployee(long id) { // private
		return employeeService.getEmployee(id);
	}
		
	@GetMapping("/team")
	public String getTeam(Model model, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		Map<Long, Team> team = teamService.createTeamMap(appraisalCategory, employee);
		if (team != null) {
			model.addAttribute("team", team);
			return "team";
		}
		return "redirect:/error";
	}
	
	@GetMapping("/team/{yearId}/{typeId}/{employeeId}")
	public String getAppraisalByEmployee(Model model, @PathVariable String yearId, @PathVariable String typeId, @PathVariable String employeeId, @ModelAttribute Employee employee) {
		
		AppraisalCategory appraisalCategory = getAppraisalCategory(Byte.parseByte(typeId), LocalDate.of(Year.parse(yearId).getValue(), Month.JANUARY, 01));
		Appraisal appraisal = appraisalService.getAppraisal(appraisalCategory, getEmployee(Long.parseLong(employeeId)));
		if(appraisal != null) {
			if(!employee.getProject().equals(appraisal.getProject())) {
				appraisalService.isAdmin();
			}
			model.addAttribute("employee", getEmployee(Long.parseLong(employeeId)));
			model.addAttribute("appraisal", appraisal);
			model.addAttribute("comments", teamService.getComments(appraisal));
			model.addAttribute("comment", new Comment());
			model.addAttribute("eligible", (teamService.hasVoted(appraisal, employee) || teamService.selfVote(appraisal, employee) ? false : true));
			return "team-view";
		}
		return "redirect:/error";
	}
	
	@GetMapping("/team/{yearId}/{typeId}/{employeeId}/vote-a-plus")
	public String saveVote(@PathVariable String yearId, @PathVariable String typeId, @PathVariable String employeeId, RedirectAttributes redirectAttributes, @ModelAttribute Employee employee) {
		
		AppraisalCategory appraisalCategory = getAppraisalCategory(Byte.parseByte(typeId), LocalDate.of(Year.parse(yearId).getValue(), Month.JANUARY, 01));
		Appraisal appraisal = appraisalService.getAppraisal(appraisalCategory, getEmployee(Long.parseLong(employeeId)));
		if(teamService.selfVote(appraisal, employee) || teamService.hasVoted(appraisal, employee)) {
			redirectAttributes.addFlashAttribute("message", "Already voted or self vote");
			redirectAttributes.addFlashAttribute("cssClass", "alert-danger");
		} else {
			DataAccessException dae = teamService.saveVote(appraisal, employee);
			if(dae != null) {
				redirectAttributes.addFlashAttribute("message", ERROR_MESSAGE);
				redirectAttributes.addFlashAttribute("cssClass", "alert-danger");
				System.out.println("Data Access Exception: " + dae.getMessage());
			} else {
				redirectAttributes.addFlashAttribute("message", "Vote registered successfully.");
				redirectAttributes.addFlashAttribute("cssClass", "alert-success");
			}
		}
		return "redirect:/team/{yearId}/{typeId}/{employeeId}";
	}
	
	@PostMapping("/team/{yearId}/{typeId}/{employeeId}/comment")
	public String saveComment(@Valid @ModelAttribute Comment comment, BindingResult result, @PathVariable String yearId, @PathVariable String typeId, @PathVariable String employeeId, RedirectAttributes redirectAttributes, Model model, @ModelAttribute Employee employee) {
		
		AppraisalCategory appraisalCategory = getAppraisalCategory(Byte.parseByte(typeId), LocalDate.of(Year.parse(yearId).getValue(), Month.JANUARY, 01));
		Appraisal appraisal = appraisalService.getAppraisal(appraisalCategory, getEmployee(Long.parseLong(employeeId)));
		if (result.hasErrors()) {
			if(appraisal != null) {
				model.addAttribute("employee", getEmployee(Long.parseLong(employeeId)));
				model.addAttribute("appraisal", appraisal);
				model.addAttribute("comments", teamService.getComments(appraisal));
				model.addAttribute("eligible", teamService.selfVote(appraisal, employee) ? false : true);
				return "team-view";			
			}
		}
		DataAccessException dae = teamService.saveComment(comment, appraisal, employee);
		if(dae != null) {
			redirectAttributes.addFlashAttribute("message", ERROR_MESSAGE);
			redirectAttributes.addFlashAttribute("cssClass", "alert-danger");
			System.out.println("Data Access Exception: " + dae.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("message", "Your comment was posted successfully.");
			redirectAttributes.addFlashAttribute("cssClass", "alert-success");
		}
		return "redirect:/team/{yearId}/{typeId}/{employeeId}";
	}
	
}
