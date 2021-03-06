package com.company.appraisal.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.AppraisalPk;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.entities.Project;
import com.company.appraisal.entities.Vote;
import com.company.appraisal.entities.VoteId;
import com.company.appraisal.highcharts.VotedAppraisal;
import com.company.appraisal.highcharts.VotesPerAppraisal;
import com.company.appraisal.wrappers.Team;

@Repository
public interface VoteRepository extends CrudRepository<Vote, VoteId>{
	
	Vote findByIdAppraisalAppraisalPkAndIdVoter(AppraisalPk appraisalPk, Employee voter);
	
	Optional<Vote> findById(VoteId id);
	
	List<Vote> findByIdVoterAndIdAppraisalAppraisalPkAppraisalCategory(Employee voter, AppraisalCategory category);

	@Query(value = "SELECT new com.company.appraisal.highcharts.VotesPerAppraisal (a.appraisalPk, COUNT(*)) "
			+ "FROM Appraisal a "
			+ "INNER JOIN Vote v ON a.appraisalPk = v.id.appraisal.appraisalPk "
			+ "WHERE a.appraisalPk.appraisalCategory = ?1 "
			+ "GROUP BY a.appraisalPk ")
	List<VotesPerAppraisal> getVotesPerAppraisal(AppraisalCategory category);	
	
	@Query(value = "SELECT new com.company.appraisal.highcharts.VotedAppraisal (a.appraisalPk, v.id.voter) "
			+ "FROM Appraisal a "
			+ "INNER JOIN Vote v ON a.appraisalPk = v.id.appraisal.appraisalPk "
			+ "WHERE a.appraisalPk.appraisalCategory = ?1 "
			+ "ORDER BY v.id.voter ")
	List<VotedAppraisal> getVotedAppraisals(AppraisalCategory category);
	
	@Query(value = "SELECT new com.company.appraisal.wrappers.Team (e, a, v) "
			+ "FROM Employee e "
			+ "JOIN e.user u ON u.enabled = true "
			+ "LEFT JOIN Appraisal a ON e.id = a.appraisalPk.employee AND a.appraisalPk.appraisalCategory = ?1 "
			+ "LEFT JOIN Vote v ON a = v.id.appraisal AND v.id.voter = ?2 "
			+ "WHERE e.user.project=?3")
	List<Team> getTeam(AppraisalCategory category, Employee voter, Project project);

}
