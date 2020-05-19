package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import life.corals.onboarding.R;

public class Business_Details_Activity extends AppCompatActivity {

    private LinearLayout button_next;
    private EditText et_buss_name, et_add1, et_add2, et_add3, et_postal_code, et_reg_no;
    public static final String MyPREFERENCES_BUSSINESS_DETAILS = "MyPrefs_Business_Details";
    public static final String Business_Name = "businessName";
    public static final String Address1 = "address1";
    public static final String Address2 = "address2";
    public static final String Address3 = "address3";
    public static final String Postal_Code = "postalCode";
    public static final String Registration_No = "registrationNo";

    public static final String PREFIX = "PREFIX";
    public static final String CONTACT_NAME = "CONTACT_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String BUSSINESS_TYPE = "BUSSINESS_TYPE";
    public static final String BUSSINESS_TYPE_INDEX = "BUSSINESS_TYPE_INDEX";

    private SharedPreferences sharedpreferences, sharedpreferences_otp, sharedpreferences_cat_list;
    private String buss_name, add1, add2, add3, postal_code, reg_no, country;
    private SharedPreferences sharedpreferences_open_screen;
    private ImageView imageView_back;
    private TextView textView_country;
    private EditText editText_contact_name, editText_email;
    private Spinner spinner_add, spinner_buss_type;
    private String business_type_id;
    TextView textView_add1, textView_add2, textView_add3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__details_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES_BUSSINESS_DETAILS, Context.MODE_PRIVATE);
        sharedpreferences_otp = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
        sharedpreferences_cat_list = getSharedPreferences(SplashActivity.MyPREFERENCES_CATEGORY_LIST, Context.MODE_PRIVATE);

        textView_add1=findViewById(R.id.address1_watcher);
        textView_add2=findViewById(R.id.address2_watcher);
        textView_add3=findViewById(R.id.address3_watcher);
        et_buss_name = (EditText) findViewById(R.id.edit_bussiness_name);
        et_add1 = (EditText) findViewById(R.id.edit_bussiness_add1);
        et_add2 = (EditText) findViewById(R.id.edit_bussiness_add2);
        et_add3 = (EditText) findViewById(R.id.edit_bussiness_add3);
        et_postal_code = (EditText) findViewById(R.id.edit_bussiness_postal_code);
        et_reg_no = (EditText) findViewById(R.id.edit_bussiness_reg_no);
        button_next = (LinearLayout) findViewById(R.id.button_next_business_det);
        textView_country = (TextView) findViewById(R.id.text_country);
        spinner_buss_type = (Spinner) findViewById(R.id.spinner_buss_type);
        spinner_add = (Spinner) findViewById(R.id.spinner_prefix);
        editText_contact_name = (EditText) findViewById(R.id.edit_contact_name);
        editText_email = (EditText) findViewById(R.id.edit_email);

        buss_name = sharedpreferences.getString(Business_Name, "");
        add1 = sharedpreferences.getString(Address1, "");
        add2 = sharedpreferences.getString(Address2, "");
        add3 = sharedpreferences.getString(Address3, "");
        postal_code = sharedpreferences.getString(Postal_Code, "");
        reg_no = sharedpreferences.getString(Registration_No, "");
        country = sharedpreferences_otp.getString(OTP_Activity.COUNTRY, "");

        String name = sharedpreferences.getString(CONTACT_NAME, "");
        String email = sharedpreferences.getString(EMAIL, "");

        int maxLength = 50;
        et_add1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        et_add2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        et_add3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        et_add1.addTextChangedListener(textWatcher_add1);
        et_add2.addTextChangedListener(textWatcher_add2);
        et_add3.addTextChangedListener(textWatcher_add3);

        textView_country.setText(country);
        et_buss_name.setText(buss_name);
        et_add1.setText(add1);
        et_add2.setText(add2);
        et_add3.setText(add3);
        et_postal_code.setText(postal_code);
        et_reg_no.setText(reg_no);


        ArrayList<String> arrayList = new ArrayList<>();
        String biz_List = sharedpreferences_cat_list.getString(SplashActivity.CATEGORY_LIST, "");
        if (!TextUtils.isEmpty(biz_List)) {
            arrayList = new Gson().fromJson(biz_List, new TypeToken<ArrayList<String>>() {
            }.getType());
            ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
            a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_buss_type.setAdapter(a);
            spinner_buss_type.setPrompt("Select Business Type");
        }

        spinner_buss_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                business_type_id = String.valueOf(position + 1);
                if (business_type_id.length() == 1) {
                    business_type_id = "0" + business_type_id;
                }
                //Toast.makeText(Business_Details_Activity.this, ""+business_type_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String get_buss_type = sharedpreferences.getString(BUSSINESS_TYPE, "");

        if (!TextUtils.isEmpty(get_buss_type) && get_buss_type != null) {
            spinner_buss_type.setSelection(arrayList.indexOf(get_buss_type));
        }

        arrayList = new ArrayList<>();
        arrayList.add("Mr");
        arrayList.add("Ms");
        arrayList.add("Mrs");
        arrayList.add("Miss");

        spinner_add = (Spinner) findViewById(R.id.spinner_prefix);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add.setAdapter(aa);

        String get_index = sharedpreferences.getString(PREFIX, "");
        Log.d("Type_Index1--->", "" + get_index);
        spinner_add.setSelection(arrayList.indexOf(get_index));

        editText_contact_name.setText(name);
        editText_contact_name.setSelection(name.length());

        editText_email.setText(email);
        editText_email.setSelection(email.length());

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
                String b_type = null;
                String buss_name = et_buss_name.getText().toString().trim();
                String add1 = et_add1.getText().toString().trim();
                String add2 = et_add2.getText().toString().trim();
                String add3 = et_add3.getText().toString().trim();
                String postal_code = et_postal_code.getText().toString().trim();
                String reg_no = et_reg_no.getText().toString().trim();
                if (spinner_buss_type != null && spinner_buss_type.getSelectedItem() != null) {
                    b_type = spinner_buss_type.getSelectedItem().toString().trim();
                }

                String prefix = spinner_add.getSelectedItem().toString().trim();
                String contact_name = editText_contact_name.getText().toString().trim();
                String email = editText_email.getText().toString().trim();

                if (!TextUtils.isEmpty(buss_name)) {
                    if (buss_name.length() >= 5) {
                        if (!TextUtils.isEmpty(add1)) {
                            if (!TextUtils.isEmpty(postal_code)) {
                                if (!TextUtils.isEmpty(reg_no)) {
                                    if (!TextUtils.isEmpty(b_type)) {
                                        if (!TextUtils.isEmpty(prefix)) {
                                            if (contact_name.length() >= 5 && contact_name.length() <= 200) {
                                                if ((Patterns.EMAIL_ADDRESS.matcher(email).matches())) {

                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putString(Business_Name, buss_name);
                                                    editor.putString(Address1, add1);
                                                    editor.putString(Address2, add2);
                                                    editor.putString(Address3, add3);
                                                    editor.putString(Postal_Code, postal_code);
                                                    editor.putString(Registration_No, reg_no);
                                                    editor.putString(BUSSINESS_TYPE, b_type);
                                                    editor.putString(BUSSINESS_TYPE_INDEX, business_type_id);
                                                    editor.putString(PREFIX, prefix);
                                                    editor.putString(CONTACT_NAME, contact_name);
                                                    editor.putString(EMAIL, email);
                                                    editor.commit();

                                                    Intent in = new Intent(Business_Details_Activity.this, Intro_Business_Activity.class);
                                                    startActivity(in);
                                                    finish();
                                                } else {
                                                    editText_email.setError("Enter valid email");
                                                    editText_email.requestFocus();
                                                }
                                            } else {
                                                editText_contact_name.setError("Enter valid contact name");
                                                editText_contact_name.requestFocus();
                                            }
                                        } else {
                                            Toast.makeText(Business_Details_Activity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                        Toast.makeText(Business_Details_Activity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    et_reg_no.setError("Enter valid registration number");
                                    et_reg_no.requestFocus();
                                }

                            } else {
                                et_postal_code.setError("Enter valid postal code");
                                et_postal_code.requestFocus();
                            }

                        } else {
                            et_add1.setError("Enter valid address");
                            et_add1.requestFocus();
                        }


                    } else {
                        et_buss_name.setError("Min 5 chars");
                        et_buss_name.requestFocus();
                    }

                } else {
                    et_buss_name.setError("Enter valid business name");
                    et_buss_name.requestFocus();
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "4");
        editor_os.commit();

        String buss_name = et_buss_name.getText().toString().trim();
        String add1 = et_add1.getText().toString().trim();
        String add2 = et_add2.getText().toString().trim();
        String add3 = et_add3.getText().toString().trim();
        String postal_code = et_postal_code.getText().toString().trim();
        String reg_no = et_reg_no.getText().toString().trim();
        String prefix = spinner_add.getSelectedItem().toString().trim();
        String contact_name = editText_contact_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        String b_type = null;
        if (spinner_buss_type.getChildCount() > 0) {
            b_type = spinner_buss_type.getSelectedItem().toString().trim();
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Business_Name, buss_name);
        editor.putString(Address1, add1);
        editor.putString(Address2, add2);
        editor.putString(Address3, add3);
        editor.putString(Postal_Code, postal_code);
        editor.putString(Registration_No, reg_no);
        editor.putString(BUSSINESS_TYPE, b_type);
        editor.putString(BUSSINESS_TYPE_INDEX, business_type_id);
        editor.putString(PREFIX, prefix);
        editor.putString(CONTACT_NAME, contact_name);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "4");
        editor_os.commit();

        String buss_name = et_buss_name.getText().toString().trim();
        String add1 = et_add1.getText().toString().trim();
        String add2 = et_add2.getText().toString().trim();
        String add3 = et_add3.getText().toString().trim();
        String postal_code = et_postal_code.getText().toString().trim();
        String reg_no = et_reg_no.getText().toString().trim();
        String prefix = spinner_add.getSelectedItem().toString().trim();
        String contact_name = editText_contact_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        String b_type = null;
        if (spinner_buss_type.getChildCount() > 0) {
            b_type = spinner_buss_type.getSelectedItem().toString().trim();
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Business_Name, buss_name);
        editor.putString(Address1, add1);
        editor.putString(Address2, add2);
        editor.putString(Address3, add3);
        editor.putString(Postal_Code, postal_code);
        editor.putString(Registration_No, reg_no);
        editor.putString(BUSSINESS_TYPE, b_type);
        editor.putString(BUSSINESS_TYPE_INDEX, business_type_id);
        editor.putString(PREFIX, prefix);
        editor.putString(CONTACT_NAME, contact_name);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "4");
        editor_os.commit();

        String buss_name = et_buss_name.getText().toString().trim();
        String add1 = et_add1.getText().toString().trim();
        String add2 = et_add2.getText().toString().trim();
        String add3 = et_add3.getText().toString().trim();
        String postal_code = et_postal_code.getText().toString().trim();
        String reg_no = et_reg_no.getText().toString().trim();
        String prefix = spinner_add.getSelectedItem().toString().trim();
        String contact_name = editText_contact_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        String b_type = null;
        if (spinner_buss_type.getChildCount() > 0) {
            b_type = spinner_buss_type.getSelectedItem().toString().trim();
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Business_Name, buss_name);
        editor.putString(Address1, add1);
        editor.putString(Address2, add2);
        editor.putString(Address3, add3);
        editor.putString(Postal_Code, postal_code);
        editor.putString(Registration_No, reg_no);
        editor.putString(BUSSINESS_TYPE, b_type);
        editor.putString(BUSSINESS_TYPE_INDEX, business_type_id);
        editor.putString(PREFIX, prefix);
        editor.putString(CONTACT_NAME, contact_name);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Business_Details_Activity.this, Setup_Steps_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private final TextWatcher textWatcher_add1 = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textView_add1.setText(String.valueOf(s.length() + "/50"));
            //textView_add1.setTextColor(getResources().getColor(R.color.red));

        }
        public void afterTextChanged(Editable s) {
        }
    };


    private final TextWatcher textWatcher_add2= new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView_add2.setText(String.valueOf(s.length() + "/50"));
                //textView_add2.setTextColor(getResources().getColor(R.color.red));

        }

        public void afterTextChanged(Editable s) {
        }
    };


    private final TextWatcher textWatcher_add3 = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

                textView_add3.setText(String.valueOf(s.length() + "/50"));
                //textView_add3.setTextColor(getResources().getColor(R.color.red));

        }

        public void afterTextChanged(Editable s) {
        }
    };


}
