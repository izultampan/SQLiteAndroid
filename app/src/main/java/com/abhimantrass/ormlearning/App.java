package com.abhimantrass.ormlearning;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abhimantrass.ormlearning.db.DatabaseConfig;
import com.abhimantrass.ormlearning.db.DatabaseObject;
import com.abhimantrass.ormlearning.db.DatabaseSQLiteHelper;
import com.abhimantrass.ormlearning.db.dao.AttendanceDao;
import com.abhimantrass.ormlearning.db.dao.DivisionDao;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;
import com.abhimantrass.ormlearning.db.manager.DivisionManager;
import com.abhimantrass.ormlearning.db.manager.EmployeeManager;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private App app;

    @Override
    public void onCreate() {
        super.onCreate();

        List<Class < ? extends DatabaseObject>> tables = new ArrayList<>();
        tables.add(DivisionDao.class);
        tables.add(EmployeeDao.class);
        tables.add(AttendanceDao.class);

        DatabaseSQLiteHelper sqlHelper = new DatabaseSQLiteHelper(getApplicationContext(), DatabaseConfig.NAME, DatabaseConfig.VERSION);
        sqlHelper.setTables(tables);
        sqlHelper.getWritableDatabase();
        sqlHelper.close();

        if (!DivisionManager.getInstance(getApplicationContext(), DatabaseConfig.NAME, DatabaseConfig.VERSION).isDivisionExists()) {
            DivisionManager.getInstance(getApplicationContext(), DatabaseConfig.NAME, DatabaseConfig.VERSION).generateDivision();
        }

        if (!EmployeeManager.getInstance(getApplicationContext(), DatabaseConfig.NAME, DatabaseConfig.VERSION).isEmployeeExists()) {
            EmployeeManager.getInstance(getApplicationContext(), DatabaseConfig.NAME, DatabaseConfig.VERSION).generateEmployee();
        }
    }

    public App getApp() {
        return app;
    }
}
