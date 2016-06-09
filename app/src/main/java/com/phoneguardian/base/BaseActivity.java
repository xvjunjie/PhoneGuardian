package com.phoneguardian.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/5/31.
 */
public abstract class BaseActivity extends AppCompatActivity {

    //定Activity中用到的常量都定义在这边
    public static final String TAG_Lable  = "BaseActivity";
    public static final String PREF_PASSWORD = "password";
    public static final String PREF_AUTO_UPDATE = "auto_update";
    public static final String PREF_ADDRESS_STYLE = "address_style";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这边三个方法的位置不能改变，要要先初始化册参数下面才能够引用
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /** 初始化变量，包括启动Activity传过来的变量和Activity内的变量 */
    public abstract void initVariables();

    /** 初始化视图，加载layout布局文件，初始化控件，为控件挂上事件 */
    protected abstract void initViews(Bundle savedInstanceState);

    /** 加载数据，包括网络数据，缓存数据，用户数据，调用服务器接口获取的数据 */
    protected abstract void loadData();


}
