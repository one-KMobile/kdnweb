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
<html lang="ko" xmlns:v="urn:schemas-microsoft-com:vml">   
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
<!-- 네이버 지도 API -->
<script type="text/javascript" src="http://openapi.map.naver.com/openapi/naverMap.naver?ver=2.0&key=c18aa4beadb9c5424eb889800108ffe5"></script>

<script type="text/javascript">  
function press(event) {
	if (event.keyCode==13) {
		goSearch();
	}
}
var loading = "";
$(document).ready(function () {
	//$('#topmenu').topmenu({ d1: 6, d2: 1 });  
	$('#topmenu').topmenu({ d1: "106000", d2: "106001" });  
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 리스트 선로명 기준으로 한번만 보여주기
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	var idStr = "";
	var idArrayList = new Array();
	$("[id ^= tracksNM]").each(function(index){
		var id = $(this).attr("id");
		var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
		if(orgIdStr != idStr){ 
			idArrayList.push(orgIdStr);

		}

		idStr = orgIdStr;
	});

	for(var i = 0; i < idArrayList.length; i++){  
		$("[id *= tracksNM_"+ idArrayList[i] +"]").not(":eq(0)").empty();

	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색한 후 검색 목록 유지
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	var fst_bizplc_cd = "<c:url value='${box.fst_bizplc_cd}' />";
	var scd_bizplc_cd = "<c:url value='${box.scd_bizplc_cd}' />";
	var fnct_lc_no = "<c:url value='${box.fnct_lc_no}' />";
	
	if($.trim(fst_bizplc_cd) != ''){
		setScdBizplcList(fst_bizplc_cd);
		$("#scd_bizplc_cd").val(scd_bizplc_cd);
	}
	if($.trim(scd_bizplc_cd) != ''){
		setTracksList(scd_bizplc_cd);
		$("#fnct_lc_no").val(fnct_lc_no);
	}
});
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goBatchWrite() {
		document.frm.action = "<c:url value='/kdn/nfc/Batch/Insert.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goWrite() {
		document.frm.action = "<c:url value='/kdn/nfc/Write.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key){
		document.frm.tag.value = key;
		document.frm.action = "<c:url value='/kdn/nfc/View.do'/>";
		document.frm.submit(); 
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 엑셀 생성
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goExcel() {
		document.frm.action = "<c:url value='/kdn/nfc/excel/create.do'/>";
		document.frm.submit();
		
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 로딩 바
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	$(window).ajaxStart(function() {
		loading.show();
		alert("엑셀파일 생성을 시작합니다.");
	});
	
	$(window).ajaxStop(function() {
		alert("엑셀파일을 생성하였습니다.");
		loading.hide();
	});
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch(){
		if ($.trim(document.frm.fst_bizplc_cd.value) == '') {
			alert("1차사업소를 선택해주세요.");
			$("#fst_bizplc_cd").focus();
			return;
		}
		if ($.trim(document.frm.scd_bizplc_cd.value) == '') {
			alert("2차사업소를 선택해주세요.");
			$("#scd_bizplc_cd").focus();
			return;
		}
		if ($.trim(document.frm.fnct_lc_no.value) == '') {
			alert("선로명을 선택해주세요.");
			$("#fnct_lc_no").focus();
			return;
		}
		document.frm.action = "<c:url value='/kdn/nfc/List.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) { 
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/nfc/List.do'/>";
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
		$("#scd_bizplc_cd").empty();
		$("#scd_bizplc_cd").append(addScdBizplcList);
	}  
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 선로명 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setTracksList(scdBizplcCode){
		var data = getTracksList($("#fst_bizplc_cd").val(), scdBizplcCode);
		var addTracksList = "";
		if(data != null && data.tracksList != null && data.tracksList.length > 0){
			addTracksList +='<option value="" >선택하세요.</option>';
			for (var i = 0; i < data.tracksList.length; i++) {
				addTracksList +='<option value="' + data.tracksList[i].VALUE +'" >' + data.tracksList[i].NAME + '</option>';	
			}
		}else{
			addTracksList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#fnct_lc_no").empty();
		$("#fnct_lc_no").append(addTracksList);	
	} 
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 엑셀 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setExcelCreate(){
		loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="http://moonky.woobi.co.kr/tc/attach/1/1189912512.gif" width="100" height="100" style="cursor: pointer;" id="tc/attach/1/1189912512.gif" />').appendTo(document.body).hide();
		//loading = $('<img id="loading_img" alt="loading" src="localhost:8080/images/kdn/progressbar/loading_bar.gif"/>').appendTo(document.body).hide();
		getExcelCreate();
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
					alert("엑셀파일을 생성하지 못했습니다.");
				}
			},
		    error:function(XMLHttpRequest,textStatus,errorThrown){
		    	alert("error");
			}
		});
		return returnParam; 
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 선로명 목록 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getTracksList(fstBizplcCode, scdBizplcCode){
		var requestUrl = "/ajax/getTracksList.json"; 
		var queryParam = "fst_bizplc_cd=" + fstBizplcCode + "&scd_bizplc_cd=" + scdBizplcCode;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.tracksList); 
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
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 엑셀 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getExcelCreate(){
		var requestUrl = "/ajax/getExcelCreate.json"; 
		var queryParam = "";   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.nfcList); 
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
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 네이버 지도 호출
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	
	function goList(){
		$("#ListShow").hide();
		$("#NFCTable").show();
		$("#pagination").show();
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

/* 로딩*/
#loading {
	height: 100%;
	left: 0px;
	position: fixed;
	_position:absolute; 
	top: 0px;
	width: 100%;
	filter:alpha(opacity=50);
	-moz-opacity:0.5;
	opacity : 0.5;
}

.loading {
	background-color: white;
	z-index: 199;
}

#loading_img{
	width:150px;
	position:absolute; 
	top:50%;
	left:50%;
	height:150px;
	margin-top:-75px;	//	이미지크기
	margin-left:-75px;	//	이미지크기
	z-index: 200;
		
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
	
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/nfc/List.do'/>"> <!-- target="_hidden" -->
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
			<input name="tag" type="hidden"	value="" />

			<!-- 구성관리 contents -->
			<div class="container">
				<!-- top -->
				<div class="top">
					<h3>NFC목록</h3>
					<p class="navi">
						<strong>Home</strong> &gt; NFC 관리 &gt; <strong class="location">NFC 목록</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid" >
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>1차사업소</strong></label> 
							<select id="fst_bizplc_cd" name="fst_bizplc_cd" class="select_st2 ml27"  onchange="setScdBizplcList(this.value);">
								<option value="" >선택하세요</option>
								<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
									<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.fst_bizplc_cd}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
								</c:forEach>
							</select> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>2차사업소</strong></label> 
							<select id="scd_bizplc_cd" name="scd_bizplc_cd" class="select_st2 ml27" onchange="setTracksList(this.value);">
								<option value="" > ← 선택하세요</option>
							</select> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>선로명</strong></label> 
							<select id="fnct_lc_no" name="fnct_lc_no" class="select_st2 ml27">
								<option value="" > ← 선택하세요</option>
							</select> 
						</li>
						<li>
							<%-- <label for="work_divide" class="ml137"><strong class="label_tit">순시종류</strong></label>
							<select id="searchCnd4" name="searchCnd4" class="select_st2 ml27">
								<option value="" >선택하세요</option>
								<c:forEach var="codeList" items="${codeList}" varStatus="status">
									<option value="${codeList.code_value}" <c:if test="${codeList.code_value eq viewBox.group_code_idx}">selected="selected"</c:if> >${codeList.code_name}</option>
								</c:forEach>
							</select> --%>
							<label for="work_divide" class="ml137"><strong class="label_tit">지지물</strong></label>
							<input name="eqp_nm" type="text" size="35" value='<c:out value="${box.eqp_nm}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml42">
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" style="padding-left: 10PX;"/></a>
							<input id="ListShow" type="button" value="목록 보기" title="리스트" onclick="goList();" class="button" style="display:none;float: right;margin-right: 60px;cursor:pointer;">
						</li> 
					</ul>
					<table summary="NFC 게시판입니다." id="NFCTable">
						<caption>NFC 게시판</caption>
						<thead>
							<tr>
								<th>번호</th>
								<th>선로명</th>
								<th>지지물명</th>
								<th>T A G</th>
<!-- 								<th>NFC_UID</th> -->
<!-- 								<th>비고</th> -->
								<th>이력</th>
								<th>작업구분</th>
								<th>생성일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td> 
										<c:out value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" />
									</td>
									<td id = "tracksNM_${result.fnct_lc_dtls}">
										<c:out value="${result.fnct_lc_dtls}" />
									</td>
									<td>
										<c:out value="${result.eqp_nm}" />
									</td>
									<td align="center"><a href="#" style= "color: #729ACF; text-decoration: underline;" onclick="goView('${result.tag}');">
										<c:out value="${result.tag}" /></a>
									</td>
<!-- 									<td> -->
<%-- 										<c:out value="${result.nfc_uid}" /> --%>
<!-- 									</td> -->
<!-- 									<td> -->
<%-- 										<c:out value="${result.remarks}" /> --%>
<!-- 									</td> -->
									<td>
										<c:out value="${result.work_status}" />
									</td>
									<td>
										<c:choose>
											<c:when test="${result.status eq 'U'}">
												<c:out value="사용"/>
											</c:when>
											<c:when test="${result.status eq 'D'}">
												<c:out value="폐기"/>
											</c:when>
											<c:otherwise>
												<c:out value="파손"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:out value="${result.reg_date}" />
									</td>
								</tr>
							</c:forEach>	
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.scd_bizplc_cd}">
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">검색하신 송전설비 데이터가 없습니다.</font>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">송전설비 검색 조건을 선택하세요.</font>
											</td>
										</tr>	
									</c:otherwise>
								</c:choose>
							</c:if>
						</tbody>
					</table>
					<!-- Loading Image -->
					<div id="divLoadBody" style="padding: 10px 0 0 10px">
						<input type="button" value="Excel이력생성" title="Excel이력생성" onclick="javascript:setExcelCreate();return false;" class="btn_gray">
						<input type="button" value="일괄등록" title="일괄등록" onclick="javascript:goBatchWrite();return false;" class="btn_gray">
						<input type="button" value="개별등록" title="개별등록" onclick="javascript:goWrite();return false;" class="btn_gray">
						<!-- <img src="/images/kdn/progressbar/loading_bar.png"> -->	
					</div>  
					
					<div id="pagination" class="paging" align="center">
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_pageview" />
					</div>
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
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
</html>

<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->


