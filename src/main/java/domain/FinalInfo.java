package domain;

import java.util.List;

/**
 * Created by tanxiaocan on 2016/3/20.
 */
public class FinalInfo {
    private List<String> successList;//记录成功解析的文件
    private List<String> failList;//记录解析失败的文件

    public FinalInfo(List<String> successList, List<String> failList) {
        this.successList = successList;
        this.failList = failList;
    }

    public List<String> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public List<String> getFailList() {
        return failList;
    }

    public void setFailList(List<String> failList) {
        this.failList = failList;
    }
}
