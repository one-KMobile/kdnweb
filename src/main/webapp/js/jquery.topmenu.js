jQuery(function($){
	$.fn.topmenu= function(options) {
		var menu_id = $.extend(options).d2;
		
		var opts = $.extend(options);
		var topmenu = $(this);
		var topmenuList = topmenu.find('>ul>li');
		var submenu = topmenu.find('.submenu');
		//var submenuList = submenu.find('>ul>li');

		function showMenu() {
			t = $(this).parent('li');
			if (!t.hasClass('active')) {   
				topmenuList.removeClass('active');
				t.addClass('active'); 
				submenu.hide();
				t.find('.submenu').show().css('top', 0).animate( { top: 40 }, 200 );
			}
		}
		 
		function hideMenu() {
			topmenuList.removeClass('active');
			submenu.hide();
			activeMenu();
		}

		function activeMenu() {
			if(opts.d1) {
				var t = topmenu.find("#"+opts.d1);
				//var submenuList = submenu.find('>ul>li');
				//t = topmenuList.eq(opts.d1-1);
				//$('li[id='+opts.d2+']').addClass('active');
				t.addClass('active');
				//t = topmenuList.find(opts.d1);
				//t.addClass('active');
				t.find('.submenu').show().css('top', 0).animate( { top: 40 }, 200 ); 
				//$("#gnb li").eq(opts.d1).find("a").addClass('topOn'); //상단 메뉴
				$("#gnb li").find("#"+opts.d1).addClass('topOn');  //상단메뉴
				
				if(opts.d2) {
					//t.find('.submenu>ul>li>a').eq(opts.d2-1).addClass('on');
					t.find('.submenu>ul').find('#'+opts.d2).addClass('on');
				}
			}
		}

		return this.each(function() {   
			activeMenu();
			//topmenuList.find('>a').mousedown(showMenu).focus(showMenu);
			//topmenu.mouseleave(hideMenu);  
		});
	}, 
	
	$("[id *= 'subMenuList'").each(function(){
		var liCnt = $(this).find("li").length;
		if(liCnt <= 0){ 
			$(this).parent().parent().remove();
		}	
	});
	
	$(".active #subMenuList").find("li").mouseover(function() {     
	    $(this).find("a").css("background","#00bfFf");                   
	  }).mouseout(function() { 
		  $(this).find("a").css("background",""); 
	  });
	
	$(".lnb_d1").unbind("click").bind("click",function(){    
		if($(this).parent().find(".submenu").css("display") == "none"){ 
			$(this).parent().find(".submenu").css("display", "block");            
		}else{
			$(this).parent().find(".submenu").css("display", "none");   
		}
	}); 
});