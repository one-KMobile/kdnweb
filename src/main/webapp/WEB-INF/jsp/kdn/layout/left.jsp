<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
</script>


<!-- aside -->
<aside>
      	<img class="logo" src="<c:url value='/images/kdn/new/logo.jpg' />" />
      	<!-- #cssmenu -->
      	<div id="cssmenu">
          <ul>
          	<c:forEach items="${leftMenuList}" var="leftMenuList" varStatus="status">
          		<c:if test="${leftMenuList.top_menu_id eq '000000' and leftMenuList.top_menu_id_count <= 0}">
          			<c:if test="${leftMenuList.auth_read eq 'Y'}"> 
          				<li id="${leftMenuList.menu_id}"><a href="<c:url value='${leftMenuList.controller_method}'/>"><span>${leftMenuList.menu_nm}</span></a></li> 
          			</c:if> 
          		</c:if>
          		
          		<c:if test="${leftMenuList.top_menu_id eq '000000' and leftMenuList.top_menu_id_count > 0}">
          			<c:if test="${leftMenuList.top_menu_id_count <= 1}">   
          				<li id="${leftMenuList.menu_id}" ><a href="<c:url value='${leftMenuList.controller_method}'/>"><span>${leftMenuList.menu_nm}</span></a> 
          			</c:if>
          			<c:if test="${leftMenuList.top_menu_id_count > 1}">       
          				<li class='has-sub' id="${leftMenuList.menu_id}" ><a href="<c:url value='${leftMenuList.controller_method}'/>"><span>${leftMenuList.menu_nm}</span></a> 
          			</c:if>
							<ul>
								<c:forEach items="${leftMenuList.subMenuList}" var="subMenuList" varStatus="varstatus">
									<c:if test="${!varstatus.end}">
										<c:if test="${subMenuList.auth_read eq 'Y'}">   
											<li id="${subMenuList.menu_id}"><a href="<c:url value='${subMenuList.controller_method}'/>"><span>${subMenuList.menu_nm}</span></a></li>
										</c:if>	
									</c:if>    
									<c:if test="${varstatus.end}"> 
										<c:if test="${subMenuList.auth_read eq 'Y'}">    
											<li class='last' id="${subMenuList.menu_id}"><a href="<c:url value='${subMenuList.controller_method}'/>"><span>${subMenuList.menu_nm}</span></a></li>
										</c:if>
									</c:if>
								</c:forEach> 
							</ul>
					</li>
          		</c:if>
          	</c:forEach>
          		
			<!-- <li><a href='#'><span>순시결과 관리</span></a></li>
			<li><a href='#'><span>공지사항 관리</span></a></li>

			<li class='has-sub'><a href='#'><span>공통코드 관리</span></a>
				<ul>
					<li><a href='#'><span>공통그룹코드</span></a></li>
					<li class='last'><a href='#'><span>공통코드</span></a></li>
				</ul></li>

			<li><a href='#'><span>NFC태그 관리</span></a></li>

			<li class='has-sub'><a href='#'><span>트래킹 관리</span></a>
				<ul>
					<li><a href='#'><span>실시간 트래킹</span></a></li>
					<li class='last'><a href='#'><span>순시자 트래킹</span></a></li>
				</ul></li>

			<li><a href='#'><span>로그 관리</span></a></li>
			<li><a href='#'><span>메뉴 관리</span></a></li>
			<li class='last'><a href='#'><span>권한 관리</span></a></li> -->
		</ul>
	</div>
          <!-- end #cssmenu -->
</aside> 
<!-- end aside -->
		
		
<!-- left_side -->
 <%-- <div class="left_side">
	<ul class="depth_1">
		<li><a href="#"><img src="<c:url value='/images/kdn/menu/lnb01.jpg' />" alt="구성관리" /></a>
		<li><a href="#"><img src="<c:url value='/images/kdn/menu/lnb02.jpg' />" alt="운영관리" /></a>
	</ul>	
			<!-- depth2 -->
			<div class="depth_2" id="topmenu">
				<ul>
					<c:forEach items="${leftMenuList}" var="leftMenuList" varStatus="status">
							<c:if test="${leftMenuList.top_menu_id eq '000000' and leftMenuList.top_menu_id_count <= 0}">
								<li id="${subMenuList.controller_method}"><a class="lnb_d1" href="<c:url value='${leftMenuList.controller_method}'/>"><img src="<c:url value='/images/kdn/menu/leftmenu_${leftMenuList.menu_id}.jpg' />" alt=""></a>
									<div class="submenu" style="display:none;"> 
										<ul>
											<li><a href="<c:url value='${leftMenuList.controller_method}'/>">${leftMenuList.menu_nm}</a></li>
								   		</ul>  
									</div>
								</li>
							</c:if>
							<c:if test="${leftMenuList.top_menu_id eq '000000' and leftMenuList.top_menu_id_count > 0}">       
								<li id="${leftMenuList.menu_id}"><a class="lnb_d1" href="<c:url value='${leftMenuList.controller_method}'/>"><img src="<c:url value='/images/kdn/menu/leftmenu_${leftMenuList.menu_id}.jpg' />" alt=""></a>
									<div class="submenu" style="display:none;"> 
										<ul id="subMenuList">
											<c:forEach items="${leftMenuList.subMenuList}" var="subMenuList" varStatus="varstatus">
												<c:if test="${subMenuList.auth_read eq 'Y'}">    
													<li><a id="${subMenuList.menu_id}" href="<c:url value='${subMenuList.controller_method}'/>">${subMenuList.menu_nm}</a></li>
												</c:if> 
											</c:forEach>	     
								   		</ul>
									</div>
								</li>
							</c:if>
					</c:forEach>  
					
					 
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu1.jpg' />" alt="순시자관리"></a> 
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/patrolman/List.do'/>">순시자 목록</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu2.jpg' />" alt="시설물 관리"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/eqpList.do'/>">송전설비 검색</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu3.jpg' />" alt="순시결과 관리"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/insResult01List.do'/>">순시결과 검색</a></li>
								<li><a href="#">순시결과 다운로드</a></li>
							</ul>
						</div>	
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu4.jpg' />" alt="정보동기화 이력 관리"></a>
						<div class="submenu">
							<ul>
								<li><a href="#">정보동기화 이력 검색</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu5.jpg' />" alt="공지사항 관리"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/noticeList.do'/>">공지사항 목록</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu6.jpg' />" alt="공통코드 관리"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/groupCodeList.do'/>">공통그룹코드 목록</a></li>
								<li><a href="<c:url value='/kdn/admin/codeList.do'/>">공통코드 목록</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu8.jpg' />" alt="NFC태그 관리"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/nfc/List.do'/>">NFC 목록</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu8.jpg' />" alt="트래킹"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/traking/user/Immdently.do'/>">실시간 트래킹</a></li>
								<li><a href="<c:url value='/kdn/traking/user/Patrolman.do'/>">순시자 트래킹</a></li>
							</ul>
						</div>
					</li>
					
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu8.jpg' />" alt="로그 이력"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/logHistoryListAll.do'/>">로그 이력 목록</a></li>
							</ul>
						</div>
					</li>
					<li><a class="lnb_d1" href="#"><img src="<c:url value='/images/kdn/menu/leftmenu8.jpg' />" alt="로그 이력"></a>
						<div class="submenu">
							<ul>
								<li><a href="<c:url value='/kdn/admin/menuList.do'/>">메뉴 관리</a></li>
								<li><a href="<c:url value='/kdn/admin/authList.do'/>">권한 관리</a></li>
							</ul>
						</div>
					</li>
					
					<!--  
					<li><a href="#"><img 
							src="<c:url value='/images/kdn/common/lnb01_02.gif' />"
							alt="장비정보관리" /></a>
						<ul>
							<li><a href="#"><img
									src="<c:url value='/images/kdn/common/lnb01_02_01.gif' />"
									alt="장비정보" /></a></li>
							<li><a href="#"><img
									src="<c:url value='/images/kdn/common/lnb01_02_02.gif' />"
									alt="포트정보" /></a></li>
							<li><a href="#"><img
									src="<c:url value='/images/kdn/common/lnb01_02_03.gif' />"
									alt="회선정보" /></a></li>
						</ul></li>
					-->
				</ul>		
			</div> <!-- // depth2 -->
	<ul class="myMenu">
		<li><a href="#"><img
				src="<c:url value='/images/kdn/common/myMenu.gif' />" alt="마이메뉴" /></a>
			<ul>
				<li><a href="#">- 순시자 정보변경</a></li>
				<li><a href="#"><img
						src="<c:url value='/images/kdn/common/myMenu_03.gif' />"
						alt="회선정보설정" /></a></li>
			</ul></li>
	</ul>
</div> --%>
<!-- // left_side -->
