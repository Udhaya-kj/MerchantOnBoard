package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.internal.SignInConnectionListener;

import life.corals.onboarding.R;

public class Generate_New_QR_Activity extends AppCompatActivity {


    private TextView textView_scan_qr, textView_cancel;
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__new__qr_);

        textView_scan_qr = (TextView) findViewById(R.id.text_scan_qr);
        textView_cancel = (TextView) findViewById(R.id.text_cancel);

        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Generate_New_QR_Activity.this, Setup_QR_Code_Activity.class);
                startActivity(in);
                finish();
            }
        });

        textView_scan_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Scan_QR_Code_Activity.class);
                startActivity(in);
                finish();
            }
        });

        textView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Setup_QR_Code_Activity.class);
                startActivity(in);
                finish();
            }
        });
    }
}
