package com.example.wangwang.tools;

import android.content.Context;

import com.example.wangwang.entity.Expenditure;
import com.example.wangwang.entity.Income;
import com.example.wangwang.entity.Notes;

import java.io.FileOutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;

import static android.content.Context.MODE_PRIVATE;

public class CreateTxt {
    private Context context;
    public void createTxtExpenditureByname(Context context, String fileName, String tableName, List<Expenditure> list){
        this.context = context;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);

            for(Expenditure expenditure:list){
                byte[] bytes = expenditure.toString().getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createTxtIncomeByname(Context context, String fileName, String tableName, List<Income> list) {
        this.context = context;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            for(Income income:list){
                byte[] bytes = income.toString().getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createTxtNotesByname(Context context, String fileName, String tableName, List<Notes> list) {
        this.context = context;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            for(Notes notes:list){
                byte[] bytes = notes.toString().getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
