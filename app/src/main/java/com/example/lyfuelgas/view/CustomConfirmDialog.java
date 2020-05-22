package com.example.lyfuelgas.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.lyfuelgas.R;

import androidx.fragment.app.DialogFragment;

/**
 * Created by QiuYan on 2020/4/28.
 */

public class CustomConfirmDialog extends DialogFragment {
    private TextView tvContent;
    private TextView tvConfirm;
    private TextView tvCancle;
    private OnConfirmClickListener onConfirmClickListener;
    private OnCancleClickListener OnCancleClickListener;

    public static CustomConfirmDialog newInstance(String message) {
        CustomConfirmDialog fragment = new CustomConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }

    public static CustomConfirmDialog newInstance(String message, String cancleText, String confimText) {
        CustomConfirmDialog fragment = new CustomConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("cancle",cancleText);
        bundle.putString("confirm",confimText);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_custom_confirm, container);
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(getArguments().getString("message"));
        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
        tvCancle = (TextView) view.findViewById(R.id.tvCancle);
        if(getArguments().containsKey("cancle")){
            tvCancle.setText(getArguments().getString("cancle"));
        }
        if(getArguments().containsKey("confirm")){
            tvConfirm.setText(getArguments().getString("confirm"));
        }
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != onConfirmClickListener){
                    onConfirmClickListener.onCallBack();
                }
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != OnCancleClickListener){
                    OnCancleClickListener.onCallBack();
                }
                dismiss();
            }
        });

        return view;
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener){
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public void setOnCancleClickListener(OnCancleClickListener onConfirmClickListener){
        this.OnCancleClickListener = onConfirmClickListener;
    }


    public interface OnConfirmClickListener{
        void onCallBack();
    }

    public interface OnCancleClickListener{
        void onCallBack();
    }

    public interface OnShowCallBack{
        void onCallBack();
    }

}
