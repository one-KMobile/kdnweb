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
<link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript">
	function press(event) {
		if (event.keyCode==13) {
			fn_egov_select_noticeList('1');
			goSearch();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key){
		document.frm.bbs_seq.value = key;
		document.frm.action = "<c:url value='/sample/egovSampleRefView.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goWrite(){
		document.frm.action = "<c:url value='/sample/egovSampleRefWrite.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch(){
		document.frm.action = "<c:url value='/sample/egovSampleRefList.do'/>";
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
			document.frm.action = "<c:url value='/sample/egovSampleRefDelete.do'/>";
			document.frm.submit();
		}
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) { 
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/sample/egovSampleRefList.do'/>";
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
	<form id="frm" name="frm" method="post" action="<c:url value='/sample/egovSampleRefList.do'/>"> <!-- target="_hidden" -->
		<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
		<input name="bbs_seq" type="hidden"	value="" />

		<div id="border" style="width: 730px">
			<table width="100%" cellpadding="8" class="table-search" border="0">
				<tr>
					<td width="40%" class="title_left"><img src="<c:url value='/images/egovframework/com/cmm/icon/tit_icon.gif' />" width="16" height="16" hspace="3" align="middle" alt="제목버튼이미지">&nbsp;자료실</td>
					<td width="10%">
						<select name="searchCnd" class="select" title="검색조건선택">
							<option value="All" <c:if test="${box.searchCnd == 'All'}">selected="selected"</c:if> >전체</option>
							<option value="Subject" <c:if test="${box.searchCnd == 'Subject'}">selected="selected"</c:if> >제목</option>
							<option value="Content" <c:if test="${box.searchCnd == 'Content'}">selected="selected"</c:if> >내용</option>
						</select>
					</td>
					<td width="35%"><input name="searchWrd" type="text" size="35" value='<c:out value="${box.searchWrd}"/>' maxlength="35"	onkeypress="press(event);" title="검색단어입력"></td>
					<th width="10%">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><span class="button"><input type="submit" value="조회" title="조회"	onclick="javascript:goSearch();return false;"></span></td>
								<td style="padding-left: 10px;"><span class="button"><input type="button" value="등록" title="등록"	onclick="javascript:goWrite();return false;"></span></td>
							</tr>
						</table>
					</th>
				</tr>
			</table>
			<table width="100%" cellpadding="8" class="table-line">
				<thead>
					<tr>
						<th class="title" width="5%" nowrap><input type="checkbox" id="AllCheck" name="AllCheck" onclick="AllCheckbox('chk', this.checked);"></th>
						<th class="title" width="5%" nowrap>번호</th>
						<th class="title" width="40%" nowrap>제목</th>
						<th class="title" width="10%" nowrap>작성일</th>
						<th class="title" width="8%" nowrap>첨부</th>
						<th class="title" width="5%" nowrap>조회</th>
						<th class="title" width="5%" nowrap>사용여부</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td class="lt_text3" nowrap>
								<input type="checkbox" name="chk" value="${result.bbs_seq}" class="check2">
							</td>
							<td class="lt_text3" nowrap> 
								<c:out value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" />
							</td>
							<td class="lt_text3" nowrap >
								<a style="color:#729ACF;text-decoration:underline;float: left;" href="javascript:goView(<c:out value="${result.bbs_seq}"/>)"><c:out value="${result.subject}" /></a>
							</td>
							<td class="lt_text3" nowrap>
								<c:out value="${result.reg_date_string}" />
							</td>
							<td class="lt_text3" nowrap> 
								<c:if test="${not empty result.file_id and result.filecount > 0}">
									<img src="<c:url value='/images/egovframework/com/cmm/icon/ico_file.gif'/>" alt="fileIcon">
								</c:if>
							</td>
							<td class="lt_text3" nowrap>
								<c:out value="${result.read_count}" />
							</td>
							<td class="lt_text3" nowrap>
								<c:if test="${result.use_yn eq 'N'}">
									미사용
								</c:if> 
								<c:if test="${result.use_yn eq 'Y'}">
									사용
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
						<tr> 
							<td class="lt_text3" nowrap colspan="7" >
								<font style="font-family:맑은 고딕;font-size:12px">자료실 데이터가 없습니다.</font>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-top: 10px;">
				<tr>
					<td height="10"><span class="button"><input type="submit" value="삭제" onclick="goSelectDel(); return false;"></span></td>
				</tr>
			</table>
			
			<!-- 페이지 목록 -->
			<div id="pagination" class="paging" align="center"> 
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_pageview" />
			</div>
		</div>
	</form>
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

