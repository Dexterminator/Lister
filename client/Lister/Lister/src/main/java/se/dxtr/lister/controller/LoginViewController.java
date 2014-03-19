package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.CreateAccountActivity;
import se.dxtr.lister.ListOverviewActivity;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.view.LoginView;
import se.dxtr.lister.R;

public class LoginViewController implements OnClickListener {
    LoginView view;
    private ListerModel model;

    public LoginViewController(LoginView view, ListerModel model) {
        this.view = view;
        this.model = model;
        view.loginButton.setOnClickListener(this);
        view.createAccountLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.login_button:
                intent = new Intent(view.activity, ListOverviewActivity.class);
                model.testRequest();
//                view.activity.startActivity(intent);
                break;
            case R.id.create_account:
                intent = new Intent(view.activity, CreateAccountActivity.class);
                view.activity.startActivity(intent);
                break;
        }
    }

}

