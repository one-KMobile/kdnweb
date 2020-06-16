package kdn.tlog.web;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.eqp.service.KdnEqpService;
import kdn.login.web.LoginMethod;
import kdn.tlog.service.KdnTLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;

@Controller
public class KdnTLogController{ 
	
    /** LoginMethod */
    @Resource(name="loginMethod")
    LoginMethod loginMethod;
    
    /** KdnTLogService */
    @Resource(name="kdnTLogService")
    private KdnTLogService kdnTLogService;
    
    /** KdnEqpService */
    @Resource(name = "kdnEqpService")
    private KdnEqpService kdnEqpService;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /**
   	 * [순시자 접속 로그표] 목록을 조회한다. 
   	 * @param [request] [model] 
   	 * @return "/kdn/tlog/tLogList"
   	 * @exception Exception
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
   	 */
   @RequestMapping(value="/kdn/tlog/getTLogDataList.do")
   public String getTLogDataList(HttpServletRequest request, ModelMap model) throws Exception {
	    List <Box> list = new ArrayList<Box>();
	    List <Box> fstBizplcList = new ArrayList<Box>();
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
		    
		    //1차사업소 목록
			try {
				fstBizplcList = kdnEqpService.getFstBizplcList(box);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    if(!"".equals(box.getString("searchCnd1"))){
			    list = kdnTLogService.getTLogDataList(box) ;
				model.addAttribute("list", list);
		    }
			
		    model.addAttribute("fstBizplcList", fstBizplcList);
			model.addAttribute("box", box);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return "/kdn/tlog/tLogList";
   }
   
}