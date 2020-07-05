package com.example.lyfuelgas.activity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.LyToast;
import com.example.lyfuelgas.common.utils.eventbus.EventBusUtils;
import com.example.lyfuelgas.common.utils.eventbus.EventType;
import com.example.lyfuelgas.common.utils.eventbus.MyEvent;
import com.example.lyfuelgas.contact.FillContact;
import com.example.lyfuelgas.presenter.FillPresenter;
import com.example.lyfuelgas.view.ClearEditText;
import com.example.lyfuelgas.view.MyInputFilter;
import com.example.lyfuelgas.view.SimpleToolbar;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

public class FillActivity extends MVPBaseActivity<FillPresenter> implements TextWatcher, FillContact.View {
    @BindView(R.id.simple_toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.etEmei)
    EditText etEmei;
    @BindView(R.id.etCapacity)
    ClearEditText etCapacity;
    @BindView(R.id.etPrice)
    ClearEditText etPrice;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    OrderObject orderObject;
    DeviceObject deviceObject;

    @Override
    protected FillPresenter loadPresenter() {
        return new FillPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_fill;
    }

    @Override
    protected void initData() {
        toolbar.invalidate();
        toolbar.postInvalidate();
        orderObject = (OrderObject) getIntent().getSerializableExtra("order");
        toolbar.setMainTitle("燃料加注");
        etPrice.setFilters(new InputFilter[]{inputFilter});
        etCapacity.setFilters(new InputFilter[]{inputFilter});
        mPresenter.getDeviceDetail(orderObject.equipmentId);
    }

    @Override
    protected void initEvent() {
        etEmei.addTextChangedListener(this);
        etCapacity.addTextChangedListener(capWatcher);
        etPrice.addTextChangedListener(billWatcher);
        toolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        btnSubmit.setEnabled(!TextUtils.isEmpty(etEmei.getText().toString().trim()) && !TextUtils.isEmpty(etCapacity.getText().toString().trim()) && !TextUtils.isEmpty(etPrice.getText().toString().trim()));
    }


    private MyInputFilter inputFilter = new MyInputFilter(2);

    @OnClick({R.id.ivScan,R.id.btnSubmit})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivScan:
                //扫一扫
                QrConfig qrConfig = new QrConfig.Builder()
                        .setDesText("")//扫描框下文字
                        .setShowDes(false)//是否显示扫描框下面文字
                        .setShowLight(false)//显示手电筒按钮
                        .setShowTitle(true)//显示Title
                        .setShowAlbum(false)//显示从相册选择按钮
                        .setCornerColor(getResources().getColor(R.color.color_main))//设置扫描框颜色
                        .setLineColor(getResources().getColor(R.color.color_main))//设置扫描线颜色
                        .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                        .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                        .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                        .setCustombarcodeformat(QrConfig.BARCODE_EAN13)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                        .setPlaySound(false)//是否扫描成功后bi~的声音
                        .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
                        .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                        .setTitleText(getResources().getString(R.string.qr_title))//设置Tilte文字
                        .setTitleBackgroudColor(R.color.colorPrimaryDark)//设置状态栏颜色
                        .setTitleTextColor(getResources().getColor(R.color.color_333333))//设置Title文字颜色
                        .create();
                QrManager.getInstance().init(qrConfig).startScan(this, new QrManager.OnScanResultCallback() {
                    @Override
                    public void onScanSuccess(String result) {
                        etEmei.setText(result);
                    }
                });
                break;
            case R.id.btnSubmit:
                mPresenter.addFill(orderObject,etEmei.getText().toString().trim(),etCapacity.getText().toString().trim(),etPrice.getText().toString().trim(),etRemark.getText().toString().trim());
                break;
        }
    }

    TextWatcher capWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cap = etCapacity.getText().toString().trim();
            BigDecimal capBig = null;
            if(TextUtils.isEmpty(cap)) {
                etPrice.setText("");
                etPrice.setEnabled(true);
                etPrice.addTextChangedListener(billWatcher);
            }else if(null != deviceObject){
                etPrice.removeTextChangedListener(billWatcher);
                capBig = new BigDecimal(cap);
                BigDecimal money = capBig.multiply(deviceObject.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);

                etPrice.setEnabled(false);
                etPrice.setText(money.toString());
                etPrice.setClearIconVisible(false);
            }
            btnSubmit.setEnabled(!TextUtils.isEmpty(etEmei.getText().toString().trim()) && !TextUtils.isEmpty(etCapacity.getText().toString().trim()) && !TextUtils.isEmpty(etPrice.getText().toString().trim()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher billWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String bill = etPrice.getText().toString().trim();
            BigDecimal billBig = null;
            if(TextUtils.isEmpty(bill)) {
                etCapacity.setText("");
                etCapacity.setEnabled(true);
                etCapacity.addTextChangedListener(capWatcher);
            }else if(null != deviceObject){
                etCapacity.removeTextChangedListener(capWatcher);
                if(deviceObject.getPrice().compareTo(new BigDecimal(0)) == 0){
                    etCapacity.setEnabled(false);
                    etCapacity.setText("0.00");
                    etCapacity.setClearIconVisible(false);
                    return;
                }
                billBig = new BigDecimal(bill);
                BigDecimal cap = billBig.divide(deviceObject.getPrice(),2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);

                etCapacity.setEnabled(false);
                etCapacity.setText(cap.toString());
                etCapacity.setClearIconVisible(false);
            }
            btnSubmit.setEnabled(!TextUtils.isEmpty(etEmei.getText().toString().trim()) && !TextUtils.isEmpty(etCapacity.getText().toString().trim()) && !TextUtils.isEmpty(etPrice.getText().toString().trim()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onAddFillSuccess() {
        LyToast.shortToast(mContext,"燃料加注成功！");
        EventBusUtils.post(new MyEvent(EventType.REFRESH_ORDER));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onGetDeviceDetailSuccess(DeviceObject data) {
        if(null != data){
            this.deviceObject = data;
        }
    }
}
