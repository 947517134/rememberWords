package com.example.internetpic.liteOrm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.internetpic.MyApplication;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @date :2020/3/27 22:01
 */
public class DBHelper {
    /**
     * 数据库名称
     */
    private final String DB_NAME = "base_f.db";
    private final int DB_VERSION = 2;
    private static DBHelper instance;
    private static LiteOrm db;
    private Context context;

    private DBHelper() {
        if (MyApplication.getContextApplication() == null) {
            throw new NullPointerException("RHttp not init!");
        }
        context = MyApplication.getContextApplication();
        initDB(context);
    }

    public static DBHelper get() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化数据库
     *
     * @param context
     */
    private void initDB(Context context) {
        if (db == null) {
            DataBaseConfig config = new DataBaseConfig(context, DB_NAME);
            config.debugged = true; // open the log
            config.dbVersion = DB_VERSION; // set database version
            config.onUpdateListener = null; // set database update listener
            db = LiteOrm.newSingleInstance(config);
        }
    }

    /**
     * 插入或者更新对象
     * 备注:有则更新，无则插入
     *
     * @param object
     * @return
     */
    public long insertOrUpdate(Object object) {
        long count = 0;
        if (db != null) {
            count = db.save(object);
        }
        return count;
    }

    /**
     * 更新数据
     */
    public long update(Object object) {
        long count = 0;
        if (db != null) {
            count = db.update(object);
        }
        return count;
    }

    /**
     * 插入或者更新对象
     * 备注:有则更新，无则插入
     *
     * @param object
     * @return
     */
    public long insert(Object object) {
        long count = 0;
        if (db != null) {
            count = db.insert(object);
        }
        return count;
    }

    /**
     * 删除对象
     *
     * @param var1
     * @return
     */
    public int delete(Object var1) {
        int count = 0;
        if (db != null) {
            count = db.delete(var1);
            Log.e("info", "count======" + count);
        }
        return count;
    }


    /**
     * 删除所有对象
     */
    public <T> int deleteAll(Class<T> var1) {
        int count = 0;
        if (db != null) {
            count = db.deleteAll(var1);
            Log.e("info", "count======" + count);
        }
        return count;
    }

    /**
     * 查询数据总数
     *
     * @param var1
     * @param <T>
     * @return
     */
    public <T> long queryCount(Class<T> var1) {
        long count = 0;
        if (db != null) {
            count = db.queryCount(var1);
        }
        return count;
    }

    /**
     * 查询列表---倒叙
     */
    public <T> ArrayList<T> queryDesc(Class<T> var1) {
        ArrayList<T> list = new ArrayList<>();
        if (db != null) {//desc倒叙 esc正序
            list = db.query(new QueryBuilder<T>(var1).orderBy("time desc"));
        }
        return list;
    }

    /**
     * 查询列表
     *
     * @param var1
     * @param <T>
     * @return
     */
    public <T> ArrayList<T> query(Class<T> var1) {
        ArrayList<T> list = new ArrayList<>();
        if (db != null) {
            list = db.query(var1);
        }
        return list;
    }

    /**
     * 根据ID查询数据
     *
     * @param var1
     * @param var2
     * @param <T>
     * @return
     */
    public <T> T queryById(long var1, Class<T> var2) {
        T t = null;
        if (db != null) {
            t = db.queryById(var1, var2);
        }
        return t;
    }

    /**
     * 查询  某字段 等于 Value的值
     */

    public <T> List<T> queryByWhere(Class<T> cla, String field, String value) {
        return db.query(new QueryBuilder(cla).where(field + "=?", value));
    }
}
