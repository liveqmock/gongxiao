package com.yhglobal.gongxiao.util;



import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XlsxUtil {

    /**
     * 获取指定单元格的字符串值
     * 注:部分cell类型是Numberic 按该类型读取得到的会是公式(比如:=VLOOKUP(K6,...) 只能强制按String类型读取
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    public static String getStringValue(XSSFCell cell) {
        if(cell==null) return null;
        return cell.getStringCellValue();
    }


    /**
     * 获取指定单元格的值
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    public static String getValue(Cell cell) {
        String ret = null;
        if (cell == null) {
            return ret;
        }
        CellType cellType = cell.getCellTypeEnum();
        if (cellType==null) {
            return ret;
        }
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
                ret = String.valueOf(cell.getNumericCellValue());
                break;
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
    public static XSSFSheet getSheet(String workbookPath, int sheetNumber) {
        XSSFSheet sheet = null;
        XSSFWorkbook workbook = null;
        try {
            InputStream is = new FileInputStream(workbookPath);
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook != null) {
            sheet = workbook.getSheetAt(sheetNumber);
        }
        return sheet;
    }

    /**
     *@Author:Nize
     *@Description: 读取Xls格式的文件;workbookPath  工作簿的路径;sheetNumber   第几个sheet
     *@Date:19:35 2018/1/3
     */
    public static HSSFSheet getXlsSheet(String workbookPath, int sheetNumber){
        HSSFSheet sheet=null;
        HSSFWorkbook hassfWorkbook=null;
        try {
            InputStream is = new FileInputStream(workbookPath);
            hassfWorkbook=new HSSFWorkbook(is);
        } catch (IOException e){
          e.printStackTrace();
        }

        if (hassfWorkbook != null) {
            sheet = hassfWorkbook.getSheetAt(sheetNumber);
        }
        return sheet;

    }

}
