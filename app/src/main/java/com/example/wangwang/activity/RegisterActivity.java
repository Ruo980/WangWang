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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.dao.UserDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText accountView;
    private EditText passwordView;
    private RadioButton radioButton_men;
    private RadioButton radioButton_women;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Toast.makeText(RegisterActivity.this, R.string.register_false, Toast.LENGTH_LONG).show();
            } else if(msg.what == 2){
                Toast.makeText(RegisterActivity.this, "请填写完整信息", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountView = findViewById(R.id.register_account);
        passwordView = findViewById(R.id.register_password);
        radioButton_men = findViewById(R.id.sex_man);
        radioButton_women = findViewById(R.id.sex_women);
        Button button = findViewById(R.id.register_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountView.getText().toString();
                String password = passwordView.getText().toString();
                String sex = null;
                if (radioButton_men.isChecked()) {
                    sex = radioButton_men.getText().toString();
                } else {
                    sex = radioButton_women.getText().toString();
                }
                if (account == null || password == null || sex == null) {
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } else {
                    User user = new User(account, password, sex);
                    FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                UserDao userDao = db.userDao();
                                userDao.insertUser(user);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }

            }
        });
    }
}