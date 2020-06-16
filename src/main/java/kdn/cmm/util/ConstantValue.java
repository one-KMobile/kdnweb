package kdn.cmm.util;


public class ConstantValue {
	/**
	 * RESULT CODE
	 */
	public static final String RESULT_SUCCESS = "001"; //성공
	public static final String RESULT_FAIL = "002"; //실패
	public static final String RESULT_FAIL_REQUIRE = "003"; //필수 파라메터 없음
	public static final String RESULT_FAIL_ID = "004"; //아이디 없음
	public static final String RESULT_FAIL_PASSWORD = "005"; //패스워드 틀림
	public static final String RESULT_FAIL_EXIST = "006"; //패스워드 틀림
	public static final String RESULT_FAIL_SESSION = "007"; //세션 필요
	public static final String RESULT_FAIL_NODATA = "008"; //필수데이터 없음
	public static final String RESULT_FAIL_BYTE = "009"; // 문자열 byte 초과
	public static final String RESULT_ACCEPT_LOGIN = "011"; // 이중 로그인 확인 코드
	public static final String RESULT_FAIL_UNUSE = "012"; // 계정 미사용 코드
	public static final String RESULT_NOT_CONFIRM_MOBILE= "013"; // 모바일 미승인 코드
	public static final String AUTH_FAIL = "014"; // 권한이 없음
	public static final String SESSION_FAIL = "015"; // 세션이 없음
	public static final String RESULT_CODE = "code"; //코드
	
	/**
	 * HTTP VALUE 
	 */
	public static final String HTTP_MULTIPART = "multipart/form-data"; //ENCTYPE
	public static final String HTTP_GET = "GET"; //METHOD
	public static final String HTTP_POST = "POST"; //METHOD
	
    /*
     * VIEW CONTROLE
     */
    public static final String JSON_VIEW = "jsonView";
}
