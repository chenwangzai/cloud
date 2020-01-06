package xihu.chen.workboo.down;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExcelVo {
    @Autowired
    String sheetName;
    List<?> dataList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
