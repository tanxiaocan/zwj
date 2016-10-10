package service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import utils.RespUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxiaocan on 2016/3/20.
 */
public class ItemsService {
    public static String EXPORT_FILE_PATH = "item-export.properties";
    public static List<String> getItems(){
        List<String> items = new ArrayList<String>();
        BufferedReader br = null;
        try{
//            File file = new File(getPropertiesFilePath());
            br =new BufferedReader(new InputStreamReader(new FileInputStream(getPropertiesFilePath(EXPORT_FILE_PATH)),"GBK"));
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

    //本地运行时使用此方法
    private static File getPropertiesFilePath(String filePath){
//        String propertiesPath = ItemsService.class.getResource("../").getPath() + "item-export.properties";
        try {
            return new ClassPathResource("item-export.properties").getFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //打成zip包时使用此方法
//    private static String getPropertiesFilePath(){
//        String propertiePath = ItemsService.class.getResource("/item-export.properties").getPath();
//        int startPoint = propertiePath.indexOf(":");
//        startPoint += 2;
//        int cutPoint = propertiePath.indexOf("word-resolver");
//        cutPoint += "word-resolver".length();
//        propertiePath = propertiePath.substring(startPoint,cutPoint);
//        propertiePath += "/resources/item-export.properties";
//        return propertiePath;
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
