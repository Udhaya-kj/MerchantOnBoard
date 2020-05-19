package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import life.corals.onboarding.R;
import life.corals.onboarding.Utils.ScrollToast;

public class Terms_Conditions_Activity extends AppCompatActivity {

    private LinearLayout button_next;
    private ScrollView scrollView;
    private int scrollY = 0;
    private LinearLayout linearLayout_agree;
    private TextView textView_terms_conditions;
    private CheckBox checkBox_terms;
    private SharedPreferences sharedpreferences_open_screen;
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms__conditions_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);

        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        linearLayout_agree = (LinearLayout) findViewById(R.id.layout_agree);
        textView_terms_conditions = (TextView) findViewById(R.id.terms_and_conditions_txtvw);
        checkBox_terms = (CheckBox) findViewById(R.id.checkbox_terms_condtions);
        button_next = (LinearLayout) findViewById(R.id.button_next_terms_condtions);
        button_next.setEnabled(false);
        checkBox_terms.setEnabled(false);
        textView_terms_conditions.setText(Html.fromHtml(getResources().getString(R.string.termsconditonslongvalue)));
        ScrollToast scrollToast = new ScrollToast(this);
        scrollToast.showMessage("You should scroll down to Next button", getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));

        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent in=new Intent(Terms_Conditions_Activity.this,Welcome_Activity.class);
                startActivity(in);
                finish();*/
               onBackPressed();
            }
        });


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                scrollY = scrollView.getScrollY();
                if (scrollY > 1000) {
                    //linearLayout_agree.setVisibility(View.VISIBLE);
                    checkBox_terms.setEnabled(true);
                    linearLayout_agree.setAlpha(1);
                } /*else {
                    //linearLayout_agree.setVisibility(View.GONE);
                    linearLayout_agree.setAlpha((float) 0.4);
                }*/
            }
        });

        checkBox_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    button_next.setAlpha(1);
                    button_next.setEnabled(true);

                } else {
                    button_next.setAlpha((float) 0.4);
                    button_next.setEnabled(false);


                }
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_terms.isChecked()) {
                    Intent in = new Intent(getApplicationContext(), Setup_Steps_Activity.class);
                    startActivity(in);
                    finish();
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "2");
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "2");
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "2");
        editor.commit();
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
}
