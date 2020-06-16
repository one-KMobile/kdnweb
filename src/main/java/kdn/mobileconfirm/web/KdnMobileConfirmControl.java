package kdn.mobileconfirm.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;
import kdn.mobileconfirm.service.KdnMobileConfirmService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class KdnMobileConfirmControl {
	
	/** KdnMobileConfirmService */
	@Resource(name="kdnMobileConfirmService")
	private KdnMobileConfirmService kdnMobileConfirmService;
	
	/** KdnEqpService */
	@Resource(name="kdnEqpService")
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
    private String controllerURL = "/kdn/admin/mobileConfirmList.do";
    
	/**
	 * <설명>
	 * [모바일 승인] 모바일 승인 리스트
	 * @param [request] 
	 * @return [mobileConfirmList]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
	@RequestMapping(value="/kdn/admin/mobileConfirmList.do")
	public String mobileConfirm(HttpServletRequest request, ModelMap model) throws Exception {
		Box box = BoxUtil.getBox(request);
		List<Box> list = null;
		
		/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
		} catch (Exception e) {
			e.printStackTrace();
		} 
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

		//1차사업소 목록
		List <Box> fstBizplcList = new ArrayList<Box>();
		try {
			fstBizplcList = kdnEqpService.getFstBizplcList(box);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("fstBizplcList", fstBizplcList);
		//사업소 검색시 
		if(!"".equals(box.getString("fst_bizplc_cd"))) box.addData("fst_bizplc_cd", box.getString("fst_bizplc_cd"));
		if(!"".equals(box.getString("scd_bizplc_cd")))	 box.addData("scd_bizplc_cd", box.getString("scd_bizplc_cd"));

		//날자 검색을 위한 형변환
		if(!"".equals(box.getString("regdate_start"))) box.addData("reg_date_start", box.getString("regdate_start").replace("-", "" )+"000000") ;
		if(!"".equals(box.getString("regdate_end")))  box.addData("reg_date_end", box.getString("regdate_end").replace("-", "" )+"235959") ;
		list = kdnMobileConfirmService.getMobileList(box) ;
		int totCnt = kdnMobileConfirmService.getMobileListTotCnt(box);
		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("box", box);
		model.addAttribute("totCnt", totCnt);
		model.addAttribute("list", list); 
		model.addAttribute("paginationInfo", paginationInfo);

		return "/kdn/mobileconfirm/mobileConfirmList";
	}
	
	/**
	 * <설명>
	 * [모바일 승인] 모바일 승인 Check 변경
	 * @param [setMobileCheckYN] 
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @throws Exception 
	 * @URL테스트: 
	 */ 
	@RequestMapping(value="/ajax/setMobileCheckYN.*", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView setMobileCheckYN(ModelMap model, HttpServletRequest request) throws Exception{        
		ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
		Box box = BoxUtil.getBox(request);
		if("Y".equals(box.getString("check_yn")))	 box.addData("check_yn", "N");
		else box.addData("check_yn", "Y");
		
		/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
			modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.SESSION_FAIL);
    		return modelAndView;
		}
		/**************** 세션 정보 호출 End ****************/
		
		/**************** user 권한 체크 Start ****************/
	    box.put("user_auth", sessionInfo.getString("user_auth"));
		box.put("controller_method", controllerURL);
	    if(kdnCommonUtil.isAuthUserInfo(box, "write") == true) { 
	    	modelAndView.addObject(ConstantValue.RESULT_CODE,	ConstantValue.AUTH_FAIL);
    		return modelAndView;
    	}
    	/**************** user 권한 체크 End ****************/
	    
		try {
			kdnMobileConfirmService.setMobileCheckYN(box); 
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
			modelAndView.addObject("check_yn",box.getString("check_yn"));

			if("Y".equals(box.getString("check_yn"))){
				Calendar cal = Calendar.getInstance();
				int year = cal.get ( Calendar.YEAR );
				int month = cal.get ( Calendar.MONTH ) + 1 ;
				String cont_month = java.text.MessageFormat.format("{0,number,#00}", new Object[] {new Integer(month)});
				int date = cal.get ( Calendar.DATE ) ;
				String check_date = year+"-"+cont_month + "-"+ date ;
				modelAndView.addObject("check_date" , check_date) ;

			}
			else {
				modelAndView.addObject("check_date" , "") ;
			}
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    

		return modelAndView;
	}
	
	/**
	 * <설명>
	 * [모바일 승인] 모바일 승인 선택 버튼 Check 변경
	 * @param [request] 
	 * @return [mobileConfirmList]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */  
	@RequestMapping("/kdn/admin/mobileAllConfirm.do")
	public String noticeDelete(HttpServletRequest request, Model model) throws Exception {
		String[] checks = request.getParameterValues("chk"); 
		Box box = BoxUtil.getBox(request);
		box.put("chk", checks);

		/**************** 세션 정보 호출 Start ****************/
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
			return "redirect:/kdnIndex.do";
		}
		/**************** 세션 정보 호출 End ****************/
		
		String ReusltScript = ""; 
		if("".equals(box.getString("chk"))){
			ReusltScript += "<script type='text/javaScript' language='javascript'>";
			ReusltScript += "alert('필수 항목(공지사항 선택 키)의 값이 없습니다.');";
			ReusltScript += "</script>";
			model.addAttribute("reusltScript", ReusltScript);
			return "forward:/kdn/admin/mobileConfirmList.do";
		}
		try {
			kdnMobileConfirmService.setAllConfirm(box);
			ReusltScript += "<script type='text/javaScript' language='javascript'>";
			ReusltScript += "alert('선택한 대상이 승인 처리되었습니다.');";
			ReusltScript += "</script>";
			model.addAttribute("reusltScript", ReusltScript);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward:/kdn/admin/mobileConfirmList.do";
	}
	
	@RequestMapping(value="/kdn/admin/mobileConfirmList1.do")
	public String mobileConfirm1(HttpServletRequest request) throws Exception {
		Box box = BoxUtil.getBox(request);
		List<Box> list = null;

		/**************** pageing setting Start ****************/
		PaginationInfo paginationInfo = kdnCommonUtil.getWebPaging(box);
		//**************** pageing setting End ****************/

		//1차사업소 목록
		List <Box> fstBizplcList = new ArrayList<Box>();
		try {
			fstBizplcList = kdnEqpService.getFstBizplcList(box);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//model.addAttribute("fstBizplcList", fstBizplcList);
		//사업소 검색시 
		if(!"".equals(box.getString("fst_bizplc_cd"))) box.addData("fst_bizplc_cd", box.getString("fst_bizplc_cd"));
		if(!"".equals(box.getString("scd_bizplc_cd")))	 box.addData("scd_bizplc_cd", box.getString("scd_bizplc_cd"));

		//날자 검색을 위한 형변환
		if(!"".equals(box.getString("regdate_start"))) box.addData("reg_date_start", box.getString("regdate_start").replace("-", "" )+"000000") ;
		if(!"".equals(box.getString("regdate_end")))  box.addData("reg_date_end", box.getString("regdate_end").replace("-", "" )+"235959") ;
		list = kdnMobileConfirmService.getMobileList(box) ;
		int totCnt = kdnMobileConfirmService.getMobileListTotCnt(box);
		paginationInfo.setTotalRecordCount(totCnt);

//		model.addAttribute("box", box);
//		model.addAttribute("totCnt", totCnt);
//		model.addAttribute("list", list); 
//		model.addAttribute("paginationInfo", paginationInfo);

		return "/kdn/mobileconfirm/mobileConfirmList";
	}
	
		
	
}
