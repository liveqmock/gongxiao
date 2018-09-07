package com.yhglobal.gongxiao.inventory.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.warehouse.model.InventoryLedger;

import java.util.Date;
import java.util.List;

/**
 * 进销存台账
 * @author : liukai
 */
public interface InventoryLedgerService {

    /**
     * 查询 进销存台账记录
     *
     *
     * @param rpcHeader rpc调用的header
     * @param pageNumber        页码(非空)
     * @param pageSize      条数(非空)
     * @param projectId     项目id(非空)
     * @param productCode   商品编码(可以空)
     * @param productName   商品名称(可以空)
     * @param warehouseId   仓库id(可以空)
     * @param startDate     起始时间(可以空)
     * @param endDate       结束时间(可以空)
     * @return
     */
    PageInfo<InventoryLedger> conditionalSelectInventoryLedger(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String productCode, String productName,
                                                                    String warehouseId, Date startDate, Date endDate);

}
