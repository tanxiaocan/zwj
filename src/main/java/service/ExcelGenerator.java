package service;

import utils.RespUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxiaocan on 2016/3/19.
 */
public class ExcelGenerator {
    public static Map<String,Object> exportExcel(String title,List<List<String>> cellStrsList,List<String> headers,String out){
        // 声明一个工作薄

        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格

        HSSFSheet sheet = workbook.createSheet(title);

        // 设置表格默认列宽度为15个字节

        sheet.setDefaultColumnWidth((short) 15);


        //产生表格标题行

        HSSFRow row = sheet.createRow(0);

        for (short i = 0; i < headers.size(); i++) {

            HSSFCell cell = row.createCell(i);

            cell.setCellValue(headers.get(i));

        }
        for(int i = 0;i < cellStrsList.size();i ++){
            row = sheet.createRow(i+1);
            List<String> cellStrs = cellStrsList.get(i);
            for(int j = 0;j < cellStrs.size();j ++){
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(cellStrs.get(j));
            }
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(out);
            workbook.write(outputStream);
            return RespUtils.success(null);
        } catch (IOException e) {
            System.out.println("导出文件的时候不要打开" + out + "查看");
//            e.printStackTrace();
            return RespUtils.fail("1","export excel error");
        }finally {
            try {
                workbook.close();
                if(outputStream != null){
                    outputStream.close();//关闭资源是大事
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
