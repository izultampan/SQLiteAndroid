package com.abhimantrass.ormlearning.utility;

import android.content.Context;

import com.abhimantrass.ormlearning.R;
import com.abhimantrass.ormlearning.db.manager.AttendanceManager;

/**
 * Created by zul on 6/28/2016.
 */
public class CommonUtil {

    public static String fromIdToAttendanceType(Context context, int type) {
        switch (type) {
            case AttendanceManager.ATTENDANCE_TYPE_ATTEND:
                return context.getString(R.string.attend);
            case AttendanceManager.ATTENDANCE_TYPE_LEAVE:
                return context.getString(R.string.leave);
            case AttendanceManager.ATTENDANCE_TYPE_SICK:
                return context.getString(R.string.sick);
            default:
                return context.getString(R.string.attendance_default);
        }
    }
}
