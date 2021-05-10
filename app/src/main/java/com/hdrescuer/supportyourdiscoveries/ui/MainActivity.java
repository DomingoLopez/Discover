package com.hdrescuer.supportyourdiscoveries.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hdrescuer.supportyourdiscoveries.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText email;
    EditText password;
    TextView register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        findViews();
        events();
    }


    void findViews(){
        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.login = findViewById(R.id.btn_login);
        this.register = findViewById(R.id.tvRegister);
    }

    void events(){
        this.login.setOnClickListener(this);
        this.register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                Intent i_login = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i_login);
                finish();
                break;

            case R.id.tvRegister:

                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();

                break;
        }
    }
}