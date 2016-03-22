package domain;

import java.util.List;

/**
 * Created by tanxiaocan on 2016/3/20.
 */
public class DocResolveInfo {
    private List<String> cellStrings;
    private String filePath;
    private List<String> itemList;

    public DocResolveInfo(List<String> cellStrings, String filePath,List<String> itemList) {
        this.cellStrings = cellStrings;
        this.filePath = filePath;
        this.itemList = itemList;
    }

    public List<String> getCellStrings() {
        return cellStrings;
    }

    public void setCellStrings(List<String> cellStrings) {
        this.cellStrings = cellStrings;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }
}
