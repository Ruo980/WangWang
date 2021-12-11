package com.example.wangwang.tools;

import android.content.Context;

import com.example.wangwang.entity.Expenditure;
import com.example.wangwang.entity.Income;
import com.example.wangwang.entity.Notes;

import java.io.FileOutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import static android.content.Context.MODE_PRIVATE;

public class CreateExcel {

    private WritableWorkbook writableWorkbook;
    private WritableSheet writableSheet;
    private Context context;

    public void createExcelExpenditureByname(Context context, String fileName, String tableName, List<Expenditure> list) {
        this.context = context;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            writableWorkbook = Workbook.createWorkbook(fileOutputStream);
            writableSheet = writableWorkbook.createSheet(tableName, 0);
            //设置表头
            String[] title = {"金额", "时间", "类别", "地点", "备注"};
            Label label;
            for (int i = 0; i < title.length; i++) {
                // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                // 在Label对象的子对象中指明单元格的位置和内容
                label = new Label(i, 0, title[i], getHeaderCellStyle());
                // 将定义好的单元格添加到工作表中
                writableSheet.addCell(label);
            }
            //生成内容
            for (int i = 1; i <= list.size(); i++) {
                Expenditure expenditure =list.get(i-1);
                label = new Label(0,i,expenditure.getPayMoney()+"",getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(1,i,expenditure.getPayDate(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(2,i,expenditure.getPayType(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(3,i,expenditure.getPayPlace(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(4,i,expenditure.getNotes(),getBodyCellStyle());
                writableSheet.addCell(label);
            }
            writableWorkbook.write();
            //最后一步，关闭工作簿
            writableWorkbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createExcelIncomeByname(Context context, String fileName, String tableName, List<Income> list) {
        this.context = context;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            writableWorkbook = Workbook.createWorkbook(fileOutputStream);
            writableSheet = writableWorkbook.createSheet(tableName, 0);
            //设置表头
            String[] title = {"金额", "时间", "类别", "付款方", "备注"};
            Label label;
            for (int i = 0; i < title.length; i++) {
                // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                // 在Label对象的子对象中指明单元格的位置和内容
                label = new Label(i, 0, title[i], getHeaderCellStyle());
                // 将定义好的单元格添加到工作表中
                writableSheet.addCell(label);
            }
            //生成内容
            for (int i = 1; i <= list.size(); i++) {
                Income income =list.get(i-1);
                label = new Label(0, i,income.getIncomeMoney()+"",getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(1,i,income.getIncomeDate(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(2,i,income.getIncomeType(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(3,i,income.getPayPeople(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(4,i,income.getNotes(),getBodyCellStyle());
                writableSheet.addCell(label);
            }
            writableWorkbook.write();
            //最后一步，关闭工作簿
            writableWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createExcelNotesByname(Context context, String fileName, String tableName, List<Notes> list) {
        this.context = context;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            writableWorkbook = Workbook.createWorkbook(fileOutputStream);
            writableSheet = writableWorkbook.createSheet(tableName, 0);
            //设置表头
            String[] title = {"标签名", "时间", "内容"};
            Label label;
            for (int i = 0; i < title.length; i++) {
                // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                // 在Label对象的子对象中指明单元格的位置和内容
                label = new Label(i, 0, title[i], getHeaderCellStyle());
                // 将定义好的单元格添加到工作表中
                writableSheet.addCell(label);
            }
            //生成内容
            for (int i = 1; i <= list.size(); i++) {
                Notes notes =list.get(i-1);
                label = new Label(0, i,notes.getName(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(1,i,notes.getNotesDate(),getBodyCellStyle());
                writableSheet.addCell(label);
                label = new Label(2,i,notes.getContent(),getBodyCellStyle());
                writableSheet.addCell(label);
            }
            writableWorkbook.write();
            //最后一步，关闭工作簿
            writableWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 表头单元格样式的设定
     */
    public WritableCellFormat getHeaderCellStyle() {

        /*
         * WritableFont.createFont("宋体")：设置字体为宋体
         * 10：设置字体大小
         * WritableFont.BOLD:设置字体加粗（BOLD：加粗     NO_BOLD：不加粗）
         * false：设置非斜体
         * UnderlineStyle.NO_UNDERLINE：没有下划线
         */
        WritableFont font = new WritableFont(WritableFont.createFont("宋体"),
                10,
                WritableFont.BOLD,
                false,
                UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat headerFormat = new WritableCellFormat(NumberFormats.TEXT);
        try {
            //添加字体设置
            headerFormat.setFont(font);
            //设置单元格背景色：表头为黄色
            headerFormat.setBackground(Colour.YELLOW);
            //设置表头表格边框样式
            //整个表格线为粗线、黑色
            headerFormat.setBorder(Border.ALL, BorderLineStyle.THICK, Colour.BLACK);
            //表头内容水平居中显示
            headerFormat.setAlignment(Alignment.CENTRE);
        } catch (Exception e) {
            System.out.println("表头单元格样式设置失败！");
        }
        return headerFormat;
    }

    /**
     * 表头单元格样式的设定
     */
    public WritableCellFormat getBodyCellStyle() {

        /*
         * WritableFont.createFont("宋体")：设置字体为宋体
         * 10：设置字体大小
         * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗     NO_BOLD：不加粗）
         * false：设置非斜体
         * UnderlineStyle.NO_UNDERLINE：没有下划线
         */
        WritableFont font = new WritableFont(WritableFont.createFont("宋体"),
                10,
                WritableFont.NO_BOLD,
                false,
                UnderlineStyle.NO_UNDERLINE);

        WritableCellFormat bodyFormat = new WritableCellFormat(font);
        try {
            //设置单元格背景色：表体为白色
            bodyFormat.setBackground(Colour.WHITE);
            //设置表头表格边框样式
            //整个表格线为细线、黑色
            bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

        } catch (Exception e) {
            System.out.println("表体单元格样式设置失败！");
        }
        return bodyFormat;
    }
}
