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
<link href="<c:url value='/css/egovframework/com/cmm/com.css' />" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/egovframework/com/cmm/button.css' />" rel="stylesheet" type="text/css"> 
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cop/bbs/EgovBBSMng.js' />"></script>
--%>
<link href="<c:url value='/css/kdn/com/cmm/import.css' />" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/menu_jquery.js' />"></script>
  
<!-- 네이버 지도 API -->
<script type="text/javascript" src="http://openapi.map.naver.com/openapi/naverMap.naver?ver=2.0&key=fdc3027d270ff4fbc73d5b8758da4d7b"></script>
<!-- 로컬 : 8b5278255caa0054645f0ded8824c135 -->
<!-- 174번 서버에 적용시 : fdc3027d270ff4fbc73d5b8758da4d7b -->
<script type="text/javascript">
	$(document).ready(function(){		
		$('#topmenu').topmenu({ d1: "102000" , d2: "102001" });  
		
		$(".button").mouseover(function(){  
			$(this).css({"background":"#E4EAF8","color":"#2E4B90"});	  
		}).mouseout(function(){
			$(this).css({"background":"","color":""});   
		});
	});

	function press(event) {
		if (event.keyCode==13) {
			goSearch();
		}
	}
	
	$(document).ready(function () {
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 리스트 선로명 기준으로 묶어서 보여주기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var idStr = "";
		var idArrayList = new Array();
		
		$("[id ^= 'tracksTd' ]").each(function(index){
			var id = $(this).attr("id"); 
			var orgIdStr = id.substring(id.indexOf("_")+1, id.length);
			if(orgIdStr != idStr){  
				idArrayList.push(orgIdStr); 
			}
			idStr = orgIdStr;  
		});
		 
		for(var i = 0; i < idArrayList.length; i++){ 
			var idStr = "tracksTd_" + idArrayList[i];
			var idLen = $("[id *= " + idStr +"]").length; 
			$("[id *= " + idStr +"]").not(":eq(0)").remove();  
			$("[id *= " + idStr +"]").eq(0).attr("rowspan", $.trim(idLen));  
		}	
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 리스트 선로명 기준으로 한번만 보여주기
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		/* var idStr = "";
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
		} */
		
		/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
		* 검색한 후 검색 목록 유지
		━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
		var searchCnd1 = "<c:url value='${box.searchCnd1}' />";
		var searchCnd2 = "<c:url value='${box.searchCnd2}' />";
		var searchCnd3 = "<c:url value='${box.searchCnd3}' />";
		
		if($.trim(searchCnd1) != ''){
			setScdBizplcList(searchCnd1);
			$("#searchCnd2").val(searchCnd2);
		}
		if($.trim(searchCnd2) != ''){
			setTracksList(searchCnd2);
			$("#searchCnd3").val(searchCnd3);
		}
	});
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 검색
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goSearch(){
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
		if ($.trim(document.frm.searchCnd3.value) == '') {
			alert("선로명을 선택해주세요.");
			$("#searchCnd3").focus();
			return;
		}
		document.frm.action = "<c:url value='/kdn/admin/eqpList.do'/>";
		document.frm.submit();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 페이징 이동
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function fn_pageview(pageNo) { 
		document.frm.pageIndex.value = pageNo;
		document.frm.action = "<c:url value='/kdn/admin/eqpList.do'/>";
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
	* 선로명 목록 셋팅
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function setTracksList(scdBizplcCode){
		var data = getTracksList($("#searchCnd1").val(), scdBizplcCode);
		var addTracksList = "";
		if(data != null && data.tracksList != null && data.tracksList.length > 0){
			addTracksList +='<option value="" >선택하세요.</option>';
			for (var i = 0; i < data.tracksList.length; i++) {
				addTracksList +='<option value="' + data.tracksList[i].VALUE +'" >' + data.tracksList[i].NAME + '</option>';	
			}
		}else{
			addTracksList +='<option value="" >데이터가 없습니다.</option>';			
		}
		$("#searchCnd3").empty();
		$("#searchCnd3").append(addTracksList);	
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
	* 네이버 지도 API
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function naverMapApi(latitude, longitude, eqp_nm){
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
		
		/* 
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
		*/
		
		//아래는 위에서 지정한 좌표에 마커를 표시하는 소스 입니다.
		var oSize = new nhn.api.map.Size(28, 37);
		var oOffset = new nhn.api.map.Size(14, 37);
		var oIcon = new nhn.api.map.Icon('http://static.naver.com/maps2/icons/pin_spot2.png', oSize, oOffset);

		//icon 이미지를 바꿔서 사용할 수 있습니다.
		var oMarker = new nhn.api.map.Marker(oIcon, { title : eqp_nm + ' : <font>' + latitudeStr + ' / ' + longitudeStr + '</font>'});
		oMarker.setPoint(oTowerPoint); //마커 안나오게 (주석처리)
		oMap.addOverlay(oMarker);
		
		//마커라벨 표시
		var oLabel1 = new nhn.api.map.MarkerLabel(); // 마커 라벨 선언
        oMap.addOverlay(oLabel1);// 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨
        oLabel1.setVisible(true, oMarker);// 마커 라벨 보이기 
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 네이버 지도 호출
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function goMap(latitude, longitude, eqp_nm){
		$("#ListShow").show();
		$("#tracksTable").hide();
		$("#pagination").hide();
		$("#map").css("border","1px solid #000");
		$("#map").show();
		naverMapApi(latitude, longitude, eqp_nm);
		
	}
	
	function goList(){
		$("#ListShow").hide();
		$("#tracksTable").show();
		$("#pagination").show();
		$("#map").empty();
		$("#map").hide();
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 선로에 대한 지지물 위치 전체 보여주기 (다중 마커)
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function AllMapShow(fstBizplcCode, scdBizplcCode, fnctLcNo){
		$("#ListShow").show();
		$("#tracksTable").hide();
		$("#pagination").hide();
		$("#map").css("border","1px solid #000");
		$("#map").show();
		
		var dataList = getEqpMapInfoList(fstBizplcCode, scdBizplcCode, fnctLcNo);
		
		var latitudeArr = new Array();
		var longitudeArr = new Array();
		var eqp_nmArr = new Array();
		if(dataList !=null && dataList.eqpMapInfoList != null && dataList.eqpMapInfoList.length > 0){
			for (var i = 0; i < dataList.eqpMapInfoList.length; i++) {
				latitudeArr[i] = dataList.eqpMapInfoList[i].LATITUDE;
				longitudeArr[i] = dataList.eqpMapInfoList[i].LONGITUDE;
				eqp_nmArr[i] = dataList.eqpMapInfoList[i].EQP_NM;
			}
		}	
		
		var latitudeArrStr = new Array();
		var longitudeArrStr = new Array();
		var oTowerPointArr = new Array();
		var latitudeNum = 0;
		var longitudeNum = 0;
		for ( var i = 0; i < latitudeArr.length; i++) {
			var latitudeStr = latitudeArr[i];
			var longitudeStr = longitudeArr[i];
			latitudeArrStr[i] = latitudeStr.substring(0, latitudeStr.indexOf(".")+8); 
			longitudeArrStr[i] = longitudeStr.substring(0, longitudeStr.indexOf(".")+8);
			if(Math.floor(latitudeArr.length / 2) == i){
				latitudeNum = latitudeStr;
				longitudeNum = longitudeStr;
			}
			oTowerPointArr[i] = new nhn.api.map.LatLng(latitudeStr, longitudeStr);
		}
		
		var oTowerPoint = new nhn.api.map.LatLng(latitudeNum + "", longitudeNum + "");
		var defaultLevel = 9;
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
		
		/* 
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
		*/
		
		//아래는 위에서 지정한 좌표에 마커를 표시하는 소스 입니다.
		var oSize = new nhn.api.map.Size(28, 37);
		var oOffset = new nhn.api.map.Size(14, 37);
		
		var oInfoWnd = new nhn.api.map.InfoWindow();
        oInfoWnd.setVisible(false);
        oMap.addOverlay(oInfoWnd);

        oInfoWnd.setPosition({
                top : 20,
                left :20
        });
		
        var oLabel = new nhn.api.map.MarkerLabel(); // - 마커 라벨 선언.
        oMap.addOverlay(oLabel); // - 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨.

        oInfoWnd.attach('changeVisible', function(oCustomEvent) {
                if (oCustomEvent.visible) {
                        oLabel.setVisible(false);
                }
        });
        
        var oPolyline = new nhn.api.map.Polyline([], {
                strokeColor : '#f00', // - 선의 색깔
                strokeWidth : 5, // - 선의 두께
                strokeOpacity : 0.5 // - 선의 투명도
        }); // - polyline 선언, 첫번째 인자는 선이 그려질 점의 위치. 현재는 없음.
        oMap.addOverlay(oPolyline); // - 지도에 선을 추가함.

        oMap.attach('mouseenter', function(oCustomEvent) {

                var oTarget = oCustomEvent.target;
                // 마커위에 마우스 올라간거면
                if (oTarget instanceof nhn.api.map.Marker) {
                        var oMarker = oTarget;
                        oLabel.setVisible(true, oMarker); // - 특정 마커를 지정하여 해당 마커의 title을 보여준다.
                }
        });

        oMap.attach('mouseleave', function(oCustomEvent) {

                var oTarget = oCustomEvent.target;
                // 마커위에서 마우스 나간거면
                if (oTarget instanceof nhn.api.map.Marker) {
                        oLabel.setVisible(false);
                }
        });

        oMap.attach('click', function(oCustomEvent) {
                var oTarget = oCustomEvent.target;
                oInfoWnd.setVisible(false);
                // 마커 클릭하면
                if (oTarget instanceof nhn.api.map.Marker) {
                        // 겹침 마커 클릭한거면
                        if (oCustomEvent.clickCoveredMarker) {
                                return;
                        }
                        // - InfoWindow 에 들어갈 내용은 setContent 로 자유롭게 넣을 수 있습니다. 외부 css를 이용할 수 있으며, 
                        // - 외부 css에 선언된 class를 이용하면 해당 class의 스타일을 바로 적용할 수 있습니다.
                        // - 단, DIV 의 position style 은 absolute 가 되면 안되며, 
                        // - absolute 의 경우 autoPosition 이 동작하지 않습니다. 
                        oInfoWnd.setContent('<DIV style="border-top:1px solid; border-bottom:2px groove black; border-left:1px solid; border-right:2px groove black;margin-bottom:1px;color:black;background-color:white; width:auto; height:auto;">'+
                                '<span style="color: #000000 !important;display: inline-block;font-size: 12px !important;font-weight: bold !important;letter-spacing: -1px !important;white-space: nowrap !important; padding: 2px 5px 2px 2px !important">' + 
                                oTarget.getTitle().substring(0, oTarget.getTitle().indexOf(":")) + '<br /> ' + $.trim(oTarget.getPoint()).replace(",", " / ")
                                +'<span></div>');
                        oInfoWnd.setPoint(oTarget.getPoint());
                        oInfoWnd.setPosition({right : 15, top : 30});
                        oInfoWnd.setVisible(true);
                        oInfoWnd.autoPosition();
                        return;
                }
        });
        
        for(var j = 0; j < latitudeArr.length; j++) {
        	var oIcon = new nhn.api.map.Icon('http://static.naver.com/maps2/icons/pin_spot2.png', oSize, oOffset);
        	
        	var oPoint = oTowerPointArr[j];
	      	//icon 이미지를 바꿔서 사용할 수 있습니다.
			var oMarker = new nhn.api.map.Marker(oIcon, { title : eqp_nmArr[j] + ' : <font>' + latitudeArrStr[j] + ' / ' + longitudeArrStr[j] + '</font>'}); 
			oMarker.setPoint(oPoint); //마커 안나오게 (주석처리)
			oMap.addOverlay(oMarker);
			
			//마커라벨 표시
			var oLabel1 = new nhn.api.map.MarkerLabel(); // 마커 라벨 선언
	        oMap.addOverlay(oLabel1);// 마커 라벨 지도에 추가. 기본은 라벨이 보이지 않는 상태로 추가됨
	        oLabel1.setVisible(false, oMarker);// 마커 라벨 보이기 (true, false)
        }
	}
	
	/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
	* 선로에 대한 지지물 (위도, 경도) 가져오기 AJAX
	━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
	function getEqpMapInfoList(fstBizplcCode, scdBizplcCode, fnctLcNo){
		var requestUrl = "/ajax/getEqpMapInfoList.json"; 
		var queryParam = "fst_bizplc_cd=" + fstBizplcCode + "&scd_bizplc_cd=" + scdBizplcCode + "&fnct_lc_no=" + fnctLcNo;   
		console.log(queryParam);
		var returnParam = "";  
		$.ajax({
			url : requestUrl,
			data : queryParam, 
			type : "POST",  
			async : false,
			success : function(data){
				console.log(data.code);
				console.log(data.eqpMapInfoList); 
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
        	
        	<form id="frm" name="frm" method="post" action="<c:url value='/kdn/admin/noticeList.do'/>">
				<input name="pageIndex" type="hidden"	value="<c:out value='${box.pageIndex}'/>" /> 
				<input name="board_idx" type="hidden" value="" />
			
	        	<!-- .container -->
	        	<div class="container">
					<!-- top -->
					<div class="top">
						<h3>송전설비검색</h3>
						<p class="navi">
							<strong>Home</strong> &gt; 송전설비 관리 &gt; <strong class="location">송전설비검색</strong>
						</p>
					</div>
					<!-- // top -->
					<!-- mid -->
				<div class="mid" >
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
							<select id="searchCnd2" name="searchCnd2" class="select_st2 ml27" onchange="setTracksList(this.value);">
								<option value="" > ← 선택하세요</option>
							</select> 
							
							<label for="work_divide" class="ml66"><strong class="label_tit"><font style="color:red;">* </font>선로명</strong></label> 
							<select id="searchCnd3" name="searchCnd3" class="select_st2 ml27">
								<option value="" > ← 선택하세요</option>
							</select> 
						</li>
						<li>
							<%-- <label for="work_divide" class="ml137"><strong class="label_tit">순시종류</strong></label>
							<select id="searchCnd4" name="searchCnd4" class="select_st2 ml27">
								<option value="" >선택하세요</option>
								<c:forEach var="codeList" items="${codeList}" varStatus="status">
									<option value="${codeList.code_value}" <c:if test="${codeList.code_value eq viewBox.group_code_idx}">selected="selected"</c:if> >${codeList.code_name}</option>
								</c:forEach>
							</select> --%>
							<label for="work_divide" class="ml137"><strong class="label_tit">지지물</strong></label>
							<input name="searchWrd" type="text" size="35" value='<c:out value="${box.searchWrd}"/>' maxlength="35" onkeypress="press(event);" title="검색단어입력" class="input_st ml48">
							<a href="#" onclick="javascript:goSearch();return false;"><img src="<c:url value='/images/kdn/common/btn_search.gif' />" alt="검색" style="padding-left: 10PX;"/></a>
							<input id="ListShow" type="button" value="목록" title="리스트" onclick="goList();" class="button" style="display:none;float: right;margin-right: 60px;cursor:pointer;">
						</li> 
					</ul>
					<table summary="송전설비 게시판입니다." id="tracksTable">
						<caption>송전설비 게시판</caption>
						<thead>
							<tr>
								<th class="thFrist">번호</th>
								<th>선로명</th>
								<th>지지물(송전설비)</th> 
								<th>1차사업소</th>
								<th>2차사업소</th>
								<th>위도</th>
								<th>경도</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${list}" varStatus="status">
								<tr>
									<td> 
										<c:out value="${totCnt - (status.count + (box.pageIndex -1 ) * box.recordCountPerPage) + 1}" />
									</td>
									<td id = "tracksTd_${result.fnct_lc_dtls}">
										<c:out value="${result.fnct_lc_dtls}" /><br><br> 
										<input type="button" value="지지물 전체보기" title="전체보기" class="button" style="cursor:pointer;" onclick="AllMapShow('<c:out value="${result.fst_bizplc_cd}" />', '<c:out value="${result.scd_bizplc_cd}" />', '<c:out value="${result.fnct_lc_no}" />' );">
									</td>
									<td>
										<span style="float:left;padding-left:10px;">
											<c:if test="${not empty result.latitude and not empty result.longitude}">
												<a style="color:#729ACF;text-decoration:underline;" href="javascript:goMap(<c:out value="${result.latitude}" />, <c:out value="${result.longitude}" />, '<c:out value="${result.eqp_nm}" />')">
													<c:out value="${result.eqp_nm}" />&nbsp;(<c:out value="${result.eqp_nm_string}" />)
												</a>
											</c:if>
											<c:if test="${empty result.latitude or empty result.longitude}">
												<c:out value="${result.eqp_nm}" />&nbsp;(<c:out value="${result.eqp_nm_string}" />)
											</c:if>
										</span>
									</td>
									<td>
										<c:out value="${result.fst_bizplc_cd_nm}" />
									</td>
									<td>
										<c:out value="${result.scd_bizplc_cd_nm}" />
									</td>
									<td>
										<c:out value="${fn:substring(result.latitude,0,fn:indexOf(result.latitude,'.') + 7)}" />
									</td>
									<td>
										<c:out value="${fn:substring(result.longitude,0,fn:indexOf(result.longitude,'.') + 7)}" />
									</td>
								</tr>
							</c:forEach>	
							<c:if test="${fn:length(list) == 0}">
								<c:choose>
									<c:when test="${not empty box.searchCnd1}">
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">검색하신 송전설비 데이터가 없습니다.</font>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr> 
											<td colspan="7" >
												<font style="font-family:맑은 고딕;font-size:12px">송전설비 검색 조건을 선택하세요.</font>
											</td>
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
					
					<!-- 지도 Area  -->
					<div id="map" style="dispaly:none;"></div>
					<!-- // 지도 Area  -->					
				</div>
				<!-- // mid -->
				</div> 
	        	<!-- end container -->   
	        </form> 
	        
	        <!-- bottom Area -->
				<c:import url="/kdn/layout/bottomMenu.do" charEncoding="utf-8" />
			<!-- // bottom Area -->
     </div>
     <!-- end wrap -->
	<%-- <iframe name="_hidden" id="_hidden" src="<c:url value='/html/common/blank.html'/>" style="border:0px solid red;margin:0px;width:0px;height:0px;"></iframe> --%>
</body>
</html>
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->
${reusltScript}
<!-- ******************** 결과에 따른 Alert 문구 ******************** -->


