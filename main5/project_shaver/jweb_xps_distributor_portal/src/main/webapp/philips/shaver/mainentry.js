/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/30
 */

import jQuery from 'jquery';
import {Promise} from 'es6-promise';
import './distributor/js/bootstrap.min.js';
import './distributor/js/bootstrap-table.js';
import './distributor/js/bootstrap-table-zh-CN.js';



Promise.polyfill();

(function($, window, document) {
    function transformDoubble(num, length) {
        return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
    }
    $.extend({
        transFormTime: function(time, seconds) {
            var tempTime = new Date(time);
            var confirmTime = tempTime.getFullYear() + "/" + transformDoubble((tempTime.getMonth() + 1),2) + "/" + transformDoubble(tempTime.getDate(),2);
            if (time && !seconds) {
                return confirmTime;
            } else if (time && seconds) {
                confirmTime += " " + transformDoubble(tempTime.getHours(),2) + ":" + transformDoubble(tempTime.getMinutes(),2) + ":" + transformDoubble(tempTime.getSeconds(),2);
                return confirmTime;
            } else {
                return ""
            }
        },
        getInfoFromUrl: function (name){
            var search = document.location.search;
            var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
            var matcher = pattern.exec(search);
            var items = null;
            if (null != matcher) {
                try {
                    items = decodeURIComponent(decodeURIComponent(matcher[1]));
                } catch (e) {
                    try {
                        items = decodeURIComponent(matcher[1]);
                    } catch (e) {
                        items = matcher[1];
                    }
                }
            }
            return items;
        },
        getSelectPreferAddress: function(url,el,params){
            // 收货地址
            var $submitOrderAddress = $("#"+el);
            $submitOrderAddress.html("");
            return new Promise(function (resolve,reject) {
                $.ajax({
                    url: url,
                    dataType: "json",
                    data: params,
                    success: function (res) {
                        resolve(res);
                        if(res.returnCode === 0){
                            var orderAddress = res.data;
                            var addressList = "";
                            for(var i =0;i<orderAddress.length;i++){
                                addressList += '<div class="shipping-address"><h4>'+ orderAddress[i].receiver+'</h4><h5><input type="text" value="'+orderAddress[i].provinceName+orderAddress[i].cityName+orderAddress[i].districtName+orderAddress[i].streetAddress+'"></h5>'+'<p><input type="text" value="'+orderAddress[i].cityName +'"></p></div>';
                            }
                            $submitOrderAddress.append(addressList);
                        }
                    },
                    error: function (xhr) {
                        reject(xhr);
                        console.log("失败！");
                    }
                });
            });
        },
        getSubmitOrderAddress: function(url,params){
            return new Promise(function (resolve,reject){
                $.get(url,params,function (res) {
                    if(res.returnCode === 0){
                        resolve(res);
                    }else{
                        reject(res);
                    }
                },"json")
            })
        },
        getcustomerURL: function () {
            return "";
        }
    })
}(jQuery, window, document));

$(function () {
    $.fn.bootstrapTable.defaults['len'] = {
        valign: 'middle',
        align: 'center'
    };

    $.extend($.fn.bootstrapTable.columnDefaults, $.fn.bootstrapTable.defaults['len']);

    // 主体内容导航
    var $contentNav = $("#content-nav");
    $contentNav.on("click", "a", function () {
        $(this).addClass("navlinked").siblings().removeClass("navlinked");
    });
});