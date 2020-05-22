package com.example.lyfuelgas.common.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;


/**
 * Fragment基类
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public abstract class MVPBaseFragment<P extends BasePresenter> extends Fragment implements IView {
    protected String TAG = getClass().getSimpleName();
    public Context mContext;

    protected P mPresenter;
    protected View mView;

    private boolean isInitView = false;
    private boolean isVisible = false;
    private boolean isLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (mView == null) {
            mView = inflater.inflate(getContentLayout(), container, false);
            ButterKnife.bind(this, mView);
            mPresenter = loadPresenter();
            initCommonData();
            initData();
            initEvent();
        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        super.onCreateView(inflater, container, savedInstanceState);
        isInitView = true;
        isCanLoadData();
        return mView;
    }

    /**
     * 获取加载的布局资源id
     */
    protected abstract int getContentLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();


    /**
     * 加载presenter
     * @return
     */
    protected abstract P loadPresenter();

    private void initCommonData() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }



    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        /*try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }*/
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 打开一个Activity 默认 不关闭当前activity,不带任何参数
     * @param clz
     */
    public void launchActivity(Class<?> clz) {
        launchActivity(clz, false);
    }

    /**
     * 打开一个Activity,默认 不带任何参数
     * @param clz
     * @param isCloseCurrentActivity 是否关闭当前activity
     */
    public void launchActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        launchActivity(clz, isCloseCurrentActivity, null);
    }

    /**
     * 打开一个Activity
     * @param clz
     * @param isCloseCurrentActivity 是否关闭当前activity
     * @param ex 传的参数
     */
    public void launchActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(mView.getContext(), clz);
        if (ex != null) {
            intent.putExtras(ex);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            getActivity().finish();
        }
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void launchActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mView.getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if(isVisibleToUser){
            isVisible = true;
            isCanLoadData();
        }else{
            isVisible = false;
        }
    }

    private void isCanLoadData(){
        //所以条件是view初始化完成并且对用户可见
        if(isInitView && isVisible ){
            if(isLoaded){
                resumeLoad();
            }else {
                lazyLoad();
            }
            //防止重复加载数据
            isInitView = true;
            isVisible = false;
        }
    }

    /**
     * 加载要显示的数据(第一次)
     */
    public void lazyLoad(){
        isLoaded = true;
    }

    /**
     * 再次出现的时候加载
     */
    public void resumeLoad(){

    }
}
