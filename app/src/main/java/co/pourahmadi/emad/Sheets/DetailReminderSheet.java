package co.pourahmadi.emad.Sheets;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import co.pourahmadi.emad.App;
import co.pourahmadi.emad.Core.Statics.Constant;
import co.pourahmadi.emad.Core.Utils.UtilApp;
import co.pourahmadi.emad.Core.broadcast.NotificationPublisher;
import co.pourahmadi.emad.Models.AlarmList;
import co.pourahmadi.emad.Models.CalenderEvent;
import co.pourahmadi.emad.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailReminderSheet extends BaseSheet {

    @BindView(R.id.edtReminderTitle)
    EditText edtReminderTitle;
    @BindView(R.id.imgDeleted)
    ImageView imgDeleted;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.rbOnce)
    RadioButton rbOnce;
    @BindView(R.id.rbWeekly)
    RadioButton rbWeekly;


    private ButtonClickListener mListener;

    @BindString(R.string.error_fields_required)
    String strCheckFields;
    private GregorianCalendar gregorianCalendar;
    private AlarmList alertModel;
    private boolean repeatOnce=true;
    private Context context;

    public DetailReminderSheet (ButtonClickListener mListener) {
        this.mListener = mListener;
    }

    public static DetailReminderSheet newInstance (AlarmList model, ButtonClickListener mListener) {
        DetailReminderSheet needUpdateSheet = new DetailReminderSheet(mListener);
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARG_MODEL, Parcels.wrap(model));
        needUpdateSheet.setArguments(args);
        return needUpdateSheet;
    }


    @OnClick(R.id.txtDate)
    void txtDate () {
        BottomSheetDialogFragment bottomSheetDialog = new CalenderSheet();
        UtilApp.showSheet(context, bottomSheetDialog);
    }

    @OnClick(R.id.txtTime)
    void txtTime () {
        UtilApp.setTime(context, txtTime, gregorianCalendar);
    }

    @OnClick(R.id.btnCancel)
    void btnCancel () {
        try {
            dismiss();
        } catch (Exception ignored) {
        }
    }

    @OnClick(R.id.btnConfirm)
    void btnConfirm () {
        if (alertModel != null)
            onConfirm(true);
        else
            onConfirm(false);
    }

    @OnClick(R.id.imgDeleted)
    void imgDeleted () {
        try {
            onDeleted(alertModel.getId());
        } catch (Exception ignored) {
        }
    }


    @Override
    public int getLayoutId () {
        return R.layout.sheet_show_detail;
    }

    @Override
    public void beforeView () {
        (App.getINSTANCE()).getApplicationComponent().injectActivity(this);

        setCancelable(false);
        assert getArguments() != null;
       /* strMsg = getArguments().getString(Constant.ARG_STRING);
        strConfirmButton = getArguments().getString(Constant.ARG_STRING2);
*/
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void afterView () {
        gregorianCalendar = new GregorianCalendar();
        if (getArguments() != null) {
            alertModel = Parcels.unwrap(getArguments().getParcelable(Constant.ARG_MODEL));

            if (alertModel != null) {
                edtReminderTitle.setText(alertModel.getName());
                btnConfirm.setText(Objects.requireNonNull(context).getResources().getString(R.string.edit));
                txtTime.setText(alertModel.getHour() + " : " + alertModel.getMinute());
                txtDate.setText(alertModel.getPerDate() + "");
                imgDeleted.setVisibility(View.VISIBLE);
                repeatOnce = alertModel.isRepeatOnce();
                if (repeatOnce)
                    rbOnce.setChecked(true);
                else
                    rbWeekly.setChecked(true);

                gregorianCalendar.setTime(alertModel.getEngDate());
                gregorianCalendar.set(Calendar.HOUR_OF_DAY, alertModel.getHour());
                gregorianCalendar.set(Calendar.MINUTE, alertModel.getMinute());
                System.out.println("GET TIME:" + gregorianCalendar.getTime());

               /* CalendarTool ct = new CalendarTool(alertModel.getYear(), alertModel.getMonth(), alertModel.getDay_of_week());
                System.out.println(ct.getIranianDate());
*/

            }

        }

        App.bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event instanceof CalenderEvent) {
                        if (txtDate != null)
                            txtDate.setText(((CalenderEvent) event).getTitle());

                        gregorianCalendar = ((CalenderEvent) event).getEngDate();
                        gregorianCalendar.setTime(gregorianCalendar.getTime());

                    }
                });
        radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbOnce:
                    repeatOnce = true;
                    break;
                case R.id.rbWeekly:
                    repeatOnce = false;
                    break;
            }
        });
    }

    @Override
    public void onAttachInterface (Context context) {
        this.context = context;
    }

    @Override
    public void destroyView () {
        mListener = null;
    }

    private void onDeleted (int Id) {
        mListener.ononDeletedClick(Id);
        dismiss();
    }

    private void onConfirm (boolean isEditMode) {


        if (!TextUtils.isEmpty(txtDate.getText().toString()) &&
                !TextUtils.isEmpty(txtTime.getText().toString()) &&
                !TextUtils.isEmpty(edtReminderTitle.getText().toString())) {

            gregorianCalendar.set(Calendar.HOUR_OF_DAY, gregorianCalendar.get(Calendar.HOUR_OF_DAY));
            gregorianCalendar.set(Calendar.MINUTE, gregorianCalendar.get(Calendar.MINUTE));

            if (!repeatOnce)
                gregorianCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY, Calendar.MONDAY,Calendar.SATURDAY,Calendar.TUESDAY, Calendar.THURSDAY);


            System.out.println("Year:" + gregorianCalendar.get(Calendar.YEAR));
            System.out.println("Month:" + (gregorianCalendar.get(Calendar.MONTH) + 1));
            System.out.println("Day:" + gregorianCalendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("Hour:" + gregorianCalendar.get(Calendar.HOUR_OF_DAY));
            System.out.println("Minute:" + gregorianCalendar.get(Calendar.MINUTE));
            System.out.println("TimeCalender:" + gregorianCalendar.getTime());
            startAlarm();
            if (isEditMode)
                mListener.onConfirmEditClick(alertModel.getId(), edtReminderTitle.getText().toString(), repeatOnce, gregorianCalendar.getTime(),txtDate.getText().toString(), gregorianCalendar);
            else
                mListener.onConfirmClick(edtReminderTitle.getText().toString(), repeatOnce, gregorianCalendar.getTime(),txtDate.getText().toString(), gregorianCalendar);


            dismiss();
        } else
            Toast.makeText(context, strCheckFields + "", Toast.LENGTH_SHORT).show();
    }


    public interface ButtonClickListener {
        void onConfirmClick (String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar);

        void onConfirmEditClick (int Id, String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar);

        void ononDeletedClick (int Id);
    }

    private void startAlarm () {

        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(context).getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationPublisher.class);
        Bundle b = new Bundle();
        b.putString(Constant.ARG_STRING, edtReminderTitle.getText().toString());
        b.putString(Constant.ARG_STRING2, txtDate.getText().toString() + " - " + txtTime.getText().toString());
        intent.putExtras(b);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pendingIntent);
        }

        if (!repeatOnce)
            alarmManager.setRepeating(AlarmManager.RTC, gregorianCalendar.getTimeInMillis(), 7 * 24 * 3600 * 1000, pendingIntent);



    }
}