package com.yhglobal.gongxiao.util;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 16:52 2018/4/19
 */
public class ExcelDownUtil {
    public static void resetResponse(HttpServletResponse response, Workbook workbook, String downFileName){
        try {
            //若不进行编码在IE下会乱码
            downFileName = URLEncoder.encode(downFileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OutputStream os = null;
        try {
            // 清空response
            response.reset();
            response.setContentType("application/msexcel");//设置生成的文件类型
            response.setCharacterEncoding("UTF-8");//设置文件头编码方式和文件名
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(downFileName.getBytes("utf-8"), "ISO8859-1"));
            os = response.getOutputStream();
            workbook.write(os);
        }catch(IOException e){
            throw new RuntimeException("reset response error!",e);
        }finally {
            try{
                if(os != null){
                    os.flush();
                    os.close();
                }
            }catch(IOException e){

            }
        }
    }
}
