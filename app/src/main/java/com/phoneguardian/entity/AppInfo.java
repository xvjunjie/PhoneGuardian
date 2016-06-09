package com.phoneguardian.entity;

/**
 * Created by Administrator on 2016/5/31.
 *
 *
 */
public class AppInfo  {

    private int versionCode;
    private String versionName;
    private String description;//描述
    private String downloadUrl;//下载地址

    public AppInfo() {
    }

    public AppInfo(int versionCode, String versionName) {
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public AppInfo(int versionCode, String versionName, String description) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.description = description;
    }


    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", description='" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
