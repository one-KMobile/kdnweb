package kdn.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.login.service.EgovLoginService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovCmmUseService;

@Controller
public class UploadLoginController {
	 /** EgovLoginService */
		@Resource(name = "loginService")
	    private EgovLoginService loginService;
		
		/** EgovCmmUseService */
		@Resource(name="EgovCmmUseService")
		private EgovCmmUseService cmmUseService;

	    
		/** EgovMessageSource */
	    @Resource(name="egovMessageSource")
	    EgovMessageSource egovMessageSource;
	    
	    @Resource(name="loginMethod")
	    LoginMethod loginMethod;
	    
	    /**
		 * (Test) 일반(세션) 로그인을 처리한다
		 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
		 * @param request - 세션처리를 위한 HttpServletRequest
		 * @return result - 로그인결과(세션정보)
		 * @exception Exception
		 */
	    @RequestMapping(value="/test/actionLogin.do")
	    public String actionLogin(HttpServletRequest request, ModelMap model)
	            throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box userInfo = null;
	    	String ReusltScript = "";
	    	
	    	//1. 일반 로그인 처리
	    	if(box.get("user_id") != null && !box.get("user_pwd").equals("")){
	    		userInfo = loginService.getUserInfo(box);
	    	}else{
				ReusltScript += "<script type='text/javaScript' language='javascript'>";
				ReusltScript += "alert('사용자 정보가 없습니다.');";
				ReusltScript += "</script>";
				model.addAttribute("reusltScript", ReusltScript);
				return "forward:/kdnIndex.do";
	    	}
			
			if (userInfo == null ) {
				ReusltScript += "<script type='text/javaScript' language='javascript'>";
				ReusltScript += "alert('사용자 정보가 없습니다.');";
				ReusltScript += "</script>";
				model.addAttribute("reusltScript", ReusltScript);
				return "forward:/kdnIndex.do";
			}
			// 로그인 정보를 세션에 저장
			loginMethod.putSessionKey(request, box);
			// 세션 유지시간 설정(30일)
			loginMethod.sessionkeyDelay(request);
			
			// 세션 정보 호출
			/*Box sessionBox = loginMethod.getSessionKey(request);*/
			
			return "/kdn/testUpload";
	    } 
}
