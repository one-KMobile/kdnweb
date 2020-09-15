package kdn.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kdn.api.service.KdnApiService;
import kdn.cmm.box.Box;
import kdn.login.service.EgovLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component("loginMethod")
public class LoginMethod {

	/** EgovLoginService */
	@Resource(name = "loginService")
    private EgovLoginService loginService;
	
	@Autowired
	@Qualifier("kdnApiService")
	private KdnApiService kdnApiService;
	
	private String session_key = null;
	private String user_id = null;
	private String user_pwd = null;
	private String user_name = null;
	private String user_hp = null;
	private String user_email = null;
	private String user_auth = null; 
	private String mac_address = null;
	private String comp_name = null;
	private String fst_bizplc_cd_nm = null;
	private String fst_bizplc_cd = null;
	private String scd_bizplc_cd_nm = null;
	private String scd_bizplc_cd = null;
	private String device_token = null;
	private String contract = null;
	
    /**
	 * 세션키 입력
	 * @param - [request - HttpServletRequest, box - 웹 파라미터]
	 * @exception - [주석처리부분은 m_bizplc_t디비의 데이터가 있을경우 주석해제]
	 */    
    public void putSessionKey(HttpServletRequest request, Box box) throws Exception {
    	Box userinfoBox = loginService.getUserInfo(box);
    	//json 형태로 호출 할 경우 필드값을 모두 대문자로 표기해야 함
    	Box userBizplcBox = loginService.getBizplcInfo(userinfoBox);
    	//최신계약번호
    	String contract = kdnApiService.getLatestContract(box).getString("INS_PLAN_NO");
    	System.out.println("user_info : " + userinfoBox);
    	request.getSession().setAttribute(box.getString("user_id"), box);
    	//request.getSession().putValue("session_key", request.getSession().getId());
    	request.getSession().setAttribute(request.getSession().getId(), box);
    	this.session_key = request.getSession().getId();
    	
    	System.out.println("타나" + request.getSession().getId());
    	//request.getSession().putValue("user_id", box.getString("user_id"));
    	request.getSession().setAttribute(box.getString("user_id"), box);
    	this.user_id = box.getString("user_id");
    	//request.getSession().putValue("user_pwd", box.getString("user_pwd"));
    	request.getSession().setAttribute(box.getString("user_pwd"), box);
    	this.user_pwd = box.getString("user_pwd");
    	//request.getSession().putValue("user_name", userinfoBox.getString("user_name"));
    	request.getSession().setAttribute(box.getString("user_name"), box);
    	this.user_name = userinfoBox.getString("user_name");
    	//request.getSession().putValue("user_hp", userinfoBox.getString("user_hp"));
    	request.getSession().setAttribute(box.getString("user_hp"), box);
    	this.user_hp = userinfoBox.getString("user_hp");
    	//request.getSession().putValue("user_email", userinfoBox.getString("user_email"));
    	request.getSession().setAttribute(box.getString("user_email"), box);
    	this.user_email = userinfoBox.getString("user_email");
    	//request.getSession().putValue("user_auth", userinfoBox.getString("user_auth")); 
    	request.getSession().setAttribute(box.getString("user_auth"), box);
    	this.user_auth = userinfoBox.getString("user_auth");
    	//request.getSession().putValue("mac_address", userinfoBox.getString("mac_address"));
    	request.getSession().setAttribute(box.getString("mac_address"), box);
    	this.mac_address = userinfoBox.getString("mac_address");
    	//request.getSession().putValue("comp_name", userinfoBox.getString("comp_name"));
    	request.getSession().setAttribute(box.getString("comp_name"), box);
    	this.comp_name = userinfoBox.getString("user_id");
    	//request.getSession().putValue("fst_bizplc_nm", userBizplcBox.getString("FST_BIZPLC_CD_NM"));
    	request.getSession().setAttribute(box.getString("FST_BIZPLC_CD_NM"), box);
    	this.fst_bizplc_cd_nm = userBizplcBox.getString("FST_BIZPLC_CD_NM");
    	//request.getSession().putValue("fst_bizplc_cd", userBizplcBox.getString("FST_BIZPLC_CD"));
    	request.getSession().setAttribute(box.getString("FST_BIZPLC_CD"), box);
    	this.fst_bizplc_cd = userBizplcBox.getString("FST_BIZPLC_CD");
    	//request.getSession().putValue("scd_bizplc_nm", userBizplcBox.getString("SCD_BIZPLC_CD_NM"));
    	request.getSession().setAttribute(box.getString("SCD_BIZPLC_CD_NM"), box);
    	this.scd_bizplc_cd_nm = userBizplcBox.getString("SCD_BIZPLC_CD_NM");
    	//request.getSession().putValue("scd_bizplc_cd", userBizplcBox.getString("SCD_BIZPLC_CD"));
    	request.getSession().setAttribute(box.getString("SCD_BIZPLC_CD"), box);
    	this.scd_bizplc_cd = userBizplcBox.getString("SCD_BIZPLC_CD");
    	//request.getSession().putValue("device_token", box.getString("device_token"));
    	request.getSession().setAttribute(box.getString("device_token"), box);
    	this.device_token = box.getString("device_token");
    	//request.getSession().putValue("contract", contract);
    	request.getSession().setAttribute(contract, box);
    	this.contract = contract;
    	//request.getSession().putValue("contract", contract);
    	request.getSession().setAttribute(contract, box);
    	this.contract = contract;
    }
    
    /**
	 * 세션지속시간 입력
	 * @param - [request - HttpServletRequest]
	 * @exception Exception
	 */    
    public void sessionkeyDelay (HttpServletRequest request) throws Exception {
    	request.getSession().setMaxInactiveInterval(60*60*24*30);
    }
    
    /**
	 * 세션키 호출
	 * @param - [request - HttpServletRequest]
	 * @exception - [주석처리부분은 m_bizplc_t디비의 데이터가 있을경우 주석해제]
	 */       
    public Box getSessionKey(HttpServletRequest request) throws Exception {
    	Box sessionBox = new Box();
    	System.out.println("세션키 확인 : " + request.getSession().getId());
    	System.out.println("아이디 : " + this.user_id);
    	sessionBox.put("user_id", this.user_id);
    	sessionBox.put("user_pwd", this.user_pwd);
    	sessionBox.put("session_key", this.session_key);
    	sessionBox.put("user_name", this.user_name);
    	sessionBox.put("user_hp", this.user_hp);
    	sessionBox.put("user_email", this.user_email);
    	sessionBox.put("user_auth", this.user_auth);
    	sessionBox.put("mac_address", this.mac_address);
    	sessionBox.put("comp_name", this.comp_name);
    	sessionBox.put("fst_bizplc_cd_nm", this.fst_bizplc_cd_nm);
    	sessionBox.put("fst_bizplc_cd", this.fst_bizplc_cd);
    	sessionBox.put("scd_bizplc_cd_nm", this.scd_bizplc_cd_nm);
    	sessionBox.put("scd_bizplc_cd", this.scd_bizplc_cd);
    	sessionBox.put("device_token", this.device_token);
    	sessionBox.put("contract", contract);
    	request.getSession();
    	System.out.println("세션박스 : " + sessionBox);
    	return sessionBox;
    }
    
    /**
	 * 관리자 세션키 호출
	 * @param - [request - HttpServletRequest]
	 * @exception - [주석처리부분은 m_bizplc_t디비의 데이터가 있을경우 주석해제]
	 */    
    public Box getAdminSessionKey(HttpServletRequest request) throws Exception {
    	Box sessionBox = new Box();
    	System.out.println("세션키 확인 : " + request.getSession().getId());//request.getSession().getId()
    	//sessionBox.put("fst_bizplc_cd", request.getSession().getValue("fst_bizplc_cd"));
    	System.out.println("*******fst_bizplc_cd************" + this.fst_bizplc_cd);
    	sessionBox.put("fst_bizplc_cd", this.fst_bizplc_cd);
    	//sessionBox.put("fst_bizplc_cd_nm", request.getSession().getValue("fst_bizplc_cd_nm"));
    	System.out.println("*******fst_bizplc_cd_nm************" + this.fst_bizplc_cd_nm);
    	sessionBox.put("fst_bizplc_cd_nm", this.fst_bizplc_cd_nm);
    	//sessionBox.put("scd_bizplc_cd", request.getSession().getValue("scd_bizplc_cd"));
    	System.out.println("*******scd_bizplc_cd************" + this.scd_bizplc_cd);
    	sessionBox.put("scd_bizplc_cd", this.scd_bizplc_cd);
    	//sessionBox.put("scd_bizplc_cd_nm", request.getSession().getValue("scd_bizplc_cd_nm"));
    	System.out.println("*******scd_bizplc_cd_nm************" + this.scd_bizplc_cd_nm);
    	sessionBox.put("scd_bizplc_cd_nm", this.scd_bizplc_cd_nm);
    	//sessionBox.put("user_id", request.getSession().getValue("user_id"));
    	System.out.println("*******user_id************" + this.user_id);
    	sessionBox.put("user_id", this.user_id);
    	//sessionBox.put("user_name", request.getSession().getValue("user_name"));   
    	System.out.println("*******user_name************" + this.user_name);
    	sessionBox.put("user_name", this.user_name); 	
    	//sessionBox.put("user_pwd", request.getSession().getValue("user_pwd")); 
    	System.out.println("*******user_pwd************" + this.user_pwd);
    	sessionBox.put("user_pwd", this.user_pwd); 	
    	//sessionBox.put("user_hp", request.getSession().getValue("user_hp"));
    	System.out.println("*******user_hp************" + this.user_hp);
    	sessionBox.put("user_hp", this.user_hp); 	
    	//sessionBox.put("user_email", request.getSession().getValue("user_email"));
    	System.out.println("*******user_email************" + this.user_email);
    	sessionBox.put("user_email", this.user_email); 	
    	//sessionBox.put("user_auth", request.getSession().getValue("user_auth"));
    	System.out.println("*******user_auth************" + this.user_auth);
    	sessionBox.put("user_auth", this.user_auth); 	
    	//sessionBox.put("mac_address", request.getSession().getValue("mac_address"));
    	System.out.println("*******mac_address************" + this.mac_address);
    	sessionBox.put("mac_address", this.mac_address); 
    	//sessionBox.put("session_key", request.getSession().getValue("session_key"));
    	System.out.println("*******session_key************" + this.session_key);
    	sessionBox.put("session_key", this.session_key); 
    	
    	request.getSession();
    	System.out.println("세션박스 : " + sessionBox);
    	return sessionBox;
    }
    
    /**
	 * 세션키 체크
	 * @param - [request - HttpServletRequest]
	 * @return - [boolean - true : 세션키 존재함, false : 세션키 존재하지 않음]
	 * @exception Exception
	 */     
    public boolean isExistSessionKey(HttpServletRequest request) {
    	  
    	System.out.println("세션키 : "+request.getSession().getId());

    	
    	/*if(request.getSession(true).isNew()) {
    		return false;
    	}else {
    		return true;
    	}*/
    	if(session_key != null){
    		return true;
    	}else {
    		return false;
    	}
    }
}
