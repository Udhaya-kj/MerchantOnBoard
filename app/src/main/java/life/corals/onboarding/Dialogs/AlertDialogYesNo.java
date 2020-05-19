package life.corals.onboarding.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import life.corals.onboarding.R;

public abstract class AlertDialogYesNo {
    public AlertDialogYesNo(Context mcntx, String title, String msg, String ok_btn, String cnl_btn, boolean isShowTitle, boolean isShowOkBtn, boolean isShowCancelBtn, boolean isShowSuccImage, boolean isShowWarningImage) {
        failureDiolog(mcntx, title, msg, ok_btn, cnl_btn, isShowTitle, isShowOkBtn, isShowCancelBtn,isShowSuccImage,isShowWarningImage);
    }

    private void failureDiolog(Context mcntx, String title, String msg, String ok_btn, String cnl_btn, boolean isShowTitle, boolean isShowOkBtn, boolean isShowCancelBtn, boolean isShowSuccImage, boolean isShowWarningImage) {

        //Toast.makeText(mcntx, "Alert....", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder;
        AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_create_redeem, null, false);
        ImageView imageView_succ =(ImageView) view.findViewById(R.id.image_success);
        ImageView imageView_war =(ImageView) view.findViewById(R.id.image_warning);
        Button btnOK = (Button) view.findViewById(R.id.ok_button);
        Button btnCancel = (Button) view.findViewById(R.id.cancel_button);

        TextView titletv = view.findViewById(R.id.text_alert_title);
        TextView msgtv = view.findViewById(R.id.text_alert_content);
        titletv.setText(title);
        msgtv.setText(msg);
        btnOK.setText(ok_btn);

        btnCancel.setText(cnl_btn);
        if (isShowSuccImage) {
            imageView_succ.setVisibility(View.VISIBLE);
        }
        if (isShowWarningImage) {
            imageView_war.setVisibility(View.VISIBLE);
        }
        if (isShowTitle) {
            titletv.setVisibility(View.VISIBLE);
        }

        if (!isShowOkBtn) {
            btnOK.setVisibility(View.GONE);
        }
        if (!isShowCancelBtn) {
            btnCancel.setVisibility(View.GONE);
        }
        builder = new AlertDialog.Builder(mcntx);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

      /*  if (!((Activity) mcntx).isFinishing()) {
            //show dialog
            alertDialog.show();
        } else {
            if (!((Activity) mcntx).isFinishing()) {
                //show dialog
                alertDialog.show();
            }
        }*/


      /*  if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }*/

        final AlertDialog finalAlertDialog1 = alertDialog;

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onOKButtonClick();
            }

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onCancelButtonClick();
            }

        });
    }

    public abstract void onOKButtonClick();

    public abstract void onCancelButtonClick();

   /* private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 550;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Log.d("DISPLAY_WIDTH", "getDisplayWidth: " + size.x);
            if (size.x > 720) {
                width = size.x - 400;
            }
        }
        return width;
    }*/
}
