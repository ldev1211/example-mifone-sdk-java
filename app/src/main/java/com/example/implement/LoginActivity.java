package com.example.implement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mifonelibproj.core.Factory;
import com.example.mifonelibproj.listener.MifoneCoreListener;
import com.example.mifonelibproj.model.other.ConfigMifoneCore;
import com.example.mifonelibproj.model.other.RegistrationState;
import com.example.mifonelibproj.model.other.State;
import com.example.mifonelibproj.model.other.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextView btnLogin;
    User user;
    View viewDialog;
    TextInputEditText textInputLayoutUsername,textInputLayoutPassword;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputLayoutPassword = findViewById(R.id.edt_password);
        textInputLayoutUsername = findViewById(R.id.edt_username);
        btnLogin = findViewById(R.id.btn_login);
        viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_prog,null);
        Factory.registerListener(new MifoneCoreListener() {
            @Override
            public void onResultConfigAccount(boolean isSuccess, String message) {
                if(isSuccess){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onCallStateChanged(State state, String message) {

            }

            @Override
            public void onRegistrationStateChanged(RegistrationState state, String message) {

            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onExpiredAccessToken() {

            }

            @Override
            public void onResultConfigProxy(boolean isSuccess) {

            }
        });
        btnLogin.setOnClickListener(v->{
            if(dialog == null) {
                dialog = new Dialog(LoginActivity.this);
                dialog.getWindow().setBackgroundDrawableResource(
                        R.color.transparent);
                dialog.setContentView(viewDialog);
            }
            dialog.show();
            user = new User(textInputLayoutUsername.getText().toString().trim(),textInputLayoutPassword.getText().toString().trim(),"sf");
            Factory.createMifoneCore(LoginActivity.this, new ConfigMifoneCore(5,"",""),user);
            Factory.configCore();
        });
    }
}