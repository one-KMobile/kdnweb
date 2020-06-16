<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%

/**
  * @Class Name : EgovFileList.jsp
  * @Description : 파일 목록화면
  * @Modification Information
  * @
  * @  수정일   	수정자		수정내용
  * @ ----------	------		---------------------------
  * @ 2009.03.26	이삼섭		최초 생성
  * @ 2011.07.20	옥찬우		<Input> Tag id속성 추가( Line : 68 )
  *
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.26
  *  @version 1.0
  *  @see
  *
  */
%>
<!-- link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css"-->

<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript">
	$(document).ready(function () {
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* img 태그 src에 이미지 경로 셋팅하기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		$("img[id *= 'fileicon_']").each(function(index){
			var idStr = $(this).attr("id"); 
			var fileName = idStr.substring(idStr.indexOf("_")+1, idStr.length);
			$($(this)).attr("src", FileExtCheck(fileName));
		});
		
		//수정일 경우 
		if("${updateFlag}" == "Y"){ 	
			$("input[id ^= file_]").attr('disabled', true);
		}
	});
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 파일 다운로드
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_egov_downFile(atchFileId, fileSn){
		window.open("<c:url value='/cmm/fms/FileDown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 하나의 파일을 삭제한다.
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	/* function fn_egov_deleteFile(atchFileId, fileSn) {
		var forms = document.getElementsByTagName("form");
		
		for (var i = 0; i < forms.length; i++) {
			if (typeof(forms[i].atchFileId) != "undefined" &&
					typeof(forms[i].fileSn) != "undefined" &&
					typeof(forms[i].fileListCnt) != "undefined") {
				form = forms[i];
			}
		}
		//form = document.forms[0];
		
		var form = document.fileFrm;
		
		form.atchFileId.value = atchFileId;
		form.fileSn.value = fileSn;
		form.fileListCnt.value = "${fileListCnt}";
		
		form.action = "<c:url value='/cmm/fms/deleteFileInfs.do'/>";
		form.submit();
	} */

	function fn_egov_check_file(flag) {
		if (flag=="Y") {
			document.getElementById('file_upload_posbl').style.display = "block";
			document.getElementById('file_upload_imposbl').style.display = "none";
		} else {
			document.getElementById('file_upload_posbl').style.display = "none";
			document.getElementById('file_upload_imposbl').style.display = "block";
		}
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 파일 확장자 체크해서 확장자에 맞는 이미지 불러오기
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function FileExtCheck(FileName){
		var ImageFileName = "";
		var EndFileName = FileName.substring(FileName.indexOf(".")+1, FileName.length);
		
		if(EndFileName.length > 0){
			var EndFileNameLCase =   EndFileName.toLowerCase();

			if(EndFileNameLCase == "doc" || EndFileNameLCase == "docx"){
				ImageFileName = "ico_word.gif";	
			}else if(EndFileNameLCase == "pptx" || EndFileNameLCase == "pptx"){
				ImageFileName = "ico_ppt.gif";
			}else if(EndFileNameLCase == "hwp"){
				ImageFileName = "ico_han.gif";
			}else if(EndFileNameLCase == "pdf"){
				ImageFileName = "ico_pdf.gif";
			}else if(EndFileNameLCase == "jpg" || EndFileNameLCase == "gif" || EndFileNameLCase == "pcx" || EndFileNameLCase == "bmp" || EndFileNameLCase == "png" || EndFileNameLCase == "psd"){
				ImageFileName = EndFileName + ".gif";
			}else if(EndFileNameLCase == "avi" || EndFileNameLCase == "wmv" || EndFileNameLCase == "mp4"){
				ImageFileName = "movie.gif";
			}else if(EndFileNameLCase == "txt"){
				ImageFileName = "txt.gif";
			}else if(EndFileNameLCase == "htm" || EndFileNameLCase == "html" || EndFileNameLCase == "cab" || EndFileNameLCase == "zip" || EndFileNameLCase == "mp3" || EndFileNameLCase == "wav"){
				ImageFileName = EndFileName  + ".gif";
			}else{
				ImageFileName = "clip.gif";
			}
		}	
		ImageFileName = "<c:url value='/images/fileicon/" + ImageFileName.toLowerCase() + "' />";
		return ImageFileName;
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 파일이 있을때 '파일 삭제' 체크박스 선택하면 활성화 OR 비활성화
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function ToggleFileAttach(index) {
		if ($('#FileDelFlag_' + index).is(':checked')) {
			$('#file_' + index).attr('disabled', false);
		} else {
			$('#file_' + index).attr('disabled', true);
		}
	}
</script>

<%-- <form id="fileFrm" name="fileFrm" method="post" action="">
	<input type="hidden" id="atchFileId1" name="atchFileId" value="${atchFileId}">
	<input type="hidden" id="fileSn" name="fileSn" >
	<input type="hidden" name="fileListCnt" id="fileListCnt" value="${fileListCnt}">
</form> --%>

<!--<title>파일목록</title> -->

<table>
	<c:forEach var="fileVO" items="${fileList}" varStatus="status">
	<tr>
		<td style="padding-left: 5px;">
			<c:choose>
				<c:when test="${updateFlag eq 'Y'}"><!--수정 화면  -->
					<span style="color:blue;">
						<img id="fileicon_${fileVO.orignlFileNm}" width="16" height="16" />
					</span>
					<c:out value="${fileVO.orignlFileNm}"/>&nbsp;[<c:out value="${fileVO.fileMg}"/>&nbsp;byte]
					<input type="checkbox" name="FileDelFlag" id="FileDelFlag_${fileVO.fileSn}" value="${fileVO.fileSn}" onclick="ToggleFileAttach(${fileVO.fileSn});" /> 파일 삭제
					<p style="margin-top: 5px;margin-bottom:3px;">
						<input name="file_${status.count}" id="file_${fileVO.fileSn}" type="file" title="첨부파일입력"/>
					</p>
					<%-- <img src="<c:url value='/images/egovframework/com/cmm/fms/icon/bu5_close.gif' />" 
						width="19" height="18" style="cursor:pointer;" onClick="javascript:fn_egov_deleteFile('<c:out value="${fileVO.atchFileId}"/>','<c:out value="${fileVO.fileSn}"/>');" alt="파일삭제"> --%>
				</c:when>
				<c:otherwise><!--상세 화면  -->
					<font style="font-family:Malgun Gothic;font-weight:bold;" >파일 ${status.count} :</font>&nbsp;
					<span style="color:blue;">
						<img id="fileicon_${fileVO.orignlFileNm}" width="16" height="16" />
					</span>
					<a href="javascript:fn_egov_downFile('<c:out value="${fileVO.atchFileId}"/>','<c:out value="${fileVO.fileSn}"/>')">
					<c:out value="${fileVO.orignlFileNm}"/>&nbsp;[<c:out value="${fileVO.fileMg}"/>&nbsp;byte]
					</a>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	</c:forEach>
	<c:if test="${updateFlag eq 'Y'}"><!-- 수정 화면  -->
		<c:if test="${fileListCnt < addFileCount}">
			<c:if test="${addFileCount - fileListCnt  ne 0}">
				<c:forEach var="i" begin="1" end="${addFileCount - fileListCnt}" varStatus="status">
					<tr>
						<td style="padding-left: 5px;">
							<input name="addFile_${status.count}" id="addFile_${status.count}" type="file" title="첨부파일입력"/>
						</td>
					</tr>			
				</c:forEach>
			</c:if>
		</c:if>
	</c:if>
	
	<c:if test="${fn:length(fileList) == 0}">
		<tr>
			<td></td>
		</tr>
    </c:if>
</table>
