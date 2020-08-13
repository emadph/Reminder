package co.pourahmadi.emad.features.activities.mainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.ncapdevi.fragnav.FragNavController;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import co.pourahmadi.emad.App;
import co.pourahmadi.emad.Core.CustomToast.Toasts;
import co.pourahmadi.emad.Core.Utils.UtilApp;
import co.pourahmadi.emad.R;
import co.pourahmadi.emad.features.activities.BaseActivity.BaseActivity;
import co.pourahmadi.emad.features.fragments.BaseFragment;
import co.pourahmadi.emad.features.fragments.home.HomeFragment;

public class MainActivity extends BaseActivity implements
        MainContract.View
        , BaseFragment.FragmentNavigationAndTitle,
        FragNavController.RootFragmentListener {
    private final static int numberTabs = 1;
    private final String TAG = MainActivity.class.getSimpleName();
    @Inject
    RequestManager glide;
    @BindView(R.id.toolbar_menu)
    ImageView imgBack;
    @BindView(R.id.toolbar_title)
    TextView txtToolbar;
    @BindView(R.id.include)
    View toolbarLayout;
    @BindString(R.string.double_backpres)
    String double_backpress;
    @BindString(R.string.app_name)
    String strAppName;
    @BindString(R.string.noHeader)
    String strNoHeader;
    private boolean doubleBackToExitPressedOnce = false;
    private MainActivity context;
    private MainContract.Presenter mainPresenter;
    private FragNavController fragNavController;

    @OnClick(R.id.toolbar_menu)
    void onbackClick () {
        onBackPressed();
    }

    /*===============================*/
    /*Function Android*/
    /*===============================*/
    @Override
    public int getLayoutId () {
        return R.layout.activity_main;
    }

    @Override
    public void beforeView () {
        ((App) getApplication()).getApplicationComponent().injectActivity(this);
        context = MainActivity.this;
        mainPresenter = new MainPresenter(this);
    }

    @Override
    public void afterView (Bundle savedInstanceState) {
        initView(savedInstanceState);
        initClicks();
    }

    @Override
    public void destroyView () {
    }

    @Override
    public void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.fragNavController.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed () {
        Log.d(TAG, "onBackPressed: ");
        if (doubleBackToExitPressedOnce) {
            UtilApp.exitApplication(context);
            return;
        }
        Toasts.makeToast(context, imgBack, this.double_backpress);

        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 3500);

    }

    @Override
    public void onResume () {
        super.onResume();
        mainPresenter.subscribe();

    }

    @Override
    public void onPause () {
        super.onPause();
        mainPresenter.unsubscribe();
    }


    private void initView (Bundle savedInstanceState) {
        txtToolbar.setText(strAppName);

        this.fragNavController = new FragNavController(getSupportFragmentManager(), R.id.container);
        this.fragNavController.setRootFragmentListener(this);
        this.fragNavController.setFragmentHideStrategy(FragNavController.HIDE);
        this.fragNavController.initialize(FragNavController.TAB1, savedInstanceState);

    }

    private void initClicks () {

    }


    @Override
    public void showToast (String txt) {
        Toasts.makeToast(context, imgBack, txt);
    }

    @Override
    public void showErorrResp () {
        Toasts.makeToast(context, imgBack, context.getResources().getString(R.string.serverErorr));
    }

    @Override
    public void showLoading () {
        // Loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading () {
        //  Loading.setVisibility(View.GONE);

    }

    @Override
    public void setPresenter (MainContract.Presenter _presenter) {
        mainPresenter = _presenter;
    }


    @Override
    public int getNumberOfRootFragments () {
        return numberTabs;
    }

    @Override
    public Fragment getRootFragment (int index) {
        switch (index) {
            case FragNavController.TAB1:
                return new HomeFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void pushFragment (Fragment fragment) {
        this.fragNavController.pushFragment(fragment);
    }

    @Override
    public void pushTitle (String title, boolean canBack) {
        toolbarLayout.setVisibility(!title.equals(strNoHeader) ? View.VISIBLE : View.GONE);
        imgBack.setVisibility(canBack ? View.INVISIBLE : View.GONE);

        if (title == null) {
            txtToolbar.setText(strAppName);
        } else {
            txtToolbar.setText(title);
        }

        if (canBack) {
            imgBack.setImageResource(R.drawable.back_front);
        }
    }
}
