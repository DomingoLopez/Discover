package com.hdrescuer.supportyourdiscoveries.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hdrescuer.supportyourdiscoveries.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    Button register;
    EditText username;
    EditText email;
    EditText password;
    TextView tvlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();


        findViews();
        events();
    }


    void findViews(){
        this.email = findViewById(R.id.email_register);
        this.password = findViewById(R.id.password_register);
        this.register = findViewById(R.id.btn_register);
        this.tvlogin = findViewById(R.id.tvLogin);
        this.username = findViewById(R.id.name_register);
    }

    void events(){
        this.register.setOnClickListener(this);
        this.tvlogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_register:
                Intent i_register = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(i_register);
                finish();
                break;

            case R.id.tvLogin:

                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                finish();

                break;
        }
    }
}