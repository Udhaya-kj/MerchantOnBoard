package life.corals.onboarding.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

import life.corals.onboarding.R;

import life.corals.onboarding.receiver.ConnectivityReceiver;

public class OTP_Activity extends AppCompatActivity {


    private Button button_next;
    private TextView textView_resend,textView_time,textView_dial_code;

    private String mVerificationId;
    //The edittext to input the code
    private EditText editTextCode;
    //firebase auth object
    private FirebaseAuth mAuth;
    private String mob, country,currency,dial_code,country_code;

    public static final String MyPREFERENCES_OTP_VALIDATE = "MyPrefs_Otp_Validate";
    public static final String OTP_VALIDATE = "OTP_VALIDATE";
    public static final String OTP_MOBILE = "OTP_MOBILE";
    public static final String COUNTRY = "COUNTRY";
    public static final String CURRENCY = "CURRENCY";
    public static final String DIAL_CODE = "DIAL_CODE";
    public static final String COUNTRY_CODE = "COUNTRY_CODE";
    private SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES_OTP_COUNT = "MyPrefs_Otp_Count";
    public static final String OTP_COUNT = "OTP_COUNT";
    private SharedPreferences sharedpreferences_otp_count;

    private ProgressBar  progress;
    private FrameLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        sharedpreferences = getSharedPreferences(MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
        sharedpreferences_otp_count = getSharedPreferences(MyPREFERENCES_OTP_COUNT, Context.MODE_PRIVATE);

        linearLayout = (FrameLayout)findViewById(R.id.layout_otp);
        progress = findViewById(R.id.verify_otp_progress_bar);
        editTextCode = (EditText) findViewById(R.id.editText_Code);
        button_next = (Button) findViewById(R.id.button_next_opt_fragment);
        textView_resend = (TextView) findViewById(R.id.text_resend_otp);
        textView_time = (TextView) findViewById(R.id.text_time);
        textView_dial_code = (TextView) findViewById(R.id.text_dial_code);
        //textView_resend.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Resend OTP" + "</u>  </font>"));
        textView_resend.setEnabled(false);

        if (getIntent() != null) {
            mob = getIntent().getStringExtra("mobile");
            country = getIntent().getStringExtra("country");
            currency = getIntent().getStringExtra("currency");
            dial_code = getIntent().getStringExtra("dial_code");
            country_code = getIntent().getStringExtra("country_code");
            Log.d("Mobile---->", "" + mob + "," + country + "," + currency+ "," + dial_code);
        }

        textView_dial_code.setText("+"+dial_code);
        boolean isConn= ConnectivityReceiver.isConnected();

        if (isConn) {
            if (!TextUtils.isEmpty(mob) && mob != null) {
                sendVerificationCode(mob);
            } else {
                Toast.makeText(this, "Something went wrong.Please try again!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Snackbar snackbar = Snackbar
                    .make(linearLayout, "Please check your connection and try again", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


        Timer();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editTextCode.getText().toString().trim();
              /*  if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }*/

                //verifying the code entered manually
                verifyVerificationCode(code);

            }
        });

        textView_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mob) && mob != null) {
                    Timer();
                    sendVerificationCode(mob);
                } else {
                    Toast.makeText(OTP_Activity.this, "Something went wrong.Please try again!", Toast.LENGTH_SHORT).show();
                }


              /*  String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }
                //verifying the code entered manually
                verifyVerificationCode(code);*/
            }
        });


    }



    private void sendVerificationCode(String mobile) {

        progress.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+dial_code+"" + mobile,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);

        Toast.makeText(this, "OTP has been sent to "+mob, Toast.LENGTH_LONG).show();
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTP_Activity.this, "Verification Failed.Please try again!", Toast.LENGTH_LONG).show();

            Log.d("Verification Failed--->", "" + e.getMessage().toString());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
            //Toast.makeText(OTP_Activity.this, "Code sent!", Toast.LENGTH_LONG).show();

        }
    };


    private void verifyVerificationCode(String code) {

        try {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
           // toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTP_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            progress.setVisibility(View.INVISIBLE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(OTP_VALIDATE, "1");
                            editor.putString(OTP_MOBILE, mob);
                            editor.putString(COUNTRY, country);
                            editor.putString(CURRENCY, currency);
                            editor.putString(DIAL_CODE, dial_code);
                            editor.putString(COUNTRY_CODE, country_code);
                            editor.commit();

                            Intent in = new Intent(getApplicationContext(), Welcome_Activity.class);
                            startActivity(in);
                            finish();


                        } else {

                            progress.setVisibility(View.INVISIBLE);
                            //verification unsuccessful.. display an error message
                            int otp_cnt = sharedpreferences_otp_count.getInt(OTP_COUNT, 0);

                            Log.d("Otp_Count--->", "" + otp_cnt);
                            if (otp_cnt >= 2) {

                                View dialogView = LayoutInflater.from(OTP_Activity.this).inflate(R.layout.alert_otp, null, false);
                                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OTP_Activity.this);
                                Button ok_btn = (Button) dialogView.findViewById(R.id.ok_button_otp);
                                //setting the view of the builder to our custom view that we already inflated
                                builder.setCancelable(false);
                                builder.setView(dialogView);
                                //finally creating the alert dialog and displaying it
                                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                ok_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent a = new Intent(Intent.ACTION_MAIN);
                                        a.addCategory(Intent.CATEGORY_HOME);
                                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(a);
                                    }
                                });

                            } else {

                                String message = "";
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    message = "Invalid code entered. Please enter valid code";
                                }
                                Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_otp), message, Snackbar.LENGTH_LONG);
                                snackbar.show();

                                int otp = otp_cnt + 1;
                                SharedPreferences.Editor editor = sharedpreferences_otp_count.edit();
                                editor.putInt(OTP_COUNT, otp);
                                editor.commit();

                            }


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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedpreferences_otp_count = getSharedPreferences(MyPREFERENCES_OTP_COUNT, Context.MODE_PRIVATE);
        int otp_cnt = sharedpreferences_otp_count.getInt(OTP_COUNT, 0);
        Log.d("Otp_Count On Resume--->", "" + otp_cnt);

        if (otp_cnt >= 2) {
            progress.setVisibility(View.INVISIBLE);
            View dialogView = LayoutInflater.from(OTP_Activity.this).inflate(R.layout.alert_otp, null, false);
            final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OTP_Activity.this);
            Button ok_btn = (Button) dialogView.findViewById(R.id.ok_button_otp);
            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);
            //finally creating the alert dialog and displaying it
            final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();

            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            });

        }
    }

    public void Timer(){
        new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                textView_time.setText(""+String.format("%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                textView_time.setText("00 : 00");
                progress.setVisibility(View.INVISIBLE);
                textView_resend.setAlpha(1f);
                textView_resend.setEnabled(true);
            }
        }.start();
    }

}
