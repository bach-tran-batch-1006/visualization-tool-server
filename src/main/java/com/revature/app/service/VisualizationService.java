package com.revature.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.VisualizationDao;

@Service
public class VisualizationService {
  
	 @Autowired
	 VisualizationDao visualizationDao;

}
