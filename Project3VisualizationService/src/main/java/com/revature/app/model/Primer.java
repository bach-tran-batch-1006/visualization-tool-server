package com.revature.app.model;

import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;

import javax.persistence.Table;

import com.revature.app.dto.PrimerDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Primer")
@AllArgsConstructor 
@EqualsAndHashCode 
@Getter 
@Setter 
@ToString 
@NoArgsConstructor
public class Primer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primer_id")
    private int primerId;

    @Column(name = "primer_name")
    private String primerName;


    @ElementCollection
    private List<Integer> skillList;

    public Primer(String primerName, List<Integer> skillList) {
        this.primerName = primerName;
        this.skillList = skillList;
    }

    public Primer(PrimerDto primeDTO) {
        this.primerName = primeDTO.getName();
        this.skillList = primeDTO.getSkillList();
    }
}
