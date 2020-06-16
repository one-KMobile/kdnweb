package kdnmobile.excel.upd.service;

public class ExcelVO {
	
	private String file_path; /* 업로드 파일 경로 */
	private String file_name; /* 업로드 파일 이름 */
	private String upld_optn; /*업로드 옵션*/
	private String ins_type; /* 순시종류 */
	private String tower_no; /* 철탑번호 */
	private String weather; /* 날씨 */
	private String cooperation_count; /* 협력인원수 */
	private String defect_cont; /* 불량내용 */
	private String check_state; /* 점검상태 */
	
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getUpld_optn() {
		return upld_optn;
	}
	public void setUpld_optn(String upld_optn) {
		this.upld_optn = upld_optn;
	}
	public String getIns_type() {
		return ins_type;
	}
	public void setIns_type(String ins_type) {
		this.ins_type = ins_type;
	}
	public String getTower_no() {
		return tower_no;
	}
	public void setTower_no(String tower_no) {
		this.tower_no = tower_no;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getCooperation_count() {
		return cooperation_count;
	}
	public void setCooperation_count(String cooperation_count) {
		this.cooperation_count = cooperation_count;
	}
	public String getDefect_cont() {
		return defect_cont;
	}
	public void setDefect_cont(String defect_cont) {
		this.defect_cont = defect_cont;
	}
	public String getCheck_state() {
		return check_state;
	}
	public void setCheck_state(String check_state) {
		this.check_state = check_state;
	}

}

