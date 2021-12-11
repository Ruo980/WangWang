package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.dao.NotesDao;
import com.example.wangwang.dao.UserDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UpdatePasswordActivity extends AppCompatActivity {

    private String account;
    private String password;
    private TextView textView;
    private Button button;
    private List<User> user = new LinkedList<User>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(UpdatePasswordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdatePasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        textView=findViewById(R.id.update_password_text);
        button =findViewById(R.id.update_password_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=textView.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                        UserDao userDao = db.userDao();
                        User user = new User(account,password,"男");
                        userDao.updateUser(user);
                        Message msg = new Message();
                        msg.what=1;
                        handler.sendMessage(msg);

                    }
                }).start();
            }
        });
    }
}