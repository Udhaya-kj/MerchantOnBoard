package life.corals.onboarding.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import life.corals.onboarding.Adapter.MyListAdapter;
import life.corals.onboarding.R;
import life.corals.onboarding.client.model.RedemptionList;

public class Add_Redemption_Activity extends AppCompatActivity {

    private TextView textView_add_redeem,textView_no_item;
    private ListView listView;
    private ArrayList<String> title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list;
    private SharedPreferences sharedpreferences_open_screen, sharedpreferences_redeem;
    private ImageView imageView_back,img_no_redeem_item;
    /*   private HorizontalScrollView horizontalScrollView;
    private LinearLayout linear_Layout;*/
    private LinearLayout button_next,linear_Layout;
    public static final String MyPREFERENCES_REDEEM = "MyPrefs_Redeem";
    public static final String REDEEM_TITLE = "REDEEM_TITLE";
    public static final String REDEEM_DESCRIPTION = "REDEEM_DESCRIPTION";
    public static final String REDEEM_AMOUNT = "REDEEM_AMOUNT";
    public static final String REDEEM_BONUS = "REDEEM_BONUS";
    public static final String REDEEM_START_DATE = "REDEEM_START_DATE";
    public static final String REDEEM_START_TIME = "REDEEM_START_TIME";
    public static final String REDEEM_EXPIRY_DATE = "REDEEM_EXPIRY_DATE";
    public static final String REDEEM_EXPIRY_TIME = "REDEEM_EXPIRY_TIME";
    private SharedPreferences sharedpreferences_add_redeem;
    private List<RedemptionList> list;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__redemption_);

        gson = new Gson();
        list = new ArrayList<>();
        sharedpreferences_add_redeem = getSharedPreferences(Redeem_Points_Activity.MyPREFERENCES_ADD_REDEEM, Context.MODE_PRIVATE);
        sharedpreferences_redeem = getSharedPreferences(MyPREFERENCES_REDEEM, Context.MODE_PRIVATE);
        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        textView_add_redeem = (TextView) findViewById(R.id.text_add_redeem);
        textView_no_item = (TextView) findViewById(R.id.text_no_item);
        img_no_redeem_item = (ImageView) findViewById(R.id.image_emptylist);
        listView = (ListView) findViewById(R.id.listview_redemptions);
        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        textView_add_redeem.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + getResources().getString(R.string.reward_points_setup) + "</u>  </font>"));
        /*  horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_layout);
          horizontal_Linear_Layout = (LinearLayout) findViewById(R.id.horizontal_linear_layout);*/
        //horizontalScrollView.setVisibility(View.INVISIBLE);
        button_next = (LinearLayout) findViewById(R.id.button_next_add_redemption);

        title_list = new ArrayList<>();
        desc_list = new ArrayList<>();
        bonus_list = new ArrayList<>();
        a_date_list = new ArrayList<>();
        a_time_list = new ArrayList<>();
        e_date_list = new ArrayList<>();
        e_time_list = new ArrayList<>();
        act_dys_list = new ArrayList<>();

        String add_redeem = sharedpreferences_add_redeem.getString(Redeem_Points_Activity.ADD_REDEEM, "");
        Type type = new TypeToken<List<RedemptionList>>() {
        }.getType();

        if (!TextUtils.isEmpty(add_redeem)) {
            list = gson.fromJson(add_redeem, type);
            Log.d("Add_Redeem--->", " redeem--- " + list.size());

            if (list.size() != 0) {
                for (int j = 0; j < list.size(); j++) {
                    String title = list.get(j).getRedeemTitle();
                    String desc = list.get(j).getRedeemDescription();
                    String bonus = list.get(j).getRedeemPoints();

                    String act_dt = list.get(j).getRedeemActivedt();
                    String act_time = list.get(j).getRedeemActtime();
                    String exp_date = list.get(j).getRedeemExpdt();
                    String exp_time = list.get(j).getRedeemEndtime();
                    String act_days = list.get(j).getRedeemActivedays();
                   // boolean sharable = list.get(j).isIsCustSharable();

                    title_list.add(title);
                    desc_list.add(desc);
                    bonus_list.add(bonus);
                    a_date_list.add(act_dt);
                    a_time_list.add(act_time);
                    e_date_list.add(exp_date);
                    e_time_list.add(exp_time);
                    act_dys_list.add(act_days);
                }
                img_no_redeem_item.setVisibility(View.GONE);
                textView_no_item.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                MyListAdapter customAdapter = new MyListAdapter(Add_Redemption_Activity.this, title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list);
                listView.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();

            } else {
                listView.setVisibility(View.GONE);
                img_no_redeem_item.setVisibility(View.VISIBLE);
                textView_no_item.setVisibility(View.VISIBLE);
            }

        } else {
            listView.setVisibility(View.GONE);
            img_no_redeem_item.setVisibility(View.VISIBLE);
            textView_no_item.setVisibility(View.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(Add_Redemption_Activity.this, Redeem_Points_Activity.class);
                in.putExtra("position", String.valueOf(position));
                in.putExtra("title", title_list.get(position));
                in.putExtra("desc", desc_list.get(position));
                in.putExtra("points", bonus_list.get(position));
                in.putExtra("act_date", a_date_list.get(position));
                in.putExtra("act_time", a_time_list.get(position));
                in.putExtra("exp_date", e_date_list.get(position));
                in.putExtra("exp_time", e_time_list.get(position));
                in.putExtra("act_days", act_dys_list.get(position));
                // Log.d("Adapter---->", position + "," + title_list.get(position) + "," + a_date_list.get(position) + " " + a_time_list.get(position) + "," + e_date_list.get(position) + " " + e_time_list.get(position) + "," + act_dys_list.get(position));
                startActivity(in);
                finish();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                View dialogView = LayoutInflater.from(Add_Redemption_Activity.this).inflate(R.layout.alert_create_redeem, null, false);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Add_Redemption_Activity.this);
                Button cancel_btn = (Button) dialogView.findViewById(R.id.cancel_button);
                Button ok_btn = (Button) dialogView.findViewById(R.id.ok_button);
                TextView textView = (TextView) dialogView.findViewById(R.id.text_alert_content);
                textView.setText("Are you sure  you want to delete this redemption?");
                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);
                builder.setCancelable(false);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        list.remove(position);
                        //Log.d("Add_Redeem--->", " redeem--- " + list.size());
                        title_list.clear();
                        desc_list.clear();
                        bonus_list.clear();
                        a_date_list.clear();
                        a_time_list.clear();
                        e_date_list.clear();
                        e_time_list.clear();
                        act_dys_list.clear();

                        if (list.size() != 0) {
                            Log.d("Add_Redeem--->", " redeem--- " + list.size());
                            for (int j = 0; j < list.size(); j++) {
                                String title = list.get(j).getRedeemTitle();
                                String desc = list.get(j).getRedeemDescription();
                                String bonus = list.get(j).getRedeemPoints();
                                String act_dt = list.get(j).getRedeemActivedt();
                                String act_time = list.get(j).getRedeemActtime();
                                String exp_date = list.get(j).getRedeemExpdt();
                                String exp_time = list.get(j).getRedeemEndtime();
                                String act_days = list.get(j).getRedeemActivedays();

                                title_list.add(title);
                                desc_list.add(desc);
                                bonus_list.add(bonus);
                                a_date_list.add(act_dt);
                                a_time_list.add(act_time);
                                e_date_list.add(exp_date);
                                e_time_list.add(exp_time);
                                act_dys_list.add(act_days);
                               /* if(sharable){
                                    sharable_list.add("1");
                                }
                                else {
                                    sharable_list.add("0");
                                }*/
                            }

                            MyListAdapter customAdapter = new MyListAdapter(Add_Redemption_Activity.this, title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list);
                            listView.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();

                            String redeems = new Gson().toJson(list);
                            SharedPreferences.Editor editor = sharedpreferences_add_redeem.edit();
                            editor.putString(Redeem_Points_Activity.ADD_REDEEM, redeems);
                            editor.commit();

                        } else {
                            Log.d("Add_Redeem--->", " redeem--- " + list.size());
                            listView.setVisibility(View.GONE);
                            img_no_redeem_item.setVisibility(View.VISIBLE);
                            textView_no_item.setVisibility(View.VISIBLE);

                            String redeems = new Gson().toJson(list);
                            SharedPreferences.Editor editor = sharedpreferences_add_redeem.edit();
                            editor.putString(Redeem_Points_Activity.ADD_REDEEM, redeems);
                            editor.commit();
                        }

                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                return true;
            }
        });
        //list.remove(0);

        String r_title = sharedpreferences_redeem.getString(REDEEM_TITLE, "");
        String r_desc = sharedpreferences_redeem.getString(REDEEM_DESCRIPTION, "");
        String r_bonus = sharedpreferences_redeem.getString(REDEEM_BONUS, "");
        String r_a_date = sharedpreferences_redeem.getString(REDEEM_START_DATE, "");
        String r_a_time = sharedpreferences_redeem.getString(REDEEM_START_TIME, "");
        String r_e_date = sharedpreferences_redeem.getString(REDEEM_EXPIRY_DATE, "");
        String r_e_time = sharedpreferences_redeem.getString(REDEEM_EXPIRY_TIME, "");

        Log.d("Redeem_List--->", "" + r_title + "," + r_desc + "," + r_bonus + "," + r_a_date + "," + r_a_time + "," + r_e_date + "," + r_e_time);

        /*if (!TextUtils.isEmpty(r_title) && !TextUtils.isEmpty(r_desc)  && !TextUtils.isEmpty(r_bonus) &&
                !TextUtils.isEmpty(r_a_date) && !TextUtils.isEmpty(r_a_time) && !TextUtils.isEmpty(r_e_date) && !TextUtils.isEmpty(r_e_time)) {
            title_list = new Gson().fromJson(r_title, new TypeToken<ArrayList<String>>() {
            }.getType());
            desc_list = new Gson().fromJson(r_desc, new TypeToken<ArrayList<String>>() {
            }.getType());
            bonus_list = new Gson().fromJson(r_bonus, new TypeToken<ArrayList<String>>() {
            }.getType());
            a_date_list = new Gson().fromJson(r_a_date, new TypeToken<ArrayList<String>>() {
            }.getType());
            a_time_list = new Gson().fromJson(r_a_time, new TypeToken<ArrayList<String>>() {
            }.getType());
            e_date_list = new Gson().fromJson(r_e_date, new TypeToken<ArrayList<String>>() {
            }.getType());
            e_time_list = new Gson().fromJson(r_e_time, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
*/




        /*if (getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra("title");
            String desc = getIntent().getStringExtra("desc");
            String amt = getIntent().getStringExtra("amount");
            String bonus = getIntent().getStringExtra("bonus");
            String a_date = getIntent().getStringExtra("date_active");
            String a_time = getIntent().getStringExtra("time_active");
            String e_date = getIntent().getStringExtra("date_expiry");
            String e_time = getIntent().getStringExtra("time_expiry");

            title_list.add(title);
            desc_list.add(desc);
            amt_list.add(amt);
            bonus_list.add(bonus);

            a_date_list.add(a_date);
            a_time_list.add(a_time);
            e_date_list.add(e_date);
            e_time_list.add(e_time);

            String title_ = new Gson().toJson(title_list);
            String desc_ = new Gson().toJson(desc_list);
            String amt_ = new Gson().toJson(amt_list);
            String bonus_ = new Gson().toJson(bonus_list);
            String a_date_ = new Gson().toJson(a_date_list);
            String a_time_ = new Gson().toJson(a_time_list);
            String e_date_ = new Gson().toJson(e_date_list);
            String e_time_ = new Gson().toJson(e_time_list);

            SharedPreferences.Editor editor = sharedpreferences_redeem.edit();
            editor.putString(REDEEM_AMOUNT, amt_);
            editor.putString(REDEEM_BONUS, bonus_);
            editor.putString(REDEEM_TITLE, title_);
            editor.putString(REDEEM_DESCRIPTION, desc_);
            editor.putString(REDEEM_START_DATE, a_date_);
            editor.putString(REDEEM_START_TIME, a_time_);
            editor.putString(REDEEM_EXPIRY_DATE, e_date_);
            editor.putString(REDEEM_EXPIRY_TIME, e_time_);
            editor.commit();


            if (title_list.size() > 0 && desc_list.size() > 0 && amt_list.size() > 0 && bonus_list.size() > 0 && a_date_list.size() > 0 && a_time_list.size() > 0 && e_date_list.size() > 0 && e_time_list.size() > 0) {
                horizontalScrollView.setVisibility(View.VISIBLE);
                MyListAdapter customAdapter = new MyListAdapter(getApplicationContext(), title_list, desc_list, amt_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list,act_dys_list);
                listView.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();
            }
        }*/


        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView_add_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_Redemption_Activity.this, Redeem_Points_Activity.class);
                startActivity(i);
                finish();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_Redemption_Activity.this, Review_Activity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(Add_Redemption_Activity.this, Loyalty_Points_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();


/*        String title_ = new Gson().toJson(title_list);
        String desc_ = new Gson().toJson(desc_list);
        String amt_ = new Gson().toJson(amt_list);
        String bonus_ = new Gson().toJson(bonus_list);
        String a_date_ = new Gson().toJson(a_date_list);
        String a_time_ = new Gson().toJson(a_time_list);
        String e_date_ = new Gson().toJson(e_date_list);
        String e_time_ = new Gson().toJson(e_time_list);

        SharedPreferences.Editor editor = sharedpreferences_redeem.edit();
        editor.putString(REDEEM_AMOUNT, amt_);
        editor.putString(REDEEM_BONUS, bonus_);
        editor.putString(REDEEM_TITLE, title_);
        editor.putString(REDEEM_DESCRIPTION, desc_);
        editor.putString(REDEEM_START_DATE, a_date_);
        editor.putString(REDEEM_START_TIME, a_time_);
        editor.putString(REDEEM_EXPIRY_DATE, e_date_);
        editor.putString(REDEEM_EXPIRY_TIME, e_time_);
        editor.commit();*/

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "10");
        editor_os.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "10");
        editor_os.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "10");
        editor_os.commit();
    }


}
