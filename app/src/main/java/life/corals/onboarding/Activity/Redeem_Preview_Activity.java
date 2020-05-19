package life.corals.onboarding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import life.corals.onboarding.R;
import life.corals.onboarding.client.model.RedemptionList;

public class Redeem_Preview_Activity extends AppCompatActivity {

    private Button ok_btn, cancel_btn;
    TextView textView_title, textView_desc, textView_conditions, textView_settings1, textView_settings2, textView_create_update;
    String pos, title, desc, points, act_date, end_date, act_time, end_time, actdays;
    private ArrayList<String> daysList;
    int daysCount = 0;
    LinearLayout layout_settings2;
    Button button_cancel, button_create;
    RedemptionList redemptionList = new RedemptionList();
    private List<RedemptionList> list;
    private Gson gson;
    ImageView imageView;
    private SharedPreferences sharedpreferences_add_redeem;
    public static final String MyPREFERENCES_ADD_REDEEM = "MyPrefs_Add_Redeem";
    public static final String ADD_REDEEM = "ADD_REDEEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem__preview_);

        list = new ArrayList<>();
        gson = new Gson();
        daysList = new ArrayList<>();
        imageView = findViewById(R.id.image_back);
        cancel_btn = (Button) findViewById(R.id.cancel_button);
        ok_btn = (Button) findViewById(R.id.ok_button);
        textView_title = (TextView) findViewById(R.id.text_title);
        textView_desc = (TextView) findViewById(R.id.text_desc);
        textView_conditions = (TextView) findViewById(R.id.text_terms_conditions);
        textView_settings1 = (TextView) findViewById(R.id.text_settings1);
        textView_settings2 = (TextView) findViewById(R.id.text_settings2);
        textView_create_update = (TextView) findViewById(R.id.text_update_create);
        layout_settings2 = findViewById(R.id.layout_settings2);
        button_create = findViewById(R.id.ok_button);
        button_cancel = findViewById(R.id.cancel_button);
        sharedpreferences_add_redeem = getSharedPreferences(MyPREFERENCES_ADD_REDEEM, Context.MODE_PRIVATE);

        String add_redeem = sharedpreferences_add_redeem.getString(ADD_REDEEM, "");
        Type type = new TypeToken<List<RedemptionList>>() {
        }.getType();
        if (!TextUtils.isEmpty(add_redeem)) {
            list = gson.fromJson(add_redeem, type);
        }

        if (getIntent().getExtras() != null) {
            pos = getIntent().getStringExtra("position");
            title = getIntent().getStringExtra("title");
            desc = getIntent().getStringExtra("desc");
            points = getIntent().getStringExtra("points");
            act_date = getIntent().getStringExtra("act_date");
            end_date = getIntent().getStringExtra("end_date");
            act_time = getIntent().getStringExtra("act_time");
            end_time = getIntent().getStringExtra("end_time");
            actdays = getIntent().getStringExtra("actdays");


            String startDate_conv = null, endDate_conv = null;
            if (!TextUtils.isEmpty(act_date) && !TextUtils.isEmpty(end_date)) {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat d = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    Date convertedDate_S = inputFormat.parse(act_date);
                    Date convertedDate_E = inputFormat.parse(end_date);
                    startDate_conv = d.format(convertedDate_S);
                    endDate_conv = d.format(convertedDate_E);

                } catch (ParseException e) {
                    Log.d("Date--->", "" + e.getMessage());
                }
            }

            if (!TextUtils.isEmpty(actdays)) {
                char get1 = actdays.charAt(0);
                char get2 = actdays.charAt(1);
                char get3 = actdays.charAt(2);
                char get4 = actdays.charAt(3);
                char get5 = actdays.charAt(4);
                char get6 = actdays.charAt(5);
                char get7 = actdays.charAt(6);
                Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);

                daysList.clear();
                //Sunday
                if (String.valueOf(get1).equals("y")) {
                    daysCount = 1;
                    daysList.add("Sunday");
                }

                //Monday
                if (String.valueOf(get2).equals("y")) {
                    daysCount += 1;
                    daysList.add("Monday");
                }

                //Tuesday
                if (String.valueOf(get3).equals("y")) {
                    daysCount += 1;
                    daysList.add("Tuesday");
                }

                //Wednesday
                if (String.valueOf(get4).equals("y")) {
                    daysCount += 1;
                    daysList.add("Wednesday");
                }
                //Thursday
                if (String.valueOf(get5).equals("y")) {
                    daysCount += 1;
                    daysList.add("Thursday");
                }

                //Friday
                if (String.valueOf(get6).equals("y")) {
                    daysCount += 1;
                    daysList.add("Friday");
                }

                //Saturday
                if (String.valueOf(get7).equals("y")) {
                    daysCount += 1;
                    daysList.add("Saturday");
                }

                Log.d("Days====>", "" + daysCount + " , " + daysList.size());

                String days = "";
                for (int i = 0; i < daysList.size(); i++) {
                    if (i == 0) {
                        days = daysList.get(i);
                    } else if (i == (daysList.size() - 1)) {
                        days += " and " + daysList.get(i);
                    } else {
                        days += ", " + daysList.get(i);
                    }
                    Log.d("Days====>", "List=== " + days + " , " + daysCount);
                }


                textView_title.setText(title + " for " + points + " points");
                textView_desc.setText(desc);
                textView_conditions.setText("Validity Period: " + startDate_conv + " to " + endDate_conv + ". Applicable for one redemption per customer. Not valid with other offers. Merchant decision is final. Terms & conditions apply");
                if (!TextUtils.isEmpty(pos)) {
                    textView_create_update.setText("Do you want to update this voucher?");
                }

                if (daysCount == 7 && !TextUtils.isEmpty(act_time) && !TextUtils.isEmpty(end_time)) {
                    textView_settings1.setText("Voucher validity : " + startDate_conv + " to " + endDate_conv);
                    textView_settings2.setText("Voucher will be displayed for redemption on  All days between " + act_time + " to " + end_time);


                } else if (daysCount != 7 && !TextUtils.isEmpty(act_time) && !TextUtils.isEmpty(end_time)) {
                    textView_settings1.setText("Voucher validity : " + startDate_conv + " to " + endDate_conv);
                    textView_settings2.setText("Voucher will be displayed for redemption on " + days + " between " + act_time + " to " + end_time);


                } else if (TextUtils.isEmpty(String.valueOf(daysCount)) && TextUtils.isEmpty(act_time) && TextUtils.isEmpty(end_time)) {
                    textView_settings1.setText("Voucher validity : " + startDate_conv + " to " + endDate_conv);
                    layout_settings2.setVisibility(View.GONE);
                }

            } else {
                textView_settings1.setText("Voucher validity : " + startDate_conv + " to " + endDate_conv);
                //textView_settings2.setText("Voucher will be displayed for redemption on " + days + " between " + s_time + " to " + e_time);
                layout_settings2.setVisibility(View.GONE);
            }
        }

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pos) || pos == null) {
                    redemptionList.setRedeemTitle(title);
                    redemptionList.setRedeemDescription(desc);
                    redemptionList.setRedeemDepositAmt("0.0");
                    redemptionList.setRedeemPoints(points);
                    redemptionList.setRedeemActivedt(act_date);
                    redemptionList.setRedeemActtime(act_time);
                    redemptionList.setRedeemExpdt(end_date);
                    redemptionList.setRedeemEndtime(end_time);
                    redemptionList.setRedeemType("P");
                    redemptionList.setRedeemActivedays(actdays);
                    list.add(redemptionList);
                    Log.d("redemptionList======", "onClickAfter: " + list);
                    String redeems = new Gson().toJson(list);
                    SharedPreferences.Editor editor = sharedpreferences_add_redeem.edit();
                    editor.putString(ADD_REDEEM, redeems);
                    editor.commit();

                    startActivity(new Intent(Redeem_Preview_Activity.this, Add_Redemption_Activity.class));
                    finish();
                } else {
                    redemptionList.setRedeemTitle(title);
                    redemptionList.setRedeemDescription(desc);
                    redemptionList.setRedeemDepositAmt("0.0");
                    redemptionList.setRedeemPoints(points);
                    redemptionList.setRedeemActivedt(act_date);
                    redemptionList.setRedeemActtime(act_time);
                    redemptionList.setRedeemExpdt(end_date);
                    redemptionList.setRedeemEndtime(end_time);
                    redemptionList.setRedeemType("P");
                    redemptionList.setRedeemActivedays(actdays);
                    list.set(Integer.parseInt(pos), redemptionList);
                    Log.d("redemptionList======", "onClickAfter: " + list);
                    String redeems = new Gson().toJson(list);
                    SharedPreferences.Editor editor = sharedpreferences_add_redeem.edit();
                    editor.putString(ADD_REDEEM, redeems);
                    editor.commit();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LayoutInflater factory = LayoutInflater.from(Redeem_Preview_Activity.this);
                            final View deleteDialogView = factory.inflate(R.layout.success_dialog, null);
                            final AlertDialog deleteDialog = new AlertDialog.Builder(Redeem_Preview_Activity.this).create();
                            deleteDialog.setView(deleteDialogView);
                            deleteDialog.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    deleteDialog.dismiss();
                                    startActivity(new Intent(Redeem_Preview_Activity.this, Add_Redemption_Activity.class));
                                    finish();
                                }
                            }, 3000);

                        }
                    });

                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Redeem_Preview_Activity.this, Add_Redemption_Activity.class);
                startActivity(in);
                finish();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener()
                {
        @Override
        public void onClick (View v){
            Intent in = new Intent(Redeem_Preview_Activity.this, Redeem_Points_Activity.class);
            in.putExtra("position", pos);
            in.putExtra("title", title);
            in.putExtra("desc", desc);
            in.putExtra("points", points);
            in.putExtra("act_date", act_date);
            in.putExtra("exp_date", end_date);
            in.putExtra("act_time", act_time);
            in.putExtra("exp_time", end_time);
            in.putExtra("act_days", actdays);
            startActivity(in);
            finish();
        }
    });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(Redeem_Preview_Activity.this, Redeem_Points_Activity.class);
        in.putExtra("position", pos);
        in.putExtra("title", title);
        in.putExtra("desc", desc);
        in.putExtra("points", points);
        in.putExtra("act_date", act_date);
        in.putExtra("exp_date", end_date);
        in.putExtra("act_time", act_time);
        in.putExtra("exp_time", end_time);
        in.putExtra("act_days", actdays);
        startActivity(in);
        finish();
    }
}
