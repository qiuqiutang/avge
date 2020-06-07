package com.example.lyfuelgas.view.choose;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.DensityUtils;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by QiuYan on 2018/8/25.
 */

public class PopChooseDialog {
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.tvCancle)
    TextView tvCancle;

    private Activity context;
    private View parentView;
    private PopupWindow popupWindow = null;
    private WindowManager.LayoutParams layoutParams = null;
    private LayoutInflater layoutInflater = null;

    private List<SingleObject> mList = null;

    private OnChangeResultListener onChangeResultListener = null;
    PopChooseAdapter mAdapter;

    public void setOnChangeResultListener(OnChangeResultListener onChangeResultListener) {
        this.onChangeResultListener = onChangeResultListener;
    }


    public PopChooseDialog(Activity context) {
        this.context = context;
        init();
    }

    private void init() {
        layoutParams = context.getWindow().getAttributes();
        layoutInflater = context.getLayoutInflater();
        initView();
        initPopupWindow();
    }

    private void initView() {
        parentView = layoutInflater.inflate(R.layout.dialog_pop, null);
        ButterKnife.bind(this, parentView);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        rvList.setLayoutManager(manager);
        rvList.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(parentView, WindowManager.LayoutParams.MATCH_PARENT, (int) (DensityUtils.getScreenHeight(context) * (3.0 / 5)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                context.getWindow().setAttributes(layoutParams);
                popupWindow.dismiss();
            }
        });
    }


    private void bindData() {
        mAdapter = new PopChooseAdapter(mList);
        mAdapter.setOnItemClickListener(new OnItemClickListener<SingleObject>() {
            @Override
            public void onItemClick(SingleObject data, View view, int position) {
                if(null != onChangeResultListener){
                    onChangeResultListener.onChange(data);
                }
                popupWindow.dismiss();
            }
        });
        rvList.setAdapter(mAdapter);
    }


    public void show(View v) {
        layoutParams.alpha = 0.6f;
        context.getWindow().setAttributes(layoutParams);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void setData(List<SingleObject> data) {
        this.mList = data;
        bindData();
    }


    @OnClick({R.id.tvCancle})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancle:
                popupWindow.dismiss();
                break;
        }
    }



    public interface OnChangeResultListener{
        void onChange(SingleObject singleObject);
    }


}
