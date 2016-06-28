package com.abhimantrass.ormlearning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuhenk on 3/7/2015.
 */
public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseSQLiteHelper";

    private List<Class<? extends DatabaseObject>> classes;

    public DatabaseSQLiteHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e(TAG, "call db oncreate");

        try {
            List<String> createQueries = getCreateQueries();
            for(String str : createQueries) {
                sqLiteDatabase.execSQL(str);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        try {
            List<String> dropQueries = getDropQueries();
            for(String str : dropQueries) {
                sqLiteDatabase.execSQL(str);
            }
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e.getMessage());
        }
    }

    public void setTables(List<Class<? extends DatabaseObject>> classes){
        this.classes = classes;
    }

    public List<String> getCreateQueries() throws Exception {
        if(classes == null)
            throw new Exception("Accepted classes have not been initialized");
        List<String> sqls = new ArrayList<String>();
        if(classes != null) {
            for(Class<? extends DatabaseObject> c : classes) {
                String str = createSqlCreate(c);
                //Log.d(TAG, "Create QUERY = " + str);
                sqls.add(str);
            }
            return sqls;
        }

        return null;
    }
    public List<String> getDropQueries() throws Exception {
        if(classes == null)
            throw new Exception("Accepted classes have not been initialized");
        List<String> sqls = new ArrayList<String>();
        if(classes != null) {
            for(Class<? extends DatabaseObject> c : classes) {
                String str = createSqlDrop(c);
                sqls.add(str);
            }
            return sqls;
        }
        return null;
    }

    public String createSqlCreate(Class<? extends DatabaseObject> cls) {

        String baseQuery = "CREATE TABLE " + cls.getSimpleName() + "( {columns} )";

        StringBuilder columns = new StringBuilder();

        Field[] fields = cls.getDeclaredFields();

        for(Field f : fields) {
            f.setAccessible(true);
            DatabaseColumn columnCache = f.getAnnotation(DatabaseColumn.class);
            try{
                if(columnCache != null){
                    if(!columnCache.isIgnore()) {
                        String sname = columnCache.columnName() + " " + mapType(f) + " ";
                        if (columnCache.isPrimaryKey()) {
                            sname = sname + " PRIMARY KEY ";
                        }
                        if (columnCache.autoincrement()) {
                            sname = sname + " AUTOINCREMENT ";
                        }
                        if (columnCache.isNotNull()) {
                            sname = sname + " NOT NULL ";
                        }
                        if (columnCache.isUnique()) {
                            sname = sname + " UNIQUE ";
                        }

                        sname = sname + ",";
                        columns.append(sname);
                    } else {
                        continue;
                    }
                } else {
                    String temp = f.getName() + " " + mapType(f) + " ,";
                    columns.append(temp);
                }
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                continue;
            }
        }

        if(columns.length() > 1) {
            String result = columns.substring(0, columns.length() - 1);
            baseQuery = baseQuery.replace("{columns}", result);
            Log.d(TAG, "Create query --->> " + baseQuery);
            return baseQuery;
        } else {
            return null;
        }
    }

    public String createSqlDrop(Class<? extends DatabaseObject> cls) {
        return "DROP TABLE IF EXISTS " + cls.getSimpleName();
    }

    private String mapType(Field f) throws Exception {
        if(f.getType().equals(Integer.class) || f.getType().equals(Long.class) || f.getType().equals(int.class) || f.getType().equals(long.class)) {
            return "integer";
        } else if(f.getType().equals(Float.class) || f.getType().equals(Double.class)) {
            return "real";
        } else if(f.getType().equals(String.class)) {
            return "text";
        } else
            throw new Exception("Unsupported field format " + f.getType());
    }
}
