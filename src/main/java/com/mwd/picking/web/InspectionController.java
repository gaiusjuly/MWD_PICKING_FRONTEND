package com.mwd.picking.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mwd.picking.model.Inspection;
import com.mwd.picking.service.InspectionService;


@RestController
@RequestMapping("/api")
public class InspectionController {
	@Autowired
	private InspectionService inspectionService;
	private static final int FAILED = -1;
	private static final int SUCCESS = 0;  
	
	@PostMapping("/inspection")
	ResponseEntity<List<Inspection>> getInspectionList(@RequestBody Inspection data) throws Exception {
		System.err.println("inspection");
//		System.out.println("data.getCenterCode() --> " + data.getCenterCode());
//		System.out.println("data.getStoreCode() --> " + data.getStoreCode());
//		System.out.println("data.getDeliDate() --> " + data.getDeliDate());
//		System.out.println("data.getDeliMethod() --> " + data.getDeliMethod());
//		System.out.println("data.getStatus() --> " + data.getStatus());
		return ResponseEntity.ok(inspectionService.selectInspection(data));
	}
	
	@ResponseBody
	@PostMapping("/inspection/confirmation")
	int confirmInspection(@RequestBody String data) throws Exception {
		System.err.println("/inspection/confirmation --> " + data);
		List<Inspection> O09List = new ArrayList<>();
		
		JSONObject root = new JSONObject(data);
		for (int i = 0 ; i < root.length() ; i++) {
			String s1 = root.get(i + "") + "";
			JSONObject jObj = new JSONObject(s1);
			
			Inspection insp = new Inspection();
			insp.setOrderCode(jObj.get("orderCode") + "");
			insp.setOrderDetailSeq(jObj.get("orderDetailSeq") + "");
			insp.setDeliNo(jObj.get("deliNo") + "");
			insp.setDeliDetailSeq(jObj.get("deliDetailSeq") + "");
			insp.setStoreCode(jObj.get("storeCode") + "");
			insp.setPickerId(jObj.get("pickerId") + "");
			O09List.add(insp);
		}
		
		for (int i = 0 ; i < O09List.size() ; i++) {
			System.out.println("getPickerId : " + Optional.ofNullable(O09List.get(i).getPickerId()) );
			System.out.println("orderCode : " + Optional.ofNullable(O09List.get(i).getOrderCode()));
			System.out.println("orderDetailSeq : " + Optional.ofNullable(O09List.get(i).getOrderDetailSeq()));
			System.out.println("deliNo : " + Optional.ofNullable(O09List.get(i).getDeliNo()));
			System.out.println("deliDetailSeq : " + Optional.ofNullable(O09List.get(i).getDeliDetailSeq()));
			System.out.println("storeCode : " + Optional.ofNullable(O09List.get(i).getStoreCode()));
			System.out.println("===========================================================================");
		}
		
//		if (1 > 0) return FAILED;
		
		Optional<String> userId = Optional.ofNullable(O09List.get(0).getPickerId());
		
		List<Inspection> O01_O03List = new ArrayList<>();
		List<Inspection> returnList = new ArrayList<>();
		for (int i = 0 ; i < O09List.size(); i++) {
			returnList = inspectionService.confirmInspection(O09List.get(i));
			for (int j = 0 ; j < returnList.size(); j++) {
				returnList.get(j).setPickerId(userId.get());
				O01_O03List.add(returnList.get(j));
				System.out.println("pickerId : "      + O01_O03List.get(O01_O03List.size() - 1).getPickerId());
				System.out.println("deliNo : "        + O01_O03List.get(O01_O03List.size() - 1).getDeliNo());
				System.out.println("deliDetailSeq : " + O01_O03List.get(O01_O03List.size() - 1).getDeliDetailSeq());
			}
		}
		
		System.err.println("O01_O03List.size() : " + O01_O03List.size());
		if (O01_O03List.size() <= 0) {
			return FAILED;
		}
		
		// 상품을 나타내는 실데이터 업데이트 한번에 하고 문제 생기면 롤백 처리
		if (inspectionService.updateInspectionAll(O01_O03List, O09List) < 0) {
			return FAILED;
		}
		
		return SUCCESS;
	}
}
