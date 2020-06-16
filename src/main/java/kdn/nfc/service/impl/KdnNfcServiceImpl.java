/**
 * 
 */
/**
 * @author netted
 *
 */
package kdn.nfc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.nfc.service.KdnNfcService;

import org.springframework.stereotype.Service;


@Service("kdnNfcService")
public class KdnNfcServiceImpl implements KdnNfcService {
	@Resource(name="kdnNfcDAO")
	private KdnNfcDAO kdnNfcDAO;
	
    /**
 	 * NFC 목록
 	 * @param box - [fst_bizplc_nm
 	  						, scd_bizplc_nm
 	  						, fnct_lc_dtls
 	  						, eqp_nm]
 	 * @return list - [nfc_uid
							, fnct_lc_dtls
							, eqp_nm
							, remarks
							, tag
							, status
							, reg_date]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public List<Box> getNfcList(Box box) throws Exception {
		return kdnNfcDAO.getNfcList(box);
	}
	
    /**
 	 * NFC 총튜플수
 	 * @param box - [fst_bizplc_nm
 	  						, scd_bizplc_nm
 	  						, fnct_lc_dtls
 	  						, eqp_nm]
 	 * @return int - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public int getNfcTotalCount(Box box) throws Exception {
		return kdnNfcDAO.getNfcTotalCount(box);
	}
	
    /**
 	 * NFC 정보
 	 * @param box - [nfc_uid]
 	 * @return box - [nfc_uid
							, fst_bizplc_nm
							, scd_bizplc_nm
					        , fnct_lc_dtls
					        , eqp_nm
					        , remarks
					        , tag
					        , status]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getNfcInfo(Box box) throws Exception {
		return kdnNfcDAO.getNfcInfo(box);
	}
	
    /**
 	 * NFC 생성
 	 * @param box - [nfc_uid
							, tag
							, fnct_lc_dtls
							, fst_bizplc_nm
							, scd_bizplc_nm
							, eqp_nm
							, remarks]
 	 * @return int - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Object setNfcInsert(Box box) throws Exception {
		return kdnNfcDAO.setNfcInsert(box);
	}
	
    /**
 	 * NFC 이력 생성
 	 * @param box - [nfc_uid
							, tag
							, remarks
							, status]
 	 * @return int - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Object setNfcRecordInsert(Box box) throws Exception {
		return kdnNfcDAO.setNfcRecordInsert(box);
	}
	
    /**
 	 * NFC 수정
 	 * @param box - [fnct_lc_dtls
							, fst_bizplc_nm
							, scd_bizplc_nm
							, eqp_nm
							, nfc_uid
							, remarks]
 	 * @return int - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public int setNfcUpdate(Box box) throws Exception {
		return kdnNfcDAO.setNfcUpdate(box);
	}
	
    /**
 	 * NFC 이력 수정
 	 * @param box - [status
							, remarks
							, nfc_uid]
 	 * @return int - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public int setNfcRecordUpdate(Box box) throws Exception {
		return kdnNfcDAO.setNfcRecordUpdate(box);
	}
	
    /**
 	 * 선택된 1차사업소, 2차사업소명 호출
 	 * @param box - [fst_bizplc_cd, scd_bizplc_cd]
 	 * @return box - [fst_bizplc_nm, scd_bizplc_nm]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getSelectedBizplc(Box box) throws Exception {
		return kdnNfcDAO.getSelectedBizplc(box);
	}
	
    /**
 	 * 선택된 선로명 호출
 	 * @param box - [fnct_lc_no]
 	 * @return box - [fnct_lc_dtls]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getSelectedTracks(Box box) throws Exception {
		return kdnNfcDAO.getSelectedTracks(box);
	}
	
    /**
 	 * 지지물 리스트
 	 * @param box - [fnct_lc_no]
 	 * @return box - [eqp_nm, tower_idx]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public List<Box> getTowerList(Box box) throws Exception {
		return kdnNfcDAO.getTowerList(box);
	}
	
    /**
 	 * 선택된 지지물
 	 * @param box - [eqp_no]
 	 * @return box - [eqp_nm, eqp_no]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getSelectedTower(Box box) throws Exception {
		return kdnNfcDAO.getSelectedTower(box);
	}
	
	public List<Box> getNfcRecordList(Box box) throws Exception {
		return kdnNfcDAO.getNfcRecordList(box);
	}
	
    /**
 	 * NFC 일괄 생성
 	 * @param box - [nfc_idx
							, tag
							, fnct_lc_no
							, fnct_lc_dtls
							, eqp_no
							, eqp_nm
							, reg_date]
 	 * @return Object - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Object setBatchNfcInsert(Box box) throws Exception {
		return kdnNfcDAO.setBatchNfcInsert(box);
	}
	
    /**
 	 * NFC 이력 일괄 생성
 	 * @param box - [nfc_record_idx
							, tag, status
							, reg_date]
 	 * @return Object - []
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Object setBatchNfcRecordInsert(Box box) throws Exception {
		return kdnNfcDAO.setBatchNfcRecordInsert(box);
	}
	
	public List<Box> getBatchNfcList(Box box) throws Exception {
		return kdnNfcDAO.getBatchNfcList(box);
	}
	
    /**
 	 * NFC 선로명 지지물 갯수 출력
 	 * @return list - [cnt, fnct_lc_no]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public int getTrackCnt(Box box) throws Exception {
		return kdnNfcDAO.getTrackCnt(box);
	}
	
    /**
 	 * 지지물 선로리스트
 	 * @return list - [fnct_lc_no]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public List<Box> getTracksList(Box box) throws Exception {
		return kdnNfcDAO.getTracksList(box);
	}
	
    /**
 	 * nfc 부분등록
 	 * @return list - [fnct_lc_no, fnct_lc_dtls, eqp_no, eqp_nm]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public List<Box> getSubBatch(Box box) throws Exception {
		return kdnNfcDAO.getSubBatch(box);
	}
	
    /**
 	 * nfc 총개수 카운터
 	 * @return list -
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public int getMaxTag(Box box) throws Exception {
		return kdnNfcDAO.getMaxTag(box);
	}
	
    /**
 	 * nfc 데이터 존재여부 체크
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public int isExistNfc(Box box) throws Exception {
		return kdnNfcDAO.isExistNfc(box);
	}
}