package com.phoneguardian.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.phoneguardian.R;
import com.phoneguardian.base.BaseActivity;
import com.phoneguardian.entity.AppInfo;
import com.phoneguardian.global.Config;
import com.phoneguardian.net.HttpRequest;
import com.phoneguardian.net.RequestCallback;

/**
 * Created by Administrator on 2016/5/31.
 *
 * 进入界面动图
 *
 * 学得网络请求，将json转实体类
 * 获得app版本号，版本名
 */
public class SplashActivity extends BaseActivity {

   /* @BindView(R.id.tv_version)
    TextView tvVersion;*/

    private AppInfo mLocalAppInfo;
    public static final String TAG_LOG = "SplashActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {
        mLocalAppInfo = getLocalAppInfo();
        Log.i(TAG_LOG, mLocalAppInfo.toString());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void loadData() {
        getAppInfoFromServer();

    }

    /**
     * 从服务端获得app学习，并转成实体类
     */
    private void getAppInfoFromServer() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.requestGet(Config.VERSION_URL, new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                Gson gson = new Gson();
                AppInfo gsonFromServer = gson.fromJson(content, AppInfo.class);
                Log.i(TAG_LOG, "服务器版本信息--->" + gsonFromServer);
//                goMainActivity();

//                MainActivity.startAct(getApplicationContext());
            }

            @Override
            public void onFaile(String errorMsg) {
                Toast.makeText(SplashActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                Log.e(TAG_LOG, "获取版本信息异常--->" + errorMsg);
            }
        });


    }


    /**
     * 从本地获得app版本号跟，名字
     * @return
     */
    public AppInfo getLocalAppInfo() {
        AppInfo appInfo = new AppInfo();
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;

    }
}
