package com.fis.crm.commons;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExcelReportUtils {

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int WIDTH = 255;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");



    public <E> ByteArrayInputStream export(List<E> obj, List<String> header, List<String> column, String title,
                                           List<Integer> lsSize, List<Integer> lsAlign, String... underHeader) throws Exception {
        return createWorkbook("Data", obj, header, column, title, lsSize, lsAlign, underHeader);
    }

    public <E> ByteArrayInputStream export(String sheetName, List<E> obj, List<String> header, List<String> column, String title,
                                           List<Integer> lsSize, List<Integer> lsAlign, String... underHeader) throws Exception {
        return createWorkbook(sheetName, obj, header, column, title, lsSize, lsAlign, underHeader);
    }

    public <E> ByteArrayInputStream createWorkbook(String sheetName, List<E> obj, List<String> header, List<String> column, String title,
                                                   List<Integer> lsSize, List<Integer> lsAlign, String... underHeader) throws Exception {

        // 1. Set Title
        XSSFWorkbook my_workbook = new XSSFWorkbook();
        XSSFSheet my_sheet = my_workbook.createSheet(sheetName);

        // Set column width
        for (int i = 0; i < header.size(); i++) {
            my_sheet.setColumnWidth(1 + i, lsSize.get(i));
        }

        CellStyle styleTitle = my_workbook.createCellStyle();
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setFillForegroundColor(IndexedColors.GREEN.index);
        styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont fontTitle = my_workbook.createFont();
        fontTitle.setFontHeightInPoints((short) 14);
        fontTitle.setBold(true);
        fontTitle.setFontName("Times New Roman");
        fontTitle.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        styleTitle.setFont(fontTitle);

        XSSFFont fontHeader1 = my_workbook.createFont();
        fontHeader1.setFontHeightInPoints((short) 12);
        fontHeader1.setFontName("Times New Roman");

        XSSFFont fontHeader = my_workbook.createFont();
        fontHeader.setFontHeightInPoints((short) 12);
        fontHeader.setFontName("Times New Roman");

        fontHeader.setBold(true);

        XSSFCellStyle styleHeader = my_workbook.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderTop(BorderStyle.THIN);
        byte[] rgb = new byte[3];
        rgb[0] = (byte) 220;
        rgb[1] = (byte) 220;
        rgb[2] = (byte) 220;
        XSSFColor color = new XSSFColor(rgb, new DefaultIndexedColorMap());
        styleHeader.setFillForegroundColor(color);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFont(fontHeader);
        styleHeader.setWrapText(true);

        CellStyle styleParam = my_workbook.createCellStyle();
        styleParam.setAlignment(HorizontalAlignment.CENTER);
        styleParam.setFont(fontHeader);
        styleParam.setWrapText(true);

        CellStyle styleNormal = my_workbook.createCellStyle();
        styleNormal.setBorderRight(BorderStyle.THIN);
        styleNormal.setBorderLeft(BorderStyle.THIN);
        styleNormal.setBorderBottom(BorderStyle.THIN);
        styleNormal.setBorderTop(BorderStyle.THIN);
        styleNormal.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNormal.setWrapText(true);
        styleNormal.setFont(fontHeader1);

        CellStyle styleNumber = my_workbook.createCellStyle();
        styleNumber.setAlignment(HorizontalAlignment.CENTER);
        styleNumber.setBorderRight(BorderStyle.THIN);
        styleNumber.setBorderLeft(BorderStyle.THIN);
        styleNumber.setBorderBottom(BorderStyle.THIN);
        styleNumber.setBorderTop(BorderStyle.THIN);
        styleNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNumber.setFont(fontHeader1);

        styleHeader.setWrapText(true);

        CellStyle styleNumberRight = my_workbook.createCellStyle();
        styleNumberRight.setAlignment(HorizontalAlignment.RIGHT);
        styleNumberRight.setBorderRight(BorderStyle.THIN);
        styleNumberRight.setBorderLeft(BorderStyle.THIN);
        styleNumberRight.setBorderBottom(BorderStyle.THIN);
        styleNumberRight.setBorderTop(BorderStyle.THIN);
        styleNumberRight.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNumberRight.setWrapText(true);
        styleNumberRight.setFont(fontHeader1);

        Row row = my_sheet.createRow((short) 0);
        Cell cell = row.createCell((short) 0);
        cell.setCellValue(title);
        cell.setCellStyle(styleTitle);

        //Date export
        styleParam.setAlignment(HorizontalAlignment.CENTER);
        styleParam.setFont(fontHeader);
        styleParam.setWrapText(true);
        CellStyle dateExportStyle = my_workbook.createCellStyle();
        dateExportStyle.setAlignment(HorizontalAlignment.CENTER);
        dateExportStyle.setFont(fontHeader);
        dateExportStyle.setWrapText(true);
        short start = 5;
        row = my_sheet.createRow((short) 1);
        cell = row.createCell(0);
        cell.setCellStyle(dateExportStyle);
        cell.setCellValue(Translator.toLocale("common.dateExport") + ": "  +LocalDate.now().format(DATE_FORMAT));

        my_sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column.size()));
        my_sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, column.size()));

        //set param
        for (int i = 0; i < underHeader.length; i++) {
            Row rowP = my_sheet.createRow((short) 1+ i);
            Cell cellP = rowP.createCell((short) 0);
            cellP.setCellValue(underHeader[i]);
            cellP.setCellStyle(styleParam);
            my_sheet.addMergedRegion(new CellRangeAddress(2 + i, 2+ i, 0, column.size()));
        }

        // 2. Set Header
        // So thu tu
        row = my_sheet.createRow((short) 2 + underHeader.length);
        cell = row.createCell(0);
        cell.setCellValue("STT");
        cell.setCellStyle(styleHeader);
        for (int i = 0; i < header.size(); i++) {
            cell = row.createCell(i + 1);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(styleHeader);

        }

        // 3. Set detail
        for (int k = 0; k < obj.size(); k++) {
            row = my_sheet.createRow((short) k + 3 + underHeader.length);

            // STT
            cell = row.createCell(0);
            int stt = k + 1;
            cell.setCellValue(stt);
            cell.setCellStyle(styleNumber);

            /////////////////////
            Object valueObj = obj.get(k);
            Class c1 = valueObj.getClass();
            Field[] valueObjFields = c1.getDeclaredFields();

            for (int c = 0; c < column.size(); c++) {
                for (int i = 0; i < valueObjFields.length; i++) {
                    String fieldName = valueObjFields[i].getName();
                    if (fieldName.equals(column.get(c))) {
                        valueObjFields[i].setAccessible(true);
                        Object newObj = valueObjFields[i].get(valueObj);
                        try {
                            int d = Integer.parseInt(newObj.toString());
                            cell = row.createCell(c + 1);
                            cell.setCellValue(DataUtil.nvl(newObj, ""));

                        } catch (Exception ex) {
                            try {
                                BigDecimal d = new BigDecimal(newObj.toString());
                                cell = row.createCell(c + 1);
                                DecimalFormat df2 = new DecimalFormat("###,###,###,###.####");
                                cell.setCellValue(df2.format(d));
                            } catch (Exception e) {
                                cell = row.createCell(c + 1);
                                cell.setCellValue(DataUtil.nvl(newObj, ""));
                            }
                        }
                        if (lsAlign.get(c) == 0) {
                            cell.setCellStyle(styleNormal);
                        }
                        if (lsAlign.get(c) == 1) {
                            cell.setCellStyle(styleNumber);
                        }
                        if (lsAlign.get(c) == 2) {
                            cell.setCellStyle(styleNumberRight);
                        }

                        break;
                    }
                }
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //FileOutputStream fileOut = new FileOutputStream("export-test.xlsx");
        //my_workbook.write(fileOut);
        my_workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
