package se.dxtr.lister.view;

import se.dxtr.lister.R;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.model.TodoList;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class ListOverviewView {
    View view;
    ListerModel model;
    public final Activity activity;
    public LinearLayout overviewElement;

    public ListOverviewView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        this.model = model;

        // Setup the rest of the view layout
        List<TodoList> todoLists = model.getTodoLists();
        ScrollView overviewContainer = (ScrollView) activity.findViewById(R.id.overview_container);
        for (TodoList todoList : todoLists) {
            TextView listTitle = (TextView) activity.findViewById(R.id.list_title);
            listTitle.setText(todoList.getTitle());
        }


        overviewElement = (LinearLayout) activity.findViewById(R.id.overview_element_id);


    }

}