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
<!-- JQ-PLOT의 기본 설정 -->
<!-- <script type="text/javascript" src="/js/jqplot/jquery.jqplot.js"/>
Highlighter(마우스 접근시 데이터정보 표시) 설정
<script type="text/javascript" src="/js/jqplot/jqplot.highlighter.js"/>
좌표에 관한 정보나 Zoom 기능 사용시 설정
<script type="text/javascript" src="/js/jqplot/jqplot.cursor.js"/>
축의 데이터를 날짜형태로 입력하기 위해서 설정
<script type="text/javascript" src="/js/jqplot/jqplot.dateAxisRenderer.js"/>
축의 데이터의 Label Option을 설정
<script type="text/javascript" src="/js/jqplot/jqplot.canvasAxisLabelRenderer.js"/>
Legend(Line에대한 간단한 범례)의 Option을 설정
<script type="text/javascript" src="/js/jqplot/jqplot.enhanceLegendRenderer.js"/>
축의 데이터를 순서에 상관없이 자동정렬을 설정
<script type="text/javascript" src="/js/jqplot/jqplot.categoryAxisRenderer.js"/>
축의 데이터 표현설정과 그래프위의 점의 Option을 설정
<script type="text/javascript" src="/js/jqplot/jqplot.canvasAxisTickRenderer.js"/>
 -->

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
		$('#topmenu').topmenu({ d1: 8, d2: 1 });
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
		$("#dateSearch_td").text("ddddd") ;
		
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
				//defaultDate: "+1w",
				defaultDate: "",
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
				//defaultDate: "+1w",
				defaultDate: "",
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
		   arryStat.push(statValues);
		});
		
		$(function (){
		 	//X,Y 쌍으로 배열의 형태로 차례대로 값을 넣습니다.
		   //  var line =[[1,3],[2,7],[3,9],[4,1],[5,4],[6,6],[7,8],[8,2],[9,5]];
		     //id가 graphDiv인 곳에 그래프로 나타낼 Line을 넣어 표현한다.
		   //  var plot = $.jqplot('graphDiv', [line]);
		     
		    // var line =[['2013/12/25',20],['2013/12/26',22],['2013/12/27',11],['2013/12/28',32],
		    //            ['2013/12/29',41],['2013/12/30',2341]];
		       
		     var line =arryStat;
		     //var line1 =[['2013-01-25',815] , ['2013-01-26',115]];
		     
		     var  plot = $.jqplot('graphDiv', [line],{
		    	 		title:'일별 접속',
		                axes:{
		                  xaxis:{
		                       // 날짜 형태로 입력을 하기위해서는 Date형식의 Renderer을 사용합니다.
		                       renderer:$.jqplot.DateAxisRenderer,
		                       tickOptions:{ // 축에관한 옵션                    
		                           // 입력된 값이 날짜형태로 인식되기 위해서 format 형식을 정해주고 입력값도
		                           // yyyy/mm/dd 형식으로 입력해야만 정상적으로 나타납니다.
		                             formatString:'%y-%m-%d'
		                       }
		                      // ticks : ['2013/12/25','2013/12/26','2013/12/27','2013/12/28','2013/12/29','2013/12/30']
		                   },
		                   yaxis: {
		                       tickOptions: {
		                           formatString: '%.0f'
		                       }
		                   }
		                },
					     highlighter: {
					         sizeAdjust: 10,
					         tooltipLocation: 'n',
					         tooltipAxes: 'y',
					         tooltipFormatString: '<b><i><span style="color:red;">hello</span></i></b> %.0f',
					         useAxesFormatters: false
					     },
					     cursor: {
					         show: true
					     }
		     
		        });
		     
		      
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
							<select name="log_type" id="log_type" class="select_st ml8" title="검색조건선택" onchange="goSearch()">
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
									<%-- <option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.searchCnd1}">selected="selected"</c:if> >${fstBizplcList.code_name}</option> --%>
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
							<label for="work_divide" class="ml21"><strong class="label_tit">년/월/일</strong></label> 
							<select name="date_type" id="date_type" class="select_st ml8" title="검색조건선택" onchange="goSearch()">
								<option value="">전체</option>
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
								<c:out value="${box.date_type}" />
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
											<td colspan="6"><font style="font-family: 맑은 고딕; font-size: 12px">공지 데이터가 없습니다.</font></td>
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

