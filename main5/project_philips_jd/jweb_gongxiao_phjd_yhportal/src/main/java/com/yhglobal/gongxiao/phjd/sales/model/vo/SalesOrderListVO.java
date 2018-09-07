package com.yhglobal.gongxiao.phjd.sales.model.vo;

import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderCountBO;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderListBO;

import java.io.Serializable;
import java.util.List;

/**
 * 订单管理页前段---视图
 *
 * @author weizecheng
 * @date 2018/8/22 15:49
 */
public class SalesOrderListVO implements Serializable{

    private List<SalesOrderListBO> list;

    private List<SalesOrderCountBO> count;

    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<SalesOrderListBO> getList() {
        return list;
    }

    public void setList(List<SalesOrderListBO> list) {
        this.list = list;
    }

    public List<SalesOrderCountBO> getCount() {
        return count;
    }

    public void setCount(List<SalesOrderCountBO> count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalesOrderListVO{");
        sb.append("list=").append(list);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
