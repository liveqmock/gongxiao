package com.yhglobal.gongxiao.inventory.util;

import com.yhglobal.gongxiao.inventory.model.InventoryExcelModel;
import com.yhglobal.gongxiao.util.XlsxUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析库存的excel
 *
 * @author liukai
 */
public class InventoryPaser {

    //    private static Logger logger  =  LoggerFactory.getLogger(ManualOrderParser.class);

    static int sheetIndex = 0; //xlxs中对应的下标

    static String[] header = {"物料编码", "物料", "规格型号", "计量单位", "基本计量单位", "数量", "可用数量",
            "基本数量", "批次", "可用基本数量", "生产日期", "到期日期", "可用辅助数量", "辅助计量单位", "辅助数量",
            "辅助属性", "仓库", "库位", "库存类型", "库存状态", "客户", "供应商", "项目号", "跟踪号", "库存组织",
            "预留数量", "预留基本数量", "预留辅助数量", "入库时间", "是否赠品", "库存品质", "数量", "销售占用数量",
            "采购指导价", "采购价", "成本价", "应收返利", "实收返利", "现金折扣", "出货总金额", "最终出货时间"
    };

    /**
     * 检查xlsx文件的header是否和定义的一致
     * 目标是防止导出的格式已变更，但代码还在使用老的格式解析
     *
     * @param headerRow header所在行对应的XSSF对象
     * @return 校验是否通过
     */
    public static boolean verifyHeader(XSSFRow headerRow) {
        boolean ret = true;

        int columnNumber = headerRow.getLastCellNum();
        if (header.length != columnNumber) {
            ret = false;
            return ret;
        }

        for (int i = 0; i < header.length; i++) {
            XSSFCell cell = headerRow.getCell(i);
            String cellValue = XlsxUtil.getValue(cell);
            if (!header[i].equals(cellValue)) {
                ret = false;
            }
        }
        return ret;
    }

    public static List<InventoryExcelModel> parse(String xlsxFile) {
        List<InventoryExcelModel> ret = null;
        XSSFSheet sheet = XlsxUtil.getSheet(xlsxFile, sheetIndex);
        if (sheet == null) {
            return ret;
        }

        XSSFRow headerRow = sheet.getRow(0);
        boolean verificationPassed = verifyHeader(headerRow);
        if (!verificationPassed) {
            return ret;
        }

        ret = new ArrayList<>();
        int j = 0;

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {//注:这里是小于或等于lastRowNumber
            XSSFRow xssfRow = sheet.getRow(rowNum);
            String value = null;
            if (xssfRow != null && xssfRow.getCell(0) != null) {
                int i = 0;
                InventoryExcelModel inventoryExcelModel = new InventoryExcelModel();
                inventoryExcelModel.setMaterialCode(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setMaterial(XlsxUtil.getValue(xssfRow.getCell(i++)));

                XSSFCell cell = xssfRow.getCell(i++);
                if (cell==null||"".equals(cell.getStringCellValue())){
                    System.out.println("cell 为空 第"+rowNum+"行");
                    continue;
                }
                inventoryExcelModel.setProductCode(XlsxUtil.getValue(cell));

                inventoryExcelModel.setProductUnit(XlsxUtil.getValue(xssfRow.getCell(i++)));

                inventoryExcelModel.setBasicUnit(XlsxUtil.getValue(xssfRow.getCell(i++)));

                inventoryExcelModel.setQuantity((int) Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));

                inventoryExcelModel.setAvailabalQuantity((int) Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setBasicQuantity((int) Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setBatchNo(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setAvailabalBasicQuantity((int) Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setProductTime(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setDeadlineTime(XlsxUtil.getValue(xssfRow.getCell(i++)));

                cell = xssfRow.getCell(i++);
                if (cell!=null) {
                    inventoryExcelModel.setAvailabalBasicQuantity((int) Double.parseDouble(XlsxUtil.getValue(cell)));
                }
                inventoryExcelModel.setHelpUnit(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setHelpQuantity(Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setHelpNatue(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setWarehouseName(XlsxUtil.getValue(xssfRow.getCell(i++)));
                String position = XlsxUtil.getValue(xssfRow.getCell(i++));
                System.out.println(position);
                //（0:普通采购 1：货补 2：赠品）
                if (position == null) {
                    break;
                } else {
                    if (position.equals("赠品区")) {
                        inventoryExcelModel.setPosition(3);
                    } else if (position.equals("良品区")) {
                        inventoryExcelModel.setPosition(1);
                    }else if (position.equals("不良品区")){
                        inventoryExcelModel.setPosition(1);
                    }
                }
//                inventoryExcelModel.setPosition(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setInventoryType(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setInventoryStatus(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setCustomer(XlsxUtil.getValue(xssfRow.getCell(i++)));

                inventoryExcelModel.setSupplier(XlsxUtil.getValue(xssfRow.getCell(i++)));
                inventoryExcelModel.setProject(XlsxUtil.getValue(xssfRow.getCell(i++)));

                inventoryExcelModel.setTraceNo(XlsxUtil.getValue(xssfRow.getCell(i++)));

                inventoryExcelModel.setInventoryManagement(XlsxUtil.getValue(xssfRow.getCell(i++)));
                cell = xssfRow.getCell(i++);
                if (cell!=null) {
                    inventoryExcelModel.setPlanReserveQuantity((int) Double.parseDouble(XlsxUtil.getValue(cell)));
                }
                inventoryExcelModel.setPlanReserveBasicQuantity((int) Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                cell = xssfRow.getCell(i++);
//                if (cell!=null ) {
//                    value = XlsxUtil.getValue(cell);
//                    if (!StringUtils.isEmpty(value)) {
//                        inventoryExcelModel.setPlanReserveHelQuantity((int) Double.parseDouble(XlsxUtil.getValue(cell)));
//                    }
//                }
                inventoryExcelModel.setEntryTime(XlsxUtil.getValue(xssfRow.getCell(i++)));

                String gg = XlsxUtil.getValue(xssfRow.getCell(i++));
                System.out.println(gg);
                if (gg == null) {
                    break;
                } else {
                    if (gg.equals("赠品")) {
                        inventoryExcelModel.setGiftStatus(2);
                    } else if (gg.equals("货补")) {
                        inventoryExcelModel.setGiftStatus(1);
                    }else {
                        inventoryExcelModel.setGiftStatus(0);
                    }
                }

                XSSFCell cell2 = xssfRow.getCell(i++);
                System.out.println("============"+cell2.getStringCellValue());

                if (XlsxUtil.getValue(cell2).equals("普通好机")) {
                    inventoryExcelModel.setInventoryQuality(1);
                } else if (XlsxUtil.getValue(cell2).equals("残次")) {
                    inventoryExcelModel.setInventoryQuality(101);
                } else if (XlsxUtil.getValue(cell2).equals("机损")) {
                    inventoryExcelModel.setInventoryQuality(102);
                } else if (XlsxUtil.getValue(cell2).equals("箱损")) {
                    inventoryExcelModel.setInventoryQuality(103);
                } else {  //冻结
                    inventoryExcelModel.setInventoryQuality(201);
                }

                value = XlsxUtil.getValue(xssfRow.getCell(i++));
                if (value == null) {
                    value = "0";
                }
                inventoryExcelModel.setInventoryQuantity((int) Double.parseDouble(value));

                cell = xssfRow.getCell(i++);
                if (cell!=null) {
                    inventoryExcelModel.setOccupyQuantity((int) Double.parseDouble(XlsxUtil.getValue(cell)));
                }
                inventoryExcelModel.setGuidePurchasePrice(Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setPurchasePrice(Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setCostPrice(Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setShouldRebate(Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));
                inventoryExcelModel.setActualRebate(Double.parseDouble(XlsxUtil.getValue(xssfRow.getCell(i++))));

                value = XlsxUtil.getValue(xssfRow.getCell(i++));
                if (value == null) {
                    value = "0";
                }
                inventoryExcelModel.setCashDiscount(Double.parseDouble(value));


               value = XlsxUtil.getValue(xssfRow.getCell(i++));
                if (value == null) {
                    inventoryExcelModel.setSalesTotalAmount(0);
                } else {
                    inventoryExcelModel.setSalesTotalAmount(Double.parseDouble(value));
                }

                if (XlsxUtil.getValue(xssfRow.getCell(i++)) == null) {
                    inventoryExcelModel.setCreateTime("");
                } else {
                    inventoryExcelModel.setCreateTime(XlsxUtil.getValue(xssfRow.getCell(i++)));
                }


                ret.add(inventoryExcelModel);
            }
        }
        return ret;
    }


    public static void main(String[] args) {
        String xlsxFile = "D:\\BL\\即时库存查询.xlsx";
        List<InventoryExcelModel> recordList = parse(xlsxFile);
        System.out.println(recordList.size());

    }
}
