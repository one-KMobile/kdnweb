package kdn.mobileconfirm.service;

import java.util.List;

import kdn.cmm.box.Box;

public interface KdnMobileConfirmService {
	
	/**
	 * <설명> [모바일 목록] 목록 저장
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void saveMobile(Box box);
	
	/**
	 * <설명> [모바일 목록] 사용자 유무 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	Box getMobileExist(Box box);
	
	/**
	 * <설명> [모바일 목록] 모바일 목록 조회
	 * 
	 * @param [box]
	 * @return [List<Box>]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getMobileList(Box box);
	
	/**
	 * <설명> [모바일 목록] 모바일 목록 카운터 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	int getMobileListTotCnt(Box box);
	
	/**
	 * <설명> [모바일 목록] 모바일 승인 세팅
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void setMobileCheckYN(Box box);
	
	/**
	 * <설명> [모바일 목록] 선택한 번호 승인 세팅
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	void setAllConfirm(Box box);

}
