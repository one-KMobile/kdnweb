/**
 * 
 */
/**
 * @author netted
 *
 */
package kdn.traking.service;

import java.util.List;

import kdn.cmm.box.Box;


public interface KdnTrakingService {
	
    /**
 	 * 순시자 트래킹 정보
 	 * @param box - [user_id]
 	 * @return box - [user_id
							, latitude
							, longitude
							, remarks]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	Box getTrakingInfo(Box box) throws Exception;
	
    /**
 	 * 사업소의 의한 순시자 목록
 	 * @param box - [fst_bizplc_cd, scd_bizplc_cd]
 	 * @return box - [ user_id
							, user_name
							, latitude
							, longitude
							, remarks]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	List<Box> getAdminByBizplcList(Box box) throws Exception;
	
    /**
 	 * 최신 날짜로 검색된 TRAKING_USER 정보
 	 * @param box - [user_id
 	 * 						, fst_bizplc_cd
							, scd_bizplc_cd]
 	 * @return box - [ user_id
							, latitude
							, longitude
							, remarks]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getImmediatelyTraking(Box box) throws Exception;
	
    /**
 	 * 최신 날짜로 검색된 TRAKING_USER 정보
 	 * @return box - [ user_id
							, user_name]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public List<Box> getTrakingUserList(Box box) throws Exception;
	
	/**
 	 * 선택된 날짜로 순시를 한 순시자 트래킹
 	 * @param box - [ user_id
							, traking_date
							, fst_bizplc_cd
							, scd_bizplc_cd]
	 * @return box - [ user_id
							, user_name
							, latitude
							, longitude
							, reg_date]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public List<Box> getTrakingByUserList(Box box) throws Exception;
	
    /**
 	 * 선택된 날짜로 순시를 한 순시자 트래킹
 	 * @param box - [ user_id]
	 * @return box - [ user_id
							, user_name
							, latitude
							, longitude
							, reg_date]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */		
	public List<Box> getRecentTrakingList(Box box) throws Exception ;
}