<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
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
 
<!-- 달력 관련 스크립트  -->
<link href="<c:url value='/css/kdn/com/cmm/jquery-ui.css' />" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.ui.datepicker-ko.js' />"></script>
<!-- 레이아웃 팝업 -->
<link href="<c:url value='/css/kdn/layoutPop/jquery.powertip-green.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery.powertip.min.js' />"></script>

<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: "111000", d2: "111002" }); 
		$("#s_category_code option:eq(3)").remove();
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var schFst_bizplc_cd= "<c:url value='${box.fst_bizplc_cd}' />";
		$("#fst_bizplc_cd > option[value = " + schFst_bizplc_cd + "]").attr("selected", "true");
		var check_yn_req = "<c:url value='${box.check_yn}' />";
		$("#check_yn > option[value = " + check_yn_req + "]").attr("selected", "true");
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 1차 사업소 검색한 경우는 2차 사업소 페이지에 나오게 하고 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		if($.trim(schFst_bizplc_cd) != ''){
			setScdBizplcList(schFst_bizplc_cd);
		}
		var schScd_bizplc_cd = "<c:url value='${box.scd_bizplc_cd}' />";
		$("#scd_bizplc_cd > option[value = " + schScd_bizplc_cd + "]").attr("selected", "true");
	});
	
	//달력 스크립트 
	$(function () {
		var dates = $("#regdate_start").datepicker({
			//defaultDate: "+1w",
			defaultDate: "",
			changeMonth: true,
			onSelect: function (selectedDate) {
				var option = this.id == "regdate_start" ? "minDate" : "maxDate", 
					instance = $(this).data("datepicker"),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings);
				dates.not(this).datepicker("option", option, date);
			}
		});
		
		var dates = $("#regdate_end").datepicker({
			//defaultDate: "+1w",
			defaultDate: "",
			changeMonth: true,
			onSelect: function (selectedDate) {
				var option = this.id == "regdate_end" ? "minDate" : "maxDate", 
					instance = $(this).data("datepicker"),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings);
				dates.not(this).datepicker("option", option, date);
			}
		}); 
	});
	
	function press(event) {
		if (event.keyCode == 13) {
			goSearch();
		}
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
	 * 선택한 대상 승인
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goAllConfirm() {
		var chkLen = $("input[name=chk]:checked").length;
		if (chkLen <= 0) {
			alert("승인할 대상을 지정해 주세요.");
			return;
		}
		document.frm.action = "<c:url value='/kdn/admin/mobileAllConfirm.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch() {
		document.frm.action = "<c:url value='/kdn/admin/mobileConfirmList.do'/>";
		document.frm.pageIndex.value = "1" ;
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/mobileConfirmList.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 달력 이미지 클릭시 달력 표시
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function calljscal(id) {
		$('#' + id).datepicker("show");
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 달력 데이터 리셋
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function dateReset() {
		$("#regdate_start").val("");
		$("#regdate_end").val("");
	}
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 승인여부의 미승인 컬럼 체크시 자동 승인 처리 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setCheckYN(m_idx , check_yn){
		var requestUrl = "/ajax/setMobileCheckYN.json"; 
		var queryParam = "m_idx=" + m_idx+"&check_yn="+check_yn;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code); 
				if(data.code == '001'){
					var check_yn_tmp = "";
					var check_date = "" ;
				 	if(data.check_yn=="Y"){
						check_yn_tmp = "승인" ;
						check_date = data.check_date ;
					}else{
						check_yn_tmp = "미승인" ;
						check_date = data.check_date ;
					} 
					var tmp = "<a href=\"#\" style=\"color: #729ACF; text-decoration: underline;\" onclick=\"setCheckYN('"+m_idx+"' , '"+data.check_yn+"');\">"+check_yn_tmp+"</a>" ;
					$("#checkYN_"+m_idx).html(tmp);
					$("#checkDate_"+m_idx).html(data.check_date);
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
				<h3>모바일 승인</h3>
				<p class="navi">
					<strong>Home</strong> &gt; 모바일 관리 &gt; <strong class="location">모바일 승인</strong>
				</p>
			</div>
			<!-- // top -->
			<!-- mid -->
			<div class="mid" >
				<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/patrolman/List.do'/>">
				<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
					<ul>
						<li>
						<label for="constitutor"><strong class="label_tit">목록 검색</strong></label>
						<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>1차사업소</strong></label> 
							<select id="fst_bizplc_cd" name="fst_bizplc_cd" class="select_st2 ml8" onchange="setScdBizplcList(this.value);">
								<option value="" >선택하세요</option>
								<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
									<%-- <option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.searchCnd1}">selected="selected"</c:if> >${fstBizplcList.code_name}</option> --%>
									<option value="${fstBizplcList.code_value}">${fstBizplcList.code_name}</option>
								</c:forEach>
							</select> 
							<label for="work_divide" class="ml21"></label> 
							<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>2차사업소</strong></label> 
							<select id="scd_bizplc_cd" name="scd_bizplc_cd" class="select_st2 ml8" >
								<option value="" > ← 선택하세요</option>
							</select> 
						 </li>
						 <li>
						  	<label for="work_divide" class="ml21">
						  	<label for="work_divide" class="ml21"><label for="work_divide" class="ml21">
							<label for="work_divide" class="ml21"><strong class="label_tit">검색어</strong></label> 
							<input name="searchWrd" type="text" size="20" placeholder="회원검색" value='<c:out value="${box.searchWrd}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml8"> 
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" /></a>
							<label for="work_divide" class="ml21"><strong class="label_tit">승인여부</strong></label> 
							<select name="check_yn" id="check_yn" class="select_st ml8" title="검색조건선택" >
								<option value="">전체</option>
								<option value="Y">승인</option>
								<option value="N">미승인</option>
							</select> 
							<label for="work_divide" class="ml21"></label>
							<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>등록일</strong></label>
							<input name="regdate_start" id="regdate_start" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" value="${box.regdate_start}" readonly>
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('regdate_start')" style="cursor:pointer;">
							~
							<input name="regdate_end" id="regdate_end" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" value="${box.regdate_end}" readonly>
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('regdate_end')" style="cursor:pointer;">
							<input id="ListShow" type="button" value="초기화" title="리스트" onclick="dateReset();" class="button" style="cursor:pointer;">
						 </li>
					</ul>
					<table summary="공지 게시판입니다.">
						<caption>순시자 게시판</caption>
						<thead>
							<tr>
								<th><input type="checkbox" id="AllCheck" name="AllCheck" onclick="AllCheckbox('chk', this.checked);" class="checkbox ml8"></th>
								<th>번호</th>
								<th>아이디(이름)</th>
								<th>승인여부</th>
								<th>승인일</th>
								<th>등록일</th>
							</tr>
						</thead>
						<div id="content" style="border:1px solid red; font-size:11px; visibility:hidden; background-color='#FFFFCC'" ></div>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td><input type="checkbox" name="chk" value="${result.m_idx}" class="checkbox ml8"></td>
									<td><c:out	value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" /></td>
									<td> 
									<%-- <a style="color:#729ACF;" href="#" id="regId_${result.mobile_sn}"> --%>
											<c:out value="${result.reg_id}" />(<c:out value="${result.user_name}" />)
										<!-- </a> -->
									</td>
									<td id="checkYN_${result.m_idx}" >
										<c:if test="${result.check_yn eq 'Y'}">
											<c:set value="승인 " var="check_yn" />
										</c:if>
										<c:if test="${result.check_yn eq 'N'}">
											<c:set value="미승인 " var="check_yn" />
										</c:if>
									  	<a href="#" style= "color: #729ACF; text-decoration: underline;" onclick="setCheckYN('${result.m_idx}' , '${result.check_yn}');"><c:out value="${check_yn}" /></a>
									<td id="checkDate_${result.m_idx}"><c:out value="${result.check_date}" /></td>
									<td><c:out value="${result.reg_date}" /></td>
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
						<input type="button" value="선택승인" title="선택승인" onclick="goAllConfirm(); return false;" class="btn_gray"> 
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

