package com.mwd.picking.persitence.mybatis;

import org.apache.ibatis.session.SqlSession;

import com.mwd.picking.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author Sayyoung
 */
//@Component
@Repository
public class UserRepository {
	private final SqlSession sqlSession;

	  public UserRepository(SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }

	  public User loadUserByUsername(String userId) {
		  System.err.println("userId is "+ userId);
		  User usr = this.sqlSession.selectOne("selectUser",userId);
		//  System.err.println("rtn user "+ usr);
	    //return this.sqlSession.selectOne("selectUser",userId);
		  return usr;
	  }

}
