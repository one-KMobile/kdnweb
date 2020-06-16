package kdn.insResult.service;

import java.util.List;

import kdn.cmm.box.Box;

public interface KdnInsResultMantService {
	/**
	 * <설명> [송전설비] 설비 점검 조회
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	List<Box> getInsResultMantView(Box box) throws Exception;
	
	/**
	 * <설명> [송전설비] 설비 점검 마스터
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	List<Box> getInsResultMantMaster(Box box);

	/**
	 * <설명> [송전설비] 설비 점검 마스터
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */		
	List<Box> getInsResultGGT(Box box);;

}
