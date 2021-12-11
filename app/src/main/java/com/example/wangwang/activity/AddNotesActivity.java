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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.dao.NotesDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Notes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNotesActivity extends AppCompatActivity {

    private String account;
    private EditText editText;
    private TextView textView;
    private String content;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(AddNotesActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNotesActivity.this, NotesActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        textView = findViewById(R.id.add_notes_text_count);
        editText = findViewById(R.id.add_notes_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                content = editText.getText().toString();
                textView.setText(String.valueOf(s.length()));
                if (s.length() == 200) {
                    Toast.makeText(AddNotesActivity.this, "字数已上限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button button = findViewById(R.id.save_notes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddNotesActivity.this);
                EditText editText1 = new EditText(AddNotesActivity.this);
                builder.setTitle("请编辑发布标签名称")
                        .setIcon(R.drawable.logo)
                        .setView(editText1)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                                        NotesDao notesDao = db.NotesDao();
                                        String name = editText1.getText().toString();
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                                        Calendar calendar = Calendar.getInstance();
                                        Date date = new Date(System.currentTimeMillis());
                                        String notesDate = simpleDateFormat.format(date);
                                        calendar.setTime(date);
                                        Notes notes = new Notes(account, name, notesDate, calendar.get(Calendar.MONTH) + 1, calendar.get(calendar.WEEK_OF_MONTH), content);
                                        try {
                                            notesDao.insertNotes(notes);
                                            Message msg = new Message();
                                            msg.what = 1;
                                            handler.sendMessage(msg);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                thread.start();

                            }
                        })
                        .show();
            }
        });
    }
}