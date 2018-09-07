package com.yhglobal.gongxiao.foundation.brand.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.foundation.product.brand.model.Brand;
import com.yhglobal.gongxiao.foundation.product.brand.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基础数据--品牌
 *
 * @Author: 葛灿
 * @Date:2018/2/1--19:53
 */
@Controller
@RequestMapping("/foundation/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    /**
     * 返回所有的商家品牌
     *
     * @param response
     * @return java.util.List<com.yhglobal.gongxiao.foundation.product.productBasic.ProductBasic>
     */
    @RequestMapping(value = "/listBrand", method = RequestMethod.GET)
    @ResponseBody
    private GongxiaoResult getBrandList(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //返回所有的商家品牌
//        List<Brand> brands = brandService.selectAll();
        GongxiaoResult<List<Brand>> gongxiaoResult = new GongxiaoResult();
//        if (brands != null) {
//            gongxiaoResult.setReturnCode(200);
//            gongxiaoResult.setData(brands);
//        }
        return gongxiaoResult;
    }

}
