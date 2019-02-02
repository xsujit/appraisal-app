package com.masteknet.appraisal.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masteknet.appraisal.highcharts.VotedAppraisal;
import com.masteknet.appraisal.highcharts.DrillDown;
import com.masteknet.appraisal.highcharts.VotesPerAppraisal;
import com.masteknet.appraisal.highcharts.SeriesData;
import com.masteknet.appraisal.services.TeamService;

@Controller
public class ResultController extends AppraisalBase {
	
	@Autowired
	private TeamService teamService;

	@GetMapping("/result/full-year")
	public String getResult(Model model) {

		List<VotesPerAppraisal> fullYearResults = teamService.getVotesPerAppraisal(getAppraisalCategory());
		TreeMap<Long, Long> fullYearResultMap = new TreeMap<>();
		TreeMap<Long, Long> midYearResultMap = new TreeMap<>();
		for(VotesPerAppraisal result : fullYearResults) {
			fullYearResultMap.put(result.getAppraisalPk().getEmployee().getId(), result.getVotes());
		}
		if (appraisalCategoryService.getAppraisalType().getType() == 1) { // if full year appraisal, then get mid year votes
			
			List<VotesPerAppraisal> midYearResults = teamService.getVotesPerAppraisal(appraisalCategoryService.getAppraisalCategory((byte) 0));
			for(VotesPerAppraisal result : midYearResults) {
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
		System.out.println(fullYearResultMap);
		model.addAttribute("fullYearResultMap", fullYearResultMap);
		model.addAttribute("midYearResultMap", midYearResultMap);
		return "result-year";
	}
	
	@GetMapping("/result")
	public String getResultDrill(Model model) throws JsonProcessingException {
		
		List<VotesPerAppraisal> fullYearResults = teamService.getVotesPerAppraisal(getAppraisalCategory());
		List<SeriesData> seriesDataList = new ArrayList<>();
		for(VotesPerAppraisal result : fullYearResults) {
			seriesDataList.add(new SeriesData(result.getAppraisalPk().getEmployee().getFirstName(), result.getVotes(), result.getAppraisalPk().getEmployee().getId()));
		}
		ObjectMapper objectMapper = new ObjectMapper();
		model.addAttribute("seriesData", objectMapper.writeValueAsString(seriesDataList));
		List<VotedAppraisal> votedAppraisals = teamService.getVotedAppraisals(getAppraisalCategory());
		List<DrillDown> drillDownList = new ArrayList<>(); // master list
		for(VotedAppraisal votedAppraisal : votedAppraisals) {
			boolean found = false;
			if(!drillDownList.isEmpty()) {
				for(DrillDown ddt : drillDownList) {
					if(ddt.getId() == votedAppraisal.getAppraisalPk().getEmployee().getId()) {
						found = true;
						ddt.getData().put(votedAppraisal.getVoter().getId(), 1); 
					}
				}
			} 
			if (!found) {
				Map<Long, Integer> dataMap = new HashMap<>();
				DrillDown ddt = new DrillDown();
				ddt.setId(votedAppraisal.getAppraisalPk().getEmployee().getId());
				dataMap.put(votedAppraisal.getVoter().getId(), 1);
				ddt.setData(dataMap);
				drillDownList.add(ddt);	
			}			
		}
		model.addAttribute("drillDown", objectMapper.writeValueAsString(drillDownList));
		return "result-current";
	}

}
