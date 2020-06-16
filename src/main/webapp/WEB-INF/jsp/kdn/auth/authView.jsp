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
$(document).ready(function(){		
	//$('#topmenu').topmenu({ d1: 9, d2: 2 });  
	$('#topmenu').topmenu({ d1: "110000", d2: "110001" });
	
	$("th").last().addClass("th_bot");
	$("td").last().addClass("td_bot");
});

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 목록 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goList(){
	form = document.searchForm;
	form.action = "<c:url value='/kdn/admin/authList.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 수정 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goModify(){
	form = document.searchForm;
	form.action = "<c:url value='/kdn/admin/authWrite.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 삭제 처리
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goDelete(){
	form = document.searchForm;
	
	if (confirm('정말로 삭제 하시겠습니까?')) {
		form.action = "<c:url value='/kdn/admin/authDelete.do'/>";
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
				<input id="auth_idx" name="auth_idx" type="hidden"	value="<c:out value='${viewBox.auth_idx}'/>" />
				<input id="chk" name="chk" type="hidden" value="<c:out value='${viewBox.auth_idx}'/>" /><!-- 삭제 처리 하기 위해 키값 셋팅 파라메터  -->
				<!-- 검색 파라메터  -->
				<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
				<input type="hidden" name="searchCnd"  value="<c:out value='${box.searchCnd}'/>"/>
				<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
			</form>
				<!-- top -->
				<div class="top">
					<h3>권한 상세</h3>
					<p class="navi">  
						<strong>Home</strong> &gt; 권한관리 &gt; <strong class="location">권한 상세</strong>
					</p>
				</div>
				<!-- // top -->
				
				<table width="700" border="0" cellpadding="0" cellspacing="0"	>
					<tr>
						<th class="th_top">권한ID&nbsp;&nbsp;</th>
						<td class="td_top" style="padding-left:10px"><font style="font-weight:bold;">${viewBox.auth_id}</font></td>
					</tr>
					<tr>
						<th width="20%" height="23" class="required_text" scope="row" nowrap>권한명&nbsp;&nbsp;</th>
						<td style="padding-left: 10px;">${viewBox.auth_nm}</td>
					</tr>
					<tr>
						<th width="20%" height="23" class="required_text" scope="row" nowrap>설명&nbsp;&nbsp;</th>
						<td style="padding-left: 10px;">${viewBox.auth_account}</td>
					</tr>
					<tr>
						<th width="20%" height="23" class="required_text" scope="row" nowrap>사용여부&nbsp;&nbsp;</th>
						<td style="padding-left: 10px;">
							<c:if test="${viewBox.use_yn == 'N'}">
								미사용
							</c:if> 
							<c:if test="${viewBox.use_yn == 'Y'}">
								사용
							</c:if>
						</td>
					</tr>
					<tr>
						<th height="23" class="required_text" scope="row" nowrap >등록자&nbsp;&nbsp;</th>
						<td style="padding-left: 10px;">${viewBox.reg_id} (${viewBox.reg_date_string})</td>
					</tr>
					<c:if test="${not empty viewBox.upd_id}">
					<tr>
						<th height="23" class="required_text" scope="row" nowrap >수정자&nbsp;&nbsp;</th>
						<td style="padding-left: 10px;">${viewBox.upd_id} (${viewBox.upd_date_string})</td>  
					</tr>
					</c:if>
				</table>
				
				<div style="padding: 10px 0 0 10px">
					<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="goModify(); return false;" class="btn_gray"/>
					<input type="button" value="<spring:message code="button.delete" />" title="삭제" onclick="goDelete(); return false;" class="btn_gray"/>
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
