package com.abhimantrass.ormlearning.view;

import android.os.Handler;

import com.abhimantrass.ormlearning.controller.IController;

/**
 * Created by zul on 6/27/2016.
 */
public interface IMvcTemplate  {

    void setHandler(Handler handler);
    Handler getHandler();

    void setController(IController controller);
    IController getController();
}
