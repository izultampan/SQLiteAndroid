package com.abhimantrass.ormlearning.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.abhimantrass.ormlearning.utility.Factory;
import com.abhimantrass.ormlearning.view.LoginActivity;
import com.abhimantrass.ormlearning.view.SplashScreenActivity;

/**
 * Created by zul on 6/27/2016.
 */
public class SplashScreenHandler extends Handler {

    public static final int WHAT_FINISH_INITIATE = 0;
    public static final int DELAY_TIME = 2000;

    private Activity activity;

    public SplashScreenHandler(Activity activity) {
        this.activity = activity;
    }

    private SplashScreenActivity getActivity() {
        return (SplashScreenActivity) activity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case WHAT_FINISH_INITIATE:
                Intent intent = Factory.getInstance(getActivity()).createIntent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
