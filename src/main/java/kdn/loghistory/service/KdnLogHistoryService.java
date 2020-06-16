package kdn.loghistory.service;

import java.util.List;

import kdn.cmm.box.Box;


public interface KdnLogHistoryService{
	
	/**
	 * <설명> [로그 히스토리] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getLogList(Box box);
	
	/**
	 * <설명> [로그 히스토리] 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getLogListTotCnt(Box box);
	
	/**
	 * <설명> [로그 히스토리] 모든 목록 조회 
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getLogListAll(Box box);
	
	/**
	 * <설명> [로그 히스토리] 모든 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getLogListTotCntAll(Box box);
	
	/**
	 * <설명> [로그 히스토리] 상세 조회 
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	Box getLogView(Box box);
	
	/**
	 * <설명> [로그 히스토리] 상세 조회 
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void logSave(Box box);

}
