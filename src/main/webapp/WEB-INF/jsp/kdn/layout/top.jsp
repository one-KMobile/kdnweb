<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- header -->
		<header>  
            <div class="navi">              
            <ul>
            	<%-- <li class="current" ><a href="<c:url value='/kdn/admin/noticeList.do'/>" >HOME</a></li> --%> 
            	<li id="top_104000"><a href="<c:url value='/kdn/admin/noticeList.do'/>" >공지사항</a></li>  
                <li id="top_101000"><a href="<c:url value='/kdn/admin/patrolman/List.do'/>" >순시자</a></li>
                <li id="top_102000"><a href="<c:url value='/kdn/admin/eqpList.do'/>" >시설물</a></li>
                <li id="top_103000"><a href="<c:url value='/kdn/admin/insResultList.do'/>" >순시결과</a></li>
                <li id="top_107000"><a href="<c:url value='/kdn/traking/user/Immdently.do'/>" >트래킹</a></li>
                <li id="top_106000"><a href="<c:url value='/kdn/nfc/List.do'/>" >NFC태그</a></li>    
            </ul>
            
            <div class="greeting">               
				<div class="welcome"><c:if test="${not empty sessionInfo.user_id}"><span>${sessionInfo.user_name}</span> 님 환영합니다.</c:if></div>   
				<c:if test="${not empty sessionInfo.user_id}"><a href="/actionLogout.do"><img src="<c:url value='/images/kdn/new/btn_logout.gif' />" alt="로그아웃" style="margin-top: 2px;"/></a></c:if> 
			</div>
            </div>
            <!-- end navi -->                     
           <!--  <div class="tit_depth">         
            <div class="tit_bar"></div><h1>공지사항<p class="depth"><strong>Home</strong> &gt; 공지사항 관리 &gt; <strong class="location">공지사항</strong></p></h1> 
            </div>   -->
        </header>  
        <!-- end header --> 
<!-- header -->

	<%-- <div id="header">		
			<!-- ofh --> 
			<div class="ofh">
				<h1><a href=""><img src="<c:url value='/images/kdn/logo/logo.png' />" alt="KDN로고" /></a></h1>
				<ul id="gnb">
					<li><a href="<c:url value='/kdn/admin/patrolman/List.do'/>">HOME</a></li>
					<li><a id="101000" href="<c:url value='/kdn/admin/patrolman/List.do'/>">순시자</a></li>
					<li><a id="102000" href="<c:url value='/kdn/admin/eqpList.do'/>">시설물</a></li>
					<li><a id="103000" href="<c:url value='/kdn/admin/insResultList.do'/>">순시결과</a></li>
					<!-- <li><a href="#">정보동기화</a></li> -->
					<li><a id="104000" href="<c:url value='/kdn/admin/noticeList.do'/>">공지사항/PUSH</a></li>
					<li><a id="105000" href="<c:url value='/kdn/admin/groupCodeList.do'/>">공통코드</a></li>  
					<li><a href="<c:url value='/kdn/admin/logHistoryListAll.do'/>">로그 이력</a></li>
					<li><a href="<c:url value='/kdn/admin/mobileConfirmList.do'/>">모바일 승인</a></li>
					<li><a href="<c:url value='/kdn/nfc/List.do'/>">NFC태그 관리</a></li>
					<li><a href="<c:url value='/kdn/admin/logHistoryListAll.do'/>">로그 이력</a></li>
				</ul>
				<!-- greeting -->
				<div class="greeting">          
					<p class="welcome"><c:if test="${not empty sessionInfo.user_id}"><span class="sysop">${sessionInfo.user_name} [${sessionInfo.user_auth}]</span> 님 환영합니다.</c:if></p> 
					<ul> 
						<li><c:if test="${not empty sessionInfo.user_id}"><a href="<c:url value='/actionLogout.do'/>"><img src="<c:url value='/images/kdn/common/btn_logout.gif' />" alt="로그아웃" /></a></c:if></li>  
					</ul>
				</div>
				<!-- // greeting -->   
			</div>
			<!-- // ofh -->
	</div> --%>
<!-- // header -->

