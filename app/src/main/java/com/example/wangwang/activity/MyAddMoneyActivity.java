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
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.adapter.MyAddAdapter;
import com.example.wangwang.dao.IncomeDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Income;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyAddMoneyActivity extends AppCompatActivity {

    private String account;
    private int count = 0;
    private TextView countView;
    private ListView listView;
    private MyAddAdapter myAddAdapter;
    private List<Income> list = new LinkedList<Income>();
    private String[] months = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] weeks = new String[]{"0", "1", "2", "3", "4", "5", "6"};
    private Spinner spinnerMonth;
    private Spinner spinnerWeek;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {//按月查询
                int month = Integer.parseInt((String) msg.obj);
                if (month == 0) {//全部查询,默认点开页面全部查询
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                IncomeDao incomeDao = db.incomeDao();
                                list = incomeDao.loadIncomeByAccount(account);
                                for (Income income : list) {
                                    count = count + income.getIncomeMoney();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("收入总额:" + count);
                        myAddAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {//表示特定月查询
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            IncomeDao incomeDao = db.incomeDao();
                            list = incomeDao.selectIncomeByMonth(account,month);
                            for (Income income : list) {
                                count = count + income.getIncomeMoney();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("月总额:" + count);
                        myAddAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else if (msg.what == 1) {
                HashMap hashMap = (HashMap) msg.obj;
                int week = Integer.parseInt((String) hashMap.get("week"));
                int month = Integer.parseInt((String) hashMap.get("month"));
                if (week != 0) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            IncomeDao incomeDao = db.incomeDao();
                            list = incomeDao.selectIncomeByMonthAndWeek(account,month, week);
                            for (Income income : list) {
                                count = count + income.getIncomeMoney();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("周收入总额:" + count);
                        myAddAdapter.changeData(list);
                        count = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(week==0){//week等于0就按照本月全部显示
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            IncomeDao incomeDao = db.incomeDao();
                            list = incomeDao.selectIncomeByMonth(account,month);
                            for (Income income : list) {
                                count = count + income.getIncomeMoney();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("月总额:" + count);
                        myAddAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 2) {//点击出现弹窗
                Income income = (Income) msg.obj;
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAddMoneyActivity.this);
                builder.setIcon(R.drawable.logo)
                        .setTitle("删除")
                        .setMessage("确定删除" + income.getIncomeDate() + "的" + income.getIncomeType() + "收入记录吗？\n" + "备注为：" + income.getNotes())
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            IncomeDao incomeDao = db.incomeDao();
                                            incomeDao.deleteIncome(income);
                                            list = incomeDao.loadIncomeByAccount(account);
                                            for (Income income : list) {
                                                count = count + income.getIncomeMoney();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    countView.setText("收入总额:" + count);
                                    myAddAdapter.changeData(list);
                                    count = 0;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_money);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        countView = findViewById(R.id.my_add_count);
        listView = findViewById(R.id.my_add_list);
        myAddAdapter = new MyAddAdapter(MyAddMoneyActivity.this, list);
        listView.setAdapter(myAddAdapter);
        spinnerMonth = findViewById(R.id.spinner_add_month);
        spinnerMonth = findViewById(R.id.spinner_add_month);
        spinnerMonth.setPrompt("按月查询");
        spinnerMonth.setSelection(0);
        spinnerMonth.setGravity(Gravity.CENTER);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months);
        spinnerMonth.setAdapter(adapter1);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String month = months[i];
                Message msg = new Message();
                msg.what = 0;
                msg.obj = month;
                handler.sendMessage(msg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerWeek = findViewById(R.id.spinner_add_week);
        spinnerWeek.setPrompt("按周查询");
        spinnerWeek.setSelection(0);
        spinnerWeek.setGravity(Gravity.CENTER);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weeks);
        spinnerWeek.setAdapter(adapter2);
        spinnerWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int month_index = spinnerMonth.getSelectedItemPosition();
                if (month_index != 0) {//等于0全部列出，在月中全列就可以了
                    String week = weeks[i];
                    String month = months[month_index];
                    HashMap hashMap = new HashMap();
                    hashMap.put("week", week);
                    hashMap.put("month", month);
                    Message msg = new Message();
                    msg.what = 1;//按周查询实际上是周月同时查询
                    msg.obj = hashMap;
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.my_add_list_id);
                int id = Integer.parseInt(textView.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                        IncomeDao incomeDao = db.incomeDao();
                        List<Income> list = incomeDao.selectIncomeById(id);
                        Income income = list.get(0);
                        Message message = new Message();
                        message.what = 2;
                        message.obj = income;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}