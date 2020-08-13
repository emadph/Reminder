package co.pourahmadi.emad.features.fragments.home;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.pourahmadi.emad.Core.BaseObservable.BasePresenter;
import co.pourahmadi.emad.Core.BaseObservable.BaseView;
import co.pourahmadi.emad.Models.AlarmList;
import io.reactivex.Observable;

interface HomeContract {

    interface View extends BaseView <Presenter> {

        void showToast (String txt);

        void getAlarmList(List <AlarmList> list);
        void showLoading ();

        void hideLoading ();



    }
    interface Model {
        void updateAlarm (int Id, String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar);
        void deletAlarm (int Id);
        void addAlarm (String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar);
        Observable <List <AlarmList>> getAlarmList();
    }
    interface Presenter extends BasePresenter {
        void updateAlarm(int Id, String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar,String succesMsg);
        void addAlarm(String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar,String succesMsg);
        void deletAlarm(int Id,String succesMsg);
        void getAlarmList();

    }


}
