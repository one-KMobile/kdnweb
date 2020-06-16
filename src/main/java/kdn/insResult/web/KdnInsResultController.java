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
package kdn.insResult.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.FileManager;
import kdn.cmm.util.FileManagerService;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.eqp.service.KdnEqpService;
import kdn.file.service.KdnFileService;
import kdn.insResult.service.KdnInsResultService;
import kdn.insResult.service.impl.KdnInsResultMethod;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springmodules.validation.commons.DefaultBeanValidator;

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
public class KdnInsResultController {
	
	/** KdnInsResultService */
    @Resource(name = "kdnInsResultService")
    private KdnInsResultService kdnInsResultService;
    
    /** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
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
    
    /** KdnCodeService */
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /** kdnFileService */
    @Resource(name="kdnFileService")
    private KdnFileService kdnFileService;
    
    /** fileManagerService */
    @Resource(name="fileManagerService")
    private FileManagerService fileManagerService;
    
    /** fileManagerService */
    @Resource(name="fileManager")
    private FileManager fileManager;
    
    @Resource(name="kdnInsResultMethod")
    private KdnInsResultMethod kdnInsResultMethod;
    
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL = "/kdn/admin/insResultList.do";
    
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    /**
   	 * [순시결과] 목록을 조회한다. (pageing)
   	 * @param [request] [model] 
   	 * @return "/kdn/insResult/insResultList"
   	 * @exception Exception
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
   	 */
   @RequestMapping(value="/kdn/admin/insResultList.do")
   public String insResultList(HttpServletRequest request, ModelMap model) throws Exception {
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
		
		int totCnt = 0;
		if(!"".equals(box.getString("basicMonth"))){
			box.put("fst_bizplc_cd", sessionInfo.getString("fst_bizplc_cd"));
			box.put("scd_bizplc_cd", sessionInfo.getString("scd_bizplc_cd"));
			
			list = kdnInsResultService.getInsResultList(box);
			model.addAttribute("list", list);
		       
			totCnt = kdnInsResultService.getInsResultListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
		}
		
		model.addAttribute("box", box);
		model.addAttribute("totCnt", totCnt);
	    model.addAttribute("paginationInfo", paginationInfo);
	       
	    return "/kdn/insResult/insResultList";
   }
	   
   /**
	 * [순시결과] 상세 화면을 조회한다.
	 * @param [request] [model]
	 * @return "/kdn/insResult/insResult + 순시코드 + View"
	 * @exception Exception
	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/admin/insResultView.do")
    public String insResultView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	Box viewBox = null;
    	List <Box> subList = new ArrayList<Box>();
    	List<Box> fileList = new ArrayList<Box>();
		
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
		
		String insTypeCd = "";
        try {
        	if(box.get("schedule_id") != null && !box.get("schedule_id").equals("") ){
        		viewBox = kdnInsResultService.getInsResultTopView(box);
        		
        		insTypeCd = viewBox.getString("code_id");
        		if("001".equals(insTypeCd)){ /*보통순시*/
        			subList = kdnInsResultService.getInsResult001SubList(box);
        			fileList = kdnInsResultMethod.getFileInfo(subList);
        			model.addAttribute("subList", subList);
        		}else if("025".equals(insTypeCd)){ /*항공장애등등구확인점검*/
        			subList = kdnInsResultService.getInsResult025SubList(box);
        			fileList = kdnInsResultMethod.getFileInfo(subList);
        			model.addAttribute("subList", subList);
        		}else if("024".equals(insTypeCd)){ /*항공장애등점검*/
        			subList = kdnInsResultService.getInsResult024SubList(box);
        			fileList = kdnInsResultMethod.getFileInfo(subList);
        			model.addAttribute("subList", subList);
        		}else if("028".equals(insTypeCd)){ /*접지저항측정점검*/
        			subList = kdnInsResultService.getInsResult028SubList(box);
        			fileList = kdnInsResultMethod.getFileInfo(subList);
        			model.addAttribute("subList", subList);
        		}else if("027".equals(insTypeCd)){ /*전선접속개소점검*/
        			subList = kdnInsResultService.getInsResult027SubList(box);
        			
        			Box resultBox = null;
        			List <Box> addSubList = new ArrayList<Box>();
        			String[] CType = new String[3];
        			
        			for (int i = 0; i < subList.size(); i++) {
        				resultBox = subList.get(i);
					
	        			CType = resultBox.getString("rv").split("@@");
	        			
	        			String[] C1_value = new String[6];
	        			String[] C2_value = new String[6];
	        			String[] C3_value = new String[6];
	        			for (int j = 0; j < CType.length; j++) {
	        				if( j == 0 ){
	        					C1_value = CType[j].split("_");
	        					for (int k = 0; k < C1_value.length; k++) {
	        						resultBox.put("C1_"+ k, C1_value[k]);
								}
	        				}
	        				if( j == 1 ){
	        					C2_value = CType[j].split("_");
	        					for (int k = 0; k < C2_value.length; k++) {
	        						resultBox.put("C2_"+ k, C2_value[k]);
								}
	        				}
							if( j== 2 ){
								C3_value = CType[j].split("_");
								for (int k = 0; k < C3_value.length; k++) {
	        						resultBox.put("C3_"+ k, C3_value[k]);
								}
							}
						}
	        			addSubList.add(resultBox);
        			}
        			model.addAttribute("subList", addSubList);
        			fileList = kdnInsResultMethod.getFileInfo(addSubList);
        		}else if("026".equals(insTypeCd)){ /*점퍼접속개소점검*/
        			subList = kdnInsResultService.getInsResult026SubList(box); 
        			
        			Box resultBox = null;
        			List <Box> addSubList = new ArrayList<Box>();
        			String[] CType = new String[3];
        			
        			for (int i = 0; i < subList.size(); i++) {
        				resultBox = subList.get(i);
					
	        			CType = resultBox.getString("rv").split("@@");
	        			
	        			String[] C1_value = new String[6];
	        			String[] C2_value = new String[6];
	        			String[] C3_value = new String[6];
	        			for (int j = 0; j < CType.length; j++) {
	        				if( j == 0 ){
	        					C1_value = CType[j].split("_");
	        					for (int k = 0; k < C1_value.length; k++) {
	        						resultBox.put("C1_"+ k, C1_value[k]);
								}
	        				}
	        				if( j == 1 ){
	        					C2_value = CType[j].split("_");
	        					for (int k = 0; k < C2_value.length; k++) {
	        						resultBox.put("C2_"+ k, C2_value[k]);
								}
	        				}
							if( j== 2 ){
								C3_value = CType[j].split("_");
								for (int k = 0; k < C3_value.length; k++) {
	        						resultBox.put("C3_"+ k, C3_value[k]);
								}
							}
						}
	        			addSubList.add(resultBox);
        			}
        			model.addAttribute("subList", addSubList);
        			fileList = kdnInsResultMethod.getFileInfo(addSubList);
        		}else{
        			
        		} 
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        model.addAttribute("box", box);
        model.addAttribute("viewBox", viewBox);
        model.addAttribute("fileList", fileList);
        
        return "/kdn/insResult/insResult" + insTypeCd + "View";
    }
    
    /**
 	 * [순시결과] 불량애자 상세 화면을 조회한다.
 	 * @param [request] [model]
 	 * @return "/kdn/insResult/insResult029View"
 	 * @exception Exception
 	 * @author [정현도] 
    	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
     @RequestMapping("/kdn/admin/insResult029View.do")
     public String insResult029View(HttpServletRequest request, Model model) throws Exception {
     	Box box = BoxUtil.getBox(request);
     	Box viewBox = null;
     	List <Box> subList = new ArrayList<Box>();
     	List <Box> fileList = new ArrayList<Box>();
 		
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
         	if(box.get("schedule_id") != null && !box.get("schedule_id").equals("") ){
         		viewBox = kdnInsResultService.getInsResultTopView(box);

         		List<Box> masterList = kdnInsResultService.getMasterIdx(box);
              	StringBuffer sb = new StringBuffer();
              	sb.append("'");
            	for(int i=0; i<masterList.size(); i++) {
            		Box masterBox = masterList.get(i);
            		sb.append(masterBox.getString("MASTER_IDX")).append("','");
            	}
              	
              	String masterAllList = sb.substring(0, sb.length()-2);
              	box.put("master_idx", masterAllList);
         		subList = kdnInsResultService.getInsResult029SubList(box);

         	}
         	fileList = kdnInsResultMethod.getFileInfo(subList);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
     	
         model.addAttribute("box", box);
         model.addAttribute("viewBox", viewBox);
         model.addAttribute("subList", subList);
         model.addAttribute("fileList", fileList);
         
         return "/kdn/insResult/insResult029View";
     }
    
     /**
	   	 * [보통순시 - 001] 순시결과 사진이미지 팝업
	   	 * @param [request] [model] 
	   	 * @return "/kdn/menu/menuList"
	   	 * @exception Exception
	   	 * @author [정현도] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @RequestMapping("/kdn/admin/pop/insResult001View.do")
	   public String popInsResult001View(HttpServletRequest request, ModelMap model) throws Exception {
		   ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
		   Box box = BoxUtil.getBox(request);
		   //**************** 세션 정보 호출 Start ****************//
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		/*	if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.SESSION_FAIL);
	    		return modelAndView;
			}*/
			//**************** 세션 정보 호출 End ****************//
			
			//**************** user 권한 체크 Start ****************//
		  /*  box.put("user_auth", sessionInfo.getString("user_auth"));
			box.put("controller_method", controllerURL);
		    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
		    	modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.AUTH_FAIL);
	    		return modelAndView;
	    	}*/
	    	//**************** user 권한 체크 End ****************//
		    
			/**************** 세션 정보 호출 Start ****************/
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
		    List<Box> fileList = kdnFileService.getFileInfo(box);
		    //List<Box> fileList = new ArrayList<Box>();
		    /*for(int i=0; i<list.size(); i++) {
		    	Box fileBox = list.get(i);
		    	String file_realpath = fileManager.thumJAIResizeCreate(fileBox.getString("file_realpath"), 100, 100);
		    	file_realpath = file_realpath.substring(file_realpath.indexOf("webapp") +6, file_realpath.length());
		    	
		    	fileBox.put("file_realpath", file_realpath);
		    	fileList.add(fileBox);
		    }*/
		    model.addAttribute("fileList", fileList);
	    	return "/kdn/pop/insResultView";
	    }
	   
	     /**
		   	 * [보통순시 - 001] 순시결과 사진이미지 팝업
		   	 * @param [request] [model] 
		   	 * @return "/kdn/menu/menuList"
		   	 * @exception Exception
		   	 * @author [정현도] 
		   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
		   	 */
		   @RequestMapping("/kdn/admin/delete/insResult.do")
		   public String deleteInsResult(HttpServletRequest request, ModelMap model) throws Exception {
			   ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
			   Box box = BoxUtil.getBox(request);
			   //**************** 세션 정보 호출 Start ****************//
				Box sessionInfo = null;
				try {
					sessionInfo = loginMethod.getAdminSessionKey(request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			/*	if (sessionInfo == null || "".equals(sessionInfo.getString("user_id"))) {
					modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.SESSION_FAIL);
		    		return modelAndView;
				}*/
				//**************** 세션 정보 호출 End ****************//
				
				//**************** user 권한 체크 Start ****************//
			  /*  box.put("user_auth", sessionInfo.getString("user_auth"));
				box.put("controller_method", controllerURL);
			    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
			    	modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.AUTH_FAIL);
		    		return modelAndView;
		    	}*/
		    	//**************** user 권한 체크 End ****************//
			    
				/**************** 세션 정보 호출 Start ****************/
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
			    
			    kdnInsResultService.setMasterDelete(box);
			    kdnInsResultService.set001Delete(box);
			    kdnInsResultService.set026Delete(box);
			    kdnInsResultService.setSub026Delete(box);
			    kdnInsResultService.set027Delete(box);
			    kdnInsResultService.setSub027Delete(box);
			    kdnInsResultService.set025Delete(box);
			    kdnInsResultService.set028Delete(box);
			    kdnInsResultService.setSub028Delete(box);
			    kdnInsResultService.set029Delete(box);
			    kdnInsResultService.setSub021Delete(box);
			    kdnInsResultService.set024Delete(box);
			    
			    return "/kdn/insResult/insResultList";
		    }
}

