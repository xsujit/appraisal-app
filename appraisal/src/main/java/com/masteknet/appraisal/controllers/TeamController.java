package com.masteknet.appraisal.controllers;

import java.util.ArrayList;
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

import com.masteknet.appraisal.domain.models.Team;
import com.masteknet.appraisal.entities.Appraisal;
import com.masteknet.appraisal.entities.Comment;
import com.masteknet.appraisal.entities.Employee;
import com.masteknet.appraisal.exceptions.AppraisalNotFoundException;
import com.masteknet.appraisal.services.AppraisalService;
import com.masteknet.appraisal.services.TeamService;

@Controller
public class TeamController extends AppraisalBase {
	
	@Autowired
	private TeamService teamService;
	@Autowired
	private AppraisalService appraisalService;
	
	private Employee getEmployee(long id) { // private
		return employeeService.getEmployee(id);
	}
		
	@GetMapping("/team")
	public String getTeam(Model model) {
		ArrayList<Team> teamList = teamService.getTeam(getAppraisalCategory(), getLoggedInEmployee());
		if (teamList != null) {
			model.addAttribute("teamList", teamList);
			return "team";
		}
		return "redirect:/error?message=Your+team+could+not+be+displayed.+Please+contact+support.";
	}
	
	@GetMapping("/team/{employeeId}")
	public String getAppraisalByEmployee(Model model, @PathVariable String employeeId) {
		
		Appraisal appraisal = appraisalService.getAppraisal(getEmployee(Long.parseLong(employeeId)), getAppraisalCategory());
		if(appraisal == null || !appraisal.isSignedOff()) {
			throw new AppraisalNotFoundException(employeeId);
		} else {
			model.addAttribute("employee", getEmployee(Long.parseLong(employeeId)));
			model.addAttribute("appraisal", appraisal);
			model.addAttribute("comments", teamService.getComments(appraisal));
			model.addAttribute("comment", new Comment());
			model.addAttribute("eligible", (teamService.hasVoted(appraisal, getLoggedInEmployee()) || teamService.selfVote(appraisal, getLoggedInEmployee()) ? false : true));
			return "team-view";
		}
	}
	
	@GetMapping("/team/{employeeId}/vote-a-plus")
	public String saveVote(@PathVariable String employeeId) {
		
		Appraisal appraisal = appraisalService.getAppraisal(getEmployee(Long.parseLong(employeeId)), getAppraisalCategory());
		if(appraisal == null) {
			throw new AppraisalNotFoundException(employeeId);
		}
		if(teamService.selfVote(appraisal, getLoggedInEmployee()) || teamService.hasVoted(appraisal, getLoggedInEmployee())) {
			return "redirect:/team/{employeeId}?error=Already+voted+or+self+vote.";
		} 
		try {
			teamService.saveVote(appraisal, getLoggedInEmployee());
		} catch (DataAccessException dae) {
			return "redirect:/team/{employeeId}?error=Unable+to+save+your+vote.+Please+contact+support.";
		}
		return "redirect:/team/{employeeId}?success=Vote+registered+successfully.";
	}
	
	@PostMapping("/team/{employeeId}/comment")
	public String saveComment(@Valid @ModelAttribute Comment comment, BindingResult result, @PathVariable String employeeId, Model model) {
		
		Appraisal appraisal = appraisalService.getAppraisal(getEmployee(Long.parseLong(employeeId)), getAppraisalCategory());
		if(appraisal == null) {
			throw new AppraisalNotFoundException(employeeId);
		}
		if (result.hasErrors()) {
				model.addAttribute("employee", getEmployee(Long.parseLong(employeeId)));
				model.addAttribute("appraisal", appraisal);
				model.addAttribute("comments", teamService.getComments(appraisal));
				model.addAttribute("eligible", teamService.selfVote(appraisal, getLoggedInEmployee()) ? false : true);
				return "team-view";
		}
		try {
			teamService.saveComment(comment, appraisal, getLoggedInEmployee());	
		} catch (DataAccessException dae) {
			return "redirect:/team/{employeeId}?error=Your+comment+could+not+be+posted.+Please+contact+support.";
		}
		return "redirect:/team/{employeeId}?success=Your+comment+was+posted+successfully.";
	}
	
}
