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

public class NewsActivity extends AppCompatActivity {

    private static final String TAG = "NewsActivity";
    @BindView(R.id.news_rv)
     RecyclerView newsRv;
    private List<String> addressList=new ArrayList<>();
    private BaseRecyclerAdapter<String> newsRecyclyerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initview();
    }
        private void initview(){
            addressList.add(new String());
            addressList.add(new String());
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            newsRv.setLayoutManager(layoutManager);
            setRv();
        }

    private void setRv() {
        LyLog.i(TAG,"初始化rv");
        newsRecyclyerAdapter = new BaseRecyclerAdapter<String>(this, addressList, R.layout.rv_news) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setClick(R.id.rv_news_ll,s,position,newsRecyclyerAdapter);
            }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
            }
        };
        newsRv.setAdapter(newsRecyclyerAdapter);
    }
}
