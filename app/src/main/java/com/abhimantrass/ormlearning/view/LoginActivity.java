package com.abhimantrass.ormlearning.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.abhimantrass.ormlearning.R;
import com.abhimantrass.ormlearning.controller.LoginController;
import com.abhimantrass.ormlearning.handler.LoginHandler;
import com.abhimantrass.ormlearning.utility.Factory;

/**
 * Created by zul on 6/27/2016.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LoginController controller;
    private EditText txtUserName;
    private EditText txtPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setHandler((LoginHandler) Factory.getInstance(this).createHandler(LoginHandler.class, this));
        controller = (LoginController) Factory.getInstance(this).createController(LoginController.class, this, getHandler());

        txtUserName = (EditText) findViewById(R.id.txt_username);
        txtPassword = (EditText) findViewById(R.id.txt_password);

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                controller.executeLogin(txtUserName.getText().toString(), txtPassword.getText().toString());
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
