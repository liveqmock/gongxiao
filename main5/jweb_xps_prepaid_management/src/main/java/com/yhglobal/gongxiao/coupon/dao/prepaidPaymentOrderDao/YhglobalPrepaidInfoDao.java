package com.yhglobal.gongxiao.coupon.dao.prepaidPaymentOrderDao;


import com.yhglobal.gongxiao.coupon.dao.mapper.prepaidPaymentOrderMapper.YhglobalPrepaidInfoMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 14:53 2018/5/8
 */
@Repository
public class YhglobalPrepaidInfoDao {

    @Autowired
    private YhglobalPrepaidInfoMapper yhglobalPrepaidInfoMapper;

    public int insert(YhglobalPrepaidInfo record){
        return yhglobalPrepaidInfoMapper.insert( record);
    }

    public int updateByPrimaryKey(String tablePrefix,YhglobalPrepaidInfo record){
        return yhglobalPrepaidInfoMapper.updateByPrimaryKey(tablePrefix, record);
    }

    public YhglobalPrepaidInfo selectByPrimaryKey(String tablePrefix,Integer prepaidId){
        return yhglobalPrepaidInfoMapper.selectByPrimaryKey(tablePrefix, prepaidId);
    }

    public List<YhglobalPrepaidInfo> selectAll(@Param("tablePrefix") String tablePrefix,
                                               @Param("projectId") Long projectId,
                                               @Param("prepaidNo") String prepaidNo,
                                               @Param("receivables") String receivables,
                                               @Param("dateStart") Date dateStart,
                                               @Param("dateEnd") Date dateEnd){
        return yhglobalPrepaidInfoMapper.selectAll(tablePrefix, projectId,prepaidNo,receivables,dateStart,dateEnd);
    }
}
