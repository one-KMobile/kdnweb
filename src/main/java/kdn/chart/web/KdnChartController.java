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
package kdn.chart.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.chart.service.KdnChartService;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
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
public class KdnChartController {
	
	/** KdnChartService */
    @Resource(name = "kdnChartService")
    private KdnChartService kdnChartService;
    
    /** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** Validator */
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
    /** LoginMethod */
    @Resource(name="loginMethod")
    LoginMethod loginMethod;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @RequestMapping("/kdn/chart/statsLogDataView.do")
	public String statsLogDataView(HttpServletRequest request, ModelMap model) throws Exception {
    	Box box = BoxUtil.getBox(request);
    	List <Box> fstBizplcList = new ArrayList<Box>();
    	
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
	    
		//1차사업소 목록
		try {
			fstBizplcList = kdnEqpService.getFstBizplcList(box);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("fstBizplcList", fstBizplcList);
		
		return "/kdn/statistics/statsLogDataView";
	}
    
    /**
	 * <설명>
	 * 로그접속통계 차트 쿼리
	 * @param [fst_bizplc_cd] [scd_bizplc_cd] [startDate] [endDate] 
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/ajax/chart/getLogData.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getTowerList(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
        if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd")) || "".equals(box.getString("startDate")) || "".equals(box.getString("endDate"))) { // 필수 파라메터 체크
			modelAndView.addObject(ConstantValue.RESULT_CODE,
					ConstantValue.RESULT_FAIL_REQUIRE);
			return modelAndView;
		}
        
        try {
        	List<Box> list = kdnChartService.getLogData(box);
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
        	modelAndView.addObject("getLogDataList",list);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }
	    
}
