package kdn.admin.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import kdn.cmm.box.Box;

import org.springframework.stereotype.Component;


@Component("kdnAdminMethod")
public class KdnAdminMethod {
	
	 public static String getShortMacAddress() {
	        String value = "";

	        try {
	            value = getMacAddress();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        value = value.replaceAll("-", "");

	        return value;
	    }

	/**
	     * 현재 컴퓨터의 맥 주소를 리턴한다.
	     * 
	     * @return MAC Address
	     * @throws IOException
	     */
	    public final static String getMacAddress() throws IOException {
	        String os = System.getProperty("os.name");

	        if (os.startsWith("Windows")) {
	            return ParseMacAddress(windowsRunIpConfigCommand());
	        } else if (os.startsWith("Linux")) {
	            return ParseMacAddress(linuxRunIfConfigCommand());
	        } else {
	            throw new IOException("unknown operating system: " + os);
	        }

	    }

	    /*
	     * Linux 에 있는 네트워크 설정 값들을 문자열로 불러온다.
	     */

	    private final static String linuxRunIfConfigCommand() throws IOException {
	        Process p = Runtime.getRuntime().exec("ifconfig");
	        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

	        StringBuffer buffer = new StringBuffer();
	        for (;;) {
	            int c = stdoutStream.read();
	            if (c == -1)
	                break;
	            buffer.append((char) c);
	        }
	        String outputText = buffer.toString();

	        stdoutStream.close();

	        return outputText;
	    }

	    /*
	     * Windows에 있는 네트워크 설정값들을 문자열로 가져온다.
	     */

	    private final static String windowsRunIpConfigCommand() throws IOException {
	        Process p = Runtime.getRuntime().exec("ipconfig /all");
	        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

	        StringBuffer buffer = new StringBuffer();
	        for (;;) {
	            int c = stdoutStream.read();
	            if (c == -1)
	                break;
	            buffer.append((char) c);
	        }
	        String outputText = buffer.toString();

	        stdoutStream.close();

	        return outputText;
	    }

	    /**
	     * 문자열에서  패턴에 맞는 문자열 즉 맥주소를 뽑아낸다.
	     * @param text 검사할 문자열
	     * @return 맥 주소
	     */
	    public static String ParseMacAddress(String text) {
	        String result = null;
	        String[] list = text.split("\\p{XDigit}{2}(-\\p{XDigit}{2}){5}");
	        int index = 0;
	        for (String str : list) {
	            if (str.length() < text.length()) {
	                index = str.length();
	                result = text.substring(index, index + 17);
	                if (!result.equals("00-00-00-00-00-00")) {
	                    break;
	                }
	                text = text.substring(index + 17);

	            }
	        }
	        return result;
	    }
	    
	    /**
		 * 서버의 있는 user_email, user_hp의 값을 자바스크립트의 맞게 컨버터
		 * @param [box]
		 * @exception Exception
		 * @author [정현도] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */	    
	    public void voidConvertClient(Box viewBox) {
	    	//email
	    	String email = viewBox.getString("user_email");
    		viewBox.put("first_email", email.substring(0, email.lastIndexOf("@")));
    		viewBox.put("second_email", email.substring(email.lastIndexOf("@")+1, email.length()));
    		//phone
    		String phone = viewBox.getString("user_hp");
    		//viewBox.put("news_agency", phone.substring(0, phone.lastIndexOf("_")));
    		viewBox.put("first_phone", phone.substring(0, phone.indexOf("-")));
    		viewBox.put("second_phone", phone.substring(phone.indexOf("-")+1, phone.lastIndexOf("-")));
    		viewBox.put("third_phone", phone.substring(phone.lastIndexOf("-")+1, phone.length()));
	    }
	    
	    /**
		 * 클라이언트의 있는 first_email, second_email, news_agency, first_phone, second_phone, third_phone의 값을 서버의 맞게 컨버터
		 * @param [box]
		 * @exception Exception
		 * @author [정현도] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */	    	    
	    public void voidConvertServer(Box box) {
	    	if(!"".equals(box.getString("first_tel"))) {
	    		//tel
	    		box.put("user_tel", box.getString("first_tel")
	    			.concat("-").concat(box.getString("second_tel")).concat("-").concat(box.getString("third_tel")));
	    	}
	    	//email
    		box.put("user_email", box.getString("first_email").concat("@").concat(box.getString("second_email")));
    		//phone
    		box.put("user_hp", box.getString("first_phone")
    			.concat("-").concat(box.getString("second_phone")).concat("-").concat(box.getString("third_phone")));
	    }
}
