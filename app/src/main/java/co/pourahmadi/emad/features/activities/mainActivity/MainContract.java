package co.pourahmadi.emad.features.activities.mainActivity;

import co.pourahmadi.emad.Core.BaseObservable.BasePresenter;
import co.pourahmadi.emad.Core.BaseObservable.BaseView;

interface MainContract {

    interface View extends BaseView <Presenter> {


        void showToast (String txt);

        void showErorrResp ();

        void showLoading ();

        void hideLoading ();
    }

    interface Presenter extends BasePresenter {

    }

}
