package se.dxtr.lister.controller;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import se.dxtr.lister.R;
import se.dxtr.lister.view.CreateAccountView;

public class CreateAccountViewController implements OnClickListener {
    CreateAccountView view;

    public CreateAccountViewController(CreateAccountView view) {
        this.view = view;

        view.createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText username = (EditText) view.activity.findViewById(R.id.created_username);
        EditText password = (EditText) view.activity.findViewById(R.id.created_password);

        view.activity.finish();
    }
//
//    private void createAccount(String username, String password) {
//        URL host = null;
//        InputStream is = null;
//        try {
//            host = new URL(view.activity.getString(R.string.host) + "get_lists/id="+uid);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }

}
