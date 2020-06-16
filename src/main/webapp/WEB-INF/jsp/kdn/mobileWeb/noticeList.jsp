<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>  

<html lang="ko" xmlns:v="urn:schemas-microsoft-com:vml">   
	<head>
		<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0" />   --> 
		<title>DARTS TV</title>  
		<link rel="stylesheet" href="<c:url value='/css/mobileWeb/mobile.css' />">
		<script type="text/javascript" src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/mobileWeb/kdnNotice.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/mobileWeb/viewport.js' />"></script>
		
		<script type="text/javascript">
			function update(scale) {    
			}

			mj.viewport({    width : 640,    onAdjustment : update});
			function isAndroid(){
				if(navigator.userAgent.match(/iPhone|iPod|iPad|webmate/)){
					return false;
				}
				else if(navigator.userAgent.match(/Android|CUPCAKE|dream/)){
					return true;
				}
			}
		</script>   
	</head>
	<body style="overflow-y:hidden"> 
		<%-- <img src="<c:url value='/images/mobileWeb/icon/notice_round.png' />" alt="" style="position: absolute;left: 20px;top: 117px;width: 18px;z-index:9999">
		<img src="<c:url value='/images/mobileWeb/icon/notice_round.png' />" alt="" style="position: absolute;right: 20px;top: 117px;width: 18px;z-index:9999;-webkit-transform: rotate(90deg)">
		<img src="<c:url value='/images/mobileWeb/icon/notice_round.png' />" alt="" style="position: absolute;left: 20px;top: 763px;width: 18px;z-index:9999;-webkit-transform: rotate(270deg)">
		<img src="<c:url value='/images/mobileWeb/icon/notice_round.png' />" alt="" style="position: absolute;right: 20px;top: 763px;width: 18px;z-index:9999;-webkit-transform: rotate(180deg)"> --%>
		<form id="frm" name="frm"> 
			<input  type="hidden" id="noticeCagoCd" name="noticeCagoCd" value="" />
		</form>
		<%-- <header>
			<h1 style="font-size: 40px;font-weight: bold;padding-top: 25px;font-family:맑은 고딕;color:white;">
				<!-- <img src="image/mobileWeb/logo.png" alt="DARTS TV">  -->   
				공지사항
			</h1>
			<div class="all-view">       
				<img src="<c:url value='/images/mobileWeb/btn/view.png' />" alt=""> 
			</div>	
			<div class="refresh"> 
				<img src="<c:url value='/images/mobileWeb/btn/refresh.png' />" alt="새로고침">
			</div>	  
		</header> --%>     
		<article>
			<section class="notice">
				<ul class="tab-type1">
					<!-- <li class="on"><span>전체</span></li>      
					<li><span>개인/긴급</span></li> -->  
					<li style="float: left;width: 50%;height: 75px;">
						<img src="<c:url value='/images/mobileWeb/new/n3_btn_notice1.png' />" alt="공지사항" width="100%">
					</li> 
					<li style="float: left;width: 50%;height: 75px;">  
						<img src="<c:url value='/images/mobileWeb/new/n3_btn_notice2.png' />" alt="작업지시" width="100%">
					</li>
				</ul>
				<ul class="board-view"> 
					<!-- <li class="on">
						<div class="tit">
							도움말 및 공지사항
						</div>
						<div class="txt" >
							<div>
								새누리당 심재철 최고위원은 14일 박근혜 대통령 당선인의 대선 공약 이행 방침에 대해 "원칙이 훼손되거나 예산이 없는데도 무조건 공약대로 해야 한다는 것은 바람직하지 않다"고 말했다. 심 최고위원은 이날 서울 여의도 당사에서 열린 최고위원회의에서 "공약 이행도 좋지만 현실적으로 쉽지 않은 대형 예산 공약들에 대해서는 출구 전략도 같이 생각하셨으면 한다"며 이같이 말했다. 새누리당 지도부에서 '무리한 공약'에 대한 재검토 필요성이 제기된 것이다.<br />심 최고위원은 이 같은 공약의 대표적인 사례로 기초노령연금을 들며 "65세 이상 누구에게나 노령연금 <br />새누리당 심재철 최고위원은 14일 박근혜 대통령 당선인의 대선 공약 이행 방침에 대해 "원칙이 훼손되거나 예산이 없는데도 무조건 공약대로 해야 한다는 것은 바람직하지 않다"고 말했다. 심 최고위원은 이날 서울 여의도 당사에서 열린 최고위원회의에서 "공약 이행도 좋지만 현실적으로 쉽지 않은 대형 예산 공약들에 대해서는 출구 전략도 같이 생각하셨으면 한다"며 이같이 말했다. 새누리당 지도부에서 '무리한 공약'에 대한 재검토 필요성이 제기된 것이다.<br />심 최고위원은 이 같은 공약의 대표적인 사례로 기초노령연금을 들며 "65세 이상 누구에게나 노령연금 <br />새누리당 심재철 최고위원은 14일 박근혜 대통령 당선인의 대선 공약 이행 방침에 대해 "원칙이 훼손되거나 예산이 없는데도 무조건 공약대로 해야 한다는 것은 바람직하지 않다"고 말했다. 심 최고위원은 이날 서울 여의도 당사에서 열린 최고위원회의에서 "공약 이행도 좋지만 현실적으로 쉽지 않은 대형 예산 공약들에 대해서는 출구 전략도 같이 생각하셨으면 한다"며 이같이 말했다. 새누리당 지도부에서 '무리한 공약'에 대한 재검토 필요성이 제기된 것이다.<br />심 최고위원은 이 같은 공약의 대표적인 사례로 기초노령연금을 들며 "65세 이상 누구에게나 노령연금 <br />
							</div>
						</div>
					</li> -->
					<!--
					<li>
						<div class="tit">
							도움말 및 공지사항
						</div>
						<div class="txt">
							<div>
								새누리당 심재철 최고위원은 14일 박근혜 대통령 당선인의 대선 공약 이행 방침에 대해 "원칙이 훼손되거나 예산이 없는데도 무조건 공약대로 해야 한다는 것은 바람직하지 않다"고 말했다. 심 최고위원은 이날 서울 여의도 당사에서 열린 최고위원회의에서 "공약 이행도 좋지만 현실적으로 쉽지 않은 대형 예산 공약들에 대해서는 출구 전략도 같이 생각하셨으면 한다"며 이같이 말했다. 새누리당 지도부에서 '무리한 공약'에 대한 재검토 필요성이 제기된 것이다.<br />심 최고위원은 이 같은 공약의 대표적인 사례로 기초노령연금을 들며 "65세 이상 누구에게나 노령연금 <br />새누리당 심재철 최고위원은 14일 박근혜 대통령 당선인의 대선 공약 이행 방침에 대해 "원칙이 훼손되거나 예산이 없는데도 무조건 공약대로 해야 한다는 것은 바람직하지 않다"고 말했다. 심 최고위원은 이날 서울 여의도 당사에서 열린 최고위원회의에서 "공약 이행도 좋지만 현실적으로 쉽지 않은 대형 예산 공약들에 대해서는 출구 전략도 같이 생각하셨으면 한다"며 이같이 말했다. 새누리당 지도부에서 '무리한 공약'에 대한 재검토 필요성이 제기된 것이다.<br />심 최고위원은 이 같은 공약의 대표적인 사례로 기초노령연금을 들며 "65세 이상 누구에게나 노령연금 <br />새누리당 심재철 최고위원은 14일 박근혜 대통령 당선인의 대선 공약 이행 방침에 대해 "원칙이 훼손되거나 예산이 없는데도 무조건 공약대로 해야 한다는 것은 바람직하지 않다"고 말했다. 심 최고위원은 이날 서울 여의도 당사에서 열린 최고위원회의에서 "공약 이행도 좋지만 현실적으로 쉽지 않은 대형 예산 공약들에 대해서는 출구 전략도 같이 생각하셨으면 한다"며 이같이 말했다. 새누리당 지도부에서 '무리한 공약'에 대한 재검토 필요성이 제기된 것이다.<br />심 최고위원은 이 같은 공약의 대표적인 사례로 기초노령연금을 들며 "65세 이상 누구에게나 노령연금 <br />
							</div>
						</div>
					</li>
					-->
				</ul>
			</section>
		</article>
	</body>
</html>