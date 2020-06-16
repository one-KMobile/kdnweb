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
<link href="<c:url value='/css/kdn/com/cmm/reset.css' />" rel="stylesheet" type="text/css"> 
<link href="<c:url value='/css/kdn/com/cmm/popup.css' />" rel="stylesheet" type="text/css">   
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<script type="text/javascript">

	 $(document).ready(function () { 
		 $('#topmenu').topmenu({ d1: "112000", d2: "112001" });  
		 
		 /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 순시 결과 엑셀 파일 전송
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		 $("#excel_at").unbind("click").bind("click", function(){
     		if ($.trim(document.saveFrm.excelfile.value) == '') {
				alert("파일을 등록해주세요.");
				$("#excelfile").focus();
				return;
			} 
				
			if(fileTypeChk("excelfile")){
				return false;
			}	
			  
			document.saveFrm.action = "<c:url value='/insertExcelData.do'/>";
			document.saveFrm.submit();
		 });
		
		 /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 파일 확장자 체크
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		 function fileTypeChk(fileId){
			var thumbext = document.getElementById(fileId).value; //파일을 추가한 input 박스의 값
			thumbext = thumbext.slice(thumbext.indexOf(".") + 1).toLowerCase(); //파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.
			
			if(thumbext != "xls" && thumbext != "xlsx" ){ //확장자를 확인합니다.
				alert(' 파일은(xls, xlsx)만 등록 가능합니다.');
				return true;
			}
			return false;
		 }
		 
	 }); 
	
</script>
<title>게시판 목록조회</title>

</head>
<body>
	<!-- wrap -->
	<div id="wrap">
    	
		<!-- Left Area --> 
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		 
		 <!-- Top Area -->
		<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
		<!-- // Top Area --> 
		
		<!-- pop_wrap -->
		<div id="pop_wrap" style="margin-top: -43px;margin-left: 200px;"> 
			<!-- head -->
			<div id="head">
				<h1 style="background: #0A6ECD;font-family: 맑은 고딕;font-size: 20PX;color: white;font-weight: bold;padding: 5PX 0PX 8PX 20PX;">KDN MOBILE 순시결과 엑셀 업로드</h1>     
			</div>
			<!-- // head -->
			<!-- mid -->
			<div id="mid" style="height:150px;">
				<h2 style="font-size: 15px;color: #46649B;font-weight: bold;padding: 10px;font-family: 맑은 고딕;}">순시결과 엑셀 업로드</h2>
				<form name="saveFrm" method="post" action="<c:out value='/insertExcelData.do' />"  enctype="multipart/form-data"> 
					<input type="hidden" name="upld_optn" value="I" />
					
					<fieldset>
						<legend>사용자 정보 입력 폼</legend>
						<table summary="엑셀 업로드" class="table_input">
							<tr>
								<th><span>*</span> <label for="responsibility">엑셀 업로드</label></th>
								<td colspan="3"> 
									<input type="file" id="excelfile" name="excelfile" style="width:193px;" />  
									<input type="button" value="전송" id="excel_at" class="button" style="margin-right: 10px;float:right;cursor:pointer;"/>
								</td>							
							</tr>
						</table>
					</fieldset>
				</form>
			</div>
			<!-- // mid -->  
		</div>   
		<!-- // pop_wrap -->
	
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


