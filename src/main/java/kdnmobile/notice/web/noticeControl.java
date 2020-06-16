package kdnmobile.notice.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class noticeControl {
	 
	/**
     * 모바일 웹 공지사항 페이지 호출
     * @param 
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/mobileWeb/noticeList.do")
    public ModelAndView noticeList(HttpServletRequest request, ModelMap model) throws Exception {
    	ModelAndView mnv = new ModelAndView("/kdn/mobileWeb/noticeList");
    	return mnv;
    }
}
