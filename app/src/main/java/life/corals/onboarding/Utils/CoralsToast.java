package life.corals.onboarding.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import life.corals.onboarding.R;

public class CoralsToast {

    private Toast coralsToast;
    private Context mCtx;
    private TextView message;
    private RoundedImageView messageIcon;
    private View view;

    public CoralsToast(Context mCtx) {
        this.mCtx = mCtx;
        coralsToast = new Toast(mCtx);
        coralsToast.setDuration(Toast.LENGTH_LONG);
        initializeToast();
    }

    private void initializeToast() {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        view = layoutInflater.inflate(R.layout.custom_toast_layout, null, false);
        view.setLayoutParams(getLayoutParams());
        message = view.findViewById(R.id.toast_message_text);
        messageIcon = view.findViewById(R.id.toast_message_icon);
        coralsToast.setView(view);
        coralsToast.setDuration(Toast.LENGTH_LONG);
    }

    private ViewGroup.LayoutParams getLayoutParams() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        return layoutParams;
    }

    private int getDisplayWidth() {
        int width = 400;
        if (((Activity) mCtx).getWindow() != null) {
            Display display = ((Activity) mCtx).getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Log.d("DISPLAY_WIDTH", "getDisplayWidth: " + size.x);
            width = size.x;
        }
        return width;
    }

    public void showMessage(String msg, Drawable drawable) {
        message.setText(msg);
        if (drawable != null) {
            messageIcon.setImageDrawable(drawable);
        } else {
            messageIcon.setVisibility(View.GONE);
        }
        coralsToast.show();
    }
}
