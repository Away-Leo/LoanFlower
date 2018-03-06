package com.pingxun.loanflower.base;

import android.support.v7.app.AppCompatDelegate;

import com.mob.MobSDK;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxundata.pxcore.applications.BaseApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;




/**
 * Created by LH.
 * Application基类
 */
public class App extends BaseApplication {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(R.color.white, R.color.tab_font);//全局设置主题颜色
            return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
        });
    }


    @Override
    protected void appInit() {

        MobSDK.init(this, "24033fc53da00", "7dbcbfa6c8b9dcc05ad9de8ae0817a61");
        UMConfigure.init(this, InitDatas.UMENG_APPKEY, InitDatas.CHANNEL_NO, UMConfigure.DEVICE_TYPE_PHONE, null);

    }




}
