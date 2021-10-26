/**
 *    Copyright 2015-2019 the original author or authors.
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
package com.mwd.picking.model;

import java.io.Serializable;

/**
 * @author sayyoung
 */
public class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  private String storeCode;
  public String getStoreCode() {
	return storeCode;
}
public void setStoreCode(String storeCode) {
	this.storeCode = storeCode;
}

private int cnt;
  public int getCnt() {
	return cnt;
}
public void setCnt(int cnt) {
	this.cnt = cnt;
}

private int pageGroup;
  private int limit; 
  public int getLimit() {
	return limit;
}
public void setLimit(int limit) {
	this.limit = limit;
}
public int getPageGroup() {
	return pageGroup;
  }
  public void setPageGroup(int pageGroup) {
	this.pageGroup = pageGroup;
  }

private String deliNo;
  public String getDeliNo() {
	return deliNo;
}
public void setDeliNo(String deliNo) {
	this.deliNo = deliNo;
}
public String getDeliDiv() {
	return deliDiv;
}
public void setDeliDiv(String deliDiv) {
	this.deliDiv = deliDiv;
}
public String getDeliDivNm() {
	return deliDivNm;
}
public void setDeliDivNm(String deliDivNm) {
	this.deliDivNm = deliDivNm;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getStatusNm() {
	return statusNm;
}
public void setStatusNm(String statusNm) {
	this.statusNm = statusNm;
}
public String getDeliOrder() {
	return deliOrder;
}
public void setDeliOrder(String deliOrder) {
	this.deliOrder = deliOrder;
}
public String getDeliDate() {
	return deliDate;
}
public void setDeliDate(String deliDate) {
	this.deliDate = deliDate;
}
public String getOrderCode() {
	return orderCode;
}
public void setOrderCode(String orderCode) {
	this.orderCode = orderCode;
}
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public String getOrderName() {
	return orderName;
}
public void setOrderName(String orderName) {
	this.orderName = orderName;
}
public String getTitleProduct() {
	return titleProduct;
}
public void setTitleProduct(String titleProduct) {
	this.titleProduct = titleProduct;
}
public String getProductCnt() {
	return productCnt;
}
public void setProductCnt(String productCnt) {
	this.productCnt = productCnt;
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
public String getPickerId() {
	return pickerId;
}
@Override
public String toString() {
	return "Pick [deliNo=" + deliNo + ", deliDiv=" + deliDiv + ", deliDivNm=" + deliDivNm + ", status=" + status
			+ ", statusNm=" + statusNm + ", deliOrder=" + deliOrder + ", deliDate=" + deliDate + ", orderCode="
			+ orderCode + ", orderId=" + orderId + ", orderName=" + orderName + ", titleProduct=" + titleProduct
			+ ", productCnt=" + productCnt + ", qty=" + qty + ", pickQty=" + pickQty + ", pickerId=" + pickerId
			+ ", pickerName=" + pickerName + ", deliMethod=" + deliMethod + ", erpOrderCnt=" + erpOrderCnt
			+ ", erpReserveOrderCnt=" + erpReserveOrderCnt + ", jmProductCnt=" + jmProductCnt + ", orderRegularYn="
			+ orderRegularYn + ", pickArea=" + pickArea + "]";
}
public void setPickerId(String pickerId) {
	this.pickerId = pickerId;
}
public String getPickerName() {
	return pickerName;
}
public void setPickerName(String pickerName) {
	this.pickerName = pickerName;
}
public String getDeliMethod() {
	return deliMethod;
}
public void setDeliMethod(String deliMethod) {
	this.deliMethod = deliMethod;
}
public String getErpOrderCnt() {
	return erpOrderCnt;
}
public void setErpOrderCnt(String erpOrderCnt) {
	this.erpOrderCnt = erpOrderCnt;
}
public String getErpReserveOrderCnt() {
	return erpReserveOrderCnt;
}
public void setErpReserveOrderCnt(String erpReserveOrderCnt) {
	this.erpReserveOrderCnt = erpReserveOrderCnt;
}
public String getJmProductCnt() {
	return jmProductCnt;
}
public void setJmProductCnt(String jmProductCnt) {
	this.jmProductCnt = jmProductCnt;
}
public String getOrderRegularYn() {
	return orderRegularYn;
}
public void setOrderRegularYn(String orderRegularYn) {
	this.orderRegularYn = orderRegularYn;
}
private String deliDiv;
  private String deliDivNm;
  private String status;
  private String statusNm;
  private String deliOrder;
  private String deliDate;
  private String orderCode;
  private String orderId;
  private String orderName;
  private String titleProduct;
  private String productCnt;
  private String qty;
  private String pickQty;
  private String pickerId;
  private String pickerName;
  private String deliMethod;
  private String erpOrderCnt;
  private String erpReserveOrderCnt;
  private String jmProductCnt;
  private String orderRegularYn;
  private String pickArea;
  
public String getPickArea() {
	return pickArea;
}
public void setPickArea(String pickArea) {
	this.pickArea = pickArea;
}
}
