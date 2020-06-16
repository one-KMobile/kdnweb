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
package kdn.auth.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.auth.service.KdnAuthService;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
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
public class KdnAuthController {
	
	/** KdnAuthService */
    @Resource(name = "kdnAuthService")
    private KdnAuthService kdnAuthService;
    
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
    private String controllerURL = "/kdn/admin/authList.do";
    
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
	   	 * [권한] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/kdn/auth/authList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @RequestMapping("/kdn/admin/authList.do")
	   public String authList(HttpServletRequest request, ModelMap model) throws Exception {
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
		    
		    list = kdnAuthService.getAuthList(box);
			model.addAttribute("list", list);
		       
		    int totCnt = kdnAuthService.getAuthListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
			
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
		    model.addAttribute("paginationInfo", paginationInfo);
		       
		    return "/kdn/auth/authList";
	   }
	   
	   /**
		 * [권한] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/auth/authView"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/authView.do")
	    public String authView(HttpServletRequest request, Model model) throws Exception {
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
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "read") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	        try {
	        	if(box.get("auth_idx") != null && !box.get("auth_idx").equals("")){
	        		viewBox = kdnAuthService.getAuthView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/auth/authView";
	    } 	   
	   
	   /**
		 * [권한] 등록 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/auth/authWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/authWrite.do")
	    public String authWrite(HttpServletRequest request, Model model) throws Exception {
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
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
			
	        try {
	        	if(box.get("auth_idx") != null && !box.get("auth_idx").equals("") ){
	        		viewBox = kdnAuthService.getAuthView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/auth/authWrite";
	    } 
	    
	    /**
		 * [권한] 등록 저장.
		 * @param [request] [model]
		 * @return "/kdn/admin/authList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/authSave.do")
	    public String authSave(HttpServletRequest request, Model model) throws Exception {
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
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
 			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("auth_id"))) || "".equals(unscript(box.getString("auth_nm"))) || "".equals(unscript(box.getString("auth_account")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(권한ID, 권한명, 권한 설명) 의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/authList.do";
	    	}
	    	
	    	try {
	    		int OverlapCount = kdnAuthService.getAuthIdOverlapCount(box);
	    		
	    		if(OverlapCount <= 0){
	    			kdnAuthService.authSave(box);
	    		}
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('권한을 등록하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/authList.do";
	    } 
	    
	    /**
		 * [권한] 수정.
		 * @param [request] [model]
		 * @return "/kdn/admin/authList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/authUpdate.do")
	    public String authUpdate(HttpServletRequest request, Model model) throws Exception {
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
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
 			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("auth_idx")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(권한 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/authList.do";
	    	}
	    	
	    	try {
	    		kdnAuthService.authUpdate(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('권한을 수정하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/authList.do";
	    }
	    
	    /**
		 * [권한] 삭제.
		 * @param [request] [model]
		 * @return "/kdn/admin/authList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/authDelete.do")
	    public String authDelete(HttpServletRequest request, Model model) throws Exception {
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
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	model.addAttribute("reusltScript", StringUtil.getAlertMessage(egovMessageSource.getMessage("Auth.message"))); 
	    		return "forward:/kdn/admin/noticeList.do";
	    	}
	    	/**************** user 권한 체크 End ****************/
 			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(box.getString("chk"))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(권한 선택 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/authList.do";
	    	}
	    	
	    	try {
	    		kdnAuthService.authDelete(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	return "forward:/kdn/admin/authList.do";
	    }
	    
	    /**
		 * <설명>
		 * [권한] 권한ID 중복 체크 카운트
		 * @param [auth_id]  
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/authIdOverlapCount.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView authIdOverlapCount(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
			if ("".equals(box.getString("auth_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	int totCnt = kdnAuthService.getAuthIdOverlapCount(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
	        	modelAndView.addObject("overlapCnt",totCnt);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
	   	 * [메뉴 권한] 메뉴 권한을 주기위한 리스트 페이지 호출
	   	 * @param [request] [model] 
	   	 * @return "/kdn/menu/menuList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @RequestMapping("/kdn/admin/pop/menuGrantAuthList.do")
	   public ModelAndView menuGrantAuthList(HttpServletRequest request, ModelMap model) throws Exception {
		   ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
		   Box box = BoxUtil.getBox(request);
		   /**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.SESSION_FAIL);
	    		return modelAndView;
			}
			/**************** 세션 정보 호출 End ****************/
			
			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.AUTH_FAIL);
	    		return modelAndView;
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
		    ModelAndView mnv = new ModelAndView("/kdn/pop/menuGrantAuthList");
	    	return mnv;
	    }
	   
	   /**
		 * <설명>
		 * [권한] 메뉴 권한을 주기위한 리스트 가져오기
		 * @param [auth_id] [subMenuId] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/menuGrantAuthList.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getMenuGrantAuthList(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        List <Box> list = new ArrayList<Box>();
	        
			if ("".equals(box.getString("auth_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	list = kdnAuthService.getMenuGrantAuthList(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
	        	modelAndView.addObject("resultList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
		 * <설명>
		 * [권한] 메뉴 권한 주기
		 * @param [auth_id] [menu_id] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	     * @throws Exception 
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/menuGrantAuthSave.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView menuGrantAuthSave(ModelMap model, HttpServletRequest request) throws Exception{        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	        /**************** 세션 정보 호출 Start ****************/
 			Box sessionInfo = null;
 			try {
 				sessionInfo = loginMethod.getAdminSessionKey(request);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	
 			if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
 				modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.SESSION_FAIL);
	    		return modelAndView;
 			}
 			/**************** 세션 정보 호출 End ****************/
 			
 			/**************** user 권한 체크 Start ****************/
		    box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.AUTH_FAIL);
	    		return modelAndView;
	    	}
	    	/**************** user 권한 체크 End ****************/
		    
			if ("".equals(box.getString("auth_id"))  || "".equals(box.getString("menu_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	int totCnt = kdnAuthService.getMenuGrantAuthCnt(box); 
	        	if(totCnt > 0){
	        		kdnAuthService.menuGrantAuthUpdate(box);
	        	}else{
	        		kdnAuthService.menuGrantAuthSave(box); 
	        	}
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	   
	   
}
