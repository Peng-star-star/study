define([ "/js/common/utils.js",
         "/js/app/user/userAdd.js"], function(utils,add) {

	var _Search_Vue;

	var init = function() {
		add.init();
		initVue();
		initEvents();
		initTable();
	}
	var initEvents = function() {
		$("#search_button").bind('click', function() {
			search();
		});
		$("#refresh_button").bind('click', function() {
			refresh();
		});
		$("#add_button").bind('click',function(){
			add.addUser();
		});
		$("#update_button").bind('click', function() {
			add.updateUser();
		});
		$("#delete_button").bind('click', function() {
			deleteUsers();
		});
	}
	initVue = function() {
		_Search_Vue = new Vue({
			el : '#data-table_filter',
			data : {
				params : {}
			},
			methods : {}
		});
	}

	initTable = function() {
		var settings = {
			url : "/user/listDate",
			pageSize : 10,
			queryParams : function(params) {
				return {
					pageSize : params.limit,
					pageNum : params.offset / params.limit + 1,
					userName : _Search_Vue.params.userName,
				};
			},
			columns : [ {
				checkbox : true
			}, {
				field : 'id',
				visible : false
			}, {
				field : 'userName',
				title : '用户名'
			}, {
				field : 'name',
				title : '姓名'
			}, {
				field : 'createTime',
				title : '创建时间'
			} ]
		};
		$MB.initTable('userTable', settings);
	}

	function search() {
		$MB.refreshTable('userTable');
	}

	function refresh() {
		$(".user-table-form")[0].reset();
		$MB.refreshTable('userTable');
	}

	function deleteUsers() {
	    var selected = $("#userTable").bootstrapTable('getSelections');
	    var selected_length = selected.length;
	    var contain = false;
	    if (!selected_length) {
	        $MB.n_warning('请勾选需要删除的用户！');
	        return;
	    }
	    var ids = "";
	    for (var i = 0; i < selected_length; i++) {
	        ids += selected[i].id;
	        if (i !== (selected_length - 1)) ids += ",";
	        if (userName === selected[i].username) contain = true;
	    }
	    if (contain) {
	        $MB.n_warning('勾选用户中包含当前登录用户，无法删除！');
	        return;
	    }

	    $MB.confirm({
	        text: "确定删除选中用户？",
	        confirmButtonText: "确定删除"
	    }, function () {
	        $.post('/user/delete', {"ids": ids}, function (r) {
	            if (r.code === 0) {
	                $MB.n_success(r.msg);
	                refresh();
	            } else {
	                $MB.n_danger(r.msg);
	            }
	        });
	    });
	}
	
	return {
		init : init
	};

});
