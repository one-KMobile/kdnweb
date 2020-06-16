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
package kdn.insResult.imsi.web;

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
import kdn.insResult.imsi.service.KdnInsResultImsiService;
import kdn.insResult.service.impl.KdnInsResultMethod;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.rte.fdl.property.EgovPropertyService;

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
public class KdnInsResultImsiController {
	
	/** KdnInsResultService */
    @Resource(name = "kdnInsResultImsiService")
    private KdnInsResultImsiService kdnInsResultImsiService;
    
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
	 * [순시결과] 상세 화면을 조회한다.
	 * @param [request] [model]
	 * @return "/kdn/insResult/insResult + 순시코드 + View"
	 * @exception Exception
	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @RequestMapping("/kdn/admin/insResultImsiView.do")
    public String insResultImsiView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	Box viewBox = null;
    	List <Box> subList = new ArrayList<Box>();
		
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
        	if(box.getString("ins_ty_cd") != null && !box.getString("ins_ty_cd").equals("") ){
        		insTypeCd = box.getString("ins_ty_cd");
        	}else{
        		insTypeCd = "001";
        		box.put("ins_ty_cd", "001");
        	}
        	
        	if(insTypeCd != null && !insTypeCd.equals("") ){
        		viewBox = kdnInsResultImsiService.getInsResultTopView(box);
        		
        		if("001".equals(insTypeCd)){ /*보통순시*/
        			subList = kdnInsResultImsiService.getInsResult001SubList(box);
        			model.addAttribute("subList", subList);
        		}else if("025".equals(insTypeCd)){ /*항공장애등등구확인점검*/
        			subList = kdnInsResultImsiService.getInsResult025SubList(box);
        			model.addAttribute("subList", subList);
        		}else if("024".equals(insTypeCd)){ /*항공장애등점검*/
        			subList = kdnInsResultImsiService.getInsResult024SubList(box);
        			model.addAttribute("subList", subList);
        		}else if("028".equals(insTypeCd)){ /*접지저항측정점검*/
        			subList = kdnInsResultImsiService.getInsResult028SubList(box);
        			model.addAttribute("subList", subList);
        		}else if("021".equals(insTypeCd)){ /*기별점검*/
        			subList = kdnInsResultImsiService.getInsResult021SubList(box);
        			model.addAttribute("subList", subList);
        		}else if("029".equals(insTypeCd)){ /*불량애자*/
        			subList = kdnInsResultImsiService.getInsResult029SubList(box);
        			model.addAttribute("subList", subList);
        		}else if("027".equals(insTypeCd)){ /*전선접속개소점검*/
        			System.out.println("####################11111111");
        			subList = kdnInsResultImsiService.getInsResult027SubList(box);
        			System.out.println("####################2222222222");
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
        		}else if("026".equals(insTypeCd)){ /*점퍼접속개소점검*/
        			subList = kdnInsResultImsiService.getInsResult026SubList(box); 
        			
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
        		}else{
        			
        		} 
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        model.addAttribute("box", box);
        model.addAttribute("viewBox", viewBox);
        System.out.println("@@@=" + insTypeCd);
        return "/kdn/insResult/imsi/insResult" + insTypeCd + "View";          
    }
    
}

