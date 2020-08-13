package co.pourahmadi.emad.Core.Font;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import co.pourahmadi.emad.R;

/**
 * Created by chris on 17/03/15. For Calligraphy.
 */
public class TextField extends AppCompatTextView {

    public TextField (final Context context, final AttributeSet attrs) {
        super(context, attrs, R.attr.textFieldStyle);
    }

}
