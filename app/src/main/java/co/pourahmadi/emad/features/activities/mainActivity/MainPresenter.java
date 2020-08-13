package co.pourahmadi.emad.features.activities.mainActivity;


import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter implements MainContract.Presenter {

    private final CompositeDisposable compositeDisposable;
    private final MainContract.View view;

    public MainPresenter (MainContract.View _mainView) {
        this.view = _mainView;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);

    }

    @Override
    public void subscribe () {

    }

    @Override
    public void unsubscribe () {
        //   compositeDisposable.clear();
    }

}
