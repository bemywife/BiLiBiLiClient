package com.fightzhao.bilibiliclient.module.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.fightzhao.bilibiliclient.module.common.view.activity.MainActivity;
import com.fightzhao.bilibiliclient.R;
import com.fightzhao.bilibiliclient.base.BaseActivity;
import com.fightzhao.bilibiliclient.module.login.presenter.ILoginPresenterImpl;
import com.fightzhao.bilibiliclient.utils.ConstantUtils;
import com.fightzhao.bilibiliclient.utils.NetUtil;
import com.fightzhao.bilibiliclient.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<ILoginPresenterImpl> implements LoginView {
    // TODO: 2016/11/25 在进入主界面的时候可以加个界面
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.iv_icon_left)
    ImageView mLeftLogo;

    @BindView(R.id.iv_icon_right)
    ImageView mRightLogo;

    @BindView(R.id.delete_username)
    ImageView mDeleteUserName;

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button mBtLogin;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolBar() {
        mToolbar.setNavigationIcon(R.mipmap.ic_cancle);
        mToolbar.setTitle("登录");
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mPresenter = new ILoginPresenterImpl(this);

        mDeleteUserName.setOnClickListener(v -> {
            // 清空用户名和密码
            et_username.setText("");
            et_password.setText("");
            mDeleteUserName.setVisibility(View.GONE);
            et_username.setFocusable(true);
            et_username.setFocusableInTouchMode(true);
            et_username.requestFocus();
        });


        et_username.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && et_username.getText().length() > 0) {
                mDeleteUserName.setVisibility(View.VISIBLE);
            } else {
                mDeleteUserName.setVisibility(View.GONE);
            }
            mLeftLogo.setImageResource(R.mipmap.ic_22);
            mRightLogo.setImageResource(R.mipmap.ic_33);
        });

        et_password.setOnFocusChangeListener((v, hasFocus) -> {
            mLeftLogo.setImageResource(R.mipmap.ic_22_hide);
            mRightLogo.setImageResource(R.mipmap.ic_33_hide);

        });

        et_username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                // 如果用户名清空了 清空密码 清空记住密码选项
                et_password.setText("");
                if (s.length() > 0) {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    @OnClick(R.id.btn_login)
    void startLogin() {

        boolean isNetConnected = NetUtil.isConnected(this);
        if (!isNetConnected) {
            toast("当前网络不可用,请检查网络设置");
            return;
        }
        mPresenter.validateCredentials(et_username.getText().toString(), et_password.getText().toString());
    }

    @Override
    public void setUsernameError() {
        snackbar("用户名不能为空");
    }

    @Override
    public void setPasswordError() {
        snackbar("密码不能为空");
    }

    @Override
    public void navigateToHome() {
        PreferenceUtils.putBoolean(ConstantUtils.KEY, true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showProgress() {
        super.showProgress();
    }
}
