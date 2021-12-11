package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.dao.UserDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.User;

import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText accountView;
    private EditText passwordView;
    private ImageButton imageButton;
    private String account;
    private String password;
    private List<User> list = new LinkedList<User>();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==1){
                Toast.makeText(LoginActivity.this,R.string.login_false,Toast.LENGTH_LONG).show();
            }else if(msg.what==0){
                Intent intent =new Intent(LoginActivity.this,FunctionActivity.class);
                intent.putExtra("account",account);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageButton = findViewById(R.id.next_step);
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.nextbutton_translate);
        imageButton.startAnimation(translate);
        accountView = findViewById(R.id.account);
        passwordView = findViewById(R.id.password);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                UserDao userDao = db.userDao();
                list = userDao.loadAllUsers();

            }
        });
        thread.start();
        try {
            thread.join();
            User user = list.get(0);
            accountView.setText(user.getAccount());
            passwordView.setText(user.getPassword());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Animation scale = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.nextbutton_scale);
        imageButton.startAnimation(scale);
        account = accountView.getText().toString();
        password = passwordView.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                UserDao userDao = db.userDao();
                List user = userDao.loginUser(account, password);
                Message message =new Message();
                if (user.size() == 0) {
                    message.what=1;
                    handler.sendMessage(message);
                }else{//登录验证成功
                    message.what=0;
                    handler.sendMessage(message);
                }
            }
        }).start();


    }
}