package com.revature.app.dto;

import java.util.List;

import com.revature.app.model.Curriculum;
import com.revature.app.model.Primer;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class VisualizationDTO {

	String title;
	
	List<Curriculum> curricula;
	
	List<Primer> primers;
}
