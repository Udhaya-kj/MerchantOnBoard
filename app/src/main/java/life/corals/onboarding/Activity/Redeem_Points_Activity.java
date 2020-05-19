package life.corals.onboarding.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import life.corals.onboarding.R;
import life.corals.onboarding.client.model.RedemptionList;
import life.corals.onboarding.client.model.SetUpRedemptionList;

public class Redeem_Points_Activity extends AppCompatActivity {

    private EditText editText_redeem_bonus, editText_redeem_title, editText_redeem_desc;
    TextView editText_start_dt, editText_expiry_dt, editText_start_time, editText_expiry_time;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    private Button button_next;
    private String date_active, date_expiry, time_active, time_expiry, pos;
    DatePickerDialog datePickerDialog;
    private ImageView imageView_back;
    private SharedPreferences sharedpreferences_open_screen;
    Button btn_yes_sday, btn_yes_mnday, btn_yes_tsday, btn_yes_wedday, btn_yes_trsday, btn_yes_fdday, btn_yes_strday;
    String activeDays = null;
    RedemptionList redemptionList = new RedemptionList();
    private List<RedemptionList> list;
/*    SetUpRedemptionList redemptionList = new SetUpRedemptionList();
    private List<SetUpRedemptionList> list;*/
    boolean isActiveSunday = true, isActiveMonday = true, isActiveTuesday = true, isActiveWednesday = true, isActiveThursday = true, isActiveFriday = true, isActiveSaturday = true;
    private SharedPreferences sharedpreferences_add_redeem;
    private Gson gson;

    public static final String MyPREFERENCES_ADD_REDEEM = "MyPrefs_Add_Redeem";
    public static final String ADD_REDEEM = "ADD_REDEEM";
    int sharable = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem__points_);
        list = new ArrayList<>();
        gson = new Gson();

        sharedpreferences_add_redeem = getSharedPreferences(MyPREFERENCES_ADD_REDEEM, Context.MODE_PRIVATE);
        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        button_next = (Button) findViewById(R.id.button_next_redeem_point);
        editText_redeem_bonus = (EditText) findViewById(R.id.edit_redeem_bonus);
        editText_start_dt = (TextView) findViewById(R.id.edit_start_date);
        editText_expiry_dt = (TextView) findViewById(R.id.edit_expiry_date);

        editText_start_time = (TextView) findViewById(R.id.edit_start_time);
        editText_expiry_time = (TextView) findViewById(R.id.edit_expiry_time);
        editText_redeem_title = (EditText) findViewById(R.id.edit_redeem_title);
        editText_redeem_desc = (EditText) findViewById(R.id.edit_redeem_desc);
        imageView_back = (ImageView) findViewById(R.id.back_arrow);

        btn_yes_sday = (Button) findViewById(R.id.btn_yes_sunday);
        btn_yes_mnday = (Button) findViewById(R.id.btn_yes_monday);
        btn_yes_tsday = (Button) findViewById(R.id.btn_yes_tuesday);
        btn_yes_wedday = (Button) findViewById(R.id.btn_yes_wedsnesday);
        btn_yes_trsday = (Button) findViewById(R.id.btn_yes_thursday);
        btn_yes_fdday = (Button) findViewById(R.id.btn_yes_friday);
        btn_yes_strday = (Button) findViewById(R.id.btn_yes_saturday);

      /*  editText_redeem_desc.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editText_redeem_desc.setRawInputType(InputType.TYPE_CLASS_TEXT);*/
        editText_start_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activate Date" + "</u>  </font>"));
        editText_expiry_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Date" + "</u>  </font>"));
        editText_start_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activate Time" + "</u>  </font>"));
        editText_expiry_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Time" + "</u>  </font>"));

        if (getIntent().getExtras() != null) {
            pos = getIntent().getStringExtra("position");
            String title = getIntent().getStringExtra("title");
            String desc = getIntent().getStringExtra("desc");
            String point = getIntent().getStringExtra("points");
            date_active = getIntent().getStringExtra("act_date");
            time_active = getIntent().getStringExtra("act_time");
            date_expiry = getIntent().getStringExtra("exp_date");
            time_expiry = getIntent().getStringExtra("exp_time");
            String act_days = getIntent().getStringExtra("act_days");

            char days = act_days.charAt(0);
            Log.d("Data--->", pos + "," + act_days + "," + time_active + "," + time_expiry+ "," + date_active + "," + date_expiry);
            editText_redeem_title.setText(title);
            editText_redeem_title.setSelection(title.length());
            editText_redeem_desc.setText(desc);
            editText_redeem_bonus.setText(point);
            editText_start_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + date_active + "</u>  </font>"));
            editText_expiry_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + date_expiry + "</u>  </font>"));
            editText_start_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + time_active + "</u>  </font>"));
            editText_expiry_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + time_expiry + "</u>  </font>"));

            if (!TextUtils.isEmpty(act_days)) {
                char days0 = act_days.charAt(0);
                if (String.valueOf(days0).equals("y")) {
                    btn_yes_sday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveSunday = true;
                } else {
                    btn_yes_sday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveSunday = false;
                }

                char days1 = act_days.charAt(1);
                if (String.valueOf(days1).equals("y")) {
                    btn_yes_mnday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveMonday = true;
                } else {
                    btn_yes_mnday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveMonday = false;
                }

                char days2 = act_days.charAt(2);
                if (String.valueOf(days2).equals("y")) {
                    btn_yes_tsday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveTuesday = true;
                } else {
                    btn_yes_tsday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveTuesday = false;
                }

                char days3 = act_days.charAt(3);
                if (String.valueOf(days3).equals("y")) {
                    btn_yes_wedday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveWednesday = true;
                } else {
                    btn_yes_wedday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveWednesday = false;
                }

                char days4 = act_days.charAt(4);
                if (String.valueOf(days4).equals("y")) {
                    btn_yes_trsday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveThursday = true;
                } else {
                    btn_yes_trsday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveThursday = false;
                }

                char days5 = act_days.charAt(5);
                if (String.valueOf(days5).equals("y")) {
                    btn_yes_fdday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveFriday = true;
                } else {
                    btn_yes_fdday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveFriday = false;
                }

                char days6 = act_days.charAt(6);
                if (String.valueOf(days6).equals("y")) {
                    btn_yes_strday.setBackground(getResources().getDrawable(R.drawable.yes_select_selector));
                    isActiveSaturday = true;
                } else {
                    btn_yes_strday.setBackground(getResources().getDrawable(R.drawable.no_select_selector));
                    isActiveSaturday = false;
                }
            }

        }
        String add_redeem = sharedpreferences_add_redeem.getString(ADD_REDEEM, "");
        Type type = new TypeToken<List<RedemptionList>>() {
        }.getType();
        if (!TextUtils.isEmpty(add_redeem)) {
            list = gson.fromJson(add_redeem, type);
        }




        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editText_start_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Redeem_Points_Activity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    date_active = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    date_active = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    date_active = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    date_active = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                // editText_start_dt.setText(date_active);
                                editText_start_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + date_active + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();
            }
        });

        editText_expiry_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Redeem_Points_Activity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    date_expiry = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    date_expiry = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    date_expiry = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    date_expiry = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                //editText_expiry_dt.setText(date_expiry);
                                editText_expiry_dt.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + date_expiry + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });

        editText_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartTime();
            }
        });

        editText_expiry_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExpiryTime();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_redeems();
            }
        });


        btn_yes_sday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //s_status = "1";
                if (isActiveSunday) {
                    btn_yes_sday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveSunday = false;
                } else {
                    btn_yes_sday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveSunday = true;
                }
            }
        });

        btn_yes_mnday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mn_status = "1";
                btn_yes_mnday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveMonday) {
                    btn_yes_mnday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveMonday = false;
                } else {
                    btn_yes_mnday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveMonday = true;
                }
            }
        });


        btn_yes_tsday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ts_status = "1";
                btn_yes_tsday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveTuesday) {
                    btn_yes_tsday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveTuesday = false;
                } else {
                    btn_yes_tsday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveTuesday = true;
                }
            }
        });


        btn_yes_wedday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*wed_status = "1";
                btn_yes_wedday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveWednesday) {
                    btn_yes_wedday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveWednesday = false;
                } else {
                    btn_yes_wedday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveWednesday = true;
                }

            }
        });


        btn_yes_trsday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* thrs_status = "1";
                btn_yes_trsday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveThursday) {
                    btn_yes_trsday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveThursday = false;
                } else {
                    btn_yes_trsday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveThursday = true;
                }

            }
        });


        btn_yes_fdday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* fr_status = "1";
                btn_yes_fdday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveFriday) {
                    btn_yes_fdday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveFriday = false;
                } else {
                    btn_yes_fdday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveFriday = true;
                }

            }
        });


        btn_yes_strday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  st_status = "1";
                btn_yes_strday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveSaturday) {
                    btn_yes_strday.setBackgroundResource(R.drawable.no_select_selector);
                    isActiveSaturday = false;
                } else {
                    btn_yes_strday.setBackgroundResource(R.drawable.yes_select_selector);
                    isActiveSaturday = true;
                }
            }
        });

    }

    public String getActDays() {
        if (isActiveSunday) {
            activeDays = "y";
        } else {
            activeDays = "n";
        }

        if (isActiveMonday) {
            activeDays = activeDays + "" + "y";
        } else {
            activeDays = activeDays + "" + "n";
        }

        if (isActiveTuesday) {
            activeDays = activeDays + "" + "y";
        } else {
            activeDays = activeDays + "" + "n";
        }

        if (isActiveWednesday) {
            activeDays = activeDays + "" + "y";
        } else {
            activeDays = activeDays + "" + "n";
        }

        if (isActiveThursday) {
            activeDays = activeDays + "" + "y";
        } else {
            activeDays = activeDays + "" + "n";
        }

        if (isActiveFriday) {
            activeDays = activeDays + "" + "y";
        } else {
            activeDays = activeDays + "" + "n";
        }

        if (isActiveSaturday) {
            activeDays = activeDays + "" + "y";
        } else {
            activeDays = activeDays + "" + "n";
        }

        return activeDays;
    }

    public void getStartTime() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            time_active = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            time_active = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            time_active = hourOfDay + ":" + min;

                        } else {
                            time_active = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + time_active);
                        //editText_start_time.setText(time_active);
                        editText_start_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + time_active + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void getExpiryTime() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            time_expiry = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            time_expiry = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            time_expiry = hourOfDay + ":" + min;

                        } else {
                            time_expiry = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + time_expiry);
                        //editText_expiry_time.setText(time_expiry);
                        editText_expiry_time.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + time_expiry + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void validate_redeems() {

        final String title = editText_redeem_title.getText().toString();
        final String desc = editText_redeem_desc.getText().toString();
        final String bonus = editText_redeem_bonus.getText().toString();
        final String exp_date = editText_expiry_dt.getText().toString().trim();
        final String start_date = editText_start_dt.getText().toString().trim();
        final String exp_time = editText_expiry_time.getText().toString().trim();
        final String start_time = editText_start_time.getText().toString().trim();
        final String getActDays = getActDays();
        Log.i("app---->", start_date + " , " + exp_date + "," + start_time + " , " + exp_time + " , " + sharable);
        //&& Integer.parseInt(bonus) >= 1 && Integer.parseInt(bonus) <= 999999

        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 10) {
                if (!TextUtils.isEmpty(desc)) {
                    if (desc.length() >= 15) {
                        if (!TextUtils.isEmpty(bonus) && !bonus.startsWith("0")) {
                            if (!TextUtils.isEmpty(start_date) && !start_date.equals("Select Activate Date")) {
                                if (!TextUtils.isEmpty(exp_date) && !exp_date.equals("Select Expiry Date")) {
                                    if (!TextUtils.isEmpty(start_time) && !start_time.equals("Select Activate Time")) {
                                        if (!TextUtils.isEmpty(exp_time) && !exp_time.equals("Select Expiry Time")) {
                                            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                            try {
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
                                                String act_dt = start_date + " " + start_time;
                                                String exp_dt = exp_date + " " + exp_time;
                                                Date date1 = sdf.parse(act_dt);
                                                Date date2 = sdf.parse(exp_dt);

                                                if (date1.compareTo(date2) < 0) {
                                                    Log.i("app", "Date1 is before Date2");
                                                    if (TextUtils.isEmpty(pos)) {
                                                    /*    Intent in=new Intent(Redeem_Points_Activity.this,Redeem_Preview_Activity.class);
                                                        in.putExtra("position",pos);
                                                        in.putExtra("title",title);
                                                        in.putExtra("desc",desc);
                                                        in.putExtra("points",bonus);
                                                        in.putExtra("act_date",start_date);
                                                        in.putExtra("end_date",exp_date);
                                                        in.putExtra("act_time",start_time);
                                                        in.putExtra("end_time",exp_time);
                                                        in.putExtra("actdays",getActDays);
                                                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(in);*/

                                                        View dialogView = LayoutInflater.from(Redeem_Points_Activity.this).inflate(R.layout.layout_create_redeem1, null, false);
                                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Redeem_Points_Activity.this);
                                                        Button cancel_btn = (Button) dialogView.findViewById(R.id.cancel_button);
                                                        Button ok_btn = (Button) dialogView.findViewById(R.id.ok_button);
                                                        TextView textView_title = (TextView) dialogView.findViewById(R.id.text_title);
                                                        TextView textView_desc = (TextView) dialogView.findViewById(R.id.text_desc);
                                                        TextView textView_bonus = (TextView) dialogView.findViewById(R.id.text_bonus);
                                                        TextView from_dt = (TextView) dialogView.findViewById(R.id.from_date);
                                                        TextView to_dt = (TextView) dialogView.findViewById(R.id.to_date);
                                                        TextView s_time = (TextView) dialogView.findViewById(R.id.start_time);
                                                        TextView e_time = (TextView) dialogView.findViewById(R.id.end_time);
                                                        TextView textView_sunday = (TextView) dialogView.findViewById(R.id.sunday);
                                                        TextView textView_monday = (TextView) dialogView.findViewById(R.id.monday);
                                                        TextView textView_tuesday = (TextView) dialogView.findViewById(R.id.tuesday);
                                                        TextView textView_wednesday = (TextView) dialogView.findViewById(R.id.wednesday);
                                                        TextView textView_thursday = (TextView) dialogView.findViewById(R.id.thursday);
                                                        TextView textView_friday = (TextView) dialogView.findViewById(R.id.friday);
                                                        TextView textView_saturday = (TextView) dialogView.findViewById(R.id.saturday);
                                                        textView_title.setText(title+" for "+bonus+" points");
                                                        textView_desc.setText(desc);
                                                        if (bonus.equals("0")) {
                                                            textView_bonus.setVisibility(View.GONE);
                                                        } else {
                                                            textView_bonus.setVisibility(View.VISIBLE);
                                                            textView_bonus.setText(bonus);
                                                        }
                                                        String startDate_conv = null, endDate_conv = null;
                                                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                        SimpleDateFormat d = new SimpleDateFormat("yyyy-MMM-dd");
                                                        try {
                                                            Date convertedDate_S = inputFormat.parse(start_date);
                                                            Date convertedDate_E = inputFormat.parse(exp_date);
                                                            startDate_conv = d.format(convertedDate_S);
                                                            endDate_conv = d.format(convertedDate_E);

                                                        } catch (ParseException e) {
                                                            Log.d("Date--->", "" + e.getMessage());
                                                        }

                                                        from_dt.setText(startDate_conv);
                                                        to_dt.setText(endDate_conv);
                                                        s_time.setText(start_time);
                                                        e_time.setText(exp_time);

                                                        if (!TextUtils.isEmpty(getActDays)) {
                                                            char get1 = getActDays.charAt(0);
                                                            char get2 = getActDays.charAt(1);
                                                            char get3 = getActDays.charAt(2);
                                                            char get4 = getActDays.charAt(3);
                                                            char get5 = getActDays.charAt(4);
                                                            char get6 = getActDays.charAt(5);
                                                            char get7 = getActDays.charAt(6);
                                                            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);
                                                            //Sunday
                                                            if (String.valueOf(get1).equals("y")) {
                                                                textView_sunday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_sunday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Monday
                                                            if (String.valueOf(get2).equals("y")) {
                                                                textView_monday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_monday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Tuesday
                                                            if (String.valueOf(get3).equals("y")) {
                                                                textView_tuesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_tuesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Wednesday
                                                            if (String.valueOf(get4).equals("y")) {
                                                                textView_wednesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_wednesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Thursday
                                                            if (String.valueOf(get5).equals("y")) {
                                                                textView_thursday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_thursday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Friday
                                                            if (String.valueOf(get6).equals("y")) {
                                                                textView_friday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_friday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Saturday
                                                            if (String.valueOf(get7).equals("y")) {
                                                                textView_saturday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_saturday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }
                                                        }
                                                        //setting the view of the builder to our custom view that we already inflated
                                                        builder.setView(dialogView);
                                                        builder.setCancelable(false);
                                                        final AlertDialog alertDialog = builder.create();
                                                        alertDialog.show();

                                                        ok_btn.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {

                                                                alertDialog.dismiss();
                                                                redemptionList.setRedeemTitle(title);
                                                                redemptionList.setRedeemDescription(desc);
                                                                redemptionList.setRedeemDepositAmt("0.0");
                                                                redemptionList.setRedeemPoints(bonus);
                                                                redemptionList.setRedeemActivedt(start_date);
                                                                redemptionList.setRedeemActtime(start_time);
                                                                redemptionList.setRedeemExpdt(exp_date);
                                                                redemptionList.setRedeemEndtime(exp_time);
                                                                redemptionList.setRedeemType("P");
                                                                redemptionList.setRedeemActivedays(getActDays);

                                                                list.add(redemptionList);
                                                                Log.d("redemptionList======", "onClickAfter: " + list);
                                                                String redeems = new Gson().toJson(list);

                                                                SharedPreferences.Editor editor = sharedpreferences_add_redeem.edit();
                                                                editor.putString(ADD_REDEEM, redeems);
                                                                editor.commit();

                                                                Intent in = new Intent(Redeem_Points_Activity.this, Add_Redemption_Activity.class);
                                                                startActivity(in);
                                                                finish();

                                                            }

                                                        });
                                                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                alertDialog.dismiss();
                                                            }
                                                        });

                                                    } else {
                                             /*           Intent in=new Intent(Redeem_Points_Activity.this,Redeem_Preview_Activity.class);
                                                        in.putExtra("position",pos);
                                                        in.putExtra("title",title);
                                                        in.putExtra("desc",desc);
                                                        in.putExtra("points",bonus);
                                                        in.putExtra("act_date",start_date);
                                                        in.putExtra("end_date",exp_date);
                                                        in.putExtra("act_time",start_time);
                                                        in.putExtra("end_time",exp_time);
                                                        in.putExtra("actdays",getActDays);
                                                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(in);*/

                                                        View dialogView = LayoutInflater.from(Redeem_Points_Activity.this).inflate(R.layout.layout_create_redeem1, null, false);
                                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Redeem_Points_Activity.this);
                                                        TextView textView = (TextView) dialogView.findViewById(R.id.text_alert_content);
                                                        Button cancel_btn = (Button) dialogView.findViewById(R.id.cancel_button);
                                                        Button ok_btn = (Button) dialogView.findViewById(R.id.ok_button);
                                                        TextView textView_title = (TextView) dialogView.findViewById(R.id.text_title);
                                                        TextView textView_desc = (TextView) dialogView.findViewById(R.id.text_desc);
                                                        TextView textView_bonus = (TextView) dialogView.findViewById(R.id.text_bonus);
                                                        TextView from_dt = (TextView) dialogView.findViewById(R.id.from_date);
                                                        TextView to_dt = (TextView) dialogView.findViewById(R.id.to_date);
                                                        TextView s_time = (TextView) dialogView.findViewById(R.id.start_time);
                                                        TextView e_time = (TextView) dialogView.findViewById(R.id.end_time);
                                                        TextView textView_sunday = (TextView) dialogView.findViewById(R.id.sunday);
                                                        TextView textView_monday = (TextView) dialogView.findViewById(R.id.monday);
                                                        TextView textView_tuesday = (TextView) dialogView.findViewById(R.id.tuesday);
                                                        TextView textView_wednesday = (TextView) dialogView.findViewById(R.id.wednesday);
                                                        TextView textView_thursday = (TextView) dialogView.findViewById(R.id.thursday);
                                                        TextView textView_friday = (TextView) dialogView.findViewById(R.id.friday);
                                                        TextView textView_saturday = (TextView) dialogView.findViewById(R.id.saturday);

                                                        textView_title.setText(title+" for "+bonus+" points");
                                                        textView_desc.setText(desc);
                                                        if (bonus.equals("0")) {
                                                            textView_bonus.setVisibility(View.GONE);
                                                        } else {
                                                            textView_bonus.setVisibility(View.VISIBLE);
                                                            textView_bonus.setText(bonus);
                                                        }

                                                        String startDate_conv = null, endDate_conv = null;
                                                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                        SimpleDateFormat d = new SimpleDateFormat("yyyy-MMM-dd");
                                                        try {
                                                            Date convertedDate_S = inputFormat.parse(start_date);
                                                            Date convertedDate_E = inputFormat.parse(exp_date);
                                                            startDate_conv = d.format(convertedDate_S);
                                                            endDate_conv = d.format(convertedDate_E);

                                                        } catch (ParseException e) {
                                                            Log.d("Date--->", "" + e.getMessage());
                                                        }

                                                        from_dt.setText(startDate_conv);
                                                        to_dt.setText(endDate_conv);
                                                        s_time.setText(start_time);
                                                        e_time.setText(exp_time);

                                                        if (!TextUtils.isEmpty(getActDays)) {
                                                            char get1 = getActDays.charAt(0);
                                                            char get2 = getActDays.charAt(1);
                                                            char get3 = getActDays.charAt(2);
                                                            char get4 = getActDays.charAt(3);
                                                            char get5 = getActDays.charAt(4);
                                                            char get6 = getActDays.charAt(5);
                                                            char get7 = getActDays.charAt(6);
                                                            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);
                                                            //Sunday
                                                            if (String.valueOf(get1).equals("y")) {
                                                                textView_sunday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_sunday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Monday
                                                            if (String.valueOf(get2).equals("y")) {
                                                                textView_monday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_monday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Tuesday
                                                            if (String.valueOf(get3).equals("y")) {
                                                                textView_tuesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_tuesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Wednesday
                                                            if (String.valueOf(get4).equals("y")) {
                                                                textView_wednesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_wednesday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Thursday
                                                            if (String.valueOf(get5).equals("y")) {
                                                                textView_thursday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_thursday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Friday
                                                            if (String.valueOf(get6).equals("y")) {
                                                                textView_friday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_friday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }

                                                            //Saturday
                                                            if (String.valueOf(get7).equals("y")) {
                                                                textView_saturday.setBackground(getResources().getDrawable(R.drawable.circle_bg_green));
                                                            } else {
                                                                textView_saturday.setBackground(getResources().getDrawable(R.drawable.circle_bg_red));
                                                            }
                                                        }

                                                        //setting the view of the builder to our custom view that we already inflated
                                                        builder.setView(dialogView);
                                                        builder.setCancelable(false);
                                                        //finally creating the alert dialog and displaying it
                                                        final AlertDialog alertDialog = builder.create();
                                                        alertDialog.show();

                                                        ok_btn.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                alertDialog.dismiss();
                                                                //String actDays = getActDays();
                                                                redemptionList.setRedeemTitle(title);
                                                                redemptionList.setRedeemDescription(desc);
                                                                redemptionList.setRedeemDepositAmt("0.0");
                                                                redemptionList.setRedeemPoints(bonus);
                                                                redemptionList.setRedeemActivedt(start_date);
                                                                redemptionList.setRedeemActtime(start_time);
                                                                redemptionList.setRedeemExpdt(exp_date);
                                                                redemptionList.setRedeemEndtime(exp_time);
                                                                redemptionList.setRedeemType("P");
                                                                redemptionList.setRedeemActivedays(getActDays);

                                                                list.set(Integer.parseInt(pos), redemptionList);
                                                                //Log.d("redemptionList======", "onClickAfter: " + list);
                                                                String redeems = new Gson().toJson(list);

                                                                SharedPreferences.Editor editor = sharedpreferences_add_redeem.edit();
                                                                editor.putString(ADD_REDEEM, redeems);
                                                                editor.commit();

                                                                Intent in = new Intent(Redeem_Points_Activity.this, Add_Redemption_Activity.class);
                                                                startActivity(in);
                                                                finish();

                                                            }
                                                        });
                                                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                alertDialog.dismiss();
                                                            }
                                                        });

                                                    }
                                                } else {
                                                    Toast.makeText(Redeem_Points_Activity.this, "Please select valid active & expiry date", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            Toast.makeText(Redeem_Points_Activity.this, "Please select expiry time", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(Redeem_Points_Activity.this, "Please select active time", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(Redeem_Points_Activity.this, "Please select expiry date", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Redeem_Points_Activity.this, "Please select active date", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            editText_redeem_bonus.setError("Enter valid points");
                            editText_redeem_bonus.requestFocus();
                        }

                    } else {
                        editText_redeem_desc.setError("Minimum 15chars");
                        editText_redeem_desc.requestFocus();
                    }
                } else {
                    editText_redeem_desc.setError("Enter valid description");
                    editText_redeem_desc.requestFocus();
                }


            } else {
                editText_redeem_title.setError("Minimum 10 chars");
                editText_redeem_title.requestFocus();
            }

        } else {
            editText_redeem_title.setError("Enter valid title");
            editText_redeem_title.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(Redeem_Points_Activity.this, Add_Redemption_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Handcare");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
