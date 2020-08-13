package co.pourahmadi.emad.Core.BaseObservable;

import android.app.ProgressDialog;
import android.view.View;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/1/5 16:28
 */

public abstract class BaseObserver<T> implements Observer <T> {

    private View view;
    private ProgressDialog pLoading;

    public BaseObserver () {
        showLoading();
       /* pLoading = new ProgressDialog(context);
        //pLoading.setTitle(context.getString(R.string.get_data));
        pLoading.setMessage(context.getString(R.string.please_wait));
        pLoading.setIndeterminate(true);
        pLoading.setCancelable(false);
        pLoading.setCancelable(false);
        pLoading.setCanceledOnTouchOutside(false);
        pLoading.show();*/

/*         view = View.inflate(context, R.layout.fragment_order_sq_setting, null);

        RelativeLayout latest_info_container = (RelativeLayout)view.findViewById(R.id.viewgroup);
        latest_info_container.addView(view,0);
        latest_info_container.setEditBasketVisibility(View.VISIBLE);*/
    }

    @Override
    public abstract void onSubscribe (Disposable d);

    @Override
    public void onNext (T t) {

        hideLoading();
        onRespSuccess(t);

        //pLoading.dismiss();
        //  view.setEditBasketVisibility(View.GONE);
    }

    @Override
    public void onError (Throwable e) {
        //pLoading.dismiss();
        hideLoading();
        onRespError(e);

    }

    @Override
    public void onComplete () {
        //pLoading.dismiss();
        //hideLoading();
    }


    protected abstract void onRespSuccess (T t);

    protected abstract void onRespError (Throwable e);

    protected abstract void showLoading ();

    protected abstract void hideLoading ();
}
