package com.gikee.app.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {

    private String userName;

    private String passWord;

    private String email;

    private String phoneNum;

    private String nickName;

    private String userImg;

    @Generated(hash = 646508216)
    public UserBean(String userName, String passWord, String email, String phoneNum,
            String nickName, String userImg) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.phoneNum = phoneNum;
        this.nickName = nickName;
        this.userImg = userImg;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return this.userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
