package com.yhglobal.gongxiao.phjd.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.phjd.model.JdQualityGuaranteeDeposit;

/**
 * @author 王帅
 */
public interface JdQualityGuaranteeDepositService {

    public PageInfo<JdQualityGuaranteeDeposit> selectByManyConditions(String settlementNo,
                                                                      Integer documentCode,
                                                                      Integer depositStatus,
                                                                      Long jdProjectId);
}
