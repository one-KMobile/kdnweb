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
<html lang="ko">     
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
<link href="<c:url value='/css/kdn/sitemap/default.css' />" rel="stylesheet" type="text/css">  
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript">
	 var p_auth_id = Request("auth_id"); 
	
	 $(document).ready(function () {
		 
		 if(p_auth_id != null && p_auth_id != "" && p_auth_id != "undefinded"){
			 $("#authNm").text(p_auth_id);    	       
		 }
		 
		 setMenuGrantAuthList(p_auth_id); 
		 
		 $("input[id ^= 'authWrite_']").unbind("click").bind("click", function(){
			 var id = $(this).attr("id");
			 var menuId = id.substring(id.indexOf("_") + 1, id.length);
			 
			 var val = "";
			 if($("#" + id).is(":checked")){
				 val = "Y";
			 }else{
				 val = "N";
			 }
			 menuGrantAuthSave("write", menuId, val);
		 });
		 $("input[id ^= 'authRead_']").unbind("click").bind("click", function(){
			 var id = $(this).attr("id");
			 var menuId = id.substring(id.indexOf("_") + 1, id.length);
			 
			 var val = "";
			 if($("#" + id).is(":checked")){
				 val = "Y";
			 }else{
				 val = "N";
			 }
			 menuGrantAuthSave("read", menuId, val);
			 if(val == "N"){
				 $("#authWrite_" + menuId).attr("checked", false);  
				 menuGrantAuthSave("write", menuId, 'N');
			 }
		 });
		 
		 $(".sm2_expander, #top > .sm2_title").unbind("click").bind("click", function(){
			 var id = $(this).attr("id");
			 var idStr = id.substring(id.indexOf("_") + 1, id.length); 
			 
			 if($("#subUl_" + idStr).css("display") == "none"){ 
				 $("#li_" + idStr).removeClass("sm2_liClosed").addClass("sm2_liOpen");    
				 $("#subUl_" + idStr).show();
				 $("#subUl_" + idStr).focus();
			 }else{
				 $("#li_" + idStr).removeClass("sm2_liOpen").addClass("sm2_liClosed"); 
				 $("#subUl_" + idStr).hide();
				 $("#subUl_" + idStr).focus();   
			 }  
		 });
		 
		 $("#allShow").unbind("click").bind("click", function(){
			 if($(this).hasClass("hide")){
				 $("[id ^= 'li_']").removeClass("sm2_liClosed").addClass("sm2_liOpen");   
				 $(this).removeClass("hide").addClass("show");
				 $(this).text("메뉴 ▲");
				 $("ul[id *= 'subUl_']").show();	 
			 }else{
				 $("[id ^= 'li_']").removeClass("sm2_liOpen").addClass("sm2_liClosed");
				 $(this).removeClass("show").addClass("hide");
				 $(this).text("메뉴 ▼");  				  
				 $("ul[id *= 'subUl_']").hide();  
			 }
		 }); 
		   
	 }); 
	 
	 
	 function setMenuGrantAuthList(authId){  
			var data = getMenuGrantAuthList(authId, ""); 
			var addMenuAuthList = "";
			if(data != null && data.resultList != null && data.resultList.length > 0){
				for ( var i = 0; i < data.resultList.length; i++) { 
					
					if(i == 0){
						addMenuAuthList +='<li class="sm2_liClosed" style="padding-top: 10px;" id="li_' + data.resultList[i].menu_id + '" >';	    
					}else{
						addMenuAuthList +='<li  class="sm2_liClosed" id="li_' + data.resultList[i].menu_id + '">';      
					}
					addMenuAuthList +='<dl  class="sm2_s_published" >';
					if(data.resultList[i].top_menu_id_count > 0){
						addMenuAuthList +='<a href="#"  class="sm2_expander" id="topMenu_' + data.resultList[i].menu_id + '" style="z-index:99999">&nbsp;</a>';
						addMenuAuthList +=   '<dt id="top"><a style="z-index:9999" class="sm2_title" href="#" id="topMenu_' + data.resultList[i].menu_id + '">' + data.resultList[i].menu_nm + '</a></dt>';	
					}else{
						addMenuAuthList +='<a href="#">&nbsp;</a>'; 
						addMenuAuthList +=   '<dt ><a href="#" >' + data.resultList[i].menu_nm + '</a></dt>';  
					}
					/* addMenuAuthList +=  '<dd><strong>Actions:</strong> <span class="sm2_move">'; 
					if(data.resultList[i].auth_read == 'Y'){
						addMenuAuthList +='<input id="authRead_' + data.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" checked="checked"/>';
					}else{  
						addMenuAuthList +='<input id="authRead_' + data.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" />';  
					}
					if(data.resultList[i].auth_write == 'Y'){
						addMenuAuthList +='<input id="authWrite_' + data.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" checked="checked"/>';
					}else{  
						addMenuAuthList +='<input id="authWrite_' + data.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" />';  
					}
					addMenuAuthList +=	 '</span></dd>'; */
					addMenuAuthList +='</dl>';
					addMenuAuthList +='<ul id="subUl_'+ data.resultList[i].menu_id +'" style="display:none;"></ul>';
					addMenuAuthList += '</li>';  
				}
				$("#sitemap").empty();
				$("#sitemap").append(addMenuAuthList);
				
				for ( var i = 0; i < data.resultList.length; i++) {
					setSubMenuGrantAuthList(authId, data.resultList[i].menu_id);
				}
			}else{
				addMenuAuthList +='<li >데이터가 없습니다.</li>';			
			}
		} 
	 
	 function setSubMenuGrantAuthList(authId, menuId){ 
		 	var subData = getMenuGrantAuthList(authId, menuId);  
		 	var addSubMenuAuthList = "";
		 	if(subData != null && subData.resultList != null && subData.resultList.length > 0){
				for ( var i = 0; i < subData.resultList.length; i++) {
					addSubMenuAuthList +=  '<li class="sm2_liOpen">';  
					addSubMenuAuthList +=   '<dl class="sm2_s_published"><a href="#"class="">&nbsp;</a>';     
					addSubMenuAuthList +=     '<dt><a class="sm2_title" href="#">' + subData.resultList[i].menu_nm + '</a></dt>';
					addSubMenuAuthList +=     '<dd style="margin-top: 3px;"><strong>Actions:</strong> <span class="sm2_move">';
					if($.trim(subData.resultList[i].menu_id) == "104000" || $.trim(subData.resultList[i].menu_id) == "104001"){ 
						addSubMenuAuthList +='<input id="authRead_' + subData.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" checked="checked" disabled="disabled"/>';
					}else{
						if(subData.resultList[i].auth_read == 'Y'){
							addSubMenuAuthList +='<input id="authRead_' + subData.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" checked="checked"/>';
						}else{  
							addSubMenuAuthList +='<input id="authRead_' + subData.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" />';  
						}	
					}
					if(subData.resultList[i].auth_write == 'Y'){ 
						addSubMenuAuthList +='<input id="authWrite_' + subData.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" checked="checked"/>';
					}else{  
						addSubMenuAuthList +='<input id="authWrite_' + subData.resultList[i].menu_id + '" type="checkbox" value="Y" style="margin-left: 10px;width: 20px;height: 20px;" />';  
					}
					addSubMenuAuthList +='</span></dd>';
					addSubMenuAuthList +=   '</dl>';
					addSubMenuAuthList += '</li>';
				}
			}
			$("#subUl_" + menuId).empty();  
			$("#subUl_"  + menuId).append(addSubMenuAuthList);  
	 }
	 
	   /*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 메뉴 권한을 주기위한 리스트 가져오기 AJAX
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		function getMenuGrantAuthList(authId, subMenuId){
			var requestUrl = "/ajax/menuGrantAuthList.json";
			var queryParam = "";
			if(subMenuId != ""){
				queryParam = "auth_id=" + authId + "&subMenuId=" + subMenuId;
			}else{
				queryParam = "auth_id=" + authId;					   
			}
			console.log(queryParam); 
			var returnParam = "";  
			$.ajax({ 
				url : requestUrl,
				data : queryParam, 
				type : "POST",  
				async : false,
				success : function(data){
					console.log(data.code);
					console.log(data.resultList); 
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
		* 메뉴 권한 주기 AJAX
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		function menuGrantAuthSave(type, menuId, value){
			var requestUrl = "/ajax/menuGrantAuthSave.json";
			var queryParam = "";
			if(type == "write"){
				queryParam = "type=" + type + "&menu_id=" + menuId + "&auth_id=" + p_auth_id + "&auth_write=" + value;
			}else if(type == "read"){
				queryParam = "type=" + type + "&menu_id=" + menuId + "&auth_id=" + p_auth_id + "&auth_read=" + value;
			}
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
	   
		//Request
		function Request(valuename)   
		{
		    var rtnval;
		    var nowAddress = decodeURI(location.href);
			//var nowAddress = unescape(location.href);
		    var parameters = new Array();
		    parameters = (nowAddress.slice(nowAddress.indexOf("?")+1,nowAddress.length)).split("&");
		    for(var i = 0 ; i < parameters.length ; i++){
		        if(parameters[i].indexOf(valuename) != -1){
		            rtnval = parameters[i].split("=")[1];
		            if(rtnval == undefined || rtnval == null){
		                rtnval = "";
		            }
		            return rtnval;
		        }
		    }
		} 
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* F5키 방지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		function f5(){
			 if(event.keyCode==116){
				//alert("새로고침(F5)은 금지합니다.");
			     event.keyCode =505; 
			     event.cancelBubblue = true;
			     event.returnValue=false;
			     return false;
			 }
		}
	
</script>
<style type="text/css">
h1 {
	font-size: 12px;
}

caption {
	visibility: hidden;
	font-size: 0;
	height: 0;
	margin: 0;
	padding: 0;
	line-height: 0;
}
body {background-color:white;}
</style>
<title>순시결과 사진</title> 	

</head>
<body class="has_js" style="overflow-x:hidden;overflow-y:hidden;" background="white"><!-- oncontextmenu="return false" onkeydown="f5()" -->    
<div id="container">


		<table board="1">
		<thead > 
		<tr>
			<th style="text-align:center">제목</th>
			<th style="text-align:center">사진</th>
			<th style="text-align:center">내용</th>
		</tr>			
		</thead>
				<c:forEach var="fileList" items="${fileList}" varStatus="status">
				<tr>
					<td>
						<c:out value="${fileList.file_subject}"/>
					</td>
					<td>
						<img width="140" height="170" src="<c:out value="${fileList.file_realpath}"/>"/>
					</td>
					<td>
						<c:out value="${fileList.file_contents}"/>
					</td>
				</tr>	
				</c:forEach>
			
		</table>

	</div>
</body>


</html>


