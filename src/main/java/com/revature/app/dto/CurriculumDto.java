package com.revature.app.dto;

import java.util.List;

import com.revature.app.model.Skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class CurriculumDto {

	public String name;
	public List<Skill> skillList;
	
	public CurriculumDto(String name) {
		this.name = name;
		this.skillList = null;
	}
}
