package se.dxtr.lister.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import se.dxtr.lister.R;

public class AddItemView {
    View view;
    public final Activity activity;
    public Button addItemButton;

    public AddItemView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout

        addItemButton = (Button) activity.findViewById(R.id.add_item_button);
    }

}
