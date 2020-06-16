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
<%-- <script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.topmenu.js' />"></script> --%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<!-- 차트  -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">  
	$(document).ready(function () {
		//$('#topmenu').topmenu({ d1: 8, d2: 2 });
		$('#topmenu').topmenu({ d1: "108000", d2: "108002" });
		
		thisYear(); //초기에 년 셋팅
		thisMonth(); //초기에 월 셋팅
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var searchCnd1 = "<c:url value='${box.searchCnd1}' />";
		var searchCnd2 = "<c:url value='${box.searchCnd2}' />";
		var basicYear = "<c:url value='${box.basicYear}' />";
		var basicMonth = "<c:url value='${box.basicMonth}' />";
		
		if($.trim(searchCnd1) != ''){
			setScdBizplcList(searchCnd1);
			$("#searchCnd2").val(searchCnd2);
		}
		if($.trim(basicYear) != ''){
			$("#basicYear").val(basicYear);
		}
		if($.trim(basicMonth) != ''){     
			$("#basicMonth").val(basicMonth);   
		} 
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 차트 닫기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		$("#chart_x").unbind("click").bind("click", function(){
			$("#chart_div").css("display","none");
			$("#chart_x").css("display","none");
		});
	});
	
	function press(event) {
		if (event.keyCode == 13) {
			goSearch();
		}
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
		$("#searchCnd2").empty();
		$("#searchCnd2").append(addScdBizplcList);
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
	 * 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch() {
		if ($.trim(document.frm.searchCnd1.value) == '') {
			alert("1차사업소를 선택해주세요.");
			$("#searchCnd1").focus();
			return;
		}
		if ($.trim(document.frm.searchCnd2.value) == '') {
			alert("2차사업소를 선택해주세요.");
			$("#searchCnd2").focus();
			return;
		}
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
		dataCall(); 
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
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 차트
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    var jsonData = "";
    function dataCall(){
    	$("#chart_div").empty();  
    	$("#dataNullView").css("display","none");
    	$("#chart_div").css("display","block"); 
		$("#chart_x").css("display","block");  
    	jsonData = getLogDataAjax();
    	drawChart(); 
    }  
     
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    
    function drawChart() {
		var data = new google.visualization.DataTable(); 
		data.addColumn('string', 'Task'); 
		data.addColumn('number', '접속수');
		data.addColumn({type:'string', role:'annotation'});
		data.addColumn({type:'string', role:'style'}); 
		
		/* var fruits = [
		    "Orange", "Apple", "Mango" ,"#76A7FA", "#703593", "#871B47", "gray" 
		]; */ 
		
		if(jsonData != null && jsonData.getLogDataList != null && jsonData.getLogDataList.length > 0){
			data.addRows(jsonData.getLogDataList.length);  	
			var dateViewStr = "";
			var dataCount = 0; 
			for ( var i = 0; i < jsonData.getLogDataList.length; i++) {
				if(jsonData.getLogDataList[i].REG_DATE != null && jsonData.getLogDataList[i].REG_DATE !="" && jsonData.getLogDataList[i].REG_DATE.length > 0){
					dataCount ++;	
					dateViewStr = "";
					dateViewStr = jsonData.getLogDataList[i].REG_DATE;
					dateViewStr = dateViewStr.substring(0, dateViewStr.indexOf("-")) + "년 " + dateViewStr.substring(dateViewStr.indexOf("-") + 1, dateViewStr.length) + "월"; 
				}
				data.setCell(i, 0, $.trim(jsonData.getLogDataList[i].USER_NAME));  
				data.setCell(i, 1, parseInt(jsonData.getLogDataList[i].LOG_CONN_CNT));
				if(parseInt(jsonData.getLogDataList[i].LOG_CONN_CNT) > 0){
					data.setCell(i, 2, $.trim(jsonData.getLogDataList[i].LOG_CONN_CNT));	
				}else{
					data.setCell(i, 2, ""); 
				}
				/* data.setCell(i, 3, fruits[i]); */   
			}
			
			if(dataCount > 0){   
				var options = {
				        title: dateViewStr + ' 로그접속통계',
				        width: 950,   
				    	height: 450, 
				    	fontSize : 12, 
				    	fontName : 'Malgun Gothic',    
				    	/* bar: {groupWidth: "35%"},  */ 
			    		/* legend : 'bottom', */  
			    		annotations: {
						    textStyle: {
						      fontName: 'Malgun Gothic',
						      fontSize: 11,
						      /* bold: true, */ //굵기
						      //italic: true,   	//약간기울림체
						      rx: 10,                   // x-radius of the corner curvature.
						      ry: 10,					// y-radius of the corner curvature.  
						      color: '#000000',     // The color of the text.  
						      auraColor: '#0000000', // The color of the text outline.     
						      opacity: 0.8          // The transparency of the text.  
						    }
						  }, 
				        vAxis: {title: '로그접속수',  titleTextStyle: {color: '#000000', fontName: 'Malgun Gothic', fontSize: 14}}, /* 세로축  */    
				        hAxis: {title: '순시자', titleTextStyle: {color: '#000000', fontName: 'Malgun Gothic', fontSize: 14}}/* 가로축  */
			      	  }; 
			        
				  /* ColumnChart : 세로 막대 / BarChart  : 가로 막대 / LineChart  : 라인차트 / PieChart  : 원형차트 */    
			      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
			      chart.draw(data, options);   
			}else{
				var YearStr = $("#basicYear").val();
				var MonthStr = $("#basicMonth").val(); 
				
				$("#chart_div").css("display","none"); 
				$("#chart_x").css("display","none");
				$("#dataNullView").css("display","block");
				$("#dateViewStr").text(YearStr + "년 " + MonthStr + "월의 순시접속로그가 없습니다.");      
			}
    	}else{
    		if ($.trim(document.frm.searchCnd1.value) != '' && $.trim(document.frm.searchCnd2.value) != '') {
    			var YearStr = $("#basicYear").val();
    			var MonthStr = $("#basicMonth").val(); 
    			
    			$("#chart_div").css("display","none");   
    			$("#chart_x").css("display","none"); 
    			$("#dataNullView").css("display","block");
    			$("#dateViewStr").text(YearStr + "년 " + MonthStr + "월의 순시접속로그가 없습니다.");
    		}else{  
    			$("#chart_div").css("display","none");  
    			$("#chart_x").css("display","none"); 
    			$("#dataNullView").css("display","block");
    			$("#dateViewStr").text("검색정보를 선택하세요."); 	   			
    		}	
    	} 
    }
    
    function getLogDataAjax(){
    	var YearStr = $("#basicYear").val();
		var MonthStr = $("#basicMonth").val(); 
    	var lastDay = ( new Date( parseInt(YearStr), parseInt(MonthStr), 0) ).getDate();
    	
    	var startDate = YearStr + MonthStr + "01";
    	var endDate = YearStr + MonthStr + lastDay; 
    	
    	var requestUrl = "/ajax/chart/getLogData.json"; 
    	var queryParam = "fst_bizplc_cd=" + $("#searchCnd1 option:selected").val() + "&scd_bizplc_cd=" + $("#searchCnd2 option:selected").val() + "&startDate=" + startDate + "&endDate=" + endDate;
    	
    	var jsonData = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.getLogDataList); 
				if(data.code == '001'){
					jsonData = data;
				}else if(data.code == '002'){ 
					
				}
			},
		    error:function(XMLHttpRequest,textStatus,errorThrown){
		    	alert("error");
			}
		});
		return jsonData;
    }
</script>
<title>게시판 목록조회</title>
</head>
<body>
	<noscript class="noScriptTitle">자바스크립트를 지원하지 않는 브라우저에서는 일부	기능을 사용하실 수 없습니다.</noscript>
	
	<!-- wrap -->
	<div id="wrap">
    	
		<!-- Left Area --> 
		<c:import url="/kdn/layout/leftMenu.do" charEncoding="utf-8" />
		<!-- // Left Area -->
		 
		 <!-- Top Area -->
		<c:import url="/kdn/layout/topMenu.do" charEncoding="utf-8" />
		<!-- // Top Area --> 
	
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/patrolman/List.do'/>">
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" /> 
 			<input name="log_idx" type="hidden" value="" />

			<!-- container -->
			<div class="container">
				<!-- top -->
				<div class="top">
					<h3>로그 접속 월별 통계 (App)</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 로그 관리 &gt; <strong class="location">로그 접속 월별 통계 (App)</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid">
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>1차사업소</strong></label> 
							<select id="searchCnd1" name="searchCnd1" class="select_st2 ml27" onchange="setScdBizplcList(this.value);">
								<option value="" >선택하세요</option>
								<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
									<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.searchCnd1}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
								</c:forEach>
							</select>  
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>2차사업소</strong></label> 
							<select id="searchCnd2" name="searchCnd2" class="select_st2 ml27" >
								<option value="" > ← 선택하세요</option>
							</select> 
						</li>
						<li>
							<label for="work_divide" class="ml127" ><strong class="label_tit"><font style="color:red;">* </font>순시년도</strong></label>            
							<select id="basicYear" name="basicYear" class="select_st ml27" onchange="addDateOption();" >     
							</select>  
							<label for="work_divide" class="ml66" ><strong class="label_tit"><font style="color:red;">* </font>순시월</strong></label>
							<select id="basicMonth" name="basicMonth" class="select_st ml27"  > 
							</select>
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" style="padding-left: 10PX;"/></a> 
						 </li>
					</ul>  
						<div id="chart_x" style="font-weight: bold;position: absolute;top:110px;left:5%;z-index: 9999999;font-size: 21px;display:none;cursor:pointer;">ⓧ</div>
						<div id="chart_div" style="width: 950px; height: 450px;z-index: 9999;border: 1px solid;position:relative;top: 50px;left:4%;display:none;">  
						</div>
						<div id="dataNullView"style="text-align: center;padding-top: 5%;font-size: 15px;font-weight: bold;display:none;"> 
							<font id="dateViewStr"></font>    
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
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

