package com.yhglobal.gongxiao.excel;


import com.yhglobal.gongxiao.model.BatchImportPurchaseOrder;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.XlsUtil;
import com.yhglobal.gongxiao.util.XlsxUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PurchaseOrderParse {

    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderParse.class);

    static int sheetIndex = 0; //xlxs中对应的下标


    public static Map<String, List<BatchImportPurchaseOrder>> parse(String filePath) {
        String[] split = filePath.split("\\.");
        Workbook wb;
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
            int i = 0;//从0列开始
            String status= XlsxUtil.getValue(sheetRow.getCell(i++));
            if (status == null || "".equals(status)){
                continue;
            }else {
                if (status.contains("总计")){
                    continue;
                }
            }

            String supplierOrderNo = XlsxUtil.getValue(sheetRow.getCell(i++));

            batchImportPurchaseOrder.setSupplierNumber(supplierOrderNo);//第1列 订单号
            batchImportPurchaseOrder.setContractNumber(XlsxUtil.getValue(sheetRow.getCell(i++)));//第2列 参考单号

            Cell cell = sheetRow.getCell(i++);//第3列 业务日期
            String d = cell.getStringCellValue();
            Date businessDate = DateUtil.stringToDate(d,"dd.MM.yyyy");
            batchImportPurchaseOrder.setBusinessDate(businessDate);

            batchImportPurchaseOrder.setPurchaseType(XlsxUtil.getValue(sheetRow.getCell(i++)));//第4列 采购方式

            i += 2; //跳过3列

            String itemStatus = XlsxUtil.getValue(sheetRow.getCell(i++));
            if ("取消".equals(itemStatus)){
                continue;
            }
            batchImportPurchaseOrder.setStatus(itemStatus);//第7列 状态
            batchImportPurchaseOrder.setProductCode(XlsxUtil.getValue(sheetRow.getCell(i++))); //第8列 型号
            i += 4; //跳过4列

            String purchaseNumber = XlsxUtil.getValue(sheetRow.getCell(i++));//第13列 采购数量
            double purNumber = 0;
            if (purchaseNumber == null || "".equals(purchaseNumber)) {
            } else {
                purNumber = Double.parseDouble(purchaseNumber);
            }
            batchImportPurchaseOrder.setPurchaseNumber((int)purNumber);

            i += 4; //跳过4列
            String cashAmount = XlsxUtil.getValue(sheetRow.getCell(i++));//第18列 现金点
            double cash = 0;
            if (cashAmount == null || "".equals(cashAmount)) {
            } else {
                cash = Double.parseDouble(cashAmount);
            }
            batchImportPurchaseOrder.setCashAmount(cash);

            i += 2; //跳过2列
            String prepaidAmount = XlsxUtil.getValue(sheetRow.getCell(i++));//第21列 代垫
            double prepaid = 0;
            if (prepaidAmount == null || "".equals(prepaidAmount)) {
            } else {
                prepaid = Double.parseDouble(prepaidAmount);
            }
            batchImportPurchaseOrder.setPrepaidAmount(prepaid);

            i++; //跳过1列
            String couponAmount = XlsxUtil.getValue(sheetRow.getCell(i++));//第23列 返利
            double coupon = 0;
            if (couponAmount == null || "".equals(couponAmount)) {
            } else {
                coupon = Double.parseDouble(couponAmount);
            }
            batchImportPurchaseOrder.setCouponAmount(coupon);

            i++; //跳过1列

            String totalAmount = XlsxUtil.getValue(sheetRow.getCell(i++));//第23列 代垫
            double total = 0;
            if (totalAmount == null || "".equals(totalAmount)) {
            } else {
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
