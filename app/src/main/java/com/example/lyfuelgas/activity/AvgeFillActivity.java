package com.example.lyfuelgas.activity;

import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.BaseRecyclerAdapter;
import com.example.lyfuelgas.adapter.BaseRecyclerViewHolder;
import com.example.lyfuelgas.app.CustomerManager;
import com.example.lyfuelgas.app.SupplierManager;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.CustomerObject;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.SupplierObject;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.LyToast;
import com.example.lyfuelgas.common.utils.ProgressDialogUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.common.utils.eventbus.EventBusUtils;
import com.example.lyfuelgas.common.utils.eventbus.EventType;
import com.example.lyfuelgas.common.utils.eventbus.MyEvent;
import com.example.lyfuelgas.contact.AvgeFillContact;
import com.example.lyfuelgas.presenter.AvgeFIllPresenter;
import com.example.lyfuelgas.view.ClearEditText;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class AvgeFillActivity extends MVPBaseActivity<AvgeFIllPresenter> implements AvgeFillContact.View {
    private static final String TAG = "AvgeFillActivity";
    @BindView(R.id.fill_rl)
    RelativeLayout fill_rl;
    @BindView(R.id.avge_fill_rv)
    RecyclerView avge_fill_rv;
    @BindView(R.id.home_return)
    ImageView homeReturn;
    @BindView(R.id.home_refresh)
    ImageView homeRefresh;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    ClearEditText etCapacity;
    ClearEditText etBill;


    private PopupWindow buypopupWindow;
    private BaseRecyclerAdapter<String> adapter;
    private List<String> list = new ArrayList<>();


    CustomerObject customerObject;
    DeviceObject deviceObject;
    //DeviceTypeObject deviceTypeObject;
    //MeasureObject measureObject;
    SupplierObject supplierObject;

    //capatity:0,bill:1
    int inputStatus = -1;
    @Override
    protected AvgeFIllPresenter loadPresenter() {
        return new AvgeFIllPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_avge_fill;
    }

    @Override
    protected void initData() {
        if(null != getIntent()){
            deviceObject = (DeviceObject) getIntent().getSerializableExtra("device");
        }
        supplierObject = SupplierManager.getInstance().getSupplier();
        customerObject = CustomerManager.getInstance().getCustomer();
        init_view();
        setRv();
        if(null == deviceObject) {
            String deviceInfo = (String) SPUtils.get(mContext, UserManager.getInstance().getDeviceInfoKey(), "");
            deviceObject = GsonUtils.parseJsonToBean(deviceInfo, DeviceObject.class);
        }
        StringBuilder sbInfo = new StringBuilder();
        if(null != deviceObject){
            sbInfo.append("设备IMEI：");
            sbInfo.append(deviceObject.imei);
            sbInfo.append("\n");
            sbInfo.append("地址：");
            sbInfo.append(deviceObject.address);
            sbInfo.append("\n");
            sbInfo.append("总容量：");
            sbInfo.append(String.format("%.2f",deviceObject.getCapacity()));
            sbInfo.append("L\n");
            sbInfo.append("当前容量：");
            sbInfo.append(String.format("%.2f",deviceObject.remainPercent*deviceObject.getCapacity()/100));
            sbInfo.append("L\n");
            sbInfo.append("供应商：");
            sbInfo.append(deviceObject.supplierContact);
            sbInfo.append("\n");
        }


        tvAddress.setText(sbInfo.toString());

    }

    @Override
    protected void initEvent() {

    }


    private void init_view() {
        list.add(new String("10"));
        list.add(new String("10"));
        list.add(new String("100"));
        list.add(new String("10"));
        list.add(new String("10"));
        list.add(new String("10"));
        list.add(new String("200"));
        list.add(new String("10"));
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 4);
        avge_fill_rv.setLayoutManager(gridLayoutManager1);
    }

    private void setRv() {
        adapter = new BaseRecyclerAdapter<String>(this, list, R.layout.rv_avge_fill) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setTxt(R.id.rv_avge_fill_tv, s);
            }
        };
        avge_fill_rv.setAdapter(adapter);
    }

    /**
     * 立即购买的泡泡
     */
    private void showAddBuyPopupWindow() {
        LyLog.i(TAG, "弹出泡泡");
        View addpackageview = LayoutInflater.from(this).inflate(R.layout.details_buy_pop, null);
        buypopupWindow = new PopupWindow(addpackageview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
        TextView tvContact = addpackageview.findViewById(R.id.tvContact);
        tvContact.setText(customerObject.fullname);
        TextView tvContactTel = addpackageview.findViewById(R.id.tvContactTel);
        tvContactTel.setText(customerObject.relMobile);
        TextView tvAddress = addpackageview.findViewById(R.id.tvAddress);
        tvAddress.setText(deviceObject.address);
        TextView tvDeviceInfo = addpackageview.findViewById(R.id.tvDeviceInfo);
        etCapacity = addpackageview.findViewById(R.id.etCapacity);
        etCapacity.addTextChangedListener(capWatcher);
        etBill = addpackageview.findViewById(R.id.etBill);
        etBill.addTextChangedListener(billWatcher);
        StringBuilder sbInfo = new StringBuilder();
        if(null != deviceObject){
            sbInfo.append("设备IMEI：");
            sbInfo.append(deviceObject.imei);
            sbInfo.append("\n");
            sbInfo.append("总容量：");
            sbInfo.append(String.format("%.2f",deviceObject.getCapacity()));
            sbInfo.append("L\n");
            sbInfo.append("当前容量：");
            sbInfo.append(String.format("%.2f",deviceObject.remainPercent*deviceObject.getCapacity()/100));
            sbInfo.append("L\n");
            sbInfo.append("供应商：");
            sbInfo.append(deviceObject.supplierContact);
            sbInfo.append("\n");
        }

        tvDeviceInfo.setText(sbInfo.toString());
        TextView tvSubmit = addpackageview.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buypopupWindow.dismiss();
                mPresenter.addOrder(deviceObject,etCapacity.getText().toString().trim(),etBill.getText().toString().trim());
                ProgressDialogUtils.showProgressDialog(mContext,"正在提交，请稍后...");
            }
        });
        TextView tvCancel = addpackageview.findViewById(R.id.tvCancle);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buypopupWindow.dismiss();
            }
        });
        addpackageview.findViewById(R.id.details_pop_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buypopupWindow.dismiss();
            }
        });

        //点击外围、返回键、自身控件使弹出框消失
        buypopupWindow.setBackgroundDrawable(new BitmapDrawable());
        buypopupWindow.setFocusable(true);
        buypopupWindow.setOutsideTouchable(true);
        buypopupWindow.update();
        buypopupWindow.showAtLocation(findViewById(R.id.re_productdetails_button), Gravity.BOTTOM, 0, 0);
        bgAlpha(0.618f);
        buypopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1f);
            }
        });

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
                etBill.setText("");
                etBill.setEnabled(true);
                etBill.addTextChangedListener(billWatcher);
            }else {
                etBill.removeTextChangedListener(billWatcher);
                capBig = new BigDecimal(cap);
                BigDecimal money = capBig.multiply(deviceObject.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);

                etBill.setEnabled(false);
                etBill.setText(money.toString());
                etBill.setClearIconVisible(false);
            }
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
            String bill = etBill.getText().toString().trim();
            BigDecimal billBig = null;
            if(TextUtils.isEmpty(bill)) {
                etCapacity.setText("");
                etCapacity.setEnabled(true);
                etCapacity.addTextChangedListener(capWatcher);
            }else {
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
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * 设置窗口的背景透明度
     *
     * @param f 0.0-1.0
     */
    private void bgAlpha(float f) {
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.alpha = f;
        this.getWindow().setAttributes(layoutParams);
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (buypopupWindow != null && buypopupWindow.isShowing()) {

            buypopupWindow.dismiss();

            buypopupWindow = null;

        }
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.home_return, R.id.home_refresh, R.id.fill_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_return:
                finish();
                break;
            case R.id.home_refresh:
                break;
            case R.id.fill_rl:
                showAddBuyPopupWindow();
                break;
        }
    }

    @Override
    public void onAddOrderSuccess() {
        LyToast.shortToast(mContext,"生成订单成功！");
        EventBusUtils.post(new MyEvent(EventType.REFRESH_ORDER));
        finish();
    }
}
