package com.revature.app.service;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dao.PrimerDao;
import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.dto.PrimerDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.PrimerNotFoundException;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Primer;
import com.revature.app.model.Visualization;

@Service
public class PrimerServices {

	String badParam = "The Primer ID provided must be of type int";
	String emptyParam = "The Primer ID was left blank";
	String emptyName = "The Primer name was left blank";
	
	@Autowired
	private PrimerDao primerDao;
	
	@Autowired
	private VisualizationDao vDao;

	
	@Transactional(rollbackOn = { CurriculumNotAddedException.class })
	public Primer addPrimer(PrimerDto primerDto) throws EmptyParameterException {
		Primer primer = new Primer(primerDto.getName(), primerDto.getSkillList());
		if (primerDto.getName().trim().equals("")) {
			throw new EmptyParameterException(emptyName);
		}
//		primer.setP1(curriculumDto.getP1());
//		curriculum.setP2(curriculumDto.getP2());
//		curriculum.setP3(curriculumDto.getP3());

//		if(curriculumDto.getP1()!=0) {
//			curriculum.setP1(curriculumDto.getP1());
//		}
//		if(curriculumDto.getP2()!=0) {
//			curriculum.setP2(curriculumDto.getP2());
//		}
//		if(curriculumDto.getP3() != 0) {
//			curriculum.setP3(curriculumDto.getP3());
//		}
		primer = primerDao.save(primer);
		return primer;
	}

	@Transactional(rollbackOn = {CurriculumNotFoundException.class})
	public Primer getPrimerByID(String primeId) throws PrimerNotFoundException, BadParameterException, EmptyParameterException {
		Primer primer = null;
		try {
			if(primeId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(primeId);
			primer = primerDao.findByPrimerId(id);
			if(primer == null) {
				throw new PrimerNotFoundException("The primer with ID " + primeId + " could not be found.");
			} else {
				return primer;
			}
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {EmptyCurriculumException.class})
	public List<Primer> getAllPrimers() {
		List<Primer> primers;
		primers = primerDao.findAll();
		return primers;
	}

	@Transactional
	public Primer updatePrimerByID(String primeId, PrimerDto primerDto)
			throws EmptyParameterException, PrimerNotFoundException, BadParameterException {
		Primer primer = null;
		try {
			if (primeId.trim().equals("")) {
				throw new EmptyParameterException(emptyParam);
			}
			if (primerDto.getName().trim().equals("")) {
				throw new EmptyParameterException("The primer name was left blank");
			}
			int id = Integer.parseInt(primeId);
			primer = primerDao.findByPrimerId(id);
			if (primer == null) {
				throw new PrimerNotFoundException("The primer could not be updated because it couldn't be found");
			} else {
				primer.setPrimerName(primerDto.getName());
				primer.setSkillList(primerDto.getSkillList());
//				primer.setP1(primerDto.getP1());
//				primer.setP2(curriculumDto.getP2());
//				primer.setP3(curriculumDto.getP3());

				primer = primerDao.save(primer);
			}
			return primer;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {PrimerNotFoundException.class, ForeignKeyConstraintException.class})
	public Primer deletePrimerByID(String primeId) throws PrimerNotFoundException, EmptyParameterException, BadParameterException {
		Primer primer = null;
		try {
			if(primeId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(primeId);
			primer = primerDao.findByPrimerId(id);
			if(primer == null) {
				throw new PrimerNotFoundException("The primer could not be deleted because it couldn't be found");
			} else {
				List<Visualization> vList = vDao.findAll();
				for(Visualization v : vList) {
					if(v.getCurriculumList().contains(id)) {
						List<Primer> newPrimer = v.getPrimerList();
						newPrimer.remove(primer);
						v.setPrimerList(newPrimer);
						vDao.save(v);
					}
				}
				primerDao.deleteById(primer.getPrimerId());
				primerDao.flush();
			}
			return primer;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		} /*catch (DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException("Please remove this primer from all visualizations before attempting to delete this curriculum");
		}*/
	}
}
