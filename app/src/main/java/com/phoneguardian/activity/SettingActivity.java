package com.phoneguardian.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.phoneguardian.R;
import com.phoneguardian.base.BaseActivity;
import com.phoneguardian.utils.PrefUtils;
import com.phoneguardian.view.SettingClickView;
import com.phoneguardian.view.SettingItemView;

/**
 * Created by Administrator on 2016/6/7.
 */
public class SettingActivity extends BaseActivity{

    private String[] mItems;
    private SettingClickView scvStyle;
    private int mWhichStyle;



    public static void startActivity(Context context){
        Intent intent  = new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }



    @Override
    public void initVariables() {
        mItems = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };
        mWhichStyle = PrefUtils.getInt(PREF_ADDRESS_STYLE, 0, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        initUpdate();
        initAddress();

    }

    /**
     * 电话归上帝啊显示
     */
    private void initAddress() {
        initAddressStyle();



    }

    /**
     * 归属地提示框风格
     */
    private void initAddressStyle() {

        SettingClickView scvAddressStyle = (SettingClickView) findViewById(R.id.scv_address_style);
        scvAddressStyle.setTitle("归属地提示框风格");
        scvAddressStyle.setDesc(mItems[mWhichStyle]);
        scvAddressStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });



    }

    /**
     * 显示对话框
     */
    private void showChooseDialog() {
        AlertDialog.Builder addressDialog = new AlertDialog.Builder(SettingActivity.this);
        addressDialog.setTitle("归属地提示框风格");
        addressDialog.setIcon(R.drawable.ic_launcher);
        addressDialog.setSingleChoiceItems(mItems, mWhichStyle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保存风格
                PrefUtils.putInt(PREF_ADDRESS_STYLE,which,SettingActivity.this);
                scvStyle.setDesc(mItems[which]);
                dialog.dismiss();
            }
        });
        addressDialog.setNegativeButton("取消", null);
        addressDialog.show();

    }


    /**
     * 初始化自动更新
     */
    private void initUpdate() {
       final SettingItemView sivupdate = (SettingItemView) findViewById(R.id.siv_update);

        sivupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sivupdate.setCheck(!sivupdate.isChecked());
                PrefUtils.putBoolean(PREF_AUTO_UPDATE,sivupdate.isChecked(),SettingActivity.this);

            }
        });
        boolean autoUpdate = PrefUtils.getBoolean(PREF_AUTO_UPDATE, true, SettingActivity.this);
        sivupdate.setCheck(autoUpdate);

    }

    @Override
    protected void loadData() {

    }
}
