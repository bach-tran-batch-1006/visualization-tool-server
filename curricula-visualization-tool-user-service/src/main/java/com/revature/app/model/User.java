package com.revature.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity


@Table
public class User {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false)
    private String first; // first name

    @Column(nullable = false)
    private String last; // last name

 
    @Column( nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pass; // password
    
    //@OneToMany(mappedBy = "visualizationHolder", fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Visualization> visualizationList = new ArrayList<Visualization>();
    
    public User(String first, String last, String email, String pass) {
		super();
		this.first = first;
		this.last = last;
		this.email = email;
		this.pass = pass;
	}


	public User(String email, String pass) {
		super();
		this.email = email;
		this.pass = pass;
	}

	public User(int id) {
		super();
		this.id = id;
		
	}

}
