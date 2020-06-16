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
  * @Class Name : EgovBoardMstrRegist.jsp
  * @Description : 게시판 생성 화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.03.12   이삼섭          최초 생성
  * @ 2009.06.26   한성곤          2단계 기능 추가 (댓글관리, 만족도조사)
  *   2011.09.15   서준식          2단계 기능 추가 (댓글관리, 만족도조사) 적용 방법 변경
  *   2011.11.11   이기하		   첨부파일선택 여부에 따른 첨부가능파일 숫자에 대한 체크추가
  *  @author 공통서비스 개발팀 이삼섭
  *  @since 2009.03.12
  *  @version 1.0
  *  @see
  *
  */
%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css"> --%>
<%-- <script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/sym/cal/EgovCalPopup.js' />" ></script> --%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<script type="text/javascript">
	$(document).ready(function () {
		//$('#topmenu').topmenu({ d1: 6, d2: 1 });
		$('#topmenu').topmenu({ d1: "106000", d2: "106001" }); 

		
/* 		$(function () {
			var dates = $("#startdate, #enddate, #send_date").datepicker({
				defaultDate: "+1w",
				changeMonth: true,
				onSelect: function (selectedDate) {
					var option = this.id == "startdate" ? "minDate" : "maxDate", 
						instance = $(this).data("datepicker"),
						date = $.datepicker.parseDate(
							instance.settings.dateFormat ||
							$.datepicker._defaults.dateFormat,
							selectedDate, instance.settings);
					dates.not(this).datepicker("option", option, date);
				}
			});
		}); */
		
		$("th").last().addClass("th_bot");
		$("td").last().addClass("td_bot");
		
	});
/* 	var remarks = "<c:url value='${box.remarks}' />'";
	
	if($.trim(remarks) != ''){
		setTracksList(remarks);
	} */
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		form.action = "<c:url value='/kdn/nfc/List.do'/>";
		form.submit();
	}
	
	/* function setTracksList(eqpNo){
		
		var data = getTracksList(eqpNo);
		var addTracksList = "";
		
		//addTracksList = "<c:out value=" + data.eqpInfo[0].eqp_no + "></c:out>";
		addTracksList = data.eqpInfo.EQP_NO; 
		alert(data.eqpInfo.EQP_NO);
		$("#abc").val(data.eqpInfo.EQP_NO);
		//$("#abc").append(addTracksList);
//		$("#fst_bizplc_cd").value=addTracksList;

	}  */

	/* function getTracksList(eqpNo){
		var requestUrl = "/kdnweb/ajax/getEqpInfo.json"; 
		var queryParam = "eqp_no=" + eqpNo;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.eqpInfo); 
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
	} */
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		//document.saveFrm.onsubmit(); // 에디터의 값을 얻기 위해서는 FORM 의 onsubmit()(document.form.onsubmit();) 함수를 호출하여 얻을 수 있습니다.
		
	/* 	if ($.trim(document.frm.fst_bizplc_cd.value) == '') {
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
		if ($.trim(document.frm.scd_bizplc_cd.value) == '') {
			alert("2차사업소를 선택해주세요.");
			$("#scd_bizplc_cd").focus();
			return;
		} */
		
		var keyVal = $("#tag").val();	   
		if(keyVal != null && keyVal != "" && keyVal != "undefined"){ 
			//수정
			if(confirm("수정하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/nfc/Update.do'/>";
				document.saveFrm.submit();
			}
		}
	}

</script>

<title>순시자 수정</title>
<style type="text/css">
h1 {font-size:12px;}
caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}

/* 에디터 Style  */
.noStyle {background:ButtonFace; BORDER-TOP:0px; BORDER-bottom:0px; BORDER-left:0px; BORDER-right:0px;}
.noStyle th{background:ButtonFace; padding-left:0px;padding-right:0px}
.noStyle td{background:ButtonFace; padding-left:0px;padding-right:0px}
</style>

</head>
<body><!-- 에디터  -->
<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>

<!-- wrap -->
<div id="wrap">

<!-- Left Area -->
<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
<!-- // Left Area -->
<!-- Top Area -->
<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
<!-- // Top Area -->

<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/kdn/admin/patrolman/Update.do'/>">
	<input id="nfc_uid" name="nfc_uid" type="hidden"	value="<c:out value='${viewBox.nfc_uid}'/>" />
	<!-- 검색 파라메터  -->
	<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
	<input type="hidden" id="tag" name="tag"  value="<c:out value='${viewBox.tag}'/>"/>
	<input type="hidden" name="scd_bizplc_cd_old"  value="<c:out value='${viewBox.scd_bizplc_cd}'/>"/>
	<input type="hidden" name="fnct_lc_no_old"  value="<c:out value='${viewBox.fnct_lc_no}'/>"/>
	<div id="contents">
		<!-- top -->
		<div class="top">
			<h3>NFC태그 수정</h3>
			<p class="navi">
				<strong>Home</strong> &gt; NFC태그 관리 &gt; <strong class="location">NFC태그 수정</strong>
			</p>
		</div>	
		<table width="700px" border="0" cellpadding="0" cellspacing="0"	summary="">
			<tr>
			   <th width="20%" height="23" class="th_top" nowrap ><span class="star">*</span>TAG</th>
			   <td width="80%" nowrap colspan="7" class="td_top">
					&nbsp;&nbsp;<c:out value='${viewBox.tag }'></c:out>
				</td>
			</tr>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>선로명</th>
				<td width="80%" nowrap colspan="7">
					&nbsp;&nbsp;<c:out value='${viewBox.fnct_lc_dtls }'></c:out>
				</td>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>지지물명</th>
				<td width="80%" align="center" nowrap colspan="7">
  					&nbsp;&nbsp;<c:out value='${viewBox.eqp_nm }'></c:out>
				</td>
			</tr>
			<tr>
			  	<th width="20%" height="23" nowrap><span class="star">*</span>작업구분</th>
				<td width="80%" nowrap colspan="7">
			  		<select name="status" id="status" class="select_st ml27" title="검색조건선택">
			  			<option value="">선택해주세요</option>
			  			<c:forEach var="codeList" items="${codeList}" varStatus="status">
			  				<option value="${codeList.code_value}"<c:if test="${codeList.code_value eq viewBox.status}">selected="selected"</c:if>>${codeList.code_name}</option>
			  			</c:forEach>
			  		</select>

				</td>
			</tr>
						<tr>
			  	<th width="20%" height="23" nowrap>비고</th>
				<td width="80%" nowrap colspan="7">
					<input id="remarks" name="remarks" type="text" size="50" value="${viewBox.remarks}"  maxlength="50" title="비고입력" onchange="setTracksList(this.value);">
				</td>
			</tr>
		</table>
		
		<div style="padding: 10px 0 0 10px">
			<c:if test="${not empty viewBox.tag}">
				<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="goSave(); return false;" class="btn_gray" />
			</c:if>
			<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray" />
		</div>
	</div>
<!-- bottom Area -->
<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
<!-- // bottom Area -->
</form>
</div>

</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

