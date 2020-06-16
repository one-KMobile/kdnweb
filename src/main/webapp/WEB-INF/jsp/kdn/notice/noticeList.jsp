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
<%-- <script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>  --%>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>  
<script type="text/javascript">
	$(document).ready(function(){		
		$('#topmenu').topmenu({ d1: '104000', d2: '104001' });   
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var searchCnd1 = "<c:url value='${box.searchCnd1}' />";
		var searchCnd2 = "<c:url value='${box.searchCnd2}' />";
		if($.trim(searchCnd1) != ''){
			setScdBizplcList(searchCnd1);
			$("#searchCnd2").val(searchCnd2);
		}
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
		document.frm.board_idx.value = key;
		document.frm.action = "<c:url value='/kdn/admin/noticeView.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goWrite() {
		document.frm.action = "<c:url value='/kdn/admin/noticeWrite.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch() {
		document.frm.action = "<c:url value='/kdn/admin/noticeList.do'/>";
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
			document.frm.action = "<c:url value='/kdn/admin/noticeDelete.do'/>";
			document.frm.submit();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/noticeList.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 2차사업소 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setScdBizplcList(fstBizplcCode){
		var data = getScdBizplcList(fstBizplcCode);
		var addScdBizplcList = "";
		if(data != null && data.scdBizplcList != null && data.scdBizplcList.length > 0){
			addScdBizplcList +='<option value="" >선택하세요.</option>';
			for (var i = 0; i < data.scdBizplcList.length; i++) {
				addScdBizplcList +='<option value="' + data.scdBizplcList[i].CODE_VALUE +'" >' + data.scdBizplcList[i].CODE_NAME + '</option>';	
			}
		}else{
			addScdBizplcList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#searchCnd2").empty();
		$("#searchCnd2").append(addScdBizplcList);
	} 
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 2차사업소 목록 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getScdBizplcList(fstBizplcCode){
		var requestUrl = "/ajax/getScdBizplcList.json"; 
		var queryParam = "fst_bizplc_cd=" + fstBizplcCode;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.scdBizplcList); 
				if(data.code == '001'){
					returnParam = data;
				}else if(data.code == '002'){ 
					
				}
			},
		    error:function(XMLHttpRequest,textStatus,errorThrown){
		    	alert("error");
			}
		});
		return returnParam; 
	}
	
</script>
<title>게시판 목록조회</title>
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
        	
        	<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/noticeList.do'/>">
				<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" /> 
				<input name="board_idx" type="hidden" value="" />
				
	        	<!-- .container -->
	        	<div class="container">
					<!-- top --> 
					<div class="top">
						<h3>공지사항 목록</h3> 
						<p class="navi">
							<strong>Home</strong> &gt; 공지사항 관리 &gt; <strong class="location">공지사항</strong>
						</p>
					</div>       
					<!-- // top -->
					<!-- mid -->
					<div class="mid">
						<ul>
							<li>
								<label for="constitutor"><strong class="label_tit">송전 설비 검색</strong></label> 
								
								<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;"></font>1차사업소</strong></label> 
								<select id="searchCnd1" name="searchCnd1" class="select_st2 ml27" onchange="setScdBizplcList(this.value);">
									<option value="" >선택하세요</option>
									<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
										<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.searchCnd1}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
									</c:forEach>
								</select> 
								
								<label for="work_divide" class="ml66"></label> 
								<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;"></font>2차사업소</strong></label> 
								<select id="searchCnd2" name="searchCnd2" class="select_st2 ml27" >
									<option value="" > ← 선택하세요</option>
								</select> 
							</li>
							<li>
								<label for="constitutor"><strong class="label_tit">공지 검색</strong></label> 
								<label for="work_divide" class="ml33"></label> 
								<label for="work_divide" class="ml66"><strong class="label_tit">공지구분</strong></label>
								<select name="s_category_code" id="s_category_code" class="select_st ml27" title="검색조건선택">
									<option value="">전체</option>
									<c:forEach var="codeList" items="${codeList}" varStatus="status">
										<option value="${codeList.code_value}"
											<c:if test="${codeList.code_value eq box.s_category_code}">selected="selected"</c:if>>${codeList.code_name}</option>
									</c:forEach>
								</select> 
								
								<label for="work_divide" class="ml66"></label><label for="work_divide" class="ml33"></label><label for="work_divide" class="ml8"></label> 
								<label for="work_divide" class="ml66"><strong class="label_tit">제목</strong></label> 
								<input name="searchWrd" type="text" size="35" value='<c:out value="${box.searchWrd}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml42"> 
								<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" /></a>
							</li>
						</ul>
						<table summary="공지 게시판입니다.">
							<caption>공지 게시판</caption>
							<thead>
								<tr>
									<th class="thFrist"><input type="checkbox" id="AllCheck" name="AllCheck" onclick="AllCheckbox('chk', this.checked);" class="checkbox"></th>
									<th>번호</th>
									<th>제목</th>
									<th>PUSH 내용</th>
									<th>발송일자</th>
									<th>1차 사업소</th>
									<th>2차 사업소</th>
									<th>공지구분</th>
									<th>사용여부</th>
								</tr>
							</thead>
							<tbody>	
								<c:forEach var="topList" items="${topList}" varStatus="status">
									<tr>
										<td><input type="checkbox" name="chk" value="${topList.board_idx}" class="checkbox"></td> 
										<td><font style="color:red;font-weight:bold;">Notice</font></td>
										<td>
											<a style="color: #729ACF; text-decoration: underline; float: left;" href="javascript:goView(<c:out value="${topList.board_idx}"/>)">
												<c:choose>
													<c:when test="${fn:length(topList.board_title) > 16}">
														<c:out value="${fn:substring(topList.board_title,0,16)}" /> ...
													</c:when>
													<c:otherwise>
														<c:out value="${topList.board_title}" />
													</c:otherwise>
												</c:choose>
											</a>
										</td>
										<td>
											<font style="float:left;"><c:out value="${topList.push_cont}" /></font> 
										</td>
										<td><c:out value="${topList.send_date}" /></td>
										<td>
											<c:out value="${topList.fst_bizplc_cd_nm}" />
										</td>
										<td>
											<c:out value="${topList.scd_bizplc_cd_nm}" />
										</td>
										
										<td>
											<c:out value="${topList.code_name}" />
										</td>
										
										<td>
											<c:if test="${topList.use_yn eq 'N'}">
												미사용
											</c:if> 
											<c:if test="${topList.use_yn eq 'Y'}">
												사용
											</c:if>
										</td>
									</tr>	
								</c:forEach>
								<c:forEach var="result" items="${list}" varStatus="status">
									<tr>
										<td><input type="checkbox" name="chk" value="${result.board_idx}" class="checkbox"></td> 
										<td><c:out	value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" /></td>
										<td>
											<a style="color: #729ACF; text-decoration: underline; float: left;" href="javascript:goView(<c:out value="${result.board_idx}"/>)">
												<c:choose>
													<c:when test="${fn:length(result.board_title) > 16}">
														<c:out value="${fn:substring(result.board_title,0,16)}" /> ...
													</c:when>
													<c:otherwise>
														<c:out value="${result.board_title}" />
													</c:otherwise>
												</c:choose>
											</a>
										</td>
										<td>
											<font style="float:left;"><c:out value="${result.push_cont}" /></font>  
										</td>
										<td><c:out value="${result.send_date}" /></td>
										<td>
											<c:out value="${result. fst_bizplc_cd_nm}" />
										</td>
										<td>
											<c:out value="${result.scd_bizplc_cd_nm}" />
										</td>
										<td>
											<c:out value="${result.code_name}" />
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
					</div>
					<!-- // mid -->
				</div> 
	        	<!-- end container -->  
	        	
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

