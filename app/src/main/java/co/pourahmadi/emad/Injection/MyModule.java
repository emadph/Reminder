package co.pourahmadi.emad.Injection;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Singleton;

import androidx.room.Room;
import co.pourahmadi.emad.Core.saveData.RoomDatabase.AppDatabase;
import co.pourahmadi.emad.R;
import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {

    private static final int CONNECT_TIMEOUT_IN_MS = 60000;

    @Provides
    @Singleton
    RequestManager requestManager (Application application, RequestOptions requestOptions) {
        return Glide.with(application).applyDefaultRequestOptions(requestOptions);
    }

    @Provides
    @Singleton
    RequestOptions requestOptions () {
        return new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder);
    }


    @Provides
    @Singleton
    AppDatabase database (Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "NiazJoo").build();
    }

}