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
	int UserId;
	String title;
	
	List<Curriculum> curricula;
	
	public VisualizationDTO(int UserId, String title) {
	
		this.UserId = UserId;
		this.title = title;
		this.curricula = new ArrayList<>();
	}
	
}
