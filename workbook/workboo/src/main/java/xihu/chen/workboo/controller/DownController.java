package xihu.chen.workboo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xihu.chen.workboo.StudentEntiy;
import xihu.chen.workboo.down.ExcelDownUtil;
import xihu.chen.workboo.down.ExcelVo;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DownController {
    @Resource
    private ExcelDownUtil excelDownUtil;
    @GetMapping("/down")
    public String down()  {

        List<StudentEntiy> studentEntiys = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StudentEntiy studentEntiy = new StudentEntiy();
            studentEntiy.setAddr("chenqiang");
            studentEntiy.setAge(i);
            studentEntiy.setName("liu" + i);
            studentEntiys.add(studentEntiy);
        }
        ExcelVo excelVo = new ExcelVo();
        excelVo.setDataList(studentEntiys);
        excelVo.setSheetName("测试");
        try {
            excelDownUtil.excelDown("qiang",excelVo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "fail";
        }
        return  "success";
    }
    @GetMapping("/bulkdown")
    public String sp() {
        List<ExcelVo> excelVoList = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            List<StudentEntiy> studentEntiys = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                StudentEntiy studentEntiy = new StudentEntiy();
                studentEntiy.setAddr("chenqiang");
                studentEntiy.setAge(i);
                studentEntiy.setName("liu" + i);
                studentEntiys.add(studentEntiy);
            }
            ExcelVo excelVo = new ExcelVo();
            excelVo.setDataList(studentEntiys);
            excelVo.setSheetName("测试"+j);
            excelVoList.add(excelVo);

        }
        try {
            excelDownUtil.excelDown(".xlsx","bulk",excelVoList);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
