package se.dxtr.lister.view;

import se.dxtr.lister.R;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ListDetailsView {
    View view;
    public final Activity activity;
    public CheckBox listElement1;
    public Button shareButton;

    public ListDetailsView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout
        listElement1 = (CheckBox) activity.findViewById(R.id.list_element_1);
        shareButton = (Button) activity.findViewById(R.id.share_button);
    }

}
