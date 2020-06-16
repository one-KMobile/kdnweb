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
<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: '111000', d2: '111001' });  
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 숫자 . 만 입력 가능
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		$("#version").keyup(function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^0-9.]/gi,''));
			}
		});	
		
		<c:if test="${not empty viewBox.remarks}">
		if(document.saveFrm.remarks.value != ""){ 
			strByteView("remarks","subText1Byte");			
		}
		</c:if>
		
	});

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		//form.action = "<c:url value='/kdn/admin/noticeList.do'/>";
		window.location.href = "<c:url value='/kdn/version/versionList.do'/>";
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		//document.saveFrm.onsubmit(); // 에디터의 값을 얻기 위해서는 FORM 의 onsubmit()(document.form.onsubmit();) 함수를 호출하여 얻을 수 있습니다.
		
		if ($.trim(document.saveFrm.use_yn.value) == '') {
			alert("사용여부를 선택해 주십시오.");
			$("#use_yn").focus();
			return;
		}
		
		if ($.trim(document.saveFrm.version.value) == '') {
			alert("버젼을 입력해 주십시오.");
			$("#board_title").focus();
			return;
		}
		
		var keyVal = $("#version_idx").val();	   
		if(keyVal != null && keyVal != "" && keyVal != "undefined"){ 
			//수정
			if(confirm("수정하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/version/versionUpdate.do'/>";
				document.saveFrm.submit();
			}
		}else{
			//등록
			if(confirm("등록하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/version/versionSave.do'/>";
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
	
</script>

<title>모바일 등록</title>
<style type="text/css">
h1 {font-size:12px;}
caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}
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
			<!-- 구성관리 contents -->
		<div id="contents" >
			<!-- top -->
			<div class="top">
				<h3>모바일 <c:if test="${not empty viewBox.version_idx}">수정</c:if><c:if test="${empty viewBox.version_idx}">등록</c:if> </h3>
				<p class="navi">
					<strong>Home</strong> &gt; 모바일 관리 &gt; <strong class="location">모바일 <c:if test="${not empty viewBox.version_idx}">수정</c:if><c:if test="${empty viewBox.version_idx}">등록</c:if></strong>
				</p>
			</div>
			<!-- // top -->
			
			<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/kdn/admin/noticeSave.do'/>">
				<input id="version_idx" name="version_idx" type="hidden"	value="<c:out value='${viewBox.version_idx}'/>" />
				<!-- 검색 파라메터  -->
				<input type="hidden" name="pageIndex"  value="<c:out value='${box.pageIndex}'/>"/>
				<input type="hidden" name="s_category_code"  value="<c:out value='${box.s_category_code}'/>"/>
				<input type="hidden" name="searchWrd"  value="<c:out value='${box.searchWrd}'/>"/>
				<table width="700px" border="0" cellpadding="0" cellspacing="0"  >
					<tr>
						<th class="th_top"><span class="star">*</span>사용여부</th>
						<td class="td_top">
							<select id="use_yn" name="use_yn">
								<c:forEach var="codeList" items="${codeList}" varStatus="status">
									<option value="${codeList.code_value}" <c:if test="${codeList.code_value eq viewBox.use_yn}">selected="selected"</c:if> >${codeList.code_name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>버젼</th>
						<td>
							<input id="version" name="version" type="text" size="60" value="${viewBox.version}"  maxlength="60" title="제목입력">
						</td>
					</tr>
					
					<tr>
						<th><span class="star">*</span>URL</th>
						<td>
							<input id="url" name="url" type="text" size="60" value="${viewBox.url}"  maxlength="60" title="제목입력">
						</td>
					</tr>
					
					<tr>
						<th>
							비고란
						</th>
						<td>
							<textarea id="remarks" name="remarks" rows="5" cols="80" maxlength="400" title="제목입력" onkeyup="UIControls.RestrainString.excute(this, 200, 'subText1Byte');"/><c:out value="${viewBox.remarks}" escapeXml="true"/></textarea>
							<div><span style="float:right;padding-right:33%;">(<span id="subText1Byte" style="color:red;">0</span><font face="맑은 고딕">byte/총 200byte</font>)</span></div></td>
					</tr>
					<tr>
						<th>
							등록자
						</th>
						<td>
							&nbsp;&nbsp;<c:out value="${viewBox.reg_id}" />
						</td>
					</tr>
					<tr>
						<th>등록일</th>
						<td>
							&nbsp;&nbsp;<c:out value="${viewBox.reg_date}"/>
						</td>
					</tr>
					<tr>
						<th class="th_bot">수정일</th>
						<td class="th_bot">
							&nbsp;&nbsp;<c:out value="${viewBox.upd_date}" />
						</td>
					</tr>
				</table>
				<div style="padding: 10px 0 0 10px">
					<td>
			  		<c:if test="${not empty viewBox.version_idx}">
						<input type="button" value="<spring:message code="button.update" />" title="수정" onclick="javascript:goSave(); return false;" class="btn_gray" />
			  		</c:if>
			  		<c:if test="${empty viewBox.version_idx}">   
			  			<input type="button" value="<spring:message code="button.create" />" title="등록" onclick="javascript:goSave(); return false;" class="btn_gray"/>
			  		</c:if>
			      </td>
			      <td>
			      		<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray" />
			      </td>
				</div>
			</form>
		</div>
		<!-- // 구성관리 contents -->
		<!-- bottom Area -->
		<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
		<!-- // bottom Area -->
	</div>
    <!-- end wrap -->
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>