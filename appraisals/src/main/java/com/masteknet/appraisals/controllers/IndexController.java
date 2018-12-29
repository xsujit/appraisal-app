package com.masteknet.appraisals.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.models.Progress;
import com.masteknet.appraisals.services.AppraisalService;
import com.masteknet.appraisals.services.TeamService;

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
	public String getIndexPage(Model model, @ModelAttribute AppraisalCategory appraisalCategory, @ModelAttribute Employee employee) {
		Map<Integer, Progress> progressMap = getStagesComplete(employee, appraisalCategory);
		double count = countStagesComplete(progressMap);
		model.addAttribute("percentComplete", calculatePercentageComplete(count));
		model.addAttribute("progressMap", progressMap);
		return "index";
	}
	
	private Map<Integer, Progress> getStagesComplete(Employee me, AppraisalCategory category) { // private
		
		boolean voted = false;
		Map<Integer, Progress> progressMap = new HashMap<>();
		progressMap.put(1, new Progress("Register", true));
		progressMap.put(2, new Progress("Submit", false));
		progressMap.put(3, new Progress("Sign off", false));
		progressMap.put(4, new Progress("Vote", false));		
		voted = teamService.hasVoted(me, category);
		Appraisal appraisal = appraisalService.getAppraisal(category, me);
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
