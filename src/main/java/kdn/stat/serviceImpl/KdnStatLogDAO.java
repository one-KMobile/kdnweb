package kdn.stat.serviceImpl;

import java.util.List;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;

@Repository("kdnStatLogDAO")
public class KdnStatLogDAO extends KdnSqlMapClient {
	
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getStatLogList(Box box) {
		return list("StatLog.getStatLogList", box);
	}
	
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getStatLogListTotCnt(Box box) {
		return (Integer) selectByPk("StatLog.getStatLogListTotCnt", box);
	}

	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@SuppressWarnings("unchecked")
	public List<Box> getStatYYLogList(Box box) {
		return list("StatLog.getStatYYLogList", box);
	}
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@SuppressWarnings("unchecked")
	public List<Box> getStatMMLogList(Box box) {
		return list("StatLog.getStatMMLogList", box);
	}
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@SuppressWarnings("unchecked")
	public List<Box> getStatDDLogList(Box box) {
		return list("StatLog.getStatDDLogList", box);
	}
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public int getStatYYLogListTotCnt(Box box) {
		return (Integer) selectByPk("StatLog.getStatYYLogListTotCnt", box);
	}
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public int getStatMMLogListTotCnt(Box box) {
		return (Integer) selectByPk("StatLog.getStatMMLogListTotCnt", box);
	}
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public int getStatDDLogListTotCnt(Box box) {
		return (Integer) selectByPk("StatLog.getStatDDLogListTotCnt", box);
	}
}
