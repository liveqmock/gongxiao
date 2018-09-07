$(function () {
    function fnToggle(el, imgurl, imgurl2) {
        var $el = $("#" + el);
        var defaultValue = $el.val();
        $el.focus(function () {
            $(this).addClass("inputfocus").prev().attr("src", imgurl);
            if (this.value == defaultValue) {
                this.value = "";
            }
        });
        $el.blur(function () {
            $(this).removeClass("inputfocus").prev().attr("src", imgurl2);
            if (this.value == "") {
                this.value = defaultValue;
            }
        })
    }

    fnToggle("username", "./images/login/user2.png", "./images/login/user1.png");
    fnToggle("password", "./images/login/pwd2.png", "./images/login/pwd1.png");
    //检测密码由6-21位字母和数字组成
    // var regExp = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;

    function YHtrim(str) { //删除左右两端的空格
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }

    function loginEvent() {
        var $userName = $("#username");
        var $pwd = $("#password");
        var userName = YHtrim($userName.val());
        var passWord = YHtrim($pwd.val());
        var curWwwPath = window.document.location.href;
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        var localhostPath = curWwwPath.substring(0, pos);

        if (!userName) {
            // 请输入用户名
            $("#username-verification").html("* 请输入用户名");
            return false;
        } else {
            $("#username-verification").empty();
        }

        if (!passWord) {
            // 请输入密码
            $("#password-verification").html("* 请输入密码");
            return false;
        } else {
            $("#password-verification").empty();
        }

        $.ajax({
            url: localhostPath + "/login/checkAccount",
            type: "POST",
            dataType: "json",
            data: {
                userName: userName,
                passWord: passWord
            },
            success: function (res) {
                if (res.returnCode == 0) {
                    window.location.href = "./purchase/purchase-management.html?v=59c52ad2e7";
                } else if (res.returnCode == 1500) {
                    // 用户不存在
                    $("#username-verification").html("* " + res.message);
                } else if (res.returnCode == 1501) {
                    // 密码错误
                    $("#password-verification").html("* " + res.message);
                } else {
                    $.renderLayerMessage(res.message);
                }
            },
            error: function (XMLHttpRequest) {
                console.log(XMLHttpRequest);
            }
        })

    }

    // 粗略判断输入
    $("#login-btn").click(function (e) {
        var oEvent = document.all ? window.event : e;
        oEvent.preventDefault();
        loginEvent();
    });

    // enter事件
    document.onkeydown = function (e) {
        var oEvent = document.all ? window.event : e;
        if (oEvent.keyCode === 13) {
            loginEvent();
        }
    }
});