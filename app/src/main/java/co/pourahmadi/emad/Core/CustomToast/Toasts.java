package co.pourahmadi.emad.Core.CustomToast;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import co.pourahmadi.emad.R;

public class Toasts {

/*    public static void makeToast(Context context, String text, boolean isLong) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.toast_layout, null);

        AppCompatTextView textV = layout.findViewById(R.id.snackbar_text);
        textV.setText(text);

        Toasts toast = new Toasts(context);
        toast.setGravity(Gravity.BOTTOM , 0, DimenUtil.dpToPx(42));
        toast.setDuration((isLong) ? Toasts.LENGTH_LONG : Toasts.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }*/

    /*
     * view : Attach View
     * msg : R.string.msg
     */
    public static void makeToast (Context context, View view, String msg) {
        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = layout.findViewById(R.id.snackbar_text);
        textView.setText(msg + "");
        layout.setBackgroundColor(context.getResources().getColor(R.color.bg_SnkBar));
        layout.setPadding(0, 0, 0, 0);
        layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        /////////////////////////////////////////////
  /*      final LayoutInflater mInflater = LayoutInflater.from(context);
        // Inflate our custom view
        View snackView = mInflater.inflate(R.layout.toast_layout, null);
        TextView textViewTop = snackView.findViewById(R.id.snackbar_text);
        textViewTop.setText(msg + "");
        snackView.setBackgroundColor(context.getResources().getColor(R.color.bg_SnkBar));
        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);
        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);*/
        // Show the Snackbar
        snackbar.show();
    }


}
