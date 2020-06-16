package kdn.stat.service;

import java.util.List;

import kdn.cmm.box.Box;

public interface KdnStatLogService {

	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getStatLogList(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getStatLogListTotCnt(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getStatYYLogList(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getStatMMLogList(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */

	List<Box> getStatDDLogList(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */

	int getStatYYLogListTotCnt(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getStatMMLogListTotCnt(Box box);
	
	/**
	 * <설명> [로그 히스토리 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getStatDDLogListTotCnt(Box box);
	
}
