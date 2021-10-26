package com.mwd.picking.model;

public class Pick {
  public String getDeliNo() {
		return deliNo;
	}
	public void setDeliNo(String deliNo) {
		this.deliNo = deliNo;
	}
	public String getDeliDetailSeq() {
		return deliDetailSeq;
	}
	public void setDeliDetailSeq(String deliDetailSeq) {
		this.deliDetailSeq = deliDetailSeq;
	}
	public String getMasterCartCode() {
		return masterCartCode;
	}
	public void setMasterCartCode(String masterCartCode) {
		this.masterCartCode = masterCartCode;
	}
	public String getPickQty() {
		return pickQty;
	}
	public void setPickQty(String pickQty) {
		this.pickQty = pickQty;
	}
	public String getPickArea() {
		return pickArea;
	}
	public void setPickArea(String pickArea) {
		this.pickArea = pickArea;
	}
	public String getToteType() {
		return toteType;
	}
	public void setToteType(String toteType) {
		this.toteType = toteType;
	}
	public String getToteArea() {
		return toteArea;
	}
	public void setToteArea(String toteArea) {
		this.toteArea = toteArea;
	}
	public String getToteSort() {
		return toteSort;
	}
	public void setToteSort(String toteSort) {
		this.toteSort = toteSort;
	}
	public String getPickSort() {
		return pickSort;
	}
	public void setPickSort(String pickSort) {
		this.pickSort = pickSort;
	}
	public String getPickerId() {
		return pickerId;
	}
	public void setPickerId(String pickerId) {
		this.pickerId = pickerId;
	}
private String deliNo;
  private String deliDetailSeq;
  private String deliPickSeq;
  public String getDeliPickSeq() {
	return deliPickSeq;
}
public void setDeliPickSeq(String deliPickSeq) {
	this.deliPickSeq = deliPickSeq;
}
private String masterCartCode;
private String currentCartCode;
  public String getCurrentCartCode() {
	return currentCartCode;
}
public void setCurrentCartCode(String currentCartCode) {
	this.currentCartCode = currentCartCode;
}
private String pickQty;
  private String pickArea;
  private String toteType;
  private String toteArea;
  private String toteSort;
  private String pickSort;
  private String pickerId;
  private String status;
  private String productId;
  private String groupkey;
  private String allcQty;
  private String orderCnclType;
  private String picknode;
  private String orderCode;
  private String replaceReasonCode;
  private String salePrice;
  private String inspQty;
  private String orgProductId;
  private String orgDeliNo;
  private String subPickNode;
  private String priceEditAbleYn;
  private String orderPrice;
 
	public String getOrderPrice() {
	return orderPrice;
}
public void setOrderPrice(String orderPrice) {
	this.orderPrice = orderPrice;
}
	public String getPriceEditAbleYn() {
	return priceEditAbleYn;
}
public void setPriceEditAbleYn(String priceEditAbleYn) {
	this.priceEditAbleYn = priceEditAbleYn;
}
	public String getSubPickNode() {
	return subPickNode;
}
public void setSubPickNode(String subPickNode) {
	this.subPickNode = subPickNode;
}
	public String getOrgDeliNo() {
	return orgDeliNo;
}
public void setOrgDeliNo(String orgDeliNo) {
	this.orgDeliNo = orgDeliNo;
}
	public String getOrgProductId() {
	return orgProductId;
}
public void setOrgProductId(String orgProductId) {
	this.orgProductId = orgProductId;
}
	public String getInspQty() {
	return inspQty;
}
public void setInspQty(String inspQty) {
	this.inspQty = inspQty;
}
	public String getSalePrice() {
	return salePrice;
}
public void setSalePrice(String salePrice) {
	this.salePrice = salePrice;
}
	public String getReplaceReasonCode() {
	return replaceReasonCode;
}
public void setReplaceReasonCode(String replaceReasonCode) {
	this.replaceReasonCode = replaceReasonCode;
}
	public String getOrderCode() {
	return orderCode;
}
public void setOrderCode(String orderCode) {
	this.orderCode = orderCode;
}
	public String getPicknode() {
	return picknode;
}
public void setPicknode(String picknode) {
	this.picknode = picknode;
}
	public String getOrderCnclType() {
	return orderCnclType;
}
public void setOrderCnclType(String orderCnclType) {
	this.orderCnclType = orderCnclType;
}
	public String getTotalPickQty() {
		return totalPickQty;
	}

	public void setTotalPickQty(String totalPickQty) {
		this.totalPickQty = totalPickQty;
	}

private String totalPickQty;

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	private String qty;
public String getAllcQty() {
	return allcQty;
}
public void setAllcQty(String allcQty) {
	this.allcQty = allcQty;
}
public String getGroupkey() {
	return groupkey;
}
public void setGroupkey(String groupkey) {
	this.groupkey = groupkey;
}
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	private String storeCode;
  
}
