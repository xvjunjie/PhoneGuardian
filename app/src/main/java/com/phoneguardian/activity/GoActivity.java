package com.phoneguardian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.phoneguardian.R;
import com.phoneguardian.base.BaseActivity;

/**
 * Created by Administrator on 2016/6/1.
 */
public class GoActivity extends BaseActivity {


    public static void startActivity(Context context){
        Intent intent = new Intent(context,GoActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_go);

    }

    @Override
    protected void loadData() {

    }
}
