/*
 * Copyright (c) 2017. Coding By Emad Pourahmadi
 * Contact me : 09354912598.
 * Email  : emad_pa_69@yahoo.com
 * WebSite : www.emadpourahmadi.ir
 */

package co.pourahmadi.emad.features.activities.BaseActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import co.pourahmadi.emad.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ForceCloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_close);

        // TextView errorDetailsText = findViewById(R.id.error_details);
        //  errorDetailsText.setText(CustomActivityOnCrash.getStackTraceFromIntent(getIntent()));

        Button restartButton = findViewById(R.id.force_close_back);

        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config == null) {
            //This should never happen - Just finish the activity to avoid a recursive crash.
            finish();
            return;
        }

        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            // restartButton.setText(R.string.restart_app);
            restartButton.setOnClickListener(v -> CustomActivityOnCrash.restartApplication(ForceCloseActivity.this, config));
        } else {
            restartButton.setOnClickListener(v -> CustomActivityOnCrash.closeApplication(ForceCloseActivity.this, config));
        }
    }

    @Override
    protected void attachBaseContext (Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
