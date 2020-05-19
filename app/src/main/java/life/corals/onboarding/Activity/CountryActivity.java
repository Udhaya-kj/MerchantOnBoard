package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import life.corals.onboarding.R;
import life.corals.onboarding.Utils.HttpHandler;
import life.corals.onboarding.Utils.NetworkUtil;

public class CountryActivity extends AppCompatActivity {
    private Spinner spinner;
    private LinearLayout button_next;
    private ProgressDialog pDialog;

    private boolean network_status;
    private LinearLayout linearLayout;
    SharedPreferences sharedpreferences,sharedpreferences_otp_count;
    int min_l, max_l;
    private String currency, pos,mob_dial_code,start_digit,country_code;
    private EditText editText_mob;
    private ArrayList<String> country_code_list, symbol_list, mobile_start_digits_list, mobile_min_length_list, mobile_max_length_list, country_name_list, time_zone_list,dial_code_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        linearLayout = findViewById(R.id.layout_country);
        button_next = (LinearLayout) findViewById(R.id.button_next_country);
        spinner = (Spinner) findViewById(R.id.spinner_country);
        editText_mob = (EditText) findViewById(R.id.edit_mobile);

        country_code_list = new ArrayList<>();
        symbol_list = new ArrayList<>();
        mobile_start_digits_list = new ArrayList<>();
        mobile_min_length_list = new ArrayList<>();
        mobile_max_length_list = new ArrayList<>();
        country_name_list = new ArrayList<>();
        time_zone_list = new ArrayList<>();
        dial_code_list = new ArrayList<>();

        sharedpreferences_otp_count = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_COUNT, Context.MODE_PRIVATE);

        if (getIntent().getExtras() != null) {
            country_code_list = (ArrayList<String>) getIntent().getSerializableExtra("code");
            symbol_list = (ArrayList<String>) getIntent().getSerializableExtra("symbol");
            mobile_start_digits_list = (ArrayList<String>) getIntent().getSerializableExtra("start_dg");
            mobile_min_length_list = (ArrayList<String>) getIntent().getSerializableExtra("min_l");
            mobile_max_length_list = (ArrayList<String>) getIntent().getSerializableExtra("max_l");
            country_name_list = (ArrayList<String>) getIntent().getSerializableExtra("c_name");
            time_zone_list = (ArrayList<String>) getIntent().getSerializableExtra("zone");
            dial_code_list = (ArrayList<String>) getIntent().getSerializableExtra("dial_code");
        }
        Log.d("C_List---->", "" + country_code_list.size());

        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country_name_list);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa1);
        spinner.setPrompt("Select Country");

        // new CountryActivity.GetContacts().execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pos = String.valueOf(position);

                final String code = country_code_list.get(position);
                final String sym = symbol_list.get(position);
                final String start = mobile_start_digits_list.get(position);
                final String min = mobile_min_length_list.get(position);
                final String max = mobile_max_length_list.get(position);
                final String country = country_name_list.get(position);
                final String zone = time_zone_list.get(position);
                final String dial_code = dial_code_list.get(position);

                country_code=code;
                max_l = Integer.parseInt(max);
                min_l = Integer.parseInt(min);
                start_digit = start;
                currency = sym;
                mob_dial_code=dial_code;

                /*SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(ccode, code);
                editor.putString(csymbol, sym);
                editor.putString(cstart_digit, start);
                editor.putString(cmin_l, min);
                editor.putString(cmax_l, max);
                editor.putString(cc_name, country);
                editor.putString(czone, zone);
                editor.putString(c_dial_code, zone);
                editor.commit();*/

                //Toast.makeText(CountryActivity.this, "code :" + code + ",sym :" + sym + ",country :" + country + ",max_l :" + max_l + ",min_l : " + min_l, Toast.LENGTH_SHORT).show();

                if (editText_mob.getText().length() > 0) {
                    editText_mob.setText("");
                    editText_mob.requestFocus();
                }


                Log.d("Max---->", "" + max_l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editText_mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText_mob.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_l)});
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                network_status = NetworkUtil.getConnectivityStatusString(CountryActivity.this);
                Log.d("NETWORK--->", "" + network_status);
                int otp_cnt = sharedpreferences_otp_count.getInt(OTP_Activity.OTP_COUNT, 0);

                Log.d("Otp_Count--->", "" + otp_cnt);
                if (otp_cnt >= 2) {

                    View dialogView = LayoutInflater.from(CountryActivity.this).inflate(R.layout.alert_otp, null, false);
                    final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CountryActivity.this);
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
                    if (network_status) {
                        String get_mobile_number = editText_mob.getText().toString();

              /*  if (!TextUtils.isEmpty(get_mobile_number) && get_mobile_number != null) {
                    if (get_mobile_number.length() >= min_l) {
                        Intent in = new Intent(CountryActivity.this, OTP_Activity.class);
                        in.putExtra("mobile", get_mobile_number);
                        in.putExtra("currency", currency);
                        in.putExtra("country", spinner.getSelectedItem().toString());
                        in.putExtra("dial_code", mob_dial_code);
                        startActivity(in);
                        finish();

                        *//*startActivity(new Intent(CountryActivity.this, OTP_Activity.class).putExtra("mobile", get_mobile_number).putExtra("currency", currency).putExtra("country", spinner.getSelectedItem().toString()));
                        CountryActivity.this.finish();*//*
                    } else {
                        editText_mob.setError("Enter valid mobile number");
                        editText_mob.requestFocus();
                    }
                } else {
                    editText_mob.setError("Enter valid mobile number");
                    editText_mob.requestFocus();
                }*/


                        if (!TextUtils.isEmpty(get_mobile_number) && get_mobile_number != null) {
                            //int get_mob = editText_mob.getText().length();

                            Log.d("Mobile-->", "" + get_mobile_number + "," + start_digit);
                            String s = get_mobile_number.substring(0, 1);

                            if (get_mobile_number.length() >= min_l && get_mobile_number.length() <= max_l) {

                                String ss = start_digit.replace("[", "");
                                String ss1 = ss.replace("]", "");
                                List<String> myList = new ArrayList<String>();
                                String[] array = ss1.split(",");
                                myList = Arrays.asList(array);

                                Log.d("Digit---->", "" + s + "," + ss1);

                                if (myList.contains(s)) {
                                    Intent in = new Intent(CountryActivity.this, OTP_Activity.class);
                                    in.putExtra("mobile", get_mobile_number);
                                    in.putExtra("currency", currency);
                                    in.putExtra("country", spinner.getSelectedItem().toString());
                                    in.putExtra("dial_code", mob_dial_code);
                                    in.putExtra("country_code", country_code);
                                    startActivity(in);
                                    finish();

                                } else {
                                    Toast.makeText(CountryActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(CountryActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            editText_mob.requestFocus();
                            editText_mob.setError("Enter valid mobile number");
                            //Toast.makeText(CountryActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Please check your connection and try again", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }


                }
            }
        });

    }


}
