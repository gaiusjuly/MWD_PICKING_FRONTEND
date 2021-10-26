package com.mwd.picking.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mwd.picking.comm.Util;
import com.mwd.picking.model.Manual;
import com.mwd.picking.persitence.mybatis.BulkRepository;

@Service
public class BulkServiceImpl {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BulkServiceImpl.class);
	
	@Autowired
	private BulkRepository repository;
	
	/**
	* 이형주문목록 조회
	* 
	* **/
	public List<Manual> selectBulkDeliList(Manual data) throws Exception { 
		return repository.selectBulkDeliList(data);
    }
	
	/**
	* 이형검수완료
	* 
	* SA_TR_DETAIL.STATUS = 01(출고예정) -> 40(검수완료)
	* 
	* **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int putBulkPick(HashMap<String, Object> map) throws Exception {
	  
		for(String key : map.keySet()) {
			
			HashMap imap = (HashMap) map.get(key);
			// 배송피킹유형 : 이형상품("1")
			imap.put("deliPickType", "1");
			// 상태 : 출고예정("01")
			imap.put("status", "01");
			List<HashMap> pickList = repository.selectBulkPickList(imap);
			for(int i=0; i<pickList.size(); i++) {
	  
				// 1) IF_TR_DETAIL : 01(출고예정) -> 40(검수완료)
				pickList.get(i).put("FRSTATUS", "01");
				pickList.get(i).put("TOSTATUS", "40");
				pickList.get(i).put("ORDER_CNCL_TYPE", "0");
				pickList.get(i).put("ALLC_QTY", pickList.get(i).get("PICK_QTY"));
				pickList.get(i).put("PICK_QTY", pickList.get(i).get("PICK_QTY"));
				pickList.get(i).put("INSP_QTY", pickList.get(i).get("PICK_QTY"));
				repository.updateBulkDetailStatus(pickList.get(i));
	  
				// 2) IF_TR_PICK : 피킹정보생성
				pickList.get(i).put("CART_CODE", "*");
				pickList.get(i).put("STATUS",    "40");
				pickList.get(i).put("TOTE_TYPE", "*");
				pickList.get(i).put("TOTE_SORT", "999");
				pickList.get(i).put("TOTE_AREA", "0");
				repository.insertBulkPick(pickList.get(i));
	  
				// 3) IF_TR_HEADER : 상태업데이트
				repository.updateBulkHeaderStatus(pickList.get(i));
				
			}
		}
	  
		return 1;
	}
	
	/**
	* 이형검수취소
	* 
	* SA_TR_DETAIL.STATUS = 40(검수완료) -> 01(출고예정)
	* 
	* **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int putBulkPickCncl(HashMap<String, Object> map) throws Exception {
	  
		for(String key : map.keySet()) {
			
			HashMap imap = (HashMap) map.get(key);
			// 배송피킹유형 : 이형상품("1")
			imap.put("deliPickType", "1");
			// 상태 : 검수완료("40")
			imap.put("status", "40");
			List<HashMap> pickList = repository.selectBulkPickList(imap);
			for(int i=0; i<pickList.size(); i++) {
				
				// 1) IF_TR_DETAIL : 40(검수완료) ->  01(출고예정)
				pickList.get(i).put("FRSTATUS",        "40");
				pickList.get(i).put("TOSTATUS",        "01");
				pickList.get(i).put("ORDER_CNCL_TYPE", "0");
				pickList.get(i).put("ALLC_QTY",        "0");
				pickList.get(i).put("PICK_QTY",        "0");
				pickList.get(i).put("INSP_QTY",        "0");
				
				// 2) 대체상품일경우 원상품정보 복구
				if(Util.empty(pickList.get(i).get("ORG_PRODUCT_ID")) == false) {
					
					// 2-1) 원상품정보조회
					List<HashMap> orgReplaceList = repository.selectBulkOrgReplaceList(pickList.get(i));
					
					// 2-2) 대체상품정보가 잘못되면 프로세스 종료
					if(orgReplaceList.size()>1 || orgReplaceList.size()<1) {
						continue;
					}
					
					// 2-3) 원상품정보 IF_TR_DETAIL.STATUS : 01 상태로 복구
					orgReplaceList.get(0).put("FRSTATUS",        "99");
					orgReplaceList.get(0).put("TOSTATUS",        "01");
					orgReplaceList.get(0).put("ORDER_CNCL_TYPE", "0");
					orgReplaceList.get(0).put("ALLC_QTY",        "0");
					orgReplaceList.get(0).put("PICK_QTY",        "0");
					orgReplaceList.get(0).put("INSP_QTY",        "0");
					orgReplaceList.get(0).put("PICKERID",        pickList.get(0).get("PICKERID"));
					repository.updateBulkDetailStatus(orgReplaceList.get(0));
					
					// 2-4) 대체상품정보 취소처리
					pickList.get(i).put("TOSTATUS",        "99");
				
				} 
				
				// 1-1) IF_TR_DETAIL : 40(검수완료) ->  01(출고예정)
				repository.updateBulkDetailStatus(pickList.get(i));
				
				// 3) IF_TR_PICK : 99(취소)
				pickList.get(i).put("STATUS", "99");
				repository.updateBulkPickStatus(pickList.get(i));
		
				// 4) IF_TR_HEADER : 상태업데이트
				repository.updateBulkHeaderStatus(pickList.get(i));
			}
		}
	  
		return 1;
	}
	
	/**
	* 이형상품품절처리
	* 
	* SA_TR_DETAIL.STATUS = 40(검수완료) -> 01(출고예정)
	* 
	* **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int putBulkSoldout(HashMap<String, Object> map) throws Exception {
	  
		for(String key : map.keySet()) {
			
			HashMap imap = (HashMap) map.get(key);
			// 배송피킹유형 : 이형상품("1")
			imap.put("deliPickType", "1");
			// 상태 : 전체
			imap.put("status",       "");
			List<HashMap> pickList = repository.selectBulkPickList(imap);
			for(int i=0; i<pickList.size(); i++) {
	  
				// 1) IF_TR_DETAIL : STATUS -> 99(취소), ORDER_CNCL_TYPE -> 1 : 품절로 인한 삭제
				pickList.get(i).put("TOSTATUS",        "99");
				pickList.get(i).put("ORDER_CNCL_TYPE", "1");
				pickList.get(i).put("ALLC_QTY",        "0");
				pickList.get(i).put("PICK_QTY",        "0");
				pickList.get(i).put("INSP_QTY",        "0");
				repository.updateBulkDetailStatus(pickList.get(i));
				
				// 2) IF_TR_PICK : 99(취소)
				pickList.get(i).put("STATUS", "99");
				repository.updateBulkPickStatus(pickList.get(i));
	  
				// 3) IF_TR_HEADER : 상태업데이트
				repository.updateBulkHeaderStatus(pickList.get(i));
				
			}
		}
	  
		return 1;
	}

}
