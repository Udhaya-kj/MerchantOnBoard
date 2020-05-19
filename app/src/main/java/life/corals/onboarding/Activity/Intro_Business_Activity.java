package life.corals.onboarding.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import life.corals.onboarding.Adapter.MyListAdapter_Competitors;
import life.corals.onboarding.Adapter.MyListAdapter_Recyclerview;
import life.corals.onboarding.R;

public class Intro_Business_Activity extends AppCompatActivity {

    private LinearLayout button_next;
    private EditText editText_disp_name, editText_intro_buss, editText_phone, editText_website;
    public static final String MyPREFERENCES_INTRO_BUSSINESS = "MyPrefs_Intro_Bussiness";
    public static final String INTRO_BUSSINESS = "INTRO_BUSSINESS";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String WEBSITE = "WEBSITE";
    public static final String COMPETITORS_LIST = "COMPETITORS_LIST";
    public static final String COMPETITORS_TYPE_LIST = "COMPETITORS_TYPE_LIST";
    public static final String COMPETITORS_TYPE_ID_LIST = "COMPETITORS_TYPE_ID_LIST";

    SharedPreferences sharedpreferences;
    private SharedPreferences sharedpreferences_open_screen;
    private ImageView imageView_back;
    private TextView textView_competitors;
    private SharedPreferences sharedpreferences_cat_list;
    private ArrayList<String> biz_competitors_id, competitors_list;
    public static ArrayList<String> biz_comp_list_id, biz_comp_list_data, biz_id_list, biz_type_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__business_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        sharedpreferences_cat_list = Intro_Business_Activity.this.getSharedPreferences(SplashActivity.MyPREFERENCES_CATEGORY_LIST, Context.MODE_PRIVATE);
        textView_competitors = (TextView) findViewById(R.id.text_select_competitors);
        editText_disp_name = (EditText) findViewById(R.id.edit_bussiness_disp_name);
        editText_intro_buss = (EditText) findViewById(R.id.edit_intro_buss_details);
        editText_phone = (EditText) findViewById(R.id.edit_phone_no);
        editText_website = (EditText) findViewById(R.id.edit_website_url);
        button_next = (LinearLayout) findViewById(R.id.button_next_intro_business);
        sharedpreferences = getSharedPreferences(MyPREFERENCES_INTRO_BUSSINESS, Context.MODE_PRIVATE);

        String disp_name = sharedpreferences.getString(DISPLAY_NAME, "");
        String intro = sharedpreferences.getString(INTRO_BUSSINESS, "");
        String phone = sharedpreferences.getString(PHONE_NUMBER, "");
        String website = sharedpreferences.getString(WEBSITE, "");
        String competitors = sharedpreferences.getString(COMPETITORS_TYPE_LIST, "");
        String biz_list = sharedpreferences_cat_list.getString(SplashActivity.CATEGORY_LIST, "");
        String id_list = sharedpreferences.getString(COMPETITORS_TYPE_ID_LIST, "");
        String comp_id_list = sharedpreferences.getString(COMPETITORS_LIST, "");
        Log.d("Type--->", "" + competitors);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager li = new LinearLayoutManager(Intro_Business_Activity.this);
        recyclerView.setLayoutManager(li);

       /* editText_intro_buss.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editText_intro_buss.setRawInputType(InputType.TYPE_CLASS_TEXT);*/

        textView_competitors.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Competitors" + "</u>  </font>"));
        biz_type_list = new ArrayList<>();
        biz_competitors_id = new ArrayList<>();
        biz_comp_list_id = new ArrayList<>();
        biz_comp_list_data = new ArrayList<>();
        biz_id_list = new ArrayList<>();
        competitors_list = new ArrayList<>();

        if (!TextUtils.isEmpty(comp_id_list)) {
            biz_comp_list_id = new Gson().fromJson(comp_id_list, new TypeToken<ArrayList<String>>() {
            }.getType());

        }
        Log.d("Id_List--->",""+biz_comp_list_id);
        if (!TextUtils.isEmpty(id_list)) {
            biz_id_list = new Gson().fromJson(id_list, new TypeToken<ArrayList<String>>() {
            }.getType());
            Log.d("biz_id_list--->", "" + biz_id_list);
        }

        if (!TextUtils.isEmpty(biz_list)) {
            biz_type_list = new Gson().fromJson(biz_list, new TypeToken<ArrayList<String>>() {
            }.getType());
            Log.d("biz_type_list--->", "" + biz_type_list);
        }

        if (!TextUtils.isEmpty(competitors)) {
            competitors_list = new Gson().fromJson(competitors, new TypeToken<ArrayList<String>>() {
            }.getType());

            biz_comp_list_data = new Gson().fromJson(competitors, new TypeToken<ArrayList<String>>() {
            }.getType());
            Log.d("competitors_list--->", "" + competitors_list + "," + competitors);
            if (competitors_list.size() == 0) {
                textView_competitors.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Competitors" + "</u>  </font>"));
            } else {
                textView_competitors.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Change Competitors" + "</u>  </font>"));
                MyListAdapter_Recyclerview myListAdapter_recyclerview = new MyListAdapter_Recyclerview(competitors_list);
                recyclerView.setAdapter(myListAdapter_recyclerview);
                myListAdapter_recyclerview.notifyDataSetChanged();
            }
        }
        //int size = 27;
        for (int i = 1; i <= biz_type_list.size(); i++) {
            String data = String.valueOf(i);
            if (data.length() == 1) {
                data = "0" + data;
            }
            biz_competitors_id.add(data);
        }

        if (biz_id_list.size() == 0) {
            for (int i = 0; i < biz_type_list.size(); i++) {
                biz_id_list.add("00");
            }
        }
        Log.d("Biz_List_size--->", "" + biz_id_list.size());

        editText_disp_name.setText(disp_name);
        editText_disp_name.setSelection(disp_name.length());
        editText_intro_buss.setText(intro);
        editText_intro_buss.setSelection(intro.length());
        editText_phone.setText(phone);
        editText_phone.setSelection(phone.length());

        if(TextUtils.isEmpty(website)){
            editText_website.setText("https://www.");
        }
        else {
            editText_website.setText(website);
        }
        editText_website.setSelection(website.length());

        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textView_competitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCompetitors();

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String disp_name = editText_disp_name.getText().toString().trim();
                String intro_buss = editText_intro_buss.getText().toString();
                String phn = editText_phone.getText().toString().trim();
                String web = editText_website.getText().toString().trim();

                if (!TextUtils.isEmpty(disp_name)) {
                    if (intro_buss.length() >= 50 && intro_buss.length() <= 2000) {

                        if (!TextUtils.isEmpty(phn) && phn.length() > 7) {
                            if (URLUtil.isValidUrl(web) && Patterns.WEB_URL.matcher(web).matches()) {

                                //String competitorsList, competitorsListData, typeIdList;
                                String competitorsList = new Gson().toJson(biz_comp_list_id);
                                String competitorsListData = new Gson().toJson(biz_comp_list_data);
                                String typeIdList = new Gson().toJson(biz_id_list);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(INTRO_BUSSINESS, intro_buss);
                                editor.putString(DISPLAY_NAME, disp_name);
                                editor.putString(PHONE_NUMBER, phn);
                                editor.putString(WEBSITE, web);
                                editor.putString(COMPETITORS_LIST, competitorsList);
                                editor.putString(COMPETITORS_TYPE_LIST, competitorsListData);
                                editor.putString(COMPETITORS_TYPE_ID_LIST, typeIdList);
                                editor.commit();

                                Log.d("ID====>", "" + biz_comp_list_id);
                                Intent in = new Intent(Intro_Business_Activity.this, Operating_Hours_Activity.class);
                                startActivity(in);
                                finish();
                            } else {
                                editText_website.setError("Enter valid URL");
                                editText_website.requestFocus();
                            }
                        } else {
                            editText_phone.setError("Enter valid phone number");
                            editText_phone.requestFocus();
                        }


                    } else {
                        editText_intro_buss.setError("Min 50 chars and Max 2000 chars");
                        editText_intro_buss.requestFocus();
                    }
                } else {
                    editText_disp_name.setError("Enter valid business display name");
                    editText_disp_name.requestFocus();
                }
            }


        });
    }

    public void getCompetitors() {
        View dialogView = LayoutInflater.from(Intro_Business_Activity.this).inflate(R.layout.business_competitor_layout, null, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Intro_Business_Activity.this);
        ListView listView = (ListView) dialogView.findViewById(R.id.listview_compitetors);
        Button button = (Button) dialogView.findViewById(R.id.button_comp_ok);
        builder.setView(dialogView);
        builder.setCancelable(true);
        MyListAdapter_Competitors myListAdapter_competitors = new MyListAdapter_Competitors(Intro_Business_Activity.this, biz_type_list, biz_competitors_id);
        listView.setAdapter(myListAdapter_competitors);
        myListAdapter_competitors.notifyDataSetChanged();
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ListSize--->", "" + biz_comp_list_data.size());
                alertDialog.dismiss();
                if (biz_comp_list_data.size() == 0) {
                    textView_competitors.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Competitors" + "</u>  </font>"));
                    MyListAdapter_Recyclerview myListAdapter_recyclerview = new MyListAdapter_Recyclerview(biz_comp_list_data);
                    recyclerView.setAdapter(myListAdapter_recyclerview);
                    myListAdapter_recyclerview.notifyDataSetChanged();
                } else {
                    textView_competitors.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Change Competitors" + "</u>  </font>"));
                    MyListAdapter_Recyclerview myListAdapter_recyclerview = new MyListAdapter_Recyclerview(biz_comp_list_data);
                    recyclerView.setAdapter(myListAdapter_recyclerview);
                    myListAdapter_recyclerview.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "5");
        editor_os.commit();

        String disp_name = editText_disp_name.getText().toString().trim();
        String intro_buss = editText_intro_buss.getText().toString();
        String phn = editText_phone.getText().toString().trim();
        String web = editText_website.getText().toString().trim();
        String competitorsList = new Gson().toJson(biz_comp_list_id);
        String competitorsListData = new Gson().toJson(biz_comp_list_data);
        String typeIdList = new Gson().toJson(biz_id_list);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(INTRO_BUSSINESS, intro_buss);
        editor.putString(DISPLAY_NAME, disp_name);
        editor.putString(PHONE_NUMBER, phn);
        editor.putString(WEBSITE, web);
        editor.putString(COMPETITORS_LIST, competitorsList);
        editor.putString(COMPETITORS_TYPE_LIST, competitorsListData);
        editor.putString(COMPETITORS_TYPE_ID_LIST, typeIdList);
        editor.commit();


    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "5");
        editor_os.commit();

        String disp_name = editText_disp_name.getText().toString().trim();
        String intro_buss = editText_intro_buss.getText().toString();
        String phn = editText_phone.getText().toString().trim();
        String web = editText_website.getText().toString().trim();
        String competitorsList = new Gson().toJson(biz_comp_list_id);
       String competitorsListData = new Gson().toJson(biz_comp_list_data);
       String typeIdList = new Gson().toJson(biz_id_list);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(INTRO_BUSSINESS, intro_buss);
        editor.putString(DISPLAY_NAME, disp_name);
        editor.putString(PHONE_NUMBER, phn);
        editor.putString(WEBSITE, web);
        editor.putString(COMPETITORS_LIST, competitorsList);
        editor.putString(COMPETITORS_TYPE_LIST, competitorsListData);
        editor.putString(COMPETITORS_TYPE_ID_LIST, typeIdList);
        editor.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "5");
        editor_os.commit();

        String disp_name = editText_disp_name.getText().toString().trim();
        String intro_buss = editText_intro_buss.getText().toString();
        String phn = editText_phone.getText().toString().trim();
        String web = editText_website.getText().toString().trim();
        String competitorsList = new Gson().toJson(biz_comp_list_id);
        String competitorsListData = new Gson().toJson(biz_comp_list_data);
        String typeIdList = new Gson().toJson(biz_id_list);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(INTRO_BUSSINESS, intro_buss);
        editor.putString(DISPLAY_NAME, disp_name);
        editor.putString(PHONE_NUMBER, phn);
        editor.putString(WEBSITE, web);
        editor.putString(COMPETITORS_LIST, competitorsList);
        editor.putString(COMPETITORS_TYPE_LIST, competitorsListData);
        editor.putString(COMPETITORS_TYPE_ID_LIST, typeIdList);
        editor.commit();

    }


    @Override
    public void onBackPressed() {
        Intent in = new Intent(Intro_Business_Activity.this, Business_Details_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }


}
