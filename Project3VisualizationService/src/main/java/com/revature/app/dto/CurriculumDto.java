package com.revature.app.dto;

import java.util.ArrayList;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class CurriculumDto {

	private String name;
	private List<Integer> skillList;
	
	public CurriculumDto(String name) {
		this.name = name;
		this.skillList = new ArrayList<>();

	}
}

