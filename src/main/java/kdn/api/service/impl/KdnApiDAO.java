/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kdn.api.service.impl;

import java.util.List;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;



/**  
 * @Class Name : SampleDAO.java
 * @Description : Sample DAO Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 * 
 *  Copyright (C) by MOPAS All right reserved.
 */

@Repository("kdnApiDAO")
public class KdnApiDAO extends KdnSqlMapClient {
	    
    /**
	 * <설명> 
	 * 공지사항 리스트
	 * @param [box] 
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getApiNoticeList(Box box) throws Exception {
    	return list("KdnApiDAO.getApiNoticeList", box); 
    }
	
	/**
	 * <설명> 공지사항 상세 정보
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getApiNoticeView(Box box) throws Exception {
		return (Box) selectByPk("KdnApiDAO.getApiNoticeView", box);
	}
	
	/**
	 * <설명> 
	 * 코드 정보 리스트
	 * @param [box] 
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getApiCodeInfoList(Box box) throws Exception {
    	return list("KdnApiDAO.getApiCodeInfoList", box); 
    }

    /**
	 * 순시결과정보
	 * @param - box - [fst_bizplc_code, scd_bizplc_code, tracks_name, ins_date] 
	 * @return box - [순시결과정보]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    @SuppressWarnings("unchecked")
	public List<Box> getApiSearchInspectionResult(Box box) throws Exception {
    	List<Box> list =  list("inspectionResult.getApiSearchInspectionResult", box);
    	System.out.println("dao단 : " + list);
    	return list;
    }
    
    /**
	 * 사업소코드 호출
	 * @param - box 
	 * @return box - [사업소코드 정보]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Box getBizcode(Box box) throws Exception {
    	Box info = (Box)selectByPk("schedule.getBizcode", box);
    	return info;
    }
    
    /**
	 * 순시 선로 목록 확인 (원래는 9140 / 020 인데 데이터가 없어서 9110 / 010 으로 작업)
	 * @param - box - [fnct_lc_ty_nm, fst_bizplc_cd, sch_bizplc_cd]
	 * @return list - [사업소코드 정보]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    @SuppressWarnings("unchecked")
	public List<Box> getTracksList(Box box) throws Exception {
    	List<Box> list = list("schedule.getTracksList", box);
    	return list;
    }
    
    /**
	 * 선로의 스케줄 가져오기
	 * @param - box - [CYCLE_YM, INS_TY_CD]
	 * @return list - [순시목록 스케쥴]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */       
    @SuppressWarnings("unchecked")
	public List<Box> getTracksInSchedule(Box box) throws Exception {
    	List<Box> list = list("schedule.getTracksInSchedule", box);
    	return list;
    }
    
    /**
	 * 회선 테이블 검색(전선접속개소, 접지저항)
	 * @param [fst_bizplc_cd, scd_bizplc_cd]
	 * @return list - [sub_schedule_idx, fnct_lc_no, fnct_lc_dtls, position]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    @SuppressWarnings("unchecked")
	public List<Box> getCircuitList(Box box) throws Exception {
    	List<Box> list = list("schedule.getCircuitList", box);
    	return list;
    }
    
    /**
	 * 회선 테이블 검색(전선접속개소, 접지저항)
	 * @param [fst_bizplc_cd, scd_bizplc_cd]
	 * @return list - [sub_schedule_idx, fnct_lc_no, fnct_lc_dtls, position]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    @SuppressWarnings("unchecked")
	public List<Box> getPoorInsulators(Box box) throws Exception {
    	return list("schedule.getPoorInsulators", box);
    }
    
    /**
	 * 스케쥴 최신계약번호
	 * @param box - [user_id]
	 * @return box - [ins_plan_no]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public Box getLatestContract(Box box) throws Exception {
		return (Box)selectByPk("schedule.getLatestContract", box);
	}
    /**
	 * 선택된 선로정보 호출
	 * @param - box - [fnct_lc_no]
	 * @return box - [순시목록 스케쥴]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Box getSelectedTracks(Box box) throws Exception {
    	Box info = (Box)selectByPk("schedule.getSelectedTracks", box);
    	return info;
    }
    
    /**
	 * 지지물 리스트 출력
	 * @return list - [trans_tower_idx, trans_tower_type_code]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */    
    @SuppressWarnings("unchecked")
	public List<Box> getTransTowerList(Box box) throws Exception {
    	return list("getTransTowerList", box);
    }
    
    /**
	 * 지지물 검색
	 * @param box - [fnct_lc_no, eqp_nm]
	 * @return list - [fnct_lc_dlts, eqp_nm, eqp_ty_cd_nm]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */      
    @SuppressWarnings("unchecked")
	public List<Box> getSearchedTransTower(Box box) throws Exception {
    	return list("getSearchedTransTower", box);
    }
    
    /**
	 * 지지물 인덱스 검색
	 * @param box - [fnct_lc_no]
	 * @return list - [eqp_no]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    @SuppressWarnings("unchecked")
	public List<Box> getTowerIdx(Box box) throws Exception {
    	return list("getTowerIdx", box);
    }
    
    /**
	 * 지지물의 위도경도 업데이트
	 * @param box - [latitude, longitude, eqp_no]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public int setLatitudeWithLongitudeUpdate(Box box) throws Exception {
    	return update("setLatitudeWithLongitudeUpdate", box);
    }
    
    /**
	 * 트레킹 등록
	 * @param box - [user_id, latitude, longitude, remarks]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Object setTrakingInsert(Box box) throws Exception {
    	return (Object) insert("traking.setTrakingInsert", box);
    }
    
    /**
	 * 트레킹 수정
	 * @param box - [user_id, latitude, longitude, remarks]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public int setTrakingUpdate(Box box) throws Exception {
    	return (Integer) update("traking.setTrakingUpdate", box);
    }
    
    /**
	 * 트레킹 계정 검색
	 * @param box - [user_id]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box getTrakingUserInfo(Box box) throws Exception {
    	return (Box) selectByPk("traking.getTrakingUserInfo", box);
    }
    
    /**
	 * NFC 태그 정보
	 * @param box - [eqp_no]
	 * @return box - [tag] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box getTagInfo(Box box) throws Exception {
    	return (Box) selectByPk("nfc.getTagInfo", box);
    }
    
    /**
	 * 부순시자 검색
	 * @param box - [fst_bizplc_cd, scd_bizplc_cd, user_id]
	 * @return list - [user_id, user_name] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    @SuppressWarnings("unchecked")
	public List<Box> getSubPatrolmanList(Box box) throws Exception {
    	return list("inspectionResult.getSubPatrolmanList", box);
    }
    
    /**
	 * 순시결과 마스터 입력
	 * @param box - [master_idx
							, schedule_id
							, fnct_lc_no
							, eqp_no
							, cycle_ym
							, ins_type_code
							, work_type
							, nfc_id
							, ins_date
							, check_ins_name_a
							, check_ins_name_b
							, report_date
							, weather_code
							, check_ins_co_count
							, remarks
							, latitude
							, longitude
							, tag_yn
							, reg_date
							, reg_id]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Object setMasterInsert(Box box) throws Exception {
    	return insert("inspectionResult.setMasterInsert", box);
    }
    
    /**
	 * 순시결과 마스터 수정
	 * @param box - [master_idx
							, ins_date]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    public int setMasterUpdate(Box box) throws Exception {
    	return update("inspectionResult.setMasterUpdate", box);
    }
    
    /**
	 * 순시결과 마스터키가 존재하냐?
	 * @param box - [schedule_id, eqp_no, ins_type_code]
	 * @return box - [master_idx
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Box isExistMaster(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.isExistMaster", box);
    }
    
    /**
	 * 순시결과 마스터인덱스 검색
	 * @param box - [schedule_id]
	 * @return box - [master_idx] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */         
    public Box getMasterIdx(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.getMasterIdx", box);
    }
    
    /**
	 * 순시결과 보통순시 입력
	 * @param box - [result_idx
							, master_idx
							, tins_result_secd
							, examin_cn]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Object setNormalInspectionInsert(Box box) throws Exception {
    	return insert("inspectionResult.setNormalInspectionInsert", box);
    }

    /**
	 * 순시결과 보통순시 수정
	 * @param box - [ master_idx
							, tins_result_secd
							, examin_cn]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public int setNormalInspectionUpdate(Box box) throws Exception {
    	return update("inspectionResult.setNormalInspectionUpdate", box);
    }
    
    /**
	 * 순시결과 점퍼접속개소 입력
	 * @param box - [result_idx
							, master_idx
							, out_temp
							, circuit_code
							, measure_load]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */      
    public Object setConnectionPointJumper(Box box) throws Exception {
    	return insert("inspectionResult.setConnectionPointJumper", box);
    }
    
    /**
	 * 순시결과 점퍼접속개소 수정
	 * @param box - [group_file_id]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */       
    public int setConnectionPointJumperUpdate(Box box) throws Exception {
    	return update("inspectionResult.setConnectionPointJumperUpdate", box);
    }
    
    /**
	 * 순시결과 점퍼접속개소 인덱스 검색
	 * @param box - [master_idx]
	 * @return box - [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public Box getConnectionPointJumperIdx(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.getConnectionPointJumperIdx", box);
    }
    
    /**
	 * 순시결과 점퍼접속개소 설비번호 중복체크
	 * @param box - [master_idx, pwln_eqp_no, cl_no]
	 * @return box - [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box isExistPwlneqpnoJumper(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.isExistPwlneqpnoJumper", box);
    }
    
    /**
	 * 순시결과 점퍼접속개소 중복체크
	 * @param box - [result_idx, cl_no, pwln_eqp_no, cndctr_sn]
	 * @return result_bbbb_idx
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box isExistBBBB(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.isExistBBBB", box);
    }
    
    
    /**
	 * 순시결과 점퍼접속개소 측정 입력
	 * @param box - [result_idx
							, position_grp
							, position_dt
							, c1_temper_w
							, c1_temper_c
							, c1_temp_cha
							, c2_temper_w
							, c2_temper_c
							, c2_temp_cha
							, c3_temper_w
							, c3_temper_c
							, c3_temp_cha]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public Object setMeasuringConnectionPointJumper(Box box) throws Exception {
    	return insert("inspectionResult.setMeasuringConnectionPointJumper", box);
    }
    
    /**
	 * 순시결과 점퍼접속개소 측정 수정
	 * @param box - [result_idx
							, cabl_tp
							, cnpt_tp
							, good_secd]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public int setMeasuringConnectionPointJumperUpdate(Box box) throws Exception {
    	return update("inspectionResult.setMeasuringConnectionPointJumperUpdate", box);
    }
    
    /**
	 * 순시결과 전선접속개소 입력
	 * @param box - [result_idx
							, master_idx
							, out_temp
							, circuit_code
							, measure_load]
	 * @return int 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */        
    public Object setConnectionPointWire(Box box) throws Exception {
    	return insert("inspectionResult.setConnectionPointWire", box);
    }
    
    /**
	 * 순시결과 전선접속개소 수정
	 * @param box - [group_file_id]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */       
    public int setConnectionPointWireUpdate(Box box) throws Exception {
    	return update("inspectionResult.setConnectionPointWireUpdate", box);
    }
    
    /**
	 * 순시결과 전선접속개소 인덱스 검색
	 * @param box - [master_idx]
	 * @return box - [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public Box getConnectionPointWireIdx(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.getConnectionPointWireIdx", box);
    }
    
    /**
	 * 순시결과 전선접속개소 설비번호 중복체크
	 * @param box - [master_idx, pwln_eqp_no, cl_no]
	 * @return box - [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Box isExistPwlneqpno(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.isExistPwlneqpno", box);
    }
    
    /**
	 * 순시결과 전선접속개소 입력
	 * @param box - [result_idx
							, position_grp
							, position_dt
							, c1_temper_w
							, c1_temper_c
							, c1_temp_cha
							, c2_temper_w
							, c2_temper_c
							, c2_temp_cha
							, c3_temper_w
							, c3_temper_c
							, c3_temp_cha]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Object setMeasuringConnectionPointWire(Box box) throws Exception {
    	return insert("inspectionResult.setMeasuringConnectionPointWire", box);
    }
    
    /**
	 * 순시결과 전선접속개소 중복체크
	 * @param box - [result_idx, cl_no, pwln_eqp_no, cndctr_sn]
	 * @return result_cccc_idx
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box isExistCCCC(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.isExistCCCC", box);
    }
    
    /**
	 * 순시결과 전선접속개소 측정 수정
	 * @param box - [result_idx
							, cabl_tp
							, cnpt_tp
							, good_secd]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public int setMeasuringConnectionPointWireUpdate(Box box) throws Exception {
    	return update("inspectionResult.setMeasuringConnectionPointWireUpdate", box);
    }
    
    /**
	 * 항공장애점등점검 등록
	 * @param - box - [result_idx, check_start_time ,check_end_time ,light_type ,light_class ,control_count ,light_count ,light_state_top ,light_state_middle ,light_state_bottom] 
	 * @return Object - [항공쟁애점등점검 등록]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Object setCheckAirFailureLight(Box box) throws Exception {
    	Object insert = insert("inspectionResult.setCheckAirFailureLight", box);
    	return insert;
    }
    
    /**
	 * 항공장애점등점검 중복체크
	 * @param box - [flight_lmp_knd, flight_lmp_no, srcelct_knd]
	 * @return box - [result_idx]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Box isExistDD(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.isExistDD", box);
    }
    
    /**
	 * 항공장애점등점검 수정
	 * @param - box - [master_idx, flck_co, lightg_stadiv_cd, good_secd, group_file_id, imprmn_request_no] 
	 * @return int - [항공쟁애점등점검 등록]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */         
    public int setCheckAirFailureLightUpdate(Box box) throws Exception {
    	return update("inspectionResult.setCheckAirFailureLightUpdate", box);
    }
    
    /**
	 * 순시결과 항공장애등 점검 입력
	 * @param box - [result_idx
							, master_idx
							, flight_lmp_knd
							, flight_lmp_no
							, srcelct_knd
							, ctrl_ban_gdbd_secd
							, slrcl_gdbd_secd
							, srgbtry_gdbd_secd
							, rgist_gu_gdbd_secd
							, cabl_gdbd_secd
							, good_secd
							, imprmn_request_no
							, group_file_id]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */   
    public Object setCheckAirFailure(Box box) throws Exception {
    	return insert("inspectionResult.setCheckAirFailure", box);
    }
    
    /**
	 * 항공장애 스케줄
	 * @param box - [fst_bizplc_cd, scd_bizplc_cd]
	 * @return box - [eqp_no
							, tower_idx
							, flight_lmp_knd
							, flight_lmp_knd_nm
							, srcelct_knd
							, srcelct_knd_nm
							, flight_lmp_no
							, srgbtry_gdbd_secd]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    @SuppressWarnings("unchecked")
	public List<Box> getErrorAir(Box box) throws Exception {
    	return list("schedule.getErrorAir", box);
    }
    
    /**
	 * 항공장애 중복체크
	 * @param box - [flight_lmp_knd, flight_lmp_no, srcelct_knd]
	 * @return box - [result_idx]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box isExistHH(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.isExistHH", box);
    }
    /**
	 * 순시결과 항공장애등 점검 수정
	 * @param box - [ master_idx
							, flight_lmp_knd
							, flight_lmp_no
							, srcelct_knd
							, ctrl_ban_gdbd_secd
							, slrcl_gdbd_secd
							, srgbtry_gdbd_secd
							, rgist_gu_gdbd_secd
							, cabl_gdbd_secd
							, good_secd
							, imprmn_request_no
							, group_file_id]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */   
    public int setCheckAirFailureUpdate(Box box) throws Exception {
    	return update("inspectionResult.setCheckAirFailureUpdate", box);
    }
    
    /**
	 * 순시결과 접지저항 입력
	 * @param box - [master_idx, msr_co, good_secd, pmttr, group_file_id, imprmn_request_no, stdr_rs]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Object setGroundResistanceMeasurements(Box box) throws Exception {
    	return insert("inspectionResult.setGroundResistanceMeasurements", box);
    }
    
    /**
	 * 순시결과 접지저항 수정
	 * @param box - [master_idx, msr_co, good_secd, pmttr, group_file_id, imprmn_request_no]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public int setGroundResistanceMeasurementsUpdate(Box box)throws Exception {
    	return update("inspectionResult.setGroundResistanceMeasurementsUpdate", box);
    }
    
    /**
	 * 순시결과 접지저항 인덱스 호출
	 * @param box - [master_idx]
	 * @return box [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box getGroundResistanceMeasurementsIdx(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.getGroundResistanceMeasurementsIdx", box);
    }
    
    /**
 	 * 순시결과 접지저항 측정 입력
 	 * @param box - [result_idx
							, msr_co
							, msr_odr
							, msr_rs]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */ 
    public Object setSubGroundResistanceMeasurements(Box box) throws Exception {
    	return insert("inspectionResult.setSubGroundResistanceMeasurements", box);
    }
    
    /**
 	 * 순시결과 접지저항 측정 수정
 	 * @param box - [result_idx
							, msr_co
							, msr_odr
							, msr_rs]
 	 * @return int
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
 	 * @author [정현도] 
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */ 
    public int setSubGroundResistanceMeasurementsUpdate(Box box) throws Exception {
    	return update("inspectionResult.setSubGroundResistanceMeasurementsUpdate", box);
    }
    /**
	 * 순시결과 저항기준값 호출
	 * @param box - [fnct_lc_no]
	 * @return box [resistance_value]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box getResistanceStandards(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.getResistanceStandards", box);
    }
    
    /**
	 * 순시결과 불량애자검출 입력
	 * @param box - [result_idx
							, master_idx
							, cl_no
							, ty_secd
							, phs_secd
							, insbty_lft
							, insbty_rit
							, insr_qy
							, bad_insr_qy
							, good_secd
							, ym
							, biz
							, unity_ins_no
							, insr_eqp_no]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Object setAgilePoorDetection(Box box) throws Exception {
    	return insert("inspectionResult.setAgilePoorDetection", box);
    }
    
    /**
	 * 순시결과 불량애자검출 입력
	 * @param box - [master_idx, cl_no, ty_secd, phs_secd]
	 * @return [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public Box isExistFF(Box box) throws Exception {
    	return (Box) selectByPk("inspectionResult.isExistFF", box);
    }
    
    /**
	 * 순시결과 불량애자검출 수정
	 * @param box - [master_idx
							, cl_no
							, ty_secd
							, phs_secd
							, insbty_lft
							, insbty_rit
							, insr_qy
							, bad_insr_qy
							, good_secd]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public int setAgilePoorDetectionUpdate(Box box) throws Exception {
    	return update("inspectionResult.setAgilePoorDetectionUpdate", box);
    }
    
    /**
	 * 순시결과 기별점검및정밀점검 입력
	 * @param box - [result_idx
							, master_idx
							, check_start_time
							, check_end_time
							, inspect_type
							, inspect_item_1
							, inspect_item_2
							, inspect_item_3
							, inspect_item_4
							, inspect_item_5
							, inspect_item_6
							, inspect_item_7
							, inspect_item_8
							, inspect_item_9
							, inspect_item_10
							, inspect_item_11
							, inspect_item_12
							, inspect_item_13
							, inspect_item_14
							, inspect_item_15
							, inspect_item_16
							, inspect_item_17
							, inspect_item_18
							, inspect_item_19
							, detail_inspect_item_1
							, detail_inspect_item_2
							, detail_inspect_item_3
							, detail_inspect_item_4
							, detail_inspect_item_5
							, detail_inspect_item_6]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    public Object setMessageAndThoroughInspection(Box box) throws Exception {
    	return insert("inspectionResult.setMessageAndThoroughInspection", box);
    }
    
    /**
	 * 순시결과 서브기별점검및정밀점검 입력
	 * @param box - [result_idx
							, master_idx
							, eqp_no
							, ins_itm_cd
							, good_secd
							, imprmn_request_no]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */    
    public Object setSubMessageAndThoroughInspection(Box box) throws Exception {
    	return insert("inspectionResult.setSubMessageAndThoroughInspection", box);
    }
    
    /**
	 * 순시결과 서브기별점검및정밀점검 수정
	 * @param box - [result_idx
							, master_idx
							, ins_itm_cd
							, good_secd
							, imprmn_request_no]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public int setSubMessageAndThoroughInspectionUpdate(Box box) throws Exception {
    	return update("inspectionResult.setSubMessageAndThoroughInspectionUpdate", box);
    }
    /**
	 * 순시결과 기별점검 인덱스 검색
	 * @param box - [master_idx]
	 * @return box - [result_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    public Box getMessageAndThoroughInspectionIdx(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.getMessageAndThoroughInspectionIdx", box);
    }

    /**
	 * 순시결과 예방순시 리스트
	 * @param box - [fst_bizplc_cd
							, scd_bizplc_cd
							, fnct_lc_no]
	 * @return list [OCT.RGT_SN
						, OCT.BEGIN_EQP_NO
						, OCT.END_EQP_NO
						, BEGIN_EQP_NM
						, END_EQP_NM
						, OCT.CWRK_NM 
						, OPTR.TINS_RESULT_SECD 
						, OPTR.TINS_RESULT]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    @SuppressWarnings("unchecked")
	public List<Box> getPreventingInspection(Box box) throws Exception {
    	return list("inspectionResult.getPreventingInspection", box);
    }
    
    /**
	 * 순시결과 예방순시 입력
	 * @param box - [result_idx
	 						, rgt_sn
							, before_eqp_no
							, after_eqp_no
							, cwrk_spt_nm
							, tins_result_secd
							, tins_result
							, group_file_id
							, imprmn_request_no
							, gubun
							, tid]
	 * @return list []
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Object setPreventingInspection(Box box) throws Exception {
    	return insert("inspectionResult.setPreventingInspection", box);
    }
    
    /**
	 * 순시결과 예방순시 수정
	 * @param box - [result_idx
							, rgt_sn
							, tins_result_secd
							, tins_result]
	 * @return list []
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public int setPreventingInspectionUpdate(Box box) throws Exception {
    	return update("inspectionResult.setPreventingInspectionUpdate", box);
    }
    
    /**
	 * 순시결과 예방순시 rgt_sn 존재여부 확인
	 * @param box - [rgt_sn]
	 * @return list []
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public Box getRgtsnIsExist(Box box) throws Exception {
    	return (Box)selectByPk("inspectionResult.getRgtsnIsExist", box);
    }
  
    /**
	 * 위도 경도가 is not null인 데이터만 호출
	 * @param box - []
	 * @return list [latitude, longitude]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */     
    @SuppressWarnings("unchecked")
	public List<Box> getLatitude(Box box) throws Exception {
    	return list("eqpDAO.getLatitude", box);
    }
    
    /**
	 * 주소 입력
	 * @param box - [address, eqp_no]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
    public int setAddress(Box box) throws Exception {
    	return update("eqpDAO.setAddress", box);
    }
    
    /**
	 * < 지중순시 모바일 시스템 > 정기순시/예방순시 - 유압리스트 추출
	 * @param [fst_bizplc_cd, scd_bizplc_cd]
	 * @return list - [sub_schedule_idx, fnct_lc_no, fnct_lc_dtls, position]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    @SuppressWarnings("unchecked")
	public List<Box> getOileqpList(Box box) throws Exception {
    	List<Box> list = list("schedule.getOileqpList", box);
    	return list;
    }
    
    /**
	 * < 지중순시 모바일 시스템 > 설비검색
	 * @param [fst_bizplc_cd, scd_bizplc_cd]
	 * @return list - [sub_schedule_idx, fnct_lc_no, fnct_lc_dtls, position]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */  
    @SuppressWarnings("unchecked")
	public List<Box> apiSearchFacilityList(Box box) throws Exception {
    	List<Box> list = list("schedule.getFacilityList", box);
    	return list;
    }
}

