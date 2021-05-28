package com.revature.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.revature.app.dto.CurriculumDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Curricula")
@AllArgsConstructor @EqualsAndHashCode @Getter @Setter @ToString @NoArgsConstructor
public class Curriculum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "curriculum_id")
	private int curriculumId;

	@Column(name = "curriculum_name")
	private String curriculumName;

	@ManyToMany(cascade = { CascadeType.MERGE})
	@JoinTable(name = "Curricula_Skills", joinColumns = { @JoinColumn(name = "curriculum_id") }, inverseJoinColumns = {
			@JoinColumn(name = "skill_id") })
	private List<Skill> skillList;
	
	public Curriculum(CurriculumDto dto) {
		this.curriculumName = dto.name;
		this.skillList = dto.skillList;
	}
}
