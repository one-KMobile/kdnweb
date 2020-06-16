/**
 * 
 */
/**
 * @author netted
 *
 */
package kdnmobile.schedule.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kdn.api.service.KdnApiMethod;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.login.web.LoginMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terracotta.agent.repkg.de.schlichtherle.io.FileInputStream;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class ScheduleExcel {
	
	@Resource(name = "kdnApiMethod")
	private KdnApiMethod kdnApiMethod;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
	
	@Resource(name = "kdnCommonUtil")
	private KdnCommonUtil kdnCommonUtil;
	
	@Resource(name = "loginMethod")
	private LoginMethod loginMethod;
	
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	/**
     * 스케줄 파일 다운로드
     * @param 
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/kdnmobile/schedule/excel/download.do")
    public String scheduleExcelDownload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    
    	//String realPath = propertyService.getString("Globals.kdnExcelFileStorePath").concat("SCHEDULE/");
	    //String realPath = "c:\\KdnMobile\\Excel\\SCHEDULE\\";
	    //String realPath = "c:/KdnMobile" + File.separator + "Excel" + File.separator + "SCHEDULE" + File.separator;
	    String realPath = propertyService.getString("Globals.kdnFileExcelDownloadPath");
    	String fileName = kdnApiMethod.getExcelSchedule(request);
    	String clienFileName = "input_data.xls";
    	response.setContentType("applicatin/octer-stream");
    	response.setHeader("Content-Disposition", "attachment;filename="+clienFileName+";");
    	File file = new File(realPath.concat(fileName));
    	InputStream in = new FileInputStream(file);
    	int fileSize = (int)file.length();
    	byte[] buffer = new byte[fileSize];
    	if(file.isFile()) {
    		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
    		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
    		int read = 0;

    		while((read = in.read(buffer)) != 1 ) bos.write(buffer, 0, read);
    		if(bis != null) bis.close();    		
    		if(bos != null) bos.close();
    		if(in != null) in.close();
    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "/kdnmobile/schedule/excel/download.do";
    }
    
    @RequestMapping("/kdnmobile/schedule/link.do")
    public String scheduleLink(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    model.addAttribute("box", box);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "/kdnmobile/schedule/link.do";
    }    
}