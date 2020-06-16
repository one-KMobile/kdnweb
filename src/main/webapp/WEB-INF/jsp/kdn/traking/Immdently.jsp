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
<%-- <link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css"> --%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>

<!-- 네이버 지도 API -->
<!-- 지도 키를 발급 받을 때는 실행 될 수 있는 기본적인 URL만 입력하면 된다. 예) localhost:8080/kdnweb -->
<!-- 운영서버 키 -->
 <script type="text/javascript" src="http://openapi.map.naver.com/openapi/naverMap.naver?ver=2.0&key=c18aa4beadb9c5424eb889800108ffe5"></script>
<!-- 로컬서버 키 -->
<!-- script type="text/javascript" src="http://openapi.map.naver.com/openapi/naverMap.naver?ver=2.0&key=8a1e31023df7df51274641b9ae73ac5d"></script> -->

<script type="text/javascript">
	$(document).ready(function(){		
		//$('#topmenu').topmenu({ d1: 7, d2: 1 });  
		$('#topmenu').topmenu({ d1: "107000", d2: "107001" });  
	});

	function press(event) {
		if (event.keyCode==13) {
			goSearch();
		}
	}
	
	$(document).ready(function () {
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 리스트 선로명 기준으로 한번만 보여주기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var idStr = "";
		var idArrayList = new Array();
		$("[id ^= tracksTd]").each(function(index){
			var id = $(this).attr("id");
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			if(orgIdStr != idStr){ 
				idArrayList.push(orgIdStr); 
			}
			idStr = orgIdStr;
		});
		
		for(var i = 0; i < idArrayList.length; i++){  
			$("[id *= tracksTd_"+ idArrayList[i] +"]").not(":eq(0)").empty();
		}
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var fst_bizplc_cd = "<c:url value='${box.fst_bizplc_cd}' />";
		var scd_bizplc_cd = "<c:url value='${box.scd_bizplc_cd}' />";
		var user_id = "<c:url value='${box.user_id}' />";
		var reg_date = "<c:url value='${box.traking_date}' />";

		if($.trim(fst_bizplc_cd) != ''){
			setScdBizplcList(fst_bizplc_cd);
			$("#scd_bizplc_cd").val(scd_bizplc_cd);
		}
		if($.trim(scd_bizplc_cd) != ''){
			setTrakingList(scd_bizplc_cd);
			$("#user_id").val(user_id);
		}
		
		if($.trim(user_id) != ''){
			setTrakingUserList(reg_date);
			$("#traking_date").val(reg_date);
		}

	});
	
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
		if ($.trim(document.frm.user_id.value) == '') {
			alert("순시자를 선택해주세요.");
			$("#user_id").focus();
			return;
		}
		
		/* String serverIp = request.getServerName();
		String serverPort = Integer.toString(request.getServerPort());
		alert(serverIp);
		alert(serverPort); */
		
		/* if ($.trim(document.frm.traking_date.value) == '') {
			alert("순시자를 선택해주세요.");
			$("#traking_date").focus();
			return;
		} */
		document.frm.action = "<c:url value='/kdn/traking/user/Immdently.do'/>";
		document.frm.submit();
		
		setTrakingUserList($.trim(document.frm.traking_date.value));
/* 	 	var latitude = ${viewBox.latitude};
		var longitude = ${viewBox.longitude};
		var user_name = ${viewBox.user_name};
		goMap(latitude, longitude, user_name); */
	}
	
	function choiceMap() {
		if($.trim(document.frm.scd_bizplc_cd.value) != "") {
			setTrakingList($("#scd_bizplc_cd").val());
		}else if($.trim(document.frm.user_id.value) != "") {
			setTrakingInfo($("#user_id").val());
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
		$("#scd_bizplc_cd").empty();
		$("#scd_bizplc_cd").append(addScdBizplcList);
		
	} 
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 트래킹 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setTrakingList(scdBizplcCode){
		var data = getTrakingList($("#fst_bizplc_cd").val(), scdBizplcCode);
		if(data.trakingList.length == 0) {
			alert("2차 사업소의 순시트래킹 이력이 없습니다. 2차 사업소를 다시 선택해주세요"); 
			var mapStr =""; 
			mapStr += "<tr>";
			mapStr += "<td>목록 검색을 다시 선택해주세요</td>";
			mapStr += "</tr>";
			document.getElementById("tracksTable").innerHTML = mapStr;
		} else {
		var addTrakingList = "";
		var mapStr = "";
		var arrayUsername = new Array();
		var arrayLatitude = new Array();
		var arrayLongitude = new Array();
		var arrayRegdate = new Array();
		if(data != null && data.trakingList != null && data.trakingList.length > 0){

			addTrakingList +='<option value="" >선택하세요.</option>';
			for (var i=0; i < data.trakingList.length; i++) {
				addTrakingList +='<option value="' + data.trakingList[i].user_id +'" >' + data.trakingList[i].user_name + '</option>';
				arrayUsername[i] = data.trakingList[i].user_name;
				arrayLatitude[i] = data.trakingList[i].latitude;
				arrayLongitude[i] = data.trakingList[i].longitude;
				arrayRegdate[i] = data.trakingList[i].reg_date;
			}
		}else{
			addTrakingList +='<option value="" >데이터가 없습니다.</option>';			
		}
		
		$("#user_id").empty();
		$("#user_id").append(addTrakingList);	
		$("#search").remove();	 

		mapStr += "<a href='javascript:goMapList(\""+arrayLatitude+"\",\""+arrayLongitude+"\",\""+arrayUsername+"\",\""+arrayRegdate+"\");' target='_self'><img src='<c:url value='/images/kdn/common/btn_search.gif' />' alt='검색' style='padding-left: 10PX;'/></a>";
		document.getElementById("mapDiv").innerHTML = mapStr;
		}
	}
	
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 트래킹 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
 	function setTrakingInfo(userId){
		if(userId == "") {
			var data = getTrakingList($("#fst_bizplc_cd").val(), $("#scd_bizplc_cd").val());
			if(data.trakingList.length == 0) {
				alert("순시자의 순시트래킹 이력이 없습니다. 순시자를 다시 선택해주세요"); 
				var mapStr =""; 
				mapStr += "<tr>";
				mapStr += "<td>목록 검색을 다시 선택해주세요</td>";
				mapStr += "</tr>";
				document.getElementById("tracksTable").innerHTML = mapStr;
			} else {
				var addTrakingList = "";
				var mapStr = "";
				var arrayUsername = new Array();
				var arrayLatitude = new Array();
				var arrayLongitude = new Array();
				var arrayRegdate = new Array();
				if(data != null && data.trakingList != null && data.trakingList.length > 0){
	
					addTrakingList +='<option value="" >전체순시자</option>';
					for (var i=0; i < data.trakingList.length; i++) {
						addTrakingList +='<option value="' + data.trakingList[i].user_id +'" >' + data.trakingList[i].user_name + '</option>';
						arrayUsername[i] = data.trakingList[i].user_name;
						arrayLatitude[i] = data.trakingList[i].latitude;
						arrayLongitude[i] = data.trakingList[i].longitude;
						arrayRegdate[i] = data.trakingList[i].reg_date;
					}

				}else{
					addTrakingList +='<option value="" >데이터가 없습니다.</option>';			
				}
			
				$("#user_id").empty();
				$("#user_id").append(addTrakingList);	
				$("#search").remove();				 
				mapStr += "<a href='javascript:goMapList(\""+arrayLatitude+"\",\""+arrayLongitude+"\",\""+arrayUsername+"\",\""+arrayRegdate+"\");' target='_self'><img src='<c:url value='/images/kdn/common/btn_search.gif' />' alt='검색' style='padding-left: 10PX;'/></a>";
				document.getElementById("mapDiv").innerHTML = mapStr;
			}
		}else {
			var data = getTrakingInfo($("#fst_bizplc_cd").val(), $("#scd_bizplc_cd").val(), userId);
			if(data.trakingInfo.length == 0) {
				alert("순시자의 순시트래킹 이력이 없습니다. 순시자를 다시 선택해주세요"); 
				var mapStr =""; 
				mapStr += "<tr>";
				mapStr += "<td>목록 검색을 다시 선택해주세요</td>";
				mapStr += "</tr>";
				document.getElementById("tracksTable").innerHTML = mapStr;
			} else {
				$("#user_name").val(data.trakingInfo.user_name);
				$("#latitude").val(data.trakingInfo.latitude);
				$("#longitude").val(data.trakingInfo.longitude);
				$("#remarks").val(data.trakingInfo.remarks);
				var user_name = data.trakingInfo.user_name;
				user_name = String(user_name);
				var str = "";
				//\" -> "로 인식한다.
				//\\ -> \로 인식한다.
				$("#search").remove();
				str += "<a href='javascript:goMap("+data.trakingInfo.latitude+","+data.trakingInfo.longitude+",\""+user_name+"\",\""+data.trakingInfo.reg_date+"\");' target='_self'><img src='<c:url value='/images/kdn/common/btn_search.gif' />' alt='검색' style='padding-left: 10PX;'/></a>";
				
				document.getElementById("mapDiv").innerHTML = str;
			}
		}
		
	} 
	
 	function setTrakingUserList(traking_date){

		 var data = getTrakingByUserList($("#user_id").val(), $("#fst_bizplc_cd").val(), $("#scd_bizplc_cd").val(), traking_date);
		
		var mapStr = "";
		var arrayUsername = new Array();
		var arrayLatitude = new Array();
		var arrayLongitude = new Array();
		var arrayRegdate = new Array();
		if(data != null && data.trakingUserList != null && data.trakingUserList.length > 0){

			for (var i=0; i < data.trakingUserList.length; i++) {
				arrayUsername[i] = data.trakingUserList[i].RN;
				arrayLatitude[i] = data.trakingUserList[i].LATITUDE;
				arrayLongitude[i] = data.trakingUserList[i].LONGITUDE;
				arrayRegdate[i] = data.trakingUserList[i].REG_DATE;
			}
		} 
		
		//$("#user_id").empty();
		$("#traking").remove();
		mapStr += "<input type='button' id='traking' name='traking' value='순시자트래킹' title='순시자트래킹' onclick='javascript:goMapList(\""+arrayLatitude+"\",\""+arrayLongitude+"\",\""+arrayUsername+"\",\""+arrayRegdate+"\");' class='button'></a>";
		
		//mapStr += "<a href='javascript:goMapList(\""+arrayLatitude+"\",\""+arrayLongitude+"\",\""+arrayUsername+"\",\""+arrayRegdate+"\");' target='_self'><img src='<c:url value='/images/kdn/common/btn_search.gif' />' id='img1' onClick='setTrakingUserList(\"" +traking_date+"\");' alt='검색' style='padding-left: 10PX;'/></a>";
		
		document.getElementById("mapDiv").innerHTML = mapStr;
		
	}
 	
 /* 	function setTrakingUserList(traking_date){

		var data = getTrakingByUserList($("#user_id").val(), $("#fst_bizplc_cd").val(), $("#scd_bizplc_cd").val(), traking_date);
		
		var mapStr = "";
		var arrayUsername = new Array();
		var arrayLatitude = new Array();
		var arrayLongitude = new Array();
		var arrayRegdate = new Array();
		if(data != null && data.trakingUserList != null && data.trakingUserList.length > 0){

			for (var i=0; i < data.trakingUserList.length; i++) {
				arrayUsername[i] = data.trakingUserList[i].RN;
				arrayLatitude[i] = data.trakingUserList[i].LATITUDE;
				arrayLongitude[i] = data.trakingUserList[i].LONGITUDE;
				arrayRegdate[i] = data.trakingUserList[i].REG_DATE;
			}
		}
		
		//$("#user_id").empty();
		mapStr += "<a href='javascript:goMapList(\""+arrayLatitude+"\",\""+arrayLongitude+"\",\""+arrayUsername+"\",\""+arrayRegdate+"\");' target='_self'><img src='<c:url value='/images/kdn/common/btn_search.gif' />' id='img1' onClick='setTrakingUserList(\"" +traking_date+"\");' alt='검색' style='padding-left: 10PX;'/></a>";
		
		document.getElementById("mapDiv").innerHTML = mapStr;
		
	} */
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
	* 트래킹 목록 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getTrakingList(fstBizplcCode, scdBizplcCode){
		var requestUrl = "/ajax/get/traking/user/List.json"; 
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
				console.log(data.trakingList); 
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
	* 트래킹 정보 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
 	function getTrakingInfo(fstBizplcCd, scdBizplcCd, userId){
		var requestUrl = "/ajax/get/traking/user/Info.json"; 
		var queryParam = "fst_bizplc_cd="+fstBizplcCd+ "&scd_bizplc_cd="+scdBizplcCd+"&user_id=" + userId;
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.trakingInfo); 
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
	* 선택된 순시자 트래킹 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
 	function getTrakingByUserList(userId, fstBizplcCode, scdBizplcCode, traking_date){
		var requestUrl = "/ajax/get/traking/by/user/List.json"; 
		var queryParam = "user_id=" + userId + "&fst_bizplc_cd=" + fstBizplcCode + "&scd_bizplc_cd=" + scdBizplcCode + "&traking_date=" + traking_date;
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.trakingUserList); 
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
	* 네이버 지도 API(리스트)
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	
	function naverMapApiList(latitude, longitude, user_name, reg_date){
		var latitudeStr = latitude + "";
		var longitudeStr = longitude + "";
		latitudeStr = latitudeStr.substring(0, latitudeStr.indexOf(".")+8); 
		longitudeStr = longitudeStr.substring(0, longitudeStr.indexOf(".")+8);

		//var oTowerPoint = new nhn.api.map.LatLng(37.3346369,126.9236806);
		var oTowerPoint = new nhn.api.map.LatLng(latitudeStr, longitudeStr);  
		
		var oTrakingPoint = new Array();

		var latitudeArray = new Array();
		var longitudeArray = new Array();
		var usernameArray = new Array();
		var regdateArray = new Array();
			
		latitudeArray = latitude.split(",");
		longitudeArray = longitude.split(",");
		usernameArray = user_name.split(",");
		regdateArray = reg_date.split(",");	
	
		for(var i=0; i<latitudeArray.length; i++) {
			oTrakingPoint[i] = new nhn.api.map.LatLng(latitudeArray[i], longitudeArray[i]);	
		}
		
		var defaultLevel = 10;
		var oMap = new nhn.api.map.Map(document.getElementById('map'), { 
						point : oTowerPoint,
						zoom : defaultLevel,
						//복수 마커를 사용 할 경우 boundary는 마커의 갯수를 설정
						boundary : oTrakingPoint,
						enableWheelZoom : true,
						enableDragPan : true,
						enableDblClickZoom : true,
						mapMode : 0,
						activateTrafficMap : false,
						activateBicycleMap : false,
						detectCoveredMarker : true,
						minMaxLevel : [ 1, 14 ],
						size : new nhn.api.map.Size(1032, 775)		});  
		// 줌 컨트롤러
		var oSlider = new nhn.api.map.ZoomControl();
		oMap.addControl(oSlider);
		oSlider.setPosition({
			top : 10,
			left : 10
		});

		var oMapTypeBtn = new nhn.api.map.MapTypeBtn();
		oMap.addControl(oMapTypeBtn);
		oMapTypeBtn.setPosition({
			bottom : 10,
			right : 80
		});

		var oThemeMapBtn = new nhn.api.map.ThemeMapBtn();
		oThemeMapBtn.setPosition({
			bottom : 10,
			right : 10
		});
		oMap.addControl(oThemeMapBtn);
		
		/*
		var oBicycleGuide = new nhn.api.map.BicycleGuide(); // - 자전거 범례 선언
		oBicycleGuide.setPosition({
			top : 10,
			right : 10
		}); // - 자전거 범례 위치 지정
		oMap.addControl(oBicycleGuide);// - 자전거 범례를 지도에 추가.
		*/
		
		var oTrafficGuide = new nhn.api.map.TrafficGuide(); // - 교통 범례 선언
		oTrafficGuide.setPosition({
			bottom : 30,
			left : 10
		});  // - 교통 범례 위치 지정.
		oMap.addControl(oTrafficGuide); // - 교통 범례를 지도에 추가.

		var trafficButton = new nhn.api.map.TrafficMapBtn(); // - 실시간 교통지도 버튼 선언
		trafficButton.setPosition({
			bottom:10, 
			right:150
		}); // - 실시간 교통지도 버튼 위치 지정
		oMap.addControl(trafficButton);
		
		//아래는 위에서 지정한 좌표에 마커를 표시하는 소스 입니다.
		var oSize = new nhn.api.map.Size(28, 37);
		var oOffset = new nhn.api.map.Size(14, 37);

			for(var i = 0; i < oTrakingPoint.length; i++) {	
				var oIcon = new nhn.api.map.Icon('http://static.naver.com/maps2/icons/pin_spot2.png', oSize, oOffset);
				//icon 이미지를 바꿔서 사용할 수 있습니다.

				var oPoint = oTrakingPoint[i];
				var oMarker = new nhn.api.map.Marker(oIcon, { title : usernameArray[i] + ' / <font>' + regdateArray[i] + '</font>' }); 
				oMarker.setPoint(oPoint); //마커 안나오게 (주석처리)
				oMap.addOverlay(oMarker);
				
				//마커라벨 표시
				var oLabel1 = new nhn.api.map.MarkerLabel(); // 마커 라벨 선언
		        oMap.addOverlay(oLabel1);// 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨
		        oLabel1.setVisible(true, oMarker);// 마커 라벨 보이기 
			} 
		
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 네이버 지도 API(단건)
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function naverMapApi(latitude, longitude, user_name, reg_date){
		var latitudeStr = latitude + "";
		var longitudeStr = longitude + "";
		latitudeStr = latitudeStr.substring(0, latitudeStr.indexOf(".")+8); 
		longitudeStr = longitudeStr.substring(0, longitudeStr.indexOf(".")+8);
		
		//var oTowerPoint = new nhn.api.map.LatLng(37.3346369,126.9236806);
		var oTowerPoint = new nhn.api.map.LatLng(latitude, longitude);  
		var defaultLevel = 10;
		var oMap = new nhn.api.map.Map(document.getElementById('map'), { 
						point : oTowerPoint,
						zoom : defaultLevel,
						enableWheelZoom : true,
						enableDragPan : true,
						enableDblClickZoom : true,
						mapMode : 0,
						activateTrafficMap : false,
						activateBicycleMap : false,
						minMaxLevel : [ 1, 14 ],
						size : new nhn.api.map.Size(1032, 775)		});
		// 줌 컨트롤러
		var oSlider = new nhn.api.map.ZoomControl();
		oMap.addControl(oSlider);
		oSlider.setPosition({
			top : 10,
			left : 10
		});

		var oMapTypeBtn = new nhn.api.map.MapTypeBtn();
		oMap.addControl(oMapTypeBtn);
		oMapTypeBtn.setPosition({
			bottom : 10,
			right : 80
		});

		var oThemeMapBtn = new nhn.api.map.ThemeMapBtn();
		oThemeMapBtn.setPosition({
			bottom : 10,
			right : 10
		});
		oMap.addControl(oThemeMapBtn);
		
		/*
		var oBicycleGuide = new nhn.api.map.BicycleGuide(); // - 자전거 범례 선언
		oBicycleGuide.setPosition({
			top : 10,
			right : 10
		}); // - 자전거 범례 위치 지정
		oMap.addControl(oBicycleGuide);// - 자전거 범례를 지도에 추가.
		*/
		
		var oTrafficGuide = new nhn.api.map.TrafficGuide(); // - 교통 범례 선언
		oTrafficGuide.setPosition({
			bottom : 30,
			left : 10
		});  // - 교통 범례 위치 지정.
		oMap.addControl(oTrafficGuide); // - 교통 범례를 지도에 추가.

		var trafficButton = new nhn.api.map.TrafficMapBtn(); // - 실시간 교통지도 버튼 선언
		trafficButton.setPosition({
			bottom:10, 
			right:150
		}); // - 실시간 교통지도 버튼 위치 지정
		oMap.addControl(trafficButton);
		
		//아래는 위에서 지정한 좌표에 마커를 표시하는 소스 입니다.
		var oSize = new nhn.api.map.Size(28, 37);
		var oOffset = new nhn.api.map.Size(14, 37);
		var oIcon = new nhn.api.map.Icon('http://static.naver.com/maps2/icons/pin_spot2.png', oSize, oOffset);

		//icon 이미지를 바꿔서 사용할 수 있습니다.
		var oMarker = new nhn.api.map.Marker(oIcon, { title : user_name + ' / <font>' + reg_date + '</font>' });
		oMarker.setPoint(oTowerPoint); //마커 안나오게 (주석처리)
		oMap.addOverlay(oMarker);
		
		//마커라벨 표시
		var oLabel1 = new nhn.api.map.MarkerLabel(); // 마커 라벨 선언
        oMap.addOverlay(oLabel1);// 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨
        oLabel1.setVisible(true, oMarker);// 마커 라벨 보이기 
        
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 네이버 지도 호출(리스트)
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goMapList(latitude, longitude, user_name, reg_date){
		$("#ListShow").show();
		$("#tracksTable").hide();
		$("#pagination").hide();
		$("#map").css("border","1px solid #000");
		$("#map").empty();
		$("#map").show();

		naverMapApiList(latitude, longitude, user_name, reg_date);


	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 네이버 지도 호출(단건)
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goMap(latitude, longitude, user_name, reg_date){
		$("#ListShow").show();
		$("#tracksTable").hide();
		$("#pagination").hide();
		$("#map").css("border","1px solid #000");
		$("#map").empty();  
		$("#map").show();
		naverMapApi(latitude, longitude, user_name, reg_date);
		
	}
	
	function goList(){
		$("#ListShow").hide();
		$("#tracksTable").show();
		$("#pagination").show();
		$("#map").empty();
		$("#map").hide();
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
	
		<form id="frm" name="frm" method="post" action="<c:url value='/kdn/traking/user/Immdently.do'/>"> <!-- target="_hidden" -->
			<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" />
			<input name="group_code_idx" type="hidden"	value="" />
			<input name="user_name" id="user_name" type="hidden" value="" />
			<input name="latitude" id="latitude" type="hidden" value="" />
			<input name="longitude" id="longitude" type="hidden" value="" />
			<input name="remarks" id="remarks" type="hidden" value="" />
			<input name="fst_bizplc_cd_old" id="fst_bizplc_cd_old" type="hidden" value="" />
			<input name="scd_bizplc_cd_old" id="scd_bizplc_cd_old" type="hidden" value="" />
<!-- 			<input name="fst_bizplc_cd" id="fst_bizplc_cd" type="hidden" value="" /> -->
<%-- 			<input name="user_id" type="hidden" value="${#user_id}.val()" /> --%>
				
			<!-- container -->	
			<div class="container">
				<!-- top -->	
				<div class="top">
					<h3>실시간 트래킹</h3>
					<p class="navi">
						<strong>Home</strong> &gt; 트래킹 관리 &gt; <strong class="location">순시자위치검색</strong>
					</p>
				</div>
				<!-- // top -->
				<!-- mid -->
				<div class="mid" >
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
							<select id="scd_bizplc_cd" name="scd_bizplc_cd" class="select_st2 ml27" onchange="setTrakingList(this.value);">
								<option value="" > ← 선택하세요</option>
							</select> 
							

							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>순시자</strong></label> 
							<select id="user_id" name="user_id" class="select_st2 ml27" onchange="javascript:setTrakingInfo(this.value);">
								<option value="" >선택하세요</option>
								<option value="test" onclick="setScdBizplcList('<c:out value="${box.fst_bizplc_cd}"/>');"> ← 선택하세요</option>
							</select> 
						</li>
						<li>
							<div id="dateDiv">
								<%-- <label for="work_divide" class="ml137"><strong class="label_tit">순시일</strong></label>
								<input name="traking_date" id="traking_date" type="text" size="10" maxlength="10" style="width:100px;cursor:pointer;" class="select_st2 ml48" value="${box.traking_date}">
								<img src="<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif' />" alt="달력창팝업버튼이미지" onClick="calljscal('traking_date')" style="cursor:pointer;">
								&nbsp;&nbsp;<input type="button" id="traking" name="traking" value="순시자트래킹" title="순시자트래킹" onclick="goSearch();" class="button">  --%>
								
								<a href="javascript:choiceMap();" target="_self"><img src='<c:url value="/images/kdn/common/btn_search.gif" />' id="search" name="search" alt="검색" style="padding-left: 10PX;"/></a>
								<div id="mapDiv" ></div>
								
							</div>
							
							
							<%-- <label for="work_divide" class="ml137"><strong class="label_tit">순시종류</strong></label>
							<select id="searchCnd4" name="searchCnd4" class="select_st2 ml27">
								<option value="" >선택하세요</option>
								<c:forEach var="codeList" items="${codeList}" varStatus="status">
									<option value="${codeList.code_value}" <c:if test="${codeList.code_value eq viewBox.group_code_idx}">selected="selected"</c:if> >${codeList.code_name}</option>
								</c:forEach>
							</select> --%>
<!-- 							<label for="work_divide" class="ml137"><strong class="label_tit">지지물</strong></label> -->
<%-- 							<input name="searchWrd" type="text" size="35" value='<c:out value="${box.user_id}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml48"> --%>
<%-- 							<a href="javascript:goMap(<c:out value="${#latitude}" />, <c:out value="${#longitude}" />, '<c:out value="${#user_name}" />');"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" style="padding-left: 10PX;"/></a> --%>
									
									
<%-- 							<input id="ListShow" type="button" value="목록 보기" title="리스트" onclick="goMap(${#latitude}, ${#longitude}, ${#user_name});" class="button" style="display:none;float: right;margin-right: 60px;cursor:pointer;"> --%>
						</li> 
					</ul>
					<table summary="송전설비 게시판입니다." id="tracksTable">
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.user_id}">
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">검색하신 순시자가 없습니다.</font>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">목록 검색을 선택하세요.</font>
											</td>
										</tr>	
									</c:otherwise>
								</c:choose>
							</c:if>
						</tbody>
					</table>
					
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
	
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
</html>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->


