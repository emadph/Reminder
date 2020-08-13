package co.pourahmadi.emad;

import android.annotation.SuppressLint;
import android.app.Application;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import co.pourahmadi.emad.Core.saveData.RoomDatabase.AppDatabase;
import co.pourahmadi.emad.Injection.AppModule;
import co.pourahmadi.emad.Injection.ApplicationComponent;
import co.pourahmadi.emad.Injection.DaggerApplicationComponent;
import co.pourahmadi.emad.Injection.DaggerMyComponent;
import co.pourahmadi.emad.Injection.MyComponent;
import co.pourahmadi.emad.Injection.MyModule;
import co.pourahmadi.emad.Models.RxBus;
import co.pourahmadi.emad.features.activities.BaseActivity.ForceCloseActivity;
import co.pourahmadi.emad.features.activities.mainActivity.MainActivity;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static App INSTANCE;
    private static RxBus bus;
    private ApplicationComponent applicationComponent;
    private AppDatabase database;

    public static RxBus bus () {
        return bus;
    }

    public static App getINSTANCE () {
        return INSTANCE;
    }


    @Override
    public void onCreate () {
        super.onCreate();
        INSTANCE = this;
        bus = new RxBus();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/IRANSansMobile_Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());


        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true) //default: true
                .showErrorDetails(false) //default: true
                .showRestartButton(true) //default: true
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(3000) //default: 3000
                .errorDrawable(R.drawable.error) //default: bug image
                .restartActivity(MainActivity.class) //default: null (your app's launch activity)
                .errorActivity(ForceCloseActivity.class) //default: null (default error activity)
                //.eventListener(new YourCustomEventListener()) //default: null
                .apply();

        MyComponent myComponent = DaggerMyComponent.builder()
                .appModule(new AppModule(this))
                .myModule(new MyModule())
                .build();

        applicationComponent =
                DaggerApplicationComponent.builder()
                        .myComponent(myComponent)
                        .build();

        database = myComponent.DATABASE();

    }

    public ApplicationComponent getApplicationComponent () {
        return applicationComponent;
    }

}
