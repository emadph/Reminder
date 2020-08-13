package co.pourahmadi.emad.features.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {


    protected FragmentNavigationAndTitle mFragmentNavigation;
    private Unbinder unbinder;

    @Override
    public void onAttach (Activity activity) {
        getContexts(activity);
        if (activity instanceof FragmentNavigationAndTitle)
            this.mFragmentNavigation = (FragmentNavigationAndTitle) activity;
        super.onAttach(activity);
    }


    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeView();
    }

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_LTR);
        this.unbinder = ButterKnife.bind(this, view);
        pushTitle();
        return view;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterView();
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        destroyView();
        this.unbinder.unbind();
    }

    @Override
    public void onHiddenChanged (boolean hidden) {
        super.onHiddenChanged(hidden);
        pushTitle();
    }

    public abstract
    @LayoutRes
    int getLayoutId ();

    public abstract FragmentActivity getContexts (Activity activity);

    public abstract String titleView ();

    public abstract boolean canPressBack ();

    public abstract void beforeView ();

    public abstract void afterView ();

    public abstract void destroyView ();

    private void pushTitle () {
        try {
            this.mFragmentNavigation.pushTitle(titleView(), canPressBack());
        } catch (Exception ignored) {
        }
    }

    public interface FragmentNavigationAndTitle {

        void pushFragment (Fragment fragment);

        void pushTitle (String title, boolean canBack);
    }


}