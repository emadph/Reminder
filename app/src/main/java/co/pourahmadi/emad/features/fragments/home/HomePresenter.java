package co.pourahmadi.emad.features.fragments.home;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.pourahmadi.emad.Core.BaseObservable.BaseObserver;
import co.pourahmadi.emad.Models.AlarmList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Emad on 29/07/2018.
 */

public class HomePresenter implements HomeContract.Presenter {

    private final CompositeDisposable compositeDisposable;
    private final HomeContract.View view;
    private final HomeContract.Model model;

    public HomePresenter (HomeContract.View _mainView, HomeContract.Model model) {
        this.view = _mainView;
        this.model = model;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe () {

    }

    @Override
    public void unsubscribe () {
        compositeDisposable.clear();
    }

    @Override
    public void updateAlarm (int Id, String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar,String succesMsg) {
        model.updateAlarm(Id,title, repeatOnce, engDate, perDate, calendar);
        view.showToast(succesMsg);
        getAlarmList();
    }

    @Override
    public void addAlarm (String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar,String succesMsg) {
        model.addAlarm(title, repeatOnce, engDate, perDate, calendar);
        view.showToast(succesMsg);
        getAlarmList();
    }

    @Override
    public void deletAlarm (int Id, String succesMsg) {
        model.deletAlarm(Id);
        view.showToast(succesMsg);
        getAlarmList();
    }

    @Override
    public void getAlarmList () {
        model.getAlarmList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver <List <AlarmList>>() {
                    @Override
                    public void onSubscribe (Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    protected void onRespSuccess (List <AlarmList> alarmLists) {
                        view.getAlarmList(alarmLists);
                    }

                    @Override
                    protected void onRespError (Throwable e) {
                        view.showToast(e.getMessage() + "");
                    }

                    @Override
                    protected void showLoading () {
                        view.showLoading();
                    }

                    @Override
                    protected void hideLoading () {
                        view.hideLoading();
                    }
                });


    }
}
