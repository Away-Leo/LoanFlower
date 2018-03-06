package com.pingxun.loanflower.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.loanflower.R;
import com.pingxundata.answerliu.pxcore.data.ProductListBean;
import com.pingxundata.pxmeta.utils.GlideImgManager;
import com.pingxundata.pxmeta.utils.MyTools;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * 产品列表adapter
 */

public class ProductListAdapter extends BaseQuickAdapter<ProductListBean, BaseViewHolder> {


    public ProductListAdapter(int layoutResId, List<ProductListBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, ProductListBean item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_money_range, "额度范围  " + MyTools.initTvQuota(item.getStartAmount(), item.getEndAmount()));
        helper.setText(R.id.tv_time_range,"期限范围  "+ String.valueOf(item.getStartPeriod())+"~"+ String.valueOf(item.getEndPeriod())+item.getPeriodType());
        helper.setText(R.id.tv_interest_rate, item.getPeriodType() + "利率  " + String.valueOf(item.getServiceRate()) + "%");
        GlideImgManager.glideLoader(mContext, item.getImg(), R.mipmap.img_default, R.mipmap.img_default, (ImageView) helper.getView(R.id.iv_icon), 1);
//      Glide.with(mContext).load(item.getImg()).crossFade().transform(new GlideRoundTransform(mContext,10)).into((ImageView) helper.getView(R.id.iv));
        SpannableString spannableString = new SpannableString("申请人数"+ String.valueOf(item.getClickNum())+"人");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 4,spannableString.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.tv_apply_num, spannableString);




    }


}