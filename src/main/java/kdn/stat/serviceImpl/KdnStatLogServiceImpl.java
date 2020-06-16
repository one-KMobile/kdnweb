package kdn.stat.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kdn.cmm.box.Box;
import kdn.stat.service.KdnStatLogService;

@Service("kdnStatLogService")
public class KdnStatLogServiceImpl implements KdnStatLogService{
	
	@Resource(name="kdnStatLogDAO")
	private KdnStatLogDAO kdnStatLogDAO;
	
	/**
	 * <설명> [로그 히스토리 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public List<Box> getStatLogList(Box box) {
		return kdnStatLogDAO.getStatLogList(box);
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
	@Override
	public int getStatLogListTotCnt(Box box) {
		return kdnStatLogDAO.getStatLogListTotCnt(box);
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
	@Override
	public List<Box> getStatYYLogList(Box box) {
		return kdnStatLogDAO.getStatYYLogList(box);
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
	@Override
	public List<Box> getStatMMLogList(Box box) {
		return kdnStatLogDAO.getStatMMLogList(box);
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
	@Override
	public List<Box> getStatDDLogList(Box box) {
		return kdnStatLogDAO.getStatDDLogList(box);
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
	@Override
	public int getStatYYLogListTotCnt(Box box) {
		return kdnStatLogDAO.getStatYYLogListTotCnt(box);
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
	@Override
	public int getStatMMLogListTotCnt(Box box) {
		return kdnStatLogDAO.getStatMMLogListTotCnt(box);
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
	@Override
	public int getStatDDLogListTotCnt(Box box) {
		return kdnStatLogDAO.getStatDDLogListTotCnt(box);
	}
	
}
