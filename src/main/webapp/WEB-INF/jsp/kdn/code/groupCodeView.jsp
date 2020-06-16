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
<title>공통그룹코드 등록</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/com.css' />">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script> --%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<script type="text/javaScript" language="javascript">
<!--
/* ********************************************************
 * 초기화
 ******************************************************** */
function fnInit(){
}

$(document).ready(function(){		
	//$('#topmenu').topmenu({ d1: 5, d2: 1 });
	$('#topmenu').topmenu({ d1: "105000", d2: "105001" });
});

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 목록 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goList(){
	form = document.searchForm;
	form.action = "<c:url value='/kdn/admin/groupCodeList.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 수정 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goModify(){
	form = document.searchForm;
	form.action = "<c:url value='/kdn/admin/groupCodeWrite.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 삭제 처리
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goDelete(){
	form = document.searchForm;
	
	if (confirm('정말로 삭제 하시겠습니까?')) {
		form.action = "<c:url value='/kdn/admin/groupCodeDelete.do'/>";
		form.submit();	
	}
}
</script>
</head>
<body>
	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>

	<!-- wrap -->
	<div id="wrap"> 
		<!-- Left Area --> 
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		 
		 <!-- Top Area -->
		<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
		<!-- // Top Area --> 
		
		<!-- 구성관리 contents -->
		<div id="contents">
			<form name="searchForm" id="searchForm" method="post" >
				<input id="group_code_id" name="group_code_id" type="hidden"	value="<c:out value='${viewBox.group_code_id}'/>" />
				<input id="chk" name="chk" type="hidden" value="<c:out value='${viewBox.group_code_id}'/>" /><!-- 삭제 처리 하기 위해 키값 셋팅 파라메터  -->
				<!-- 검색 파라메터  -->
				<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
				<input type="hidden" name="searchCnd"  value="<c:out value='${box.searchCnd}'/>"/>
				<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
			</form>
				
				<!-- top -->
				<div class="top">
					<h3>공통그룹코드 상세</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 공통코드 관리 &gt; <strong class="location">공통그룹코드 상세</strong>
					</p>
				</div>
				<!-- // top -->
				<table width="700" border="0" cellpadding="0" cellspacing="0"	 >
					<tr>
						<th class="th_top">공통그룹코드ID&nbsp;&nbsp;</th>
						<td class="td_top" style="padding-left:10px">${viewBox.group_code_id}</td> 
					</tr>
					<tr>
						<th>공통그룹코드ID명&nbsp;&nbsp;</th>
						<td style="padding-left:10px">${viewBox.group_code_name}</td>
					</tr>  
					<tr>
						<th>공통그룹코드 설명&nbsp;&nbsp;</th>
						<td style="padding-left:10px">${viewBox.group_code_account}</td>
					</tr>
					<tr>
						<th>정렬순서&nbsp;&nbsp;</th>
						<td style="padding-left:10px">${viewBox.order_code}</td>
					</tr>
					<tr>
						<th class="th_bot">사용여부&nbsp;&nbsp;</th>
						<td class="td_bot" style="padding-left:10px">
							<c:if test="${viewBox.use_yn == 'N'}">
								미사용
							</c:if> 
							<c:if test="${viewBox.use_yn == 'Y'}">
								사용
							</c:if>
						</td>
					</tr>
				</table>
				
				<div style="padding: 10px 0 0 10px">
					<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="goModify(); return false;" class="btn_gray"/>
					<c:if test="${not empty viewBox.group_code_id and viewBox.sub_code_count <= 0}">
						<input type="button" value="<spring:message code="button.delete" />" title="삭제" onclick="goDelete(); return false;" class="btn_gray"/>	
					</c:if>
					<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray"/>
				</div>
			</div>
			<!-- // 구성관리 contents -->
			
			<!-- bottom Area -->
				<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
			<!-- // bottom Area -->
		</div>
    	<!-- end wrap -->	
</body>
</html>
