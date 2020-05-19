package life.corals.onboarding.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import life.corals.onboarding.Constants.Constants;
import life.corals.onboarding.Dialogs.AlertDialogYesNo;
import life.corals.onboarding.R;
import life.corals.onboarding.Utils.HttpHandler;
import life.corals.onboarding.Utils.MyApplication;
import life.corals.onboarding.receiver.ConnectivityReceiver;


public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private TextView text_url;
    private String TAG = CountryActivity.class.getSimpleName();
    private ArrayList<String> country_code_list, symbol_list, mobile_start_digits_list, mobile_min_length_list, mobile_max_length_list, country_name_list, time_zone_list, dial_code_list;
    private ArrayList<String> country_code_list_NI, symbol_list_NI, mobile_start_digits_list_NI, mobile_min_length_list_NI, mobile_max_length_list_NI, country_name_list_NI, time_zone_list_NI, dial_code_list_NI;

    private SharedPreferences sharedpreferences, sharedpreferences_params, sharedpreferences_cat_list;
    private LinearLayout linearLayout;

    public static final String MyPREFERENCES_PARAMS = "MyPrefs_Params";
    public static final String ccode = "ccode";
    public static final String csymbol = "csymbol";
    public static final String cstart_digit = "cstart_digit";
    public static final String cmin_l = "cmin_l";
    public static final String cmax_l = "cmax_l";
    public static final String cc_name = "cc_name";
    public static final String czone = "czone";
    public static final String c_dial_code = "c_dial_code";

    public static final String MyPREFERENCES_CATEGORY_LIST = "MyPrefs_Category_List";
    public static final String CATEGORY_LIST = "CATEGORY_LIST";
    private String currentVersion;
    private AlertDialogYesNo alertDialogYesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        //new getPlaystoreVersion().execute();

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d("CurrentVersion--->", "" + currentVersion);
            //new VersionChecker().execute();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        init();
    }

    public void init() {



        linearLayout = findViewById(R.id.layout_splash);
        text_url = (TextView) findViewById(R.id.text_url);
        text_url.setText(Html.fromHtml("<font color=#FFFFFF>  <u>" + "https://corals.life/" + "</u>  </font>"));

        text_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://corals.life/"));
                startActivity(browserIntent);

                /*SplashActivity.this.finish();
                System.exit(0);*/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(a);
                    }
                }, 2000);

            }
        });


        sharedpreferences_params = SplashActivity.this.getSharedPreferences(MyPREFERENCES_PARAMS, Context.MODE_PRIVATE);
        sharedpreferences_cat_list = SplashActivity.this.getSharedPreferences(MyPREFERENCES_CATEGORY_LIST, Context.MODE_PRIVATE);

        country_code_list = new ArrayList<>();
        symbol_list = new ArrayList<>();
        mobile_start_digits_list = new ArrayList<>();
        mobile_min_length_list = new ArrayList<>();
        mobile_max_length_list = new ArrayList<>();
        country_name_list = new ArrayList<>();
        time_zone_list = new ArrayList<>();
        dial_code_list = new ArrayList<>();

        country_code_list_NI = new ArrayList<>();
        symbol_list_NI = new ArrayList<>();
        mobile_start_digits_list_NI = new ArrayList<>();
        mobile_min_length_list_NI = new ArrayList<>();
        mobile_max_length_list_NI = new ArrayList<>();
        country_name_list_NI = new ArrayList<>();
        time_zone_list_NI = new ArrayList<>();
        dial_code_list_NI = new ArrayList<>();


    }

    class getParams extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            country_code_list.clear();
            symbol_list.clear();
            mobile_start_digits_list.clear();
            mobile_min_length_list.clear();
            mobile_max_length_list.clear();
            country_name_list.clear();
            time_zone_list.clear();
            dial_code_list.clear();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(Constants.CORALS_PARAMS_URL);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("country-settings");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String code = c.getString("country-code");
                        String symbol = c.getString("symbol");
                        String start_digit = c.getString("mobile-start-digits");
                        String min_l = c.getString("mobile-min-length");
                        String max_l = c.getString("mobile-max-length");
                        String c_name = c.getString("country-name");
                        String zone = c.getString("time-zone");
                        String dial_code = c.getString("country-dialcode");

                        country_code_list.add(code);
                        symbol_list.add(symbol);
                        mobile_start_digits_list.add(start_digit);
                        mobile_min_length_list.add(min_l);
                        mobile_max_length_list.add(max_l);
                        country_name_list.add(c_name);
                        time_zone_list.add(zone);
                        dial_code_list.add(dial_code);
                    }
                    Log.d("C_List---->", "" + country_code_list.size());

                    String codeList = new Gson().toJson(country_code_list);
                    String symbolList = new Gson().toJson(symbol_list);
                    String startDigitList = new Gson().toJson(mobile_start_digits_list);
                    String minLenList = new Gson().toJson(mobile_min_length_list);
                    String maxLenList = new Gson().toJson(mobile_max_length_list);
                    String nameList = new Gson().toJson(country_name_list);
                    String zoneList = new Gson().toJson(time_zone_list);
                    String dialCodeList = new Gson().toJson(dial_code_list);

                    SharedPreferences.Editor editor = sharedpreferences_params.edit();
                    editor.putString(ccode, codeList);
                    editor.putString(csymbol, symbolList);
                    editor.putString(cstart_digit, startDigitList);
                    editor.putString(cmin_l, minLenList);
                    editor.putString(cmax_l, maxLenList);
                    editor.putString(cc_name, nameList);
                    editor.putString(czone, zoneList);
                    editor.putString(c_dial_code, dialCodeList);
                    editor.commit();

                    JSONObject biz_types = jsonObj.getJSONObject("categorytype");
                    String cat1 = biz_types.getString("01");
                    String cat2 = biz_types.getString("02");
                    String cat3 = biz_types.getString("03");
                    String cat4 = biz_types.getString("04");
                    String cat5 = biz_types.getString("05");

                    String cat6 = biz_types.getString("06");
                    String cat7 = biz_types.getString("07");
                    String cat8 = biz_types.getString("08");
                    String cat9 = biz_types.getString("09");
                    String cat10 = biz_types.getString("10");

                    String cat11 = biz_types.getString("11");
                    String cat12 = biz_types.getString("12");
                    String cat13 = biz_types.getString("13");
                    String cat14 = biz_types.getString("14");
                    String cat15 = biz_types.getString("15");

                    String cat16 = biz_types.getString("16");
                    String cat17 = biz_types.getString("17");
                    String cat18 = biz_types.getString("18");
                    String cat19 = biz_types.getString("19");
                    String cat20 = biz_types.getString("20");

                    String cat21 = biz_types.getString("21");
                    String cat22 = biz_types.getString("22");
                    String cat23 = biz_types.getString("23");
                    String cat24 = biz_types.getString("24");
                    String cat25 = biz_types.getString("25");

                    String cat26 = biz_types.getString("26");
                    String cat27 = biz_types.getString("27");

                    Log.d("Cat_List---->", "" + cat1 + "," + cat27);

                    ArrayList<String> category_list = new ArrayList<>();
                    category_list.add(cat1);
                    category_list.add(cat2);
                    category_list.add(cat3);
                    category_list.add(cat4);
                    category_list.add(cat5);

                    category_list.add(cat6);
                    category_list.add(cat7);
                    category_list.add(cat8);
                    category_list.add(cat9);
                    category_list.add(cat10);

                    category_list.add(cat11);
                    category_list.add(cat12);
                    category_list.add(cat13);
                    category_list.add(cat14);
                    category_list.add(cat15);

                    category_list.add(cat16);
                    category_list.add(cat17);
                    category_list.add(cat18);
                    category_list.add(cat19);
                    category_list.add(cat20);

                    category_list.add(cat21);
                    category_list.add(cat22);
                    category_list.add(cat23);
                    category_list.add(cat24);
                    category_list.add(cat25);
                    category_list.add(cat26);
                    category_list.add(cat27);

                    String categoryList = new Gson().toJson(category_list);

                    SharedPreferences.Editor cat_editor = sharedpreferences_cat_list.edit();
                    cat_editor.putString(CATEGORY_LIST, categoryList);
                    cat_editor.commit();

                    sharedpreferences = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
                    String otp_status = sharedpreferences.getString(OTP_Activity.OTP_VALIDATE, "");
                    // Log.d("otp_status---->", "" + otp_status);
                    if (otp_status.equals("1")) {

                        Intent in = new Intent(SplashActivity.this, Welcome_Activity.class);
                        startActivity(in);
                        finish();
                    } else {

                        if (country_code_list.size() > 0 && symbol_list.size() > 0 && mobile_start_digits_list.size() > 0 && mobile_min_length_list.size() > 0 &&
                                mobile_max_length_list.size() > 0 && country_name_list.size() > 0 && time_zone_list.size() > 0 && dial_code_list.size() > 0) {

                            Intent i = new Intent(SplashActivity.this, Captcha_Activity.class);
                            i.putExtra("code", country_code_list);
                            i.putExtra("symbol", symbol_list);
                            i.putExtra("start_dg", mobile_start_digits_list);
                            i.putExtra("min_l", mobile_min_length_list);
                            i.putExtra("max_l", mobile_max_length_list);
                            i.putExtra("c_name", country_name_list);
                            i.putExtra("zone", time_zone_list);
                            i.putExtra("dial_code", dial_code_list);
                            startActivity(i);
                            finish();

                        }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Couldn't get response from server. Please try again!", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //getParams_No_Internet();
                        Toast.makeText(getApplicationContext(), "Couldn't get response from server. Please try again!", Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    public void getParams_No_Internet() {
        String codeList = sharedpreferences_params.getString(ccode, "");
        String symbolList = sharedpreferences_params.getString(csymbol, "");
        String startDigitList = sharedpreferences_params.getString(cstart_digit, "");
        String minLenList = sharedpreferences_params.getString(cmin_l, "");
        String maxLenList = sharedpreferences_params.getString(cmax_l, "");
        String nameList = sharedpreferences_params.getString(cc_name, "");
        String zoneList = sharedpreferences_params.getString(czone, "");
        String dialCodeList = sharedpreferences_params.getString(c_dial_code, "");

        country_code_list_NI = new Gson().fromJson(codeList, new TypeToken<ArrayList<String>>() {
        }.getType());
        symbol_list_NI = new Gson().fromJson(symbolList, new TypeToken<ArrayList<String>>() {
        }.getType());
        mobile_start_digits_list_NI = new Gson().fromJson(startDigitList, new TypeToken<ArrayList<String>>() {
        }.getType());
        mobile_min_length_list_NI = new Gson().fromJson(minLenList, new TypeToken<ArrayList<String>>() {
        }.getType());
        mobile_max_length_list_NI = new Gson().fromJson(maxLenList, new TypeToken<ArrayList<String>>() {
        }.getType());
        country_name_list_NI = new Gson().fromJson(nameList, new TypeToken<ArrayList<String>>() {
        }.getType());
        time_zone_list_NI = new Gson().fromJson(zoneList, new TypeToken<ArrayList<String>>() {
        }.getType());
        dial_code_list_NI = new Gson().fromJson(dialCodeList, new TypeToken<ArrayList<String>>() {
        }.getType());

        if (country_code_list_NI == null) {
            //showAlertDialog_Internet(getResources().getString(R.string.offline), false);
            alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", getResources().getString(R.string.offline), "OK", "REMIND LATER", false, true, false, false, true) {
                @Override
                public void onOKButtonClick() {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }

                @Override
                public void onCancelButtonClick() {
                }
            };

            /*Snackbar snackbar = Snackbar.make(linearLayout, getResources().getString(R.string.offline), Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else {
            sharedpreferences = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
            String otp_status = sharedpreferences.getString(OTP_Activity.OTP_VALIDATE, "");

            if (otp_status.equals("1")) {
                Intent in = new Intent(SplashActivity.this, Welcome_Activity.class);
                startActivity(in);
                finish();
            } else {
                if (country_code_list_NI.size() > 0 && symbol_list_NI.size() > 0 && mobile_start_digits_list_NI.size() > 0 && mobile_min_length_list_NI.size() > 0 &&
                        mobile_max_length_list_NI.size() > 0 && country_name_list_NI.size() > 0 && time_zone_list_NI.size() > 0 && dial_code_list_NI.size() > 0) {
                    //Log.d("Check--->", "NI else");
                    Intent i = new Intent(SplashActivity.this, Captcha_Activity.class);
                    i.putExtra("code", country_code_list_NI);
                    i.putExtra("symbol", symbol_list_NI);
                    i.putExtra("start_dg", mobile_start_digits_list_NI);
                    i.putExtra("min_l", mobile_min_length_list_NI);
                    i.putExtra("max_l", mobile_max_length_list_NI);
                    i.putExtra("c_name", country_name_list_NI);
                    i.putExtra("zone", time_zone_list_NI);
                    i.putExtra("dial_code", dial_code_list_NI);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, "Something went wrong.Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }



    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        //showSnack(isConnected);
    }


    public void getPlaystoreVersionVolley() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "https://www.corals.life/configs/a.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String version = response.getString("version");
                    boolean force_update = response.getBoolean("force-update");
                    String min_version = response.getString("min-support-version");
                    final String msg = response.getString("msg");
                    // txtResponse.setText(jsonResponse);
                    Log.d("version---->", currentVersion + " , " + version + " , " + force_update + " , " + min_version + " , " + msg);

                    if (!currentVersion.isEmpty() && !version.isEmpty()) {

                        if (Float.valueOf(min_version) >= (Float.valueOf(currentVersion))) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", msg, "", "OK", false, false, true, false, true) {
                                        @Override
                                        public void onOKButtonClick() {

                                        }

                                        @Override
                                        public void onCancelButtonClick() {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                                            }
                                        }
                                    };
                                }
                            });
                        } else if (Float.valueOf(currentVersion).equals(Float.valueOf(version))) {
                            // no need to update
                            Log.d("version---->", "1" + " ,List :" + country_code_list.size());
                            if (country_code_list.size() > 0 && symbol_list.size() > 0 && mobile_start_digits_list.size() > 0 && mobile_min_length_list.size() > 0 &&
                                    mobile_max_length_list.size() > 0 && country_name_list.size() > 0 && time_zone_list.size() > 0 && dial_code_list.size() > 0) {
                                getParams_No_Internet();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new getParams().execute();
                                    }
                                });
                            }
                            Log.d("version---->", currentVersion + "," + version);

                        } else if ((Float.valueOf(currentVersion) > (Float.valueOf(version)))) {
                            // no need to update
                            Log.d("version---->", "1" + " ,List :" + country_code_list.size());
                            if (country_code_list.size() > 0 && symbol_list.size() > 0 && mobile_start_digits_list.size() > 0 && mobile_min_length_list.size() > 0 &&
                                    mobile_max_length_list.size() > 0 && country_name_list.size() > 0 && time_zone_list.size() > 0 && dial_code_list.size() > 0) {
                                getParams_No_Internet();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new getParams().execute();
                                    }
                                });
                            }
                            Log.d("version---->", currentVersion + "," + version);

                        } else if ((Float.valueOf(currentVersion) < (Float.valueOf(version)) && !force_update)) {
                            // soft update
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", msg, "OK", "REMIND LATER", false, true, true, false, false) {
                                        @Override
                                        public void onOKButtonClick() {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                                            }

                                        }

                                        @Override
                                        public void onCancelButtonClick() {

                                            if (country_code_list.size() > 0 && symbol_list.size() > 0 && mobile_start_digits_list.size() > 0 && mobile_min_length_list.size() > 0 &&
                                                    mobile_max_length_list.size() > 0 && country_name_list.size() > 0 && time_zone_list.size() > 0 && dial_code_list.size() > 0) {

                                                getParams_No_Internet();
                                            } else {
                                                new getParams().execute();
                                            }
                                        }
                                    };

                                }
                            });

                        } else if (Float.valueOf(currentVersion) < (Float.valueOf(version)) && force_update) {
                            //Soft update
                            Log.d("version---->", "2");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", msg, "", "OK", false, false, true, false, true) {
                                        @Override
                                        public void onOKButtonClick() {

                                        }

                                        @Override
                                        public void onCancelButtonClick() {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                                            }
                                        }
                                    };

                                }
                            });
                        } else {
                            Log.d("version---->", "6");
                            //could not get version
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", "Something went wrong.Please try again or re-install the application", "OK", "REMIND LATER", false, true, false, false, true) {
                                        @Override
                                        public void onOKButtonClick() {
                                            Intent a = new Intent(Intent.ACTION_MAIN);
                                            a.addCategory(Intent.CATEGORY_HOME);
                                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(a);
                                        }

                                        @Override
                                        public void onCancelButtonClick() {

                                        }
                                    };
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", "Something went wrong.Please try again or re-install the application", "OK", "REMIND LATER", false, true, false, false, true) {
                                    @Override
                                    public void onOKButtonClick() {
                                        Intent a = new Intent(Intent.ACTION_MAIN);
                                        a.addCategory(Intent.CATEGORY_HOME);
                                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(a);
                                    }

                                    @Override
                                    public void onCancelButtonClick() {

                                    }
                                };
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }

    public void getServerDownStatus() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "https://www.corals.life/configs/a-down.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    boolean capp_down = response.getBoolean("is-capp-down");
                    final String down_title = response.getString("title");
                    final String down_msg = response.getString("Message");
                    Log.d("Down---->", capp_down + "," + down_title + "," + down_msg);

                    if (!capp_down) {
                        getPlaystoreVersionVolley();

                    } else if (capp_down) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, down_title, down_msg, "OK", "REMIND LATER", true, true, false, false, true) {
                                    @Override
                                    public void onOKButtonClick() {
                                        Intent a = new Intent(Intent.ACTION_MAIN);
                                        a.addCategory(Intent.CATEGORY_HOME);
                                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(a);
                                    }

                                    @Override
                                    public void onCancelButtonClick() {

                                    }
                                };
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getServerDownStatus();
                }
            }, 3000);
        } else {
            //showAlertDialog_Internet(getResources().getString(R.string.offline), false);
            alertDialogYesNo = new AlertDialogYesNo(SplashActivity.this, "", getResources().getString(R.string.offline), "OK", "REMIND LATER", false, true, false, false, true) {
                @Override
                public void onOKButtonClick() {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }

                @Override
                public void onCancelButtonClick() {
                }
            };
        }


        MyApplication.getInstance().setConnectivityListener(this);

    }

}
