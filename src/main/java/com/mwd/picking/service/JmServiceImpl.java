package com.mwd.picking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mwd.picking.model.Jangman;
import com.mwd.picking.model.JmHeader;
import com.mwd.picking.persitence.mybatis.JmRepository;

@Service
public class JmServiceImpl {
	@Autowired
	 private JmRepository repository;
	
	public List<Jangman> selectJangman(Jangman jangman) throws Exception
   { 
		return repository.selectJangman(jangman);

   }
	
	public int updateJangmanPrintCount(String orderCode) throws Exception
   { 
		return repository.updateJangmanPrintCount(orderCode);
   }
	
	public List<JmHeader> getJmHeaderInfo(HashMap param) throws Exception{
        return this.repository.selectJmHeaderInfo(param); 
    }
	    
	public List<Map> getJmDetailInfo(HashMap param) throws Exception{
        return this.repository.selectJmDetailInfo(param); 
    }
}
