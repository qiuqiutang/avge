package com.example.lyfuelgas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyfuelgas.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeCamer extends Fragment {
    private static final String TAG = "HomeCamer";
    @BindView(R.id.home_refresh)
    ImageView homeRefresh;
    @BindView(R.id.iv_error)
    ImageView iv_error;
    @BindView(R.id.tv_error)
    TextView tv_error;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_camer, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_error.setText("敬请期待");
        tv_error.setTextColor(getResources().getColor(R.color.color_main));
        iv_error.setImageResource(R.drawable.jingqingqidai);

    }


    @OnClick(R.id.home_refresh)
    public void onViewClicked() {
    }
}