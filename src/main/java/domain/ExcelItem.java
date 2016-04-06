package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaiwenjie on 2016/3/19.
 * 采用单例存储需要解析的属性，在构造函数中构造
 */
public class ExcelItem {
    private static final ExcelItem excelItem = new ExcelItem();//字段
    private List<String> itemList = new ArrayList<String>();//字段
    private ExcelItem(){//构造方法：用来构造对象
        String items = "";
        File file = new File("src/main/resources/item-export.properties");
        BufferedReader br = null;
        try{
            br =new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                items += line;
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
        String[] itemArr = items.split("，");
        for(String item : itemArr){
            itemList.add(item);
        }
    }

    public static ExcelItem getInstance(){
        return excelItem;
    }

    public ExcelItem reSetItems(String items){
        itemList.removeAll(itemList);
        String[] itemArr = items.split(",");
        for(String item : itemArr){
            itemList.add(item);
        }
        return excelItem;
    }

    public List<String> getItemList(){
        return itemList;
    }
}
