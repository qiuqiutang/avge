package com.example.lyfuelgas.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.BaseRecyclerAdapter;
import com.example.lyfuelgas.adapter.BaseRecyclerViewHolder;
import com.example.lyfuelgas.common.utils.LyLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAddress extends AppCompatActivity {

    private static final String TAG = "AccountAddress";
    @BindView(R.id.address_rv)
    RecyclerView addressRv;
    private List<String> addressList=new ArrayList<>();
    private BaseRecyclerAdapter<String> accountRecyclyerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_address);
        ButterKnife.bind(this);
        initview();

    }

   private void initview(){
       addressList.add(new String());
       addressList.add(new String());
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       addressRv.setLayoutManager(layoutManager);

       setRv();
    }

    private void setRv() {
        LyLog.i(TAG,"初始化rv");
        accountRecyclyerAdapter = new BaseRecyclerAdapter<String>(this, addressList, R.layout.rv_account) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

            }
        };
        addressRv.setAdapter(accountRecyclyerAdapter);
    }
}
