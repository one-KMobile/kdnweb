package kdn.insResultStat.service;
import java.util.List;
import kdn.cmm.box.Box;

public interface KdnInsResultStatService {
	
	/**
	 * <설명> [순시결과 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getInsResultList(Box box);
	
	/**
	 * <설명> [순시결과 통계] 카운터
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getInsResultStatListTotCnt(Box box);

}
