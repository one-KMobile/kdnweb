package kdn.nfc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.cmm.util.KdnCommonUtil;
import kdn.nfc.service.KdnNfcService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Component;


@Component("kdnNfcMethod")
public class KdnNfcMethod {
	
	@Resource(name = "kdnNfcService")
	private KdnNfcService kdnNfcService;
	
    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }

    /**
	 * 서버의 있는 status의 값을 자바스크립트의 맞게 컨버터
	 * @param [box - status]
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	    
	public void voidConvertClientCode(Box viewBox) throws Exception {
		if("U".equals(viewBox.getString("status"))) {
			viewBox.put("status", "101");
		}else if("D".equals(viewBox.getString("status"))) {
			viewBox.put("status", "102");
		}else {
			viewBox.put("status", "103");
		}
	}
	
    /**
	 * 서버의 있는 status의 값을 한국어로 컨버터
	 * @param [box - status]
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	    
	public void voidConvertKorean(Box viewBox) throws Exception {
		if("U".equals(viewBox.getString("STATUS"))) {
			viewBox.put("status", "사용");
		}else if("D".equals(viewBox.getString("STATUS"))) {
			viewBox.put("status", "폐기");
		}else {
			viewBox.put("status", "파손");
		}
	}
	
    /**
	 * 클라이언트의 있는 status의 값을 서버의 맞게 컨버터
	 * @param [box - status]
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	    
	public void voidConvertServerCode(Box box) throws Exception {
		if("101".equals(box.getString("status"))) {
			box.put("status", "U");
		}else if("102".equals(box.getString("status"))) {
			box.put("status", "D");
		}else {
			box.put("status", "B");
		}
	}
	
    public List<Box> getExcelSave() throws Exception {
    	List<Box> nfcList = null;
    	
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    
	    HSSFSheet sheet = workbook.createSheet("Sheet1");
	    
	    //Font 설정.
	    HSSFFont font = workbook.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	      
	    //제목의 스타일 지정
	    HSSFCellStyle titlestyle = workbook.createCellStyle();
	    titlestyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
	    titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titlestyle.setFont(font);
	      
	    //Row 생성
	    HSSFRow row = sheet.createRow((int)0);
	    //Cell 생성
	    HSSFCell cell1 = row.createCell((int)0 );
	    cell1.setCellValue("선로명");
	    cell1.setCellStyle(titlestyle);
	    
	    HSSFCell cell2 = row.createCell((int)1);
	    cell2.setCellValue("지지물명");
	    cell2.setCellStyle(titlestyle);
	    
	    HSSFCell cell3 = row.createCell((int)2);
	    cell3.setCellValue("TAG");
	    cell3.setCellStyle(titlestyle);
	      
	    HSSFCell cell4 = row.createCell((int)3);
	    cell4.setCellValue("상태");
	    cell4.setCellStyle(titlestyle);
	      
	    HSSFCell cell5 = row.createCell((int)4);
	    cell5.setCellValue("생성일");
	    cell5.setCellStyle(titlestyle);
	    
	    HSSFCell cell6 = row.createCell((int)5);
	    cell6.setCellValue("선로코드");
	    cell6.setCellStyle(titlestyle);
	    
	    HSSFCell cell7 = row.createCell((int)6);
	    cell7.setCellValue("지지물코드");
	    cell7.setCellStyle(titlestyle);
	    //내용 스타일 지정
	    HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	      
	    //내용중 가운데 정렬 추가
	    HSSFCellStyle styleCenter = workbook.createCellStyle();
	    styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleCenter.setFont(font);
	    Box box = new Box();

	    nfcList = kdnNfcService.getNfcList(box);
	    for (int i=0; i<nfcList.size();i++){
	    	Box nfcRecordInfo = nfcList.get(i);
	    	voidConvertKorean(nfcRecordInfo);
	        row = sheet.createRow((int)(i+1));
	         
	        cell1 = row.createCell((int)0);
	        cell1.setCellValue(nfcRecordInfo.getString("fnct_lc_dtls"));
	        cell1.setCellStyle(styleCenter);
	          
	        cell2 = row.createCell((int)1);
	        cell2.setCellValue(nfcRecordInfo.getString("eqp_nm"));
	        cell2.setCellStyle(style);
	        
	        cell3 = row.createCell((int)2);
	        cell3.setCellValue(nfcRecordInfo.getString("tag"));
	        cell3.setCellStyle(style);
	          
	        cell4 = row.createCell((int)3);
	        cell4.setCellValue(nfcRecordInfo.getString("status"));
	        cell4.setCellStyle(style);
	        
	        cell5 = row.createCell((int)4);
	        cell5.setCellValue(nfcRecordInfo.getString("reg_date"));
	        cell5.setCellStyle(style);  
	        
	        cell6 = row.createCell((int)5);
	        cell6.setCellValue(nfcRecordInfo.getString("fnct_lc_no"));
	        cell6.setCellStyle(style);  
	        
	        cell7 = row.createCell((int)6);
	        cell7.setCellValue(nfcRecordInfo.getString("eqp_no"));
	        cell7.setCellStyle(style);  

	    }
	      
	    // 실제 저장될 파일 이름
	    String realName = KdnCommonUtil.getTodayDate().concat(KdnCommonUtil.getTodayTime()).concat(".xls");
	      
	    // 실제로 저장될 파일 풀 경로
	    File path = new File(".");
	    System.out.println("파일경로 : " + path.getAbsolutePath());
	    File file = new File(path.getAbsolutePath(), realName);
	      
	    //엑셀 파일을 만듬
	    FileOutputStream fileOutput = new FileOutputStream(file);
	    workbook.write(fileOutput);
	    fileOutput.close();

	    return nfcList;
    }
    
}
