package com.mwd.picking.web;

import java.util.List;

import com.mwd.picking.model.Pick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.mwd.picking.service.PickGenServiceImpl;
import com.mwd.picking.model.GroupHeader;

@RestController
@RequestMapping("/api")
public class PickGenController {
	private static final Logger logger = LoggerFactory.getLogger(PickGenController.class);
	
	@Autowired
	private PickGenServiceImpl pickGenService;
	
	
	@PostMapping("/generategroupkey")
    public ResponseEntity<List<GroupHeader>> postResult(
    		@RequestBody GroupHeader data) throws Exception {
		System.err.println("pickArea "+data.getPickArea()+ " " + " " + data.getDelinostr());
		System.err.println("storeCode "+data.getGroupkey());
		return ResponseEntity.ok(pickGenService.generateGroupKey(data));
	}

	@PostMapping("/rollbacktote")
	ResponseEntity<Integer> rollbacktote(
			@RequestBody GroupHeader data) throws Exception {
		System.err.println("rollbacktote "+data.getGroupkey());
		return ResponseEntity.ok(pickGenService.rollbacktote(data));
	}
	@PostMapping("/rollbackpick")
	ResponseEntity<Integer> rollbackpick(
			@RequestBody GroupHeader data) throws Exception {
		System.err.println("rollbacktote "+data.getGroupkey());
		return ResponseEntity.ok(pickGenService.rollbackpick(data));
	}

}
