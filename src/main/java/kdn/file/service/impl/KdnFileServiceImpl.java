package kdn.file.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kdn.cmm.box.Box;
import kdn.file.service.KdnFileService;

@Service("kdnFileService")
public class KdnFileServiceImpl implements KdnFileService {

	@Resource(name="kdnFileDAO")
	private KdnFileDAO kdnFileDAO;
	
    /**
	 * 그룹 파일 관리 입력
	 * @param box - [group_file_seq
							, group_file_id
							, group_file_name
							, group_file_realpath
							, reg_id
							, reg_date]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public Object setGroupFileInsert(Box box) throws Exception {
		return kdnFileDAO.setGroupFileInsert(box);
	}
	
    /**
	 * 파일 관리 입력
	 * @param box - [group_file_seq
							, group_file_id
							, group_file_name
							, group_file_realpath
							, reg_id
							, reg_date]
	 * @return Object
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public Object setFileInsert(Box box) throws Exception {
		return kdnFileDAO.setFileInsert(box);
	}
	
    /**
	 * 파일 아이디 MAX 값 호출
	 * @param box - [group_file_id]
	 * @return int - [file_id]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public Box getMaxFileId(Box box) throws Exception {
		return kdnFileDAO.getMaxFileId(box);
	}
	
    /**
	 * 선택된 group_file_id로 검색
	 * @param box - [group_file_id]
	 * @return box - [*]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public Box getGroupFileInfo(Box box) throws Exception {
		return kdnFileDAO.getGroupFileInfo(box);
	}
	
    /**
	 * 그룹 파일 삭제
	 * @param box - [group_file_id]
	 * @return int - [*]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 	
	public int setGroupFileDelete(Box box) throws Exception {
		return kdnFileDAO.setGroupFileDelete(box);
	}
	
    /**
	 * 파일 삭제
	 * @param box - [group_file_id]
	 * @return int - [*]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public int setFileDelete(Box box) throws Exception {
		return kdnFileDAO.setFileDelete(box);
	}
	
    /**
	 * 파일이름으로 중복체크
	 * @param box - [file_name]
	 * @return int
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public int isExistFileName(Box box) throws Exception {
		return kdnFileDAO.isExistFileName(box);
	}
	
    /**
	 * 파일명, 파일경로 검색
	 * @param box - [group_file_id]
	 * @return box - [file_name, file_realpath]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩 
	 * @author [정현도] 
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public List<Box> getFileInfo(Box box) throws Exception {
		return kdnFileDAO.getFileInfo(box);
	}
}
