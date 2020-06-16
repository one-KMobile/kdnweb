<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<!-- progressbar -->
<link href="<c:url value='/css/kdn/com/cmm/bootstrap-progressbar-2.3.2.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/bootstrap-progressbar.js' />"></script>
<!-- progressbar -->
<!-- 차트  -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	var arryStat = new Array();
	$(document).ready(function(){		
		//$('#topmenu').topmenu({ d1: 3, d2: 2 }); 
		$('#topmenu').topmenu({ d1: "103000" , d2: "103002" }); 
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 중복 컬럼 제거
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		//removeDupCol("fstBizplcTd");
		//removeDupCol("scdBbizplcTd");
		//removeDupCol("fnctLcTd");
		//removeDupCol("cycleYmTd");
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var fst_bizplc_cd = "<c:url value='${box.fst_bizplc_cd}' />";
		var scd_bizplc_cd = "<c:url value='${box.scd_bizplc_cd}' />";
		var fnct_lc_no = "<c:url value='${box.fnct_lc_no}' />";

		if($.trim(fst_bizplc_cd) != ''){
			setScdBizplcList(fst_bizplc_cd);
			$("#scd_bizplc_cd").val(scd_bizplc_cd);
		}
		if($.trim(scd_bizplc_cd) != ''){
			setTracksList(scd_bizplc_cd);
			$("#fnct_lc_no").val(fnct_lc_no);
		}
		
		//그래프
		var tmpArray_0 = new Array("타이틀" ,"점검" ,"미점검" , { role: 'annotation' } );
		arryStat.push(tmpArray_0);
		$(":hidden[id ^=stat]").each(function(index){
			var hiddeValue = $(this).val();
			var statValues = hiddeValue.split(",");
			var statInsName= statValues[0] ;
			var statInsCount = statValues[1]*1;
			var statInsNotCount = statValues[2]*1-statInsCount;
			var tmpArray = new Array();
			tmpArray.push(statInsName ,statInsCount, statInsNotCount, '');
			arryStat.push(tmpArray);
		});
		drawChart();
		
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
		} 
		/*-- progressbar  */
		$('.bar').progressbar({display_text: 'center'}) ;
	});
	
	//중복 컬럼값 제거
	function removeDupCol(colname){
		var idStr = "";
		var idArrayList = new Array();
		$("[id ^= "+colname+"]").each(function(index){
			var id = $(this).attr("id");
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			if(orgIdStr != idStr){ 
				idArrayList.push(orgIdStr);
			}
			idStr = orgIdStr;
		});

		for(var i = 0; i < idArrayList.length; i++){  
			$("[id *= "+colname+"_"+ idArrayList[i] +"]").not(":eq(0)").empty();

		}
	} 
	
	function press(event){
		if (event.keyCode==13) {
			goSearch();
		}
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 상세 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goView(key, codeId){
		document.frm.schedule_id.value = key;
		
		switch (codeId) {
		case '001': case '025': case '024': /* 보통순시, 항공장애등등구확인점검, 항공장애등점검 */
			document.frm.action = "<c:url value='/kdn/admin/insResultView.do'/>";	
			break;
		case '021': /* 기별점검 */
			document.frm.action = "<c:url value='/kdn/admin/insResultGGView.do'/>";	
			break;
		case '022': /* 정밀점검 */
			document.frm.action = "<c:url value=''/>";	
			break;
		case '027': /* 전선접속 */
			document.frm.action = "<c:url value=''/>";	
			break;
		case '028': /* 접지저항 */
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
 		if ($.trim(document.frm.fst_bizplc_cd.value) == '') {
			alert("1차사업소를 선택해주세요.");
			$("#fst_bizplc_cd").focus();
			return;
		}
		if ($.trim(document.frm.scd_bizplc_cd.value) == '') {
			alert("2차사업소를 선택해주세요.");
			$("#scd_bizplc_cd").focus();
			return;
		}
		if ($.trim(document.frm.fnct_lc_no.value) == '') {
			alert("선로명을 선택해주세요.");
			$("#fnct_lc_no").focus();
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
		
		var YearStr = $("#basicYear").val();
		var MonthStr = $("#basicMonth").val();
		var cycle_ym = YearStr + MonthStr;  
		$("#cycle_ym").val(cycle_ym);
		document.frm.action = "<c:url value='/kdn/admin/insStatsList.do'/>";
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
	* 선로명 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setTracksList(scdBizplcCode){
		var data = getTracksList($("#fst_bizplc_cd").val(), scdBizplcCode);
		var addTracksList = "";
		if(data != null && data.tracksList != null && data.tracksList.length > 0){
			addTracksList +='<option value="" >선택하세요.</option>';
			for (var i = 0; i < data.tracksList.length; i++) {
				addTracksList +='<option value="' + data.tracksList[i].VALUE +'" >' + data.tracksList[i].NAME + '</option>';	
			}
		}else{
			addTracksList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#fnct_lc_no").empty();
		$("#fnct_lc_no").append(addTracksList);	
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
	* 선로명 목록 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getTracksList(fstBizplcCode, scdBizplcCode){
		var requestUrl = "/ajax/getTracksList.json"; 
		var queryParam = "fst_bizplc_cd=" + fstBizplcCode + "&scd_bizplc_cd=" + scdBizplcCode;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.tracksList); 
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
	* 차트
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	google.load("visualization", "1", {packages:["corechart"]});
	//google.setOnLoadCallback(drawChart);
	function drawChart(){
		
 		var data = google.visualization.arrayToDataTable(arryStat);
		var view = new google.visualization.DataView(data);
	   	view.setColumns([0, 1,
			            { calc: "stringify",
			              sourceColumn: 1,
			              type: "string",
			              role: "annotation"},
			            2,{ calc: "stringify",
			               sourceColumn: 2,
			               type: "string",
			               role: "annotation"}]); 
	    var options = {
	    	title: '순시결과 통계', 
	       	width: 600,
	        height: 440,
	        fontSize : 11,
	        fontName : 'Malgun Gothic',  
	        legend: { position: 'top', maxLines: 3 },
			bar: { groupWidth: '40' },
	        isStacked: true,
	        colors:['#ff0000','#cccccc'],
	        vAxis: {title: '순시 점검수',  titleTextStyle: {color: '#000000', fontName: 'Malgun Gothic', fontSize: 14}}, /* 세로축  */
	        hAxis: {title: '순시 종류', titleTextStyle: {color: '#000000', fontName: 'Malgun Gothic', fontSize: 14}}/* 가로축  */
	    };
	    var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
	    if('${totCnt}' =='0'){
	    	$("#columnchart_values").css("display","none");
	    }else{
	    	chart.draw(view, options);
	    }
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

/*  progressbar 세팅 */
Progressbar.defaults = {    
    transition_delay: 300,
    refresh_speed: 50,
    display_text: 'none',
    use_percentage: true,
    update: $.noop,
    done: $.noop,
    fail: $.noop
}
/* progressbar 높이 재조정(디폴트 너무 큼) */
.progress {
	margin-bottom: 0px	
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
			<!-- .container -->
	      	<div class="container">
			<!-- top -->
			<div class="top">
				<h3>송전설비검색</h3>
				<p class="navi">
					<strong>Home</strong> &gt; 순시결과 관리 &gt; <strong class="location">순시결과 통계</strong>
					</p>
				</div>
			<!-- // top -->
			<!-- mid -->
			<div class="mid" >
				<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/insStatsList.do'/>"> <!-- target="_hidden" -->
				<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
				<input name="cycle_ym" id="cycle_ym" type="hidden"	value="<c:out value='${box.cycle_ym}'/>" />
				<input name="schedule_id" id="schedule_id" type="hidden"	value="" />
				<ul>
					<li>
						<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
						
						<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>1차사업소</strong></label> 
						<select id="fst_bizplc_cd" name="fst_bizplc_cd" class="select_st2 ml27" onchange="setScdBizplcList(this.value);">
							<option value="" >선택하세요</option>
							<c:forEach var="fstBizplcList" items="${fstBizplcList}" varStatus="status">
								<option value="${fstBizplcList.code_value}" <c:if test="${fstBizplcList.code_value eq box.fst_bizplc_cd}">selected="selected"</c:if> >${fstBizplcList.code_name}</option>
							</c:forEach>
						</select> 
						
						<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>2차사업소</strong></label> 
						<select id="scd_bizplc_cd" name="scd_bizplc_cd" class="select_st2 ml27" onchange="setTracksList(this.value);">
							<option value="" > ← 선택하세요</option>
						</select> 
						
						<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>선로명</strong></label> 
						<select id="fnct_lc_no" name="fnct_lc_no" class="select_st2 ml27">
							<option value="" > ← 선택하세요</option>
						</select> 
					</li>
					
					<li> 
						<label for="work_divide" class="ml66" >
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
							<th>번호</th>
							<th>1차사업소</th>
							<th>2차사업소</th>
							<th>선로명</th>
							<th>순시월</th>
							<th>순시종류</th>
							<th>점검비율 <label for="work_divide" class="ml66" ></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${list}" varStatus="status">
							<input type="hidden" id="stat_${status.count}" value="${result.ins_name},${result.ins_count},${result.tower_count}">
							<tr>
								<td> 
									<c:out value="${status.count}" />
								</td>
								<td id="fstBizplcTd_${result.fst_bizplc_cd_nm}">
									<c:out value="${result.fst_bizplc_cd_nm}" />
								</td>
								<td id="scdBbizplcTd_${result.scd_bizplc_cd_nm}"> 
									<c:out value="${result.scd_bizplc_cd_nm}" />
								</td>
								<td id="fnctLcTd_${result.fnct_lc_dtls}">
									<span style="float:left;padding-left:10px;"><c:out value="${result.fnct_lc_dtls}" /></span>
								</td>
								<td id="cycleYmTd_${result.cycle_ym}">
									<c:if test="${not empty result.cycle_ym}">
										<c:out value="${fn:substring(result.cycle_ym,0,4)}" />년&nbsp;<c:out value="${fn:substring(result.cycle_ym,4,6)}" />월
									</c:if>
								</td>
								<td style="text-align: left;">
									<label for="work_divide" class="ml21" />
									<a style="color:#729ACF;text-decoration:underline;" href="javascript:goView('<c:out value="${result.schedule_id}" />','<c:out value="${result.code_id}"/>')"><c:out value="${result.ins_name}"/> (<c:out value="${result.ins_count}"/>/<c:out value="${result.tower_count}"/>)</a>
								</td>
								<td style="text-align: left;">
									<fmt:formatNumber var="num1" value="${result.ins_count}" pattern=".00"/>
									<fmt:formatNumber var="num2" value="${result.tower_count}" pattern=".00"/>
									<fmt:formatNumber var="num3" value="${(num1/num2)*100}" />
									<div class="progress progress-striped active">
									    <div class="bar bar-danger" role="progressbar" aria-valuetransitiongoal="<c:out value="${num3}" />  ">
									    </div>
									</div>
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
					<tbody>
					</tbody>
				</table>
				<div>&nbsp;<br>&nbsp;</div>
				<div  align="center" >
					<div id="columnchart_values" style="position: relative; display:block; border: 1px solid;width: 700px; height: 450px;"/>
				</div>
				</form>
				
			</div>
		<!-- // mid -->
		</div> 
       	<!-- end container -->  
	</div>
	<!-- bottom Area -->
	<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />  
		<!-- // bottom Area -->
    <!-- end wrap -->
</body>
</html>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->


