package com.isi.utils;

import com.isi.dto.BaseException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/19 21:21
 * @description
 */
public class ExcelUtils {
    private static Workbook wb;
    private static Sheet sheet;
    private static Row row;

    private static final String EXCEL_XLS = "xls";
    private static final String CSV = "csv";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 读取表头
     *
     * @param inputStream in
     * @param suffix      文件后缀
     * @return map <列下标，数据>
     */
    public static Map<Integer, String> readExcelTitle(InputStream inputStream, String suffix) {
        getWorkbook(inputStream, suffix);
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < colNum; i++) {
            map.put(i, row.getCell(i).getStringCellValue());
        }
        return map;



    }

    /**
     * 读取excel内容:表头+数据内容
     *
     * @param inputStream 文件
     * @return Map<行下标, Map < 列下标, Str>>
     */
    public static List<Map<String, String>> readExcelContent(InputStream inputStream, String suffix) {

        getWorkbook(inputStream, suffix);
        List<Map<String, String>> content = new ArrayList<>();
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        // 标题总列数
        int colNum1 = row.getPhysicalNumberOfCells();

        Map<Integer, String> map = new HashMap<>();

        //保存表头到map
        for (int i = 0; i < colNum1; i++) {
            map.put(i, row.getCell(i).getStringCellValue());
        }
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                int j = 0;
                Map<String, String> cellValue = new HashMap<>();
                while (j < colNum) {
                    //获取每一行对应的j列的数据
                    String obj = getCellFormatValue(row.getCell(j));
                    cellValue.put(map.get(j), obj);
                    j++;
                }
                content.add(cellValue);
            }
        }
//        System.out.println(content);
        return content;
    }


    /**
     * 判断数据类型
     *
     * @param cell cell
     * @return String
     */
    private static String getCellFormatValue(Cell cell) {
        String cellValue = null;
        if (cell != null) {
            // 判断当前Cell的Type
            CellType cellTypeEnum = cell.getCellTypeEnum();
            switch (cellTypeEnum) {
                // str
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                // num
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                        Instant instant = cell.getDateCellValue().toInstant();
                        ZoneId zoneId = ZoneId.systemDefault();
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
                        cellValue = dateTimeFormatter.format(localDateTime);
                    } else {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                default:
                    throw new BaseException("上传的表格格式有问题，请检查后重试");
            }
        }
        return cellValue;
    }


    private static void getWorkbook(InputStream inputStream, String suffix) {

        try {
            //2003
            if (EXCEL_XLS.equals(suffix)) {
                wb = new HSSFWorkbook(inputStream);
                //2007/2010
            } else if (EXCEL_XLSX.equals(suffix) || CSV.equals(suffix)) {
                wb = new XSSFWorkbook(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
