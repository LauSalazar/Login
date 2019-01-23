package com.example.salazarlaura.login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salazarlaura.login.R;
import com.example.salazarlaura.login.helper.Constants;
import com.example.salazarlaura.login.helper.CustomSharedPreferences;
import com.example.salazarlaura.login.helper.ValidateInternet;
import com.example.salazarlaura.login.models.User;
import com.example.salazarlaura.login.services.Repository;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    private EditText etUsuario;
    private EditText etSenha;
    private ValidateInternet validateInternet;
    private Repository repository;
    private Button btnIngresar;
    private CustomSharedPreferences customSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validateInternet = new ValidateInternet(this);
        repository = new Repository();

        etUsuario = findViewById(R.id.etUsuario);
        etUsuario.addTextChangedListener(this);
        etSenha = findViewById(R.id.etSenha);
        etSenha.addTextChangedListener(this);
        btnIngresar = findViewById(R.id.btnIngresar);
        customSharedPreferences = new CustomSharedPreferences(LoginActivity.this);
        verifyToken();
    }
    public void hacerLogin(View view) {
        if(validateInternet.verificarInternet()){
            createThreadToLogin();
        } else {
            showToast("No se pudo conectar a internet");
        }
    }

    private void verifyToken(){
        if (customSharedPreferences.getString(Constants.TOKEN)!=null){
            hacerAutoLogin(customSharedPreferences.getString(Constants.TOKEN));
        }
    }

    private void hacerAutoLogin(String token){
        if (validateInternet.verificarInternet()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        User user = repository.getUser(customSharedPreferences.getString(Constants.TOKEN));
                        startProfile(user);
                    } catch (IOException e){
                        showToast(e.getMessage());
                    }

                }
            });
            thread.start();
        } else {
            showToast("No se pudo conectar a internet");
        }
    }

    private void createThreadToLogin() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loginRepository();
            }
        });
        thread.start();
    }

    private void loginRepository() {
        try {
            User user = repository.getUser(etUsuario.getText().toString(), etSenha.getText().toString());
            saveToken(user.getToken());
            saveUser(user);

            startProfile(user);
            showToast(user.getName());
        } catch (IOException e) {
            showToast(e.getMessage());
        }
    }

    private void saveUser(User user){
        customSharedPreferences.saveUser(Constants.USER,user);
    }

    private void saveToken(String token){
        customSharedPreferences.addString(Constants.TOKEN,token);
    }

    private void startProfile(User user){
        Intent intent = new Intent( LoginActivity.this, ProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


    private void showToast(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!etUsuario.getText().toString().trim().isEmpty() &&
                !etSenha.getText().toString().trim().isEmpty()){
            btnIngresar.setEnabled(true);
        }
    }
}
