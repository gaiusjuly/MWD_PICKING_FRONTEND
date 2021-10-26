package com.mwd.picking.model;

public class Product {
  public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getOrgSalePrice() {
		return orgSalePrice;
	}
	public void setOrgSalePrice(String orgSalePrice) {
		this.orgSalePrice = orgSalePrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
private String orderCode;
  private String productId;
  private String proName;
  private String orgSalePrice;
  private String salePrice;
  private String imgUrl;
  private String storeCode;
  private String orderDetailSeq;
  private String cardPrice;
  public String getCardPrice() {
	return cardPrice;
}
public void setCardPrice(String cardPrice) {
	this.cardPrice = cardPrice;
}
public String getMpPrice() {
	return mpPrice;
}
public void setMpPrice(String mpPrice) {
	this.mpPrice = mpPrice;
}
public String getMorePrice() {
	return morePrice;
}
public void setMorePrice(String morePrice) {
	this.morePrice = morePrice;
}
public String getTimePrice() {
	return timePrice;
}
public void setTimePrice(String timePrice) {
	this.timePrice = timePrice;
}
public String getSpotPrice() {
	return spotPrice;
}
public void setSpotPrice(String spotPrice) {
	this.spotPrice = spotPrice;
}
public String getHalinTotMoney() {
	return halinTotMoney;
}
public void setHalinTotMoney(String halinTotMoney) {
	this.halinTotMoney = halinTotMoney;
}
public String getOrderPrice() {
	return orderPrice;
}
public void setOrderPrice(String orderPrice) {
	this.orderPrice = orderPrice;
}
  private String mpPrice;
  private String morePrice;
  private String timePrice;
  private String spotPrice;
  private String halinTotMoney;
  private String orderPrice;
  
public String getOrderDetailSeq() {
	return orderDetailSeq;
}
public void setOrderDetailSeq(String orderDetailSeq) {
	this.orderDetailSeq = orderDetailSeq;
}
public String getStoreCode() {
	return storeCode;
}
public void setStoreCode(String storeCode) {
	this.storeCode = storeCode;
}
}
