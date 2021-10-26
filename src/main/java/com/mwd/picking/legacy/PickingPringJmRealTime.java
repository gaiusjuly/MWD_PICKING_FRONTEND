package com.mwd.picking.legacy;

import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mwd.picking.legacy.PickingPrint;
import com.mwd.picking.legacy.StringUtil;

public class PickingPringJmRealTime {

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

    public PickingPringJmRealTime() {

    }

    public static void main(String[] args)    {
        System.out.println("Pickign Print Started.......");
        try {
            PickingPrint pp = new PickingPrint();

            HashMap param = new HashMap();

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

            PickingPrint.printSend(param,firstMeesage,"","");

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        System.out.println("Pickign Print End.......");
    }

    //프린팅..
    public static String printSend(HashMap param, List<Map> detail) throws Exception {

        String sOutput = null;

        try{

            sOutput = "";

            if(detail.size()>0) {

                for(int i=0 ; i< detail.size() ; i++) {

                    HashMap row = (HashMap)detail.get(i);

                    String  product_id = "";
                    String  product_name = "";
                    int     discount_price = 0;

                    /* 현재 : 1-과세, 0-면세 */
                    if ("0".equals(row.get("PRO_TAX_FLAG"))) {
                        product_id = (String)row.get("PRODUCT_ID") + "*";
                    } else {
                        product_id = (String)row.get("PRODUCT_ID");
                    }

                    BigDecimal QTY 	= new BigDecimal(0); QTY = (BigDecimal)row.get("QTY");
                    BigDecimal DISCOUNT_PRICE = new BigDecimal(0); DISCOUNT_PRICE = (BigDecimal)row.get("DISCOUNT_PRICE");
                    BigDecimal SALE_PRICE 	  = new BigDecimal(0); SALE_PRICE  = (BigDecimal)row.get("SALE_PRICE");
                    BigDecimal SALE_MONEY 	  = new BigDecimal(0); SALE_MONEY  = (BigDecimal)row.get("SALE_MONEY");

                    BigDecimal CMS_QTY    	  = new BigDecimal(0); CMS_QTY     = (BigDecimal)row.get("CMS_QTY");
                    BigDecimal CMS_PRICE 	  = new BigDecimal(0); CMS_PRICE   = (BigDecimal)row.get("CMS_PRICE");
                    BigDecimal FREE_QTY 	  = new BigDecimal(0); FREE_QTY    = (BigDecimal)row.get("FREE_QTY");
                    BigDecimal FREE_PRICE 	  = new BigDecimal(0); FREE_PRICE  = (BigDecimal)row.get("FREE_PRICE");
                    BigDecimal MORE_QTY 	  = new BigDecimal(0); MORE_QTY    = (BigDecimal)row.get("MORE_QTY");

                    BigDecimal MORE_PRICE 	  = new BigDecimal(0); MORE_PRICE  = (BigDecimal)row.get("MORE_PRICE");
                    BigDecimal S_CMS_QTY 	  = new BigDecimal(0); S_CMS_QTY   = (BigDecimal)row.get("S_CMS_QTY");
                    BigDecimal S_CMS_PRICE 	  = new BigDecimal(0); S_CMS_PRICE = (BigDecimal)row.get("S_CMS_PRICE");

                    BigDecimal C_CMS_QTY 	  = new BigDecimal(0); C_CMS_QTY   = (BigDecimal)row.get("C_CMS_QTY");
                    BigDecimal C_CMS_PRICE 	  = new BigDecimal(0); C_CMS_PRICE = (BigDecimal)row.get("C_CMS_PRICE");

                    product_name   = (String)row.get("PRO_NAME");
                    discount_price = QTY.intValue()*DISCOUNT_PRICE.intValue(); //E_CMS_PRICE : 에누리금액

                    sOutput =  sOutput + ESC + "E" + (char)0;  //폰트 bold값을 초기화 해줌.
                    sOutput =  sOutput + ESC + "a" + (char)0;  //왼쪽 정렬
                    sOutput =  sOutput + GS  + "!" + (char)0;  //font size
                    sOutput =  sOutput + product_name +" "+  (String)row.get("PRO_GAGE")+ "\n";
                    sOutput =  sOutput  + StringUtil.getRpadB(10,product_id)
                            + StringUtil.getLpadB(12,StringUtil.addComma(SALE_PRICE.intValue()+DISCOUNT_PRICE.intValue()))
                            + StringUtil.getLpadB(7,StringUtil.addComma(QTY.intValue()))
                            + StringUtil.getLpadB(12,StringUtil.addComma(SALE_MONEY.intValue()+discount_price))+ "\n";

                    if (DISCOUNT_PRICE.intValue() > 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[할인금액]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(discount_price*-1))+ "\n";
                    }

                    if (CMS_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[MP쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(CMS_PRICE.intValue()*QTY.intValue()*-1))+ "\n";
                    }

                    if (FREE_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[무료쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(FREE_PRICE.intValue()*QTY.intValue()*-1))+ "\n"; //  FREE_MONEY ==> FREE_PRICE
                    }

                    if (MORE_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[다다익선]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(MORE_PRICE.intValue()*QTY.intValue()*-1))+ "\n"; // MORE_AMOUNT ==> MORE_PRICE
                    }

                    if (S_CMS_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[DM쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(S_CMS_PRICE.intValue()*QTY.intValue()*-1))+ "\n"; // S_CMS_MONEY => S_CMS_PRICE
                    }

                    if (C_CMS_QTY.intValue() != 0) {
                        sOutput =  sOutput + StringUtil.getRpadB(10,"")
                                + StringUtil.getLpadB(12,"[카드쿠폰]")
                                + StringUtil.getLpadB(7,"")
                                + StringUtil.getLpadB(12,StringUtil.addComma(C_CMS_PRICE.intValue()*QTY.intValue()*-1))+ "\n"; // C_CMS_MONEY => C_CMS_PRICE
                    }

                    sOutput =  sOutput +"\n"+ "[요청내역]" + "\n";
                    sOutput =  sOutput + ESC + "E" + (char)0;  //강조
                    sOutput =  sOutput + GS + "!"  + (char)16; //font size
                    sOutput =  sOutput + (String)row.get("PRO_OPTION_VALUE")            + "\n";
                    sOutput =  sOutput + ESC + "E" + (char)0;
                    sOutput =  sOutput + ESC + "a" + (char)0;  //왼쪽 정렬
                    sOutput =  sOutput + GS + "!"  + (char)0;  //font size
	                /*
	                sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
	                sOutput =  sOutput +"고객님 요청에 따라 정성껏 손질 하였습니다" + "\n\n";
	                sOutput =  sOutput +(String)row.get("GROUP_DESC") +" 서비스 담당자________________" + "\n";
	                */
                    sOutput =  sOutput +""+ "-----------------------------------------" + "\n";

                }

                sOutput =  sOutput + ESC + "E" + (char)0;  //폰트 bold값을 초기화 해줌.
                sOutput =  sOutput + ESC + "a" + (char)0;  //왼쪽 정렬
                sOutput =  sOutput + GS  + "!" + (char)0;  //font size
                sOutput =  sOutput +"\n"+ "상품 요청사항" + "\n";
                sOutput =  sOutput + ESC + "E" + (char)0;  //강조
                sOutput =  sOutput + GS + "!"  + (char)16; //font size
                sOutput =  sOutput + param.get("ORDER_MESSAGE") + "\n";
                sOutput =  sOutput + ESC + "E" + (char)0;
                sOutput =  sOutput + ESC + "a" + (char)0;  //왼쪽 정렬
                sOutput =  sOutput + GS + "!"  + (char)0;  //font size
                sOutput =  sOutput +""+ "-----------------------------------------" + "\n";
                sOutput =  sOutput +"고객님 요청에 따라 정성껏 손질 하였습니다.." + "\n\n";
                sOutput =  sOutput +" 서비스 담당자________________" + "\n";

                //Type 1-보관용, 2-고객용 (석동리 대리 요청으로 고객용만 출력시킴(2016-11-16)
                //pp.printSend(oValue, sOutput,"1");
                //if (sPrintProductYn.equals("Y")) {
                //    pp.printSend(oValue, sOutput,"2");
                //}
            }

        }catch(Exception ex){
            throw ex;
        }

        return sOutput;
    }

    /* 기능  : 피킹 영수증내역을 출력한다.
     * param : oValue, detailList,   Type : 1-고객용, 2-보관용
     */
    public static void printSend(HashMap param , String listDetail, String Type ) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;
        System.err.println(param);
        try{

            String sDelivDate       = (String)param.get("DELIV_DATE");       //배송일자
            String sDelivRegionName = StringUtil.nvl((String)param.get("DELI_REGION_NAME")); //지역명
            String sDelivType       = StringUtil.nvl((String)param.get("DELI_TYPE"));        //지역명
            String sDelivOrder      = StringUtil.nvl((String)param.get("DELI_ORDER"));       //차수
            String sPickUpTime      = StringUtil.nvl((String)param.get("PICK_UP_TIME"));     //픽업시간
            String sStartTime       = (String)param.get("START_TIME");       //출발시간
            String sOrderName       = (String)param.get("ORDER_NAME");       //주문자명
            String sOrderId         = (String)param.get("ORDER_ID");         //주문자ID
            String sOrderTel        = StringUtil.nvl((String)param.get("ORDER_TEL"));        //주문자전화번호
            String sOrderHp         = StringUtil.nvl((String)param.get("ORDER_HP"));         //주문자핸드폰
            String sGroupCode       = (String)param.get("GROUP_CODE");       //그룹코드
            String sOrderDatePrint  = (String)param.get("ORDER_DATE_PRINT"); //주문일자
            String sOrderCode       = (String)param.get("ORDER_CODE");
            String sReceName        = (String)param.get("RECE_NAME");
            String sReceAddr1       = (String)param.get("RECE_ADDR1");
            String sReceAddr2       = (String)param.get("RECE_ADDR2");
            String sDeliTitle       = (String)param.get("DELI_TITLE");
            //-----[ 프린트 아이피/ 포트 설정 ]
            String printIP          = (String)param.get("PRINT_IP");         //점포별 프린트 IP
            String sPrintPort       = (String)param.get("PRINT_PORT");       //프린트포트
            String sOrderDeliMeghod = (String)param.get("ORDER_DELI_METHOD");       //프린트포트

            // 영수증 사용목적
            String sUseMethod = "";
            if (Type.equals("1")) {
                sUseMethod = "(고객용)";
            }else{
                sUseMethod = "(보관용)";
            }

            // 그룹별 서비스 분리
            if (sGroupCode.equals("715")) {
                sGroupCode = "축산 ";
            } else if (sGroupCode.equals("713")) {
                sGroupCode = "농산 ";
            } else if (sGroupCode.equals("714")) {
                sGroupCode = "수산 ";
            } else {
                sGroupCode = "";
            }

              /*
              System.out.println("Type=========>" + Type);
              System.out.println("sGroupCode===>" + sGroupCode);
              System.out.println("printIP======>" + printIP);
              System.out.println("sPrintPort===>" + sPrintPort);
              */
            //maxda
            printerServer = new Socket(printIP, Integer.parseInt(sPrintPort));
            printerServer.setSoTimeout(10*1000);

            String headerMeesage = "";

            headerMeesage = "";
            headerMeesage += ESC + "a" + (char)1;   //가운데 정렬
            headerMeesage += ESC + "M" + (char)0;   //font A
            headerMeesage += ESC + "E" + (char)1;   //강조
            headerMeesage += GS + "!"  + (char)16;  //font size
            headerMeesage +="MEGAMART" + LF  ;      //줄바꿈
            headerMeesage += GS + "!" + (char)0;    //끝내고
            headerMeesage +=sGroupCode + "손질 서비스 요청서 " + sUseMethod + LF ;
            headerMeesage += GS + "!" + (char)0;    //끝내고
            headerMeesage += ""+ "-----------------------------------------" + "\n";

            // 전국택배일 경우
            if(sDelivType.equals("Z")) {
                headerMeesage += GS + "!"  + (char)16;  //font size
                headerMeesage += ESC + "a" + (char)1;   //가운데 정렬
                headerMeesage += ESC + "E" + (char)1;   //강조
                headerMeesage += sDeliTitle + LF ;      // 전국 유형별 타이틀로 변경
                headerMeesage += GS + "!" + (char)0;    //끝내고
                // 택배점일 경우
            } else {
                headerMeesage += GS + "!"  + (char)16;  //font size
                headerMeesage += ESC + "a" + (char)1;   //가운데 정렬
                headerMeesage += ESC + "E" + (char)0;
                // 택배점 추가 시작
                headerMeesage += sDeliTitle + LF ;
                headerMeesage += GS + "!" + (char)0;    //끝내고
                headerMeesage += ""+ "-----------------------------------------" + "\n";
                // 택배점 추가 종료
                headerMeesage += ESC + "a" + (char)1;   //가운데 정렬
                headerMeesage += ESC + "E" + (char)0;
                headerMeesage += sDelivDate + " "+ sDelivRegionName + "  "+ sDelivOrder +"차"+ LF ;
                headerMeesage += GS + "!" + (char)1;    //끝내고
                headerMeesage += ESC + "E" + (char)1;   //강조
                if(sOrderDeliMeghod.equals("2")) {
                    headerMeesage +="상품픽업 "+sPickUpTime + "   고객픽업 " +  sStartTime + LF ;
                }else if(sOrderDeliMeghod.equals("3")) {
                    headerMeesage +="상품픽업         " + "   차량출발 " +  sStartTime + LF ;
                }else {
                    headerMeesage +="상품픽업 "+sPickUpTime + "   차량출발 " +  sStartTime + LF ;
                }
                headerMeesage += GS + "!" + (char)0;    //끝내고
            }
            headerMeesage += ""+ "-----------------------------------------"  + LF+ "\n";
            headerMeesage += ESC + "E" + (char)0;
            headerMeesage += ESC + "a" + (char)0;   //왼쪽  정렬
            headerMeesage += "주문코드:" + sOrderCode + LF ;
            headerMeesage += "주문일  :" + sOrderDatePrint + LF ;
            headerMeesage += "주문자명:" +sOrderName + " ID:"+sOrderId + LF ; //줄바꿈
            headerMeesage += "수령자명:" +sReceName  + LF ; //줄바꿈
              /*
              headerMeesage += "배송주소:" +sReceAddr1 + LF ; //줄바꿈
              headerMeesage += "         " +sReceAddr2 + LF ; //줄바꿈
              headerMeesage += "손질문의 고객센터:1566-1560"+ LF ;            //줄바꿈
              */
            //headerMeesage += "TEL:"+ sOrderTel+" HP:"+ sOrderHp+ LF ; //줄바꿈 "
            headerMeesage += ""+ "-----------------------------------------" + "\n";
            headerMeesage += ""+ "상품명           단가     수량      금액 " + "\n";
            headerMeesage += ""+ "-----------------------------------------" + "\n";

            headerMeesage =  headerMeesage + listDetail;
            headerMeesage =  headerMeesage +"\n\n\n\n";

            System.out.println("\n [장만서비스내역] \n" +headerMeesage);

            headerMeesage +=GS + "/";
            //maxda

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = "\n";
            String cutt  = GS + "V0"; // 자른다.

            //첫번째 최종 주문에 대한 영수증 출력
            dataOut.write(headerMeesage.getBytes("euc-kr"));
            dataOut.write(dummy.getBytes());
            dataOut.write(cutt.getBytes());

            dataOut.flush();

        }catch(Exception ex){
            throw ex;
        }finally{
            try {
                if ( dataOut != null ) {
                    System.out.println("\n--------------------------------------------");
                    System.out.println(" # dataOut close\n");
                    System.out.println("----------------------------------------------");
                    dataOut.close();
                    dataOut = null;
                }
                if (printerServer != null) {
                    System.out.println("\n--------------------------------------------");
                    System.out.println(" # printerServer close\n");
                    System.out.println("\n--------------------------------------------");
                    printerServer.close();
                    printerServer = null;
                }
            } catch ( Exception ignored ){}
        }
    }

    public static void printSend(HashMap param , String firstMessage) throws Exception {

        Socket printerServer = new Socket();
        DataOutputStream dataOut = null;

        try{

            // 운영아이피
            String printIP   = (String)param.get("PRINT_IP");
            String printPort = (String)param.get("PRINT_PORT");

            //System.out.println("printIP======>" + printIP);
            //System.out.println("printPort====>" + printPort);

            printerServer = new Socket(printIP, Integer.parseInt(printPort));
            printerServer.setSoTimeout(10*1000);

            dataOut = new DataOutputStream(printerServer.getOutputStream());

            String dummy = " \n  \n \n \n \n";
            String cutt = GS + "V0";

            //첫번째 최종 주문에 대한 영수증 출력
            dataOut.write(firstMessage.getBytes());
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
}
