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

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.eqp.service.KdnEqpService;
import kdn.insResult.service.KdnInsResultMantService;
import kdn.insResult.service.KdnInsResultService;
import kdn.insResult.service.impl.KdnInsResultMethod;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;

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
public class KdnInsResultMantController {
	
	/** KdnInsResultMantService */
    @Resource(name = "kdnInsResultMantService")
    private KdnInsResultMantService kdnInsResultMantService;
    
    /** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
    /** LoginMethod */
    @Resource(name="loginMethod")
    LoginMethod loginMethod;
    
    /** KdnInsResultService */
    @Resource(name = "kdnInsResultService")
    private KdnInsResultService kdnInsResultService;
    
    /** KdnCodeService */
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name="kdnInsResultMethod")
    private KdnInsResultMethod kdnInsResultMethod;
    
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL = "/kdn/admin/insResultList.do";
    
   /**
  	 * [순시결과] 목록을 조회한다. (pageing)
  	 * @param [request] [model] 
  	 * @return "/kdn/insResult/insResultGGView"
  	 * @exception Exception
  	 * @author [신명섭] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
  	 */
  @RequestMapping(value="/kdn/admin/insResultGGView.do")
  public String insResultMantList(HttpServletRequest request, ModelMap model) throws Exception {
	  Box box = BoxUtil.getBox(request);
	  Box viewBox = null;
	  List <Box> subList = null;
	  List <Box> fileList = null;
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
			  subList = kdnInsResultMantService.getInsResultGGT(box);
			  fileList = kdnInsResultMethod.getFileInfo(subList);
		  }
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  model.addAttribute("box", box);
	  model.addAttribute("viewBox", viewBox);
	  model.addAttribute("subList", subList);
	  model.addAttribute("fileList", fileList);
	  return "/kdn/insResult/insResultGGView";
  }
}
