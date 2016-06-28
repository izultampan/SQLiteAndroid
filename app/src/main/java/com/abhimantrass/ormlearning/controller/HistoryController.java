package com.abhimantrass.ormlearning.controller;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.abhimantrass.ormlearning.R;
import com.abhimantrass.ormlearning.db.DatabaseConfig;
import com.abhimantrass.ormlearning.db.dao.AttendanceDao;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;
import com.abhimantrass.ormlearning.db.manager.AttendanceManager;
import com.abhimantrass.ormlearning.handler.HistoryHandler;
import com.abhimantrass.ormlearning.view.HistoryActivity;

/**
 * Created by zul on 6/27/2016.
 */
public class HistoryController implements IController {

    private Activity activity;
    private Handler handler;
    private AttendanceManager manager;

    public HistoryController(Activity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
        manager = new AttendanceManager(activity, DatabaseConfig.NAME, DatabaseConfig.VERSION);
    }

    public HistoryActivity getActivity() {
        return (HistoryActivity) activity;
    }

    public void saveAttendance(int empId) {
        AttendanceDao dao = new AttendanceDao();
        dao.attendanceType = AttendanceManager.ATTENDANCE_TYPE_DEFAULT;
        dao.attendanceTime = String.valueOf(System.currentTimeMillis());
        dao.empId = empId;
        dao.remark = activity.getString(R.string.remark);
        manager.saveAttendance(dao);
    }

    public void getListOfAttendance(EmployeeDao employeeDao) {
        Message message = Message.obtain();
        message.what = HistoryHandler.WHAT_UPDATE_ADAPTER;
        message.obj = manager.getAttendanceByEmployeeId(employeeDao.empId);

        handler.sendMessage(message);
    }

    public void deleteAttendance(AttendanceDao dao) {
        manager.updateAttendance(dao);
    }

    public void updateAttendance(AttendanceDao dao) {
        manager.deleteAttendance(dao);
    }
}
