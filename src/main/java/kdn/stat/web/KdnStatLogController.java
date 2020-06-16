package kdn.stat.web;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.admin.service.KdnAdminService;
import kdn.admin.service.impl.KdnAdminMethod;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.KdnCommonUtil;
import kdn.cmm.util.StringUtil;
import kdn.code.service.KdnCodeService;
import kdn.eqp.service.KdnEqpService;
import kdn.loghistory.service.KdnLogHistoryService;
import kdn.login.web.LoginMethod;
import kdn.stat.service.KdnStatLogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class KdnStatLogController{
	
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
    
    /** KdnLogHistoryService */
    @Resource(name="kdnLogHistoryService")
    private KdnLogHistoryService kdnLogHistoryService;
    
    /** KdnEqpService */
    @Resource(name="kdnEqpService")
    private KdnEqpService kdnEqpService;
    
    /** KdnStatLogService */
    @Resource(name="kdnStatLogService")
    private KdnStatLogService kdnStatLogService;
    
    /** KdnCommonUtil */
    @Resource(name="kdnCommonUtil")
    private KdnCommonUtil kdnCommonUtil;
    
    /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
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
   	 * [로그 히스토리 통계] 개별 목록을 조회한다. (pageing)
   	 * @param [request] [model] 
   	 * @return "/kdn/admin/logHistoryList.do"
   	 * @exception Exception
   	 * @author [신명섭] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
   	 */
   @RequestMapping(value="/kdn/stat/statLogList")
   public String logHistoryList(HttpServletRequest request, ModelMap model) throws Exception {
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

	    	//관리자 검색 카테고리 [코드]
//		    box.put("groupCodeId", "ADMIN01"); 
//			List<Box> codeList = kdnCodeService.selectCodeList(box);
			
			
			//{recordCountPerPage=10, firstIndex=0, pageIndex=1, searchWrd=t, 
			//lastIndex=10, groupCodeId=ADMIN01, log_type=, s_category_code=63, user_id=}
			
//			if("63".equals(box.getString("s_category_code"))) {
//				box.put("reg_id", box.getString("searchWrd"));
//			}else if("65".equals(box.getString("s_category_code"))) {
//				box.put("user_name", box.getString("searchWrd"));
//			}	
//			}else if("66".equals(box.getString("s_category_code"))) {
//				box.put("search_user_email", box.getString("searchWrd"));
//			}
			
	    	
	    	//관리자 검색 카테고리 [접속타입]
		    box.put("groupCodeId", "LOG_TYPE"); 
		    List logTypeList = kdnCodeService.selectCodeList(box) ;
		    box.put("groupCodeId", "DATE_TYPE"); 
		    
		    List dateTypeList = kdnCodeService.selectCodeList(box) ;
		    
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
    		if(!"".equals(box.getString("fst_bizplc_cd"))) box.addData("fst_bizplc_cd", box.getString("fst_bizplc_cd"));
    		if(!"".equals(box.getString("scd_bizplc_cd")))	 box.addData("scd_bizplc_cd", box.getString("scd_bizplc_cd"));
			
    		//날자 검색을 위한 형변환
			if(!"".equals(box.getString("startdate"))) box.addData("cont_startdate", box.getString("startdate").replace("-", "" )+"000000") ;
			if(!"".equals(box.getString("enddate")))  box.addData("cont_enddate", box.getString("enddate").replace("-", "" )+"235959") ;
			
			List<Box> list = null ;
			int totCnt = 0 ;
			
			//년월일분기
			if("YY".equals(box.getString("date_type"))){
				list = kdnStatLogService.getStatYYLogList(box);
				totCnt = kdnStatLogService.getStatYYLogListTotCnt(box) ;
			}else if("MM".equals(box.getString("date_type"))){
				list = kdnStatLogService.getStatMMLogList(box);
				totCnt = kdnStatLogService.getStatMMLogListTotCnt(box) ;
			}else if("DD".equals(box.getString("date_type"))){
				list = kdnStatLogService.getStatDDLogList(box);
				totCnt = kdnStatLogService.getStatDDLogListTotCnt(box) ;
			}else{
				list = kdnStatLogService.getStatDDLogList(box);
				totCnt = kdnStatLogService.getStatDDLogListTotCnt(box) ;
			}
			
			paginationInfo.setTotalRecordCount(totCnt);
			
			model.addAttribute("list", list);
			model.addAttribute("logTypeList", logTypeList); 
			model.addAttribute("dateTypeList", dateTypeList); 
			
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt);
			//model.addAttribute("codeList", codeList); 
		    model.addAttribute("paginationInfo", paginationInfo);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return "/kdn/stat/statLogList";
   }
   
}