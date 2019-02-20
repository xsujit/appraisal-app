package com.company.appraisal.controllers;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.services.AppraisalCategoryService;
import com.company.appraisal.services.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class ResultController {
	
	@Autowired
	private TeamService teamService;
	@Autowired
	protected AppraisalCategoryService appraisalCategoryService;

	@GetMapping("/result/full-year")
	public String getResult(Model model, @ModelAttribute("loggedInEmployee") Employee me, @ModelAttribute("appraisalCategory") AppraisalCategory category) {
		
		if (category.getAppraisalType().getType() == 0) { // mid year
			
			model.addAttribute("midYearResultMap", teamService.computeMidYearResults(category));
			model.addAttribute("fullYearResultMap", new TreeMap<>());			
		} else { // full year
			Map<String, TreeMap<Long, Long>> combinedResultsMap = 
					teamService.computeFullYearResults(category, appraisalCategoryService.getAppraisalCategory((byte) 0));
			model.addAttribute("midYearResultMap", combinedResultsMap.get("mid"));
			model.addAttribute("fullYearResultMap", combinedResultsMap.get("full"));
		}
		return "result-year";
	}
	
	@GetMapping("/result")
	public String getResultDrill(Model model, @ModelAttribute("loggedInEmployee") Employee me, 
			@ModelAttribute("appraisalCategory") AppraisalCategory category) throws JsonProcessingException {
		
		Map<String, String> resultsMap = teamService.computeDrillDownResults(category);
		model.addAttribute("seriesData", resultsMap.get("seriesData"));
		model.addAttribute("drillDown", resultsMap.get("drillDown"));
		return "result-current";
	}

}
