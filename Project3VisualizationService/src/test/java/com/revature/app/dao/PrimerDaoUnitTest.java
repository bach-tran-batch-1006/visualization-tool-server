package com.revature.app.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;

//import org.hibernate.Session;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//import com.revature.app.model.Category;
import com.revature.app.model.Primer;
//import com.revature.app.model.Skill;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class PrimerDaoUnitTest {
    
    //testing with sonarCloud
    @Autowired
    private PrimerDao pDao;

//    @Autowired
//    EntityManagerFactory emf;
//
//    private EntityManager em;
//
//    @BeforeEach
//    public void setup() {
//        em = emf.createEntityManager();
//    }

    @Test
    @Transactional
    @Commit
    @Order(0)
    void test_addPrimer_success() {
        Primer actual = pDao.save(new Primer("BackEnd Developer1", new ArrayList<>()));
        Primer expected = new Primer("BackEnd Developer1", new ArrayList<>());
        expected.setPrimerId(1);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Commit
    @Order(1)
    void test_getPrimerbyID_success() {

        Primer actual = pDao.findByPrimerId(1);
        Primer expected = new Primer("BackEnd Developer1", new ArrayList<>());
        expected.setPrimerId(1);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Commit
    @Order(2)
    void test_getAllPrimer_success() {
        pDao.save(new Primer("BackEnd Developer2", new ArrayList<>()));
        pDao.save(new Primer("BackEnd Developer3", new ArrayList<>()));
        List<Primer> actual = pDao.findAll();
        System.out.println("actual " + actual);

        List<Primer> expected = new ArrayList<>();
        Primer expected1 = new Primer("BackEnd Developer1", new ArrayList<>());
        expected1.setPrimerId(1);
        Primer expected2 = new Primer("BackEnd Developer2", new ArrayList<>());
        expected2.setPrimerId(2);
        Primer expected3 = new Primer("BackEnd Developer3", new ArrayList<>());
        expected3.setPrimerId(3);
        
        expected.add(expected1);
        expected.add(expected2);
        expected.add(expected3);

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Commit
    @Order(3)
    void test_updatePrimerByID_success() {
        //curriculumDao.findByCurriculumId(1);
        Primer actual = pDao.save(new Primer("update Developer", new ArrayList<>()));
        Primer expected = new Primer("update Developer", new ArrayList<>());
        expected.setPrimerId(4);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    @Commit
    @Order(4)
    void test_deletePrimerByID_success() {
        pDao.deleteById(1);
        
        Primer actual = pDao.findByPrimerId(1);
        Primer expected = null;
        assertEquals(expected, actual);
    }
}
