package com.pingxun.loanflower.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.pingxun.loanflower.R;
import com.umeng.analytics.MobclickAgent;


/**
 * Activity基类
 */
public abstract class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity{


    protected String TAG;
    protected Activity me;
    // 布局view
    protected SV bindingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        this.me = this;//引用me自己，便于子类调用
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        bindingView = DataBindingUtil.setContentView(me, getLayoutId());
        initData();
    }


    /**
     * 获取根布局
     * @return 布局ID
     */
    protected abstract int getLayoutId();

    /**
     * 加载数据
     */
    protected abstract void initData();

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    /**
     * 初始化头部返回的View
     */
    public void initTopView(String titleStr) {
        RelativeLayout back = (RelativeLayout)findViewById(R.id.iv_topview_back);
        TextView title = (TextView) findViewById(R.id.tv_topview_title);
        if (back != null) {
            back.setOnClickListener(v -> finish());
        }
        if (title != null) {
            title.setText(titleStr);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private ProgressDialog dialog;


    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(me);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
