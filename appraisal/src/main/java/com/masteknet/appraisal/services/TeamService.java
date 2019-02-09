package com.masteknet.appraisal.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masteknet.appraisal.entities.Appraisal;
import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.entities.Comment;
import com.masteknet.appraisal.entities.CommentId;
import com.masteknet.appraisal.entities.Employee;
import com.masteknet.appraisal.entities.Vote;
import com.masteknet.appraisal.entities.VoteId;
import com.masteknet.appraisal.highcharts.DrillDown;
import com.masteknet.appraisal.highcharts.SeriesData;
import com.masteknet.appraisal.highcharts.VotedAppraisal;
import com.masteknet.appraisal.highcharts.VotesPerAppraisal;
import com.masteknet.appraisal.repositories.CommentRepository;
import com.masteknet.appraisal.repositories.VoteRepository;
import com.masteknet.appraisal.wrappers.Team;

@Service
public class TeamService {

	// repositories
	private final VoteRepository voteRepository;
	private final CommentRepository commentRepository;
	
	@Autowired
	public TeamService(VoteRepository voteRepository, CommentRepository commentRepository) {
		super();
		this.voteRepository = voteRepository;
		this.commentRepository = commentRepository;
	}
	
	public ArrayList<Team> getTeam(AppraisalCategory appraisalCategory, Employee voter) {
		List<Team> teamList = new ArrayList<>();
		teamList = voteRepository.getTeam(appraisalCategory, voter, voter.getUser().getProject());
		return (ArrayList<Team>) teamList;
	}
	
	public boolean hasVoted(Appraisal appraisal, Employee me) { // check if voted for an appraisal
		Optional<Vote> vote = voteRepository.findById(new VoteId(me, appraisal));
		return vote.isPresent();
	}

	public boolean hasVoted(Employee voter, AppraisalCategory category) { // check if voted in a category
		List<Vote> vote = getVotes(voter, category);
		if(!vote.isEmpty()) {
			return true;
		} 
		return false;
	}
	
	public boolean selfVote(Appraisal appraisal, Employee me) {
		return (me.equals(appraisal.getAppraisalPk().getEmployee())) ? true : false;
	}
	
	public List<VotesPerAppraisal> getVotesPerAppraisal(AppraisalCategory category) {
		return voteRepository.getVotesPerAppraisal(category);
	}
	
	public Map<String, String> computeDrillDownResults(AppraisalCategory appraisalCategory) throws JsonProcessingException {
		
		List<VotesPerAppraisal> fullYearResults = getVotesPerAppraisal(appraisalCategory);
		List<SeriesData> seriesDataList = new ArrayList<>();
		for(VotesPerAppraisal result : fullYearResults) {
			seriesDataList.add(new SeriesData(result.getAppraisalPk().getEmployee().getFirstName(), result.getVotes(), result.getAppraisalPk().getEmployee().getId()));
		}
		ObjectMapper objectMapper = new ObjectMapper();
		List<VotedAppraisal> votedAppraisals = getVotedAppraisals(appraisalCategory);
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
		Map<String, String> resultsMap = new HashMap<>();
		resultsMap.put("seriesData", objectMapper.writeValueAsString(seriesDataList));
		resultsMap.put("drillDown", objectMapper.writeValueAsString(drillDownList));
		return resultsMap;
	}
	
	public TreeMap<Long, Long> computeMidYearResults(AppraisalCategory appraisalCategory) {
		
		TreeMap<Long, Long> midYearResultMap = new TreeMap<>();
		List<VotesPerAppraisal> midYearResults = getVotesPerAppraisal(appraisalCategory);
		for(VotesPerAppraisal result : midYearResults) {
			midYearResultMap.put(result.getAppraisalPk().getEmployee().getId(), result.getVotes());
		}
		return midYearResultMap;
	}
	
	public Map<String, TreeMap<Long, Long>> computeFullYearResults(AppraisalCategory current, AppraisalCategory previous) {

		List<VotesPerAppraisal> fullYearResults = getVotesPerAppraisal(current);
		TreeMap<Long, Long> fullYearResultMap = new TreeMap<>();
		TreeMap<Long, Long> midYearResultMap = new TreeMap<>();
		for(VotesPerAppraisal result : fullYearResults) {
			fullYearResultMap.put(result.getAppraisalPk().getEmployee().getId(), result.getVotes());
		}
		if (current.getAppraisalType().getType() == 1) { // if full year appraisal, then get mid year votes
			
			List<VotesPerAppraisal> midYearResults = getVotesPerAppraisal(previous);
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
		Map<String, TreeMap<Long, Long>> combinedResultsMap = new HashMap<>();
		combinedResultsMap.put("full", fullYearResultMap);
		combinedResultsMap.put("mid", midYearResultMap);
		return combinedResultsMap;
	}
	
	public List<VotedAppraisal> getVotedAppraisals(AppraisalCategory category) {
		return voteRepository.getVotedAppraisals(category);
	}

	public List<Vote> getVotes(Employee voter, AppraisalCategory category) {
		List<Vote> vote = voteRepository.findByIdVoterAndIdAppraisalAppraisalPkAppraisalCategory(voter, category);
		return vote;
	}

	public void saveVote(Appraisal appraisal, Employee me) {
		Vote vote = new Vote();
		vote.setId(new VoteId(me, appraisal));
		voteRepository.save(vote);
	}
	
	public void saveComment(Comment comment, Appraisal appraisal, Employee commenter) {
		
		CommentId commentId = new CommentId(commenter, appraisal);
		comment.setCommentId(commentId);
		commentRepository.save(comment);
	}
	
	public List<Comment> getComments(Appraisal appraisal){
		List<Comment> comments = commentRepository.findByCommentIdAppraisal(appraisal);
		return comments;
	}
}
