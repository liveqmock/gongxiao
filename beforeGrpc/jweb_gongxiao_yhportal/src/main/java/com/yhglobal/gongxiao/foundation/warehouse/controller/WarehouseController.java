package com.yhglobal.gongxiao.foundation.warehouse.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基础数据--仓库
 *
 * @Author: 葛灿
 * @Date:2018/2/1--19:53
 */
@Controller
@RequestMapping("/foundation/warehouse")
public class WarehouseController {
    @Reference
    private WarehouseService warehouseService;

    /**
     * 返回所有的仓库
     *
     * @param response
     * @return java.util.List<com.yhglobal.gongxiao.foundation.product.productBasic.ProductBasic>
     * @author 葛灿
     * @date 2018/2/5 17:06
     */
    @RequestMapping(value = "/listWarehouse", method = RequestMethod.GET)
    @ResponseBody
    private List<Warehouse> getProductCategoryList(HttpServletResponse response) {
        //返回所有的仓库
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return null;
//        return warehouseService.selectAll();
    }

}
