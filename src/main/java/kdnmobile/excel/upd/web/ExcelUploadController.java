package kdnmobile.excel.upd.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.code.service.KdnCodeService;
import kdnmobile.excel.upd.cmm.ExcelUtil;
import kdnmobile.excel.upd.service.ExcelUploadService;
import kdnmobile.excel.upd.service.ExcelVO;
import kdnmobile.excel.upd.service.TotInsExcelVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29	한성곤			2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.07.01 안민정		 	댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.07 서준식           유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 * </pre>
 */

@Controller
public class ExcelUploadController {

	 
    @Resource(name = "ExcelUploadService")
    private ExcelUploadService excelUploadService;

    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;

    /**
     * 게시물에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO 
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/excelUploadView.do")
    public ModelAndView excelUploadView(HttpServletRequest request, ModelMap model) throws Exception {
    	ModelAndView mnv = new ModelAndView("/kdn/ExcelUpload/excelUploadView");
    	return mnv;
    }
    
    @RequestMapping("/insertExcelData.do")
    public ModelAndView insertExcelData(@ModelAttribute("excelVO") ExcelVO excel, final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	
    	ModelAndView mnv = new ModelAndView("/kdn/ExcelUpload/excelUploadView");
    	final Map<String, MultipartFile> files = multiRequest.getFileMap();
    	
    	//엑셀 파일 경로
		//String fileName = "C:\\excelDataTest.xlsx";
		String filePath = "";
    			
    	List<FileVO> result = null;
		String fileStreCours 		= "";	//저장된 path
		String streFileNm 	= "";	//저장된 파일명
		String OrignlFileNm =""; //오리지날 파일명
		
		if (!files.isEmpty()) {
			result = fileUtil.kdnParseFileInf(files, "KDNMOBILE_", 0, "");
			
			fileStreCours     = result.get(0).getFileStreCours();
			streFileNm  = result.get(0).getStreFileNm();
			OrignlFileNm  = result.get(0).getOrignlFileNm();
			
			filePath = fileStreCours + "/"+ streFileNm;
			
			excel.setFile_path(filePath);
			excel.setFile_name(OrignlFileNm);
			
			System.out.println("filePath = " +  filePath);
			System.out.println("OrignlFileNm =" +  OrignlFileNm);
		}
		
		ExcelUtil eu = new ExcelUtil();
		List<TotInsExcelVO> list = null;
		String results = null;
		
		//실제 파일명에서 확장자 추출
		String excelType = getStringFromEnd(OrignlFileNm);

		//엑셀 데이터를 읽어 list형으로 반환함
		if(excelType.equals("xls")){
			list = eu.doXlsExcel(filePath, excel);
		}else if(excelType.equals("xlsx")){
			list = eu.doXlsxExcel(filePath, excel); 
		}
		
		/*byte[] key = null;
		System.out.println();
		System.out.println("보통순시@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		key = Cipher.getMasterKeySet("zcfN3ePcoJZqJv+FyDqr1dGoCE9jJLwg3sEnqNnSdi4urBEGw8xb4aww+nhOeYd3cMNeYZRO2h+6iHUtWHNuBQ==");
		System.out.println("보통순시 [gnrlz_opnin] = " + Cipher.getDataDcSet("urpQ29pu8Gus4PpXqapHqEDyp2F57Y0HRrgJBzvUnFU=", key));
		key = Cipher.getMasterKeySet("oP0kil+YMaTS9yjgAAdRo7lVweRKtBCBqNeTsylVn+6VvPUSgnauQKKRrMT6UBR1apMk2jwG0xEt4ttrX+9crQ==");
		System.out.println("보통순시 [gnrlz_opnin] = " + Cipher.getDataDcSet("h1ZqfdtZdbNVxlkkwj/saw==", key));
		key = Cipher.getMasterKeySet("4REw3kbOEugempYp2bF+AO6N8e4QLQUUqndYLPEtRPk76Wc/GZZT6KJ6esyMakoCWgjWiU/BC6sSTMY4cgzAmg==");
		System.out.println("보통순시 [gnrlz_opnin] = " + Cipher.getDataDcSet("msDPnebZqjsxTzYw9F99ng==", key));*/
		
		try {
			excelUploadService.insertInsTot(list); //순시결과 엑셀 DB에 저장
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		results = Integer.toString(list.size());
		
		//업로드된 엑셀파일을 삭제처리
		/*File f = new File(fileName);
		if(f.exists())f.delete();*/
		
		String ReusltScript = ""; 
		ReusltScript += "<script type='text/javaScript' language='javascript'>";
        ReusltScript += "alert('순시결과를 업로드하였습니다.');";
        ReusltScript += "</script>";
        mnv.addObject("reusltScript", ReusltScript);
		mnv.addObject("results", results);
    	return mnv;
    }
    
    public String getStringFromEnd(String s) { 
		return s.substring(s.lastIndexOf(".")+1, s.length()); 
	}

   
}
