package com.yhglobal.gongxiao.inventory.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.inventory.service.InserInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 插入库存数据的工具类controller
 * @author liukai
 */
@RequestMapping("/inventory")
@Controller
public class InSertInventoryController {

    private static Logger logger = LoggerFactory.getLogger(InSertInventoryController.class);

    @Reference
    InserInventoryService inserInventoryService;

    @RequestMapping("/insert")
    @ResponseBody
    public GongxiaoResult insertInventory(HttpServletRequest request){
        logger.info("[IN] [insertInventory]");
        GongxiaoResult result = new GongxiaoResult();
        try {
            int i = inserInventoryService.insertInventory();
            logger.info("[OUT] get insertInventory success");
            return new GongxiaoResult(0,"SUCCESS");
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(1, ErrorCode.UNKNOWN_ERROR);
        }

    }
}
