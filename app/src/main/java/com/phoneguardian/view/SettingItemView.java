package com.phoneguardian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phoneguardian.R;

/**
 * Created by Administrator on 2016/6/7.
 * 学得简单的 自定义View
 *
 */
public class SettingItemView extends RelativeLayout {

    public static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    private String mDescOff;//自动更新关
    private String mDescOn;//自动更新开

    private TextView tvTitle;
    private TextView tvDesc;//更新
    private CheckBox cbCheck;



    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int attrCount = attrs.getAttributeCount();
        //获取stitle属性的值
        String title = attrs.getAttributeValue(NAMESPACE, "stitle");//默认值要在attr.xml中定义
        //获取关闭设置时的描述
        mDescOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
        //获取开启设置时的描述
        mDescOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
        initView();

        setTitle(title);
        setDesc(mDescOff);

    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        View view =View.inflate(getContext(), R.layout.item_setting,null);

        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        cbCheck = (CheckBox) view.findViewById(R.id.cb_check);

        addView(view);
    }

    /**设置标题*/
    public void setTitle(String title){

        tvTitle.setText(title);
    }

    /**显示是否更新*/
    public void setDesc(String desc){
        tvDesc.setText(desc);
    }
    /** 是否开启 */
    public boolean isChecked(){
        return cbCheck.isChecked();
    }


    /**
     * 设置开启状态
     * @return
     */
    public void setCheck(boolean check){
        cbCheck.setChecked(check);
        if (check){
            setDesc(mDescOn);//开
        }else {
            setDesc(mDescOff);
        }
    }
}
