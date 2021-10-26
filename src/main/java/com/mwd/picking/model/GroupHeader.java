package com.mwd.picking.model;

public class GroupHeader {

	private String delinostr;
	private String masterToteArea;
	public String getMasterToteArea() {
		return masterToteArea;
	}
	public void setMasterToteArea(String masterToteArea) {
		this.masterToteArea = masterToteArea;
	}
	public String getDelinostr() {
		return delinostr;
	}
	public void setDelinostr(String delinostr) {
		this.delinostr = delinostr;
	}
	private String[] delinos;
	private String deliDate;
	public String getDeliDate() {
		return deliDate;
	}
	public void setDeliDate(String deliDate) {
		this.deliDate = deliDate;
	}
	public String[] getDelinos() {
		return delinos;
	}
	public void setDelinos(String[] delinos) {
		this.delinos = delinos;
	}
	private String groupkey;
  	public String getGroupkey() {
		return groupkey;
	}
	public void setGroupkey(String groupkey) {
		this.groupkey = groupkey;
	}
	public String getCenterCode() {
		return centerCode;
	}
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	public String getPickerId() {
		return pickerId;
	}
	public void setPickerId(String pickerId) {
		this.pickerId = pickerId;
	}
	public String getPickArea() {
		return pickArea;
	}
	public void setPickArea(String pickArea) {
		this.pickArea = pickArea;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String deliNo;
	public String getDeliNo() {
		return deliNo;
	}
	public void setDeliNo(String deliNo) {
		this.deliNo = deliNo;
	}
	private String centerCode;
  	private String pickerId;
  	private String pickArea;
  	private String status;
  	private String qty;
  	private String deliDetailSeq;
  	private String storeCode;
  	
  	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getDeliDetailSeq() {
		return deliDetailSeq;
	}
	public void setDeliDetailSeq(String deliDetailSeq) {
		this.deliDetailSeq = deliDetailSeq;
	}
	private String userId;
  	private String[] qtys;
  	
	public String[] getQtys() {
		return qtys;
	}
	public void setQtys(String[] qtys) {
		this.qtys = qtys;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
}
