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
package egovframework.sample.service.impl;

import java.util.List;

import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;

import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.sample.service.SampleDefaultVO;
import egovframework.sample.service.SampleVO;


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

@Repository("sampleDAO")
public class SampleDAO extends EgovaSampleAbstractDAO {
	
	/* ********************************* [Sample] Start********************************* */
	 /**
	 * <설명> 
	 * [자료실] 목록 조회
	 * @param [box] 
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [범승철] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
    @SuppressWarnings("unchecked")
	public List<Box> selectSampleRefList(Box box) throws Exception {
    	return list("sampleDAO.selectSampleRefList", box);
    }
    
    /**
  	 * <설명> 
  	 * [자료실] 목록 카운트 조회
  	 * @param [box] 
  	 * @return [int]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
  	 * @author [범승철] 
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
	   public int selectSampleRefListTotCnt(Box box) {
	       return (Integer)getSqlMapClientTemplate().queryForObject("sampleDAO.selectSampleRefListTotCnt", box);
	   }
	   
	   /**
	  	 * <설명> 
	  	 * [자료실] 정보 조회
	  	 * @param [box] 
	  	 * @return [Box]
	  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	  	 * @author [범승철] 
	  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	  	 */
	    public Box selectSampleRefView(Box box) throws Exception {
	    	return (Box)selectByPk("sampleDAO.selectSampleRefView", box);
	    }
	    
	    /**
	  	 * <설명> 
	  	 * [자료실] 등록 저장
	  	 * @param [box] 
	  	 * @return [String]
	  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	  	 * @author [범승철] 
	  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	  	 */
	    public void sampleRefSave(Box box) throws Exception {
	        insert("sampleDAO.sampleRefSave", box);
	    }
	    
	    /**
	  	 * <설명> 
	  	 * [자료실] 수정
	  	 * @param [box] 
	  	 * @return [String]
	  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	  	 * @author [범승철] 
	  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	  	 */
	    public void sampleRefUpdate(Box box) throws Exception {
	        update("sampleDAO.sampleRefUpdate", box);
	    } 
	    
	    /**
	  	 * <설명> 
	  	 * [자료실] 삭제
	  	 * @param [box] 
	  	 * @return [String]
	  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	  	 * @author [범승철] 
	  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	  	 */
	    public void sampleRefDelete(Box box) throws Exception {
	        delete("sampleDAO.sampleRefDelete", box);
	    }
	    
	    /**
	  	 * <설명> 
	  	 * [자료실] 조회수 증가
	  	 * @param [box] 
	  	 * @return [String]
	  	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	  	 * @author [범승철] 
	  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	  	 */
	    public void sampleRefReadCountUpdate(Box box) throws Exception {
	        update("sampleDAO.sampleRefReadCountUpdate", box);
	    }
	    
	    /**
		 * <설명> 
		 * [자료실] 목록 조회 (Api 샘플)
		 * @param [box] 
		 * @return [List]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩 
		 * @author [범승철] 
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
		@SuppressWarnings("unchecked")
		public List<Box> getApiSelectSampleRefList(Box box) throws Exception {
	    	return list("sampleDAO.getApiSelectSampleRefList", box); 
	    }
	    /* ********************************* [Sample] End ********************************* */
	
	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertSample(SampleVO vo) throws Exception {
        return (String)insert("sampleDAO.insertSample_S", vo);
    }

    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
    public void updateSample(SampleVO vo) throws Exception {
        update("sampleDAO.updateSample_S", vo);
    }

    /**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteSample(SampleVO vo) throws Exception {
        delete("sampleDAO.deleteSample_S", vo);
    }

    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public SampleVO selectSample(SampleVO vo) throws Exception {
        return (SampleVO) selectByPk("sampleDAO.selectSample_S", vo);
    }

    /**
	 * 글 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 글 목록
	 * @exception Exception
	 */
    public List selectSampleList(SampleDefaultVO searchVO) throws Exception {
        return list("sampleDAO.selectSampleList_D", searchVO);
    }

    /**
	 * 글 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 글 총 갯수
	 * @exception
	 */
    public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
        return (Integer)getSqlMapClientTemplate().queryForObject("sampleDAO.selectSampleListTotCnt_S", searchVO);
    }

}
