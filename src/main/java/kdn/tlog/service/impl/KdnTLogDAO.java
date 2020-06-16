package kdn.tlog.service.impl;

import java.util.List;

import kdn.cmm.KdnSqlMapClient;
import kdn.cmm.box.Box;

import org.springframework.stereotype.Repository;

@Repository("kdnTLogDAO")
public class KdnTLogDAO extends KdnSqlMapClient {
	
	/**
	 * <설명> [순시자 접속 로그표] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	@SuppressWarnings("unchecked")
	public List<Box> getTLogDataList(Box box) {
		return list("TLog.getTLogData", box);
	}

}
