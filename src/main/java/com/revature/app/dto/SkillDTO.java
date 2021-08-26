package com.revature.app.dto;

import com.revature.app.model.Category;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @EqualsAndHashCode @Getter @ToString @Setter @NoArgsConstructor
public class SkillDTO {

	String name;
	Category category;
}
