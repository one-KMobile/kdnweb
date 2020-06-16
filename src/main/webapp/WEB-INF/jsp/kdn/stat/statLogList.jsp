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
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css"> 

<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script>
<!-- 달력 관련 스크립트  -->
<link href="<c:url value='/css/kdn/com/cmm/jquery-ui.css' />" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.ui.datepicker-ko.js' />"></script>
<!--그래프  -->
<!-- JQ-PLOT의 CSS를 설정 -->
<link class="include" rel="stylesheet" type="text/css" href="/css/jqplot/jquery.jqplot.css"/>
<script type="text/javascript" src="<c:url value='/js/jqplot/jquery.jqplot.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.highlighter.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.cursor.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.dateAxisRenderer.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.canvasAxisLabelRenderer.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.enhancedLegendRenderer.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.categoryAxisRenderer.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jqplot/jqplot.canvasAxisTickRenderer.js' />"></script>
<script type="text/javascript">
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: '108000', d2: '108003' });
		//$('#topmenu').topmenu({ d1: 8, d2: 3 }); 
		$("#s_category_code option:eq(3)").remove();
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var schFst_bizplc_cd= "<c:url value='${box.fst_bizplc_cd}' />";
		$("#fst_bizplc_cd > option[value = " + schFst_bizplc_cd + "]").attr("selected", "true");
		var log_type = "<c:url value='${box.log_type}' />";
		$("#log_type > option[value = " + log_type + "]").attr("selected", "true");
		var date_type = "<c:url value='${box.date_type}' />";
		$("#date_type > option[value = " + date_type + "]").attr("selected", "true");
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 1차 사업소 검색한 경우는 2차 사업소 페이지에 나오게 하고 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		if($.trim(schFst_bizplc_cd) != ''){
			setScdBizplcList(schFst_bizplc_cd);
		}
		var schScd_bizplc_cd = "<c:url value='${box.scd_bizplc_cd}' />";
		$("#scd_bizplc_cd > option[value = " + schScd_bizplc_cd + "]").attr("selected", "true");
				
		$("[id ^= logTypeTd]").each(function(index){
			var id = $(this).attr("id");
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
  			var log_type_val = $('#'+id).text().trim();
  			if(log_type_val =="1"){
  				$('#'+id).text("APP")
  			}else if(log_type_val =="2"){
  				$('#'+id).text("WEB")
  			}else if(log_type_val =="3"){
  				$('#'+id).text("SYNC")
  			}else{
  				$('#'+id).text("전체")
  			}	
  		});
  		
		$(function () {
			var dates = $("#startdate").datepicker({
				defaultDate: "+1w",
				//defaultDate: "",
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
			
			var dates = $("#enddate").datepicker({
				defaultDate: "+1w",
				//defaultDate: "",
				changeMonth: true,
				onSelect: function (selectedDate) {
					var option = this.id == "enddate" ? "minDate" : "maxDate", 
						instance = $(this).data("datepicker"),
						date = $.datepicker.parseDate(
							instance.settings.dateFormat ||
							$.datepicker._defaults.dateFormat,
							selectedDate, instance.settings);
					dates.not(this).datepicker("option", option, date);
				}
			});
			
		});
		

		/*--그래프---------------------*/
		var arryStat = new Array();
		$(":hidden[id ^=stat]").each(function() {
		   var hiddeValue = $(this).val();
		   var statValues = hiddeValue.split(",");
		   var statDate= statValues[0] ;
		   var statCount = statValues[1]*1;
		   var tmpArray = new Array(statDate ,statCount )
		   arryStat.push(tmpArray);
		});
		
		var statDataType = '일별 접속';
		var formatStr = "%y-%m-%d" ;
		//var scd_bizplc_cd = "";
		var fst_bizplc_cd  = "";
		var scd_bizplc_cd  = "";
		if(schFst_bizplc_cd !=''){
			fst_bizplc_cd =	 $("#fst_bizplc_cd option:selected").text();
			scd_bizplc_cd =	" : " + $("#scd_bizplc_cd option:selected").text();
		}
		
		if(date_type=='DD'){
			statDataType = '일별 접속' + '<br>' + fst_bizplc_cd + scd_bizplc_cd;
			formatStr = "%y-%m-%d" ;
		}
		else if(date_type=='MM'){
			statDataType = '월별 접속' + '<br>' + fst_bizplc_cd + scd_bizplc_cd;
			formatStr = "%y-%m" ;
		}
		else if	(date_type=='YY'){
			statDataType = '연도별 접속' + '<br>' + fst_bizplc_cd + scd_bizplc_cd;
			formatStr = "%y" ;
		}
		
		$(function(){
			 //var line1=[['2008-07-30',180], ['2008-07-14',6]];
			 var line =arryStat;
			 if(line!=""){
				 var plot2 = $.jqplot('graphDiv', [line], {
					 title : statDataType ,
				      axes:{
				        xaxis:{
				          renderer:$.jqplot.DateAxisRenderer, 
				         /*  tickOptions:{formatString:'%b %#d, %#I %p'},
				          min:'June 16, 2008 8:00AM', 
				          tickInterval:'2 weeks' */
				          
				          tickOptions:{formatString: formatStr}
				        },
				    	yaxis:{ // y축 옵션
				             //           label : '선호도', // y축 Label
				             min : 0, // 최소값
				     	}
				      },
				      series:[{
				    	  color : 'blue', //Line Color
				    	  lineWidth:2,  // Line 굵기
				    	  markerOptions:{style:'square'}
				      	}
				      ]
				  });
			 }
		});
	});
	
	function press(event) {
		if (event.keyCode == 13) {
			goSearch();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key) {
		document.frm.log_idx.value = key;
		document.frm.action = "<c:url value='/kdn/admin/logHistoryView.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch() {
		document.frm.action = "<c:url value='/kdn/stat/statLogList.do'/>";
		document.frm.pageIndex.value = "1" ;
		document.frm.submit();
	}


	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/stat/statLogList.do'/>";
		document.frm.submit();
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
	* 달력 이미지 클릭시 달력 표시
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function calljscal(id) {
		var id = '#' + id
		$(id).datepicker("show");
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 달력 데이터 리셋
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function dateReset() {
		$("#startdate").val("");
		$("#enddate").val("");
	}
	
	
</script>
<title>게시판 목록조회</title>
</head>
<body>
<!-- <div style="display:none">
	<td id="stat_">
			
	</td>
</div> -->

	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부	기능을 사용하실 수 없습니다.</noscript>
	<!-- Top Area -->
	<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
	<!-- // Top Area -->
	
	<!-- container -->
	<div id="container">
		<!-- Left Area -->
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/patrolman/List.do'/>">
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" /> 
 			<input name="log_idx" type="hidden" value="" />

			<!-- 구성관리 contents -->
			<div class="contents1">
				<!-- top -->
				<div class="top">
					<h3>로그 이력</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 로그 관리 &gt; <strong class="location">개별 로그</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid">
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							<label for="work_divide" class="ml21"><strong class="label_tit">접속타입</strong></label> 
							<select name="log_type" id="log_type" class="select_st ml8" title="검색조건선택" >
								<option value="">전체</option>
								<c:forEach var="logTypeList" items="${logTypeList}" varStatus="status">
									<option value="${logTypeList.code_value}">${logTypeList.code_name}</option>
								</c:forEach>
							</select> 
							
							<label for="work_divide" class="ml66"></label><label for="work_divide" class="ml66"></label>
							<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>1차사업소</strong></label> 
							<select id="fst_bizplc_cd" name="fst_bizplc_cd" class="select_st2 ml8" onchange="setScdBizplcList(this.value);">
								<option value="" >선택하세요</option>
								<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
									<option value="${fstBizplcList.code_value}">${fstBizplcList.code_name}</option>
								</c:forEach>
							</select> 
							
							<label for="work_divide" class="ml21"></label> 
							<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>2차사업소</strong></label> 
							<select id="scd_bizplc_cd" name="scd_bizplc_cd" class="select_st2 ml8" >
								<option value="" > ← 선택하세요</option>
							</select> 
							
						</li>
						<li>
							
							<label for="work_divide" class="ml66"></label>
							<label for="work_divide" class="ml21"><strong class="label_tit">일/월/년</strong></label> 
							<select name="date_type" id="date_type" class="select_st ml8" title="검색조건선택" onchange="goSearch()">
								<c:forEach var="dateTypeList" items="${dateTypeList}" varStatus="status">
									<option value="${dateTypeList.code_value}">${dateTypeList.code_name}</option>
								</c:forEach>
							</select> 
							
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" /></a>
						
							<label for="work_divide" class="ml66"></label><label for="work_divide" class="ml66"></label>
							<label for="work_divide" class="ml21"></label>
							<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>검색기간</strong></label> 
							<input name="startdate" id="startdate" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" value="${box.startdate}" readonly>
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('startdate')" style="cursor:pointer;">
							~
							<input name="enddate" id="enddate" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" value="${box.enddate}" readonly>
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('enddate')" style="cursor:pointer;">
							<input id="ListShow" type="button" value="초기화" title="리스트" onclick="dateReset();" class="button" style="cursor:pointer;">
						 </li>
						 
						 <li>
						 	<tr style="height: 30px;">
								<th width="10%" height="23" class="required_text" scope="row" nowrap="" style="text-align: center;">검색기간</th>
							<td id="dateSearch_td" style="padding-left:10px;" colspan="7">
							<label for="work_divide" class="ml21">
								<c:if test="${box.startdate != '' || box.enddate != '' }" >
									<c:out value="${box.startdate}" /> 
									~
									<c:out value="${box.enddate}" />
								</c:if>
							</td>
						 </li>
					</tr>
					</ul>
					<table summary="공지 게시판입니다.">
						<caption>순시자 게시판</caption>
						<thead>
							<tr>
								<th>번호</th>
								<th>접속타입</th>
								<th id="th_date">접속일</th>
								<th>접속수</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<input type="hidden" id="stat_${status.count}" value="${result.statDate},${result.statDateCount}">
								<tr>
									<td><c:out	value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" /></td>
									<td id="logTypeTd_${status.count}"><c:out value="${box.log_type}" /></td>
									<td><c:out value="${result.statDate}" /></td>
									<td><c:out value="${result.statDateCount}" /></td>
								</tr>
							</c:forEach>
							<!-- 그래프를 출력할 Div를 생성 -->
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.searchWrd}">
										<tr>
											<td colspan="6"><font style="font-family: 맑은 고딕; font-size: 12px">검색하신 공지 데이터가 없습니다.</font></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="6"><font style="font-family: 맑은 고딕; font-size: 12px">접속 데이터가 없습니다.</font></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:if>
						</tbody>
						
					</table>
					<!-- paging -->
					<div id="pagination" class="paging" align="center">
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_pageview" />
					</div>
					<div id="graphDiv" />
					<!-- // paging -->
				</div>
				<!-- // mid -->
			</div>
			<!-- // 구성관리 contents -->
		</form>
	</div>
	<!-- // container -->
	<!-- bottom Area -->
		<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
	<!-- // bottom Area -->
	
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

