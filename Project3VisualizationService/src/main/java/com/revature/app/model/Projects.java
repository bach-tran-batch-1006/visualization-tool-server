package com.revature.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Projects")
@AllArgsConstructor @EqualsAndHashCode @Getter @Setter @ToString @NoArgsConstructor
public class Projects {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int projectId;
	
	@Column(name = "project_name")
	private String projectName;
	
	@ElementCollection
	private List<Integer> skillList;

	public Projects(String projectName, List<Integer> skillList) {
		super();
		this.projectName = projectName;
		this.skillList = skillList;
	}

	
}
	