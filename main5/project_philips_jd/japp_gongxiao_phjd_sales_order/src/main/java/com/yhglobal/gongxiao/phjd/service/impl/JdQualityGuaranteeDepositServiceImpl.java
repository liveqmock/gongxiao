package com.yhglobal.gongxiao.phjd.service.impl;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.phjd.dao.JdQualityGuaranteeDepositDao;
import com.yhglobal.gongxiao.phjd.model.JdQualityGuaranteeDeposit;
import com.yhglobal.gongxiao.phjd.service.JdQualityGuaranteeDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王帅
 */
@Service
public class JdQualityGuaranteeDepositServiceImpl {

    static Logger logger = LoggerFactory.getLogger(JdQualityGuaranteeDepositServiceImpl.class);

    @Autowired
    JdQualityGuaranteeDepositDao jdQualityGuaranteeDepositDao;

//    @Override
//    public PageInfo<JdQualityGuaranteeDeposit> selectByManyConditions(String settlementNo,
//                                                               Integer documentCode,
//                                                               Integer depositStatus,
//                                                               Long jdProjectId){
//
//        return null;
//    }
}
