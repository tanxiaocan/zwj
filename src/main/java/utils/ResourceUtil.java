package utils;

import org.springframework.core.io.ClassPathResource;
import service.ItemsService;
import java.io.File;
import java.io.IOException;

/**
 * Created by tanxiaocan on 2016/10/11.
 */
public class ResourceUtil {
    //本地运行时使用此方法
    public static File getPropertiesFilePath(String filePath){
//        String propertiesPath = ItemsService.class.getResource("../").getPath() + "item-export.properties";
        try {
            return new ClassPathResource(filePath).getFile();
        } catch (IOException e) {
            System.out.println("文件加载不到，尝试使用jar方式加载");
            return getPropertiesFileForJar(filePath);
        }
    }

    //    打成zip包时使用此方法
    public static File getPropertiesFileForJar(String filePath){
        String propertiesPath = ItemsService.class.getResource("/" + filePath).getPath();
        System.out.println(propertiesPath);
        int startPoint = propertiesPath.indexOf(":");
        startPoint += 2;
        int cutPoint = propertiesPath.indexOf("word-resolver");
        cutPoint += "word-resolver".length();
        propertiesPath = propertiesPath.substring(startPoint,cutPoint);
        propertiesPath += "/resources/" + filePath;
        System.out.println(propertiesPath);
        return new File(propertiesPath);
    }
}
