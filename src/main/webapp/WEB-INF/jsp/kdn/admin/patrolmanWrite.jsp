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
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<%-- <script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/sym/cal/EgovCalPopup.js' />" ></script> --%>
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<%-- <script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script> --%>
<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: 1, d2: 1 });
		
		/* $(function () {
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
		});
		 */
		$("th").last().addClass("th_bot");  
		$("td").last().addClass("td_bot"); 
		
	});

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.saveFrm;
		form.action = "<c:url value='/kdn/admin/patrolman/List.do'/>";
		form.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 디바이스 삭제 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goDeviceDel(){
		form = document.saveFrm;
		form.action = "<c:url value='/kdn/admin/patrolman/DeviceDel.do'/>";
		form.submit();
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
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 중복계정  체크 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
 	function setUserId(userId){
		var data = getUserId(userId);
		if(data.isExist != null || data.isExist.length > 0) {
			alert("중복 계정이 존재합니다. 다른 계정을 선택해주세요");
			$("#user_id").focus();
			return $("#user_id").val();
		}
	}  
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 중복계정 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getUserId(userId){
		var requestUrl = "/ajax/exist/userid.json"; 
		var queryParam = "user_id=" + userId;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.isExist); 
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
	
	/* $(function() {
		$('#passwd').click(function(){ */
			function gggg() {
			alert($("#user_auth").val() );
			var str = "";
			<%-- 					<input id="user_pwd" name="user_pwd" type="password" size="20" value="${viewBox.user_pwd}"  maxlength="20" title="제목입력"> --%>
		//  			<input type="button" value="비밀번호 확인" />" title="수정" onclick="goSave(); return false;" class="button" />
			str += $("#input_password").val();
			
			if($("#user_auth").val() == "SYSTEM") {
				str += $("#input_password").val();
				$("#sub_passwd").append(str);
			}else {
				var str = "";
				str += "SYSTEM 권한만 패스워드를 볼수 있습니다.";
				$("#sub_passwd").append(str);
			}
		}
	/* 	});
	}); */
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 등록 저장
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSave(){
		//document.saveFrm.onsubmit(); // 에디터의 값을 얻기 위해서는 FORM 의 onsubmit()(document.form.onsubmit();) 함수를 호출하여 얻을 수 있습니다.
		if($.trim(document.saveFrm.fst_bizplc_cd.value) == '') {
			alert("1차 사업소를 선택해주세요");
			$("#fst_bizplc_cd").focus();
			return;
		} 
		if($.trim(document.saveFrm.scd_bizplc_cd.value) == '') {
			alert("2차 사업소를 선택해주세요");
			$("#scd_bizplc_cd").focus();
			return;
		}
		if($.trim(document.saveFrm.user_id.value) == '') {
			alert("순시자 아이디를 입력해주세요");
			$("#user_id").focus();
			return;
		}

		if($.trim(document.saveFrm.user_name.value) == '') {
			alert("순시자 이름을 입력해주세요");
			$("#user_name").focus();
			return;
		} 
		if ($.trim(document.saveFrm.user_pwd.value) == '') {
			alert("비밀번호를 입력해주세요.");
			$("#user_pwd").focus();
			return;
		}
		if ($.trim(document.saveFrm.user_pwd_confirm.value) == '') {
			alert("비밀번호 확인을 입력해주세요.");
			$("#user_pwd_confirm").focus();
			return;
		} 
		if($.trim(document.saveFrm.user_pwd.value) != $.trim(document.saveFrm.user_pwd_confirm.value)) {
			alert("비밀번호와 비밀번호확인이 맞지 않습니다.");
			$("#user_pwd").focus();
			return;
		}
		if ($.trim(document.saveFrm.first_tel.value) == '') {
			alert("지역번호를 입력해주세요.");
			$("#first_tel").focus();
			return;
		}
		if ($.trim(document.saveFrm.second_tel.value) == '') {
			alert("첫번째 집전화를 입력해주세요.");
			$("#second_tel").focus();
			return;
		}
		if ($.trim(document.saveFrm.third_tel.value) == '') {
			alert("두번째 집전화를 입력해주세요.");
			$("#third_tel").focus();
			return;
		}
		if ($.trim(document.saveFrm.first_phone.value) == '') {
			alert("첫번째 휴대전화를 입력해주세요.");
			$("#first_phone").focus();
			return;
		}
		if ($.trim(document.saveFrm.second_phone.value) == '') {
			alert("두번째 휴대전화를 입력해주세요.");
			$("#second_phone").focus();
			return;
		}
		if ($.trim(document.saveFrm.third_phone.value) == '') {
			alert("세번째 휴대전화를 입력해주세요.");
			$("#third_phone").focus();
			return;
		}
		
		if ($.trim(document.saveFrm.first_email.value) == '') {
			alert("이메일을 입력해주세요.");
			$("#first_email").focus();
			return;
		}
		if ($.trim(document.saveFrm.second_email.value) == '') {
			alert("이메일 사이트를 입력해주세요");
			$("#second_email").focus();
			return;
		}

		var keyVal = $("#user_id").val();	   
		if(keyVal != null && keyVal != "" && keyVal != "undefined" ){ 
			//등록
			if(confirm("등록하시겠습니까?")){
				document.saveFrm.action = "<c:url value='/kdn/admin/patrolman/Insert.do'/>";
				document.saveFrm.submit();
			}
		}
	}

</script>

<title>순시자 등록</title>
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

<form  id="saveFrm" name="saveFrm" method="post" action="<c:out value='/kdn/admin/patrolman/Insert.do'/>">
	
	<!-- 구성관리 contents -->
	<div id="contents">
		<!-- top -->
				<div class="top">
					<h3>순시자 등록</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 순시자 관리 &gt; <strong class="location">순시자 등록</strong>
					</p>
				</div>
		<!-- // top -->
		<table width="700px" border="0" cellpadding="0" cellspacing="0"	summary="">
			<tr>
				<th width="20%" height="23" class="th_top" nowrap><span class="star">*</span>1차사업소</th>
				<td width="80%" nowrap colspan="3" class="td_top">
					<select id="fst_bizplc_cd" name="fst_bizplc_cd"   onchange="setScdBizplcList(this.value);">
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
					<select id="scd_bizplc_cd" name="scd_bizplc_cd" onchange="setTracksList(this.value);">
						<option value="" > ↑ 선택하세요</option>
					</select> 
				</td>
			</tr>
			<tr>
			   <th width="20%" height="23" nowrap ><span class="star">*</span>순시자 ID</th>
			   <td width="80%" nowrap colspan="7">
					<input id="user_id" name="user_id" type="text" size="20" maxlength="20" title="순시자ID"> &nbsp; &nbsp; &nbsp; 
				</td>
			</tr>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>순시자 이름</th>
				<td width="80%" nowrap colspan="7">
					<input id="user_name" name="user_name" type="text" size="20" maxlength="20" title="순시자이름">
				</td>
			</tr>
			<tr>
			   <th width="20%" height="23" nowrap ><span class="star">*</span>순시자 패스워드</th>
			   <td width="80%" nowrap colspan="7">
					<input id="user_pwd" name="user_pwd" type="password" size="20" maxlength="20" title="순시자 패스워드">
				</td>
			</tr>
			<tr>
			   <th width="20%" height="23" nowrap ><span class="star">*</span>순시자 패스워드 확인</th>
			   <td width="80%" nowrap colspan="7">
					<input id="user_pwd_confirm" name="user_pwd_confirm" type="password" size="20" maxlength="20" title="순시자 패스워드 확인">
				</td>
			</tr>
			<tr>	

				<th width="20%" height="23" nowrap><span class="star">*</span>집전화</th>
				<td width="80%" align="center" nowrap colspan="7">
						<input id="first_tel" name="first_tel" type="text" size="8" maxlength="3" title="지역번호 입력">
	&nbsp;-
						<input id="second_tel" name="second_tel" type="text" size="8"  maxlength="4" title="첫번째 전화번호 입력">
	&nbsp;-
				  		<input id="third_tel" name="third_tel" type="text" size="8" maxlength="4" title="두번째 전화번호 입력">
				</td>
			  </tr>
			  <tr>
			<tr>	

				<th width="20%" height="23" nowrap><span class="star">*</span>휴대전화번호</th>
				<td width="80%" align="center" nowrap colspan="7">
						<input id="first_phone" name="first_phone" type="text" size="8" maxlength="3" title="통신사 입력">
	&nbsp;-
						<input id="second_phone" name="second_phone" type="text" size="8"  maxlength="4" title="첫번째 핸드폰 입력">
	&nbsp;-
				  		<input id="third_phone" name="third_phone" type="text" size="8" maxlength="4" title="두번째 핸드폰 입력">
				</td>
			  </tr>
			  <tr>
			  	<th width="20%" height="23" nowrap><span class="star">*</span>이메일</th>
				<td width="80%" nowrap colspan="7">
			  		<input id="first_email" name="first_email" type="text" size="20" maxlength="20" title="이메일 주소 입력">
&nbsp;@
			  		<input id="second_email" name="second_email" type="text" size="20" maxlength="20" title="이메일 사이트 입력">

			</td>
			</tr>
			<c:if test="${sessionInfo.user_auth eq 'SYSTEM' }">	
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>권한설정</th>
				<td width="80%" nowrap colspan="7">
			  		<select name="user_auth" id="user_auth" title="검색조건선택">
			  			<option value="" >선택하세요</option>
			  			<option value="KEPCO">한전관리자</option>
						<option value="KPS">순시자</option>
						<option value="SYSTEM">시스템관리자</option>
						<option value="KPSADMIN">사업소관리자</option>
			  		</select>
			  	</td>
			</tr>
			<tr>
				<th width="20%" height="23" nowrap><span class="star">*</span>사용여부</th>
				<td width="80%" nowrap colspan="7">
					<select name="use_yn" id="use_yn" title="검색조건선택">
						<option value="" >선택하세요</option>
						<option value="Y">사용</option>
						<option value="N">미사용</option>
					</select> 
				</td>
				</c:if>
		</table>
		
		<div style="padding: 10px 0 0 10px">
			<input type="button" value="등록" title="등록" onclick="goSave();" class="btn_gray">
      		<input type="button" value="<spring:message code="button.list" />" title="목록" onclick="goList(); return false;" class="btn_gray" />
		</div>
	  </div>
	<!-- // 구성관리 contents -->
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

