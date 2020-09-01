package com.example.lyfuelgas.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.activity.AvgeAutoActivity;
import com.example.lyfuelgas.activity.AvgeDiagnoseActivity;
import com.example.lyfuelgas.activity.AvgeFillActivity;
import com.example.lyfuelgas.activity.AvgeMassageActivity;
import com.example.lyfuelgas.activity.NewsActivity;
import com.example.lyfuelgas.activity.SelectDeviceActivity;
import com.example.lyfuelgas.adapter.BaseRecyclerAdapter;
import com.example.lyfuelgas.app.DeviceTypeManager;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.bean.MeasureObject;
import com.example.lyfuelgas.common.mvp.MVPBaseFragment;
import com.example.lyfuelgas.common.utils.CallPhone;
import com.example.lyfuelgas.common.utils.DensityUtils;
import com.example.lyfuelgas.common.utils.GlideUtils;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.contact.HomeAvgeContact;
import com.example.lyfuelgas.presenter.HomeAvgePresenter;
import com.example.lyfuelgas.view.CustomAlertDialog;
import com.example.lyfuelgas.view.VerticalProgressBar;

import java.math.BigDecimal;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class HomeAvgeFragment extends MVPBaseFragment<HomeAvgePresenter> implements HomeAvgeContact.View {

    private static final String TAG = "HomeAvgeFragment";
    @BindView(R.id.tvFuel)
    TextView tvFuel;
    @BindView(R.id.pb_fuel)
    VerticalProgressBar pb_fuel;
    @BindView(R.id.tvTemperature)
    TextView homeFmTemperatureTv;
    @BindView(R.id.tvDeviceType)
    TextView tvDeviceType;
    @BindView(R.id.imageView)
    ImageView ivDevicePic;
    @BindView(R.id.tvCapacity)
    TextView tvCapacity;
    @BindView(R.id.tvCountDown)
    AppCompatTextView tvCountDown;
    @BindView(R.id.layoutCountDown)
    ConstraintLayout layoutCountDown;
    @BindView(R.id.tvCountDownDesc)
    TextView tvCountDownDesc;
    @BindView(R.id.groupToxic)
    Group groupToxic;
    @BindView(R.id.groupFlammable)
    Group groupFlammable;
   /* @BindView(R.id.home_fm_is_gas_iv)
    ImageView homeFmIsGasIv;
    @BindView(R.id.home_fm_is_gas1_tv)
    TextView homeFmIsGas1Tv;
    @BindView(R.id.home_fm_is_gas_tv)
    TextView homeFmIsGasTv;


    @BindView(R.id.home_fm_capacity_tv)
    TextView homeFmCapacityTv;
    @BindView(R.id.home_fm_time_remaining_tv)
    TextView homeFmTimeRemainingTv;
    @BindView(R.id.avge_auto_fl)
    FrameLayout avgeAutoFl;
    @BindView(R.id.avge_auto_iv)
    ImageView avgeAutoIv;
    @BindView(R.id.avge_diagnose_iv)
    ImageView avgeDiagnoseIv;
    @BindView(R.id.avge_msg_iv)
    ImageView avgeMsgIv;
    @BindView(R.id.fm_avge_news)
    ImageView fm_avge_news;
    @BindView(R.id.ivSwitch)
    ImageView ivSwitch;
    @BindView(R.id.avge_touch)
    ImageView avge_touch;
    @BindView(R.id.tvDeviceType)
    TextView tvDeviceType;
    @BindView(R.id.ivDevicePic)
    ImageView ivDevicePic;
    @BindView(R.id.lvCombustibleGas)
    ViewGroup lvCombustibleGas;
    @BindView(R.id.lvToxicGas)
    ViewGroup lvToxicGas;*/





    private DeviceTypeObject deviceTypeObject;
    private DeviceObject deviceObject;

    CustomAlertDialog alertDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        mPresenter.getCustomer();
        setData();
        alertDialog = CustomAlertDialog.newInstance("暂不支持当前设备");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected HomeAvgePresenter loadPresenter() {
        return new HomeAvgePresenter();
    }


    private void setData() {

        String deviceInfo = (String) SPUtils.get(mContext, UserManager.getInstance().getDeviceInfoKey(), "");
        if (!TextUtils.isEmpty(deviceInfo)) {
            deviceObject = GsonUtils.parseJsonToBean(deviceInfo, DeviceObject.class);
            mPresenter.getDeviceInfo(deviceObject.id);
            deviceTypeObject = DeviceTypeManager.getInstance().getDeviceType(deviceObject.equipmentTypeId);
            if (null != deviceTypeObject) {
                tvDeviceType.setText(deviceTypeObject.name);
                //groupFlammable.setVisibility(deviceTypeObject.isShowToxicGas() ? View.VISIBLE : View.GONE);
                //groupToxic.setVisibility(deviceTypeObject.isShowToxicGas() ? View.VISIBLE : View.GONE);
            }
        }
    }



    private void initCircleViewMsg(String str, ImageView iv) {
        int i = Integer.parseInt(str);

        switch (i / 5) {
            case 0:
                if (0 == i) iv.setImageResource(R.mipmap.circle0);
                else iv.setImageResource(R.mipmap.circle3);
                break;
            case 1:
                iv.setImageResource(R.mipmap.circle5);
                break;
            case 2:
                iv.setImageResource(R.mipmap.circle10);
                break;
            case 3:
                iv.setImageResource(R.mipmap.circle15);
                break;
            case 4:
                iv.setImageResource(R.mipmap.circle20);
                break;
            case 5:
                iv.setImageResource(R.mipmap.circle25);
                break;
            case 6:
                iv.setImageResource(R.mipmap.circle30);
                break;
            case 7:
                iv.setImageResource(R.mipmap.circle35);
                break;
            case 8:
                iv.setImageResource(R.mipmap.circle40);
                break;
            case 9:
                iv.setImageResource(R.mipmap.circle45);
                break;
            case 11:
                iv.setImageResource(R.mipmap.circle50);
                break;
            case 12:
                iv.setImageResource(R.mipmap.circle55);
                break;
            case 13:
                iv.setImageResource(R.mipmap.circle60);
                break;
            case 14:
                iv.setImageResource(R.mipmap.circle70);
                break;
            case 15:
                iv.setImageResource(R.mipmap.circle75);
                break;
            case 16:
                iv.setImageResource(R.mipmap.circle80);
                break;
            case 17:
                iv.setImageResource(R.mipmap.circle85);
                break;
            case 18:
                iv.setImageResource(R.mipmap.circle90);
                break;
            case 19:
                if (i <= 97 && i >= 95) {
                    iv.setImageResource(R.mipmap.circle95);
                } else {
                    iv.setImageResource(R.mipmap.circle98);
                }
                break;
            case 20:
                iv.setImageResource(R.mipmap.circle100);
                break;

        }

    }


    /* *//**
     * 立即购买的泡泡
     *//*
    private void showTypePopupWindow() {
        strings = new ArrayList<>();
        strings.add("型号1");
        strings.add("型号2");
        strings.add("型号3");
        strings.add("型号4");

        LyLog.i(TAG, "弹出泡泡");
        View addpackageview = LayoutInflater.from(getActivity()).inflate(R.layout.poplist, null);
         recycleview= addpackageview.findViewById(R.id.poplist_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(layoutManager);
        LyLog.i(TAG,"初始化rv");

        home_avge_recyclyer = new BaseRecyclerAdapter<String>(getActivity(), strings, R.layout.rv_button) {

            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setTxt( R.id.text1,s);
                holder.setClick(R.id.text1,s,position,home_avge_recyclyer);
                holder.setTxtColor(R.id.text1,"#C3DDFA");
                if(position%2!=0)
                    holder.setTxtBackground(R.id.text1,"#4DADEA");
                else
                    holder.setTxtBackground(R.id.text1,"#4A99D4");
                LyLog.i(TAG,"设置tv");
            }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
                popupWindow.dismiss();
            }
        };
        recycleview.setAdapter(home_avge_recyclyer);

        popupWindow = new PopupWindow(addpackageview,WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
        //点击外围、返回键、自身控件使弹出框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.showAtLocation(view.findViewById(R.id.fm_home_avge_ll), Gravity.TOP, 0, 300);
       // bgAlpha(0.618f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
              //  bgAlpha(1f);
            }
        });

    }*/

    /**
     * 设置窗口的背景透明度
     *
     * @param f 0.0-1.0
     */
    private void bgAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    @OnClick({R.id.layoutAlarm,
            R.id.layoutStat,
            R.id.ivSwitch,
            R.id.layoutFill,
            R.id.layoutAutoContrl,
            R.id.layoutSensing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutAlarm:
                CallPhone.call(getActivity(), "119");
                break;
            case R.id.layoutAutoContrl:
                alertDialog.show(getFragmentManager(),"pending");
                //launchActivity(AvgeAutoActivity.class);
                break;
            case R.id.layoutFill:
                Bundle bundle = new Bundle();
                bundle.putSerializable("device",deviceObject);
                bundle.putSerializable("deviceType",deviceTypeObject);
                launchActivity(AvgeFillActivity.class,false,bundle);
                break;
            case R.id.layoutSensing:
                alertDialog.show(getFragmentManager(),"pending");
                //startActivity(new Intent(getContext(), AvgeDiagnoseActivity.class));
                break;
            case R.id.layoutStat:
                Bundle bundle1 = new Bundle();
                bundle1.putString("phone",deviceObject.supplierMobile);
                launchActivity(AvgeMassageActivity.class,false,bundle1);
                break;
            case R.id.ivSwitch:
                launchActivityForResult(SelectDeviceActivity.class,null, 110);
                break;

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (110 == requestCode && RESULT_OK == resultCode) {
            setData();
        }
    }

    @Override
    public void onGetMeasureInfoSuccess(MeasureObject data) {
        /*if(null != data){
            measureObject = data;
            homeFmTemperatureTv.setText(measureObject.temperature+"℃");
            String fuelStr = deviceTypeObject.height <= 0 ? "0" : String.valueOf((int)(((measureObject.liquid*10) /(deviceTypeObject.height*10) )*100));
            homeFmFuelTv.setText(fuelStr+"%");
            initLineViewMsg(fuelStr, homeFmFuelIv);
        }else {
            homeFmTemperatureTv.setText("--℃");
            homeFmFuelTv.setText("--");
        }
        homeFmIsGas1Tv.setText("--");
        homeFmIsGasTv.setText("--");
        homeFmTimeRemainingTv.setText("--");*/
    }

    @Override
    public void onGetDeviceInfoSuccess(DeviceObject data) {
        if(null != data){
            deviceObject = data;
            homeFmTemperatureTv.setText(String.format("%.1f ℃", deviceObject.temperature));
            tvFuel.setText(deviceObject.remainPercent+"%");
            pb_fuel.setProgress((int)(deviceObject.remainPercent));
            GlideUtils.loadImageView(mContext,deviceObject.picUrl,ivDevicePic);
            tvCapacity.setText(String.format("%.2fL",deviceObject.getCapacity()));
            Drawable drawable = getResources().getDrawable(
                    R.drawable.ico_waring);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tvCountDown.setText(deviceObject.getMaxTime());
            if(1 == deviceObject.liquidAbStatus) {
                tvFuel.setTextColor(Color.RED);
                tvFuel.setCompoundDrawables(null, null, drawable, null);
                tvFuel.setCompoundDrawablePadding(4);
            }else {
                tvFuel.setTextColor(getResources().getColor(R.color.home_head_text));
                tvFuel.setCompoundDrawables(null, null, null, null);
            }

            if(1 == deviceObject.temperatureAbStatus) {
                homeFmTemperatureTv.setTextColor(Color.RED);
                homeFmTemperatureTv.setCompoundDrawables(null, null, drawable, null);
                homeFmTemperatureTv.setCompoundDrawablePadding(4);
            }else {
                homeFmTemperatureTv.setTextColor(getResources().getColor(R.color.home_head_text));
                homeFmTemperatureTv.setCompoundDrawables(null, null, null, null);
            }
        }else {
            homeFmTemperatureTv.setText("--℃");
            tvFuel.setText("--");
        }
        //homeFmIsGas1Tv.setText("--");
        //homeFmIsGasTv.setText("--");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null != deviceObject){
            mPresenter.getDeviceInfo(deviceObject.id);
        }
    }
}
