package com.revature.app.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.Visualization.VisualizationDTO;
import com.revature.app.expections.VisualizationNotFound;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Visualization;

@Service
public class VisualizationService {

	@Autowired
	VisualizationDao visualizationDao;
	@Transactional
	public Visualization createVisualization(VisualizationDTO visualizationdto) {

		Visualization visualization = new Visualization();
		if (visualizationdto.getTitle().length() > 0) {
			visualization.setVisualizationName(visualizationdto.getTitle());
		}
		if (visualizationdto.getCurricula() != null) {
			visualization.setCurriculumList(visualizationdto.getCurricula());
		} else {
			visualization.setCurriculumList(new ArrayList<Curriculum>());
		}
		visualization = this.visualizationDao.save(visualization);

		return visualization;
	}
	@Transactional
	public Visualization findVisualizationByID(int id) throws VisualizationNotFound {

		Optional<Visualization> diagram = this.visualizationDao.findById(id);
		if (diagram.isPresent() == false) {
			throw new VisualizationNotFound();
		}

		return diagram.get();
	}

	@Transactional
	public Visualization UpdateVisualizationByID(Integer id, VisualizationDTO visualizationdto)
			throws VisualizationNotFound {

		Optional<Visualization> diagram = this.visualizationDao.findById(id);
		if (diagram.isPresent() == false) {
			throw new VisualizationNotFound();
		}

		Visualization visualization = diagram.get();
		if (visualizationdto.getTitle().length() > 0) {
			visualization.setVisualizationName(visualizationdto.getTitle());
		}
		visualization = this.visualizationDao.save(visualization);
		return visualization;

	}
	@Transactional
	public int deleteVisualizationByID(int id) throws VisualizationNotFound {

		if (visualizationDao.existsById(id) == false) {
			throw new VisualizationNotFound();

		}
		this.visualizationDao.deleteById(id);
		return 1;
	}

	@Transactional
	public Visualization findByName(String name) throws VisualizationNotFound {

		Visualization visualization = null;

		visualization = this.visualizationDao.findByVisualizationName(name);
		if (visualization == null) {
			throw new VisualizationNotFound();
		}

		return visualization;

	}
	public ArrayList<Visualization>  FindAllVisualization() throws VisualizationNotFound {
		
		ArrayList<Visualization> result= null;
		
		result =(ArrayList<Visualization>)this.visualizationDao.findAll();
		if(result == null )
		{
			throw new VisualizationNotFound();
		}
		return result;
	}

}
