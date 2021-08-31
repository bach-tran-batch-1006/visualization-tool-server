package com.revature.app.dto;

import java.util.List;

import com.revature.app.model.Curriculum;
import com.revature.app.model.Primer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor 
@NoArgsConstructor
public class VisualizationDTO {

	String title;
	
	List<Curriculum> curricula;
	
	List<Primer> primers;
}
