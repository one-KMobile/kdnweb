<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
 /**
  * @Class Name  : EgovCcmCmmnDetailCodeDetail.jsp
  * @Description : EgovCcmCmmnDetailCodeDetail 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.04.01   이중호              최초 생성
  *
  *  @author 공통서비스팀
  *  @since 2009.04.01
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
<title>자료실 상세</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css' />">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javaScript" language="javascript">
<!--
/* ********************************************************
 * 초기화
 ******************************************************** */
function fnInit(){
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 목록 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goList(){
	form = document.searchForm;
	form.action = "<c:url value='/sample/egovSampleRefList.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 수정 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goModify(){
	form = document.searchForm;
	form.action = "<c:url value='/sample/egovSampleRefWrite.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 삭제 처리
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goDelete(){
	form = document.searchForm;
	
	if (confirm('정말로 삭제 하시겠습니까?')) {
		form.action = "<c:url value='/sample/egovSampleRefDelete.do'/>";
		form.submit();	
	}
}

/* ********************************************************
 * 삭제 처리 함수
 ******************************************************** */
function fnDelete(){
	if (confirm("<spring:message code='common.delete.msg'/>")) {
		var varForm				 = document.getElementById("Form");
		varForm.action           = "<c:url value='/sym/ccm/cde/EgovCcmCmmnDetailCodeRemove.do'/>";
		varForm.codeId.value     = "${result.codeId}";
		varForm.code.value       = "${result.code}";
		varForm.submit();
	}
}
-->
</script>
</head>
<body>
<form name="searchForm" id="searchForm" method="post" >
	<input id="bbs_seq" name="bbs_seq" type="hidden"	value="<c:out value='${viewBox.bbs_seq}'/>" />
	<input id="chk" name="chk" type="hidden" value="<c:out value='${viewBox.bbs_seq}'/>" /><!-- 삭제 처리 하기 위해 키값 셋팅 파라메터  -->
	<!-- 검색 파라메터  -->
	<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
	<input type="hidden" name="searchCnd"  value="<c:out value='${box.searchCnd}'/>"/>
	<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
</form>
<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
	<table width="700" cellpadding="8" class="table-search" border="0">
		<tr>
			<td width="100%" class="title_left"><h1 class="title_left">
					<img
						src="<c:url value='/images/egovframework/com/cmm/icon/tit_icon.gif' />"
						width="16" height="16" hspace="3" style="vertical-align: middle"
						alt="제목아이콘이미지">&nbsp;자료실 상세
				</h1></td>
		</tr>
	</table>
	<table width="700" border="0" cellpadding="0" cellspacing="1"
		class="table-register"
		summary="코드ID명, 코드, 코드명, 코드설명, 사용여부가 나타나 있는 공통상세코드 상세조회 테이블이다.">
		<CAPTION style="display: none;">공통상세코드 상세조회</CAPTION>
		<tr>
			<th width="20%" height="23" class="required_text" scope="row" nowrap>제목&nbsp;&nbsp;</th>
			<td style="padding-left: 5px;">${viewBox.subject}</td>
		</tr>
		<tr>
			<th width="20%" height="23" class="required_text" scope="row" nowrap>작성일&nbsp;&nbsp;</th>
			<td style="padding-left: 5px;">${viewBox.reg_date_string}</td>
		</tr>
		<tr>
			<th width="20%" height="23" class="required_text" scope="row" nowrap>내용&nbsp;&nbsp;</th>
			<td style="padding-left: 5px;"><c:out value="${viewBox.content}" escapeXml="true"/></td>
		</tr>
		<tr>
			<th width="20%" height="23" class="required_text" scope="row" nowrap>사용여부&nbsp;&nbsp;</th>
			<td style="padding-left: 5px;">
				<c:if test="${viewBox.use_yn == 'N'}">
					미사용
				</c:if> 
				<c:if test="${viewBox.use_yn == 'Y'}">
					사용
				</c:if>
			</td>
		</tr>
		<c:if test="${not empty viewBox.file_id and viewBox.filecount > 0}">
		<tr>
			<th width="20%" height="23" class="required_text" scope="row" nowrap>첨부파일&nbsp;&nbsp;</th>
			<td>
				<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${viewBox.file_id}" />
				</c:import>
			</td>
		</tr>
		</c:if>
	</table>
	<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td height="10"></td>
		  </tr>
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" align="center">
						<tr>
							<td><span class="button"><input type="submit"
									value="수정" onclick="goModify(); return false;"></span></td>
							<td width="10"></td>
					
							<c:if test="${not empty viewBox.bbs_seq}">
								<td><span class="button"><input type="submit"
										value="삭제" onclick="goDelete(); return false;"></span></td>
								<td width="10"></td>
							</c:if>
					
							<td><span class="button"><input type="submit"
									value="목록" onclick="goList(); return false;"></span></td>
							<td width="10"></td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
</body>
</html>
