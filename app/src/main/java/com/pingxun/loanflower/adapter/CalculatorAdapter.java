package com.pingxun.loanflower.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.data.CalculatorResult;

import java.util.List;


/**
 * Created by Lh on 2017-08-03.
 * 计算结果
 */

public class CalculatorAdapter extends BaseQuickAdapter<CalculatorResult, BaseViewHolder> {


    public CalculatorAdapter(int layoutResId, List<CalculatorResult> dataBeanList) {
        super(layoutResId, dataBeanList);

    }
    @Override
    protected void convert(BaseViewHolder helper, CalculatorResult item) {

          helper.setText(R.id.tv_month_num,item.getMonthNum());
          helper.setText(R.id.tv_yuegongbenjin,item.getYueGongBenJin()+"");
          helper.setText(R.id.tv_yuegonglixi,item.getYueGongLixi()+"");
          helper.setText(R.id.tv_haiqianjin,item.getHaiQianJin()+"");
//        helper.setText(R.id.tv_month_num, item.getProductName());
//        helper.setText(R.id.tv_edu, MyTools.initTvQuota(item.getStartAmount(), item.getEndAmount()));
//        helper.setText(R.id.tv_qixian, String.valueOf(item.getStartPeriod())+"~"+ String.valueOf(item.getEndPeriod())+item.getPeriodType());
//        helper.setText(R.id.tv_lilv_danwei,item.getPeriodType() + "利率");
//        helper.setText(R.id.tv_lilv, String.valueOf(item.getServiceRate()) + "%");
//        SpannableString spannableString = new SpannableString("申请人数"+String.valueOf(item.getClickNum())+"人");
//        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 4,spannableString.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        helper.setText(R.id.tv_apply_num, spannableString);


    }


}