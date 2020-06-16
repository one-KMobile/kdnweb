package kdn.version.service.impl;

import java.util.List;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;

@Repository("kdnVersionDAO")
public class KdnVersionDAO extends KdnSqlMapClient {
	
	
	/**
	 * <설명> [모바일 버전 관리] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getVersionList(Box box) {
		return list("Version.getVersionList" , box);
		
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
	public int getVersionListTotCnt(Box box) {
		return (Integer) selectByPk("Version.getVersionListTotCnt", box);
	}
	
	/**
	 * <설명> [모바일 버전 관리] 삭제
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void versionDelete(Box box) {
		delete("Version.versionDelete", box) ;
		
	}
	
	/**
	 * <설명> [모바일 버전 관리] 등록, 수정
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getVersionView(Box box) {
		return (Box) selectByPk("Version.getVersionView", box) ;
		
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
	public void versionInsert(Box box) {
		insert("Verstion.versionInsert", box) ;
		
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
	public void versionUpdate(Box box) {
		update("Version.versionUpdate", box);
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
	public Box getVersionLastVersion(Box box) {
		return (Box) selectByPk("Version.getVersionLastVersion", box);
	}
	

}
