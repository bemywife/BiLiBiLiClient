package com.fightzhao.bilibiliclient.module.common.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.fightzhao.bilibiliclient.R;
import com.fightzhao.bilibiliclient.base.BaseActivity;
import com.fightzhao.bilibiliclient.entity.discover.ActivityCenterInfo;
import com.fightzhao.bilibiliclient.http.HostType;
import com.fightzhao.bilibiliclient.http.manager.RetrofitManager;
import com.fightzhao.bilibiliclient.module.login.view.LoginActivity;
import com.fightzhao.bilibiliclient.utils.ConstantUtils;
import com.fightzhao.bilibiliclient.utils.PreferenceUtils;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_exit)
    Button mExit;


    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        RetrofitManager.getInstance(HostType.HOST_API_BASE_URL).getActivityCenterObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActivityCenterInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ActivityCenterInfo activityCenterInfo) {
                         for (ActivityCenterInfo.ListBean item : activityCenterInfo.list){
                             KLog.i("item",item.title);
                         }
                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_exit)
    void exit() {
        //退出登录
        PreferenceUtils.putBoolean(ConstantUtils.KEY, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


}
