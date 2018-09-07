package com.yhglobal.gongxiao.payment.prepaid;

import com.yhglobal.gongxiao.payment.prepaid.mapping.YhglobalPrepaidInfoMapper;
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
        return yhglobalPrepaidInfoMapper.insert(record);
    }
    public int updateByPrimaryKey(YhglobalPrepaidInfo record){
        return yhglobalPrepaidInfoMapper.updateByPrimaryKey(record);
    }
    public YhglobalPrepaidInfo selectByPrimaryKey(Integer prepaidId){
        return yhglobalPrepaidInfoMapper.selectByPrimaryKey(prepaidId);
    }
    public List<YhglobalPrepaidInfo> selectAll(@Param("projectId") Long projectId,
                                               @Param("prepaidNo") String prepaidNo,
                                               @Param("receivables") String receivables,
                                               @Param("dateStart") Date dateStart,
                                               @Param("dateEnd") Date dateEnd){
        return yhglobalPrepaidInfoMapper.selectAll(projectId,prepaidNo,receivables,dateStart,dateEnd);
    }
}
