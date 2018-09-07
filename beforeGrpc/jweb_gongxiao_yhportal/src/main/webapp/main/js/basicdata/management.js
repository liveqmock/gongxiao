/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-02-28 11:13:56
 * @version $Id$
 */
$(function() {





    var $basicDataManageTable = $("#yhtable");
    var $basicDataManageQueryBtn = $("#yhquery-btn");
    

    function getValue(){
    	var $basicItemId = $("#basicitemid");
    	var $basicItem = $("#basicitem");
    	var obj = {
    		itemId: $basicItemId.val(),
    		item: $basicItem.val()
    	}
    	return obj;
    }


    function init() {
        renderTable();
        //基础数据管理查询
        $basicDataManageQueryBtn.click(function() {
        	var basicItem = getValue();
		    $.ajax({
                type: 'GET',
                url: "./json/brand2.json",
                data: {
                    itemId: basicItem.itemId,
                    item: basicItem.item
                },
                success: function (res) {
                    if(res.returnCode === 0){
                        var basicLoadtableData = data.data;
                        $basicDataManageTable.bootstrapTable('load', basicLoadtableData);
                    }
                }
            })
        })
    }
    init();

    //渲染表格
    function renderTable() {
        var tableData = [{}];
        $basicDataManageTable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: tableData,
            columns: [{
            	checkbox: true
            },{
                field: 'productName',
                title: '项目ID',
                class: 'proname'
            }, {
                field: 'productCode',
                title: '项目名称',
            }, {
                field: 'id',
                title: '公司主体',
            }, {
                field: 'brandName',
                title: '品牌商',
            }, {
                field: 'categoryName',
                title: '月度返利'
            }, {
                field: 'childName',
                title: '季度返利',
            }, {
                field: 'grade',
                title: '年度返利',
            }, {
                field: 'barCode',
                title: '操作人',
            }, {
                field: 'productType',
                title: '最后修改时间',
                formatter: function(value, row, index) {
                    if (!value) {
                        return '未知'
                    }
                    return value
                }
            }, {
                title: '操作',
                width: 90,
                //events: operateEvents,
                //formatter: operateFormatter
            }]
        })
    };

})