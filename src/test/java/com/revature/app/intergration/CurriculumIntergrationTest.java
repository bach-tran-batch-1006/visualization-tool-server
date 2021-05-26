package com.revature.app.intergration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.app.dao.CurriculumDao;

public class CurriculumIntergrationTest {

	@Autowired
    private MockMvc mvc;

    @Autowired
    private CurriculumDao curriculumDao;
    
    
}
