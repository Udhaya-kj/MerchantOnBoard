package life.corals.onboarding.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

import life.corals.onboarding.R;
import life.corals.onboarding.Utils.MyApplication;
import life.corals.onboarding.receiver.ConnectivityReceiver;

public class Captcha_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String ALLOWED_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private CheckBox checkBox;
    private ArrayList<String> country_code_list, symbol_list, mobile_start_digits_list, mobile_min_length_list, mobile_max_length_list, country_name_list, time_zone_list, dial_code_list;

    private String created_captcha;
    private ImageView imageView_back;
    //public final String SiteKey = "6LdjxMUUAAAAAPSA2lN4lQ6ixUpVG2I0_0SaVutA";
    public final String SiteKey = "6LdK8coUAAAAAOeeocBhNOl4x5QGS0qlJFkR6eqj"; //Registered Label :corals onboard
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha_);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(Captcha_Activity.this)
                .build();

        mGoogleApiClient.connect();

        checkBox = findViewById(R.id.checkbox_robot);
        imageView_back = findViewById(R.id.image_back_captcha);
        country_code_list = new ArrayList<>();
        symbol_list = new ArrayList<>();
        mobile_start_digits_list = new ArrayList<>();
        mobile_min_length_list = new ArrayList<>();
        mobile_max_length_list = new ArrayList<>();
        country_name_list = new ArrayList<>();
        time_zone_list = new ArrayList<>();
        dial_code_list = new ArrayList<>();

        if (getIntent().getExtras() != null) {
            country_code_list = (ArrayList<String>) getIntent().getSerializableExtra("code");
            symbol_list = (ArrayList<String>) getIntent().getSerializableExtra("symbol");
            mobile_start_digits_list = (ArrayList<String>) getIntent().getSerializableExtra("start_dg");
            mobile_min_length_list = (ArrayList<String>) getIntent().getSerializableExtra("min_l");
            mobile_max_length_list = (ArrayList<String>) getIntent().getSerializableExtra("max_l");
            country_name_list = (ArrayList<String>) getIntent().getSerializableExtra("c_name");
            time_zone_list = (ArrayList<String>) getIntent().getSerializableExtra("zone");
            dial_code_list = (ArrayList<String>) getIntent().getSerializableExtra("dial_code");
        } else {
            Toast.makeText(this, "Something went wrong.Please try again!", Toast.LENGTH_SHORT).show();
        }

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(true);
                        Toast.makeText(Captcha_Activity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                        SafetyNet.SafetyNetApi.verifyWithRecaptcha(mGoogleApiClient, SiteKey)
                                .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                    @Override
                                    public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                        Status status = recaptchaTokenResult.getStatus();
                                        if ((status != null) && status.isSuccess()) {
                                            // Toast.makeText(Captcha_Activity.this, "Captcha verified successfully!", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Captcha_Activity.this, CountryActivity.class);
                                            i.putExtra("code", country_code_list);
                                            i.putExtra("symbol", symbol_list);
                                            i.putExtra("start_dg", mobile_start_digits_list);
                                            i.putExtra("min_l", mobile_min_length_list);
                                            i.putExtra("max_l", mobile_max_length_list);
                                            i.putExtra("c_name", country_name_list);
                                            i.putExtra("zone", time_zone_list);
                                            i.putExtra("dial_code", dial_code_list);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            checkBox.setChecked(false);
                                            Toast.makeText(Captcha_Activity.this, "Verification failed.Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } else {
                    checkBox.setChecked(false);
                    Toast.makeText(Captcha_Activity.this, getResources().getString(R.string.offline), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Toast.makeText(this, "onConnected()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(this, "onConnectionSuspended: " + i, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Toast.makeText(this, "onConnectionFailed():\n" + connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);

    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
    }

   /* private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {

        if (!isConnected) {
            Toast.makeText(this, getResources().getString(R.string.offline), Toast.LENGTH_SHORT).show();
        }

    }*/
}
