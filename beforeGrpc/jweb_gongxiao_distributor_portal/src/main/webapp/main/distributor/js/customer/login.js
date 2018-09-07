/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/10
 */
$(function() {
    init();

    function focusChangeBackgroundImage(el) {
        var $el = $("#" + el);
        $el.focus(function(){
            $(this).prev("label").addClass("loginfocus");
        });
        $el.blur(function(){
            $(this).prev("label").removeClass("loginfocus");
        })
    }

    function setLoginBoxHeight() {
        var $loginFrames = $("#login-frames");
        var windowHeight = $(document).height();
        var marginTop = -((windowHeight - $loginFrames.height())/2 + 20);
        $loginFrames.css("margin-top",marginTop);
    }


    function getPageElement() {
        var $loginBtn = $("#login-btn");
        var $userName = $("#username");
        var $pwd = $("#pwd");
        return {
            $loginBtn: $loginBtn,
            $userName: $userName,
            $pwd: $pwd
        };
    }

    // 初始化
    function init(){
        setLoginBoxHeight();
        focusChangeBackgroundImage("username");
        focusChangeBackgroundImage("pwd");
        var $pageElement = getPageElement();

        function loginEvent() {
            var loginParams = {
                distributorName: $pageElement.$userName.val(),
                password: $pageElement.$pwd.val()
            };
            // 获取当前网址
            var curWwwPath = window.document.location.href;
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            var localhostPath = curWwwPath.substring(0, pos);
            if(loginParams.distributorName && loginParams.password){
                $.post(localhostPath+"/user/login",loginParams,function (res) {
                    if(res.returnCode === 0){
                        window.location.href = "./accountquery.html";
                    }
                },"json")
            }else{
                alert("请填写账号密码！");
            }
        }

        $pageElement.$loginBtn.click(function (e) {
            var oEvent = document.all ? window.event : e;
            oEvent.preventDefault();
            loginEvent();
        });
        document.onkeydown = function(e){
            var oEvent = document.all ? window.event : e;
            if(oEvent.keyCode === 13){
                loginEvent();
            }
        }
    }
});
 