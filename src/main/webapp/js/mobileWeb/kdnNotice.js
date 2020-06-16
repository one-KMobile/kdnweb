p_board_idx = Request("board_idx"); 

function f_p_board_idx(){
	return p_board_idx;
}

$(document).ready(function(){
	init();
});

var init = function () {   
	NoticeUtil.NoticeCagoList();   
	NoticeUtil.NoticeList("NOTICETP01");      
	
	$('.tab-type1 li').click(function () {
		var index = $(this).index();
		$('.tab-type1 li').eq(0).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice1_ov.png"); 
		$('.tab-type1 li').eq(1).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice2_ov.png");   
		if(index == 0){
			$('.tab-type1 li').removeClass("categoryOn");
			$('.tab-type1 li').eq(index).addClass("categoryOn");    
			$('.tab-type1 li').eq(index).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice1.png");
		}else if(index == 1){
			$('.tab-type1 li').removeClass("categoryOn");
			$('.tab-type1 li').eq(index).addClass("categoryOn");    
			$('.tab-type1 li').eq(index).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice2.png");
		}
		var cagoCd = $(this).attr("id");
		NoticeUtil.NoticeList(cagoCd);
	});
	
	var boardIdx = f_p_board_idx();
	if(boardIdx != null && boardIdx != ""){
		if($("#" + boardIdx).length > 0){
			var category = $("#" + boardIdx).find("[id ^= 'category_' ]").attr("id");
			var categoryStr = category.substring(category.indexOf("_") + 1, category.length);
			
			$('.tab-type1 li').eq(0).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice1_ov.png"); 
			$('.tab-type1 li').eq(1).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice2_ov.png");
			
			if(categoryStr == "NOTICETP01"){
				$('.tab-type1 li').eq(0).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice1.png");
			}else if(categoryStr == "NOTICETP02"){
				$('.tab-type1 li').eq(1).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice2.png");
			}
			
			NoticeUtil.NoticeList($.trim(categoryStr));   
		}else{
			$('.tab-type1 li').eq(0).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice1_ov.png"); 
			$('.tab-type1 li').eq(1).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice2_ov.png");
			
			if($('.tab-type1 li').eq(0).hasClass("categoryOn") == true){
				$('.tab-type1 li').eq(1).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice2.png");
				NoticeUtil.NoticeList("NOTICETP02");  
			}else{
				$('.tab-type1 li').eq(0).find("img").attr("src", "../../../../images/mobileWeb/new/n3_btn_notice1.png");
				NoticeUtil.NoticeList("NOTICETP01");
			}
		}
		  
		$('.board-view li').removeClass('on');  
		$('#'+ boardIdx).addClass('on'); 
	}
	
}; 

var NoticeUtil = {
		dataList : null, 
		NoticeCagoList : function(){  
			dataList = kdnNoticeCagoListAjax();  
			var noticeCagoArea = "";  
			if(dataList !=null && dataList.apiCodeInfoList != null && dataList.apiCodeInfoList.length > 0){
				for (var i = 0; i < dataList.apiCodeInfoList.length; i++) { 
					if(i == 0){ 
						noticeCagoArea += '<li id="' + dataList.apiCodeInfoList[i].CODE_ID +'" style="float: left;width: 50%;height: 75px;" class="categoryOn">'; 
						noticeCagoArea += 	'<img src="../../../../images/mobileWeb/new/n3_btn_notice1.png" alt="공지사항" width="100%">';  
						noticeCagoArea += '</li>';
					}else{ 
						noticeCagoArea += '<li id="' + dataList.apiCodeInfoList[i].CODE_ID +'" style="float: left;width: 50%;height: 75px;">';
						noticeCagoArea += 	'<img src="../../../../images/mobileWeb/new/n3_btn_notice2_ov.png" alt="작업지시" width="100%">';  
						noticeCagoArea += '</li>';
					}
				}
			}else{
				
			}
			$(".tab-type1").empty(); 
			$(".tab-type1").append(noticeCagoArea);
		},
		NoticeList : function(cagoCd){ 
				dataList = kdnNoticeListAjax(cagoCd);
				var noticeArea = "";
				if(dataList !=null && dataList.apiNoticeList != null && dataList.apiNoticeList.length > 0){ 
					for (var i = 0; i < dataList.apiNoticeList.length; i++) {
						noticeArea +='<li id="' + dataList.apiNoticeList[i].BOARD_IDX + '">';
						noticeArea += 	'<div class="tit" id="category_'+ dataList.apiNoticeList[i].CATEGORY_CODE +  '">';
						var title = dataList.apiNoticeList[i].BOARD_TITLE;	
						if(title.length > 10){ 
							noticeArea +=	(title.substring(0,10)+'...');
						}else{
							noticeArea +=	title;  
						}
						noticeArea +=		'<span class="date">'+replaceAll(dataList.apiNoticeList[i].SEND_DATE, "-",".")+'</span>';
						noticeArea += 	'</div>';
						noticeArea += 	'<div class="txt">';      
						noticeArea += 		'<div>';
						noticeArea += 		'<div style="font-size:20px;" >';             
						noticeArea += 		'<font style="font-weight:bold">발송자</font> : ' + dataList.apiNoticeList[i].SEND_NAME + '&nbsp;&nbsp;&nbsp;<font style="font-weight:bold">발송일</font> : ' + dataList.apiNoticeList[i].SEND_DATE + ' <br/><br/>';
						noticeArea +=			dataList.apiNoticeList[i].BOARD_CONT;
						noticeArea += 		'</div>';
						noticeArea += 		'</div>';  
						noticeArea += 	'</div>';
						noticeArea +='</li>';
					}
				}else{
					noticeArea += '					<table style="width:100%;padding-top: 15%;position: absolute;z-index: 999999;top: 150px;">';
					noticeArea += '					   <tr>';
					noticeArea += '				        <td style="text-align:-moz-center;text-align:center;">';
					noticeArea += '				            <table style="width:200px;margin:auto;">';
					noticeArea += '								<tr>';
					noticeArea += '				                    <td style="font-size: 20px;font-weight: bold;color:black;padding-bottom: 10px;">';
					noticeArea += '데이터가 없습니다.';
					noticeArea += '				                    </td>';
					noticeArea += '				                </tr>';
					noticeArea += '							</table>';
					noticeArea += '				        </td>';
					noticeArea += '						</tr>';
					noticeArea += '					</table>';	
				}
				$(".board-view").empty(); 
				$(".board-view").append(noticeArea);

				$('.board-view li').click(function () {
					var index = $(this).index();
					if($(this).is(".on")){ 
						$('.board-view li').eq(index).removeClass('on');;
					}else{
						$('.board-view li').removeClass('on');
						$('.board-view li').eq(index).addClass('on');
					}
				});			
		}
}; 

/* 공지사항 리스트 AJAX*/
function kdnNoticeListAjax(cagoCd){     
	var requestUrl = "/api/apiNoticeList.json";   
	var queryParam = "";
	if(cagoCd != "All"){  
		queryParam = "notice_cago_cd=" + cagoCd;       
	}
	console.log(queryParam);    
	var returnParam = "";  
	$.ajax({
		url : requestUrl,
		data : queryParam,   
		type : "POST",     
		async : false,
		success : function(data){
			console.log(data.code);
			console.log(data.apiNoticeList);   
			if(data.code == '001'){
				returnParam = data;
			}else if(data.code == '002'){
				alert("실패");
			}
		},
	    error:function(XMLHttpRequest,textStatus,errorThrown){
	    	alert("arror");
		}
	});
	return returnParam;
}

/* 공지사항 카테고리 리스트 AJAX*/
function kdnNoticeCagoListAjax(){ 
	var requestUrl = "/api/apiCodeInfoList.json";   
	var queryParam = "group_code_id=NOTICETYPE";
	console.log(queryParam);    
	var returnParam = "";  
	$.ajax({
		url : requestUrl,
		data : queryParam,
		type : "POST",     
		async : false,
		success : function(data){
			console.log(data.code);
			console.log(data.apiCodeInfoList);   
			if(data.code == '001'){
				returnParam = data;
			}else if(data.code == '002'){
				alert("실패");
			}
		},
	    error:function(XMLHttpRequest,textStatus,errorThrown){
	    	alert("arror");
		}
	});
	return returnParam;
}


//Request
function Request(valuename)   
{
    var rtnval;
    var nowAddress = decodeURI(location.href);  
	//var nowAddress = unescape(location.href);
    var parameters = new Array();
    parameters = (nowAddress.slice(nowAddress.indexOf("?")+1,nowAddress.length)).split("&");
    for(var i = 0 ; i < parameters.length ; i++){
        if(parameters[i].indexOf(valuename) != -1){
            rtnval = parameters[i].split("=")[1];
            if(rtnval == undefined || rtnval == null){
                rtnval = "";
            }
            return rtnval;
        }
    }
}

function replaceAll(sValue, param1, param2) {
    return sValue.split(param1).join(param2); 
}



