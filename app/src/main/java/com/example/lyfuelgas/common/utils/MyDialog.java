package com.example.lyfuelgas.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lyfuelgas.R;


public class MyDialog extends android.app.Dialog implements View.OnClickListener{
    private static final String TAG = "MyDialog";
    private TextView dialog_ok;
    private TextView dialog_cancel;

    private Context mContext;
    private String okstr;
    private String canclestr;
    private OnCloseListener listener;
   /* private String okStr;
    private String cancleStr;*/


    public MyDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    public MyDialog(Context context, String okstr,String canclestr) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.canclestr = canclestr;
        this.okstr = okstr;
    }

    public MyDialog(Context context, int themeResId, String okstr,String canclestr) {
        super(context, themeResId);
        this.mContext = context;
        this.canclestr = canclestr;
        this.okstr = okstr;
    }

    public MyDialog(Context context, int themeResId, String okstr,String canclestr, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.canclestr = canclestr;
        this.okstr = okstr;
        this.listener = listener;
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }



/*    public MyDialog setPositiveButton(String name){
        this.okStr = name;
        return this;
    }

    public MyDialog setNegativeButton(String name){
        this.cancleStr = name;
        return this;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my);
        setCanceledOnTouchOutside(false);
        LyLog.i(TAG, "111");
        initView();
    }

    private void initView(){
        LyLog.i(TAG, "2222");
        dialog_ok = findViewById(R.id.dialog_ok);
        dialog_ok.setOnClickListener(this);
        dialog_cancel = findViewById(R.id.dialog_cancel);
        dialog_cancel.setOnClickListener(this);
        dialog_ok.setText(okstr);
        dialog_cancel.setText(canclestr);
 /*      if(!TextUtils.isEmpty(okStr)){
            dialog_ok.setText(okStr);
        }

        if(!TextUtils.isEmpty(cancleStr)){
            dialog_cancel.setText(cancleStr);
        }
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_ok:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.dialog_cancel:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(android.app.Dialog dialog, boolean confirm);
    }
}
