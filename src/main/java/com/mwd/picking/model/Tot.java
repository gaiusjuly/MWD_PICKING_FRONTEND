package com.mwd.picking.model;

public class Tot {
  private String deliNo;

	public String getDeliNos() {
		return deliNos;
	}

	public void setDeliNos(String deliNos) {
		this.deliNos = deliNos;
	}

	private String deliNos;
  public String getDeliNo() {
	return deliNo;
}
public void setDeliNo(String deliNo) {
	this.deliNo = deliNo;
}
public String getOrderName() {
	return orderName;
}
public void setOrderName(String orderName) {
	this.orderName = orderName;
}
public String getOrderCode() {
	return orderCode;
}
public void setOrderCode(String orderCode) {
	this.orderCode = orderCode;
}
public String getQty() {
	return qty;
}
public void setQty(String qty) {
	this.qty = qty;
}
public String getPickQty() {
	return pickQty;
}
public void setPickQty(String pickQty) {
	this.pickQty = pickQty;
}
public String getToteArea() {
	return toteArea;
}
public void setToteArea(String toteArea) {
	this.toteArea = toteArea;
}
public String getCartCode() {
	return cartCode;
}
public void setCartCode(String cartCode) {
	this.cartCode = cartCode;
}
public String getCartCnt() {
	return cartCnt;
}
public void setCartCnt(String cartCnt) {
	this.cartCnt = cartCnt;
}

  private String deliDetailSeq;
  public String getDeliDetailSeq() {
	return deliDetailSeq;
}
public void setDeliDetailSeq(String deliDetailSeq) {
	this.deliDetailSeq = deliDetailSeq;
}

private String orderName;
  private String orderCode;
  private String qty;
  private String pickQty;
  private String toteArea;
  private String cartCode;
  private String cartCnt;
  private String status;
  private String allcQty;
  private String pickArea;
  private String currentCartCode;
  public String getCurrentCartCode() {
	return currentCartCode;
}

public void setCurrentCartCode(String currentCartCode) {
	this.currentCartCode = currentCartCode;
}

public String getPickArea() {
	return pickArea;
}
public void setPickArea(String pickArea) {
	this.pickArea = pickArea;
}
public String getAllcQty() {
	return allcQty;
}
public void setAllcQty(String allcQty) {
	this.allcQty = allcQty;
}
public String getMasterCartCode() {
	return masterCartCode;
}
public void setMasterCartCode(String masterCartCode) {
	this.masterCartCode = masterCartCode;
}
public String getMasterToteArea() {
	return masterToteArea;
}
public void setMasterToteArea(String masterToteArea) {
	this.masterToteArea = masterToteArea;
}
private String masterCartCode;
  private String masterToteArea;
    
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
}
