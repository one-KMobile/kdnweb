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
package kdn.api.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javassist.bytecode.ByteArray;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.api.service.KdnApiMethod;
import kdn.api.service.KdnApiService;
import kdn.aria.Cipher;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.FileManager;
import kdn.cmm.util.FileManagerService;
import kdn.cmm.util.KdnCommonUtil;
import kdn.file.service.KdnFileService;
import kdn.login.web.LoginMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0;

import egovframework.com.cmm.ComDefaultCodeVO;
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
public class KdnApiController {
	
	/** EgovSampleService */
    @Resource(name = "kdnApiService")
    private KdnApiService kdnApiService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** Validator */
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    @Autowired
    @Qualifier("loginMethod")
    private LoginMethod loginMethod;
    
    @Autowired
    @Qualifier("kdnApiMethod")
    private KdnApiMethod kdnApiMethod;
    
    @Autowired
    @Qualifier("fileManagerService")
    private FileManagerService fileManagerService;
    
    @Autowired
    @Qualifier("fileManager")
    private FileManager fileManager;
    
    @Resource(name = "kdnFileService")
    private KdnFileService kdnFileService;
    
    // 코드에서 검색항목 불러오기 
    ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
    
    int cnt = 0;
    
    /**
	 * <설명>
	 * 공지사항 리스트
	 * @param [notice_cago_cd]  
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/api/apiNoticeList.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView apiNoticeList(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
        try {
        	List<Box> list = kdnApiService.getApiNoticeList(box);
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
        	modelAndView.addObject("apiNoticeList",list);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }
    
    /**
	 * <설명>
	 * 공지사항 상세 정보
	 * @param [board_idx]  
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/api/apiNoticeView.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView apiNoticeView(ModelMap model, HttpServletRequest request){
    	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
    	Box box = BoxUtil.getBox(request);
    	Box viewBox = null; 
		
    	if("".equals(box.getString("board_idx"))){ //필수 파라메터 체크
     	   modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_REQUIRE);
 		   return modelAndView;
         }
    	
        try {
        	if(box.get("board_idx") != null && !box.get("board_idx").equals("") ){
        		viewBox = kdnApiService.getApiNoticeView(box);
        	}
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
        	modelAndView.addObject("apiNoticeView",viewBox);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}
        return modelAndView;
    }
    
    /**
	 * <설명>
	 * 코드 정보 리스트
	 * @param [group_code_id]  
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/api/apiCodeInfoList.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView apiCodeInfoList(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
        /*if("".equals(box.getString("group_code_id"))){ //필수 파라메터 체크
      	   modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_REQUIRE);
  		   return modelAndView;
          }*/
        
        try {
        	//세션키 있는지 없는지 체크
        	/*if(!loginMethod.isExistSessionKey(request)) {
        		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
        		return modelAndView;
        	}*/
        	
        	List<Box> list = kdnApiService.getApiCodeInfoList(box);
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
        	modelAndView.addObject("apiCodeInfoList",list);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }
    
        
        /**
    	 * <설명>
    	 * 순시결과 API
    	 * @param [tracks_name, ins_date, ins_type_code, start_ins_date, end_ins_date]
    	 * @return [modelAndView]
    	 * @throws [예외명] [설명] // 각 예외 당 하나씩
    	 * @author [정현도]
    	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
    	 * @URL테스트: 
    	 */ 
        @RequestMapping(value="/api/inspection/result.*", method={RequestMethod.POST,RequestMethod.GET})
        public ModelAndView apiInspectionResult(ModelMap model, HttpServletRequest request){        
            ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
            Box box = BoxUtil.getBox(request);
            
            try {
            	//세션키 있는지 없는지 체크
            	/*if(!loginMethod.isExistSessionKey(request)) {
            		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
            		return modelAndView;
            	}*/
            	
            	//세션키 정보 호출
            	Box sessionBox = loginMethod.getSessionKey(request);
            	box.put("fst_bizplc_code", sessionBox.getString("fst_bizplc_cd"));
            	box.put("scd_bizplc_code", sessionBox.getString("scd_bizplc_cd"));
            	if(!"".equals(box.getString("start_ins_date")) || !"".equals(box.getString("end_ins_date"))) {
            		box.put("date_status", "Y");
            	}else {
            		box.put("date_status", "");
            	}
            	
            	//순시결과 정보 호출
            	List<Box> inspectionResultList = kdnApiService.getApiSearchInspectionResult(box);
            	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
            	modelAndView.addObject("inspectionResultList",inspectionResultList);
            	return modelAndView;
    		} catch (Exception e) {
    			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
    			e.printStackTrace();
    			return modelAndView;
    		}    
            
        }      
        
        /**
     	 * 선로목록
     	 * @param - box[ 
     	 * @return list - [선로 목록]
     	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
     	 * @author [정현도] 
     	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
     	 * @exception api검토
     	 */
         @RequestMapping(value="/api/tracks/list.*", method={RequestMethod.POST,RequestMethod.GET})
         public ModelAndView apiTracksList(ModelMap model, HttpServletRequest request) {
         	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
             Box box = BoxUtil.getBox(request);
             
             try {
             	/*if(!loginMethod.isExistSessionKey(request)) {
             		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
             		return modelAndView;
             	}*/
             	/*box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_code"));
            	box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_code"));*/
            	box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
            	box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));

            	//사업소 정보 호출
            	Box bizplcBox = kdnApiService.getBizcode(box);
            	box.put("fnct_lc_ty_nm", bizplcBox.getString("FNCT_LC_TY_NM"));
            	//선로목록 호출
            	List<Box> tracksList = kdnApiService.getTracksList(box);
            	modelAndView.addObject("tracksList", tracksList);
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
             	return modelAndView;
             }catch(Exception e) {
            	e.printStackTrace(); 
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
             	return modelAndView;
             }
         }
        
         /**
     	 * 스케줄 결과검색
     	 * @param - box[fnct_lc_no, ins_ty_cd] 
     	 * @return list - [스케줄 결과검색]
     	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
     	 * @author [정현도] 
     	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
     	 * @exception api검토
     	 */
         @RequestMapping(value="/api/inspection/result/in/schedule.*", method={RequestMethod.POST,RequestMethod.GET})
         public ModelAndView apiInspectionResultInSchedule(ModelMap model, HttpServletRequest request) {
         	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
             Box box = BoxUtil.getBox(request);
             
             /*if("".equals(box.getString("fnct_lc_no"))) {
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
             	return modelAndView;
             }*/
             try {
             	/*if(!loginMethod.isExistSessionKey(request)) {
             		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
             		return modelAndView;
             	}*/
            	 box.put("user_id", loginMethod.getSessionKey(request).getString("user_id"));
            	 List<Box> resultScheduleList = null;
            	if("true".equals(box.getString("first"))) {
            		//스케쥴 처리
            		resultScheduleList = kdnApiMethod.getScheduleMethod(box, request);
            	}else {
            		String insPlanNo = kdnApiService.getLatestContract(box).getString("INS_PLAN_NO"); //최신스케줄20140300000000000293
                 	//System.out.println("################# insPlanNo ################# "+insPlanNo);
            		if(!box.getString("insplan_no").equals(insPlanNo)) { // 박스 20140300000000000226
            			//스케쥴 처리
            			resultScheduleList = kdnApiMethod.getScheduleMethod(box, request);
            		}
            	}
            	
             	modelAndView.addObject("schedule", resultScheduleList);
             	//modelAndView.addObject("planno", "20140300000000000226");
             	//System.out.println("################# one test ################# "+resultScheduleList);
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
             	return modelAndView;
             }catch(Exception e) {
             	e.printStackTrace();
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
             	return modelAndView;
             }
         }
         
         /**
     	 * 회선정보 호출
     	 * @param - box[]
     	 * @return list - [스케줄 결과검색]
     	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
     	 * @author [정현도] 
     	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
     	 * @exception api검토
     	 */
         @RequestMapping(value="/api/circuit/in/schedule.*", method={RequestMethod.POST,RequestMethod.GET})
         public ModelAndView apiCircuitInSchedule(ModelMap model, HttpServletRequest request) {
         	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
             Box box = BoxUtil.getBox(request);
             
             /*if("".equals(box.getString("fnct_lc_no"))) {
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
             	return modelAndView;
             }*/
             try {
             	/*if(!loginMethod.isExistSessionKey(request)) {
             		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
             		return modelAndView;
             	}*/
            	 
             	//box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
             	//box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));
            	Box sessionBox = loginMethod.getSessionKey(request);
            	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
            	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
                //System.out.println("############fnct_lc_no2############## "+sessionBox.getString("fst_bizplc_cd"));
             	
             	//전선접속개소, 접지저항일 경우
             	List<Box> list = kdnApiService.getCircuitList(box);
             	
             	modelAndView.addObject("result", list);
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
             	return modelAndView;
             }catch(Exception e) {
             	e.printStackTrace();
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
             	return modelAndView;
             }
         }
         /**
      	 * 불량애자 호출
      	 * @param - box[]
      	 * @return list - [스케줄 결과검색]
      	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
      	 * @author [정현도] 
      	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
      	 * @exception api검토
      	 */
          @RequestMapping(value="/api/poor/insulators.*", method={RequestMethod.POST,RequestMethod.GET})
          public ModelAndView apiPoorInsulators(ModelMap model, HttpServletRequest request) {
          	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
              Box box = BoxUtil.getBox(request);
              
              /*if("".equals(box.getString("fnct_lc_no"))) {
              	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
              	return modelAndView;
              }*/
              try {
              	/*if(!loginMethod.isExistSessionKey(request)) {
              		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
              		return modelAndView;
              	}*/
             	 
              	box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
              	box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));
              	
              	//불량애자 호출
              	List<Box> list = kdnApiService.getPoorInsulators(box);
              	
              	modelAndView.addObject("result", list);
              	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
              	return modelAndView;
              }catch(Exception e) {
              	e.printStackTrace();
              	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
              	return modelAndView;
              }
          }
         
          /**
        	 * 항공장애 호출
        	 * @param - box[]
        	 * @return list - [스케줄 결과검색]
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
        	 * @exception api검토
        	 */
            @RequestMapping(value="/api/error/air.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiErrorAir(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                
                /*if("".equals(box.getString("fnct_lc_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }*/
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
               	 
                	box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
                	box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));
                	box.put("fnct_lc_ty_nm", "가공선로 기능위치");
                  	List<Box> tracksList = kdnApiService.getTracksList(box);
                  	StringBuffer sb = new StringBuffer();
                  	sb.append("'");
                	for(int i=0; i<tracksList.size(); i++) {
                		Box tracksBox = tracksList.get(i);
                		sb.append(tracksBox.getString("FNCT_LC_NO")).append("','");
                	}
                  	
                  	String trackAllList = sb.substring(0, sb.length()-2);
                  	box.put("fnct_lc_no", trackAllList);
                	//항공장애
                	List<Box> list = kdnApiService.getErrorAir(box);
                	
                	modelAndView.addObject("result", list);
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	e.printStackTrace();
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	return modelAndView;
                }
            }
            
        /**
     	 * 지지물 리스트
     	 * @param - box[ 
     	 * @return list - [지지물 리스트]
     	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
     	 * @author [정현도] 
     	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
     	 */
         @RequestMapping(value="/api/trans/tower/list.*", method={RequestMethod.POST,RequestMethod.GET})
         public ModelAndView apiTransTowerList(ModelMap model, HttpServletRequest request) {
         	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
             Box box = BoxUtil.getBox(request);
             if("".equals(box.getString("fnct_lc_no"))) {
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
             	return modelAndView;
             }
             
             try {
             	/*if(!loginMethod.isExistSessionKey(request)) {
             		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
             		return modelAndView;
             	}*/
             	
             	List<Box> transTowerList = kdnApiService.getTransTowerList(box);
             	modelAndView.addObject("transTowerList", transTowerList);
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
             	return modelAndView;
             }catch(Exception e) {
             	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
             	return modelAndView;
             }
         }
         
         /**
      	 * 지지물 검색
      	 * @param - box[fnct_lc_no, eqp_nm] 
      	 * @return int - [항공쟁애점등점검 등록]
      	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
      	 * @author [정현도] 
      	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
      	 * @exception api검토
      	 */
          @RequestMapping(value="/api/search/trans/tower.*", method={RequestMethod.POST,RequestMethod.GET})
          public ModelAndView apiSearchTransTower(ModelMap model, HttpServletRequest request) {
          	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
              Box box = BoxUtil.getBox(request);
              /*if("".equals(box.getString("fnct_lc_no"))) {
            	  modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
            	  return modelAndView;
              }*/
              
              try {
              	/*if(!loginMethod.isExistSessionKey(request)) {
              		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
              		return modelAndView;
              	}*/
            	//Box sessionBox = loginMethod.getSessionKey(request);
            	box.put("fnct_lc_ty_nm", "가공선로 기능위치");
            	//box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
            	//box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
            	box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
            	box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));
              	List<Box> tracksList = kdnApiService.getTracksList(box);
              	StringBuffer sb = new StringBuffer();
              	sb.append("'");
            	for(int i=0; i<tracksList.size(); i++) {
            		Box tracksBox = tracksList.get(i);
            		sb.append(tracksBox.getString("FNCT_LC_NO")).append("','");
            	}
              	String trackAllList = sb.substring(0, sb.length()-2);
              	if("".equals(box.getString("fnct_lc_no"))) {
              		box.put("fnct_lc_no", trackAllList);
              	}else {
              		box.put("fnct_lc_no", "'".concat(box.getString("fnct_lc_no")).concat("'"));
              	}
              	List<Box> searchedTransTower = kdnApiService.getSearchedTransTower(box);
              	//System.out.println("리스트 튜플수 : " + searchedTransTower.size());

              	modelAndView.addObject("searchedTransTower", searchedTransTower);
              	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
              	return modelAndView;
              }catch(Exception e) {
              	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
              	return modelAndView;
              }
          }
          
          /**
       	 * 지지물 위도 경도 업데이트
       	 * @param - box[fnct_lc_no]
       	 * @return int - 성공
       	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
       	 * @author [정현도] 
       	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
       	 */
           @RequestMapping(value="/api/latitude/with/longitude/update.*", method={RequestMethod.POST,RequestMethod.GET})
           public ModelAndView apiLatitudeWithLongitudeUpdate(ModelMap model, HttpServletRequest request) {
           	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
               Box box = BoxUtil.getBox(request);
               if("".equals(box.getString("fnct_lc_no"))) {
             	  modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
             	  return modelAndView;
               }
               double latitude = box.getDouble("latitude");
               double longitude = box.getDouble("longitude");
               try {
               		List<Box> towerIdxList = kdnApiService.getTowerIdx(box);
               		for(int i=0; i<towerIdxList.size(); i++) {
               			Box towerIdxBox = towerIdxList.get(i);
               			towerIdxBox.put("eqp_no", towerIdxBox.getString("EQP_NO"));
               			if(i < box.getInt("offset") + 10) {
               				latitude = latitude - 0.002000;
               				longitude = longitude - 0.002000;
	               			towerIdxBox.put("latitude", latitude);
	               			towerIdxBox.put("longitude", longitude);
	               			kdnApiService.setLatitudeWithLongitudeUpdate(towerIdxBox);
	               			//System.out.println("10 la: " + towerIdxBox.getString("latitude"));
	               			//System.out.println("10 lo: " + towerIdxBox.getString("longitude"));
               			}else if (i < box.getInt("offset") + 20) {
               				latitude = latitude + 0.003000;
               				longitude = longitude + 0.003000;
               				towerIdxBox.put("latitude", latitude);
                   			towerIdxBox.put("longitude", longitude);
                   			kdnApiService.setLatitudeWithLongitudeUpdate(towerIdxBox);
                   			//System.out.println("20 la: " + towerIdxBox.getString("latitude"));
	               			//System.out.println("20 lo: " + towerIdxBox.getString("longitude"));
               			}else if (i <box.getInt("offset") + 30) {
               				if(i == box.getInt("offset") + 20) {
               					latitude = box.getDouble("latitude");
               				}
               				latitude = latitude + 0.002000;
               				longitude = longitude - 0.002000;
	               			towerIdxBox.put("latitude", latitude);
	               			towerIdxBox.put("longitude", box.getString("longitude"));
	               			kdnApiService.setLatitudeWithLongitudeUpdate(towerIdxBox);
	               			//System.out.println("30 la: " + towerIdxBox.getString("latitude"));
	               			//System.out.println("30 lo: " + towerIdxBox.getString("longitude"));
               			}else {
               				if(i==box.getInt("offset") + 30) {
               					longitude = box.getDouble("longitude");
               					latitude = box.getDouble("latitude");
               				}
               				longitude = longitude - 0.002000;
               				latitude = latitude + 0.003000;
	               			towerIdxBox.put("latitude", box.getString("latitude"));
	               			towerIdxBox.put("longitude", longitude);
	               			kdnApiService.setLatitudeWithLongitudeUpdate(towerIdxBox);
	               			//System.out.println("40 la: " + towerIdxBox.getString("latitude"));
	               			//System.out.println("40 lo: " + towerIdxBox.getString("longitude"));
               			}/*else if(i < 50) {
               				latitude = latitude + 0.0000300;
               				longitude = longitude + 0.0000300;
               				towerIdxBox.put("latitude", latitude);
               				towerIdxBox.put("longitude", longitude);
               				kdnApiService.setLatitudeWithLongitudeUpdate(towerIdxBox);
               				System.out.println("50 la: " + towerIdxBox.getString("latitude"));
	               			System.out.println("50 lo: " + towerIdxBox.getString("longitude"));
               			}else {
               				latitude = latitude + 0.0000300;
               				longitude = longitude - 0.0000300;
               				towerIdxBox.put("latitude", latitude);
               				towerIdxBox.put("longitude", longitude);
               				kdnApiService.setLatitudeWithLongitudeUpdate(towerIdxBox);
               				System.out.println("60 la: " + towerIdxBox.getString("latitude"));
	               			System.out.println("60 lo: " + towerIdxBox.getString("longitude"));
               			}*/
               		}
               	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
               	return modelAndView;
               }catch(Exception e) {
               	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
               	return modelAndView;
               }
           }
           
           /**
        	 * 순시자 트레킹
        	 * @param - box[user_id, latitude, longitude, remark] 
        	 * @return int - []
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
        	 * @exception api검토
        	 */
            @RequestMapping(value="/api/traking/user/save.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiTrakingUserSave(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                if("".equals(box.getString("user_id")) || "".equals(box.getString("latitude")) || "".equals(box.getString("longitude")) || "".equals(box.getString("device_token"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
               	  	return modelAndView;
                }
                try{
                	//Box userInfo = kdnApiService.getTrakingUserInfo(box);
                	//if(userInfo == null) {
                	kdnApiService.setTrakingInsert(box);
                	/*}else {
                		kdnApiService.setTrakingUpdate(box);
                	}*/
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
            }
            
            /**
         	 * NFC 태그 정보
         	 * @param - box[eqp_no] 
         	 * @return box - [tag]
         	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
         	 * @author [정현도] 
         	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
         	 * @exception api검토
         	 */
             @RequestMapping(value="/api/nfc/tag/info.*", method={RequestMethod.POST,RequestMethod.GET})
             public ModelAndView apiNfcTagInfo(ModelMap model, HttpServletRequest request) {
             	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                 Box box = BoxUtil.getBox(request);
                 if("".equals(box.getString("eqp_no"))) {
                	 modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	 return modelAndView;
                 }
                 try{
                 	//Box userInfo = kdnApiService.getTrakingUserInfo(box);
                 	//if(userInfo == null) {
                 	Box nfcTagInfo = kdnApiService.getTagInfo(box);
                 	/*}else {
                 		kdnApiService.setTrakingUpdate(box);
                 	}*/
                 	modelAndView.addObject("nfcTagInfo", nfcTagInfo);
                 	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                 	return modelAndView;
                 }catch(Exception e) {
                 	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                 	e.printStackTrace();
                 	return modelAndView;
                 }
             }
            /**
          	 * 순시결과 보통순시 입력
          	 * @param box - [schedule_id
          							, fnct_lc_no
          							, eqp_no
          							, cycle_ym
          							, ins_type_code
          							, work_type
          							, nfc_id
          							, ins_date
          							, check_ins_name_a
          							, check_ins_name_b
          							, report_date
          							, weather_code
          							, check_ins_co_count
          							, remarks
          							, latitude
          							, longitude
          							, tag_yn
          							, reg_id
									, tins_result_secd
									, examin_cn
									, result_file
									, imprmn_request_no
									, gubun
									, tid]
          	 * @return int 
          	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
          	 * @author [정현도] 
          	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
             * @throws UnsupportedEncodingException 
          	 */   
            @RequestMapping(value="/api/result/normal/inspection.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiResultNormalInspection(ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
            	request.setCharacterEncoding("UTF-8");
                Box box = BoxUtil.getBox(request);
                
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                
                try{
        			
                	//복호화       	
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");
                	
                	String user_id = box.getString("reg_id");
					String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
                	if(!"".equals(box.getString("file_subject1"))) {
		               	box.put("reg_id", user_id);
		               	fileManagerService.saveFile(box, request, "NORMAL_INSPECTION", group_file_id, user_id);
		               	box.put("group_file_id", group_file_id);
                	}
                	
                	//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	Box isExistMaterIdx = kdnApiService.isExistMaster(box);
                	//마스터 인덱스가 좋재하지 않으면 보통순시 입력
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
	                	
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);
	                	
	                	//보통순시 입력
	                	kdnApiService.setNormalInspectionInsert(box);
	                //마스터 인덱스가 존재하면 보통순시 수정	
            		}else {
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
            			//파일수정
            			boolean isDelete = false;
            			if(!"".equals(box.getString("file_subject1"))) {
            				isDelete = fileManagerService.deleteFile(box, request, "NORMAL_INSPECTION", group_file_id);
            				fileManagerService.deleteFileDB(box);
            				if(isDelete == true) {
            					fileManagerService.saveFile(box, request, "NORMAL_INSPECTION", group_file_id, user_id);
            					//box.put("group_file_id", group_file_id);
            				}
            			}
            			//보통순시 수정
            			kdnApiService.setNormalInspectionUpdate(box);
            		}
                	
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
            }
            
            /**
          	 * 순시결과 점퍼접속개소 입력
          	 * @param box - [schedule_id
          							, fnct_lc_no
          							, eqp_no
          							, cycle_ym
          							, ins_type_code
          							, nfc_id
          							, check_ins_name_a
          							, check_ins_name_b
          							, weather_code
          							, remarks
          							, latitude
          							, longitude
          							, tag_yn
          							, reg_id
									, out_temp
									, circuit_code
									, measure_load
									, position_grp
									, position_dt
									, c1_temper_w
									, c1_temper_c
									, c1_temp_cha
									, c2_temper_w
									, c2_temper_c
									, c2_temp_cha
									, c3_temper_w
									, c3_temper_c
									, c3_temp_cha]
          	 * @return int 
          	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
          	 * @author [정현도] 
          	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
          	 */   
            @RequestMapping(value="/api/connection/point/jumper/insert.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiConnectionPointJumperInsert(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                try{
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");

                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	fileManagerService.saveFile(box, request, "CONNECTION_POINT_JUMPER", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
	                	isExistMaterIdx = kdnApiService.isExistMaster(box);
            		}
            		Box bbBox = new Box();
            		bbBox.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            		bbBox.put("cl_no", box.getString("cl_no"));
            		bbBox.put("pwln_eqp_no", box.getString("pwln_eqp_no1"));
            		Box isExistBBIdx = kdnApiService.isExistPwlneqpnoJumper(bbBox);
            		if(isExistBBIdx == null) {
	                	//순시결과 마스터 인덱스 검색
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);

	                	//점퍼접속개소 측정 입력
	                	for(int i=1; i<=48; i++) {
	                		if("".equals(box.getString("pwln_eqp_no" + i))) {
	                			break;
	                		}
	                		box.put("pwln_eqp_no", box.getString("pwln_eqp_no" + i));

	                		//점퍼접속개소 입력
			                kdnApiService.setConnectionPointJumper(box);

		                	//점퍼접속개소 인덱스 검색
		                	String resultIdx = kdnApiService.getConnectionPointJumperIdx(box).getString("RESULT_IDX");
		                	box.put("result_idx", resultIdx);
		                	//점퍼접속개소 측정 입력
	                		box.put("cabl_tp", box.getString("cabl_tp"+ i));
	                		box.put("cnpt_tp", box.getString("cnpt_tp"+ i));
	                		box.put("good_secd", box.getString("good_secd"+ i));
	                		kdnApiService.setMeasuringConnectionPointJumper(box);
	                	}
            		}else {
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            			
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
    	               	//점퍼접속개소 측정 수정
    	               	for(int i=1; i<=48; i++) {
    	               		if("".equals(box.getString("pwln_eqp_no" + i))) {
    	               			break;
    	               		}
    	               		box.put("pwln_eqp_no", box.getString("pwln_eqp_no" + i));
    	               		//Box isExistPwlneqpnoJumper = kdnApiService.isExistPwlneqpnoJumper(box);
    	               		//파일수정
    	               		if(!"".equals(box.getString("file_subject1"))) {
    	               			boolean isDelete = false;
    	            			isDelete = fileManagerService.deleteFile(box, request, "CONNECTION_POINT_JUMPER", group_file_id);
    	            			fileManagerService.deleteFileDB(box);
    	            			if(isDelete == true) {
    	            				fileManagerService.saveFile(box, request, "CONNECTION_POINT_JUMPER", group_file_id, user_id);
    	            			}
    	            			//점퍼접속개소 수정
    	               			kdnApiService.setConnectionPointJumperUpdate(box);
    	               		}
    	               		
                			//점퍼접속개소 인덱스 검색
    	               		String resultIdx = kdnApiService.isExistPwlneqpnoJumper(box).getString("RESULT_IDX");
        	               	box.put("result_idx", resultIdx);
    	               		Box isExistBBBBIdx = kdnApiService.isExistBBBB(box);
    	               		if(isExistBBBBIdx == null) {
    	               		//점퍼접속개소 측정 입력
    	                		box.put("cabl_tp", box.getString("cabl_tp"+ i));
    	                		box.put("cnpt_tp", box.getString("cnpt_tp"+ i));
    	                		box.put("good_secd", box.getString("good_secd"+ i));
    	                		kdnApiService.setMeasuringConnectionPointJumper(box);
    	               		}else {
    	               			//점퍼접속개소 측정 수정
	    	               		box.put("cabl_tp", box.getString("cabl_tp"+ i));
	    	               		box.put("cnpt_tp", box.getString("cnpt_tp"+ i));
	    	               		box.put("good_secd", box.getString("good_secd"+ i));
	    	               		kdnApiService.setMeasuringConnectionPointJumperUpdate(box);
    	               		}
    	               	}
            			
            		}
            		cnt = cnt + 1;
                	//System.out.println("!!!!!!!!!!!!!!!!!!!" + cnt);
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
            }
            
            /**
          	 * 순시결과 전선접속개소 입력
          	 * @param box - [schedule_id
          							, fnct_lc_no
          							, eqp_no
          							, cycle_ym
          							, ins_type_code
          							, nfc_id
          							, check_ins_name_a
          							, check_ins_name_b
          							, weather_code
          							, remarks
          							, latitude
          							, longitude
          							, tag_yn
          							, reg_id
									, tim_load
									, cndctr_co
									, result_file
									, file_subject
									, file_contents
									, cl_no
									, cndctr_sn
									, cabl_tp1
									, cnpt_tp1
									, good_secd1
									, cabl_tp2
									, cnpt_tp2
									, good_secd2
									, cabl_tp3
									, cnpt_tp3
									, good_secd3
									, imprmn_request_no]
          	 * @return int 
          	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
          	 * @author [정현도] 
          	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
          	 */   
            @RequestMapping(value="/api/connection/point/wire/insert.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiConnectionPointWireInsert(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                try{
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");

                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	fileManagerService.saveFile(box, request, "CONNECTION_POINT_WIRE", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
	                	isExistMaterIdx = kdnApiService.isExistMaster(box);
            		}
            		Box ccBox = new Box();
            		ccBox.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));

            		ccBox.put("cl_no", box.getString("cl_no"));
            		ccBox.put("pwln_eqp_no", box.getString("pwln_eqp_no1"));
            		Box isExistCCIdx = kdnApiService.isExistPwlneqpno(ccBox);
            		if(isExistCCIdx == null) {
	                	//순시결과 마스터 인덱스 검색
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);

	                	//전선접속개소 측정 입력
	                	for(int i=1; i<=48; i++) {
	                		if("".equals(box.getString("pwln_eqp_no" + i))) {
	                			break;
	                		}
	                		box.put("pwln_eqp_no", box.getString("pwln_eqp_no" + i));

	                		//전선접속개소 입력
			                kdnApiService.setConnectionPointWire(box);
		                	//전선접속개소 인덱스 검색
		                	String resultIdx = kdnApiService.getConnectionPointWireIdx(box).getString("RESULT_IDX");
		                	box.put("result_idx", resultIdx);
		                	//전선접속개소 측정 입력
	                		box.put("cabl_tp", box.getString("cabl_tp"+ i));
	                		box.put("cnpt_tp", box.getString("cnpt_tp"+ i));
	                		box.put("good_secd", box.getString("good_secd"+ i));
	                		kdnApiService.setMeasuringConnectionPointWire(box);
	                	}
            		}else {
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
   			
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
    	               	//전선접속개소 측정 수정
    	               	for(int i=1; i<=48; i++) {
    	               		if("".equals(box.getString("pwln_eqp_no" + i))) {
    	               			break;
    	               		}
    	               		box.put("pwln_eqp_no", box.getString("pwln_eqp_no" + i));
    	               		//Box isExistPwlneqpno = kdnApiService.isExistPwlneqpno(box);
    	               		//파일수정
    	               		if(!"".equals(box.getString("file_subject1"))) {
    	               			boolean isDelete = false;
    	            			isDelete = fileManagerService.deleteFile(box, request, "CONNECTION_POINT_WIRE", group_file_id);
    	            			fileManagerService.deleteFileDB(box);
    	            			if(isDelete == true) {
    	            				fileManagerService.saveFile(box, request, "CONNECTION_POINT_WIRE", group_file_id, user_id);
    	            			}
    	            			//전선접속개소 수정
    	               			kdnApiService.setConnectionPointWireUpdate(box);
    	               		}
    	               		
                			//전선접속개소 인덱스 검색
    	               		String resultIdx = kdnApiService.isExistPwlneqpno(box).getString("RESULT_IDX");
        	               	box.put("result_idx", resultIdx);
    	               		Box isExistCCCCIdx = kdnApiService.isExistCCCC(box);
    	               		if(isExistCCCCIdx == null) {
    	               		//전선접속개소 측정 입력
    	                		box.put("cabl_tp", box.getString("cabl_tp"+ i));
    	                		box.put("cnpt_tp", box.getString("cnpt_tp"+ i));
    	                		box.put("good_secd", box.getString("good_secd"+ i));
    	                		kdnApiService.setMeasuringConnectionPointWire(box);
    	               		}else {
    	               			//전선접속개소 측정 수정
	    	               		box.put("cabl_tp", box.getString("cabl_tp"+ i));
	    	               		box.put("cnpt_tp", box.getString("cnpt_tp"+ i));
	    	               		box.put("good_secd", box.getString("good_secd"+ i));
	    	               		kdnApiService.setMeasuringConnectionPointWireUpdate(box);
    	               		}
    	               	}
            			
            		}
                	cnt = cnt + 1;
                	//System.out.println("!!!!!!!!!!!!!!!!!!!" + cnt);
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
            }
            
            /**
        	 * 항공장애점등점검 등록
        	 * @param - box - [schedule_id
          							, fnct_lc_no
          							, eqp_no
          							, cycle_ym
          							, ins_type_code
          							, nfc_id
          							, check_ins_name_a
          							, check_ins_name_b
          							, weather_code
          							, check_ins_co_count
          							, remarks
          							, latitude
          							, longitude
          							, tag_yn
          							, reg_id
									, flight_lmp_knd
									, flight_lmp_no
									, srcelct_knd
									, flck_co
									, lightg_stadiv_cd
									, good_secd
									, result_file
									, imprmn_request_no] 
        	 * @return int - [항공쟁애점등점검 등록]
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
        	 */
            @RequestMapping(value="/api/check/air/failure/light.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiCheckAirFailureLight(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");
                	
                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	
	                	fileManagerService.saveFile(box, request, "CHECK_AIR_FAILURE_LIGHT", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);

	                	//순시결과 마스터 인덱스 검색
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);
	                	//항공장애점등점검 입력
	                	kdnApiService.setCheckAirFailureLight(box);
            		}else {
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            			
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
            			Box isExistDDIdx = kdnApiService.isExistDD(box);
            			if(isExistDDIdx == null) {
            				//파일수정
            				if(!"".equals(box.getString("file_subject1"))) {
    	               			boolean isDelete = false;
    	            			isDelete = fileManagerService.deleteFile(box, request, "CHECK_AIR_FAILURE_LIGHT", group_file_id);
    	            			fileManagerService.deleteFileDB(box);
    	            			if(isDelete == true) {
    	            				fileManagerService.saveFile(box, request, "CHECK_AIR_FAILURE_LIGHT", group_file_id, user_id);
    	            			}
    	               		}
            				//항공장애점등점검 입력
    	                	kdnApiService.setCheckAirFailureLight(box);
            			}else {
	            			//항공장애등점검 수정
	            			kdnApiService.setCheckAirFailureLightUpdate(box);
            			}
            		}
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            /**
        	 * 항공장애점검 등록
        	 * @param - box - [schedule_id
          							, fnct_lc_no
          							, eqp_no
          							, cycle_ym
          							, ins_type_code
          							, nfc_id
          							, check_ins_name_a
          							, check_ins_name_b
          							, weather_code
          							, check_ins_co_count
          							, remarks
          							, latitude
          							, longitude
          							, tag_yn
          							, reg_id
	     							, flight_lmp_knd
	     							, flight_lmp_no
	     							, srcelct_knd
	     							, ctrl_ban_gdbd_secd
	     							, slrcl_gdbd_secd
	     							, srgbtry_gdbd_secd
	     							, rgist_gu_gdbd_secd
	     							, cabl_gdbd_secd
	     							, good_secd
	     							, imprmn_request_no
	     							, result_file] 
        	 * @return int - [항공쟁애점검 등록]
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
        	 */
            @RequestMapping(value="/api/check/air/failure.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiCheckAirFailure(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");
                	
                	//파일생성
                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
            		if(!"".equals(box.getString("file_subject1"))) {
	                	fileManagerService.saveFile(box, request, "CHECK_AIR_FAILURE", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
            		
	                	//순시결과 마스터 인덱스 검색
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);
	                	//항공장애점검 입력
	                	kdnApiService.setCheckAirFailure(box);
            		}else {
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            			
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
            			Box isExistHH = kdnApiService.isExistHH(box);
            			if(isExistHH == null) {
            				//파일수정
            				if(!"".equals(box.getString("file_subject1"))) {
    	               			boolean isDelete = false;
    	            			isDelete = fileManagerService.deleteFile(box, request, "CHECK_AIR_FAILURE", group_file_id);
    	            			fileManagerService.deleteFileDB(box);
    	            			if(isDelete == true) {
    	            				fileManagerService.saveFile(box, request, "CHECK_AIR_FAILURE", group_file_id, user_id);
    	            			}
    	               		}
            				//항공장애점검 입력
    	                	kdnApiService.setCheckAirFailure(box);
            			}else {
	            			//항공장애점검 수정
	            			kdnApiService.setCheckAirFailureUpdate(box);
            			}
            		}
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }            
            
            /**
        	 * 접지저항측정 입력
        	 * @param - box - [schedule_id
	          							, fnct_lc_no
	          							, eqp_no
	          							, cycle_ym
	          							, ins_type_code
	          							, nfc_id
	          							, check_ins_name_a
	          							, check_ins_name_b
	          							, weather_code
	          							, check_ins_co_count
	          							, remarks
	          							, latitude
	          							, longitude
	          							, tag_yn
	          							, reg_id
										, good_secd
										, pmttr
		     							, msr_rs1
		     							, msr_rs2
		     							, msr_rs3
		     							, msr_rs4
		     							, msr_rs5
										, imprmn_request_no
										, result_file] 
        	 * @return int - []
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
        	 */
            @RequestMapping(value="/api/ground/resistance/measurements.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiGroundResistanceMeasurements(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");
                	
                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	
	                	fileManagerService.saveFile(box, request, "GROUND_RESISTANCE_MEASUREMENTS", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
	                	
	                	//순시결과 마스터 인덱스 검색
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);
	                	//저항기준값 호출
	                	String resistanceValue = kdnApiService.getResistanceStandards(box).getString("RESISTANCE_VALUE");
	                	box.put("stdr_rs", resistanceValue);
	                	//접지저항측정 입력
	                	kdnApiService.setGroundResistanceMeasurements(box);
	                	//순시결과 접지저항 인덱스 검색
	                	String resultIdx = kdnApiService.getGroundResistanceMeasurementsIdx(box).getString("RESULT_IDX");
	                	box.put("result_idx", resultIdx);
	                	//순시결과 접지저항 측정 입력
	                	for(int i =1; i<=5; i++) {
	                		box.put("msr_odr", i);
	                		box.put("msr_rs", box.getString("msr_rs" +i));
	                		kdnApiService.setSubGroundResistanceMeasurements(box);
	                	}
            		} else {
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            			
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
            			//파일수정
            			if(!"".equals(box.getString("file_subject1"))) {
	               			boolean isDelete = false;
	            			isDelete = fileManagerService.deleteFile(box, request, "GROUND_RESISTANCE_MEASUREMENTS", group_file_id);
	            			fileManagerService.deleteFileDB(box);
	            			if(isDelete == true) {
	            				fileManagerService.saveFile(box, request, "GROUND_RESISTANCE_MEASUREMENTS", group_file_id, user_id);
	            			}
	               		}
	                	//접지저항측정 입력
	                	kdnApiService.setGroundResistanceMeasurementsUpdate(box);
	                	//순시결과 접지저항 인덱스 검색
	                	String resultIdx = kdnApiService.getGroundResistanceMeasurementsIdx(box).getString("RESULT_IDX");
	                	box.put("result_idx", resultIdx);
	                	//순시결과 접지저항 측정 입력
	                	for(int i =1; i<=5; i++) {
	                		box.put("msr_odr", i);
	                		box.put("msr_rs", box.getString("msr_rs" +i));
	                		kdnApiService.setSubGroundResistanceMeasurementsUpdate(box);
	                	}
            			
            		}
                	
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            /**
        	 * 불량애자검출 입력
        	 * @param - box - [schedule_id
	          							, fnct_lc_no
	          							, eqp_no
	          							, cycle_ym
	          							, ins_type_code
	          							, nfc_id
	          							, check_ins_name_a
	          							, check_ins_name_b
	          							, weather_code
	          							, check_ins_co_count
	          							, remarks
	          							, latitude
	          							, longitude
	          							, tag_yn
	          							, reg_id
	          							, ty_secd
	          							, phs_secd
	          							, cl_no
	          							, insbty_lft
			  							, insbty_rit
			  							, insr_qy
			  							, bad_insr_qy
			  							, good_secd
	          							, ym
	          							, biz
	          							, unity_ins_no
	          							, insr_eqp_no
	          							, result_file] 
        	 * @return int - 
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
        	 */
            @RequestMapping(value="/api/agile/poor/detection.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiAgilePoorDetection(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");
                	//System.out.println("eqp_no 확인 : " + box.getString("eqp_no"));
                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	
	                	fileManagerService.saveFile(box, request, "AGILE_POOR_DETECTION", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
            		}

	               	//순시결과 마스터 인덱스 검색
	               	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	               	box.put("master_idx", masterIdx);
	               	
	               	//마스터 수정
        			kdnApiService.setMasterUpdate(box);
        			
	               	Box isExistFFIdx = kdnApiService.isExistFF(box);
	               	if(isExistFFIdx == null) {
	               		//파일수정
	               		if(!"".equals(box.getString("file_subject1"))) {
	               			boolean isDelete = false;
	            			isDelete = fileManagerService.deleteFile(box, request, "AGILE_POOR_DETECTION", group_file_id);
	            			fileManagerService.deleteFileDB(box);
	            			if(isDelete == true) {
	            				fileManagerService.saveFile(box, request, "AGILE_POOR_DETECTION", group_file_id, user_id);
	            			}
	               		}
	               		//불량애자검출 입력
	               		kdnApiService.setAgilePoorDetection(box);
	               	}else {
	            		box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
	            		
	            		//불량애자검출 수정
	            		kdnApiService.setAgilePoorDetectionUpdate(box);
	            	}

                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            /**
        	 * 기별 및 정밀점검 입력
        	 * @param - box - [schedule_id
	          							, fnct_lc_no
	          							, eqp_no
	          							, cycle_ym
	          							, ins_type_code
	          							, nfc_id
	          							, check_ins_name_a
	          							, check_ins_name_b
	          							, weather_code
	          							, check_ins_co_count
	          							, remarks
	          							, latitude
	          							, longitude
	          							, tag_yn
	          							, reg_id
			  							, item1
			  							, item2
			  							, item3
			  							, item4
			  							, item5
			  							, item6
			  							, item7
			  							, item8
			  							, item9
			  							, item10
			  							, item11
			  							, item12
			  							, item13
			  							, item14
			  							, item15
			  							, item16
			  							, item17
			  							, item18] 
        	 * @return int - 
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
        	 */
            @RequestMapping(value="/api/message/and/thorough/inspection.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiMessageAndThoroughInspection(ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                Box box = BoxUtil.getBox(request);
                if("".equals(box.getString("schedule_id")) || "".equals(box.getString("fnct_lc_no")) || "".equals(box.getString("eqp_no"))) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	return modelAndView;
                }
                
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
                	//복호화
                	box = Cipher.getDecryptBox(box);
                	box.remove("master_key");
                	
                	String user_id = box.getString("reg_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("eqp_no"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	
	                	fileManagerService.saveFile(box, request, "MESSAGE_AND_THOROUGH_INSPECTION", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
            		//송전설비 지역정보 입력
                	if(!"".equals(box.getString("latitude"))) {
                		kdnApiMethod.setEqpAreaInfo(box);
                	}
                	
            		Box isExistMaterIdx = kdnApiService.isExistMaster(box);
            		if(isExistMaterIdx == null) {
	                	//순시결과 마스터 입력
	                	kdnApiService.setMasterInsert(box);
            		
	                	//순시결과 마스터 인덱스 검색
	                	String masterIdx = kdnApiService.getMasterIdx(box).getString("MASTER_IDX");
	                	box.put("master_idx", masterIdx);
	                	//순시결과 기별정검 인덱스 검색
	                	String[] itemCd = new String[19];
	                	//기별점검 측정 입력
	                	for(int i=1; i<19; i++) {
	                		itemCd[i] = box.getString("item" + i);
	                		box.put("ins_itm_cd", i);
	                		box.put("good_secd", itemCd[i]);
	                		kdnApiService.setSubMessageAndThoroughInspection(box);
	                	}
            		}else {
            			//파일수정
            			if(!"".equals(box.getString("file_subject1"))) {
	               			boolean isDelete = false;
	            			isDelete = fileManagerService.deleteFile(box, request, "MESSAGE_AND_THOROUGH_INSPECTION", group_file_id);
	            			fileManagerService.deleteFileDB(box);
	            			if(isDelete == true) {
	            				fileManagerService.saveFile(box, request, "MESSAGE_AND_THOROUGH_INSPECTION", group_file_id, user_id);
	            			}
	               		}
            			box.put("master_idx", isExistMaterIdx.getString("MASTER_IDX"));
            			
            			//마스터 수정
            			kdnApiService.setMasterUpdate(box);
            			
            			String[] itemCd = new String[19];
            			//기별점검 측정 입력
	                	for(int i=1; i<19; i++) {
	                		itemCd[i] = box.getString("item" + i);
	                		box.put("ins_itm_cd", i);
	                		box.put("good_secd", itemCd[i]);
	                		kdnApiService.setSubMessageAndThoroughInspectionUpdate(box);
	                	}
            		}
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            /**
        	 * 부순시자 리스트 
        	 * @param - box 
        	 * @return list
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
        	 */
            @RequestMapping(value="/api/sub/patrolmans.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiSubPatrolmans(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                //Box box = BoxUtil.getBox(request);
                
                try {
                	/*if(!loginMethod.isExistSessionKey(request)) {
                		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                		return modelAndView;
                	}*/
                	//세션정보 호출
                	Box sessionBox = loginMethod.getSessionKey(request);
                	//부순시자 검색
                	List<Box> list = kdnApiService.getSubPatrolmanList(sessionBox);
                	modelAndView.addObject("patrolmans", list);
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            /**
        	 * 예방순시스케줄 리스트
        	 * @param - box [fnct_lc_no]
        	 * @return list
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
        	 */
            @RequestMapping(value="/api/preventing/inspection.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiPreventingInspection(ModelMap model, HttpServletRequest request) {
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
               Box box = BoxUtil.getBox(request);
                
            	if("".equals(box.getString("fnct_lc_no"))) {
            		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
            		return modelAndView;
            	}
                try {
                	//세션정보 호출
                	Box sessionBox = loginMethod.getSessionKey(request);
                	//예방순시 검색
                	sessionBox.put("fnct_lc_no", box.getString("fnct_lc_no"));
                	List<Box> list = kdnApiService.getPreventingInspection(sessionBox);
                	modelAndView.addObject("preventingInspection", list);
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            /**
        	 * 순시결과 예방순시 입력
        	 * @param - box [result_idx
									, before_eqp_no
									, after_eqp_no
									, cwrk_spt_nm
									, tins_result_secd
									, tins_result
									, group_file_id
									, imprmn_request_no
									, gubun
									, tid]
        	 * @return list
        	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
        	 * @author [정현도] 
        	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
        	 */
            @RequestMapping(value="/api/preventing/inspection/insert.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiPreventingInspectionInsert(ModelMap model, HttpServletRequest request) throws Exception{
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
            	request.setCharacterEncoding("UTF-8");
               Box box = BoxUtil.getBox(request);
	               if("".equals(box.getString("rgt_sn")) || "".equals(box.getString("fnct_lc_no"))) {
	               	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
	               	return modelAndView;
	               }
                try {
                	//복호화
                	/*box = Cipher.getDecryptBox(box);
                	box.remove("master_key");*/
                	
                	Box sessionBox = loginMethod.getSessionKey(request);
                	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
                	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
                	
                	String user_id = sessionBox.getString("user_id");
                	String group_file_id = user_id.concat("_").concat(box.getString("rgt_sn"));
                	//파일생성
            		if(!"".equals(box.getString("file_subject1"))) {
	                	
	                	fileManagerService.saveFile(box, request, "PREVENTING_INSPECTION", group_file_id, user_id);
	                	box.put("group_file_id", group_file_id);
            		}
            		
                	List<Box> list = kdnApiService.getPreventingInspection(box);
                	Box rgtsnIsExist = kdnApiService.getRgtsnIsExist(box);
                	for(int i=0; i<list.size(); i++ ) {
                		Box insertBox = list.get(i);
                		box.put("before_eqp_no", insertBox.getString("BEGIN_EQP_NM"));
                		box.put("after_eqp_no", insertBox.getString("END_EQP_NM"));
                		box.put("cwrk_spt_nm", insertBox.getString("CWRK_NM"));
                		if(rgtsnIsExist != null) {
                			//파일수정
    	               		if(!"".equals(box.getString("file_subject1"))) {
    	               			boolean isDelete = false;
    	            			isDelete = fileManagerService.deleteFile(box, request, "PREVENTING_INSPECTION", group_file_id);
    	            			fileManagerService.deleteFileDB(box);
    	            			if(isDelete == true) {
    	            				fileManagerService.saveFile(box, request, "PREVENTING_INSPECTION", group_file_id, user_id);
    	            			}
    	               		}
    	               		
                			kdnApiService.setPreventingInspectionUpdate(box);
                		}else {
	                		//순시결과 예방순시 입력
	                    	kdnApiService.setPreventingInspection(box);
                		}
                	}
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            @RequestMapping(value="/api/schedule/excel/download.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiScheduleExcelDownload(ModelMap model, HttpServletRequest request) throws Exception{
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
            	request.setCharacterEncoding("UTF-8");
                try {
                	kdnApiMethod.getExcelSchedule(request);
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            
            @RequestMapping(value="/api/aaa/bbb.*", method={RequestMethod.POST,RequestMethod.GET})
            public ModelAndView apiAaaBbb(ModelMap model, HttpServletRequest request) throws Exception{
            	Box box = BoxUtil.getBox(request);
            	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
            	request.setCharacterEncoding("UTF-8");
                try {
                	List<Box> list = kdnApiService.getLatitude(box);
                	for(int i =0; i<list.size(); i++) {
                		Box listBox = list.get(i);
                		listBox.put("eqp_no", listBox.getString("EQP_NO"));
                		String address = kdnApiMethod.getAddressByLatitudeLongitude(listBox.getString("LATITUDE"), listBox.getString("LONGITUDE"));
                		listBox.put("address", address);
                		kdnApiService.setAddress(listBox);
                	}
                	
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                	return modelAndView;
                }catch(Exception e) {
                	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                	e.printStackTrace();
                	return modelAndView;
                }
                
            }
            /**
          	 * < 지중순시 모바일 시스템 > 설비검색
          	 * @param - box[fnct_lc_no, eqp_nm] 
          	 * @return int - [항공쟁애점등점검 등록]
          	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
          	 * @author [정현도] 
          	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
          	 * @exception api검토
          	 */
              @RequestMapping(value="/api/search/facility/eqplist.*", method={RequestMethod.POST,RequestMethod.GET})
              public ModelAndView apiSearchFacilityList(ModelMap model, HttpServletRequest request) {
              	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                  Box box = BoxUtil.getBox(request);
                  /*if("".equals(box.getString("fnct_lc_no"))) {
                	  modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                	  return modelAndView;
                  }*/
                  
                  try {
                  	/*if(!loginMethod.isExistSessionKey(request)) {
                  		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                  		return modelAndView;
                  	}*/
                	//Box sessionBox = loginMethod.getSessionKey(request);
                   	Box sessionBox = loginMethod.getSessionKey(request);
                   	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
                   	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
                    System.out.println("############ eqplist ############## "+sessionBox.getString("fst_bizplc_cd"));
                    	
                    List<Box> list = kdnApiService.apiSearchFacilityList(box);
                    	
                    //modelAndView.addObject("result", list);
                    modelAndView.addObject("searchedTransTower", list);
                    System.out.println("############ list ############## "+list.size());
                    modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                  	return modelAndView;
                  }catch(Exception e) {
                  	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                  	return modelAndView;
                  }
              }

              
              /**
          	 * < 지중순시 모바일 시스템 > 정기순시/예방순시 - 유압리스트 호출
          	 * @param - box[]
          	 * @return list - [스케줄 결과검색]
          	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
          	 * @author [정현도] 
          	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
          	 * @exception api검토
          	 */
              @RequestMapping(value="/api/oileqp/in/schedule.*", method={RequestMethod.POST,RequestMethod.GET})
              public ModelAndView apiOileqpInSchedule(ModelMap model, HttpServletRequest request) {
              	ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
                  Box box = BoxUtil.getBox(request);
                  
                  /*if("".equals(box.getString("fnct_lc_no"))) {
                  	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
                  	return modelAndView;
                  }*/
                  try {
                  	/*if(!loginMethod.isExistSessionKey(request)) {
                  		modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_SESSION);
                  		return modelAndView;
                  	}*/
                 	 
                  	//box.put("fst_bizplc_cd", loginMethod.getSessionKey(request).getString("fst_bizplc_cd"));
                  	//box.put("scd_bizplc_cd", loginMethod.getSessionKey(request).getString("scd_bizplc_cd"));
                 	Box sessionBox = loginMethod.getSessionKey(request);
                 	box.put("fst_bizplc_cd", sessionBox.getString("fst_bizplc_cd"));
                 	box.put("scd_bizplc_cd", sessionBox.getString("scd_bizplc_cd"));
                     System.out.println("############ oileqp/in ############## "+sessionBox.getString("fst_bizplc_cd"));
                  	
                  	List<Box> list = kdnApiService.getOileqpList(box);
                  	
                  	modelAndView.addObject("result", list);
                  	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_SUCCESS);
                  	return modelAndView;
                  }catch(Exception e) {
                  	e.printStackTrace();
                  	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL);
                  	return modelAndView;
                  }
              }
              
              
}
