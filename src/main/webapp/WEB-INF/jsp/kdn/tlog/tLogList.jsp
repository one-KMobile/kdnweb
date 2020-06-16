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
<script type="text/javascript">  
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: '108000', d2: '108004' });
		
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
			$("#tableDiv").css("display","block");     
		}else{
			$("#dataNullView").css("display","block");
			$("#dateViewStr").text("검색정보를 선택하세요."); 
		}
		
		$("td").each(function(index){
			var chk = $(this).text();
			
			if($.trim(chk) == "O"){   
				$(this).text(""); 
				$(this).css({"background":"blue", "opacity":"0.9"}); 
			}else if(chk == ""){ 
				$(this).css({"background":"gray", "opacity":"0.1"});  
			}
		});
 		
	});
	
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
		
		var YearStr = $("#basicYear").val();
		var MonthStr = $("#basicMonth").val();
		var lastDay = ( new Date( YearStr, MonthStr, 0) ).getDate();
		
		var startDate = YearStr + "-" + MonthStr + "-01" ;
		var endDate = YearStr + "-" + MonthStr + "-" + lastDay;
		
		$("#startDate").val(startDate);
		$("#endDate").val(endDate);
		
		if(parseInt(lastDay) > 28 && parseInt(lastDay) <= 29){
			$("#lastDayType").val("1");
		}else if(parseInt(lastDay) > 28 && parseInt(lastDay) <= 30){
			$("#lastDayType").val("2");
		}else if(parseInt(lastDay) > 28 && parseInt(lastDay) <= 31){
			$("#lastDayType").val("3");
		}
		
		document.frm.action = "<c:url value='/kdn/tlog/getTLogDataList.do'/>";
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
	
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/tlog/getTLogDataList.do'/>">
			<input name="lastDayType" id="lastDayType" type="hidden"	value="<c:out value='${box.lastDayType}'/>" />
			<input name="startDate" id="startDate" type="hidden"	value="<c:out value='${box.startDate}'/>" />
			<input name="endDate" id="endDate" type="hidden"	value="<c:out value='${box.endDate}'/>" />
			
			<!-- container --> 
			<div class="container">
				<!-- top -->
				<div class="top">
					<h3>순시자 접속 월별 로그표 (App)</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 로그 관리 &gt; <strong class="location">순시자 접속 월별 로그표 (App)</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid">
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label>
							<%--  
							<label for="work_divide" class="ml66"><strong class="label_tit">접속타입</strong></label> 
							<select name="log_type" id="log_type" class="select_st ml8" title="검색조건선택">
								<option value="">전체</option>
								<c:forEach var="logTypeList" items="${logTypeList}" varStatus="status">
									<option value="${logTypeList.code_value}">${logTypeList.code_name}</option>
								</c:forEach>
							</select> --%> 
							
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
							
							<span style="float:right;padding-right:10px;"><font style="color:blue;opacity:0.9">■</font> 접속함 <font style="color:gray;opacity:0.2">■</font> 접속하지 않음 <font style="font-weight:bold; ">(App)</font></span>
						 </li>
					</ul>
					<div id="tableDiv"  style="width:100%;height:550px; overflow:auto;overflow-x:hidden;display:none;"> 
						<table summary="공지 게시판입니다." >    
							<caption>순시자 게시판</caption>
							<thead>
								<tr>
									<th style="width: 5%;">번호</th>    
									<th style="width: 10%;">순시자</th>
									<th style="width: 15px;">1</th>
									<th style="width: 15px;">2</th>
									<th style="width: 15px;">3</th>
									<th style="width: 15px;">4</th>
									<th style="width: 15px;">5</th>
									<th style="width: 15px;">6</th>
									<th style="width: 15px;">7</th>
									<th style="width: 15px;">8</th>
									<th style="width: 15px;">9</th>
									<th style="width: 15px;">10</th>
									<th style="width: 15px;">11</th>
									<th style="width: 15px;">12</th>
									<th style="width: 15px;">13</th>
									<th style="width: 15px;">14</th>
									<th style="width: 15px;">15</th>
									<th style="width: 15px;">16</th>
									<th style="width: 15px;">17</th>
									<th style="width: 15px;">18</th>
									<th style="width: 15px;">19</th>
									<th style="width: 15px;">20</th>
									<th style="width: 15px;">21</th>
									<th style="width: 15px;">22</th>
									<th style="width: 15px;">23</th>
									<th style="width: 15px;">24</th>
									<th style="width: 15px;">25</th>
									<th style="width: 15px;">26</th>
									<th style="width: 15px;">27</th>
									<th style="width: 15px;">28</th> 
									<c:if test="${not empty box.lastDayType}">
										<c:choose>
											<c:when test="${box.lastDayType eq '1' }">
												<th style="width: 15px;">29</th>
											</c:when>
											<c:when test="${box.lastDayType eq '2' }">
												<th style="width: 15px;">29</th>
												<th style="width: 15px;">30</th>
											</c:when>
											<c:when test="${box.lastDayType eq '3' }">
												<th style="width: 15px;">29</th>
												<th style="width: 15px;">30</th>
												<th style="width: 15px;">31</th>		
											</c:when>
										</c:choose>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="result" items="${list}" varStatus="status">
									<tr>
										<td style="border: 1px solid #eaeaea;"><c:out value="${status.count}" /></td> 
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.user_id}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_1}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_2}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_3}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_4}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_5}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_6}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_7}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_8}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_9}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_10}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_11}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_12}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_13}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_14}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_15}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_16}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_17}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_18}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_19}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_21}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_22}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_23}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_24}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_25}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_26}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_27}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_28}" /></td>
										<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_29}" /></td>
										<c:if test="${not empty box.lastDayType}">
											<c:choose>
												<c:when test="${box.lastDayType eq '1' }">
													<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_29}" /></td>
												</c:when>
												<c:when test="${box.lastDayType eq '2' }">
													<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_29}" /></td>
													<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_30}" /></td>
												</c:when>
												<c:when test="${box.lastDayType eq '3' }">
													<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_29}" /></td>
													<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_30}" /></td>
													<td style="border: 1px solid #eaeaea;"><c:out value="${result.value_31}" /></td>		
												</c:when>  
											</c:choose>
										</c:if>
									</tr>
								</c:forEach>
								<c:if test="${fn:length(list) == 0}">
									<c:choose>
										<c:when test="${not empty box.searchCnd1}">
											<tr>
												<c:if test="${box.lastDayType eq '1' }">
													<td colspan="31"><font style="font-family: 맑은 고딕; font-size: 12px">검색하신 순시자 접속 데이터가 없습니다.</font></td>
												</c:if>
												<c:if test="${box.lastDayType eq '2' }">
													<td colspan="32"><font style="font-family: 맑은 고딕; font-size: 12px">검색하신 순시자 접속 데이터가 없습니다.</font></td>
												</c:if>
												<c:if test="${box.lastDayType eq '3' }">
													<td colspan="33"><font style="font-family: 맑은 고딕; font-size: 12px">검색하신 순시자 접속 데이터가 없습니다.</font></td>
												</c:if>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<c:if test="${box.lastDayType eq '1' }">
													<td colspan="31"><font style="font-family: 맑은 고딕; font-size: 12px">순시자 접속 데이터가 없습니다.</font></td>
												</c:if>
												<c:if test="${box.lastDayType eq '2' }">
													<td colspan="32"><font style="font-family: 맑은 고딕; font-size: 12px">순시자 접속 데이터가 없습니다.</font></td>
												</c:if>
												<c:if test="${box.lastDayType eq '3' }">
													<td colspan="33"><font style="font-family: 맑은 고딕; font-size: 12px">순시자 접속 데이터가 없습니다.</font></td>
												</c:if>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:if>
							</tbody>
						</table>
					</div>
					<div id="dataNullView" style="text-align: center;padding-top: 5%;font-size: 15px;font-weight: bold;display:none;">   
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
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

