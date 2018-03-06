package com.pingxun.loanflower.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pingxun.loanflower.R;
import com.pingxundata.answerliu.pxcore.data.ApplyListBean;

import java.util.List;

/**
 * Created by Lh on 2017-08-03.
 * 消息
 */

public class MsgListAdapter extends BaseQuickAdapter<ApplyListBean, BaseViewHolder> {


    public MsgListAdapter(int layoutResId, List<ApplyListBean> dataBeanList) {
        super(layoutResId, dataBeanList);

    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyListBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time,item.getSendDate());
        helper.setText(R.id.tv_content,item.getContent());

        if (!item.isRead()) {
            helper.setImageResource(R.id.iv_information,R.mipmap.icon_new_information);
        } else {
            helper.setImageResource(R.id.iv_information,R.mipmap.icon_information);
        }





    }


}