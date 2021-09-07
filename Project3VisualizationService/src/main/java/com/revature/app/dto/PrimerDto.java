package com.revature.app.dto;



import java.util.ArrayList;
import java.util.List;

//import com.revature.app.model.Curriculum;

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
public class PrimerDto {

    private String name;
    private List<Integer> skillList;
    public PrimerDto(String name) {
        this.name = name;
        this.skillList = new ArrayList<>();
    }
}
