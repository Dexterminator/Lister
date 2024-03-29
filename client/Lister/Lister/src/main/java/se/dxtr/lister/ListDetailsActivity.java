package se.dxtr.lister;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import se.dxtr.lister.controller.ListDetailsViewController;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.view.ListDetailsView;

public class ListDetailsActivity extends Activity {
    ListDetailsView listDetailsView;
    ListDetailsViewController listDetailsViewController;
    ListerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        model = ((ListerApplication) this.getApplication()).getModel();

        // Creating the list details instance
        listDetailsView = new ListDetailsView(findViewById(R.layout.activity_list_details), model, this);
        listDetailsViewController = new ListDetailsViewController(listDetailsView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listDetailsView.update();
        listDetailsViewController.update();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
