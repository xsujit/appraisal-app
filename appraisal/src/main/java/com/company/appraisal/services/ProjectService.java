package com.company.appraisal.services;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.appraisal.entities.Project;
import com.company.appraisal.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	private final ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		super();
		this.projectRepository = projectRepository;
	}
	
	public Iterable<Project> getProjects() {
		return projectRepository.findAll();
	}
	
	public Project getProject(long id) {
		return projectRepository.findById(id);
	}
	
	public Map<Long, String> createProjectMap(){
		Map<Long, String> projectMap = new HashMap<>();
		projectMap.put((long) 0, "Please select");
		Iterable<Project> projects = getProjects();
		for(Project project : projects) {
			projectMap.put(project.getId(), project.getTitle());
		}
		return projectMap;
	}

}
