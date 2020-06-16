package kdnmobile.excel.upd.cmm;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import kdn.aria.Cipher;
import kdnmobile.excel.upd.service.ExcelVO;
import kdnmobile.excel.upd.service.TotInsExcelVO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public List<TotInsExcelVO> doXlsExcel(String fileName, ExcelVO excel) {
		List<TotInsExcelVO> list = new ArrayList<TotInsExcelVO>();

		try {
			FileInputStream inputStream = new FileInputStream(fileName);

			POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
			HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);

			int sheetNum = workbook.getNumberOfSheets();
			String sheetName = null;
			int i = 0;
			short c = 0;
			System.out.println("@@@@@@ [시트 갯수] = " + sheetNum);
			
			for (int j = 0; j < sheetNum; j++) {
				HSSFSheet sheet = workbook.getSheetAt(j); // 첫번째 시트(0) 부터 ~
				sheetName = sheet.getSheetName(); // 시트 이름 가져오기
				int rows = sheet.getPhysicalNumberOfRows(); // 행개수
				
				if(rows > 1){ //셀에 제목말고 데이터가 있을 경우
					int totalExcelCnt = 0;
					String[][] arrExcelData = null;
		
					HSSFRow row = sheet.getRow(0); // row 가져오기
					totalExcelCnt = row.getPhysicalNumberOfCells(); // 셀 개수 가져오기
					System.out.println("## rows : " + rows + ", cells : " + totalExcelCnt);
					arrExcelData = new String[rows][totalExcelCnt];
					
					for (i = 0; i < rows; i++) {
						row = sheet.getRow(i); // row 가져오기
						if (row != null) {
							int cells = row.getLastCellNum(); // 셀 개수 가져오기
							arrExcelData = new String[rows][cells];
							for (c = 0; c < cells; c++) {
								HSSFCell cell = row.getCell(c);
								if (cell != null) {
									String value = "";
									if (c == 0) {
										cell.setCellType(1);
									}
									switch (cell.getCellType()) {  // 각셀의 데이터값을 가져올때 맞는 데이터형으로 변환
										case HSSFCell.CELL_TYPE_FORMULA:
											value = cell.getCellFormula();
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:
											System.out.println("#####Num");
											value = "" + cell.getNumericCellValue();
											break;
										case HSSFCell.CELL_TYPE_STRING:
											System.out.println("#####String");
											value = "" + cell.getStringCellValue();
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											System.out.println("#####Blank");
											value = "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											System.out.println("#####Error");
											value = "" + cell.getErrorCellValue();
											break;
										default:
											System.out.println("#####Default");
									}
									// System.out.println(i+" : 행, "+c+" : 열, 값 : "+value);
									arrExcelData[i][c] = value; // 엑셀값을 배열에 넣는다.
								}
							}
						}
						System.out.println("@@@@@@@[sheetName] = " + sheetName);
						if (i > 0) {
							/*
							 * 루프 돌면서 엑셀에 있는 데이터를 세팅 
							 * for(int cellCnt=0;cellCnt<totalExcelCnt;cellCnt++){
							 * 
							 * System.out.println("## : " +
							 * arrExcelData[i][totalExcelCnt]); }
							 */
		
							/*ExcelVO ec = new ExcelVO();
							ec.setFile_path(excel.getFile_path());
							ec.setFile_name(excel.getFile_name());
							ec.setIns_type(sheetName);*/ 
							
							TotInsExcelVO totInsExcelVO = new TotInsExcelVO();
							totInsExcelVO.setIns_type(sheetName.trim()); /*순시종류코드*/
							
							if("024".equals(totInsExcelVO.getIns_type().trim())){ //항공장애등점검 
								totInsExcelVO.setMaster_key(arrExcelData[i][56]); 	/*암호화, 복호화하기 위한 마스터키*/	
							}else if("021".equals(totInsExcelVO.getIns_type().trim())){ //기별점검
								totInsExcelVO.setMaster_key(arrExcelData[i][65]); 	
							}else if("029".equals(totInsExcelVO.getIns_type().trim())){ //불량애자
								totInsExcelVO.setMaster_key(arrExcelData[i][58]); 	
							}else if("028".equals(totInsExcelVO.getIns_type().trim())){ //접지저항측정
								totInsExcelVO.setMaster_key(arrExcelData[i][55]); 	
							}else if("027".equals(totInsExcelVO.getIns_type().trim())){ //전선접속개소
								totInsExcelVO.setMaster_key(arrExcelData[i][63]); 	
							}else{
								totInsExcelVO.setMaster_key(arrExcelData[i][53]); 
							}
							
							byte[] key = Cipher.getMasterKeySet(totInsExcelVO.getMaster_key());

							totInsExcelVO.setUnity_ins_no(Cipher.getDataDcSet(arrExcelData[i][0], key));		/*통합점검번호*/
							totInsExcelVO.setIns_plan_no(Cipher.getDataDcSet(arrExcelData[i][1], key)); 		/*점검계획번호*/
							totInsExcelVO.setFnct_eqp_no(Cipher.getDataDcSet(arrExcelData[i][2], key)); 		/*기능설비번호*/
							totInsExcelVO.setIns_sn(Cipher.getDataDcSet(arrExcelData[i][3], key));				/*점검순번*/
							totInsExcelVO.setFst_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][5], key)); 		/*1차사업소코드*/
							totInsExcelVO.setScd_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][6], key)); 		/*2차사업소코드*/
							totInsExcelVO.setThd_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][7], key)); 		/*3차사업소코드*/
							totInsExcelVO.setIns_ty_cd(Cipher.getDataDcSet(arrExcelData[i][8], key));		 	/*점검유형코드*/
							totInsExcelVO.setSimplcty_inst_at(Cipher.getDataDcSet(arrExcelData[i][9], key)); 	/*간이점검표여부*/
							totInsExcelVO.setIns_resn(Cipher.getDataDcSet(arrExcelData[i][10], key)); 			/*점검사유*/
							totInsExcelVO.setIns_dt(Cipher.getDataDcSet(arrExcelData[i][11], key));		 		/*점검일자*/
							totInsExcelVO.setStrt_dtm(Cipher.getDataDcSet(arrExcelData[i][12], key));		 	/*출발일시*/
							totInsExcelVO.setArvl_dtm(Cipher.getDataDcSet(arrExcelData[i][13], key));		 	/*도착일시*/
							totInsExcelVO.setStrcfm_man(Cipher.getDataDcSet(arrExcelData[i][14], key));		/*출발확인자*/
							totInsExcelVO.setArvlcfm_man(Cipher.getDataDcSet(arrExcelData[i][15], key)); 		/*도착확인자*/
							totInsExcelVO.setInsctr_1(Cipher.getDataDcSet(arrExcelData[i][16], key));		 	/*점검자1*/
							totInsExcelVO.setInsctr_2(Cipher.getDataDcSet(arrExcelData[i][17], key));		 	/*점검자2*/
							totInsExcelVO.setInsctr_user_ids_1(Cipher.getDataDcSet(arrExcelData[i][18], key)); 		/*점검자유저ids1*/
							totInsExcelVO.setInsctr_user_ids_2(Cipher.getDataDcSet(arrExcelData[i][19], key)); 		/*점검자유저ids2*/
							totInsExcelVO.setIns_ccpy_nm(Cipher.getDataDcSet(arrExcelData[i][20], key));		 		/*점검협력사명*/
							totInsExcelVO.setDetect_mthcd(Cipher.getDataDcSet(arrExcelData[i][21], key));	 		/*검출방법코드*/
							totInsExcelVO.setBegin_spot(Cipher.getDataDcSet(arrExcelData[i][22], key)); 		 		/*시작지점*/
							totInsExcelVO.setEnd_spot(Cipher.getDataDcSet(arrExcelData[i][23], key)); 			 		/*종료지점*/
							totInsExcelVO.setWeath(Cipher.getDataDcSet(arrExcelData[i][46], key)); 				 		/*날씨*/
							totInsExcelVO.setAtemp(Cipher.getDataDcSet(arrExcelData[i][24], key));		 		 		/*온도*/
							totInsExcelVO.setHumid(Cipher.getDataDcSet(arrExcelData[i][25], key)); 				 		/*습도*/
							totInsExcelVO.setGnrlz_jdgmnt_gdbd(Cipher.getDataDcSet(arrExcelData[i][26], key));		/*종합판정양부*/
							totInsExcelVO.setGnrlz_opnin(Cipher.getDataDcSet(arrExcelData[i][45], key)); 		 		/*종합의견*/
							totInsExcelVO.setAttfl_no(Cipher.getDataDcSet(arrExcelData[i][27], key)); 			 		/*첨부파일번호*/
							totInsExcelVO.setAprv_no(Cipher.getDataDcSet(arrExcelData[i][28], key)); 			 		/*결재번호*/
							totInsExcelVO.setImprmn_requst_no(Cipher.getDataDcSet(arrExcelData[i][29], key)); 		/*정비요청번호*/
							totInsExcelVO.setCrt_dtm(Cipher.getDataDcSet(arrExcelData[i][30], key)); 			 		/*등록일시*/
							totInsExcelVO.setCrt_user(Cipher.getDataDcSet(arrExcelData[i][31], key)); 			 		/*등록자*/
							totInsExcelVO.setUpd_dtm(Cipher.getDataDcSet(arrExcelData[i][32], key)); 			 		/*수정일시*/
							totInsExcelVO.setUpd_user(Cipher.getDataDcSet(arrExcelData[i][33], key)); 			 		/*수정자*/
							totInsExcelVO.setUnity_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][34], key)); 	 		/*통합사업소코드*/
							totInsExcelVO.setGubun(Cipher.getDataDcSet(arrExcelData[i][35], key));				/*Gubun*/ 
							totInsExcelVO.setTid(Cipher.getDataDcSet(arrExcelData[i][36], key));					/*tid*/
							totInsExcelVO.setJunc_code(Cipher.getDataDcSet(arrExcelData[i][37], key));			/*Junc_code*/
							totInsExcelVO.setObjectid(Cipher.getDataDcSet(arrExcelData[i][38], key));			/*Objectid*/ 
							totInsExcelVO.setMangr_id(Cipher.getDataDcSet(arrExcelData[i][39], key)); 			/*감독자id*/
							
							//시트 이름으로 순시종류 판단하여 TotInsExcelVO에 셋팅
							if("001".equals(totInsExcelVO.getIns_type().trim())){ //보통순시
								System.out.println("@@@@@@@@@@@@보통순시 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_001(Cipher.getDataDcSet(arrExcelData[i][0], key));
								totInsExcelVO.setEqp_no_001(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setTins_result_secd(Cipher.getDataDcSet(arrExcelData[i][47], key)); 			/*순시결과구분코드*/
								totInsExcelVO.setExamin_cn(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*조사내용*/
								totInsExcelVO.setAttfl_no_001(Cipher.getDataDcSet(arrExcelData[i][49], key)); 					/*첨부파일번호*/
								totInsExcelVO.setImprmn_requst_no_001(Cipher.getDataDcSet(arrExcelData[i][50], key)); 	/*정비요청번호*/
								totInsExcelVO.setGubun_001(Cipher.getDataDcSet(arrExcelData[i][51], key));
								totInsExcelVO.setTid_001(Cipher.getDataDcSet(arrExcelData[i][52], key));
								list.add(totInsExcelVO);
							}else if("021".equals(totInsExcelVO.getIns_type().trim())){ //기별점검
								System.out.println("@@@@@@@@@@@@기별점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_021(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setEqp_no_021(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setImprmn_requst_no_021(Cipher.getDataDcSet(arrExcelData[i][29], key));	/*정비요청번호*/
								totInsExcelVO.setGood_secd_00001(Cipher.getDataDcSet(arrExcelData[i][47], key)); 			/*선로점검항목코드(00001) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00002(Cipher.getDataDcSet(arrExcelData[i][48], key)); 			/*선로점검항목코드(00002) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00003(Cipher.getDataDcSet(arrExcelData[i][49], key)); 			/*선로점검항목코드(00003) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00004(Cipher.getDataDcSet(arrExcelData[i][50], key)); 			/*선로점검항목코드(00004) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00005(Cipher.getDataDcSet(arrExcelData[i][51], key)); 			/*선로점검항목코드(00005) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00006(Cipher.getDataDcSet(arrExcelData[i][52], key)); 			/*선로점검항목코드(00006) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00007(Cipher.getDataDcSet(arrExcelData[i][53], key)); 			/*선로점검항목코드(00007) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00008(Cipher.getDataDcSet(arrExcelData[i][54], key)); 			/*선로점검항목코드(00008) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00009(Cipher.getDataDcSet(arrExcelData[i][55], key)); 			/*선로점검항목코드(00009) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00010(Cipher.getDataDcSet(arrExcelData[i][56], key)); 			/*선로점검항목코드(00010) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00011(Cipher.getDataDcSet(arrExcelData[i][57], key)); 			/*선로점검항목코드(00011) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00012(Cipher.getDataDcSet(arrExcelData[i][58], key)); 			/*선로점검항목코드(00012) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00013(Cipher.getDataDcSet(arrExcelData[i][59], key)); 			/*선로점검항목코드(00013) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00014(Cipher.getDataDcSet(arrExcelData[i][60], key)); 			/*선로점검항목코드(00014) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00015(Cipher.getDataDcSet(arrExcelData[i][61], key)); 			/*선로점검항목코드(00015) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00016(Cipher.getDataDcSet(arrExcelData[i][62], key)); 			/*선로점검항목코드(00016) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00017(Cipher.getDataDcSet(arrExcelData[i][63], key)); 			/*선로점검항목코드(00017) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00018(Cipher.getDataDcSet(arrExcelData[i][64], key)); 			/*선로점검항목코드(00018) - 양호구분코드*/
								list.add(totInsExcelVO);
							}else if("024".equals(totInsExcelVO.getIns_type().trim())){ //항공장애등점검
								System.out.println("@@@@@@@@@@@@항공장애등점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_024(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_024(Cipher.getDataDcSet(arrExcelData[i][29], key));	/*정비요청번호*/
								totInsExcelVO.setEqp_no_024(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setFlight_lmp_knd(Cipher.getDataDcSet(arrExcelData[i][47], key));				/*항공등종류*/   
								totInsExcelVO.setFlight_lmp_no(Cipher.getDataDcSet(arrExcelData[i][48], key));  				/*항공등번호*/  
								totInsExcelVO.setSrcelct_knd(Cipher.getDataDcSet(arrExcelData[i][49], key));   					/*전원종류*/    
								totInsExcelVO.setCtrl_ban_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][50], key));  		/*제어반양부구분코드*/
								totInsExcelVO.setSlrcl_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][51], key));    			/*태양전지양부구분코드*/
								totInsExcelVO.setSrgbtry_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][52], key));  		/*축전지양부구분코드*/
								totInsExcelVO.setRgist_gu_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][53], key));		/*등기구양부구분코드*/
								totInsExcelVO.setCabl_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][54], key)); 				/*전선양부구분코드*/    
								totInsExcelVO.setGood_secd(Cipher.getDataDcSet(arrExcelData[i][55], key)); 					/*양호구분코드 */
								list.add(totInsExcelVO);
							}else if("025".equals(totInsExcelVO.getIns_type().trim())){ //항공장애등등구확인점검
								System.out.println("@@@@@@@@@@@@항공장애등등구확인점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_025(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_025(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setEqp_no_025(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setFlight_lmp_knd_025(Cipher.getDataDcSet(arrExcelData[i][47], key)); 		/*항공등종류*/
								totInsExcelVO.setFlight_lmp_no_025(Cipher.getDataDcSet(arrExcelData[i][48], key)); 			/*항공등번호*/
								totInsExcelVO.setSrcelct_knd_025(Cipher.getDataDcSet(arrExcelData[i][49], key)); 			/*전원종류*/   
								totInsExcelVO.setFlck_co(Cipher.getDataDcSet(arrExcelData[i][50], key));  						/*점멸횟수*/         
								totInsExcelVO.setLightg_stadiv_cd(Cipher.getDataDcSet(arrExcelData[i][51], key)); 			/*점등상태구분코드*/
								totInsExcelVO.setGood_secd_025(Cipher.getDataDcSet(arrExcelData[i][52], key));  			/*양호구분코드 */
								list.add(totInsExcelVO);
							}else if("029".equals(totInsExcelVO.getIns_type())){ //불량애자검출
								System.out.println("@@@@@@@@@@@@불량애자검출 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_029(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_029(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setEqp_no_029(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setCl_no_029(Cipher.getDataDcSet(arrExcelData[i][47], key)); 						/*회선번호*/
								totInsExcelVO.setInsr_eqp_no(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*애자설비번호*/
								totInsExcelVO.setInsbty_lft(Cipher.getDataDcSet(arrExcelData[i][49], key)); 						/*애자련형좌*/
								totInsExcelVO.setInsbty_rit(Cipher.getDataDcSet(arrExcelData[i][50], key)); 						/*애자련형우*/
								totInsExcelVO.setTy_secd(Cipher.getDataDcSet(arrExcelData[i][51], key)); 						/*유형구분코드*/
								totInsExcelVO.setPhs_secd(Cipher.getDataDcSet(arrExcelData[i][52], key)); 						/*상구분코드*/
								totInsExcelVO.setInsr_qy(Cipher.getDataDcSet(arrExcelData[i][53], key)); 						/*애자수량*/
								totInsExcelVO.setBad_insr_qy(Cipher.getDataDcSet(arrExcelData[i][54], key)); 					/*불량애자수량*/
								totInsExcelVO.setGood_secd_029(Cipher.getDataDcSet(arrExcelData[i][57], key)); 				/*양호구분코드*/
								list.add(totInsExcelVO);
							}else if("027".equals(totInsExcelVO.getIns_type())){ //전선접속개소점검
								System.out.println("@@@@@@@@@@@@전선접속개소점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_027(Cipher.getDataDcSet(arrExcelData[i][0], key)); 			/*통합점검번호*/
								totInsExcelVO.setEqp_no_027(Cipher.getDataDcSet(arrExcelData[i][44], key)); 				/*설비번호*/
								totInsExcelVO.setCl_no(Cipher.getDataDcSet(arrExcelData[i][47], key)); 						/*회선번호*/
								totInsExcelVO.setTtm_load(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*당시부하*/
								totInsExcelVO.setCndctr_co(Cipher.getDataDcSet(arrExcelData[i][49], key)); 					/*도체수*/
								totInsExcelVO.setCndctr_sn(Cipher.getDataDcSet(arrExcelData[i][50], key)); 					/*도체순번*/
								totInsExcelVO.setImprmn_requst_no_027(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setPwln_eqp_no_c1(Cipher.getDataDcSet(arrExcelData[i][51], key)); 				/*C1 전력선설비번호*/
								totInsExcelVO.setCabl_tp_c1(Cipher.getDataDcSet(arrExcelData[i][52], key)); 						/*C1 전선온도*/
								totInsExcelVO.setCnpt_tp_c1(Cipher.getDataDcSet(arrExcelData[i][53], key)); 						/*C1 접속점온도*/
								totInsExcelVO.setGood_secd_027_c1(Cipher.getDataDcSet(arrExcelData[i][54], key));  			/*C1 양호구분코드 */
								totInsExcelVO.setPwln_eqp_no_c2(Cipher.getDataDcSet(arrExcelData[i][55], key)); 			/*C2 전력선설비번호*/
								totInsExcelVO.setCabl_tp_c2(Cipher.getDataDcSet(arrExcelData[i][56], key)); 					/*C2 전선온도*/
								totInsExcelVO.setCnpt_tp_c2(Cipher.getDataDcSet(arrExcelData[i][57], key)); 					/*C2 접속점온도*/
								totInsExcelVO.setGood_secd_027_c2(Cipher.getDataDcSet(arrExcelData[i][58], key));  		/*C2 양호구분코드 */
								totInsExcelVO.setPwln_eqp_no_c3(Cipher.getDataDcSet(arrExcelData[i][59], key)); 				/*C3 전력선설비번호*/
								totInsExcelVO.setCabl_tp_c3(Cipher.getDataDcSet(arrExcelData[i][60], key)); 						/*C3 전선온도*/
								totInsExcelVO.setCnpt_tp_c3(Cipher.getDataDcSet(arrExcelData[i][61], key)); 						/*C3 접속점온도*/
								totInsExcelVO.setGood_secd_027_c3(Cipher.getDataDcSet(arrExcelData[i][62], key));  			/*C3 양호구분코드 */
								list.add(totInsExcelVO);
							}else if("028".equals(totInsExcelVO.getIns_type())){ //접지저항측정
								System.out.println("@@@@@@@@@@@@접지저항측정 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_028(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_028(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setEqp_no_028(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setGood_secd_028(Cipher.getDataDcSet(arrExcelData[i][47], key)); 				/*양호구분코드*/
								totInsExcelVO.setPmttr(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*특이사항(비고)*/
								totInsExcelVO.setMsr_co(Cipher.getDataDcSet(arrExcelData[i][49], key)); 					/*측정횟수*/
								totInsExcelVO.setMsr_odr_1(Cipher.getDataDcSet(arrExcelData[i][50], key)); 				/*1[㎲]*/
								totInsExcelVO.setMsr_odr_2(Cipher.getDataDcSet(arrExcelData[i][51], key)); 				/*2[㎲]*/
								totInsExcelVO.setMsr_odr_3(Cipher.getDataDcSet(arrExcelData[i][52], key)); 				/*3[㎲]*/
								totInsExcelVO.setMsr_odr_5(Cipher.getDataDcSet(arrExcelData[i][53], key)); 				/*5[㎲]*/
								totInsExcelVO.setMsr_odr_10(Cipher.getDataDcSet(arrExcelData[i][54], key)); 			/*10[㎲]*/
								//totInsExcelVO.setStdr_rs(Cipher.getDataDcSet(arrExcelData[i][], key)); 					/*기준저항값*/
								list.add(totInsExcelVO);
							}else{
								
							}							
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<TotInsExcelVO> doXlsxExcel(String fileName, ExcelVO excel) {
		List<TotInsExcelVO> list = new ArrayList<TotInsExcelVO>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
					fileName));

			int sheetNum = workbook.getNumberOfSheets();
			String sheetName = null;
			int i = 0;
			short c = 0;
			System.out.println("@@@@@@ [시트 갯수] = " + sheetNum);
			
			for (int j = 0; j < sheetNum; j++) {
				XSSFSheet sheet = workbook.getSheetAt(j); // 첫번째 시트
				sheetName = sheet.getSheetName(); // 시트 이름 가져오기
				int rows = sheet.getPhysicalNumberOfRows(); // 행개수

				if(rows > 1){ //셀에 제목말고 데이터가 있을 경우	
					int totalCellCnt = 0;
					String[][] arrExcelData = null;
		
					XSSFRow row = sheet.getRow(0); // row 가져오기
					totalCellCnt = row.getPhysicalNumberOfCells(); // 셀 개수 가져오기
					System.out.println("## ROWS : " + rows + ", CELLS : " + totalCellCnt);
					arrExcelData = new String[rows][totalCellCnt];
				
					for (i = 0; i < rows; i++) {
						row = sheet.getRow(i); // row 가져오기
						if (row != null) {
							int cells = row.getLastCellNum(); // 셀 개수 가져오기
							for (c = 0; c < cells; c++) {
								XSSFCell cell = row.getCell(c);
								if (cell != null) {
									String value = "";
									if (c == 0) {
										cell.setCellType(1);
									}
									switch (cell.getCellType()) { // 각셀의 데이터값을 가져올때 맞는 데이터형으로 변환
										case HSSFCell.CELL_TYPE_FORMULA:
											value = cell.getCellFormula();
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:
											value = "" + cell.getNumericCellValue();
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = "" + cell.getStringCellValue();
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											// value =""+cell.getBooleanCellValue();
											value = "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = "" + cell.getErrorCellValue();
											break;
										default:
									}
									// System.out.println(i+" : 행, "+c+" : 열, 값 : "+value);
									arrExcelData[i][c] = value;// 엑셀값을 배열에 넣는다.
								}
							}
						}
		
						if (i > 0) {
							/*
							 * 루프 돌면서 엑셀에 있는 데이터를 세팅
							 *  for(int cellCnt=0;cellCnt<totalCellCnt;cellCnt++){
							 * 
							 * System.out.println("## : " +
							 * arrExcelData[i][totalCellCnt]); }
							 */
							
							/*ExcelVO ec = new ExcelVO();
							ec.setFile_path(excel.getFile_path());
							ec.setFile_name(excel.getFile_name());
							ec.setIns_type(sheetName);*/ 
							
							TotInsExcelVO totInsExcelVO = new TotInsExcelVO();
							totInsExcelVO.setIns_type(sheetName.trim()); /*순시종류코드*/
							
							if("024".equals(totInsExcelVO.getIns_type().trim())){ //항공장애등점검 
								totInsExcelVO.setMaster_key(arrExcelData[i][56]); 	/*암호화, 복호화하기 위한 마스터키*/	
							}else if("021".equals(totInsExcelVO.getIns_type().trim())){ //기별점검
								totInsExcelVO.setMaster_key(arrExcelData[i][65]); 	
							}else if("029".equals(totInsExcelVO.getIns_type().trim())){ //불량애자
								totInsExcelVO.setMaster_key(arrExcelData[i][58]); 	
							}else if("028".equals(totInsExcelVO.getIns_type().trim())){ //접지저항측정
								totInsExcelVO.setMaster_key(arrExcelData[i][55]); 	
							}else if("027".equals(totInsExcelVO.getIns_type().trim())){ //전선접속개소
								totInsExcelVO.setMaster_key(arrExcelData[i][63]); 	
							}else{
								totInsExcelVO.setMaster_key(arrExcelData[i][53]); 
							}
							
							byte[] key = Cipher.getMasterKeySet(totInsExcelVO.getMaster_key());

							totInsExcelVO.setUnity_ins_no(Cipher.getDataDcSet(arrExcelData[i][0], key));		/*통합점검번호*/
							totInsExcelVO.setIns_plan_no(Cipher.getDataDcSet(arrExcelData[i][1], key)); 		/*점검계획번호*/
							totInsExcelVO.setFnct_eqp_no(Cipher.getDataDcSet(arrExcelData[i][2], key)); 		/*기능설비번호*/
							totInsExcelVO.setIns_sn(Cipher.getDataDcSet(arrExcelData[i][3], key));				/*점검순번*/
							totInsExcelVO.setFst_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][5], key)); 		/*1차사업소코드*/
							totInsExcelVO.setScd_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][6], key)); 		/*2차사업소코드*/
							totInsExcelVO.setThd_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][7], key)); 		/*3차사업소코드*/
							totInsExcelVO.setIns_ty_cd(Cipher.getDataDcSet(arrExcelData[i][8], key));		 	/*점검유형코드*/
							totInsExcelVO.setSimplcty_inst_at(Cipher.getDataDcSet(arrExcelData[i][9], key)); 	/*간이점검표여부*/
							totInsExcelVO.setIns_resn(Cipher.getDataDcSet(arrExcelData[i][10], key)); 			/*점검사유*/
							totInsExcelVO.setIns_dt(Cipher.getDataDcSet(arrExcelData[i][11], key));		 		/*점검일자*/
							totInsExcelVO.setStrt_dtm(Cipher.getDataDcSet(arrExcelData[i][12], key));		 	/*출발일시*/
							totInsExcelVO.setArvl_dtm(Cipher.getDataDcSet(arrExcelData[i][13], key));		 	/*도착일시*/
							totInsExcelVO.setStrcfm_man(Cipher.getDataDcSet(arrExcelData[i][14], key));		/*출발확인자*/
							totInsExcelVO.setArvlcfm_man(Cipher.getDataDcSet(arrExcelData[i][15], key)); 		/*도착확인자*/
							totInsExcelVO.setInsctr_1(Cipher.getDataDcSet(arrExcelData[i][16], key));		 	/*점검자1*/
							totInsExcelVO.setInsctr_2(Cipher.getDataDcSet(arrExcelData[i][17], key));		 	/*점검자2*/
							totInsExcelVO.setInsctr_user_ids_1(Cipher.getDataDcSet(arrExcelData[i][18], key)); 		/*점검자유저ids1*/
							totInsExcelVO.setInsctr_user_ids_2(Cipher.getDataDcSet(arrExcelData[i][19], key)); 		/*점검자유저ids2*/
							totInsExcelVO.setIns_ccpy_nm(Cipher.getDataDcSet(arrExcelData[i][20], key));		 		/*점검협력사명*/
							totInsExcelVO.setDetect_mthcd(Cipher.getDataDcSet(arrExcelData[i][21], key));	 		/*검출방법코드*/
							totInsExcelVO.setBegin_spot(Cipher.getDataDcSet(arrExcelData[i][22], key)); 		 		/*시작지점*/
							totInsExcelVO.setEnd_spot(Cipher.getDataDcSet(arrExcelData[i][23], key)); 			 		/*종료지점*/
							totInsExcelVO.setWeath(Cipher.getDataDcSet(arrExcelData[i][46], key)); 				 		/*날씨*/
							totInsExcelVO.setAtemp(Cipher.getDataDcSet(arrExcelData[i][24], key));		 		 		/*온도*/
							totInsExcelVO.setHumid(Cipher.getDataDcSet(arrExcelData[i][25], key)); 				 		/*습도*/
							totInsExcelVO.setGnrlz_jdgmnt_gdbd(Cipher.getDataDcSet(arrExcelData[i][26], key));		/*종합판정양부*/
							totInsExcelVO.setGnrlz_opnin(Cipher.getDataDcSet(arrExcelData[i][45], key)); 		 		/*종합의견*/
							totInsExcelVO.setAttfl_no(Cipher.getDataDcSet(arrExcelData[i][27], key)); 			 		/*첨부파일번호*/
							totInsExcelVO.setAprv_no(Cipher.getDataDcSet(arrExcelData[i][28], key)); 			 		/*결재번호*/
							totInsExcelVO.setImprmn_requst_no(Cipher.getDataDcSet(arrExcelData[i][29], key)); 		/*정비요청번호*/
							totInsExcelVO.setCrt_dtm(Cipher.getDataDcSet(arrExcelData[i][30], key)); 			 		/*등록일시*/
							totInsExcelVO.setCrt_user(Cipher.getDataDcSet(arrExcelData[i][31], key)); 			 		/*등록자*/
							totInsExcelVO.setUpd_dtm(Cipher.getDataDcSet(arrExcelData[i][32], key)); 			 		/*수정일시*/
							totInsExcelVO.setUpd_user(Cipher.getDataDcSet(arrExcelData[i][33], key)); 			 		/*수정자*/
							totInsExcelVO.setUnity_bizplc_cd(Cipher.getDataDcSet(arrExcelData[i][34], key)); 	 		/*통합사업소코드*/
							totInsExcelVO.setGubun(Cipher.getDataDcSet(arrExcelData[i][35], key));				/*Gubun*/ 
							totInsExcelVO.setTid(Cipher.getDataDcSet(arrExcelData[i][36], key));					/*tid*/
							totInsExcelVO.setJunc_code(Cipher.getDataDcSet(arrExcelData[i][37], key));			/*Junc_code*/
							totInsExcelVO.setObjectid(Cipher.getDataDcSet(arrExcelData[i][38], key));			/*Objectid*/ 
							totInsExcelVO.setMangr_id(Cipher.getDataDcSet(arrExcelData[i][39], key)); 			/*감독자id*/
							
							//시트 이름으로 순시종류 판단하여 TotInsExcelVO에 셋팅
							if("001".equals(totInsExcelVO.getIns_type().trim())){ //보통순시
								System.out.println("@@@@@@@@@@@@보통순시 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_001(Cipher.getDataDcSet(arrExcelData[i][0], key));
								totInsExcelVO.setEqp_no_001(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setTins_result_secd(Cipher.getDataDcSet(arrExcelData[i][47], key)); 			/*순시결과구분코드*/
								totInsExcelVO.setExamin_cn(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*조사내용*/
								totInsExcelVO.setAttfl_no_001(Cipher.getDataDcSet(arrExcelData[i][49], key)); 					/*첨부파일번호*/
								totInsExcelVO.setImprmn_requst_no_001(Cipher.getDataDcSet(arrExcelData[i][50], key)); 	/*정비요청번호*/
								totInsExcelVO.setGubun_001(Cipher.getDataDcSet(arrExcelData[i][51], key));
								totInsExcelVO.setTid_001(Cipher.getDataDcSet(arrExcelData[i][52], key));
								list.add(totInsExcelVO);
							}else if("021".equals(totInsExcelVO.getIns_type().trim())){ //기별점검
								System.out.println("@@@@@@@@@@@@기별점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_021(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setEqp_no_021(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setImprmn_requst_no_021(Cipher.getDataDcSet(arrExcelData[i][29], key));	/*정비요청번호*/
								totInsExcelVO.setGood_secd_00001(Cipher.getDataDcSet(arrExcelData[i][47], key)); 			/*선로점검항목코드(00001) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00002(Cipher.getDataDcSet(arrExcelData[i][48], key)); 			/*선로점검항목코드(00002) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00003(Cipher.getDataDcSet(arrExcelData[i][49], key)); 			/*선로점검항목코드(00003) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00004(Cipher.getDataDcSet(arrExcelData[i][50], key)); 			/*선로점검항목코드(00004) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00005(Cipher.getDataDcSet(arrExcelData[i][51], key)); 			/*선로점검항목코드(00005) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00006(Cipher.getDataDcSet(arrExcelData[i][52], key)); 			/*선로점검항목코드(00006) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00007(Cipher.getDataDcSet(arrExcelData[i][53], key)); 			/*선로점검항목코드(00007) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00008(Cipher.getDataDcSet(arrExcelData[i][54], key)); 			/*선로점검항목코드(00008) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00009(Cipher.getDataDcSet(arrExcelData[i][55], key)); 			/*선로점검항목코드(00009) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00010(Cipher.getDataDcSet(arrExcelData[i][56], key)); 			/*선로점검항목코드(00010) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00011(Cipher.getDataDcSet(arrExcelData[i][57], key)); 			/*선로점검항목코드(00011) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00012(Cipher.getDataDcSet(arrExcelData[i][58], key)); 			/*선로점검항목코드(00012) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00013(Cipher.getDataDcSet(arrExcelData[i][59], key)); 			/*선로점검항목코드(00013) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00014(Cipher.getDataDcSet(arrExcelData[i][60], key)); 			/*선로점검항목코드(00014) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00015(Cipher.getDataDcSet(arrExcelData[i][61], key)); 			/*선로점검항목코드(00015) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00016(Cipher.getDataDcSet(arrExcelData[i][62], key)); 			/*선로점검항목코드(00016) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00017(Cipher.getDataDcSet(arrExcelData[i][63], key)); 			/*선로점검항목코드(00017) - 양호구분코드*/
								totInsExcelVO.setGood_secd_00018(Cipher.getDataDcSet(arrExcelData[i][64], key)); 			/*선로점검항목코드(00018) - 양호구분코드*/
								list.add(totInsExcelVO);
							}else if("024".equals(totInsExcelVO.getIns_type().trim())){ //항공장애등점검
								System.out.println("@@@@@@@@@@@@항공장애등점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_024(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_024(Cipher.getDataDcSet(arrExcelData[i][29], key));	/*정비요청번호*/
								totInsExcelVO.setEqp_no_024(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setFlight_lmp_knd(Cipher.getDataDcSet(arrExcelData[i][47], key));				/*항공등종류*/   
								totInsExcelVO.setFlight_lmp_no(Cipher.getDataDcSet(arrExcelData[i][48], key));  				/*항공등번호*/  
								totInsExcelVO.setSrcelct_knd(Cipher.getDataDcSet(arrExcelData[i][49], key));   					/*전원종류*/    
								totInsExcelVO.setCtrl_ban_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][50], key));  		/*제어반양부구분코드*/
								totInsExcelVO.setSlrcl_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][51], key));    			/*태양전지양부구분코드*/
								totInsExcelVO.setSrgbtry_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][52], key));  		/*축전지양부구분코드*/
								totInsExcelVO.setRgist_gu_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][53], key));		/*등기구양부구분코드*/
								totInsExcelVO.setCabl_gdbd_secd(Cipher.getDataDcSet(arrExcelData[i][54], key)); 				/*전선양부구분코드*/    
								totInsExcelVO.setGood_secd(Cipher.getDataDcSet(arrExcelData[i][55], key)); 					/*양호구분코드 */
								list.add(totInsExcelVO);
							}else if("025".equals(totInsExcelVO.getIns_type().trim())){ //항공장애등등구확인점검
								System.out.println("@@@@@@@@@@@@항공장애등등구확인점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_025(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_025(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setEqp_no_025(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setFlight_lmp_knd_025(Cipher.getDataDcSet(arrExcelData[i][47], key)); 		/*항공등종류*/
								totInsExcelVO.setFlight_lmp_no_025(Cipher.getDataDcSet(arrExcelData[i][48], key)); 			/*항공등번호*/
								totInsExcelVO.setSrcelct_knd_025(Cipher.getDataDcSet(arrExcelData[i][49], key)); 			/*전원종류*/   
								totInsExcelVO.setFlck_co(Cipher.getDataDcSet(arrExcelData[i][50], key));  						/*점멸횟수*/         
								totInsExcelVO.setLightg_stadiv_cd(Cipher.getDataDcSet(arrExcelData[i][51], key)); 			/*점등상태구분코드*/
								totInsExcelVO.setGood_secd_025(Cipher.getDataDcSet(arrExcelData[i][52], key));  			/*양호구분코드 */
								list.add(totInsExcelVO);
							}else if("029".equals(totInsExcelVO.getIns_type())){ //불량애자검출
								System.out.println("@@@@@@@@@@@@불량애자검출 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_029(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_029(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setEqp_no_029(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setCl_no_029(Cipher.getDataDcSet(arrExcelData[i][47], key)); 						/*회선번호*/
								totInsExcelVO.setInsr_eqp_no(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*애자설비번호*/
								totInsExcelVO.setInsbty_lft(Cipher.getDataDcSet(arrExcelData[i][49], key)); 						/*애자련형좌*/
								totInsExcelVO.setInsbty_rit(Cipher.getDataDcSet(arrExcelData[i][50], key)); 						/*애자련형우*/
								totInsExcelVO.setTy_secd(Cipher.getDataDcSet(arrExcelData[i][51], key)); 						/*유형구분코드*/
								totInsExcelVO.setPhs_secd(Cipher.getDataDcSet(arrExcelData[i][52], key)); 						/*상구분코드*/
								totInsExcelVO.setInsr_qy(Cipher.getDataDcSet(arrExcelData[i][53], key)); 						/*애자수량*/
								totInsExcelVO.setBad_insr_qy(Cipher.getDataDcSet(arrExcelData[i][54], key)); 					/*불량애자수량*/
								totInsExcelVO.setGood_secd_029(Cipher.getDataDcSet(arrExcelData[i][57], key)); 				/*양호구분코드*/
								list.add(totInsExcelVO);
							}else if("027".equals(totInsExcelVO.getIns_type())){ //전선접속개소점검
								System.out.println("@@@@@@@@@@@@전선접속개소점검 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_027(Cipher.getDataDcSet(arrExcelData[i][0], key)); 			/*통합점검번호*/
								totInsExcelVO.setEqp_no_027(Cipher.getDataDcSet(arrExcelData[i][44], key)); 				/*설비번호*/
								totInsExcelVO.setCl_no(Cipher.getDataDcSet(arrExcelData[i][47], key)); 						/*회선번호*/
								totInsExcelVO.setTtm_load(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*당시부하*/
								totInsExcelVO.setCndctr_co(Cipher.getDataDcSet(arrExcelData[i][49], key)); 					/*도체수*/
								totInsExcelVO.setCndctr_sn(Cipher.getDataDcSet(arrExcelData[i][50], key)); 					/*도체순번*/
								totInsExcelVO.setImprmn_requst_no_027(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setPwln_eqp_no_c1(Cipher.getDataDcSet(arrExcelData[i][51], key)); 				/*C1 전력선설비번호*/
								totInsExcelVO.setCabl_tp_c1(Cipher.getDataDcSet(arrExcelData[i][52], key)); 						/*C1 전선온도*/
								totInsExcelVO.setCnpt_tp_c1(Cipher.getDataDcSet(arrExcelData[i][53], key)); 						/*C1 접속점온도*/
								totInsExcelVO.setGood_secd_027_c1(Cipher.getDataDcSet(arrExcelData[i][54], key));  			/*C1 양호구분코드 */
								totInsExcelVO.setPwln_eqp_no_c2(Cipher.getDataDcSet(arrExcelData[i][55], key)); 			/*C2 전력선설비번호*/
								totInsExcelVO.setCabl_tp_c2(Cipher.getDataDcSet(arrExcelData[i][56], key)); 					/*C2 전선온도*/
								totInsExcelVO.setCnpt_tp_c2(Cipher.getDataDcSet(arrExcelData[i][57], key)); 					/*C2 접속점온도*/
								totInsExcelVO.setGood_secd_027_c2(Cipher.getDataDcSet(arrExcelData[i][58], key));  		/*C2 양호구분코드 */
								totInsExcelVO.setPwln_eqp_no_c3(Cipher.getDataDcSet(arrExcelData[i][59], key)); 				/*C3 전력선설비번호*/
								totInsExcelVO.setCabl_tp_c3(Cipher.getDataDcSet(arrExcelData[i][60], key)); 						/*C3 전선온도*/
								totInsExcelVO.setCnpt_tp_c3(Cipher.getDataDcSet(arrExcelData[i][61], key)); 						/*C3 접속점온도*/
								totInsExcelVO.setGood_secd_027_c3(Cipher.getDataDcSet(arrExcelData[i][62], key));  			/*C3 양호구분코드 */
								list.add(totInsExcelVO);
							}else if("028".equals(totInsExcelVO.getIns_type())){ //접지저항측정
								System.out.println("@@@@@@@@@@@@접지저항측정 VO 셋팅");
								totInsExcelVO.setUnity_ins_no_028(Cipher.getDataDcSet(arrExcelData[i][0], key)); 				/*통합점검번호*/
								totInsExcelVO.setImprmn_requst_no_028(Cipher.getDataDcSet(arrExcelData[i][29], key));  	/*정비요청번호*/
								totInsExcelVO.setEqp_no_028(Cipher.getDataDcSet(arrExcelData[i][44], key)); 					/*설비번호*/
								totInsExcelVO.setGood_secd_028(Cipher.getDataDcSet(arrExcelData[i][47], key)); 				/*양호구분코드*/
								totInsExcelVO.setPmttr(Cipher.getDataDcSet(arrExcelData[i][48], key)); 					/*특이사항(비고)*/
								totInsExcelVO.setMsr_co(Cipher.getDataDcSet(arrExcelData[i][49], key)); 					/*측정횟수*/
								totInsExcelVO.setMsr_odr_1(Cipher.getDataDcSet(arrExcelData[i][50], key)); 				/*1[㎲]*/
								totInsExcelVO.setMsr_odr_2(Cipher.getDataDcSet(arrExcelData[i][51], key)); 				/*2[㎲]*/
								totInsExcelVO.setMsr_odr_3(Cipher.getDataDcSet(arrExcelData[i][52], key)); 				/*3[㎲]*/
								totInsExcelVO.setMsr_odr_5(Cipher.getDataDcSet(arrExcelData[i][53], key)); 				/*5[㎲]*/
								totInsExcelVO.setMsr_odr_10(Cipher.getDataDcSet(arrExcelData[i][54], key)); 			/*10[㎲]*/
								//totInsExcelVO.setStdr_rs(Cipher.getDataDcSet(arrExcelData[i][], key)); 					/*기준저항값*/
								list.add(totInsExcelVO);
							}else{
								
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

