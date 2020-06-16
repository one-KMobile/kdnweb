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
package kdn.eqp.web;

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
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;
import kdn.nfc.service.KdnNfcService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class KdnEqpController {
	
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
    
    /** KdnNfcService */
    @Resource(name="kdnNfcService")
    private KdnNfcService kdnNfcService;
    
    /** KdnCodeService */
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
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
	   	 * [송전설비] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/kdn/eqp/eqpList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @IncludedInfo(name="송전설비", order = 2 ,gid = 100)
	   @RequestMapping(value="/kdn/admin/eqpList.do")
	   public String eqpList(HttpServletRequest request, ModelMap model) throws Exception {
		    Box box = BoxUtil.getBox(request);
		    List <Box> list = new ArrayList<Box>();
		    List <Box> codeList = new ArrayList<Box>();
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
			
		   	/**************** pageing setting Start ****************/
		    PaginationInfo paginationInfo = kdnCommonUtil.getWebPaging(box);
			/**************** pageing setting End ****************/
			
			// 순시종류 [코드]
			box.put("groupCodeId", "INS_TYPE");
			try {
				codeList = kdnCodeService.selectCodeList(box);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//1차사업소 목록
			try {
				fstBizplcList = kdnEqpService.getFstBizplcList(box);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int totCnt = 0;
			if(!"".equals(box.getString("searchCnd1"))){
			    list = kdnEqpService.getEqpList(box);
				model.addAttribute("list", list);
				
			    totCnt = kdnEqpService.getEqpListTotCnt(box);
				paginationInfo.setTotalRecordCount(totCnt);
			}
						
			model.addAttribute("box", box);
			model.addAttribute("codeList", codeList);
			model.addAttribute("fstBizplcList", fstBizplcList);
			model.addAttribute("totCnt", totCnt);
		    model.addAttribute("paginationInfo", paginationInfo);
		       
		    return "/kdn/eqp/eqpList";
	   }
	   
	    /**
		 * <설명>
		 * [송전설비] 2차사업소 목록
		 * @param [fst_bizplc_cd] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/getScdBizplcList.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getScdBizplcList(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        List <Box> list = new ArrayList<Box>();
	        
			if ("".equals(box.getString("fst_bizplc_cd"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	list = kdnEqpService.getScdBizplcList(box); 
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("scdBizplcList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
		 * <설명>
		 * [송전설비] 선로명 목록
		 * @param [fst_bizplc_cd] [scd_bizplc_cd] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/getTracksList.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getTracksList(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        List <Box> list = new ArrayList<Box>();
	        
	        if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	list = kdnEqpService.getTracksList(box); 
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("tracksList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
		 * <설명>
		 * [송전설비] 선로명, 1차사업소, 2차사업소, 지지물명
		 * @param [fst_bizplc_cd] [scd_bizplc_cd] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [정현도]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/getEqpInfo.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getEqpInfo(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	       /* if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}*/
	        
	        try {
	        	Box info = kdnEqpService.getEqpInfo(box);
	        	info.put("fst_bizplc_cd", info.getString("FST_BIZPLC_CD"));
	        	info.put("scd_bizplc_cd", info.getString("SCD_BIZPLC_CD"));
	        	Box bizplc = kdnNfcService.getSelectedBizplc(info);
	        	info.put("FST_BIZPLC_CD_NM", bizplc.getString("fst_bizplc_cd_nm"));
	        	info.put("SCD_BIZPLC_CD_NM", bizplc.getString("scd_bizplc_cd_nm"));
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("eqpInfo",info);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
		 * <설명>
		 * [송전설비] 지지물명 목록
		 * @param [fnct_lc_no] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [정현도]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/getTowerList.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getTowerList(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	       /* if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}*/
	        
	        try {
	        	List<Box> list = kdnEqpService.getTowerList(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("towerList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
	    /**
		 * <설명>
		 * [송전설비] 지도 맵(위도, 경도) 가져오기
		 * @param [fst_bizplc_cd] [scd_bizplc_cd] [fnct_lc_no]
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/getEqpMapInfoList.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getEqpMapInfoList(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	        if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd")) || "".equals(box.getString("fnct_lc_no"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	List<Box> list = kdnEqpService.getEqpMapInfoList(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("eqpMapInfoList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
}
