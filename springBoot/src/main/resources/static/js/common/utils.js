define(function() {

	//将vue.data转换为对象，去除其他信息
	function vueToJsonObj(obj) {
		if (obj != null) {
			return JSON.parse(JSON.stringify(obj));
		}
		return null;
	}

	return {
		vueToJsonObj : vueToJsonObj
	};

});
