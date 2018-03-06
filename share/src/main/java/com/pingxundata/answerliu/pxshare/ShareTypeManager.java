package com.pingxundata.answerliu.pxshare;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by LH on 2017/11/29.
 * Describe:分享工具类
 */

public class ShareTypeManager {

     private static MyPlatformActionListener myPlatformActionListener = new MyPlatformActionListener();

    /**
     * 分享到微信好友
     */
    public static void shareWeChat(ShareBean shareBean) {

        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(shareBean.getText());
        sp.setTitle(shareBean.getTitle());
        sp.setImageUrl(shareBean.getImageUrl());
        sp.setUrl(shareBean.getUrl());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(myPlatformActionListener);
        platform.share(sp);
    }

    /**
     * 分享到朋友圈
     */
    public static void shareWechatMoments(ShareBean shareBean) {
        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(shareBean.getText());
        sp.setTitle(shareBean.getText());
        sp.setUrl(shareBean.getUrl());
        sp.setImageUrl(shareBean.getImageUrl());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(myPlatformActionListener);
        platform.share(sp);
    }

    /**
     * 分享到QQ好友
     */
    public static void shareQQ(ShareBean shareBean) {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(shareBean.getText());
        sp.setTitle(shareBean.getTitle());
        sp.setTitleUrl(shareBean.getUrl());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(myPlatformActionListener);
        platform.share(sp);
    }

    /**
     * 分享到QQ空间
     */
    public static void shareQZone(ShareBean shareBean) {
        Platform platform = ShareSDK.getPlatform(QZone.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(shareBean.getText());
        sp.setTitle(shareBean.getTitle());
        sp.setUrl(shareBean.getUrl());
        sp.setTitleUrl(shareBean.getUrl());
        sp.setImageUrl(shareBean.getImageUrl());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(myPlatformActionListener);
        platform.share(sp);

    }

    /**
     * 分享到新浪微博
     */
    static void shareWeiBo(ShareBean shareBean) {
        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(shareBean.getText());
        sp.setTitle(shareBean.getTitle());
        sp.setUrl(shareBean.getUrl());
        sp.setImageUrl(shareBean.getImageUrl());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(myPlatformActionListener);
        platform.share(sp);
    }

    /**
     * 分享到短信
     */
    static void shareShortMessage(ShareBean shareBean) {
        Platform platform = ShareSDK.getPlatform(ShortMessage.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(shareBean.getText() + shareBean.getUrl());
        sp.setTitle(shareBean.getText() + shareBean.getUrl());
        platform.setPlatformActionListener(myPlatformActionListener);
        platform.share(sp);
    }


    private static class MyPlatformActionListener implements PlatformActionListener {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//            Log.e("onComplete==>>","分享成功");
            EventBus.getDefault().post("close_share_dialog");//eventBus更新用户名
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
//            throwable.printStackTrace();
//            final String error = throwable.toString();
//            Log.e("onError==>>","分享错误");
        }

        @Override
        public void onCancel(Platform platform, int i) {
//            Log.e("onCancel==>>","分享取消");
        }
    }

}
