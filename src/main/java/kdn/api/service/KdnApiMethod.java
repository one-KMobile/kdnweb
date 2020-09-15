package kdn.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.util.FileManagerService;
import kdn.cmm.util.KdnCommonUtil;
import kdn.login.web.LoginMethod;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import egovframework.rte.fdl.property.EgovPropertyService;

@Component("kdnApiMethod")
public class KdnApiMethod {
	
	@Resource(name="loginMethod")
	private LoginMethod loginMethod;
	
	@Autowired
	@Qualifier("kdnApiService")
	private KdnApiService kdnApiService;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
	
	@Resource(name = "fileManagerService")
	private FileManagerService fileManagerService;

    /**
 	 * 위도, 경도를 이용한 주소 검색
 	 * @param String - LATITUDE, LONGITUDE
 	 * @return String - ADDRESS
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public String getAddressByLatitudeLongitude(String latitude, String longitude)throws Exception {
		String BASE_GEOCODER_URL = "http://maps.google.com/maps/api/geocode/json?";
		String GEOCODER_REQUEST = BASE_GEOCODER_URL + "latlng="+latitude+","+longitude+"&language=ko&sensor=false";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(GEOCODER_REQUEST).openStream(), "UTF-8"));

        String line = "";
        String jsondata = "";
        while ((line = reader.readLine()) != null) {
        	jsondata += line;
        }
        
        JSONObject jsonObject = new JSONObject(); 
        jsonObject = new JSONObject(jsondata);
		String address = "";
		if(((JSONArray)jsonObject.get("results")).length() != 0){
			address = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getString("formatted_address");
		}
		
        return address;
	}
	
	public String getTransTowerIdx(String tower_idx) throws Exception {
		String towerIdx = null;
		if(tower_idx.lastIndexOf("호") != -1) {
			towerIdx =tower_idx.substring(0, tower_idx.lastIndexOf("호")).concat("호");
		}else {
			tower_idx = tower_idx.concat("호");
		}
		return towerIdx;
	}
	
	public List<Box> getScheduleMethod(Box box, HttpServletRequest request) throws Exception {
		box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
     	box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));

     	System.out.println("################# cycle_ym ################# "+box.getString("cycle_ym"));
     	//날짜 계산
     	if("".equals(box.getString("cycle_ym"))) {
     		Calendar cal = Calendar.getInstance();
     		Object year = cal.get(Calendar.YEAR);
     		box.put("start_date", year.toString().concat("01"));
     		box.put("end_date", year.toString().concat("12"));
     	}else {
     		box.put("start_date", box.getString("cycle_ym"));
     		box.put("end_date", box.getString("cycle_ym"));
     	}
     	System.out.println("################# start_date ################# "+box.getString("start_date"));
     	System.out.println("################# end_date ################# "+box.getString("end_date"));
     	//선로의 의한 스케줄정보 호출
     	List<Box> scheduleList = kdnApiService.getTracksInSchedule(box);
     	/*for(int i=0; i<scheduleList.size(); i++) {
     		Box scheduleBox = scheduleList.get(i);
     		String address = getAddressByLatitudeLongitude(scheduleBox.getString("LATITUDE"), scheduleBox.getString("LONGITUDE"));
     		//scheduleBox.put("FST_BIZPLC_NAME", sessionBox.getString("fst_bizplc_name"));
     		//scheduleBox.put("SCD_BIZPLC_NAME", sessionBox.getString("scd_bizplc_name"));
     		scheduleBox.put("ADDRESS", address);
     		resultScheduleList.add(scheduleBox);
     	}*/
     	return scheduleList;
	}
	
/*	public HSSFCell scheduleCell(HSSFRow row, HSSFCellStyle titlestyle) throws Exception {
		//Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("TOWER_IDX");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("CYCLE_YM");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("INS_SN");
	    cell3.setCellStyle(titlestyle);
	    
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("FNCT_LC_NO");
	    cell4.setCellStyle(titlestyle);
	    
	    HSSFCell cell5 = row.createCell((int)4);
	    cell5.setCellValue("FNCT_LC_DTLS");
	    cell5.setCellStyle(titlestyle);
	    
	    HSSFCell cell6 = row.createCell((int)5);
	    cell6.setCellValue("INS_TY_CD");
	    cell6.setCellStyle(titlestyle);
	    
	    HSSFCell cell7 = row.createCell((int)6);
	    cell7.setCellValue("NM");
	    cell7.setCellStyle(titlestyle);
	    
	    HSSFCell cell8 = row.createCell((int)7);
	    cell8.setCellValue("EQP_NO");
	    cell8.setCellStyle(titlestyle);
	    
	    HSSFCell cell9 = row.createCell((int)8);
	    cell9.setCellValue("EQP_NM");
	    cell9.setCellStyle(titlestyle);
	    
	    HSSFCell cell10 = row.createCell((int)9);
	    cell10.setCellValue("LATITUDE");
	    cell10.setCellStyle(titlestyle);
	    
	    HSSFCell cell11 = row.createCell((int)10);
	    cell11.setCellValue("LONGITUDE");
	    cell11.setCellStyle(titlestyle);
	    
	    HSSFCell cell12 = row.createCell((int)11);
	    cell12.setCellValue("UNITY_INS_NO");
	    cell12.setCellStyle(titlestyle);

	}*/
	
	public void getExcelAir(HSSFSheet sheet, HSSFWorkbook workbook, HttpServletRequest request) throws Exception {
		workbook.setSheetName(6, "항공장애");
		//Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("EQP_NO");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("TOWER_IDX");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("FLIGHT_LMP_KND");
	    cell3.setCellStyle(titlestyle);
	    
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("FLIGHT_LMP_KND_NM");
	    cell4.setCellStyle(titlestyle);
	    
	    HSSFCell cell5 = row.createCell((int)4);
	    cell5.setCellValue("SRCELCT_KND");
	    cell5.setCellStyle(titlestyle);
	    
	    HSSFCell cell6 = row.createCell((int)5);
	    cell6.setCellValue("SRCELCT_KND_NM");
	    cell6.setCellStyle(titlestyle);
	    
	    HSSFCell cell7 = row.createCell((int)6);
	    cell7.setCellValue("FLIGHT_LMP_NO");
	    cell7.setCellStyle(titlestyle);

	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    
	    //세션정보
	    Box sessionBox = loginMethod.getSessionKey(request);
	    Box box = new Box();
    	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
    	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
    	box.put("fnct_lc_ty_nm", "가공선로 기능위치");
      	List<Box> tracksList = kdnApiService.getTracksList(box);
      	StringBuffer sb = new StringBuffer();
      	sb.append("'");
    	for(int i=0; i<tracksList.size(); i++) {
    		Box tracksBox = tracksList.get(i);
    		sb.append(tracksBox.getString("FNCT_LC_NO")).append("','");
    	}
      	
      	String trackAllList = sb.substring(0, sb.length()-2);
      	box.put("fnct_lc_no", trackAllList);
	    List<Box> list = kdnApiService.getErrorAir(box);
	    for (int i=0; i<list.size();i++){
	    	Box listBox = list.get(i);
	    	//voidConvertKorean(scheduleBox);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(listBox.getString("EQP_NO"));
	        cell1.setCellStyle(styleCenter);
	          
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(listBox.getString("TOWER_IDX"));
	        cell2.setCellStyle(style);
	        
	        cell3 = row.createCell((int)2);
	        cell3.setCellValue(listBox.getString("FLIGHT_LMP_KND"));
	        cell3.setCellStyle(style);
	          
	        cell4 = row.createCell((int)3);
	        cell4.setCellValue(listBox.getString("FLIGHT_LMP_KND_NM"));
	        cell4.setCellStyle(style);
	        
	        cell5 = row.createCell((int)4);
	        cell5.setCellValue(listBox.getString("SRCELCT_KND"));
	        cell5.setCellStyle(style);  
	        
	        cell6 = row.createCell((int)5);
	        cell6.setCellValue(listBox.getString("SRCELCT_KND_NM"));
	        cell6.setCellStyle(style);  
	        
	        cell7 = row.createCell((int)6);
	        cell7.setCellValue(listBox.getString("FLIGHT_LMP_NO"));
	        cell7.setCellStyle(style);  
	        
	    }
	}
	
	public void getExcelPoor(HSSFSheet sheet, HSSFWorkbook workbook, HttpServletRequest request) throws Exception {
		workbook.setSheetName(5, "불량애자");
		//Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("EQP_NO");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("EQP_NM");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("TOWER_IDX");
	    cell3.setCellStyle(titlestyle);
	    
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("INSBTY_LFT");
	    cell4.setCellStyle(titlestyle);
	    
	    HSSFCell cell5 = row.createCell((int)4);
	    cell5.setCellValue("INSBTY_RIT");
	    cell5.setCellStyle(titlestyle);
	    
	    HSSFCell cell6 = row.createCell((int)5);
	    cell6.setCellValue("CL_NO");
	    cell6.setCellStyle(titlestyle);
	    
	    HSSFCell cell7 = row.createCell((int)6);
	    cell7.setCellValue("CL_NM");
	    cell7.setCellStyle(titlestyle);
	    
	    HSSFCell cell8 = row.createCell((int)7);
	    cell8.setCellValue("TY_SECD");
	    cell8.setCellStyle(titlestyle);
	    
	    HSSFCell cell9 = row.createCell((int)8);
	    cell9.setCellValue("TY_SECD_NM");
	    cell9.setCellStyle(titlestyle);
	    
	    HSSFCell cell10 = row.createCell((int)9);
	    cell10.setCellValue("PHS_SECD");
	    cell10.setCellStyle(titlestyle);
	    
	    HSSFCell cell11 = row.createCell((int)10);
	    cell11.setCellValue("INSR_EQP_NO");
	    cell11.setCellStyle(titlestyle);
	    
	    HSSFCell cell12 = row.createCell((int)11);
	    cell12.setCellValue("INSR_NM");
	    cell12.setCellStyle(titlestyle);
	    
	    HSSFCell cell13 = row.createCell((int)12);
	    cell13.setCellValue("INS_CNT");
	    cell13.setCellStyle(titlestyle);
	    
	    HSSFCell cell14 = row.createCell((int)13);
	    cell14.setCellValue("PROD_YMD");
	    cell14.setCellStyle(titlestyle);
	    
	    HSSFCell cell15 = row.createCell((int)14);
	    cell15.setCellValue("PROD_COMP");
	    cell15.setCellStyle(titlestyle);
	    
	    HSSFCell cell16 = row.createCell((int)15);
	    cell16.setCellValue("PHS_SECD_NM");
	    cell16.setCellStyle(titlestyle);

	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    
	    //세션정보
	    Box sessionBox = loginMethod.getSessionKey(request);
	    Box box = new Box();
    	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
    	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));

	    List<Box> list = kdnApiService.getPoorInsulators(box);
	    for (int i=0; i<list.size();i++){
	    	Box listBox = list.get(i);
	    	//voidConvertKorean(scheduleBox);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(listBox.getString("EQP_NO"));
	        cell1.setCellStyle(styleCenter);
	          
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(listBox.getString("EQP_NM"));
	        cell2.setCellStyle(style);
	        
	        cell3 = row.createCell((int)2);
	        cell3.setCellValue(listBox.getString("TOWER_IDX"));
	        cell3.setCellStyle(style);
	          
	        cell4 = row.createCell((int)3);
	        cell4.setCellValue(listBox.getString("INSBTY_LFT"));
	        cell4.setCellStyle(style);
	        
	        cell5 = row.createCell((int)4);
	        cell5.setCellValue(listBox.getString("INSBTY_RIT"));
	        cell5.setCellStyle(style);  
	        
	        cell6 = row.createCell((int)5);
	        cell6.setCellValue(listBox.getString("CL_NO"));
	        cell6.setCellStyle(style);  
	        
	        cell7 = row.createCell((int)6);
	        cell7.setCellValue(listBox.getString("CL_NM"));
	        cell7.setCellStyle(style);  
	        
	        cell8 = row.createCell((int)7);
	        cell8.setCellValue(listBox.getString("TY_SECD"));
	        cell8.setCellStyle(style); 
	        
	        cell9 = row.createCell((int)8);
	        cell9.setCellValue(listBox.getString("TY_SECD_NM"));
	        cell9.setCellStyle(style);
	          
	        cell10 = row.createCell((int)9);
	        cell10.setCellValue(listBox.getString("PHS_SECD"));
	        cell10.setCellStyle(style);
	        
	        cell11 = row.createCell((int)10);
	        cell11.setCellValue(listBox.getString("INSR_EQP_NO"));
	        cell11.setCellStyle(style);  
	        
	        cell12 = row.createCell((int)11);
	        cell12.setCellValue(listBox.getString("INSR_NM"));
	        cell12.setCellStyle(style); 
	        
	        cell13 = row.createCell((int)12);
	        cell13.setCellValue(listBox.getString("INS_CNT"));
	        cell13.setCellStyle(style);
	          
	        cell14 = row.createCell((int)13);
	        cell14.setCellValue(listBox.getString("PROD_YMD"));
	        cell14.setCellStyle(style);
	        
	        cell15 = row.createCell((int)14);
	        cell15.setCellValue(listBox.getString("PROD_COMP"));
	        cell15.setCellStyle(style);  
	        
	        cell16 = row.createCell((int)15);
	        cell16.setCellValue(listBox.getString("PHS_SECD_NM"));
	        cell16.setCellStyle(style);
	    }
	}
	
	public void getExcelWire(HSSFSheet sheet, HSSFWorkbook workbook, HttpServletRequest request) throws Exception {
		workbook.setSheetName(4, "전선접속");
		//Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("EQP_NO");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("EQP_NM");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("FNCT_LC_NO");
	    cell3.setCellStyle(titlestyle);
	    
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("FNCT_LC_DTLS");
	    cell4.setCellStyle(titlestyle);
	    
	    HSSFCell cell5 = row.createCell((int)4);
	    cell5.setCellValue("TOWER_IDX");
	    cell5.setCellStyle(titlestyle);
	    
	    HSSFCell cell6 = row.createCell((int)5);
	    cell6.setCellValue("TTM_LOAD");
	    cell6.setCellStyle(titlestyle);
	    
	    HSSFCell cell7 = row.createCell((int)6);
	    cell7.setCellValue("CONT_NUM");
	    cell7.setCellStyle(titlestyle);
	    
	    HSSFCell cell8 = row.createCell((int)7);
	    cell8.setCellValue("SN");
	    cell8.setCellStyle(titlestyle);
	    
	    HSSFCell cell9 = row.createCell((int)8);
	    cell9.setCellValue("POWER_NO_C1");
	    cell9.setCellStyle(titlestyle);
	    
	    HSSFCell cell10 = row.createCell((int)9);
	    cell10.setCellValue("POWER_NO_C2");
	    cell10.setCellStyle(titlestyle);
	    
	    HSSFCell cell11 = row.createCell((int)10);
	    cell11.setCellValue("POWER_NO_C3");
	    cell11.setCellStyle(titlestyle);

	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    
	    //세션정보
	    Box sessionBox = loginMethod.getSessionKey(request);
	    Box box = new Box();
    	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
    	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));

	    List<Box> list = kdnApiService.getCircuitList(box);
	    for (int i=0; i<list.size();i++){
	    	Box listBox = list.get(i);
	    	//voidConvertKorean(scheduleBox);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(listBox.getString("EQP_NO"));
	        cell1.setCellStyle(styleCenter);
	          
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(listBox.getString("EQP_NM"));
	        cell2.setCellStyle(style);
	        
	        cell3 = row.createCell((int)2);
	        cell3.setCellValue(listBox.getString("FNCT_LC_NO"));
	        cell3.setCellStyle(style);
	          
	        cell4 = row.createCell((int)3);
	        cell4.setCellValue(listBox.getString("FNCT_LC_DTLS"));
	        cell4.setCellStyle(style);
	        
	        cell5 = row.createCell((int)4);
	        cell5.setCellValue(listBox.getString("TOWER_IDX"));
	        cell5.setCellStyle(style);  
	        
	        cell6 = row.createCell((int)5);
	        cell6.setCellValue(listBox.getString("TTM_LOAD"));
	        cell6.setCellStyle(style);  
	        
	        cell7 = row.createCell((int)6);
	        cell7.setCellValue(listBox.getString("CONT_NUM"));
	        cell7.setCellStyle(style);  
	        
	        cell8 = row.createCell((int)7);
	        cell8.setCellValue(listBox.getString("SN"));
	        cell8.setCellStyle(style); 
	        
	        cell9 = row.createCell((int)8);
	        cell9.setCellValue(listBox.getString("POWER_NO_C1"));
	        cell9.setCellStyle(style);
	          
	        cell10 = row.createCell((int)9);
	        cell10.setCellValue(listBox.getString("POWER_NO_C2"));
	        cell10.setCellStyle(style);
	        
	        cell11 = row.createCell((int)10);
	        cell11.setCellValue(listBox.getString("POWER_NO_C3"));
	        cell11.setCellStyle(style);  
	    }
	}
	
	public void getExcelTower(HSSFSheet sheet, HSSFWorkbook workbook, HttpServletRequest request) throws Exception {
		workbook.setSheetName(3, "송전설비");
		//Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0);
	    cell1.setCellValue("FNCT_LC_DTLS");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1 );
	    cell2.setCellValue("TOWER_IDX");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("EQP_NO");
	    cell3.setCellStyle(titlestyle);
	    
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("FNCT_LC_NO");
	    cell4.setCellStyle(titlestyle);
	    
	    HSSFCell cell5 = row.createCell((int)4);
	    cell5.setCellValue("EQP_NM");
	    cell5.setCellStyle(titlestyle);
	    
	    HSSFCell cell6 = row.createCell((int)5);
	    cell6.setCellValue("LATITUDE");
	    cell6.setCellStyle(titlestyle);
	    
	    HSSFCell cell7 = row.createCell((int)6);
	    cell7.setCellValue("LONGITUDE");
	    cell7.setCellStyle(titlestyle);
	    
	    HSSFCell cell8 = row.createCell((int)7);
	    cell8.setCellValue("ADDRESS");
	    cell8.setCellStyle(titlestyle);

	    HSSFCell cell9 = row.createCell((int)8);
	    cell9.setCellValue("EQP_TY_CD_NM");
	    cell9.setCellStyle(titlestyle);
	    
	    HSSFCell cell10 = row.createCell((int)9);
	    cell10.setCellValue("CONT_NUM");
	    cell10.setCellStyle(titlestyle);

	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    
	    Box sessionBox = loginMethod.getSessionKey(request);
	    Box box = new Box();
    	box.put("fnct_lc_ty_nm", "가공선로 기능위치");
    	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
    	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
      	List<Box> tracksList = kdnApiService.getTracksList(box);
      	StringBuffer sb = new StringBuffer();
      	sb.append("'");
    	for(int i=0; i<tracksList.size(); i++) {
    		Box tracksBox = tracksList.get(i);
    		sb.append(tracksBox.getString("FNCT_LC_NO")).append("','");
    	}
      	
      	String trackAllList = sb.substring(0, sb.length()-2);
      	if("".equals(box.getString("fnct_lc_no"))) {
      		box.put("fnct_lc_no", trackAllList);
      	}else {
      		box.put("fnct_lc_no", "'".concat(box.getString("fnct_lc_no")).concat("'"));
      	}
	    List<Box> list = kdnApiService.getSearchedTransTower(box);
	    for (int i=0; i<list.size();i++){
	    	Box listBox = list.get(i);
	    	//voidConvertKorean(scheduleBox);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(listBox.getString("FNCT_LC_DTLS"));
	        cell1.setCellStyle(style);
	        
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(listBox.getString("TOWER_IDX"));
	        cell2.setCellStyle(styleCenter);        
	          
	        cell3 = row.createCell((int)2);
	        cell3.setCellValue(listBox.getString("EQP_NO"));
	        cell3.setCellStyle(style);
	        
	        cell4 = row.createCell((int)3);
	        cell4.setCellValue(listBox.getString("FNCT_LC_NO"));
	        cell4.setCellStyle(style);	
	        
	        cell5 = row.createCell((int)4);
	        cell5.setCellValue(listBox.getString("EQP_NM"));
	        cell5.setCellStyle(style);  
	        
	        cell6 = row.createCell((int)5);
	        cell6.setCellValue(listBox.getString("LATITUDE"));
	        cell6.setCellStyle(style);  
	        
	        cell7 = row.createCell((int)6);
	        cell7.setCellValue(listBox.getString("LONGITUDE"));
	        cell7.setCellStyle(style);  
	        
	        cell8 = row.createCell((int)7);
	        cell8.setCellValue(listBox.getString("ADDRESS"));
	        cell8.setCellStyle(style);  
	        
	        cell9 = row.createCell((int)8);
	        cell9.setCellValue(listBox.getString("EQP_TY_CD_NM"));
	        cell9.setCellStyle(style); 
	        
	        cell10 = row.createCell((int)9);
	        cell10.setCellValue(listBox.getString("CONT_NUM"));
	        cell10.setCellStyle(style);  

	    }
	}
	
	public void getExcelTracks(HSSFSheet sheet, HSSFWorkbook workbook, HttpServletRequest request) throws Exception {
		workbook.setSheetName(2, "선로");
		//Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("FNCT_LC_NO");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("FNCT_LC_DTLS");
	    cell2.setCellStyle(titlestyle);

	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    
	    Box box = loginMethod.getSessionKey(request);
	    List<Box> list = kdnApiService.getTracksList(box);
	    for (int i=0; i<list.size();i++){
	    	Box listBox = list.get(i);
	    	//voidConvertKorean(scheduleBox);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(listBox.getString("FNCT_LC_NO"));
	        cell1.setCellStyle(styleCenter);
	          
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(listBox.getString("FNCT_LC_DTLS"));
	        cell2.setCellStyle(style);

	    }
	      
	}
	
	public void getExcelCode(HSSFSheet sheet, HSSFWorkbook workbook) throws Exception {
		workbook.setSheetName(1, "코드");
		//Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("GROUP_CODE_ID");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("GROUP_CODE_NAME");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("CODE_ID");
	    cell3.setCellStyle(titlestyle);
	    
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("CODE_NAME");
	    cell4.setCellStyle(titlestyle);

	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    
	    Box box = new Box();
	    List<Box> list = kdnApiService.getApiCodeInfoList(box);
	    for (int i=0; i<list.size();i++){
	    	Box listBox = list.get(i);
	    	//voidConvertKorean(scheduleBox);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(listBox.getString("GROUP_CODE_ID"));
	        cell1.setCellStyle(styleCenter);
	          
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(listBox.getString("GROUP_CODE_NAME"));
	        cell2.setCellStyle(style);
	        
	        cell3 = row.createCell((int)2);
	        cell3.setCellValue(listBox.getString("CODE_ID"));
	        cell3.setCellStyle(style);
	          
	        cell4 = row.createCell((int)3);
	        cell4.setCellValue(listBox.getString("CODE_NAME"));
	        cell4.setCellStyle(style);

	    }
	      
	}
	
	public String getExcelSchedule(HttpServletRequest request) throws Exception {
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    
		    HSSFSheet sheet = workbook.createSheet("스케줄");
		    HSSFSheet sheet1 = workbook.createSheet("코드");
		    HSSFSheet sheet2 = workbook.createSheet("선로");
		    HSSFSheet sheet3 = workbook.createSheet("송전설비");
		    HSSFSheet sheet4 = workbook.createSheet("전선접속");
		    HSSFSheet sheet5 = workbook.createSheet("불량애자");
		    HSSFSheet sheet6 = workbook.createSheet("항공장애");
		    
		    workbook.setSheetName(0, "스케줄");
		    //Font 설정.
		    HSSFFont font = workbook.createFont();
		    font.setFontName(HSSFFont.FONT_ARIAL);
		      
		    //제목의 스타일 지정
		    HSSFCellStyle titlestyle = workbook.createCellStyle();
		    titlestyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    titlestyle.setFont(font);
		      
		    //Row 생성
		    HSSFRow row = sheet.createRow((int)0);
		    //Cell 생성
		    HSSFCell cell1 = row.createCell((int)0 );
		    cell1.setCellValue("INS_PLAN_NO");
		    cell1.setCellStyle(titlestyle);
		    
		    HSSFCell cell2 = row.createCell((int)1 );
		    cell2.setCellValue("TOWER_IDX");
		    cell2.setCellStyle(titlestyle);
		    
		    HSSFCell cell3 = row.createCell((int)2);
		    cell3.setCellValue("CYCLE_YM");
		    cell3.setCellStyle(titlestyle);
		    
		    HSSFCell cell4 = row.createCell((int)3);
		    cell4.setCellValue("INS_SN");
		    cell4.setCellStyle(titlestyle);
		    
		    HSSFCell cell5 = row.createCell((int)4);
		    cell5.setCellValue("FNCT_LC_NO");
		    cell5.setCellStyle(titlestyle);
		    
		    HSSFCell cell6 = row.createCell((int)5);
		    cell6.setCellValue("FNCT_LC_DTLS");
		    cell6.setCellStyle(titlestyle);
		    
		    HSSFCell cell7 = row.createCell((int)6);
		    cell7.setCellValue("INS_TY_CD");
		    cell7.setCellStyle(titlestyle);
		    
		    HSSFCell cell8 = row.createCell((int)7);
		    cell8.setCellValue("NM");
		    cell8.setCellStyle(titlestyle);
		    
		    HSSFCell cell9 = row.createCell((int)8);
		    cell9.setCellValue("EQP_NO");
		    cell9.setCellStyle(titlestyle);
		    
		    HSSFCell cell10 = row.createCell((int)9);
		    cell10.setCellValue("EQP_NM");
		    cell10.setCellStyle(titlestyle);
		    
		    HSSFCell cell11 = row.createCell((int)10);
		    cell11.setCellValue("LATITUDE");
		    cell11.setCellStyle(titlestyle);
		    
		    HSSFCell cell12 = row.createCell((int)11);
		    cell12.setCellValue("LONGITUDE");
		    cell12.setCellStyle(titlestyle);
		    
		    HSSFCell cell13 = row.createCell((int)12);
		    cell13.setCellValue("ADDRESS");
		    cell13.setCellStyle(titlestyle);
		    
		    HSSFCell cell14 = row.createCell((int)13);
		    cell14.setCellValue("UNITY_INS_NO");
		    cell14.setCellStyle(titlestyle);
		    
		    HSSFCell cell15 = row.createCell((int)14);
		    cell15.setCellValue("INS_TY_CD_COUNT");
		    cell15.setCellStyle(titlestyle);

		    //내용 스타일 지정
		    HSSFCellStyle style = workbook.createCellStyle();
		    style.setFont(font);
		      
		    //내용중 가운데 정렬 추가
		    HSSFCellStyle styleCenter = workbook.createCellStyle();
		    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    styleCenter.setFont(font);
		    
		    Box sessionBox = loginMethod.getSessionKey(request);
		    sessionBox.put("start_date", "201401");
		    sessionBox.put("end_date", "201412");
		    List<Box> scheduleList = kdnApiService.getTracksInSchedule(sessionBox);
		    for (int i=0; i<scheduleList.size();i++){
		    	Box scheduleBox = scheduleList.get(i);
		    	//voidConvertKorean(scheduleBox);
		        row = sheet.createRow((int)(i+1));
		        cell1 = row.createCell((int)0);
		        cell1.setCellValue(scheduleBox.getString("INS_PLAN_NO"));
		        cell1.setCellStyle(styleCenter); 
		        
		        cell2 = row.createCell((int)1);
		        cell2.setCellValue(scheduleBox.getString("TOWER_IDX"));
		        cell2.setCellStyle(styleCenter);
		          
		        cell3 = row.createCell((int)2);
		        cell3.setCellValue(scheduleBox.getString("CYCLE_YM"));
		        cell3.setCellStyle(style);
		        
		        cell4 = row.createCell((int)3);
		        cell4.setCellValue(scheduleBox.getString("INS_SN"));
		        cell4.setCellStyle(style);
		          
		        cell5 = row.createCell((int)4);
		        cell5.setCellValue(scheduleBox.getString("FNCT_LC_NO"));
		        cell5.setCellStyle(style);
		        
		        cell6 = row.createCell((int)5);
		        cell6.setCellValue(scheduleBox.getString("FNCT_LC_DTLS"));
		        cell6.setCellStyle(style);  
		        
		        cell7 = row.createCell((int)6);
		        cell7.setCellValue(scheduleBox.getString("INS_TY_CD"));
		        cell7.setCellStyle(style);  
		        
		        cell8 = row.createCell((int)7);
		        cell8.setCellValue(scheduleBox.getString("NM"));
		        cell8.setCellStyle(style);  
		        
		        cell9 = row.createCell((int)8);
		        cell9.setCellValue(scheduleBox.getString("EQP_NO"));
		        cell9.setCellStyle(style);
		        
		        cell10 = row.createCell((int)9);
		        cell10.setCellValue(scheduleBox.getString("EQP_NM"));
		        cell10.setCellStyle(style);
		          
		        cell11 = row.createCell((int)10);
		        cell11.setCellValue(scheduleBox.getString("LATITUDE"));
		        cell11.setCellStyle(style);
		        
		        cell12 = row.createCell((int)11);
		        cell12.setCellValue(scheduleBox.getString("LONGITUDE"));
		        cell12.setCellStyle(style);  
		        
		        cell13 = row.createCell((int)12);
		        cell13.setCellValue(scheduleBox.getString("ADDRESS"));
		        cell13.setCellStyle(style);  
		        
		        cell14 = row.createCell((int)13);
		        cell14.setCellValue(scheduleBox.getString("UNITY_INS_NO"));
		        cell14.setCellStyle(style);   
		        
		        cell15 = row.createCell((int)14);
		        cell15.setCellValue(scheduleBox.getString("INS_TY_CD_COUNT"));
		        cell15.setCellStyle(style);

		    }
		    getExcelCode(sheet1, workbook);  
		    getExcelTracks(sheet2, workbook, request);
		    getExcelTower(sheet3, workbook, request);
		    getExcelWire(sheet4, workbook, request);
		    getExcelPoor(sheet5, workbook, request);
		    getExcelAir(sheet6, workbook, request);
		    
		    // 실제 저장될 파일 이름
		    String realName = KdnCommonUtil.getTodayDate().concat(KdnCommonUtil.getTodayTime()).concat(".xls");

		    // 실제로 저장될 파일 풀 경로
		    //String realPath = "c:/KdnMobile" + File.separator + "Excel" + File.separator + "SCHEDULE" + File.separator;
		    //String realPath = "c:\\KdnMobile\\Excel\\SCHEDULE\\";
		    String realPath = propertyService.getString("Globals.kdnFileExcelDownloadPath");
		    //String realPath = propertyService.getString("Globals.kdnExcelFileStorePath").concat("SCHEDULE/");
		    fileManagerService.setDerectoryCreate(realPath); //디렉토리가 없으면 디렉토리 생성
		    File file = new File(realPath, realName);
		      
		    //엑셀 파일을 만듬
		    FileOutputStream fileOutput = new FileOutputStream(file);
		    workbook.write(fileOutput);
		    fileOutput.close();
		    return realName;
	    }
	
//송전설비 지역정보 입력	
	public void setEqpAreaInfo(Box box) throws Exception {
		//위도경도 입력
    	kdnApiService.setLatitudeWithLongitudeUpdate(box);
    	//주소 입력
    	String address = getAddressByLatitudeLongitude(box.getString("latitude"), box.getString("longitude"));
    	box.put("address", address);
    	kdnApiService.setAddress(box);
	}
}
