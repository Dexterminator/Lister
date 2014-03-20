package se.dxtr.lister.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import se.dxtr.lister.R;
import se.dxtr.lister.controller.ListOverviewViewController;

public class ListDetailsView {
    View view;
    public final Activity activity;
    public CheckBox listElement1;
    public Button shareButton;
    int id;

    public ListDetailsView(View view, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        Intent intent = activity.getIntent();
        id = intent.getIntExtra(ListOverviewViewController.LIST_ID, 0);
        Log.d("Test detailview id", String.valueOf(id));

        // Setup the rest of the view layout
        listElement1 = (CheckBox) activity.findViewById(R.id.list_element_1);
        shareButton = (Button) activity.findViewById(R.id.share_button);
    }

}
