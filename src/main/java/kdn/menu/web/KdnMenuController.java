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
package kdn.menu.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.login.web.LoginMethod;
import kdn.menu.service.KdnMenuService;

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
public class KdnMenuController {
	
	/** KdnMenuService */
    @Resource(name = "kdnMenuService")
    private KdnMenuService kdnMenuService;
    
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
    private String controllerURL = "/kdn/admin/menuList.do";
    
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
	   	 * [메뉴] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/kdn/menu/menuList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @RequestMapping("/kdn/admin/menuList.do")
	   public String menuList(HttpServletRequest request, ModelMap model) throws Exception {
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
		    
			System.out.println("@@@@@@@@@@@@request.getServletPath() = " + request.getServletPath());
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
			
		    list = kdnMenuService.getMenuList(box);
			model.addAttribute("list", list);
		       
		    int totCnt = kdnMenuService.getMenuListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
			
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
		    model.addAttribute("paginationInfo", paginationInfo);
		       
		    return "/kdn/menu/menuList";
	   }
	   
	   /**
		 * [메뉴] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/menu/menuView"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/menuView.do")
	    public String menuView(HttpServletRequest request, Model model) throws Exception {
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
	        	if(box.get("menu_idx") != null && !box.get("menu_idx").equals("")){
	        		viewBox = kdnMenuService.getMenuView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/menu/menuView";
	    } 	   
	   
	   /**
		 * [메뉴] 등록 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/menu/menuWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/menuWrite.do")
	    public String menuWrite(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
	    	List <Box> topMenuIdList = new ArrayList<Box>();
	    	
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
	        	if(box.get("menu_idx") != null && !box.get("menu_idx").equals("") ){
	        		viewBox = kdnMenuService.getMenuView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        //상위메뉴ID 선택 리스트
			try {
				topMenuIdList = kdnMenuService.topMenuIdList(box);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("topMenuIdList", topMenuIdList);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/menu/menuWrite";
	    } 
	    
	    /**
		 * [공통코드] 등록 저장.
		 * @param [request] [model]
		 * @return "/kdn/admin/menuList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/menuSave.do")
	    public String menuSave(HttpServletRequest request, Model model) throws Exception {
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
	    	
	    	if("".equals(unscript(box.getString("menu_id"))) || "".equals(unscript(box.getString("menu_nm"))) || "".equals(unscript(box.getString("menu_account")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(메뉴ID, 메뉴명, 메뉴 설명) 의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/menuList.do";
	    	}
	    	
	    	try {
	    		int OverlapCount = kdnMenuService.getMenuIdOverlapCount(box);
	    		
	    		if(OverlapCount <= 0){
	    			kdnMenuService.menuSave(box);
	    		}
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('메뉴를 등록하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/menuList.do";
	    } 
	    
	    /**
		 * [메뉴] 수정.
		 * @param [request] [model]
		 * @return "/kdn/admin/menuList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/menuUpdate.do")
	    public String menuUpdate(HttpServletRequest request, Model model) throws Exception {
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
	    	
	    	if("".equals(unscript(box.getString("menu_idx")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(메뉴 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/menuList.do";
	    	}
	    	
	    	try {
	    		kdnMenuService.menuUpdate(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('메뉴를 수정하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	return "forward:/kdn/admin/menuList.do";
	    }
	    
	    /**
		 * [메뉴] 삭제.
		 * @param [request] [model]
		 * @return "/kdn/admin/menuList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/menuDelete.do")
	    public String menuDelete(HttpServletRequest request, Model model) throws Exception {
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
		         ReusltScript += "alert('필수 항목(메뉴 선택 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/menuList.do";
	    	}
	    	
	    	try {
	    		kdnMenuService.menuDelete(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	return "forward:/kdn/admin/menuList.do";
	    }
	    
	    /**
		 * <설명>
		 * [메뉴] 메뉴ID 중복 체크 카운트
		 * @param [menu_id]  
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/menuIdOverlapCount.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView menuIdOverlapCount(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
			if ("".equals(box.getString("menu_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	int totCnt = kdnMenuService.getMenuIdOverlapCount(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
	        	modelAndView.addObject("overlapCnt",totCnt);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
}
