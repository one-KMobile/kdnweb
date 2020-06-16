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
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>
<!-- 달력 관련 스크립트  -->
<link href="<c:url value='/css/kdn/com/cmm/jquery-ui.css' />" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.ui.datepicker-ko.js' />"></script> 
<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: '104000', d2: '104001' }); 
		
		$(function () {
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
			
			$("th").last().addClass("th_bot");
			$("td").last().addClass("td_bot");
			
		});
		
		<c:if test="${not empty viewBox.board_idx}">
		if(document.saveFrm.push_cont.value != ""){ 
			strByteView("push_cont","subText1Byte");			
		}
		if(document.saveFrm.board_cont.value != ""){   
			strByteView("board_cont","subText2Byte");			
		}
		</c:if>
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var searchCnd1 = "<c:url value='${box.searchCnd1}' />";
		var searchCnd2 = "<c:url value='${box.searchCnd2}' />";
		
		var fst_bizplc_cd = "<c:url value='${viewBox.fst_bizplc_cd}' />";
		var scd_bizplc_cd = "<c:url value='${viewBox.scd_bizplc_cd}' />";
		
		$("#searchCnd1 > option[value = " + fst_bizplc_cd + "]").attr("selected", "true")
		$("#searchCnd2 > option[value = " + scd_bizplc_cd + "]").attr("selected", "true")
	});

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		//form.action = "<c:url value='/kdn/admin/noticeList.do'/>";
		window.location.href = "<c:url value='/kdn/admin/noticeList.do'/>";
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		//document.saveFrm.onsubmit(); // 에디터의 값을 얻기 위해서는 FORM 의 onsubmit()(document.form.onsubmit();) 함수를 호출하여 얻을 수 있습니다.
		if ($.trim(document.saveFrm.category_code.value) == '') {
			alert("구분을 선택해 주십시오.");
			$("#category_code").focus();
			return;
		}
		if ($.trim(document.saveFrm.send_name.value) == '') {
			alert("발송기관을 입력해 주십시오.");
			$("#send_name").focus();
			return;
		}
		if ($.trim(document.saveFrm.board_title.value) == '') {
			alert("제목을 입력해 주십시오.");
			$("#board_title").focus();
			return;
		}
		if ($.trim(document.saveFrm.board_cont.value) == '') {
			alert("내용을 입력해 주십시오.");
			$("#board_cont").focus();
			return;
		}
		if ($.trim(document.saveFrm.searchCnd1.value) != '') {
			if ($.trim(document.saveFrm.searchCnd2.value) == '') {
				alert("2차 사업소를 선택해 주십시요.");
				$("#searchCnd2").focus();
				return;
			}
		}		
		var keyVal = $("#board_idx").val();	   
		if(keyVal != null && keyVal != "" && keyVal != "undefined"){ 
			//수정
			if(confirm("수정하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/admin/noticeUpdate.do'/>";
				document.saveFrm.submit();
			}
		}else{
			//등록
			if(confirm("등록하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/admin/noticeSave.do'/>";
				document.saveFrm.submit();;
			}
		}
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* TextArea Byte수 제한하기 자바 스크립트
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	var UIControls = {}; 
	UIControls.RestrainString = {
		excute:function(sender, limitByte, viewWrapId){
			var stringValue = sender.value;
			var _byteSize = 0;
			var _charByteSize = 0;
			for(var i=0; i<stringValue.length; i++){  
				_charByteSize = this.getCharByteSize(stringValue.charAt(i));
				_byteSize += _charByteSize;
				if(_byteSize > limitByte){
					alert(limitByte + "byte 까지 입력하실 수 있습니다.");
				    _byteSize -= _charByteSize;
				    sender.value = stringValue.substring(0, i);
				    break;
				}
			}
			if(viewWrapId) $("#" + viewWrapId).text(_byteSize);          
		},
		getCharByteSize: function(char){
			if(!char) return 0;
			
			var charCode = char.charCodeAt(0);
			
			if(charCode <= 0x00007F) return 1;
			else return 2;
		}
	};	
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 달력 이미지 클릭시 달력 표시
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function calljscal(id) {
		var id = '#' + id
		$(id).datepicker("show");
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 문자열 길이를 바이트값으로 나타내기
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function strByteView(strId,viewId){  
		var stringValue = $("#"+strId).val();
		var _charByteSize = 0;
		for(var i=0; i<stringValue.length; i++){  
			var str = stringValue.charAt(i);
			if(escape(str).length > 4){
				_charByteSize	+= 2;
			}else{
				_charByteSize++;
			}
		}
		$("#"+viewId).text(_charByteSize);
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 2차사업소 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setScdBizplcList(fstBizplcCode){
		var data = getScdBizplcList(fstBizplcCode);
		var addScdBizplcList = "";
		if(data != null && data.scdBizplcList != null && data.scdBizplcList.length > 0){
			addScdBizplcList +='<option value="" >선택하세요</option>';
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

<title>공지 등록</title>
<style type="text/css">
h1 {font-size:12px;}
caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}
</style>
</head>
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
				<h3>공지 사항 <c:if test="${not empty viewBox.board_idx}">수정</c:if><c:if test="${empty viewBox.board_idx}">등록</c:if></h3>
				<p class="navi">
					<strong>Home</strong> &gt; 공지 사항 &gt; <strong class="location">공지 <c:if test="${not empty viewBox.board_idx}">수정</c:if><c:if test="${empty viewBox.board_idx}">등록</c:if></strong>
				</p>
			</div>
			<!-- // top -->
			<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/kdn/admin/noticeSave.do'/>">
			<input id="board_idx" name="board_idx" type="hidden"	value="<c:out value='${viewBox.board_idx}'/>" />
			<!-- 검색 파라메터  -->
			<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
			<input type="hidden" name="s_category_code"  value="<c:out value='${box.s_category_code}'/>"/>
			<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
	
			<table width="700px" border="0" cellpadding="0" cellspacing="0"	summary="" >
				<tr>
					<th class="th_top"><span class="star">*</span>구분</th>
					<td class="td_top">
					<select id="category_code" name="category_code">
							<option value="" >선택하세요</option>
							<c:forEach var="codeList" items="${codeList}" varStatus="status">
								<option value="${codeList.code_value}" <c:if test="${codeList.code_value eq viewBox.category_code}">selected="selected"</c:if> >${codeList.code_name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>발송기관</th>
					<td><input id="send_name" name="send_name" type="text" size="60" value="${viewBox.send_name}"  maxlength="60" title="발송기관"></td>
				</tr>
				<tr>
					<th><span class="star">*</span>1차사업소</th>
					<td>
						<select id="searchCnd1" name="searchCnd1" class="select_st2" onchange="setScdBizplcList(this.value);">
							<option value="" >선택안함</option>
									<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
										<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.searchCnd1}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
									</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>2차사업소</th>
					<td>
						<select id="searchCnd2" name="searchCnd2" class="select_st2" >
							<option value="" >△선택하세요</option>
							<c:forEach var="scdBizplcList" items="${scdBizplcList}" varStatus="status">
								<option value="${scdBizplcList.CODE_VALUE}" <c:if test="${scdBizplcList.CODE_VALUE eq box.searchCnd2}">selected="selected"</c:if> >${scdBizplcList.CODE_NAME}</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>제목</th>
					<td>
						<input id="board_title" name="board_title" type="text" size="60" value="${viewBox.board_title}"  maxlength="60" title="제목입력">
						<input type="checkbox" name="top_yn" id="top_yn" style="margin-top:2px;" value="Y" <c:if test="${viewBox.top_yn eq 'Y'}">checked="checked"</c:if> > <font style="color:black;">공지 여부</font>
					</td>
				</tr>
				<tr>
					<th>PUSH 내용</th>
					<td>
						<textarea id="push_cont" name="push_cont" rows="5" cols="80" title="PUSH 내용" onkeyup="UIControls.RestrainString.excute(this, 50, 'subText1Byte');" ><c:out value="${viewBox.push_cont}" escapeXml="true"/></textarea>
						<div>
							<input type="checkbox" name="push_yn" id="push_yn" style="margin-top:2px;" value="Y" <c:if test="${viewBox.push_yn eq 'Y'}">checked="checked"</c:if> > <font style="color:black;">공지 등록시 PUSH를 전송합니다.</font>
							<span style="float:right;padding-right:33%;">  
								(<span id="subText1Byte" style="color:red;">0</span> <font face="맑은 고딕">byte/총 50byte</font>)
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>내용</th>
					<td>
						<textarea id="board_cont" name="board_cont" class="textarea" rows="10" cols="100" onkeyup="UIControls.RestrainString.excute(this, 2000, 'subText2Byte');"><c:out value="${viewBox.board_cont}" escapeXml="false" /></textarea>
						<div>
							<span style="float: right; padding-right: 33%;">
							(<span id="subText2Byte" style="color: red;">0</span><font	face="맑은 고딕">byte/총 2000byte</font>)
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span>사용여부</th>
					<td>
						<select id="use_yn" name="use_yn">
							<option value="Y" <c:if test="${viewBox.use_yn == 'Y'}">selected="selected"</c:if> > 사용</option>
							<option value="N" <c:if test="${viewBox.use_yn == 'N'}">selected="selected"</c:if> >미사용</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>
						<span style="padding-left:5px;font-size:12px;font-weight:bold; "><c:out value="${sysdate}"></c:out></span>
					</td>
				</tr>
			</table>
			<div style="padding: 10px 0 0 10px">
		  		<c:if test="${not empty viewBox.board_idx}">
					<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="javascript:goSave(); return false;" class="btn_gray" />
		  		</c:if>
		  		<c:if test="${empty viewBox.board_idx}">   
		  			<input type="button" value="<spring:message code="button.create" />" title="등록" onclick="javascript:goSave(); return false;" class="btn_gray"/>
		  		</c:if>
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

