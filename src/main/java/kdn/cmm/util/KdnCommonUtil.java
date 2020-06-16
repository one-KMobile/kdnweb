package kdn.cmm.util;

import java.util.Calendar;
import java.util.Enumeration;

import javax.annotation.Resource;

import kdn.auth.service.KdnAuthService;
import kdn.cmm.box.Box;
import kdn.menu.service.KdnMenuService;

import org.springframework.stereotype.Component;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Component("kdnCommonUtil")
public class KdnCommonUtil {
	
    /** KdnAuthService */
    @Resource(name = "kdnAuthService")
    private KdnAuthService kdnAuthService;
    
    /** KdnMenuService */
    @Resource(name = "kdnMenuService")
    private KdnMenuService kdnMenuService;
    
	public PaginationInfo getWebPaging(Box box) throws Exception {
		PaginationInfo paginationInfo = new PaginationInfo();
 	
		paginationInfo.setCurrentPageNo(box.getInt("pageIndex",1));
		paginationInfo.setRecordCountPerPage(10); //페이지당 레코드 개수
		paginationInfo.setPageSize(10); //페이지사이즈

		box.put("firstIndex", paginationInfo.getFirstRecordIndex()); //첫페이지 인덱스
		box.put("lastIndex",paginationInfo.getLastRecordIndex()); //마지막페이지 인덱스
		box.put("recordCountPerPage",paginationInfo.getRecordCountPerPage()); //페이지당 레코드 개수
		
		String pageNo = box.getString("pageIndex");
	    if(pageNo != null && !(pageNo.equals(""))){
	    	box.put("pageIndex", pageNo);
	    }else{
	    	box.put("pageIndex", 1);
	    }
	    return paginationInfo;
	}
	
	/**
	 * <설명> 
	 * 오늘 날짜 구하기
	 * @return [String - today]
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	 
    public static String getTodayDate() {
		Calendar cal = Calendar.getInstance();
		Object year = cal.get(Calendar.YEAR);
		Object month = cal.get(Calendar.MONTH)+1;
		Object day = cal.get(Calendar.DAY_OF_MONTH);

    	if(cal.get(Calendar.MONTH)+1 < 10) {
    		month = "0".concat(month.toString());
    	}
    	if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
    		day = "0".concat(day.toString());
    	}
		
    	System.out.println("날짜 : " + year);
    	System.out.println("날짜 : " + month);
    	System.out.println("날짜 : " + day);
    	System.out.println("날짜통합 : " + year+month+day);
    	String today = year.toString() + month.toString() + day.toString(); 
    	
    	return today;
    }
    
	/**
	 * <설명> 
	 * 현재 시간 구하기
	 * @return [String - Time]
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	 
    public static String getTodayTime() {
    	Calendar cal = Calendar.getInstance();
		Object hour = cal.get(Calendar.HOUR_OF_DAY);
		Object minute = cal.get(Calendar.MINUTE);
		Object second = cal.get(Calendar.SECOND);
		
		System.out.println("날짜 : " + hour);
    	System.out.println("날짜 : " + minute);
    	System.out.println("날짜 : " + second);
    	
    	System.out.println(hour.toString() + minute.toString() + second.toString());
    	String time = hour.toString().concat(minute.toString()).concat(second.toString()); 
    	
    	return time;
    }
    
    public String getConvertCount1000(int count) {
    	String converCount = "";
    	if(count < 10) {
    		converCount = "000" + count;
		}else if(count < 100) {
			converCount = "00" + count;
		}else if(count < 1000) {
			converCount = "0" + count;
		}else {
			converCount = String.valueOf(count);
		}
    	return converCount;
    }
    
    public String getConverCount10000(int count) {
    	String converCount = "";
    	if(count < 10) {
    		converCount = "0000" + count;
		}else if(count < 100) {
			converCount = "000" + count;
		}else if(count < 1000) {
			converCount = "00" + count;
		}else if(count < 10000) {
			converCount = "0" + count;
		}else {
			converCount = String.valueOf(count);
		}
    	return converCount;
    }
    
    public static Box getEncoding(Box box) throws Exception{
    	 Enumeration<String> enumerationKey = box.keys();
    	 Enumeration<String> enumerationValue = box.elements();
    	 Box resultBox = new Box();
    	 //fileBox.put("file_subject", new String(box.getString("file_subject" +i).getBytes("8859_1"), "UTF-8"));
    	    while (enumerationKey.hasMoreElements()) {
    	        String key = (String) enumerationKey.nextElement();
    	        String value = (String) enumerationValue.nextElement();
    	        resultBox.put(key, new String(value.getBytes("UTF-8")));
    	    }
    	 return resultBox;
    }
    
    /*user 권한 체크*/
    public boolean isAuthUserInfo(Box box,String authType) throws Exception{ 
		int authUserChkCnt = 0;
		int isSubMenuCnt = 0;
		boolean result = false;
		
		if("read".equals(authType.trim()) || "write".equals(authType.trim())){
			box.put("authType", authType.trim());
		}
		
		try {
			isSubMenuCnt = kdnMenuService.isSubMenuCnt(box);
			if(isSubMenuCnt > 0){
				box.put("isSubMenu", "Y");
			}else{
				box.put("isSubMenu", "N");
			}
			authUserChkCnt = kdnAuthService.authUserInfoCnt(box);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(authUserChkCnt <=0){
			result = true;
		}
   	 return result;
   }
}
