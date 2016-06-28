package com.abhimantrass.ormlearning.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.abhimantrass.ormlearning.db.DatabaseConfig;
import com.abhimantrass.ormlearning.db.dao.EmployeeDao;
import com.abhimantrass.ormlearning.db.manager.LoginManager;
import com.abhimantrass.ormlearning.utility.Factory;
import com.abhimantrass.ormlearning.view.HistoryActivity;
import com.abhimantrass.ormlearning.view.LoginActivity;

/**
 * Created by zul on 6/27/2016.
 */
public class LoginController implements IController {

    public static final String KEY_EMPLOYEE = "key_employee";

    private Activity activity;
    private Handler handler;

    public LoginController(Activity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
    }

    private LoginActivity getActivity() {
        return (LoginActivity) activity;
    }

    public void executeLogin(String userName, String password) {
        LoginManager manager  = new LoginManager(activity, DatabaseConfig.NAME, DatabaseConfig.VERSION);
        EmployeeDao employee = manager.getEmployeeById(userName, password);

        if (employee != null) {
            Intent intent = Factory.getInstance(activity).createIntent(activity, HistoryActivity.class);
            intent.putExtra(KEY_EMPLOYEE, employee);
            activity.startActivity(intent);
        } else {
            // TODO send message employee not found
        }
    }
}
