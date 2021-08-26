package com.revature.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.revature.app.model.Curriculum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data @Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class VisualizationDTO {

	String title;
	int UserId;
	List<Curriculum> curricula;
	
	public VisualizationDTO(String title, int UserId) {
		this.title = title;
		this.UserId = UserId;
		this.curricula = new ArrayList<>();
	}
	
}
