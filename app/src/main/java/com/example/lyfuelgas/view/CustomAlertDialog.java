package com.example.lyfuelgas.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.lyfuelgas.R;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by QiuYan on 2018/8/24.
 */

public class CustomAlertDialog extends DialogFragment {
    private TextView tvContent;
    private TextView tvConfirm;
    private OnConfirmClickListener onConfirmClickListener;
    private CustomConfirmDialog.OnShowCallBack onShowCallBack;
    private static boolean shown = false;

    public void setOnShowCallBack(CustomConfirmDialog.OnShowCallBack onShowCallBack){
        this.onShowCallBack = onShowCallBack;
    }

    public static CustomAlertDialog newInstance(String message) {
        CustomAlertDialog fragment = new CustomAlertDialog();
        fragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_custom_alert, container);
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(getArguments().getString("message"));
        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != onConfirmClickListener){
                    onConfirmClickListener.onCallBack();
                }
                dismiss();
            }
        });
        if(null != onShowCallBack){
            onShowCallBack.onCallBack();
        }
        return view;
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener){
        this.onConfirmClickListener = onConfirmClickListener;
    }



    public interface OnConfirmClickListener{
        void onCallBack();
    }


    /**
     * 返回确认按钮对象
     * @return
     */
    public View getConfirmView(){
        return tvConfirm;
    }



    @Override
    public void show(FragmentManager manager, String tag) {
        if (shown) return;
        super.show(manager, tag);
        shown = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }
}
