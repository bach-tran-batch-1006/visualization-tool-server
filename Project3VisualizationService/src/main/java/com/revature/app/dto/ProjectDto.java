package com.revature.app.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDto {

	private String name;
	
	private List<Integer> skillList;

	public ProjectDto(String name) {
		super();
		this.name = name;
		this.skillList = new ArrayList<Integer>();
	}
	
	
}
