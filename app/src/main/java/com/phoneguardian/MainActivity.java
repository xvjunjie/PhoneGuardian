package com.phoneguardian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.phoneguardian.activity.GoActivity;
import com.phoneguardian.activity.SettingActivity;
import com.phoneguardian.activity.TelecommunicationsActivity;
import com.phoneguardian.adapter.GridViewAdapter;
import com.phoneguardian.base.BaseActivity;
import com.phoneguardian.utils.MD5Utils;
import com.phoneguardian.utils.PrefUtils;
import com.phoneguardian.utils.ToastUtils;


public class MainActivity extends BaseActivity {

    private String[] strName;//功能项名字
    private int[] imagIds;//功能项图标


    //    学习这边的这段代码，方便页面跳转
    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initVariables() {
        strName = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
        imagIds = new int[]{

                R.drawable.home_safe,
                R.drawable.home_callmsgsafe, R.drawable.home_apps,
                R.drawable.home_taskmanager, R.drawable.home_netmanager,
                R.drawable.home_trojan, R.drawable.home_sysoptimize,
                R.drawable.home_tools, R.drawable.home_settings
        };
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        GridView gvFunction = (GridView) findViewById(R.id.gv_fuction);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, strName, imagIds);
        gvFunction.setAdapter(gridViewAdapter);
        gvFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://手机防盗界面
                        showSafeDialog();
                        break;
                    case 1://通讯卫士
                        TelecommunicationsActivity.staticActivity(MainActivity.this);
                        break;
                    case 8:
                        SettingActivity.startActivity(MainActivity.this);
                        break;

                }
            }
        });
    }


    /**
     * 显示对话框
     */
    private void showSafeDialog() {
        String savedPassword = PrefUtils.getString(PREF_PASSWORD, "", MainActivity.this);

        if (!TextUtils.isEmpty(savedPassword)){
            showInputDialog();
        }else {
            showSetPassworddialog();
        }

    }


    /**
     * 显示设置密码对话框
     */
    public void showSetPassworddialog(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.activity_set_password_dialog, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_Confirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_surePassWord);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                String passwordConfirm = etPasswordConfirm.getText().toString().trim();
                if (!TextUtils.isEmpty(password)&& !TextUtils.isEmpty(passwordConfirm)){
                    if (password.equals(passwordConfirm)){
                        PrefUtils.putString(PREF_PASSWORD, MD5Utils.encode(password), MainActivity.this);
                        GoActivity.startActivity(MainActivity.this);
                        dialog.dismiss();
                    }else {
                        ToastUtils.showToast(MainActivity.this,"两次输入密码不一致！");
                    }
                }else {
                    ToastUtils.showToast(MainActivity.this,"密码不能为空！");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view,0,0,0,0);
        dialog.show();

    }

    /**
     * 显示输入密码的对话框
     */
    public void showInputDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.activity_input_password_dialog, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                String savedPassword = PrefUtils.getString(PREF_PASSWORD, "", MainActivity.this);
                if (!TextUtils.isEmpty(password)){
                    if (MD5Utils.encode(password).equals(savedPassword)){
                        GoActivity.startActivity(MainActivity.this);
                        dialog.dismiss();
                    }else {
                        ToastUtils.showToast(MainActivity.this,"密码错误！");
                    }
                }else {
                    ToastUtils.showToast(MainActivity.this,"密码不能为空！");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view,0,0,0,0);
        dialog.show();

    }




    @Override
    protected void loadData() {

    }
}
