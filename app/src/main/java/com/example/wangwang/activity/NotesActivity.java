package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wangwang.R;
import com.example.wangwang.adapter.NotesAdapter;
import com.example.wangwang.dao.NotesDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Notes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private String account;
    private int count;//便签总数
    private Button button;
    private TextView countView;
    private ListView listView;
    private NotesAdapter notesAdapter;
    private List<Notes> list = new LinkedList<Notes>();
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
                                NotesDao notesDao = db.NotesDao();
                                list = notesDao.loadNotesByAccount(account);
                                count = list.size();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("便签总数:" + count);
                        notesAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {//表示特定月查询
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            NotesDao notesDao = db.NotesDao();
                            list = notesDao.selectNotesByMonth(account, month);
                            count = list.size();
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("月便签总数:" + count);
                        notesAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
                HashMap hashMap = (HashMap) msg.obj;
                int week = Integer.parseInt((String) hashMap.get("week"));
                int month = Integer.parseInt((String) hashMap.get("month"));
                if (week != 0) {//不等于0按周查
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            NotesDao notesDao = db.NotesDao();
                            list = notesDao.selectNotesByMonthAndWeek(account, month, week);
                            count = list.size();
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("周便签总数:" + count);
                        notesAdapter.changeData(list);
                        count = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (week == 0) {//周为0，则全月查询
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                            NotesDao notesDao = db.NotesDao();
                            list = notesDao.selectNotesByMonth(account, month);
                            count = list.size();
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        countView.setText("月便签总数:" + count);
                        notesAdapter.changeData(list);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        button = findViewById(R.id.my_notes_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, AddNotesActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        countView = findViewById(R.id.my_notes_count);
        listView = findViewById(R.id.my_notes_list);
        notesAdapter = new NotesAdapter(NotesActivity.this, list);
        listView.setAdapter(notesAdapter);
        spinnerMonth = findViewById(R.id.spinner_notes_month);
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
        spinnerWeek = findViewById(R.id.spinner_notes_week);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NotesActivity.this,EditNotesActivity.class);
                intent.putExtra("account",account);
                TextView textView = view.findViewById(R.id.my_notes_list_id);//注意是view中的布局，不是activity
                String id =textView.getText().toString();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        spinnerMonth.setSelection(0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        spinnerMonth.setSelection(12);
    }
}