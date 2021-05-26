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
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
			name = "Visualizations_Curricula",
			joinColumns = {@JoinColumn(name = "visualization_id")},
			inverseJoinColumns = {@JoinColumn(name = "curriculum_id")})
	private List<Curriculum> curriculumList;
}
