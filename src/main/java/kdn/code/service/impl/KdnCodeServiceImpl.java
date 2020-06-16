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
package kdn.code.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.code.service.KdnCodeService;

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

@Service("kdnCodeService") 
public class KdnCodeServiceImpl extends AbstractServiceImpl implements   
        KdnCodeService { 
	
	/** SampleDAO */
    @Resource(name="kdnCodeDAO")
    private KdnCodeDAO kdnCodeDAO;
    
    /** ID Generation */
    @Resource(name="egovIdGnrService")    
    private EgovIdGnrService egovIdGnrService;
    
	/**
	 * <설명> [공통그룹코드] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getGroupCodeList(Box box) throws Exception {
		return kdnCodeDAO.getGroupCodeList(box);
	}

	/**
	 * <설명> [공통그룹코드] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getGroupCodeListTotCnt(Box box) {
		return kdnCodeDAO.getGroupCodeListTotCnt(box);
	}

	/**
	 * <설명> [공통그룹코드] 정보 조회
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getGroupCodeView(Box box) throws Exception {
		Box viewBox = kdnCodeDAO.getGroupCodeView(box);
		return viewBox;
	}

	/**
	 * <설명> [공통그룹코드] 등록 저장
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void groupCodeSave(Box box) throws Exception {
		kdnCodeDAO.groupCodeSave(box);
	}

	/**
	 * <설명> [공통그룹코드] 삭제
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void groupCodeDelete(Box box) throws Exception {
		kdnCodeDAO.groupCodeDelete(box);
	}

	/**
	 * <설명> [공통그룹코드] 수정
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void groupCodeUpdate(Box box) throws Exception {
		kdnCodeDAO.groupCodeUpdate(box);
	}

	/**
	 * <설명> [공통코드] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getCodeList(Box box) throws Exception {
		return kdnCodeDAO.getCodeList(box);
	}

	/**
	 * <설명> [공통코드] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getCodeListTotCnt(Box box) {
		return kdnCodeDAO.getCodeListTotCnt(box);
	}

	/**
	 * <설명> [공통코드] 정보 조회
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getCodeView(Box box) throws Exception {
		Box viewBox = kdnCodeDAO.getCodeView(box);
		return viewBox;
	}

	/**
	 * <설명> [공통코드] 등록 저장
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void codeSave(Box box) throws Exception {
		kdnCodeDAO.codeSave(box);
	}

	/**
	 * <설명> [공통코드] 삭제
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void codeDelete(Box box) throws Exception {
		kdnCodeDAO.codeDelete(box);
	}

	/**
	 * <설명> [공통코드] 수정
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void codeUpdate(Box box) throws Exception {
		kdnCodeDAO.codeUpdate(box);
	}
	
	/**
	 * <설명> [공통그룹코드] 공통그룹코드 선택 리스트
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> selectGroupCodeList(Box box) throws Exception {
		return kdnCodeDAO.selectGroupCodeList(box);
	}
	
	/**
	 * <설명> [공통코드] 공통코드 선택 리스트
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> selectCodeList(Box box) throws Exception {
		return kdnCodeDAO.selectCodeList(box);
	}

	/**
	 * <설명> [공통그룹코드] 공통그룹코드ID 중복 체크 카운트
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getGroupCodeOverlapCount(Box box) {
		return kdnCodeDAO.getGroupCodeOverlapCount(box);
	}
	
	/**
	 * <설명> [공통코드] 공통코드ID 중복 체크 카운트
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getCodeOverlapCount(Box box) {
		return kdnCodeDAO.getCodeOverlapCount(box);
	}
}
