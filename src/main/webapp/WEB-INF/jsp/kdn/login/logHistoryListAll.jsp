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

<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>
<!-- 달력 관련 스크립트  -->
<link href="<c:url value='/css/kdn/com/cmm/jquery-ui.css' />" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.ui.datepicker-ko.js' />"></script>

<!-- 차트  -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">  
	$(document).ready(function () {
		$('#topmenu').topmenu({ d1: '108000', d2: '108001' });
		$("#s_category_code option:eq(3)").remove();
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var schFst_bizplc_cd= "<c:url value='${box.fst_bizplc_cd}' />";
		$("#fst_bizplc_cd > option[value = " + schFst_bizplc_cd + "]").attr("selected", "true");
		var log_type = "<c:url value='${box.log_type}' />";
		$("#log_type > option[value = " + log_type + "]").attr("selected", "true");
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 1차 사업소 검색한 경우는 2차 사업소 페이지에 나오게 하고 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		if($.trim(schFst_bizplc_cd) != ''){
			setScdBizplcList(schFst_bizplc_cd);
		}
		var schScd_bizplc_cd = "<c:url value='${box.scd_bizplc_cd}' />";
		$("#scd_bizplc_cd > option[value = " + schScd_bizplc_cd + "]").attr("selected", "true");
		
		/* removeDupCol("logTypeTd");
		removeDupCol("fstBizplcTd");
		removeDupCol("scdBizplcTd"); */
		
		$("[id ^= logTypeTd]").each(function(index){
			var id = $(this).attr("id");
  			var log_type_val = $('#'+id).text().trim();
  			if(log_type_val =="1"){
  				$('#'+id).text("APP");
  			}else if(log_type_val =="2"){
  				$('#'+id).text("WEB");
  			}else if(log_type_val =="3"){
  				$('#'+id).text("SYNC");
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
	 * 등록 페이지로 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goWrite() {
		document.frm.action = "<c:url value='/kdn/admin/noticeWrite.do'/>";
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch() {
		document.frm.action = "<c:url value='/kdn/admin/logHistoryListAll.do'/>";
		document.frm.pageIndex.value = "1" ;
		document.frm.submit();
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 체크박스 전체 선택
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function AllCheckbox(name, bln) {
		var obj = document.getElementsByName(name);
		if (obj == null)
			return;

		var max = obj.length;
		if (max == null) {
			obj.checked = bln;
		} else {
			for ( var i = 0; i < max; i++) {
				obj[i].checked = bln;
			}
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 선택한 대상 삭제
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSelectDel() {
		var chkLen = $("input[name=chk]:checked").length;

		if (chkLen <= 0) {
			alert("삭제할 대상을 지정해 주세요.");
			return;
		}

		if (confirm('선택한 대상을 삭제 하시겠습니까?')) {
			document.frm.action = "<c:url value='/kdn/admin/patrolman/delete.do'/>";
			document.frm.submit();
		}
	}

	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	 * 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) {
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/logHistoryListAll.do'/>";
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
		$('#' + id).datepicker("show");
	}
	
	//중복 컬럼값 제거
	/* function removeDupCol(colname){
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
	}  */
	
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
       	<!-- .container -->
       	<div class="container">
			<!-- top -->
			<div class="top">
				<h3>로그 목록</h3>
				<p class="navi">
					<strong>Home</strong> &gt; 로그 관리 &gt; <strong class="location">로그 목록</strong>
				</p>
			</div>
			<!-- // top -->
			<!-- mid -->
			<div class="mid" >
				<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/patrolman/List.do'/>">
				<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" /> 
 				<input name="log_idx" type="hidden" value="" />
					<ul>
						<li>
							<label for="constitutor"><strong class="label_tit">목록 검색</strong></label> 
							<label for="work_divide" class="ml21"><strong class="label_tit">접속타입</strong></label> 
							<select name="log_type" id="log_type" class="select_st ml8" title="검색조건선택">
								<option value="">전체</option>
								<c:forEach var="logTypeList" items="${logTypeList}" varStatus="status">
									<option value="${logTypeList.code_value}">${logTypeList.code_name}</option>
								</c:forEach>
							</select> 
							
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
							<label for="work_divide" class="ml21"><strong class="label_tit">검색어</strong></label> 
							<input name="searchWrd" type="text" size="20" placeholder="회원검색" value='<c:out value="${box.searchWrd}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml8"> 
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" /></a>
							
							<label for="work_divide" class="ml21"></label>
							<label for="work_divide" class="ml21"><strong class="label_tit"><font style="color:red;"></font>접속시간</strong></label> 
							<input name="startdate" id="startdate" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" value="${box.startdate}" readonly>
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('startdate')" style="cursor:pointer;">
							~
							<input name="enddate" id="enddate" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" value="${box.enddate}" readonly>
							<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('enddate')" style="cursor:pointer;">
				
						<%-- 	<label for="work_divide" class="ml42"><strong class="label_tit">검색구분</strong></label> 
							<select name="s_category_code" id="s_category_code" class="select_st ml8" title="검색조건선택">
								<option value="">전체</option>
								<c:forEach var="codeList" items="${codeList}" varStatus="status">
									<option value="${codeList.code_value}"
										<c:if test="${codeList.code_value eq box.s_category_code}">selected="selected"</c:if>>${codeList.code_name}</option>
								</c:forEach>
							</select>  --%>
						 </li>
					</ul>
					<table summary="공지 게시판입니다.">
						<caption>순시자 게시판</caption>
						<thead>
							<tr>
								<th>번호</th>
								<th>접속타입</th>
								<th>1차 사업소</th>
								<th>2차 사업소</th>
								<th>이름(ID)</th>
								<th>접속시간</th>
								<th>비고란</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td><c:out	value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" /></td>
									<td id="logTypeTd_${result.log_type}" >
										<c:choose>
											<c:when test="${result.log_type eq 1}">APP</c:when>
											<c:when test="${result.log_type eq 2}">WEB</c:when>
											<c:when test="${result.log_type eq 3}">SYNC</c:when>
										</c:choose>
									</td>
									<td id="fstBizplcTd_${result.fst_bizplc_cd_nm}" ><c:out value="${result.fst_bizplc_cd_nm}" /></td>
									<td id="scdBizplcTd_${result.scd_bizplc_cd_nm}" ><c:out value="${result.scd_bizplc_cd_nm}" /></td>
									
									<td align="center"><a href="#" style= "color: #729ACF; text-decoration: underline;" onclick="goView('${result.log_idx}');"><c:out value="${result.user_name}" />(<c:out value="${result.user_id}" />)</a></td>
									<td><c:out value="${result.reg_date}" /></td>
									<td>
										<c:choose>
											<c:when test="${fn:length(result.remarks) gt 10}">
												<c:out value="${fn:substring(result.remarks,0,10)}" />......
											</c:when>
											<c:otherwise>
												<c:out value="${result.remarks}" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
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
					<!-- // paging -->
				</form>
			</div>
		<!-- // mid -->
		</div> 
      	<!-- end container -->   
    </form> 
  </div>
  <!-- end wrap -->
    <!-- bottom Area -->
	<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
	<!-- // bottom Area --> 
</body>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
</html>

