package service;

import org.springframework.core.io.ClassPathResource;
import utils.ResourceUtil;
import utils.RespUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static utils.ResourceUtil.getPropertiesFilePath;

/**
 * Created by tanxiaocan on 2016/3/20.
 */
public class ItemsService {
    public static String EXPORT_FILE_PATH = "item-export.properties";
    public static List<String> getItems(){
        List<String> items = new ArrayList<String>();
        BufferedReader br = null;
        try{
            br =new BufferedReader(new InputStreamReader(new FileInputStream(ResourceUtil.getPropertiesFilePath(EXPORT_FILE_PATH)),"GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();//关闭资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    public static Map<String,Object> addItem(String item){
        List<String> items = getItems();
        for(String orginalItem : items){
            if(orginalItem.equals(item)){
                return RespUtils.fail("1","该规则已存在！");
            }
        }
        BufferedWriter bufferedWriter = null;
        try{
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getPropertiesFilePath(EXPORT_FILE_PATH),true),"GBK"));
            bufferedWriter.newLine();
            bufferedWriter.write(item);
            return RespUtils.success(item);
        }catch (Exception e){
            e.printStackTrace();
            return RespUtils.fail("1","写入规则错误！");
        }finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("写入新规则的时候关闭流失败！");
                }
            }
        }
    }

//    //本地运行时使用此方法
//    private static File getPropertiesFilePath(String filePath){
////        String propertiesPath = ItemsService.class.getResource("../").getPath() + "item-export.properties";
//        try {
//            return new ClassPathResource(filePath).getFile();
//        } catch (IOException e) {
//            System.out.println("文件加载不到，尝试使用jar方式加载");
//            return getPropertiesFileForJar(filePath);
//        }
//    }
//
////    打成zip包时使用此方法
//    private static File getPropertiesFileForJar(String filePath){
//        String propertiesPath = ItemsService.class.getResource("/" + filePath).getPath();
//        System.out.println(propertiesPath);
//        int startPoint = propertiesPath.indexOf(":");
//        startPoint += 2;
//        int cutPoint = propertiesPath.indexOf("word-resolver");
//        cutPoint += "word-resolver".length();
//        propertiesPath = propertiesPath.substring(startPoint,cutPoint);
//        propertiesPath += "/resources/" + filePath;
//        System.out.println(propertiesPath);
//        return new File(propertiesPath);
//    }

    public static void main(String[] args){
        String propertiePath = "file:/d:/aaabbbfsafasfsa/libs/fafasfasf/fafasfasf";
        int startPoint = propertiePath.indexOf(":");
        startPoint += 2;
        int cutPoint = propertiePath.indexOf("libs");
        cutPoint += "libs".length();
        propertiePath = propertiePath.substring(startPoint,cutPoint);
        System.out.println(propertiePath);
    }
}
