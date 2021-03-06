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
package kdn.insResult.service.impl;

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

@Repository("kdnInsResultDAO")
public class KdnInsResultDAO extends KdnSqlMapClient {
	
	/**
	 * <설명> [순시결과] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResultList(Box box) throws Exception {
		return list("InsResult.getInsResultList", box);
	}

	/**
	 * <설명> [순시결과] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getInsResultListTotCnt(Box box) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"InsResult.getInsResultListTotCnt", box);
	}
	
	/**
	 * <설명> [순시결과] 상세 정보 조회 (상단부분)
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getInsResultTopView(Box box) throws Exception {  
		return (Box) selectByPk("InsResult.getInsResultTopView", box);
	}
	
	/**
	 * <설명> [순시결과 - 보통순시] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult001SubList(Box box) throws Exception {
		return list("InsResult.getInsResult001SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 항공장애등등구확인점검] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult025SubList(Box box) throws Exception {
		return list("InsResult.getInsResult025SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 항공장애등점검] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult024SubList(Box box) throws Exception {
		return list("InsResult.getInsResult024SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 접지저항측정점검] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult028SubList(Box box) throws Exception {
		return list("InsResult.getInsResult028SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 전선접속개소점검] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult027SubList(Box box) throws Exception {
		return list("InsResult.getInsResult027SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 점퍼접속개소점검] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult026SubList(Box box) throws Exception {
		return list("InsResult.getInsResult026SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 불량애자] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box] - schedule_id
	 * @return [List] - ty_secd
				                , phs_secd
				                , defect_tot
				                , defect_work_cnt
				                , defect_cnt
				                , ym
				                , biz
				                , tower_idx
				                , eqp_no
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult029SubList(Box box) throws Exception {
		return list("InsResult.getInsResult029SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 지지물번호 검색
	 * 
	 * @param [box] - eqp_no
	 * @return [box] - tower_idx
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */		
	public Box getTowerIdx(Box box) throws Exception {
		return (Box)selectByPk("InsResult.getTowerIdx", box);
	}
	
	/**
	 * <설명> [순시결과 - 마스터 인덱스 호출
	 * @param [box] - schedule_id
	 * @return [box] - master_idx
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */		
	@SuppressWarnings("unchecked")
	public List<Box> getMasterIdx(Box box)throws Exception {
		return list("InsResult.getMasterIdx", box);
	}
	
	//순시결과 마스터 삭제
	public int setMasterDelete(Box box) throws Exception {
		return delete("InspectionResult.setMasterDelete", box);
	}
	
	//순시결과 보통순시 삭제
	public int set001Delete(Box box) throws Exception {
		return delete("InspectionResult.set001Delete", box);
	}
	
	//순시결과 점퍼접속 삭제
	public int set026Delete(Box box) throws Exception {
		return delete("InspectionResult.set026Delete", box);
	}
	
	//순시결과 점퍼접속 측정 삭제
	public int setSub026Delete(Box box) throws Exception {
		return delete("InspectionResult.setSub026Delete", box);
	}
	
	//순시결과 전선접속 삭제
	public int set027Delete(Box box) throws Exception {
		return delete("InspectionResult.set027Delete", box);
	}
	
	//순시결과 전선접속 측정 삭제
	public int setSub027Delete(Box box) throws Exception {
		return delete("InspectionResult.setSub027Delete", box);
	}
	
	//순시결과 항공등등구 삭제
	public int set025Delete(Box box) throws Exception {
		return delete("InspectionResult.set025Delete", box);
	}
	
	//순시결과 접지저항 삭제
	public int set028Delete(Box box) throws Exception {
		return delete("InspectionResult.set028Delete", box);
	}
	
	//순시결과 접지저항 측정 삭제
	public int setSub028Delete(Box box) throws Exception {
		return delete("InspectionResult.setSub028Delete", box);
	}
	
	//순시결과 불량애자 삭제
	public int set029Delete(Box box) throws Exception {
		return delete("InspectionResult.set029Delete", box);
	}
	
	//순시결과 기별점검 측정 삭제
	public int setSub021Delete(Box box) throws Exception {
		return delete("InspectionResult.setSub021Delete", box);
	}
	
	//순시결과 장애등점검 삭제
	public int set024Delete(Box box) throws Exception {
		return delete("InspectionResult.set024Delete", box);
	}
}
