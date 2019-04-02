define(function() {

	//将vue.data转换为对象，去除其他信息
	function vueToJsonObj(obj) {
		if (obj != null) {
			return JSON.parse(JSON.stringify(obj));
		}
		return null;
	}
	//实现类似于java的format
	String.prototype.format = function(args) {
	    var result = this;
	    if (arguments.length > 0) {    
	        if (arguments.length == 1 && typeof (args) == "object") {
	            for (var key in args) {
	                if(args[key]!=undefined){
	                    var reg = new RegExp("({" + key + "})", "g");
	                    result = result.replace(reg, args[key]);
	                }
	            }
	        }
	        else {
	            for (var i = 0; i < arguments.length; i++) {
	                if (arguments[i] != undefined) {
	                    var reg = new RegExp("({[" + i + "]})", "g");
	                    result = result.replace(reg, arguments[i]);
	                }
	            }
	        }
	    }
	    return result;
	}

	return {
		vueToJsonObj : vueToJsonObj
	};

});
