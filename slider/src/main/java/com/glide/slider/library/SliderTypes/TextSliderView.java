package com.glide.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.glide.slider.library.R;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        AppCompatImageView target = v.findViewById(R.id.glide_slider_image);
        AppCompatTextView description = v.findViewById(R.id.glide_slider_description);
        AppCompatTextView title = v.findViewById(R.id.glide_slider_title);
        description.setText(getDescription());
        title.setText(getTitle());
        bindEventAndShow(v, target);
        return v;
    }
}
