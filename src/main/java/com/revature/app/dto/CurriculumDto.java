package com.revature.app.dto;

import java.util.List;

import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @EqualsAndHashCode @Getter @Setter @ToString @NoArgsConstructor
public class CurriculumDto {
	
	public String name;
	public List<Skill> skillList;
	
	public CurriculumDto(String name) {
		this.name = name;
	}
	
}