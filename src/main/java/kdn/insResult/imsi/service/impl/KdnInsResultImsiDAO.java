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
package kdn.insResult.imsi.service.impl;

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

@Repository("kdnInsResultImsiDAO")
public class KdnInsResultImsiDAO extends KdnSqlMapClient {
	
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
		return (Box) selectByPk("InsResultImsi.getInsResultTopView", box);
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
		return list("InsResultImsi.getInsResult001SubList", box);
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
		return list("InsResultImsi.getInsResult025SubList", box);
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
		return list("InsResultImsi.getInsResult024SubList", box);
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
		return list("InsResultImsi.getInsResult028SubList", box);
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
		return list("InsResultImsi.getInsResult027SubList", box);
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
		return list("InsResultImsi.getInsResult026SubList", box);
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
		return list("InsResultImsi.getInsResult029SubList", box);
	}
	
	/**
	 * <설명> [순시결과 - 기별점검] 상세 목록 조회 (하단부분)
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResult021SubList(Box box) throws Exception {
		return list("InsResultImsi.getInsResult021SubList", box);
	}
}
