package com.example.implement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mifonelibproj.core.Factory;

public class TransferFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t0,ts,tsh;
    ImageView btnTransfer;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transfer, container, false);
        editText = view.findViewById(R.id.edt_transfer);
        btnTransfer = view.findViewById(R.id.btn_transfer);
        t1 = view.findViewById(R.id.num_1_trans);
        t2 = view.findViewById(R.id.num_2_trans);
        t3 = view.findViewById(R.id.num_3_trans);
        t4 = view.findViewById(R.id.num_4_trans);
        t5 = view.findViewById(R.id.num_5_trans);
        t6 = view.findViewById(R.id.num_6_trans);
        t7 = view.findViewById(R.id.num_7_trans);
        t8 = view.findViewById(R.id.num_8_trans);
        t9 = view.findViewById(R.id.num_9_trans);
        t0 = view.findViewById(R.id.num_0_trans);
        ts = view.findViewById(R.id.star_trans);
        tsh = view.findViewById(R.id.sharp_trans);

        btnTransfer.setOnClickListener(v->{
            Factory.transfer(editText.getText().toString());
        });

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

        return view;
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