define(["/js/common/utils.js"],function(utils) {

	var _Vue;

	var init = function(){
		initVue();
	}
	
	var initVue = function() {
		_Vue = new Vue({
			el : '#form',
			data : {
				form : {
					rememberMe : true
				}
			},
			methods : {
				submit : function() {
					submit();
				},
				reloadCode : reloadCode
			}
		});
	};

	// 初始化事件
	var initEvent = function() {
	};
	
	var submit = function(){
		var data = utils.vueToJsonObj(_Vue.form);
		var callback = function(rt){
			if (rt.code === 0) {
				debugger;
                location.href = '/index';
            } else {
            	reloadCode();
                alert(rt.msg);
            }
		}
		$.post("/login", data, callback);
	}
	
	function reloadCode() {
	    $("#vcode_img").attr("src", "/gifCode?data=" + new Date() + "");
	}

	return {
		init : init
	};

});
