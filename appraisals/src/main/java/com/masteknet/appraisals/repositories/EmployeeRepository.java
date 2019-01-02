package com.masteknet.appraisals.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.Project;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	@EntityGraph(value = "graph.Employee.user", type = EntityGraph.EntityGraphType.FETCH)
	Employee findByUserId(long userId);
	@Query(value="SELECT e FROM Employee e WHERE e.id=?1")
	Employee findById(long employeeId);
	Iterable<Employee> findByUserProject(Project project);
	
}
