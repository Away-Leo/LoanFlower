package com.pingxun.loanflower.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.loanflower.R;
import com.pingxundata.answerliu.pxcore.data.ApplyListBean;
import com.pingxundata.pxmeta.utils.GlideImgManager;
import com.pingxundata.pxmeta.utils.MyTools;

import java.util.List;


/**
 * Created by Lh on 2017-08-03.
 * 申请列表adapter
 */

public class ApplyListAdapter extends BaseQuickAdapter<ApplyListBean, BaseViewHolder> {


    public ApplyListAdapter(int layoutResId, List<ApplyListBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }
    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean item) {
        GlideImgManager.glideLoader(mContext, item.getImg(), R.mipmap.img_default, R.mipmap.img_default, (ImageView) helper.getView(R.id.iv), 1);
        helper.setText(R.id.tv_title, item.getProductName());
        helper.setText(R.id.tv_edu, MyTools.initTvQuota(item.getStartAmount(), item.getEndAmount()));
        helper.setText(R.id.tv_qixian, String.valueOf(item.getStartPeriod())+"~"+ String.valueOf(item.getEndPeriod())+item.getPeriodType());
        helper.setText(R.id.tv_lilv_danwei,item.getPeriodType() + "利率");
        helper.setText(R.id.tv_lilv, String.valueOf(item.getServiceRate()) + "%");
//        SpannableString spannableString = new SpannableString("申请人数"+String.valueOf(item.getClickNum())+"人");
//        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 4,spannableString.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        helper.setText(R.id.tv_apply_num, spannableString);


    }


}