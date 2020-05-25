package com.example.lyfuelgas.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.common.exit.PressExit;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.ProgressDialogUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.contact.SelectDeviceContact;
import com.example.lyfuelgas.presenter.SelectDevicePresenter;
import com.example.lyfuelgas.view.CustomConfirmDialog;
import com.example.lyfuelgas.view.SimpleToolbar;
import com.example.lyfuelgas.view.treerecycleview.TreeRecyclerAdapter;
import com.example.lyfuelgas.view.treerecycleview.TreeRecyclerType;
import com.example.lyfuelgas.view.treerecycleview.base.BaseRecyclerAdapter;
import com.example.lyfuelgas.view.treerecycleview.base.ViewHolder;
import com.example.lyfuelgas.view.treerecycleview.factory.ItemHelperFactory;
import com.example.lyfuelgas.view.treerecycleview.item.TreeItem;
import com.example.lyfuelgas.view.treerecycleview.item.TreeItemGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SelectDeviceActivity extends MVPBaseActivity<SelectDevicePresenter> implements SelectDeviceContact.View {
    @BindView(R.id.simple_toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.rvList)
    RecyclerView rvList;

    List<TreeItem> items;
    TreeRecyclerAdapter mAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_EXPAND);

    @Override
    protected SelectDevicePresenter loadPresenter() {
        return new SelectDevicePresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_select_device;
    }

    @Override
    protected void initData() {
        toolbar.setMainTitle("选择设备");
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setAdapter(mAdapter);

        //mPresenter.getDeviceList();
        ProgressDialogUtils.showProgressDialog(mContext,"数据加载中……");
        mPresenter.getDeviceTypeList();
    }

    @Override
    protected void initEvent() {
        toolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull ViewHolder viewHolder, int position) {
                TreeItem treeItem = mAdapter.getItemManager().getItem(position);
                if(treeItem.getData() instanceof DeviceObject){
                    SPUtils.put(mContext, UserManager.getInstance().getDeviceInfoKey(), GsonUtils.toJsonString((DeviceObject) treeItem.getData()));
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    public void onGetDeviceListSuccess(ArrayList<DeviceTypeObject> data) {
        //创建item
        List<TreeItem> items = ItemHelperFactory.createItems(data, null);
        this.items = items;
        if(this.items.size() > 0){
            for (int i = 0; i < this.items.size(); i++){
                TreeItem item = this.items.get(i);
                if(item instanceof TreeItemGroup){
                    TreeItemGroup treeItemGroup = (TreeItemGroup) item;
                    treeItemGroup.setExpand(true);
                    this.items.set(i, treeItemGroup);
                }
            }

            //添加到adapter
            mAdapter.getItemManager().replaceAllItem(this.items);
        }else {
            mAdapter.getItemManager().clean();
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit(){
        String deviceInfo = (String) SPUtils.get(mContext, UserManager.getInstance().getDeviceInfoKey(),"");
        if(TextUtils.isEmpty(deviceInfo)){
            CustomConfirmDialog customConfirmDialog = CustomConfirmDialog.newInstance("是否确认退出？");
            customConfirmDialog.setOnConfirmClickListener(new CustomConfirmDialog.OnConfirmClickListener() {
                @Override
                public void onCallBack() {
                    finish();
                }
            });
            customConfirmDialog.show(getSupportFragmentManager(),"exitDialog");
            return;
        }
        finish();
    }

}
