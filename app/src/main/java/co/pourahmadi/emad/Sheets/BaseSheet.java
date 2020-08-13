package co.pourahmadi.emad.Sheets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.pourahmadi.emad.App;
import co.pourahmadi.emad.R;

public abstract class BaseSheet extends BottomSheetDialogFragment {

    protected boolean isKeyboard = false;
    private Unbinder unbinder;

    @Override
    public int getTheme () {
        return R.style.BottomSheetDialogTheme;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogs -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogs;
            FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
            assert bottomSheet != null;
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
/*
        if (dialog.getWindow() != null)
            if (this.isKeyboard)
                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            else dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
*/
        return dialog;
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (App.getINSTANCE()).getApplicationComponent().injectActivity(this);
        beforeView();
    }

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ViewCompat.setLayoutDirection(view, ViewCompat.LAYOUT_DIRECTION_LTR);
        this.unbinder = ButterKnife.bind(this, view);
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
    public void onAttach (@NonNull Activity context) {
        super.onAttach(context);
        onAttachInterface(context);
    }

    public abstract
    @LayoutRes
    int getLayoutId ();

    public abstract void beforeView ();

    public abstract void afterView ();

    public abstract void onAttachInterface (Context context);

    protected abstract void destroyView ();
}
