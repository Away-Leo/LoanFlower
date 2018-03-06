package com.pingxun.loanflower.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.loanflower.R;
import com.pingxundata.answerliu.pxcore.data.ServerModelList;
import com.pingxundata.pxmeta.utils.GlideImgManager;

import java.util.List;

/**
 * Created by LH on 2017/9/19.
 * Describe:首页产品推荐Adapter
 */

public class HomeProductRecommendAdapter extends BaseQuickAdapter<ServerModelList, BaseViewHolder> {
    public HomeProductRecommendAdapter(@LayoutRes int layoutResId, @Nullable List<ServerModelList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServerModelList item) {
        GlideImgManager.glideLoader(mContext,item.getImg(), R.mipmap.img_default,R.mipmap.img_default,(ImageView) helper.getView(R.id.iv),1);
        helper.setText(R.id.tv_title,item.getName());
    }
}
