package com.yhglobal.gongxiao.phjd.sales.dao;

import com.yhglobal.gongxiao.phjd.sales.dao.mapper.SalesContractNumberIndexMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesContractNumberIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 销售合同索引Dao
 *
 * @author weizecheng
 * @date 2018/8/30 14:17
 */
@Repository
public class SalesContractNumberIndexDao {

    @Autowired
    private SalesContractNumberIndexMapper salesContractNumberIndexMapper;


    /**
     * 根据项目Id获取销售索引
     *
     * @author weizecheng
     * @date 2018/8/30 13:05
     * @param prefix 表前缀
     * @param projectId 项目Id
     * @return SalesContractNumberIndex
     */
    public SalesContractNumberIndex getByProjectId(String prefix ,Long projectId){
        return salesContractNumberIndexMapper.getByProjectId(prefix,projectId);
    }

    /**
     * 更新销售索引
     *
     * @author weizecheng
     * @date 2018/8/30 14:23
     * @param prefix 表前缀
     * @param dateVersion 版本号
     * @param id 表Id
     * @param index 销售索引
     * @param updateLast 最后更新时间 yyyymmdd
     * @return int
     */
    public int updateIndex(String prefix,Long dateVersion,Long id,Integer index,String updateLast){
        return salesContractNumberIndexMapper.updateById(prefix, dateVersion, id, index, updateLast);
    }

}
