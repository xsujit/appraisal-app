package com.masteknet.appraisals.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.masteknet.appraisals.models.Result;
import com.masteknet.appraisals.services.TeamService;

@Controller
public class ResultController extends AppraisalBase {
	
	@Autowired
	private TeamService teamService;

	@GetMapping("/result")
	public String getResult(Model model) {

		List<Result> fullYearResults = teamService.getVotesPerEmployee(getAppraisalCategory());
		TreeMap<Long, Long> fullYearResultMap = new TreeMap<>();
		TreeMap<Long, Long> midYearResultMap = new TreeMap<>();
		for(Result result : fullYearResults) {
			fullYearResultMap.put(result.getAppraisalPk().getEmployee().getId(), result.getVotes());
		}
		if (appraisalCategoryService.getAppraisalType().getType() == 1) { // if full year appraisal, then get mid year votes
			
			List<Result> midYearResults = teamService.getVotesPerEmployee(appraisalCategoryService.getAppraisalCategory((byte) 0));
			for(Result result : midYearResults) {
				midYearResultMap.put(result.getAppraisalPk().getEmployee().getId(), result.getVotes());
			}
			
			if (!fullYearResultMap.keySet().equals( midYearResultMap.keySet() )) {
				HashSet<Long> unionKeys = new HashSet<>(fullYearResultMap.keySet());
				unionKeys.addAll(midYearResultMap.keySet());
				unionKeys.removeAll(fullYearResultMap.keySet());
				for (Long i : unionKeys) {
					fullYearResultMap.put(i, (long) 0);
				}
				unionKeys.clear();
				unionKeys.addAll(midYearResultMap.keySet());
				unionKeys.addAll(fullYearResultMap.keySet());
				unionKeys.removeAll(midYearResultMap.keySet());
				for (Long i : unionKeys) {
					midYearResultMap.put(i, (long) 0);
				}
			}
		}

		model.addAttribute("fullYearResultMap", fullYearResultMap);
		model.addAttribute("midYearResultMap", midYearResultMap);
		return "result";
	}

}
