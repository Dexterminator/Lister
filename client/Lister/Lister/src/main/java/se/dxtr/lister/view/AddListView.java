package se.dxtr.lister.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import se.dxtr.lister.R;

public class AddListView {
    View view;
    public final Activity activity;
    public Button addListButton;

    public AddListView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout

        addListButton = (Button) activity.findViewById(R.id.add_list_button);
    }

}
