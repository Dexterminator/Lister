package se.dxtr.lister.controller;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import se.dxtr.lister.ListOverviewActivity;
import se.dxtr.lister.view.CreateAccountView;

public class CreateAccountViewController implements OnClickListener {
    CreateAccountView view;

    public CreateAccountViewController(CreateAccountView view) {
        this.view = view;

        view.createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        intent = new Intent(view.activity, ListOverviewActivity.class);
        view.activity.startActivity(intent);
    }

}
