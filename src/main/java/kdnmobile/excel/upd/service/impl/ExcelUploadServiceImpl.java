package kdnmobile.excel.upd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdnmobile.excel.upd.service.ExcelUploadService;
import kdnmobile.excel.upd.service.TotInsExcelVO;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 게시물 관리를 위한 서비스 구현 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *	 2011.09.22 서준식          nttId IdGen 서비스로 변경
 * </pre>
 */
@Service("ExcelUploadService")
public class ExcelUploadServiceImpl extends AbstractServiceImpl implements ExcelUploadService {

    @Resource(name = "ExcelUploadDAO")
    private ExcelUploadDAO excelUploadDAO;

    /**
  	 * <설명> 엑셀 순시결과 저장
  	 * 
  	 * @param [TotInsExcelVO]
  	 * @throws [예외명] [설명] // 각 예외 당 하나씩
  	 * @author [범승철]
  	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
  	 */
    public void insertInsTot(List<TotInsExcelVO> param) throws Exception {
    	excelUploadDAO.insertInsTot(param);
    }
    

}
