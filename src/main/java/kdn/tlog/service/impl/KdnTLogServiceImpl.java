package kdn.tlog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kdn.cmm.box.Box;
import kdn.tlog.service.KdnTLogService;

import org.springframework.stereotype.Service;

@Service("kdnTLogService")
public class KdnTLogServiceImpl implements KdnTLogService{
	
	@Resource(name="kdnTLogDAO")
	private KdnTLogDAO kdnTLogDAO;
	
	/**
	 * <설명> [순시자 접속 로그표] 목록 조회
	 * 
	 * @param [box]
	 * @return [List]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public List<Box> getTLogDataList(Box box) {
		return kdnTLogDAO.getTLogDataList(box);
	}
	
}
