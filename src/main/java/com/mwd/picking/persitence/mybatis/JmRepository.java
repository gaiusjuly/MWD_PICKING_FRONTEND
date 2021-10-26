package com.mwd.picking.persitence.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.mwd.picking.model.Jangman;
import com.mwd.picking.model.JmHeader;

@Repository
public class JmRepository {
	private final SqlSession sqlSession;

	  public JmRepository(SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }

	  public List<Jangman> selectJangman(Jangman jangman) {
	    return this.sqlSession.selectList("selectJangmanList", jangman);
	  }
	  
	  public int updateJangmanPrintCount(String orderCode) {
		    this.sqlSession.update("insertJmPrintOrder", orderCode);
		    return this.sqlSession.update("insertJmPrintOrder1", orderCode);
		    
	  }
	  
	  
	  public List<JmHeader> selectJmHeaderInfo(HashMap param){
			return this.sqlSession.selectList("getJmHeaderInfo",param);
		}
		
	  public List<Map> selectJmDetailInfo(HashMap param){
			return this.sqlSession.selectList("getJmDetailInfo",param);
	  }
	  
	  
}
