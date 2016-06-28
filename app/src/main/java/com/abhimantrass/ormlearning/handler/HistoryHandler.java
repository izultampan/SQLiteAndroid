package com.abhimantrass.ormlearning.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.abhimantrass.ormlearning.db.dao.AttendanceDao;
import com.abhimantrass.ormlearning.view.HistoryActivity;

import java.util.List;

/**
 * Created by zul on 6/27/2016.
 */
public class HistoryHandler extends Handler {

    public static final int WHAT_UPDATE_ADAPTER = 0;
    private Activity activity;

    public HistoryHandler(Activity activity) {
        this.activity = activity;
    }

    private HistoryActivity getActivity() {
        return (HistoryActivity) activity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case WHAT_UPDATE_ADAPTER:
                getActivity().setDataList((List<AttendanceDao>) msg.obj);
                break;
        }
    }
}