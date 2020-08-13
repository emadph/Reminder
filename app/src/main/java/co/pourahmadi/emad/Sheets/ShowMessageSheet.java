package co.pourahmadi.emad.Sheets;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import co.pourahmadi.emad.Core.Statics.Constant;
import co.pourahmadi.emad.R;

public class ShowMessageSheet extends BaseSheet {

    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    private String strMsg;
    private String strConfirmButton;
    private ButtonClickListener mListener;

    public ShowMessageSheet (ButtonClickListener mListener) {
        this.mListener = mListener;
    }

    public static ShowMessageSheet newInstance (String msg, String confirmTitle, ButtonClickListener mListener) {
        ShowMessageSheet needUpdateSheet = new ShowMessageSheet(mListener);
        Bundle args = new Bundle();
        args.putString(Constant.ARG_STRING, msg);
        args.putString(Constant.ARG_STRING2, confirmTitle);
        needUpdateSheet.setArguments(args);
        return needUpdateSheet;
    }

    @OnClick(R.id.btnCancel)
    void btnCancel () {
        try {
            onCancel();
            dismiss();
        } catch (Exception ignored) {
        }
    }

    @OnClick(R.id.btnConfirm)
    void btnConfirm () {
        try {
            onConfirm();
        } catch (Exception ignored) {
        }
    }

    @Override
    public int getTheme () {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public int getLayoutId () {
        return R.layout.sheet_show_message;
    }

    @Override
    public void beforeView () {

        setCancelable(false);
        assert getArguments() != null;
        strMsg = getArguments().getString(Constant.ARG_STRING);
        strConfirmButton = getArguments().getString(Constant.ARG_STRING2);

    }

    @Override
    public void afterView () {
        title.setText(Html.fromHtml(strMsg));
        btnConfirm.setText(strConfirmButton);
    }

    @Override
    public void onAttachInterface (Context context) {

    }

    @Override
    public void destroyView () {
        mListener = null;
    }

    private void onCancel () {
    }

    private void onConfirm () {
        mListener.onConfirmClick();
        dismiss();
    }

    public interface ButtonClickListener {
        void onConfirmClick ();
    }


}