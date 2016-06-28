package com.abhimantrass.ormlearning.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.abhimantrass.ormlearning.R;
import com.abhimantrass.ormlearning.controller.SplashScreenController;
import com.abhimantrass.ormlearning.handler.SplashScreenHandler;
import com.abhimantrass.ormlearning.utility.Factory;

/**
 * Created by zul on 6/27/2016.
 */
public class SplashScreenActivity extends BaseActivity {

    private SplashScreenController controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setHandler((SplashScreenHandler) Factory.getInstance(this).createHandler(SplashScreenHandler.class, this));
        controller = (SplashScreenController) Factory.getInstance(this).createController(SplashScreenController.class, this, getHandler());
        setController(controller);
        controller.initiate();
    }
}
