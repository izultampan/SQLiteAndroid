package com.abhimantrass.ormlearning.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abhimantrass.ormlearning.controller.IController;

/**
 * Created by zul on 6/27/2016.
 */
public class BaseActivity extends AppCompatActivity implements IMvcTemplate{

    private Handler handler;
    private IController controller;

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public IController getController() {
        return controller;
    }
}
