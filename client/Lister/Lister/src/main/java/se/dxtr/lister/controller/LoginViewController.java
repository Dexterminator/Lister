package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import se.dxtr.lister.ListOverviewActivity;
import se.dxtr.lister.view.LoginView;

public class LoginViewController implements OnClickListener {
    LoginView view;

    public LoginViewController(LoginView view) {
        this.view = view;
        view.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(view.activity, ListOverviewActivity.class);
        view.activity.startActivity(intent);

    }

}

