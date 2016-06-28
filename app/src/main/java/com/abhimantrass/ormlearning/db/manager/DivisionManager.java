package com.abhimantrass.ormlearning.db.manager;

import android.content.Context;

import com.abhimantrass.ormlearning.db.DatabaseObjectHelper;
import com.abhimantrass.ormlearning.db.DatabaseSQLiteHelper;
import com.abhimantrass.ormlearning.db.dao.DivisionDao;

/**
 * Created by zul on 6/27/2016.
 */
public class DivisionManager implements DBManager {

    public static final String MOBILE_DIVISION_ID   = "DIV0001";
    public static final String GIS_DIVISION_ID      = "DIV0002";
    public static final String DATABASE_DIVISION_ID = "DIV0003";

    private static DivisionManager instance;

    private DatabaseObjectHelper dbHelper;

    private DatabaseSQLiteHelper sqlHelper;

    private DivisionManager(Context context, String dbName, int dbVersion) {
        sqlHelper = new DatabaseSQLiteHelper(context, dbName, dbVersion);
        dbHelper = new DatabaseObjectHelper(sqlHelper);
    }

    public static DivisionManager getInstance(Context context, String dbName, int dbVersion) {
        if (instance == null) {
            instance = new DivisionManager(context, dbName, dbVersion);
        }

        return instance;
    }

    public boolean isDivisionExists() {
        return dbHelper.getAllData(DivisionDao.class).size() > 0;
    }

    public void generateDivision() {
        DivisionDao div = new DivisionDao();
        div.divId = MOBILE_DIVISION_ID;
        div.divName = "mobile";
        div.divDesc = "mobile division";
        dbHelper.saveObject(div);

        div = new DivisionDao();
        div.divId = GIS_DIVISION_ID;
        div.divName = "gis";
        div.divDesc = "gis division";
        dbHelper.saveObject(div);

        div = new DivisionDao();
        div.divId = DATABASE_DIVISION_ID;
        div.divName = "database";
        div.divDesc = "database division";
        dbHelper.saveObject(div);
    }

    public void deleteAllDivision() {

    }
}
