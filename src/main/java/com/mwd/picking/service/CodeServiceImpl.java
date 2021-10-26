package com.mwd.picking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mwd.picking.persitence.mybatis.CodeRepository;
import com.mwd.picking.model.Code;

@Service
public class CodeServiceImpl {
	
	@Autowired
	 private CodeRepository repository;
	
	public List<Code> selectCommcode(String codeDiv) throws Exception
    { 
		return repository.selectCommcode(codeDiv);
    }
	
}
