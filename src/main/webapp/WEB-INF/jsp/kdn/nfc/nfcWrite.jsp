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

<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.ui.datepicker-ko.js' />"></script> 

<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>
<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: 6, d2: 1 });
		
// 		$(function () {
// 			var dates = $("#startdate, #enddate, #send_date").datepicker({
// 				defaultDate: "+1w",
// 				changeMonth: true,
// 				onSelect: function (selectedDate) {
// 					var option = this.id == "startdate" ? "minDate" : "maxDate", 
// 						instance = $(this).data("datepicker"),
// 						date = $.datepicker.parseDate(
// 							instance.settings.dateFormat ||
// 							$.datepicker._defaults.dateFormat,
// 							selectedDate, instance.settings);
// 					dates.not(this).datepicker("option", option, date);
// 				}
// 			});

//		});
		
// 		<c:if test="${not empty viewBox.board_idx}">
// 		if(document.saveFrm.push_cont.value != ""){ 
// 			strByteView("push_cont","subTextByte");			
// 		}
// 		</c:if>

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
		
		if($.trim(fnct_lc_no) != "") {
			setTowerList(fnct_lc_no);
			$("#eqp_no").val(eqp_no);
		}
	});

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		form.action = "<c:url value='/kdn/nfc/List.do'/>";
		form.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		//document.saveFrm.onsubmit(); // 에디터의 값을 얻기 위해서는 FORM 의 onsubmit()(document.form.onsubmit();) 함수를 호출하여 얻을 수 있습니다.
		
		 if ($.trim(document.saveFrm.fst_bizplc_cd.value) == '') {
			alert("1차사업소를 선택해주세요.");
			$("#fst_bizplc_cd").focus();
			return;
		}
		if ($.trim(document.saveFrm.scd_bizplc_cd.value) == '') {
			alert("2차사업소를 선택해주세요.");
			$("#scd_bizplc_cd").focus();
			return;
		}
		if ($.trim(document.saveFrm.fnct_lc_no.value) == '') {
			alert("선로명을 선택후세요.");
			$("#fnct_lc_no").focus();
			return;
		}
		if ($.trim(document.saveFrm.eqp_no.value) == '') {
			alert("지지물명을 선택해주세요.");
			$("#eqp_no").focus();
			return;
		}
		if ($.trim(document.saveFrm.status.value) == '') {
			alert("작업구분을 선택해주세요.");
			$("#status").focus();
			return;
		}

		//등록
		if(confirm("등록하시겠습니까?")){
			document.saveFrm.action = "<c:url value='/kdn/nfc/Insert.do'/>";
			document.saveFrm.submit();;
		}

	} 

	/* var tower = "<c:url value='${box.eqp_no}' />";

	if($.trim(tower) != ''){
		setTracksList(tower);
	} */
	
	/* function setTracksList(eqpNo){
		
		var data = getTracksList(eqpNo);

		addTracksList = data.eqpInfo.EQP_NO; 

		$("#fnct_lc_dtls").val(data.eqpInfo.FNCT_LC_DTLS);
		$("#fst_bizplc_nm").val(data.eqpInfo.FST_BIZPLC_CD_NM);
		$("#scd_bizplc_nm").val(data.eqpInfo.SCD_BIZPLC_CD_NM);
		$("#eqp_nm").val(data.eqpInfo.EQP_NM);
		$("#fnct_lc_no").val(data.eqpInfo.FNCT_LC_NO);
		$("#fst_bizplc_cd").val(data.eqpInfo.FST_BIZPLC_CD);
		$("#scd_bizplc_cd").val(data.eqpInfo.SCD_BIZPLC_CD);
		$("#eqp_no").val(data.eqpInfo.EQP_NO);

	} 

	function getTracksList(eqpNo){
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
				$("#fnct_lc_dtls").val(data.tracksList[i].NAME);
			}
		}else{
			addTracksList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#fnct_lc_no").empty();
		$("#fnct_lc_no").append(addTracksList);	
	} 
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 지지물명 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setTowerList(fnct_lc_no){
		var data = getTowerList(fnct_lc_no);
		var addTowerList = "";
		if(data != null && data.towerList != null && data.towerList.length > 0){
			addTowerList +='<option value="" >선택하세요.</option>';
			for (var i = 0; i < data.towerList.length; i++) {
				addTowerList +='<option value="' + data.towerList[i].EQP_NO +'" >' + data.towerList[i].EQP_NM + '</option>';	
			}
		}else{
			addTowerList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#eqp_no").empty();
		$("#eqp_no").append(addTowerList);	
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
	* 지지물명 목록 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getTowerList(fnct_lc_no){
		var requestUrl = "/ajax/getTowerList.json"; 
		var queryParam = "fnct_lc_no=" + fnct_lc_no;   
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
</script>

<title>공지 등록</title>
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

<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/kdn/nfc/Insert.do'/>">
	<!-- 코드입력 -->
<!-- 	<input type="hidden" name="fnct_lc_no" id="fnct_lc_no" />
	<input type="hidden" name="fst_bizplc_cd" id="fst_bizplc_cd" />
	<input type="hidden" name="scd_bizplc_cd" id="scd_bizplc_cd" /> 
	<input type="hidden" name="eqp_no_old" id="eqp_no_old" /> -->

<input type="hidden" name="fnct_lc_dtls" id="fnct_lc_dtls" />
<input type="hidden" name=fst_bizplc_nm id="fst_bizplc_nm" />
<input type="hidden" name="scd_bizplc_nm" id="scd_bizplc_nm" />
<input type="hidden" name="eqp_nm" id="eqp_nm" />
<!-- container -->
<div id="contents">
	<!-- top -->
	<div class="top">
		<h3>NFC태그 등록</h3>
		<p class="navi">
			<strong>Home</strong> &gt; NFC태그 관리 &gt; <strong class="location">NFC태그 등록</strong>
		</p>
	</div>
	<!-- // top -->	

		<table width="700px" border="0" cellpadding="0" cellspacing="0" summary="">
<!-- 			<tr> -->
<%-- 				<th width="20%" height="23" nowrap><label>시설물 코드</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />"	width="15" height="15" alt="필수입력표시"></th> --%>
<!-- 				<td width="80%" nowrap colspan="3"> -->
<!-- 					<input id="eqp_no" name="eqp_no" type="text" size="20" maxlength="20" title="시설물코드입력" onchange="setTracksList(this.value);"> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<%-- 				<th width="20%" height="23" nowrap><label>NFC_UID</label><img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />"	width="15" height="15" alt="필수입력표시"></th> --%>
<!-- 				<td width="80%" nowrap colspan="3"> -->
<!-- 					<input id="nfc_uid" name="nfc_uid" type="text" size="20"  maxlength="20" title="NFC_UID 입력"> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<%-- 			   <th width="20%" height="23" nowrap >TAG<img src="<c:url value='/images/egovframework/com/cmm/icon/required.gif' />" alt="필수입력표시"  width="15" height="15"  ></th> --%>
<!-- 			   <td width="80%" nowrap colspan="3"> -->
<!-- 					<input name="tag" id="tag" type="text" size="20" maxlength="20" title="TAG 입력">  -->
<!-- 			  	</td> -->
<!-- 			</tr> -->
			<tr>
				<th width="20%" height="23" nowrap class="th_top"><span class="star">*</span>1차사업소</th>
				<td width="80%" nowrap colspan="3" class="td_top">
					<select id="fst_bizplc_cd" name="fst_bizplc_cd" class="select_st2 ml27"  onchange="setScdBizplcList(this.value);">
						<option value="" >선택하세요</option>
							<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
								<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.fst_bizplc_cd}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
							</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>2차사업소</th>
				<td width="80%" nowrap colspan="3">
					<select id="scd_bizplc_cd" name="scd_bizplc_cd" class="select_st2 ml27" onchange="setTracksList(this.value);">
						<option value="" > ← 선택하세요</option>
					</select> 
				</td>
			</tr>
			<tr>
				<th width="20%"  height="23" nowrap><span class="star">*</span>선로명</th>
				<td width="80%" nowrap colspan="3">
					<select id="fnct_lc_no" name="fnct_lc_no" class="select_st2 ml27" onchange="setTowerList(this.value);">
						<option value="" > ← 선택하세요</option>
					</select> 
				</td> 
			</tr>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>지지물명</th>
				<td width="80%" nowrap colspan="3" style="padding-left: 1px;">
<!-- 				id는 자바스크립트에서 사용, name은 자바에서 사용 -->
					<select id="eqp_no" name="eqp_no" class="select_st2 ml27">
						<option value="" > ← 선택하세요</option>
					</select> 
				</td>
			</tr>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>작업구분</th>
				<td width="80%" nowrap colspan="3">
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
				<td width="80%" nowrap colspan="3" style="padding-left: 1px;">
					<input id="remarks" name="remarks" type="text" size="50" maxlength="50" title="비고 입력">
				</td>
			</tr>
		</table>
		<div style="padding: 10px 0 0 10px">
			<input type="button" value="<spring:message code="button.create" />" title="등록" onclick="goSave(); return false;" class="btn_gray"/>
			<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray" />
		</div>
</div>
<!-- // contents -->
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

