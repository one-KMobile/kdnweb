<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	html {width:100%; height:100%;}
	body {width:100%; height:100%; margin:0; padding:0;}
	input[type=checkbox], input[type=radio] {margin:0; padding:0;}
	input[type=checkbox], input[type=radio] {width:13px; height:13px;}
	input, select {margin:0; padding:0; color:#666;}
	fieldset {margin:0; padding:0; border:0px none;}
	fieldset * {_vertical-align:top;}
	legend {margin:0; padding:0; visibility:hidden; position:absolute; top:-99999px; left:-99999px; margin-top:-99999px; margin-left:-99999px;}
	form {margin: 0; padding: 0; line-height:0; font-size:0;}
	form * {vertical-align:middle; _vertical-align:top;}
	img {border:0;}
	#loginLayer {position:relative; width:100%; height:100%;}
	.adLogo {position: absolute; top: 220px; left: 50%; margin-left:-200px;}
	#view {width:410px; position: absolute; top: 280px; left: 50%; margin-left:-200px;  z-index:10000;}
	#view .titlebar {font-size:0;}
	#view .titlebar * {vertical-align:top;}
	#view .contbox {padding:0 5px; background:url('images/kdn/login/bg_admin_login.gif') repeat-y left top;}
	#view .contbox * {vertical-align:middle !important; _vertical-align:top;}
	#view .contbox .login {padding:20px;  background:#fff;}
	#view .contbox table {width:100%; border-collapse:collapse; font-size:11px; border-top:#ccc solid 1px;}
	#view .contbox table th {border-bottom:#d9d9d9 solid 1px; padding:10px 0; background:#ededed; _vertical-align:middle;}
	#view .contbox table td {border-bottom:#d9d9d9 dotted 1px; padding:7px 0 7px 10px; _padding:6px 0 6px 10px;}
	#view .contbox table * {vertical-align:middle;}
	#view .contbox table label {margin-bottom:2px;}
	.login .mL5 {margin-left:5px;}
</style>
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" > 
$(document).ready(function() {
});
  
function goLogin(){
	if(document.loginFrm.idcheck.checked == true){
		saveLogin(document.loginFrm.user_id.value); 
	}else{ 
		saveLogin(""); 
	}
	
	if(document.loginFrm.user_id.value == "") {	
		alert("아이디 항목에 4자이상 입력하여 주시기 바랍니다.");
		$("#user_id").focus();
		return;
	}
	if(document.loginFrm.user_pwd.value == "") { 
		alert("패스워드를 입력하여 주시기 바랍니다.");
		$("#user_pwd").focus(); 
		return;  
	}
	
	if ($.trim(document.loginFrm.user_id.value) == 'kps') {
		document.loginFrm.action = "<c:url value='/test/actionLogin.do'/>";
		document.loginFrm.submit();
	}else{
		document.loginFrm.action = "<c:url value='/actionLogin.do'/>";
		document.loginFrm.submit();	
	}
} 

function confirmSave(checkbox){
	 var isRemember;
	
	 if(checkbox.checked){
	 	isRemember = confirm("이 PC에 로그인 정보를 저장하시겠습니까? PC방등의 공공장소에서는 개인정보가 유출될 수 있으니 주의해주십시오.");
	  	if(!isRemember) checkbox.checked = false;
	 }
}

function setsave(name, value, expiredays){
	 var today = new Date();
	 today.setDate( today.getDate() + expiredays );
	 document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";"
}

function saveLogin(id){
	 if(id != ""){ 
	  // userid 쿠키에 id 값을 7일간 저장
	  setsave("adminId", id, 7);
	 }else{
	  // userid 쿠키 삭제
	  setsave("adminId", id, -1);
	 }
}

function getLogin(){
	// userid 쿠키에서 id 값을 가져온다.
	var cook = document.cookie + ";";
	var idx = cook.indexOf("adminId", 0);  
	var val = "";

 	if(idx != -1){
		cook = cook.substring(idx, cook.length);
		begin = cook.indexOf("=", 0) + 1;
		end = cook.indexOf(";", begin); 
		val = unescape( cook.substring(begin, end) );
 	}

	// 가져온 쿠키값이 있으면
	if(val != ""){
		document.loginFrm.user_id.value = val;
		document.loginFrm.idcheck.checked = true;
	}
}

function entkey(){
    if(event.keyCode == 13){
    	goLogin(); // 로그인 메소드 실행
    }
 }
</script>
</head>
<body onLoad="getLogin()">
<div id="loginLayer">
	<!-- <div class="adLogo"><img src="images/kdn/login/logo.gif" alt="관리자" /></div> -->
	<div id="view">
		<div class="titlebar">
			<img src="<c:url value='images/kdn/login/t_admin_login.gif' />" alt="관리자로그인" />
		</div>
		<form  name="loginFrm" id="loginFrm" action="<c:url value='/actionLogin.do'/>" method="post" >    
			<fieldset>
				<legend>관리자 로그인</legend>
				<div class="contbox">
					<div class="login">
						<table> 
							<colgroup>
								<col width="30%" />
								<col width="*" />
							</colgroup>
							<tbody>  
								<tr>
									<th><img src="<c:url value='images/kdn/login/txt_id.gif' />" alt="아이디" /></th>    
									<td><input type="text" name="user_id" id="user_id" style="width:124px;ime-mode:disabled" tabindex="1" /> <input type="checkbox" name="idcheck" id="idcheck" class="mL5" style="_border:0 none;ime-mode:disabled;" onclick="confirmSave(this)" /> <label for="idcheck"><img src="<c:url value='images/kdn/login/txt_save.gif' />" alt="아이디저장" /></label></td>
								</tr>
								<tr>  
									<th><img src="<c:url value='images/kdn/login/txt_pw.gif' />" alt="패스워드" /></th>
									<td><input type="password" name="user_pwd" id="user_pwd" style="width:124px;" tabindex="2" onkeypress="entkey();" />
									<a href="#" class="mL5" tabindex="3">    
										<img src="<c:url value='images/kdn/login/btn_login.gif' />" alt="로그인" onclick="goLogin();"/>  
									</a>
									</td>								
								</tr>
							</tbody> 
						</table>
					</div>
				</div>
			</fieldset>
		</form>
		<div class="foot">
			<img src="<c:url value='images/kdn/login/img_admin_login.gif' />" alt="※ 정보가 다른 경우 관리자에게 문의하세요." />
		</div>
	</div>
</div>
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>