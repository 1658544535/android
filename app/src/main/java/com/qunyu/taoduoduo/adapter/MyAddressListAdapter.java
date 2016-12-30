package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.bean.MyAddressBean;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/30.
 */

public class MyAddressListAdapter extends BaseAdapter{
    private Context mc;
    private ArrayList<MyAddressBean> mlist;
    private MyAddressBean bean;
    private LayoutInflater layoutInflater;
    private Handler mhandler;
    private boolean isSelect;

    public MyAddressListAdapter(Context c,ArrayList<MyAddressBean> list,Handler handler) {
        this.mc=c;
        this.mlist=list;
        layoutInflater=LayoutInflater.from(mc);
        mhandler=handler;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder=new ViewHolder();

        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.my_address_listitem,null,false);
            holder.userName= (TextView) convertView.findViewById(R.id.tv_username);
            holder.userAddress= (TextView) convertView.findViewById(R.id.tv_userAddress);
            holder.userPhoneNum= (TextView) convertView.findViewById(R.id.tv_userPhoneNum);
            holder.tv_select_address= (TextView) convertView.findViewById(R.id.tv_select_address2);
            holder.iv_select_address= (ImageView) convertView.findViewById(R.id.iv_select_address1);
            holder.layout_delete= (PercentLinearLayout) convertView.findViewById(R.id.tv_delete);
            holder.layout_edit= (PercentLinearLayout) convertView.findViewById(R.id.tv_edit);
            holder.layout_select= (PercentLinearLayout) convertView.findViewById(R.id.layout_select);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        bean=mlist.get(position);
        holder.userName.setText(bean.getName());
        holder.userAddress.setText(bean.getAddress());
        holder.userPhoneNum.setText(bean.getTel());
        if(bean.getIsDefault()==1){
            holder.iv_select_address.setImageResource(R.mipmap.default_icon_seleted);
            holder.tv_select_address.setTextColor(mc.getResources().getColor(R.color.text_01));
        }
        //删除按钮
        holder.layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=Message.obtain();
                msg.arg2=Integer.parseInt(mlist.get(position).getAddId());
                msg.arg1=position;
                msg.what=1;
                mhandler.sendMessage(msg);
            }
        });
        holder.layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=Message.obtain();
                msg.what=3;
                msg.arg1=position;
                msg.arg2=Integer.parseInt(mlist.get(position).getAddId());
                mhandler.sendMessage(msg);
               // AddressDialogFragment.mhandler.sendMessage(msg);
            }
        });

        holder.layout_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=Message.obtain();
                msg.what=4;
                msg.arg1=position;
                msg.arg2=Integer.parseInt(mlist.get(position).getAddId());
                mhandler.sendMessage(msg);
                isSelect=true;
            }
        });
//        if(isSelect){
//            holder.iv_select_address.setImageResource(R.mipmap.default_icon_seleted);
//            holder.tv_select_address.setTextColor(mc.getResources().getColor(R.color.text_01));
//            isSelect=false;
//        }





        return convertView;
    }

    private class ViewHolder{
        TextView userName,userAddress,userPhoneNum,tv_select_address;
        ImageView iv_select_address;
        PercentLinearLayout layout_delete,layout_edit,layout_select;

    }
}
