package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/5.
 */

public class PopuWindowListAdapter extends BaseAdapter{
    private List<String> list=new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mc;
    String[] mProvinceDatas;

    public PopuWindowListAdapter(Context context,String[] mProvinceDatas){
        this.mc=context;
        this.mProvinceDatas=mProvinceDatas;
        layoutInflater= LayoutInflater.from(mc);
    }
    @Override
    public int getCount() {
        return mProvinceDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return mProvinceDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null) {

            convertView = layoutInflater.inflate(R.layout.popuwindow_item, null,false);
            holder.tv_popuWindow= (TextView) convertView.findViewById(R.id.tv_popuWindow_item);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_popuWindow.setText(mProvinceDatas[position]);
        Log.i("province",mProvinceDatas[position]+"");

        return convertView;
    }
    class ViewHolder{
        TextView tv_popuWindow;
    }
}
