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
package egovframework.sample.service;

import java.util.List;

import kdn.cmm.box.Box;



/**  
 * @Class Name : EgovSampleService.java
 * @Description : EgovSampleService Class
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
public interface EgovSampleService {
	
	/* ********************************* [Sample] Start********************************* */
	 /**
	 * <설명> 
	 * [자료실] 목록 조회
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    List<Box> selectSampleRefList(Box box) throws Exception;
    
    /**
	 * <설명> 
	 * [자료실] 목록 카운트 조회
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    int selectSampleRefListTotCnt(Box box);
    
    /**
	 * [자료실] 정보 조회
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    Box selectSampleRefView(Box box) throws Exception;
    
    /**
   	 * [자료실] 등록 저장
   	 * @param [box] 
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
    public void sampleRefSave(Box box) throws Exception;
    
    /**
   	 * [자료실] 수정
   	 * @param [box] 
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
    public void sampleRefUpdate(Box box) throws Exception;
    
    /**
   	 * [자료실] 삭제
   	 * @param [box] 
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
    public void sampleRefDelete(Box box) throws Exception;
    
    /**
   	 * [자료실] 조회수 증가
   	 * @param [box] 
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
    public void sampleRefReadCountUpdate(Box box) throws Exception;
    
    /**
   	 * <설명> 
   	 * [자료실] 목록 조회 (Api 샘플)
   	 * @param [box] 
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
       List<Box> getApiSelectSampleRefList(Box box) throws Exception;
    /* ********************************* [Sample] End ********************************* */
	
	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    String insertSample(SampleVO vo) throws Exception;
    
    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
    void updateSample(SampleVO vo) throws Exception;
    
    /**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형 
	 * @exception Exception
	 */
    void deleteSample(SampleVO vo) throws Exception;
    
    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    SampleVO selectSample(SampleVO vo) throws Exception;
    
    /**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List selectSampleList(SampleDefaultVO searchVO) throws Exception;
    
    /**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectSampleListTotCnt(SampleDefaultVO searchVO);
    
}
