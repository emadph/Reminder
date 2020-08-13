package co.pourahmadi.emad.Sheets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.pourahmadi.emad.App;
import co.pourahmadi.emad.Models.CalenderEvent;
import co.pourahmadi.emad.R;
import ir.smartlab.persiandatepicker.PersianDatePicker;
import ir.smartlab.persiandatepicker.util.PersianCalendar;

public class CalenderSheet extends BottomSheetDialogFragment {


    @BindView(R.id.persianDatePicker)
    PersianDatePicker persianDatePicker;
    @BindView(R.id.date)
    TextView dateText;
    @BindColor(R.color.colorPrimary)
    int ProgressKeepColor;
    @BindView(R.id.submit)
    Button Submit;
    private BottomSheetBehavior mBehavior;
    private Unbinder unbinder;
    private PersianCalendar persianCalendar1;

    @OnClick(R.id.submit)
    public void Submit () {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(persianCalendar1.getTime());

        Log.d("Submit", ": "+gregorianCalendar.getTime());
        App.bus()
                .send(new CalenderEvent(dateText.getText().toString(), gregorianCalendar));
        dismiss();
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getTheme () {
        return R.style.BottomSheetDialogTheme;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.sheet_calender, null);

        unbinder = ButterKnife.bind(this, view);
        persianCalendar1 = new PersianCalendar();

        String date = persianDatePicker.getDisplayPersianDate().getPersianYear() + "/" +
                persianDatePicker.getDisplayPersianDate().getPersianMonth() + "/" +
                persianDatePicker.getDisplayPersianDate().getPersianDay();


        persianCalendar1.setPersianDate(persianDatePicker.getDisplayPersianDate().getPersianYear(),
                persianDatePicker.getDisplayPersianDate().getPersianMonth(),
                persianDatePicker.getDisplayPersianDate().getPersianDay());
        dateText.setText(date);

        persianDatePicker.setOnDateChangedListener((newYear, newMonth, newDay) -> {
            persianCalendar1.setPersianDate(persianDatePicker.getDisplayPersianDate().getPersianYear(),
                    persianDatePicker.getDisplayPersianDate().getPersianMonth(),
                    persianDatePicker.getDisplayPersianDate().getPersianDay());

            dateText.setText(newYear + "/" +
                    newMonth + "/" +
                    newDay);

        });
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart () {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}