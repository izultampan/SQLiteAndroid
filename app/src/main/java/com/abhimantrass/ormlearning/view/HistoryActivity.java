package com.abhimantrass.ormlearning.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abhimantrass.ormlearning.R;
import com.abhimantrass.ormlearning.controller.HistoryController;
import com.abhimantrass.ormlearning.controller.LoginController;
import com.abhimantrass.ormlearning.db.dao.AttendanceDao;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;
import com.abhimantrass.ormlearning.handler.HistoryHandler;
import com.abhimantrass.ormlearning.utility.Factory;
import com.abhimantrass.ormlearning.view.adapter.HistoryListAdapter;

import java.util.List;

/**
 * Created by zul on 6/27/2016.
 */
public class HistoryActivity extends BaseActivity implements HistoryListAdapter.HistoryAdapterClickListener {

    private HistoryController controller;
    private RecyclerView recyclerView;

    private HistoryListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setHandler((HistoryHandler) Factory.getInstance(this).createHandler(HistoryHandler.class, this));
        controller = (HistoryController) Factory.getInstance(this).createController(HistoryController.class, this, getHandler());

        Bundle bundle = getIntent().getExtras();
        EmployeeDao dao = (EmployeeDao)  bundle.getSerializable(LoginController.KEY_EMPLOYEE);

        controller.saveAttendance(dao.empId);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryListAdapter();
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        controller.getListOfAttendance(dao);
    }

    public void setDataList(List<AttendanceDao> dataList) {
        adapter.setDataList(dataList, true);
    }

    @Override
    public void onClicked(int id, AttendanceDao attendance) {
        switch (id) {
            case R.id.btn_update:
                controller.updateAttendance(attendance);
                break;
            case R.id.btn_delete:
                controller.deleteAttendance(attendance);
                break;
        }
    }
}
