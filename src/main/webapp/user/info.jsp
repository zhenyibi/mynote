
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心</div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-8">
                    <%-- 表单类型：enctype="multipart/form-data"   提交方式：post --%>
                    <form class="form-horizontal" method="post" action="user?actionName=userCenter" enctype="multipart/form-data">
                        <div class="form-group">
                            <%-- 设置隐藏域存放用户行为actionName --%>
                            <input type="hidden" name="updateUserActionName" value="updateUser">
                            <label for="nickName" class="col-sm-2 control-label">昵称:</label>
                            <div class="col-sm-3">
                                <input class="form-control" name="nick" id="nickName" placeholder="昵称"
                                       value="${user.nick}">
                            </div>
                            <label for="img" class="col-sm-2 control-label">头像:</label>
                            <div class="col-sm-5">
                                <input type="file" id="img" name="img">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="mood" class="col-sm-2 control-label">心情:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" name="mood" id="mood" rows="3">${user.mood}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" id="btn" class="btn btn-success" onclick="return updateUser()">
                                    修改
                                </button>&nbsp;&nbsp;
                                <span style="color:red; font-size: 12px" id="msg"></span>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4">
                    <img src="user?actionName=userHead&imageName=${user.head}" style="width:240px;height:180px">
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    /**
     * 验证昵称的唯一性：
     * --昵称文本框的失焦事件  blur
     * ----1.获取昵称文本框的值
     * ----2.判断值是否为空
     * --------如果为空，提示用户，禁用按钮，并return
     * ----3.判断昵称是否做了修改
     * --------从session作用域中获取用户昵称 （如果在js中想要使用el表达式获取域对象，js需要写在JSP页面中，无法在js文件中获取）
     * --------如果用户昵称与session中的昵称一致，则return
     * ----4.如果昵称做了修改
     * --------发送ajax请求后台，验证昵称是否可用
     * ----------如果不可用，提示用户，并禁用按钮
     * ----------如果可用，清空提示信息，按钮可用
     *
     * --昵称文本框的聚焦事件  focus
     * ----1.清空提示信息
     * ----2.按钮可用
     */
    $("#nickName").blur(function () { // blur：失焦事件
        // 1.获取昵称文本框的值
        var nickName = $("#nickName").val();
        // 2.判断值是否为空，使用util.js工具类里的判空方法
        if (isEmpty(nickName)) {
            // 如果为空，提示用户，禁用按钮，并return
            $("#msg").html("用户昵称不能为空！");
            $("#btn").prop("disabled", true); // prop()方法：设置标签属性的值，如：这里设置 disable=true
            return;
        }
        // 3.判断昵称是否做了修改
        // 先从session作用域中获取用户昵称，在这里使用了el表达式来获取
        var nick = '${user.nick}';
        // 如果用户昵称与session中的昵称一致（即没有修改），则return
        if (nickName == nick) { // 如果没有做修改
            return;
        }
        // 4.如果昵称做了修改
        // 发送ajax请求后台，验证昵称是否可用
        $.ajax({
            type: "get", // 请求类型
            url: "user", // 请求提交地址
            data: {
                actionName: "checkNick", // 用户行为参数
                nick: nickName // 需要修改为昵称的参数
            },
            success: function (code) { // result：请求返回结果内容
                // 如果可用，清空提示信息，按钮可用
                if (code == 1) {
                    // 1.清空提示信息
                    $("#msg").html("");
                    // 2.按钮可用
                    $("#btn").prop("disabled", false);
                } else { // 如果不可用，提示用户，并禁用按钮
                    // 1.提示用户
                    $("#msg").html("该昵称已存在，请重新输入！");
                    // 2.禁用按钮
                    $("#btn").prop("disabled", true);
                }
            }
        });
    }).focus(function () { // focus：聚焦事件
        // 1.清空提示信息
        $("#msg").html("");
        // 2.按钮可用
        $("#btn").prop("disabled", false);
    });

    /**
     * 表单提交校验：
     * --满足条件，返回true，表示提交表单
     * --不满足条件，返回false，表示不提交表单
     * @returns {boolean}
     */
    function updateUser() {
        // 获取昵称文本框的值
        var nickName = $("#nickName").val();
        // 判断值是否为空
        if (isEmpty(nickName)) {
            // 如果为空，提示用户，禁用按钮，并return false，不提交表单
            $("#msg").html("用户昵称不能为空！");
            $("#btn").prop("disabled", true);
            return false;
        }
        // 唯一性校验 TODO
        return true; // 返回true，表示提交表单
    }
</script>
