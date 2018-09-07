package com.yhglobal.gongxiao.phjd.excel;


import com.yhglobal.gongxiao.phjd.model.BatchImportPurchaseOrder;
import com.yhglobal.gongxiao.phjd.util.XlsUtil;
import com.yhglobal.gongxiao.phjd.util.XlsxUtil;
import com.yhglobal.gongxiao.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 采购订单导入类 (移植)
 *
 * @author weizecheng
 * @date 2018/9/5 11:46
 */
public class PurchaseOrderParse {

    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderParse.class);
    //xlxs中对应的下标
    static int sheetIndex = 0;


    /**
     * 解析从philips系统导出的采购单（不涉及具体业务计算）
     * 注：philips导出的文件 会包含多个订单，每个订单有多个明细，每个订单和合同号一一对应
     *
     * 返回值为Map<"合同号", List<商品信息等>>
     */
    public static Map<String, List<BatchImportPurchaseOrder>> parse(String filePath) {
        String[] split = filePath.split("\\.");
        Sheet sheet = null;
        //1) 文件读取准备
        //根据文件后缀进行判断 获取sheet页
        if ("xlsx".equals(split[1])) {
            sheet = XlsxUtil.getSheet(filePath, sheetIndex);
        } else if ("xls".equals(split[1])) {
            sheet = XlsUtil.getSheet(filePath, sheetIndex);
        }

        //需要返回的
        Map<String, List<BatchImportPurchaseOrder>> map = new HashMap<>();
        if (sheet == null) {
            logger.error("sheet could not be found: " + filePath);
            return map;
        }

        System.out.println("总行数:" + sheet.getLastRowNum());
        //2) 文件读取
        for (int row = 11; row < sheet.getLastRowNum(); row++) {
            Row sheetRow = sheet.getRow(row);
            if (sheetRow==null){
                continue;
            }
            BatchImportPurchaseOrder batchImportPurchaseOrder = new BatchImportPurchaseOrder();
            //从0列开始
            int i = 0;
            String status= XlsxUtil.getValue(sheetRow.getCell(i++));
            if (StringUtils.isBlank(status)){
                continue;
            }else {
                if (status.contains("总计")){
                    continue;
                }
            }

            String supplierOrderNo = XlsxUtil.getValue(sheetRow.getCell(i++));
            //第1列 订单号
            batchImportPurchaseOrder.setSupplierNumber(supplierOrderNo);
            //第2列 参考单号
            batchImportPurchaseOrder.setContractNumber(XlsxUtil.getValue(sheetRow.getCell(i++)));

            //第3列 业务日期
            Cell cell = sheetRow.getCell(i++);
            String d = cell.getStringCellValue();
            Date businessDate = DateUtil.stringToDate(d,"dd.MM.yyyy");
            batchImportPurchaseOrder.setBusinessDate(businessDate);

            //第4列 采购方式
            batchImportPurchaseOrder.setPurchaseType(XlsxUtil.getValue(sheetRow.getCell(i++)));

            //跳过3列
            i += 2;

            String itemStatus = XlsxUtil.getValue(sheetRow.getCell(i++));
            if ("取消".equals(itemStatus)){
                continue;
            }
            //第7列 状态
            batchImportPurchaseOrder.setStatus(itemStatus);
            //第8列 型号
            batchImportPurchaseOrder.setProductCode(XlsxUtil.getValue(sheetRow.getCell(i++)));
            //跳过4列
            i += 4;

            //第13列 采购数量
            String purchaseNumber = XlsxUtil.getValue(sheetRow.getCell(i++));
            double purNumber = 0;
            if (StringUtils.isNotBlank(purchaseNumber)) {
                purNumber = Double.parseDouble(purchaseNumber);
            }
            batchImportPurchaseOrder.setPurchaseNumber((int)purNumber);
            //跳过4列
            i += 4;
            //第18列 现金点
            String cashAmount = XlsxUtil.getValue(sheetRow.getCell(i++));
            double cash = 0;
            if (StringUtils.isNotBlank(cashAmount)) {
                cash = Double.parseDouble(cashAmount);
            }
            batchImportPurchaseOrder.setCashAmount(cash);
            //跳过2列
            i += 2;
            //第21列 代垫
            String prepaidAmount = XlsxUtil.getValue(sheetRow.getCell(i++));
            double prepaid = 0;
            if (StringUtils.isNotBlank(prepaidAmount)) {
                prepaid = Double.parseDouble(prepaidAmount);
            }
            batchImportPurchaseOrder.setPrepaidAmount(prepaid);

            i++; //跳过1列
            //第23列 返利
            String couponAmount = XlsxUtil.getValue(sheetRow.getCell(i++));
            double coupon = 0;
            if (StringUtils.isNotBlank(couponAmount)) {
                coupon = Double.parseDouble(couponAmount);
            }
            batchImportPurchaseOrder.setCouponAmount(coupon);

            i++; //跳过1列
            //第23列 代垫
            String totalAmount = XlsxUtil.getValue(sheetRow.getCell(i++));
            double total = 0;
            if (StringUtils.isNotBlank(totalAmount)) {
                total = Double.parseDouble(totalAmount);
            }
            batchImportPurchaseOrder.setTotalAmount(total);
            //3)记录丢到map里
            List<BatchImportPurchaseOrder> batchImportPurchaseOrderList = map.get(supplierOrderNo);
            if (batchImportPurchaseOrderList ==null|| batchImportPurchaseOrderList.size()==0){
                batchImportPurchaseOrderList = new ArrayList<>();
                batchImportPurchaseOrderList.add(batchImportPurchaseOrder);
                map.put(supplierOrderNo,batchImportPurchaseOrderList);
            }else {
                batchImportPurchaseOrderList.add(batchImportPurchaseOrder);
            }
        }

        return map;

    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Desktop\\FX\\orderItems (1).xls";
        Map<String, List<BatchImportPurchaseOrder>> map = parse(path);
        logger.info("采购列表文件解析完成,一共解析了{}个采购单",map.size());
    }

}
