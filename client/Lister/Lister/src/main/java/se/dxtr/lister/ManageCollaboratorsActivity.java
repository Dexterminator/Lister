package se.dxtr.lister;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import se.dxtr.lister.controller.ManageCollaboratorsViewController;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.view.ManageCollaboratorsView;

public class ManageCollaboratorsActivity extends Activity {
    ManageCollaboratorsView manageCollaboratorsView;
    ManageCollaboratorsViewController manageCollaboratorsViewController;
    ListerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_collaborators);

        model = ((ListerApplication) this.getApplication()).getModel();

        manageCollaboratorsView = new ManageCollaboratorsView(findViewById(R.layout.activity_manage_collaborators), model, this);
        manageCollaboratorsViewController = new ManageCollaboratorsViewController(manageCollaboratorsView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_collaborators, menu);
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
