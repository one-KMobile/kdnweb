package kdn.cmm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.auth.service.KdnAuthService;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class KdnIndexController {
	
	@Resource(name="loginMethod")
    LoginMethod loginMethod;
	
	/** KdnAuthService */
    @Resource(name = "kdnAuthService")
    private KdnAuthService kdnAuthService;
    
	@RequestMapping("/kdnIndex.do")
	public String kdnIndex(HttpServletRequest request, Model model){
		
		 //세션 정보 호출
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("@@@@@ [sessionInfo] = " + sessionInfo);
		
		if(sessionInfo == null || "".equals(sessionInfo.getString("user_id"))){
			return "/kdn/login/login";
		}else{
			return "redirect:/kdn/admin/noticeList.do";
		}
	}
	
	@RequestMapping("/kdn/layout/topMenu.do")
	public String topMenu(ModelMap model){
		return "/kdn/layout/top";
	}
	
	@RequestMapping("/kdn/layout/leftMenu.do")
	public String leftMenu(HttpServletRequest request, ModelMap model) throws Exception {
		List <Box> list = new ArrayList<Box>();
		
		 //세션 정보 호출
		Box sessionInfo = null;
		try {
			sessionInfo = loginMethod.getAdminSessionKey(request);
			model.addAttribute("sessionInfo", sessionInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		list = kdnAuthService.getLeftMenuList(sessionInfo);
		model.addAttribute("leftMenuList", list);
		
		return "/kdn/layout/left";
	}
	
	@RequestMapping("/kdn/layout/bottomMenu.do")
	public String bottomMenu(ModelMap model){
		return "/kdn/layout/bottom";
	}
	
}
