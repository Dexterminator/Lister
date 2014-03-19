package se.dxtr.lister;

import android.app.Application;

import se.dxtr.lister.model.ListerModel;

/**
 * Created by Dexter on 2014-03-19.
 */
public class ListerApplication extends Application{

    private ListerModel model = new ListerModel();

    public ListerModel getModel() {
        return model;
    }

    public void setModel(ListerModel model) {
        this.model = model;
    }
}
