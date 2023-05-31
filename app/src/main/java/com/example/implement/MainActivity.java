package com.example.implement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mifonelibproj.core.Factory;
import com.example.mifonelibproj.listener.MifoneCoreListener;
import com.example.mifonelibproj.model.other.RegistrationState;
import com.example.mifonelibproj.model.other.State;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static String TAG = "IMPLEMENTDEBUG";
    String TAG_FRAGMENT_CALL = "FRAGMENT_CALL";
    EditText editText;
    ImageView button;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t0,ts,tsh,tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permission permission = new Permission(this);
        if (!permission.checkPermissions(new String[]{Manifest.permission.RECORD_AUDIO})){
            permission.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},0);
        }
        tvState = findViewById(R.id.tv_state);
        editText = findViewById(R.id.editTextNumber);
        button = findViewById(R.id.button);
        t1 = findViewById(R.id.num_1);
        t2 = findViewById(R.id.num_2);
        t3 = findViewById(R.id.num_3);
        t4 = findViewById(R.id.num_4);
        t5 = findViewById(R.id.num_5);
        t6 = findViewById(R.id.num_6);
        t7 = findViewById(R.id.num_7);
        t8 = findViewById(R.id.num_8);
        t9 = findViewById(R.id.num_9);
        t0 = findViewById(R.id.num_0);
        ts = findViewById(R.id.star);
        tsh = findViewById(R.id.sharp);

        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        t9.setOnClickListener(this);
        t0.setOnClickListener(this);
        ts.setOnClickListener(this);
        tsh.setOnClickListener(this);
        button.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.add(R.id.frame_fragment, new CallFragment(editText.getText().toString(),CallFragment.TYPE_CALL_OUT), TAG_FRAGMENT_CALL);
            fragmentTransaction.commit();
        });
        Factory.registerListener(new MifoneCoreListener() {
            @Override
            public void onResultConfigAccount(boolean isSuccess, String message) {
                Log.d(TAG, "onResultConfigAccount: "+isSuccess+", message: "+message);
            }

            @Override
            public void onCallStateChanged(State state, String message) {
                Log.d(TAG, "onCallStateChanged: "+state+", message: "+message);
                switch (state.toInt()){
                    case State.Released:
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_CALL);
                        fragmentTransaction.remove(fragment).commit();
                        break;
                    case State.IncomingReceived:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame_fragment, new CallFragment(null,CallFragment.TYPE_INCOMING), TAG_FRAGMENT_CALL);
                        fragmentTransaction.commit();
                        break;
                    case State.Connected:
                        Bundle bundle = new Bundle();
                        bundle.putString("actionConnected","connected");
                        Fragment fragmentCall = new CallFragment(null,CallFragment.TYPE_CONNECTED);
                        fragmentCall.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_fragment,fragmentCall, TAG_FRAGMENT_CALL).commit();
                        break;
                }
            }
            @Override
            public void onRegistrationStateChanged(RegistrationState state, String message) {
                Log.d(TAG, "onRegistrationStateChanged: code state: "+state.toInt()+", message: "+state.toMessage());
                if(state.toInt()==RegistrationState.PROGRESS){
                    tvState.setText("Registration in progressing");
                    tvState.setTextColor(Color.parseColor("#DFA248"));
                } else if(state.toInt() == RegistrationState.OK){
                    tvState.setText("Online");
                    tvState.setTextColor(Color.parseColor("#69A30C"));
                }
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: "+message);
            }

            @Override
            public void onExpiredAccessToken() {

            }

            @Override
            public void onResultConfigProxy(boolean isSuccess) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.num_0)
            editText.setText(editText.getText().toString()+"0");
        else if(id == R.id.num_1)
            editText.setText(editText.getText().toString()+"1");
        else if(id == R.id.num_2)
            editText.setText(editText.getText().toString()+"2");
        else if (id== R.id.num_3)
            editText.setText(editText.getText().toString()+"3");
        else if (id == R.id.num_4)
            editText.setText(editText.getText().toString()+"4");
        else if (id == R.id.num_5)
            editText.setText(editText.getText().toString()+"5");
        else if (id == R.id.num_6)
            editText.setText(editText.getText().toString()+"6");
        else if (id == R.id.num_7)
            editText.setText(editText.getText().toString()+"7");
        else if (id == R.id.num_8)
            editText.setText(editText.getText().toString()+"8");
        else if (id == R.id.num_9)
            editText.setText(editText.getText().toString()+"9");
        else if (id == R.id.sharp)
            editText.setText(editText.getText().toString()+"*");
        else if (id == R.id.star)
            editText.setText(editText.getText().toString()+"#");
    }
}