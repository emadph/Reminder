package co.pourahmadi.emad.features.activities.BaseActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean haveEventBus = false;
    String TAG = BaseActivity.class.getSimpleName();
    private BroadcastReceiver mNetworkReceiver;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (this.haveEventBus)
            EventBus.getDefault().register(this);
        View view = getLayoutInflater().inflate(getLayoutId(), null);
        ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_LTR);
        setContentView(view);
        ButterKnife.bind(this);
        afterView(savedInstanceState);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        try {
            destroyView();
            if (this.haveEventBus)
                EventBus.getDefault().unregister(this);
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void attachBaseContext (Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public abstract
    @LayoutRes
    int getLayoutId ();

    public abstract void beforeView ();

    public abstract void afterView (Bundle savedInstanceState);

    public abstract void destroyView ();


}
