/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kdn.code.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**  
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 * 
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class KdnCodeController {
	
	/** KdnCodeService */
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** Validator */
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
    /** EgovFileMngService */
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    /** EgovFileMngUtil */
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    /** LoginMethod */
    @Resource(name="loginMethod")
    LoginMethod loginMethod;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL1 = "/kdn/admin/groupCodeList.do";
    private String controllerURL2 = "/kdn/admin/codeList.do";
    
    // 코드에서 검색항목 불러오기 
    ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
    
	    /**
	     * XSS 방지 처리.
	     * 
	     * @param data
	     * @return
	     */
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
	   	 * [공통그룹코드] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/kdn/code/groupCodeList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @IncludedInfo(name="공통그룹코드", order = 2 ,gid = 100)
	   @RequestMapping(value="/kdn/admin/groupCodeList.do")
	   public String groupCodeList(HttpServletRequest request, ModelMap model) throws Exception {
		    Box box = BoxUtil.getBox(request);
		    List <Box> list = new ArrayList<Box>();
		    
		    /**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
				model.addAttribute("sessionInfo", sessionInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
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
			
		    list = kdnCodeService.getGroupCodeList(box);
			model.addAttribute("list", list);
		       
		    int totCnt = kdnCodeService.getGroupCodeListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
						
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
		    model.addAttribute("paginationInfo", paginationInfo);
		       
		    return "/kdn/code/groupCodeList";
	   }
	   
	   /**
		 * [공통그룹코드] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/code/groupCodeView"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/groupCodeView.do")
	    public String groupCodeView(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
			
	    	/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
				model.addAttribute("sessionInfo", sessionInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL1);
		    if(kdnCommonUtil.isAuthUserInfo(box, "read") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	        try {
	        	if(box.get("group_code_id") != null && !box.get("group_code_id").equals("")){
	        		viewBox = kdnCodeService.getGroupCodeView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/code/groupCodeView";
	    } 
	   
	   /**
		 * [공통그룹코드] 등록 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/code/groupCodeWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/groupCodeWrite.do")
	    public String groupCodeWrite(@ModelAttribute("searchVO") FileVO fileVO, HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
	    	
	    	/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
				model.addAttribute("sessionInfo", sessionInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL1);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	        try {
	        	if(box.get("group_code_id") != null && !box.get("group_code_id").equals("") ){
	        		viewBox = kdnCodeService.getGroupCodeView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box); 
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/code/groupCodeWrite";
	    } 
	    
	    /**
		 * [공통그룹코드] 등록 저장.
		 * @param [request] [model]
		 * @return "/kdn/admin/groupCodeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/groupCodeSave.do")
	    public String groupCodeSave(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				box.put("reg_id", sessionInfo.getString("user_id"));
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL1);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("group_code_id"))) || "".equals(unscript(box.getString("group_code_name"))) || "".equals(unscript(box.getString("group_code_account")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공통그룹코드ID, 공통그룹코드명, 공통그룹코드 설명) 의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/groupCodeList.do";
	    	}
	    	
	    	try {
	    		int OverlapCount = kdnCodeService.getGroupCodeOverlapCount(box);
	    		
	    		if(OverlapCount <= 0){
	    			kdnCodeService.groupCodeSave(box);
	    		}
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('공통그룹코드를 등록하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/groupCodeList.do";
	    } 
	    
	    /**
		 * [공통그룹코드] 수정.
		 * @param [request] [model]
		 * @return "/kdn/admin/groupCodeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/groupCodeUpdate.do")
	    public String groupCodeUpdate(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				box.put("upd_id", sessionInfo.getString("user_id"));
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL1);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("group_code_id")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공통그룹코드 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/groupCodeList.do";
	    	}
	    	
	    	try {
	    		kdnCodeService.groupCodeUpdate(box); 
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('공통그룹코드를 수정하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/groupCodeList.do";
	    }
	    
	    /**
		 * [공통그룹코드] 삭제.
		 * @param [request] [model]
		 * @return "/kdn/admin/groupCodeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/groupCodeDelete.do")
	    public String groupCodeDelete(HttpServletRequest request, Model model) throws Exception {
	    	String[] checks = request.getParameterValues("chk"); 
	    	Box box = BoxUtil.getBox(request);
	    	box.put("chk", checks);
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL1);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
 			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(box.getString("chk"))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공통그룹코드 선택 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/groupCodeList.do";
	    	}
	    	
	    	try {
	    		kdnCodeService.groupCodeDelete(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	return "forward:/kdn/admin/groupCodeList.do";
	    }
	    
	    /**
	   	 * [공통코드] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/kdn/code/codeList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @IncludedInfo(name="공통코드", order = 3 ,gid = 100)
	   @RequestMapping(value="/kdn/admin/codeList.do")
	   public String codeList(HttpServletRequest request, ModelMap model) throws Exception {
		    Box box = BoxUtil.getBox(request);
		    List <Box> list = new ArrayList<Box>();
		    
		    /**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				model.addAttribute("sessionInfo", sessionInfo);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
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
			
		    list = kdnCodeService.getCodeList(box);
			model.addAttribute("list", list);
		       
		    int totCnt = kdnCodeService.getCodeListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
						
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
		    model.addAttribute("paginationInfo", paginationInfo);
		       
		    return "/kdn/code/codeList";
	   }
	   
	   /**
		 * [공통코드] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/code/codeView"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/codeView.do")
	    public String codeView(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				model.addAttribute("sessionInfo", sessionInfo);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL2); 
		    if(kdnCommonUtil.isAuthUserInfo(box, "read") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	        try {
	        	if(box.get("group_code_id") != null && !box.get("group_code_id").equals("") ){
	        		if(box.get("code_id") != null && !box.get("code_id").equals("") ){
	        			viewBox = kdnCodeService.getCodeView(box);
	        		}
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/code/codeView";
	    } 
	   
	   /**
		 * [공통코드] 등록 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/code/codeWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/codeWrite.do")
	    public String codeWrite(@ModelAttribute("searchVO") FileVO fileVO, HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	List <Box> groupCodeList = new ArrayList<Box>();
	    	Box viewBox = null;
	    	
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				model.addAttribute("sessionInfo", sessionInfo);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL2); 
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	        try {
	        	if(box.get("group_code_id") != null && !box.get("group_code_id").equals("") ){	
		        	if(box.get("code_id") != null && !box.get("code_id").equals("") ){
		        		viewBox = kdnCodeService.getCodeView(box);
		        	}
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        //공통그룹코드 선택 리스트 조회
			try {
				groupCodeList = kdnCodeService.selectGroupCodeList(box);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("groupCodeList", groupCodeList);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/code/codeWrite";
	    } 
	    
	    /**
		 * [공통코드] 등록 저장.
		 * @param [request] [model]
		 * @return "/kdn/admin/codeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/codeSave.do")
	    public String codeSave(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				box.put("reg_id", sessionInfo.getString("user_id"));
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL2); 
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
 			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("code_id"))) || "".equals(unscript(box.getString("code_name"))) || "".equals(unscript(box.getString("code_account")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공통코드ID, 공통코드명, 공통코드 설명) 의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/codeList.do";
	    	}
	    	
	    	try {
	    		int OverlapCount = kdnCodeService.getCodeOverlapCount(box);
	    		
	    		if(OverlapCount <= 0){
	    			kdnCodeService.codeSave(box);
	    		}
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('공통코드를 등록하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	
	        
	    	return "forward:/kdn/admin/codeList.do";
	    } 
	    
	    /**
		 * [공통코드] 수정.
		 * @param [request] [model]
		 * @return "/kdn/admin/codeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/codeUpdate.do")
	    public String codeUpdate(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 				box.put("upd_id", sessionInfo.getString("user_id"));
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL2); 
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("code_id")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공통코드 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/codeList.do";
	    	}
	    	
	    	try {
	    		kdnCodeService.codeUpdate(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('공통코드를 수정하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/codeList.do";
	    }
	    
	    /**
		 * [공통코드] 삭제.
		 * @param [request] [model]
		 * @return "/kdn/admin/codeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/codeDelete.do")
	    public String codeDelete(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	String[] checks = request.getParameterValues("chk");
	    	
	    	System.out.println("checks.length = "+ checks.length);
	    	String[] GroupCd = new String[checks.length];
	    	String[] Cd = new String[checks.length];
	    	
	    	for (int i = 0; i < checks.length; i++) {
	    		GroupCd[i] = checks[i].substring(0, checks[i].indexOf("/"));
	    		Cd[i] = checks[i].substring(checks[i].indexOf("/") + 1 , checks[i].length());
			}
	    	
	    	box.put("groupCdArr", GroupCd);
	    	box.put("cdArr", Cd);
			
	    	/**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				return "redirect:/kdnIndex.do";
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL2); 
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
 			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(box.getString("chk"))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공통코드 선택 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/codeList.do";
	    	}
	    	
	    	try {
	    		kdnCodeService.codeDelete(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	return "forward:/kdn/admin/codeList.do";
	    }
	    
	    /**
		 * <설명>
		 * [공통그룹코드] 공통그룹코드ID 중복 체크 카운트
		 * @param [group_code_id]  
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/groupCodeOverlapCount.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView groupCodeOverlapCount(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
			if ("".equals(box.getString("group_code_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	int totCnt = kdnCodeService.getGroupCodeOverlapCount(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
	        	modelAndView.addObject("overlapCnt",totCnt);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
		 * <설명>
		 * [공통코드] 공통코드ID 중복 체크 카운트
		 * @param [code_id] [group_code_idx] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/codeOverlapCount.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView codeOverlapCount(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
			if ("".equals(box.getString("code_id")) || "".equals(box.getString("group_code_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	int totCnt = kdnCodeService.getCodeOverlapCount(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
	        	modelAndView.addObject("overlapCnt",totCnt);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }

}
