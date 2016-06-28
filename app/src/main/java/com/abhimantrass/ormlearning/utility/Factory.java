package com.abhimantrass.ormlearning.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.abhimantrass.ormlearning.controller.HistoryController;
import com.abhimantrass.ormlearning.controller.IController;
import com.abhimantrass.ormlearning.controller.LoginController;
import com.abhimantrass.ormlearning.controller.SplashScreenController;
import com.abhimantrass.ormlearning.handler.HistoryHandler;
import com.abhimantrass.ormlearning.handler.LoginHandler;
import com.abhimantrass.ormlearning.handler.SplashScreenHandler;
import com.abhimantrass.ormlearning.view.LoginActivity;

/**
 * Created by zul on 6/27/2016.
 */
public class Factory {

    private static Factory instance;
    private static Context ctx;

    private Factory() {

    }

    public static Factory getInstance(Context context) {
        if (instance == null) {
            instance = new Factory();
        }

        ctx = context;
        return instance;
    }

    public IController createController(Class<?> clazz, Activity activity, Handler handler) {
        if (clazz.equals(SplashScreenController.class)) {
            return new SplashScreenController(activity, handler);
        }
        else if (clazz.equals(LoginController.class)) {
            return new LoginController(activity, handler);
        }
        else if (clazz.equals(HistoryController.class)) {
            return new HistoryController(activity, handler);
        }
        else {
            return null;
        }
    }

    public Handler createHandler(Class< ? extends Handler> clazz, Activity activity) {
        if (clazz.equals(SplashScreenHandler.class)) {
            return new SplashScreenHandler(activity);
        }
        else if (clazz.equals(LoginHandler.class)) {
            return new LoginHandler(activity);
        }
        else if (clazz.equals(HistoryHandler.class)) {
            return new HistoryHandler(activity);
        }
        else {
            return null;
        }
    }

    public Intent createIntent(Context context, Class< ? extends Activity> activity) {
        return new Intent(context, activity);
    }
}
