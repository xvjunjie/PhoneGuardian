package com.phoneguardian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phoneguardian.R;

/**
 * Created by Administrator on 2016/5/31.
 */
public class GridViewAdapter extends BaseAdapter {

    private  String[] strName ;//功能项名字
    private  int[] imagIds ;//功能项图标
    private final  Context context;

    public GridViewAdapter(Context context,String[] strName,  int[] imagIds) {
        this.strName = strName;
        this.context = context;
        this.imagIds = imagIds;
    }

    @Override
    public int getCount() {
       return strName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override//// TODO: 2016/5/31
    public View getView(int position, View convertView, ViewGroup parent) {//这边的代码可以做优化
        //生成视图
        View view = View.inflate(context, R.layout.activity_gridview_item,null);
        //获取视图中的图片展示项的控件
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        //获取文字描述项的控件
        TextView tvFuncName = (TextView) view.findViewById(R.id.tv_func_name);

        //设置文字和图片
        ivIcon.setImageResource(imagIds[position]);
        tvFuncName.setText(strName[position]);
        return view;
    }
}
