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
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		
		if(confirm("파일을 업로드 하시겠습니까?")){
			alert("파일 업로드 하였습니다.");
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
<body>
<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form  id="saveFrm" name="saveFrm" method="post"> 

<div id="border" style="width:730px">
	<table width="100%" cellpadding="8" class="table-search" border="0">
		<tr>
			<td width="100%" class="title_left">
				<h1>
					<img src="<c:url value='/images/egovframework/com/cmm/icon/tit_icon.gif' />" 	width="16" height="16" hspace="3" align="middle" alt="제목아이콘이미지">&nbsp;송변전통합관리 시스템 > 순시결과 파일 업로드
				</h1>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-register" summary="">
		<tr>
			<th height="23" class="required_text" nowrap><label style="padding-right: 10px;">순시결과 파일</label></th>
			<td width="80%" nowrap colspan="1">
				<input name="file_1" id="egovComFileUploader1" type="file" title="첨부파일입력" style="width: 100%;"/>
			</td>
		</tr>
	</table>
	
	<div align="center" style="padding-top:10px;">
		<table border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
			  <td>
			  	<span class="button">
			  			<input type="button" value="<spring:message code="button.create"/>" onclick="javascript:goSave(); return false;" >
			  	</span>
		      </td>
			</tr>
		</table>
	</div>
</div>
</form>
</html>

