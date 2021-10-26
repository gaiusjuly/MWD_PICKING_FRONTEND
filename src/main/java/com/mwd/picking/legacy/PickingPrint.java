package com.mwd.picking.legacy;

import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mwd.picking.legacy.StringUtil;


public class PickingPrint {


    public final static char STX = (char)0x02;
    public final static char ETX = (char)0x03;
    public final static char ESC = (char)0x1B;
    public final static char GS = (char)0x1D;
    public final static char CR = (char)0x0D;
    public final static char LF = (char)0x0A;

    public final static char[] data = new char[] { 0x86, 0x86,0x86,0x86 ,
            0x86, 0x86,0x86,0x86 ,
            0x86, 0x86,0x86,0x86 ,
            0xBB, 0x0A ,
            0xBA, 0x20,0x20,0x20 ,
            0x45, 0x50,0x53,0x4F ,
            0x4E, 0x20,0x20,0x20 ,
            0xBA, 0x0A
    };


    public PickingPrint() {

    }

    public static void main(String[] args)    {
        System.out.println("Pickign Print Started.......");
        try {
            com.mwd.picking.legacy.PickingPrint pp = new com.mwd.picking.legacy.PickingPrint();

            HashMap param = new HashMap();

            // 실운영
            //param.put("PRINT_IP", "172.27.62.254");
            //param.put("PRINT_PORT", "9100");

            // 도연관 테스트 아이피
            //param.put("PRINT_IP", "172.26.10.222");
            //param.put("PRINT_PORT", "9100");

            String firstMeesage = "";
            firstMeesage +=ESC + "@";
            firstMeesage +=ESC + "3" + (char)18;
            firstMeesage +=ESC + "U" + (char)1;
            firstMeesage +=ESC + "a" + (char)1;
            firstMeesage +=GS + "!" + (char)17; //font size

            for(int i=0 ; i < 14*2 ; i++) {
                firstMeesage +=  data[i];

            }
            firstMeesage +=GS + "!" + (char)0; //font size
            firstMeesage +="Thank you" + LF;

            firstMeesage +=GS + "/";

            pp.printSend(param,firstMeesage,"","");

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        System.out.println("Pickign Print End.......");
    }

    public static void printSend(HashMap param , String firstMessage, String secondMessage, String thirdMessage) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;

        try{

            // 운영아이피
            String printIP   = (String)param.get("PRINT_IP");
            String printPort = (String)param.get("PRINT_PORT");

            System.out.println(":::=> 운영 printIP======>" + printIP);
            System.out.println(":::=> 운영 printPort====>" + printPort);

            printerServer = new Socket(printIP, Integer.parseInt(printPort));
            printerServer.setSoTimeout(10*1000);

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = " \n  \n \n \n \n";
            String cutt = GS + "V0";

            //첫번째 최종 주문에 대한 영수증 출력
            dataOut.write(firstMessage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            //추가,반품,최종 거스름돈(받을돈 ) 영수증 출력
            dataOut.write(secondMessage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            //고객인식표
            dataOut.write(thirdMessage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            //고객인식표
            dataOut.write(thirdMessage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            dataOut.flush();

        }catch(Exception ex){
            throw ex;
        }finally{
            try {
                if ( dataOut != null ) {
                    dataOut.close();
                    dataOut = null;
                }
                if (printerServer != null) {
                    printerServer.close();
                    printerServer = null;
                }
            } catch ( Exception ignored ){}
        }
    }

    public static void printSend(HashMap param , String firstMessage, String secondMessage, String thirdMessage, String fourthMessage) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;

        try{

            // 운영아이피
            String printIP   = (String)param.get("PRINT_IP");
            String printPort = (String)param.get("PRINT_PORT");

            System.out.println(":::=> test rms .... 운영 printIP======>" + printIP);
            System.out.println(":::=> test rms .... 운영 printPort====>" + printPort);

            printerServer = new Socket(printIP, Integer.parseInt(printPort));
            printerServer.setSoTimeout(10*1000);

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = " \n  \n \n \n \n";
            String cutt = GS + "V0";

            //첫번째 최종 주문에 대한 영수증 출력
            dataOut.write(firstMessage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            //추가,반품,최종 거스름돈(받을돈 ) 영수증 출력
            dataOut.write(secondMessage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            //고객인식표
            if (!thirdMessage.equals("")) {
                dataOut.write(thirdMessage.getBytes("euc-kr"));
                dataOut.write(dummy.getBytes());
                dataOut.write(dummy.getBytes());
                dataOut.write(cutt.getBytes());
            }

            //라벨출력..
            if (!fourthMessage.equals("")) {
                dataOut.write(fourthMessage.getBytes("euc-kr"));
                dataOut.write(dummy.getBytes());
                dataOut.write(cutt.getBytes());
            }

            dataOut.flush();

        }catch(Exception ex){
            throw ex;
        }finally{
            try {
                if ( dataOut != null ) {
                    dataOut.close();
                    dataOut = null;
                }
                if (printerServer != null) {
                    printerServer.close();
                    printerServer = null;
                }
            } catch ( Exception ignored ){}
        }
    }

    public static void printSendLabel(HashMap param , String lebelMessage) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;

        try{

            // 운영아이피
            String printIP   = (String)param.get("PRINT_IP");
            String printPort = (String)param.get("PRINT_PORT");

            System.out.println(":::=> 운영 printIP======>" + printIP);
            System.out.println(":::=> 운영 printPort====>" + printPort);

            printerServer = new Socket(printIP, Integer.parseInt(printPort));
            printerServer.setSoTimeout(10*1000);

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = " \n  \n \n \n \n";
            String cutt = GS + "V0";

            //라벨
            if (!lebelMessage.equals("")) {
                dataOut.write(lebelMessage.getBytes("euc-kr"));
                dataOut.write(dummy.getBytes());
                dataOut.write(dummy.getBytes());
                dataOut.write(cutt.getBytes());
            }


            dataOut.flush();

        }catch(Exception ex){
            throw ex;
        }finally{
            try {
                if ( dataOut != null ) {
                    dataOut.close();
                    dataOut = null;
                }
                if (printerServer != null) {
                    printerServer.close();
                    printerServer = null;
                }
            } catch ( Exception ignored ){}
        }
    }

    // 피킹영수증(유닛,라벨)
    public static void printSendUnitLabel(HashMap param , String lebelMessage) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;

        try{

            // 운영아이피
            String printIP   = (String)param.get("PRINT_IP");
            String printPort = (String)param.get("PRINT_PORT");

            System.out.println(":::=> 운영 printIP======>" + printIP);
            System.out.println(":::=> 운영 printPort====>" + printPort);

            printerServer = new Socket(printIP, Integer.parseInt(printPort));
            printerServer.setSoTimeout(10*1000);

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = " \n  \n \n \n \n";
            String cutt = GS + "V0";

            //라벨
            if (!lebelMessage.equals("")) {
                dataOut.write(lebelMessage.getBytes("euc-kr"));
                dataOut.write(dummy.getBytes());
                dataOut.write(dummy.getBytes());
                dataOut.write(cutt.getBytes());
            }


            dataOut.flush();

        }catch(Exception ex){
            throw ex;
        }finally{
            try {
                if ( dataOut != null ) {
                    dataOut.close();
                    dataOut = null;
                }
                if (printerServer != null) {
                    printerServer.close();
                    printerServer = null;
                }
            } catch ( Exception ignored ){}
        }
    }


    //프린팅..
    public static String printSend(HashMap param, HashMap header, HashMap info, List<Map> detail) throws Exception {

        String sOutput = null;
        String[] mallAdminIds = {"mega2706", "mega2701" ,"mega2708" ,"dnftkswja" ,"mega2709" ,"mega2702" ,"mega2776" ,"mega2704"};
        try{
            if(param.get("system").equals("res")){
                sOutput = "<br/>";
            }
            if(param.get("system").equals("oms")){
                sOutput = "";
                sOutput +=ESC + "a" + (char)1; //가운데 정렬
                sOutput +=ESC + "M" + (char)0; //font A
                sOutput +=ESC + "E" + (char)1; //강조
                sOutput +=GS + "!" + (char)32; //font size
                if(!StringUtil.nvl(param.get("ADD_ORDER_ORG_ORDER_DESC")).equals("")){
                    sOutput +="추가주문" + LF ; //줄바꿈
                }
                sOutput +="MEGAMART" + LF ; //줄바꿈
                sOutput +=GS + "!" + (char)0;
                //sOutput +="(메가마트온라인쇼핑몰)" + LF ; //줄바꿈
                sOutput +="(메가마트몰 "+ info.get("COM_NAME")+ ")" + LF; //줄바꿈
                sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
                sOutput +=ESC + "E" + (char)0;

                sOutput = sOutput + info.get("ONLINE_BUSINESS_CODE")+ "\n";
                sOutput = sOutput + info.get("COM_ADDR1")+ "\n";
                sOutput = sOutput + " 문의전화:" +info.get("ONLINE_PHONE_NUMBER") + "\n\n";
                //sOutput = sOutput + info.get("COM_ADDR2")+ "\n";  //사용안함
                //sOutput = sOutput + info.get("UP_AD")+ "\n";      //사용안함.
            }
            sOutput = sOutput + "[주문코드]"+ header.get("ORDER_CODE")            +"\n";
            sOutput = sOutput + "[주문일시]"+ header.get("INS_DATE")              + "\n";
            sOutput = sOutput + "[배송일시]"+ header.get("ORDER_WANTED_DELI_DATE")+ "\n";
            sOutput = sOutput + "[주문자명]"+ header.get("ORDER_NAME_BLIND")+" ("+ header.get("ORDER_ID_BLIND")+")"+ "\n";
            sOutput = sOutput + "[전화번호]"+ header.get("ORDER_TEL_BLIND")       +  "\n";
            sOutput = sOutput + "[수령자명]"+ header.get("RECE_NAME_BLIND")       + "\n";
            sOutput = sOutput + "[전화번호]"+ header.get("RECE_HP_BLIND")         + "\n";
            sOutput = sOutput + "[배송주소]"+ header.get("RECE_ADDR1")            + "\n";
            //sOutput = sOutput + header.get("RECE_ADDR2_BLIND")+ "\n";
            sOutput = sOutput + "[주문지수]"+ header.get("TOT_ORDER_CNT")+ "회"+"\n";

            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
            sOutput =  sOutput +""+ "상품명           단가     수량      금액 " + "\n";
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

            String barcord = (String) header.get("ORDER_DATE") + (String) header.get("ORDER_SEQ") + "000";
            //String barcord = (String) header.get("ORDER_CODE") + "000";

            String product_id = null;
            String product_name = null;

            int tot_sale_money = 0;
            int tax_sale_money = 0;
            int non_sale_money = 0;
            int sale_vat_money = 0;
            int pro_cnt = 0;

            int good_money  = 0;
            int good_discount  = 0;
            int order_deli_money  = 0;
            int tot_delivery_price  = 0;
            int order_deli_package  = 0;
            int deli_discount  = 0;
            int cart_discount  = 0;
            int new_cart_discount  = 0;
            int compensation_cart_discount =0;
            int diff_cart_discount = 0;
            int order_point_money  = 0;
            int tot_pay_money  = 0;
            int discount_price = 0;
            int tot_discount_price = 0;

            int order_gift_card_money  = 0;  //상품권전환금 사용 추가
            int order_mgift_card_money  = 0; //모바일상품권전환금 사용 추가
            int order_mileage_money  = 0; //복지마일리지 추가

            // StringUtil.nvl(row.get("PACK_METHOD_FLAG")

            String packMethodFlag = null;

            int iD1Cnt = 0;
            int iD2Cnt = 0;
            int iD3Cnt = 0;

            if(detail.size() > 0){
                for(int i=0 ; i< detail.size() ; i++) {
                    HashMap row = (HashMap)detail.get(i);

                    packMethodFlag  = StringUtil.nvl((String)row.get("PACK_METHOD_FLAG"));

                    BigDecimal QTY = new BigDecimal(0);
                    QTY = (BigDecimal)row.get("QTY");

                    if(packMethodFlag.equals("D1") && QTY.intValue() > 0) {
                        iD1Cnt++;
                    }
                    if(packMethodFlag.equals("D2") && QTY.intValue() > 0) {
                        iD2Cnt++;

                    }
                    if(packMethodFlag.equals("D3") && QTY.intValue() > 0) {
                        iD3Cnt++;
                    }

                    if ("0".equals(StringUtil.nvl(row.get("PRO_TAX_FLAG")))) {// 면세 : 2 ==> 0
                        product_id = row.get("PRODUCT_ID") + "＊";
                    } else {
                        product_id = (String)row.get("PRODUCT_ID")+ "";
                    }

                    if ("D".equals(StringUtil.nvl(row.get("DELI_AREA_PRODUCT")))){     /** 직송상품일 경우에는 앞에 별표 표시 **/
                        product_name  = '★' + (String)row.get("PRO_NAME");
                    }else{
                        product_name  =  (String)row.get("PRO_NAME");
                    }

                    System.out.println("packMethodFlag==>"+packMethodFlag);

                    System.out.println("iD1Cnt==>"+iD1Cnt);
                    System.out.println("iD2Cnt==>"+iD2Cnt);
                    System.out.println("iD3Cnt==>"+iD3Cnt);

                    if(packMethodFlag.equals("D1") && iD1Cnt==1){
                        product_name = "-----------------<상  온>---------------- \n" + product_name;
                    }
                    if(packMethodFlag.equals("D2") && iD2Cnt==1){
                        product_name = "-----------------<냉  장>---------------- \n" + product_name;
                    }
                    if(packMethodFlag.equals("D3") && iD3Cnt==1){
                        product_name = "-----------------<냉  동>---------------- \n" + product_name;
                    }



                    BigDecimal SALE_PRICE = new BigDecimal(0); 		 	SALE_PRICE = (BigDecimal)row.get("SALE_PRICE");
                    BigDecimal DISCOUNT_PRICE = new BigDecimal(0);	 	DISCOUNT_PRICE = (BigDecimal)row.get("DISCOUNT_PRICE");
                    BigDecimal SALE_MONEY = new BigDecimal(0);			SALE_MONEY = (BigDecimal)row.get("SALE_MONEY");

                    BigDecimal CMS_QTY = new BigDecimal(0); 			CMS_QTY = (BigDecimal)row.get("CMS_QTY");
                    BigDecimal CMS_MONEY = new BigDecimal(0); 			CMS_MONEY = (BigDecimal)row.get("CMS_MONEY");
                    BigDecimal COUPLE_QTY = new BigDecimal(0); 			COUPLE_QTY = (BigDecimal)row.get("COUPLE_QTY");
                    BigDecimal COUPLE_MONEY = new BigDecimal(0); 	    COUPLE_MONEY = (BigDecimal)row.get("COUPLE_MONEY");
                    BigDecimal PARTNER_QTY = new BigDecimal(0); 	    PARTNER_QTY = (BigDecimal)row.get("PARTNER_QTY");
                    BigDecimal PARTNER_MONEY = new BigDecimal(0); 		PARTNER_MONEY = (BigDecimal)row.get("PARTNER_MONEY");

                    BigDecimal FREE_QTY = new BigDecimal(0); 			FREE_QTY = (BigDecimal)row.get("FREE_QTY");
                    BigDecimal FREE_MONEY = new BigDecimal(0); 			FREE_MONEY = (BigDecimal)row.get("FREE_MONEY");
                    BigDecimal TIME_QTY = new BigDecimal(0); 			TIME_QTY = (BigDecimal)row.get("TIME_QTY");
                    BigDecimal TIME_MONEY = new BigDecimal(0); 			TIME_MONEY = (BigDecimal)row.get("TIME_MONEY");
                    BigDecimal SPOT_QTY = new BigDecimal(0); 			SPOT_QTY = (BigDecimal)row.get("SPOT_QTY");
                    BigDecimal SPOT_MONEY = new BigDecimal(0); 			SPOT_MONEY = (BigDecimal)row.get("SPOT_MONEY");
                    BigDecimal MORE_QTY = new BigDecimal(0); 			MORE_QTY = (BigDecimal)row.get("MORE_QTY");
                    BigDecimal MORE_MONEY = new BigDecimal(0); 			MORE_MONEY = (BigDecimal)row.get("MORE_MONEY");
                    // 2015-06-27 주석
                    // BigDecimal S_CMS_QTY = new BigDecimal(0); 			S_CMS_QTY = (BigDecimal)row.get("S_CMS_QTY");
                    // BigDecimal S_CMS_MONEY = new BigDecimal(0); 		S_CMS_MONEY = (BigDecimal)row.get("S_CMS_MONEY");
                    BigDecimal C_CMS_QTY = new BigDecimal(0); 			C_CMS_QTY = (BigDecimal)row.get("C_CMS_QTY");
                    BigDecimal C_CMS_MONEY = new BigDecimal(0); 		C_CMS_MONEY = (BigDecimal)row.get("C_CMS_MONEY");
                    BigDecimal N_PLUS_1 = new BigDecimal(0); 			N_PLUS_1 = (BigDecimal)row.get("N_PLUS_1");
                    BigDecimal TOT_SALE_MONEY = new BigDecimal(0); 		TOT_SALE_MONEY = (BigDecimal)row.get("TOT_SALE_MONEY");
                    BigDecimal TAX_SALE_MONEY = new BigDecimal(0); 		TAX_SALE_MONEY = (BigDecimal)row.get("TAX_SALE_MONEY");
                    BigDecimal NON_SALE_MONEY = new BigDecimal(0); 		NON_SALE_MONEY = (BigDecimal)row.get("NON_SALE_MONEY");
                    BigDecimal SALE_VAT_MONEY = new BigDecimal(0); 		SALE_VAT_MONEY = (BigDecimal)row.get("SALE_VAT_MONEY");

                    BigDecimal PRO_CNT = new BigDecimal(0); 			PRO_CNT = (BigDecimal)row.get("PRO_CNT");
                    BigDecimal GOOD_MONEY = new BigDecimal(0); 			GOOD_MONEY = (BigDecimal)row.get("GOOD_MONEY");
                    BigDecimal GOOD_DISCOUNT = new BigDecimal(0); 		GOOD_DISCOUNT = (BigDecimal)row.get("GOOD_DISCOUNT");
                    BigDecimal ORDER_DELI_MONEY = new BigDecimal(0); 	ORDER_DELI_MONEY = (BigDecimal)row.get("ORDER_DELI_MONEY");
                    BigDecimal TOT_DELIVERY_PRICE = new BigDecimal(0); 	TOT_DELIVERY_PRICE = (BigDecimal)row.get("TOT_DELIVERY_PRICE");
                    BigDecimal ORDER_DELI_PACKAGE = new BigDecimal(0); 	ORDER_DELI_PACKAGE = (BigDecimal)row.get("ORDER_DELI_PACKAGE");


                    BigDecimal DELI_DISCOUNT = new BigDecimal(0); 		DELI_DISCOUNT = (BigDecimal)row.get("DELI_DISCOUNT");
                    BigDecimal CART_DISCOUNT = new BigDecimal(0); 		CART_DISCOUNT = (BigDecimal)row.get("CART_DISCOUNT");
                    BigDecimal COMPENSATION_CART_DISCOUNT = new BigDecimal(0); 		COMPENSATION_CART_DISCOUNT = (BigDecimal)row.get("COMPENSATION_CART_DISCOUNT");


                    BigDecimal ORDER_POINT_MONEY = new BigDecimal(0);    	ORDER_POINT_MONEY = (BigDecimal)row.get("ORDER_POINT_MONEY");
                    BigDecimal ORDER_GIFT_CARD_MONEY = new BigDecimal(0); 	ORDER_GIFT_CARD_MONEY = (BigDecimal)row.get("ORDER_GIFT_CARD_MONEY");
                    BigDecimal ORDER_MGIFT_CARD_MONEY = new BigDecimal(0); 	ORDER_MGIFT_CARD_MONEY = (BigDecimal)row.get("ORDER_MGIFT_CARD_MONEY");
                    BigDecimal ORDER_MILEAGE_MONEY = new BigDecimal(0); 	ORDER_MILEAGE_MONEY = (BigDecimal)row.get("ORDER_MILEAGE_MONEY");
                    BigDecimal TOT_PAY_MONEY = new BigDecimal(0); 			TOT_PAY_MONEY = (BigDecimal)row.get("TOT_PAY_MONEY");

                    String RESERVE_DATE = (String)row.get("RESERVE_DATE");
                    discount_price = QTY.intValue() * DISCOUNT_PRICE.intValue();

                    // 총 주문수량이 0인것은 스킵 시킨다.
                    if ( QTY.intValue() > 0 ) {
                        sOutput =  sOutput + product_name +" "+ row.get("PRO_GAGE")+ "\n";

                        // 한글자 4를 기준으로 20-4, 20-8
                        int s=12;
                        if ( product_id.length() == 9)  {
                            s = 12;
                        }
                        if ( product_id.length() == 10) {
                            s = 10;
                        }

                        sOutput =  sOutput  + StringUtil.getRpadB(10,product_id)
                                + StringUtil.getLpadB(s,StringUtil.addComma(SALE_PRICE.intValue()+DISCOUNT_PRICE.intValue()))
                                + StringUtil.getLpadB(7,StringUtil.addComma(QTY.intValue()))
                                + StringUtil.getLpadB(12,StringUtil.addComma(SALE_MONEY.intValue()+discount_price))+ "\n";
                        if (DISCOUNT_PRICE.intValue() > 0) {
                            tot_discount_price += discount_price;
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[할인금액]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(discount_price*-1))+ "\n";
                        }

                        if (CMS_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[MP쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(CMS_MONEY.intValue()*-1))+ "\n";
                        }

                        if (COUPLE_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[커플쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(COUPLE_MONEY.intValue()*-1))+ "\n";
                        }

                        if (PARTNER_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[연계쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(PARTNER_MONEY.intValue()*-1))+ "\n";
                        }

                        if (FREE_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[무료쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(FREE_MONEY.intValue()*-1))+ "\n";
                        }

                        if (TIME_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[타임세일]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(TIME_MONEY.intValue()*-1))+ "\n";
                        }

                        // 금액으로 변경..
                        if (SPOT_MONEY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[상품쿠폰1개]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(11,StringUtil.addComma(SPOT_MONEY.intValue()*-1))+ "\n";
                        }

                        if (RESERVE_DATE != null && !RESERVE_DATE.equals("")) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[예약배송일자]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(11,RESERVE_DATE)+ "\n";
                        }

                        if (MORE_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[다다익선]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(MORE_MONEY.intValue()*-1))+ "\n";
                        }

                        // 2015-06-27 주석
	                /*
	                if (S_CMS_QTY.intValue() != 0) {
	              	  sOutput =  sOutput + StringUtil.getRpadB(10,"")
	              			  + StringUtil.getLpadB(12,"[DM쿠폰]")
	              			  + StringUtil.getLpadB(7,"")
	              			  + StringUtil.getLpadB(12,StringUtil.addComma(S_CMS_MONEY.intValue()*-1))+ "\n";
	                }
	                */

                        if (C_CMS_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[카드쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(C_CMS_MONEY.intValue()*-1))+ "\n";
                        }

                        if (row.get("EXTRA_DESC") != null) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12, "["+ row.get("EXTRA_DESC") + "]")
                                    + StringUtil.getLpadB(7,"")+ "\n";
                        }

	                /*
	                if (N_PLUS_1.intValue() != 0) {
	                    sOutput =  sOutput + StringUtil.getRpadB(10,"")
	                                        + StringUtil.getLpadB(12, "["+N_PLUS_1.intValue() + " + 1]")
	                                        + StringUtil.getLpadB(7,"")+ "\n";
	                }
	                */
                    }

                    if(i==0) {
                        tot_sale_money = TOT_SALE_MONEY.intValue();
                        tax_sale_money = TAX_SALE_MONEY.intValue();
                        non_sale_money = NON_SALE_MONEY.intValue();
                        sale_vat_money = SALE_VAT_MONEY.intValue();
                        pro_cnt = PRO_CNT.intValue();
                        good_money  			= GOOD_MONEY.intValue();
                        good_discount  			= GOOD_DISCOUNT.intValue();
                        order_deli_money  		= ORDER_DELI_MONEY.intValue();
                        tot_delivery_price 		= TOT_DELIVERY_PRICE.intValue();
                        order_deli_package 		= ORDER_DELI_PACKAGE.intValue();
                        deli_discount  			= DELI_DISCOUNT.intValue();
                        cart_discount  			= CART_DISCOUNT.intValue();
                        compensation_cart_discount  = COMPENSATION_CART_DISCOUNT.intValue();
                        order_point_money  		= ORDER_POINT_MONEY.intValue();
                        order_gift_card_money  	= ORDER_GIFT_CARD_MONEY.intValue();
                        order_mgift_card_money  = ORDER_MGIFT_CARD_MONEY.intValue();
                        order_mileage_money  	= ORDER_MILEAGE_MONEY.intValue();
                        tot_pay_money  			= TOT_PAY_MONEY.intValue();


                        //------------------[ 품절대체 추가 구문 ]---------------------
                        int cartDiscount     = 0;

                        // 장바구니 쿠폰 금액을 절대값으로 변환 후 0이 아닐 경우 절대금액에서 -를 붙여준다.
                        diff_cart_discount = Math.abs(CART_DISCOUNT.intValue()) - Math.abs(COMPENSATION_CART_DISCOUNT.intValue());

                        if (diff_cart_discount != 0) {
                            new_cart_discount = diff_cart_discount;
                        }


                    }
                }
            }//end  if

            sOutput =  sOutput + "-----------------------------------------" + "\n";

            sOutput =  sOutput  + "상품금액     : "+  StringUtil.getLpadB(11,StringUtil.addComma(good_money+tot_discount_price)) + "\n";
            sOutput =  sOutput  + "할인금액     : "+  StringUtil.getLpadB(11,StringUtil.addComma(tot_discount_price* (-1))) + "\n";
            sOutput =  sOutput  + "상품쿠폰     : "+  StringUtil.getLpadB(11,StringUtil.addComma(good_discount* (-1))) + "\n";
            sOutput =  sOutput  + "배송료       : "+   StringUtil.getLpadB(11,StringUtil.addComma(order_deli_money)) + "\n";
            sOutput =  sOutput  + "개별배송료   : "+ StringUtil.getLpadB(11,StringUtil.addComma(tot_delivery_price)) + "\n";
            //sOutput =  sOutput  + "포장비       : "+ StringUtil.getLpadB(11,StringUtil.addComma(order_deli_package)) + "\n";
            sOutput =  sOutput  + "배송비쿠폰   : "+  StringUtil.getLpadB(11,StringUtil.addComma(deli_discount * (-1)))+ "\n";
            sOutput =  sOutput  + "장바구니쿠폰 : "+ StringUtil.getLpadB(11,StringUtil.addComma(new_cart_discount* (-1)))+ "\n";
            sOutput =  sOutput  + "대체쿠폰     : "+ StringUtil.getLpadB(11,StringUtil.addComma(compensation_cart_discount))+ "\n";
            if (header.get("POINT_DISPLAY_YN").equals("Y")){
                sOutput =  sOutput  + "메가포인트   : "+ StringUtil.getLpadB(11,StringUtil.addComma(order_point_money*(-1)))+ "\n";
            }
            sOutput =  sOutput  + "상품권전환금 : "+ StringUtil.getLpadB(11,StringUtil.addComma(order_gift_card_money*(-1)))+ "\n";
            sOutput =  sOutput  + "모바일상품권전환금 : "+ StringUtil.getLpadB(11,StringUtil.addComma(order_mgift_card_money*(-1)))+ "\n";
            //sOutput =  sOutput  + "복지마일리지 : "+ StringUtil.getLpadB(11,StringUtil.addComma(order_mileage_money*(-1)))+ "\n";

            sOutput =  sOutput + "-----------------------------------------" + "\n";
            sOutput =  sOutput + "합계         : "+ StringUtil.getLpadB(11,StringUtil.addComma(tot_pay_money)) + "\n";
            sOutput =  sOutput + "-----------------------------------------" + "\n";
            BigDecimal CARD_CANCEL_MONEY = new BigDecimal(0); 		CARD_CANCEL_MONEY = (BigDecimal)header.get("CARD_CANCEL_MONEY");
            BigDecimal ORDER_TOT_MONEY = new BigDecimal(0); 		ORDER_TOT_MONEY = (BigDecimal)header.get("ORDER_TOT_MONEY");
            BigDecimal QMONEY = new BigDecimal(0); 					QMONEY = (BigDecimal)header.get("QMONEY");

            if ("카드".equals(header.get("PAYMENT_TYPE"))) {
                sOutput =  sOutput  + "카드사명  : " + header.get("CARD_NAME")+ "\n";
                sOutput =  sOutput  + "결제금액  : "+ StringUtil.addComma(ORDER_TOT_MONEY.intValue())+ "\n";
                if(CARD_CANCEL_MONEY.intValue() >0) {
                    sOutput =  sOutput  + "카드취소  : "+ StringUtil.addComma(CARD_CANCEL_MONEY.intValue() *-1)+ "\n";
                }
                sOutput =  sOutput  + "카드번호  : "+ header.get("CARD_NUMBER")+ "\n";
                sOutput =  sOutput  + "일시불    : "+ header.get("INSTALL_TERM")+ "\n";
                sOutput =  sOutput  + "승인번호  : "+ header.get("CARD_RESULT")+ "\n";
            } else if ("실시간계좌이체".equals(header.get("PAYMENT_TYPE"))) {
                sOutput =  sOutput  + StringUtil.nvl((String)header.get("CARD_NAME"),"계좌이체 ")+ " : " +StringUtil.addComma(ORDER_TOT_MONEY.doubleValue())+ "\n";
                sOutput =  sOutput  + "계좌번호  : "+ header.get("CARD_NUMBER")+ "\n";

                sOutput =  sOutput  + "일시불    : "+ header.get("INSTALL_TERM")+ "\n";
                sOutput =  sOutput  + "승인번호  : "+ header.get("CARD_RESULT")+ "\n";
            } else {
                sOutput =  sOutput  + "현금(후불제) □현금 □카드 : "+ StringUtil.addComma(tot_pay_money) + "\n";
                sOutput =  sOutput  + "카드번호  : "+  "    - **** - **** -    "+ "\n";
                sOutput =  sOutput  + "결제방법  : "+ "[일시/  개월할부]"+ "\n";
                sOutput =  sOutput  + "승인번호  : "+ "\n";
            }

            // 2017-07-28
            if (header.get("POINT_DISPLAY_YN").equals("Y")){
                sOutput =  sOutput  + "메가포인트: "+ StringUtil.addComma(QMONEY.intValue())+ "\n";
            }

            /* 현금영수증  start */
            String CASH_RECEIPT_RESULT_DESC = StringUtil.nvl(header.get("CASH_RECEIPT_RESULT_DESC"));
            String[] arrCASH_RECEIPT_RESULT_DESC = StringUtil.split(CASH_RECEIPT_RESULT_DESC,"_");

            System.out.println("::=> arrCASH_RECEIPT_RESULT_DES : "+arrCASH_RECEIPT_RESULT_DESC.length);

            if(arrCASH_RECEIPT_RESULT_DESC.length>2) {
                sOutput =  sOutput + "\n---------------<현금영수증>---------------" + "\n";
                sOutput =  sOutput + "금     액  : "+ StringUtil.getLpadB(11,StringUtil.addComma(arrCASH_RECEIPT_RESULT_DESC[0])) + "\n";
                sOutput =  sOutput + "발행 정보  : "+ StringUtil.getLpadB(11,arrCASH_RECEIPT_RESULT_DESC[1]) + "\n";
                sOutput =  sOutput + "승인 번호  : "+ StringUtil.getLpadB(11,arrCASH_RECEIPT_RESULT_DESC[2]) + "\n";
                sOutput =  sOutput + "현금영수증 문의 ☎126 \n";
                sOutput =  sOutput + "사용자 등록 http://hometax.go.kr\n";
            }else {
                System.out.println("현금영수증 발행 정보가 없습니다.");
            }
            /* 현금영수증  end */

            sOutput =  sOutput + "-------------<판매영수증>----------------" ;
            if(param.get("system").equals("res")){
                sOutput =  sOutput  + "\n";
            }
            sOutput =  sOutput  +info.get("COM_NAME") + " : "+ info.get("COM_NO") + " 대표:"+ info.get("COM_PRESIDENT") + "\n";

            sOutput =  sOutput  + "합  계:"+ StringUtil.getLpadB(11,StringUtil.addComma(tot_sale_money)) + "원 " +   " 과세품:"+ StringUtil.getLpadB(11,StringUtil.addComma(tax_sale_money)) +"원"+ "\n";
            sOutput =  sOutput  + "면세품:"+ StringUtil.getLpadB(11,StringUtil.addComma(non_sale_money)) + "원 " +   " 부가세:"+ StringUtil.getLpadB(11,StringUtil.addComma(sale_vat_money)) + "원"+ "\n\n";

            sOutput =  sOutput  + "거래번호:"+  header.get("ORDER_SEQ") +" 총수량 :"+ pro_cnt +" 판매원:메가마트"+ "\n";
            sOutput =  sOutput  + "피킹직원:"+  header.get("PICKER_NAME") + "\n";
            sOutput =  sOutput  + "배송기사:"+  header.get("DELVER_NAME") + "\n";
            sOutput =  sOutput  + "\n";

            sOutput =  sOutput  + "상품코드 끝에＊ : 비과세 상품"  + "\n";
            //sOutput =  sOutput  + "상품코드 끝에▣ : 냉장/냉동 상품"  + "\n";
            sOutput =  sOutput  + "상품명 앞에  ★ : 개별배송 상품"+ "\n\n";
            /*
            sOutput =  sOutput  + "○교환및환불기준: 소비자피해보상규정준수 "+ "\n";
            sOutput =  sOutput  + "○배송후 고객변심으로 인한 반품은 "+ "\n";
            sOutput =  sOutput  + "  별도의 배송비 2,000원이 청구됩니다. "+ "\n"; */
            sOutput =  sOutput  + "▶교환및환불기준: 소비자피해보상규정준수"+ "\n";
            sOutput =  sOutput  + "  7일이내 신청 가능 "+ "\n";
            sOutput =  sOutput  + "  - 농산/수산/축산물/일배 상품은"+ "\n";
            sOutput =  sOutput  + "    당일만 교환/환불 가능"       + "\n";
            sOutput =  sOutput  + "  모든 상품도 개봉하시면 교환/환불 불가"+ "\n";
            sOutput =  sOutput  + "▶배송후 고객변심으로 인한 교환/환불은"+ "\n";
            sOutput =  sOutput  + "  배송비 3,000원이 청구됩니다."+ "\n";

            BigDecimal ATM = new BigDecimal(0);
            if(!param.get("PROMOTION_SUB_DESC").equals("")){
                ATM = (BigDecimal)param.get("AMT");
                sOutput =  sOutput + "\n[행사 내용]\n";
                //sOutput =  sOutput + param.get("PROMOTION_SUB_DESC") +"    "+ StringUtil.getLpadB(11,StringUtil.addComma(ATM.intValue()))+ "원 " + "\n\n";
                sOutput =  sOutput + param.get("PROMOTION_SUB_DESC") + "\n\n";
            }

            if(!param.get("EVENT_GIFT_RECEIPT").equals("0")){
                sOutput =  sOutput + "-----------------------------------------" + "\n";
                sOutput =  sOutput +param.get("EVENT_GIFT_RECEIPT") + "\n";
            }

            if(param.get("system").equals("oms")){
                sOutput =  sOutput  +  info.get("DOWN_AD") + "\n";

                sOutput =  sOutput  + ESC + "a" + (char)1;

//          sOutput +=GS + "!" + (char)32; //font size
//          sOutput +=GS + "!" + (char)0;

                // sOutput = sOutput  + GS + "h" + (char)32;  // 대략 2cm높이
                sOutput = sOutput  + GS + "h" + (char)64;    // 대략 4cm높이

                sOutput =  sOutput  + GS + "k" + (char)73 + (char)12+ (char)123+ (char)66+ (char)123+ (char)67;
//          		  + (char)barcord.charAt(0)+ (char)barcord.charAt(1)+ (char)barcord.charAt(2)+ (char)barcord.charAt(3)+ (char)barcord.charAt(4)+ (char)barcord.charAt(5)
//          		  + (char)barcord.charAt(6)+ (char)barcord.charAt(7)+ (char)barcord.charAt(8)+ (char)barcord.charAt(9)+ (char)barcord.charAt(10);
//          		  + (char)barcord.charAt(11)+ (char)barcord.charAt(12)+ (char)barcord.charAt(13)+ (char)barcord.charAt(14)+ (char)barcord.charAt(15)
//          		  + (char)barcord.charAt(16)+ (char)barcord.charAt(17) + (char)barcord.charAt(18)+ (char)barcord.charAt(19);
                for(int b=0; b<barcord.length(); b++) {
                    if(b%2==0) {
                        sOutput =  sOutput  + (char)(Integer.parseInt(barcord.substring(b, (b+2))));
                    }
                }
                sOutput =  sOutput  + LF;
                sOutput =  sOutput  + barcord;

            }else if(param.get("system").equals("res")){
                sOutput =  sOutput  + "\n";
                sOutput =  sOutput  + "바코드 No :" + barcord ;
            }

            System.out.println(sOutput);

        }catch(Exception ex){
            throw ex;
        }

        return sOutput;
    }


    public static String  printSend(HashMap param, HashMap header, HashMap info, HashMap entTotMoney, HashMap entOrgMoney, List<Map> vecRetDetail, List<Map> vecAddDetail, List<Map> vecDivDetail) throws Exception {

        String sOutput = null;

        try{
            if(param.get("system").equals("res")){
                sOutput = "";
            }
            if(param.get("system").equals("oms")){
                sOutput = "";
                sOutput +=ESC + "a" + (char)1; //가운데 정렬
                sOutput +=ESC + "M" + (char)0; //font A
                sOutput +=ESC + "E" + (char)1; //강조
                sOutput +=GS + "!" + (char)32; //font size
                if(!StringUtil.nvl(param.get("ADD_ORDER_ORG_ORDER_DESC")).equals("")){
                    sOutput +="추가주문" + LF ; //줄바꿈
                }
                sOutput +="MEGAMART" + LF ;    //줄바꿈
                sOutput +=GS + "!" + (char)0;
                //sOutput +="(메가마트인터넷쇼핑몰)" + LF ; //줄바꿈
                sOutput +="(메가마트몰 "+ info.get("COM_NAME")+ ")" + LF; //줄바꿈
                sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
                sOutput +=ESC + "E" + (char)0;

                sOutput = sOutput + info.get("ONLINE_BUSINESS_CODE")+ "\n";
                sOutput = sOutput + info.get("COM_ADDR1")+ "\n";
                sOutput = sOutput + " 문의전화:" +info.get("ONLINE_PHONE_NUMBER") + "\n\n";
                //sOutput = sOutput + info.get("COM_ADDR2")+ "\n";  //사용안함.
            }

            String product_id = null;
            String product_name = null;

            int ll_tot_return_money = 0;

            if(vecRetDetail.size()>0) {
                sOutput =  sOutput +""+ "\n";
                sOutput =  sOutput +""+ "◆품절상품" + "\n";
                sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
                sOutput =  sOutput +""+ "상품명           단가     수량      금액 " + "\n";
                sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

                int discount_price = 0;
                int tot_discount_price = 0;
                if(vecRetDetail.size() > 0){
                    for(int i=0 ; i< vecRetDetail.size() ; i++) {
                        HashMap row = (HashMap)vecRetDetail.get(i);
                        BigDecimal QTY 				= new BigDecimal(0); QTY = (BigDecimal)row.get("QTY");
                        BigDecimal DISCOUNT_PRICE 	= new BigDecimal(0); DISCOUNT_PRICE = (BigDecimal)row.get("DISCOUNT_PRICE");
                        BigDecimal SALE_PRICE 		= new BigDecimal(0); SALE_PRICE = (BigDecimal)row.get("SALE_PRICE");
                        BigDecimal SALE_MONEY 		= new BigDecimal(0); SALE_MONEY = (BigDecimal)row.get("SALE_MONEY");
                        BigDecimal CMS_QTY 			= new BigDecimal(0); CMS_QTY = (BigDecimal)row.get("CMS_QTY");
                        BigDecimal CMS_PRICE 		= new BigDecimal(0); CMS_PRICE = (BigDecimal)row.get("CMS_PRICE");
                        BigDecimal FREE_QTY 		= new BigDecimal(0); FREE_QTY = (BigDecimal)row.get("FREE_QTY");
                        BigDecimal FREE_PRICE 		= new BigDecimal(0); FREE_PRICE = (BigDecimal)row.get("FREE_PRICE");
                        BigDecimal TIME_QTY 		= new BigDecimal(0); TIME_QTY = (BigDecimal)row.get("TIME_QTY");
                        BigDecimal TIME_PRICE 		= new BigDecimal(0); TIME_PRICE = (BigDecimal)row.get("TIME_PRICE");
                        BigDecimal SPOT_QTY 		= new BigDecimal(0); SPOT_QTY = (BigDecimal)row.get("SPOT_QTY");
                        BigDecimal SPOT_PRICE 		= new BigDecimal(0); SPOT_PRICE = (BigDecimal)row.get("SPOT_PRICE");
                        BigDecimal MORE_QTY 		= new BigDecimal(0); MORE_QTY = (BigDecimal)row.get("MORE_QTY");
                        BigDecimal MORE_AMOUNT 		= new BigDecimal(0); MORE_AMOUNT = (BigDecimal)row.get("MORE_AMOUNT");
                        BigDecimal S_CMS_QTY 		= new BigDecimal(0); S_CMS_QTY = (BigDecimal)row.get("S_CMS_QTY");
                        BigDecimal S_CMS_PRICE 		= new BigDecimal(0); S_CMS_PRICE = (BigDecimal)row.get("S_CMS_PRICE");
                        BigDecimal C_CMS_QTY 		= new BigDecimal(0); C_CMS_QTY = (BigDecimal)row.get("C_CMS_QTY");
                        BigDecimal C_CMS_PRICE 		= new BigDecimal(0); C_CMS_PRICE = (BigDecimal)row.get("C_CMS_PRICE");
                        BigDecimal TOT_RETURN_MONEY = new BigDecimal(0); TOT_RETURN_MONEY = (BigDecimal)row.get("TOT_RETURN_MONEY");
                        String RESERVE_DATE = (String)row.get("RESERVE_DATE");

                        if ("0".equals(row.get("PRO_TAX_FLAG"))) {// 면세 : 2 ==> 0
                            product_id = (String)row.get("PRODUCT_ID") + "＊" + StringUtil.nvl(row.get("PACK_METHOD_FLAG"));
                        }else{
                            product_id = (String)row.get("PRODUCT_ID") + StringUtil.nvl(row.get("PACK_METHOD_FLAG"));
                        }

                        if ("D".equals(row.get("DELI_AREA_PRODUCT")))     /** 직송상품일 경우에는 앞에 별표 표시 **/
                            product_name = '★' + StringUtil.nvl((String)row.get("N_PLUS_1")) + row.get("PRO_NAME");
                        else
                            product_name  = StringUtil.nvl((String)row.get("N_PLUS_1")) + row.get("PRO_NAME");

                        discount_price = QTY.intValue()*DISCOUNT_PRICE.intValue();

                        sOutput =  sOutput + product_name +" "+ row.get("PRO_GAGE")+ "\n";

                        // 한글자 4를 기준으로 20-4, 20-8
                        int s=12;
                        if ( product_id.length() == 9)  {
                            s = 12;
                        }
                        if ( product_id.length() == 10) {
                            s = 10;
                        }

                        sOutput =  sOutput  + StringUtil.getRpadB(10,product_id)
                                + StringUtil.getLpadB(s,StringUtil.addComma(Math.abs(SALE_PRICE.intValue() - DISCOUNT_PRICE.intValue())*-1))
                                + StringUtil.getLpadB(7,StringUtil.addComma(Math.abs(QTY.intValue())*-1))
                                + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(SALE_MONEY.intValue() + discount_price)*-1))+ "\n";

                        if (DISCOUNT_PRICE.intValue() > 0) {
                            tot_discount_price += discount_price;
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[할인금액]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(discount_price)))+ "\n";
                        }

                        if (CMS_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[MP쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(CMS_PRICE.intValue()*CMS_QTY.intValue())))+ "\n";
                        }

                        if (FREE_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[무료쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(FREE_PRICE.intValue()*FREE_QTY.intValue())))+ "\n";
                        }

                        if (TIME_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[타임세일]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(TIME_PRICE.intValue()*TIME_QTY.intValue())))+ "\n";
                        }

                        if (SPOT_PRICE.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[상품쿠폰1개]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(11,StringUtil.addComma(Math.abs(SPOT_PRICE.intValue())))+ "\n";
                        }

                        if (RESERVE_DATE != null && !RESERVE_DATE.equals("")) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[예약배송일자]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(11,RESERVE_DATE)+ "\n";
                        }

                        if (MORE_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[다다익선]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(MORE_AMOUNT.intValue())))+ "\n";
                        }


                        if (S_CMS_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[DM쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(S_CMS_PRICE.intValue()*S_CMS_QTY.intValue())))+ "\n";
                        }

                        if (C_CMS_QTY.intValue() != 0) {
                            sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                    + StringUtil.getLpadB(12,"[카드쿠폰]")
                                    + StringUtil.getLpadB(7,"")
                                    + StringUtil.getLpadB(12,StringUtil.addComma(Math.abs(C_CMS_PRICE.intValue()*C_CMS_QTY.intValue())))+ "\n";
                        }

                        ll_tot_return_money  = TOT_RETURN_MONEY.intValue();
                    }
                }
            }

            int ll_tot_add_money =0;

            int discount_price = 0;
            int tot_discount_price = 0;

            if(vecAddDetail.size()>0) {
                sOutput =  sOutput +""+ "\n";
                sOutput =  sOutput +""+ "◆대체상품" + "\n";
                sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
                sOutput =  sOutput +""+ "상품명           단가     수량      금액 " + "\n";
                sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

                for(int i=0 ; i< vecAddDetail.size() ; i++) {
                    HashMap row = (HashMap)vecAddDetail.get(i);
                    BigDecimal QTY 				= new BigDecimal(0); QTY = (BigDecimal)row.get("QTY");
                    BigDecimal DISCOUNT_PRICE 	= new BigDecimal(0); DISCOUNT_PRICE = (BigDecimal)row.get("DISCOUNT_PRICE");
                    BigDecimal SALE_PRICE 		= new BigDecimal(0); SALE_PRICE = (BigDecimal)row.get("SALE_PRICE");
                    BigDecimal SALE_MONEY 		= new BigDecimal(0); SALE_MONEY = (BigDecimal)row.get("SALE_MONEY");
                    BigDecimal CMS_QTY 			= new BigDecimal(0); CMS_QTY = (BigDecimal)row.get("CMS_QTY");
                    BigDecimal CMS_PRICE 		= new BigDecimal(0); CMS_PRICE = (BigDecimal)row.get("CMS_PRICE");
                    BigDecimal FREE_QTY 		= new BigDecimal(0); FREE_QTY = (BigDecimal)row.get("FREE_QTY");
                    BigDecimal FREE_PRICE 		= new BigDecimal(0); FREE_PRICE = (BigDecimal)row.get("FREE_PRICE");
                    BigDecimal TIME_QTY 		= new BigDecimal(0); TIME_QTY = (BigDecimal)row.get("TIME_QTY");
                    BigDecimal TIME_PRICE 		= new BigDecimal(0); TIME_PRICE = (BigDecimal)row.get("TIME_PRICE");
                    BigDecimal SPOT_QTY 		= new BigDecimal(0); SPOT_QTY = (BigDecimal)row.get("SPOT_QTY");
                    BigDecimal SPOT_PRICE 		= new BigDecimal(0); SPOT_PRICE = (BigDecimal)row.get("SPOT_PRICE");
                    BigDecimal MORE_QTY 		= new BigDecimal(0); MORE_QTY = (BigDecimal)row.get("MORE_QTY");
                    BigDecimal MORE_AMOUNT 		= new BigDecimal(0); MORE_AMOUNT = (BigDecimal)row.get("MORE_AMOUNT");
                    BigDecimal S_CMS_QTY 		= new BigDecimal(0); S_CMS_QTY = (BigDecimal)row.get("S_CMS_QTY");
                    BigDecimal S_CMS_PRICE 		= new BigDecimal(0); S_CMS_PRICE = (BigDecimal)row.get("S_CMS_PRICE");
                    BigDecimal C_CMS_QTY 		= new BigDecimal(0); C_CMS_QTY = (BigDecimal)row.get("C_CMS_QTY");
                    BigDecimal C_CMS_PRICE 		= new BigDecimal(0); C_CMS_PRICE = (BigDecimal)row.get("C_CMS_PRICE");
                    BigDecimal TOT_ADD_MONEY 	= new BigDecimal(0); TOT_ADD_MONEY = (BigDecimal)row.get("TOT_ADD_MONEY");
                    String RESERVE_DATE         = (String)row.get("RESERVE_DATE");

                    if ("0".equals(row.get("PRO_TAX_FLAG"))) {          // 면세 : 2 ==> 0
                        product_id = (String) row.get("PRODUCT_ID") + "＊" + StringUtil.nvl(row.get("PACK_METHOD_FLAG"));
                    }else{
                        product_id = (String) row.get("PRODUCT_ID") + StringUtil.nvl(row.get("PACK_METHOD_FLAG"));
                    }


                    if ("D".equals(row.get("DELI_AREA_PRODUCT")))     /** 직송상품일 경우에는 앞에 별표 표시 **/
                        product_name = '★' + StringUtil.nvl((String)row.get("N_PLUS_1")) + row.get("PRO_NAME");
                    else
                        product_name  = StringUtil.nvl((String)row.get("N_PLUS_1")) + row.get("PRO_NAME");

                    discount_price = QTY.intValue()*DISCOUNT_PRICE.intValue();

                    sOutput =  sOutput + product_name +" "+ row.get("PRO_GAGE")+ "\n";

                    //
                    int s=12;
                    if ( product_id.length() == 9)  {
                        s = 12;
                    }
                    if ( product_id.length() == 10) {
                        s = 10;
                    }

                    sOutput =  sOutput  + StringUtil.getRpadB(10,product_id)
                            + StringUtil.getLpadB(s,StringUtil.addComma(SALE_PRICE.intValue()+DISCOUNT_PRICE.intValue()))
                            + StringUtil.getLpadB(7,StringUtil.addComma(QTY.intValue()))
                            + StringUtil.getLpadB(12,StringUtil.addComma(SALE_MONEY.intValue()+discount_price))+ "\n";

                    if (DISCOUNT_PRICE.intValue() > 0) {
                        tot_discount_price += discount_price;
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[할인금액]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(discount_price*-1))+ "\n";
                    }

                    if (CMS_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[MP쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(CMS_PRICE.intValue()*CMS_QTY.intValue()*-1))+ "\n";
                    }

                    if (FREE_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[무료쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(FREE_PRICE.intValue()*FREE_QTY.intValue()*-1))+ "\n";
                    }

                    if (TIME_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[타임세일]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(TIME_PRICE.intValue()*TIME_QTY.intValue()*-1))+ "\n";
                    }

                    if (SPOT_PRICE.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[상품쿠폰1개]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(11,StringUtil.addComma(Math.abs(SPOT_PRICE.intValue())*-1))+ "\n";
                    }

                    if (RESERVE_DATE != null && !RESERVE_DATE.equals("")) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[예약배송일자]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(11,RESERVE_DATE)+ "\n";
                    }

                    if (MORE_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[다다익선]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(MORE_AMOUNT.intValue()*-1))+ "\n";
                    }


                    if (S_CMS_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[DM쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(S_CMS_PRICE.intValue()*S_CMS_QTY.intValue()*-1))+ "\n";
                    }


                    if (C_CMS_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[카드쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(C_CMS_PRICE.intValue()*C_CMS_QTY.intValue()*-1))+ "\n";
                    }

                    ll_tot_add_money  = TOT_ADD_MONEY.intValue();
                }
            }

            sOutput =  sOutput + "-----------------------------------------" + "\n";

            System.out.println(header);

            BigDecimal TOT_ORG_MONEY 		= new BigDecimal(0); TOT_ORG_MONEY = (BigDecimal)entOrgMoney.get("TOT_ORG_MONEY");

            BigDecimal ORDER_DELI_MONEY 	= new BigDecimal(0); ORDER_DELI_MONEY = (BigDecimal) header.get("ORDER_DELI_MONEY");
            BigDecimal TOT_DELIVERY_PRICE 	= new BigDecimal(0); TOT_DELIVERY_PRICE = (BigDecimal)header.get("TOT_DELIVERY_PRICE");
            BigDecimal ORDER_DELI_PACKAGE 	= new BigDecimal(0); ORDER_DELI_PACKAGE = (BigDecimal)header.get("ORDER_DELI_PACKAGE");

            BigDecimal CART_DISCOUNT 		= new BigDecimal(0); CART_DISCOUNT = (BigDecimal)header.get("CART_DISCOUNT");
            BigDecimal COMPENSATION_CART_DISCOUNT = new BigDecimal(0); COMPENSATION_CART_DISCOUNT = (BigDecimal)header.get("COMPENSATION_CART_DISCOUNT");
            BigDecimal DELI_DISCOUNT 		    = new BigDecimal(0); DELI_DISCOUNT = (BigDecimal)header.get("DELI_DISCOUNT");

            BigDecimal ORDER_POINT_MONEY 		= new BigDecimal(0); ORDER_POINT_MONEY = (BigDecimal)header.get("ORDER_POINT_MONEY");
            BigDecimal ORDER_GIFT_CARD_MONEY 	= new BigDecimal(0); ORDER_GIFT_CARD_MONEY = (BigDecimal)header.get("ORDER_GIFT_CARD_MONEY");
            BigDecimal ORDER_MGIFT_CARD_MONEY 	= new BigDecimal(0); ORDER_MGIFT_CARD_MONEY = (BigDecimal)header.get("ORDER_MGIFT_CARD_MONEY");
            BigDecimal ORDER_MILEAGE_MONEY 		= new BigDecimal(0); ORDER_MILEAGE_MONEY = (BigDecimal)header.get("ORDER_MILEAGE_MONEY");
            BigDecimal CARD_CANCEL_MONEY 		= new BigDecimal(0); CARD_CANCEL_MONEY = (BigDecimal)header.get("CARD_CANCEL_MONEY");

            BigDecimal CASH_MONEY 		 = new BigDecimal(0); CASH_MONEY = (BigDecimal)header.get("CASH_MONEY");
            BigDecimal GIFT_CARD_MONEY   = new BigDecimal(0); GIFT_CARD_MONEY = (BigDecimal)header.get("GIFT_CARD_MONEY");

            String sPaymentType = (String)header.get("PAYMENT_TYPE");
            String sOfflineOrderFlag = (String)header.get("OFFLINE_ORDER_FLAG");


            //------------------[ 품절대체 추가 구문 ]---------------------
            int diffCartDiscount = 0;
            int cartDiscount     = 0;

            // 장바구니 쿠폰 금액을 절대값으로 변환 후 0이 아닐 경우 절대금액에서 -를 붙여준다.
            diffCartDiscount = Math.abs(CART_DISCOUNT.intValue()) - Math.abs(COMPENSATION_CART_DISCOUNT.intValue());

            if (diffCartDiscount != 0) {
                cartDiscount = diffCartDiscount;
            }

            //------------------------------------------------------------

            sOutput =  sOutput +" " + "주문금액     : "+ StringUtil.getLpadB(11,StringUtil.addComma(TOT_ORG_MONEY.intValue())) + "\n";
            sOutput =  sOutput +" " + "품절금액     : "+ StringUtil.getLpadB(11,StringUtil.addComma(ll_tot_return_money)) + "\n";
            sOutput =  sOutput +" " + "대체금액     : "+ StringUtil.getLpadB(11,StringUtil.addComma(ll_tot_add_money)) +  "\n";
            sOutput =  sOutput +" " + "배송료       : "+ StringUtil.getLpadB(11,StringUtil.addComma(ORDER_DELI_MONEY.intValue())) + "\n";
            sOutput =  sOutput +" " + "개별배송료   : "+ StringUtil.getLpadB(11,StringUtil.addComma(TOT_DELIVERY_PRICE.intValue())) + "\n";
            //sOutput =  sOutput +" " + "포장비       : "+ StringUtil.getLpadB(11,StringUtil.addComma(ORDER_DELI_PACKAGE.intValue())) + "\n";
            sOutput =  sOutput +" " + "장바구니쿠폰 : "+ StringUtil.getLpadB(11,StringUtil.addComma(cartDiscount*-1)) + "\n";
            sOutput =  sOutput +" " + "대체쿠폰     : "+ StringUtil.getLpadB(11,StringUtil.addComma(COMPENSATION_CART_DISCOUNT.intValue())) + "\n";
            sOutput =  sOutput +" " + "배송비쿠폰   : "+ StringUtil.getLpadB(11,StringUtil.addComma(DELI_DISCOUNT.intValue())) + "\n";

            if (header.get("POINT_DISPLAY_YN").equals("Y")){
                sOutput =  sOutput +" " + "메가포인트   : "+ StringUtil.getLpadB(11,StringUtil.addComma(ORDER_POINT_MONEY.intValue())) + "\n";
            }
            sOutput =  sOutput +" " + "상품권전환금 : "+ StringUtil.getLpadB(11,StringUtil.addComma(ORDER_GIFT_CARD_MONEY.intValue())) + "\n";
            sOutput =  sOutput +" " + "모바일상품권전환금 : "+ StringUtil.getLpadB(11,StringUtil.addComma(ORDER_MGIFT_CARD_MONEY.intValue())) + "\n";
            //sOutput =  sOutput +" " + "복지마일리지 : "+ StringUtil.getLpadB(11,StringUtil.addComma(ORDER_MILEAGE_MONEY.intValue())) + "\n";
            if(CARD_CANCEL_MONEY.intValue() > 0) {
                sOutput =  sOutput +" " + "카드취소금액 : "+ StringUtil.getLpadB(11,StringUtil.addComma(CARD_CANCEL_MONEY.intValue())) + "\n";
            }

            //합계 계산
            BigDecimal ORDER_TOT_MONEY 		= new BigDecimal(0); ORDER_TOT_MONEY = (BigDecimal) header.get("ORDER_TOT_MONEY");

            int ll_total_money =TOT_ORG_MONEY.intValue() + ll_tot_return_money + ll_tot_add_money + ORDER_DELI_MONEY.intValue() + TOT_DELIVERY_PRICE.intValue() + ORDER_DELI_PACKAGE.intValue();
            ll_total_money += ORDER_POINT_MONEY.intValue()+ CART_DISCOUNT.intValue() + DELI_DISCOUNT.intValue() + ORDER_GIFT_CARD_MONEY.intValue() + ORDER_MGIFT_CARD_MONEY.intValue() + ORDER_MILEAGE_MONEY.intValue();
            ll_total_money += CARD_CANCEL_MONEY.intValue();

            if(sOfflineOrderFlag.equals("1") && sPaymentType.equals("현금")){
                sOutput =  sOutput +" "+ "-----------------------------------------" + "\n";
                sOutput =  sOutput +" " + "현    금     : "+  StringUtil.getLpadB(11,StringUtil.addComma(CASH_MONEY.intValue())) + "\n";
                sOutput =  sOutput +" " + "상 품 권     : "+  StringUtil.getLpadB(11,StringUtil.addComma(GIFT_CARD_MONEY.intValue())) + "\n";
            }

            sOutput =  sOutput +" "+ "-----------------------------------------" + "\n";
            sOutput =  sOutput +" " + "합 계        : "+  StringUtil.getLpadB(11,StringUtil.addComma(ll_total_money)) + "\n";
            sOutput =  sOutput +" "+ "-----------------------------------------" + "\n";
            sOutput =  sOutput +" " + "최종결제금액 : "+  StringUtil.getLpadB(11,StringUtil.addComma(ll_total_money - CARD_CANCEL_MONEY.intValue())) + "\n";
            sOutput =  sOutput +" "+ "-----------------------------------------" + "\n";

            if(sOfflineOrderFlag.equals("1") && sPaymentType.equals("현금")){
                ll_total_money = 0;
            }
            /**
             * 상품에 대한 과세, 면세, 수수료 표현
             */
              /*
              if(vecDivDetail.size()>0) {
            	  for( int hh=0; hh < vecDivDetail.size(); hh++ ){
            		  //VENDOR_CODE, SUM( DECODE( PRO_TAX_FLAG , 0, POINT_DIV_VAT_AMOUNT ) ) POINT_DIV_AMOUNT, SUM(DECODE( PRO_TAX_FLAG, 1, POINT_DIV_VAT_AMOUNT ) ) POINT_DIV_VAT_AMOUNT, SUM(POINT_DIV_TAX_AMOUNT) POINT_DIV_TAX_AMOUNT
            		  //sOutput =  sOutput +" "+ "-----------------------------------------" + "\n";

            		  HashMap rows = (HashMap)vecDivDetail.get(hh);

                      BigDecimal divSuppTotAmount 				= new BigDecimal(0); divSuppTotAmount = (BigDecimal)rows.get("DIV_SUPP_TOT_AMOUNT");
                      BigDecimal divSuppAmount 				= new BigDecimal(0); divSuppAmount = (BigDecimal)rows.get("DIV_SUPP_AMOUNT");
                      BigDecimal divSuppTaxAmount 				= new BigDecimal(0); divSuppTaxAmount = (BigDecimal)rows.get("DIV_SUPP_TAX_AMOUNT");
                      BigDecimal divVatAmount 				= new BigDecimal(0); divVatAmount = (BigDecimal)rows.get("DIV_VAT_AMOUNT");

            		  sOutput =  sOutput +" "+ "---------<  판  매  영  수  증 >---------" + "\n";
                      sOutput =  sOutput +  StringUtil.getLpadB(11,rows.get("VENDOR_INFO").toString()) + "\n";
                      sOutput =  sOutput +" " + "합  계       : "+  StringUtil.getLpadB(11,StringUtil.addComma(divSuppTotAmount.intValue())) + "\n";
                      sOutput =  sOutput +" " + "과세품       : "+  StringUtil.getLpadB(11,StringUtil.addComma(divSuppTaxAmount.intValue())) + "\n";
                      sOutput =  sOutput +" " + "면세품       : "+  StringUtil.getLpadB(11,StringUtil.addComma(divSuppAmount.intValue()))    + "\n";
                      sOutput =  sOutput +" " + "부과세       : "+  StringUtil.getLpadB(11,StringUtil.addComma(divVatAmount.intValue())) + "\n";
            	  }
              }
              */



            //거스름돈과 받을돈 계산
            int ll_rec_money = ORDER_TOT_MONEY.intValue() - ll_total_money;

            // 1- 컴멘트 나중에 삭제 해야 한다.
            // 주문금액이 추가 주문금액보다 큰 경우
            if (ll_rec_money > 0) {
                // 현금일 경우 총 주문금액을 받아와야 함.
                if ("현금".equals(header.get("PAYMENT_TYPE"))) {

                    // 총합계금액이 마이너스 인경우에는 무조건 거스름돈으로 출력한다.(메가포인트만 주문하는 경우 문제 발생)
                    if (ll_total_money <0) {
                        sOutput =  sOutput +" " + "받을 돈       : "+ StringUtil.getLpadB(11,StringUtil.addComma(0)) + "\n";
                        sOutput =  sOutput +" " + "거스름돈      : "+ StringUtil.getLpadB(11,StringUtil.addComma(ll_total_money)) + "\n";
                    } else {
                        sOutput =  sOutput +" " + "받을돈       : "+ StringUtil.getLpadB(11,StringUtil.addComma(ll_total_money)) + "\n";
                        sOutput =  sOutput +" " + "거스름돈     : "+ StringUtil.getLpadB(11,StringUtil.addComma(0)) + "\n";
                    }
                    // 카드결제 일경우 총 주문금액에서 원주문결제 금액을 차감 함.
                }  else {
                    sOutput =  sOutput +" " + "받을돈       : "+ StringUtil.getLpadB(11,StringUtil.addComma(0)) + "\n";
                    sOutput =  sOutput +" " + "거스름돈     : "+ StringUtil.getLpadB(11,StringUtil.addComma((ORDER_TOT_MONEY.intValue() - ll_total_money))) +"\n";
                }
                // 추가 주문금액이 큰 경우
            } else {

                // 현금일 경우 총 주문금액을 받아와야 함.
                if ("현금".equals(header.get("PAYMENT_TYPE"))) {
                    sOutput =  sOutput +" " + "받을돈       : "+ StringUtil.getLpadB(11,StringUtil.addComma(ll_total_money)) + "\n";
                    sOutput =  sOutput +" " + "거스름돈     : "+ StringUtil.getLpadB(11,StringUtil.addComma(0)) + "\n";
                    // 카드결제 일경우 총 주문금액에서 원주문결제 금액을 차감 함.
                } else{
                    sOutput =  sOutput +" " + "받을돈       : "+ StringUtil.getLpadB(11,StringUtil.addComma((ll_total_money-ORDER_TOT_MONEY.intValue())))+ "\n";
                    sOutput =  sOutput +" " + "거스름돈     : "+ StringUtil.getLpadB(11,StringUtil.addComma(0)) +"\n";
                }
            }

            sOutput =  sOutput +" " + "수 령 자     : _____________(인)" + "\n";
            // sOutput =  sOutput +" " + "배달 요청 사항" + "\n";
            // sOutput =  sOutput +" " + header.get("ORDER_MESSAGE") + "\n";
            sOutput =  sOutput +" " + "◇일부생식(농/수/축산물)의 경우 주문시 "+ "\n";
            sOutput =  sOutput +" " + "가격과 배달시 가격이 상이할 수 있습니다."+ "\n";
            sOutput =  sOutput +" " + "☞배달시 상품에 하자가 있을때는 현장에서"+ "\n";
            sOutput =  sOutput +" " + "즉시 반품이 가능합니다."+  "\n";

            System.out.println(sOutput);

        }catch(Exception ex){
            throw ex;
        }
        return sOutput;

    }

    /*
    public static String customerInfo(HashMap param, HashMap header,HashMap header2, List<Map> detail) throws Exception {
        String sOutput = null;

        try{

            sOutput = "";

            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            sOutput +=header.get("REGION_NAME") + " " + header.get("DELI_ORDER") + "차"+  LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)32; //font size
            sOutput +="MEGAMART" + LF ; //줄바꿈
            sOutput +=GS + "!" + (char)0;
            sOutput +="(고객인식표)" + LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;

            sOutput = sOutput + "[주문고객]"+ header.get("ORDER_NAME") + "\n";
            sOutput = sOutput + "[수령고객]"+ header.get("RECE_NAME")  + "\n";
            sOutput = sOutput + "[배송주소]"+ header.get("RECE_ADDR1") + "\n";
            sOutput = sOutput + header.get("RECE_ADDR2")+ "\n";
            //sOutput = sOutput + "[전화번호]"+ header.get("RECE_FULL_TEL")+ "\n";
            sOutput = sOutput + "[전화번호]"+ header.get("RECE_TEL")+ "("+header.get("RECE_HP") + ")\n";
            sOutput = sOutput + "[배달요청사항]" + "\n";
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)0; //font size
            sOutput = sOutput + header2.get("ORDER_MESSAGE") + "\n\n";

            String sProductNm      = "";
            String sProGage        = "";
            String sProductId      = "";
            String sProOptionValue = "";
            String sJmProductYn    = "";
               int discount_price  = 0;


            for(int i=0 ; i< detail.size() ; i++) {
                HashMap row = (HashMap)detail.get(i);

                sProductId      = (String)row.get("PRODUCT_ID");
                sProductNm      = (String)row.get("PRO_NAME");
                sProGage        = (String)row.get("PRO_GAGE");
                sProOptionValue = (String)row.get("PRO_OPTION_VALUE");
                sJmProductYn    = (String)row.get("JM_PRODUCT_YN");

                if (sProOptionValue != null && sProOptionValue.equals("")) {
                	sProOptionValue = "장만내용 없음";
                }

                BigDecimal QTY 				= new BigDecimal(0);  QTY = (BigDecimal)row.get("QTY");
                BigDecimal DISCOUNT_PRICE 	= new BigDecimal(0); DISCOUNT_PRICE = (BigDecimal)row.get("DISCOUNT_PRICE");
                BigDecimal SALE_MONEY 	    = new BigDecimal(0); DISCOUNT_PRICE = (BigDecimal)row.get("SALE_MONEY");
                BigDecimal SALE_PRICE 	    = new BigDecimal(0); DISCOUNT_PRICE = (BigDecimal)row.get("SALE_PRICE");

                discount_price = QTY.intValue() * DISCOUNT_PRICE.intValue();

                //  현재 : 1-과세, 0-면세
                if ("0".equals((String)row.get("PRO_TAX_FLAG"))) {
                	sProductId = sProductId + "*";
            	}

                // 장만상품일 경우
                if (sJmProductYn !=null && sJmProductYn.equals("Y")) {

                	 sOutput =  sOutput + ESC + "E" + (char)0;  //폰트 bold값을 초기화 해줌.
                     sOutput =  sOutput + ESC + "a" + (char)0;  //왼쪽 정렬
                     sOutput =  sOutput + GS  + "!" + (char)0;  //font size

                     sOutput =  sOutput  + sProductNm +" "+ sProGage + "\n";
                     sOutput =  sOutput  + StringUtil.getRpadB(10,sProductId)
                                         + StringUtil.getLpadB(12,StringUtil.addComma(SALE_PRICE.intValue()+DISCOUNT_PRICE.intValue()))
                                         + StringUtil.getLpadB(7,StringUtil.addComma(QTY.intValue()))
                                         + StringUtil.getLpadB(12,StringUtil.addComma(SALE_MONEY.intValue()+discount_price))+ "\n";

                     sOutput =  sOutput +"\n"+ "장만내역"  +  "\n";
                     sOutput =  sOutput + ESC + "E" + (char)1;  //강조
                     sOutput =  sOutput + GS + "!"  + (char)16; //font size
                     sOutput =  sOutput + sProOptionValue   + "\n";
                     sOutput =  sOutput + ESC + "E" + (char)0;
                     sOutput =  sOutput + ESC + "a" + (char)0;  //왼쪽 정렬
                     sOutput =  sOutput + GS + "!"  + (char)0;  //font size
                     sOutput =  sOutput +""+ "-----------------------------------------" + "\n\n";

                }

            }

            BigDecimal ATM = new BigDecimal(0);
            if(!param.get("PROMOTION_SUB_DESC").equals("")){
           	   ATM = (BigDecimal)param.get("AMT");
           	   sOutput =  sOutput + "\n[행사 집계]\n";
           	   sOutput =  sOutput + param.get("PROMOTION_SUB_DESC") +"    "+ StringUtil.getLpadB(11,StringUtil.addComma(ATM.intValue()))+ "원 " + "\n\n";
            }

            if(!param.get("EVENT_GIFT_RECEIPT").equals("0")){
               	sOutput =  sOutput + "-----------------------------------------" + "\n";
               	sOutput =  sOutput +param.get("EVENT_GIFT_RECEIPT") + "\n";
            }
            // 고객정보 추가 메시지 추가
            sOutput =  sOutput + "-----------------------------------------" + "\n";
            sOutput =  sOutput + "컨테이너 NO:          보냉가방 NO:       " + "\n\n";
            sOutput =  sOutput + "냉       장:          냉       동:       " + "\n\n";
            sOutput =  sOutput + "박 스 수 량:               쌀    :       " + "\n\n";
            sOutput =  sOutput + "화   장  지:               물    :       " + "\n\n";
            sOutput =  sOutput + "-----------------------------------------" + "\n";
        }catch(Exception ex){
           throw ex;
        }

        return sOutput;
    }
    */

    // 고객인식표
    public static String customerInfo_NEW(HashMap param, HashMap header,HashMap header2, List<Map> detail) throws Exception {
        String sOutput = null;

        try{

            sOutput = "";

            String orderCode = (String) header.get("ORDER_CODE");

            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            if(!StringUtil.nvl(param.get("ADD_ORDER_ORG_ORDER_DESC")).equals("")){
                sOutput +="추가주문" + LF ; //줄바꿈
            }
            sOutput +=header.get("REGION_NAME") + " " + header.get("DELI_ORDER") + "차"+header.get("ORDER_DELI_METHOD_DESC")+  LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)32; //font size
            sOutput +="MEGAMART" + LF ; //줄바꿈
            sOutput +=GS + "!" + (char)0;
            sOutput +="(고객인식표)" + LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;
            sOutput = sOutput + "[주문고객]"+ header.get("ORDER_NAME") +"("+ header.get("ORDER_ID_DESC")+")\n";
            sOutput = sOutput + "[수령고객]"+ header.get("RECE_NAME")  + "\n";
            sOutput = sOutput + "[배송주소]"+ header.get("RECE_ADDR1") + "\n";
            sOutput = sOutput + header.get("RECE_ADDR2")+ "\n";
            //sOutput = sOutput + "[전화번호]"+ header.get("RECE_FULL_TEL")+ "\n";
            sOutput = sOutput + "[전화번호]"+ header.get("RECE_TEL")+ "("+header.get("RECE_HP") + ")\n";
            sOutput = sOutput + "[배달요청사항]" + "\n";
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)0; //font size
            sOutput = sOutput + header2.get("ORDER_MESSAGE") + "\n\n";
            System.out.println("lavel_print_flag 00=>");
            String sProductNm      = "";
            String sProGage        = "";
            String sProductId      = "";
            String sProOptionValue = "";
            String sJmProductYn    = "";
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;
            if(!param.get("EVENT_GIFT_RECEIPT").equals("")){
                sOutput =  sOutput + "-----------------------------------------" + "\n";
                sOutput =  sOutput +param.get("EVENT_GIFT_RECEIPT") + "\n";
            }

            BigDecimal ATM = new BigDecimal(0);
            if(!param.get("PROMOTION_SUB_DESC").equals("")){
                ATM = (BigDecimal)param.get("AMT");
                sOutput =  sOutput + "\n[행사 내용]\n";
                //sOutput =  sOutput + param.get("PROMOTION_SUB_DESC") +"    "+ StringUtil.getLpadB(11,StringUtil.addComma(ATM.intValue()))+ "원 " + "\n\n";
                sOutput =  sOutput + param.get("PROMOTION_SUB_DESC")  + "\n\n";
            }

            if(!param.get("EVENT_GIFT_RECEIPT").equals("0")){
                sOutput =  sOutput + "-----------------------------------------" + "\n";
                sOutput =  sOutput +param.get("EVENT_GIFT_RECEIPT") + "\n";
            }

            // 고객정보 추가 메시지 추가
            sOutput =  sOutput + "-----------------------------------------" + "\n";
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            sOutput =  sOutput + "수      량 :  " + param.get("PRINT_CNT") + "\n\n";
            sOutput +=GS  + "!" + (char)0; //font size
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;
            //--------------------[ 주류 영수증 추가 ]-----------------------
            String  sAlcoholYn = (String)param.get("ALCOHOL_YN"); // 주류여부
            if(sAlcoholYn.equals("Y")){
                sOutput +=ESC + "a" + (char)1; //가운데 정렬
                sOutput = sOutput + "★주류 구매고객★\n";
                sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
                sOutput =  sOutput + "주류상품 인도시 신분증 확인 필수\n";
                sOutput =  sOutput + "[판매기록부 기준수량]\n";
                sOutput =  sOutput + "1.맥주:4박스(6,000ml)\n";
                sOutput =  sOutput + "2.희석식 소주:2상자(7,200ml)\n";
                sOutput =  sOutput + "3.위스키 및 브랜드:1상자(3,000ml)\n";
            }
            //-----------------------------------------------------------------

            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

            String product_name      = null;
            String jm_product_yn     = null;
            String pro_option_value  = null;
            String lavel_print_flag  = null;

            if(detail.size() > 0){
                for(int i=0 ; i< detail.size() ; i++) {
                    HashMap row = (HashMap)detail.get(i);

                    BigDecimal QTY = new BigDecimal(0);
                    QTY = (BigDecimal)row.get("QTY");
                    product_name      =  (String)row.get("PRO_NAME") +" "+(String)row.get("PRO_GAGE") ;
                    lavel_print_flag  =  (String)row.get("LABEL_PRINT_FLAG");
                    // 주문수량이 0이상이고 라벨출력 여부가 Y 값만 출력 시킨다.
                    if ( QTY.intValue() > 0 && lavel_print_flag.equals("Y")) {

                        System.out.println(" 고객인식표(배송기사) 사용" );
                        sOutput =  sOutput + product_name + StringUtil.getLpadB(2,"");
                        sOutput =  sOutput + StringUtil.getRpadB(2,StringUtil.addComma(QTY.intValue()))+ "\n";

                    }
                }
            }

            // 라벨영수증 장만상품 출력
            // ---------------------------
            /*
            if(detail.size() > 0){
                for(int k=0 ; k< detail.size() ; k++) {
                    HashMap row = (HashMap)detail.get(k);

                    BigDecimal QTY = new BigDecimal(0);
                    QTY = (BigDecimal)row.get("QTY");

                    product_name      =  (String)row.get("PRO_NAME");
                    jm_product_yn     =  (String)row.get("JM_PRODUCT_YN");
                    pro_option_value  =  (String)row.get("PRO_OPTION_VALUE");

                    // 주문수량이 0이상이고 장만내역이 있고 장만출력여부가 Y 값만 출력 시킨다.
                    if (QTY.intValue() > 0 && (jm_product_yn != null && jm_product_yn.equals("Y"))) {
                    	System.out.println(":==> 고객영수증 장만영수증을 출력" );
    	                sOutput =  sOutput + product_name +" "+ pro_option_value +" "+ StringUtil.getRpadB(1,StringUtil.addComma(QTY.intValue()))+ "\n";
                    }
                }
            }

            System.out.println("\n\n:==> 고객영수증 장만내역 포함 \n" + sOutput);
            */

            //--------------------------------------
            // 2018-05-04 name tag 주문번호 추가
            //--------------------------------------
            sOutput =  sOutput  + LF;
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)0; //font size
            sOutput =  sOutput + orderCode;

            /*
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;
            sOutput =  sOutput + "-----------------------------------------" + "\n";
            sOutput =  sOutput + "컨테이너 NO:          보냉가방 NO:       " + "\n\n";
            sOutput =  sOutput + "냉       장:          냉       동:       " + "\n\n";
            sOutput =  sOutput + "     쌀    :               물    :       " + "\n\n";
            sOutput =  sOutput + "-----------------------------------------" + "\n";
            */

        }catch(Exception ex){
            throw ex;
        }

        return sOutput;
    }

    // 네임택
    public static String labelInfo(HashMap param, HashMap header,HashMap header2, List<Map> detail) throws Exception {
        String sOutput = null;

        try{

            sOutput = "";
            //String barcord = (String) header.get("ORDER_DATE") + (String) header.get("ORDER_SEQ") + "000";
            String barcord   = ((String)header.get("ORDER_CODE")).substring(2, 17)+"0";
            String orderCode = ((String)header.get("ORDER_CODE"));

            System.out.println("barcode  exchange barcode========>" + barcord);
            System.out.println("labelInfo.LABEL_NO===============>" + param.get("LABEL_NO"));

            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            sOutput +=header.get("REGION_NAME") + " " + header.get("DELI_ORDER") + "차"+header.get("ORDER_DELI_METHOD_DESC")+  LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)32; //font size
            sOutput +="MEGAMART" + LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            sOutput = sOutput + "[수령자명]"+ header.get("RECE_NAME") + "\n";
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)0; //font size
            //sOutput = sOutput + "[주문코드]"+ header.get("ORDER_CODE") + "\n\n";
            sOutput = sOutput + "[주문고객]"+ header.get("ORDER_NAME") +"("+ header.get("ORDER_ID_DESC")+")\n";
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)0; //font size
            sOutput = sOutput + "[배송주소]"+ header.get("RECE_ADDR1") + "\n";
            // sOutput = sOutput + header.get("RECE_ADDR2")+ "\n"; // 상세주소 개인정보 위배
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            sOutput = sOutput + param.get("LABEL_NO") + "\n";

            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)0; //font siz
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n\n";
            sOutput =  sOutput  + ESC + "a" + (char)1;
            //sOutput =  sOutput  + GS + "h" + (char)32;      // 대략 2cm높이
            sOutput = sOutput  + GS + "h" + (char)64;    // 대략 4cm높이
            sOutput =  sOutput  + GS + "k" + (char)73 + (char)12+ (char)123+ (char)66+ (char)123+ (char)67;

            for(int b=0; b<barcord.length(); b++) {
                if(b%2==0) {
                    sOutput =  sOutput  + (char)(Integer.parseInt(barcord.substring(b, (b+2))));
                }
            }

            sOutput =  sOutput  + LF;
            sOutput =  sOutput  + orderCode;

            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;

            sOutput =  sOutput  + LF;
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

            String product_name      = null;
            String jm_product_yn     = null;
            String pro_option_value  = null;
            String lavel_print_flag  = null;

            if(detail.size() > 0){
                for(int i=0 ; i< detail.size() ; i++) {
                    HashMap row = (HashMap)detail.get(i);

                    BigDecimal QTY = new BigDecimal(0);
                    QTY = (BigDecimal)row.get("QTY");

                    product_name      =  (String)row.get("PRO_NAME") + " " + (String)row.get("PRO_GAGE");
                    lavel_print_flag  =  (String)row.get("LABEL_PRINT_FLAG");

                    // 주문수량이 0이상이고 라벨출력 여부가 Y 값만 출력 시킨다.
                    if ( QTY.intValue() > 0 && lavel_print_flag.equals("Y")) {
                        sOutput =  sOutput + product_name + StringUtil.getLpadB(2,"");
                        sOutput =  sOutput + StringUtil.getRpadB(2,StringUtil.addComma(QTY.intValue()))+ "\n";
                    }
                }
            }

            // 라벨영수증 장만상품 출력
            // ---------------------------
            /*
            if(detail.size() > 0){
                for(int k=0 ; k< detail.size() ; k++) {
                    HashMap row = (HashMap)detail.get(k);

                    BigDecimal QTY = new BigDecimal(0);
                    QTY = (BigDecimal)row.get("QTY");

                    product_name      =  (String)row.get("PRO_NAME");
                    jm_product_yn     =  (String)row.get("JM_PRODUCT_YN");
                    pro_option_value  =  (String)row.get("PRO_OPTION_VALUE");

                    // 주문수량이 0이상이고 장만내역이 있고 장만출력여부가 Y 값만 출력 시킨다.
                    if (QTY.intValue() > 0 && (jm_product_yn != null && jm_product_yn.equals("Y"))) {
                    	System.out.println(":==> 라벨 장만영수증을 출력" );
    	                sOutput =  sOutput + product_name +" "+ pro_option_value +" "+ StringUtil.getRpadB(1,StringUtil.addComma(QTY.intValue()))+ "\n";
                    }
                }
            }
            */

            if(header.get("ORDER_ID") != null && header.get("ORDER_ID").equals("비회원주문")) {
                sOutput +=ESC + "a" + (char)1; //가운데 정렬
                sOutput +=ESC + "M" + (char)0; //font A
                sOutput +=ESC + "E" + (char)1; //강조
                sOutput +=GS + "!" + (char)16; //font size
                sOutput = sOutput + "비회원 구매 " + "\n";
                sOutput = sOutput + "첫 구매 감사드립니다." + "\n";
            }
            System.out.println("\n\n:==> 라벨 영수증내역 \n" + sOutput);

        } catch(Exception ex){
            throw ex;
        }

        return sOutput;
    }

    // 유닛라벨정보
    public static String unitLabelInfo(HashMap param, List<Map> detail) throws Exception {

        String sOutput = null;

        try{
            sOutput = "";
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS  + "!" + (char)32; //font size
            sOutput +="MEGA MART"+  LF + LF; //줄바꿈
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;
            sOutput = sOutput +""+ "-----------------------------------------" + "\n";
            sOutput +=ESC + "a" + (char)1; //가운데 정렬
            sOutput +=ESC + "M" + (char)0; //font A
            sOutput +=ESC + "E" + (char)1; //강조
            sOutput +=GS + "!" + (char)16; //font size
            sOutput +="상품 피킹 리스트"+ LF+ LF; //줄바꿈
            sOutput +=param.get("DELI_YOIL")+ " "+param.get("CALL_JOB")+ LF+ LF; //줄바꿈
            sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
            sOutput +=ESC + "E" + (char)0;
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
            sOutput =  sOutput +""+ "상품명           단가              수량 " + "\n";
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

            String product_id   = "";
            String product_name = "";

            if(detail.size() > 0){
                for(int i=0 ; i< detail.size() ; i++) {
                    HashMap row = (HashMap)detail.get(i);

                    BigDecimal QTY = new BigDecimal(0);
                    QTY = (BigDecimal)row.get("QTY");

                    product_id    = (String)row.get("PRODUCT_ID");
                    product_name  = (String)row.get("PRODUCT_NAME");
                    BigDecimal SALE_PRICE = new BigDecimal(0); 		 	SALE_PRICE = (BigDecimal)row.get("SALE_PRICE");

                    // 총 주문수량이 0인것은 스킵 시킨다.
                    if ( QTY.intValue() > 0 ) {
                        sOutput =  sOutput + product_name+ "\n";
                        sOutput =  sOutput  + StringUtil.getRpadB(10,product_id)
                                + StringUtil.getLpadB(10,StringUtil.addComma(SALE_PRICE.intValue()))
                                + StringUtil.getLpadB(17,StringUtil.addComma(QTY.intValue()))+ "\n";

                    }
                }
            }
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n\n\n\n";
            sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

        } catch(Exception ex){
            throw ex;
        }

        return sOutput;
    }

    public static String eventInfo(HashMap param, HashMap header, HashMap info) throws Exception {
        String sOutput = null;

        try{

            String barcord = (String) header.get("ORDER_DATE") + (String) header.get("ORDER_SEQ") + "000";

            //String barcord = (String) header.get("ORDER_CODE") + "000";

            sOutput = "";

            if(param.get("system").equals("res")){
                sOutput = "<br/>";
            }

            if(param.get("system").equals("oms")){
                sOutput = "";
                sOutput +=ESC + "a" + (char)1; //가운데 정렬
                sOutput +=ESC + "M" + (char)0; //font A
                sOutput +=ESC + "E" + (char)1; //강조
                sOutput +=GS + "!" + (char)32; //font size
                sOutput +="MEGAMART" + LF ; //줄바꿈
                sOutput +=GS + "!" + (char)0;
                sOutput +="(메가마트몰 "+ info.get("COM_NAME")+ ")" + LF + LF; //줄바꿈
            }

            //sOutput +=ESC + "a" + (char)1; //가운데 정렬
            if(!param.get("system").equals("res")){
                sOutput +=ESC + "a" + (char)0;
                sOutput +=ESC + "M" + (char)0; //font A
                sOutput +=ESC + "E" + (char)1; //강조
                sOutput +=GS + "!" + (char)16; //font size
            }

            // 김해점만 출력 되도록 변경
            if (header.get("MALL_ID").equals("2709")) {
                sOutput +="올뉴 모닝을 드립니다. " +  LF + LF;          // 줄바꿈
                // 언/천/기/덕계점
                //} else if (header.get("MALL_ID").equals("2702")||header.get("MALL_ID").equals("2704")||header.get("MALL_ID").equals("2708")||header.get("MALL_ID").equals("2776")) {
                // sOutput +="240만원 상품권 행운 경품 대잔치 " +  LF + LF; // 줄바꿈

                if(!param.get("system").equals("res")){
                    sOutput +=ESC + "a" + (char)0; //왼쪽  정렬
                    sOutput +=ESC + "E" + (char)0;
                    sOutput +=GS  + "!" + (char)0; //font size
                }

                sOutput = sOutput + "응모기간  : 5.31(수)~7.1(토)"+ "\n";
                sOutput = sOutput + "현장추첨일: 17.7.02(일) 오후3시"+ "\n";
                sOutput = sOutput + "성    명  : " + header.get("ORDER_NAME") + "\n";

                if ( header.get("ORDER_TEL") != null && !header.get("ORDER_TEL").equals(" ")) {
                    sOutput = sOutput + "전화번호  : " + header.get("ORDER_HP") + " " + header.get("ORDER_TEL") +"\n";
                } else {
                    sOutput = sOutput + "전화번호  : " + header.get("ORDER_HP") +"\n";
                }

                sOutput = sOutput + "주    소  : " + header.get("RECE_ADDR1")+ "\n";
                sOutput = sOutput + "            " + header.get("RECE_ADDR2")+ "\n\n";

                sOutput = sOutput + "회원번호  : " + header.get("MEMBER_CODE") + "\n";
                sOutput = sOutput + "-추첨당일 현장 참여자에 한하여 경품지급"  + "\n";
                sOutput = sOutput + " 현장추점시 본인 신분증 지참필수"         + "\n";
                sOutput = sOutput + "-제세공과금 22%는 당첨자 본인 부담"       + "\n\n";

                if(param.get("system").equals("oms")){

                    sOutput =  sOutput  + ESC + "a" + (char)1;
                    sOutput =  sOutput  + GS + "h" + (char)64; // 바코드높이 설정
                    sOutput =  sOutput  + GS + "k" + (char)73 + (char)12+ (char)123+ (char)66+ (char)123+ (char)67;

                    for(int b=0; b<barcord.length(); b++) {
                        if(b%2==0) {
                            sOutput =  sOutput  + (char)(Integer.parseInt(barcord.substring(b, (b+2))));
                        }
                    }

                    sOutput =  sOutput  + LF;
                    sOutput =  sOutput  + barcord;

                } else if(param.get("system").equals("res")){
                    sOutput =  sOutput  + "\n";
                    sOutput =  sOutput  + "바코드 No :" + barcord ;
                }
            }

        }catch(Exception ex){
            throw ex;
        }

        return sOutput;
    }

    public static void printUnitLabel(HashMap param , String unitLabelMeesage) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;

        try{
            // 운영아이피
            String printIP   = (String)param.get("PRINT_IP");
            String printPort = (String)param.get("PRINT_PORT");

            System.out.println(":::=> 운영 printIP======>" + printIP);
            System.out.println(":::=> 운영 printPort====>" + printPort);
            System.out.println(":::=>unitLabelMeesage====>" + unitLabelMeesage);

            printerServer = new Socket(printIP, Integer.parseInt(printPort));
            printerServer.setSoTimeout(10*1000);

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = " \n  \n \n \n \n";
            String cutt = GS + "V0";

            //라벨
            if (!unitLabelMeesage.equals("")) {
                dataOut.write(unitLabelMeesage.getBytes("euc-kr"));
                dataOut.write(dummy.getBytes());
                dataOut.write(dummy.getBytes());
                dataOut.write(cutt.getBytes());
            }

            dataOut.flush();

        }catch(Exception ex){
            throw ex;
        }finally{
            try {
                if ( dataOut != null ) {
                    dataOut.close();
                    dataOut = null;
                }
                if (printerServer != null) {
                    printerServer.close();
                    printerServer = null;
                }
            } catch ( Exception ignored ){}
        }
    }

}
