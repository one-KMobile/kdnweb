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
<%-- 
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>
<link href="/css/egovframework/com/cmm/com.css" rel="stylesheet" type="text/css"> 
--%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: "112000", d2: "112002" });
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 리스트 구분선 보여주기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var idStr = "";
		var idArrayList = new Array();
		
		$("[id ^= 'eqp_' ]").each(function(index){  
			var id = $(this).attr("id"); 
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			if(orgIdStr != idStr){  
				idArrayList.push(orgIdStr);   
			}
			idStr = orgIdStr;  
		});
		 
		for(var i = 0; i < idArrayList.length; i++){ 
			var idStr = "eqp_" + idArrayList[i];
			$("[id = '" + idStr +"']").last().siblings().css("border-bottom","1px solid black");
			$("[id = '" + idStr +"']").last().css("border-bottom","1px solid black"); 
		}
		
		$("td").last().siblings().css("border-bottom","");     
		$("td").last().css("border-bottom","");    
		
		//중복제거3
		var idStr3 = "";
		var idArrayList3 = new Array();
		$("[id ^= 'groupFileId_']").each(function(index){
			var id = $(this).attr("id");
			
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			
			if(orgIdStr != idStr3){ 
				idArrayList3.push(orgIdStr);
			}
			
			idStr3 = orgIdStr;
		
		});
		
		for(var i = 0; i < idArrayList3.length; i++){   
			var idStr = "groupFileId_" + idArrayList3[i];
			var idLen = $("[id = " + idStr +"]").length; 
			$("[id = '" + idStr +"']").not(":eq(0)").remove();   
			$("[id = '" + idStr +"']").eq(0).attr("rowspan", $.trim(idLen));  
		}
		
	});
	
</script>
<title>게시판 목록조회</title>

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
</style>
</head>
<body>
	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
	
	<!-- wrap -->
	<div id="wrap">
    	
		<!-- Left Area --> 
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		 
		<header>  
            <div class="navi">              
            <ul>      
            	<li id="top_104000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=001" >보통순시</a></li>  
                <li id="top_101000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=" >점퍼접속</a></li>
                <li id="top_102000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=027" >전선접속</a></li>
                <li id="top_103000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=028" >접지저항</a></li>
                <li id="top_107000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=021" >기별점검</a></li>
                <li id="top_106000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=025" >항공등등구</a></li> 
                <li id="top_106000" class="current"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=024" >장애등점검</a></li>
                <li id="top_106000"><a href="<c:url value='/kdn/admin/insResultImsiView.do'/>?ins_ty_cd=029" >불량애자</a></li>  
            </ul>  
            </div>  
        </header> 
		
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/insResultList.do'/>">
			<!-- 검색 파라메터  -->
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
			<input name="basicYear" type="hidden"	value="<c:out value='${box.basicYear}'/>" />
			<input name="basicMonth" type="hidden"	value="<c:out value='${box.basicMonth}'/>" />
			<input name="cycle_ym" type="hidden"	value="<c:out value='${box.cycle_ym}'/>" />
			
			<!-- container -->
			<div class="container"> 
				<!-- top -->
				<div class="top">
					<h3>순시결과 상세</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 순시결과 관리 &gt; <strong class="location">순시결과 상세</strong>
					</p>
				</div>
				<!-- // top -->
				
				<div style="float: left;width: 1033px;margin-bottom: 10px">
					<div style="margin-top: 10px;padding-left: 18px;font-weight: bold;margin-bottom: 12px;float: left;">○ 항공장애등 점검</div>
					
					<div style="margin-top: 40px;padding-left: 30px;margin-bottom: 13PX;">
						<span style="font-weight: bold;">▷ 설비정보</span> 
					</div>
					
					<table width="100%" border="1" cellpadding="0" cellspacing="1"	class="table-register" style="padding-top:20px;">
					<tr style="height: 30px;border-bottom: 1px solid #D2D4D1;">
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">1차사업소</th>
						<td style="padding-left:10px;">${viewBox.fst_bizplc_cd_nm}</td>
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">2차사업소</th>
						<td style="padding-left:10px;">${viewBox.scd_bizplc_cd_nm}</td>
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">선로명</th>
						<td style="padding-left:10px;">${viewBox.fnct_lc_dtls}</td>
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">순시원(정)</th>
						<td style="padding-left:10px;">${viewBox.check_ins_name_a}</td>
					</tr>
					<tr style="height: 30px;border-bottom: 1px solid #D2D4D1;">
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">순시종류</th>
						<td style="padding-left:10px;" colspan="3">${viewBox.ins_name}</td>
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">날씨</th>
						<td style="padding-left:10px;">${viewBox.weather_name}</td>
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">순시원(부)</th>
						<td style="padding-left:10px;">${viewBox.check_ins_name_b}</td>
					</tr>
					<tr style="height: 30px;">
						<th width="10%" height="23" class="required_text" scope="row" nowrap style="text-align: center;">기타사항</th>
						<td style="padding-left:10px;" colspan="7">${viewBox.gnrlz_opnin}</td>
					</tr>
					</table>
				</div>
				
				<!-- mid -->
				<div class="mid" >
					<div style="margin-top: 10px;padding-left: 30px;">
						<span style="font-weight: bold;">▷ 점검결과</span> 
						<div style="float:right;margin-bottom: 5px;margin-right:5px;">
							<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font> 양호
							<font style="font-size: 18px;font-weight: bold;color:#ffd700;">ⓥ</font> 조치후양호
							<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font> 불량
							<font style="font-size: 15px;font-weight: bold;position: relative;top: 2px;">㊀</font> 해당없음
						</div>
					</div>
					<div style="width:100%;height:450px; overflow:auto;overflow-x:hidden"> 
						<table summary="순시결과 게시판입니다." id="tracksTable" >
							<caption>순시결과 게시판</caption> 
							<thead > 
								<tr style="border-top: #1A90D8 2px solid"> 
									<th>번호</th>
									<th>지지물<br/>번호</th>
									<th>순시시각</th>
									<th>항공등종류</th>
									<th>전원</th>
									<th>항공등 번호</th>
									<th>제어반</th>
									<th>태양전지</th>
									<th>축전지</th>
									<th>등기구</th>
									<th>전선</th>
									<th>양부판정</th>
								</tr>
							</thead>
							<tbody>   
								<c:forEach var="result" items="${subList}" varStatus="status">
									<tr>
										<td> 
											<c:out value="${status.count}" />
										</td>
										<td id = "eqp_${result.eqp_no}">
											<span style="float:left;padding-left:10px;"><c:out value="${result.eqp_nm}" /></span>
										</td>
										<td>
											<c:out value="${result.ins_date}" />
										</td>
										<td>
											<c:out value="${result.flight_lmp_knd}" />
										</td>
										<td>
											<c:out value="${result.srcelct_knd}" />
										</td>
										<td>
											<c:out value="${result.flight_lmp_no}" />
										</td>
										<td>
											<c:choose>
												<c:when test="${result.ctrl_ban_gdbd_secd eq '001'}"><!-- 양호  -->
													<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
												</c:when>
												<c:when test="${result.ctrl_ban_gdbd_secd eq '003'}"><!-- 불량 -->
													<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>  
										</td>
										<td>
											<c:choose>
												<c:when test="${result.slrcl_gdbd_secd eq '001'}"><!-- 양호  -->
													<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
												</c:when>
												<c:when test="${result.slrcl_gdbd_secd eq '003'}"><!-- 불량 -->
													<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>  
										</td>
										<td>
											<c:choose>
												<c:when test="${result.srgbtry_gdbd_secd eq '001'}"><!-- 양호  -->
													<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
												</c:when>
												<c:when test="${result.srgbtry_gdbd_secd eq '003'}"><!-- 불량 -->
													<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>  
										</td>
										<td>
											<c:choose>
												<c:when test="${result.rgist_gu_gdbd_secd eq '001'}"><!-- 양호  -->
													<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
												</c:when>
												<c:when test="${result.rgist_gu_gdbd_secd eq '003'}"><!-- 불량 -->
													<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>  
										</td>
										<td>
											<c:choose>
												<c:when test="${result.cabl_gdbd_secd eq '001'}"><!-- 양호  -->
													<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
													<!-- <font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
													<font style="font-size: 18px;color: #cfcfcf;">ⓧ</font> -->
												</c:when>
												<c:when test="${result.cabl_gdbd_secd eq '003'}"><!-- 불량 -->
													<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>  
										</td>
										<td>
											<c:choose>
												<c:when test="${result.good_secd eq '001'}"><!-- 양호  -->
													<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font>
												</c:when>
												<c:when test="${result.good_secd eq '002'}"><!-- 조치후양호 -->
													<font style="font-size: 18px;font-weight: bold;color:#ffd700;">ⓥ</font>
												</c:when>
												<c:when test="${result.good_secd eq '003'}"><!-- 불량 -->
													<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font>
												</c:when>
												<c:when test="${result.good_secd eq '004'}"><!-- 해당없음 -->
													<font style="font-size: 16px;font-weight: bold;position: relative;top: 1px;">㊀</font> 
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>
										</td>  
									</tr>
								</c:forEach> 
								<c:if test="${fn:length(subList) == 0}">
									<tr> 
										<td colspan="12" >
											<font style="font-family:맑은 고딕;font-size:12px">데이터가 없습니다.</font>
										</td>
									</tr>	
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<!-- // mid -->
			</div>
			<!-- // container -->
			
			<!-- bottom Area -->
				<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
			<!-- // bottom Area -->
			</form>
		</div>
    	<!-- end wrap -->	
</body>
</html>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->


