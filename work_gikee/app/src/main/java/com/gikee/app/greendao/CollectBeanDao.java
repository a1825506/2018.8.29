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
 * DAO for table "COLLECT_BEAN".
*/
public class CollectBeanDao extends AbstractDao<CollectBean, Long> {

    public static final String TABLENAME = "COLLECT_BEAN";

    /**
     * Properties of entity CollectBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tag = new Property(2, String.class, "tag", false, "TAG");
        public final static Property Logo = new Property(3, String.class, "logo", false, "LOGO");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
        public final static Property Trandnum = new Property(5, Integer.class, "trandnum", false, "TRANDNUM");
        public final static Property Addressid = new Property(6, String.class, "addressid", false, "ADDRESSID");
        public final static Property Address_list = new Property(7, String.class, "address_list", false, "ADDRESS_LIST");
        public final static Property Iscollect = new Property(8, boolean.class, "iscollect", false, "ISCOLLECT");
        public final static Property Balance = new Property(9, String.class, "balance", false, "BALANCE");
        public final static Property Address_type = new Property(10, String.class, "address_type", false, "ADDRESS_TYPE");
        public final static Property Detail = new Property(11, String.class, "detail", false, "DETAIL");
        public final static Property Count = new Property(12, String.class, "count", false, "COUNT");
        public final static Property Collect_time = new Property(13, String.class, "collect_time", false, "COLLECT_TIME");
        public final static Property Trade_count = new Property(14, int.class, "trade_count", false, "TRADE_COUNT");
    }


    public CollectBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CollectBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COLLECT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TAG\" TEXT," + // 2: tag
                "\"LOGO\" TEXT," + // 3: logo
                "\"TYPE\" TEXT," + // 4: type
                "\"TRANDNUM\" INTEGER," + // 5: trandnum
                "\"ADDRESSID\" TEXT," + // 6: addressid
                "\"ADDRESS_LIST\" TEXT," + // 7: address_list
                "\"ISCOLLECT\" INTEGER NOT NULL ," + // 8: iscollect
                "\"BALANCE\" TEXT," + // 9: balance
                "\"ADDRESS_TYPE\" TEXT," + // 10: address_type
                "\"DETAIL\" TEXT," + // 11: detail
                "\"COUNT\" TEXT," + // 12: count
                "\"COLLECT_TIME\" TEXT," + // 13: collect_time
                "\"TRADE_COUNT\" INTEGER NOT NULL );"); // 14: trade_count
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COLLECT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CollectBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(3, tag);
        }
 
        String logo = entity.getLogo();
        if (logo != null) {
            stmt.bindString(4, logo);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        Integer trandnum = entity.getTrandnum();
        if (trandnum != null) {
            stmt.bindLong(6, trandnum);
        }
 
        String addressid = entity.getAddressid();
        if (addressid != null) {
            stmt.bindString(7, addressid);
        }
 
        String address_list = entity.getAddress_list();
        if (address_list != null) {
            stmt.bindString(8, address_list);
        }
        stmt.bindLong(9, entity.getIscollect() ? 1L: 0L);
 
        String balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(10, balance);
        }
 
        String address_type = entity.getAddress_type();
        if (address_type != null) {
            stmt.bindString(11, address_type);
        }
 
        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(12, detail);
        }
 
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(13, count);
        }
 
        String collect_time = entity.getCollect_time();
        if (collect_time != null) {
            stmt.bindString(14, collect_time);
        }
        stmt.bindLong(15, entity.getTrade_count());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CollectBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(3, tag);
        }
 
        String logo = entity.getLogo();
        if (logo != null) {
            stmt.bindString(4, logo);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(5, type);
        }
 
        Integer trandnum = entity.getTrandnum();
        if (trandnum != null) {
            stmt.bindLong(6, trandnum);
        }
 
        String addressid = entity.getAddressid();
        if (addressid != null) {
            stmt.bindString(7, addressid);
        }
 
        String address_list = entity.getAddress_list();
        if (address_list != null) {
            stmt.bindString(8, address_list);
        }
        stmt.bindLong(9, entity.getIscollect() ? 1L: 0L);
 
        String balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(10, balance);
        }
 
        String address_type = entity.getAddress_type();
        if (address_type != null) {
            stmt.bindString(11, address_type);
        }
 
        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(12, detail);
        }
 
        String count = entity.getCount();
        if (count != null) {
            stmt.bindString(13, count);
        }
 
        String collect_time = entity.getCollect_time();
        if (collect_time != null) {
            stmt.bindString(14, collect_time);
        }
        stmt.bindLong(15, entity.getTrade_count());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CollectBean readEntity(Cursor cursor, int offset) {
        CollectBean entity = new CollectBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tag
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // logo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // type
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // trandnum
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // addressid
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // address_list
            cursor.getShort(offset + 8) != 0, // iscollect
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // balance
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // address_type
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // detail
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // count
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // collect_time
            cursor.getInt(offset + 14) // trade_count
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CollectBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTag(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLogo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTrandnum(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setAddressid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAddress_list(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIscollect(cursor.getShort(offset + 8) != 0);
        entity.setBalance(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAddress_type(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDetail(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCount(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCollect_time(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setTrade_count(cursor.getInt(offset + 14));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CollectBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CollectBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CollectBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
