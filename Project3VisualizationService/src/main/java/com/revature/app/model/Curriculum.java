package com.revature.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.revature.app.dto.CurriculumDto;

//import com.revature.app.dto.CurriculumDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Curricula")
@AllArgsConstructor 
@EqualsAndHashCode 
@Getter 
@Setter 
@ToString 
@NoArgsConstructor
public class Curriculum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "curriculum_id")
	private int curriculumId;

	@Column(name = "curriculum_name")
	private String curriculumName;

	@ElementCollection
	private List<Integer> skillList;
	
	public Curriculum(String curriculumName, List<Integer> skillList) {
		this.curriculumName = curriculumName;
		this.skillList = skillList;
	}
	
	public Curriculum(CurriculumDto curDTO) {
		this.curriculumName = curDTO.getName();
		this.skillList = curDTO.getSkillList();
	}
}
