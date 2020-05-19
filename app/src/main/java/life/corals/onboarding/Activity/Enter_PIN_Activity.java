package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import life.corals.onboarding.R;

public class Enter_PIN_Activity extends AppCompatActivity {


    private ImageView imageView_back;
    private EditText edit_pin,edit_mob;
    private Button button_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__pin_);

        imageView_back=findViewById(R.id.back_arrow_pin);
        edit_pin=findViewById(R.id.edit_pin);
        edit_mob=findViewById(R.id.edit_mob_no);
        button_next=findViewById(R.id.button_next_pin);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pin=edit_pin.getText().toString();
                String mob=edit_mob.getText().toString();

                if(!TextUtils.isEmpty(pin) && !TextUtils.isEmpty(mob)) {
                    Intent in = new Intent(Enter_PIN_Activity.this, Dashboard_Activity.class);
                    startActivity(in);
                    finish();
                }
                else {
                    Toast.makeText(Enter_PIN_Activity.this, "Enter valid data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
