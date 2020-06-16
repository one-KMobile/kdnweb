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

import javax.annotation.Resource;
import kdn.cmm.box.Box;
import kdn.insResult.service.KdnInsResultMantService;
import kdn.insResultStat.service.KdnInsResultStatService;

import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("kdnInsResultStatService") 
public class KdnInsResultStatServiceImpl extends AbstractServiceImpl implements   
	KdnInsResultStatService{ 
	
	/** SampleDAO */
    @Resource(name="kdnInsResultStatDAO")
    private KdnInsResultStatDAO kdnInsResultStatDAO;
    
	/**
	 * <설명> [순시결과 통계] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public List<Box> getInsResultList(Box box) {
		return kdnInsResultStatDAO.getInsResultList(box);
	}

	/**
	 * <설명> [순시결과 통계] 카운터
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@Override
	public int getInsResultStatListTotCnt(Box box) {
		return kdnInsResultStatDAO.getInsResultStatListTotCnt(box);
	}
    
   
}
