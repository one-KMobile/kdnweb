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
package kdn.insResultStat.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.eqp.service.KdnEqpService;
import kdn.insResultStat.service.KdnInsResultStatService;
import kdn.login.web.LoginMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import egovframework.com.cmm.EgovMessageSource;
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
public class KdnInsResultStatController {
	
    /** KdnInsResultStatService */
    @Resource(name = "kdnInsResultStatService")
    private KdnInsResultStatService kdnInsResultStatService;
    
    /** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
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
    private String controllerURL = "/kdn/admin/insResultList.do";
    
    /**
   	 * [순시통계] 목록을 조회한다. (pageing)
   	 * @param [request] [model] 
   	 * @return "/kdn/insResult/insStatsList"
   	 * @exception Exception
   	 * @author [신명섭] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
   	 */
   @RequestMapping(value="/kdn/admin/insStatsList.do")
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

	    //1차 사업소 목록
		List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
		int totCnt = 0;
		if(!"".equals(box.getString("basicMonth"))){
			box.put("fst_bizplc_cd", sessionInfo.getString("fst_bizplc_cd"));
			box.put("scd_bizplc_cd", sessionInfo.getString("scd_bizplc_cd"));
			box.put("fst_bizplc_cd", box.getString("fst_bizplc_cd"));
			box.put("scd_bizplc_cd", box.getString("scd_bizplc_cd"));
			box.put("fnct_lc_no", box.getString("fnct_lc_no"));
			box.put("cycle_ym" , box.getString("basicYear")+box.getString("basicMonth"));
			list = kdnInsResultStatService.getInsResultList(box);
			model.addAttribute("list", list);
			totCnt = kdnInsResultStatService.getInsResultStatListTotCnt(box);
		}
		
		model.addAttribute("box", box);
		model.addAttribute("fstBizplcList", fstBizplcList);
		model.addAttribute("totCnt", totCnt);
	    return "/kdn/insResult/insResultStatList";
   }
}

