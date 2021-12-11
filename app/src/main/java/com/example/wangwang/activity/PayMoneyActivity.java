package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.adapter.PayAdapter;
import com.example.wangwang.dao.ExpenditureDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Expenditure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PayMoneyActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private String account;
    private EditText editText;
    private Spinner payType;
    //    两个用于输入备注
    private EditText editText2;
    private String notes;
    private Button save;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {//记录新增支出成功
                Toast.makeText(PayMoneyActivity.this, R.string.pay_success, Toast.LENGTH_SHORT).show();
            } else {//记录失败
                Toast.makeText(PayMoneyActivity.this, R.string.pay_failure, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
//      设置时间弹出框
        editText = findViewById(R.id.pay_money_time);
        editText.setFocusable(false);
        editText.setOnClickListener(this);
//      初始化类型下拉框
        List<HashMap> list = getList();
        PayAdapter payAdapter = new PayAdapter(this, list);
        payType = findViewById(R.id.pay_money_type);
        payType.setPrompt("请选择支付类型");
        payType.setSelection(0);
        payType.setAdapter(payAdapter);
//        设置备注
        editText2 = findViewById(R.id.pay_money_notes);
        editText2.setFocusable(false);
        editText2.setOnClickListener(this);
//        设置提交按钮
        save = findViewById(R.id.save_pay_money);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pay_money_time) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else if (view.getId() == R.id.save_pay_money) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //金额
                        EditText editText = findViewById(R.id.pay_money_count);
                        int payMoney = Integer.parseInt(editText.getText().toString());
                        //时间
                        editText = findViewById(R.id.pay_money_time);
                        String saveTime = editText.getText().toString();
//                        字符串和util.Date的转换关系
//                        获取月、星期
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        Date payDate = null;
                        int month=0;
                        int week=0;
                        try {
                            payDate = simpleDateFormat.parse(saveTime);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(payDate);
                            month = cal.get(Calendar.MONTH)+1;
                            week = cal.get(Calendar.WEEK_OF_MONTH);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //类型
                        int position = payType.getSelectedItemPosition();
                        List<HashMap> list = getList();
                        HashMap hashMap = list.get(position);
                        String typeText = ((String) hashMap.get("text")).replace(" ", "");
                        //地点
                        editText = findViewById(R.id.pay_money_place);
                        String payPlace = editText.getText().toString();
                        //备注
                        editText = findViewById(R.id.pay_money_notes);
                        notes = editText.getText().toString();
                        Expenditure expenditure = new Expenditure(account, payMoney, saveTime,month,week, typeText, payPlace, notes);
                        FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                        Message message = new Message();
                        ExpenditureDao expenditureDao = db.ExpenditureDao();
                        expenditureDao.insertExpenditure(expenditure);
                        //插入成功，所有值清0，同时Toast告知消息
                        //金额
                        editText = findViewById(R.id.pay_money_count);
                        editText.setText(null);
                        editText = findViewById(R.id.pay_money_time);
                        editText.setText(null);
                        editText = findViewById(R.id.pay_money_place);
                        editText.setText(null);
                        //备注
                        editText = findViewById(R.id.pay_money_notes);
                        editText.setText(null);
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                        //插入失败，Toast告知消息
                        e.printStackTrace();
                    }

                }
            }).start();

        } else if (view.getId() == R.id.pay_money_notes) {
            //    弹出框
            EditText editText3 = new EditText(PayMoneyActivity.this);
            //将原始值读入其中
            notes = editText2.getText().toString();
            editText3.setText(notes);
            editText3.setMaxLines(20);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notes")
                    .setIcon(R.drawable.logo)
                    .setView(editText3)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            notes = editText3.getText().toString();
                            editText2.setText(notes);
                        }
                    })
                    .show();

        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String desc = String.format("%d年%d月%d日", year, month + 1, day);
        editText.setText(desc);
    }
//类型数据
    private List<HashMap> getList() {
        //      初始化类型下拉框
        List<HashMap> list = new LinkedList<HashMap>();
        HashMap hashMap = new HashMap();
        hashMap.put("text", "吃       饭");
        hashMap.put("image", R.drawable.pay_type_eat);
        list.add(hashMap);
        hashMap = new HashMap();
        hashMap.put("text", "旅       游");
        hashMap.put("image", R.drawable.pay_type_travel);
        list.add(hashMap);
        hashMap = new HashMap();
        hashMap.put("text", "娱       乐");
        hashMap.put("image", R.drawable.pay_type_game);
        list.add(hashMap);
        hashMap = new HashMap();
        hashMap.put("text", "观       影");
        hashMap.put("image", R.drawable.pay_type_watch);
        list.add(hashMap);
        return list;
    }
//星期数据
    public static String getWeek(Date date){

        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(week_index);
        if(week_index<0){
            week_index = 0;
        }
        System.out.println(weeks[week_index]);
        return weeks[week_index];

    }
}