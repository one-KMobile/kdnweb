/**
 * 
 */
/**
 * @author netted
 *
 */
package kdn.nfc.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.aria.Cipher;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;
import kdn.nfc.service.KdnNfcService;
import kdn.nfc.service.impl.KdnNfcMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class KdnNfcController {
	
	/** KdnNfcService */
	@Resource(name="kdnNfcService")
	private KdnNfcService kdnNfcService;
	
	/** LoginMethod */
	@Resource(name="loginMethod")
	private LoginMethod loginMethod;
	
	/** KdnCodeService */
	@Resource(name = "kdnCodeService")
	private KdnCodeService kdnCodeService;
	
	/** KdnEqpService */
	@Resource(name = "kdnEqpService")
	private KdnEqpService kdnEqpService;
	
	/** KdnNfcMethod */
	@Resource(name = "kdnNfcMethod")
	private KdnNfcMethod kdnNfcMethod;
	
	 /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL = "/kdn/nfc/List.do";
    
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
   	 * [NFC] 목록을 조회한다. (pageing)
   	 * @param [request] [model] 
   	 * @return "/kdn/nfc/nfcList"
   	 * @exception Exception
   	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
   	 */
   @RequestMapping(value="/kdn/nfc/List.do")
   public String nfcList(HttpServletRequest request, ModelMap model) throws Exception {
	    Box box = BoxUtil.getBox(request);

	    try {
	    	/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);

			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", request.getServletPath());
		    if(kdnCommonUtil.isAuthUserInfo(box, "read") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
		    /**************** pageing setting Start ****************/
		    PaginationInfo paginationInfo = kdnCommonUtil.getWebPaging(box);
		    /**************** pageing setting End ****************/
		    
		    //NFC 작업구분 카테고리 [코드]
		    box.put("groupCodeId", "NFCWORK"); 
			List<Box> codeList = kdnCodeService.selectCodeList(box);
			
			//NFC 목록
			if("".equals(box.getString("fst_bizplc_cd"))) {
				box.put("is_search_tower_idx", "N");
			}else {
				box.put("is_search_tower_idx", "Y");
			}
		    List<Box> list = kdnNfcService.getNfcList(box);
			System.out.println("2222 " + list);
			
			//1차 사업소 목록
			List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
			
			//페이징 처리
		    int totCnt = kdnNfcService.getNfcTotalCount(box);
		    paginationInfo.setTotalRecordCount(totCnt);
			
			model.addAttribute("list", list);
			model.addAttribute("fstBizplcList", fstBizplcList);
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
			model.addAttribute("codeList", codeList); 
		    model.addAttribute("paginationInfo", paginationInfo);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return "/kdn/nfc/nfcList";
   }
   

   /**
  	 * [NFC] 목록을 조회한다. (pageing)
  	 * @param [request] [model] 
  	 * @return "/kdn/nfc/nfcList"
  	 * @exception Exception
  	 * @author [정현도] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
  	 */
  /*@RequestMapping(value="/kdn/nfc/Batch/Insert.do")
  public String nfcBatchInsert(HttpServletRequest request, ModelMap model) throws Exception {
	    Box box = BoxUtil.getBox(request);
	    System.out.println("1111 " + box);
	    try {
	    	//세션 정보 호출
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);

			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			
			String fnct_lc_no = null;
			String all_no = null;
			int fnct_lc_no_sum = 0;
			int allCnt = 0;
			int cnt = 0;
			
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			
			List<Box> list = kdnNfcService.getBatchNfcList(box);
			Object nfcInsert = null;
			Object nfcRecordInsert = null;
			Box emptyTotCnt = new Box();
			int totCnt = kdnNfcService.getNfcTotalCount(emptyTotCnt);
			for(int i = 0; i<list.size(); i++) {
				Box batchNfcOne = list.get(i);
				Box batchNfcTwo = list.get(i+1);
				
				if(batchNfcOne.getString("FNCT_LC_NO").equals(batchNfcTwo.getString("FNCT_LC_NO"))) {
					//일부분 등록
					if(totCnt > 0) {
						List<Box> tracksCountList = kdnNfcService.getTrackCnt(box);
						for(int j=0; j< tracksCountList.size(); j++) {
							Box tracksCountBox = tracksCountList.get(j);
							if(batchNfcOne.getString("FNCT_LC_NO").equals(tracksCountBox.getString("FNCT_LC_NO"))) {
								cnt = cnt + 1 ;
								allCnt = allCnt + 1;
								fnct_lc_no_sum = tracksCountBox.getInt("CNT") + cnt;
								//all_no = String.valueOf(kdnNfcService.getNfcTotalCount(box) + (j+1));
								fnct_lc_no = kdnCommonUtil.getConvertCount100(fnct_lc_no_sum);
								all_no = kdnCommonUtil.getConverCount10000(totCnt + allCnt);
								
								box.put("fnct_lc_no", batchNfcOne.getString("FNCT_LC_NO"));
								box.put("fnct_lc_dtls", batchNfcOne.getString("FNCT_LC_DTLS"));
								box.put("eqp_no", batchNfcOne.getString("EQP_NO"));
								box.put("eqp_nm", batchNfcOne.getString("EQP_NM"));
								box.put("fnct_lc_no_count", fnct_lc_no);
								box.put("all_no", all_no);
								//NFC 일괄 생성
								nfcInsert = kdnNfcService.setBatchNfcInsert(box);
								//NFC 이력 일괄 생성
								nfcRecordInsert = kdnNfcService.setBatchNfcRecordInsert(box);
							}
						}
					//일괄 등록
					} else {
						fnct_lc_no_sum = fnct_lc_no_sum + 1;
						fnct_lc_no = kdnCommonUtil.getConvertCount100(fnct_lc_no_sum);
						all_no = kdnCommonUtil.getConverCount10000(i+1);
						
						box.put("fnct_lc_no", batchNfcOne.getString("FNCT_LC_NO"));
						box.put("fnct_lc_dtls", batchNfcOne.getString("FNCT_LC_DTLS"));
						box.put("eqp_no", batchNfcOne.getString("EQP_NO"));
						box.put("eqp_nm", batchNfcOne.getString("EQP_NM"));
						box.put("fnct_lc_no_count", fnct_lc_no);
						box.put("all_no", all_no);
						//NFC 일괄 생성
						nfcInsert = kdnNfcService.setBatchNfcInsert(box);
						//NFC 이력 일괄 생성
						nfcRecordInsert = kdnNfcService.setBatchNfcRecordInsert(box);
					}
					
					
				} else {
					//일부분 등록
					if(totCnt > 0) {
						List<Box> tracksCountList = kdnNfcService.getTrackCnt(box);
						for(int j=0; j< tracksCountList.size(); j++) {
							Box tracksCountBox = tracksCountList.get(j);
							if(batchNfcOne.getString("FNCT_LC_NO").equals(tracksCountBox.getString("FNCT_LC_NO"))) {
								cnt = cnt + 1 ;
								allCnt = allCnt + 1;
								fnct_lc_no_sum = tracksCountBox.getInt("CNT") + cnt;
								//all_no = String.valueOf(kdnNfcService.getNfcTotalCount(box) + (j+1));
								fnct_lc_no = kdnCommonUtil.getConvertCount100(fnct_lc_no_sum);
								all_no = kdnCommonUtil.getConverCount10000(totCnt + allCnt);
								
								box.put("fnct_lc_no", batchNfcOne.getString("FNCT_LC_NO"));
								box.put("fnct_lc_dtls", batchNfcOne.getString("FNCT_LC_DTLS"));
								box.put("eqp_no", batchNfcOne.getString("EQP_NO"));
								box.put("eqp_nm", batchNfcOne.getString("EQP_NM"));
								box.put("fnct_lc_no_count", fnct_lc_no);
								box.put("all_no", all_no);
								//NFC 일괄 생성
								nfcInsert = kdnNfcService.setBatchNfcInsert(box);
								//NFC 이력 일괄 생성
								nfcRecordInsert = kdnNfcService.setBatchNfcRecordInsert(box);
								cnt = 0;
								fnct_lc_no_sum = 0;
							}
						}
					//일괄 등록
					} else {
						fnct_lc_no_sum = fnct_lc_no_sum + 1;
						fnct_lc_no = kdnCommonUtil.getConvertCount100(fnct_lc_no_sum);
						all_no = kdnCommonUtil.getConverCount10000(i+1);
						
						box.put("fnct_lc_no", batchNfcOne.getString("FNCT_LC_NO"));
						box.put("fnct_lc_dtls", batchNfcOne.getString("FNCT_LC_DTLS"));
						box.put("eqp_no", batchNfcOne.getString("EQP_NO"));
						box.put("eqp_nm", batchNfcOne.getString("EQP_NM"));
						box.put("fnct_lc_no_count", fnct_lc_no);
						box.put("all_no", all_no);
						//NFC 일괄 생성
						nfcInsert = kdnNfcService.setBatchNfcInsert(box);
						//NFC 이력 일괄 생성
						nfcRecordInsert = kdnNfcService.setBatchNfcRecordInsert(box);
						fnct_lc_no_sum = 0;
					}
					
				}
			}
			
			stopWatch.stop();
			System.out.println("시간 : " + stopWatch.shortSummary());
			
			String ReusltScript = ""; 
			if(nfcInsert == null){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('NFC를 등록하지 못했습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/nfc/List.do";
	    	}
			
			ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('NFC를 일괄 등록 했습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return "forward:/kdn/nfc/List.do";
  }*/
   
   /**
	 * [NFC] NFC TAG 일괄 등록
	 * @param [request] [model]
	 * @return "/kdn/nfc/List.do"
	 * @exception Exception
	 * @author [정현도] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
   @RequestMapping(value="/kdn/nfc/Batch/Insert.do")
   public String nfcBatchInsert(HttpServletRequest request, ModelMap model) throws Exception {
 	    Box box = BoxUtil.getBox(request);

 	    try {
 	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = loginMethod.getAdminSessionKey(request);
 			model.addAttribute("sessionInfo", sessionInfo);

 			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
		    /**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
 			StopWatch stopWatch = new StopWatch();
 			stopWatch.start();
 			String ReusltScript = "";
 			boolean scriptCheck = false;
 			int sum = 0;
 			int ifCnt = kdnNfcService.isExistNfc(box);
 			List<Box> list = kdnNfcService.getTracksList(box);
 			//초기일괄등록
 			if(ifCnt ==0) {
 				String is_init = "Y";
	 			for(int i=0; i<list.size(); i++) {
	 				Box tracksCodeBox = list.get(i);
		 				
	 				int totalCnt = kdnNfcService.getNfcTotalCount(box);
	 				sum = sum + totalCnt;
	 				tracksCodeBox.put("total_cnt", totalCnt);
	 				tracksCodeBox.put("is_init", is_init);
	 				Object nfcInsert = kdnNfcService.setBatchNfcInsert(tracksCodeBox);
	 				Object nfcRecordInsert = kdnNfcService.setBatchNfcRecordInsert(tracksCodeBox);
	 			}
	 		//부분일괄등록
 			} else {
 				List<Box> partList = kdnNfcService.getSubBatch(box);
 				if(partList.isEmpty()) {
 					ReusltScript += "<script type='text/javaScript' language='javascript'>";
 		 	        ReusltScript += "alert('일괄등록 할 데이터가 없습니다.');";
 		 	        ReusltScript += "</script>";
 		 	        model.addAttribute("reusltScript", ReusltScript);
 		 	        scriptCheck = true;
 				}else {
	 				for(int i=0; i<partList.size(); i++) {
	 					Box partBox = partList.get(i);
	 					int tracksCnt = kdnNfcService.getTrackCnt(partBox);
	 					String convertTracksCnt = kdnCommonUtil.getConvertCount1000(tracksCnt);
	 					int totalCnt = kdnNfcService.getMaxTag(partBox);
	 					String convertTotalCnt = kdnCommonUtil.getConverCount10000(totalCnt);
	 					String tag = partBox.getString("FNCT_LC_NO").concat("_").concat(partBox.getString("EQP_NO").concat("_").concat(convertTracksCnt).concat("_").concat(convertTotalCnt));

	 					partBox.put("tag", tag);
	 					partBox.put("status", "U");
	 					partBox.put("fnct_lc_no", partBox.getString("FNCT_LC_NO"));
	 					partBox.put("eqp_no", partBox.getString("EQP_NO"));
	 					partBox.put("fnct_lc_dtls", partBox.getString("FNCT_LC_DTLS"));
	 					partBox.put("eqp_nm", partBox.getString("EQP_NM"));
	 					kdnNfcService.setNfcInsert(partBox);
	 					kdnNfcService.setNfcRecordInsert(partBox);
	 				}
 				}
 			}
 			stopWatch.stop();
 			System.out.println("시간 : " + stopWatch.shortSummary());
 			System.out.println("총입력수 : " + sum);
 			
 			
 			/*if(){
 	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
 		         ReusltScript += "alert('NFC를 등록하지 못했습니다.');";
 		         ReusltScript += "</script>";
 		         model.addAttribute("reusltScript", ReusltScript);
 		         
 		         return "forward:/kdn/nfc/List.do";
 	    	}*/
 			if(scriptCheck == false) {
	 			ReusltScript += "<script type='text/javaScript' language='javascript'>";
	 	        ReusltScript += "alert('일괄 등록 되었습니다.');";
	 	        ReusltScript += "</script>";
	 	        model.addAttribute("reusltScript", ReusltScript);
 			}
 	    }catch(Exception e) {
 	    	e.printStackTrace();
 	    }
 	    return "forward:/kdn/nfc/List.do";
   }   
  
   /**
	 * [NFC] 상세 화면을 조회한다.
	 * @param [request] [model]
	 * @return "/kdn/nfc/nfcView"
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/nfc/View.do")
    public String nfcView(HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
		try {
			/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);

		
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "read") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
			//NFC 상세보기
			Box viewBox = new Box();
        	if(box.get("tag") != null && !box.get("tag").equals("") ){
        		viewBox = kdnNfcService.getNfcInfo(box);
        	}
        	
        	//NFC 작업구분 카테고리 [코드]
		    box.put("groupCodeId", "NFCWORK"); 
			List<Box> codeList = kdnCodeService.selectCodeList(box);
			System.out.println("codeList 확인 : " + codeList);
        	/*//1차 사업소
			List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
			System.out.println("fstBizplcList 확인 : " + fstBizplcList);
			//2차 사업소
			List<Box> scdBizplcList = kdnEqpService.getWebScdBizplcList(viewBox);
			System.out.println("fstBizplcList 확인 : " + fstBizplcList);
			//선로명
			List<Box> tracksList = kdnEqpService.getWebTracksList(viewBox);
			//지지물
			List<Box> towerList = kdnNfcService.getTowerList(viewBox);*/
        	//클라이언트의 맞게 컨버팅
			//kdnNfcMethod.voidConvertClientCode(viewBox);
			
        	System.out.println("viewBox 확인 : " + viewBox);
        	//model.addAttribute("towerList", towerList);
        	model.addAttribute("codeList", codeList);
        	//model.addAttribute("fstBizplcList", fstBizplcList);
        	//model.addAttribute("scdBizplcList", scdBizplcList);
        	//model.addAttribute("tracksList", tracksList);
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
		} catch(Exception e) {
			e.printStackTrace();
		}
        return "/kdn/nfc/nfcView";
    }
    
    /**
	 * [NFC] 수정.
	 * @param [request] [model]
	 * @return "/kdn/nfc/List.do"
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/nfc/Update.do")
    public String nfcUpdate(HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	System.out.println("box 확인 : " + box);
		try {
			/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
		
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("tag")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(NFC_UID 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/nfc/List.do";
	    	}
	    		//서버의 맞게 커버팅
	    		kdnNfcMethod.voidConvertServerCode(box);
	    		kdnNfcService.setNfcUpdate(box);
	    		kdnNfcService.setNfcRecordUpdate(box);
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('NFC를 수정하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	
        return "forward:/kdn/nfc/List.do";
    }
    
    /**
	 * [NFC] 쓰기화면.
	 * @param [request] [model]
	 * @return "/kdn/nfc/nfcWrite"
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/nfc/Write.do")
    public String nfcWrite(HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	System.out.println("box 확인 : " + box);
		try {
			/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
		
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
			//1차 사업소
			List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
			
			box.put("groupCodeId", "NFCWORK"); 
	    	List<Box> codeList = kdnCodeService.selectCodeList(box);
	    	model.addAttribute("fstBizplcList", fstBizplcList);
	    	model.addAttribute("codeList", codeList);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	
        return "/kdn/nfc/nfcWrite";
    }
    
    /**
	 * [NFC] 생성.
	 * @param [request] [model]
	 * @return "/kdn/nfc/List.do"
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/nfc/Insert.do")
    public String nfcInsert(HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
		try {
			/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
		
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
			String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("fst_bizplc_cd"))) || "".equals(unscript(box.getString("fst_bizplc_cd"))) ){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(제목, 내용)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/nfc/List.do";
	    	}
	    	//서버의 맞게 커버팅
	    	kdnNfcMethod.voidConvertServerCode(box);
	    	//NFC 테이블 총 튜플 수
	    	Box totCnt = new Box();
	    	int allCount = kdnNfcService.getNfcTotalCount(totCnt) + 1;
	    	String allCnt = kdnCommonUtil.getConverCount10000(allCount);
	    	//선로명 총 튜플 수
	    	Box tracksTotCnt = new Box();
	    	tracksTotCnt.put("fnct_lc_no", box.getString("fnct_lc_no"));
	    	int tracksCount = kdnNfcService.getNfcTotalCount(tracksTotCnt) + 1;
	    	String trackCnt = kdnCommonUtil.getConvertCount1000(tracksCount);
	    	
	    	//코드로 이름명 추출
	    	Box nameBox = kdnEqpService.getEqpInfo(box);
		    box.put("eqp_nm", nameBox.getString("EQP_NM"));
		    box.put("fnct_lc_dtls", nameBox.getString("FNCT_LC_DTLS"));
	    	//NFC 등록
	    	box.put("tag", box.getString("fnct_lc_no").concat("_").concat(box.getString("eqp_no"))
	    				.concat("_").concat(trackCnt).concat("_").concat(allCnt));
	    	kdnNfcService.setNfcInsert(box); 
	    	//NFC 이력 등록
	    	kdnNfcService.setNfcRecordInsert(box);
	    	
	    	ReusltScript += "<script type='text/javaScript' language='javascript'>";
		    ReusltScript += "alert('NFC를 등록하였습니다.(등록하신 일련번호 : " + unscript(box.getString("tag")) + ")');";
		    ReusltScript += "</script>";
		    model.addAttribute("box", box);
		    model.addAttribute("reusltScript", ReusltScript);
		    
			} catch (Exception e) {
				e.printStackTrace();
			}
    	
        return "forward:/kdn/nfc/List.do";
    }
    
    /**
   	 * [NFC] 엑셀 생성.
   	 * @param [request] [model]
   	 * @return "/kdn/nfc/List.do"
   	 * @exception Exception
   	 * @author [정현도] 
     * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
       @RequestMapping("/kdn/nfc/excel/create.do")
       public String nfcExcelCreate(HttpServletRequest request, Model model) throws Exception {
       	Box box = BoxUtil.getBox(request);
   		try {
   			/**************** 세션 정보 호출 Start ****************/
   			Box sessionInfo = loginMethod.getAdminSessionKey(request);
   		
   			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
   				return "redirect:/kdnIndex.do";
   			}
   			/**************** 세션 정보 호출 End ****************/
   			
   			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
   			String ReusltScript = ""; 	
   			String loadingBar = "";
   			loadingBar += "makePreload('페이지 로딩중. 잠시만 기다려 주세요.'');";
   			loadingBar +=  "self.onload=hidePreload;";
   			List<Box> list = kdnNfcMethod.getExcelSave();

   	    	if(list.isEmpty()){
   	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
   		         ReusltScript += "alert('엑셀파일을 생성하지 못했습니다.');";
   		         ReusltScript += "</script>";
   		         model.addAttribute("reusltScript", ReusltScript);

   		         return "forward:/kdn/nfc/List.do";
   	    	}
   	    	
   	    	ReusltScript += "<script type='text/javaScript' language='javascript'>";
   		    ReusltScript += "alert('엑셀파일을 생성했습니다..');";
   		    ReusltScript += "self.location.replace('/kdn/nfc/List.do');";
   		    ReusltScript += "</script>";

   		    model.addAttribute("loadingBar", loadingBar);
   		    model.addAttribute("reusltScript", ReusltScript);

   			} catch (Exception e) {
   				e.printStackTrace();
   			}
           return "forward:/kdn/nfc/List.do";
       }
       
       @RequestMapping(value="/ajax/getExcelCreate.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView ajaxGetExcelCreate(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	        try {
	        	List<Box> list = kdnNfcMethod.getExcelSave();
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("nfcList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
}