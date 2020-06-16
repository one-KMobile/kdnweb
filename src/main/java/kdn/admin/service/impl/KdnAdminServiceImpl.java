package kdn.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.admin.service.KdnAdminService;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("kdnAdminService")
public class KdnAdminServiceImpl implements KdnAdminService{
	@Resource(name = "kdnAdminDAO")
	private KdnAdminDAO kdnAdminDAO;
	
    /**
 	 * 순시자 리스트
 	 * @return list - [user_name
							, user_id
							, user_pwd
							, user_auth
							, user_email
							, mac_address
							, user_hp
							, comp_type]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public List<Box> getAdminList(Box box) throws Exception {
		return kdnAdminDAO.getAdminList(box);
	}
	
    /**
 	 * 순시자 정보
 	 * @param box - [user_id
 	 * 						, user_pwd]
 	 * @return box - [user_name
							, user_id
							, user_pwd
							, user_auth
							, user_email
							, mac_address
							, user_hp
							, comp_type]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getAdminInfo(Box box) throws Exception {
		return kdnAdminDAO.getAdminInfo(box);
	}
	
    /**
 	 * 순시자 수정
 	 * @param box - [new_user_pwd
 	 * 						,user_pwd
							, mac_address
							, user_hp
							, user_email
							, user_auth]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public int setAdminUpdate(Box box) throws Exception {
		return kdnAdminDAO.setAdminUpdate(box);
	}
	
    /**
 	 * 순시자 총튜플 수
 	 * @param box - [user_id
 	  						, user_name
 	  						, user_email]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public int getAdminTotalCount(Box box) throws Exception {
		return kdnAdminDAO.getAdminTotalCount(box);
	}
	
    /**
 	 * 순시자 등록
 	 * @param box - [user_idx
							, fst_bizplc_cd
							, scd_bizplc_cd
							, user_name
							, user_id
							, user_pwd
							, user_tel
							, user_hp
							, user_email
							, user_auth
							, comp_type
							, conf_state
							, reg_date
							, use_yn]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public Object setAdminInsert(Box box) throws Exception {
		return kdnAdminDAO.setAdminInsert(box);
	}
    /**
 	 * 순시자 삭제
 	 * @param box - [user_id]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public int setAdminDelete(Box box) throws Exception {
		return kdnAdminDAO.setAdminDelete(box);
	}
	
    /**
 	 * 사업소명 검색
 	 * @param box - [fst_bizplc_cd, scd_bizplc_cd]
 	 * @return box - [fst_bizplc_cd_nm, scd_bizplc_cd_nm]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	public Box getBizplcNameInfo(Box box) throws Exception {
		return kdnAdminDAO.getBizplcNameInfo(box);
	}
	 /**
 	 * 디바이스 가진 순시자 검색
 	 * @param box - [user_id]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	@Override
	public List<Box> getAdminIsNotTokenList(Box box) {
		return kdnAdminDAO.getAdminIsNotTokenList(box);
	}
	
	/**
 	 * 디바이스 삭제
 	 * @param box - [user_id]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	@Override
	public int setDeviceDel(Box box) {
		return kdnAdminDAO.setDeviceDel(box);
	}

	/**
 	 * 순시자 디바이스 검색
 	 * @param box - [user_id]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	@Override
	public Box getUserToken(Box box) {
		return kdnAdminDAO.getUserToken(box); 
	}
	
	/**
 	 * 사업소에 해당하는 순시자 디바이스 검색
 	 * @param box - [fst_bizplc_cd , scd_bizplc_cd]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [신명섭] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	@Override
	public List<Box> getUserTokenList(Box box) {
		return kdnAdminDAO.getUserTokenList(box);
	}
	
	/**
 	 * 순시자 아이디가 존재하냐?
 	 * @param box - [user_id]
 	 * @return box - [user_id]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */	
	public Box isExistToUserid(Box box) throws Exception {
		return kdnAdminDAO.isExistToUserid(box);
	}
}
