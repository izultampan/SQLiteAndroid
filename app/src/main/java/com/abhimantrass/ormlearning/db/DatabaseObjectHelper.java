package com.abhimantrass.ormlearning.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhuhenk on 3/6/2015.
 */
public class DatabaseObjectHelper {
    private static final String TAG="DatabaseObjectHelper";

    private SQLiteDatabase mDatabase;
    private DatabaseSQLiteHelper mSqlHelper;

    public DatabaseObjectHelper(DatabaseSQLiteHelper sqliteHelper) {
        mSqlHelper = sqliteHelper;
    }

    public DatabaseObjectHelper(){}

    public void setSqlHelper(DatabaseSQLiteHelper sqlHelper) {
        mSqlHelper = sqlHelper;
    }

    public void open() {
        mDatabase = mSqlHelper.getWritableDatabase();
    }

    public void close() {
        mSqlHelper.close();
    }

    public DatabaseSQLiteHelper getSqlHelper() {
        return mSqlHelper;
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    public int truncateTable(Class<? extends DatabaseObject> cls) {
        open();
        int res = mDatabase.delete(cls.getSimpleName(), null, null);
        close();
        return res;
    }

    public long saveObject(DatabaseObject object) {

        open();

        ContentValues values = new ContentValues();
        Class<? extends  DatabaseObject> cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();

        for(Field f : fields) {
            f.setAccessible(true);
            DatabaseColumn columnCache = f.getAnnotation(DatabaseColumn.class);
            String key;
            if(columnCache != null) {
                boolean isPrimary = columnCache.isPrimaryKey();
                boolean autoIncrement = columnCache.autoincrement();
                boolean isIgnore = columnCache.isIgnore();
                if( (isPrimary && autoIncrement) || isIgnore)
                    continue;
                else
                    key = columnCache.columnName();
            } else {
                key = f.getName();
            }

            try {
                f.setAccessible(true);
                Object o = f.get(object);

                if(o instanceof Integer) {
                    values.put(key, (Integer) o);
                } else if(o instanceof String) {
                    values.put(key, (String) o);
                } else if(o instanceof Long) {
                    values.put(key, (Long) o);
                }
            } catch (IllegalAccessException e) {
                Log.d(TAG, e.getMessage());
                continue;
            }
        }

        long insertid = mDatabase.insert(cls.getSimpleName(), null, values);
        close();
        return insertid;
    }

    public long updteObject(DatabaseObject object, String whereClause, String[] whereArgs) {

        open();

        ContentValues values = new ContentValues();
        Class<? extends  DatabaseObject> cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();

        for(Field f : fields) {
            f.setAccessible(true);
            DatabaseColumn columnCache = f.getAnnotation(DatabaseColumn.class);
            String key;
            if(columnCache != null) {
                boolean isPrimary = columnCache.isPrimaryKey();
                boolean autoIncrement = columnCache.autoincrement();
                boolean isIgnore = columnCache.isIgnore();
                if( (isPrimary && autoIncrement) || isIgnore)
                    continue;
                else
                    key = columnCache.columnName();
            } else {
                key = f.getName();
            }

            try {
                f.setAccessible(true);
                Object o = f.get(object);

                if(o == null) {
                    continue;
                }

                if(o instanceof Integer) {
                    values.put(key, (Integer) o);
                } else if(o instanceof String) {
                    values.put(key, (String) o);
                } else if(o instanceof Long) {
                    values.put(key, (Long) o);
                }

            } catch (IllegalAccessException e) {
                Log.d(TAG, e.getMessage());
                continue;
            }
        }


        printContentValues(values);

        long updateId = mDatabase.update(cls.getSimpleName(), values, whereClause, whereArgs);
        close();
        return updateId;
    }

    public boolean insertBulkObjects(List<DatabaseObject> list){
        open();

        boolean result = false;
        mDatabase.beginTransaction();
        try {
            for (DatabaseObject object : list) {
                ContentValues values = new ContentValues();
                Class<? extends DatabaseObject> cls = object.getClass();
                Field[] fields = cls.getDeclaredFields();

                for (Field f : fields) {
                    f.setAccessible(true);
                    DatabaseColumn columnCache = f.getAnnotation(DatabaseColumn.class);
                    String key;
                    if (columnCache != null) {
                        boolean isPrimary = columnCache.isPrimaryKey();
                        boolean autoIncrement = columnCache.autoincrement();
                        boolean isIgnore = columnCache.isIgnore();
                        if ((isPrimary && autoIncrement) || isIgnore)
                            continue;
                        else
                            key = columnCache.columnName();
                    } else {
                        key = f.getName();
                    }

                    try {
                        f.setAccessible(true);
                        Object o = f.get(object);

                        if (o instanceof Integer) {
                            values.put(key, (Integer) o);
                        } else if (o instanceof String) {
                            values.put(key, (String) o);
                        } else if (o instanceof Long) {
                            values.put(key, (Long) o);
                        }
                    } catch (IllegalAccessException e) {
                        Log.d(TAG, e.getMessage());
                        continue;
                    }
                }

                mDatabase.insert(cls.getSimpleName(), null, values);
            }
            mDatabase.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
            //Log.d(TAG, e.getMessage());
        }finally {
            mDatabase.endTransaction();
            close();
        }

        return result;
    }

    public List<DatabaseObject> getAllData(Class<? extends DatabaseObject> cls) {
        return getAllDataBy(cls, null, null, false);
    }

    public List<DatabaseObject> getDataRawQuery(Class<? extends DatabaseObject> cls, String selectQuery) {
        open();
        Log.d(TAG, "SQL query : " + selectQuery);
        List<DatabaseObject> objs = new ArrayList<DatabaseObject>();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()) {
            try {
                do{
                    if(objs == null) objs = new ArrayList<DatabaseObject>();
                    DatabaseObject obj = cursorToCacheObject(cursor, cls);
                    objs.add(obj);
                } while(cursor.moveToNext());
            } catch (Exception e) {
            }
        }

        close();

        return objs;
    }

    public DatabaseObject getObjectRawQuery(Class<? extends DatabaseObject> cls, String selectQuery) {
        open();
        Log.d(TAG, "SQL query : " + selectQuery);
        DatabaseObject obj = null;
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        if(cursor != null && cursor.getCount() > 0){
            try {
                cursor.moveToFirst();
                obj = cursorToCacheObject(cursor, cls);
            }catch (Exception e){}
            cursor.close();
        }
        close();
        return obj;
    }

    public  DatabaseObject cursorToCacheObject(Cursor cursor, Class<? extends DatabaseObject> clss) throws Exception {
        int collen = cursor.getColumnCount();

        DatabaseObject cObj = clss.newInstance();

        for(int i=0; i < collen; i++) {
            String colName = cursor.getColumnName(i);
            int type = cursor.getType(i);
            //Log.d(TAG, "Cursor type = " + type + " , Column name = " + colName);
            if(type != Cursor.FIELD_TYPE_NULL) {
                Object obj;
                switch (type) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        obj = cursor.getInt(i);
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        obj = cursor.getFloat(i);
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        obj = cursor.getString(i);
                        break;
                    default:
                        throw new Exception("Wrong cursor type...");
                }

                setValue(colName, cObj, obj);
            }
        }

        return cObj;
    }

    private void setValue(String columnName, DatabaseObject cObj, Object value) throws Exception {
        Class<? extends  DatabaseObject> clss = cObj.getClass();
        Field[] fields = clss.getDeclaredFields();
        Field selectedField = null;

        for(Field field: fields) {
            DatabaseColumn columnCache = field.getAnnotation(DatabaseColumn.class);
            String name;
            if(columnCache!=null) {
                name = columnCache.columnName();
            } else {
                name = field.getName();
            }
            if(name != null && name.equals(columnName)) {
                selectedField = field;
                selectedField.setAccessible(true);
                break;
            }
        }

        if(selectedField != null) {
            selectedField.set(cObj, value);
        }
    }

    public List<DatabaseObject> getAllDataBy(Class<? extends DatabaseObject> cls, String whereClause, String orderByClause, boolean isDescending) {
        return getAllDataBy(cls, whereClause, orderByClause, "", isDescending);
    }

    public List<DatabaseObject> getAllDataBy(Class<? extends DatabaseObject> cls, String whereClause, String orderByClause, String groupBy, boolean isDescending) {

        String selectQuery = "select * from " + cls.getSimpleName();

        if(!TextUtils.isEmpty(whereClause))
            selectQuery += " " + whereClause;

        if(!TextUtils.isEmpty(orderByClause))
            selectQuery += " " + orderByClause;

        if (!TextUtils.isEmpty(groupBy)) {
            selectQuery += " " + groupBy;
        }

        if(isDescending)
            selectQuery += " DESC";

        open();
        Log.d(TAG, "SQL query : " + selectQuery);
        List<DatabaseObject> objs = new ArrayList<DatabaseObject>();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()) {
            try {
                do{
                    if(objs == null) objs = new ArrayList<DatabaseObject>();
                    DatabaseObject obj = cursorToCacheObject(cursor, cls);
                    objs.add(obj);
                } while(cursor.moveToNext());
            } catch (Exception e) {
                Log.e("DatabaseObjectHelper", e.getMessage());
                e.printStackTrace();
            }
        }

        close();

        return objs;
    }

    public DatabaseObject getRowDataBy(Class<? extends DatabaseObject> cls, String whereQuery){
        List<DatabaseObject> list = getAllDataBy(cls, whereQuery, null, false);
        if(list!=null && list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public void printContentValues(ContentValues vals) {
        Set<Map.Entry<String, Object>> s = vals.valueSet();
        Iterator itr = s.iterator();

        Log.d(TAG, "ContentValue Length :: " + vals.size());

        while (itr.hasNext()) {
            Map.Entry me = (Map.Entry) itr.next();
            String key = me.getKey().toString();
            Object value = me.getValue();

            Log.d(TAG, "Key:" + key + ", values:" + String.valueOf(value));
        }
    }
}
