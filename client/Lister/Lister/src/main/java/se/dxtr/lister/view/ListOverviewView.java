package se.dxtr.lister.view;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import se.dxtr.lister.R;
import se.dxtr.lister.model.ListItem;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.model.TodoList;

public class ListOverviewView {
    View view;
    ListerModel model;
    public final Activity activity;

    public ListOverviewView(View view, ListerModel model, final Activity activity) {

        // store in the class the reference to the Android View
        this.view = view;
        this.activity = activity;
        this.model = model;

        // Setup the rest of the view layout
        List<TodoList> todoLists = model.getTodoLists();
        LinearLayout overviewContainer = (LinearLayout) activity.findViewById(R.id.overview_container);
        for (TodoList todoList : todoLists) {
            LinearLayout overviewElement = (LinearLayout) View.inflate(activity.getBaseContext(),
                    R.layout.list_overview_element, null);
            TextView listTitle = (TextView) overviewElement.findViewById(R.id.list_title);
            listTitle.setText(todoList.getTitle());
            overviewContainer.addView(overviewElement);
            for (ListItem listItem: todoList.getListItems()) {
                CheckBox checkBox = (CheckBox) View.inflate(activity.getBaseContext(),
                        R.layout.list_element, null);
                checkBox.setText(listItem.getContent());
                overviewElement.addView(checkBox);
            }
        }

    }

}