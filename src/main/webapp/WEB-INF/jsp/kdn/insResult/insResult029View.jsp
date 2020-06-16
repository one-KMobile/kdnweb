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
		$('#topmenu').topmenu({ d1: "103000", d2: "103001" }); 
		
		$("#goListBtn").mouseover(function(){  
			$(this).css({"background":"#E4EAF8","color":"#2E4B90"});	  
		}).mouseout(function(){
			$(this).css({"background":"","color":""});   
		});
		
		//중복제거2
		 var idStr1 = "";
		var idArrayList1 = new Array();
		$("[id ^= 'eqp_']").each(function(index){
			var id = $(this).attr("id");

			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			
			if(orgIdStr != idStr1){ 
				idArrayList1.push(orgIdStr);
			}
			idStr1 = orgIdStr;

		}); 

		for(var i = 0; i < idArrayList1.length; i++){  
			/* var idStr = "eqp_" + idArrayList1[i];
			var idLen = $("[id = " + idStr +"]").length; 
			$("[id = '" + idStr +"']").not(":eq(0)").remove();   
			$("[id = '" + idStr +"']").eq(0).attr("rowspan", $.trim(idLen));   */
		}
		
		//중복제거2
		var idStr2 = "";
		var idArrayList2 = new Array();
		$("[id ^= 'tower_']").each(function(index){
			var id = $(this).attr("id");
			
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			
			if(orgIdStr != idStr2){ 
				idArrayList2.push(orgIdStr);
			}
			
			idStr2 = orgIdStr;

		});
 
		for(var i = 0; i < idArrayList2.length; i++){
			var idStr = "tower_" + idArrayList2[i];
			var idLen = $("[id = " + idStr +"]").length; 
			$("[id = '" + idStr +"']").not(":eq(0)").remove();   
			$("[id = '" + idStr +"']").eq(0).attr("rowspan", $.trim(idLen));  
		}
		
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
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 목록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goList(){
		form = document.frm;
		form.action = "<c:url value='/kdn/admin/insResultList.do'/>";
		form.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 팝업 노출
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/	
	function roleView(group_file_id){
		var cw = screen.availWidth;     //화면 넓이
		var ch = screen.availHeight;    //화면 높이
		
		var ml = (cw-500)/2;        //가운데 띄우기위한 창의 x위치
		var mt = (ch-600)/2;         //가운데 띄우기위한 창의 y위치
		 
		var popUrl = "/kdn/admin/pop/insResult001View.do?group_file_id=" + group_file_id	//팝업창에 출력될 페이지 URL  
		var popOption = "width=500, height=600, resizable=yes, scrollbars=auto, status=no, top=" + mt + ",left=" + ml;    //팝업창 옵션(optoin)  
			window.open(popUrl,"",popOption);
	}
		
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
		 
		 <!-- Top Area -->
		<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
		<!-- // Top Area --> 
		
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
					<div style="margin-top: 10px;padding-left: 18px;font-weight: bold;margin-bottom: 12px;float: left;">○ 불량애자 점검</div>
					
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
						<td style="padding-left:10px;" colspan="7">${viewBox.remarks}</td>
					</tr>
					</table>
				</div>
				
				<!-- mid -->
				<div class="mid" >
					<div style="margin-top: 10px;padding-left: 30px;">
						<span style="font-weight: bold;">▷ 점검결과</span>
						<!-- <input type="button" value="List로 이동" title="목록" onclick="goList(); return false;" class="button" style="margin-left: 30PX;cursor:pointer;" id="goListBtn"> -->
						<input type="button" value="목록" title="목록" onclick="goList(); return false;" class="button" style="margin-left: 30PX;cursor:pointer;" id="goListBtn"> 
						<div style="float:right;margin-bottom: 5px;margin-right:5px;">
							<font style="font-size: 18px;font-weight: bold;color: #32cd32;">ⓥ</font> 양호
							<font style="font-size: 18px;font-weight: bold;color:#ffd700;">ⓥ</font> 조치후양호
							<font style="font-size: 18px;font-weight: bold;color: red">ⓧ</font> 불량
							<font style="font-size: 15px;font-weight: bold;position: relative;top: 2px;">㊀</font> 해당없음
						</div>
					</div>
					<div style="width:100%;height:400px; overflow:auto;overflow-x:hidden"> 
						<table summary="순시결과 게시판입니다." id="tracksTable" >
							<caption>순시결과 게시판</caption> 
							<thead> 
								<tr style="border-top: #1A90D8 2px solid;">
									<th>번호</th>
									<th>지지물<br/>번호</th>
									<th>애자런형(좌)</th>
									<th>애자런형(우)</th>
									<th>회선명</th>
									<th>위치</th>
									<th>상중하</th>
									<th>애자수량</th>
									<th>불량수량</th>
									<th>양부판정</th>
									<th>순시사진</th>
								</tr>
							</thead>
							<tbody>   
								<c:forEach var="result" items="${subList}" varStatus="status">
									<tr>
										<td> 
											<c:out value="${status.count}" />
										</td>
										<td id = "tower_${fn:replace(result.tower_idx, '.','')}">  
											<c:out value="${result.tower_idx}" />
										</td>
										<td>
											<c:out value="${result.insbty_lft}" />
										</td>
										<td>
											<c:out value="${result.insbty_rit}" />
										</td>
										<td id = "eqp_${result.cl_no}">
											<c:out value="${result.cl_no}" />
										</td>
										<td id = "ty_${result.ty_secd}">
											<c:out value="${result.ty_secd}" />
										</td>
										<td>
											<c:out value="${result.phs_secd}" />
										</td>
										<td>
											<c:out value="${result.insr_qy}" />
										</td>
										<td>
											<c:out value="${result.bad_insr_qy}" />
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
										<td id="groupFileId_${result.group_file_id}">
											<c:if test="${result.group_file_id_check eq '1'}">
												<a style="color:#729ACF;" id="groupFileId" href="javascript:roleView('<c:out value="${result.group_file_id}"/>');">사진보기</a>
											</c:if>
										</td> 
									</tr>
								</c:forEach> 
								<c:if test="${fn:length(subList) == 0}">  
									<tr> 
										<td colspan="11" >
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


