package com.pingxundata.answerliu.pxshare;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.pingxundata.pxmeta.utils.AppUtils;
import com.pingxundata.pxmeta.utils.ToastUtils;
import com.pingxundata.pxmeta.views.basepopupview.BasePopupWindow;


/**
 * Created by LH on 2017/11/28.
 * Describe:
 */

public class SharePopupView extends BasePopupWindow implements View.OnClickListener {

    private View popupView;
    private ShareBean mShareBean;
    private Context mContext;


    public SharePopupView(Activity context, ShareBean shareBean, Context ctx) {
        super(context);
        this.mShareBean = shareBean;
        this.mContext=ctx;
        bindEvent();
    }

    public SharePopupView(Activity context, int w, int h) {
        super(context, w, h);
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    public View getClickToDismissView() {
        return popupView.findViewById(R.id.click_to_dismiss);
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_share, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }

    private void bindEvent() {
        if (popupView != null) {
            popupView.findViewById(R.id.btn_share_wechat).setOnClickListener(this);
            popupView.findViewById(R.id.btn_share_circle_of_friends).setOnClickListener(this);
            popupView.findViewById(R.id.btn_share_qq).setOnClickListener(this);
            popupView.findViewById(R.id.btn_share_qzone).setOnClickListener(this);
            popupView.findViewById(R.id.btn_share_weibo).setOnClickListener(this);
            popupView.findViewById(R.id.btn_share_sms).setOnClickListener(this);
            popupView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();


        if (i == R.id.btn_share_wechat) {
            if (!AppUtils.appInstalled(mContext, "com.tencent.mm")) {
                ToastUtils.showToast(mContext, "检测到您未安装微信或版本过低，请安装或升级微信后重试");
                return;
            }
            ShareTypeManager.shareWeChat(mShareBean);
//            ToastUtils.showToast(mContext,"暂未开放!");
        } else if (i == R.id.btn_share_circle_of_friends) {
            if (!AppUtils.appInstalled(mContext, "com.tencent.mm")) {
                ToastUtils.showToast(mContext, "检测到您未安装微信或版本过低，请安装或升级微信后重试");
                return;
            }
            ShareTypeManager.shareWechatMoments(mShareBean);
//            ToastUtils.showToast(mContext,"暂未开放!");
        } else if (i == R.id.btn_share_qq) {
            String [] pks = {"com.tencent.mobileqq","com.tencent.mobileqqi","com.tencent.qqlite","com.tencent.minihd.qq","com.tencent.tim"};
            DemoUtils.isValidClient(pks);
            ShareTypeManager.shareQQ(mShareBean);
        } else if (i == R.id.btn_share_qzone) {
            String [] pks = {"com.tencent.mobileqq","com.tencent.mobileqqi","com.tencent.qqlite","com.tencent.minihd.qq","com.tencent.tim"};
            DemoUtils.isValidClient(pks);
            ShareTypeManager.shareQZone(mShareBean);
        } else if (i == R.id.btn_share_weibo) {
            if (!AppUtils.appInstalled(mContext, "com.sina.weibo")) {
                ToastUtils.showToast(mContext, "检测到您未安装新浪微博或版本过低，请安装或升级新浪微博后重试");
                return;
            }
            ShareTypeManager.shareWeiBo(mShareBean);
//            ToastUtils.showToast(mContext,"暂未开放!");
        } else if (i == R.id.btn_share_sms) {
            ShareTypeManager.shareShortMessage(mShareBean);
        } else if (i == R.id.btn_cancel) {
            dismiss();
        } else {

        }

    }


}
