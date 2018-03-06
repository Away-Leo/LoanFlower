package com.pingxun.loanflower.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.data.CreditCardBean;
import com.pingxundata.pxmeta.utils.GlideImgManager;

import java.util.List;

/**
 * Created by LH on 2017/9/19.
 * Describe:首页办卡专区Adapter
 */

public class HomeCardRecommendAdapter extends BaseQuickAdapter<CreditCardBean, BaseViewHolder> {
    public HomeCardRecommendAdapter(@LayoutRes int layoutResId, @Nullable List<CreditCardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditCardBean item) {
        GlideImgManager.glideLoader(mContext,item.getImg(), R.mipmap.img_default,R.mipmap.img_default,(ImageView) helper.getView(R.id.iv),1);
        helper.setText(R.id.tv_title,item.getName());
    }
}
