package com.pingxun.loanflower.ui.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.UiHomeBinding;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxun.loanflower.other.Urls;
import com.pingxundata.pxcore.autoupdate.UpdateChecker;
import com.pingxundata.pxcore.utils.WallManager;
import com.pingxundata.pxmeta.utils.GDlocationUtil;
import com.pingxundata.pxmeta.utils.ToastUtils;
import com.pingxundata.pxmeta.views.DragFloatActionButton;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * 主Activity
 */
public class MainActivity extends BaseActivity<UiHomeBinding> implements TabHost.OnTabChangeListener{
    private long mLastPressBackTime;
    private MainTab[] mTabs;

    @Override
    protected int getLayoutId() {
        return R.layout.ui_home;
    }

    @Override
    protected void initData() {
        mTabs = MainTab.values();

        getAppModule();
        checkUpdate();
        WallManager.with(me,(DragFloatActionButton)findViewById(R.id.wallFloatingButton), InitDatas.APP_NAME,InitDatas.SUSPENDFLAG).doWall();
        AndPermission.with(me)
                .requestCode(100)
                .permission(Permission.LOCATION, Permission.STORAGE)
                .rationale(mRationaleListener)
                .callback(this)
                .start();
    }

    /**
     * 获取APP模块
     */
    private void getAppModule() {
//          String appModule= SharedPrefsUtil.getValue(App.getAppContext(),InitDatas.SP_NAME, InitDatas.AppModule,"6,7,5");//默认开启信用卡模块
//          if (!appModule.contains("3")){//0:是否通过,1:首页,2:消息,3:信用卡,4:产品超市,5:我的,6:产品大全,7:精准,8:攻略
//              mTabs[2] = mTabs[mTabs.length - 1];//把最后一个元素替代指定的元素
//              mTabs = Arrays.copyOf(mTabs, mTabs.length - 1);//数组缩容
//          }
        initTabs();
    }

    /**
     * 权限申请回调
     */
    private RationaleListener mRationaleListener = (requestCode, rationale) -> AlertDialog.newBuilder(me)
            .setTitle("提示")
            .setMessage("请开启定位权限以向您推荐适合的产品")
            .setPositiveButton("确定", (dialog2, which) -> rationale.resume())
            .setNegativeButton("取消", (dialog, which) -> rationale.cancel()).show();

    // 成功回调的方法，用注解即可，这里的100就是请求时的requestCode。
    @PermissionYes(100)
    private void getPermissionYes(List<String> grantedPermissions) {


        GDlocationUtil.init(getApplication());
        GDlocationUtil.getCurrentLocation(location -> {
            InitDatas.province = location.getProvince();
            InitDatas.city = location.getCity();
            InitDatas.district = location.getDistrict();
 //           Logger.d(InitDatas.province+"/"+InitDatas.city+"/"+InitDatas.district);
        });
    }

    @PermissionNo(100)
    private void getPermissionNo(List<String> grantedPermissions) {

    }


    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WallManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }



    /**
     * 检查更新
     */
    private void checkUpdate() {
        UpdateChecker.checkForDialog(me, InitDatas.APP_UPDATE, Urls.URL_GET_FIND_PRODUCT_VERSION);
    }



    @SuppressLint("ObsoleteSdkInt")
    private void initTabs() {
        bindingView.myTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        bindingView.myTabhost.getTabWidget().setShowDividers(0);
        for (MainTab mainTab : mTabs) {
            TabHost.TabSpec tabSpec = bindingView.myTabhost.newTabSpec(getString(mainTab.getResName()));
            @SuppressLint("InflateParams") ViewGroup indicator = (ViewGroup) getLayoutInflater().inflate(R.layout.tab_indicator, null, false);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            title.setText(getString(mainTab.getResName()));
            ImageView icon = (ImageView) indicator.findViewById(R.id.tab_icon);
            icon.setImageResource(mainTab.getResIcon());
            tabSpec.setIndicator(indicator);
            tabSpec.setContent(tag -> new View(getApplicationContext()));
            bindingView.myTabhost.addTab(tabSpec, mainTab.getClazz(), null);
        }
        bindingView.myTabhost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String s) {
        final int size = bindingView.myTabhost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = bindingView.myTabhost.getTabWidget().getChildAt(i);
            if (i == bindingView.myTabhost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_BACK) {
            long current = System.currentTimeMillis();
            if (current - mLastPressBackTime > 2000) {
                mLastPressBackTime = current;
                ToastUtils.showToast(me, "再按一次退出");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
