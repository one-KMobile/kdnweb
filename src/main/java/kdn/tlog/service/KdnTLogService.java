package kdn.tlog.service;

import java.util.List;

import kdn.cmm.box.Box;

public interface KdnTLogService {

	/**
	 * <설명> [순시자 접속 로그표] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	List<Box> getTLogDataList(Box box);
	
}
