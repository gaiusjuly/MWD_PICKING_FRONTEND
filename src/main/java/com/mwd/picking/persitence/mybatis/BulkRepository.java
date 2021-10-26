package com.mwd.picking.persitence.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Repository;

import com.mwd.picking.model.Manual;


@Repository
public class BulkRepository {
	private final SqlSession sqlSession;

	public BulkRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 이형주문목록조회
	public List<Manual> selectBulkDeliList(Manual data) {
		return this.sqlSession.selectList("selectBulkDeliList", data);
	}

	// 이형피킹대상조회
	@SuppressWarnings("rawtypes")
	public List<HashMap> selectBulkPickList(HashMap map) {
		return this.sqlSession.selectList("selectBulkPickList", map);
	}
		
	// IF_TR_DETAIL 상태 업데이트
	@SuppressWarnings("rawtypes")
	public int updateBulkDetailStatus(HashMap map) {
		return this.sqlSession.update("updateBulkDetailStatus",map);
	}
		
	// IF_TR_PICK 이형피킹정보 생성
	@SuppressWarnings("rawtypes")
	public int insertBulkPick(HashMap map) {
		return this.sqlSession.insert("insertBulkPick",map);
	}
		
	// IF_TR_HEADER 상태 업데이트
	@SuppressWarnings("rawtypes")
	public int updateBulkHeaderStatus(HashMap map) {
		return this.sqlSession.update("updateBulkHeaderStatus",map);
	}
	
	// IF_TR_PICK 상태 업데이트
	@SuppressWarnings("rawtypes")
	public int updateBulkPickStatus(HashMap map) {
		return this.sqlSession.update("updateBulkPickStatus",map);
	}
	
	// 대채상품의 원상품정보 조회
	@SuppressWarnings("rawtypes")
	public List<HashMap> selectBulkOrgReplaceList(HashMap map) {
		return this.sqlSession.selectList("selectBulkOrgReplaceList", map);
	}

}
