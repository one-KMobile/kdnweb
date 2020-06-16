package kdn.cmm.util;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.view.AbstractView;

/*
public class FileDownloadView extends AbstractView {

	@Autowired 
	@Qualifier("fileManager")
	private FileManager fileManager;
	
	@Autowired
	@Qualifier("fileManagerService")
	private FileManagerService fileManagerService;
	
	static Logger logger = Logger.getLogger(FileDownloadView.class);
	
	@Override
	protected void renderMergedOutputModel(Map model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		File file = (File) model.get("file_realpath");
		response.setContentType("application/otest-stream");
		response.setContentLength((int) file.length());
		
		//String fileName = file.getName();
		String fileName = (String)model.get("original_name");
		fileName = URLEncoder.encode(fileName, "utf-8");
		response.setHeader("Content-Disposition", "attachment;fileName=\""	+ fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
		}
		out.flush();
		Box box = BoxUtil.getBox(request);
		String saveFileName = null;
		String originalFileName = null;
		Box fileBox = fileManagerService.getFileName(box);
		if(fileBox != null && !"".equals(fileBox.getString("original_name"))){
			saveFileName = fileBox.getString("file_realpath");
			originalFileName = fileBox.getString("original_name");
		}
		
//		logger.debug("@@@@@@@@@@@@@@@@@@@@ saveFileName : "+saveFileName+" / file_id : "+box.getString("file_id"));
//		logger.debug("@@@@@@@@@@@@@@@@@@@@ originalFileName : "+originalFileName+" / file_seq : "+box.getString("file_seq"));
		fileManager.doFileDownload(saveFileName, originalFileName, response);
	}
}*/