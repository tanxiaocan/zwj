package service;

import domain.DocResolveInfo;
import domain.FinalInfo;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.StringUtils;
import utils.RespUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

/**
 * Created by zhaiwenjie on 2016/3/18.
 */
public class DomResolveServcie {

    /**
     * 解析的第一步，指定一个目录名称，获取目录下所有需要解析的doc，以字符串数组的格式返回
     * @param dirName 要解析的目录名称
     * @return
     */
    private static Map<String,Object> readDirFile(String dirName){
        try {
            File dirPath = new File(dirName);
            String[] fileStrs = dirPath.list();//获取目录下所有文件名，不包括目录名
            for(int i = 0;i < fileStrs.length;i ++){
                fileStrs[i] = dirName + "\\" + fileStrs[i];//加上目录名，拼出文件的绝对路径
            }
            return RespUtils.success(fileStrs);//成功返回
        }catch (Exception e){
            return RespUtils.fail("1","目录：" + dirName + "非法");//如果读取目录失败，返回目录非法信息
        }
    }

    /**
     * 解析第二步，解析一个word的所有表格，将表格中所有 需要的 属性值（不包含属性名）以顺序存储的方式放到一个list中返回
     * @param filePath
     * @return
     */
    private static Map<String,Object> resolveDocTable(String filePath,List<String> itemList){
        List<String> cellStrings = new ArrayList<String>();//记录一份文件所有单元格的字符串列表
        try {
            System.out.println("resolveWordTable:" + filePath);
            OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);//读取单个文件
            XWPFDocument xwpf = new XWPFDocument(opcPackage);//生成word文档对象
            resolveAllTables(xwpf,itemList,cellStrings);
//            for (XWPFTable xwpfTable : xwpf.getTables()) {//遍历word中所有的表格
//                for(int i=0;i<xwpfTable.getNumberOfRows();i++){
//                    XWPFTableRow xwpfTableRow = xwpfTable.getRow(i);
//                    List<XWPFTableCell> xwpfTableCellList = xwpfTableRow.getTableCells();
//                    if(xwpfTableCellList.size() % 2 != 0){//如果表格的单元格数目不是偶数，返回该文件，这样的word不解析
//                        return RespUtils.fail("1","filePath" + "格式不符合要求");
//                    }
//                    getNeededAttr(xwpfTableCellList, itemList, cellStrings);
//                }
//            }
            return RespUtils.success(new DocResolveInfo(cellStrings,filePath,itemList));

        } catch (Exception e) {
            e.printStackTrace();
            /**打印未正常提取的文档名
             * 记录在文档里
             */
            return RespUtils.fail("1",filePath);
        }
    }

    private static void resolveAllTables(XWPFDocument xwpf,List<String> itemList,List<String> cellStrings){
        for (int i = 0; i < itemList.size(); i++) {
            String item = itemList.get(i);
            for (XWPFTable xwpfTable : xwpf.getTables()) {
                String value = getNeededAttrNew(xwpfTable,item,cellStrings);
                cellStrings.add(value);
                break;
            }
            String[] itemAndType = getItemAndType(item);
            itemList.set(i,itemAndType[0]);
        }
    }

    private static String getNeededAttrNew(XWPFTable xwpfTable,String item,List<String> cellStrings){
        return  getItemValue(getItemAndType(item),xwpfTable);
    }
    private static String getItemValue(String[] itemAndType,XWPFTable xwpfTable){
        String value = "-";
        for(int i=0;i<xwpfTable.getNumberOfRows();i++){
            XWPFTableRow xwpfTableRow = xwpfTable.getRow(i);
            List<XWPFTableCell> xwpfTableCellList = xwpfTableRow.getTableCells();
            for(int j=0;j<xwpfTableCellList.size();j++){
                if(!StringUtils.hasText(itemAndType[1])){
                    if(itemAndType[0].equals(getPrettyCellStr(xwpfTableCellList.get(j).getParagraphs()))){
                        value = getPrettyCellStr(xwpfTableCellList.get(j + 1).getParagraphs());
                        return value;
                    }
                }else{
                    if(itemAndType[0].equals(getPrettyCellStr(xwpfTableCellList.get(j).getParagraphs()))){
                        value = getPrettyCellStr(xwpfTable.getRow(i + 1).getTableCells().get(j).getParagraphs());
                        return value;
                    }
                }
            }
        }
        return value;
    }
    private static String[] getItemAndType(String item){
        String[] itemAndType = new String[2];
        if(item.contains("-")){
            itemAndType = item.split("-");
        }else {
            itemAndType[0] = item;
            itemAndType[1] = "";
        }
        return itemAndType;
    }
    /**
     * 获取所有需要的属性值
     * @param xwpfTableCellList
     * @param itemList
     * @return
     */
    private static void getNeededAttr(List<XWPFTableCell> xwpfTableCellList,List<String> itemList,List<String> cellStrings){
        for(int i = 0;i < xwpfTableCellList.size() / 2;i ++){//遍历单元格的时候，每次都从属性名开始读取
            List<XWPFParagraph> paragraphList = xwpfTableCellList.get(2 * i).getParagraphs();
            String value = getPrettyCellStr(paragraphList);//拿到属性名称
            if(itemList.contains(value)){//如果属性名称在我们需要的属性里面，那么将属性值保存下来
                value = getPrettyCellStr(xwpfTableCellList.get(2 * i + 1).getParagraphs());//拿到需要的属性值
                cellStrings.add(value);//将属性值保存下来
            }
        }
    }

    /**
     * 获取单元格的完美值（拼接后去掉空格，空值用-表示)
     * @return
     */
    private static String getPrettyCellStr(List<XWPFParagraph> paragraphList){
        String value = "";
        for(XWPFParagraph paragraph : paragraphList){
            value += paragraph.getParagraphText();//+表示拼接，消除回车
        }
        value = value.replaceAll(" ","");//消除所有空格
        if(value.equals("")){
            value = "-";
        }
        return value;
    }

    /**
     * 收集所有word的表格，放到list中，调取导出excel方法将信息导出到excel中
     * @param dirPath //要解析的目录名称
     * @param out //要输出的文件名称
     * @return
     */
    public static Map<String,Object> resolveDomToExcel(String dirPath,String out,List<String> itemList ){
        List<String> successList = new ArrayList<String>();//记录成功解析的文件
        List<String> failList = new ArrayList<String>();//记录解析失败的文件
        List<List<String>> cellStrsList = new ArrayList<List<String>>();//记录所有word文档的需要的属性值，供导出excel使用
        Map<String,Object> dirResp = readDirFile(dirPath);//读取要解析word文件的目录，拿到所有文件的绝对路径

        if(dirResp.get("code").equals("0")){//如果解析word文件目录成功
            String[] fileStrs = (String[]) dirResp.get("data");//取出所有word文件的绝对路径
            for(String filePath : fileStrs){//遍历所有word文件
                Map<String,Object> docResolveResp = resolveDocTable(filePath,itemList);//读取单个word文件，获取所有需要的属性值
                if(docResolveResp.get("code").equals("0")){
                    DocResolveInfo info = (DocResolveInfo) docResolveResp.get("data");//这个对象里包含了单个word文件所有需要的属性值列表，解析的word文件名称，要解析的属性名称
                    List<String> cellStrings = info.getCellStrings();
                    cellStrsList.add(cellStrings);//将单个的word文件的所有属性值放到一个大的list中，供导出excel使用
                    successList.add(info.getFilePath());//将导出成功的文件名称记录下来
                }else{
                    failList.add(docResolveResp.get("message").toString());//将导出失败的文件名记录下来
                }
            }
            Map<String,Object> exportResp = ExcelGenerator.exportExcel("翟文洁摘录", cellStrsList,itemList, out);//执行导出excel操作
            if(!exportResp.get("code").equals("0")){//如果导出的时候失败了，返回导出的错误
                return exportResp;
            }
            return RespUtils.success(new FinalInfo(successList,failList));//返回所有导出失败和成功的文件信息
        }else{
            return dirResp;
        }
    }

    public static void main(String[] args){
        Map<String,Object> resp = resolveDomToExcel("d:/test-files","d:/10.xls",null);
        if(resp.get("code").equals("0")){
            FinalInfo finalInfo = (FinalInfo) resp.get("data");
            System.out.println("sucess:" + finalInfo.getSuccessList().size() +"");
            for(String file : finalInfo.getSuccessList()){
                System.out.println(file);
            }
        }
    }

}
