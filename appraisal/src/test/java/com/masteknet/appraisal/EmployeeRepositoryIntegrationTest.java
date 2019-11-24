package com.masteknet.appraisal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.appraisal.AppraisalApplication;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.entities.Project;
import com.company.appraisal.entities.User;
import com.company.appraisal.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=AppraisalApplication.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
    	Project project = new Project();
    	project.setDescription("SLC Onsite TnM");
    	project.setTitle("SLC Onsite");
    	project.setId(1);
    	User user = new User("password", "sujit@mastek.com", project);
        Employee sujit = new Employee(11226, user, "Sujit", "Mohanan", "Glasgow");
        entityManager.persist(sujit);
        entityManager.flush();
     
        // when
        Employee found = employeeRepository.findById(11226);
     
        // then
        assertThat(found.getUser())
          .isEqualTo(sujit.getUser());
    }

}
