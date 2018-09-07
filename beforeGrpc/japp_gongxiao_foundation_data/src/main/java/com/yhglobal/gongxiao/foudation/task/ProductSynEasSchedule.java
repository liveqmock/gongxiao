package com.yhglobal.gongxiao.foudation.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foudation.basicdata.service.BasicDataSynService;
import com.yhglobal.gongxiao.foudation.model.MaterialInfo;
import com.yhglobal.gongxiao.foundation.product.product.dao.ProductNewBasicDao;
import com.yhglobal.gongxiao.foundation.product.product.dao.ProductNewDao;
import com.yhglobal.gongxiao.foundation.product.product.model.Product;
import com.yhglobal.gongxiao.foundation.product.product.model.ProductBasic;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步EAS TASK
 *
 * @author: 陈浩
 **/
public class ProductSynEasSchedule {

    private Logger logger = LoggerFactory.getLogger(ProductSynEasSchedule.class);

    @Autowired
    ProductNewBasicDao productNewBasicDao;

    @Autowired
    ProductNewDao productNewDao;

    @Autowired
    BasicDataSynService basicDataSynService; //基础数据同步主数据服务类

    /**
     * 只能确定物料是否存在,不能确认物料是否分配给项目
     */

    public void synProduct2EAS() {
        List<ProductBasic> productBasics = productNewBasicDao.selectAllProduct();
        try {
            for (ProductBasic productBasic : productBasics) {
                List<MaterialInfo> materialInfos = basicDataSynService.synProductInfo(productBasic.getEasCode());
                if (materialInfos!=null && materialInfos.size()!=0){
                    //获取该物料下所有未同步到EAS的货品列表
                    List<Product> products = productNewDao.selectProductByEasCode(productBasic.getEasCode());
                    logger.info("物料号={}的货品,未同步给EAS的记录有{}条",productBasic.getEasCode(),products.size());
                    for (Product product:products){
                        Long id = product.getId();
                        productNewDao.updateEasStatus(id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
