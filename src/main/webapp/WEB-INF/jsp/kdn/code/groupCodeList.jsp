<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
 /**
  * @Class Name : EgovBBSLoneMstrList.jsp
  * @Description : 게시판 속성 목록화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.08.25   한성곤          최초 생성
  *
  *  @author 공통컴포넌트 개발팀 한성곤
  *  @since 2009.08.25
  *  @version 1.0
  *  @see
  */
%>
<html lang="ko"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 
<link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>
--%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script> 
<script type="text/javascript">
	$(document).ready(function(){		
		$('#topmenu').topmenu({ d1: "105000", d2: "105001" });  
	});
	
	function press(event) {
		if (event.keyCode==13) {
			goSearch();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key){
		document.frm.group_code_id.value = key;
		document.frm.action = "<c:url value='/kdn/admin/groupCodeView.do'/>";
		document.frm.submit(); 
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goWrite(){
		document.frm.action = "<c:url value='/kdn/admin/groupCodeWrite.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch(){
		document.frm.action = "<c:url value='/kdn/admin/groupCodeList.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 체크박스 전체 선택
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function AllCheckbox(name, bln) { 
	    var obj = document.getElementsByName(name); 
	    if(obj == null) return;

	    var max = obj.length;
	    if(max == null) {
	        obj.checked = bln;
	    }else {
	        for(var i = 0; i < max; i++) {
	            obj[i].checked = bln;
	        }
	    }
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 선택한 대상 삭제
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSelectDel(){
		var chkLen = $("input[name=chk]:checked").length;

		if(chkLen <= 0){
			alert("삭제할 대상을 지정해 주세요.");
			return;
		}
		
		if (confirm('선택한 대상을 삭제 하시겠습니까?')) {
			document.frm.action = "<c:url value='/kdn/admin/groupCodeDelete.do'/>";
			document.frm.submit();
		}
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) { 
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/groupCodeList.do'/>";
		document.frm.submit();
	}
	
</script>
<title>게시판 목록조회</title>

<style type="text/css">
h1 {
	font-size: 12px;
}

caption {
	visibility: hidden;
	font-size: 0;
	height: 0;
	margin: 0;
	padding: 0;
	line-height: 0;
}
</style>

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
		
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/groupCodeList.do'/>"> <!-- target="_hidden" -->
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
			<input name="group_code_id" type="hidden"	value="" />

			<!-- container -->
			<div class="container"> 
				<!-- top -->
				<div class="top">
					<h3>공통그룹코드</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 공통코드관리 &gt; <strong class="location">공통그룹코드</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid" >
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit">검색구분</strong></label> 
							<select name="searchCnd" title="검색조건선택" class="select_st ml27">
								<option value="All" <c:if test="${box.searchCnd == 'All'}">selected="selected"</c:if> >전체</option>
								<option value="codeId" <c:if test="${box.searchCnd == 'codeId'}">selected="selected"</c:if> >코드ID</option>
								<option value="codeName" <c:if test="${box.searchCnd == 'codeName'}">selected="selected"</c:if> >코드명</option>
							</select>
								
							<label for="work_divide" class="ml66"><strong class="label_tit">검색단어</strong></label> 
							<input name="searchWrd" type="text" size="35" value='<c:out value="${box.searchWrd}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml42"> 
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" /></a>
						</li>
					</ul>
					<table summary="공통그룹코드 게시판입니다.">
						<caption>공통그룹코드 게시판</caption>
						<thead>
							<tr>
								<th class="thFrist"><input type="checkbox" id="AllCheck" name="AllCheck" onclick="AllCheckbox('chk', this.checked);" class="checkbox"></th>
								<th>번호</th>
								<th>공통그룹코드ID</th>
								<th>공통그룹코드명</th>
								<th>정렬순서</th>
								<th>사용여부</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td>
										<c:if test="${result.sub_code_count <= 0}">
											<input type="checkbox" name="chk" value="${result.group_code_id}" class="checkbox">
										</c:if>
									</td>
									<td> 
										<c:out value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" />
									</td>
									<td>
										<a style="color:#729ACF;text-decoration:underline;float: left;" href="javascript:goView('<c:out value="${result.group_code_id}"/>')"><c:out value="${result.group_code_id}" /></a>
									</td>
									<td>
										<c:out value="${result.group_code_name}" />
									</td>
									<td>
										<c:out value="${result.order_code}" />
									</td>
									<td> 
										<c:if test="${result.use_yn eq 'N'}">
											미사용
										</c:if> 
										<c:if test="${result.use_yn eq 'Y'}">
											사용
										</c:if>
									</td>
								</tr>
							</c:forEach>	
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.searchWrd}">
										<tr> 
											<td colspan="6" >
												<font style="font-family:맑은 고딕;font-size:12px">검색하신 공통그룹코드 데이터가 없습니다.</font>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr> 
											<td colspan="6" >
												<font style="font-family:맑은 고딕;font-size:12px">공통그룹코드 데이터가 없습니다.</font>
											</td>
										</tr>	
									</c:otherwise>
								</c:choose>
							</c:if>
						</tbody>
					</table>
					<div style="padding: 10px 0 0 10px">
						<input type="button" value="삭제" title="삭제" onclick="goSelectDel(); return false;" class="btn_gray"> 
						<input type="button" value="등록" title="등록" onclick="javascript:goWrite();return false;" class="btn_gray">
					</div>
					<!-- paging -->
					<div id="pagination" class="paging" align="center">
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_pageview" />  
					</div>
					<!-- // paging -->

					<!-- paging -->
					<!-- <p class="paging">  
						<a href="#" class="firstBtn"><img src="images/kdn/common/btn_first.gif" alt="처음으로" /></a>
						<a href="#" class="prevBtn"><img src="images/kdn/common/btn_prev.gif" alt="이전" /></a>
						<a href="#" class="firstPaging"><strong>1</strong></a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#">7</a><a href="#">8</a><a href="#">9</a><a href="#">10</a>
						<a href="#" class="nextBtn"><img src="images/kdn/common/btn_next.gif" alt="다음" /></a>
						<a href="#" class="lastBtn"><img src="images/kdn/common/btn_last.gif" alt="마지막으로" /></a>
					</p> -->
					<!-- // paging -->
				</div>
				<!-- // mid -->
			</div>
			<!-- // container -->
			
			<!-- bottom Area -->
				<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
			<!-- // bottom Area --> 
		</form>
	</div>
     <!-- end wrap -->
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
</html>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->

