package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import life.corals.onboarding.R;

public class Loyalty_Points_Activity extends AppCompatActivity {
    private LinearLayout button_next;
    private EditText editText_amount;
    private TextView textView_amt_title, textView_points;
    private TextView textView_currency, textView_1unit, textView_5unit, textView_10unit, textView_15unit, textView_60days, textView_90days, textView_180days, textView_360days;
    public static final String MyPREFERENCES_LOYALTY_POINTS = "MyPrefs_Loyalty_points";
    public static final String AMOUNT_PER_POINTS = "AMOUNT_PER_POINTS";
    public static final String POINTS_PER_UNIT = "POINTS_PER_UNIT";
    public static final String POINTS_EXPIRES = "POINTS_EXPIRES";
    private SharedPreferences sharedpreferences;
    private SharedPreferences sharedpreferences_open_screen, sharedpreferences_currency;
    private ImageView imageView_back;
    private String getPoints = null, getPoints_days = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty__points_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES_LOYALTY_POINTS, Context.MODE_PRIVATE);
        sharedpreferences_currency = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);

        editText_amount = (EditText) findViewById(R.id.edit_amount);
        button_next = (LinearLayout) findViewById(R.id.button_next_loyalty_points);
        textView_currency = (TextView) findViewById(R.id.text_currency);

        textView_points = (TextView) findViewById(R.id.text_points_issued);
        textView_amt_title = (TextView) findViewById(R.id.text_amt_title);
        textView_1unit = (TextView) findViewById(R.id.text_1unit);
        textView_5unit = (TextView) findViewById(R.id.text_5units);
        textView_10unit = (TextView) findViewById(R.id.text_10units);
        textView_15unit = (TextView) findViewById(R.id.text_15units);

        textView_60days = (TextView) findViewById(R.id.text_60days);
        textView_90days = (TextView) findViewById(R.id.text_90days);
        textView_180days = (TextView) findViewById(R.id.text_180days);
        textView_360days = (TextView) findViewById(R.id.text_360days);

        String amt = sharedpreferences.getString(AMOUNT_PER_POINTS, "");
        String spent_points = sharedpreferences.getString(POINTS_PER_UNIT, "");
        String expire_points = sharedpreferences.getString(POINTS_EXPIRES, "");
        final String currency = sharedpreferences_currency.getString(OTP_Activity.CURRENCY, "");

        textView_points.setText("No.of points issued for  " + currency + "1");
        textView_amt_title.setText("Minimum purchase for points issuance");
        if (!TextUtils.isEmpty(spent_points)) {
            if (spent_points.equals("1")) {
                getPoints = "1";
                textView_1unit.setTextColor(getResources().getColor(R.color.white));
                textView_1unit.setBackgroundResource(R.drawable.gradient_button);
            } else if (spent_points.equals("5")) {
                getPoints = "5";
                textView_5unit.setTextColor(getResources().getColor(R.color.white));
                textView_5unit.setBackgroundResource(R.drawable.gradient_button);
            } else if (spent_points.equals("10")) {
                getPoints = "10";
                textView_10unit.setTextColor(getResources().getColor(R.color.white));
                textView_10unit.setBackgroundResource(R.drawable.gradient_button);

            } else if (spent_points.equals("15")) {
                getPoints = "15";
                textView_15unit.setTextColor(getResources().getColor(R.color.white));
                textView_15unit.setBackgroundResource(R.drawable.gradient_button);
            }
        }

        if (!TextUtils.isEmpty(expire_points)) {
            if (expire_points.equals("60")) {
                getPoints_days = "60";
                textView_60days.setTextColor(getResources().getColor(R.color.white));
                textView_60days.setBackgroundResource(R.drawable.gradient_button);
            } else if (expire_points.equals("90")) {
                getPoints_days = "90";
                textView_90days.setTextColor(getResources().getColor(R.color.white));
                textView_90days.setBackgroundResource(R.drawable.gradient_button);
            } else if (expire_points.equals("180")) {
                getPoints_days = "180";
                textView_180days.setTextColor(getResources().getColor(R.color.white));
                textView_180days.setBackgroundResource(R.drawable.gradient_button);

            } else if (expire_points.equals("360")) {
                getPoints_days = "360";
                textView_360days.setTextColor(getResources().getColor(R.color.white));
                textView_360days.setBackgroundResource(R.drawable.gradient_button);
            }
        }

        //Points per Unit
        textView_1unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPoints = "1";
                textView_1unit.setTextColor(getResources().getColor(R.color.white));
                textView_1unit.setBackgroundResource(R.drawable.gradient_button);

                textView_5unit.setTextColor(getResources().getColor(R.color.black));
                textView_5unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_10unit.setTextColor(getResources().getColor(R.color.black));
                textView_10unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_15unit.setTextColor(getResources().getColor(R.color.black));
                textView_15unit.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });

        textView_5unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPoints = "5";
                textView_5unit.setTextColor(getResources().getColor(R.color.white));
                textView_5unit.setBackgroundResource(R.drawable.gradient_button);

                textView_1unit.setTextColor(getResources().getColor(R.color.black));
                textView_1unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_10unit.setTextColor(getResources().getColor(R.color.black));
                textView_10unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_15unit.setTextColor(getResources().getColor(R.color.black));
                textView_15unit.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });

        textView_10unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPoints = "10";
                textView_10unit.setTextColor(getResources().getColor(R.color.white));
                textView_10unit.setBackgroundResource(R.drawable.gradient_button);

                textView_1unit.setTextColor(getResources().getColor(R.color.black));
                textView_1unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_5unit.setTextColor(getResources().getColor(R.color.black));
                textView_5unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_15unit.setTextColor(getResources().getColor(R.color.black));
                textView_15unit.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });

        textView_15unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPoints = "15";
                textView_15unit.setTextColor(getResources().getColor(R.color.white));
                textView_15unit.setBackgroundResource(R.drawable.gradient_button);

                textView_1unit.setTextColor(getResources().getColor(R.color.black));
                textView_1unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_5unit.setTextColor(getResources().getColor(R.color.black));
                textView_5unit.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_10unit.setTextColor(getResources().getColor(R.color.black));
                textView_10unit.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });


//Expiry Days
        textView_60days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPoints_days = "60";
                textView_60days.setTextColor(getResources().getColor(R.color.white));
                textView_60days.setBackgroundResource(R.drawable.gradient_button);

                textView_90days.setTextColor(getResources().getColor(R.color.black));
                textView_90days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_180days.setTextColor(getResources().getColor(R.color.black));
                textView_180days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_360days.setTextColor(getResources().getColor(R.color.black));
                textView_360days.setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        textView_90days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPoints_days = "90";
                textView_90days.setTextColor(getResources().getColor(R.color.white));
                textView_90days.setBackgroundResource(R.drawable.gradient_button);

                textView_60days.setTextColor(getResources().getColor(R.color.black));
                textView_60days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_180days.setTextColor(getResources().getColor(R.color.black));
                textView_180days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_360days.setTextColor(getResources().getColor(R.color.black));
                textView_360days.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });

        textView_180days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPoints_days = "180";
                textView_180days.setTextColor(getResources().getColor(R.color.white));
                textView_180days.setBackgroundResource(R.drawable.gradient_button);

                textView_60days.setTextColor(getResources().getColor(R.color.black));
                textView_60days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_90days.setTextColor(getResources().getColor(R.color.black));
                textView_90days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_360days.setTextColor(getResources().getColor(R.color.black));
                textView_360days.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });

        textView_360days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPoints_days = "360";
                textView_360days.setTextColor(getResources().getColor(R.color.white));
                textView_360days.setBackgroundResource(R.drawable.gradient_button);

                textView_60days.setTextColor(getResources().getColor(R.color.black));
                textView_60days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_90days.setTextColor(getResources().getColor(R.color.black));
                textView_90days.setBackgroundColor(getResources().getColor(R.color.grey));

                textView_180days.setTextColor(getResources().getColor(R.color.black));
                textView_180days.setBackgroundColor(getResources().getColor(R.color.grey));

            }
        });


        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView_currency.setText("" + currency);
        editText_amount.setText(amt);
        editText_amount.setSelection(amt.length());


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = editText_amount.getText().toString();

                if (!TextUtils.isEmpty(amount)) {
                    if (!amount.startsWith("0")) {
                        if (!TextUtils.isEmpty(getPoints)) {
                            if (!TextUtils.isEmpty(getPoints_days)) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(AMOUNT_PER_POINTS, amount);
                                editor.putString(POINTS_PER_UNIT, getPoints);
                                editor.putString(POINTS_EXPIRES, getPoints_days);
                                editor.commit();

                                Intent in = new Intent(getApplicationContext(), Add_Redemption_Activity.class);
                                startActivity(in);
                                finish();
                            } else {
                                Toast.makeText(Loyalty_Points_Activity.this, "Select points expiry days ", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(Loyalty_Points_Activity.this, "Select No.of points issued for  "+currency+"1", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        editText_amount.setError("Enter valid amount");
                        editText_amount.requestFocus();
                    }
                } else {
                    editText_amount.setError("Enter valid amount");
                }
            }


        });

    }


    @Override
    protected void onPause() {
        super.onPause();

        String amount = editText_amount.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(AMOUNT_PER_POINTS, amount);
        editor.putString(POINTS_PER_UNIT, getPoints);
        editor.putString(POINTS_EXPIRES, getPoints_days);
        editor.commit();


        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "9");
        editor_os.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();


        String amount = editText_amount.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(AMOUNT_PER_POINTS, amount);
        editor.putString(POINTS_PER_UNIT, getPoints);
        editor.putString(POINTS_EXPIRES, getPoints_days);
        editor.commit();


        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "9");
        editor_os.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String amount = editText_amount.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(AMOUNT_PER_POINTS, amount);
        editor.putString(POINTS_PER_UNIT, getPoints);
        editor.putString(POINTS_EXPIRES, getPoints_days);
        editor.commit();

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "9");
        editor_os.commit();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Loyalty_Points_Activity.this, Setup_QR_Code_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}
