package com.masteknet.appraisals.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.AppraisalPk;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.Project;
import com.masteknet.appraisals.entities.Vote;
import com.masteknet.appraisals.entities.VoteId;
import com.masteknet.appraisals.models.Result;
import com.masteknet.appraisals.models.Team;

@Repository
public interface VoteRepository extends CrudRepository<Vote, VoteId>{
	
	Vote findByIdAppraisalAppraisalPkAndIdVoter(AppraisalPk appraisalPk, Employee voter);
	
	Optional<Vote> findById(VoteId id);
	
	List<Vote> findByIdVoterAndIdAppraisalAppraisalPkAppraisalCategory(Employee voter, AppraisalCategory category);

	@Query(value = "SELECT new com.masteknet.appraisals.models.Result (a.appraisalPk, COUNT(*)) "
			+ "FROM Appraisal a "
			+ "INNER JOIN Vote v ON a.appraisalPk = v.id.appraisal.appraisalPk "
			+ "WHERE a.appraisalPk.appraisalCategory = ?1 "
			+ "GROUP BY a.appraisalPk ")
	List<Result> countVotesPerEmployee(AppraisalCategory category);	
	
	@Query(value = "SELECT new com.masteknet.appraisals.models.Team (e, a, v) "
			+ "FROM Employee e "
			+ "LEFT JOIN Appraisal a ON e.id = a.appraisalPk.employee AND a.appraisalPk.appraisalCategory = ?1 "
			+ "LEFT JOIN Vote v ON a = v.id.appraisal AND v.id.voter = ?2 "
			+ "WHERE e.project=?3"
			)
	List<Team> getTeam(AppraisalCategory category, Employee voter, Project project);

}
