package kdnmobile.excel.upd.service.impl;

import java.util.List;

import kdnmobile.excel.upd.service.TotInsExcelVO;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * 게시물 관리를 위한 데이터 접근 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------      --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2011.09.22 서준식          nttId IdGen 서비스로 변경
 * </pre>
 */
@Repository("ExcelUploadDAO")
public class ExcelUploadDAO extends EgovComAbstractDAO {

	 /**
  	 * <설명> 엑셀 순시결과 저장
  	 * 
  	 * @param [TotInsExcelVO]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [범승철]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
    public void insertInsTot(List<TotInsExcelVO> param) throws Exception {
    	System.out.println("@@@@@@@@ param.size() =" + param.size());
    	
    	for (int i = 0; i < param.size(); i++) {
    		TotInsExcelVO totInsList = new TotInsExcelVO();
    		totInsList = param.get(i);
    		
    		int overlapTopCnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlapTotCnt", totInsList);
    		if(overlapTopCnt > 0){ //update
    			System.out.println("@@@@@@@ [순시결과 통합 이력 Update]");
    			delete("ExcelUploadDAO.deleteInsTot", totInsList); // 순시결과 통합 이력 삭제
    			insert("ExcelUploadDAO.insertInsTot", totInsList); // 순시결과 통합 이력 저장
    		}else{ //insert
    			System.out.println("@@@@@@@ [순시결과 통합 이력 Insert]");
    			insert("ExcelUploadDAO.insertInsTot", totInsList); // 순시결과 통합 이력 저장
    		}
    		System.out.println("@@@@@@@ [순시종류 코드] =" + totInsList.getIns_type());
    		
    		if("001".equals(totInsList.getIns_type().trim())){	/*보통순시 점검 결과 저장 (O)*/
    			int overlap001Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap001Cnt", totInsList);
    			if(overlap001Cnt > 0){ //update
    				System.out.println("@@@@@@@ [보통순시 Update]");
    				delete("ExcelUploadDAO.deleteIns001", totInsList); 
    				insert("ExcelUploadDAO.insertIns001", totInsList);
        		}else{ //insert
        			System.out.println("@@@@@@@ [보통순시 Insert]");
        			insert("ExcelUploadDAO.insertIns001", totInsList);
        		}
    		}else if("024".equals(totInsList.getIns_type().trim())){	/*항공장애등점검결과 저장 (O)*/
    			int overlap024Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap024Cnt", totInsList);
    			if(overlap024Cnt > 0){ //update
    				System.out.println("@@@@@@@ [항공장애등 Update]");
    				delete("ExcelUploadDAO.deleteIns024", totInsList); 
    				insert("ExcelUploadDAO.insertIns024", totInsList);
        		}else{ //insert
        			System.out.println("@@@@@@@ [항공장애등 Insert]");
        			insert("ExcelUploadDAO.insertIns024", totInsList);
        		}
    		}else if("025".equals(totInsList.getIns_type().trim())){	 /*항공장애등등구확인결과 저장 (O)*/
    			int overlap025Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap025Cnt", totInsList);
    			if(overlap025Cnt > 0){ //update
    				System.out.println("@@@@@@@ [항공장애등등구 Update]");
    				delete("ExcelUploadDAO.deleteIns025", totInsList); 
    				insert("ExcelUploadDAO.insertIns025", totInsList);
        		}else{ //insert
        			System.out.println("@@@@@@@ [항공장애등등구 Insert]");
        			insert("ExcelUploadDAO.insertIns025", totInsList);
        		}
    		}else if("021".equals(totInsList.getIns_type())){	/*기별점검 (O)*/
    			for (int j = 0; j < 18; j++) {
    				String good_secd = "";
    				String ins_itm_cd = "";
    				
    				switch (j) {
						case 0:
							ins_itm_cd = "00001";
							good_secd = totInsList.getGood_secd_00001(); 
							break;
						case 1:
							ins_itm_cd = "00002";
							good_secd = totInsList.getGood_secd_00002(); 
							break;
						case 	2:
							ins_itm_cd = "00003";
							good_secd = totInsList.getGood_secd_00003(); 
							break;
						case 3:
							ins_itm_cd = "00004";
							good_secd = totInsList.getGood_secd_00004(); 
							break;
						case 4: 
							ins_itm_cd = "00005";
							good_secd = totInsList.getGood_secd_00005(); 
							break;
						case 5: 
							ins_itm_cd = "00006";
							good_secd = totInsList.getGood_secd_00006(); 
							break;
						case 6: 
							ins_itm_cd = "00007";
							good_secd = totInsList.getGood_secd_00007(); 
							break;
						case 7: 
							ins_itm_cd = "00008";
							good_secd = totInsList.getGood_secd_00008(); 
							break;
						case 8: 
							ins_itm_cd = "00009";
							good_secd = totInsList.getGood_secd_00009(); 
							break;
						case 9: 
							ins_itm_cd = "00010";
							good_secd = totInsList.getGood_secd_00010(); 
							break;
						case 10: 
							ins_itm_cd = "00011";
							good_secd = totInsList.getGood_secd_00011(); 
							break;
						case 11: 
							ins_itm_cd = "00012";
							good_secd = totInsList.getGood_secd_00012(); 
							break;
						case 12: 
							ins_itm_cd = "00013";
							good_secd = totInsList.getGood_secd_00013(); 
							break;
						case 13: 
							ins_itm_cd = "00014";
							good_secd = totInsList.getGood_secd_00014(); 
							break;
						case 14: 
							ins_itm_cd = "00015";
							good_secd = totInsList.getGood_secd_00015(); 
							break;
						case 15: 
							ins_itm_cd = "00016";
							good_secd = totInsList.getGood_secd_00016(); 
							break;
						case 16: 
							ins_itm_cd = "00017";
							good_secd = totInsList.getGood_secd_00017(); 
							break;
						case 17: 
							ins_itm_cd = "00018";
							good_secd = totInsList.getGood_secd_00018(); 
							break;
						default:
							break;
					}
    				totInsList.setIns_itm_cd(ins_itm_cd);
    				totInsList.setGood_secd_021(good_secd);
    				
    				int overlap021Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap021Cnt", totInsList);
    				if(overlap021Cnt > 0){ //update
        				System.out.println("@@@@@@@ [기별점검 Update]");
        				delete("ExcelUploadDAO.deleteIns021", totInsList); 
        				insert("ExcelUploadDAO.insertIns021", totInsList);
            		}else{ //insert
            			System.out.println("@@@@@@@ [기별점검 Insert]");
            			insert("ExcelUploadDAO.insertIns021", totInsList);
            		}
				}
    		}else if("027".equals(totInsList.getIns_type())){	/*전선접속개소점검결과 저장*/
    			for (int j = 0; j < 3; j++) {
    				String pwln_eqp_no = "";
    				String cabl_tp = "";
    				String cnpt_tp = "";
    				String good_secd = "";
    				
    				switch (j) {
						case 0:
							pwln_eqp_no = totInsList.getPwln_eqp_no_c1();
							cabl_tp = totInsList.getCabl_tp_c1();
							cnpt_tp = totInsList.getCnpt_tp_c1();
							good_secd = totInsList.getGood_secd_027_c1();
							break;
						case 1:
							pwln_eqp_no = totInsList.getPwln_eqp_no_c2();
							cabl_tp = totInsList.getCabl_tp_c2();
							cnpt_tp = totInsList.getCnpt_tp_c2();
							good_secd = totInsList.getGood_secd_027_c2();
							break;
						case 2:
							pwln_eqp_no = totInsList.getPwln_eqp_no_c3();
							cabl_tp = totInsList.getCabl_tp_c3();
							cnpt_tp = totInsList.getCnpt_tp_c3();
							good_secd = totInsList.getGood_secd_027_c3();
							break;
						default:
							break;
					}
    				totInsList.setPwln_eqp_no(pwln_eqp_no);
    				totInsList.setCabl_tp(cabl_tp);
    				totInsList.setCnpt_tp(cnpt_tp);
    				totInsList.setGood_secd_027(good_secd);
    				
    				int overlap027RCnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap027RCnt", totInsList);
        			if(overlap027RCnt > 0){ //update
        				System.out.println("@@@@@@@ [전선접속개소점검결과 측정 Update]");
        				delete("ExcelUploadDAO.deleteIns027R", totInsList); 
        				insert("ExcelUploadDAO.insertIns027R", totInsList);
            		}else{ //insert
            			System.out.println("@@@@@@@ [전선접속개소점검결과 측정 Insert]");
            			insert("ExcelUploadDAO.insertIns027R", totInsList);
            		}
        			
    				int overlap027Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap027Cnt", totInsList);
	    			if(overlap027Cnt > 0){ //update
	    				System.out.println("@@@@@@@ [전선접속개소점검결과 측정 Update]");
	    				delete("ExcelUploadDAO.deleteIns027", totInsList); 
	    				insert("ExcelUploadDAO.insertIns027", totInsList);
	        		}else{ //insert
	        			System.out.println("@@@@@@@ [전선접속개소점검결과 측정 Insert]");
	        			insert("ExcelUploadDAO.insertIns027", totInsList);
	        		}
    			}
    		}else if("028".equals(totInsList.getIns_type())){	/*접지저항측정결과 저장 (O)*/
    			int overlap028RCnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap028RCnt", totInsList);
    			if(overlap028RCnt > 0){ //update
    				System.out.println("@@@@@@@ [접지저항측정결과 Update]");
    				delete("ExcelUploadDAO.deleteIns028R", totInsList); 
    				insert("ExcelUploadDAO.insertIns028R", totInsList);
        		}else{ //insert
        			System.out.println("@@@@@@@ [접지저항측정결과 Insert]");
        			insert("ExcelUploadDAO.insertIns028R", totInsList);
        		}
    			for (int j = 0; j < 5; j++) {
    				String msr_rs = "";
    				
	    			switch (j) {
						case 0:
							msr_rs = totInsList.getMsr_odr_1();
							totInsList.setMsr_odr("1");
							break;
						case 1:
							msr_rs = totInsList.getMsr_odr_2();
							totInsList.setMsr_odr("2");
							break;
						case 	2:
							msr_rs = totInsList.getMsr_odr_3();
							totInsList.setMsr_odr("3");
							break;
						case 3:
							msr_rs = totInsList.getMsr_odr_5();
							totInsList.setMsr_odr("4");
							break;
						case 4: 
							msr_rs = totInsList.getMsr_odr_10();
							totInsList.setMsr_odr("5");
							break;
						default:
							break;
					}
	    			totInsList.setMsr_rs(msr_rs);
	    			
	    			int overlap028Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap028Cnt", totInsList);
	    			if(overlap028Cnt > 0){ //update
	    				System.out.println("@@@@@@@ [접지저항측정차수 Update]");
	    				delete("ExcelUploadDAO.deleteIns028", totInsList); 
	    				insert("ExcelUploadDAO.insertIns028", totInsList);
	        		}else{ //insert
	        			System.out.println("@@@@@@@ [접지저항측정차수 Insert]");
	        			insert("ExcelUploadDAO.insertIns028", totInsList);
	        		}
    			}
    		}else if("029".equals(totInsList.getIns_type())){	/*불량애자검출결과 저장 (O)*/
    			int overlap029Cnt = (Integer) getSqlMapClientTemplate().queryForObject("ExcelUploadDAO.getOverlap029Cnt", totInsList);
    			if(overlap029Cnt > 0){ //update
    				System.out.println("@@@@@@@ [불량애자검출결과 Update]");
    				delete("ExcelUploadDAO.deleteIns029", totInsList); 
    				insert("ExcelUploadDAO.insertIns029", totInsList);
        		}else{ //insert
        			System.out.println("@@@@@@@ [불량애자검출결과 Insert]");
        			insert("ExcelUploadDAO.insertIns029", totInsList);
        		}
    		}
		}
    }
    
}
