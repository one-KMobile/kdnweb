package kdn.mobileconfirm.service.impl;

import java.util.List;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;


@Repository("kdnMobileConfirmDAO")
public class KdnMobileConfirmDAO extends KdnSqlMapClient{
	
	/**
	 * <설명> [모바일 목록] 목록 저장
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void saveMobile(Box box) {
		insert("MobileConfirm.saveMobile", box) ;
	}
	
	/**
	 * <설명> [모바일 목록] 사용자 유무 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getMobileExist(Box box) {
		return (Box) selectByPk("MobileConfirm.getMobileExist", box);
	}
	
	/**
	 * <설명> [모바일 목록] 모바일 목록 조회
	 * 
	 * @param [box]
	 * @return [List<Box>]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getMobileList(Box box) {
		return list("MobileConfirm.getMobileList", box);
	}

	/**
	 * <설명> [모바일 목록] 모바일 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getMobileListTotCnt(Box box) {
		return (Integer) selectByPk("MobileConfirm.getMobileListTotCnt", box);
	}
	
	/**
	 * <설명> [모바일 목록] 모바일 승인 세팅
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void setMobileCheckYN(Box box) {
		update("MobileConfirm.setMobileCheckYN", box) ;
		
	}

	/**
	 * <설명> [모바일 목록] 선택한 번호 승인 세팅
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void setAllConfirm(Box box) {
		update("MobileConfirm.setAllConfirm", box) ;
	}
}
