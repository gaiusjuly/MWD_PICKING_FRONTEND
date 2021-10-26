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
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.mwd.picking.model.Order;
import com.mwd.picking.model.OrderProduct;
import com.mwd.picking.model.Pick;
import com.mwd.picking.model.Product;
import com.mwd.picking.model.OrderOwner;
import com.mwd.picking.model.Tot;
import com.mwd.picking.model.Good;
import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Memo;
import org.springframework.stereotype.Repository;

/**
 * @author Sayyoung
 */
//@Component
@Repository
public class PickRepository {
	private final SqlSession sqlSession;

	  public PickRepository(SqlSession sqlSession) {
	    this.sqlSession = sqlSession;
	  }

	  public List<Order> selectPicks(Order pick) {
	    return this.sqlSession.selectList("selectPicks", pick);
	  }
	  
	  public List<OrderProduct> selectOrderProduct(String orderCode) {
		    return this.sqlSession.selectList("selectOrderDetailProduct", orderCode);
	  }
	  
	  public OrderOwner selectOrderOwner(String orderCode) {
		    return this.sqlSession.selectOne("selectOrderDetailOwner", orderCode);
	  }
	  
	  public int insertMemo(HashMap data) {
		    return this.sqlSession.insert("insertMemo", data);
	  }
	  
	  public List<Memo> selectMemo(String deliNo) {
	       return this.sqlSession.selectList("selectMemo", deliNo);
	  }
	  
	  public List<Tot> selectTots(String groupkey, String pickArea, String storeCode) {
		  Map<String, String> param = new HashMap<String, String>();
		  param.put("groupkey",groupkey);
		  param.put("pickArea",pickArea);
		  param.put("storeCode",storeCode);
	       return this.sqlSession.selectList("selectTotList", param);
	  }
	  
	  public String selectHasAssignTot(String groupkey, String cartcode) {
		   Map<String, String> header = new HashMap<String, String>();
		   header.put("groupkey",groupkey);
		   header.put("cartcode",cartcode);
		   return this.sqlSession.selectOne("selectHasAssignTot",header );
	  }
	  
	  public List<HashMap> selecAssignDeliveryinfo(String groupkey) {
		    return this.sqlSession.selectList("selecAssignDeliveryinfo", groupkey);
	  }
	  
	  public int updateMasterTrDetail(HashMap header) {
		    return this.sqlSession.update("updateMasterTrDetail", header);
	  }

	public int deleteMasterTrDetail(HashMap header) {
		return this.sqlSession.update("deleteMasterTrDetail", header);
	}
	  
	  public int updateMoveStatusProductScan(GroupHeader header) {
		    return this.sqlSession.update("updateMoveStatusProductScan", header);
	  }
	  
	  public List<Good> selectGoods(String groupkey, String storeCode) {
		  Map<String, String> param = new HashMap<String, String>();
		  param.put("groupkey",groupkey);
		  param.put("storeCode", storeCode);
		    return this.sqlSession.selectList("selectGoods", param);
	  }
	  
	  public List<Good> selectGoodDetailList(String groupkey, String productid, String sortno, String storeCode) {
			  Map<String, String> param = new HashMap<String, String>();
			  param.put("groupkey",groupkey);
			  param.put("productid", productid);
			  param.put("sortno", sortno);
			  param.put("storeCode", storeCode);
		    return this.sqlSession.selectList("selectGoodDetailList", param);
	  }
	  
	  public List<Tot> selectGoodTotList(String groupkey, String productid, String delino, String deliseq, String storeCode) {
		  Map<String, String> param = new HashMap<String, String>();
		  param.put("groupkey",groupkey);
		  param.put("productid",productid);
		  param.put("delino",delino);
		  param.put("deliseq",deliseq);
		  param.put("storeCode",storeCode);
		  return this.sqlSession.selectList("selectGoodTotList", param);
	  }
	  
	  public String selectCheckupGood(String productId) {
		   return this.sqlSession.selectOne("selectCheckupGood",productId );
	  }

	public List<Tot> selectassigntotelist(Tot tot) {
		return this.sqlSession.selectList("selectassigntotelist", tot);
	}
	
	public String selectBcrCheck(Pick data) {
		   return this.sqlSession.selectOne("selectBcrCheck", data );
	}
	
	//대체대상상품
	public List<Product> selectProductInfoTarget(Product data) {
		   return this.sqlSession.selectList("selectProductInfoTarget", data );
	}
	
	//대체상품
	public List<Product> selectProductInfo(Product data) {
		   return this.sqlSession.selectList("selectProductInfo", data );
	}
	
}
