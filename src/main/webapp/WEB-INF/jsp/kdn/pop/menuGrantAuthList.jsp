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
		 
		 var agt = navigator.userAgent.toLowerCase();
		 if (agt.indexOf("chrome") != -1){
			 
		 }else{
			 $("#sitemap").css("overflow","auto");  
		 }
		 
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
						addMenuAuthList +=   '<dt id="top"><a href="#" >' + data.resultList[i].menu_nm + '</a></dt>';  
					}
					
					if(data.resultList[i].top_menu_id_count <= 0){
						addMenuAuthList +=  '<dd style="padding: 3px 0px 0px 0px;"><strong>Actions:</strong> <span class="sm2_move">';  
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
						addMenuAuthList +=	 '</span></dd>'; 
					}
					
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
<title>메뉴 권한 조회</title> 

</head>
<body class="has_js" style="overflow-x:hidden;overflow-y:hidden;" oncontextmenu="return false" onkeydown="f5()" >              
 
<div id="container">
  <div id="content">
    <div id="content_left">
      <div id="content_right">
        <noscript>
        <div class="info i_error">
          <div class="info_top">
            <div class="info_tl">&#160;</div>
            <div class="info_tr">&#160;</div>
          </div>
          <div class="infobox">
            <h3>Javascript is disabled</h3>
            <p>You need to have Javascript enabled to use the administration features of this site.<br />
              Please enable Javascript and try again.</p>
          </div>
          <div class="info_bot">
            <div class="info_bl">&#160;</div>
            <div class="info_br">&#160;</div>
          </div>
        </div>
        </noscript>
        <div id="ctl00_siteWarning" class="info i_error" style="display: none;">
          <div class="info_top">
            <div class="info_tl">&#160;</div>
            <div class="info_tr">&#160;</div>
          </div>
          <div class="infobox">
            <h3 id="ctl00_siteWarningInnerHeading">&#160;</h3>
            <p id="ctl00_siteWarningInner">&#160;</p>
          </div>
          <div class="info_bot">
            <div class="info_bl">&#160;</div>
            <div class="info_br">&#160;</div>
          </div>
        </div>
        <!---->
        <div class="page">     
          <div class="page_inner">
            <div class="page_top">
              <div class="page_left"> </div>
              <div class="page_right"> </div>
            </div>
            <div class="col01"> 
              <h1 style="font-weight:bold;" >메뉴 권한 (<font id="authNm"></font>)</h1> 
              <dl id="sitemap_header">
                <dt>
					<span id="allShow" style="z-index:9999;cursor:pointer;" class="hide">메뉴 ▼</span>        
					<span style="position: absolute;right: 10px;">읽기 쓰기</span>     
                </dt>
              </dl>
              
              <ul id="sitemap" style="width:100%;height:450px;">              
              
			    <!-- <li> 
                  <dl class="sm2_s_published"><a href="#"class="sm2_expander">&nbsp;</a>
                    <dt><a class="sm2_title" href="#">Home</a></dt>
                    <dd class="sm2_actions"><strong>Actions:</strong> <span class="sm2_move" title="Move">Move</span><span class="sm2_delete" title="Delete">Delete</span><a href="#" class="sm2_addChild" title="Add Child">Add Child</a></dd>
                    <dd class="sm2_status"><strong>Status:</strong> <span class="sm2_pub" title="Published">Published</span><span class="sm2_workFlow" title="Draft Exists">Draft Exists</span></dd>
                  </dl>
				</li>
				
                <li class="sm2_liOpen">
                  <dl class="sm2_s_published"><a href="#"class="sm2_expander">&nbsp;</a>
                    <dt><a class="sm2_title" href="#">About us</a></dt>
                    <dd class="sm2_actions"><strong>Actions:</strong> <span class="sm2_move" title="Move">Move</span><span class="sm2_delete" title="Delete">Delete</span><a href="#" class="sm2_addChild" title="Add Child">Add Child</a></dd>
                  </dl>
                  <ul>
                    <li class="sm2_liOpen">
                      <dl class="sm2_s_published"><a href="#"class="sm2_expander">&nbsp;</a>
                        <dt><a class="sm2_title" href="#">Things we do</a></dt>
                        <dd class="sm2_actions"><strong>Actions:</strong> <span class="sm2_move" title="Move">Move</span><span class="sm2_delete" title="Delete">Delete</span><a href="#" class="sm2_addChild" title="Add Child">Add Child</a></dd>
                      </dl>
                    </li>
                  </ul>
                </li>  -->
                
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>


</html>


