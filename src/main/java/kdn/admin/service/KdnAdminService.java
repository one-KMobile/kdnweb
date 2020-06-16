/**
 * 
 */
/**
 * @author netted
 *
 */
package kdn.admin.service;

import java.util.List;

import kdn.cmm.box.Box;


public interface KdnAdminService {
    /**
 	 * 순시자 리스트
 	 * @return list - [user_name
							, user_id
							, user_pwd
							, user_auth
							, user_email
							, mac_address
							, user_hp
							, comp_type]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	List<Box> getAdminList(Box box) throws Exception;
	
    /**
 	 * 순시자 정보
 	 * @param box - [user_id
 	 * 						, user_pwd]
 	 * @return box - [user_name
							, user_id
							, user_pwd
							, user_auth
							, user_email
							, mac_address
							, user_hp
							, comp_type]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	Box getAdminInfo(Box box) throws Exception;
	
    /**
 	 * 순시자 등록
 	 * @param box - [user_idx
							, fst_bizplc_cd
							, scd_bizplc_cd
							, user_name
							, user_id
							, user_pwd
							, user_tel
							, user_hp
							, user_email
							, user_auth
							, comp_type
							, conf_state
							, reg_date
							, use_yn]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	Object setAdminInsert(Box box) throws Exception;
	
    /**
 	 * 순시자 수정
 	 * @param box - [new_user_pwd
 	 * 						,user_pwd
							, mac_address
							, user_hp
							, user_email
							, user_auth]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	int setAdminUpdate(Box box) throws Exception; 
	
    /**
 	 * 순시자 총튜플 수
 	 * @param box - [user_id
 	  						, user_name
 	  						, user_email]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	int getAdminTotalCount(Box box) throws Exception;
	
    /**
 	 * 순시자 삭제
 	 * @param box - [user_id]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	int setAdminDelete(Box box) throws Exception;
	
    /**
 	 * 사업소명 검색
 	 * @param box - [fst_bizplc_cd, scd_bizplc_cd]
 	 * @return box - [fst_bizplc_cd_nm, scd_bizplc_cd_nm]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getBizplcNameInfo(Box box) throws Exception;

	/**
 	 * 디바이스 가진 순시자 검색
 	 * @param box
 	 * @return List<Box>
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	List<Box> getAdminIsNotTokenList(Box box);

	/**
 	 * 디바이스 삭제 
 	 * @param box
 	 * @return List<Box>
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	int setDeviceDel(Box box);

	/**
 	 * 순시자 디바이스 조회 
 	 * @param box
 	 * @return List<Box>
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	Box getUserToken(Box box);
	
	/**
 	 * 사업소에 맞는 순시자 디바이스 조회 
 	 * @param box
 	 * @return List<Box>
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	List<Box> getUserTokenList(Box box);
	
	/**
 	 * 순시자 아이디가 존재하냐?
 	 * @param box - [user_id]
 	 * @return box - [user_id]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	Box isExistToUserid(Box box) throws Exception; 
	

}