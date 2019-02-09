package com.masteknet.appraisal.controllers;

import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.services.AppraisalCategoryService;
import com.masteknet.appraisal.services.TeamService;

@Controller
public class ResultController {
	
	@Autowired
	private TeamService teamService;
	@Autowired
	protected AppraisalCategoryService appraisalCategoryService;

	@GetMapping("/result/full-year")
	public String getResult(Model model, HttpSession session) {
		
		AppraisalCategory appraisalCategory = (AppraisalCategory) session.getAttribute("appraisalCategory");
		if (appraisalCategory.getAppraisalType().getType() == 0) { // mid year
			
			model.addAttribute("midYearResultMap", teamService.computeMidYearResults(appraisalCategory));
			model.addAttribute("fullYearResultMap", new TreeMap<>());			
		} else { // full year
			Map<String, TreeMap<Long, Long>> combinedResultsMap = 
					teamService.computeFullYearResults(appraisalCategory, appraisalCategoryService.getAppraisalCategory((byte) 0));
			model.addAttribute("midYearResultMap", combinedResultsMap.get("mid"));
			model.addAttribute("fullYearResultMap", combinedResultsMap.get("full"));
		}
		return "result-year";
	}
	
	@GetMapping("/result")
	public String getResultDrill(Model model, HttpSession session) throws JsonProcessingException {
		
		AppraisalCategory appraisalCategory = (AppraisalCategory) session.getAttribute("appraisalCategory");
		Map<String, String> resultsMap = teamService.computeDrillDownResults(appraisalCategory);
		model.addAttribute("seriesData", resultsMap.get("seriesData"));
		model.addAttribute("drillDown", resultsMap.get("drillDown"));
		return "result-current";
	}

}
