import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class Myapplication extends MultiDexApplication {
    public Myapplication() {
        super();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
