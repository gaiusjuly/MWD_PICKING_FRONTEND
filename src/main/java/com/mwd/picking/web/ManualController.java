package com.mwd.picking.web;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.mwd.picking.service.ManualServiceImpl;
import com.mwd.picking.service.PickCompleteServiceImpl;
import com.mwd.picking.model.Event;
import com.mwd.picking.model.Inspection;
import com.mwd.picking.model.Jangman;
import com.mwd.picking.model.Manual;
import com.mwd.picking.model.Pick;

@RestController
@RequestMapping("/api")
public class ManualController {
	private static final Logger logger = LoggerFactory.getLogger(ManualController.class);
	@Autowired
	private ManualServiceImpl manualService;
	
	@Autowired
	private PickCompleteServiceImpl pickCompleteService;
	
@PostMapping("/getmanualpicklist1")
	 ResponseEntity<List<Manual>> getManualPickList(
    		@RequestBody Manual data) throws Exception {
		System.err.println("getManualPickList");
		return ResponseEntity.ok(manualService.selectManualPickList(data));
	}

	@PostMapping("/domanualpick1")

	ResponseEntity<Integer> putManualPick(
		@RequestBody HashMap<String, Object> map) throws Exception {
         System.err.println(map);
		Pick[] picks = new Pick[map.size()];
		for( String key : map.keySet() ) {
			HashMap temp = (HashMap) map.get(key);
//			System.err.println(temp.keySet());
			Pick pick = new Pick();
			for (Object k : temp.keySet()) {
				pick.setDeliNo((String) temp.get("deliNo"));
				pick.setDeliDetailSeq((String) temp.get("deliDetailSeq"));
				pick.setStatus((String) temp.get("status"));
				pick.setPicknode((String) temp.get("picknode"));
				pick.setGroupkey((String) temp.get("groupkey"));
				pick.setPickArea((String) temp.get("pickArea"));
				pick.setToteArea("1");
				pick.setToteType((String) temp.get("toteType"));
				pick.setPickerId((String) temp.get("pickerId"));
				pick.setStoreCode((String) temp.get("storeCode"));
				pick.setMasterCartCode((String) temp.get("masterCartCode"));
				pick.setQty((String) temp.get("qty"));
				pick.setPickQty((String) temp.get("pickQty"));
				pick.setTotalPickQty((String) temp.get("totalPickQty"));
				pick.setOrderCnclType("0");
			}
			picks[new Integer(key).intValue()] = pick;
		}
		return ResponseEntity.ok(pickCompleteService.putManualPick(picks));
	}
	
	@PostMapping("/getinspectionlist")
	 ResponseEntity<List<Inspection>> getInspectionList(
   		@RequestBody Inspection data) throws Exception {
		System.err.println("getInspectionList");
		return ResponseEntity.ok(manualService.selectInspection(data));
	}
	
	@PostMapping("/getEventList")
	 ResponseEntity<List<Event>> getEventList(
  		@RequestBody Event data) throws Exception {
		System.err.println("getEventList");
		return ResponseEntity.ok(manualService.selectEventList(data));
	}
	
//	@PostMapping("/putInspection")
//	 ResponseEntity<Integer> putInspection(
//  		@RequestBody Inspection data) throws Exception {
//		System.err.println("getInspectionList");
//		return ResponseEntity.ok(manualService.updateInspection(data));
//	}
}
