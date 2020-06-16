package kdn.version.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.login.web.LoginMethod;
import kdn.version.service.KdnVersionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class KdnVersionController {

	/** KdnCodeService */
	@Resource(name = "kdnCodeService")
	private KdnCodeService kdnCodeService;
	
	/** LoginMethod */
	@Resource(name = "loginMethod")
	LoginMethod loginMethod;
	
	/** KdnVersionService */
	@Resource(name = "kdnVersionService")
	private KdnVersionService kdnVersionService;
	
	 /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL = "/kdn/version/versionList.do";
    
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
	 * [모바일 버젼] 목록을 조회한다. (pageing)
	 * 
	 * @param [request] [model]
	 * @return "/kdn/version/versionList.do"
	 * @exception Exception
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@RequestMapping(value = "/kdn/version/versionList.do")
	public String versionList(HttpServletRequest request, ModelMap model)throws Exception {
		Box box = BoxUtil.getBox(request);
		if(request.getHeader("referer").indexOf("versionView.do")!=0){ //업데이트 이후 초기값 세팅
			box.addData("use_yn", "");
		}
		
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

			// 관리자 검색 카테고리 [코드]
			box.put("groupCodeId", "USE_YN"); // 사용유무
			List<Box> codeList = kdnCodeService.selectCodeList(box);
			List<Box> list = kdnVersionService.getVersionList(box);
			int totCnt = kdnVersionService.getVersionListTotCnt(box);

			paginationInfo.setTotalRecordCount(totCnt);

			model.addAttribute("list", list);
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
			model.addAttribute("codeList", codeList);
			model.addAttribute("paginationInfo", paginationInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/kdn/version/versionList";
	}
	
	
	/**
	 * [모바일 버전] 삭제.
	 * @param [request] [model]
	 * @return "/kdn/version/versionList.do"
	 * @exception Exception
	 * @author [신명섭] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/version/versionDelete.do")
    public String versionDelete(HttpServletRequest request, Model model) throws Exception {
    	String[] checks = request.getParameterValues("chk"); 
    	Box box = BoxUtil.getBox(request);
    	box.put("chk", checks);
		
    	/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
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
    	if("".equals(box.getString("chk"))){
    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
	         ReusltScript += "alert('필수 항목(모바일 버전 선택 키)의 값이 없습니다.');";
	         ReusltScript += "</script>";
	         model.addAttribute("reusltScript", ReusltScript);
	         return "forward:/kdn/version/versionList.do";
    	}
    	try {
    		kdnVersionService.versionDelete(box);
    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "forward:/kdn/version/versionList.do";
    }
    
    /**
	 * [모바일 버젼] 등록 화면을 등록, 수정한다.
	 * @param [request] [model]
	 * @return "/kdn/version/versionWrite"
	 * @exception Exception
	 * @author [신명섭] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/version/versionWrite.do")
    public String versionWrite(@ModelAttribute("searchVO") FileVO fileVO, HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	Box viewBox = null;
    	List <Box> codeList = new ArrayList<Box>();
    	
    	/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
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
		
        try {
        	if(box.get("version_idx") != null && !box.get("version_idx").equals("") ){
        		viewBox = kdnVersionService.getVersionView(box);
        	} 
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        //공지사항 카테고리 [사용여부]
	    box.put("groupCodeId", "USE_YN"); 
		try {
			codeList = kdnCodeService.selectCodeList(box);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        model.addAttribute("box", box);
        model.addAttribute("viewBox", viewBox);
        model.addAttribute("codeList", codeList); 
        return "/kdn/version/versionWrite";
    } 
    
    /**
	 * [모바일 버전] 등록 저장.
	 * @param [request] [model]
	 * @return "/kdn/version/versionList.do"
	 * @exception Exception
	 * @author [신명섭] 
   	 * @fix(<신명섭>) [yyyy.mm.dd]: [푸시전송]
	 */
    @RequestMapping("/kdn/version/versionSave.do")
    public String versionSave(HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
			box.put("reg_id", sessionInfo.getString("user_id"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
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
    	if("".equals(unscript(box.getString("version"))) || "".equals(unscript(box.getString("use_yn"))) ){
    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
	         ReusltScript += "alert('필수 항목(사용여부 , 버전)의 값이 없습니다.');";
	         ReusltScript += "</script>";
	         model.addAttribute("reusltScript", ReusltScript);
	         return "forward:/kdn/admin/noticeList.do";
    	}
    	
    	try {
    		kdnVersionService.versionInsert(box); 
    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('모바일 버전을 등록하였습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "forward:/kdn/version/versionList.do";
    } 
    
    /**
	 * [공지] 상세 화면을 조회한다.
	 * @param [request] [model]
	 * @return "/kdn/version/versionWrite"
	 * @exception Exception
	 * @author [신명섭] 
   	 * @fix(<신명섭>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/version/versionView.do")
    public String noticeView(HttpServletRequest request, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	Box viewBox = null;
    	List <Box> codeList = new ArrayList<Box>();
    	
    	/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
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
		
    	 //공지사항 카테고리 [사용여부]
	    box.put("groupCodeId", "USE_YN"); 
		try {
			codeList = kdnCodeService.selectCodeList(box);
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
        	if(box.get("version_idx") != null && !box.get("version_idx").equals("") ){
        		viewBox = kdnVersionService.getVersionView(box);
        	} 
		} catch (Exception e) {
			e.printStackTrace();
		}
        model.addAttribute("codeList", codeList);
        model.addAttribute("box", box);
        model.addAttribute("viewBox", viewBox);
        return "/kdn/version/versionView";
    } 
    
    /**
  	 * [공지] 버전 수정
  	 * @param [request] [model]
  	 * @return "/kdn/version/versionList.do"
  	 * @exception Exception
  	 * @author [신명섭] 
   	 * @fix(<신명섭>) [yyyy.mm.dd]: [수정 내용]
  	 */
      @RequestMapping("/kdn/version/versionUpdate.do")
      public String versionUpdate(HttpServletRequest request, Model model) throws Exception {
      	Box box = BoxUtil.getBox(request);

      	/**************** 세션 정보 호출 Start ****************/
  		Box sessionInfo = null;
  		try {
  			sessionInfo = loginMethod.getAdminSessionKey(request);
  			model.addAttribute("sessionInfo", sessionInfo);
  			box.put("reg_id", sessionInfo.getString("user_id"));
  		} catch (Exception e) {
  			e.printStackTrace();
  		} 
  		
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
    	if("".equals(unscript(box.getString("version_idx")))){
    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
	         ReusltScript += "alert('필수 항목(버전 키)의 값이 없습니다.');";
	         ReusltScript += "</script>";
	         model.addAttribute("reusltScript", ReusltScript);
	         return "forward:/kdn/version/versionList.do";
    	}
    	
    	try {
    		kdnVersionService.versionUpdate(box);
    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('모바일 버전을 수정하였습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
	        box.addData("use_yn", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	//return "redirect:/kdn/version/versionList.do";
    	return "forward:/kdn/version/versionList.do";
      } 
}