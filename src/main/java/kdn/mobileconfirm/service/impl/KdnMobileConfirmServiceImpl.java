package kdn.mobileconfirm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kdn.cmm.box.Box;
import kdn.mobileconfirm.service.KdnMobileConfirmService;


@Service("kdnMobileConfirmService")
public class KdnMobileConfirmServiceImpl implements KdnMobileConfirmService{
	
	@Resource(name="kdnMobileConfirmDAO")
	private KdnMobileConfirmDAO kdnMobileConfirmDAO ;
	
	/**
	 * <설명> [모바일 목록] 목록 저장
	 * 
	 * @param [box]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public void saveMobile(Box box) {
		kdnMobileConfirmDAO.saveMobile(box) ;
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
	@Override
	public Box getMobileExist(Box box) {
		// TODO Auto-generated method stub
		return kdnMobileConfirmDAO.getMobileExist(box);
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
	@Override
	public List<Box> getMobileList(Box box) {
		return kdnMobileConfirmDAO.getMobileList(box);
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
	@Override
	public int getMobileListTotCnt(Box box) {
		return kdnMobileConfirmDAO.getMobileListTotCnt(box);
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
	@Override
	public void setMobileCheckYN(Box box) {
		kdnMobileConfirmDAO.setMobileCheckYN(box);
		
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
	@Override
	public void setAllConfirm(Box box) {
		kdnMobileConfirmDAO.setAllConfirm(box);
	}
	
	
	
}
