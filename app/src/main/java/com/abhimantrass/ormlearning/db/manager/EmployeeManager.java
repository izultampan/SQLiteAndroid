package com.abhimantrass.ormlearning.db.manager;

import android.content.Context;

import com.abhimantrass.ormlearning.db.DatabaseObjectHelper;
import com.abhimantrass.ormlearning.db.DatabaseSQLiteHelper;
import com.abhimantrass.ormlearning.db.dao.DivisionDao;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;

/**
 * Created by zul on 6/27/2016.
 */
public class EmployeeManager implements DBManager {

    private static EmployeeManager instance;

    private DatabaseSQLiteHelper sqlHelper;
    private DatabaseObjectHelper dbHelper;

    private EmployeeManager(Context context, String dbName, int dbVersion) {
        sqlHelper = new DatabaseSQLiteHelper(context, dbName, dbVersion);
        dbHelper = new DatabaseObjectHelper(sqlHelper);
    }

    public static EmployeeManager getInstance(Context context, String dbName, int dbVersion) {
        if (instance == null) {
            instance = new EmployeeManager(context, dbName, dbVersion);
        }

        return instance;
    }

    public boolean isEmployeeExists() {
        return dbHelper.getAllData(EmployeeDao.class).size() > 0;
    }

    public void generateEmployee() {
        EmployeeDao dao = new EmployeeDao();
        dao.divId = DivisionManager.MOBILE_DIVISION_ID;
        dao.empName = "Bacharudin";
        dao.empAddress = "Jakarta";
        dao.empUsername = "udin";
        dao.empPassword = "123";
        dbHelper.saveObject(dao);

        dao = new EmployeeDao();
        dao.divId = DivisionManager.MOBILE_DIVISION_ID;
        dao.empName = "Chandra";
        dao.empAddress = "Jakarta";
        dao.empUsername = "chandra";
        dao.empPassword = "123";
        dbHelper.saveObject(dao);

        dao = new EmployeeDao();
        dao.divId = DivisionManager.GIS_DIVISION_ID;
        dao.empName = "Demian";
        dao.empAddress = "Jakarta";
        dao.empUsername = "demi";
        dao.empPassword = "123";
        dbHelper.saveObject(dao);

        dao = new EmployeeDao();
        dao.divId = DivisionManager.GIS_DIVISION_ID;
        dao.empName = "Rio";
        dao.empAddress = "Jakarta";
        dao.empUsername = "rio";
        dao.empPassword = "123";
        dbHelper.saveObject(dao);

        dao = new EmployeeDao();
        dao.divId = DivisionManager.DATABASE_DIVISION_ID;
        dao.empName = "Tengku";
        dao.empAddress = "Jakarta";
        dao.empUsername = "tengku";
        dao.empPassword = "123";
        dbHelper.saveObject(dao);
    }

//    DivisionDao div = new DivisionDao();
//    div.divId = "DIV0001";
//    div.divName = "mobile";
//    div.divDesc = "mobile division";
//    dbHelper.saveObject(div);
//
//    div = new DivisionDao();
//    div.divId = "DIV0002";
//    div.divName = "gis";
//    div.divDesc = "gis division";
//    dbHelper.saveObject(div);
//
//    div = new DivisionDao();
//    div.divId = "DIV0003";
//    div.divName = "database";
//    div.divDesc = "database division";
//    dbHelper.saveObject(div);
}
