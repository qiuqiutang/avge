package com.example.lyfuelgas.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Avge_Auto_right_Fragment extends Fragment {


    @BindView(R.id.auto_right_time)
    TextView autoRightTime;
    @BindView(R.id.auto_right_stop)
    ImageView autoRightStop;
    @BindView(R.id.auto_right_lock1)
    ImageView autoRightLock1;
    @BindView(R.id.auto_right_lock2)
    ImageView autoRightLock2;
    @BindView(R.id.auto_right_lock3)
    ImageView autoRightLock3;
    @BindView(R.id.auto_right_lock4)
    ImageView autoRightLock4;
    @BindView(R.id.auto_right_meet)
    ImageView autoRightMeet;
    @BindView(R.id.auto_right_lock1_tv)
    TextView autoRightLock1Tv;
    @BindView(R.id.auto_right_lock2_tv)
    TextView autoRightLock2Tv;
    @BindView(R.id.auto_right_lock3_tv)
    TextView autoRightLock3Tv;
    @BindView(R.id.auto_right_lock4_tv)
    TextView autoRightLock4Tv;
    @BindView(R.id.auto_right_meet_tv)
    TextView autoRightMeetTv;
    @BindView(R.id.auto_right_jar)
    ImageView autoRightJar;
    @BindView(R.id.auto_right_jar_safety)
    ImageView autoRightJarSafety;
    @BindView(R.id.auto_right_jar_safety_tv)
    TextView autoRightJarSafetyTv;
    @BindView(R.id.auto_right_meet1)
    ImageView auto_right_meet1;

    private static final String TAG = "Avge_Auto_right_Fragment";
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_avge__auto__right, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick({R.id.auto_right_meet1,R.id.auto_right_time, R.id.auto_right_stop, R.id.auto_right_lock1, R.id.auto_right_lock2, R.id.auto_right_lock3, R.id.auto_right_lock4, R.id.auto_right_meet, R.id.auto_right_lock1_tv, R.id.auto_right_lock2_tv, R.id.auto_right_lock3_tv, R.id.auto_right_lock4_tv, R.id.auto_right_meet_tv, R.id.auto_right_jar, R.id.auto_right_jar_safety, R.id.auto_right_jar_safety_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.auto_right_time:
                break;
            case R.id.auto_right_stop:
                break;
            case R.id.auto_right_lock1:
                break;
            case R.id.auto_right_lock2:
                break;
            case R.id.auto_right_lock3:
                break;
            case R.id.auto_right_lock4:
                break;
            case R.id.auto_right_meet:
                LyLog.i(TAG, "点击了");
                new MyDialog(getContext(), R.style.dialog, "确认开启应急卸料阀","取消开启应急卸料阀", new MyDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.auto_right_meet1:
                LyLog.i(TAG, "点击了");
                new MyDialog(getContext(), R.style.dialog, "确认开启应急消防阀","取消开启应急消防阀", new MyDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.auto_right_lock1_tv:
                break;
            case R.id.auto_right_lock2_tv:
                break;
            case R.id.auto_right_lock3_tv:
                break;
            case R.id.auto_right_lock4_tv:
                break;
            case R.id.auto_right_meet_tv:
                break;
            case R.id.auto_right_jar:
                break;
            case R.id.auto_right_jar_safety:
                break;
            case R.id.auto_right_jar_safety_tv:
                break;
        }
    }
    public Avge_Auto_right_Fragment() {
        // Required empty public constructor
    }
}
