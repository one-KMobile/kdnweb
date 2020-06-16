package kdn.login.web;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kdn.admin.service.KdnAdminService;
import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.FileManagerService;
import kdn.loghistory.service.KdnLogHistoryService;
import kdn.mobileconfirm.service.KdnMobileConfirmService;
import kdn.version.service.KdnVersionService;
import kdn.login.service.EgovLoginService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.sim.service.EgovClntInfo;


/**
 * 일반 로그인, 인증서 로그인을 처리하는 컨트롤러 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성 
 *  2011.8.26	정진오			IncludedInfo annotation 추가
 *  2011.09.07  서준식          스프링 시큐리티 로그인 및 SSO 인증 로직을 필터로 분리
 *  2011.09.25  서준식          사용자 관리 컴포넌트 미포함에 대한 점검 로직 추가
 *  2011.09.27  서준식          인증서 로그인시 스프링 시큐리티 사용에 대한 체크 로직 추가
 *  2011.10.27  서준식          아이디 찾기 기능에서 사용자 리름 공백 제거 기능 추가
 *  </pre>
 */

@Controller
public class EgovLoginController {
	
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
    
    @Resource(name="kdnAdminService")
    private KdnAdminService kdnAdminService;
    
    @Resource(name="kdnLogHistoryService")
    private KdnLogHistoryService kdnLogHistoryService;
    
    @Resource(name="kdnMobileConfirmService")
    private KdnMobileConfirmService kdnMobileConfirmService;
    
    /** KdnVersionService */
	@Resource(name = "kdnVersionService")
	private KdnVersionService kdnVersionService;
	
	@Resource(name = "fileManagerService")
	private FileManagerService fileManagerService;
	/** log */
    protected static final Log LOG = LogFactory.getLog(EgovLoginController.class);
	
    /**
	 * 일반(세션) 로그인을 처리한다
	 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception log모드
	 * @exception Exception
	 */
    @RequestMapping(value="/actionLogin.do")
    public String actionLogin(HttpServletRequest request, ModelMap model)
            throws Exception {
    	Box box = BoxUtil.getBox(request);
    	Box userInfo = null;
    	String ReusltScript = "";
    	
    	//1. 일반 로그인 처리
    	if(box.get("user_id") != null && !box.get("user_pwd").equals("")){
    		fileManagerService.setDerectoryCreate("d:/kdnweb/app/");
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
		} else if ("N".equals(userInfo.getString("use_yn"))) {
			ReusltScript += "<script type='text/javaScript' language='javascript'>";
			ReusltScript += "alert('사용여부가 미사용 처리로 되있습니다. 관리자께 문의하세요.');";
			ReusltScript += "</script>";
			model.addAttribute("reusltScript", ReusltScript);
			return "forward:/kdnIndex.do";
		}
		// 로그인 정보를 세션에 저장
		loginMethod.putSessionKey(request, box);
		// 세션 유지시간 설정(30일)
		loginMethod.sessionkeyDelay(request);
		
		//2.서버에 로그 이력 저장
		String getIp = request.getRemoteAddr() ;
		box.addData("log_type", "2") ;  //WEB login
		box.addData("mac_address", getIp) ;
		box.addData("remarks", "") ;
		box.addData("reg_id", box.getString("user_id")) ;
		kdnLogHistoryService.logSave(box) ;
		return "redirect:/kdn/admin/noticeList.do";
    }   
    
    
    
    /**
	 * 인증서 로그인을 처리한다
	 * @param vo - 주민번호가 담긴 LoginVO
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */
    @RequestMapping(value="/uat/uia/actionCrtfctLogin.do")
    public String actionCrtfctLogin(@ModelAttribute("loginVO") LoginVO loginVO, 
    		HttpServletRequest request,
    		HttpServletResponse response,
			ModelMap model)
            throws Exception {
    	
    	// 접속IP
    	String userIp = EgovClntInfo.getClntIP(request);
    	
    	/*
    	// 1. GPKI 인증
    	GPKIHttpServletResponse gpkiresponse = null;
	    GPKIHttpServletRequest gpkirequest = null;
	    String dn = "";
	    try{
	    	gpkiresponse = new GPKIHttpServletResponse(response);
		    gpkirequest = new GPKIHttpServletRequest(request);
	        gpkiresponse.setRequest(gpkirequest);
	        X509Certificate cert = null; 
	        
	        byte[] signData = null;
	        byte[] privatekey_random = null;
	        String signType = "";
	        String queryString = "";
	        
	        cert = gpkirequest.getSignerCert();
	        dn = cert.getSubjectDN();
	        
	        java.math.BigInteger b = cert.getSerialNumber();
	        b.toString();
	        int message_type =  gpkirequest.getRequestMessageType();
	        if( message_type == gpkirequest.ENCRYPTED_SIGNDATA || message_type == gpkirequest.LOGIN_ENVELOP_SIGN_DATA ||
	            message_type == gpkirequest.ENVELOP_SIGNDATA || message_type == gpkirequest.SIGNED_DATA){
	            signData = gpkirequest.getSignedData();
	            if(privatekey_random != null) {
	                privatekey_random   = gpkirequest.getSignerRValue();
	            }
	            signType = gpkirequest.getSignType();
	        }       
	        queryString = gpkirequest.getQueryString(); 
	    }catch(Exception e){
	    	return "cmm/egovError";
	    }
	    
	    // 2. 업무사용자 테이블에서 dn값으로 사용자의 ID, PW를 조회하여 이를 일반로그인 형태로 인증하도록 함
	    if (dn != null && !dn.equals("")) {
	    	
	    	loginVO.setDn(dn);
	    	LoginVO resultVO = loginService.actionCrtfctLogin(loginVO);
	        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
	        	
	        	//스프링 시큐리티패키지를 사용하는지 체크하는 로직
	        	if(EgovComponentChecker.hasComponent("egovAuthorManageService")){
	        		// 3-1. spring security 연동
		            return "redirect:/j_spring_security_check?j_username=" + resultVO.getUserSe() + resultVO.getId() + "&j_password=" + resultVO.getUniqId();
		            
	        	}else{
	        		// 3-2. 로그인 정보를 세션에 저장
		        	request.getSession().setAttribute("loginVO", resultVO);
		    		return "redirect:/uat/uia/actionMain.do";
	        	}
	            
	        } else {
	        	model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
	        	return "egovframework/com/uat/uia/EgovLoginUsr";
	        }
	    } else {
	    	model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
	    }
	   	*/
    	return "egovframework/com/uat/uia/EgovLoginUsr";
    }
    
    /**
	 * 로그인 후 메인화면으로 들어간다
	 * @param 
	 * @return 로그인 페이지
	 * @exception Exception
	 */
    @RequestMapping(value="/uat/uia/actionMain.do")
	public String actionMain(ModelMap model) 
			throws Exception {
    	
    	// 1. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	/*
    	// 2. 메뉴조회
		MenuManageVO menuManageVO = new MenuManageVO();
    	menuManageVO.setTmp_Id(user.getId());
    	menuManageVO.setTmp_UserSe(user.getUserSe());
    	menuManageVO.setTmp_Name(user.getName());
    	menuManageVO.setTmp_Email(user.getEmail());
    	menuManageVO.setTmp_OrgnztId(user.getOrgnztId());
    	menuManageVO.setTmp_UniqId(user.getUniqId());
    	List list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
		model.addAttribute("list_headmenu", list_headmenu);
    	*/
    	
		// 3. 메인 페이지 이동
		String main_page = Globals.MAIN_PAGE;
		
		LOG.debug("Globals.MAIN_PAGE > " +  Globals.MAIN_PAGE);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		LOG.debug("main_page > " +  main_page);
		
		
		if (main_page.startsWith("/")) {
		    return "forward:" + main_page;
		} else {
		    return main_page;
		}
		
		/*
		if (main_page != null && !main_page.equals("")) {
			
			// 3-1. 설정된 메인화면이 있는 경우
			return main_page;
			
		} else {
			
			// 3-2. 설정된 메인화면이 없는 경우
			if (user.getUserSe().equals("USR")) {
	    		return "egovframework/com/EgovMainView";
	    	} else {
	    		return "egovframework/com/EgovMainViewG";
	    	}
		}
		*/
	}
    
    /**
	 * 로그아웃한다.
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) 
			throws Exception {
    	
    	System.out.println("@@@@@@@@@@###########");
    	/*String userIp = EgovClntInfo.getClntIP(request);
    	
    	// 1. Security 연동
    	return "redirect:/j_spring_security_logout";*/
    	request.getSession().invalidate();
    	
    	return "redirect:/kdnIndex.do";
    	/*return "redirect:http://kdn.testbed.kr/";*/
    }
    
    /**
	 * 아이디/비밀번호 찾기 화면으로 들어간다
	 * @param 
	 * @return 아이디/비밀번호 찾기 페이지
	 * @exception Exception
	 */
	@RequestMapping(value="/uat/uia/egovIdPasswordSearch.do")
	public String idPasswordSearchView(ModelMap model) 
			throws Exception {
		
		// 1. 비밀번호 힌트 공통코드 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM022");
		List code = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("pwhtCdList", code);
		
		return "egovframework/com/uat/uia/EgovIdPasswordSearch";
	}
	
	/**
	 * 인증서안내 화면으로 들어간다
	 * @return 인증서안내 페이지
	 * @exception Exception
	 */
	@RequestMapping(value="/uat/uia/egovGpkiIssu.do")
	public String gpkiIssuView(ModelMap model) 
			throws Exception {
		return "egovframework/com/uat/uia/EgovGpkiIssu";
	}
	
    /**
	 * 아이디를 찾는다.
	 * @param vo - 이름, 이메일주소, 사용자구분이 담긴 LoginVO
	 * @return result - 아이디
	 * @exception Exception
	 */
    @RequestMapping(value="/uat/uia/searchId.do")
    public String searchId(@ModelAttribute("loginVO") LoginVO loginVO, 
    		ModelMap model)
            throws Exception {
    	
    	if (loginVO == null || loginVO.getName() == null || loginVO.getName().equals("")
    		&& loginVO.getEmail() == null || loginVO.getEmail().equals("")
    		&& loginVO.getUserSe() == null || loginVO.getUserSe().equals("")
    	) {
    		return "egovframework/com/cmm/egovError";
    	}
    	
    	// 1. 아이디 찾기
    	loginVO.setName(loginVO.getName().replaceAll(" ", ""));
        LoginVO resultVO = loginService.searchId(loginVO);
        
        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
        	
        	model.addAttribute("resultInfo", "아이디는 " + resultVO.getId() + " 입니다.");
        	return "egovframework/com/uat/uia/EgovIdPasswordResult";
        } else {
        	model.addAttribute("resultInfo", egovMessageSource.getMessage("fail.common.idsearch"));
        	return "egovframework/com/uat/uia/EgovIdPasswordResult";
        }
    }
    
    /**
	 * 비밀번호를 찾는다.
	 * @param vo - 아이디, 이름, 이메일주소, 비밀번호 힌트, 비밀번호 정답, 사용자구분이 담긴 LoginVO
	 * @return result - 임시비밀번호전송결과
	 * @exception Exception
	 */
    @RequestMapping(value="/uat/uia/searchPassword.do")
    public String searchPassword(@ModelAttribute("loginVO") LoginVO loginVO, 
    		ModelMap model)
            throws Exception {
    	
    	if (loginVO == null || loginVO.getId() == null || loginVO.getId().equals("")
    		&& loginVO.getName() == null || loginVO.getName().equals("")
    		&& loginVO.getEmail() == null || loginVO.getEmail().equals("")
    		&& loginVO.getPasswordHint() == null || loginVO.getPasswordHint().equals("")
    		&& loginVO.getPasswordCnsr() == null || loginVO.getPasswordCnsr().equals("")
    		&& loginVO.getUserSe() == null || loginVO.getUserSe().equals("")
    	) {
    		return "egovframework/com/cmm/egovError";
    	}
    	
    	// 1. 비밀번호 찾기
        boolean result = loginService.searchPassword(loginVO);
        
        // 2. 결과 리턴
        if (result) {
        	model.addAttribute("resultInfo", "임시 비밀번호를 발송하였습니다.");
        	return "egovframework/com/uat/uia/EgovIdPasswordResult";
        } else {
        	model.addAttribute("resultInfo", egovMessageSource.getMessage("fail.common.pwsearch"));
        	return "egovframework/com/uat/uia/EgovIdPasswordResult";
        }
    }
    
    /**
	 * 개발 시스템 구축 시 발급된 GPKI 서버용인증서에 대한 암호화데이터를 구한다.
	 * 최초 한번만 실행하여, 암호화데이터를 EgovGpkiVariables.js의 ServerCert에 넣는다.
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/uat/uia/getEncodingData.do")
	public void getEncodingData() 
			throws Exception {
    	
    	/*
    	X509Certificate x509Cert = null;
		byte[] cert = null;
		String base64cert = null;
		try {
			x509Cert = Disk.readCert("/product/jeus/egovProps/gpkisecureweb/certs/SVR1311000011_env.cer");
			cert = x509Cert.getCert();
			Base64 base64 = new Base64();
			base64cert = base64.encode(cert); 
			log.info("+++ Base64로 변환된 인증서는 : " + base64cert);
			
		} catch (GpkiApiException e) {
			e.printStackTrace();
		}
		*/
    }
    
    /**
     * 인증서 DN추출 팝업을 호출한다.
     * @return 인증서 등록 페이지
     * @exception Exception
     */
    @RequestMapping(value = "/uat/uia/EgovGpkiRegist.do")
    public String gpkiRegistView(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	/** GPKI 인증 부분 */
        // OS에 따라 (local NT(로컬) / server Unix(서버)) 구분
        String os = System.getProperty("os.arch");
        //String virusReturn = null;
        
        /*
        // 브라우저 이름을 받기위한 처리
    	String webKind = EgovClntInfo.getClntWebKind(request);
    	String[] ss = webKind.split(" ");
    	String browser = ss[1];
    	model.addAttribute("browser",browser);
    	// -- 여기까지
        if (os.equalsIgnoreCase("x86")) {
            //Local Host TEST 진행중
        } else {
            if (browser.equalsIgnoreCase("Explorer")) {
		GPKIHttpServletResponse gpkiresponse = null;
		GPKIHttpServletRequest gpkirequest = null;

		try {
		    gpkiresponse = new GPKIHttpServletResponse(response);
		    gpkirequest = new GPKIHttpServletRequest(request);
		    
		    gpkiresponse.setRequest(gpkirequest);
		    model.addAttribute("challenge", gpkiresponse.getChallenge());
		    
		    return "egovframework/com/uat/uia/EgovGpkiRegist";

		} catch (Exception e) {
		    return "egovframework/com/cmm/egovError";
		}
	    } 	
        }
        */
        return "egovframework/com/uat/uia/EgovGpkiRegist";
    }

    /**
     * 인증서 DN값을 추출한다
     * @return result - dn값
     * @exception Exception
     */
    @RequestMapping(value = "/uat/uia/actionGpkiRegist.do")
	public String actionGpkiRegist(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		/** GPKI 인증 부분 */
		// OS에 따라 (local NT(로컬) / server Unix(서버)) 구분
		String os = System.getProperty("os.arch");
		// String virusReturn = null;
		String dn = "";

		// 브라우저 이름을 받기위한 처리
		String webKind = EgovClntInfo.getClntWebKind(request);
		String[] ss = webKind.split(" ");
		String browser = ss[1];
		model.addAttribute("browser", browser);
		/*
		// -- 여기까지
		if (os.equalsIgnoreCase("x86")) {
			// Local Host TEST 진행중
		} else {
			if (browser.equalsIgnoreCase("Explorer")) {
				GPKIHttpServletResponse gpkiresponse = null;
				GPKIHttpServletRequest gpkirequest = null;
				try {
					gpkiresponse = new GPKIHttpServletResponse(response);
					gpkirequest = new GPKIHttpServletRequest(request);
					gpkiresponse.setRequest(gpkirequest);
					X509Certificate cert = null;

					// byte[] signData = null;
					// byte[] privatekey_random = null;
					// String signType = "";
					// String queryString = "";

					cert = gpkirequest.getSignerCert();
					dn = cert.getSubjectDN();

					model.addAttribute("dn", dn);
					model
							.addAttribute("challenge", gpkiresponse
									.getChallenge());

					return "egovframework/com/uat/uia/EgovGpkiRegist";
				} catch (Exception e) {
					return "egovframework/com/cmm/egovError";
				}
			}
		}
		*/
		return "egovframework/com/uat/uia/EgovGpkiRegist";
	}
    
    /**
	 * <설명>
	 * 로그인 API
	 * @param [user_id, user_password]  
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @exception log모드
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/api/login.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView apiLogin(ModelMap model, HttpServletRequest request){ 
System.out.println("******************************oneoneoneoneoneoneoneone*************************************");    	
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
       /* if("".equals(box.getString("searchCnd")) || "".equals(box.getString("searchWrd")) ){ //필수 파라메터 체크
    	   modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_REQUIRE);
		   return modelAndView;
        }*/
        if("".equals(box.getString("user_id")) || "".equals(box.getString("user_pwd"))) {
        	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
        	return modelAndView;
        }
        try {
        	/*String path = "d:/kdnweb/app/";
        	File dir = new File(path);
    		if(!dir.isDirectory()) {
    			dir.mkdir();
    		}*/
        	fileManagerService.setDerectoryCreate("d:/kdnweb/app/");
    		Box userinfo = loginService.getUserInfo(box);
    		int isExistUserId = loginService.isExistUserId(box);
    		Box sessionBox = null;
            //if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
    		if(isExistUserId == 0) {
    			modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_ID);
    			return modelAndView;
    		}
    		else if(userinfo == null) {
            	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_PASSWORD);
            	return modelAndView;
            }
    		else if("N".equals(userinfo.getString("use_yn"))){
            	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_UNUSE);
            	return modelAndView;
            }
    		else if(box.getString("user_id").equals(userinfo.getString("user_id")) && box.getString("user_pwd").equals(userinfo.getString("user_pwd"))) {	
	        	//로그인 정보를 세션에 저장
	        	loginMethod.putSessionKey(request, box);
	        	//세션 유지시간 설정(30일)
	        	loginMethod.sessionkeyDelay(request);
	        	//세션 정보 호출
	        	sessionBox = loginMethod.getSessionKey(request);
	        	//디바이스키 업데이트
	        	loginService.setUserDeviceToken(box);
	        		
	        	//로그 테이블에 정보저장
	        	String getDevice_token = box.getString("device_token") ;
	    		box.addData("log_type", "1") ;  // APP login  
	    		box.addData("mac_address", getDevice_token) ;
	    		box.addData("remarks", "") ;
	    		box.addData("reg_id", box.getString("user_id")) ;
	    		kdnLogHistoryService.logSave(box) ;
	    		
	    		//모바일 최신 버전 정보 내려주기
	    		Box versionBox = kdnVersionService.getVersionLastVersion(box);
	    		sessionBox.addData("appVersion", versionBox.get("version"));
	    		sessionBox.addData("appUrl", versionBox.get("url"));
	    		
	    		//모바일 인증 데이터에 정보저장(처음사용자만)
	    		Box existBox =  kdnMobileConfirmService.getMobileExist(box);
	    		if(existBox == null){ //최초 로그인
	    			box.addData("mobile_sn", getDevice_token);
	    			box.addData("check_yn", "N");
	    			kdnMobileConfirmService.saveMobile(box) ;
	    		}else if("N".equals(existBox.getString("CHECK_YN"))){  //모바일 미승인
    				modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_NOT_CONFIRM_MOBILE);
    				return modelAndView ;
	    		}
            }else {
            	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_PASSWORD);
            	return modelAndView;
            }
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
        	modelAndView.addObject("session",sessionBox);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        return modelAndView;
    }
    
    /**
	 * <설명>
	 * 로그아웃 API
	 * @param [user_id, user_password]  
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/api/logout.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView apiLogout(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        
       /* if("".equals(box.getString("searchCnd")) || "".equals(box.getString("searchWrd")) ){ //필수 파라메터 체크
    	   modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_REQUIRE);
		   return modelAndView;
        }*/
        
        try {
        	//로그아웃
        	request.getSession().invalidate();
        	Box sessionBox = loginMethod.getSessionKey(request);
        	sessionBox.clear();
        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
        	modelAndView.addObject("session",sessionBox);
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        
        return modelAndView;
    }        
    
    
    /**
	 * <설명>
	 * 앱 삭제 완료 처리 확인 페이지
	 * @param [user_id]  
	 * @return [modelAndView]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @URL테스트: 
	 */ 
    @RequestMapping(value="/api/deviceDelConfirm.*", method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView apiDeviceDelConfirm(ModelMap model, HttpServletRequest request){        
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        Box box = BoxUtil.getBox(request);
        if("".equals(box.getString("user_id"))) {
        	modelAndView.addObject(ConstantValue.RESULT_CODE, ConstantValue.RESULT_FAIL_REQUIRE);
        	return modelAndView;
        }
        try {
        	//앱 삭제 처리 업데이트
        	box.addData("device_del", "3") ;
        	if(	kdnAdminService.setDeviceDel(box) > 0 ){ //device_del 값 3 로 변경  1:정상 2:삭제 처리중 3:삭제 완료
        		modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
        	}else{
        		modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
        	}
		} catch (Exception e) {
			modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
			e.printStackTrace();
		}    
        return modelAndView;
    }
    
    
}