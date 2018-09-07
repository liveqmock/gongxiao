package com.yhglobal.gongxiao.payment.AccountManageDao;

import com.yhglobal.gongxiao.payment.AccountManageDao.mapping.YhglobalToPayMoneyMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalToPayMoney;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class YhglobalToPayMoneyDao {

    @Autowired
    YhglobalToPayMoneyMapper yhglobalToPayMoneyMapper;

    // 插入新的付款申请单
    public int insert(YhglobalToPayMoney yhglobalToPayMoney){return yhglobalToPayMoneyMapper.insert(yhglobalToPayMoney);}
    // 更新付款单数据
    public int update(YhglobalToPayMoney yhglobalToPayMoney){return yhglobalToPayMoneyMapper.updateByPrimaryKey(yhglobalToPayMoney);}

    public List<YhglobalToPayMoney> queryData(@Param("paymentApplyNo")String paymentApplyNo,
                                              @Param("purchasePlanNo")String purchasePlanNo,
                                              @Param("supplierId")Long supplierId,
                                              @Param("receiveStatus")Byte receiveStatus,
                                              @Param("settlementType")Byte settlementType,
                                              @Param("paymentType")Byte paymentType,
                                              @Param("dateS")Date dateS,
                                              @Param("dateE")Date dateE){
        return yhglobalToPayMoneyMapper.queryData(paymentApplyNo,purchasePlanNo,supplierId,receiveStatus,settlementType,paymentType,dateS,dateE);
    }

}
