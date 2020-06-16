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
<%-- <link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css"> --%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>

<!-- 달력 관련 스크립트  -->
<link href="<c:url value='/css/kdn/com/cmm/jquery-ui.css' />" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.ui.datepicker-ko.js' />"></script> 

<script type="text/javascript">
	$(document).ready(function(){		
		$('#topmenu').topmenu({ d1: 3, d2: 1 });  
	});

	function press(event) {
		if (event.keyCode==13) {
			goSearch();
		}
	}
	
	$(document).ready(function () {
		$(function () {
			var dates = $("#ins_date, #enddate").datepicker({
				defaultDate: "+1w", //d - 일, w - 주, m - 월, y - 년 
				changeMonth: true,
				onSelect: function (selectedDate) {
					var option = this.id == "ins_date" ? "minDate" : "maxDate", 
						instance = $(this).data("datepicker"),
						date = $.datepicker.parseDate(
							instance.settings.dateFormat ||
							$.datepicker._defaults.dateFormat,
							selectedDate, instance.settings);
					dates.not(this).datepicker("option", option, date);
				}
			});
		});
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 리스트 선로명 기준으로 한번만 보여주기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var idStr = "";
		var idArrayList = new Array();
		$("[id ^= tracksTd]").each(function(index){
			var id = $(this).attr("id");
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			if(orgIdStr != idStr){ 
				idArrayList.push(orgIdStr); 
			}
			idStr = orgIdStr;
		});
		
		for(var i = 0; i < idArrayList.length; i++){
			$("[id *= tracksTd_"+ idArrayList[i] +"]").not(":eq(0)").empty();
		}
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var searchCnd1 = "<c:url value='${box.searchCnd1}' />";
		var searchCnd2 = "<c:url value='${box.searchCnd2}' />";
		var searchCnd3 = "<c:url value='${box.searchCnd3}' />";
		
		if($.trim(searchCnd1) != ''){
			setScdBizplcList(searchCnd1);
			$("#searchCnd2").val(searchCnd2);
		}
		if($.trim(searchCnd2) != ''){
			setTracksList(searchCnd2);
			$("#searchCnd3").val(searchCnd3);
		}
	});
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key){
		document.frm.group_code_idx.value = key;
		document.frm.action = "<c:url value='/kdn/admin/insResultMantView.do'/>";
		document.frm.submit(); 
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch(){
		if ($.trim(document.frm.searchCnd1.value) == '') {
			alert("1차사업소를 선택해주세요.");
			$("#searchCnd1").focus();
			return;
		}
		if ($.trim(document.frm.searchCnd2.value) == '') {
			alert("2차사업소를 선택해주세요.");
			$("#searchCnd2").focus();
			return;
		}
		/* if ($.trim(document.frm.searchCnd3.value) == '') {
			alert("선로명을 선택해주세요.");
			$("#searchCnd3").focus();
			return;
		} */
		
		document.frm.action = "<c:url value='/kdn/admin/insResultMantView.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) { 
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/insResultMantView.do'/>";
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
	* 선로명 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setTracksList(scdBizplcCode){
		var data = getTracksList($("#searchCnd1").val(), scdBizplcCode);
		var addTracksList = "";
		if(data != null && data.tracksList != null && data.tracksList.length > 0){
			addTracksList +='<option value="" >선택하세요.</option>';
			for (var i = 0; i < data.tracksList.length; i++) {
				addTracksList +='<option value="' + data.tracksList[i].VALUE +'" >' + data.tracksList[i].NAME + '</option>';	
			}
		}else{
			addTracksList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#searchCnd3").empty();
		$("#searchCnd3").append(addTracksList);	
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
	* 달력 이미지 클릭시 달력 표시
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function calljscal(id) {
		var id = '#' + id
		$(id).datepicker("show");
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 달력 데이터 리셋
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function dateReset() {
		$("#ins_date").val("");
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
	<!-- Top Area -->
	<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
	<!-- // Top Area -->
	
	<!-- container -->
	<div id="container">
		<!-- Left Area -->
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/groupCodeList.do'/>"> <!-- target="_hidden" -->
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
			<input name="group_code_idx" type="hidden"	value="" />

			<!-- 구성관리 contents -->
			<div class="contents1">
				<!-- top -->
				<div class="top">
					<h3>순시결과 검색</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 순시결과 관리 &gt; <strong class="location">순시결과 검색</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid" >
					<ul>
						<li> 
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>1차사업소</strong></label> 
							<select id="searchCnd1" name="searchCnd1" class="select_st2 ml27" onchange="setScdBizplcList(this.value);">
								<option value="" >선택하세요</option>
								<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
									<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.searchCnd1}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
								</c:forEach>
							</select> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>2차사업소</strong></label> 
							<select id="searchCnd2" name="searchCnd2" class="select_st2 ml27" onchange="setTracksList(this.value);">
								<option value="" > ← 선택하세요</option>
							</select> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>선로명</strong></label> 
							<select id="searchCnd3" name="searchCnd3" class="select_st2 ml27">
								<option value="" > ← 선택하세요</option>
							</select> 
						</li>
						<li>
							<label for="work_divide" class="ml66"></label>
							<label for="work_divide" class="ml21"><strong class="label_tit">순시자</strong></label> 
							<input name="check_ins_name_a" id ="check_ins_name_a" type="text" size="20" placeholder="회원검색" value='<c:out value="${box.check_ins_name_a}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml8">
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" style="padding-left: 10PX;"/></a>
							
							<label for="work_divide" class="ml137"><strong class="label_tit">순시일</strong></label>    
							<input name="ins_date" id="ins_date" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" class="select_st2 ml48" value="${box.ins_date}" readonly >
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('ins_date')" style="cursor:pointer;">
							
						</li> 
					</ul>
					<table summary="순시결과 게시판입니다." >
						<caption>순시결과 게시판</caption>
						<thead>
							<tr>
								<th>번호</th>
								<th>점검종류</th>
								<th>구분</th>
								<th>점검대상</th>
								<th>주요점검대상</th>
								<th>지지물번호</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${ViewBoxList}" varStatus="status">
								<tr>
									<td>
										${status.count} 
									</td>
									<td>
											<c:out value="${result.ins_kndnm}" />
											<c:out value="${result.ins_itm_cd}" />
									</td>
									<td>
										<c:out value="${result.ins_senm}" />
									</td>
									<td>
										<c:out value="${result.ins_tgt_nm}" />
									</td>
									<td>
										<c:out value="${result.main_itm_nm}" />
									</td>
										<td>
											<c:out value="${result.good_secd}" />
											<c:out value="${result.fnct_lc_dtls}" />
										</td>
								</tr>
							</c:forEach>
							
									<%-- <td>
										<c:out value="${ViewBoxList.inspect_type}" />
									</td> --%>
							<%-- <c:forEach var="resultView" items="${ViewBoxList}" varStatus="status">
								<td>
										<c:out value="${resultView.inspect_type}" />
								</td>
							</c:forEach> --%>
							
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.searchCnd1}">
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">순시결과 데이터가 없습니다.</font>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">순시결과 검색 조건을 선택하세요.</font>
											</td>
										</tr>	
									</c:otherwise>
								</c:choose>
							</c:if>
						</tbody>
					</table>
					<!-- paging -->
					<div id="pagination" class="paging" align="center">
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_pageview" />
					</div>
					<!-- // paging -->
					
					<!-- 지도 Area  -->
					<div id="map" style="dispaly:none;"></div>
					<!-- // 지도 Area  -->					
				</div>
				<!-- // mid -->
			</div>
			<!-- // 구성관리 contents -->
		</form>
	</div>
	<!-- // container -->
	<!-- bottom Area -->
		<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
	<!-- // bottom Area -->
	
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
</html>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->


