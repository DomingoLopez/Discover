package com.hdrescuer.supportyourdiscoveries.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.SessionManager;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.AuthorRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    Button register;
    EditText username;
    EditText email;
    EditText password;
    TextView tvlogin;

    AuthorRepository authorRepository;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        this.authorRepository = new AuthorRepository(getApplication());
        session = new SessionManager(this);
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

                if(validateForm()){

                    this.authorRepository.insertAuthor(new AuthorEntity(
                            this.username.getText().toString(),
                            this.email.getText().toString(),
                            this.password.getText().toString(),
                            ""
                    ));


                    Constants.USERNAME = username.getText().toString();
                    Constants.EMAIL = email.getText().toString();
                    Constants.PHOTO = "";

                    session.setLogin(true);
                    session.setInitials(Constants.USERNAME,Constants.EMAIL,Constants.PHOTO);

                    Intent i_register = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(i_register);
                    finish();
                    break;
                }




            case R.id.tvLogin:

                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                finish();

                break;
        }
    }


    boolean validateForm(){

        boolean valido = false;

        if(this.username.getText().toString().isEmpty())
            this.username.setError("Nombre de usuario requerido");
        else if(this.email.getText().toString().isEmpty())
            this.email.setError("Email requerido");
        else if(this.password.getText().toString().isEmpty())
            this.password.setError("Contraseña requerida");
        else
            valido = true;


        return valido;
    }
}