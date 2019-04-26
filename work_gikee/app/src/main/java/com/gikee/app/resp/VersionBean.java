package com.gikee.app.resp;

public class VersionBean extends ResponseInfo{


    /**
     * newVersion :
     * downUrl :
     * mode :
     */

    private String newVersion;
    private String downUrl;
    private String mode;
    private int size;
    private String versioninfo_zh_CN;
    private String versioninfo_en;

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVersioninfo_zh_CN() {
        return versioninfo_zh_CN;
    }

    public void setVersioninfo_zh_CN(String versioninfo_zh_CN) {
        this.versioninfo_zh_CN = versioninfo_zh_CN;
    }

    public String getVersioninfo_en() {
        return versioninfo_en;
    }

    public void setVersioninfo_en(String versioninfo_en) {
        this.versioninfo_en = versioninfo_en;
    }
}
