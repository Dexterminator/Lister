package se.dxtr.lister;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import se.dxtr.lister.controller.LoginViewController;
import se.dxtr.lister.model.ListerModel;
import se.dxtr.lister.view.LoginView;

public class MainActivity extends Activity {
    LoginView loginView;
    LoginViewController controller;
    ListerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ((ListerApplication) this.getApplication()).getModel();

        setContentView(R.layout.activity_main);

        // Creating the login instance
        loginView = new LoginView(findViewById(R.layout.login_view), this);
        controller = new LoginViewController(loginView, model);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
