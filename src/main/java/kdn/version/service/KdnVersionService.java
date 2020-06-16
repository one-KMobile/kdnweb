package kdn.version.service;

import java.util.List;
import kdn.cmm.box.Box;

public interface KdnVersionService {
	
	/**
	 * <설명> [모바일 버전 관리] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getVersionList(Box box);
	
	/**
	 * <설명> [모바일 버전 관리] 목록 카운터조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getVersionListTotCnt(Box box);
	
	/**
	 * <설명> [모바일 버전 관리] 목록 삭제
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void versionDelete(Box box);
	
	/**
	 * <설명> [모바일 버전 관리] 등록, 수정화면
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	Box getVersionView(Box box);
	
	/**
	 * <설명> [모바일 버전 관리] 등록
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void versionInsert(Box box);
	
	/**
	 * <설명> [모바일 버전 관리] 수정
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void versionUpdate(Box box);
	
	/**
	 * <설명> [최신 모바일 버전] 
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	Box getVersionLastVersion(Box box);

}
