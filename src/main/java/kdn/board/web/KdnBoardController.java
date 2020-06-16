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
package kdn.board.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.admin.service.KdnAdminService;
import kdn.auth.service.KdnAuthService;
import kdn.board.service.KdnBoardService;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.sender.PushSender;
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
public class KdnBoardController {
	
	/** KdnBoardService */
    @Resource(name = "kdnBoardService")
    private KdnBoardService kdnBoardService;
    
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
    
    /** KdnCodeService */
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;
    
    /** KdnAuthService */
    @Resource(name = "kdnAuthService")
    private KdnAuthService kdnAuthService;
    
    /** LoginMethod */
    @Resource(name="loginMethod")
    LoginMethod loginMethod;
    
    /** KdnAdminService */
    @Resource(name="kdnAdminService")
    private KdnAdminService kdnAdminService;
    
	/** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL = "/kdn/admin/noticeList.do";
    
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
	   	 * [공지] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/kdn/notice/noticeList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @IncludedInfo(name="공지사항", order = 1 ,gid = 100)
	   @RequestMapping(value="/kdn/admin/noticeList.do")
	   public String noticeList(HttpServletRequest request, ModelMap model) throws Exception {
		    Box box = BoxUtil.getBox(request);
		    List <Box> list = new ArrayList<Box>();
		    List <Box> topList = new ArrayList<Box>();
		    List <Box> codeList = new ArrayList<Box>();
		    
		    /**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
				model.addAttribute("sessionInfo", sessionInfo);
			} catch (Exception e) {
					e.printStackTrace();
			} 
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
		   	/**************** pageing setting Start ****************/
		    PaginationInfo paginationInfo = kdnCommonUtil.getWebPaging(box);
			/**************** pageing setting End ****************/
			
            //공지사항 카테고리 [코드]
		    box.put("groupCodeId", "NOTICETYPE"); 
			try {
				codeList = kdnCodeService.selectCodeList(box);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			topList = kdnBoardService.getTopNoticeList(box);
			model.addAttribute("topList", topList);
		
			
			//1차사업소 목록
			List <Box> fstBizplcList = new ArrayList<Box>();
			try {
				fstBizplcList = kdnEqpService.getFstBizplcList(box);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model.addAttribute("fstBizplcList", fstBizplcList);
			
			//사업소 검색시 
    		if(!"".equals(box.getString("searchCnd1"))) box.addData("fst_bizplc_cd", box.getString("searchCnd1"));
    		if(!"".equals(box.getString("searchCnd2")))	 box.addData("scd_bizplc_cd", box.getString("searchCnd2"));
			
		    list = kdnBoardService.getNoticeList(box);
			model.addAttribute("list", list);
		       
		    int totCnt = kdnBoardService.getNoticeListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
			
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
			model.addAttribute("codeList", codeList); 
		    model.addAttribute("paginationInfo", paginationInfo);
		    		    
		    return "/kdn/notice/noticeList";
	   }
	   
	   /**
		 * [공지] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/notice/noticeView"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<신명섭>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/noticeView.do")
	    public String noticeView(HttpServletRequest request, Model model) throws Exception {
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
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
	        try {
	        	if(box.get("board_idx") != null && !box.get("board_idx").equals("") ){
	        		viewBox = kdnBoardService.getNoticeView(box);
	        	} 
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/notice/noticeView";
	    } 

	   /**
		 * [공지] 등록 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/notice/noticeWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/noticeWrite.do")
	    public String noticeWrite(@ModelAttribute("searchVO") FileVO fileVO, HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
	    	List <Box> codeList = new ArrayList<Box>();
	    	
	    	/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = null;
			try {
				sessionInfo = loginMethod.getAdminSessionKey(request);
				model.addAttribute("sessionInfo", sessionInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
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
	        	if(box.get("board_idx") != null && !box.get("board_idx").equals("") ){
	        		viewBox = kdnBoardService.getNoticeView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        
	        //현재 날짜 구하기
	        Calendar now = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String date = sdf.format(now.getTime()); 
	        
	        
	        //1차사업소 목록
	        List <Box> fstBizplcList = new ArrayList<Box>();
			try {
				fstBizplcList = kdnEqpService.getFstBizplcList(box);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//2차 사업소 목록
			List <Box> scdBizplcList = new ArrayList<Box>();
			if(box.get("board_idx") != null && !box.get("board_idx").equals("") ){
				box.addData("fst_bizplc_cd",viewBox.getString("fst_bizplc_cd")) ;
				scdBizplcList = kdnEqpService.getScdBizplcList(box);
        	}
			
			model.addAttribute("fstBizplcList", fstBizplcList);
			model.addAttribute("scdBizplcList", scdBizplcList);
			
	        //공지사항 카테고리 [코드]
		    box.put("groupCodeId", "NOTICETYPE"); 
			try {
				codeList = kdnCodeService.selectCodeList(box);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        model.addAttribute("box", box);
	        model.addAttribute("sysdate", date);
	        model.addAttribute("viewBox", viewBox);
	        model.addAttribute("codeList", codeList); 
	        
	        return "/kdn/notice/noticeWrite";
	    } 
	    
	    /**
		 * [공지] 등록 저장.
		 * @param [request] [model]
		 * @return "/kdn/admin/noticeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<신명섭>) [yyyy.mm.dd]: [푸시전송]
		 */
	    @RequestMapping("/kdn/admin/noticeSave.do")
	    public String noticeSave(HttpServletRequest request, Model model) throws Exception {
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
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
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
	    	if("".equals(unscript(box.getString("board_title"))) || "".equals(unscript(box.getString("board_cont"))) ){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(제목, 내용)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         return "forward:/kdn/admin/noticeList.do";
	    	}
	    	
	    	try {
	    		if(!"".equals(box.getString("searchCnd1"))) box.addData("fst_bizplc_cd", box.getString("searchCnd1"));
	    		if(!"".equals(box.getString("searchCnd2")))	 box.addData("scd_bizplc_cd", box.getString("searchCnd2"));
	    		kdnBoardService.kdnNoticeSave(box); 
	    		//푸시
	    		if(box.getString("push_yn").equals("Y")){
		    		//1.공지사항의 푸시전송 flag 와 메세지 가져오기
			    	String board_cont = box.getString("board_cont");  //내용
			    	String title = box.getString("board_title");  //제목
			    	String push_cont = box.getString("push_cont");  //푸시내용
			    	String board_idx = kdnBoardService.getMaxBoardIdx(box)+"";  //index
			    	String device_del = box.getString("device_del"); //잃어버린 폰일 경우 flag = "Y" ;
			    	if(!device_del.equals("Y")) 	device_del = "N" ;
			    	
			    	//푸시전송(회원목록 가져와서 푸시전송)
			    	//1.선택한 2차 사업소에 속한 순시자의 device_token  추출
			    	box.put("groupCodeId", "ADMIN01"); 
			    	List<Box> lists = kdnAdminService.getUserTokenList(box);
			    	//List<Box> lists = kdnAdminService.getAdminIsNotTokenList(box);
			    	String device_token;
			    	PushSender pushSender = PushSender.getInstance() ;
			    	for(Box device_Box : lists){
			    		device_token = (String) device_Box.get("device_token") ;
			    		pushSender.sendGcmPush(title , push_cont , device_token , device_del , board_idx);
			    	}
		    	}
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('공지를 등록하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return "forward:/kdn/admin/noticeList.do";
	    } 
	    
	    /**
		 * [공지] 수정.
		 * @param [request] [model]
		 * @return "/kdn/admin/noticeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/noticeUpdate.do")
	    public String noticeUpdate(HttpServletRequest request, Model model) throws Exception {
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
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
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
	    	
	    	if("".equals(unscript(box.getString("board_idx")))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(공지사항 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/noticeList.do";
	    	}
	    	
	    	try {
	    		//1차 .2차 사업소
	    		if(!"".equals(box.getString("searchCnd1"))) box.addData("fst_bizplc_cd", box.getString("searchCnd1"));
	    		if(!"".equals(box.getString("searchCnd2")))	 box.addData("scd_bizplc_cd", box.getString("searchCnd2"));
	    		kdnBoardService.noticeUpdate(box);
	    		
	    		if(box.getString("push_yn").equals("Y")){
		    		//1.공지사항의 푸시전송 flag 와 메세지 가져오기
			    	String board_cont = box.getString("board_cont");  //내용
			    	String title = box.getString("board_title");  //제목
			    	String push_cont = box.getString("push_cont");  //푸시내용
			    	String board_idx = box.getString("board_idx");  //index
			    	String device_del = box.getString("device_del"); //잃어버린 폰일 경우 flag = "Y" ;
			    	if(!device_del.equals("Y")){
			    		device_del = "N" ;
			    	}
			    	
			    	//푸시전송(회원목록 가져와서 푸시전송)
			    	//1.회원목록 가져와 device_token  추출
			    	box.put("groupCodeId", "ADMIN01"); 
			    	List<Box> lists = kdnAdminService.getAdminIsNotTokenList(box);
			    	String device_token;
			    	PushSender pushSender = PushSender.getInstance() ;
			    	for(Box device_Box : lists){
			    		device_token = (String) device_Box.get("device_token") ;
			    		pushSender.sendGcmPush(title , push_cont , device_token , device_del , board_idx);
			    	}
		    	}
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('공지사항을 수정하였습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        return "forward:/kdn/admin/noticeList.do";
	    }
	    
	    /**
		 * [공지] 삭제.
		 * @param [request] [model]
		 * @return "/kdn/admin/noticeList.do"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/noticeDelete.do")
	    public String noticeDelete(HttpServletRequest request, Model model) throws Exception {
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
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
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
		         ReusltScript += "alert('필수 항목(공지사항 선택 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/kdn/admin/noticeList.do";
	    	}
	    	
	    	try {
	    		kdnBoardService.noticeDelete(box);
	    		
	    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
		        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
		        ReusltScript += "</script>";
		        model.addAttribute("reusltScript", ReusltScript);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        return "forward:/kdn/admin/noticeList.do";
	    }
	    

	    /**
		 * [공지] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/notice/noticeView"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/noticePush.do")
	    public String noticePush(HttpServletRequest request, Model model) throws Exception {
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
			
			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
				return "redirect:/kdnIndex.do";
			}
			/**************** 세션 정보 호출 End ****************/
			
	        try {
	        	if(box.get("board_idx") != null && !box.get("board_idx").equals("") ){
	        		viewBox = kdnBoardService.getNoticeView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	   
	        
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/kdn/notice/noticeView";
	    } 
	    
	   	}
