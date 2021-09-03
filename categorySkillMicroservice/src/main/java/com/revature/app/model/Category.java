package com.revature.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="Categories")
@AllArgsConstructor @EqualsAndHashCode @Getter @Setter @ToString @NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int categoryId;
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "category_description")
	private String categoryDescription;

	@Column(name="userid")
	private int userid = 0;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy="category") 
	@JsonIgnore
	private List<Skill> SkillList = new ArrayList<Skill>();
	
	public Category(String categoryName, String categoryDescription, int userid) {
		super();
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.userid = userid;
	}

//	public Category(int categoryId, String categoryName, String categoryDescription) {
//		super();
//		this.categoryId = categoryId;
//		this.categoryName = categoryName;
//		this.categoryDescription = categoryDescription;
//	}
	
	@PreRemove
	private void preRemove() {
	    for (Skill s : SkillList ) {
	        s.setCategory(null);
	    }
	}
	
}
