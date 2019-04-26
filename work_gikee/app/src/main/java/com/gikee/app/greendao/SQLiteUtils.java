package com.gikee.app.greendao;


import com.gikee.app.base.GikeeApplication;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtils {

    private static SQLiteUtils instance;
    CollectBeanDao collectBeanDao;
    RemindBeanDao remindBeanDao;
    TrandBeanDao trandBeanDao;
    DaoSession daoSession;
    LoginBeanDao loginBeanDao;

    private SQLiteUtils() {
        collectBeanDao = GikeeApplication.getMyApplicationContext().getDaoSession().getCollectBeanDao();
        remindBeanDao = GikeeApplication.getMyApplicationContext().getDaoSession().getRemindBeanDao();
        trandBeanDao = GikeeApplication.getMyApplicationContext().getDaoSession().getTrandBeanDao();
        daoSession = GikeeApplication.getMyApplicationContext().getDaoSession();
        loginBeanDao =GikeeApplication.getMyApplicationContext().getDaoSession().getLoginBeanDao();
    }

    public static SQLiteUtils getInstance() {
        if (instance == null) {
            synchronized (SQLiteUtils.class) {
                if (instance == null) {
                    instance = new SQLiteUtils();
                }
            }
        }
        return instance;
    }



    //保存消息
    public void addRemind(RemindBean remindBean) {
        remindBeanDao.insert(remindBean);
    }

    //删除消息
    public void deleteRemind(RemindBean remindBean) {
        remindBeanDao.delete(remindBean);
    }

    //获取消息
    public List getRemind() {
        List list1 = remindBeanDao.queryBuilder().orderDesc(RemindBeanDao.Properties.Id).build().list();
        return list1 ;
    }



    //增加
    public void addContacts(CollectBean testBean) {
        collectBeanDao.insert(testBean);
    }

    //删除
    public void deleteContacts(CollectBean testBean) {
        collectBeanDao.delete(testBean);
    }

    public Long selectID(CollectBean testBean) {
        collectBeanDao.detachAll();//清除缓存
        return collectBeanDao.getKey(testBean);
    }


    //修改
    public void updateContacts(CollectBean testBean) {
        collectBeanDao.update(testBean);
    }

    //条件查询
    public List<CollectBean> selectAllContacts(String type) {
        collectBeanDao.detachAll();//清除缓存
        List<CollectBean> list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Type.eq(type)).orderAsc(CollectBeanDao.Properties.Trandnum).build().list();
        return list1 == null ? new ArrayList() : list1;
    }


    //条件查询
    public List<CollectBean> selectContacts(String type,String detail) {
        collectBeanDao.detachAll();//清除缓存
        List<CollectBean> list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Type.eq(type),CollectBeanDao.Properties.Detail.notEq(detail)).orderAsc(CollectBeanDao.Properties.Id).build().list();
        return list1 == null ? new ArrayList() : list1;
    }

    public List<CollectBean> selectExchangeAddress(String type,String tag) {
        collectBeanDao.detachAll();//清除缓存
        List<CollectBean> list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Type.eq(type),CollectBeanDao.Properties.Tag.eq(tag)).orderAsc(CollectBeanDao.Properties.Trandnum).build().list();
        return list1 == null ? new ArrayList() : list1;
    }



    //条件查询
    public boolean isCollect(String name) {
        boolean flag=false;
        collectBeanDao.detachAll();//清除缓存
        List list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Name.eq(name)).build().list();
        if(list1.size()>0){
            flag=true;
        }
        return flag ;
    }


    //条件查询
    public List getEntityID(String name) {
        boolean flag=false;
        collectBeanDao.detachAll();//清除缓存
        List list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Name.eq(name)).build().list();
        return list1 ;
    }

    public List getAddressID(String addressid) {
        boolean flag=false;
        collectBeanDao.detachAll();//清除缓存
        List list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Addressid.eq(addressid)).build().list();
        return list1 ;
    }

    //条件查询
    public boolean isAddressCollect(String addressid) {
        boolean flag=false;
        collectBeanDao.detachAll();//清除缓存
        List list1 = collectBeanDao.queryBuilder().where(CollectBeanDao.Properties.Addressid.eq(addressid)).build().list();
        if(list1.size()>0){
            flag = true;
        }
        return flag ;
    }

    //删除表中内容
    public void deleteAllContact() {
        collectBeanDao.deleteAll();
    }


    //查询趋势表
    public List getTrand(String symbol) {
        trandBeanDao.detachAll();//清除缓存
        List list1 = trandBeanDao.queryBuilder().where(TrandBeanDao.Properties.Symbol.eq(symbol)).orderAsc(TrandBeanDao.Properties.Trandnum).build().list();
        return list1 ;
    }
    //ba
    public void addTrand(TrandBean trandBean) {
        trandBeanDao.insert(trandBean);
    }

    public void updateTrand(TrandBean trandBean) {
        trandBeanDao.update(trandBean);
    }


    //保存登录信息
    public void addLogin(LoginBean loginBean) {

        List<LoginBean> loginBeanList =getLoginStatus();

        if(loginBeanList.size()!=0){

            List<LoginBean> loginBeanList_ex = getLoginStatus();

            loginBean.setId(loginBeanList_ex.get(0).getId());

            loginBeanDao.update(loginBean);
        }else
            loginBeanDao.insert(loginBean);
    }

    //获取登录
    public List getLoginStatus() {
        List list1 = loginBeanDao.queryBuilder().build().list();
        return list1 ;
    }



}
