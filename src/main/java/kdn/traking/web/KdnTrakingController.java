/**
 * 
 */
/**
 * @author netted
 *
 */
package kdn.traking.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.admin.service.KdnAdminService;
import kdn.api.service.KdnApiMethod;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;
import kdn.traking.service.KdnTrakingService;
import kdn.traking.service.impl.KdnTrakingMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import egovframework.com.cmm.EgovMessageSource;


@Controller
public class KdnTrakingController {
	
	/** LoginMethod */
	@Resource(name = "loginMethod")
	private LoginMethod loginMethod;
	
	/** KdnAdminService */
	@Resource(name = "kdnAdminService")
	private KdnAdminService kdnAdminService;
	
	/** KdnTrakingService */
	@Resource(name = "kdnTrakingService")
	private KdnTrakingService kdnTrakingService;
	
	/** KdnEqpService */
	@Resource(name = "kdnEqpService")
	private KdnEqpService kdnEqpService;
	
	/** KdnApiMethod */
	@Resource(name = "kdnApiMethod")
	private KdnApiMethod kdnApiMethod;
	
	 /** KdnTrakingMethod */
	@Resource(name = "kdnTrakingMethod")
	private KdnTrakingMethod kdnTrakingMethod;
	
	 /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	/**
	* [트래킹] 실시간 트래킹 지도정보 화면
	* @param [request] [model]
	* @return "/kdn/traking/Immdently"
	* @exception Exception
	* @author [정현도] 
	* @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	*/
	@RequestMapping("/kdn/traking/user/Immdently.do")
	public String trakingUserImmdently(HttpServletRequest request, Model model) throws Exception {
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
		    
			List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
			List<Box> list = null;
	        if(box.get("traking_date") != null && !box.get("traking_date").equals("") ){
	        	list = kdnTrakingService.getTrakingByUserList(box);
	        }
	        System.out.println("list 확인 : " + list);
	        model.addAttribute("fstBizplcList", fstBizplcList);
	        model.addAttribute("box", box);
	        model.addAttribute("list", list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/kdn/traking/Immdently";
	}
	   
	/**
	* [트래킹] 순시자 트래킹 지도정보 화면
	* @param [request] [model]
	* @return "/kdn/traking/Patrolman"
	* @exception Exception
	* @author [정현도] 
	* @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	*/
	@RequestMapping("/kdn/traking/user/Patrolman.do")
	public String trakingUserPatrolman(HttpServletRequest request, Model model) throws Exception {
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
		    
			List<Box> fstBizplcList = kdnEqpService.getFstBizplcList(box);
			List<Box> list = null;
	        if(box.get("traking_date") != null && !box.get("traking_date").equals("") ){
	        	list = kdnTrakingService.getTrakingByUserList(box);
	        }
	        System.out.println("list 확인 : " + list);
	        model.addAttribute("fstBizplcList", fstBizplcList);
	        model.addAttribute("box", box);
	        model.addAttribute("list", list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/kdn/traking/Patrolman";
	}
	
    /**
	 * <설명>
	 * [트래킹] 실시간 트래킹 순시자 목록
	 * @param [fst_bizplc_cd, scd_bizplc_cd] 
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/ajax/get/traking/user/List.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getTrakingUserList(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
        if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd"))) { // 필수 파라메터 체크
			modelAndView.addObject(ConstantValue.RESULT_CODE,
					ConstantValue.RESULT_FAIL_REQUIRE);
			return modelAndView;
		}
        
        try {
        	//List<Box> list = kdnTrakingService.getAdminByBizplcList(box);
        	/*List<Box> trakingUserList = kdnTrakingService.getTrakingUserList(box);
        	ArrayList<Box> list = new ArrayList<Box>();
        	for(int i=0; i<trakingUserList.size(); i++) {
        		Box trakingUserBox = trakingUserList.get(i);
	        	
        		trakingUserBox.put("fst_bizplc_cd", box.getString("fst_bizplc_cd"));
        		trakingUserBox.put("scd_bizplc_cd", box.getString("scd_bizplc_cd"));
        		trakingUserBox.put("user_id", trakingUserBox.getString("USER_ID"));
        		Box trakingInfo = kdnTrakingService.getImmediatelyTraking(trakingUserBox);
        		trakingInfo.put("USER_NAME", trakingUserBox.getString("USER_NAME"));
        		//trakingInfo.put("REG_DATE", kdnTrakingMethod.getTimeToDB(trakingInfo));
        		
        		list.add(trakingInfo);
        	
        	}*/
        	List<Box> list = kdnTrakingService.getRecentTrakingList(box);
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
        	modelAndView.addObject("trakingList",list);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }
    
    /**
	 * <설명>
	 * [트래킹] 트래킹 순시자 정보
	 * @param [fst_bizplc_cd, scd_bizplc_cd, user_id] 
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/ajax/get/traking/user/Info.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getTrakingUserInfo(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
        if ("".equals(box.getString("user_id"))) { // 필수 파라메터 체크
			modelAndView.addObject(ConstantValue.RESULT_CODE,
					ConstantValue.RESULT_FAIL_REQUIRE);
			return modelAndView;
		}
        
        try {
        	Box trakingInfo = kdnTrakingService.getTrakingInfo(box);
        	String address = kdnApiMethod.getAddressByLatitudeLongitude(trakingInfo.getString("latitude"), trakingInfo.getString("longitude"));
        	trakingInfo.put("ADDRESS", address);
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
        	modelAndView.addObject("trakingInfo",trakingInfo);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }
    
    /**
	 * <설명>
	 * [트래킹] 트래킹 순시자 목록
	 * @param [fst_bizplc_cd, scd_bizplc_cd, user_id] 
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/ajax/get/traking/by/user/List.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getTrakingByUserList(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
        if ("".equals(box.getString("fst_bizplc_cd")) || "".equals(box.getString("scd_bizplc_cd"))) { // 필수 파라메터 체크
			modelAndView.addObject(ConstantValue.RESULT_CODE,
					ConstantValue.RESULT_FAIL_REQUIRE);
			return modelAndView;
		}
        
        try {
        	//List<Box> list = kdnTrakingService.getAdminByBizplcList(box);
        	List<Box> trakingByUserList = kdnTrakingService.getTrakingByUserList(box);
        	ArrayList<Box> list = new ArrayList<Box>();
        	/*for(int i=0; i<trakingByUserList.size(); i++) {
        		Box trakingUserBox = trakingByUserList.get(i);
        		
        		trakingUserBox.put("REG_DATE", kdnTrakingMethod.getTimeToDB(trakingUserBox));
        		
        		list.add(trakingUserBox);
        	}*/
        	System.out.println("list 확인 : " + trakingByUserList);
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS); 
        	modelAndView.addObject("trakingUserList",trakingByUserList);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }
}