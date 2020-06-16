package kdn.cmm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kdn.cmm.box.Box;
import kdn.file.service.KdnFileService;
import kdn.login.web.LoginMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.property.EgovPropertyService;

@Service("fileManagerService")
public class FileManagerService {
	
	@Autowired private FileManager fileManager;
	
	@Resource(name="loginMethod")
	private LoginMethod loginMethod;
	
	@Autowired
	@Qualifier("kdnFileService")
	private KdnFileService kdnFileService;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
	
	static Logger logger = Logger.getLogger(FileManagerService.class);
	
	//String commonPath = File.separator.concat("home").concat(File.separator).concat("tomcat").concat(File.separator).concat("apache-tomcat").concat(File.separator).concat("kdnweb").concat(File.separator).concat("images").concat(File.separator).concat("InsResult").concat(File.separator);
	
	/**
	 * id로 지정된 property 파일에서 읽어오기  
	@Value("#{config['upload.system.path']}")
	 private String fileSystemPath;
	*/
	
	 /**
     * insertFile
     * @param box
     * @param request
     * @see 파일 입력
     */
	public int insertFile(Box box,HttpServletRequest request,String fileFolder){
		MultipartHttpServletRequest multipartRequest =  null;
		ArrayList<String> cdnFilePathList = new ArrayList<String>();
		int file_id = 0;
		
		try {
			multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
			List<MultipartFile> files = multipartRequest.getFiles("result_file");
			for(int i = 0 ; i < files.size(); i++){
	    		Box filesBox = (Box) files.get(i);
//	    		if(file != null){
//	    			logger.info("file.getName() : "+file.getName());
//	    		}
	    	//	if(file != null && file.getSize() > 0){
	    			String fileNewRealPath = "";
	    			String fileRealPath = "";
	    			//시스템의 맞는 파일, 파일명 생성(/home/tomcat6/app/hongmessenger/WebContent/upload/)
	    			//fileRealPath = doFileUpload(file.getBytes(), file.getOriginalFilename(), realPath);

	    			//디비의 맞는 파일명 생성(/upload/)
	    			//fileNewRealPath = fileRealPath.replaceAll(uploadSystemPath, uploadSystemNewPath);
//	    			logger.info("fileNewPath 확인! : " + fileNewRealPath);
					/*if("".equals(box.getString("file_id")) || box.getInt("file_id") == 0 ){
						this.sqlSession1.insert("File.insertFILE_SEQ");
						file_id = this.sqlSession1.selectOne("File.getFILE_SEQ");
						this.sqlSession1.delete("File.deleteFILE_SEQ");
						box.put("file_id", file_id);
					}*/
					
					if(fileRealPath != null){
						
						box.put("file_realpath", fileNewRealPath);
					//	box.put("original_name", file.getOriginalFilename());
						String displayFileExt = fileNewRealPath.substring(fileNewRealPath.lastIndexOf(".")+1,fileNewRealPath.length());
						if(!("avi".equals(displayFileExt.toLowerCase()) || "mpg".equals(displayFileExt.toLowerCase())
							|| "mpeg".equals(displayFileExt.toLowerCase()) || "mpe".equals(displayFileExt.toLowerCase())
							|| "wmv".equals(displayFileExt.toLowerCase()) || "asf".equals(displayFileExt.toLowerCase())
							|| "asx".equals(displayFileExt.toLowerCase()) || "swf".equals(displayFileExt.toLowerCase())
							|| "flv".equals(displayFileExt.toLowerCase()) || "rm".equals(displayFileExt.toLowerCase())
							|| "mov".equals(displayFileExt.toLowerCase()) || "3gp".equals(displayFileExt.toLowerCase()) 
							|| "swf".equals(displayFileExt.toLowerCase()) || "mp4".equals(displayFileExt.toLowerCase())
							|| "vod".equals(displayFileExt.toLowerCase()) )) {
							//작은 이미지 url변경
							//시스템의 맞는 피일명을 리사이즈 후에 디비의 맞는 파일명으로 변환
					//		String smallFileRealPath = getChangeSystemURL(fileManager.thumJAIResizeCreate(fileRealPath, 150, 150));;
							
							//중간 이미지 url변경
							//시스템의 맞는 피일명을 리사이즈 후에 디비의 맞는 파일명으로 변환
						//	String middleFileRealPath = getChangeSystemURL(fileManager.thumJAIResizeCreate(fileRealPath, 700, 700));
							
							//DB의 맞는 URL 적용
						//	box.put("small_file_realpath", smallFileRealPath); //이미지파일 리사이즈(small)
						//	box.put("middle_file_realpath", middleFileRealPath); //이미지 파일 리사이즈(middle)
						}	
						//디비의 파일경로명으로 생성해야 됨
						File originalFile = new File(fileRealPath);
//						System.out.println("@@@@@@@originalFile@@@@@@@@="+originalFile.exists());
//						System.out.println("@@@@@@@getAbsolutePath@@@@@@@@="+originalFile.getAbsolutePath());
					//}
						
						//file_name = box.getString("original_name");
						//file_name = file_name.substring(file_name.lastIndexOf(".")+1,file_name.length());
						//System.out.println("***************************file_name="+ file_name);
						
						box.put("file_seq", i);
						
						String smallFileRealPathGDB = "";
							if(fileFolder != null && "GDB".equals(fileFolder)){
								
								if(i == 2){
									box.put("file_cdn", fileNewRealPath);
								}
								
							/*	smallFileRealPathGDB = getChangeSystemURL(fileManager.thumJAIResizeCreate(fileRealPath, 200, 150));
								box.put("small_file_realpath", smallFileRealPathGDB); //이미지파일 리사이즈(small)
							}else if(fileFolder != null && "EMOTICON".equals(fileFolder)){ //이모티콘 썸네일 크기 조정(20130825)
								smallFileRealPathGDB = getChangeSystemURL(fileManager.thumJAIResizeCreate(fileRealPath, 40, 40));
								box.put("small_file_realpath", smallFileRealPathGDB); //이미지파일 리사이즈(small)
							}else if(fileFolder != null && "STICKER".equals(fileFolder)){ //스티커 썸네일 크기 조정(20130825)
								smallFileRealPathGDB = getChangeSystemURL(fileManager.thumJAIResizeCreate(fileRealPath, 150, 150));
								box.put("small_file_realpath", smallFileRealPathGDB); //이미지파일 리사이즈(small)
							}else{
								// fileRealPath file_ext를 첵크하여 avi , mp4 ,vod ,flv , mov 확장자 소문자로 바꾼후 해당하는 확장자만 cdn 업로드 
								String fileExt = null;
								fileExt = fileRealPath.substring(fileRealPath.lastIndexOf(".")+1,fileRealPath.length());
								if(fileExt != null && (fileExt.toLowerCase().equals("avi") || fileExt.toLowerCase().equals("swf") || fileExt.toLowerCase().equals("mov") || fileExt.toLowerCase().equals("mp4") || fileExt.toLowerCase().equals("vod") || fileExt.toLowerCase().equals("flv"))){
									cdnFilePathList.add(fileRealPath);
								}
							}*/
						}
						
						/*Box fileBox = this.sqlSession1.selectOne("File.selectFile", box);
						if(fileBox != null && !"".equals(fileBox.getString("original_name"))){
							fileManager.doFileDelete(fileBox.getString("file_realpath")); //파일 삭제
							this.sqlSession1.delete("File.deleteFILE", box);
							this.sqlSession1.insert("File.insertFILE", box);							
						}else{
							this.sqlSession1.insert("File.insertFILE", box);
	
						}*/
						
					}
	    		}    		
	    //	}
			if(cdnFilePathList != null && cdnFilePathList.size() > 0){
			//	ftpTransferService.putFile(cdnFilePathList,"group/"+box.getString("group_idx"));
				
				/*if(isTransfer == true){
					String cdn_url = "http://gvod.hong21.com/gdb/"+StringUtil.getPathFileName(box.getString("file_cdn"));
					//String cdn_url = "http://mobilecdn.phoenixdart.com/phoenixdart/gdb/"+StringUtil.getPathFileName(box.getString("file_cdn"));
					System.out.println("@@@@@@@@@@@@@@@@@@@@222="+ cdn_url);
				}*/
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file_id;
	}

	// path : 파일을 저장할 경로
	// 리턴 : 서버에 저장된 새로운 파일명
	public String doFileUpload(byte[] bytes, String originalFileName, String path) throws Exception {
		if(bytes == null){
			return null;
		}
		
		// 클라이언트가 업로드한 파일의 이름
		if(originalFileName.equals("")){
			return null;
		}
		
		// 확장자
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		if(fileExt == null || fileExt.equals("")){
			return null;
		}
		StringBuffer str = new StringBuffer();
		// 서버에 저장할 새로운 파일명을 만든다.
		str.append(String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
		str.append(System.nanoTime());
		str.append(fileExt);
		
		// 업로드할 경로가 존재하지 않는 경우 폴더를 생성 한다.
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		StringBuffer fullpathname = new StringBuffer();
		fullpathname.append(path).append("/").append(str.toString());
		
		FileOutputStream fos = new FileOutputStream(fullpathname.toString());
		fos.write(bytes);
		fos.close();
		
		return fullpathname.toString();
	}
	
	// 파일 다운로드
	// saveFileName : 서버에 저장된 파일명
	// originalFileName : 클라이언트가 업로드한 파일명
	// path : 서버에 저장된 경로
	public boolean doFileDownload(String saveFileName, String originalFileName, HttpServletResponse response) {
		
		String load_dir = saveFileName;
		StringBuffer str = new StringBuffer();
        try {
    		if(originalFileName == null || "".equals(originalFileName)){
    			str.append(String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
	    		str.append(System.nanoTime());
	    		str.append(".err");
				originalFileName = str.toString();
				//originalFileName = new String(originalFileName.getBytes("euc-kr"),"8859_1");
    		}
        } catch (Exception e) {
        }

	    try {
	        File file = new File(load_dir);

	        if (file.exists()){
	            byte readByte[] = new byte[4096];

	            response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition","attachment;filename=" + originalFileName);

	            BufferedInputStream  fin  = new BufferedInputStream(new FileInputStream(file));
	            //javax.servlet.ServletOutputStream outs =	response.getOutputStream();
	            OutputStream outs = response.getOutputStream();
	            
	   			int read;
	    		while ((read = fin.read(readByte, 0, 4096)) != -1)
	    				outs.write(readByte, 0, read);
	    		outs.flush();
	    		outs.close();
	            fin.close();
	            
	            return true;
	        }
	    } catch(Exception e) {
	    }
	    
	    return false;
	}
	
	// 파일 다운로드
	// saveFileName : 서버에 저장된 파일명
	// originalFileName : 클라이언트가 업로드한 파일명
	// path : 서버에 저장된 경로
/*	public Box getFileName(Box box) {
		Box fileBox = this.sqlSession1.selectOne("File.selectFile", box);
	    return fileBox;
	}
	*/
	// 실제 파일 삭제
	/*public void doFileDelete(String fileName, String path) throws Exception {
		File file = null;
		String fullFileName = path + File.separator + fileName;
        file = new java.io.File(fullFileName);
        if (file.exists()){
           file.delete();
        }
	}	*/

	
	/**
	 * <설명>
	 * 파일경로로 파일 삭제
	 * @param [String] [fileRealPath]
	 * @return [void]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [김정수]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	/*public void doFileDelete(String fileRealPath) throws Exception {
		String fileRealPathResult = "";
		if(fileRealPath.contains(webReplacePath)) {
			fileRealPathResult = fileRealPath;
		}else {
			fileRealPathResult = webReplacePath.concat(fileRealPath);
		}
		fileManager.doFileDelete(fileRealPathResult); //파일 삭제
	}	*/
	
	 /**
	 * <설명> 
	 * 파일 저장 파라메터가 file로 넘겨져온 것만 처리  
	 * @param [request] [fileFolder]
	 * @return [String]
	 * @throws [모든예외 던짐]
	 * @author [범승철]
	 * @fix(<김정수>) [2013.03.20]: [파라메터가 photo로 넘겨져온 것도 처리]
	 */ 
	/*public String saveFile(Box box,HttpServletRequest request,String fileFolder) {
		String fileRealPath = null;
		try{
			MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
			String realPath = "c:\\" + fileFolder;
			MultipartFile file = multipartRequest.getFile("file");
			
			if(file == null || file.getSize() == 0){ // 회원 프로필 수정시 photo 파라메터를 받기 때문에 생성한 줄 
				file = multipartRequest.getFile("result_file");
			}
			
			if(file != null && file.getSize() > 0){
				//시스템에 저장될 때 경로 변경
				fileRealPath = doFileUpload(file.getBytes(),file.getOriginalFilename(),realPath);
			}
		}catch (Exception e) {
			logger.info("파일 업로드주 오류 발생 :" + e.getMessage());
		}
		return fileRealPath;
	}	*/
	
	 /**
	 * <설명> 
	 * 파일 저장 파라메터가 file로 넘겨져온 것만 처리  
	 * @param [request] [fileFolder] [group_file_id] [user_id]
	 * @return [String]
	 * @throws [모든예외 던짐]
	 * @author [정현도]
	 */ 
	/*public void saveFile(Box box,HttpServletRequest request,String fileFolder, String group_file_id, String user_id) {
		MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
		int count = 0;
		for(int i=0; i<10; i++) {
			if(!"".equals(box.getString("result_file").concat(String.valueOf(i)))) {
				count+=1;
			}else {
				count+=0;
			}
		}
		List<MultipartFile> files = multipartRequest.getFiles("result_file");
		String arrayFiles[] = new String[files.size()];
		try{
			String commonPath = propertyService.getString("Globals.kdnFileStorePath");
			String realPath = commonPath.concat(fileFolder);
			//그룹파일디비 생성
			Box groupFileBox = new Box(); 
			groupFileBox.put("group_file_id", group_file_id);
			groupFileBox.put("group_file_name", fileFolder);
			groupFileBox.put("group_file_realpath", realPath);
			groupFileBox.put("reg_id", user_id);
			Box groupFileInfo = kdnFileService.getGroupFileInfo(groupFileBox);
			if(groupFileInfo == null) {
				kdnFileService.setGroupFileInsert(groupFileBox);
			}
			int file_id = 0;
			for(int i=0; i<files.size(); i++) {
				//시스템에 저장될 때 경로
				MultipartFile file = files.get(i);
				String fileRealPath = doFileUpload(file.getBytes(),file.getOriginalFilename(),realPath);
				arrayFiles[i] = fileRealPath;
				
				//파일 아이디 MAX 값 호출
				Box maxFileId = kdnFileService.getMaxFileId(groupFileBox);
				if(maxFileId == null) {
					file_id = 1;
				}else {
					file_id = maxFileId.getInt("FILE_ID") + 1;
				}
				//파일디비 생성
				Box fileBox = new Box();
				fileBox.put("file_id", file_id);
				fileBox.put("group_file_id", group_file_id);
				fileBox.put("file_name", file.getOriginalFilename());
				fileBox.put("file_realpath", fileRealPath);
				fileBox.put("reg_id", user_id);
				kdnFileService.setFileInsert(fileBox);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("파일 업로드주 오류 발생 :" + e.getMessage());
		}
	}	*/
	
	/**
	 * <설명> 
	 * 시스템 파일 삭제
	 * @throws [모든예외 던짐]
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public boolean deleteFile(Box box, HttpServletRequest request, String fileFolder, String group_file_id) throws Exception{
		MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
		boolean isDelete = false;
		if(multipartRequest != null) {
			//String commonPath = propertyService.getString("Globals.kdnFileStorePathTemp");
			//String commonPath = "D:/kdnweb/images/InsResult/";
			//리눅스 서버
			String commonPath = propertyService.getString("Globals.kdnFileStorePath");
			String realPath = "";
			if("PREVENTING_INSPECTION".equals(fileFolder)) {
				realPath = commonPath.concat(fileFolder).concat("/").concat(box.getString("rgt_sn"));
			}else {
				realPath = commonPath.concat(fileFolder).concat("/").concat(box.getString("eqp_no"));
			}
			File filePath = new File(realPath);
			isDelete = fileManager.deleteDirectory(filePath);
			box.put("group_file_id", group_file_id);
		}
		return isDelete;
	}
	
	/**
	 * <설명> 
	 * DB 파일 삭제
	 * @throws [모든예외 던짐]
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */		
	public boolean deleteFileDB(Box box) throws Exception {
		Box isExistFile = kdnFileService.getGroupFileInfo(box);
		boolean isDelect = false;
		if(isExistFile != null) {
			kdnFileService.setFileDelete(box);
			kdnFileService.setGroupFileDelete(box);
			isDelect = true;
		}
		return isDelect;
	}
	public void saveFile(Box box,HttpServletRequest request,String fileFolder, String group_file_id, String user_id) {
		MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
		int count = 0;
		for(int i=1; i<50; i++) {
			if(multipartRequest.getFile("result_file" + i) != null) {
				count+=1;
			}else {
				break;
			}
		}
		//List<MultipartFile> files = multipartRequest.getFiles("result_file");
		String arrayFiles[] = new String[count+1];
		try{
			
			//리눅스 서버
			String commonPath = propertyService.getString("Globals.kdnFileStorePath");
			//String commonPath = File.separator.concat("home").concat(File.separator).concat("tomcat").concat(File.separator).concat("apache-tomcat").concat(File.separator).concat("kdnweb").concat(File.separator).concat("images").concat(File.separator).concat("InsResult").concat(File.separator);
			//윈도우 서버
			//String commonPath = "D:/kdnweb/images/InsResult/";
			String commonPathDB = propertyService.getString("Globals.kdnFileStorePathTempDB");
			String realPath = "";
			String dbRealPath="";
			if("PREVENTING_INSPECTION".equals(fileFolder)) {
				realPath = commonPath.concat(fileFolder).concat("/").concat(box.getString("rgt_sn"));
				dbRealPath = commonPathDB.concat(fileFolder).concat("/").concat(box.getString("rgt_sn"));
			} else {
				realPath = commonPath.concat(fileFolder).concat("/").concat(box.getString("eqp_no"));
				dbRealPath = commonPathDB.concat(fileFolder).concat("/").concat(box.getString("eqp_no"));
			}
			System.out.println("경로 확인 : " + realPath);
			//그룹파일디비 생성
			Box groupFileBox = new Box(); 
			groupFileBox.put("group_file_id", group_file_id);
			groupFileBox.put("group_file_name", fileFolder);
			groupFileBox.put("group_file_realpath", dbRealPath);
			groupFileBox.put("reg_id", user_id);
			Box groupFileInfo = kdnFileService.getGroupFileInfo(groupFileBox);
			System.out.println("m_group_file_t 확인" + groupFileBox);
			if(groupFileInfo == null) {
				System.out.println("1111");
				kdnFileService.setGroupFileInsert(groupFileBox);
				System.out.println("2222");
			}
			int file_id = 0;
			for(int i=1; i<=count; i++) {
				//시스템에 저장될 때 경로
				MultipartFile file = multipartRequest.getFile("result_file" + i);
				box.put("file_name", file.getOriginalFilename());
				int isExistFileName = kdnFileService.isExistFileName(box);
				if(isExistFileName != 0) {
					continue;
				}
				String fileRealPath = doFileUpload(file.getBytes(),file.getOriginalFilename(),realPath);
				String dbFileRealPath = dbRealPath.concat(fileRealPath.substring(fileRealPath.lastIndexOf("/"), fileRealPath.length()));
				arrayFiles[i] = fileRealPath;
				
				//파일 아이디 MAX 값 호출
				Box maxFileId = kdnFileService.getMaxFileId(groupFileBox);
				if(maxFileId == null) {
					file_id = 1;
				}else {
					file_id = maxFileId.getInt("FILE_ID") + 1;
				}
				//파일디비 생성
				Box fileBox = new Box();
				fileBox.put("file_id", file_id);
				fileBox.put("group_file_id", group_file_id);
				fileBox.put("file_name", file.getOriginalFilename());
				
				fileBox.put("file_realpath", dbFileRealPath);
				fileBox.put("reg_id", user_id);
				//new String(param.getBytes("ISO-8859-1"), "UTF-8")
				fileBox.put("file_subject", box.getString("file_subject" +i));
				fileBox.put("file_contents", box.getString("file_contents" +i));
				System.out.println("m_file_t 확인" + fileBox);
				kdnFileService.setFileInsert(fileBox);
				System.out.println("3333");
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("파일 업로드주 오류 발생 :" + e.getMessage());
		}
	}
	
	//디렉토리 생성
	public void setDerectoryCreate(String path) {
		System.out.println("파일경로 : " + path);
		File dir = new File(path);
		if(!dir.isDirectory()) {
			dir.mkdir();
		}else {
			return;
		}
	}
	 /**
	 * <설명> 
	 * 관리자에서 데이터 삭제시 해당 파일 데이터 삭제  
	 * @param [Box] 
	 * @return [Integer]
	 * @throws [모든예외 던짐]
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
/*	public int deleteFileId(Box box) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		//def.setName("transaction");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = transactionManager1.getTransaction(def);
		
		int deleteCount = 0;
		try{
			deleteCount = this.sqlSession1.delete("File.deleteFileId", box);
		}catch(Exception e){
			transactionManager1.rollback(status);
			e.printStackTrace();
			throw e;
		}
		transactionManager1.commit(status);
		return deleteCount;
	}*/
	
	
	/**
	 * <설명> 
	 * 파일아이디 / 파일시퀀스로 해당 파일 데이터 삭제  
	 * @param [Box] [검색 파라메터 : file_id, file_seq]
	 * @return [Integer]
	 * @throws [모든예외 던짐]
	 * @author [김정수]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
/*	public int deleteFileIdSeq(Box box){
		//DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		//def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		//TransactionStatus status = transactionManager1.getTransaction(def);
		
		int deleteCount = 0;
		try{
			deleteCount = this.sqlSession1.delete("File.deleteFILE", box);
		}catch(Exception e){
			//transactionManager1.rollback(status);
			e.printStackTrace();
		}
		//transactionManager1.commit(status);
		return deleteCount;
	}	*/
	
	/**
	 * <설명> 
	 *  web.system.path를 web.system.new.path로 경로를 변경해줌(System타입을 -> DB타입의 맞게 경로 변경)
	 * @param [String - imageURL]
	 * @return [String]
	 * @throws [모든예외 던짐]
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
/*	public String getChangeSystemURL(String imageURL) {
		String changeImageURL = imageURL.replaceAll(uploadSystemPath, uploadSystemNewPath);
		return changeImageURL;
	}*/
	
	/**
	 * <설명> 
	 * web.system.new.path를 web.system.path로 경로를 변경해줌(DB타입을 -> System타입의 맞게 경로 변경)
	 * @param [String - imageURL]
	 * @return [String]
	 * @throws [모든예외 던짐]
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
/*	public String getChangeDbURL(String imageURL) {
		String changeImageURL = imageURL.replace(uploadSystemNewPath, uploadSystemPath);
		return changeImageURL;
	}*/

}
