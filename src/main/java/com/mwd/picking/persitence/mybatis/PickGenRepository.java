/**
 *    Copyright 2015-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mwd.picking.persitence.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Pick;
import org.springframework.stereotype.Repository;

/**
 * @author Sayyoung
 */
@Repository

public class PickGenRepository {
	private final SqlSession sqlSession;

	  public PickGenRepository(SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }

	  public List<GroupHeader> selectGroupHeader(GroupHeader data) {
	    return this.sqlSession.selectList("selectGroupHeader", data);
	  }
	  
	  public String selectGroupSeq() {
		    return this.sqlSession.selectOne("getGroupSeq");
	  }
	  
	  public int insertGroupHeader(GroupHeader header) {
		    return this.sqlSession.insert("insertGroupHeader",header);
	  }
	  
	  public int insertGroupDetail(GroupHeader header) {
		    return this.sqlSession.insert("insertGroupDetail", header);
	  }
	  
	  public int updateTrHeader(GroupHeader header) {
		    return this.sqlSession.update("updateTrHeader", header);
	  }
	  
	  public int updateTrHeaderPost(String deliNo) {
		    return this.sqlSession.update("updateTrHeaderPost", deliNo);
	  }
	  
	  public int updateTrDetail(GroupHeader header) {
		    return this.sqlSession.update("updateTrDetail", header);
	  }
	 
	  public List<GroupHeader> selectQty(GroupHeader data) {
		    return this.sqlSession.selectList("selectQty", data);
	  }
	  
	  /*picking*/
	  public String selectMaxDeliPickSeq(Pick pick) {
		    return this.sqlSession.selectOne("selectMaxDeliPickSeq" ,pick);
	  }
	  
	  public List<Pick> selectHasToteSort(Pick pick) {
		    return this.sqlSession.selectList("selectHasToteSort",pick);
	  }
	  
	  public List<Pick> selectExtractToteSort(Pick pick) {
		    return this.sqlSession.selectList("selectExtractToteSort",pick);
	  }
	  
	  public int updateCompleteTrHeader(Pick pick) {
		    return this.sqlSession.update("updateCompleteTrHeader",pick);
	  }
	  
	  public int updateCompleteTrDetail(Pick pick) {
		    return this.sqlSession.update("updateCompleteTrDetail",pick);
	  }
	  
	  public int updateCurrentCartCode(Pick pick) {
		    return this.sqlSession.update("updateCurrentCartCode",pick);
	  }
	  
	  public int insertPick(Pick pick) {
		  System.err.println("pick.getToteType "+pick.getToteType());
		    return this.sqlSession.insert("insertPick",pick);
	  }
	  
	  public int updateCancelPick(Pick pick) {
//		  if(pick.getStatus().equals("40"))pick.setStatus("99");
		  if(pick.getPicknode().equals("outofstock") || pick.getPicknode().equals("alter")||pick.getPicknode().equals("replace")||pick.getSubPickNode().equals("recovery"))pick.setStatus("99");
		  return this.sqlSession.update("updateCancelPick",pick);
	  }
	 
	  public int updateRemoveTrDetailProduct(Pick pick) {
		  if(pick.getPicknode().equals("outofstock") || pick.getPicknode().equals("replace")||pick.getSubPickNode().equals("recovery"))pick.setStatus("99");
		  if(pick.getPicknode().equals("alter"))pick.setStatus("01");
   	      return this.sqlSession.update("updateRemoveTrDetailProduct",pick);
	  }
	  
	  public int updateRecoveryOnOrder(Pick pick) {
		  return this.sqlSession.update("updateRecoveryOnOrder",pick);
		    
	  }
	  
	  public int updateOrderInfo(Pick pick) {
		    return this.sqlSession.update("updateOrderInfo", pick);
	  }
	  
	  public String selectOrderStatus(String groupkey) {
		    return this.sqlSession.selectOne("selectOrderStatus",groupkey);
	  }
	  
	  public String selectProductByBarcode(String productId) {
		    return this.sqlSession.selectOne("selectProductByBarcode" , productId);
	  }
	  
	  public int updateCartCodeByDeliNo(Pick pick) {
		  return this.sqlSession.update("updateCartCodeByDeliNo",pick);
	  }
	  
	  //전체피킹
	  public List<Pick> selectOnPickProduct(Pick pick) {
		    return this.sqlSession.selectList("selectOnPickProduct",pick);
	  }
	  //전체피킹취소
	  public List<Pick> selectOnPickTotByProduct(Pick pick) {
		    return this.sqlSession.selectList("selectOnPickTotByProduct",pick);
	  }

	public int rollbackassigntotedetail(GroupHeader header) {
		return this.sqlSession.insert("rollbackassigntotedetail",header);
	}
	public int rollbackassigntoteheader(GroupHeader header) {return this.sqlSession.insert("rollbackassigntoteheader",header);}

	public int rollbackpick(GroupHeader header) {
		return this.sqlSession.update("rollbacktrpick",header);
	}
	public int rollbacktrdetail(GroupHeader header) {
		return this.sqlSession.insert("rollbacktrdetail",header);
	}
}
