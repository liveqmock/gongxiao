package com.yhglobal.gongxiao.phjd.util;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XlsUtil {

    /**
     * 获取指定单元格的值
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    public static String getValue(HSSFCell cell) {
        String ret = null;
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case BOOLEAN:
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                ret = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                ret = cell.getStringCellValue();
                break;
            default:

        }
        return ret;
    }

    /**
     * 获取指定下标的页(sheet)
     *
     * @param workbookPath  工作簿的路径
     * @param sheetNumber   第几个sheet
     * @return Sheet对象
     */
    public static HSSFSheet getSheet(String workbookPath, int sheetNumber) {
        HSSFSheet sheet = null;
        HSSFWorkbook workbook = null;
        try {
            InputStream is = new FileInputStream(workbookPath);
            workbook = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (workbook != null) {
            sheet = workbook.getSheetAt(sheetNumber);
        }
        return sheet;
    }


}
