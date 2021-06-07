package com.revature.app.dto;

import java.util.ArrayList;

import java.util.List;

import com.revature.app.model.Skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Getter
@Setter
public class CurriculumDto {

	private String name;
	private List<Skill> skillList;
	
	public CurriculumDto(String name) {
		this.name = name;
		this.skillList = new ArrayList<>();

	}
}
