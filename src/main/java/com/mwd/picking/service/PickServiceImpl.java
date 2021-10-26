package com.mwd.picking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mwd.picking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mwd.picking.persitence.mybatis.PickRepository;
import com.mwd.picking.persitence.mybatis.PickGenRepository;

@Service
public class PickServiceImpl {
	
	@Autowired
	 private PickRepository repository;
	
	@Autowired
	 private PickGenRepository genrepository;
	
	public List<Order> selectPicks(Order pick) throws Exception
    { 
		return repository.selectPicks(pick);
//        UserDetails user = new org.springframework.security.core.userdetails.User(username, currentUser.getPassword()
//        , true, true, true, true, AuthorityUtils.createAuthorityList(currentUser.getRole()));
//        return user;
    }
	
	public List<GroupHeader> selectGroupHeader(GroupHeader header ) throws Exception
    { 
		return genrepository.selectGroupHeader(header);
    }
	
	public List<OrderProduct> selectOrderDetailProduct(String orderCode) throws Exception
    { 
		return repository.selectOrderProduct(orderCode);
    }
	
	public OrderOwner selectOrderDetailOwner(String orderCode) throws Exception
    { 
		return repository.selectOrderOwner(orderCode);
    }
	
	public int insertMemo(HashMap data) throws Exception
    { 
		return repository.insertMemo(data);
    }
	
	public List<Memo> selectMemo(String deliNo) throws Exception
    { 
		return repository.selectMemo(deliNo);
    }
	
	public List<Tot> selectTots(String groupkey, String pickArea, String storeCode) throws Exception
    { 
		return repository.selectTots(groupkey, pickArea, storeCode);
    }
	
	public String selectHasAssignTot(String groupkey, String cartCode) throws Exception
    { 
		String rtn = repository.selectHasAssignTot(groupkey, cartCode);
		System.err.println("rtn "+ rtn);
		return rtn;
    }
	
	public List<HashMap> selecAssignDeliveryinfo(String groupkey) throws Exception
    { 
		return repository.selecAssignDeliveryinfo(groupkey);
    }
	
	public int updateMasterTrDetail(HashMap header) throws Exception
    { 
		return repository.updateMasterTrDetail(header);
    }

	public int deleteMasterTrDetail(HashMap header) throws Exception
	{
		return repository.deleteMasterTrDetail(header);
	}

	public int updateMoveStatusProductScan(GroupHeader header) throws Exception
    { 
		return repository.updateMoveStatusProductScan(header);
    }
	
	public List<Good> selectGoods(String groupkey, String storeCode) throws Exception
    { 
		return repository.selectGoods(groupkey,storeCode);
    }
	
	public List<Good> selectGoodDetailList(String groupkey, String productid, String sortno, String storeCode) throws Exception
    { 
		return repository.selectGoodDetailList(groupkey, productid, sortno, storeCode);
    }
	
	public List<Tot> selectGoodTotList(String groupkey, String productid, String delino, String deliseq, String storeCode) throws Exception
    {
		return repository.selectGoodTotList(groupkey, productid, delino, deliseq, storeCode);
    }

	public String selectCheckupGood(String productId) throws Exception
    { 
		String rtn = repository.selectCheckupGood(productId);
		return rtn;
    }

	public List<Tot> getassigntotelist(Tot tot) throws Exception
	{
		return repository.selectassigntotelist(tot);
	}
	
	public String selectBcrCheck(Pick data) throws Exception
    { 
		String rtn = repository.selectBcrCheck(data);
		return rtn;
    }

	public List<Product> getProductInfo(Product data) throws Exception
    { 
		return repository.selectProductInfo(data);
    }
	
	public List<Product> getProductInfoTarget(Product data) throws Exception
    { 
		return repository.selectProductInfoTarget(data);
    }
	
}
