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
package kdn.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.board.service.KdnBoardService;
import kdn.cmm.box.Box;

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

@Service("kdnBoardService")
public class KdnBoardServiceImpl extends AbstractServiceImpl implements   
        KdnBoardService { 
	
	/** SampleDAO */
    @Resource(name="kdnBoardDAO")
    private KdnBoardDAO kdnBoardDAO;
    
    /** ID Generation */
    @Resource(name="egovIdGnrService")    
    private EgovIdGnrService egovIdGnrService;
    
    /**
   	 * <설명> 
   	 * [공지] 목록 조회
   	 * @param [box] 
   	 * @return [List]
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
      public List<Box> getNoticeList(Box box) throws Exception {
    	  return kdnBoardDAO.getNoticeList(box);
      }
      
    /**
  	 * <설명> 
  	 * [공지] 목록 카운트 조회
  	 * @param [box] 
  	 * @return [int]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
  	 * @author [범승철] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
      public int getNoticeListTotCnt(Box box) {
  		return kdnBoardDAO.getNoticeListTotCnt(box);
  	 }
      
      /**
	 	 * <설명> 
	 	 * [공지] Top 목록 조회
	 	 * @param [box] 
	 	 * @return [List]
	 	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 	 * @author [범승철] 
	 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 	 */
	    public List<Box> getTopNoticeList(Box box) throws Exception {
	  	  return kdnBoardDAO.getTopNoticeList(box);
	    }
      
      /**
  	 * <설명> 
  	 * [공지] 정보 조회
  	 * @param [box] 
  	 * @return [Box]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
  	 * @author [범승철] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
    public Box getNoticeView(Box box) throws Exception {
  	  	Box viewBox = kdnBoardDAO.getNoticeView(box);
        return viewBox;
    }
    
    /**
  	 * <설명> 
  	 * [공지] 등록 저장
  	 * @param [box] 
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
  	 * @author [범승철] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
      public void kdnNoticeSave(Box box) throws Exception {
    	  kdnBoardDAO.kdnNoticeSave(box); 
	  }
      
      /**
  	 * <설명> [공지] 삭제
  	 * 
  	 * @param [box]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [범승철]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
  	public void noticeDelete(Box box) throws Exception {
  		kdnBoardDAO.noticeDelete(box);
  	}

  	/**
  	 * <설명> [공지] 수정
  	 * 
  	 * @param [box]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [범승철]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
  	public void noticeUpdate(Box box) throws Exception {
  		kdnBoardDAO.noticeUpdate(box);
  	}

  	/**
  	 * <설명> [공지] max 값 가져오기
  	 * 
  	 * @param [box]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [신명섭]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
	@Override
	public int getMaxBoardIdx(Box box) throws Exception {
		return kdnBoardDAO.getMaxBoardIdx(box);
		
	}

}
