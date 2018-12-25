define([ "/js/common/utils.js" ], function(utils) {

	var _Form_Vue;

	var init = function() {
		initRole();
		initVue();
		initEvents();
	}
	var initVue = function() {
		_Form_Vue = new Vue({
			el : '#user-add-form',
			data : {
				form : {}
			},
			methods : {}
		});
	}
	var initEvents = function() {
		$("#user-add .btn-save").click(
				function() {
					var name = $(this).attr("name");
					//var validator = $userAddForm.validate();
					//var flag = validator.form();
					var flag = true;
					if (flag) {
						if (name === "save") {
							ajaxJson("/user/add", _Form_Vue.form,
									function(r) {
										if (r.code === 0) {
											closeModal();
											$MB.n_success(r.msg);
											$MB.refreshTable("userTable");
										} else
											$MB.n_danger(r.msg);
									});
						}
						if (name === "update") {
							ajaxJson("/user/update", _Form_Vue.form, function(r) {
								debugger;
								if (r.code === 0) {
									closeModal();
									$MB.n_success(r.msg);
									$MB.refreshTable("userTable");
								} else
									$MB.n_danger(r.msg);
							});
						}
					}
				});
		$("#user-add .btn-close").click(function() {
			closeModal();
		});
	}

	function initRole() {
		$.post("/role/listDate", {}, function(r) {
			var data = r.rows;
			var option = "";
			for (var i = 0; i < data.length; i++) {
				option += "<option value='" + data[i].id + "'>"
						+ data[i].roleName + "</option>"
			}
			var $rolesSelect = $("#user-add-form").find(
					"select[name='rolesSelect']");
			$rolesSelect.html("").append(option);
			var options = {
				selectAllText : '所有角色',
				allSelected : '所有角色',
				width : '100%',
				onClose : function() {
					//$("#user-add-form").find("input[name='roles']").val($rolesSelect.val());
					var selecteds = $rolesSelect.val();
					if(selecteds){
						_Form_Vue.form.roles = selecteds.reduce(function(arr, cur) {
							obj = {id:cur}
							arr.push(obj);
							return arr;
						}, []);
					}
					// validator.element("input[name='roles']");
				}
			};
			$rolesSelect.multipleSelect(options);
		});
	}

	function addUser() {
		$('#user-add').modal();
		$("#user-add-button").attr("name", "save");
		$("#user-add-modal-title").html('新增用户');
		$("#user-add-form").find("select[name='rolesSelect']").multipleSelect('setSelects',[]);
		$("#user-add-form").find("select[name='rolesSelect']").multipleSelect("refresh");
	}

	function updateUser() {
		var selected = $("#userTable").bootstrapTable('getSelections');
		var selected_length = selected.length;
		if (!selected_length) {
			$MB.n_warning('请勾选需要修改的用户！');
			return;
		}
		if (selected_length > 1) {
			$MB.n_warning('一次只能修改一个用户！');
			return;
		}
		var id = selected[0].id;
		$.post("/user/getUser", {
			"id" : id
		}, function(r) {
			if (r.code === 0) {
				$('#user-add').modal();
				var user = r.msg;
				_Form_Vue.form = user;
				var roleArr = [];
				for (var i = 0; i < user.roles.length; i++) {
					roleArr.push(user.roles[i].id);
				}
				$('#user-add').find("select[name='rolesSelect']")
						.multipleSelect('setSelects', roleArr);
				$('#user-add').find("input[name='roles']")
						.val(
								$('#user-add').find(
										"select[name='rolesSelect']").val());
				$("#user-add-button").attr("name", "update");
				$("#user-add-modal-title").html('编辑用户');
			} else {
				$MB.n_danger(r.msg);
			}
		});
	}

	function closeModal() {
		// validator.resetForm();
		/*
		 * $("#user-add-form").find("select[name='rolesSelect']").multipleSelect('setSelects',
		 * []);
		 * $("#user-add-form").find("select[name='rolesSelect']").multipleSelect("refresh");
		 * $("#user-add-form").find("input[name='username']").removeAttr("readonly");
		 * $("#user-add-form").find(".user_password").show();
		 */
		$MB.closeAndRestModal("user-add");
	}

	function ajaxJson(url,jsonObj,cb,ecb){
		jsonObj["pt"] = new Date().getTime();
		$.ajax({
			url:url,
			type: "post",
			dataType: "json",
			data: JSON.stringify(jsonObj),
			contentType: "application/json",
			success:cb,
			error : ecb
		});
	}
	
	return {
		init : init,
		addUser : addUser,
		updateUser : updateUser
	};

});
