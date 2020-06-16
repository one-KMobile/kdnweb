package kdn.insResult.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import kdn.auth.service.KdnAuthService;
import kdn.cmm.box.Box;
import kdn.file.service.KdnFileService;

@Component("kdnInsResultMethod")
public class KdnInsResultMethod {

	@Resource(name="kdnFileService")
	private KdnFileService kdnFileService;
	
    /**
	 * [순시결과] 파일정보 호출
	 * @param [list - 순시결과 리스트]
	 * @return [list - 파일결과 리스트]
	 * @exception Exception
	 * @author [정현도] 
   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public List<Box> getFileInfo(List<Box> list) throws Exception {
		List<Box> fileList = new ArrayList<Box>();
		for(int i=0; i<list.size(); i++) {
			Box subBox = list.get(i);
			if("".equals(subBox.getString("group_file_id"))) {
				continue;
			}else {
				fileList = kdnFileService.getFileInfo(subBox);
			}
		}
		return fileList;
	}
}
