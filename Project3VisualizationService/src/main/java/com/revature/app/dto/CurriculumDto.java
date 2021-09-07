package com.revature.app.dto;

import java.util.ArrayList;

import java.util.List;

//import com.revature.app.model.Curriculum;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class CurriculumDto {

	private String name;
	private List<Integer> skillList;
	private int p1;
	private int p2;
	private int p3;
	
	public CurriculumDto(String name) {
		this.name = name;
		this.skillList = new ArrayList<>();
		this.p1 = 0;
		this.p2 = 0;
		this.p3 = 0;
	}
}

