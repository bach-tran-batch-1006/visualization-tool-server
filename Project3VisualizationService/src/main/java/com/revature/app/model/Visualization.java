package com.revature.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Visualizations")
@AllArgsConstructor @EqualsAndHashCode @Getter @Setter @ToString @NoArgsConstructor
public class Visualization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "visualization_id")
	private int visualizationId;
	
	@Column(name = "visualization_name")
	private String visualizationName;
	
	@ElementCollection
	private List<Curriculum> curriculumList;

	public Visualization(String visualizationName, List<Curriculum> curriculumList) {
		super();
		this.visualizationName = visualizationName;
		this.curriculumList = curriculumList;
	}
	
	
}
