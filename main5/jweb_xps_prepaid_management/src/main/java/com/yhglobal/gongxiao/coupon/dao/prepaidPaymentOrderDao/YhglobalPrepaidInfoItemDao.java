package com.yhglobal.gongxiao.coupon.dao.prepaidPaymentOrderDao;


import com.yhglobal.gongxiao.coupon.dao.mapper.prepaidPaymentOrderMapper.YhglobalPrepaidInfoItemMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 14:53 2018/5/8
 */
@Repository
public class YhglobalPrepaidInfoItemDao {
    @Autowired
    private YhglobalPrepaidInfoItemMapper yhglobalPrepaidInfoItemMapper;

    public int insert(YhglobalPrepaidInfoItem record){
        return yhglobalPrepaidInfoItemMapper.insert( record);
    }

    public int updateByPrimaryKey(String tablePrefix,YhglobalPrepaidInfoItem record){
        return yhglobalPrepaidInfoItemMapper.updateByPrimaryKey(tablePrefix, record);
    }
    public YhglobalPrepaidInfoItem selectByPrimaryKey(String tablePrefix,Integer rowId){
        return yhglobalPrepaidInfoItemMapper.selectByPrimaryKey(tablePrefix, rowId);
    }
    public List<YhglobalPrepaidInfoItem> selectByInfoId(String tablePrefix,Integer infoId){
        return yhglobalPrepaidInfoItemMapper.selectByInfoId(tablePrefix, infoId);
    }
}
