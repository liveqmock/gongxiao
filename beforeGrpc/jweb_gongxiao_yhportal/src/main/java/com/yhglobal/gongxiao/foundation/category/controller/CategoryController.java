package com.yhglobal.gongxiao.foundation.category.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.foundation.product.category.model.ProductCategory;
import com.yhglobal.gongxiao.foundation.product.category.service.ProductCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基础数据--品类
 *
 * @Author: 葛灿
 * @Date:2018/2/1--19:53
 */
@Controller
@RequestMapping("/foundation/category")
public class CategoryController {
    @Reference
    private ProductCategoryService productCategoryService;

    /**
     * 返回所有的商家品类
     *
     * @param response
     * @return java.util.List<com.yhglobal.gongxiao.foundation.product.productBasic.ProductBasic>
     */
    @RequestMapping(value = "/listSellerCategory", method = RequestMethod.GET)
    @ResponseBody
    private GongxiaoResult getBusinessProductTypeList(HttpServletResponse response) {
        //返回所有的商家品类
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        List<ProductCategory> productCategories = productCategoryService.selectAll();
        GongxiaoResult<List<ProductCategory>> gongxiaoResult = new GongxiaoResult<>();
//        if (productCategories != null && productCategories.size() != 0) {
//            gongxiaoResult.setReturnCode(200);
//            gongxiaoResult.setData(productCategories);
//        } else {
//            gongxiaoResult.setMessage("暂无数据");
//        }
        return gongxiaoResult;
    }
}
