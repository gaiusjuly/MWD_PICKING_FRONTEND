package com.mwd.picking.persitence.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.data.mapping.AccessOptions.SetOptions.Propagation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mwd.picking.model.Inspection;

@Repository
public class InspectionRepository {
	private static final int SUCCESS = 0;
	private static final int FAILED = -1;
	
	private final SqlSession sqlSession;

	public InspectionRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<Inspection> getInspection(Inspection data) {
		return this.sqlSession.selectList("getInspection", data);
	}
	
	public List<Inspection> getInspectionCandidate(Inspection data) {
		return this.sqlSession.selectList("getInspectionCandidate", data);
	}

	@Transactional
	public int updateInspectionPickForO01_O03(Inspection data) {
		return this.sqlSession.update("updateInspectionPickForO01_O03", data);
	}
	
	@Transactional
	public int updateInspectionDetailForO01_O03(Inspection data) {
		return this.sqlSession.update("updateInspectionDetailForO01_O03", data);
	}
	
	@Transactional
	public int updateInspectionHeaderForO01_O03(Inspection data) {
		return this.sqlSession.update("updateInspectionHeaderForO01_O03", data);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class })
	public int updateInspectionAll(List<Inspection> O01_O03List, List<Inspection> O09List) {
		try {
			for (int i = 0 ; i < O01_O03List.size(); i++) {
				this.sqlSession.update("updateInspectionPickForO01_O03", O01_O03List.get(i));
				this.sqlSession.update("updateInspectionDetailForO01_O03", O01_O03List.get(i));
				this.sqlSession.update("updateInspectionHeaderForO01_O03", O01_O03List.get(i));
			}
			
			for (int i = 0 ; i < O09List.size(); i++) {
				this.sqlSession.update("updateInspectionDetailForO09", O09List.get(i));
				this.sqlSession.update("updateInspectionHeaderForO09", O09List.get(i));
//				this.sqlSession.update("updateInspectionForO09", O09List.get(i));
			}
			
		} catch(Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
			return FAILED;
		}
		
		return SUCCESS;
	}
}
