package com.abhimantrass.ormlearning.controller;

import android.app.Activity;
import android.os.Handler;

import com.abhimantrass.ormlearning.handler.SplashScreenHandler;
import com.abhimantrass.ormlearning.view.SplashScreenActivity;

/**
 * Created by zul on 6/27/2016.
 */
public class SplashScreenController implements IController {

    private Activity activity;
    private Handler handler;

    public SplashScreenController(Activity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
    }

    private SplashScreenActivity getActivity() {
        return (SplashScreenActivity) activity;
    }

    public void initiate() {
        handler.sendEmptyMessageDelayed(SplashScreenHandler.WHAT_FINISH_INITIATE, SplashScreenHandler.DELAY_TIME);
    }
}
