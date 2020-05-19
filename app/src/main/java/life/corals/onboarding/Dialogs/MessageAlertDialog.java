package life.corals.onboarding.Dialogs;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;


import life.corals.onboarding.Constants.Constants;
import life.corals.onboarding.R;

public abstract class MessageAlertDialog implements View.OnClickListener {

    private Context mCtx;
    private AlertDialog alertDialog;
    private TextView messageTextView;
    private TextView messageTitle;

    private Button cancelButton;
    private Button okayButton;

    public MessageAlertDialog(Context mCtx) {
        this.mCtx = mCtx;
        initializeAlertDialog();
    }

    private void initializeAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setCancelable(false);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.message_alert_dialog_layout, null, false);
        okayButton = view.findViewById(R.id.message_alert_dialog_okay_button);
        okayButton.setOnClickListener(this);
        cancelButton = view.findViewById(R.id.message_alert_dialog_cancel_button);
        cancelButton.setOnClickListener(this);
        messageTextView = view.findViewById(R.id.message_alert_dialog_msg_text_view);
        messageTitle = view.findViewById(R.id.message_alert_dialog_title_text_view);
        builder.setView(view);
        alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public void showMessage(String message) {
        try {
            if (alertDialog != null) {
                alertDialog.show();
                cancelButton.setVisibility(View.GONE);
                messageTextView.setText(message);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(Objects.requireNonNull(alertDialog.getWindow()).getAttributes());
                lp.width = getDisplayWidth();
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.x = 0;
                lp.y = 0;
                alertDialog.getWindow().setAttributes(lp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessageWithTwoButton(String message, String okayButtonText, String cancelButtonText) {
        try {
            if (alertDialog != null) {
                alertDialog.show();
                messageTextView.setText(message);
                okayButton.setText(okayButtonText);
                cancelButton.setText(cancelButtonText);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(Objects.requireNonNull(alertDialog.getWindow()).getAttributes());
                lp.width = getDisplayWidth();
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.x = 0;
                lp.y = 0;
                alertDialog.getWindow().setAttributes(lp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showMessageWithTwoButtonAndTitle(String title, String message, String okayButtonText, String cancelButtonText) {
        try {
            if (alertDialog != null) {
                alertDialog.show();
                messageTitle.setText(title);
                messageTextView.setText(Html.fromHtml(message));
                okayButton.setText(okayButtonText);
                cancelButton.setText(cancelButtonText);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(Objects.requireNonNull(alertDialog.getWindow()).getAttributes());
                lp.width = getDisplayWidth();
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.x = 0;
                lp.y = 0;
                alertDialog.getWindow().setAttributes(lp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getDisplayWidth() {
        int width = 440;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Log.d("DISPLAY_WIDTH", "getDisplayWidth: " + size.x);
            width = size.x - Constants.APP_ALERT_MINUS_SIZE;
        }
        return width;
    }

    public void dismissMessage() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.message_alert_dialog_okay_button) {
            dismissMessage();
            onOkButtonClick();
        }
        if (v.getId() == R.id.message_alert_dialog_cancel_button) {
            dismissMessage();
        }
    }

    protected abstract void onOkButtonClick();
}
