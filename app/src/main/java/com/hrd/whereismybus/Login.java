package com.hrd.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class Login extends AppCompatActivity {

    //ImageView im;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //im = findViewById(R.id.imageView);
        edt = findViewById(R.id.edt_username);
    }
}