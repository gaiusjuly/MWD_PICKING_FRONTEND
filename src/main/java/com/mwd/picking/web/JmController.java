package com.mwd.picking.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mwd.picking.service.JmServiceImpl;
import com.mwd.picking.legacy.PickingPringJmRealTime;

import com.mwd.picking.model.Jangman;
import com.mwd.picking.model.JmHeader;

@RestController
@RequestMapping("/api")
public class JmController {
	private static final Logger logger = LoggerFactory.getLogger(JmController.class);
	@Autowired
	private JmServiceImpl jmService;

	@PostMapping("/getjangman")
	ResponseEntity<List<Jangman>> postResult(
			@RequestBody Jangman data) throws Exception {
		System.err.println(data);
		return ResponseEntity.ok(jmService.selectJangman(data));
	}

	@PostMapping("/putjangmanprintcount")
	ResponseEntity<Integer> putJangmanPrintCount (
			@RequestBody String orderCode) throws Exception {
		logger.info("PostMapping putjangmanprintcount "+ orderCode);
		return ResponseEntity.ok(jmService.updateJangmanPrintCount(orderCode));
	}

	@PostMapping("/printjangman")
	ResponseEntity<Integer> printjangman (
			@RequestBody Jangman jangman) throws Exception {
		int rtn=0;
		logger.info("PostMapping putjangmanprintcount "+ jangman.getOrderCodes());
		String orderCodes = jangman.getOrderCodes();
		String sPringOutLayer = jangman.getsPringOutLayer();
		String groupCode = jangman.getGroupCode();
		String[] order_code = orderCodes.split(",");
		HashMap param = new HashMap();
		for (int i = 0; i < order_code.length; i++) {

			String orderCode = order_code[i];
			param.put("ORDER_CODE", orderCode);

			// 피킹장만내역 등록---------------------------------------------
			int result = this.jmService.updateJangmanPrintCount(orderCode);
			//-----------------------------------------------------------------

			// 장만내역 (Header) 정보 조회
//			List<Map> vecHeader = this.jmService.getJmHeaderInfo(param);
			List<JmHeader> vecHeader = this.jmService.getJmHeaderInfo(param);

			System.err.println("vecHeader "+ vecHeader.size());
//			System.err.pritnln("vecHeader "+ vecHeader.get(0).getPrintIpP());
			if (vecHeader.size() > 0) {
				//------프린트IP는 아이패드 기준으로 설정한다.---------------------
				param.put("PRINT_IP"         , vecHeader.get(0).getPrintIpP());
				param.put("PRINT_PORT"       , "9100");

				if(groupCode.equals("713")){
					param.put("PRINT_IP"  , vecHeader.get(0).getPrintIpN()); // 4층피킹룸
					param.put("PRINT_PORT", "9100");
				}
				if(groupCode.equals("714")){
					param.put("PRINT_IP"  , vecHeader.get(0).getPrintIpS()); // 4층피킹룸
					param.put("PRINT_PORT", "9100");
				}
				if(groupCode.equals("715")){
					param.put("PRINT_IP"  , vecHeader.get(0).getPrintIpC()); // 4층피킹룸
					param.put("PRINT_PORT", "9100");
				}
				if(groupCode.equals("610")){
					param.put("PRINT_IP"  , vecHeader.get(0).getPrintIpB()); // 4층피킹룸
					param.put("PRINT_PORT", "9100");
				}

//				if(sPringOutLayer.equals("3")){
//					param.put("PRINT_IP"  , vecHeader.get(0).getPrintIpP3());// 2층 택배
//					param.put("PRINT_PORT", "9100");
//				}
//
//				if(sPringOutLayer.equals("4")){
//					param.put("PRINT_IP"  , vecHeader.get(0).getPrintIpP4());// 2층 신선
//					param.put("PRINT_PORT", "9100");
//				}

				System.out.println(" sPringOutLayer  ===>" + sPringOutLayer);
//				System.out.println(" PRINT_IP        ===>" + param.get("PRINT_IP").toString());

				param.put("DELI_REGION_NAME" , vecHeader.get(0).getDeliRegionName());
				param.put("DELI_TYPE"        , vecHeader.get(0).getDeliType());
				param.put("ORDER_DATE_YOIL"  , vecHeader.get(0).getOrderDate());
				param.put("DELIV_DATE"       , vecHeader.get(0).getDelivDate());
				param.put("ORDER_DATE"       , vecHeader.get(0).getOrderDate());
				param.put("DELI_ORDER"       , vecHeader.get(0).getDeliOrder());
				param.put("PICK_UP_TIME"     , vecHeader.get(0).getPickUpTime());
				param.put("START_TIME"       , vecHeader.get(0).getStartTime());
				param.put("ORDER_NAME"       , vecHeader.get(0).getOrderName());
				param.put("ORDER_ID"         , vecHeader.get(0).getOrderId());
				param.put("ORDER_TEL"        , vecHeader.get(0).getOrderTel());
				param.put("ORDER_HP"         , vecHeader.get(0).getOrderHp());
				param.put("GROUP_CODE"       , vecHeader.get(0).getGroupCode());
				param.put("ORDER_DATE_PRINT" , vecHeader.get(0).getOrderDatePrint());
				//--------------------------------------------------------------------
				param.put("ORDER_CODE"       , vecHeader.get(0).getOrderCode());
				param.put("RECE_NAME"        , vecHeader.get(0).getReceName());
				param.put("RECE_ADDR1"       , vecHeader.get(0).getReceAddr1());
				param.put("RECE_ADDR2"       , vecHeader.get(0).getReceAddr2());
				param.put("DELI_TITLE"       , vecHeader.get(0).getDeliTitle());
				param.put("ORDER_MESSAGE"    , vecHeader.get(0).getOrderMessage());
				param.put("ORDER_DELI_METHOD", vecHeader.get(0).getOrderDeliMethod());
//	        	param.put("REQ_FLAG"         , sREQ_FLAG);  // 일반, 예약구분

			}

			// 장만내역 (Detail) 정보 조회
			List<Map> vecDetail = this.jmService.getJmDetailInfo(param);
			System.err.println("vecDetail length "+ vecDetail.size());

			if(vecDetail.size()>0) {
				String firstMeesage = PickingPringJmRealTime.printSend(param, vecDetail);

				//if (vecDetail.size()>0) {
				param.put("GROUP_CODE", vecDetail.get(0).get("GROUP_CODE"));
				//}

				//Type 1-보관용, 2-고객용 (석동리 대리 요청으로 고객용만 출력시킴(2016-11-16)
				PickingPringJmRealTime.printSend(param, firstMeesage, "2");
				jmService.updateJangmanPrintCount(orderCode);
				rtn=1;
			}
		}

		return ResponseEntity.ok(rtn);
	}

}
