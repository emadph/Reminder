package co.pourahmadi.emad.Core.Utils;

/*
  Created by Emad on 10/03/2017.
 */

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import co.pourahmadi.emad.R;
import io.github.inflationx.calligraphy3.CalligraphyUtils;


public class UtilApp {

    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private final String TAG = butterknife.internal.Utils.class.getSimpleName();

    public static void showSheet (Context context, BottomSheetDialogFragment dialogFragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.add(dialogFragment, dialogFragment.getTag());
        transaction.commitAllowingStateLoss();
    }
    public static void setTime (Context context, TextView textView, GregorianCalendar gregorianCalendar) {
        Calendar mcurrentTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        try {
            mcurrentTime.setTime(sdf.parse(textView.getText().toString()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        @SuppressLint("SetTextI18n") TimePickerDialog.OnTimeSetListener mTimeSetListenerIni = (view, hourOfDay, minute1) -> {
            textView.setText(hourOfDay + ":" + minute1);

            gregorianCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            gregorianCalendar.set(Calendar.MINUTE,minute1);
        };
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(context, mTimeSetListenerIni,
                mcurrentTime.get(Calendar.HOUR_OF_DAY),
                mcurrentTime.get(Calendar.MINUTE), true);
        mTimePickerDialog.setTitle(context.getResources().getString(R.string.selecTimeNext));
        mTimePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.confirmTime), mTimePickerDialog);
        mTimePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.back), mTimePickerDialog);
        mTimePickerDialog.show();
    }


    @SuppressLint("DefaultLocale")
    public static String seprateNumber (String number) {
        String s = null;
        try {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,###.#");
            s = formatter.format(amount);
        } catch (NumberFormatException ignored) {
        }
        // Set s back to the view after temporarily removing the text change listener
        return s;
    }


    public static void hideKeyboard (View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null || !imm.isActive()) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ignored) {

        }
    }

    public static void ExitAnimation (Context context) {
        ((AppCompatActivity) context).finish();
        ((AppCompatActivity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void exitApplication (Activity activity) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(startMain);
        if (Build.VERSION.SDK_INT >= 21) {
            activity.finishAffinity();
        } else {
            ActivityCompat.finishAffinity(activity);
        }

        //   activity.finish();
        System.exit(0);
    }



    private static void trimCache (Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static boolean deleteDir (File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        assert dir != null;
        return dir.delete();
    }

    public static void launchGoogleMaps (Context context, double latitude, double longitude, String label) {
        String format = "google.navigation:q=" + Double.toString(latitude) + "," + Double.toString(longitude);
        Uri gmmIntentUri = Uri.parse(format);
   /*     Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=18.519513,73.868315&" +
                "destination=18.518496,73.879259&" +
                "waypoints=18.520561,73.872435|18.519254,73.876614|18.52152,73.877327|18.52019,73.879935&" +
                "travelmode=driving");*/
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context, "ّبرنامه نقشه یافت نشد !", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void launchGoogleMaps2 (Context context, double latitude, double longitude, String label) {
        String format = "geo:0,0?q=" + Double.toString(latitude) + "," + Double.toString(longitude) + "(" + label + ")";
        Uri uri = Uri.parse(format);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void getTotalHeightofRecyclerView (RecyclerView recyclerView) {

        RecyclerView.Adapter mAdapter = recyclerView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            View mView = recyclerView.findViewHolderForAdapterPosition(i).itemView;

            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
        }

        if (totalHeight > 100) {
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = 100;
            recyclerView.setLayoutParams(params);
        }
    }

    public static void changeTabFont (TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, "fonts/IRANSansMobile_Medium.ttf");
                }
            }
        }
    }

    public static void changeTabsFont (Context context, TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int k = 0; k < tabChildsCount; k++) {
                View tabViewChild = vgTab.getChildAt(k);
                if (tabViewChild instanceof TextView) {
                    Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Medium.ttf");
                    ((TextView) tabViewChild).setTypeface(face, Typeface.NORMAL);
                }
            }
        }
    }

    public static void applyBottomNavFont (Context context, BottomNavigationView navigationView) {
        // The BottomNavigationView widget doesn't provide a native way to set the appearance of
        // the text views. So we have to hack in to the view hierarchy here.
        for (int i = 0; i < navigationView.getChildCount(); i++) {
            View child = navigationView.getChildAt(i);
            if (child instanceof BottomNavigationMenuView) {
                BottomNavigationMenuView menu = (BottomNavigationMenuView) child;
                for (int j = 0; j < menu.getChildCount(); j++) {
                    View item = menu.getChildAt(j);
                    View smallItemText = item.findViewById(R.id.smallLabel);
                    if (smallItemText instanceof TextView) {
                        // Set font here
                        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Light.ttf");
                        ((TextView) smallItemText).setTypeface(face, Typeface.NORMAL);
                    }
                    View largeItemText = item.findViewById(R.id.largeLabel);
                    if (largeItemText instanceof TextView) {
                        // Set font here
                        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Light.ttf");
                        ((TextView) largeItemText).setTypeface(face, Typeface.NORMAL);
                    }
                }
            }
        }
    }

    public static boolean isLocationEnabled (Context context) {
        if (context == null)
            return false;

        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static boolean isValidOnlyNumber (String toString) {
        return TextUtils.isDigitsOnly(toString);
    }

    public static int dpToPx (int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight (Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth (Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isValidString (String string) {
//        Log.d(TAG, "isValid");
        return string != null && !string.trim().isEmpty();
    }

    public static boolean isAndroid5 () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Drawable changeDrawableColor (Context context, @ColorRes int colorToId,
                                                @DrawableRes int drawableToChangeId) {
//        Log.v(TAG, "changeDrawableColor");
        int color = context.getResources().getColor(colorToId);
        Drawable drawable = context.getResources().getDrawable(drawableToChangeId);
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        return drawable;
    }

    public static void disableEnableControls (boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    public static void setBadge (Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    private static String getLauncherClassName (Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List <ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }

    public static boolean isMyServiceRunning (Context context, Class <?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public static boolean isValidContextForGlide (final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return !activity.isDestroyed() && !activity.isFinishing();
            }
        }
        return true;
    }

    public static boolean validateSpaceEdtxt (String txt) {
        return TextUtils.isEmpty(txt) || (txt.length() > 0 && txt.startsWith(" ") && txt.endsWith(" ") && txt.contains(" "));
    }

    public static void selectSpinnerValue (Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setFragment (Context context, int layoutId, Fragment fragment, String tagName, boolean addToBackStack) {
        FragmentTransaction t = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        if (addToBackStack)
            t.addToBackStack(null);
        t.replace(layoutId, fragment, tagName);
        t.commit();
    }

    public static void sendSMS (Context context, String Number) {

        if (Number != null) {
            try {
                Uri uri = Uri.parse("smsto:" + Number);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                // intent.putExtra("sms_body", smsText);
                context.startActivity(intent);
            } catch (Exception ignored) {

            }
        } else {
            Toast.makeText(context, "شماره ای ثبت نشده است", Toast.LENGTH_SHORT).show();
        }

    }

    public static void callNumber (Context context, String Number) {
        if (Number != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + Number));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "شماره ای ثبت نشده است", Toast.LENGTH_SHORT).show();
        }

    }

    public static void sendEmail (Context context, String email) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
            context.startActivity(Intent.createChooser(emailIntent, ""));
        } catch (Exception e) {
            Toast.makeText(context, "ایمیل مورد نظر یافت نشد !", Toast.LENGTH_SHORT).show();
        }

    }

    public static void openBrowser (Context context, String url) {
        try {
            Uri webpage = Uri.parse(url);

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(context, "وب سایت مورد نظر یافت نشد !", Toast.LENGTH_SHORT).show();
        }

    }

    public static void openTelegram (Context context, String telegramUrl) {
        try {
            Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl));
            context.startActivity(telegram);
        } catch (Exception E) {
            Toast.makeText(context, "آی دی مورد نظر یافت نشد !", Toast.LENGTH_SHORT).show();
        }

    }

    public static void expand (final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        int prevHeight = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate (ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        valueAnimator.start();

    }

    public static void collapse (final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation (float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds () {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void openInstagram (Context context, String url) {
        Uri uri = Uri.parse("http://instagram.com/_u/" + url);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            context.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + url)));
        }

    }


    private int fontpercent_screenheight (Context _context, double d) {
        //get resolution
        DisplayMetrics Display_Metrics = new DisplayMetrics();
        ((Activity) _context).getWindowManager().getDefaultDisplay().getMetrics(Display_Metrics);
        int px = (int) ((float) Display_Metrics.heightPixels * ((float) d / 100));
        float dp = px / Display_Metrics.density;
        return (int) dp;
    }

    public void setTextSize (Context _context, ViewGroup viewTree) {
        Stack <ViewGroup> stackOfViewGroup = new Stack <>();
        stackOfViewGroup.push(viewTree);
        while (!stackOfViewGroup.isEmpty()) {
            ViewGroup tree = stackOfViewGroup.pop();
            for (int i = 0; i < tree.getChildCount(); i++) {
                View child = tree.getChildAt(i);
                if (child instanceof ViewGroup) {
                    // recursive call
                    stackOfViewGroup.push((ViewGroup) child);
                } else if (child instanceof Button) {
                    ((Button) child).setTextSize(fontpercent_screenheight(_context, 3));
                } else if (child instanceof EditText) {
                    ((EditText) child).setTextSize(fontpercent_screenheight(_context, 3));
                } else if (child instanceof TextView) {
                    // base case
                    ((TextView) child).setTextSize(fontpercent_screenheight(_context, 3));
                }
            }
        }
    }


}