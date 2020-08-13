package co.pourahmadi.emad.Injection;


import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import co.pourahmadi.emad.Core.saveData.RoomDatabase.AppDatabase;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, MyModule.class})
public interface MyComponent {


    AppDatabase DATABASE ();

    RequestManager requestManager ();
}