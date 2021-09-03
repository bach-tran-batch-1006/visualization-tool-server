package com.revature.app.dao;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import com.revature.app.model.Category;
import com.revature.app.model.Primer;


@Repository
public interface PrimerDao extends JpaRepository<Primer, Integer>{    
    

    public Primer findByPrimerId(int primerId);

    public List<Primer> findAll();
} 
