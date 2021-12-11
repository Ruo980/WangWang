package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.adapter.DataMangerAdpater;
import com.example.wangwang.dao.ExpenditureDao;
import com.example.wangwang.dao.IncomeDao;
import com.example.wangwang.dao.NotesDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Expenditure;
import com.example.wangwang.entity.Income;
import com.example.wangwang.entity.Notes;
import com.example.wangwang.tools.CreateExcel;
import com.example.wangwang.tools.CreateTxt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DataMangerActivity extends AppCompatActivity {

    private String account;
    private ListView listView;
    private List<Expenditure> list1 = new LinkedList<Expenditure>();
    private List<Income> list2 = new LinkedList<Income>();
    private List<Notes> list3 = new LinkedList<Notes>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {//支出总表下载
                AlertDialog.Builder builder = new AlertDialog.Builder(DataMangerActivity.this);
                builder.setIcon(R.drawable.logo)
                        .setTitle("下载")
                        .setMessage("选择输出格式？")
                        .setPositiveButton("下载Excel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            ExpenditureDao expenditureDao = db.ExpenditureDao();
                                            list1 = expenditureDao.loadExpendituresByAccount(account);
                                            CreateExcel createExcel = new CreateExcel();
                                            createExcel.createExcelExpenditureByname(getApplicationContext(), "expenditure.xls", "expenditure", list1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    Toast.makeText(DataMangerActivity.this,"下载成功",Toast.LENGTH_SHORT);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("下载Txt",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            ExpenditureDao expenditureDao = db.ExpenditureDao();
                                            list1 = expenditureDao.loadExpendituresByAccount(account);
                                            CreateTxt createTxt = new CreateTxt();
                                            createTxt.createTxtExpenditureByname(getApplicationContext(),"expenditure.txt","expenditure",list1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    Toast.makeText(DataMangerActivity.this,"下载成功",Toast.LENGTH_SHORT);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
            } else if (msg.what == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DataMangerActivity.this);
                builder.setIcon(R.drawable.logo)
                        .setTitle("下载")
                        .setMessage("选择输出格式？")
                        .setPositiveButton("下载Excel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            IncomeDao incomeDao = db.incomeDao();
                                            list2=incomeDao.loadIncomeByAccount(account);
                                            CreateExcel createExcel = new CreateExcel();
                                            createExcel.createExcelIncomeByname(getApplicationContext(), "income.xls", "income", list2);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    Toast.makeText(DataMangerActivity.this,"下载成功",Toast.LENGTH_SHORT);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("下载Txt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            IncomeDao incomeDao = db.incomeDao();
                                            list2=incomeDao.loadIncomeByAccount(account);
                                            CreateTxt createTxt = new CreateTxt();
                                            createTxt.createTxtIncomeByname(getApplicationContext(),"income.txt","income",list2);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    Toast.makeText(DataMangerActivity.this,"下载成功",Toast.LENGTH_SHORT);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
            }else if(msg.what==2){
                AlertDialog.Builder builder = new AlertDialog.Builder(DataMangerActivity.this);
                builder.setIcon(R.drawable.logo)
                        .setTitle("下载")
                        .setMessage("选择输出格式？")
                        .setPositiveButton("下载Excel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            NotesDao notesDao = db.NotesDao();
                                            list3 = notesDao.loadNotesByAccount(account);
                                            CreateExcel createExcel = new CreateExcel();
                                            createExcel.createExcelNotesByname(getApplicationContext(), "notes.xls", "notes", list3);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    Toast.makeText(DataMangerActivity.this,"下载成功",Toast.LENGTH_SHORT);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("下载Txt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            NotesDao notesDao = db.NotesDao();
                                            list3 = notesDao.loadNotesByAccount(account);
                                            CreateTxt createTxt = new CreateTxt();
                                            createTxt.createTxtNotesByname(getApplicationContext(),"notes.txt","notes",list3);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    Toast.makeText(DataMangerActivity.this,"下载成功",Toast.LENGTH_SHORT);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manger);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        List<HashMap> list3 = new LinkedList<>();
        HashMap hashMap = new HashMap();
        hashMap.put("text", "支出总表下载");
        list3.add(hashMap);
        hashMap = new HashMap();
        hashMap.put("text", "收入总表下载");
        list3.add(hashMap);
        hashMap = new HashMap();
        hashMap.put("text", "标签总表下载");
        list3.add(hashMap);
        DataMangerAdpater dataMangerAdpater = new DataMangerAdpater(this, list3);
        listView = findViewById(R.id.my_data_list);
        listView.setAdapter(dataMangerAdpater);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = new Message();
                message.what = i;
                handler.sendMessage(message);
            }
        });

    }
}