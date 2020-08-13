package co.pourahmadi.emad.Models;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Emad on 02/09/2018.
 */
// this is the middleman object
public class RxBus {


    private final PublishSubject <Object> bus = PublishSubject.create();

    public RxBus () {
    }

    public void send (Object o) {
        bus.onNext(o);
    }

    public Observable <Object> toObservable () {
        return bus;
    }
}