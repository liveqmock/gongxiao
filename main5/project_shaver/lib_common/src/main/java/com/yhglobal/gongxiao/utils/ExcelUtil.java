package com.yhglobal.gongxiao.utils;


import com.yhglobal.gongxiao.common.ExcelField;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


/**;
 * @Description:
 * @author: LUOYI
 * @Date: Created in 11:26 2018/4/19
 */
public class ExcelUtil<T> {
    private Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 生成excel保存到目录
     * @param list
     * @param sheetName
     * @param output
     * @return
     */
    public boolean getExcelToFile(List<T> list, String sheetName,OutputStream output) {
        try{
            Workbook workbook = this.getListToExcel(list,sheetName);
            output.flush();
            workbook.write(output);

        }catch(IOException e){
            return Boolean.FALSE;
        }finally {
            try{
                output.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return Boolean.TRUE;
    }
    /*output.flush();
            workbook.write(output);
            output.close();*/
    /**
     * 将list数据源的数据导入到excel表单
     *
     * @param list
     *            数据源
     * @param sheetName
     *            工作表的名称
     */
    public Workbook getListToExcel(List<T> list, String sheetName) {
        try {
            // excel中每个sheet中最多有65536行
            int sheetSize = 65536;
            // 得到所有定义字段
            Field[] allFields = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();
            // 得到所有field并存放到一个list中
            for (Field field : allFields) {
                if (field.isAnnotationPresent(ExcelField.class)) {
                    fields.add(field);
                }
            }
            // 产生工作薄对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 取出一共有多少个sheet
            int listSize = 0;
            if (list != null && list.size() >= 0) {
                listSize = list.size();
            }
            double sheetNo = Math.ceil(listSize / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                // 产生工作表对象
                HSSFSheet sheet = workbook.createSheet();
                // 设置工作表的名称.
                workbook.setSheetName(index, sheetName + index);
                HSSFRow row;
                HSSFCell cell;// 产生单元格
                row = sheet.createRow(0);// 产生一行
                /* *********普通列样式********* */
                HSSFFont font = workbook.createFont();
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                font.setFontName("Arail narrow"); // 字体
                /* *********标红列样式********* */
                HSSFFont newFont = workbook.createFont();
                HSSFCellStyle newCellStyle = workbook.createCellStyle();
                newFont.setFontName("Arail narrow"); // 字体
                /* *************创建列头名称*************** */
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    ExcelField attr = field.getAnnotation(ExcelField.class);
                    int col = i;
                    // 根据指定的顺序获得列号
                    if (StringUtils.isNotBlank(attr.column())) {
                        col = getExcelCol(attr.column());
                    }
                    // 创建列
                    cell = row.createCell(col);
                    if (attr.isMark()) {
                        newFont.setColor(HSSFFont.COLOR_RED); // 字体颜色
                        newCellStyle.setFont(newFont);
                        cell.setCellStyle(newCellStyle);
                    } else {
                        font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                    }
                    sheet.setColumnWidth(i, (int) ((attr.name().getBytes().length <= 4 ? 6 : attr.name().getBytes().length) * 1.5 * 256));
                    // 设置列中写入内容为String类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    // 写入列名
                    cell.setCellValue(attr.name());
                    // 如果设置了提示信息则鼠标放上去提示.
                    if (StringUtils.isNotBlank(attr.prompt())) {
                        setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);
                    }
                    // 如果设置了combo属性则本列只能选择不能输入
                    if (attr.combo().length > 0) {
                        setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);
                    }
                }
                /* *************创建内容列*************** */
                font = workbook.createFont();
                cellStyle = workbook.createCellStyle();
                int startNo = index * sheetSize;
                int endNo = Math.min(startNo + sheetSize, listSize);
                // 写入各条记录,每条记录对应excel表中的一行
                for (int i = startNo; i < endNo; i++) {
                    row = sheet.createRow(i + 1 - startNo);
                    T vo = (T) list.get(i); // 得到导出对象.
                    for (int j = 0; j < fields.size(); j++) {
                        // 获得field
                        Field field = fields.get(j);
                        // 设置实体类私有属性可访问
                        field.setAccessible(true);
                        ExcelField attr = field.getAnnotation(ExcelField.class);
                        int col = j;
                        // 根据指定的顺序获得列号
                        if (StringUtils.isNotBlank(attr.column())) {
                            col = getExcelCol(attr.column());
                        }
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            // 创建cell
                            cell = row.createCell(col);
                            if (attr.isMark()) {
                                newFont.setColor(HSSFFont.COLOR_RED); // 字体颜色
                                newCellStyle.setFont(newFont);
                                cell.setCellStyle(newCellStyle);
                            } else {
                                font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
                                cellStyle.setFont(font);
                                cell.setCellStyle(cellStyle);
                            }
                            // 如果数据存在就填入,不存在填入空格
                            Class<?> classType = (Class<?>) field.getType();
                            String value = null;
                            if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                                value = DateUtil.dateToString(sdf.parse(String.valueOf(field.get(vo))),DateUtil.DATE_TIME_FORMAT);
                            }
                            cell.setCellValue(field.get(vo) == null ? "" : value == null ? String.valueOf(field.get(vo)) : value);
                        }
                    }
                }
                /* *************创建合计列*************** */
                HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    ExcelField attr = field.getAnnotation(ExcelField.class);
                    if (attr.isSum()) {
                        int col = i;
                        // 根据指定的顺序获得列号
                        if (StringUtils.isNotBlank(attr.column())) {
                            col = getExcelCol(attr.column());
                        }
                        BigDecimal totalNumber = BigDecimal.ZERO;
                        for (int j = 1, len = (sheet.getLastRowNum() - 1); j < len; j++) {
                            HSSFRow hssfRow = sheet.getRow(j);
                            if (hssfRow != null) {
                                HSSFCell hssfCell = hssfRow.getCell(col);
                                Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");//浮点数判断
                                if (hssfCell != null && hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING
                                        && pattern.matcher(hssfCell.getStringCellValue()).matches()) {
                                    totalNumber = totalNumber.add(BigDecimal.valueOf(Double.valueOf(hssfCell.getStringCellValue())));
                                }
                            }
                        }
                        HSSFCell sumCell = lastRow.createCell(col);
                        sumCell.setCellValue(new HSSFRichTextString("合计：" + totalNumber));
                    }
                }
            }
            /*output.flush();
            workbook.write(output);
            output.close();*/
            return workbook;
        } catch (Exception e) {
            throw new RuntimeException("将list数据源的数据导入到excel表单异常!", e);
        }

    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param col
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet
     *            要设置的sheet.
     * @param promptTitle
     *            标题
     * @param promptContent
     *            内容
     * @param firstRow
     *            开始行
     * @param endRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param endCol
     *            结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                                          int firstCol, int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet
     *            要设置的sheet.
     * @param textlist
     *            下拉框显示的内容
     * @param firstRow
     *            开始行
     * @param endRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param endCol
     *            结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }
}
