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
import com.mwd.picking.service.UserDetailServiceImpl;
import com.mwd.picking.service.PickServiceImpl;
import com.mwd.picking.service.PickCompleteServiceImpl;
import com.mwd.picking.service.CodeServiceImpl;
import com.mwd.picking.model.Order;
import com.mwd.picking.model.Code;
import com.mwd.picking.model.OrderOwner;
import com.mwd.picking.model.OrderProduct;
import com.mwd.picking.model.Tot;
import com.mwd.picking.model.User;
import com.mwd.picking.model.Good;
import com.mwd.picking.model.GroupHeader;
import com.mwd.picking.model.Memo;
import com.mwd.picking.model.Pick;
import com.mwd.picking.model.Product;

@RestController
@RequestMapping("/api")
public class PickController {
	private static final Logger logger = LoggerFactory.getLogger(PickController.class);
	@Autowired
	private UserDetailServiceImpl UserDetailService;
	
	@Autowired
	private PickServiceImpl pickService;
	
	@Autowired
	private CodeServiceImpl codeService;
	
	@Autowired
	private PickCompleteServiceImpl pickCompleteService;

	@GetMapping("/getcommcode")
	ResponseEntity<List<Code>> getCommCode(@RequestParam("codeDiv") String codeDiv) throws Exception {
		logger.info("commcode controller");
		return ResponseEntity.ok(codeService.selectCommcode(codeDiv));
	}
	
	@GetMapping("/getjob")
	ResponseEntity<List<GroupHeader>> getJob(@RequestParam("pickerId") String pickerId, @RequestParam("pickArea") String pickArea, @RequestParam("storeCode") String storeCode) throws Exception {
		logger.info("get job"+pickerId + ' '+storeCode);
		GroupHeader header = new GroupHeader();
		header.setPickerId(pickerId);
		header.setPickArea(pickArea);
		header.setStoreCode(storeCode);
		return ResponseEntity.ok(pickService.selectGroupHeader(header));
	}
	
	@GetMapping("/getusername")
//	public ResponseEntity<UserDetails> getUsername(@RequestParam("userId") String userId) {
	public ResponseEntity<User> getUsername(@RequestParam("userId") String userId) {
		logger.info("Getusername controller");
//		System.err.println("Getusername controller");
//		System.err.println("userId "+ userId);
//		return ResponseEntity.ok(UserDetailService.loadUserByUsername(userId));
		return ResponseEntity.ok(UserDetailService.loadUserInfo(userId));
	}
	
	@PostMapping("/getpicks")
    ResponseEntity<List<Order>> postResult(
    		@RequestBody Order data) throws Exception {
		System.err.println(data);
//		ResponseEntity<List> temp =  ResponseEntity.ok(pickService.selectPicks(pick));
//		System.err.println(temp);
		return ResponseEntity.ok(pickService.selectPicks(data));
	}
	
	@GetMapping("/getorderdetailproduct")
	public ResponseEntity<List<OrderProduct>> getOrderDetailProduct(@RequestParam("orderCode") String orderCode) throws Exception {
		logger.info("/getorderdetailproduct mapping");
		return ResponseEntity.ok(pickService.selectOrderDetailProduct(orderCode));
	}
	
	@GetMapping("/getorderdetailowner")
	public ResponseEntity<OrderOwner> getOrderDetailOwner(@RequestParam("orderCode") String orderCode) throws Exception {
		logger.info("/getorderdetailowner mapping");
		return ResponseEntity.ok(pickService.selectOrderDetailOwner(orderCode));
	}
	
	@PostMapping("/putMemo")
    ResponseEntity<Integer> putMemo (
    		@RequestBody HashMap<?, ?> data) throws Exception {
		logger.info("PostMapping putmemo "+ data);	
		return ResponseEntity.ok(pickService.insertMemo(data));
	}
	
	@GetMapping("/getmemo")
	public ResponseEntity<List<Memo>> getMemo(@RequestParam("deliNo") String deliNo) throws Exception {
		logger.info("/getMemo mapping");
		return ResponseEntity.ok(pickService.selectMemo(deliNo));
	}
	
	@GetMapping("/gettots")
	public ResponseEntity<List<Tot>> getTots(@RequestParam("groupkey") String groupkey, @RequestParam("pickArea") String pickArea, @RequestParam("storeCode") String storeCode) throws Exception {
		logger.info("/getTots mapping");
		return ResponseEntity.ok(pickService.selectTots(groupkey,pickArea,storeCode));
	}
	
	@GetMapping("/gethasassigntot")
	public ResponseEntity<String> getHasassigntot(@RequestParam("groupkey") String groupkey, @RequestParam("cartcode") String cartcode) throws Exception {
		logger.info("/hasassigntot mapping");
		logger.info("groupkey "+groupkey);
		logger.info("cartcode "+cartcode);
		return ResponseEntity.ok(pickService.selectHasAssignTot(groupkey, cartcode));
	}
	
	@GetMapping("/getAssignDeliveryinfo")
	public ResponseEntity<List<HashMap>> selecAssignDeliveryinfo(@RequestParam("groupkey") String groupkey) throws Exception {
		logger.info("/getAssignDeliveryinfo mapping");
		logger.info("groupkey "+ groupkey);
		return ResponseEntity.ok(pickService.selecAssignDeliveryinfo(groupkey));
	}
	
	@PostMapping("/putMasterTrDetail")
    ResponseEntity<Integer> putMasterTrDetail(
    		@RequestBody HashMap data) throws Exception {
		return ResponseEntity.ok(pickService.updateMasterTrDetail(data));
	}

	@PostMapping("/deleteMasterTrDetail")
	ResponseEntity<Integer> deleteMasterTrDetail(
			@RequestBody HashMap data) throws Exception {
		System.err.println(data);
		return ResponseEntity.ok(pickService.deleteMasterTrDetail(data));
	}
	
	@PostMapping("/putMoveStatusProductScan")
    ResponseEntity<Integer> putMoveStatusProductScan(
    		@RequestBody GroupHeader data) throws Exception {
		System.err.println(data);
		System.err.println("groupkey "+data.getGroupkey());
		System.err.println("pickArea "+data.getPickArea());
		System.err.println("pickerId "+data.getPickerId());
		
		return ResponseEntity.ok(pickService.updateMoveStatusProductScan(data));
	}
	
	@GetMapping("/getgoods")
	public ResponseEntity<List<Good>> getGoods(@RequestParam("groupkey") String groupkey, @RequestParam("storeCode") String storeCode) throws Exception {
		System.err.println("getgoods started");
		logger.info("/getGoods mapping");
		return ResponseEntity.ok(pickService.selectGoods(groupkey, storeCode));
	}
	
	@GetMapping("/getgooddetaillist")
	public ResponseEntity<List<Good>> getGoodDetailList(@RequestParam("groupkey") String groupkey, @RequestParam("productid") String productid, @RequestParam("sortno") String sortno, @RequestParam("storeCode") String storeCode) throws Exception {
		logger.info("/getgooddetaillist mapping");
		logger.info("groupkey "+ groupkey);
		logger.info("productid " + productid);
		logger.info("sortno " + sortno);
		logger.info("storeCode " + storeCode);
		
		return ResponseEntity.ok(pickService.selectGoodDetailList(groupkey,productid,sortno,storeCode));
	}
	
	@GetMapping("/getgoodtotlist")
	public ResponseEntity<List<Tot>> getGoodTotList(@RequestParam("groupkey") String groupkey, @RequestParam("productid") String productid, @RequestParam("delino") String delino, @RequestParam("deliseq") String deliseq, @RequestParam("storeCode") String storeCode) throws Exception {
		logger.info("/getgoodtotlist mapping");
		return ResponseEntity.ok(pickService.selectGoodTotList(groupkey,productid, delino, deliseq, storeCode));
	}
	
	@GetMapping("/getcheckupgood")
	public ResponseEntity<String> getCheckupGood(@RequestParam("productid") String productid) throws Exception {
		logger.info("/getcheckupgood mapping");
		return ResponseEntity.ok(pickService.selectCheckupGood(productid));
	}
	
	@PostMapping("/dopick")
    public ResponseEntity<Integer> doPick(
    		@RequestBody Pick data) throws Exception {
		System.err.println("deliNo "+data.getDeliNo());
		System.err.println("status "+data.getStatus());
		System.err.println("deliDetailSeq "+data.getDeliDetailSeq());
		System.err.println("masterCartCode "+data.getMasterCartCode());
		System.err.println("pickQty "+data.getPickQty());
		System.err.println("pickArea "+data.getPickArea());
		System.err.println("toteType "+data.getToteType());
		System.err.println("toteArea "+data.getToteArea());
		System.err.println("replaceReasonCode "+data.getReplaceReasonCode());
				
		return ResponseEntity.ok(pickCompleteService.completePick(data));
	}
	
	@PostMapping("/docancellationpick")
    public ResponseEntity<Integer> doCancellationPick(
    		@RequestBody Pick data) throws Exception {
		System.err.println("docancellationpick");
		System.err.println("deliNo "+data.getDeliNo());
		System.err.println("status "+data.getStatus());
		System.err.println("deliDetailSeq "+data.getDeliDetailSeq());
		System.err.println("masterCartCode "+data.getMasterCartCode());
		System.err.println("pickQty "+data.getPickQty());
		System.err.println("pickArea "+data.getPickArea());
		System.err.println("toteType "+data.getToteType());
		System.err.println("toteArea "+data.getToteArea());
		
		return ResponseEntity.ok(pickCompleteService.cancellationPick(data));
	}
	
	private Pick setPickKeySet(HashMap temp) {
		System.err.println((String) temp.get("status"));
		Pick pick = new Pick();
		pick.setGroupkey((String) temp.get("groupkey"));
		pick.setToteArea((String) temp.get("toteArea"));
		pick.setPickArea((String) temp.get("pickArea"));
		pick.setPicknode((String) temp.get("picknode"));
		pick.setToteType((String) temp.get("toteType"));
		pick.setStatus((String) temp.get("status"));
		pick.setOrderCnclType((String) temp.get("orderCnclType"));
		pick.setPickerId((String) temp.get("pickerId"));
		pick.setTotalPickQty((String) temp.get("totalPickQty"));
		pick.setPickQty((String) temp.get("pickQty"));
		pick.setMasterCartCode((String) temp.get("masterCartCode"));
		pick.setDeliDetailSeq((String) temp.get("deliDetailSeq"));
		pick.setDeliNo((String) temp.get("deliNo"));
		return pick;
	}
	
	@PostMapping("/doallpick")
    public ResponseEntity<Integer> doAllPick(
    		@RequestBody HashMap<String, Object> map) throws Exception {
		System.err.println("doallpick "+map.size());
	    System.err.println("map " + map);
	    System.err.println("map.keySet  "+map.keySet());
			Pick[] picks = new Pick[map.size()];
			for( String key : map.keySet() ) {
				HashMap temp = (HashMap) map.get(key);
				System.err.println("temp => "+temp.keySet());
				Pick pick = new Pick();
				for (Object k : temp.keySet()) {
//					pick = setPickKeySet(temp);
					pick.setGroupkey((String) temp.get("groupkey"));
					pick.setToteArea((String) temp.get("toteArea"));
					pick.setPickArea((String) temp.get("pickArea"));
					pick.setPicknode((String) temp.get("picknode"));
					pick.setToteType((String) temp.get("toteType"));
					pick.setStatus((String) temp.get("status"));
					pick.setOrderCnclType((String) temp.get("orderCnclType"));
					pick.setPickerId((String) temp.get("pickerId"));
					pick.setTotalPickQty((String) temp.get("totalPickQty"));
					pick.setAllcQty((String) temp.get("allcQty"));
					pick.setPickQty((String) temp.get("pickQty"));
					pick.setMasterCartCode((String) temp.get("masterCartCode"));
					pick.setCurrentCartCode((String) temp.get("currentCartCode"));
					pick.setDeliDetailSeq((String) temp.get("deliDetailSeq"));
					pick.setDeliNo((String) temp.get("deliNo"));
					pick.setOrgProductId((String) temp.get("orgProductId"));
					pick.setSubPickNode((String) temp.get("subPickNode"));
				}
				picks[new Integer(key).intValue()] = pick;
			}
//			if(map.size()==1) {
//				picks[0]=setPickKeySet(map);
//			}
			System.err.println("picks "+picks[0].getStatus());
		if(picks[0].getStatus().equals("10"))
		  return ResponseEntity.ok(pickCompleteService.allPickCancel(picks));
		else
  		  return ResponseEntity.ok(pickCompleteService.allPick(picks));	
	}
	
	@PostMapping("/doallcancelpick")
    public ResponseEntity<Integer> doCancelAllPick(
    		@RequestBody HashMap<String, Object> map) throws Exception {
		System.err.println("doallcancelpick");
		Pick[] picks = new Pick[map.size()];
		for( String key : map.keySet() ) {
			HashMap temp = (HashMap) map.get(key);
//			System.err.println(temp.keySet());
			Pick pick = new Pick();
			for (Object k : temp.keySet()) {
				System.err.println("deliNo=> "+(String) temp.get("deliNo"));
				pick.setDeliNo((String) temp.get("deliNo"));
				pick.setDeliDetailSeq((String) temp.get("deliDetailSeq"));
				pick.setGroupkey((String) temp.get("groupkey"));
				pick.setPickArea((String) temp.get("pickArea"));
				pick.setStatus((String) temp.get("status"));
				pick.setPickerId((String) temp.get("pickerId"));
				pick.setStoreCode((String) temp.get("storeCode"));
				pick.setToteType((String) temp.get("toteType"));
				pick.setTotalPickQty((String) temp.get("totalPickQty"));
				pick.setPickQty("0");
				System.err.println("deliNo "+pick.getDeliNo());
			}
			picks[new Integer(key).intValue()] = pick;
		}
		return ResponseEntity.ok(pickCompleteService.allPickCancel(picks));
	}
	
	@GetMapping("/getproductbybarcode")
	public ResponseEntity<String> getProductByBarcode(@RequestParam("productId") String productId) throws Exception {
		logger.info("/selectProductByBarcode mapping");
		return ResponseEntity.ok(pickCompleteService.selectProductByBarcode(productId));
	}

	@PostMapping("/getassigntotelist")
	public ResponseEntity<List<Tot>> getassigntotelist(
			@RequestBody Tot data) throws Exception {
		System.err.println("getassigntotelist "+ data.getDeliNos());
		return ResponseEntity.ok(pickService.getassigntotelist(data));
	}
	
	@GetMapping("/getBcrCheck")
	public ResponseEntity<String> getBcrCheck(@RequestParam("groupkey") String groupkey, @RequestParam("masterCartCode") String masterCartCode) throws Exception {
		logger.info("/getBcrCheck mapping");
		Pick data = new Pick();
		data.setGroupkey(groupkey);
		data.setMasterCartCode(masterCartCode);
		return ResponseEntity.ok(pickService.selectBcrCheck(data));
	}
	
	@GetMapping("/getProductInfo")
	public ResponseEntity<List<Product>> getProductInfo(@RequestParam("orderCode") String orderCode, @RequestParam("productId") String productId
			, @RequestParam("storeCode") String storeCode, @RequestParam("orderDetailSeq") String orderDetailSeq ) throws Exception {
		logger.info("/getProductInfo mapping");
		Product data = new Product();
		data.setOrderCode(orderCode);
		data.setProductId(productId);
		data.setStoreCode(storeCode);
		data.setOrderDetailSeq(orderDetailSeq);
		return ResponseEntity.ok(pickService.getProductInfo(data));
	}
	
	@GetMapping("/getProductInfoTarget")
	public ResponseEntity<List<Product>> getProductInfoTarget(@RequestParam("orderCode") String orderCode, @RequestParam("productId") String productId
			, @RequestParam("storeCode") String storeCode, @RequestParam("orderDetailSeq") String orderDetailSeq ) throws Exception {
		logger.info("/getProductInfoTarget mapping");
		Product data = new Product();
		data.setOrderCode(orderCode);
		data.setProductId(productId);
		data.setStoreCode(storeCode);
		data.setOrderDetailSeq(orderDetailSeq);
		return ResponseEntity.ok(pickService.getProductInfoTarget(data));
	}
}
