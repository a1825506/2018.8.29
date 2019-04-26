package com.gikee.app.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_BEAN".
*/
public class UserBeanDao extends AbstractDao<UserBean, Void> {

    public static final String TABLENAME = "USER_BEAN";

    /**
     * Properties of entity UserBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UserName = new Property(0, String.class, "userName", false, "USER_NAME");
        public final static Property PassWord = new Property(1, String.class, "passWord", false, "PASS_WORD");
        public final static Property Email = new Property(2, String.class, "email", false, "EMAIL");
        public final static Property PhoneNum = new Property(3, String.class, "phoneNum", false, "PHONE_NUM");
        public final static Property NickName = new Property(4, String.class, "nickName", false, "NICK_NAME");
        public final static Property UserImg = new Property(5, String.class, "userImg", false, "USER_IMG");
    }


    public UserBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_BEAN\" (" + //
                "\"USER_NAME\" TEXT," + // 0: userName
                "\"PASS_WORD\" TEXT," + // 1: passWord
                "\"EMAIL\" TEXT," + // 2: email
                "\"PHONE_NUM\" TEXT," + // 3: phoneNum
                "\"NICK_NAME\" TEXT," + // 4: nickName
                "\"USER_IMG\" TEXT);"); // 5: userImg
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(1, userName);
        }
 
        String passWord = entity.getPassWord();
        if (passWord != null) {
            stmt.bindString(2, passWord);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
 
        String phoneNum = entity.getPhoneNum();
        if (phoneNum != null) {
            stmt.bindString(4, phoneNum);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(5, nickName);
        }
 
        String userImg = entity.getUserImg();
        if (userImg != null) {
            stmt.bindString(6, userImg);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(1, userName);
        }
 
        String passWord = entity.getPassWord();
        if (passWord != null) {
            stmt.bindString(2, passWord);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
 
        String phoneNum = entity.getPhoneNum();
        if (phoneNum != null) {
            stmt.bindString(4, phoneNum);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(5, nickName);
        }
 
        String userImg = entity.getUserImg();
        if (userImg != null) {
            stmt.bindString(6, userImg);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public UserBean readEntity(Cursor cursor, int offset) {
        UserBean entity = new UserBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userName
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // passWord
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // email
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // phoneNum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // nickName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // userImg
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserBean entity, int offset) {
        entity.setUserName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPassWord(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEmail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPhoneNum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNickName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserImg(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(UserBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(UserBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(UserBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
