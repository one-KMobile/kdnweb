<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%
 /**
  * @Class Name : EgovBoardMstrRegist.jsp
  * @Description : 게시판 생성 화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.03.12   이삼섭          최초 생성
  * @ 2009.06.26   한성곤          2단계 기능 추가 (댓글관리, 만족도조사)
  *   2011.09.15   서준식          2단계 기능 추가 (댓글관리, 만족도조사) 적용 방법 변경
  *   2011.11.11   이기하		   첨부파일선택 여부에 따른 첨부가능파일 숫자에 대한 체크추가
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.12
  *  @version 1.0
  *  @see
  *
  */
%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>

<!-- ******************** Editor 영역 Start ******************** -->
<script type="text/javascript">
_editor_area = "content";
_editor_url = "<c:url value='/html/egovframework/com/cmm/utl/htmlarea3.0/'/>";
</script>
<script type="text/javascript" src="<c:url value='/html/egovframework/com/cmm/utl/htmlarea3.0/htmlarea.js'/>"></script>
<!-- ******************** Editor 영역 End ******************** -->
<script type="text/javascript">
	$(document).ready(function () {
		
	});

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 팝업 관련(분석 X)
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	/* function fn_egov_inqire_tmplatInqire(){
		form = document.boardMaster;
		var retVal;
		var url = "<c:url value='/cop/com/openPopup.do'/>"+"?requestUrl=/cop/tpl/selectTemplateInfsPop.do&typeFlag=BBS&width=850&height=360";
		var openParam = "dialogWidth: 850px; dialogHeight: 360px; resizable: 0, scroll: 1, center: 1";

		retVal = window.showModalDialog(url,"p_tmplatInqire", openParam);
		if(retVal != null){
			var tmp = retVal.split("|");
			form.tmplatId.value = tmp[0];
			form.tmplatNm.value = tmp[1];
		}
	} */
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		form.action = "<c:url value='/sample/egovSampleRefList.do'/>";
		form.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		document.saveFrm.onsubmit(); // 에디터의 값을 얻기 위해서는 FORM 의 onsubmit()(document.form.onsubmit();) 함수를 호출하여 얻을 수 있습니다.
		
		if ($.trim(document.saveFrm.subject.value) == '') {
			alert("제목을 입력해 주십시오.");
			$("#subject").focus();
			return;
		}
		if ($.trim(document.saveFrm.content.value) == '') {
			alert("내용을 입력해 주십시오.");
			$("#content").focus();
			return;
		}
		
		var keyVal = $("#bbs_seq").val();	   
		if(keyVal != null && keyVal != "" && keyVal != "undefined"){ 
			//수정
			if(confirm("수정하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/sample/egovSampleRefUpdate.do'/>";
				document.saveFrm.submit();
			}
		}else{
			//등록
			if(confirm("등록하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/sample/egovSampleRefSave.do'/>";
				document.saveFrm.submit();;
			}
		}
	}
</script>

<title>게시판 생성</title>

<style type="text/css">
h1 {font-size:12px;}
caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}

/* 에디터 Style  */
.noStyle {background:ButtonFace; BORDER-TOP:0px; BORDER-bottom:0px; BORDER-left:0px; BORDER-right:0px;}
.noStyle th{background:ButtonFace; padding-left:0px;padding-right:0px}
.noStyle td{background:ButtonFace; padding-left:0px;padding-right:0px}
</style>

</head>
<body onLoad="HTMLArea.init(); HTMLArea.onload = initEditor; document.saveFrm.subject.focus();"><!-- 에디터  -->
<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/sample/egovSampleRefSave.do'/>" enctype="multipart/form-data" >
	<input id="bbs_seq" name="bbs_seq" type="hidden"	value="<c:out value='${viewBox.bbs_seq}'/>" />
	<input id="atchFileId" name="atchFileId" type="hidden"	value="<c:out value='${viewBox.file_id}'/>" />
	<!-- 검색 파라메터  -->
	<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
	<input type="hidden" name="searchCnd"  value="<c:out value='${box.searchCnd}'/>"/>
	<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>

<div id="border" style="width:730px">
	<table width="100%" cellpadding="8" class="table-search" border="0">
		<tr>
			<td width="100%" class="title_left">
				<h1>
					<img src="<c:url value='/images/egovframework/com/cmm/icon/tit_icon.gif' />" 	width="16" height="16" hspace="3" align="middle" alt="제목아이콘이미지">&nbsp;자료실 등록
				</h1>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="">
		<tr>
			<th width="20%" height="23" class="required_text" nowrap><label>제목</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />"	width="15" height="15" alt="필수입력표시"></th>
			<td width="80%" nowrap colspan="3">
				<input id="subject" name="subject" type="text" size="60" value="${viewBox.subject}"  maxlength="60" title="제목입력">
			</td>
		</tr>
		<tr>
			<th width="20%" height="23" class="required_text" nowrap><label>작성일</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />"	width="15" height="15" alt="필수입력표시"> </th>
			<td width="80%" nowrap colspan="3">
				<span style="padding-left:5px;font-size:12px;font-weight:bold; "><c:out value="${sysdate}"></c:out></span>
			</td>
		</tr>
		<tr>
			<th height="23" class="required_text" nowrap><label>내용</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />" width="15" height="15" alt="필수입력표시"></th>
			<td width="80%" nowrap colspan="3">
				<div>
					<!-- 에디터를 화면에 출력합니다. -->
					<table id="TbEditor" width="100%" border="0" cellpadding="0" cellspacing="0" class="noStyle">
						<tr>
							<td>
				  				<textarea id="content" name="content" class="textarea"   style="width:100%;height:252px;" >   
				  					<c:out value="${viewBox.content}" escapeXml="true"/>
				  				</textarea>
		  					</td>
		  				</tr>
	  				</table>
  				</div>
			</td> 
		</tr>
		<tr>
			<th height="23" class="required_text" nowrap><label>사용여부</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />" width="15" height="15" alt="필수입력표시"></th>
			<td width="80%" nowrap colspan="3" style="padding-left: 1px;">
				<select id="use_yn" name="use_yn">
					<option value="Y" <c:if test="${viewBox.use_yn == 'Y'}">selected="selected"</c:if> > 사용</option>
					<option value="N" <c:if test="${viewBox.use_yn == 'N'}">selected="selected"</c:if> >미사용</option>
				</select>
			</td>
		</tr>
		<c:if test="${not empty viewBox.file_id}"><!-- 수정일 경우  -->
		<tr>
			<th height="23" class="required_text" nowrap><label style="padding-right: 10px;">첨부파일</label></th>
			<td  width="80%" nowrap colspan="3">
				<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${viewBox.file_id}" />
					<c:param name="param_addFileCount" value="3" />
				</c:import>
			</td>
		</tr>	
		</c:if>
		<c:if test="${empty viewBox.file_id}"><!-- 등록일 경우  -->
		<tr>
			<th height="23" class="required_text" nowrap><label style="padding-right: 10px;">파일1</label></th>
			<td width="80%" nowrap colspan="1">
				<input name="file_1" id="egovComFileUploader1" type="file" title="첨부파일입력"/>
			</td>
		</tr>
		<tr>
			<th height="23" class="required_text" nowrap><label style="padding-right: 10px;">파일2</label></th>
			<td width="80%" nowrap colspan="3">
				<input name="file_2" id="egovComFileUploader2" type="file" title="첨부파일입력"/>
			</td>
		</tr>
		<tr>
			<th height="23" class="required_text" nowrap><label style="padding-right: 10px;">파일3</label></th>
			<td width="80%" nowrap colspan="3">
				<input name="file_3" id="egovComFileUploader3" type="file" title="첨부파일입력"/>
			</td>
		</tr>
		</c:if>
	</table>
	
	<div align="center" style="padding-top:10px;">
		<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
			  <td>
			  	<span class="button">
			  		<c:if test="${not empty viewBox.bbs_seq}">
						<input type="button" value="수정" onclick="javascript:goSave(); return false;" >			  		
			  		</c:if>
			  		<c:if test="${empty viewBox.bbs_seq}">
			  			<input type="button" value="<spring:message code="button.create"/>" onclick="javascript:goSave(); return false;" >
			  		</c:if>
			  	</span>
		      </td>
		      <td>&nbsp;&nbsp;</td>
		      <td><span class="button"><input type="submit" value="<spring:message code="button.list" />" onclick="goList(); return false;"></span></td>
			</tr>
		</table>
	</div>
</div>
</form>
<!-- 파일 액션을 처리하기 위한 폼  -->
<%-- <form id="fileFrm" name="fileFrm" method="post" action="<c:url value='/cmm/fms/newDeleteFileInfs.do'/>">
	<input type="hidden" name="atchFileId">
	<input type="hidden" name="fileSn" >
	<input type="hidden" name="fileListCnt" id="fileListCnt" >
</form> --%>
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

