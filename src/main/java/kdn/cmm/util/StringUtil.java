package kdn.cmm.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtil
{

    private StringUtil()
    {
    }

    public static String format(String s, String s1)
    {
        return format(s, new String[] {
            s1
        });
    }

    public static String format(String s, String s1, String s2)
    {
        return format(s, new String[] {
            s1, s2
        });
    }

    public static String format(String s, String s1, String s2, String s3)
    {
        return format(s, new String[] {
            s1, s2, s3
        });
    }

    public static String format(String s, String s1, String s2, String s3, String s4)
    {
        return format(s, new String[] {
            s1, s2, s3, s4
        });
    }

    public static String format(String s, String s1, String s2, String s3, String s4, String s5)
    {
        return format(s, new String[] {
            s1, s2, s3, s4, s5
        });
    }

    public static String format(String s, String as[])
    {
        for(int i = 0; i < as.length; i++)
            s = replaceStringAll2(s, (new StringBuilder()).append("{").append(i).append("}").toString(), as[i]);

        return s;
    }

    public static String lpad(int i, int j)
    {
        return lpad(String.valueOf(i), j, "0");
    }

    public static String lpad(String s, int i)
    {
        return lpad(s, i, " ");
    }

    public static String lpad(int i, int j, String s)
    {
        return lpad(String.valueOf(i), j, s);
    }

    public static String lpad(String s, int i, String s1)
    {
        if(s.length() >= i)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        int j = i - s.length();
        for(int k = 0; k < j; k++)
            stringbuffer.append(s1);

        stringbuffer.append(s);
        return stringbuffer.toString();
    }

    public static String lpad(int i, int j, char c)
    {
        return lpad(String.valueOf(i), j, c);
    }

    public static String lpad(String s, int i, char c)
    {
        if(s.length() >= i)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        int j = i - s.length();
        for(int k = 0; k < j; k++)
            stringbuffer.append(c);

        stringbuffer.append(s);
        return stringbuffer.toString();
    }

    public static String rpad(int i, int j)
    {
        return rpad(String.valueOf(i), j, "0");
    }

    public static String rpad(String s, int i)
    {
        return rpad(s, i, " ");
    }

    public static String rpad(int i, int j, String s)
    {
        return rpad(String.valueOf(i), j, s);
    }

    public static String rpad(String s, int i, String s1)
    {
        if(s.length() >= i)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        int j = i - s.length();
        stringbuffer.append(s);
        for(int k = 0; k < j; k++)
            stringbuffer.append(s1);

        return stringbuffer.toString();
    }

    public static String rpad(int i, int j, char c)
    {
        return rpad(String.valueOf(i), j, c);
    }

    public static String rpad(String s, int i, char c)
    {
        if(s.length() >= i)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        int j = i - s.length();
        stringbuffer.append(s);
        for(int k = 0; k < j; k++)
            stringbuffer.append(c);

        return stringbuffer.toString();
    }

    public static String upperFirstCase(String s)
    {
        if(s == null || s.length() < 1)
        {
            return s;
        } else
        {
            char ac[] = s.toCharArray();
            ac[0] = Character.toUpperCase(ac[0]);
            return new String(ac);
        }
    }

    public static String capitalize(String s)
    {
        return upperFirstCase(s);
    }

    public static String toCamel(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        String as[] = s.split("_");
        if(as.length <= 1)
            return s;
        stringbuffer.append(as[0]);
        for(int i = 1; i < as.length; i++)
            stringbuffer.append(upperFirstCase(as[i]));

        return stringbuffer.toString();
    }

    public static String getStackTrace(Throwable throwable)
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        PrintWriter printwriter = new PrintWriter(bytearrayoutputstream);
        throwable.printStackTrace(printwriter);
        printwriter.flush();
        return bytearrayoutputstream.toString();
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
    
    public static String getPathFileName(String filepath){
        int file = filepath.lastIndexOf("/");
        int ext = filepath.lastIndexOf(".");
            int length = filepath.length();
        String filename = filepath.substring(file+1,ext);
         
        String extname = filepath.substring(ext+1,length);
         
        String fullfilename = filename+"."+extname;
         
        return fullfilename;
    }
    
    //시간
    public static String[] getHour(){
    	String hour[] = new String[13]; 
    
    	for (int i = 0; i < 13; i++) {  
    		String hh = i + ""; 
    		if(hh.length() < 2){
    			hh = "0"+hh;
    		}
    		hour[i] = hh;
    	}
        return hour;
    }
    
  //분
    public static String[] getMinute(){
    	String minute[] = new String[60]; 
    
    	for (int i = 0; i < 60; i++) {  
    		String mm = i + ""; 
    		if(mm.length() < 2){
    			mm = "0"+mm;
    		}
    		minute[i] = mm;
    	}
        return minute;
    }
    
  //초
    public static String[] getSecond(){
    	String second[] = new String[60]; 
    
    	for (int i = 0; i < 60; i++) {  
    		String ss = i + ""; 
    		if(ss.length() < 2){
    			ss = "0"+ss;
    		}
    		second[i] = ss;
    	}
        return second;
    }
    // md5 암호화
    public static String digest(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	byte[] md5hash = new byte[32];
    	md.update(text.getBytes("iso-8859-1"), 0, text.length());
    	//md.update(text.getBytes("utf-8"), 0, text.length());
    	md5hash = md.digest();
    	return convertToHex(md5hash);
    }
    
    private static String convertToHex(byte[] b) {
    	StringBuilder result = new StringBuilder(32);
    	for (int i = 0; i < b.length; i++) {
    		result.append(
    		Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 ));
    	}
    	return result.toString();
    }
    
    //숫자로 시작하는지 첵크
    public static boolean isNumberStart(String str){
    	final  String regex = "^\\d.*";
    	return str.matches(regex);
    }
    
    //문자열이 영문으로 시작하는지 체크
    public static boolean  isEngStart(String str){
    	String isEngStartStr = str.substring(0, 1).toUpperCase();
    	boolean result;
    	if(Pattern.matches("[A-Z]", isEngStartStr)){
    		result = true;
    	}else{
    		result = false;
    	}
    	return result;
    }
    
    //문자열에 한글이 들어있는지 체크
    public static boolean  isKor(String str){
    	//String isKorStr = str.substring(0, str.length());
    	boolean result = false;
    	for (int i = 0; i < str.length(); i++) {
			if(Character.getType(str.charAt(i)) == 5 ){
				result = true;
				break;
			}
		}
    	return result;
    }
    
    //문자열 byte 체크
	public static boolean shortCutString(String str, int limit) {
		int len = str.length();
		int cnt = 0;

		for (int i = 0; i < len; i++) {
			if (str.charAt(i) < 256) { // 1바이트 문자라면
				cnt++; // 길이 1증가
			} else { // 2바이트 문자라면
				cnt += 2; // 길이 2증가
			}
		}
		System.out.println("바이트 길이 ========" + cnt);
		if (cnt > limit) {
			return false;
		}
		return true;
	}
	
    public static void main(String args[]){
    	try{
    		//System.out.println(digest("12345678yonamine"));
    		//String jsonString = URLUtil.getJsonString("http://appapi.dartsjapan.jp/check_mem_id_pw.php?country=jp&is_encrypted=1&login_id=yonamine&login_pw="+digest("12345678yonamine"),"GET",null,null);
    		//String jsonString = URLUtil.getJsonString("http://appapiv2.dartsjapan.jp:8124/v2/version/latest?device_type=android","GET",null,null);
    		//System.out.println(jsonString);
    		//Box result = Util.jsonToBox(jsonString); 
    		/*
    		if(result != null){
    			System.out.println("result : "+result.toString());
    			//System.out.println("result : "+result.get("mid").toString());
    		}
    		*/
    		//System.out.println(Util.getJsonObject(jsonString).get("mid") + " : " + Util.getJsonObject(jsonString).get("mem_type"));
    		String tStr = "001360511720124";
    		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 

    		Date date = new Date(Long.parseLong(tStr));
    		String dt = sdfCurrent.format(date);
    		System.out.println("dt : "+dt);
    		System.out.println("isNumberStart : "+isNumberStart(tStr));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private static final char[] zeroArray =
            "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();

    /**
     * Pads the supplied String with 0's to the specified length and returns
     * the result as a new String. For example, if the initial String is
     * "9999" and the desired length is 8, the result would be "00009999".
     * This type of padding is useful for creating numerical values that need
     * to be stored and sorted as character data. Note: the current
     * implementation of this method allows for a maximum <tt>length</tt> of
     * 64.
     *
     * @param string the original String to pad.
     * @param length the desired length of the new padded String.
     * @return a new String padded with the required number of 0's.
     */
    public static String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        }
        StringBuilder buf = new StringBuilder(length);
        buf.append(zeroArray, 0, length - string.length()).append(string);
        return buf.toString();
    }
    /**
     * Formats a Date as a fifteen character long String made up of the Date's
     * padded millisecond value.
     *
     * @return a Date encoded as a String.
     */
    public static String dateToMillis(Date date) {
        return zeroPadString(Long.toString(date.getTime()), 15);
    }
    
  //특수문자 제거 하기
    public static String StringReplace(String str){       
       //String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
       String str_imsi = "";
       String[] spChars = {"`", "-", "=", ";", "'", "/", "~", "!", "@", "#", "$", "%", "&", "|", ":", "\"", "<", ">"};
       
       for (int i = 0; i < spChars.length; i++) {
    	   str_imsi =str.replaceAll(spChars[i], "");
    	   str = str_imsi; 
       }
       return str;
    }
    
    public static String replaceStringAll2(String s, String s1, String s2)
    {
        if(s == null)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        String s3 = "";
        int i = s.length();
        int j = s1.length();
        char ac[] = new char[i];
        ac = s.toCharArray();
        for(int k = 0; k < i; k++)
        {
            String s4 = s.substring(k, k + 1);
            int l = k;
            if(i - l >= j)
                s4 = s.substring(l, l + j);
            else
                s4 = s.substring(l, l + 1);
            if(s4.equals(s1))
            {
                stringbuffer.append(s2);
                k += j - 1;
            } else
            {
                stringbuffer.append(ac[k]);
            }
        }

        return stringbuffer.toString();
    }
    
    public static String getAlertMessage(String text){
    	String ReusltScript = "";
    	ReusltScript += "<script type='text/javaScript' language='javascript'>";
    	ReusltScript += "function messageCall(){";
        ReusltScript += "alert('"+ text +"');";
        ReusltScript += "}";
        ReusltScript += "</script>";
    	return ReusltScript;
    }
    
}