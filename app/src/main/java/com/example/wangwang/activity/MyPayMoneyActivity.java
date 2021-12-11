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

import com.example.wangwang.R;
import com.example.wangwang.adapter.MyPayAdapter;
import com.example.wangwang.dao.ExpenditureDao;
import com.example.wangwang.dao.IncomeDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Expenditure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyPayMoneyActivity extends AppCompatActivity {

    private String account;
    private int count = 0;//消费总额
    private TextView countView;
    private ListView listView;
    private MyPayAdapter myPayAdapter;
    private List<Expenditure> list = new LinkedList<Expenditure>();
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
                                ExpenditureDao expenditureDao = db.ExpenditureDao();
                                list = expenditureDao.loadExpendituresByAccount(account);
                                for (Expenditure expenditure : list) {
                                    count = count + expenditure.getPayMoney();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("支出总额:"+count);
                        myPayAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {//表示特定月查询
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            ExpenditureDao expenditureDao = db.ExpenditureDao();
                            list = expenditureDao.selectExpenditureByMonth(account,month);
                            for (Expenditure expenditure : list) {
                                count = count + expenditure.getPayMoney();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("月支出总额:"+count);
                        myPayAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
                HashMap hashMap = (HashMap) msg.obj;
                int week = Integer.parseInt((String) hashMap.get("week"));
                int month = Integer.parseInt((String) hashMap.get("month"));
                if (week!=0) {//不等于0按周查
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            ExpenditureDao expenditureDao = db.ExpenditureDao();
                            list = expenditureDao.selectExpenditureByMonthAndWeek(account,month, week);
                            for (Expenditure expenditure : list) {
                                count = count + expenditure.getPayMoney();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("周支出总额:" + count);
                        myPayAdapter.changeData(list);
                        count = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(week==0){//周为0，则全月查询
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            ExpenditureDao expenditureDao = db.ExpenditureDao();
                            list = expenditureDao.selectExpenditureByMonth(account,month);
                            for (Expenditure expenditure : list) {
                                count = count + expenditure.getPayMoney();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("月支出总额:"+count);
                        myPayAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 2) {//点击出现删除弹窗
                Expenditure expenditure = (Expenditure) msg.obj;
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPayMoneyActivity.this);
                builder.setIcon(R.drawable.logo)
                        .setTitle("删除")
                        .setMessage("确定删除" + expenditure.getPayDate() + "的" + expenditure.getPayType() + "消费记录吗？\n" + "备注为:" + expenditure.getNotes())
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                            ExpenditureDao expenditureDao = db.ExpenditureDao();
                                            expenditureDao.deleteExpenditure(expenditure);
                                            list = expenditureDao.loadExpendituresByAccount(account);
                                            for (Expenditure expenditure : list) {
                                                count = count + expenditure.getPayMoney();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                    countView.setText("支出总额:"+count);
                                    myPayAdapter.changeData(list);
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
        setContentView(R.layout.activity_my_pay_money);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        countView=findViewById(R.id.my_pay_count);
        listView = findViewById(R.id.my_pay_list);
        myPayAdapter = new MyPayAdapter(MyPayMoneyActivity.this, list);
        listView.setAdapter(myPayAdapter);
        spinnerMonth = findViewById(R.id.spinner_pay_month);
        spinnerMonth.setPrompt("按月查询");
        spinnerMonth.setSelection(0);
        spinnerMonth.setGravity(Gravity.CENTER);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
        spinnerMonth.setAdapter(adapter1);
        //spinner按月查询监听
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
        spinnerWeek = findViewById(R.id.spinner_pay_week);
        spinnerWeek.setPrompt("按周查询");
        spinnerWeek.setSelection(0);
        spinnerWeek.setGravity(Gravity.CENTER);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weeks);
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

        //删除Item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.my_pay_list_id);
                int id = Integer.parseInt(textView.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                        ExpenditureDao expenditureDao = db.ExpenditureDao();
                        List<Expenditure> list = expenditureDao.selectExpenditureById(id);
                        Expenditure expenditure = list.get(0);
                        Message message = new Message();
                        message.what = 2;
                        message.obj = expenditure;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

}