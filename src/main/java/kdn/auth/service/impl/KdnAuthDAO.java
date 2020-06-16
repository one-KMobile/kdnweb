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
package kdn.auth.service.impl;

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

@Repository("kdnAuthDAO")
public class KdnAuthDAO extends KdnSqlMapClient {
	
	/**
	 * <설명> [권한] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getAuthList(Box box) throws Exception {
		return list("Auth.getAuthList", box);
	}

	/**
	 * <설명> [권한] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getAuthListTotCnt(Box box) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Auth.getAuthListTotCnt", box);
	}
	
	/**
	 * <설명> [권한] 정보 조회
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getAuthView(Box box) throws Exception {
		return (Box) selectByPk("Auth.getAuthView", box);
	}
	
	/**
	 * <설명> [권한] 권한ID 중복 체크 카운트
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getAuthIdOverlapCount(Box box) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Auth.getAuthIdOverlapCount", box);
	}
	
	/**
	 * <설명> [권한] 등록 저장
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public String authSave(Box box) throws Exception {
		return (String) insert("Auth.authSave", box);
	}
	
	/**
	 * <설명> [권한] 수정
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void authUpdate(Box box) throws Exception {
		update("Auth.authUpdate", box);
	}
	
	/**
	 * <설명> [권한] 삭제
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void authDelete(Box box) throws Exception {
		delete("Auth.authDelete", box);
	}
	
	/**
	 * <설명> [메뉴 권한] 메뉴 권한을 주기위한 리스트
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getMenuGrantAuthList(Box box) throws Exception {
		return list("Auth.getMenuGrantAuthList", box);
	}
	
	/**
	 * <설명> [메뉴 권한] 메뉴 권한 주기 (Insert)
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public String menuGrantAuthSave(Box box) throws Exception {
		return (String) insert("Auth.menuGrantAuthSave", box);
	}
	
	/**
	 * <설명> [메뉴 권한] 메뉴 권한 주기 (Update)
	 * 
	 * @param [box]
	 * @return [String]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public String menuGrantAuthUpdate(Box box) throws Exception {
		return (String) insert("Auth.menuGrantAuthUpdate", box);
	}
	
	/**
	 * <설명> [권한] 메뉴 권한 카운트 체크
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getMenuGrantAuthCnt(Box box) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Auth.getMenuGrantAuthCnt", box);
	}
	
	/**
	 * <설명> [메뉴 권한] 좌측 메뉴 리스트 가져오기
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getLeftMenuList(Box box) throws Exception {
		return list("Auth.getLeftMenuList", box);
	}
	
	/**
	 * <설명> [권한] user 권한 카운트 조회
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	 public int authUserInfoCnt(Box box) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Auth.authUserInfoCnt", box);
	}
	 
	/**
	 * <설명> [권한] 권한 코드값 호출
	 * 
	 * @param [box]
	 * @return [Box] - code_value, code_name
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	 
	@SuppressWarnings("unchecked")
	public List<Box> getAuthCodeValue(Box box) throws Exception{
		 return list("Auth.getAuthCodeValue", box);
	 }

}
