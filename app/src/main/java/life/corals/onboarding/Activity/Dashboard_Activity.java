package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import life.corals.onboarding.R;

public class Dashboard_Activity extends AppCompatActivity {

    private CardView cardView_new, cardView_existing;
    private Button button_next;
    private ImageView imageView_back;
    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);

        cardView_new = findViewById(R.id.cardview_new_merchant);
        cardView_existing = findViewById(R.id.cardview_existing_merchant);
        button_next=findViewById(R.id.button_submit_dashboard);
        imageView_back=findViewById(R.id.image_back_dashboard);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cardView_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = R.color.transparent_orange;
                cardView_new.setForeground(new ColorDrawable(ContextCompat.getColor(Dashboard_Activity.this, color)));
                cardView_existing.setForeground(null);
                //cardView_new.setAlpha((float) 0.4);
                //cardView_existing.setAlpha(1);
                id=1;

            }
        });

        cardView_existing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = R.color.transparent_orange;
                cardView_existing.setForeground(new ColorDrawable(ContextCompat.getColor(Dashboard_Activity.this, color)));
                cardView_new.setForeground(null);
                //cardView_existing.setAlpha((float) 0.4);
                //cardView_new.setAlpha(1);
                id=2;
            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(Dashboard_Activity.this,Enter_PIN_Activity.class);
                startActivity(in);
                finish();
                Toast.makeText(Dashboard_Activity.this, ""+id, Toast.LENGTH_SHORT).show();
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

}
