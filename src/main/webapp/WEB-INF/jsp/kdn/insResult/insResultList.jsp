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
<%-- <script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script> --%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>   
<script type="text/javascript">
	$(document).ready(function(){		
		$('#topmenu').topmenu({ d1: "103000", d2: "103001" });  
	});
	
	function press(event) {
		if (event.keyCode==13) {
			goSearch();
		}
	}
	
	$(document).ready(function () {
		thisYear(); //초기에 년 셋팅
		thisMonth(); //초기에 월 셋팅
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var basicYear = "<c:url value='${box.basicYear}' />";
		var basicMonth = "<c:url value='${box.basicMonth}' />"; 
		
		if($.trim(basicYear) != ''){
			$("#basicYear").val(basicYear);
		}
		if($.trim(basicMonth) != ''){     
			$("#basicMonth").val(basicMonth);   
		}else{
			goSearch();
		}
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 리스트 구분선 보여주기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var idStr = "";
		var idArrayList = new Array();
		var typeStr = new Array();
		$("[id ^= 'td1_']").each(function(index){
			var id = $(this).attr("id");
			
			var TDeq = id.substring(id.indexOf("_")+1, id.indexOf("/"));
			typeStr = id.substring(id.indexOf("/")+1, id.length).split("/");
			
			var orgIdStr = typeStr[0] + typeStr[1] + typeStr[2];
			var orgIdStr2 = typeStr[0] + "/" + typeStr[1] + "/" + typeStr[2];
			if($.trim(orgIdStr) != idStr){  
				idArrayList.push(TDeq + "/" + orgIdStr2);
			}
			idStr = orgIdStr;
		});
		 
		for(var i = 0; i < idArrayList.length; i++){
			var idStr = idArrayList[i];
			var TDeq = idStr.substring(0, idStr.indexOf("/"));
			
			for(var j = 0; j < $("th").length; j++){
				if(j == 0){
					$("[id ^= 'td1_" + parseInt(TDeq-1) +"']").css("border-bottom-color", "black");
				}else{ 
					var tdId = "td" + (j+1) +"_" + parseInt(TDeq-1) ;
					$("[id = '" + tdId +"']").css("border-bottom-color", "black");	
				} 
			}
		}	
		
		$("[id ^= percent_]").each(function(index){
			var id = $(this).attr("id"); 
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			var arrOrgIdStr = orgIdStr.split("/");
			
			var percentVal = ( parseInt(arrOrgIdStr[0]) / parseInt(arrOrgIdStr[1]) ) * 100;
			var graphVal = - (120 - ( (parseInt(percentVal) / 100 ) * 120));   
			$(this).text(Math.round(percentVal) + "%");
			
			if(percentVal > 0 && percentVal <= 25){
				$(this).parent().find("img").css("background-image","url(../../../../images/kdn/progressbar/progressbg_red.gif)");	
			}else if(percentVal > 25 && percentVal <= 50){
				$(this).parent().find("img").css("background-image","url(../../../../images/kdn/progressbar/progressbg_orange.gif)");
			}else if(percentVal > 50 && percentVal <= 75){
				$(this).parent().find("img").css("background-image","url(../../../../images/kdn/progressbar/progressbg_yellow.gif)");
			}else if(percentVal > 75 && percentVal <= 100){
				$(this).parent().find("img").css("background-image","url(../../../../images/kdn/progressbar/progressbg_green.gif)");
			}else{ 
				$(this).parent().find("img").css("background-image","url(../../../../images/kdn/progressbar/progressbg_green.gif)");
			}
			
			$(this).parent().find("img").css("background-position", $.trim(graphVal) + "px 50%"); 
		}); 
		
	});
	
	
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key, codeId){
		document.frm.schedule_id.value = key;
		
		switch (codeId) {
		/* 보통순시, 항공장애등등구확인점검, 항공장애등점검, 접지저항측정점검, 전선접속개소점검, 점퍼접속개소점검 */
		case '001': case '025': case '024': case '028': case '027': case '026':
			document.frm.action = "<c:url value='/kdn/admin/insResultView.do'/>";	
			break;
		case '021': /* 기별점검 */
			document.frm.action = "<c:url value='/kdn/admin/insResultGGView.do'/>";	
			break;
		case '022': /* 정밀점검 */
			document.frm.action = "<c:url value=''/>";	
			break;
		case '029': /* 불량애자 */
			document.frm.action = "<c:url value='/kdn/admin/insResult029View.do'/>";	
			break;	
		case '003': /* 예방순시 */
			document.frm.action = "<c:url value=''/>";	
			break;
		default:
			document.frm.action = "";
			break;
		}
		document.frm.submit(); 
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch(){
		if ($.trim(document.frm.basicYear.value) == '') {
			alert("순시년도를 선택해주세요.");
			$("#basicYear").focus();
			return;
		}
		if ($.trim(document.frm.basicMonth.value) == '') {
			alert("순시월을 선택해주세요.");
			$("#basicMonth").focus();
			return;
		}
		
		var YearStr = $("#basicYear").val();
		var MonthStr = $("#basicMonth").val();
		var cycle_ym = YearStr + MonthStr;  
		$("#cycle_ym").val(cycle_ym);
		document.frm.action = "<c:url value='/kdn/admin/insResultList.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) { 
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/insResultList.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 순시결과 삭제
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/	
	function setInsResultDelete() {
		document.frm.action = "<c:url value='/kdn/admin/delete/insResult.do'/>";
		document.frm.submit();
	}
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 년도 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function thisYear(){
	  var date = new Date();
	  var thisYear = date.getFullYear();
	  var selectValue = document.getElementById("basicYear");
	  var optionIndex = 0;
	  for(var i=thisYear-3;i<thisYear+4;i++){
		   if(i==thisYear) {selectValue.add(new Option(i+"년",i,true,true),optionIndex++);} 
		   else{selectValue.add(new Option(i+"년",i),optionIndex++);}                             
	   }
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 월 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function thisMonth(){
		var date = new Date();
		var thisMonth = date.getMonth() + 1; 
		var addSelect = "";
		addSelect += '<option value="" >선택하세요</option>'; 
		for(var i = 1; i < 13; i++) {   
			if(i >= 10) {           
		  		addSelect += '<option value="' + i + '" >' + i + '월</option>';	
		  	}else{
		  		addSelect += '<option value="0' + i + '" >' + i + '월</option>';
		  	}	
		}
		$("#basicMonth").empty();
		$("#basicMonth").append(addSelect); 
		$("#basicMonth > option").eq(thisMonth).attr("selected","selected");  
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 년도 계산 Add하기
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function addDateOption(){
		var selectYear =document.getElementById("basicYear").value;
		
		var addSelect = "";
		for(var i = selectYear - 3; i < parseInt(selectYear) + 4; i++) {  
		  	if(i == selectYear) {
		  		addSelect += '<option value="' + i + '" selected="selected">' + i + '년</option>';	
		  	}else{
		  		addSelect += '<option value="' + i + '" >' + i + '년</option>';
		  	}
		}
		$("#basicYear").empty();
		$("#basicYear").append(addSelect);
	}
</script>
<title>게시판 목록조회</title>

<style type="text/css">
.progressbar{   
width: 120px; 
height: 12px;
/* background-image: url(../../../../images/kdn/progressbar/progressbg_green.gif); */
padding: 0px;
margin: 0px;
/* background-position: -78px 50%; */  
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
		
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/groupCodeList.do'/>"> <!-- target="_hidden" -->
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
			<input name="cycle_ym" id="cycle_ym" type="hidden"	value="<c:out value='${box.cycle_ym}'/>" />
			<input name="schedule_id" id="schedule_id" type="hidden"	value="" />

			<!-- .container -->
			<div class="container"> 
				<!-- top -->
				<div class="top">
					<h3>순시결과 검색</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 순시결과 관리 &gt; <strong class="location">순시결과 검색</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid" >
					<ul>
						<li> 
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							
							<label for="work_divide" class="ml66" ><strong class="label_tit"><font style="color:red;">* </font>순시년도</strong></label>            
							<select id="basicYear" name="basicYear" class="select_st ml27" onchange="addDateOption();" >     
							</select>  
							<label for="work_divide" class="ml66" ><strong class="label_tit"><font style="color:red;">* </font>순시월</strong></label>
							<select id="basicMonth" name="basicMonth" class="select_st ml27"  > 
							</select>
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" style="padding-left: 10PX;"/></a>
						</li>
					</ul>
					<table summary="순시결과 게시판입니다." >
						<caption>순시결과 게시판</caption>
						<thead>
							<tr>
								<th  class="thFrist">번호</th>
								<th>1차사업소</th>
								<th>2차사업소</th>
								<th>선로명</th>
								<th>순시월</th>
								<th>순시종류</th>
								<th>순시율(%)</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td id = "td7_${status.count}"> 
										<c:out value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" />
									</td>
									<td id = "td1_${status.count}/${result.fst_bizplc_cd_nm}/${result.scd_bizplc_cd_nm}/${result.fnct_lc_dtls}"> 
										<c:out value="${result.fst_bizplc_cd_nm}" />
									</td>
									<td id = "td2_${status.count}">
										<c:out value="${result.scd_bizplc_cd_nm}" />
									</td>
									<td id = "td3_${status.count}">
										<span style="float:left;padding-left:10px;"><c:out value="${result.fnct_lc_dtls}" /></span>
									</td>
									<td id = "td4_${status.count}">
										<c:if test="${not empty result.cycle_ym}">
											<c:out value="${fn:substring(result.cycle_ym,0,4)}" />년&nbsp;<c:out value="${fn:substring(result.cycle_ym,4,6)}" />월
										</c:if>  
									</td>
									<td id = "td5_${status.count}">   
										<a style="float:left;color:#729ACF;padding-left:10px;" href="javascript:goView('<c:out value="${result.schedule_id}" />','<c:out value="${result.code_id}" />')">
											<c:out value="${result.ins_name}" /> (<c:out value="${result.ins_count}" /> / <c:out value="${result.tower_count}" />) 
										</a>
									</td>
									<td id = "td6_${status.count}" style="width:180px;">
										<span style="float:left;margin-left:10px;">
											<img alt="퍼센트바" src="<c:url value="/images/kdn/progressbar/progressbar.gif" />" class="progressbar" >
											(<font id="percent_${result.ins_count}/${result.tower_count}"></font>)    
										</span>
									</td>
								</tr> 
							</c:forEach>
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.basicMonth}">
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">순시결과 데이터가 없습니다.</font>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">순시월 검색 조건을 선택하세요.</font>
											</td>
										</tr>	  
									</c:otherwise>
								</c:choose>
							</c:if>
						</tbody>
					</table>
					<!-- 순시결과 스케줄 삭제버튼 -->
					<!-- <div style="padding: 10px 0 0 10px">
						<input type="button" value="순시결과 스케줄 삭제" title="순시결과 스케줄 삭제" onclick="javascript:setInsResultDelete();" class="button">
					</div> -->
					<!-- paging -->
					<div id="pagination" class="paging" align="center">
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_pageview" />
					</div>
					<!-- // paging -->
					
					<!-- 지도 Area  -->
					<div id="map" style="dispaly:none;"></div>
					<!-- // 지도 Area  -->					
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
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
</html> 
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
