package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import life.corals.onboarding.R;

public class Setup_Steps_Activity extends AppCompatActivity {

    private LinearLayout button_next;
    private SharedPreferences sharedpreferences_open_screen;
    private ImageView imageView_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup__steps_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        button_next = (LinearLayout) findViewById(R.id.button_next_setup);

        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent in = new Intent(getApplicationContext(), Business_Details_Activity.class);
                    startActivity(in);
                    finish();

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "3");
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "3");
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "3");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(Setup_Steps_Activity.this,Terms_Conditions_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }


}
