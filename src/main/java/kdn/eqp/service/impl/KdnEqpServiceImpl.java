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
package kdn.eqp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.eqp.service.KdnEqpService;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**  
 * @Class Name : EgovSampleServiceImpl.java
 * @Description : Sample Business Implement Class
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

@Service("kdnEqpService") 
public class KdnEqpServiceImpl extends AbstractServiceImpl implements   
        KdnEqpService { 
	
	/** SampleDAO */
    @Resource(name="kdnEqpDAO")
    private KdnEqpDAO kdnEqpDAO;
    
    /** ID Generation */
    @Resource(name="egovIdGnrService")    
    private EgovIdGnrService egovIdGnrService;
    
	/**
	 * <설명> [송전설비] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getEqpList(Box box) throws Exception {
		return kdnEqpDAO.getEqpList(box);
	}

	/**
	 * <설명> [송전설비] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getEqpListTotCnt(Box box) {
		return kdnEqpDAO.getEqpListTotCnt(box);
	}

	/**
	 * <설명> [송전설비] 1차사업소 목록
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getFstBizplcList(Box box) throws Exception {
		return kdnEqpDAO.getFstBizplcList(box);
	}
	
	/**
	 * <설명> [송전설비] 2차사업소 목록
	 * 
	 * @param [fst_bizplc_cd]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getScdBizplcList(Box box) throws Exception {
		return kdnEqpDAO.getScdBizplcList(box);
	}
	
	/**
	 * <설명> [송전설비] 선로명 목록
	 * 
	 * @param [fst_bizplc_cd] [scd_bizplc_cd]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getTracksList(Box box) throws Exception {
		return kdnEqpDAO.getTracksList(box);
	}
	
	/**
	 * <설명> [송전설비] 2차사업소 목록
	 * 
	 * @param [fst_bizplc_cd]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getWebScdBizplcList(Box box) throws Exception {
		return kdnEqpDAO.getWebScdBizplcList(box);
	}
	
	/**
	 * <설명> [송전설비] 선로명 목록
	 * 
	 * @param [fst_bizplc_cd] [scd_bizplc_cd]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getWebTracksList(Box box) throws Exception {
		return kdnEqpDAO.getWebTracksList(box);
	}
	
	/**
	 * <설명> [송전설비] 선로명 목록
	 * 
	 * @param [eqp_no]
	 * @return list[eqp_no
					, eqp_nm
					, fst_bizplc_cd
					, scd_bizplc_cd
					, fnct_lc_no]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getEqpInfo(Box box) throws Exception {
		return kdnEqpDAO.getEqpInfo(box);
	}
	
	/**
	 * <설명> [송전설비] 지지물명 목록
	 * 
	 * @param [fnct_lc_no]
	 * @return list[eqp_no
					, eqp_nm
					, tower_idx]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getTowerList(Box box) throws Exception {
		return kdnEqpDAO.getTowerList(box);
	}
	
	/**
	 * <설명> [송전설비] 지도 맵(위도, 경도) 가져오기
	 * 
	 * @param [fst_bizplc_cd] [scd_bizplc_cd] [fnct_lc_no]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getEqpMapInfoList(Box box) throws Exception {
		return kdnEqpDAO.getEqpMapInfoList(box);
	}
}
