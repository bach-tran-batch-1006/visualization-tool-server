package com.revature.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Visualization;

@Service
public class VisualizationService {

	@Autowired
	private VisualizationDao visualizationDao;

	@Transactional
	public Visualization createVisualization(VisualizationDTO visualizationdto) throws BadParameterException {

		Visualization visualization = new Visualization();
		if (visualizationdto.getTitle().trim().equals("") || visualizationdto.getCurricula() == null) {
			throw new BadParameterException("Parameter can't be blank");
		}
		visualization.setCurriculumList(visualizationdto.getCurricula());

		visualization.setVisualizationName(visualizationdto.getTitle());
		visualization = this.visualizationDao.save(visualization);

		return visualization;
	}

	@Transactional
	public Visualization findVisualizationByID(int id) throws VisualizationNotFoundException {

		Optional<Visualization> optVisualization = this.visualizationDao.findById(id);
		if (!optVisualization.isPresent()) {
			throw new VisualizationNotFoundException("Visualization not found");
		}

		return optVisualization.get();
	}

	@Transactional
	public Visualization updateVisualizationByID(Integer id, VisualizationDTO visualizationdto)
			throws VisualizationNotFoundException, BadParameterException {

		if (visualizationdto.getTitle().trim().equals("") || visualizationdto.getCurricula() == null) {
			throw new BadParameterException("Parameter can't be blank");
		}
		Optional<Visualization> diagram = this.visualizationDao.findById(id);
		if (!diagram.isPresent()) {
			throw new VisualizationNotFoundException("Visualization not Found");
		}

		Visualization visualization = diagram.get();
		if (visualizationdto.getTitle().length() > 0) {
			visualization.setVisualizationName(visualizationdto.getTitle());
		}
		visualization = this.visualizationDao.save(visualization);
		return visualization;

	}

	@Transactional
	public int deleteVisualizationByID(int id) throws VisualizationNotFoundException {

		if (!visualizationDao.existsById(id)) {
			throw new VisualizationNotFoundException("Visualization not found");

		}
		this.visualizationDao.deleteById(id);
		return 1;
	}

	
	public List<Visualization> findAllVisualization() {

		return visualizationDao.findAll();

	}

}
