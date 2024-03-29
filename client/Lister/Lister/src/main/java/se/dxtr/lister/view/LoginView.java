package se.dxtr.lister.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.dxtr.lister.R;

public class LoginView {
    View view;
    public final Activity activity;
    public Button loginButton;
    public TextView createAccountLink;

    public LoginView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout

        loginButton = (Button) activity.findViewById(R.id.login_button);
        createAccountLink = (TextView) activity.findViewById(R.id.create_account);
    }

}
