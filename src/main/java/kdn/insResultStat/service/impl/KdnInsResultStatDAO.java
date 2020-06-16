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
package kdn.insResultStat.service.impl;

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

@Repository("kdnInsResultStatDAO")
public class KdnInsResultStatDAO extends KdnSqlMapClient {
	
	/**
	 * <설명> [순시결과 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getInsResultList(Box box) {
		return list("InsResultStat.getInsResultStatList", box) ;
	}
	
	/**
	 * <설명> [순시결과 통계] 카운터 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getInsResultStatListTotCnt(Box box) {
		return (Integer) selectByPk("InsResultStat.getInsResultStatListTotCnt" ,  box);
	}
	
	
}
