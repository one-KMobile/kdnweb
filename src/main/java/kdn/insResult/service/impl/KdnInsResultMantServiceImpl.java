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

import javax.annotation.Resource;
import kdn.cmm.box.Box;
import kdn.insResult.service.KdnInsResultMantService;
import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("kdnInsResultMantService") 
public class KdnInsResultMantServiceImpl extends AbstractServiceImpl implements   
	KdnInsResultMantService{ 
	
	/** SampleDAO */
    @Resource(name="kdnInsResultMantDAO")
    private KdnInsResultMantDAO kdnInsResultMantDAO;
    
    /** ID Generation */
    @Resource(name="egovIdGnrService")    
    private EgovIdGnrService egovIdGnrService;
    
	/**
	 * <설명> [송전설비] 설비 점검 조회
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@Override
	public List<Box> getInsResultMantView(Box box) throws Exception {
		return kdnInsResultMantDAO.getInsResultMantView(box);
	}
	
	/**
	 * <설명> [송전설비] 설비 점검 마스터
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@Override
	public List<Box> getInsResultMantMaster(Box box) {
		// TODO Auto-generated method stub
		return kdnInsResultMantDAO.getInsResultMantMaster(box);
	}
	
	/**
	 * <설명> [송전설비] 설비 점검 마스터
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [신명섭]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	@Override
	public List<Box> getInsResultGGT(Box box) {
		return kdnInsResultMantDAO.getInsResultGGT(box);
	}
}
