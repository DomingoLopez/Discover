package com.hdrescuer.supportyourdiscoveries.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.AuthorRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText username;
    EditText password;
    TextView register;
    Button login;

    AuthorRepository authorRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        this.authorRepository = new AuthorRepository(getApplication());

        findViews();
        events();
    }


    void findViews(){
        this.username = findViewById(R.id.username);
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

                if(validateForm()){
                    Intent i_login = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i_login);
                    finish();
                }else{
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }



                break;

            case R.id.tvRegister:

                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();

                break;
        }
    }



    boolean validateForm(){

        boolean valido = false;

        if(this.username.getText().toString().isEmpty())
            this.username.setError("Nombre de usuario requerido");
        else if(this.password.getText().toString().isEmpty())
            this.password.setError("Contraseña requerida");
        else{

            AuthorEntity authorEntity = this.authorRepository.getAuthorByUserName(this.username.getText().toString());


            if(authorEntity != null && authorEntity.getPassword().equals(this.password.getText().toString())){

                Constants.ID = authorEntity.getId();
                Constants.USERNAME = authorEntity.getUsername();
                Constants.EMAIL = authorEntity.getEmail();
                Constants.PHOTO =  authorEntity.getMain_photo_url();
                valido = true;
            }


        }

        return valido;
    }
}