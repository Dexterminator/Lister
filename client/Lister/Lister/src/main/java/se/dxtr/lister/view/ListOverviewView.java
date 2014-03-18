package se.dxtr.lister.view;

import se.dxtr.lister.R;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.CheckBox;


public class ListOverviewView {
    View view;
    public final Activity activity;
    public LinearLayout overviewElementId;

    public ListOverviewView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;

        // Setup the rest of the view layout

        overviewElementId = (LinearLayout) activity.findViewById(R.id.overview_element_id);
    }

}