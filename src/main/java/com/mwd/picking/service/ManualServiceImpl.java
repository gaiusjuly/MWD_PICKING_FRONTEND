package com.mwd.picking.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mwd.picking.model.Event;
import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Jangman;
import com.mwd.picking.model.Manual;
import com.mwd.picking.model.Pick;
import com.mwd.picking.model.Inspection;
import com.mwd.picking.persitence.mybatis.ManualRepository;
import com.mwd.picking.web.PickGenController;

@Service
public class ManualServiceImpl {
private static final Logger logger = LoggerFactory.getLogger(ManualServiceImpl.class);
	
	@Autowired
	private ManualRepository repository;

	public List<Manual> selectManualPickList(Manual data) throws Exception
    { 
		return repository.selectManualPickList(data);
    }
  
	public List<Inspection> selectInspection(Inspection data) throws Exception
	{ 
		return repository.selectInspection(data);
    }
	
	public List<Event> selectEventList(Event data) throws Exception
	{ 
		return repository.selectEventList(data);
    }
  
//	public int updateInspection(Inspection data) throws Exception{
//		repository.updateInspectionD(data);
//		repository.updateInspectionH(data);
//		return 1;
//	}
}
