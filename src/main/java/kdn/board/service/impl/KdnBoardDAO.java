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

@Repository("kdnBoardDAO")
public class KdnBoardDAO extends KdnSqlMapClient {
	
	/**
	 * <설명> [공지] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getNoticeList(Box box) throws Exception {
		return list("Board.getNoticeList", box);
	}

	/**
	 * <설명> [공지] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getNoticeListTotCnt(Box box) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Board.getNoticeListTotCnt", box);
	}
	
	/**
	 * <설명> [공지] Top 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getTopNoticeList(Box box) throws Exception {
		return list("Board.getTopNoticeList", box);
	}

	/**
	 * <설명> [공지] 정보 조회
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getNoticeView(Box box) throws Exception {
		return (Box) selectByPk("Board.getNoticeView", box);
	}

	/**
	 * <설명> [공지] 등록 저장
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void kdnNoticeSave(Box box) throws Exception {
		insert("Board.kdnNoticeSave", box);
	}

	/**
	 * <설명> [공지] 수정
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void noticeUpdate(Box box) throws Exception {
		update("Board.noticeUpdate", box);
	}

	/**
	 * <설명> [공지] 삭제
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void noticeDelete(Box box) throws Exception {
		delete("Board.noticeDelete", box);
	}

	/**
	 * <설명> [공지] maxIndex
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getMaxBoardIdx(Box box) {
		return (Integer)getSqlMapClientTemplate().queryForObject("Board.getMaxBoardIdx", box);
	}

	

}
