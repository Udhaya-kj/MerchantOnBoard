package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

import life.corals.onboarding.Constants.Constants;
import life.corals.onboarding.Dialogs.MessageAlertDialog;
import life.corals.onboarding.R;
import life.corals.onboarding.Utils.AppTimeOutManagerUtil;
import life.corals.onboarding.Utils.MerchantRegQRScannerUtils;

import static life.corals.onboarding.Constants.Constants.COUNTRY_CODE;

public class Scan_QR_Code_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView_back;
    private Button button_cancel;
    private static final String TAG = "ScannerOnBoardActivity";
    private static final String PAYMENT_STATIC_NODE = "26";
    private static final int SCAN_SUCCESS_INTENT = 0;
    private static final String COUNTRY_CODE_NODE = "58";
    SurfaceView surfaceView;
    private MerchantRegQRScannerUtils merchantRegQRScannerUtils;
    private final Handler handler = new Handler();
    private MessageAlertDialog messageAlertDialog;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private SharedPreferences sharedpreferences_QR_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan__qr__code_);

        sharedpreferences_QR_Status = getSharedPreferences(Setup_QR_Code_Activity.MyPREFERENCES_QR_STATUS, Context.MODE_PRIVATE);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(Scan_QR_Code_Activity.this);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 555);
            } catch (Exception e) {
                Log.d("Permission Error QR", "" + e.getMessage().toString());
            }

        }

        button_cancel = (Button) findViewById(R.id.qr_scanner_cancel_button);
        imageView_back = (ImageView) findViewById(R.id.back_arrow);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Scan_QR_Code_Activity.this, Setup_QR_Code_Activity.class);
                startActivity(in);
                finish();
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Scan_QR_Code_Activity.this, Setup_QR_Code_Activity.class);
                startActivity(in);
                finish();
            }
        });

        Button materialButton = findViewById(R.id.qr_scanner_cancel_button);
        materialButton.setOnClickListener(this);
        surfaceView = findViewById(R.id.surfaceView);

    }

    private void goBack() {
        SharedPreferences.Editor editor = sharedpreferences_QR_Status.edit();
        editor.putString(Setup_QR_Code_Activity.QR_STATUS, "0");
        editor.commit();
        messageAlertDialog.dismissMessage();
        //Scan_QR_Code_Activity.super.onBackPressed();
        startActivity(new Intent(this, Setup_QR_Code_Activity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.qr_scanner_cancel_button) {
            //super.onBackPressed();
            startActivity(new Intent(this, Setup_QR_Code_Activity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    private void successIntent(String merchantId, String countryCode) {
        Log.d(TAG, "successIntent: " + countryCode);
        SharedPreferences.Editor editor = sharedpreferences_QR_Status.edit();
        editor.putString(Setup_QR_Code_Activity.QR_STATUS, "1");
        editor.putBoolean(Setup_QR_Code_Activity.QR_REQUIRED, false);
        editor.commit();

        startActivity(new Intent(this, Setup_QR_Code_Activity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private String getMerchantUniqueId(HashMap<String, String> hashMap) {
        return hashMap.get(PAYMENT_STATIC_NODE);
    }


    @Override
    protected void onPause() {
        super.onPause();
//        merchantRegQRScannerUtils.cameraSourcePause();
        appTimeOutManagerUtil.onPauseApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();

        merchantRegQRScannerUtils = new MerchantRegQRScannerUtils(surfaceView, Scan_QR_Code_Activity.this) {
            @SuppressLint("SetTextI18n")
            @Override
            public void parsedHashMap(HashMap<String, String> stringHashMap) {
                successIntent(getMerchantUniqueId(stringHashMap), stringHashMap.get(COUNTRY_CODE_NODE));
            }

            @Override
            public void onFailureQRScan(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageAlertDialog = new MessageAlertDialog(Scan_QR_Code_Activity.this) {
                            @Override
                            protected void onOkButtonClick() {
                                goBack();
                            }
                        };
                        messageAlertDialog.showMessage(result);
                    }
                });

            }
        };
    }

}
