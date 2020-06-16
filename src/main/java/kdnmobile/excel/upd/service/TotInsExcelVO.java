package kdnmobile.excel.upd.service;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 보통순시
 */
@SuppressWarnings("serial")
public class TotInsExcelVO implements Serializable{ 
	
	private String ins_type; /*순시종류코드*/
	private String master_key; /*암호화, 복호화하기 위한 마스터키*/
	
	public String getMaster_key() {
		return master_key;
	}
	public void setMaster_key(String master_key) {
		this.master_key = master_key;
	}
	public String getIns_type() {
		return ins_type;
	}
	public void setIns_type(String ins_type) {
		this.ins_type = ins_type;
	}
	/**
	 * 점검결과-통합점검이력(CO_TOTINS_HIST_T) 테이블에 입력될 데이터 변수 선언 (O)
	 */
	private String unity_ins_no; /*통합점검번호 (int)*/
	private String ins_plan_no; /*점검계획번호*/
	private String fnct_eqp_no; /*기능설비번호*/
	private String ins_sn; /*점검순번 (int)*/
	private String fst_bizplc_cd; /*1차사업소코드*/
	private String scd_bizplc_cd; /*2차사업소코드*/
	private String thd_bizplc_cd; /*3차사업소코드*/
	private String ins_ty_cd; /*점검유형코드*/
	private String simplcty_inst_at; /*간이점검표여부*/
	private String ins_resn; /*점검사유*/
	private String ins_dt; /*점검일자*/
	private String strt_dtm; /*출발일시*/
	private String arvl_dtm; /*도착일시*/
	private String strcfm_man; /*출발확인자*/
	private String arvlcfm_man; /*도착확인자*/
	private String insctr_1; /*점검자1*/
	private String insctr_2; /*점검자2*/
	private String insctr_user_ids_1; /*점검자유저ids1*/
	private String insctr_user_ids_2; /*점검자유저ids2*/
	private String ins_ccpy_nm; /*점검협력사명*/
	private String detect_mthcd; /*검출방법코드*/
	private String begin_spot; /*시작지점*/
	private String end_spot; /*종료지점*/
	private String weath; /*날씨*/
	private String atemp; /*온도*/
	private String humid; /*습도*/
	private String gnrlz_jdgmnt_gdbd; /*종합판정양부*/
	private String gnrlz_opnin; /*종합의견*/
	private String attfl_no; /*첨부파일번호*/
	private String aprv_no; /*결재번호*/
	private String imprmn_requst_no; /*정비요청번호 (int)*/
	private String crt_dtm; /*등록일시*/
	private String crt_user; /*등록자*/
	private String upd_dtm; /*수정일시*/
	private String upd_user; /*수정자*/
	private String unity_bizplc_cd; /*통합사업소코드*/
	private String gubun;  /*(int)*/
	private String tid;
	private String junc_code;
	private String objectid; 
	private String mangr_id; /*감독자id*/
	
	/**
	 * 보통순시 점검 결과(OT_NRM_INS_RLT_T) 테이블에 입력될 데이터 변수 선언 (O)
	 */
	private String unity_ins_no_001; /*통합점검번호 (int)*/
	private String attfl_no_001; /*첨부파일번호*/       
	private String imprmn_requst_no_001; /*정비요청번호 (int)*/ 
	private String gubun_001;  /*(int)*/
	private String tid_001;
	private String eqp_no_001; /*설비번호*/   
	private String tins_result_secd; /*순시결과구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String examin_cn;  /*조사내용*/
	
	/**
	 * 항공장애등점검결과(OT_FLGT_LMP_RLT_T) 테이블에 입력될 데이터 변수 선언 (O)
	 */
	private String unity_ins_no_024; /*통합점검번호 (int)*/
	private String imprmn_requst_no_024; /*정비요청번호 (int)*/
	private String eqp_no_024; /*설비번호*/         
	private String flight_lmp_knd; /*항공등종류*/   
	private String flight_lmp_no;  /*항공등번호 (int)*/  
	private String srcelct_knd;   /*전원종류*/    
	private String ctrl_ban_gdbd_secd;  /*제어반양부구분코드*/
	private String slrcl_gdbd_secd;    /*태양전지양부구분코드*/
	private String srgbtry_gdbd_secd;  /*축전지양부구분코드*/
	private String rgist_gu_gdbd_secd;		/*등기구양부구분코드*/
	private String cabl_gdbd_secd; 		/*전선양부구분코드*/    
	private String good_secd; /*양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/  
	
	/**
	 * 항공장애등등구확인결과(OT_FLTRB_LMP_RLT_T) 테이블에 입력될 데이터 변수 선언 (O)
	 */
	private String unity_ins_no_025; /*통합점검번호 (int)*/
	private String imprmn_requst_no_025; /*정비요청번호 (int)*/
	private String eqp_no_025; /*설비번호*/         
	private String flight_lmp_knd_025; /*항공등종류*/
	private String flight_lmp_no_025; /*항공등번호 (int)*/
	private String srcelct_knd_025; /*전원종류*/   
	private String flck_co;  /*점멸횟수 (int)*/ 
	private String lightg_stadiv_cd; /*점등상태구분코드*/
	private String good_secd_025;  /*양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/ 
	
	/**
	 * 기별점검(OT_INS_RLT_T) 테이블에 입력될 데이터 변수 선언 (O)
	 */
	private String unity_ins_no_021; /*통합점검번호 (int)*/
	private String eqp_no_021; /*설비번호*/
	private String imprmn_requst_no_021; /*정비요청번호*/
	private String ins_itm_cd; /*선로점검항목코드*/
	private String good_secd_021; /*양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00001; /*선로점검항목코드[00001] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00002; /*선로점검항목코드[00002] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00003; /*선로점검항목코드[00003] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00004; /*선로점검항목코드[00004] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00005; /*선로점검항목코드[00005] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00006; /*선로점검항목코드[00006] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00007; /*선로점검항목코드[00007] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00008; /*선로점검항목코드[00008] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00009; /*선로점검항목코드[00009] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00010; /*선로점검항목코드[00010] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00011; /*선로점검항목코드[00011] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00012; /*선로점검항목코드[00012] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00013; /*선로점검항목코드[00013] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00014; /*선로점검항목코드[00014] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00015; /*선로점검항목코드[00015] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00016; /*선로점검항목코드[00016] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00017; /*선로점검항목코드[00017] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String good_secd_00018; /*선로점검항목코드[00018] - 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	
	/**
	 * 전선접속개소점검결과(OT_WIRJIN_PIN_RLT_T) 테이블에 입력될 데이터 변수 선언
	 */
	private String unity_ins_no_027; /*통합점검번호 (int)*/
	private String eqp_no_027; /*설비번호*/
	private String cl_no; /*회선번호*/
	private String pwln_eqp_no; /*전력선설비번호*/
	private String ttm_load; /*당시부하 (int)*/      
	private String cndctr_co; /*도체수*/
	  
	/**
	 * 전선접속개소점검결과 측정(OT_WIRJIN_PIN_RLT2_T) 테이블에 입력될 데이터 변수 선언 (PK : unity_ins_no, eqp_no, cl_no, pwln_eqp_no, cndctr_sn)
	 */
	/*private String unity_ins_no_027_2;
	private String eqp_no_027_2; 
	private String cl_no_027_2;
	private String pwln_eqp_no*/
	private String cndctr_sn; /*도체순번 (int)*/
	private String imprmn_requst_no_027; /*정비요청번호 (int)*/
	private String cabl_tp; /*전선온도 (int)*/
	private String cnpt_tp; /*접속점온도 (int)*/
	private String good_secd_027; /*양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String pwln_eqp_no_c1; 	/*C1 전력선설비번호 */
	private String cabl_tp_c1; 			/*C1 전선온도 (int)*/
	private String cnpt_tp_c1; 			/*C1 접속점온도 (int)*/
	private String good_secd_027_c1; /*C1 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String pwln_eqp_no_c2; 		/*C2 전력선설비번호 */
	private String cabl_tp_c2; 				/*C2 전선온도 (int)*/
	private String cnpt_tp_c2; 				/*C2 접속점온도 (int)*/
	private String good_secd_027_c2; 		/*C2 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String pwln_eqp_no_c3; 	/*C3 전력선설비번호 */
	private String cabl_tp_c3; 			/*C3 전선온도 (int)*/
	private String cnpt_tp_c3; 			/*C3 접속점온도 (int)*/
	private String good_secd_027_c3; /*C3 양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	
	/**
	 * 접지저항측정결과(OT_GRND_RS_RLT_T) 테이블에 입력될 데이터 변수 선언
	 */
	private String unity_ins_no_028; /*통합점검번호 (int)*/
	private String imprmn_requst_no_028; /*정비요청번호 (int)*/
	private String eqp_no_028; /*설비번호*/
	private String stdr_rs; /*(int)*/
	private String good_secd_028; /*양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	private String pmttr; /*특이사항(비고)*/
	 
	/**
	 * 접지저항측정차수(OT_GRND_RS_ODR_T) 테이블에 입력될 데이터 변수 선언 (PK : unity_ins_no, eqp_no, msr_co, msr_odr)
	 */
	/*private String unity_ins_no_028;
	private String eqp_no_028;*/
	/*측정차수 ( 1[㎲], 2[㎲], 3[㎲], 5[㎲], 10[㎲] )*/
	private String msr_odr_1; /*1[㎲] (int)*/
	private String msr_odr_2; /*2[㎲] (int)*/
	private String msr_odr_3; /*3[㎲] (int)*/
	private String msr_odr_5; /*5[㎲] (int)*/
	private String msr_odr_10; /*10[㎲] (int)*/
	private String msr_rs; /*측정저항값 (int)*/ 
	private String msr_co; /*측정횟수 (int)*/
	private String msr_odr; /*측정차수 (int)*/
	
	/**
	 * 불량애자검출결과(OT_BDINR_RLT_T) 테이블에 입력될 데이터 변수 선언 (O)
	 */
	private String unity_ins_no_029; /*통합점검번호 (int)*/
	private String imprmn_requst_no_029; /*정비요청번호 (int)*/
	private String eqp_no_029; /*설비번호*/
	private String cl_no_029; /*회선번호*/
	private String ty_secd; /*유형구분코드*/          
	private String phs_secd; /*상구분코드*/        
	private String insr_eqp_no; /*애자설비번호*/   
	private String insbty_lft;  /*애자련형좌*/    
	private String insbty_rit;   /*애자련형우*/
	private String insr_qy; /*애자수량 (int)*/
	private String bad_insr_qy; /*불량애자수량 (int)*/
	private String good_secd_029; /*양호구분코드 (001: 양호, 002: 조치후양호, 003: 불량, 004: 해당없음 )*/
	
	public String getUnity_ins_no() {
		return unity_ins_no;
	}
	public void setUnity_ins_no(String unity_ins_no) {
		this.unity_ins_no = unity_ins_no;
	}
	public String getIns_plan_no() {
		return ins_plan_no;
	}
	public void setIns_plan_no(String ins_plan_no) {
		this.ins_plan_no = ins_plan_no;
	}
	public String getFnct_eqp_no() {
		return fnct_eqp_no;
	}
	public void setFnct_eqp_no(String fnct_eqp_no) {
		this.fnct_eqp_no = fnct_eqp_no;
	}
	public String getIns_sn() {
		return ins_sn;
	}
	public void setIns_sn(String ins_sn) {
		this.ins_sn = ins_sn;
	}
	public String getFst_bizplc_cd() {
		return fst_bizplc_cd;
	}
	public void setFst_bizplc_cd(String fst_bizplc_cd) {
		this.fst_bizplc_cd = fst_bizplc_cd;
	}
	public String getScd_bizplc_cd() {
		return scd_bizplc_cd;
	}
	public void setScd_bizplc_cd(String scd_bizplc_cd) {
		this.scd_bizplc_cd = scd_bizplc_cd;
	}
	public String getThd_bizplc_cd() {
		return thd_bizplc_cd;
	}
	public void setThd_bizplc_cd(String thd_bizplc_cd) {
		this.thd_bizplc_cd = thd_bizplc_cd;
	}
	public String getIns_ty_cd() {
		return ins_ty_cd;
	}
	public void setIns_ty_cd(String ins_ty_cd) {
		this.ins_ty_cd = ins_ty_cd;
	}
	public String getSimplcty_inst_at() {
		return simplcty_inst_at;
	}
	public void setSimplcty_inst_at(String simplcty_inst_at) {
		this.simplcty_inst_at = simplcty_inst_at;
	}
	public String getIns_resn() {
		return ins_resn;
	}
	public void setIns_resn(String ins_resn) {
		this.ins_resn = ins_resn;
	}
	public String getIns_dt() {
		return ins_dt;
	}
	public void setIns_dt(String ins_dt) {
		this.ins_dt = ins_dt;
	}
	public String getStrt_dtm() {
		return strt_dtm;
	}
	public void setStrt_dtm(String strt_dtm) {
		this.strt_dtm = strt_dtm;
	}
	public String getArvl_dtm() {
		return arvl_dtm;
	}
	public void setArvl_dtm(String arvl_dtm) {
		this.arvl_dtm = arvl_dtm;
	}
	public String getStrcfm_man() {
		return strcfm_man;
	}
	public void setStrcfm_man(String strcfm_man) {
		this.strcfm_man = strcfm_man;
	}
	public String getArvlcfm_man() {
		return arvlcfm_man;
	}
	public void setArvlcfm_man(String arvlcfm_man) {
		this.arvlcfm_man = arvlcfm_man;
	}
	public String getInsctr_1() {
		return insctr_1;
	}
	public void setInsctr_1(String insctr_1) {
		this.insctr_1 = insctr_1;
	}
	public String getInsctr_2() {
		return insctr_2;
	}
	public void setInsctr_2(String insctr_2) {
		this.insctr_2 = insctr_2;
	}
	public String getInsctr_user_ids_1() {
		return insctr_user_ids_1;
	}
	public void setInsctr_user_ids_1(String insctr_user_ids_1) {
		this.insctr_user_ids_1 = insctr_user_ids_1;
	}
	public String getInsctr_user_ids_2() {
		return insctr_user_ids_2;
	}
	public void setInsctr_user_ids_2(String insctr_user_ids_2) {
		this.insctr_user_ids_2 = insctr_user_ids_2;
	}
	public String getIns_ccpy_nm() {
		return ins_ccpy_nm;
	}
	public void setIns_ccpy_nm(String ins_ccpy_nm) {
		this.ins_ccpy_nm = ins_ccpy_nm;
	}
	public String getDetect_mthcd() {
		return detect_mthcd;
	}
	public void setDetect_mthcd(String detect_mthcd) {
		this.detect_mthcd = detect_mthcd;
	}
	public String getBegin_spot() {
		return begin_spot;
	}
	public void setBegin_spot(String begin_spot) {
		this.begin_spot = begin_spot;
	}
	public String getEnd_spot() {
		return end_spot;
	}
	public void setEnd_spot(String end_spot) {
		this.end_spot = end_spot;
	}
	public String getWeath() {
		return weath;
	}
	public void setWeath(String weath) {
		this.weath = weath;
	}
	public String getAtemp() {
		return atemp;
	}
	public void setAtemp(String atemp) {
		this.atemp = atemp;
	}
	public String getHumid() {
		return humid;
	}
	public void setHumid(String humid) {
		this.humid = humid;
	}
	public String getGnrlz_jdgmnt_gdbd() {
		return gnrlz_jdgmnt_gdbd;
	}
	public void setGnrlz_jdgmnt_gdbd(String gnrlz_jdgmnt_gdbd) {
		this.gnrlz_jdgmnt_gdbd = gnrlz_jdgmnt_gdbd;
	}
	public String getGnrlz_opnin() {
		return gnrlz_opnin;
	}
	public void setGnrlz_opnin(String gnrlz_opnin) {
		this.gnrlz_opnin = gnrlz_opnin;
	}
	public String getAttfl_no() {
		return attfl_no;
	}
	public void setAttfl_no(String attfl_no) {
		this.attfl_no = attfl_no;
	}
	public String getAprv_no() {
		return aprv_no;
	}
	public void setAprv_no(String aprv_no) {
		this.aprv_no = aprv_no;
	}
	public String getImprmn_requst_no() {
		return imprmn_requst_no;
	}
	public void setImprmn_requst_no(String imprmn_requst_no) {
		this.imprmn_requst_no = imprmn_requst_no;
	}
	public String getCrt_dtm() {
		return crt_dtm;
	}
	public void setCrt_dtm(String crt_dtm) {
		this.crt_dtm = crt_dtm;
	}
	public String getCrt_user() {
		return crt_user;
	}
	public void setCrt_user(String crt_user) {
		this.crt_user = crt_user;
	}
	public String getUpd_dtm() {
		return upd_dtm;
	}
	public void setUpd_dtm(String upd_dtm) {
		this.upd_dtm = upd_dtm;
	}
	public String getUpd_user() {
		return upd_user;
	}
	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}
	public String getUnity_bizplc_cd() {
		return unity_bizplc_cd;
	}
	public void setUnity_bizplc_cd(String unity_bizplc_cd) {
		this.unity_bizplc_cd = unity_bizplc_cd;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getJunc_code() {
		return junc_code;
	}
	public void setJunc_code(String junc_code) {
		this.junc_code = junc_code;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public String getMangr_id() {
		return mangr_id;
	}
	public void setMangr_id(String mangr_id) {
		this.mangr_id = mangr_id;
	}
	public String getUnity_ins_no_001() {
		return unity_ins_no_001;
	}
	public void setUnity_ins_no_001(String unity_ins_no_001) {
		this.unity_ins_no_001 = unity_ins_no_001;
	}
	public String getAttfl_no_001() {
		return attfl_no_001;
	}
	public void setAttfl_no_001(String attfl_no_001) {
		this.attfl_no_001 = attfl_no_001;
	}
	public String getImprmn_requst_no_001() {
		return imprmn_requst_no_001;
	}
	public void setImprmn_requst_no_001(String imprmn_requst_no_001) {
		this.imprmn_requst_no_001 = imprmn_requst_no_001;
	}
	public String getGubun_001() {
		return gubun_001;
	}
	public void setGubun_001(String gubun_001) {
		this.gubun_001 = gubun_001;
	}
	public String getTid_001() {
		return tid_001;
	}
	public void setTid_001(String tid_001) {
		this.tid_001 = tid_001;
	}
	public String getEqp_no_001() {
		return eqp_no_001;
	}
	public void setEqp_no_001(String eqp_no_001) {
		this.eqp_no_001 = eqp_no_001;
	}
	public String getTins_result_secd() {
		return tins_result_secd;
	}
	public void setTins_result_secd(String tins_result_secd) {
		this.tins_result_secd = tins_result_secd;
	}
	public String getExamin_cn() {
		return examin_cn;
	}
	public void setExamin_cn(String examin_cn) {
		this.examin_cn = examin_cn;
	}
	public String getUnity_ins_no_024() {
		return unity_ins_no_024;
	}
	public void setUnity_ins_no_024(String unity_ins_no_024) {
		this.unity_ins_no_024 = unity_ins_no_024;
	}
	public String getImprmn_requst_no_024() {
		return imprmn_requst_no_024;
	}
	public void setImprmn_requst_no_024(String imprmn_requst_no_024) {
		this.imprmn_requst_no_024 = imprmn_requst_no_024;
	}
	public String getEqp_no_024() {
		return eqp_no_024;
	}
	public void setEqp_no_024(String eqp_no_024) {
		this.eqp_no_024 = eqp_no_024;
	}
	public String getFlight_lmp_knd() {
		return flight_lmp_knd;
	}
	public void setFlight_lmp_knd(String flight_lmp_knd) {
		this.flight_lmp_knd = flight_lmp_knd;
	}
	public String getFlight_lmp_no() {
		return flight_lmp_no;
	}
	public void setFlight_lmp_no(String flight_lmp_no) {
		this.flight_lmp_no = flight_lmp_no;
	}
	public String getSrcelct_knd() {
		return srcelct_knd;
	}
	public void setSrcelct_knd(String srcelct_knd) {
		this.srcelct_knd = srcelct_knd;
	}
	public String getCtrl_ban_gdbd_secd() {
		return ctrl_ban_gdbd_secd;
	}
	public void setCtrl_ban_gdbd_secd(String ctrl_ban_gdbd_secd) {
		this.ctrl_ban_gdbd_secd = ctrl_ban_gdbd_secd;
	}
	public String getSlrcl_gdbd_secd() {
		return slrcl_gdbd_secd;
	}
	public void setSlrcl_gdbd_secd(String slrcl_gdbd_secd) {
		this.slrcl_gdbd_secd = slrcl_gdbd_secd;
	}
	public String getSrgbtry_gdbd_secd() {
		return srgbtry_gdbd_secd;
	}
	public void setSrgbtry_gdbd_secd(String srgbtry_gdbd_secd) {
		this.srgbtry_gdbd_secd = srgbtry_gdbd_secd;
	}
	public String getRgist_gu_gdbd_secd() {
		return rgist_gu_gdbd_secd;
	}
	public void setRgist_gu_gdbd_secd(String rgist_gu_gdbd_secd) {
		this.rgist_gu_gdbd_secd = rgist_gu_gdbd_secd;
	}
	public String getCabl_gdbd_secd() {
		return cabl_gdbd_secd;
	}
	public void setCabl_gdbd_secd(String cabl_gdbd_secd) {
		this.cabl_gdbd_secd = cabl_gdbd_secd;
	}
	public String getGood_secd() {
		return good_secd;
	}
	public void setGood_secd(String good_secd) {
		this.good_secd = good_secd;
	}
	public String getUnity_ins_no_025() {
		return unity_ins_no_025;
	}
	public void setUnity_ins_no_025(String unity_ins_no_025) {
		this.unity_ins_no_025 = unity_ins_no_025;
	}
	public String getImprmn_requst_no_025() {
		return imprmn_requst_no_025;
	}
	public void setImprmn_requst_no_025(String imprmn_requst_no_025) {
		this.imprmn_requst_no_025 = imprmn_requst_no_025;
	}
	public String getEqp_no_025() {
		return eqp_no_025;
	}
	public void setEqp_no_025(String eqp_no_025) {
		this.eqp_no_025 = eqp_no_025;
	}
	public String getFlight_lmp_knd_025() {
		return flight_lmp_knd_025;
	}
	public void setFlight_lmp_knd_025(String flight_lmp_knd_025) {
		this.flight_lmp_knd_025 = flight_lmp_knd_025;
	}
	public String getFlight_lmp_no_025() {
		return flight_lmp_no_025;
	}
	public void setFlight_lmp_no_025(String flight_lmp_no_025) {
		this.flight_lmp_no_025 = flight_lmp_no_025;
	}
	public String getSrcelct_knd_025() {
		return srcelct_knd_025;
	}
	public void setSrcelct_knd_025(String srcelct_knd_025) {
		this.srcelct_knd_025 = srcelct_knd_025;
	}
	public String getFlck_co() {
		return flck_co;
	}
	public void setFlck_co(String flck_co) {
		this.flck_co = flck_co;
	}
	public String getLightg_stadiv_cd() {
		return lightg_stadiv_cd;
	}
	public void setLightg_stadiv_cd(String lightg_stadiv_cd) {
		this.lightg_stadiv_cd = lightg_stadiv_cd;
	}
	public String getGood_secd_025() {
		return good_secd_025;
	}
	public void setGood_secd_025(String good_secd_025) {
		this.good_secd_025 = good_secd_025;
	}
	public String getUnity_ins_no_027() {
		return unity_ins_no_027;
	}
	public void setUnity_ins_no_027(String unity_ins_no_027) {
		this.unity_ins_no_027 = unity_ins_no_027;
	}
	public String getEqp_no_027() {
		return eqp_no_027;
	}
	public void setEqp_no_027(String eqp_no_027) {
		this.eqp_no_027 = eqp_no_027;
	}
	public String getCl_no() {
		return cl_no;
	}
	public void setCl_no(String cl_no) {
		this.cl_no = cl_no;
	}
	public String getPwln_eqp_no() {
		return pwln_eqp_no;
	}
	public void setPwln_eqp_no(String pwln_eqp_no) {
		this.pwln_eqp_no = pwln_eqp_no;
	}
	public String getTtm_load() {
		return ttm_load;
	}
	public void setTtm_load(String ttm_load) {
		this.ttm_load = ttm_load;
	}
	public String getCndctr_co() {
		return cndctr_co;
	}
	public void setCndctr_co(String cndctr_co) {
		this.cndctr_co = cndctr_co;
	}
	public String getCndctr_sn() {
		return cndctr_sn;
	}
	public void setCndctr_sn(String cndctr_sn) {
		this.cndctr_sn = cndctr_sn;
	}
	public String getUnity_ins_no_028() {
		return unity_ins_no_028;
	}
	public void setUnity_ins_no_028(String unity_ins_no_028) {
		this.unity_ins_no_028 = unity_ins_no_028;
	}
	public String getImprmn_requst_no_028() {
		return imprmn_requst_no_028;
	}
	public void setImprmn_requst_no_028(String imprmn_requst_no_028) {
		this.imprmn_requst_no_028 = imprmn_requst_no_028;
	}
	public String getEqp_no_028() {
		return eqp_no_028;
	}
	public void setEqp_no_028(String eqp_no_028) {
		this.eqp_no_028 = eqp_no_028;
	}
	public String getStdr_rs() {
		return stdr_rs;
	}
	public void setStdr_rs(String stdr_rs) {
		this.stdr_rs = stdr_rs;
	}
	public String getGood_secd_028() {
		return good_secd_028;
	}
	public void setGood_secd_028(String good_secd_028) {
		this.good_secd_028 = good_secd_028;
	}
	public String getPmttr() {
		return pmttr;
	}
	public void setPmttr(String pmttr) {
		this.pmttr = pmttr;
	}
	public String getMsr_rs() {
		return msr_rs;
	}
	public void setMsr_rs(String msr_rs) {
		this.msr_rs = msr_rs;
	}
	public String getMsr_co() {
		return msr_co;
	}
	public void setMsr_co(String msr_co) {
		this.msr_co = msr_co;
	}
	public String getUnity_ins_no_029() {
		return unity_ins_no_029;
	}
	public void setUnity_ins_no_029(String unity_ins_no_029) {
		this.unity_ins_no_029 = unity_ins_no_029;
	}
	public String getImprmn_requst_no_029() {
		return imprmn_requst_no_029;
	}
	public void setImprmn_requst_no_029(String imprmn_requst_no_029) {
		this.imprmn_requst_no_029 = imprmn_requst_no_029;
	}
	public String getEqp_no_029() {
		return eqp_no_029;
	}
	public void setEqp_no_029(String eqp_no_029) {
		this.eqp_no_029 = eqp_no_029;
	}
	public String getCl_no_029() {
		return cl_no_029;
	}
	public void setCl_no_029(String cl_no_029) {
		this.cl_no_029 = cl_no_029;
	}
	public String getTy_secd() {
		return ty_secd;
	}
	public void setTy_secd(String ty_secd) {
		this.ty_secd = ty_secd;
	}
	public String getPhs_secd() {
		return phs_secd;
	}
	public void setPhs_secd(String phs_secd) {
		this.phs_secd = phs_secd;
	}
	public String getInsr_eqp_no() {
		return insr_eqp_no;
	}
	public void setInsr_eqp_no(String insr_eqp_no) {
		this.insr_eqp_no = insr_eqp_no;
	}
	public String getInsbty_lft() {
		return insbty_lft;
	}
	public void setInsbty_lft(String insbty_lft) {
		this.insbty_lft = insbty_lft;
	}
	public String getInsbty_rit() {
		return insbty_rit;
	}
	public void setInsbty_rit(String insbty_rit) {
		this.insbty_rit = insbty_rit;
	}
	public String getInsr_qy() {
		return insr_qy;
	}
	public void setInsr_qy(String insr_qy) {
		this.insr_qy = insr_qy;
	}
	public String getBad_insr_qy() {
		return bad_insr_qy;
	}
	public void setBad_insr_qy(String bad_insr_qy) {
		this.bad_insr_qy = bad_insr_qy;
	}
	public String getGood_secd_029() {
		return good_secd_029;
	}
	public void setGood_secd_029(String good_secd_029) {
		this.good_secd_029 = good_secd_029;
	}
	public String getUnity_ins_no_021() {
		return unity_ins_no_021;
	}
	public void setUnity_ins_no_021(String unity_ins_no_021) {
		this.unity_ins_no_021 = unity_ins_no_021;
	}
	public String getEqp_no_021() {
		return eqp_no_021;
	}
	public void setEqp_no_021(String eqp_no_021) {
		this.eqp_no_021 = eqp_no_021;
	}
	public String getGood_secd_00001() {
		return good_secd_00001;
	}
	public void setGood_secd_00001(String good_secd_00001) {
		this.good_secd_00001 = good_secd_00001;
	}
	public String getGood_secd_00002() {
		return good_secd_00002;
	}
	public void setGood_secd_00002(String good_secd_00002) {
		this.good_secd_00002 = good_secd_00002;
	}
	public String getGood_secd_00003() {
		return good_secd_00003;
	}
	public void setGood_secd_00003(String good_secd_00003) {
		this.good_secd_00003 = good_secd_00003;
	}
	public String getGood_secd_00004() {
		return good_secd_00004;
	}
	public void setGood_secd_00004(String good_secd_00004) {
		this.good_secd_00004 = good_secd_00004;
	}
	public String getGood_secd_00005() {
		return good_secd_00005;
	}
	public void setGood_secd_00005(String good_secd_00005) {
		this.good_secd_00005 = good_secd_00005;
	}
	public String getGood_secd_00006() {
		return good_secd_00006;
	}
	public void setGood_secd_00006(String good_secd_00006) {
		this.good_secd_00006 = good_secd_00006;
	}
	public String getGood_secd_00007() {
		return good_secd_00007;
	}
	public void setGood_secd_00007(String good_secd_00007) {
		this.good_secd_00007 = good_secd_00007;
	}
	public String getGood_secd_00008() {
		return good_secd_00008;
	}
	public void setGood_secd_00008(String good_secd_00008) {
		this.good_secd_00008 = good_secd_00008;
	}
	public String getGood_secd_00009() {
		return good_secd_00009;
	}
	public void setGood_secd_00009(String good_secd_00009) {
		this.good_secd_00009 = good_secd_00009;
	}
	public String getGood_secd_00010() {
		return good_secd_00010;
	}
	public void setGood_secd_00010(String good_secd_00010) {
		this.good_secd_00010 = good_secd_00010;
	}
	public String getGood_secd_00011() {
		return good_secd_00011;
	}
	public void setGood_secd_00011(String good_secd_00011) {
		this.good_secd_00011 = good_secd_00011;
	}
	public String getGood_secd_00012() {
		return good_secd_00012;
	}
	public void setGood_secd_00012(String good_secd_00012) {
		this.good_secd_00012 = good_secd_00012;
	}
	public String getGood_secd_00013() {
		return good_secd_00013;
	}
	public void setGood_secd_00013(String good_secd_00013) {
		this.good_secd_00013 = good_secd_00013;
	}
	public String getGood_secd_00014() {
		return good_secd_00014;
	}
	public void setGood_secd_00014(String good_secd_00014) {
		this.good_secd_00014 = good_secd_00014;
	}
	public String getGood_secd_00015() {
		return good_secd_00015;
	}
	public void setGood_secd_00015(String good_secd_00015) {
		this.good_secd_00015 = good_secd_00015;
	}
	public String getGood_secd_00016() {
		return good_secd_00016;
	}
	public void setGood_secd_00016(String good_secd_00016) {
		this.good_secd_00016 = good_secd_00016;
	}
	public String getGood_secd_00017() {
		return good_secd_00017;
	}
	public void setGood_secd_00017(String good_secd_00017) {
		this.good_secd_00017 = good_secd_00017;
	}
	public String getGood_secd_00018() {
		return good_secd_00018;
	}
	public void setGood_secd_00018(String good_secd_00018) {
		this.good_secd_00018 = good_secd_00018;
	}
	public String getImprmn_requst_no_021() {
		return imprmn_requst_no_021;
	}
	public void setImprmn_requst_no_021(String imprmn_requst_no_021) {
		this.imprmn_requst_no_021 = imprmn_requst_no_021;
	}
	public String getIns_itm_cd() {
		return ins_itm_cd;
	}
	public void setIns_itm_cd(String ins_itm_cd) {
		this.ins_itm_cd = ins_itm_cd;
	}
	public String getGood_secd_021() {
		return good_secd_021;
	}
	public void setGood_secd_021(String good_secd_021) {
		this.good_secd_021 = good_secd_021;
	}
	public String getMsr_odr_1() {
		return msr_odr_1;
	}
	public void setMsr_odr_1(String msr_odr_1) {
		this.msr_odr_1 = msr_odr_1;
	}
	public String getMsr_odr_2() {
		return msr_odr_2;
	}
	public void setMsr_odr_2(String msr_odr_2) {
		this.msr_odr_2 = msr_odr_2;
	}
	public String getMsr_odr_3() {
		return msr_odr_3;
	}
	public void setMsr_odr_3(String msr_odr_3) {
		this.msr_odr_3 = msr_odr_3;
	}
	public String getMsr_odr_5() {
		return msr_odr_5;
	}
	public void setMsr_odr_5(String msr_odr_5) {
		this.msr_odr_5 = msr_odr_5;
	}
	public String getMsr_odr_10() {
		return msr_odr_10;
	}
	public void setMsr_odr_10(String msr_odr_10) {
		this.msr_odr_10 = msr_odr_10;
	}
	public String getMsr_odr() {
		return msr_odr;
	}
	public void setMsr_odr(String msr_odr) {
		this.msr_odr = msr_odr;
	}
	public String getPwln_eqp_no_c1() {
		return pwln_eqp_no_c1;
	}
	public void setPwln_eqp_no_c1(String pwln_eqp_no_c1) {
		this.pwln_eqp_no_c1 = pwln_eqp_no_c1;
	}
	public String getPwln_eqp_no_c2() {
		return pwln_eqp_no_c2;
	}
	public void setPwln_eqp_no_c2(String pwln_eqp_no_c2) {
		this.pwln_eqp_no_c2 = pwln_eqp_no_c2;
	}
	public String getPwln_eqp_no_c3() {
		return pwln_eqp_no_c3;
	}
	public void setPwln_eqp_no_c3(String pwln_eqp_no_c3) {
		this.pwln_eqp_no_c3 = pwln_eqp_no_c3;
	}
	public String getCabl_tp_c1() {
		return cabl_tp_c1;
	}
	public void setCabl_tp_c1(String cabl_tp_c1) {
		this.cabl_tp_c1 = cabl_tp_c1;
	}
	public String getCabl_tp_c2() {
		return cabl_tp_c2;
	}
	public void setCabl_tp_c2(String cabl_tp_c2) {
		this.cabl_tp_c2 = cabl_tp_c2;
	}
	public String getCabl_tp_c3() {
		return cabl_tp_c3;
	}
	public void setCabl_tp_c3(String cabl_tp_c3) {
		this.cabl_tp_c3 = cabl_tp_c3;
	}
	public String getCnpt_tp_c1() {
		return cnpt_tp_c1;
	}
	public void setCnpt_tp_c1(String cnpt_tp_c1) {
		this.cnpt_tp_c1 = cnpt_tp_c1;
	}
	public String getCnpt_tp_c2() {
		return cnpt_tp_c2;
	}
	public void setCnpt_tp_c2(String cnpt_tp_c2) {
		this.cnpt_tp_c2 = cnpt_tp_c2;
	}
	public String getCnpt_tp_c3() {
		return cnpt_tp_c3;
	}
	public void setCnpt_tp_c3(String cnpt_tp_c3) {
		this.cnpt_tp_c3 = cnpt_tp_c3;
	}
	public String getGood_secd_027_c1() {
		return good_secd_027_c1;
	}
	public void setGood_secd_027_c1(String good_secd_027_c1) {
		this.good_secd_027_c1 = good_secd_027_c1;
	}
	public String getGood_secd_027_c2() {
		return good_secd_027_c2;
	}
	public void setGood_secd_027_c2(String good_secd_027_c2) {
		this.good_secd_027_c2 = good_secd_027_c2;
	}
	public String getGood_secd_027_c3() {
		return good_secd_027_c3;
	}
	public void setGood_secd_027_c3(String good_secd_027_c3) {
		this.good_secd_027_c3 = good_secd_027_c3;
	}
	public String getImprmn_requst_no_027() {
		return imprmn_requst_no_027;
	}
	public void setImprmn_requst_no_027(String imprmn_requst_no_027) {
		this.imprmn_requst_no_027 = imprmn_requst_no_027;
	}
	public String getCabl_tp() {
		return cabl_tp;
	}
	public void setCabl_tp(String cabl_tp) {
		this.cabl_tp = cabl_tp;
	}
	public String getCnpt_tp() {
		return cnpt_tp;
	}
	public void setCnpt_tp(String cnpt_tp) {
		this.cnpt_tp = cnpt_tp;
	}
	public String getGood_secd_027() {
		return good_secd_027;
	}
	public void setGood_secd_027(String good_secd_027) {
		this.good_secd_027 = good_secd_027;
	}
	
	/**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }
}

