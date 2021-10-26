package com.mwd.picking.model;

public class Memo {
  private String deliNo;
  public String getDeliNo() {
	return deliNo;
}
public void setDeliNo(String deliNo) {
	this.deliNo = deliNo;
}
public String getMemo() {
	return memo;
}
public void setMemo(String memo) {
	this.memo = memo;
}
public String getCreateBy() {
	return createBy;
}
public void setCreateBy(String createBy) {
	this.createBy = createBy;
}
public String getCreateData() {
	return createData;
}
public void setCreateData(String createData) {
	this.createData = createData;
}
private String memo;
  private String createBy;
  private String createData;
}
