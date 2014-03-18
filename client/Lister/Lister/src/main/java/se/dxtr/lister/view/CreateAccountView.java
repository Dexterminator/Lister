package se.dxtr.lister.view;

import se.dxtr.lister.R;
import android.app.Activity;
import android.view.View;
import android.widget.Button;


public class CreateAccountView {
    View view;
    public final Activity activity;
    public Button createAccountButton;

    public CreateAccountView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout

        createAccountButton = (Button) activity.findViewById(R.id.create_account_button);
    }

}
