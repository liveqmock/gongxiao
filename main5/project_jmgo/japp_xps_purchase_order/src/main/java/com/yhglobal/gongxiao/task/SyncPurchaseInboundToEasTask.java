package com.yhglobal.gongxiao.task;



import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderInbound;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderItemInbound;
import com.yhglobal.gongxiao.util.EasUtil;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

public class SyncPurchaseInboundToEasTask implements Runnable{

    private PurchaseBasicOrderInbound purchaseBasicOrderInbound;

    private List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList;

    public SyncPurchaseInboundToEasTask(PurchaseBasicOrderInbound purchaseBasicOrderInbound,
                                        List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList){
        this.purchaseBasicOrderInbound=purchaseBasicOrderInbound;
        this.purchaseBasicOrderItemInboundList = purchaseBasicOrderItemInboundList;
    }

    @Override
    public void run() {
        try {
            String s = EasUtil.sendPurchaseInbound2Eas(purchaseBasicOrderInbound, purchaseBasicOrderItemInboundList);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
