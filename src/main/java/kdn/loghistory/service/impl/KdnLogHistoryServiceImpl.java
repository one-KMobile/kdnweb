package kdn.loghistory.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.loghistory.service.KdnLogHistoryService;

import org.springframework.stereotype.Service;


@Service("kdnLogHistoryService")
public class KdnLogHistoryServiceImpl implements KdnLogHistoryService {
	
	@Resource(name="kdnLogHistoryDAO")
	private KdnLogHistoryDAO kdnLogHistoryService ;
	
	/**
	 * <설명> [로그 히스토리] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public List<Box> getLogList(Box box) {
		return kdnLogHistoryService.getLogList(box);
	}
	
	/**
	 * <설명> [로그 히스토리] 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public int getLogListTotCnt(Box box) {
		return kdnLogHistoryService.getLogListTotCnt(box) ;
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
	@Override
	public List<Box> getLogListAll(Box box) {
		return kdnLogHistoryService.getLogListAll(box);
	}

	/**
	 * <설명> [로그 히스토리] 모든 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public int getLogListTotCntAll(Box box) {
		return kdnLogHistoryService.getLogListTotCntAll(box) ;
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
	@Override
	public Box getLogView(Box box) {
		return kdnLogHistoryService.getLogView(box);
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
	@Override
	public void logSave(Box box) {
		kdnLogHistoryService.logSave(box);
	}

}
