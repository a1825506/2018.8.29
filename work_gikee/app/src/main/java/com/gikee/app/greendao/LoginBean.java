package com.gikee.app.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LoginBean {

    @Id(autoincrement = true)
    private Long id;


    public boolean fristRun;

    public int loginflax;

    @Generated(hash = 655744244)
    public LoginBean(Long id, boolean fristRun, int loginflax) {
        this.id = id;
        this.fristRun = fristRun;
        this.loginflax = loginflax;
    }

    @Generated(hash = 1112702939)
    public LoginBean() {
    }

    public boolean getFristRun() {
        return this.fristRun;
    }

    public void setFristRun(boolean fristRun) {
        this.fristRun = fristRun;
    }

    public int getLoginflax() {
        return this.loginflax;
    }

    public void setLoginflax(int loginflax) {
        this.loginflax = loginflax;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
