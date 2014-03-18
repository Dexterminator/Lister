package se.dxtr.lister;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import se.dxtr.lister.controller.ListOverviewViewController;
import se.dxtr.lister.view.ListOverviewView;

public class ListOverviewActivity extends Activity {
    ListOverviewView listOverviewView;
    ListOverviewViewController listOverviewViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_overview);

        // Creating the list overview instance
        listOverviewView = new ListOverviewView(findViewById(R.layout.activity_list_overview), this);
        listOverviewViewController = new ListOverviewViewController(listOverviewView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_overview, menu);
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
