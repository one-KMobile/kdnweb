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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="ko">
<head>
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
	$('#topmenu').topmenu({ d1: '108000', d2: '108001' });
});

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 목록 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goList(){
	form = document.searchForm;
	form.action = "<c:url value='/kdn/admin/logHistoryListAll.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 수정 페이지로 이동
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goModify(){
	form = document.searchForm;
	form.action = "<c:url value='/kdn/admin/noticeWrite.do'/>";
	form.submit();
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
* 삭제 처리
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function goDelete(){
	form = document.searchForm;
	
	if (confirm('정말로 삭제 하시겠습니까?')) {
		form.action = "<c:url value='/kdn/admin/noticeDelete.do'/>";
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
		<div id="contents" >
			<!-- top -->
			<div class="top">
				<h3>로그 관리 <c:if test="${not empty viewBox.group_code_id}">수정</c:if><c:if test="${empty viewBox.group_code_id}">등록</c:if></h3>
				<p class="navi">
					<strong>Home</strong> &gt; 로그 관리 &gt; <strong class="location">상세 로그</strong>
				</p>
			</div>
			<!-- // top -->
			<form name="searchForm" id="searchForm" method="post" >
				<input id="board_idx" name="board_idx" type="hidden"	value="<c:out value='${viewBox.board_idx}'/>" />
				<input id="chk" name="chk" type="hidden" value="<c:out value='${viewBox.board_idx}'/>" /><!-- 삭제 처리 하기 위해 키값 셋팅 파라메터  -->
				<!-- 검색 파라메터  -->
				<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
				<input type="hidden" name="s_category_code"  value="<c:out value='${box.s_category_code}'/>"/>
				<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
				<table width="700px" border="0" cellpadding="0" cellspacing="0" summary="" >
					<tr>
						<th class="th_top"></span>회원ID</th>
						<td class="td_top">&nbsp;&nbsp;${viewBox.user_id}</td>
					</tr>
					<tr>
						<th>회원명</th>
						<td>&nbsp;&nbsp;${viewBox.user_name}</td>
					</tr>
					<tr>
						<th>접속타입</th>
						<td><c:if test="${viewBox.log_type eq '1'}">
							<c:set value="APP" var="log_type" />
							</c:if>
							<c:if test="${viewBox.log_type eq '2'}">
								<c:set value="WEB"  var="log_type" />
							</c:if>
							<c:if test="${viewBox.log_type eq '3'}">
								<c:set value="SYNC"  var="log_type" />
							</c:if>
					  		&nbsp;&nbsp;<c:out value='${log_type}'/>
					  		</td>
					</tr>
					<tr>
						<th>IP/디바이스키</th>
						<td>&nbsp;&nbsp;${viewBox.fst_bizplc_cd_nm}</td>
					</tr>
					<tr>
						<th>1차 사업소</th>
						<td>&nbsp;&nbsp;${viewBox.fst_bizplc_cd_nm}</td>
					</tr>
					<tr>
						<th>2차 사업소</th>
						<td>&nbsp;&nbsp;${viewBox.scd_bizplc_cd_nm}</td>
					</tr>
					<tr>
						<th>접속 시간</th>
						<td>&nbsp;&nbsp;${viewBox.reg_date}</td>
					</tr>
					<tr>
						<th class="th_bot"></span>비고란</th>
						<td class="th_bot">&nbsp;&nbsp;<c:out value="${viewBox.remarks}" escapeXml="false"/></td>
					</tr>
				</table>
			<div style="padding: 10px 0 0 10px">
				<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray" />
			</div>
		</div>
		<!-- // 구성관리 contents -->
		<!-- bottom Area -->
		<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
		<!-- // bottom Area -->
		</form>
	</div>
    <!-- end wrap -->
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

