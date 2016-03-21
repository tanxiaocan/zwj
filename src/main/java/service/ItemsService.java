package main.java.service;

import main.java.utils.RespUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxiaocan on 2016/3/20.
 */
public class ItemsService {

    public static List<String> getItems(){
        List<String> items = new ArrayList<String>();
        File file = new File("src/main/resources/item-export.properties");
        BufferedReader br = null;
        try{
            br =new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
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
        File file = new File("src/main/resources/item-export.properties");
        BufferedWriter bufferedWriter = null;
        try{
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
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
}
