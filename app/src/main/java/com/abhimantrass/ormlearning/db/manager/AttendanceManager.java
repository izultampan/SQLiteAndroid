package com.abhimantrass.ormlearning.db.manager;

import android.content.Context;
import android.util.Log;

import com.abhimantrass.ormlearning.db.DatabaseObjectHelper;
import com.abhimantrass.ormlearning.db.DatabaseSQLiteHelper;
import com.abhimantrass.ormlearning.db.dao.AttendanceDao;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zul on 6/27/2016.
 */
public class AttendanceManager implements DBManager {

    public static final int ATTENDANCE_TYPE_DEFAULT = 0;
    public static final int ATTENDANCE_TYPE_ATTEND = 1;
    public static final int ATTENDANCE_TYPE_LEAVE = 2;
    public static final int ATTENDANCE_TYPE_SICK = 3;

    private DatabaseSQLiteHelper sqlHelper;
    private DatabaseObjectHelper dbHelper;

    public AttendanceManager(Context context, String dbName, int dbVersion) {
        sqlHelper = new DatabaseSQLiteHelper(context, dbName, dbVersion);
        dbHelper = new DatabaseObjectHelper(sqlHelper);
    }

    public void saveAttendance(AttendanceDao dao) {
        long success = dbHelper.saveObject(dao);
        if (success < 0) {

        }
    }

    public List<AttendanceDao> getAttendanceByEmployeeId(int employeeId) {
        String where = "WHERE tbl_emp_id = " + employeeId;
        return (List<AttendanceDao>)(List<?>) dbHelper.getAllDataBy(AttendanceDao.class, where, null, false);
    }

    public void updateAttendance(AttendanceDao dao) {

    }

    public void deleteAttendance(AttendanceDao dao) {

    }
}