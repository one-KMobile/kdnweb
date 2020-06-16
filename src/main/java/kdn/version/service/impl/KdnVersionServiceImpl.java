package kdn.version.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kdn.cmm.box.Box;
import kdn.stat.service.KdnStatLogService;
import kdn.version.service.KdnVersionService;

@Service("kdnVersionService")
public class KdnVersionServiceImpl implements KdnVersionService{
	
	@Resource(name="kdnVersionDAO")
	private KdnVersionDAO kdnVersionDAO;
	
	/**
	 * <설명> [모바일 버전 관리] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public List<Box> getVersionList(Box box) {
		return kdnVersionDAO.getVersionList(box);
	}
	
	/**
	 * <설명> [모바일 버전 관리] 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public int getVersionListTotCnt(Box box) {
		return kdnVersionDAO.getVersionListTotCnt(box);
	}
	
	/**
	 * <설명> [모바일 버전 관리] 삭제
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public void versionDelete(Box box) {
		kdnVersionDAO.versionDelete(box);
		
	}
	
	/**
	 * <설명> [모바일 버전 관리] 등록, 수정화면
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public Box getVersionView(Box box) {
		return kdnVersionDAO.getVersionView(box);
	}
	
	/**
	 * <설명> [모바일 버전 관리] 등록
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public void versionInsert(Box box) {
		kdnVersionDAO.versionInsert(box) ;
	}
	
	/**
	 * <설명> [모바일 버전 관리] 수정
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public void versionUpdate(Box box) {
		kdnVersionDAO.versionUpdate(box) ;
		
	}
	
	/**
	 * <설명> [최신 모바일 버전] 
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public Box getVersionLastVersion(Box box) {
		return kdnVersionDAO.getVersionLastVersion(box) ;
	}
	
}
