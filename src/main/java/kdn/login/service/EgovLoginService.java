package kdn.login.service;

import kdn.cmm.box.Box;
import egovframework.com.cmm.LoginVO;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 인터페이스 클래스
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
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  </pre>
 */
public interface EgovLoginService {
	
    /**
     * 2011.08.26
	 * 로그인 계정 조회
	 * @param - [user_id] 
	 * @return box - [user_id 정보]
	 * @exception Exception
	 */	
	public Box getUserInfo(Box box) throws Exception;
	 
    /**
     * 2011.08.26
	 * 계정 사업소 조회
	 * @param - [user_id] 
	 * @return box - [user_id 정보]
	 * @exception Exception
	 */    
    public Box getBizplcInfo(Box box) throws Exception;
    
	/**
     * 2011.08.26
	 * EsntlId를 이용한 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception;
	
	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    LoginVO actionLogin(LoginVO vo) throws Exception;
    
    /**
	 * 인증서 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    LoginVO actionCrtfctLogin(LoginVO vo) throws Exception;
    
    /**
	 * 아이디를 찾는다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    LoginVO searchId(LoginVO vo) throws Exception;
    
    /**
	 * 비밀번호를 찾는다.
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
    boolean searchPassword(LoginVO vo) throws Exception;

    /**
	 * 순시자 디비이스 토큰을 업데이트 한다.
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
	public void setUserDeviceToken(Box box);
	
	/**
	 * <설명> 순시자 아이디 존재유무 체크
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public int isExistUserId(Box box);
}
