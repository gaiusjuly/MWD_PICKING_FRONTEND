package com.mwd.picking.service;

import java.util.List;

import com.mwd.picking.model.TrDetail;
import com.mwd.picking.model.TrHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mwd.picking.persitence.mybatis.ManualRepository;
import com.mwd.picking.persitence.mybatis.PickGenRepository;
import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Pick;
import com.mwd.picking.web.PickGenController;

@Service
@Transactional
public class PickCompleteServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(PickGenController.class);
	
	@Autowired
	private PickGenRepository repository;
	
	@Autowired
	private ManualRepository manualrepository;
	
	public int allPick(Pick[] picks) throws Exception{
		System.err.println("picks length "+ picks.length);
		int rtn = 1;
		for(int i=0;i<picks.length;i++) {
		  this.completePick(picks[i]);
		}
		return rtn;
	}
	
	public int allPickCancel(Pick[] picks) throws Exception{
		int rtn = 1;
		for(int i=0;i<picks.length;i++) {
		 this.cancellationPick(picks[i]);
		}
		return rtn;
	}
	
	private int doPick(String status, Pick pick) throws Exception{
		System.err.println("doPick called");
		int rtn=0;
		List<Pick> product = this.selectOnPickProduct(pick);
		System.err.println("product size "+ product.size());
		for(int i=0;i<product.size();i++) {
			product.get(i).setGroupkey(pick.getGroupkey());
			product.get(i).setPickArea(pick.getPickArea());
			product.get(i).setStoreCode(pick.getStoreCode());
//			System.err.println(product.get(i).getPickArea());
			List<Pick> tot = this.selectOnPickTotByProduct(product.get(i));
			System.err.println("picking deli no size "+tot.size());
			  for(int j=0; j<tot.size(); j++) {
				  System.err.println("allc_qty "+tot.get(j).getAllcQty());
				  if(!tot.get(j).getAllcQty().equals("0")) {
					  Pick entity = tot.get(j);
					  entity.setPickerId(pick.getPickerId());
					  entity.setGroupkey(pick.getGroupkey());
//					  if(entity.getToteType()==null)entity.setToteType("M");
//					  if(entity.getTotalPickQty()==null)entity.setTotalPickQty(pick.getTotalPickQty());
//					  if(entity.getOrderCnclType()==null)entity.setOrderCnclType("0");
//					  if(entity.getPicknode()==null)entity.setPicknode("");
					  entity.setToteType(pick.getToteType());
					  entity.setTotalPickQty(pick.getTotalPickQty());
					  entity.setOrderCnclType(pick.getOrderCnclType());
					  entity.setPicknode(pick.getPicknode());
					  entity.setStatus(status);
//					  entity.setToteType("M");
					  System.err.println("PICK DELI_NO "+ entity.getDeliNo() );
					  System.err.println("DELI_DETAIL_SEQ "+ entity.getDeliDetailSeq() );
					  System.err.println("PICKER_ID "+ entity.getPickerId() );
					  System.err.println("STATUS "+ entity.getStatus() );
					  if(status.equals("10"))entity.setPickQty("0");
					  else entity.setPickQty(entity.getAllcQty());
					  entity.setToteArea( (new Integer(j+1).toString()) );
					  System.err.println("PICK_QTY "+ entity.getPickQty() );
					 rtn =  this.completePick(entity);
				  }
			  }
		}
		return rtn;
	}
	

public int putManualPick(Pick[] pick) throws Exception {
	for(int i=0;i<pick.length;i++) {
		completePick(pick[i]);
	}
	return 1;
}

public int cancellationPick(Pick pick) throws Exception {
	System.err.println(">>pick.getPicknode<< "+ pick.getPicknode());
	System.err.println(">>pick.getSubPicknode<< "+ pick.getSubPickNode());
    pick.setStatus("99");
    pick.setOrderCnclType("0");
    this.updateCancelPick(pick);
	pick.setStatus("10"); 
	this.updateRemoveTrDetailProduct(pick);
	if(pick.getSubPickNode().equals("recovery")) {
	  System.err.println(">>entered pick.getSubPicknode<< "+ pick.getSubPickNode());
	  this.recoveryOnOrder(pick);
	}
	return 1;
}

public int completePick(Pick pick) throws Exception {
	String deliNo = pick.getDeliNo();
	int rtn=1;
    if(pick.getStatus().equals("15") || pick.getStatus().equals("12")) {
        pick.setDeliPickSeq("1");
   	    this.insertPick(pick);
   	    this.updateCompleteTrDetail(pick);
   	    this.updateCurrentCartCode(pick);
 	    this.updateCompleteTrHeader(pick);
 	    String orderStatus = this.selectOrderStatus(pick.getGroupkey());
 	    if(orderStatus.equals("0")) {
 		  System.err.println("enter upateOrderInfo "+ pick.getDeliNo());
 	      this.updateOrderInfo(pick);
 	    }
 	}
	//본격이형
	if(pick.getPicknode().equals("alterindex")) {
		this.updateCompleteTrDetail(pick);
 	    this.updateCompleteTrHeader(pick);
	}
	
//	if(pick.getStatus().equals("99") || pick.getStatus().equals("40")) {
	if(pick.getPicknode().equals("outofstock") || pick.getPicknode().equals("cancellation") || pick.getPicknode().equals("alter") || pick.getPicknode().equals("replace")) {
//	  pick.setAllcQty("0");
//	  pick.setPickQty("0");
	  this.updateCancelPick(pick);
	  this.updateRemoveTrDetailProduct(pick);
	  if(pick.getPicknode().equals("outofstock")) {
		  this.updateCompleteTrHeader(pick);
	  }
//	  pick.setStatus("40");
	}

	
//	if(pick.getStatus().equals("40") || pick.getPicknode().equals("replace")) {
	if(pick.getPicknode().equals("alter") || pick.getPicknode().equals("replace")) {
      this.insertTrHeaderByNewDeliNo(pick);
	}
	if(pick.getPicknode().equals("alterandinspection") && pick.getStatus().equals("01")) {
		 manualrepository.updateInspectionD(pick);
		 manualrepository.updateInspectionH(pick);
	 }
	
	if(!pick.getPicknode().equals("cancellation") && !pick.getPicknode().equals("alter")) {
		pick.setDeliNo(deliNo);
		repository.updateCartCodeByDeliNo(pick);
	}
	
	return rtn;	
  }

  private String selectNewDeliNo(String data) {
	return manualrepository.selectNewDeliNo(data);
  }
  
//  private String selectMaxDeliPickSeq(Pick pick) {
//		return repository.selectMaxDeliPickSeq(pick);
//  }
  
//  private List<Pick> selectHasToteSort(Pick pick) {
//	  return repository.selectHasToteSort(pick);
//  }
  
//  private List<Pick> selectExtractToteSort(Pick pick) {
//	  return repository.selectExtractToteSort(pick);
//  }
  
  private int recoveryOnOrder(Pick pick) {
	  return repository.updateRecoveryOnOrder(pick);
  }
  
  private int updateCompleteTrHeader(Pick pick) {
	  return repository.updateCompleteTrHeader(pick);
  }
  
  private int updateCompleteTrDetail(Pick pick) {
	  return repository.updateCompleteTrDetail(pick);
  }
  private int updateCurrentCartCode(Pick pick) {
	  return repository.updateCurrentCartCode(pick);
  }
  private int insertPick(Pick pick) {
	  return repository.insertPick(pick);
  }
  
  private int updateTrHeaderPost(String deliNo)
  {
		return repository.updateTrHeaderPost(deliNo);
  }
  
  private int updateCancelPick(Pick pick) {
	  return repository.updateCancelPick(pick);
  }
  
  private int updateRemoveTrDetailProduct(Pick pick) {
	  return repository.updateRemoveTrDetailProduct(pick);
  }
  
  private List<Pick> selectOnPickProduct(Pick pick) {
	  return repository.selectOnPickProduct(pick);
  }
  
  private List<Pick> selectOnPickTotByProduct(Pick pick) {
	  return repository.selectOnPickTotByProduct(pick);
  }
  
  private String selectOrderStatus(String groupkey) {
		return repository.selectOrderStatus(groupkey);
  }
  
  private int updateOrderInfo(Pick pick) {
	  return repository.updateOrderInfo(pick);
  }
  
  public String selectProductByBarcode(String productId) {
		return repository.selectProductByBarcode(productId);
}


  private List<TrDetail> selectTrDetail(Pick trDetail) {
		return manualrepository.selectTrDetail(trDetail);
	}
  private List<TrHeader> selectTrHeader(Pick trHeader) {
		return manualrepository.selectTrHeader(trHeader);
	}

  /*대체 취소검수 이형 tr_detail tr_header 생성 이형은 배송번호 생성*/
  private int insertTrHeaderByNewDeliNo(Pick pick) {
	  String orgDeliNo = pick.getDeliNo();
  	  TrDetail td;
  	  TrHeader th;
  	  td = this.selectTrDetail(pick).get(0);
	  th = this.selectTrHeader(pick).get(0);

	  String deliNo = this.selectNewDeliNo(pick.getDeliNo());
	  pick.setDeliNo((deliNo));

	  td.setDeliNo(pick.getDeliNo());

  	  th.setDeliNo(pick.getDeliNo());
	  td.setDeliDetailSeq("1");
	  td.setPickArea(pick.getPickArea());
		  
	  pick.setDeliDetailSeq("1");
	  pick.setDeliPickSeq("1");
	  if(pick.getPicknode().equals("replace")){
		  String deliDetailSeq = manualrepository.selectMaxDeliDetailSeq(orgDeliNo);
		  System.err.println("Alterlation picking!!!! "+pick.getPickQty());
		  td.setDeliNo(orgDeliNo);
		  td.setDeliDetailSeq(deliDetailSeq);
//		  td.setAllcQty(pick.getAllcQty());
	  	  td.setAllcQty(pick.getPickQty());
	  	  td.setPickQty(pick.getPickQty());
	  	  td.setOrgProductId(pick.getOrgProductId());
	  	  td.setProductId(pick.getProductId());
	  	  td.setOrderCnclType("0");
	  	  String status = (pick.getSubPickNode().equals("alterlation"))?"40":"15";
	  	  td.setOrderPrice(pick.getOrderPrice());
	  	  td.setStatus(status);
	  	  td.setMasterCartCode(pick.getMasterCartCode());
	  	  td.setCurrentCartCode(pick.getCurrentCartCode());
	  	  td.setReplaceReasonCode(pick.getReplaceReasonCode());
	  	  pick.setDeliDetailSeq(deliDetailSeq);
	  	  System.err.println(td.getDeliDetailSeq());
	  	  System.err.println("Alterlation picking set end!!!! "+pick.getReplaceReasonCode());
	  }
//      if(pick.getStatus().equals("40")) {th.setStatus("05");td.setStatus("01");}
  		System.err.println("picknode "+ pick.getPicknode());
  		if(pick.getPicknode().equals("alter")) {
  			System.err.println("Manual picking!!!!");
  			th.setStatus("05");
  			th.setOrgDeliNo(orgDeliNo);
  			td.setStatus("40");
  			td.setOrderCnclType("0");
  			td.setPickArea("");
  			td.setJmProductYn("N");
  			td.setAllcQty("0");
  			td.setPickQty("0");
  			td.setInspQty("0");
  			td.setGroupkey("");
  			td.setMasterCartCode("");
  			td.setMasterToteArea("");
  			td.setPickArea("E");
  			td.setReplaceReasonCode("0");
  			td.setIfCreateBy(pick.getPickerId());
  		}	  
  		
  	  if(pick.getPicknode().equals("alter"))manualrepository.insertTrHeaderByNewDeliNo(th);
  	  
  	  this.updateTrHeaderPost(orgDeliNo);
  	  System.err.println("insert tr detail orderCnclType "+ td.getOrderCnclType());
  	  this.insertTrDetailByNewDeliNo(td);
  	  Pick hpick = new Pick();
  	  hpick.setDeliNo(orgDeliNo);
  	  hpick.setPickerId(pick.getPickerId());
  	  this.updateCompleteTrHeader(hpick);
	  
	  //TR_PICK insert
//	  if(!pick.getStatus().equals("40"))
	  if(!pick.getPicknode().equals("alter")) {
		  pick.setDeliNo(orgDeliNo);
		  this.insertPick(pick);
	  }
//	  if(pick.getStatus().equals("40"))
//	  if(pick.getPicknode().equals("alter")) {
//		  pick.setOrderCnclType("3");
//		  repository.updateCancelPick(pick);
//	  }
	  
 	  if(pick.getPicknode().equals("alterindexandinspection")) {
 		 manualrepository.updateInspectionD(pick);
 		 manualrepository.updateInspectionH(pick);
 	  }
 	  return 1;
  }
  private int insertTrDetailByNewDeliNo(TrDetail td) {
	  return manualrepository.insertTrDetailByNewDeliNo(td);
  }

}
