package com.company.appraisal.listeners;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.company.appraisal.services.AppraisalCategoryService;
import com.company.appraisal.services.EmployeeService;

@Component
public class AuthenticationEventListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	
	@Autowired
    private HttpSession session;
	@Autowired
	protected EmployeeService employeeService;
	@Autowired
	protected AppraisalCategoryService appraisalCategoryService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
    	session.setAttribute("loggedInEmployee", employeeService.getLoggedInEmployee());
    	session.setAttribute("appraisalCategory", appraisalCategoryService.getAppraisalCategory());
    }
}
