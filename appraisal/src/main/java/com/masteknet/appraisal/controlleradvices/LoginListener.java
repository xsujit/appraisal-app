package com.masteknet.appraisal.controlleradvices;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import com.masteknet.appraisal.services.EmployeeService;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	
	@Autowired
    private HttpSession session;
	@Autowired
	protected EmployeeService employeeService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
    	session.setAttribute("loggedInEmployee", employeeService.getLoggedInEmployee());
    }
}
