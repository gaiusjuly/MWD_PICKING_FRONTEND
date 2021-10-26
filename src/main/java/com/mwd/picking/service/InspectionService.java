package com.mwd.picking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mwd.picking.model.Inspection;
import com.mwd.picking.persitence.mybatis.InspectionRepository;

@Service
public class InspectionService {
	@Autowired
	private InspectionRepository repository;
//	private static final int SUCCESS = 0;
  
	public List<Inspection> selectInspection(Inspection data) throws Exception { 
		return repository.getInspection(data);
    }
	
	public List<Inspection> confirmInspection(Inspection data) throws Exception {
		return repository.getInspectionCandidate(data);
    }
	
	public int updateInspectionPickForO01_O03(Inspection data) throws Exception {
		return repository.updateInspectionPickForO01_O03(data);
	}
	
	public int updateInspectionDetailForO01_O03(Inspection data) throws Exception {
		return repository.updateInspectionDetailForO01_O03(data);
	}
	
	public int updateInspectionHeaderForO01_O03(Inspection data) throws Exception {
		return repository.updateInspectionHeaderForO01_O03(data);
	}

	@Transactional
	public int updateInspectionAll(List<Inspection> O01_O03List, List<Inspection> O09List) {
		return repository.updateInspectionAll(O01_O03List, O09List);
	}
}
