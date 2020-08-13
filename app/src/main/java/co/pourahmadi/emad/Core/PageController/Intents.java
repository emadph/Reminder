package co.pourahmadi.emad.Core.PageController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import co.pourahmadi.emad.Core.Statics.Constant;
import co.pourahmadi.emad.Core.Utils.UtilApp;
import co.pourahmadi.emad.R;
import co.pourahmadi.emad.features.activities.mainActivity.MainActivity;


/**
 * Created by Soheil on 3/11/2017.
 */

public class Intents {

    public static void mainActivity (Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        ActivityCompat.finishAffinity(activity);
        activity.startActivity(intent);
    }



    public static void startActivity (Context context, final Class <? extends Activity> ActivityToOpen, boolean finish) {
        Intent intent = new Intent(context, ActivityToOpen);
        context.startActivity(intent);
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
        }
        if (finish) {
            UtilApp.ExitAnimation(context);
        }

    }

    public static void startActivityBundle (Context context, final Class <? extends Activity> ActivityToOpen, boolean finish, int Id, String keyString) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.putExtra(Constant.ARG_INT, Id);
        if (keyString != null && !keyString.equals("")) {
            intent.putExtra(Constant.ARG_STRING, keyString);
        }
        context.startActivity(intent);
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
        }

        if (finish) {
            UtilApp.ExitAnimation(context);
        }
    }





}
