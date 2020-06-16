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
package kdn.board.service;

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
public interface KdnBoardService {
	
	 /**
	 * <설명> 
	 * [공지] 목록 조회
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    List<Box> getNoticeList(Box box) throws Exception;
    
    /**
	 * <설명> 
	 * [공지] 목록 카운트 조회
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    int getNoticeListTotCnt(Box box);
    
    /**
	 * <설명> 
	 * [공지] Top 목록 조회
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    List<Box> getTopNoticeList(Box box) throws Exception;
    
    /**
   	 * [공지] 정보 조회
   	 * @param [box] 
   	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
   	 * @author [범승철] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
   	 */
     Box getNoticeView(Box box) throws Exception;
     
     /**
	 * [공지] 등록 저장
	 * @param [box] 
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
     public void kdnNoticeSave(Box box) throws Exception;
     
     /**
  	 * <설명> [공지] 수정
  	 * 
  	 * @param [box]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [범승철]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
  	public void noticeUpdate(Box box) throws Exception;
  	
     /**
 	 * <설명> [공지] 삭제
 	 * 
 	 * @param [box]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩
 	 * @author [범승철]
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
 	public void noticeDelete(Box box) throws Exception;
 	
 	/**
 	 * <설명> [공지] max  값 가져오기
 	 * 
 	 * @param [box]
 	 * @throws [예외명] [설명] // 각 예외 당 하나씩
 	 * @author [신명섭]
 	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
 	 */
	int getMaxBoardIdx(Box box) throws Exception;

}
