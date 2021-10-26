package com.mwd.picking.persitence.mybatis;

import java.util.HashMap;
import java.util.List;

import com.mwd.picking.model.TrDetail;
import com.mwd.picking.model.TrHeader;
import org.apache.ibatis.session.SqlSession;

//import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Pick;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.mwd.picking.model.Event;
import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Inspection;
import com.mwd.picking.model.Manual;

@Repository
public class ManualRepository {
	private final SqlSession sqlSession;

	 public ManualRepository(SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }

	 public String selectNewDeliNo(String data) {
		return this.sqlSession.selectOne("selectNewDeliNo", data);
	}

	 public int insertTrHeaderByNewDeliNo(TrHeader trHeader) {
		    return this.sqlSession.insert("insertTrHeader",trHeader);
	  }
	  
	  public int insertTrDetailByNewDeliNo(TrDetail trDetail) {
		    return this.sqlSession.insert("insertTrDetail", trDetail);
	  }

	public String selectMaxDeliDetailSeq(String deliNo) {
		return this.sqlSession.selectOne("selectMaxTrdetailDeliNoSeq", deliNo);
	}
	
	public List<TrDetail> selectTrDetail(Pick data) {
		return this.sqlSession.selectList("selectTrDetail", data);
	}
	public List<TrHeader> selectTrHeader(Pick data) {
		return this.sqlSession.selectList("selectTrHeader", data);
	}
	
	public List<Manual> selectManualPickList(Manual data) {
		return this.sqlSession.selectList("selectManualPick", data);
	}
	
	public List<Inspection> selectInspection(Inspection data) {
		return this.sqlSession.selectList("selectInspection", data);
	}

  public int updateInspectionD(Pick data) {
	    return this.sqlSession.update("updateInspectionD", data);
  }

  public int updateInspectionH(Pick data) {
	    return this.sqlSession.update("updateInspectionH", data);
  }
  
  public List<Event> selectEventList(Event data) {
		return this.sqlSession.selectList("selectEvent", data);
  }

}
