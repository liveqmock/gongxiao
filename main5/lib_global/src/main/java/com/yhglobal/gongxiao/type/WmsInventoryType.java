package com.yhglobal.gongxiao.type;

import java.util.HashMap;
import java.util.Map;

/**x
 * WMS库存类型代码
 */
public enum WmsInventoryType {
    COMMON_GOOD_MACHINE(1, "普通好机"),
    DEFECTIVE(101, "残次"),
    ENGINE_DAMAGE(102, "机损"),
    BOX_DAMAGE(103, "箱损"),

    FROZEN_STOCK(201, "冻结库存"),
    TRANSPORTATION_INVENTORY(301, "在途库存");

    int inventoryType;
    String inventotyDesc;

    WmsInventoryType(int inventoryType, String inventotyDesc) {
        this.inventoryType = inventoryType;
        this.inventotyDesc = inventotyDesc;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public String getInventotyDesc() {
        return inventotyDesc;
    }


    /**
     * 根据数值获取对应的enum对象
     *
     * @param numValue
     * @return
     */
    private volatile static Map<Integer, WmsInventoryType> mapping = null;
    public static WmsInventoryType getInventoryTypeByNumValue(Integer numValue){
        if (mapping == null) {
            synchronized (WmsInventoryType.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (WmsInventoryType wmsInventoryType : WmsInventoryType.values()) {
                        mapping.put(wmsInventoryType.inventoryType, wmsInventoryType);
                    }
                }
            }
        }
        return mapping.get(numValue);
    }


}
