package com.abhimantrass.ormlearning.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.abhimantrass.ormlearning.view.LoginActivity;

/**
 * Created by zul on 6/27/2016.
 */
public class LoginHandler extends Handler {

    private Activity activity;

    public LoginHandler(Activity activity) {
        this.activity = activity;
    }

    public LoginActivity getActivity() {
        return (LoginActivity) activity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
