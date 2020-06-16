<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>  

<html lang="ko" xmlns:v="urn:schemas-microsoft-com:vml">   
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<title>DARTS TV</title>  
		<link rel="stylesheet" href="<c:url value='/css/mobileWeb/mobile.css' />">
		<script type="text/javascript">
		$(document).ready(function(){		
			
		});

		 function download() {
				//document.frm.auth_idx.value = key;
				document.frm.action = "<c:url value='/kdnmobile/schedule/excel/download.do'/>";
				document.frm.submit(); 	
		</script>
	</head>
	<body>
		<form id="frm" name="frm">
			<input type="button" value="다운로드" title="다운로드" onclick="download();" class="button"> 
		</form>
	</body>
</html>