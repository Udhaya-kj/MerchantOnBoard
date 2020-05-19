package life.corals.onboarding.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import life.corals.onboarding.Constants.Constants;
import life.corals.onboarding.Dialogs.AlertDialogYesNo;
import life.corals.onboarding.R;
import life.corals.onboarding.receiver.ConnectivityReceiver;

public class Success_Activity extends AppCompatActivity {

    private SharedPreferences sharedpreferences_open_screen;
    private Button button_ok;
    private TextView textView_open_playstore;
    private String appPackageName = Constants.MERCHANT_PACKAGE;
    private LinearLayout linearLayout;
    private AlertDialogYesNo alertDialogYesNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        linearLayout = (LinearLayout) findViewById(R.id.linear_success);
        button_ok = (Button) findViewById(R.id.button_success);
        textView_open_playstore = (TextView) findViewById(R.id.textView_open_playstore);
        textView_open_playstore.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Click here to open Merchant Application" + "</u>  </font>"));

        SharedPreferences preferences_buss_det = getSharedPreferences(Business_Details_Activity.MyPREFERENCES_BUSSINESS_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_bt = preferences_buss_det.edit();
        editor_bt.clear();
        editor_bt.commit();

        SharedPreferences preferences_operating_hours = getSharedPreferences(Operating_Hours_Activity.MyPREFERENCES_OPERATING_HRS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_oh = preferences_operating_hours.edit();
        editor_oh.clear();
        editor_oh.commit();

        SharedPreferences preferences_intro_buss = getSharedPreferences(Intro_Business_Activity.MyPREFERENCES_INTRO_BUSSINESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_intro = preferences_intro_buss.edit();
        editor_intro.clear();
        editor_intro.commit();


        SharedPreferences preferences_loyalty = getSharedPreferences(Loyalty_Points_Activity.MyPREFERENCES_LOYALTY_POINTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_loyalty = preferences_loyalty.edit();
        editor_loyalty.clear();
        editor_loyalty.commit();

        SharedPreferences preferences_preview = getSharedPreferences(Upload_Photo_Activity.MyPREFERENCES_UPLOAD_PHOTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_preview = preferences_preview.edit();
        editor_preview.clear();
        editor_preview.commit();

        SharedPreferences preferences_otp = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_otp = preferences_otp.edit();
        editor_otp.clear();
        editor_otp.commit();

        SharedPreferences preferences_otp_count = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_COUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_otp_count = preferences_otp_count.edit();
        editor_otp_count.clear();
        editor_otp_count.commit();

        SharedPreferences preferences_QR = getSharedPreferences(Setup_QR_Code_Activity.MyPREFERENCES_QR_STATUS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_qr = preferences_QR.edit();
        editor_qr.clear();
        editor_qr.commit();

        SharedPreferences preferences_redeem = getSharedPreferences(Add_Redemption_Activity.MyPREFERENCES_REDEEM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_redeem = preferences_redeem.edit();
        editor_redeem.clear();
        editor_redeem.commit();

        SharedPreferences preferences_add_redeem = getSharedPreferences(Redeem_Points_Activity.MyPREFERENCES_ADD_REDEEM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_add_redeem = preferences_add_redeem.edit();
        editor_add_redeem.clear();
        editor_add_redeem.commit();

        /*String add_redeem = preferences_add_redeem.getString(Redeem_Points_Activity.ADD_REDEEM, "");
        Toast.makeText(this, ""+add_redeem, Toast.LENGTH_SHORT).show();*/

        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "1");
        editor.commit();

        textView_open_playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnected= ConnectivityReceiver.isConnected();
                if(isConnected) {
                    openApp();
                }
                else {
                    Snackbar snackbar = Snackbar.make(linearLayout, getResources().getString(R.string.offline), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogYesNo = new AlertDialogYesNo(Success_Activity.this, "", getResources().getString(R.string.cloas_app), "OK", "REMIND LATER", false, true, false,false,false) {
                    @Override
                    public void onOKButtonClick() {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                        } else {
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelButtonClick() {
                    }
                };

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "1");
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "1");
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "1");
        editor.commit();
    }

    private void openApp() {

        if (isAppInstalled(Success_Activity.this, appPackageName)) {
            startActivity(getPackageManager().getLaunchIntentForPackage(appPackageName));
        } else {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    public static boolean isAppInstalled(Activity activity, String packageName) {
        PackageManager pm = activity.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
