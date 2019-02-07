package com.masteknet.appraisal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.entities.Employee;
import com.masteknet.appraisal.entities.Project;
import com.masteknet.appraisal.wrappers.TeamStatistics;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	@EntityGraph(value = "graph.Employee.user", type = EntityGraph.EntityGraphType.FETCH)
	Employee findByUserId(long userId);
	
	@Query(value="SELECT e FROM Employee e WHERE e.id=?1")
	Employee findById(long employeeId);
	
	Iterable<Employee> findByUserProject(Project project);
	
	Iterable<Employee> findByUserProjectAndUserEnabled(Project project, boolean status);
	
	@Query(value="SELECT new com.masteknet.appraisal.wrappers.TeamStatistics (e, a, COUNT(v)) "
			+ "FROM Employee e "
			+ "JOIN e.user u "
			+ "LEFT JOIN Appraisal a ON e.id = a.appraisalPk.employee "
			+ "LEFT JOIN Vote v ON e.id = v.id.voter AND v.id.appraisal.appraisalPk.appraisalCategory = ?1 "
			+ "WHERE u.project = ?2 AND a.appraisalPk.appraisalCategory = ?1 "
			+ "GROUP BY e, a"
			)
	List<TeamStatistics> getTeamStats(AppraisalCategory category, Project project);
}
