package kdn.traking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import kdn.cmm.box.Box;

import org.springframework.stereotype.Component;


@Component("kdnTrakingMethod")
public class KdnTrakingMethod {
	
	public String getTimeToDB(Box trakingInfo) throws Exception{
		SimpleDateFormat parser = new SimpleDateFormat("yyyyMMddHHmm");
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
    	Date date = parser.parse(trakingInfo.getString("REG_DATE")); 
    	String formattedDate = formatter.format(date); 
    	System.out.println("reg_date 확인 : " + formattedDate);
    	return formattedDate;
	}
		
}
