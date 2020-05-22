package com.example.lyfuelgas.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.app.BaseActivity;

public class Atest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atest);
        ImageView tv=  super.baseLeftIv();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
