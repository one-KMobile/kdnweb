<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>
<script type="text/javascript">
	$(document).ready(function(){		
		$('#topmenu').topmenu({ d1: '111000', d2: '111001' });
		//$('#topmenu').topmenu({ d1: 11, d2: 1 });  
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var use_yn = "<c:url value='${box.use_yn}' />";
		$("#use_yn > option[value = " + use_yn + "]").attr("selected", "true");
		$("[id ^= useYnTd]").each(function(index){
			var id = $(this).attr("id");
  			var log_type_val = $('#'+id).text().trim();
  			if(log_type_val =="1"){
  				$('#'+id).text("사용");
  			}else{
  				$('#'+id).text("미사용");
  			}	
  		});
	});
	
	function press(event) {
		if (event.keyCode == 13) {
			goSearch();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key) {
		document.frm.version_idx.value = key;
		document.frm.action = "<c:url value='/kdn/version/versionView.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goWrite() {
		document.frm.action = "<c:url value='/kdn/version/versionWrite.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch() {
		document.frm.action = "<c:url value='/kdn/version/versionList.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 체크박스 전체 선택
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function AllCheckbox(name, bln) {
		var obj = document.getElementsByName(name);
		if (obj == null)
			return;

		var max = obj.length;
		if (max == null) {
			obj.checked = bln;
		} else {
			for ( var i = 0; i < max; i++) {
				obj[i].checked = bln;
			}
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 선택한 대상 삭제
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSelectDel() {
		var chkLen = $("input[name=chk]:checked").length;

		if (chkLen <= 0) {
			alert("삭제할 대상을 지정해 주세요.");
			return;
		}

		if (confirm('선택한 대상을 삭제 하시겠습니까?')) {
			document.frm.action = "<c:url value='/kdn/version/versionDelete.do'/>";
			document.frm.submit();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/version/versionList.do'/>";
		document.frm.submit();
	}
	
</script>
<title>게시판 목록조회</title>
</head>
<body>
	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부	기능을 사용하실 수 없습니다.</noscript>
	<!-- wrap -->
	<div id="wrap">
		<!-- Left Area --> 
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		 <!-- Top Area -->
		<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
		<!-- // Top Area --> 
       	<!-- .container -->
       	<div class="container">
			<!-- top -->
			<div class="top">
				<h3>모바일 버전</h3>
				<p class="navi">
					<strong>Home</strong> &gt; 모바일 관리 &gt; <strong class="location">모바일 버전</strong>
				</p>
			</div>
			<!-- // top -->
			<!-- mid -->
			<div class="mid" >
				<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/noticeList.do'/>">
				<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" /> 
				<input name="version_idx" type="hidden" value="" />
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">버전 검색</strong></label> 
							<label for="work_divide" class="ml33"></label> 
							<label for="work_divide" class="ml66"><strong class="label_tit">사용 여부</strong></label>
							<select name="use_yn" id="use_yn" class="select_st ml27" title="검색조건선택" >
								<option value="">전체</option>
								<c:forEach var="codeList" items="${codeList}" varStatus="status">
									<option value="${codeList.code_value}"
										<c:if test="${codeList.code_value eq box.use_yn}">selected="selected"</c:if>>${codeList.code_name}</option>
								</c:forEach>
							</select> 
							
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" /></a>
						</li>
					</ul>
					<table summary="공지 게시판입니다.">
						<caption>공지 게시판</caption>
						<thead>
							<tr>
								<th><input type="checkbox" id="AllCheck" name="AllCheck" onclick="AllCheckbox('chk', this.checked);" class="checkbox ml8"></th>
								<th>번호</th>
								<th>버전</th>
								<th>URL</th>
								<th>등록자</th>
								<th>등록일</th>
								<th>수정일</th>
								<th>사용여부</th>
								<th>비고란</th>
							</tr>
						</thead>
						<tbody>	
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td><input type="checkbox" name="chk" value="${result.version_idx}" class="checkbox ml8"></td>
									<td><c:out	value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" /></td>
									<td id="version" name="version">
										<a style="color: #729ACF; text-decoration: underline; float: left;" href="javascript:goView(<c:out value="${result.version_idx}"/>)">
											<c:out value="${result.version}" /></font>
										</a>  
									</td>
									<td>
										<c:out value="${result.url}" /></font>  
									</td>
									<td><c:out value="${result.reg_id}" /></td>
									<td>
										<c:out value="${result.reg_date }" />
									</td>
									<td>
										<c:out value="${result.upd_date}" />
									</td>
									<td id="useYnTd_${result.version_idx}">
										<c:out value="${result.use_yn}" />
									</td>
									<td id="remarksTd_${result.version_idx}">
										<c:choose>
											<c:when test="${fn:length(result.remarks) > 16}">
												<c:out value="${fn:substring(result.remarks,0,16)}" /> ...
											</c:when>
											<c:otherwise>
												<c:out value="${result.remarks}" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.searchWrd}">
										<tr>
											<td colspan="6"><font style="font-family: 맑은 고딕; font-size: 12px">검색하신 공지 데이터가 없습니다.</font></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="6"><font style="font-family: 맑은 고딕; font-size: 12px">공지 데이터가 없습니다.</font></td>
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
				 </form> 
			</div>
			<!-- // mid -->
		</div> 
      	<!-- end container -->   
  </div>
  <!-- end wrap -->
    <!-- bottom Area -->
	<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
	<!-- // bottom Area --> 
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

