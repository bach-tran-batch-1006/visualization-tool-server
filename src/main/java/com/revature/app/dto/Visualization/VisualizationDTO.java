package com.revature.app.dto.Visualization;



import java.util.List;

import com.revature.app.model.Curriculum;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class VisualizationDTO {

	String title;
	
	List<Curriculum> curricula;
}
