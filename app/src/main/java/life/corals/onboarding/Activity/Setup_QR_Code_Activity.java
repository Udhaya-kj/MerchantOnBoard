package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import life.corals.onboarding.R;

public class Setup_QR_Code_Activity extends AppCompatActivity {

    private LinearLayout button_next;
    private TextView textView_scan, textView_generate_new_qr, textView_generate_content;
    private SharedPreferences sharedpreferences_open_screen, sharedpreferences_QR_Status;
    public static final String MyPREFERENCES_QR_STATUS = "MyPrefs_QR_Status";
    public static final String QR_STATUS = "QR_STATUS";
    public static final String QR_REQUIRED = "QR_REQUIRED";
    private ImageView imageView_back;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup__qr__code_);

        sharedpreferences_QR_Status = getSharedPreferences(MyPREFERENCES_QR_STATUS, Context.MODE_PRIVATE);
        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);

        textView_scan = (TextView) findViewById(R.id.text_scan_existing);
        textView_generate_new_qr = (TextView) findViewById(R.id.text_generate_new_QR);
        textView_generate_content = (TextView) findViewById(R.id.text_generate_content);

        button_next = (LinearLayout) findViewById(R.id.button_next_setup_QR);
        imageView_back = (ImageView) findViewById(R.id.back_arrow);

        status = sharedpreferences_QR_Status.getString(QR_STATUS, "");
        if (status.equals("1")) {
            textView_generate_content.setText("");
            textView_generate_content.setText("QR Code Scanned Successfully");
           // textView_generate_content.setTypeface(null, Typeface.BOLD);
            textView_generate_content.setTextColor(Color.parseColor("#48CAAD"));

           // textView_scan.setTypeface(null, Typeface.BOLD);
            textView_scan.setTextSize(17f);
            textView_generate_new_qr.setTextSize(16f);
            textView_generate_new_qr.setTypeface(null, Typeface.NORMAL);

        } else if (status.equals("0")) {
            textView_generate_content.setText("");
            textView_generate_content.setText("Invalid merchant QR code");
            //textView_generate_content.setTypeface(null, Typeface.BOLD);
            textView_generate_content.setTextColor(Color.parseColor("#FB5026"));

            //textView_scan.setTypeface(null, Typeface.BOLD);
            textView_scan.setTextSize(17f);
            textView_generate_new_qr.setTextSize(16f);
            textView_generate_new_qr.setTypeface(null, Typeface.NORMAL);
        }
        else if (status.equals("2")) {
            //textView_scan.setEnabled(false);
            textView_generate_content.setText("");
            textView_generate_content.setText(R.string.generate_new_qr);
            textView_generate_content.setTextColor(getResources().getColor(R.color.black));

            //textView_generate_new_qr.setTypeface(null, Typeface.BOLD);
            textView_generate_new_qr.setTextSize(17f);
            textView_scan.setTextSize(16f);
            textView_scan.setTypeface(null, Typeface.NORMAL);

        } else {
            textView_generate_content.setText("");
        }

  /*      if (getIntent().getExtras() != null) {
            status = getIntent().getStringExtra("QR_Status");
            if (status.equals("1")) {
                textView_generate_content.setText("");
                textView_generate_content.setText("QR Code Scanned Successfully");
                textView_generate_content.setTextColor(Color.parseColor("#48CAAD"));
                textView_generate_new_qr.setEnabled(false);

                SharedPreferences.Editor editor = sharedpreferences_QR_Status.edit();
                editor.putString(QR_STATUS, "1");
                editor.putBoolean(QR_REQUIRED, false);
                editor.commit();

            } else {
                textView_generate_content.setText("");
                textView_generate_content.setText("Invalid merchant QR code");
                textView_generate_content.setTextColor(Color.parseColor("#FB5026"));

                SharedPreferences.Editor editor = sharedpreferences_QR_Status.edit();
                editor.putString(QR_STATUS, "0");
                editor.commit();
            }
        }*/

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        textView_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Scan_QR_Code_Activity.class);
                startActivity(in);
                finish();
            }
        });

        textView_generate_new_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //textView_generate_new_qr.setTypeface(null, Typeface.BOLD);
                textView_generate_new_qr.setTextSize(17f);
                textView_scan.setTextSize(16f);
                textView_scan.setTypeface(null, Typeface.NORMAL);

                textView_generate_content.setText("");
                textView_generate_content.setText(R.string.generate_new_qr);
                textView_generate_content.setTextColor(getResources().getColor(R.color.black));

                SharedPreferences.Editor editor = sharedpreferences_QR_Status.edit();
                editor.putBoolean(QR_REQUIRED, true);
                editor.putString(QR_STATUS, "2");
                editor.commit();


            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = textView_generate_content.getText().toString();

                if (!TextUtils.isEmpty(content) && content != null && !content.equals("Invalid merchant QR code")) {
                    Intent in = new Intent(getApplicationContext(), Loyalty_Points_Activity.class);
                    startActivity(in);
                    finish();
                } else if (content.equals("Invalid merchant QR code")) {
                    Toast.makeText(Setup_QR_Code_Activity.this, "Please scan valid QR", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Setup_QR_Code_Activity.this, "Generate new QR or scan existing QR code!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "8");
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "8");
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "8");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Setup_QR_Code_Activity.this, Upload_Photo_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}
