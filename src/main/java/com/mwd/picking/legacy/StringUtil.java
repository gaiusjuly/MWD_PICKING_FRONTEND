package com.mwd.picking.legacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;


public class StringUtil {

    public static String imagebase = "http://www.megamart.com/attach/product_images/pro_images/";

    /**
     * 아이디/비밀번호 찾기 인증번호 6자리
     * @return
     */
    public static String RandomNumber(){
        Random random = new Random();
        int randomInteger = random.nextInt();
        String rtn = Integer.toString(randomInteger).substring(1, 7);
        return rtn;
    }


    /**
     * 비밀번호 Hex
     * @param pStr
     * @return
     */
    public static String SHAEnc(String pStr) {

        StringBuffer sb = new StringBuffer();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(pStr.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            System.out.println("Hex format : " + sb.toString());

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * @Method Name : nullToBlank
     * @param val
     * @return
     */
    public static String nullToBlank(String val) {
        if (val == null || val.length() ==0) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * @Method Name : getMultiStateStr
     * @param lstTarget
     * @return
     */
    public static String getMultiStateStr(List<String> lstTarget){
        StringBuffer strBufResult = new StringBuffer();
        for(String strValue: lstTarget){
            strBufResult.append("'"+strValue+"',");
        }
        return strBufResult.substring(0, strBufResult.length()-1).toString();
    }

    /**
     * @Method Name : leftPad
     * @param src
     *        item
     *        length
     * @return
     */
    public static String leftPad(String src, String item, int length){
        for(int i=0; i< length; i++){
            if(src.length() < length){
                src = item + src;
            }
        }
        return src;
    }

    public static boolean isNullOrEmpty(String src) {
        return (src == null) || (src.length() < 1);
    }


    /**
     */
    public static enum DELIMITER {
        COMMA("COMMA", ","),
        COLON("COLON", ":"),
        TAB("TAB", "\t"),
        SPACE("SPACE", " ");

        private String name;
        private String value;

        private DELIMITER(String name, String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * @return value
         */
        public String getValue() {
            return value;
        }

        /**
         */
        public static String lookup(String name) {
            for (DELIMITER delimiter: DELIMITER.values()) {
                if (delimiter.name.equals(name)) {
                    return delimiter.value;
                }
            }
            return DELIMITER.COMMA.getValue();
        }
    }

    /**
     * right padding
     * @param s
     * @param length
     * @param pad
     * @return
     */
    public static String rPad2(String s, int length, char pad){
        StringBuilder sb = new StringBuilder(s);
        for (int i = getLength(s); i < length; i++) {
            sb.append(pad);
        }
        return sb.toString();
    }

    /**
     * left padding
     * @param s
     * @param length
     * @param pad
     * @return
     */
    public static String lPad2(String s, int length, char pad){
        StringBuilder sb = new StringBuilder(s);
        for (int i = getLength(s); i < length; i++) {
            sb.insert(0, pad);
        }
        return sb.toString();
    }

    /**
     * @param s
     * @return
     */
    public static int getLength(String s) {
        int length = 0;
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if ('\uAC00' <= charArray[i] && charArray[i] <= '\uD7A3') {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * toString
     * @param o
     * @return
     */
    public static String toString(Object o) {
        if (o == null) return "";
        return o.toString();
    }

    /**
     * decode
     * @param s
     * @param strings
     * @return
     */
    public static String decode(String s, String ... strings ) {
        if (strings == null || strings.length < 3) return "";
        for (int i = 0; i <= strings.length - 3;) {
            if ((s == null && strings[i] == null) || s.equals(strings[i])) {
                return strings[i + 1];
            } else {
                if (i == strings.length - 3) {
                    return strings[i + 2];
                } else {
                    i += 2;
                }
            }
        }
        return "";
    }

    /*
    public static String rplc(String s, String s1, String s2)
    {
        int i = 0;
        int j = 0;
        StringBuffer stringbuffer = new StringBuffer();
        while((j = s.indexOf(s1, i)) >= 0)
        {
            stringbuffer.append(s.substring(i, j));
            stringbuffer.append(s2);
            i = j + s1.length();
        }
        stringbuffer.append(s.substring(i));
        return stringbuffer.toString();
    }
    */

    public static boolean empty(String str) {
        return str==null || str.length()==0;
    }

    public static String SubStr(String s, int i)
    {
        String s1 = "";
        int j = 0;
        char ac[] = s.toCharArray();
        for(int k = 0; k < i; k++)
        {
            if(s1.length() >= s.length() || i <= j)
            {
                if(j > i)
                    s1 = s1.substring(0, s1.length() - 1) + ".";
                break;
            }
            if(ac[k] >= '\377')
                j += 2;
            else
                j++;
            if(k + 1 < i)
                s1 = s1 + s.substring(k, k + 1);
        }

        return s1;
    }

    public static String getPerPage(int i, int j)
    {
        int k = 0;
        k = i / j;
        if(i % j > 0)
            k++;
        return String.valueOf(k);
    }

    public static String getRandomString()
    {
        String s = null;
        Random random = new Random();
        long l = random.nextLong();
        l %= 0x3b9aca00L;
        s = (new DecimalFormat("000000000")).format(Math.abs(l));
        return s;
    }

    /*
    public static String delMoney(String s, String s1)
    {
        if(IV.noData(s) || IV.noData(s1))
            return "err";
        s1 = sReplace(",", "", s1);
        int i = s.length() - 1;
        int j = s1.length();
        if(j < i)
            return "0";
        else
            return Currency.getWon(s1.substring(0, j - 3));
    }
	*/

    public static boolean isExist(String s, String s1, int i)
    {
        int j = s1.length();
        int k = 0;
        for(int l = 0; l < j / i; l++)
        {
            if(s.equals(s1.substring(k, k + i)))
                return true;
            k += i;
        }

        return false;
    }

    public static String getSelected(String s, String s1)
    {
        if(s.equals(s1))
            return "selected";
        else
            return "";
    }

    public static String makeCipher(int i, String s, String s1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int j = s1.length();
        int k = i - j;
        for(int l = 1; l <= k; l++)
            stringbuffer.append(s);

        stringbuffer.append(s1);
        return stringbuffer.toString();
    }

    public static String makeCipherReverse(int i, String s, String s1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int j = s1.length();
        int k = i - j;
        stringbuffer.append(s1);
        for(int l = 1; l <= k; l++)
            stringbuffer.append(s);

        return stringbuffer.toString();
    }

    public static String makeCipherReverses(long l, String s, String s1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s1.length();
        long l1 = l - i;
        stringbuffer.append(s1);
        for(int j = 1; j <= l1; j++)
            stringbuffer.append(s);

        return stringbuffer.toString();
    }

    /*
    public static int getAge(String s)
    {
        String s1 = null;
        int i = Integer.parseInt(s.substring(6, 7));
        if(i <= 2)
            s1 = "19";
        else
            s1 = "20";
        int j = Integer.parseInt(s1 + s.substring(0, 2));
        int k = Integer.parseInt(DateFormatUtil.getYear());
        int l = k - j;
        return l;
    }


    public static String disPrice(String s, String s1)
    {
        double d = Double.valueOf(s).doubleValue();
        double d1 = Double.valueOf(s1).doubleValue();
        String s2 = Currency.getWon(d * d1);
        String s3 = s2.substring(0, s2.length() - 1) + "0";
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("$").append(s1).append(" (").append(s3).append("원)");
        return stringbuffer.toString();
    }

    public static String disPrice(String s, int i)
    {
        String s1 = String.valueOf(i);
        double d = Double.valueOf(s).doubleValue();
        double d1 = Double.valueOf(s1).doubleValue();
        String s2 = Currency.getWon(d * d1);
        String s3 = s2.substring(0, s2.length() - 1) + "0";
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("$").append(i).append(" (").append(s3).append("원)");
        return stringbuffer.toString();
    }
    */

    public static String disOnlyPrice(String s, String s1)
    {
        double d = Double.valueOf(s).doubleValue();
        double d1 = Double.valueOf(s1).doubleValue();
        String s2 = String.valueOf(d * d1);
        int i = s2.indexOf(".");
        String s3 = s2.substring(0, i - 1) + "0";
        return s3;
    }

    public static String checkDouble(double d)
    {
        String s = null;
        if((int)d == d)
            s = String.valueOf((int)d);
        else
            s = String.valueOf(d);
        return s;
    }

    public static String rplc(String s, String s1, String s2)
    {
        int i = 0;
        int j = 0;
        StringBuffer stringbuffer = new StringBuffer();
        while((j = s.indexOf(s1, i)) >= 0)
        {
            stringbuffer.append(s.substring(i, j));
            stringbuffer.append(s2);
            i = j + s1.length();
        }
        stringbuffer.append(s.substring(i));
        return stringbuffer.toString();
    }

    public static StringBuffer subStringB(StringBuffer stringbuffer, int i, int j)
    {
        StringBuffer stringbuffer1 = new StringBuffer();
        stringbuffer1.append(subStringB(stringbuffer.toString(), i, j));
        return stringbuffer1;
    }

    public static String subStringB(String s, int i, int j)
    {
        String s1 = "";
        String s2 = "";
        int k = 0;
        int l = 0;
        int i1 = lengthB(s);
        if(j > i1)
            j = i1;
        for(int j1 = 0; j1 < j; j1++)
        {
            String s3 = "" + s.charAt(j1);
            if(k == i)
            {
                s1 = s1 + s3;
                if(s3.getBytes().length == 1)
                    l++;
                else
                if(s3.getBytes().length == 2)
                    l += 2;
                else
                    System.out.println("알수 없는 문자임");
            }
            if(l == j)
                break;
            if(k != i)
                if(s3.getBytes().length == 1)
                {
                    k++;
                    l++;
                } else
                if(s3.getBytes().length == 2)
                {
                    k += 2;
                    l += 2;
                } else
                {
                    System.out.println("알수 없는 문자임");
                }
        }

        return s1;
    }

    public static String subStringB(String s, int i)
    {
        return subStringB(s, i, lengthB(s));
    }

    public static StringBuffer subStringB(StringBuffer stringbuffer, int i)
    {
        StringBuffer stringbuffer1 = new StringBuffer();
        stringbuffer1.append(subStringB(stringbuffer.toString(), i, lengthB(stringbuffer.toString())));
        return stringbuffer1;
    }

    public static int lengthB(String s)
    {
        return s.getBytes().length;
    }

    public static int lengthB(StringBuffer stringbuffer)
    {
        return lengthB(stringbuffer.toString());
    }

    public static String subString(String s, int i, int j)
    {
        int k = s.length();
        if(i < k)
            i = k;
        if(j > k)
            j = k;
        return s.substring(i, j);
    }

    public static String getRpadB(int i, String s, String s1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int j = lengthB(s);
        int k = i - j;
        stringbuffer.append(s);
        for(int l = 1; l <= k; l++)
            stringbuffer.append(s1);

        return stringbuffer.toString();
    }

    public static String getRpadB(int i, String s)
    {
        return getRpadB(i, s, " ");
    }

    public static String getLpadB(int i, String s, String s1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int j = lengthB(s);
        int k = i - j;
        for(int l = 1; l <= k; l++)
            stringbuffer.append(s1);

        stringbuffer.append(s);
        return stringbuffer.toString();
    }

    public static String getLpadB(int i, String s)
    {
        return getLpadB(i, s, " ");
    }

    public static String sReplace(String s, String s1, String s2)
    {
        String s4 = new String(s2);
        int i = s2.indexOf(s);
        String s3;
        if(i > -1)
            s3 = "";
        else
            s3 = s2;
        for(; i > -1; i = s2.indexOf(s))
            if(i == s2.length() + 1)
            {
                s3 = s3.concat(s2.substring(0, s2.length() - 1).concat(s1));
                s2 = "";
            } else
            if(i > 0)
            {
                s3 = s3.concat(s2.substring(0, i).concat(s1));
                s2 = s2.substring(i + s.length(), s2.length());
            } else
            {
                s3 = s3.concat(s1);
                s2 = s2.substring(i + s.length(), s2.length());
            }

        if(!s2.equals(s4))
            return s3.concat(s2);
        else
            return s3;
    }

    public static boolean match(String s, String s1)
    {
        int i = 0;
        int j = 0;
        int k = s.length();
        int l = s1.length();
        int ai[] = new int[100];
        int i1 = 0;
        do
        {
            if(i == k)
            {
                if(j == l)
                    return true;
            } else
            {
                char c = s.charAt(i);
                if(j < l)
                {
                    if(c == '*')
                    {
                        ai[i1++] = i;
                        ai[i1++] = j + 1;
                        i++;
                        continue;
                    }
                    if(c == '?' || c == s1.charAt(j))
                    {
                        i++;
                        j++;
                        continue;
                    }
                } else
                if(c == '*')
                {
                    i++;
                    continue;
                }
            }
            if(i1 == 0)
                return false;
            j = ai[--i1];
            i = ai[--i1];
        } while(true);
    }

    public static int charCount(String s, char c)
    {
        String s1 = new String(s);
        int i = s1.indexOf(c);
        int j = 0;
        for(; i > 0; i = s1.indexOf(c))
        {
            j++;
            s1 = s1.substring(i + 1);
        }

        return j;
    }

    public static int charCount(String s, String s1)
    {
        String s2 = new String(s);
        int i = s2.indexOf(s1);
        int j = 0;
        for(; i > 0; i = s2.indexOf(s1))
        {
            j++;
            s2 = s2.substring(i + 1);
        }

        return j;
    }

    public static int charCount(double d, String s)
    {
        String s1 = String.valueOf(d);
        String s2 = new String(s1);
        int i = s2.indexOf(s);
        int j = 0;
        for(; i > 0; i = s2.indexOf(s))
        {
            j++;
            s2 = s2.substring(i + 1);
        }

        return j;
    }

    public static final boolean isAlphaNumeric(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isLetterOrDigit(s.charAt(i)))
                return false;

        return true;
    }

    public static final String[] split(String s, String s1)
    {
        Vector vector = new Vector();
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, s1); stringtokenizer.hasMoreTokens(); vector.addElement(stringtokenizer.nextToken()));
        String as[] = new String[vector.size()];
        vector.copyInto(as);
        return as;
    }

    public static final String join(String as[], String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < as.length; i++)
        {
            if(i > 0)
                stringbuffer.append(s);
            stringbuffer.append(as[i]);
        }

        return stringbuffer.toString();
    }

    public static final String justify(String s, int i)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\t \f\n", true);
        StringBuffer stringbuffer = new StringBuffer(500);
        int k = 0;
        boolean flag = false;
        boolean flag1 = true;
        while(stringtokenizer.hasMoreTokens())
        {
            String s1 = stringtokenizer.nextToken();
            if(s1.equals("\n"))
            {
                if(i != 0)
                    if(flag)
                    {
                        stringbuffer.append('\n');
                        stringbuffer.append('\n');
                        k = 0;
                        flag1 = true;
                        flag = false;
                    } else
                    {
                        flag = true;
                    }
            } else
            if(s1.equals(" ") || s1.equals("\t") || s1.equals("\f"))
            {
                flag = false;
            } else
            {
                flag = false;
                int j = s1.length();
                if(!flag1)
                    j++;
                if(k + j > i)
                {
                    stringbuffer.append('\n');
                    flag1 = true;
                    k = 0;
                }
                if(!flag1)
                    stringbuffer.append(' ');
                stringbuffer.append(s1);
                k += j;
                flag1 = false;
            }
        }
        String s2 = stringbuffer.toString();
        stringbuffer = null;
        return s2;
    }

    public static final String getClassName(Class class1)
    {
        String s = class1.getName();
        int i = s.lastIndexOf('.');
        return i >= 0 ? s.substring(++i) : s;
    }

    public static String getLapOutText(String s, String s1, String s2)
    {
        return getLapOutText(s, s1, s2, 1);
    }

    public static String getLapOutText(String s, String s1, String s2, int i)
    {
        String s3 = s;
        int j = 0;
        int k = 0;
        int l = 0;
        do
        {
            j = s3.indexOf(s1, k);
            l++;
            k = j + 1;
        } while(l < i && j >= 0);
        if(j < 0)
            return "";
        int i1 = s3.indexOf(s2, j);
        if(i1 < 0)
        {
            return "";
        } else
        {
            s3 = s3.substring(j + s1.length(), i1).trim();
            return s3;
        }
    }

    public static String asciiToKsc(String s)
    {
        try
        {
            if(s == null)
                return null;
            else
                return new String(s.getBytes("8859_1"), "KSC5601");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return null;
        }
    }

    public static String kscToAscii(String s)
    {
        try
        {
            if(s == null)
                return null;
            else
                return new String(s.getBytes("KSC5601"), "8859_1");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return null;
        }
    }

    /**/
    public static String nvl(String s, String s1)
    {
        if(!isNullOrEmpty(s))
            return s1;
        else
            return s;
    }


    /**
     * 문자열을 판단하여 null일 경우 빈 문자열을 반환한다.<br/>
     *
     */
    public static String nvl(String value, boolean isRemoveWhiteSpace)
    {
        if(value == null)
            return "";
        else
        {
            if(isRemoveWhiteSpace)
                return value.trim();
            else
                return value;
        }
    }


    /**
     * NULL 문자열 체크.
     *
     * @param strOrg 원본 문자열
     * @return String
     */
    public static String nvl(String strOrg)
    {
        if(strOrg != null && strOrg.length() > 0)
        {
            return strOrg.trim();
        }
        else
        {
            return "";
        }
    }

    public static String nvl(Object obj) {
        if (obj == null)
            return "";
        else
            return (String)obj;
    }

    /**
     * NULL 문자열 체크 int형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return int
     */
    public static int nvl(String strOrg, int nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Integer.parseInt(strValue);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * NULL 문자열 체크 long형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return int
     */
    public static long nvl(String strOrg, long nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Long.parseLong(strValue);
        }
        else
        {
            return nDef;
        }
    }


    /**
     * NULL 문자열 체크 float형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return float
     */
    public static float nvl(String strOrg, float nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Float.parseFloat(strValue);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * NULL 문자열 체크 double형.
     *
     * @param strOrg 원본문자열
     * @param nDef 기본값
     * @return double
     */
    public static double nvl(String strOrg, double nDef)
    {
        String strValue = nvl(strOrg);

        if(!"".equals(strValue))
        {
            return Double.parseDouble(strValue);
        }
        else
        {
            return nDef;
        }
    }



    public static String replace(String s, String s1, String s2)
    {
        StringBuffer stringbuffer = new StringBuffer(s.length() * 2);
        String s3 = s.toLowerCase();
        String s4 = s1.toLowerCase();
        int i = 0;
        for(boolean flag = false; i < s.length() && !flag;)
        {
            int j = s3.indexOf(s4, i);
            if(j == -1)
            {
                flag = true;
            } else
            {
                stringbuffer.append(s.substring(i, j) + s2);
                i = j + s1.length();
            }
        }

        if(i < s.length())
            stringbuffer.append(s.substring(i));
        return stringbuffer.toString();
    }

    /*
    public static String fileToString(String s)
    {
        if(IV.noData(s))
            return null;
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;
        FileReader filereader = null;
        BufferedReader bufferedreader = null;
        try
        {
            filereader = new FileReader(s);
            bufferedreader = new BufferedReader(filereader);
            int j;
            while((j = bufferedreader.read()) != -1)
            {
                stringbuffer.append((char)j);
                i++;
            }
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            String s1 = null;
            return s1;
        }
        catch(Exception exception)
        {
            String s2 = null;
            return s2;
        }
        finally
        {
            try
            {
                if(filereader != null)
                    filereader.close();
                if(bufferedreader != null)
                    bufferedreader.close();
            }
            catch(Exception exception2) { }
        }
        return stringbuffer.toString();
    }
	*/

    public static int cntInStr(String s, String s1)
    {
        int i = 0;
        int j = 0;
        do
        {
            j = s.indexOf(s1, j);
            if(j != -1)
            {
                i++;
                j++;
            } else
            {
                return i;
            }
        } while(true);
    }

    public static String uni20ToUni12(String s)
            throws UnsupportedEncodingException
    {
        if(s == null)
            return null;
        int i = s.length();
        char ac[] = new char[i];
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c < '\uAC00' || '\uD7A3' < c)
                ac[j] = c;
            else
                try
                {
                    byte abyte0[] = String.valueOf(c).getBytes("KSC5601");
                    if(abyte0.length != 2)
                    {
                        ac[j] = '\uFFFD';
                        System.err.println("Warning: Some of Unicode 2.0 hangul character was ignored.");
                    } else
                    {
                        ac[j] = (char)((13312 + ((abyte0[0] & 0xff) - 176) * 94 + (abyte0[1] & 0xff)) - 161);
                    }
                }
                catch(UnsupportedEncodingException unsupportedencodingexception) { }
        }

        return new String(ac);
    }

    public static String uni12ToUni20(String s)
    {
        if(s == null)
            return null;
        int i = s.length();
        char ac[] = new char[i];
        byte abyte0[] = new byte[2];
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c < '\u3400' || '\u4DFF' < c)
                ac[j] = c;
            else
            if('\u3D2E' <= c)
            {
                System.err.println("Warning: Some of Unicode 1.2 hangul character was ignored.");
                ac[j] = '\uFFFD';
            } else
            {
                try
                {
                    abyte0[0] = (byte)((c - 13312) / 94 + 176);
                    abyte0[1] = (byte)((c - 13312) % 94 + 161);
                    ac[j] = (new String(abyte0, "KSC5601")).charAt(0);
                }
                catch(UnsupportedEncodingException unsupportedencodingexception)
                {
                    throw new InternalError("Fatal Error: KSC5601 encoding is not supported.");
                }
            }
        }

        return new String(ac);
    }

    public static String getQuotedStr(String s)
    {
        if(s == null)
            return "";
        if(s.indexOf('\'') < 0)
            return s;
        StringBuffer stringbuffer = new StringBuffer(s);
        for(int i = 0; i < stringbuffer.length();)
            if(stringbuffer.charAt(i) == '\'')
            {
                stringbuffer.replace(i, i + 1, new String("''"));
                i += 2;
            } else
            {
                i++;
            }

        return new String(stringbuffer);
    }

    public static String getField(String s, int i)
    {
        return getField(s, i, ":");
    }

    public static String getField(String s, int i, String s1)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
        for(int j = 0; stringtokenizer.hasMoreTokens(); j++)
        {
            String s2 = stringtokenizer.nextToken();
            if(j == i)
                return s2;
        }

        return null;
    }

    public static int indexOfFirstNumber(String s)
    {
        String s1 = "";
        int i = 0;
        boolean flag = false;
        for(int j = 0; j < s.length(); j++)
        {
            String s2 = s.substring(j, j + 1);
            boolean flag1;
            try
            {
                i = Integer.parseInt(s2);
                flag1 = true;
            }
            catch(Exception exception)
            {
                flag1 = false;
            }
            if(!flag1)
                continue;
            i = j;
            break;
        }

        return i;
    }

    public static String cut(String s, int i)
    {
        boolean flag = false;
        String s1 = s;
        String s2 = "";
        String s3 = "";
        if(s == null)
            return s;
        if(s.length() <= i)
            return s;
        int j = s.indexOf("<");
        for(int k = s.indexOf(">"); j >= 0 && k >= 0; k = s.indexOf(">"))
        {
            if(j > 0)
                j = j;
            if(k < s.length())
                k++;
            s2 = s2 + s.substring(0, j);
            s = s.substring(k);
            j = s.indexOf("<");
        }

        if(s1.length() < i)
            return s1;
        if(s2.length() == 0)
            return s1.substring(0, i) + "...";
        if(s2.length() > 0 && s2.length() < i)
            return s1;
        else
            return replace(s1, s2, s2.substring(0, i) + "...");
    }

    public static String insertStr(String s, String s1, String s2)
    {
        String s3 = "";
        StringTokenizer stringtokenizer = new StringTokenizer(s2, "_");
        boolean flag = true;
        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == ' ')
                continue;
            flag = false;
            break;
        }

        if(flag)
            return s;
        if(s.length() <= 1)
            return "";
        int j = 0;
        try
        {
            while(stringtokenizer.hasMoreTokens())
            {
                int k = Integer.parseInt(stringtokenizer.nextToken());
                if(j + k > s.length())
                {
                    s3 = s3 + s.substring(j, s.length());
                } else
                {
                    s3 = s3 + s.substring(j, j + k);
                    j += k;
                }
                if(stringtokenizer.hasMoreTokens())
                    s3 = s3 + s1;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return s3;
    }

    public static String changeDateType(String s)
    {
        String s1 = null;
        String s2 = null;
        String s3 = null;
        String s4 = null;
        if(s == null)
            return " ";
        int i = s.trim().length();
        if(i == 8)
        {
            s2 = s.substring(0, 4);
            s3 = s.substring(4, 6);
            s4 = s.substring(6, 8);
        } else
        {
            return "can't convert";
        }
        s1 = s2 + "-" + s3 + "-" + s4;
        return s1;
    }

    public static String getSplitedFormat(String s, String s1, String s2)
    {
        String s3 = sReplace("\n", " ", s);
        if(!validateCheckForString(s3))
            return null;
        String as[] = split(s3, s1);
        int i = as.length;
        String s4 = "";
        if(i == 2)
        {
            if(as[i - 1].trim().length() == 0)
                return as[0].trim();
            s4 = as[0].trim() + s2;
        } else
        {
            if(i == 1)
                return as[0].trim();
            if(i > 2)
                s4 = as[0].trim() + s2;
        }
        return s4;
    }

    public static boolean validateCheckForString(String s)
    {
        return s != null && s.trim().length() != 0;
    }

    public static String cutString(String s, int i)
            throws Exception
    {
        int k = 0;
        boolean flag = false;
        String s1 = null;
        StringReader stringreader = new StringReader(s);
        StringWriter stringwriter = new StringWriter();
        int j;
        while((j = stringreader.read()) != -1)
        {
            stringwriter.write(j);
            if(!flag)
            {
                if(j == 38)
                    flag = true;
                else
                    k++;
            } else
            if(j == 35)
                k++;
            else
            if(j == 59)
                flag = false;
            else
            if(j == 38)
            {
                flag = true;
                k++;
            }
            if(k <= i)
                s1 = stringwriter.toString();
        }
        if(k < i)
        {
            return s;
        } else
        {
            s1 = s1.substring(0, s1.length() - 1);
            s1 = s1 + "...";
            return s1;
        }
    }

    public static String chkLow10(String s)
    {
        int i = Integer.parseInt(s);
        if(i < 10)
            return "0" + String.valueOf(i);
        else
            return String.valueOf(i);
    }

    public static String chkLow10(int i)
    {
        if(i < 10)
            return "0" + String.valueOf(i);
        else
            return String.valueOf(i);
    }

    public static String trim(String s)
    {
        int i = 0;
        char ac[] = s.toCharArray();
        int j = ac.length;
        int k;
        for(k = j; i < k && (ac[i] <= ' ' || ac[i] == '\u3000'); i++);
        for(; i < k && (ac[k - 1] <= ' ' || ac[k - 1] == '\u3000'); k--);
        return i <= 0 && k >= j ? s : s.substring(i, k);
    }

    /*
    public static String getMultiDateForHTML(String s, String s1)
    {
        if(!validateCheckForString(s))
            return null;
        String as[] = split(s, s1);
        int i = as.length;
        String s2 = "";
        for(int j = 0; j < i; j++)
            as[j] = DateFormatUtil.getFormatedDate(as[j].trim(), "/");

        if(i > 1)
            s2 = join(as, "<br>");
        else
        if(i == 1)
            return as[0].trim();
        return s2;
    }
    */


    public static String getMultiItemForHTML(String s, String s1)
    {
        if(!validateCheckForString(s))
            return null;
        String as[] = split(s.trim(), s1);
        int i = as.length;
        String s2 = "";
        if(i > 1)
            s2 = join(as, "<br>");
        else
        if(i == 1)
            return as[0].trim();
        return s2;
    }

    public static String head(String s, int i)
    {
        if(s == null)
            return "";
        String s1 = null;
        if(s.length() > i)
            s1 = s.substring(0, i) + "...";
        else
            s1 = s;
        return s1;
    }

    public static String tail(String s, int i)
    {
        if(s == null)
            return "";
        String s1 = null;
        if(s.length() > i)
            s1 = s.substring(0, i - 2) + " ...";
        else
            s1 = s;
        return s1;
    }

    public static String encodeHTMLSpecialChar(String s, int i)
    {
        switch(i)
        {
            default:
                break;

            case 1: // '\001'
                s = rplc(s, "'", "''");
                s = rplc(s, "<script", "<x-script");
                s = rplc(s, "</script", "</x-script");
                s = rplc(s, "<iframe", "<x-iframe");
                s = rplc(s, "</iframe", "</x-iframe");
                s = rplc(s, "<xmp", "<x-xmp");
                s = rplc(s, "</xmp", "</x-xmp");
                s = rplc(s, "<title", "<x-title");
                s = rplc(s, "</title", "</x-title");
                s = rplc(s, "<", "&lt;");
                s = rplc(s, "\"", "&quot;");
                break;

            case 2: // '\002'
                s = rplc(s, "'", "''");
                s = rplc(s, "<script", "<x-script");
                s = rplc(s, "</script", "</x-script");
                s = rplc(s, "<iframe", "<x-iframe");
                s = rplc(s, "</iframe", "</x-iframe");
                s = rplc(s, "<xmp", "<x-xmp");
                s = rplc(s, "</xmp", "</x-xmp");
                s = rplc(s, "<title", "<x-title");
                s = rplc(s, "</title", "</x-title");
                s = rplc(s, "\"", "&quot;");
                break;

            case 3: // '\003'
                s = rplc(s, "'", "''");
                s = rplc(s, "<script", "<x-script");
                s = rplc(s, "</script", "</x-script");
                s = rplc(s, "<iframe", "<x-iframe");
                s = rplc(s, "</iframe", "</x-iframe");
                s = rplc(s, "<xmp", "<x-xmp");
                s = rplc(s, "</xmp", "</x-xmp");
                s = rplc(s, "<title", "<x-title");
                s = rplc(s, "</title", "</x-title");
                break;

            case 11: // '\013'
                s = rplc(s, "''", "'");
                s = rplc(s, "&quot;", "\"");
                s = rplc(s, "<BR>", "");
                break;

            case 12: // '\f'
                s = rplc(s, "''", "'");
                break;

            case 13: // '\r'
                s = rplc(s, "''", "'");
                s = rplc(s, "&quot;", "\"");
                s = rplc(s, " ", "&nbsp;");
                s = rplc(s, "\n", "<br>");
                break;

            case 14: // '\016'
                s = rplc(s, "''", "'");
                break;

            case 15: // '\017'
                s = rplc(s, "''", "'");
                s = rplc(s, "\n", "<br>");
                s = rplc(s, "&quot;", "\"");
                break;

            case 9: // '\t'
                s = rplc(s, "'", "''");
                break;

            case 99: // 'c'
                s = rplc(s, "'", "''");
                s = rplc(s, "  ", " ");
                s = rplc(s, " or ", "^");
                s = rplc(s, " OR ", "^");
                s = rplc(s, " ^ ", "^");
                s = rplc(s, " and ", "&");
                s = rplc(s, " AND ", "&");
                s = rplc(s, " & ", "&");
                s = rplc(s, " ) ", ")");
                s = rplc(s, ") ", ")");
                s = rplc(s, " )", ")");
                s = rplc(s, " ( ", "(");
                s = rplc(s, " (", "(");
                s = rplc(s, "( ", "(");
                s = rplc(s, "or", "^");
                s = rplc(s, "OR", "^");
                s = rplc(s, "and", "&");
                s = rplc(s, "AND", "&");
                s = rplc(s, " + ", "&");
                s = rplc(s, ")^", ")");
                s = rplc(s, "^(", "(");
                s = rplc(s, " ", "&");
                s = rplc(s, "(", " or ( ");
                s = rplc(s, ")", " ) or ");
                s = rplc(s, " or ( or ( ", " or (( ");
                s = rplc(s, "  ) or ) or ", " )) or ");
                s = rplc(s, "^", " or ");
                s = rplc(s, "&", " and ");
                s = rplc(s, "or  and", "and");
                s = rplc(s, "and  and", "and");
                s = rplc(s, "and  or", "or");
                s = rplc(s, "or  or", "or");
                s = rplc(s, "or  and", "and");
                if(s.length() > 4 && s.substring(0, 4).equals(" or "))
                    s = s.substring(4, s.length());
                if(s.length() > 4 && s.substring(s.length() - 4, s.length()).equals(" or "))
                    s = s.substring(0, s.length() - 4);
                if(s.length() > 5 && s.substring(0, 5).equals(" and "))
                    s = s.substring(5, s.length());
                if(s.length() > 5 && s.substring(s.length() - 5, s.length()).equals(" and "))
                    s = s.substring(0, s.length() - 5);
                break;
        }
        return s;
    }

    public static double makeRound(double d)
    {
        int i = (int)(d * 10D + 0.5D);
        double d1 = i / 10D;
        return d1;
    }

    public static double makeRound2(double d)
    {
        int i = (int)(d * 100D + 0.5D);
        double d1 = i / 100D;
        return d1;
    }

    public static String decode(String s, String s1, String s2, String s3)
    {
        if(s.equals(s1))
            return s2;
        else
            return s3;
    }

    public static String decode(String s, String s1, String s2)
    {
        if(s.equals(s1))
            return s2;
        else
            return "";
    }

    public static long decode(long l, long l1, long l2, long l3)
    {
        if(l == l1)
            return l2;
        else
            return l3;
    }

    public static int decode(int i, int j, int k, int l)
    {
        if(i == j)
            return k;
        else
            return l;
    }

    public static String safeTrim(String s)
    {
        if(s != null)
        {
            String s1 = new String(s.trim());
            return s1;
        } else
        {
            return "";
        }
    }

    public static String cutTitle(String s, int i)
    {
        int j = s.length();
        int k = 0;
        int l = 0;
        while(l < j && k < i)
        {
            if(s.charAt(l++) < '\u0100')
            {
                k++;
                continue;
            }
            if(k >= i - 3)
                break;
            k += 2;
        }
        if(l < j)
        {
            s = s.substring(0, l);
            s = s + "...";
        }
        return s;
    }

    public static String cropByte(String s, int i, String s1)
    {
        if(s == null)
            return "";
        String s2 = s;
        int j = 0;
        int k = 0;
        try
        {
            if(s2.getBytes("MS949").length > i)
            {
                while(k + 1 < i)
                {
                    char c = s2.charAt(j);
                    k++;
                    j++;
                    if(c > '\177')
                        k++;
                }
                s2 = s2.substring(0, j) + s1;
            }
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s2;
    }

    public static void checkTime(long l, long l1, String s)
    {
        try
        {
            long l2 = Long.parseLong(makeCipherReverses(17L, "0", String.valueOf(l1))) - Long.parseLong(makeCipherReverses(17L, "0", String.valueOf(l)));
            System.out.println("================   " + s + "   ================");
            System.out.println("durationTime : " + l2 + " msecs");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /*
    public static long checkTime()
    {
        return Long.parseLong(DateFormatUtil.getToday("yyyyMMddHHmmssSS"));
    }
	*/

    public static String getCmd(String s)
            throws IOException
    {
        Process process = Runtime.getRuntime().exec(s);
        InputStream inputstream = process.getInputStream();
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
        String s1 = "";
        for(String s2 = ""; (s2 = bufferedreader.readLine()) != null;)
            s1 = s1 + s2 + "\n";

        bufferedreader.close();
        inputstream.close();
        return s1;
    }

    public static String wwwLink(String s)
    {
        if(s == null)
            return "";
        String s1 = s;
        boolean flag = false;
        boolean flag1 = false;
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("");
        int k;
        for(; s1.indexOf("http://") > -1; s1 = s1.substring(k))
        {
            int i = s1.indexOf("http://");
            k = s1.indexOf(" ", i);
            if(k > s1.indexOf(")", i) && s1.indexOf(")", i) > -1)
                k = s1.indexOf(")", i);
            if(k > s1.indexOf("<", i) && s1.indexOf("<", i) > -1)
                k = s1.indexOf("<", i);
            if(k == -1)
                k = s1.length();
            stringbuffer.append(s1.substring(0, i));
            if(i > 3 && s1.substring(i - 3, i).indexOf("=") > -1)
            {
                k = s1.indexOf("</a>", i) + 3;
                if(k == 2)
                    k = s1.indexOf(">", i);
                stringbuffer.append(s1.substring(i, k));
            } else
            {
                stringbuffer.append("<a href=\"" + s1.substring(i, k) + "\" target=\"_blank\" >");
                stringbuffer.append(s1.substring(i, k));
                stringbuffer.append("</a>");
            }
        }

        stringbuffer.append(s1);
        s1 = stringbuffer.toString();
        stringbuffer.setLength(0);
        int l;
        for(; s1.indexOf("www.") > -1; s1 = s1.substring(l))
        {
            int j = s1.indexOf("www.");
            l = s1.indexOf(" ", j);
            if(l > s1.indexOf(")", j) && s1.indexOf(")", j) > -1)
                l = s1.indexOf(")", j);
            if(l > s1.indexOf("<", j) && s1.indexOf("<", j) > -1)
                l = s1.indexOf("<", j);
            if(l == -1)
                l = s1.length();
            stringbuffer.append(s1.substring(0, j));
            if(j > 10 && s1.substring(j - 10, j).indexOf("=") > -1)
            {
                l = s1.indexOf("</a>", j) + 3;
                if(l == 2)
                    l = s1.indexOf(">", j);
                stringbuffer.append(s1.substring(j, l));
            } else
            {
                stringbuffer.append("<a href=\"http://" + s1.substring(j, l) + "\" target=\"_blank\" >");
                stringbuffer.append(s1.substring(j, l));
                stringbuffer.append("</a>");
            }
        }

        stringbuffer.append(s1);
        return stringbuffer.toString();
    }

    public static String getUrl(String s)
            throws Exception
    {
        InputStream inputstream = (new URL(s)).openStream();
        byte abyte0[] = new byte[64000];
        StringBuffer stringbuffer = new StringBuffer();
        int i;
        while((i = inputstream.read(abyte0, 0, abyte0.length)) > 0)
            stringbuffer.append(new String(abyte0, 0, i));
        inputstream.close();
        return stringbuffer.toString();
    }

    public static String toHtmlTag(String s)
    {
        int i = s.length();
        int j = 0;
        boolean flag = false;
        for(int k = 0; k < i; k++)
            if((s.charAt(k) == '<') | (s.charAt(k) == '>') | (s.charAt(k) == '&') | (s.charAt(k) == ' '))
                j++;

        StringBuffer stringbuffer = new StringBuffer(i + j * 5);
        for(int l = 0; l < i; l++)
            if(s.charAt(l) == '<')
                stringbuffer.append("&lt;");
            else
            if(s.charAt(l) == '>')
                stringbuffer.append("&gt;");
            else
            if(s.charAt(l) == '&')
                stringbuffer.append("&amp;");
            else
            if(s.charAt(l) == ' ')
            {
                if(l == 0)
                    stringbuffer.append("&nbsp;");
                else
                if(s.substring(l - 1, l).equals(" ") | s.substring(l - 1, l).equals("\n"))
                {
                    if(s.substring(l + 1, l + 2).equals(" "))
                        stringbuffer.append("&nbsp;");
                    else
                    if(s.substring(l - 2, l - 1).equals(" "))
                        stringbuffer.append(" ");
                    else
                        stringbuffer.append("&nbsp;");
                } else
                {
                    stringbuffer.append(" ");
                }
            } else
            {
                stringbuffer.append(s.charAt(l));
            }

        return stringbuffer.toString();
    }

    public static String[] getName(String s, int i)
    {
        Calendar calendar = Calendar.getInstance();
        int j = calendar.get(2) + 13;
        String as[] = new String[i];
        for(int k = 0; k < i; k++)
        {
            int l = (j - k) % 12;
            as[k] = s + "_" + (l != 0 ? l : 12);
        }

        return as;
    }

    /**
     * 시간 값을 이용하여 20자리 고유 문자열 반환
     * @return 숫자형태의 문자

    public static synchronized String getUniqueNumber()
    {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); //17 characters
    String sReturn = sdf.format(Calendar.getInstance().getTime());
    try
    {
    ++dummy;
    }
    catch(Exception e)
    {
    dummy = 0;
    }
    sReturn += padLeft(String.valueOf(dummy % 1000), 3, '0');

    return sReturn;
    }
     */

    /**
     * 문자열 왼쪽 패딩
     * @param value 적용시킬 문자열
     * @param width 패딩 길이
     * @param paddingChar 채울 문자
     * @return 왼쪽으로 패딩된 문자열
     */
    public static String padLeft(String value, int width, char paddingChar)
    {
        try
        {
            if(value.length() >= width)
                return value;

            String sReturn = value;
            while(sReturn.length() < width)
                sReturn = String.valueOf(paddingChar) + sReturn;

            return sReturn;
        }
        catch(Exception e)
        {
            return "";
        }
    }



    /**
     * 숫자형 문자열을 소수점을 포함하지 않은 3자리 comma형태로 formatting한다
     * 입력 : 변환될 숫자형 문자열
     * 출력 : 변형된 문자열. 만약 number로 null값이 넘어오면 null을 return한다.
     *        number가 숫자형 문자열이 아닌 경우
     * 예).addComma("20000000")==>20,000,000 , .addComma("20000000.21")==>20,000,000
     */
    public static String addComma(String number){
        return addComma(number,0);
    }

    public static String addComma(double number){
        return addComma(Double.toString(number),0);
    }

    /**
     * 숫자형 문자열을 소수점 자리수 만큼 소수점을 포함한 3자리 comma형태로 formatting한다
     * 입력:  number = 변환될 숫자형 문자열 ,prime = 보여줄 소수점자릿수
     * 출력:  String -  변형된 문자열 만약 number로 null값이 넘어오면 null을 return한다.
     *        number가 숫자형 문자열이 아닌 경우 prime이 0이하일 경우 소숫점 이하자리가 잘릴경우 반올림된다.
     * 예)IWConversion.addComma("20000000.2178",2)==> 20,,000,000.22
     */
    public static String addComma(String number, int prime){
        if(number==null)
        {
            return null;
        }
        // 소숫점 자리는 0이상이여야 한다.
        if(prime<0) throw new IllegalArgumentException("소수점자릿수는 0이상이 정수를 넣어야합니다.");
        //기본 콤마 형태
        String format="#,##0";
        //소숫점자리가 0 이상인경우 포맷형태에 .을 더한다.
        if(prime >0) format = format+".";

        //보여줄 소숫점 자리수만큼 포맷형태인 #을 더한다. 예)#,###.##
        for(int i=1 ; i<=prime ;i++ )
        {
            format=format+"#";
        }
        DecimalFormat nf= new DecimalFormat(format);

        try
        {
            //입력된 숫자를 포맷형식에 맞게 바꾼후 return한다.
            return nf.format(Double.parseDouble(number));
        }
        catch(NumberFormatException ne)
        {
            throw new NumberFormatException("\""+number+"\"는 숫자형태이어야 합니다.");
        }
    }

    /**
     * 문자열 길이 제한 두기
     */
    public static String getLimitLength(String origin, int len, String attach)
    {

        if (origin.length() <= len)
            return origin;
        return getLimitLength(origin, len) + attach;
    }
    public static String getLimitLength(String origin, int len)
    {
        String str = origin;

        if (str.length() <= len)
            return str;

        //str = str.replaceAll("&nbsp;", " ");
        str = str.substring(0, len);

        return str;
    }
}

