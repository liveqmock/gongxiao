package com.yhglobal.gongxiao.phjd.dao;

import com.yhglobal.gongxiao.phjd.dao.mapper.JdQualityGuaranteeDepositMapper;
import com.yhglobal.gongxiao.phjd.model.JdQualityGuaranteeDeposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class JdQualityGuaranteeDepositDao {

    @Autowired
    JdQualityGuaranteeDepositMapper jdQualityGuaranteeDepositMapper;

    public List<JdQualityGuaranteeDeposit> selectByManyConditions(String settlementNo,
                                                                  Integer documentCode,
                                                                 Integer depositStatus,
                                                                  Long jdProjectId){
        return jdQualityGuaranteeDepositMapper.selectByManyConditions(settlementNo, documentCode, depositStatus, jdProjectId);
    }

    public int insertModel(JdQualityGuaranteeDeposit deposit){
        return jdQualityGuaranteeDepositMapper.insert(deposit);
    }
}
