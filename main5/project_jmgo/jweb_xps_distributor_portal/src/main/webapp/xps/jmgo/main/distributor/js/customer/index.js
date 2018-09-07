(function($, window, document) {
    $.extend({
        getcustomerURL: function () {
            return "";
        },
        renderLoading:function (str) {
            layui.use('layer', function () {
                var layer = layui.layer;
                if(!str){
                    layer.closeAll('loading');
                }else{
                    layer.load(2);
                }
            });
        },
        renderLayerMessage: function (str, iconnum) {
            if(typeof iconnum === 'undefined'){
                // layui.use(['layer'], function () {
                //     var layer = layui.layer;
                //     layer.msg(str, {icon: iconnum});
                // });
                layer.msg(str, {icon: iconnum});
            }else{
                // layui.use(['layer'], function () {
                //     var layer = layui.layer;
                //     layer.msg(str);
                // });
                layer.msg(str);
            }
            // if (iconnum) {
            //     layui.use(['layer'], function () {
            //         var layer = layui.layer;
            //         layer.msg(str, {icon: iconnum});
            //     });
            // } else {
            //     layui.use(['layer'], function () {
            //         var layer = layui.layer;
            //         layer.msg(str);
            //     });
            // }
        },
    })


}(jQuery, window, document))