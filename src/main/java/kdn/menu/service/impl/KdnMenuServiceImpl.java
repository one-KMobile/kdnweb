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
package kdn.menu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.menu.service.KdnMenuService;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

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

@Service("kdnMenuService") 
public class KdnMenuServiceImpl extends AbstractServiceImpl implements   
        KdnMenuService { 
	
	/** SampleDAO */
    @Resource(name="kdnMenuDAO")
    private KdnMenuDAO kdnMenuDAO;
    
	/**
	 * <설명> [메뉴] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getMenuList(Box box) throws Exception {
		return kdnMenuDAO.getMenuList(box);
	}

	/**
	 * <설명> [메뉴] 목록 카운트 조회
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getMenuListTotCnt(Box box) {
		return kdnMenuDAO.getMenuListTotCnt(box);
	}
	
	/**
	 * <설명> [메뉴] 정보 조회
	 * 
	 * @param [box]
	 * @return [Box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public Box getMenuView(Box box) throws Exception {
		Box viewBox = kdnMenuDAO.getMenuView(box);
		return viewBox;
	}
	
	/**
	 * <설명> [메뉴] 상위메뉴ID 선택 리스트
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> topMenuIdList(Box box) throws Exception {
		return kdnMenuDAO.topMenuIdList(box);
	}
	
	/**
	 * <설명> [메뉴] 메뉴ID 중복 체크 카운트
	 * 
	 * @param [box]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int getMenuIdOverlapCount(Box box) {
		return kdnMenuDAO.getMenuIdOverlapCount(box);
	}
	
	/**
	 * <설명> [메뉴] 등록 저장
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void menuSave(Box box) throws Exception {
		kdnMenuDAO.menuSave(box);
	}
	
	/**
	 * <설명> [메뉴] 수정
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void menuUpdate(Box box) throws Exception {
		kdnMenuDAO.menuUpdate(box);
	}
	
	/**
	 * <설명> [메뉴] 삭제
	 * 
	 * @param [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public void menuDelete(Box box) throws Exception {
		kdnMenuDAO.menuDelete(box);
	}
	
	/**
	 * <설명> [메뉴] 하위 메뉴가 있는지 체크하는 카운트
	 * 
	 * @param [controller_method]
	 * @return [int]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public int isSubMenuCnt(Box box) {
		return kdnMenuDAO.isSubMenuCnt(box);
	}

}
