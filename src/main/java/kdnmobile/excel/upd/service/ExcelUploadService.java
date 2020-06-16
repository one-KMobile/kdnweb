package kdnmobile.excel.upd.service;

import java.util.List;


/**
 * 게시물 관리를 위한 서비스 인터페이스  클래스
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
 *
 * </pre>
 */
public interface ExcelUploadService {

	/**
  	 * <설명> 엑셀 순시결과 저장
  	 * 
  	 * @param [TotInsExcelVO]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [범승철]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
    public void insertInsTot(List<TotInsExcelVO> param) throws Exception;

}
