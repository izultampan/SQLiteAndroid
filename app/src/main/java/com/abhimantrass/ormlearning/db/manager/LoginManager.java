package com.abhimantrass.ormlearning.db.manager;

import android.content.Context;

import com.abhimantrass.ormlearning.db.DatabaseObjectHelper;
import com.abhimantrass.ormlearning.db.DatabaseSQLiteHelper;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;

/**
 * Created by zul on 6/27/2016.
 */
public class LoginManager implements DBManager {

    private DatabaseSQLiteHelper sqlHelper;
    private DatabaseObjectHelper dbHelper;

    public LoginManager(Context context, String dbName, int dbVersion) {
        sqlHelper = new DatabaseSQLiteHelper(context, dbName, dbVersion);
        dbHelper = new DatabaseObjectHelper(sqlHelper);
    }

    public EmployeeDao getEmployeeById(String userName, String password) {
        return (EmployeeDao) dbHelper.getRowDataBy(EmployeeDao.class, "WHERE tbl_emp_username = '" + userName + "' and " +
                "tbl_emp_password = '" + password + "'");
    }
}
