package kdn.cmm.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service("fileManager")
public class FileManager {
	
	//static Logger logger = Logger.getLogger(FileDownloadView.class);
	
	// path : 파일을 저장할 경로
	// 리턴 : 서버에 저장된 새로운 파일명
	public String doFileUpload(byte[] bytes, String originalFileName, String path) throws Exception {
		String newFileName = null;

		if(bytes == null)
			return null;
		
		// 클라이언트가 업로드한 파일의 이름
		if(originalFileName.equals("")){
			return null;
		}
		
		// 확장자
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		if(fileExt == null || fileExt.equals("")){
			return null;
		}
		// 서버에 저장할 새로운 파일명을 만든다.
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		newFileName += System.nanoTime();
		newFileName += fileExt;
		
		// 업로드할 경로가 존재하지 않는 경우 폴더를 생성 한다.
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		StringBuffer fullpathname = new StringBuffer();
		fullpathname.append(path).append("/").append(newFileName);
		
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
	
	// 실제 파일 삭제
	public boolean doFileDelete(String realPath) throws Exception {
		boolean isDelete = false;
		File file = null;
		String fullFileName = realPath;
        file = new java.io.File(fullFileName);
        if (file.exists()){
           isDelete = file.delete();
        }
        return isDelete;
	}	
	
	//디렉토리 삭제
	public boolean deleteDirectory(File path) {
        if(!path.exists()) {
            return false;
        }
         
        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
         
        return path.delete();
    }
	
	/*public String thumJAIResizeCreate(String loadFile, int maxWidth, int maxHeight) throws IOException{
		  
		  String saveFile = null;
		
		  try{
			  
			// 클라이언트가 업로드한 파일의 이름
				if(loadFile.equals("")){
					return null;
				}
				
				// 확장자
				String fileExt = loadFile.substring(loadFile.lastIndexOf(".")+1);
				if(fileExt != null){
					fileExt = fileExt.toLowerCase();
				}
				if(fileExt == null || fileExt.equals("") || (!"jpeg".equals(fileExt) && !"jpg".equals(fileExt) && !"gif".equals(fileExt) && !"png".equals(fileExt))){
					return null;
				}
				// 파일경로
				String filePath = loadFile.substring(0,loadFile.lastIndexOf("/")+1);

				// 서버에 저장할 새로운 파일명을 만든다.
				StringBuffer str = new StringBuffer();
				str.append(filePath);
				str.append(System.nanoTime());
				str.append(String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()));
	    		str.append(System.nanoTime());
	    		str.append(".");
	    		str.append(fileExt);
				saveFile = "C:/eGovFrameDev-2.6.0-FullVer/workspace/kdnweb/src/main/webapp".concat(str.toString());
			  System.out.println("패스 확인 : " +loadFile);
			  RenderedOp orgRender = JAI.create("fileload", "C:/eGovFrameDev-2.6.0-FullVer/workspace/kdnweb/src/main/webapp".concat(loadFile));//원본 이미지에 대한 RenderedOp 객체 생성
			  BufferedImage orgImg = orgRender.getAsBufferedImage();//BufferImage 객체를 얻어옴
			  
			  int orgWidth = orgImg.getWidth();
			  int orgHeight = orgImg.getHeight();
			  
			  float thumWidth = 0.0f;
			     float thumHeight = 0.0f;
			     
			     if(orgImg.getWidth() > orgImg.getHeight()) {
			      thumHeight = (float)maxHeight * ((float)orgImg.getHeight() / (float)orgImg.getWidth());
			      thumWidth = maxWidth;
			     } else if(orgImg.getWidth() < orgImg.getHeight()) {
			      thumWidth = (float)maxWidth * ((float)orgImg.getWidth() / (float)orgImg.getHeight());
			      thumHeight = maxHeight;
			     } else {
			      thumWidth = maxWidth;
			      thumHeight = maxHeight;
			     }
			     
			        if(thumWidth > orgWidth) thumWidth = orgWidth;
			        if(thumHeight > orgHeight) thumHeight = orgHeight;
			  
					      
		      BufferedImage bufferIm = null;
		      
		      bufferIm = new BufferedImage((int)thumWidth, (int)thumHeight, BufferedImage.TYPE_INT_ARGB); //투명색있는 그림은 알파값을 표현할 수 있도록 설정(20130826)
		      
		      Graphics2D  g2 = bufferIm.createGraphics();//Graphics2D 객체 생성
			  g2.drawImage(orgImg, 0, 0, (int)thumWidth, (int)thumHeight, null);//이미지를 가로 ,세로 크기로 그린다.
			  
			  File thumbnail = new File("C:/eGovFrameDev-2.6.0-FullVer/workspace/kdnweb/src/main/webapp".concat(loadFile));//썸네일 이미지에 대한 파일 객체 생성
			  
			  //ImageIO.write(bufferIm,fileExt, thumbnail);//그려진 이미지를 파일로 채움
			  ImageIO.write(bufferIm,"png", thumbnail);//그려진 이미지를 파일로 채움(20130826)  
		  }catch(Exception e){
			  e.printStackTrace();
			  return null;
		  }
		  return saveFile;
	}*/

/*    public static void main(String[] args) {
             String loadFile = "c:/test_jai/quickaction_slider_background.png";
 
             try {
            	 System.out.println("create file path : "+ThumJAIResizeCreate(loadFile,50,50));
             } catch (Exception e) {
                    e.printStackTrace();
             }
    }*/
	
}
