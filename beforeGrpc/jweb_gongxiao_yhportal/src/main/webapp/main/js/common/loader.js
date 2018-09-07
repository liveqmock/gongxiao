//加载方法
var loader={
    loaderShow:false,
    loaderStyle:"position:fixed;top:0px;left:0px;width:100%;height:100%;z-index:999;background:rgba(0,0,0,0.1);",
    loaderImgStyle:"display:block;vertical-align:middle;position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);width:27px;",
    loaderApi:function(newVal){
        if(newVal){
            var maskHtml = '';
            maskHtml+='<div id="loader" style='+this.loaderStyle+'>';
            maskHtml+='<img style='+this.loaderImgStyle+' src="../images/loader.gif" />';
            maskHtml+='</div>';
            $("body").append(maskHtml);
        }else{
            $("#loader").remove();
        }

    }
};

Object.defineProperty(loader,'loaderShow',{
    set : function(newVal) {
        loader.loaderApi(newVal)
    }
});



//验证消息
var verification={
    emptyShow:false,
    noEmpty:function(originId,targetId,prompt){
        if($("#"+originId).val()==""){
            this.emptyShow=true;
            $("#"+targetId).html(prompt);
        }else{
            $("#"+targetId).empty()
        }
    },
    noEmptySelect:function(originId,targetId,prompt){
        if($("#"+originId).html()==""){
            this.emptyShow=true;
            $("#"+targetId).html(prompt)
        }else{
            $("#"+targetId).empty()
        }
    },
    characterLength:function(originId,targetId,prompt,length){
        if($("#"+originId).val().length>length){
            this.emptyShow=true;
            $("#"+targetId).html(prompt)
        }else{
            $("#"+targetId).empty()
        }
    },
    init:function (targetId) {
        this.emptyShow = false;
        $("#"+targetId).empty()
    },
    valuation:function (targetId,prompt) {
        this.emptyShow = true;
        $("#"+targetId).html(prompt);
    }
};