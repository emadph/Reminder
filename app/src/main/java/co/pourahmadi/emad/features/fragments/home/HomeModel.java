/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.pourahmadi.emad.features.fragments.home;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.pourahmadi.emad.App;
import co.pourahmadi.emad.Core.saveData.RoomDatabase.AppDatabase;
import co.pourahmadi.emad.Core.saveData.RoomDatabase.ProductsDao;
import co.pourahmadi.emad.Models.AlarmList;
import io.reactivex.Observable;


public class HomeModel implements HomeContract.Model {


    private static HomeModel INSTANCE = null;
    private static AppDatabase database;
    private static ProductsDao db;


    // Prevent direct instantiation.
    private HomeModel (){

    }
    public static HomeModel getInstance (AppDatabase _database) {
        if (INSTANCE == null) {
            database = _database;
            db = database.getDatabase(App.getINSTANCE()).Dao();
            INSTANCE = new HomeModel();
        }
        return INSTANCE;
    }


    public static void destroyInstance () {
        INSTANCE = null;
    }


    @Override
    public void updateAlarm (int Id, String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar) {
         db.updateAlarm(Id,
                title,
                engDate,
                perDate,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                repeatOnce
        );
    }

    @Override
    public void deletAlarm (int Id) {
        db.deleteById(Id);
    }

    @Override
    public void addAlarm (String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar) {
         db.insert(new AlarmList(
                        title,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        engDate,
                        perDate,
                        repeatOnce
                )
        );
    }

    @Override
    public Observable <List <AlarmList>> getAlarmList () {
        return db.getAll();
    }

}
