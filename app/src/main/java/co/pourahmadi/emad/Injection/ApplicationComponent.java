package co.pourahmadi.emad.Injection;

import co.pourahmadi.emad.Sheets.BaseSheet;
import co.pourahmadi.emad.Sheets.DetailReminderSheet;
import co.pourahmadi.emad.features.activities.mainActivity.MainActivity;
import co.pourahmadi.emad.features.fragments.home.HomeFragment;
import dagger.Component;

@MyScope
@Component(dependencies = MyComponent.class)
public interface ApplicationComponent {

    void injectActivity (MainActivity splashScreen);
    void injectActivity (HomeFragment splashScreen);
    void injectActivity (BaseSheet splashScreen);
    void injectActivity (DetailReminderSheet splashScreen);


}