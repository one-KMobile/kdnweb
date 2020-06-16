/**
 * 
 */
/**
 * @author netted
 *
 */
package kdn.admin.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.admin.service.KdnAdminService;
import kdn.admin.service.impl.KdnAdminMethod;
import kdn.auth.service.KdnAuthService;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.sender.PushSender;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class KdnAdminController {
	
	 /** KdnCodeService */
    @Resource(name = "kdnCodeService")
    private KdnCodeService kdnCodeService;
    
    /** LoginMethod */
    @Resource(name="loginMethod")
    LoginMethod loginMethod;
    
    /** KdnAdminService */
    @Resource(name="kdnAdminService")
    private KdnAdminService kdnAdminService;
    
    /** KdnAdminMethod */
    @Resource(name="kdnAdminMethod")
    private KdnAdminMethod kdnAdminMethod;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
    /** KdnAuthService */
    @Resource(name = "kdnAuthService")
    private KdnAuthService kdnAuthService;
    
    /*메뉴 권한 체크하기 위한 컨트롤 메소드 URL*/
    private String controllerURL = "/kdn/admin/patrolman/List.do";
    
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
   	 * [순시자] 목록을 조회한다. (pageing)
   	 * @param [request] [model] 
   	 * @return "/kdn/admin/patrolmanList"
   	 * @exception Exception
   	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
   	 */
   @RequestMapping(value="/kdn/admin/patrolman/List.do")
   public String patrolmanList(HttpServletRequest request, ModelMap model) throws Exception {
	    Box box = BoxUtil.getBox(request);
	    try {
	    	/**************** 세션 정보 호출 Start ****************/
			Box sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);

			if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
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
	    	
		    //1차 사업소 목록
			List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
		    
	        //관리자 검색 카테고리 [코드]
		    box.put("groupCodeId", "ADMIN01"); 
	
			List<Box> codeList = kdnCodeService.selectCodeList(box);
			
			if("SEARCH01".equals(box.getString("s_category_code"))) {
				box.put("search_user_id", box.getString("searchWrd"));
			}else if("SEARCH02".equals(box.getString("s_category_code"))) {
				box.put("search_user_name", box.getString("searchWrd"));
			}else if("SEARCH03".equals(box.getString("s_category_code"))) {
				box.put("search_user_email", box.getString("searchWrd"));
			}
		    List<Box> list = kdnAdminService.getAdminList(box);
			model.addAttribute("list", list);
			System.out.println("2222 " + list);   
		    int totCnt = kdnAdminService.getAdminTotalCount(box);
			paginationInfo.setTotalRecordCount(totCnt);
			
			model.addAttribute("box", box);
			model.addAttribute("fstBizplcList", fstBizplcList);
			model.addAttribute("totCnt", totCnt);
			model.addAttribute("codeList", codeList); 
		    model.addAttribute("paginationInfo", paginationInfo);
	    
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return "/kdn/admin/patrolmanList";
   }
   
   		/**
 		 * [순시자] 등록 화면을 조회한다.
 		 * @param [request] [model]
 		 * @return "/kdn/admin/patrolmanWrite"
 		 * @exception Exception
 		 * @author [정현도] 
 	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 		 */
 	    @RequestMapping("/kdn/admin/patrolman/Write.do")
 	    public String noticeWrite(@ModelAttribute("searchVO") FileVO fileVO, HttpServletRequest request, Model model) throws Exception {
 	    	Box box = BoxUtil.getBox(request);
 	    	try {
 	    		/**************** 세션 정보 호출 Start ****************/
	 			Box sessionInfo = null;
	 			
	 			sessionInfo = loginMethod.getAdminSessionKey(request);
	 			model.addAttribute("sessionInfo", sessionInfo);
	
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
			    
			    //권한 코드값 호출
			    List<Box> authCodeList = kdnAuthService.getAuthCodeValue(box);
	 	        //1차사업소 목록
	 	        List <Box> fstBizplcList = new ArrayList<Box>();
	 			fstBizplcList = kdnEqpService.getFstBizplcList(box);
	 			
	 			model.addAttribute("authCodeList", authCodeList);
	 			model.addAttribute("sessionInfo", sessionInfo);
	 			model.addAttribute("fstBizplcList", fstBizplcList);
	 	        model.addAttribute("box", box);
 	        }catch(Exception e) {
 	        	e.printStackTrace();
 	        }
 	        return "/kdn/admin/patrolmanWrite";
 	    } 
   
	   /**
		 * [순시자] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/kdn/admin/patrolmanView"
		 * @exception Exception
		 * @author [정현도] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/patrolman/View.do")
	    public String patrolView(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);

			try {
				/**************** 세션 정보 호출 Start ****************/
				Box sessionInfo = loginMethod.getAdminSessionKey(request);
				model.addAttribute("sessionInfo", sessionInfo);
	
				if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
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
			    
				Box viewBox = new Box();
				List<Box> fstBizplcList = null;
				List<Box> codeList = null;
				List<Box> authCodeList = null;
	        	if(box.get("user_id") != null && !box.get("user_id").equals("") ){
	        		//관리자 검색 카테고리 [코드]
	    		    box.put("groupCodeId", "ADMIN02"); 
	    			codeList = kdnCodeService.selectCodeList(box);
	    			//권한 카테고리 [코드]
	    			authCodeList = kdnAuthService.getAuthCodeValue(box);
	        		//1차 사업소
	    			fstBizplcList = kdnEqpService.getFstBizplcList(box);
	        		viewBox = kdnAdminService.getAdminInfo(box);
	        		Box bizplcBox = kdnAdminService.getBizplcNameInfo(viewBox);
	        		viewBox.put("fst_bizplc_cd_nm", bizplcBox.getString("FST_BIZPLC_CD_NM"));
	        		viewBox.put("scd_bizplc_cd_nm", bizplcBox.getString("SCD_BIZPLC_CD_NM"));
	        		kdnAdminMethod.voidConvertClient(viewBox);
	        	}
	        	System.out.println("viewBox 확인 : " + viewBox);
		        model.addAttribute("box", box);
		        model.addAttribute("codeList", codeList); 
		        model.addAttribute("authCodeList", authCodeList);
		        model.addAttribute("fstBizplcList", fstBizplcList);
		        model.addAttribute("viewBox", viewBox);
		        model.addAttribute("sessionInfo", sessionInfo);
			} catch(Exception e) {
				e.printStackTrace();
			}
	        return "/kdn/admin/patrolmanView";
	    }
	    
	    /**
		 * [순시자] 등록.
		 * @param [request] [model]
		 * @return "/kdn/admin/patrolman/List.do"
		 * @exception Exception
		 * @author [정현도] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/patrolman/Insert.do")
	    public String patrolmanInsert(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	
			try {
				/**************** 세션 정보 호출 Start ****************/
				Box sessionInfo = loginMethod.getAdminSessionKey(request);
			
				if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
					return "redirect:/kdnIndex.do";
				}
				String insertUaerauth = box.getString("user_auth");
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
		    	if("".equals(unscript(box.getString("user_id")))){
		    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
			         ReusltScript += "alert('필수 항목(순시자 키)의 값이 없습니다.');";
			         ReusltScript += "</script>";
			         model.addAttribute("reusltScript", ReusltScript);
			         
			         return "forward:/kdn/admin/patrolman/List.do";
		    	}
		    		//kdnAdminMethod.voidConvertServer(box);
		    		//box.put("mac_address", kdnAdminMethod.getMacAddress());
		    		box.put("user_auth", insertUaerauth);
		    		kdnAdminMethod.voidConvertServer(box);
		    		kdnAdminService.setAdminInsert(box);
		    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
			        ReusltScript += "alert('순시자를 등록하였습니다.');";
			        ReusltScript += "</script>";
			        model.addAttribute("reusltScript", ReusltScript);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	
	        return "forward:/kdn/admin/patrolman/List.do";
	    }    
	    
	    /**
		 * [순시자] 수정.
		 * @param [request] [model]
		 * @return "/kdn/admin/patrolman/List.do"
		 * @exception Exception
		 * @author [정현도] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/patrolman/Update.do")
	    public String patrolmanUpdate(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	String user_auth = box.getString("user_auth");
			try {
				/**************** 세션 정보 호출 Start ****************/
				Box sessionInfo = loginMethod.getAdminSessionKey(request);
			
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
				box.put("user_auth", user_auth);
		    	String ReusltScript = ""; 
		    	
		    	if("".equals(unscript(box.getString("user_id")))){
		    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
			         ReusltScript += "alert('필수 항목(순시자 키)의 값이 없습니다.');";
			         ReusltScript += "</script>";
			         model.addAttribute("reusltScript", ReusltScript);
			         
			         return "forward:/kdn/admin/patrolman/List.do";
		    	}
		    		if("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd"))) {
		    			
		    		}
		    		if("선택하세요".equals(box.getString("fst_bizplc_cd"))) {
		    			box.put("fst_bizplc_cd", "");
		    		}
		    		kdnAdminMethod.voidConvertServer(box);
		    		box.put("mac_address", kdnAdminMethod.getMacAddress());
		    		kdnAdminService.setAdminUpdate(box);
		    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
			        ReusltScript += "alert('순시자를 수정하였습니다.');";
			        ReusltScript += "</script>";
			        model.addAttribute("reusltScript", ReusltScript);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	
	        return "forward:/kdn/admin/patrolman/List.do";
	    }
	    
	    
	    /**
		 * [순시자] 디바이스 삭제.
		 * @param [request] [model]
		 * @return "/kdn/admin/patrolman/List.do"
		 * @exception Exception
		 * @author [신명섭] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/kdn/admin/patrolman/DeviceDel.do")
	    public String patrolmanDeviceDel(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	
			try {
				/**************** 세션 정보 호출 Start ****************/
				Box sessionInfo = loginMethod.getAdminSessionKey(request);
			
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
		    	
		    	if("".equals(unscript(box.getString("user_id")))){
		    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
			         ReusltScript += "alert('필수 항목(순시자 키)의 값이 없습니다.');";
			         ReusltScript += "</script>";
			         model.addAttribute("reusltScript", ReusltScript);
			         
			         return "forward:/kdn/admin/patrolman/List.do";
		    	}
		    		kdnAdminMethod.voidConvertServer(box);
		    		box.put("mac_address", kdnAdminMethod.getMacAddress());
		    		
		    		
		    		//푸시전송(순시자 앱 삭제)
			    	//1.회원목록 가져와 device_token  추출
			    	box.put("groupCodeId", "ADMIN01");
			    	Box userBox  = kdnAdminService.getUserToken(box);
			    	
			    	PushSender pushSender = PushSender.getInstance() ;
			    	String 	device_token = userBox.getString("device_token");
			    	//pushSender.sendGcmPush(push_cont , device_token , device_del , board_idx);
			    	pushSender.sendGcmPush("", "del" , device_token , "del" , "del");
			    	
		    		
		    		kdnAdminService. setDeviceDel(box) ;
		    		
		    		ReusltScript += "<script type='text/javaScript' language='javascript'>";
			        ReusltScript += "alert('디바이스를 삭제 하였습니다.');";
			        ReusltScript += "</script>";
			        model.addAttribute("reusltScript", ReusltScript);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	
	        return "forward:/kdn/admin/patrolman/List.do";
	    }
	    
	    /**
		 * <설명>
		 * [순시자] 순시자 중복계정 확인
		 * @param [user_id] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [정현도]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/ajax/exist/userid.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView getAjaxExistUserid(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	       if ("".equals(box.getString("user_id"))) { // 필수 파라메터 체크
				modelAndView.addObject(ConstantValue.RESULT_CODE,
						ConstantValue.RESULT_FAIL_REQUIRE);
				return modelAndView;
			}
	        
	        try {
	        	Box isExist = kdnAdminService.isExistToUserid(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
	        	modelAndView.addObject("isExist",isExist);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
    
}