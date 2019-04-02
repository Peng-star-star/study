define([ "/js/common/utils.js" ], function(utils) {

	var $breadcrumb = $(".breadcrumb");
	var $main_content = $(".main-content");
	var $navigation = $("#navigation");

	var redisMemoryInfoInterval;
	var rediskeysSizeInterval;

	var init = function() {
		$(window).on("load", function() {
			// 加载loading
			setTimeout(function() {
				$(".page-loader").fadeOut();
			}, 500)
		})
		$(function(){changeFrameHeight();});
		initMenuTree();
		initEvents();
	}
	var initEvents = function() {
		initTabClose();
		window.onresize=function(){ 
			changeFrameHeight();
		}
	}
	var initTabClose = function(){
		$('.tab-close').on('click', function(ev) {
	        var ev=window.event||ev;
	        ev.stopPropagation();
	        //先判断当前要关闭的tab选项卡有没有active类，再判断当前选项卡是否最后一个，如果是则给前一个选项卡以及内容添加active，否则给下一个添加active类
	        var gParent=$(this).parent().parent(),
	            parent=$(this).parent();
	        if(gParent.hasClass('active')){
	            if(gParent.index()==gParent.length){
	                gParent.prev().addClass('active');
	                $(parent.attr('href')).prev().addClass('active');
	            }else{
	                gParent.next().addClass('active');
	                $(parent.attr('href')).next().addClass('active');
	            } 
	        }
	        gParent.remove();
	        $(parent.attr('href')).remove();
	    });
	}
	
	var loadMain = function(tabId,tabName,contentId,url){
		var $mainTabs =$("#main_tabs");
		//删除其他标签action
		var activeLi = $mainTabs.find(".active");
		activeLi.removeClass('active');
		$(activeLi.find('a').attr('href')).removeClass('active');
		//增加标签
		addTitle(tabId,tabName,contentId,url);
	}
	
	var addTitle = function(tabId,tabName,contentId,url){
		//重复的刷新，非重复的增加
		if($("#main_tabs").find("#"+tabId).length == 0){
			var tabs_temp="<li class=\"active\" id=\"{0}\"><a href=\"#{1}\" data-toggle=\"tab\">{2}"+
			"<i class=\"glyphicon glyphicon-remove tab-close\"></i></a></li>";
			var tab_content_temp ="<div class=\"tab-pane fade in active\" id=\"{0}\">{1}</div>";
			var content_temp = "<iframe src=\"{0}\" width=\"100%\" height=\"1000px\" frameborder=\"0\"></iframe>";
			var content = content_temp.format(url);
			$("#main_tabs").append(tabs_temp.format(tabId,contentId,tabName));
			$(".tab-content").append(tab_content_temp.format(contentId,content));
			initTabClose();
		}else{
			$("#main_tabs").find("#"+tabId).addClass('active');
			var $content =$($("#main_tabs").find("#"+tabId).find("a").attr('href'));
			$content.addClass("in active");
			$content.find("iframe").attr('src', $content.find("iframe").attr('src'));
		}
		changeFrameHeight();
	}
	
	function changeFrameHeight(){
        var height=document.documentElement.clientHeight-220;
        $("iframe").css("height", height);
    }
	
	function initMenuTree(){
		var str = "";
		var forTree = function (o) {
	        for (var i = 0; i < o.length; i++) {
	            var urlstr = "";
	            try {
	                if (!o[i]["url"]) {
	                    urlstr = "<div><span><i class='" + o[i]["icon"] + "'></i>&nbsp;&nbsp;" + o[i]["text"] + "</span><ul>";
	                } else {
	                	var tabId = "'menu_"+o[i]["id"]+"_tab'";
	                	var tabName = "'"+o[i]["text"]+"'";
	                	var contentId =  "'menu_"+o[i]["id"] + "_tab_content'";
	                	var url ="'"+ o[i]["url"]+"'";
	                	var click = "loadMain("+tabId+","+tabName+","+contentId+","+url+");";
	                    urlstr = "<div><span name=" + o[i]["url"] + " onclick="+click+"><i class='" + o[i]["icon"] + "'></i>&nbsp;&nbsp;" + o[i]["text"] + "</span><ul>";
	                }
	                str += urlstr;
	                if (o[i]["children"].length !== 0) {
	                    forTree(o[i]["children"]);
	                }
	                str += "</ul></div>";
	            } catch (e) {
	                console.log(e);
	            }
	        }
	        return str;
	    };
	    var menuTree = function() {
			var $navigation =$("#navigation");
			$navigation.find("ul").each(function(index, element) {
				var ulContent = $(element).html();
				var spanContent = $(element).siblings("span").html();
				if (ulContent) {
					$(element).siblings("span").html(spanContent)
				}
			});
			$navigation.find("div span").click(function() {
				var ul = $(this).siblings("ul").hide(300);
				if (ul.find("div").html() != null) {
					if (ul.css("display") === "none") {
						ul.show(300);
					} else {
						ul.hide(300);
					}
				}
			});
			$navigation.find("div>span").click(function() {
				var ul = $(this).parent().siblings().find(">ul");
				ul.hide(300);
			})
		};
		$.post("/menu/getMenuTree", null, function (r) {
	        if (r.code === 0) {
	            var data = r.msg;
	            var $crollbarInner = $(".scrollbar-inner");
	            document.getElementById("navigation").innerHTML = forTree(data.children);
	            menuTree();
	        } else {
	            //$MB.n_danger(r.msg);
	        }
	    })
	}
	
	return {
		init : init,
		loadMain :loadMain
	};

});
