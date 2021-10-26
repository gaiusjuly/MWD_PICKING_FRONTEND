package com.mwd.picking.web;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.mwd.picking.model.Manual;
import com.mwd.picking.service.BulkServiceImpl;

@RestController
@RequestMapping("/api")
public class BulkController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BulkController.class);
	
	@Autowired
	private BulkServiceImpl bulkServiceImpl;
	
	/**
	* 이형주문목록조회
	* 
	* **/
	@PostMapping("/getmanualpicklist")
	 ResponseEntity<List<Manual>> getBulkDeliList(@RequestBody Manual data) throws Exception {			
		return ResponseEntity.ok(bulkServiceImpl.selectBulkDeliList(data));
	}
	
	/**
	* 이형검수완료
	* 
	* **/
	@PostMapping("/domanualpick11")
	ResponseEntity<Integer> putBulkPick(@RequestBody HashMap<String, Object> map) throws Exception {
		return ResponseEntity.ok(bulkServiceImpl.putBulkPick(map));
	}
	
	/**
	* 이형검수취소
	* 
	* **/
	@PostMapping("/domanualpickcncl") 
	ResponseEntity<Integer> putBulkPickCncl(@RequestBody HashMap<String, Object> map) throws Exception {
		return ResponseEntity.ok(bulkServiceImpl.putBulkPickCncl(map));
	}
	
	/**
	* 이형상품품절처리
	* 
	* **/
	@PostMapping("/domanualpick") //domanualsoldout
	ResponseEntity<Integer> putBulkSoldout(@RequestBody HashMap<String, Object> map) throws Exception {
		return ResponseEntity.ok(bulkServiceImpl.putBulkSoldout(map));
	}
	
}
