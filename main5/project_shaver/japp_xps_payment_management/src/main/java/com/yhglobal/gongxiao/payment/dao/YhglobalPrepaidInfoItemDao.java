package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.YhglobalPrepaidInfoItemMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalPrepaidInfoItem;
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
        return yhglobalPrepaidInfoItemMapper.insert(record);
    }
    public int updateByPrimaryKey(YhglobalPrepaidInfoItem record){
        return yhglobalPrepaidInfoItemMapper.updateByPrimaryKey(record);
    }
    public YhglobalPrepaidInfoItem selectByPrimaryKey(Integer rowId){
        return yhglobalPrepaidInfoItemMapper.selectByPrimaryKey(rowId);
    }
    public List<YhglobalPrepaidInfoItem> selectByInfoId(Integer infoId){
        return yhglobalPrepaidInfoItemMapper.selectByInfoId(infoId);
    }
}
