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
<%-- 
<link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/sym/cal/EgovCalPopup.js' />" ></script> 
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>
--%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<script type="text/javascript">
	$(document).ready(function () {
		//$('#topmenu').topmenu({ d1: 9, d2: 2 });
		$('#topmenu').topmenu({ d1: "110000", d2: "110001" });  
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 영문만 입력 가능
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		$("#auth_id").keyup(function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^a-z]/gi,''));    
			}
		});	
		
		<c:if test="${not empty viewBox.auth_idx}">
		if(document.saveFrm.auth_id.value != ""){ 
			strByteView("auth_id","subText1Byte");			
		}
		if(document.saveFrm.auth_nm.value != ""){ 
			strByteView("auth_nm","subText2Byte");			
		}
		if(document.saveFrm.auth_account.value != ""){ 
			strByteView("auth_account","subText3Byte");			
		}
		</c:if>
		
		$("th").last().addClass("th_bot");
		$("td").last().addClass("td_bot");
		
	});

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		form.action = "<c:url value='/kdn/admin/authList.do'/>";
		form.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		if ($.trim(document.saveFrm.auth_id.value) == '') {
			alert("권한ID를 입력해 주십시오.");
			$("#auth_id").focus();
			return;
		}
		<c:if test="${empty viewBox.auth_idx}">
		if ($.trim(document.saveFrm.auth_id.value) != '') {
			var data = authIdOverlapCount($("#auth_id").val());
			if(parseInt(data.overlapCnt) > 0){
				alert("중복된 권한ID 입니다. 다시 입력해 주십시오.");
				$("#auth_id").val("");
				$("#auth_id").focus();
				return;
			}
		}
		</c:if>
		if ($.trim(document.saveFrm.auth_nm.value) == '') {
			alert("권한명을 입력해 주십시오.");
			$("#auth_nm").focus();
			return;
		}
		if ($.trim(document.saveFrm.auth_account.value) == '') {
			alert("설명을 입력해 주십시오.");
			$("#auth_account").focus();
			return;
		}

		var keyVal = "<c:out value='${viewBox.auth_idx}'/>";	      
		if(keyVal != null && keyVal != "" && keyVal != "undefined"){ 
			//수정
			if(confirm("수정하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/admin/authUpdate.do'/>";
				document.saveFrm.submit();
			}
		}else{
			//등록
			if(confirm("등록하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/admin/authSave.do'/>";
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
	* 권한ID 중복 체크 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function authIdOverlapCount(authId){ 
		var requestUrl = "/ajax/authIdOverlapCount.json"; 
		var queryParam = "auth_id=" + authId;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.overlapCnt); 
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

<title>메뉴 등록</title>
<style type="text/css">
h1 {font-size:12px;}
caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}

/* 에디터 Style  */
.noStyle {background:ButtonFace; BORDER-TOP:0px; BORDER-bottom:0px; BORDER-left:0px; BORDER-right:0px;}
.noStyle th{background:ButtonFace; padding-left:0px;padding-right:0px}
.noStyle td{background:ButtonFace; padding-left:0px;padding-right:0px}
</style>

</head>
<body onLoad="document.saveFrm.auth_id.focus();">
	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>

	<!-- wrap -->
	<div id="wrap"> 
		<!-- Left Area --> 
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		 
		 <!-- Top Area -->
		<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
		<!-- // Top Area --> 
		
		<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/kdn/admin/authList.do'/>">
			<input id="auth_idx" name="auth_idx" type="hidden"	value="<c:out value='${viewBox.auth_idx}'/>" />
			<!-- 검색 파라메터  -->
			<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
			<input type="hidden" name="searchCnd"  value="<c:out value='${box.searchCnd}'/>"/>
			<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
			
			<!-- 구성관리 contents -->
			<div id="contents" >
				<!-- top -->
				<div class="top">
					<h3>권한 <c:if test="${not empty viewBox.auth_idx}">수정</c:if><c:if test="${empty viewBox.auth_idx}">등록</c:if></h3>
					<p class="navi">
						<strong>Home</strong> &gt; 권한 관리 &gt; <strong class="location">권한 <c:if test="${not empty viewBox.auth_idx}">수정</c:if><c:if test="${empty viewBox.auth_idx}">등록</c:if></strong>
					</p>
				</div>
				<!-- // top -->
				<table width="700px" border="0" cellpadding="0" cellspacing="0"  summary="" >
					<tr>
						<th class="th_top"><span class="star">*</span>권한ID</th>
						<td class="td_top">
							<c:choose>
								<c:when test="${not empty viewBox.auth_idx}">
									<input type="hidden" id="auth_id" name="auth_id"  value="<c:out value='${viewBox.auth_id}'/>"/>
									<font class="pl10" style="font-weight:bold; "><c:out value="${viewBox.auth_id}" /></font>
								</c:when>
								<c:otherwise>
									<input id="auth_id" name="auth_id" type="text" size="35" value="${viewBox.auth_id}"  onkeyup="UIControls.RestrainString.excute(this, 30, 'subText1Byte');" title="권한ID">
									(<span id="subText1Byte" style="color:red;">0</span> <font face="맑은 고딕">byte/총 30byte</font>)
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>권한명</th>
						<td width="80%" nowrap colspan="3" >
							<input id="auth_nm" name="auth_nm" type="text" size="60" value="${viewBox.auth_nm}"  onkeyup="UIControls.RestrainString.excute(this, 50, 'subText2Byte');" title="권한명">
							(<span id="subText2Byte" style="color:red;">0</span> <font face="맑은 고딕">byte/총 50byte</font>)
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>설명</th>
						<td width="80%" nowrap colspan="3" >
							<textarea id="auth_account" name="auth_account" rows="5" cols="80" title="PUSH 내용" onkeyup="UIControls.RestrainString.excute(this, 100, 'subText3Byte');" ><c:out value="${viewBox.auth_account}" escapeXml="true"/></textarea>
							<div style="position: relative;left: 475px;">(<span id="subText3Byte" style="color:red;">0</span> <font face="맑은 고딕">byte/총 100byte</font>)</div>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>사용여부</th>
						<td width="80%" nowrap colspan="3" >
							<select id="use_yn" name="use_yn">
								<option value="Y" <c:if test="${viewBox.use_yn == 'Y'}">selected="selected"</c:if> > 사용</option>
								<option value="N" <c:if test="${viewBox.use_yn == 'N'}">selected="selected"</c:if> >미사용</option>
							</select>
						</td>
					</tr>
					<c:if test="${not empty viewBox.auth_idx}">  
						<tr>
							<th><span class="star">*</span>등록자</th>
							<td style="padding-left:10px;">
								${viewBox.reg_id} (${viewBox.reg_date_string})
							</td>
						</tr>
						<c:if test="${not empty viewBox.upd_id}">
							<tr>
								<th><span class="star">*</span>수정자</th>
								<td style="padding-left:10px;">
									${viewBox.upd_id} (${viewBox.upd_date_string})
								</td>
							</tr>
						</c:if>
					</c:if>
				</table>
				
				<div style="padding: 10px 0 0 10px">
					<c:if test="${not empty viewBox.auth_idx}">
						<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="javascript:goSave(); return false;" class="btn_gray" />
			  		</c:if>
			  		<c:if test="${empty viewBox.auth_idx}">   
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

