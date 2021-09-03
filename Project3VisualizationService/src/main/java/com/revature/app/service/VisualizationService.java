package com.revature.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.PrimerNotFoundException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Primer;
import com.revature.app.model.Visualization;

@Service
public class VisualizationService {
	
	String badParam = "The visualization ID provided must be of type int";
	String emptyParam = "The visualization ID was left blank";
	String emptyName = "The visualization name was left blank";
	String notFound = "Visualization not found";
	
//	@Autowired
//	private CurriculumService curriculumService;
//	
//	@Autowired
//	private PrimerServices primerService;

	@Autowired
	private VisualizationDao visualizationDao;
	
//	@Autowired
//	private PrimerServices primerService;

	//change logic
//	@Transactional
//	public Visualization createVisualization(VisualizationDTO visualizationDto) throws EmptyParameterException {
//		//check if title is empty, if so throw exception
//		if (visualizationDto.getTitle().trim().equals("")) {
//			throw new EmptyParameterException(emptyName);
//			//check if curricula is empty, if so instantiate with new empty list 
//		}else if(visualizationDto.getCurricula()==null) {
//			//List<Curriculum> curricula = new ArrayList<Curriculum>();
//			if(visualizationDto.getPrimers()==null) {
//				Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),null,null));
//				return visualization;
//			}else {
//			Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),null,visualizationDto.getPrimers()));
//			return visualization;
//			}
//		}else {
//			if(visualizationDto.getPrimers()==null) {
//				Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),visualizationDto.getCurricula(),null));
//				return visualization;
//			}
//			Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),visualizationDto.getCurricula(),visualizationDto.getPrimers()));
//			return visualization;
//		}
//
//	}
	//this method is fine
	@Transactional(rollbackOn = {VisualizationNotFoundException.class})
	public Visualization findVisualizationByID(String visId) throws VisualizationNotFoundException, EmptyParameterException, BadParameterException {
		try {
			if(visId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(visId);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException(notFound);
			}
			return vis;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
	//switched up logic
	@Transactional
	public Visualization updateVisualizationByID(String visID, VisualizationDTO visualizationDto)
			throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {
		try {
			//check if id was sent
			if (visID.trim().equals("")) {
				throw new EmptyParameterException(emptyParam);
			}
			//pull visualization
			int id = Integer.parseInt(visID);
			Visualization vis = visualizationDao.findById(id);
			//check if the visualization with given id exists
			if (vis == null) {
				throw new VisualizationNotFoundException(notFound);
			}
			//check if a new title was sent and set visualization name if so
			if (visualizationDto.getTitle().trim().equals("")) {
				throw new EmptyParameterException("The visualization name was left blank");
			}
			ArrayList<Integer> persistantCurriculumList = new ArrayList<>();
			
			//check if new/updated curricula were sent, set curricula list to it if so
			if (!(visualizationDto.getCurricula()==null)) {
				for (Integer eachCurriculumDTO : (ArrayList<Integer>) visualizationDto.getCurricula()) {
					persistantCurriculumList.add(eachCurriculumDTO);
				}
				vis.setCurriculumList(persistantCurriculumList);
			}
			ArrayList<Integer> persistantPrimerList = new ArrayList<>();
			if (!(visualizationDto.getPrimers()==null)) {
				for (Integer eachPrimerDTO : (ArrayList<Integer>) visualizationDto.getPrimers()) {
					persistantPrimerList.add(eachPrimerDTO);
				}
				vis.setPrimerList(persistantPrimerList);
			}
			vis.setVisualizationName(visualizationDto.getTitle());
			//update the visualization
			vis = visualizationDao.save(vis);
			return vis;

		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}/*catch (NumberFormatException | CurriculumNotFoundException  | PrimerNotFoundException e) {
			throw new BadParameterException(badParam);
		}*/
	}
	//this method is fine//association between visualization and curricula breaks as its stored in visualization anyway
	@Transactional
	public int deleteVisualizationByID(String visID) throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {
		try {
			if(visID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(visID);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException(notFound);
			}
			visualizationDao.deleteById(id);
			return id;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	//this method is fine
	public List<Visualization> findAllVisualization() {
		return visualizationDao.findAll();
	}
	
	public Visualization createVisualization(VisualizationDTO visualizationDto) throws EmptyParameterException {
        //check if title is empty, if so throw exception
        if (visualizationDto.getTitle().trim().equals(" ")) {
            throw new EmptyParameterException(emptyName);
            //check if curricula is empty, if so instantiate with new empty list 
        }else if(visualizationDto.getCurricula() == null) {
            //List<Curriculum> curricula = new ArrayList<Curriculum>();
            if(visualizationDto.getPrimers() == null) {
                Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),null,null));
                return visualization;
            }else {
            Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),null,visualizationDto.getPrimers()));
            return visualization;
            }
        }else {
            if(visualizationDto.getPrimers()==null) {
                Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),visualizationDto.getCurricula(),null));
                return visualization;
            }
            Visualization visualization = visualizationDao.save(new Visualization(visualizationDto.getTitle(),visualizationDto.getCurricula(),visualizationDto.getPrimers()));
            return visualization;
        }

    }

	//only done in controller....needs to call skill and category microService
//		@Transactional(rollbackOn = {VisualizationNotFoundException.class})
//		public List<Integer> getAllSkillsByVisualization(String visID) throws EmptyParameterException, BadParameterException, VisualizationNotFoundException {
//			try {
//				if(visID.trim().equals("")){
//					throw new EmptyParameterException(emptyParam);
//				}
//				int id = Integer.parseInt(visID);
//				Visualization vis = visualizationDao.findById(id);
//				if (vis == null) {
//					throw new VisualizationNotFoundException(notFound);
//				}
//				//The above code is just a sanity check to make sure that the visualization exists before getting
//				//the skills by the visualization 
//				
//				//Now it runs the query of the database to get all the skills
//				return visualizationDao.skillVisList(id);
//			} catch (NumberFormatException e) {
//				throw new BadParameterException(badParam);
//			}
//		}
	
//	@Transactional(rollbackOn = {VisualizationNotFoundException.class})
//	public List<Category> getAllCategoriesByVisualization(String visID) throws EmptyParameterException, BadParameterException, VisualizationNotFoundException {
//		try {
//			if(visID.trim().equals("")){
//				throw new EmptyParameterException(emptyParam);
//			}
//			int id = Integer.parseInt(visID);
//			Visualization vis = visualizationDao.findById(id);
//			if (vis == null) {
//				throw new VisualizationNotFoundException(notFound);
//			}
//			//The above code is just a sanity check to make sure that the visualization exists before getting
//			//the skills by the visualization 
//			
//			//Now it runs the query of the database to get all the skills
//			return visualizationDao.catVisList(id);
//		} catch (NumberFormatException e) {
//			throw new BadParameterException(badParam);
//		}
//	}

}