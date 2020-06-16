$( document ).ready(function() {
	$('#cssmenu li.has-sub>a').on('click', function(){
		$(this).removeAttr('href');
		var element = $(this).parent('li');
		if (element.hasClass('open')) {
			element.removeClass('open');
			element.find('li').removeClass('open');
			element.find('ul').slideUp();
		}
		else {
			element.addClass('open');
			element.children('ul').slideDown();
			element.siblings('li').children('ul').slideUp();
			element.siblings('li').removeClass('open');
			element.siblings('li').find('li').removeClass('open');
			element.siblings('li').find('ul').slideUp();
		}
	});

	$('#cssmenu>ul>li.has-sub>a').append('<span class="holder"></span>');

	(function getColor() {
		var r, g, b;
		var textColor = $('#cssmenu').css('color');
		textColor = textColor.slice(4);
		r = textColor.slice(0, textColor.indexOf(','));
		textColor = textColor.slice(textColor.indexOf(' ') + 1);
		g = textColor.slice(0, textColor.indexOf(','));
		textColor = textColor.slice(textColor.indexOf(' ') + 1);
		b = textColor.slice(0, textColor.indexOf(')'));
		var l = rgbToHsl(r, g, b);
		if (l > 0.7) {
			$('#cssmenu>ul>li>a').css('text-shadow', '0 1px 1px rgba(0, 0, 0, .35)');
			$('#cssmenu>ul>li>a>span').css('border-color', 'rgba(0, 0, 0, .35)');
		}
		else
		{
			$('#cssmenu>ul>li>a').css('text-shadow', '0 1px 0 rgba(255, 255, 255, .35)');
			$('#cssmenu>ul>li>a>span').css('border-color', 'rgba(255, 255, 255, .35)');
		}
	})();

	function rgbToHsl(r, g, b) {
	    r /= 255, g /= 255, b /= 255;
	    var max = Math.max(r, g, b), min = Math.min(r, g, b);
	    var h, s, l = (max + min) / 2;

	    if(max == min){
	        h = s = 0;
	    }
	    else {
	        var d = max - min;
	        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
	        switch(max){
	            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
	            case g: h = (b - r) / d + 2; break;
	            case b: h = (r - g) / d + 4; break;
	        }
	        h /= 6;
	    }
	    return l;
	}
	
});

jQuery(function($){
	$.fn.topmenu= function(options) {
		var opts = $.extend(options);
		
		if(opts.d1) {
			$("#top_"+opts.d1).addClass("current");
			$("#top_"+opts.d1).siblings().removeClass("current");
			$("#"+opts.d1).addClass("active");
			if(opts.d2) {
				$("#" + opts.d1).find('ul').slideDown();
				$("#" + opts.d2).addClass("active");
				
			}  
		}  
	},
	
	$(".has-sub").each(function(){  
		var liCnt = $(this).find("li").length;
		if(liCnt <= 0){ 
			$(this).remove();
			var id = $(this).attr("id");
			$("#top_" + id).remove();  
		}	
	});
	
	$("[id ^='top_']").each(function(){  
		var topId = $(this).attr("id");   
		var leftId = topId.substring(topId.indexOf("_") + 1, topId.length);
		
		if($("#" + leftId).length <= 0){
			$("#top_" + leftId).remove(); 
		}
	});
	
	$(".has-sub").find("li").mouseover(function() {     
	    $(this).find("a").css("background","#00bfFf");                   
	  }).mouseout(function() { 
		  $(this).find("a").css("background","");    
	  });
	
	if(typeof messageCall != "undefined"){    
		 messageCall();
	} 

	
	
});

