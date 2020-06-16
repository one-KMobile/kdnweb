package kdn.loghistory.service.impl;

import java.util.List;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;


@Repository("kdnLogHistoryDAO")
public class KdnLogHistoryDAO extends KdnSqlMapClient {
	/**
	 * <설명> [로그 히스토리] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getLogList(Box box) {
		return list("LogHistory.getLogList" , box);
	}
	
	/**
	 * <설명> [로그 히스토리] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getLogListTotCnt(Box box) {
		return  (Integer) getSqlMapClientTemplate().queryForObject("LogHistory.getLogListTotCnt",box) ;
	}
	
	/**
	 * <설명> [로그 히스토리] 모든 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getLogListAll(Box box) {
		return list("LogHistory.getLogListAll" , box);
	}
	
	/**
	 * <설명> [로그 히스토리] 모든 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getLogListTotCntAll(Box box) {
		return  (Integer) getSqlMapClientTemplate().queryForObject("LogHistory.getLogListTotCntAll",box) ;
	}
	
	/**
	 * <설명> [로그 히스토리] 상세 조회 
	 * 
	 * @param [box]
	 * @return [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getLogView(Box box) {
		return (Box)selectByPk("LogHistory.getLogView" , box);
	}
	
	/**
	 * <설명> [로그 히스토리] 로그 저장 
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void logSave(Box box) {
		insert("LogHistory.logSave" ,box) ;
	}
}
