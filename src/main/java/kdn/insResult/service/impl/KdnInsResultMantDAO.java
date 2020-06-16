package kdn.insResult.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

@Repository("kdnInsResultMantDAO")
public class KdnInsResultMantDAO extends KdnSqlMapClient{
	/**
	 * <설명> [송전설비] 설비 점검 조회
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResultMantView(Box box) {
		return list("InsResultMant.getInsResultMantView", box);
	}
	
	/**
	 * <설명> [송전설비] 설비 점검 마스터
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResultMantMaster(Box box) {
		return list("InsResultMant.getInsResultMantMaster", box);
	}
	
	/**
	 * <설명> [송전설비] 기별 점검 마스터
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResultGGT(Box box) {
		return list("InsResultMant.getInsResultGGT", box);
	}

}
