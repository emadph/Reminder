package co.pourahmadi.emad.features.activities.splashActivity;

import android.os.Bundle;
import android.os.Handler;

import co.pourahmadi.emad.Core.PageController.Intents;
import co.pourahmadi.emad.R;
import co.pourahmadi.emad.features.activities.BaseActivity.BaseActivity;
import co.pourahmadi.emad.features.activities.mainActivity.MainActivity;

public class SplashActivity extends BaseActivity {

    private SplashActivity context;

    @Override
    public int getLayoutId () {
        return R.layout.activity_splash;
    }

    @Override
    public void beforeView () {
        context = SplashActivity.this;
    }

    @Override
    public void afterView (Bundle savedInstanceState) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intents.startActivity(context, MainActivity.class, true);
        }, 250);

    }

    @Override
    public void destroyView () {

    }
}