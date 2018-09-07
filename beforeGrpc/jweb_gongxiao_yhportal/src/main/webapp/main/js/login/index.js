$(function(){
    function fnToggle(el,imgurl,imgurl2){
        var $el = $("#"+el);
        var defaultValue = $el.val();
        $el.focus(function(){
            $(this).addClass("inputfocus").prev().attr("src",imgurl);
            if(this.value == defaultValue){
                this.value = "";
            }
        });
        $el.blur(function(){
            $(this).removeClass("inputfocus").prev().attr("src",imgurl2);
            if(this.value == ""){
                this.value = defaultValue;
            }
        })
    }
    fnToggle("username","./images/login/user2.png","./images/login/user1.png");
    fnToggle("password","./images/login/pwd2.png","./images/login/pwd1.png");
    //检测密码由6-21位字母和数字组成
    // var regExp = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;

    function loginEvent(){
        var $userName = $("#username");
        var $pwd = $("#password");
        var userName = $userName.val();
        var passWord = $pwd.val();
        //获取当前网址，如：
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录如：/Tmall/index.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如：//localhost:8080
        var localhostPath = curWwwPath.substring(0, pos);
        //发送请求
        $.ajax({
            url: localhostPath + "/user/login",
            type: "POST",
            dataType: "json",
            data:{
                username: userName,
                password: passWord
            },
            success:function(res,status,xhr){
                if(res.returnCode == 0){
                    window.location.href = "/main/purchase/purchase-management.html?v=177c9a4c0a";
                }else{
                    console.log(res.message);
                }
            },
            error: function (XMLHttpRequest) {
                console.log(XMLHttpRequest);
            }
        })
    }

    // 粗略判断输入
    $("#login-btn").click(function(e){
        var oEvent = document.all ? window.event : e;
        oEvent.preventDefault();
        loginEvent();
    });

    // enter事件
    document.onkeydown = function(e){
        var oEvent = document.all ? window.event : e;
        if(oEvent.keyCode === 13) {
            loginEvent();
        }
    }
});