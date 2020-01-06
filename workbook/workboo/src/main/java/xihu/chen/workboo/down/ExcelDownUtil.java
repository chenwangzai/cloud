package xihu.chen.workboo.down;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
@Component
public class ExcelDownUtil {
    Logger logger= LoggerFactory.getLogger(ExcelDownUtil.class);
    private final String EXCEL_TYPE=".xls";
    public  void excelDown(String fileName, ExcelVo excelVo) throws UnsupportedEncodingException {
        excelDown(EXCEL_TYPE,fileName,excelVo);
    }
    public  void excelDown(String excelType,String fileName, ExcelVo excelVo) throws UnsupportedEncodingException {
        if(Objects.isNull(excelVo)){
            logger.info("the date of {} is null",fileName);
            throw new BusinessException("下载数据不能为空");
        }else {
            setHead(fileName,excelType);
            Workbook wb=this.getWorkBook(excelType);
            this.setWorkBook(wb,excelVo);
            write(wb);
        }
    }
    public  void excelDown(String fileName, List<ExcelVo> excelVoList) throws UnsupportedEncodingException {
        excelDown( EXCEL_TYPE, fileName, excelVoList);
    }
    public  void excelDown(String excelType,String fileName, List<ExcelVo> excelVoList) throws UnsupportedEncodingException {

        if(Objects.isNull(excelVoList)){
            logger.info("the date of {} is null",fileName);
            throw new BusinessException("下载数据不能为空");
        }else{
            setHead(fileName,excelType);
            Workbook  workbook=this.getWorkBook(excelType);
            for(ExcelVo vo:excelVoList){
                 setWorkBook(workbook,vo);
            }
            write(workbook);
        }
    }

    public void write(Workbook wb){
        HttpServletResponse response=HttpServletUtil.getResponse();
        ServletOutputStream os=null;
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (IOException e) {
            logger.info(e.toString());
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.info(e.getCause().toString());
                    throw  new BusinessException(e.getCause().toString(),e);
                }
            }
        }
    }
    public Workbook getWorkBook(String excelType){
        Workbook wb=null;
        if(wb==null){
            if(this.EXCEL_TYPE.equalsIgnoreCase(excelType)){
                wb=new HSSFWorkbook();
            }else {
                wb=new XSSFWorkbook();
            }
        }
        return wb;
    }
    public Workbook setWorkBook(Workbook wb,ExcelVo excelVo){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Sheet sheet = wb.createSheet(excelVo.getSheetName());
        sheet.setDefaultColumnWidth(50);
        sheet.setDefaultRowHeight((short)400);

        Row row = sheet.createRow(0);
        List<?> dataList = excelVo.getDataList();
        Class<?> dataClass = dataList.get(0).getClass();
        Field[] fields = dataClass.getDeclaredFields();
        int i=0;
        for(Field field:fields){
            FieldTitle annotation = field.getAnnotation(FieldTitle.class);
            Cell cell = row.createCell(i);
            cell.setCellValue(annotation.value()!=null?annotation.value():field.getName());
            cell.setCellStyle(cellStyle);
            i++;
        }
        int rowNum=dataList.size();
        int j=1;
        for(Object data:dataList){
            Row erow = sheet.createRow(j);
            for(int k=0;k<fields.length;k++){
                try {
                    Field field=fields[k];
                    field.setAccessible(true);
                    Object value = field.get(data);
                    Cell cell = erow.createCell(k);
                    cell.setCellValue(Objects.isNull(value)?"":value.toString());
                    cell.setCellStyle(cellStyle);
                } catch (IllegalAccessException e) {
                    erow.createCell(k).setCellValue("");
                }
            }
            j++;
        }
        return wb;
    }


    public void setHead(String fileName,String type) throws UnsupportedEncodingException {
        HttpServletResponse response=HttpServletUtil.getResponse();
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setHeader("Content-Disposition","attachment; filename="+fileName+type);
        response.setContentType("application/octet-stream");
        response.addHeader("Cache-Control","no-cache");
    }

}
