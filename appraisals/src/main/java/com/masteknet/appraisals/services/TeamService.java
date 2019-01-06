package com.masteknet.appraisals.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.Comment;
import com.masteknet.appraisals.entities.CommentId;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.Vote;
import com.masteknet.appraisals.entities.VoteId;
import com.masteknet.appraisals.models.Result;
import com.masteknet.appraisals.models.Team;
import com.masteknet.appraisals.repositories.CommentRepository;
import com.masteknet.appraisals.repositories.VoteRepository;

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
	
	public List<Result> getVotesPerEmployee(AppraisalCategory category) {
		return voteRepository.countVotesPerEmployee(category);
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
