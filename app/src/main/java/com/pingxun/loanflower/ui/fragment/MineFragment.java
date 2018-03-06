package com.pingxun.loanflower.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseFragment;
import com.pingxun.loanflower.databinding.FragmentMineBinding;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxun.loanflower.ui.activity.AboutUsActivity;
import com.pingxun.loanflower.ui.activity.ApplyListActivity;
import com.pingxun.loanflower.ui.activity.CooperationActivity;
import com.pingxun.loanflower.ui.activity.LoginActivity;
import com.pingxun.loanflower.ui.activity.MainActivity;
import com.pingxun.loanflower.ui.activity.UserInfoFirstActivity;
import com.pingxun.loanflower.ui.view.QuitLoginPopupView;
import com.pingxundata.answerliu.pxcore.other.Constant;
import com.pingxundata.answerliu.pxcore.other.EventMessage;
import com.pingxundata.answerliu.pxcore.view.ContactUsPopupView;
import com.pingxundata.pxmeta.utils.ActivityUtil;
import com.pingxundata.pxmeta.utils.NetUtil;
import com.pingxundata.pxmeta.utils.SharedPrefsUtil;
import com.pingxundata.pxmeta.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by LH on 2017/9/6.
 * Describe:我的Fragment
 */

public class MineFragment extends BaseFragment<FragmentMineBinding> implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{

    @Override
    protected int getRootLayoutResID() {
        EventBus.getDefault().register(this);//绑定事件接受
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销事件接受
    }

    @Override
    protected void initData() {
        bindingView.smartrefreshlayout.setColorSchemeResources(R.color.tab_font_bright);
        bindingView.smartrefreshlayout.setOnRefreshListener(this);
        initListener();
    }

    @Override
    protected void onLazyLoadOnce() {
        super.onLazyLoadOnce();
        onRefresh();
    }

    private void initListener() {
        bindingView.mineLogin.setOnClickListener(this);
        bindingView.mineShare.setOnClickListener(this);
        bindingView.mineCooperation.setOnClickListener(this);
        bindingView.mineUserInfo.setOnClickListener(this);
        bindingView.mineApply.setOnClickListener(this);
        bindingView.mineAboutUs.setOnClickListener(this);
        bindingView.mineContactUs.setOnClickListener(this);
        bindingView.btnQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_login://登录
                if (!SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                    ActivityUtil.recallGoForward(mActivity, LoginActivity.class, MainActivity.class, null, false);
                } else {
                    ToastUtils.showToast(App.getAppContext(), "您已登录");
                }
                break;
            case R.id.mine_user_info:
                if (NetUtil.getNetWorkState(App.getAppContext()) != -1) {
                    if (!SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                        ActivityUtil.recallGoForward(mActivity,LoginActivity.class,UserInfoFirstActivity.class,null,false);
                    } else {
                        ActivityUtil.goForward(mActivity, UserInfoFirstActivity.class, null, false);
                    }
                } else {
                    ToastUtils.showToast(App.getAppContext(), InitDatas.NO_NETWORK_MSG);
                }
                break;

            case R.id.mine_share://分享
                ToastUtils.showToast(App.getAppContext(),"此功能暂未开放,敬请期待!");
                break;

            case R.id.mine_cooperation://商务合作
                ActivityUtil.goForward(mActivity, CooperationActivity.class, null, false);
                break;

            case R.id.mine_apply://申请记录
                if (NetUtil.getNetWorkState(App.getAppContext()) != -1) {
                    if (!SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                        ActivityUtil.recallGoForward(mActivity, LoginActivity.class, ApplyListActivity.class, null, false);
                    } else {
                        ActivityUtil.goForward(mActivity, ApplyListActivity.class, null, false);
                    }
                } else {
                    ToastUtils.showToast(App.getAppContext(), InitDatas.NO_NETWORK_MSG);
                }
                break;
            case R.id.mine_about_us://关于我们
                ActivityUtil.goForward(mActivity, AboutUsActivity.class, null, false);
                break;
            case R.id.mine_contact_us://联系我们
                ContactUsPopupView contactUsPopupView = new ContactUsPopupView(mActivity);
                contactUsPopupView.setPopupWindowFullScreen(true);
                contactUsPopupView.showPopupWindow();
                break;
            case R.id.btn_quit://退出登录
                QuitLoginPopupView quitLoginPopupView = new QuitLoginPopupView(mActivity);
                quitLoginPopupView.setPopupWindowFullScreen(true);
                quitLoginPopupView.showPopupWindow();
                break;
        }
    }






    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(EventMessage message) {
        if (message.message.equals(Constant.RefreshMine)) {
            onRefresh();
        }
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
//        bindingView.tvVersion.setText("渠道版本号  " +InitDatas.CHANNEL_NO+"-"+AppUtils.getVersionName(mActivity).replaceAll("_","."));
        if (!SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
            bindingView.tvName.setText("请登录");
            bindingView.btnQuit.setVisibility(View.GONE);
        } else {
            String phoneStr = SharedPrefsUtil.getValue(mActivity, InitDatas.SP_NAME, InitDatas.UserPhone, "");
            String inputStr = phoneStr.substring(0, 4) + "****" + phoneStr.substring(8);
            bindingView.tvName.setText(inputStr);
            bindingView.btnQuit.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onRefresh() {
        bindingView.smartrefreshlayout.setRefreshing(true);
        bindingView.smartrefreshlayout.postDelayed(() -> {
            initView();
            bindingView.smartrefreshlayout.setRefreshing(false);
        },InitDatas.waitTime);

    }
}
