package co.pourahmadi.emad.features.fragments.home;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import co.pourahmadi.emad.App;
import co.pourahmadi.emad.Core.CustomToast.Toasts;
import co.pourahmadi.emad.Core.Utils.UtilApp;
import co.pourahmadi.emad.Core.saveData.RoomDatabase.AppDatabase;
import co.pourahmadi.emad.Core.saveData.RoomDatabase.ProductsDao;
import co.pourahmadi.emad.Models.AlarmList;
import co.pourahmadi.emad.R;
import co.pourahmadi.emad.Sheets.DetailReminderSheet;
import co.pourahmadi.emad.adapter.HomeAdapter;
import co.pourahmadi.emad.features.fragments.BaseFragment;

public class HomeFragment extends BaseFragment implements HomeContract.View, DetailReminderSheet.ButtonClickListener, HomeAdapter.listener {
    @Inject
    AppDatabase database;

    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.rvReminder)
    RecyclerView rvReminder;
    HomeAdapter adapter;

    @BindString(R.string.addReminderTitle)
    String strApp;
    @BindString(R.string.deletedMsg)
    String strDeleted;
    @BindString(R.string.strSuccessAdd)
    String strSuccess;
    @BindString(R.string.strSuccessEdit)
    String strSuccessEdit;
    private HomeContract.Presenter presenter;
    private FragmentActivity context;
    private ProductsDao db;

    @OnClick(R.id.layoutHeader)
    void addReminder () {
        DetailReminderSheet dialogFragment = new DetailReminderSheet(this);
        UtilApp.showSheet(context, dialogFragment);
    }


    @Override
    public int getLayoutId () {
        return R.layout.fragment_home;
    }

    @Override
    public FragmentActivity getContexts (Activity activity) {
        return context = (FragmentActivity) activity;
    }

    @Override
    public String titleView () {
        return strApp;
    }

    @Override
    public boolean canPressBack () {
        return false;
    }

    @Override
    public void beforeView () {
        (App.getINSTANCE()).getApplicationComponent().injectActivity(this);
        presenter = new HomePresenter(this, HomeModel.getInstance(database));
    }

    @Override
    public void afterView () {
        initView();
    }

    @Override
    public void destroyView () {
        this.presenter.unsubscribe();
    }


    private void initView () {
        adapter = new HomeAdapter();
        adapter.setListener(this);
        rvReminder.setAdapter(adapter);


    }


    @Override
    public void showToast (String txt) {
        Toasts.makeToast(context, rvReminder, txt);
    }


    @Override
    public void getAlarmList (List <AlarmList> list) {
        adapter.setData(list);
    }

    @Override
    public void showLoading () {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading () {
        loading.setVisibility(View.GONE);
    }


    @Override
    public void setPresenter (HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onConfirmClick (String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar) {
        presenter.addAlarm(title, repeatOnce, engDate, perDate, calendar,strSuccess);
    }

    @Override
    public void onConfirmEditClick (int Id, String title, boolean repeatOnce, Date engDate, String perDate, Calendar calendar) {
        presenter.updateAlarm(Id, title, repeatOnce, engDate, perDate, calendar,strSuccessEdit);

    }

    @Override
    public void ononDeletedClick (int Id) {
        presenter.deletAlarm(Id, strDeleted);
    }

    @Override
    public void itemClick (AlarmList model) {

        DetailReminderSheet dialogFragment = DetailReminderSheet.newInstance(model, this);
        UtilApp.showSheet(context, dialogFragment);
    }

}
