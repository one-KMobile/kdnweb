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
	$('#topmenu').topmenu({ d1: '111000', d2: '111001' });
});

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 목록 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goList(){
	form = document.frm1;
	form.action = "<c:url value='/kdn/version/versionList.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 수정 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goModify(){
	form = document.frm1;
	form.action = "<c:url value='/kdn/version/versionWrite.do'/>";
	form.submit();
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
		<div id="contents" >
			<!-- top -->
			<div class="top">
				<h3>모바일 버전 상세 </h3>
				<p class="navi">
					<strong>Home</strong> &gt; 모바일 관리 &gt; <strong class="location">모바일 버전 상세</strong>
				</p>
			</div>
			<!-- // top -->
			<form  id="frm1" name="frm1" method="post" action="<c:out value='/kdn/admin/noticeSave.do'/>">		
			<input id="version_idx" name="version_idx" type="hidden"	value="<c:out value='${viewBox.version_idx}'/>" />
			</form>
		
			<table width="700" border="0" cellpadding="0" cellspacing="0" >
				<tr>
					<th class="th_top">사용여부</th>
					<td class="td_top">
						<c:if test="${viewBox.use_yn eq '0'}">
							&nbsp;&nbsp;미사용
						</c:if>
						<c:if test="${viewBox.use_yn eq '1'}">
							&nbsp;&nbsp;사용
						</c:if>
					
					</td>
				</tr>
				<tr>
					<th>버젼</th>
					<td>&nbsp;&nbsp;${viewBox.version}</td>
				</tr>
				<tr>
					<th>URL</th>
					<td>&nbsp;&nbsp;${viewBox.url}</td>
				</tr>
				<tr>
					<th>비고란</th>
					<td>&nbsp;&nbsp;${viewBox.remarks}</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td>&nbsp;&nbsp;${viewBox.reg_id}</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>&nbsp;&nbsp;${viewBox.reg_date} </td>
				</tr>
				<tr>
					<th class="th_bot">수정일</th>
					<td class="th_bot">&nbsp;&nbsp;${viewBox.upd_date}</td>
				</tr>
			</table>
			<div style="padding: 10px 0 0 10px">
				<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="javascript:goModify(); return false;" class="btn_gray" />
		   		<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray" />
			</div>
		</div>
		<!-- // 구성관리 contents -->
		<!-- bottom Area -->
		<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
		<!-- // bottom Area -->
	</div>
    <!-- end wrap -->
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>
	
	