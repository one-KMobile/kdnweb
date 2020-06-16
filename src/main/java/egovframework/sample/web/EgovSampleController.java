/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.sample.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kdn.cmm.box.Box;
import kdn.cmm.box.BoxUtil;
import kdn.cmm.util.ConstantValue;
import kdn.cmm.util.JSONResponseUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.sample.service.EgovSampleService;
import egovframework.sample.service.SampleDefaultVO;
import egovframework.sample.service.SampleVO;

/**  
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 * 
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class EgovSampleController {
	
	/** EgovSampleService */
    @Resource(name = "sampleService")
    private EgovSampleService sampleService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    /** Validator */
    @Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    // 코드에서 검색항목 불러오기 
    ComDefaultCodeVO codeVo = new ComDefaultCodeVO();
    
    /* ********************************* [Sample] Start********************************* */
	    /**
	     * XSS 방지 처리.
	     * 
	     * @param data
	     * @return
	     */
	    protected String unscript(String data) {
	        if (data == null || data.trim().equals("")) {
	            return "";
	        }
	        
	        String ret = data;
	        
	        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
	        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
	        
	        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
	        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
	        
	        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
	        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
	        
	        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
	        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
	        
	        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
	        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");
	
	        return ret;
	    }
	    
	    /**
	   	 * [자료실] 목록을 조회한다. (pageing)
	   	 * @param [request] [model] 
	   	 * @return "/sample/egovSampleRefList"
	   	 * @exception Exception
	   	 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용] 
	   	 */
	   @IncludedInfo(name="자료실관리(Sample)", order = 577 ,gid = 50)
	   @RequestMapping(value="/sample/egovSampleRefList.do")
	   public String selectSampleRefList(HttpServletRequest request, ModelMap model) throws Exception {
		    Box box = BoxUtil.getBox(request);
		    List <Box> sampleList = new ArrayList<Box>();
		    
		   	/**************** pageing setting Start ****************/
		    PaginationInfo paginationInfo = new PaginationInfo();
	    	
			paginationInfo.setCurrentPageNo(box.getInt("pageIndex",1));
			paginationInfo.setRecordCountPerPage(5); //페이지당 레코드 개수
			paginationInfo.setPageSize(10); //페이지사이즈

			box.put("firstIndex", paginationInfo.getFirstRecordIndex()); //첫페이지 인덱스
			box.put("lastIndex",paginationInfo.getLastRecordIndex()); //마지막페이지 인덱스
			box.put("recordCountPerPage",paginationInfo.getRecordCountPerPage()); //페이지당 레코드 개수
			
			String pageNo = box.getString("pageIndex");
		    if(pageNo != null && !(pageNo.equals(""))){
		    	box.put("pageIndex", pageNo);
		    }else{
		    	box.put("pageIndex", 1);
		    }
			/**************** pageing setting End ****************/
			
		   	sampleList = sampleService.selectSampleRefList(box);
			model.addAttribute("resultList", sampleList);
		       
		    int totCnt = sampleService.selectSampleRefListTotCnt(box);
			paginationInfo.setTotalRecordCount(totCnt);
						
			model.addAttribute("box", box);
			model.addAttribute("totCnt", totCnt); 
		    model.addAttribute("paginationInfo", paginationInfo);
		       
		    return "/sample/egovSampleRefList";
	   }
	   
	   /**
		 * [자료실] 상세 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/sample/egovSampleRefWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/sample/egovSampleRefView.do")
	    public String egovSampleRefView(HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
			
	        try {
	        	if(box.get("bbs_seq") != null && !box.get("bbs_seq").equals("") ){
	        		viewBox = sampleService.selectSampleRefView(box);
	        		sampleService.sampleRefReadCountUpdate(box); //조회수 증가
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        model.addAttribute("box", box);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/sample/egovSampleRefView";
	    } 
	    
	   /**
		 * [자료실] 등록 화면을 조회한다.
		 * @param [request] [model]
		 * @return "/sample/egovSampleRefWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/sample/egovSampleRefWrite.do")
	    public String egovSampleRefWrite(@ModelAttribute("searchVO") FileVO fileVO, HttpServletRequest request, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(request);
	    	Box viewBox = null;
	    	//List<FileVO> fileInfoList = null;
	    	
	        try {
	        	if(box.get("bbs_seq") != null && !box.get("bbs_seq").equals("") ){
	        		viewBox = sampleService.selectSampleRefView(box);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        //현재 날짜 구하기
	        Calendar now = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String date = sdf.format(now.getTime()); 
	        
	        //파일정보 가져오기
	       /* if(viewBox != null){
		        if(viewBox.getString("file_id") != null && !"".equals(viewBox.getString("file_id"))){
		        	fileVO.setAtchFileId(viewBox.getString("file_id"));
			        fileInfoList = fileMngService.selectFileInfs(fileVO);
			        model.addAttribute("fileInfoList", fileInfoList);
		        }
	        }*/
	        
	        model.addAttribute("box", box); 
	        model.addAttribute("sysdate", date);
	        model.addAttribute("viewBox", viewBox);
	        
	        return "/sample/egovSampleRefWrite";
	    } 
	    
	    /**
		 * [자료실] 등록 저장.
		 * @param [request] [model]
		 * @return "/sample/egovSampleRefWrite"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/sample/egovSampleRefSave.do")
	    public String egovSampleRefSave(final MultipartHttpServletRequest multiRequest, Model model) throws Exception {
	    	Box box = BoxUtil.getBox(multiRequest);
			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("subject"))) || "".equals(unscript(box.getString("content"))) ){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(제목, 내용)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/sample/egovSampleRefWrite.do";
	    	}
	    	
	    	try {
	    		final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    		String atchFileId = "";
	    		 
    		    if (!files.isEmpty()) {
				    List<FileVO> result = fileUtil.parseFileInf(files, "REF_", 1, "", "");
				    atchFileId = fileMngService.insertFileInfs(result);
				    // 첨부파일 ID 셋팅
				    box.put("atchFileId", atchFileId); 
    		    }	
    		    sampleService.sampleRefSave(box); 
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('자료실을 등록하였습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
	        
	    	return "forward:/sample/egovSampleRefList.do";
	    } 
	    
	    /**
		 * [자료실] 수정.
		 * @param [request] [model]
		 * @return "/sample/egovSampleRefUpdate"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/sample/egovSampleRefUpdate.do")
	    public String egovSampleRefUpdate(final MultipartHttpServletRequest multiRequest, Model model) throws Exception {
	    	String[] fileChecks = multiRequest.getParameterValues("FileDelFlag");
	    	Box box = BoxUtil.getBox(multiRequest);
			box.put("fileChk", fileChecks);
			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(unscript(box.getString("subject"))) || "".equals(unscript(box.getString("content"))) ){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(제목, 내용)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/sample/egovSampleRefWrite.do";
	    	}
	    	
	    	//파일 삭제
	    	if(!"".equals(box.getString("fileChk")) && box.getString("fileChk") != null){
	    		fileMngService.delAllFileInfBox(box);
	    	}
	    	
	    	try {
	    		final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    		String atchFileId = "";
	    		
	    		if (!"".equals(box.getString("atchFileId")) && box.getString("atchFileId") != null) {
	    			atchFileId = box.getString("atchFileId");
				} else {
					atchFileId = "";
				}
	    		 
    		    if (!files.isEmpty()) {
    		    	if ("".equals(atchFileId)) { //처음 파일 등록할때
    				    List<FileVO> result = fileUtil.parseFileInf(files, "REF_", 1, atchFileId, "");
    				    atchFileId = fileMngService.insertFileInfs(result);
    				    //첨부파일 ID 셋팅
    				    box.put("atchFileId", atchFileId);
    				} else {
    				    FileVO fvo = new FileVO(); 
    				    fvo.setAtchFileId(atchFileId);
    				    int cnt = fileMngService.getMaxFileSN(fvo);
    				    List<FileVO> _result = fileUtil.parseFileInf(files, "REF_", cnt, atchFileId, "");
    				    
    				    int fileCnt = fileMngService.getFileCount(fvo);
    				    System.out.println("@@@@@@ [fileCnt] =" + fileCnt);
    				    if(fileCnt < 3){ //파일 3개까지만 등록 가능
    				    	fileMngService.updateFileInfs(_result);
    				    }
    				} 
    		    }	
    		    sampleService.sampleRefUpdate(box); 
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('자료실을 수정하였습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
	        
	    	return "forward:/sample/egovSampleRefList.do";
	    } 
	    
	    /**
		 * [자료실] 삭제.
		 * @param [request] [model]
		 * @return "/sample/egovSampleRefDelete"
		 * @exception Exception
		 * @author [범승철] 
	   	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 */
	    @RequestMapping("/sample/egovSampleRefDelete.do")
	    public String egovSampleRefDelete(HttpServletRequest request, Model model) throws Exception {
	    	String[] checks = request.getParameterValues("chk"); 
	    	Box box = BoxUtil.getBox(request);
	    	box.put("chk", checks);
			
	    	String ReusltScript = ""; 
	    	
	    	if("".equals(box.getString("chk"))){
	    		 ReusltScript += "<script type='text/javaScript' language='javascript'>";
		         ReusltScript += "alert('필수 항목(자료실 선택 키)의 값이 없습니다.');";
		         ReusltScript += "</script>";
		         model.addAttribute("reusltScript", ReusltScript);
		         
		         return "forward:/sample/egovSampleRefList.do";
	    	}
	    	
	    	try {
    		    sampleService.sampleRefDelete(box); 
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	ReusltScript += "<script type='text/javaScript' language='javascript'>";
	        ReusltScript += "alert('선택한 대상이 삭제 처리되었습니다.');";
	        ReusltScript += "</script>";
	        model.addAttribute("reusltScript", ReusltScript);
	        
	    	return "forward:/sample/egovSampleRefList.do";
	    } 
	    
	    /**
		 * <설명>
		 * [자료실] 목록 조회 (Api 샘플)
		 * @param [searchCnd] [searchWrd] 
		 * @return [modelAndView]
		 * @throws [예외명] [설명] // 각 예외 당 하나씩
		 * @author [범승철]
		 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
		 * @URL테스트: 
		 */ 
	    @RequestMapping(value="/api/apiSampleRefList.*", method={RequestMethod.POST,RequestMethod.GET})
	    public ModelAndView apiSampleRefList(ModelMap model, HttpServletRequest request){        
	        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
	        Box box = BoxUtil.getBox(request);
	        
	       /* if("".equals(box.getString("searchCnd")) || "".equals(box.getString("searchWrd")) ){ //필수 파라메터 체크
	    	   modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL_REQUIRE);
			   return modelAndView;
	        }*/
	        
	        try {
	        	List<Box> list = sampleService.getApiSelectSampleRefList(box);
	        	modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_SUCCESS);
	        	modelAndView.addObject("apiSampleRefList",list);
			} catch (Exception e) {
				modelAndView.addObject(ConstantValue.RESULT_CODE,ConstantValue.RESULT_FAIL);
				e.printStackTrace();
			}    
	        
	        return modelAndView;
	    }
	    
    /* ********************************* [Sample] End ********************************* */
    
    /**
	 * 글 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "/sample/egovSampleList"
	 * @exception Exception
	 */
    @RequestMapping(value="/sample/egovSampleList.do")
    public String selectSampleList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, 
    		ModelMap model)
            throws Exception {
    	
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));
    	
    	/** pageing setting */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
        List sampleList = sampleService.selectSampleList(searchVO);
        model.addAttribute("resultList", sampleList);
        
        int totCnt = sampleService.selectSampleListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "/sample/egovSampleList";
    } 

    /**
	 * 글을 조회한다.
	 * @param sampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
    @RequestMapping("/sample/selectSample.do")
    public @ModelAttribute("sampleVO")
    SampleVO selectSample(
            SampleVO sampleVO,
            @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
        return sampleService.selectSample(sampleVO);
    }
		
    /**
	 * 글 등록 화면을 조회한다.
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "/sample/egovSampleRegister"
	 * @exception Exception
	 */
    @RequestMapping("/sample/addSampleView.do")
    public String addSampleView(
            @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model)
            throws Exception {
        model.addAttribute("sampleVO", new SampleVO());
        return "/sample/egovSampleRegister";
    }
    
    /**
	 * 글을 등록한다.
	 * @param sampleVO - 등록할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/sample/egovSampleList.do"
	 * @exception Exception
	 */
    @RequestMapping("/sample/addSample.do")
    public String addSample(
    		@ModelAttribute("searchVO") SampleDefaultVO searchVO,
       	 	SampleVO sampleVO,
            BindingResult bindingResult, Model model, SessionStatus status) 
    throws Exception {
    	
    	// Server-Side Validation
    	beanValidator.validate(sampleVO, bindingResult);
    	
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("sampleVO", sampleVO);
			return "/sample/egovSampleRegister";
    	}
    	
        sampleService.insertSample(sampleVO);
        status.setComplete();
        return "forward:/sample/egovSampleList.do";
    }
    
    /**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "/sample/egovSampleRegister"
	 * @exception Exception
	 */
    @RequestMapping("/sample/updateSampleView.do")
    public String updateSampleView(
            @RequestParam("selectedId") String id ,
            @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model)
            throws Exception {
        SampleVO sampleVO = new SampleVO();
        sampleVO.setId(id);
        // 변수명은 CoC 에 따라 sampleVO
        model.addAttribute(selectSample(sampleVO, searchVO));
        return "/sample/egovSampleRegister";
    }

    /**
	 * 글을 수정한다.
	 * @param sampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/sample/egovSampleList.do"
	 * @exception Exception
	 */
    @RequestMapping("/sample/updateSample.do")
    public String updateSample(
            @ModelAttribute("searchVO") SampleDefaultVO searchVO, 
            SampleVO sampleVO, 
            BindingResult bindingResult, Model model, SessionStatus status)
            throws Exception {

    	beanValidator.validate(sampleVO, bindingResult);
    	
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("sampleVO", sampleVO);
			return "/sample/egovSampleRegister";
    	}
    	
        sampleService.updateSample(sampleVO);
        status.setComplete();
        return "forward:/sample/egovSampleList.do";
    }
    
    /**
	 * 글을 삭제한다.
	 * @param sampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/sample/egovSampleList.do"
	 * @exception Exception
	 */
    @RequestMapping("/sample/deleteSample.do")
    public String deleteSample(
            SampleVO sampleVO,
            @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status)
            throws Exception {
        sampleService.deleteSample(sampleVO);
        status.setComplete();
        return "forward:/sample/egovSampleList.do";
    }

}
