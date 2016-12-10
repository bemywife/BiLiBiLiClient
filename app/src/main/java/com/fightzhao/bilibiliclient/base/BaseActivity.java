package com.fightzhao.bilibiliclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fightzhao.bilibiliclient.R;
import com.fightzhao.bilibiliclient.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者 ：fightzhao on 2016/11/7 18:48.
 * 功能 :
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView, View.OnClickListener {
    /**
     * 将代理类通用行为抽取出来
     */
    protected T mPresenter;

    /**
     * 黄油刀
     */
    private Unbinder bind;

    // TODO: 2016/11/24 RxBus未加入 

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
    }

    protected abstract void initToolBar();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        bind.unbind();
    }

    @Override
    public void toast(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void snackbar(String msg) {
        showSnackbar(msg);
    }

    protected void showSnackbar(String msg) {
        Snackbar sBar = Snackbar.make(getDecorView(), msg, Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout) sBar.getView();
        ve.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        sBar.show();
    }

    protected View getDecorView() {
        return getWindow().getDecorView();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showRequest() {

    }

    @Override
    public void onClick(View view) {

    }



}
