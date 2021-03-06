package com.company.appraisal.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.company.appraisal.entities.Appraisal;
import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.services.AppraisalService;
import com.company.appraisal.services.TeamService;
import com.company.appraisal.wrappers.Progress;

@Controller
public class IndexController {

	@Autowired
	private AppraisalService appraisalService;
	@Autowired
	private TeamService teamService;
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/logout-success")
	public String getLogoutPage() {
		return "logout";
	}
	
	@GetMapping(value= {"/index", "/"})
	public String getIndexPage(Model model, @ModelAttribute("loggedInEmployee") Employee me, @ModelAttribute("appraisalCategory") AppraisalCategory category) {
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			Map<Integer, Progress> progressMap = getStagesComplete(category, me);
			double count = countStagesComplete(progressMap);
			model.addAttribute("percentComplete", calculatePercentageComplete(count));
			model.addAttribute("progressMap", progressMap);
		}
		return "index";
	}
	
	private Map<Integer, Progress> getStagesComplete(AppraisalCategory category, Employee employee) { // private
		
		boolean voted = false;
		Map<Integer, Progress> progressMap = new HashMap<>();
		progressMap.put(1, new Progress("Register", true));
		progressMap.put(2, new Progress("Submit", false));
		progressMap.put(3, new Progress("Sign off", false));
		progressMap.put(4, new Progress("Vote", false));		
		voted = teamService.hasVoted(employee, category);
		Appraisal appraisal = appraisalService.getAppraisal(employee, category);
		if(appraisal != null) {
			progressMap.get(2).setDone(true);
			progressMap.get(3).setDone(appraisal.isSignedOff());
		}
		if (voted) {
			progressMap.get(4).setDone(true);
		}
		return progressMap;
	}
	
	private double countStagesComplete(Map<Integer, Progress> progressMap) { // private
		
		double count = 0;
		for (Integer key : progressMap.keySet()) {
			if(progressMap.get(key).isDone()) {
				count++;
			}
		}
		return count;
	}
	
	private String calculatePercentageComplete(double count) { // private
		
		double percentComplete = 0;
		percentComplete = (count/4)*100;
		return Double.toString(Math.round(percentComplete)) + '%';
	}
}
