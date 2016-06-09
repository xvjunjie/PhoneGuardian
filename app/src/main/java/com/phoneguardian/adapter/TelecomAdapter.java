package com.phoneguardian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phoneguardian.R;
import com.phoneguardian.db.TeleComDao;
import com.phoneguardian.entity.TeleComNum;

import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class TelecomAdapter extends BaseAdapter {
    Context context;
    TeleComDao teleComDao;
    List<TeleComNum>  listDate;

    public TelecomAdapter() {
    }

    public TelecomAdapter(Context context, TeleComDao teleComDao, List<TeleComNum> listDate) {
        this.context = context;
        this.teleComDao = teleComDao;
        this.listDate = listDate;
    }

    @Override
    public int getCount() {
        return listDate.size();
    }

    @Override
    public TeleComNum getItem(int position) {
        return listDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        ViewHolder viewHolder;
        if (convertView==null){
            view = View.inflate(context, R.layout.activity_telecom_listview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.tv_number);
            viewHolder.tvMode = (TextView) view.findViewById(R.id.tv_mode);
            viewHolder.ivDelete = (ImageView) view.findViewById(R.id.iv_delete);

            view.setTag(viewHolder);
        }else {
            view= convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        final TeleComNum teleComNum = getItem(position);
        viewHolder.tvNumber.setText(teleComNum.getNumber());
        switch (teleComNum.getMode()){
            case 1:
                viewHolder.tvMode.setText("拦截电话");
                break;
            case 2:
                viewHolder.tvMode.setText("拦截短信");
                break;
            case 3:
                viewHolder.tvMode.setText("拦截电话+短信");

        }
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDate.remove(teleComNum);
                teleComDao.delete(teleComNum.number);
                //更新数据
                notifyDataSetChanged();
            }
        });
        return view;
    }
    static class ViewHolder{
        TextView tvNumber;
        TextView tvMode;
        ImageView ivDelete;


    }
}
