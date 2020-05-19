package life.corals.onboarding.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.onboarding.Adapter.MyListAdapter_Recyclerview;
import life.corals.onboarding.Adapter.MyListAdapter_Recyclerview_Vouchers;
import life.corals.onboarding.Dialogs.AlertDialogYesNo;
import life.corals.onboarding.R;
import life.corals.onboarding.client.ApiCallback;
import life.corals.onboarding.client.ApiException;
import life.corals.onboarding.client.OkHttpApiClient;
import life.corals.onboarding.client.api.WebMerchantApisApi;
import life.corals.onboarding.client.model.Body;
import life.corals.onboarding.client.model.CashbackPoint;
import life.corals.onboarding.client.model.DevicesReq;
import life.corals.onboarding.client.model.ImageBody;
import life.corals.onboarding.client.model.InlineResponse200;
import life.corals.onboarding.client.model.MerchantOutlet;
import life.corals.onboarding.client.model.RedemptionList;
import life.corals.onboarding.receiver.ConnectivityReceiver;


public class Review_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = Review_Activity.class.getName();
    private LinearLayout button_next,linear_map;
    private boolean isQRRequired = false;
    private ImageView imageView_back;
    private SharedPreferences sharedpreferences_uphoto, sharedpreferences_open_screen, sharedpreferences_business_det, sharedpreferences_Operating_hours, sharedpreferences_intro_business, sharedpreferences_loyalty_points, sharedpreferences_upload_logo, sharedpreferences_otp, sharedpreferences_QR_Status, sharedpreferences_redeem;
    String logo_icon, disp_name, business_name, add1, add2, add3, postal_code, country, contact_name, reg_no, buss_type, buss_type_index, mob_no, mob_no_customers, email, hours, website, competitors, competitors_type, prefix, min_amount, points_unit, points_expires, logo, logo2, intro_buss, location_lat, location_lon, currency, dial_code, country_code, qr_status, redeem_title, redeem_desc, redeem_amt, redeem_bonus, redeem_str_date, redeem_str_time, redeem_exp_date, redeem_exp_time;
    private TextView tv_disp_name, tv_buss_name, tv_add1, tv_add2, tv_add3, tv_country, tv_p_code, tv_reg_no, tv_hours, tv_intro_buss, tv_mob, tv_phn, tv_website, tv_c_name, tv_email, tv_buss_type, tv_min_amt, tv_pnt_unit, tv_pnt_expires, tv_location, tv_qr_status, tv_biz_comp,voucher_title,tv_points_title;
    private ImageView imageView_logo;
    private LinearLayout linearLayout;
    private Body body = new Body();
    private ArrayList<String> competitors_list, competitors_type_list;
    private List<RedemptionList> list;
    private Gson gson;
    private SharedPreferences sharedpreferences_add_redeem;
    private String merId;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView_review_comp,recyclerView_vouchers;
    AlertDialog alertDialog;
    private AlertDialogYesNo alertDialogYesNo;
    SupportMapFragment mapFragment;
    NestedScrollView mainScrollView;
    private ArrayList<String> title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_);

        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected){
            alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", getResources().getString(R.string.offline), "OK", "" + "", false, true, false,false,true) {
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

        gson = new Gson();
        list = new ArrayList<>();
        title_list = new ArrayList<>();
        desc_list = new ArrayList<>();
        bonus_list = new ArrayList<>();
        a_date_list = new ArrayList<>();
        a_time_list = new ArrayList<>();
        e_date_list = new ArrayList<>();
        e_time_list = new ArrayList<>();
        act_dys_list = new ArrayList<>();
        progressDialog = new ProgressDialog(this, R.style.MyTheme);
        recyclerView_review_comp = (RecyclerView) findViewById(R.id.recyclerview_competitors_review);
        recyclerView_vouchers = (RecyclerView) findViewById(R.id.recyclerView_vouchers);
        LinearLayoutManager li = new LinearLayoutManager(Review_Activity.this);
        recyclerView_review_comp.setLayoutManager(li);
        LinearLayoutManager li_vouchers = new LinearLayoutManager(Review_Activity.this);
        recyclerView_vouchers.setLayoutManager(li_vouchers);

        sharedpreferences_add_redeem = getSharedPreferences(Redeem_Points_Activity.MyPREFERENCES_ADD_REDEEM, Context.MODE_PRIVATE);
        sharedpreferences_uphoto = getSharedPreferences(Upload_Photo_Activity.MyPREFERENCES_UPLOAD_PHOTO, Context.MODE_PRIVATE);
        sharedpreferences_redeem = getSharedPreferences(Add_Redemption_Activity.MyPREFERENCES_REDEEM, Context.MODE_PRIVATE);
        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        sharedpreferences_business_det = getSharedPreferences(Business_Details_Activity.MyPREFERENCES_BUSSINESS_DETAILS, Context.MODE_PRIVATE);
        sharedpreferences_Operating_hours = getSharedPreferences(Operating_Hours_Activity.MyPREFERENCES_OPERATING_HRS, Context.MODE_PRIVATE);
        sharedpreferences_intro_business = getSharedPreferences(Intro_Business_Activity.MyPREFERENCES_INTRO_BUSSINESS, Context.MODE_PRIVATE);
        sharedpreferences_loyalty_points = getSharedPreferences(Loyalty_Points_Activity.MyPREFERENCES_LOYALTY_POINTS, Context.MODE_PRIVATE);
        sharedpreferences_upload_logo = getSharedPreferences(Upload_Photo_Activity.MyPREFERENCES_UPLOAD_PHOTO, Context.MODE_PRIVATE);
        sharedpreferences_otp = getSharedPreferences(OTP_Activity.MyPREFERENCES_OTP_VALIDATE, Context.MODE_PRIVATE);
        sharedpreferences_QR_Status = getSharedPreferences(Setup_QR_Code_Activity.MyPREFERENCES_QR_STATUS, Context.MODE_PRIVATE);

        redeem_title = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_TITLE, "");
        redeem_desc = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_DESCRIPTION, "");
        redeem_amt = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_AMOUNT, "");
        redeem_bonus = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_BONUS, "");
        redeem_str_date = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_START_DATE, "");
        redeem_str_time = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_START_TIME, "");
        redeem_exp_date = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_EXPIRY_DATE, "");
        redeem_exp_time = sharedpreferences_redeem.getString(Add_Redemption_Activity.REDEEM_EXPIRY_TIME, "");

        qr_status = sharedpreferences_QR_Status.getString(Setup_QR_Code_Activity.QR_STATUS, "");
        isQRRequired = sharedpreferences_QR_Status.getBoolean(Setup_QR_Code_Activity.QR_REQUIRED, false);

        logo_icon = sharedpreferences_uphoto.getString(Upload_Photo_Activity.UPLOAD_PHOTO_PATH, "");
        logo = sharedpreferences_upload_logo.getString(Upload_Photo_Activity.UPLOAD_PHOTO, "");
        logo2 = sharedpreferences_upload_logo.getString(Upload_Photo_Activity.UPLOAD_PHOTO2, "");
        // Toast.makeText(this, "" + logo_icon, Toast.LENGTH_SHORT).show();
        mob_no = sharedpreferences_otp.getString(OTP_Activity.OTP_MOBILE, "");
        country = sharedpreferences_otp.getString(OTP_Activity.COUNTRY, "");
        currency = sharedpreferences_otp.getString(OTP_Activity.CURRENCY, "");
        dial_code = sharedpreferences_otp.getString(OTP_Activity.DIAL_CODE, "");
        country_code = sharedpreferences_otp.getString(OTP_Activity.COUNTRY_CODE, "");

        Log.d("PATH--->", logo + "," + logo2);

        disp_name = sharedpreferences_intro_business.getString(Intro_Business_Activity.DISPLAY_NAME, "");
        business_name = sharedpreferences_business_det.getString(Business_Details_Activity.Business_Name, "");
        add1 = sharedpreferences_business_det.getString(Business_Details_Activity.Address1, "");
        add2 = sharedpreferences_business_det.getString(Business_Details_Activity.Address2, "");
        add3 = sharedpreferences_business_det.getString(Business_Details_Activity.Address3, "");
        postal_code = sharedpreferences_business_det.getString(Business_Details_Activity.Postal_Code, "");
        reg_no = sharedpreferences_business_det.getString(Business_Details_Activity.Registration_No, "");

        hours = sharedpreferences_Operating_hours.getString(Operating_Hours_Activity.Operating_Hours, "");
        location_lat = sharedpreferences_Operating_hours.getString(Operating_Hours_Activity.Location_LAT, "");
        location_lon = sharedpreferences_Operating_hours.getString(Operating_Hours_Activity.Location_LONG, "");

        intro_buss = sharedpreferences_intro_business.getString(Intro_Business_Activity.INTRO_BUSSINESS, "");
        mob_no_customers = sharedpreferences_intro_business.getString(Intro_Business_Activity.PHONE_NUMBER, "");
        website = sharedpreferences_intro_business.getString(Intro_Business_Activity.WEBSITE, "");
        competitors = sharedpreferences_intro_business.getString(Intro_Business_Activity.COMPETITORS_LIST, "");
        competitors_type = sharedpreferences_intro_business.getString(Intro_Business_Activity.COMPETITORS_TYPE_LIST, "");

        buss_type = sharedpreferences_business_det.getString(Business_Details_Activity.BUSSINESS_TYPE, "");
        contact_name = sharedpreferences_business_det.getString(Business_Details_Activity.CONTACT_NAME, "");
        email = sharedpreferences_business_det.getString(Business_Details_Activity.EMAIL, "");
        prefix = sharedpreferences_business_det.getString(Business_Details_Activity.PREFIX, "");
        buss_type_index = sharedpreferences_business_det.getString(Business_Details_Activity.BUSSINESS_TYPE_INDEX, "");

        min_amount = sharedpreferences_loyalty_points.getString(Loyalty_Points_Activity.AMOUNT_PER_POINTS, "");
        points_unit = sharedpreferences_loyalty_points.getString(Loyalty_Points_Activity.POINTS_PER_UNIT, "");
        points_expires = sharedpreferences_loyalty_points.getString(Loyalty_Points_Activity.POINTS_EXPIRES, "");

        competitors_list = new ArrayList<>();
        competitors_type_list = new ArrayList<>();

        mainScrollView = (NestedScrollView) findViewById(R.id.main_scrollview);
        tv_disp_name = (TextView) findViewById(R.id.text_disp_name);
        tv_buss_name = (TextView) findViewById(R.id.text_buss_name);
        tv_add1 = (TextView) findViewById(R.id.text_add1);
        tv_add2 = (TextView) findViewById(R.id.text_add2);

        tv_add3 = (TextView) findViewById(R.id.text_add3);
        tv_p_code = (TextView) findViewById(R.id.text_postal_code);
        tv_reg_no = (TextView) findViewById(R.id.text_reg_no);
        tv_hours = (TextView) findViewById(R.id.text_operating_hours);

        tv_intro_buss = (TextView) findViewById(R.id.text_intro_buss);
        tv_mob = (TextView) findViewById(R.id.text_mob_no);
        tv_phn = (TextView) findViewById(R.id.text_phn_no);
        tv_website = (TextView) findViewById(R.id.text_website);

        tv_c_name = (TextView) findViewById(R.id.text_contact_name);
        tv_email = (TextView) findViewById(R.id.text_email);
        tv_buss_type = (TextView) findViewById(R.id.text_buss_type);

        tv_min_amt = (TextView) findViewById(R.id.text_min_amount);
        tv_pnt_unit = (TextView) findViewById(R.id.text_points_per_unit);
        tv_pnt_expires = (TextView) findViewById(R.id.text_points_expires);
        tv_country = (TextView) findViewById(R.id.text_country);
        tv_location = (TextView) findViewById(R.id.text_location);
        tv_qr_status = (TextView) findViewById(R.id.text_qr_status);
        tv_biz_comp = (TextView) findViewById(R.id.textview_biz_comp);
        voucher_title = (TextView) findViewById(R.id.voucher_title);
        tv_points_title = (TextView) findViewById(R.id.text_points_title);
        imageView_logo = (ImageView) findViewById(R.id.image_logo);
        linearLayout = (LinearLayout) findViewById(R.id.layout_review);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Review_Activity.this);

        if (!TextUtils.isEmpty(competitors)) {
            competitors_list = new Gson().fromJson(competitors, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
        if (!TextUtils.isEmpty(competitors_type)) {
            competitors_type_list = new Gson().fromJson(competitors_type, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
        if (competitors_type_list.size() != 0) {
            MyListAdapter_Recyclerview myListAdapter_recyclerview = new MyListAdapter_Recyclerview(competitors_type_list);
            recyclerView_review_comp.setAdapter(myListAdapter_recyclerview);
            myListAdapter_recyclerview.notifyDataSetChanged();
        } else {
            tv_biz_comp.setVisibility(View.GONE);
            recyclerView_review_comp.setVisibility(View.GONE);
        }
        Log.d("Redemption_size--->", "" + competitors_list + "," + competitors + "," + competitors_type_list);


        if (qr_status.equals("1")) {
            tv_qr_status.setText("QR scanned Successfully");
            tv_qr_status.setTextColor(this.getResources().getColor(R.color.button_background));
        } else {
            tv_qr_status.setText("New QR Generated");
            tv_qr_status.setTextColor(this.getResources().getColor(R.color.blue_green));
        }

        tv_disp_name.setText(disp_name);
        tv_buss_name.setText(business_name);
        tv_add1.setText(add1);
        if (!TextUtils.isEmpty(add2)) {
            tv_add2.setText(add2);
        } else {
            tv_add2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(add3)) {
            tv_add3.setText(add3);
        } else {
            tv_add3.setVisibility(View.GONE);
        }

        tv_p_code.setText(postal_code);
        tv_reg_no.setText(reg_no);
        tv_hours.setText(hours);

        tv_intro_buss.setText(intro_buss);
        tv_mob.setText("+" + dial_code + " " + mob_no);
        tv_phn.setText("+" + dial_code + " " + mob_no_customers);
        tv_website.setText(website);

        tv_buss_type.setText(buss_type);
        tv_c_name.setText(prefix + "." + contact_name);
        tv_email.setText(email);

        tv_country.setText(country);
        tv_min_amt.setText(currency + "" + min_amount);
        tv_points_title.setText("No.of points issued for  "+currency+"1 : ");
        tv_pnt_unit.setText(points_unit);
        tv_pnt_expires.setText(points_expires);
        tv_country.setText(country);
        tv_location.setText(location_lat + " , " + location_lon);
        // Toast.makeText(this, ""+isQRRequired, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(logo_icon) && logo_icon != null) {
            byte[] imageAsBytes = Base64.decode(logo_icon.getBytes(), Base64.DEFAULT);
            imageView_logo.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }

        button_next = (LinearLayout) findViewById(R.id.button_submit);
        linear_map = (LinearLayout) findViewById(R.id.linear_map);
        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

                    title_list.add(title);
                    desc_list.add(desc);
                    bonus_list.add(bonus);
                    a_date_list.add(act_dt);
                    a_time_list.add(act_time);
                    e_date_list.add(exp_date);
                    e_time_list.add(exp_time);
                    act_dys_list.add(act_days);
                }
                voucher_title.setVisibility(View.VISIBLE);
                recyclerView_vouchers.setVisibility(View.VISIBLE);
                MyListAdapter_Recyclerview_Vouchers customAdapter = new MyListAdapter_Recyclerview_Vouchers(Review_Activity.this, title_list, desc_list, bonus_list, a_date_list, a_time_list, e_date_list, e_time_list, act_dys_list);
                recyclerView_vouchers.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();

            } else {
                voucher_title.setVisibility(View.GONE);
                recyclerView_vouchers.setVisibility(View.GONE);
            }

        }


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean connected = ConnectivityReceiver.isConnected();
                if (connected) {

                    //WITHOUT SERVER CALL
                    /*progressDialog.setMessage("Merchant registering...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();

                            Toast.makeText(Review_Activity.this, "Merchant Registered Successfully! ", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(Review_Activity.this, Success_Activity.class);
                            startActivity(in);
                            finish();

                        }
                    },2000);*/

                    //SERVER CALL
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            callApi();
                        }
                    }).start();

                } else {
                    Snackbar snackbar = Snackbar.make(linearLayout, getResources().getString(R.string.offline), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                linear_map.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        int action = event.getActionMasked();
                        switch (action) {
                            case MotionEvent.ACTION_UP:
                                mainScrollView.requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                        return false;
                    }
                });

            }
        });

    }

    private void getRequestBody() {

        int size = 27;
        ArrayList<String> arrayList_biz_competitors = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            String data = String.valueOf(i);
            if (data.length() == 1) {
                data = "0" + data;
            }
            arrayList_biz_competitors.add(data);
        }
        arrayList_biz_competitors.remove(buss_type_index);
        Log.d("Redeem--->", "Act_date :" + redeem_str_date + " ,Act_time :" + redeem_str_time + " ,Exp_date :" + redeem_exp_date + " ,Exp_time :" + redeem_exp_time);

       // List<RedemptionList> redemptionListArrayList = new ArrayList<>();

        String add_redeem = sharedpreferences_add_redeem.getString(Redeem_Points_Activity.ADD_REDEEM, "");
        if (!TextUtils.isEmpty(add_redeem)) {
            Type type = new TypeToken<List<RedemptionList>>() {
            }.getType();
            list = gson.fromJson(add_redeem, type);
        }
        Log.d("RedeemList--->", "" + add_redeem + "," + list);

        MerchantOutlet merchantOutlet = new MerchantOutlet();
        merchantOutlet.setGeoLat(location_lat);
        merchantOutlet.setGeoLon(location_lon);
        merchantOutlet.setOutletAdd1(add1);
        merchantOutlet.setOutletAdd2(add2);
        merchantOutlet.setOutletAdd3(add3);
        merchantOutlet.setOutletname(business_name);
        merchantOutlet.setOutletRegno(reg_no);
        merchantOutlet.setPhoneNo(dial_code + "" + mob_no);
        merchantOutlet.setPostCode(postal_code);
        merchantOutlet.setQrDisplayName(disp_name);
        merchantOutlet.setQrMerInfUniqueCd("26");
        merchantOutlet.setQrRequired(isQRRequired);
        merchantOutlet.setQrTxnCurrency(currency);
        merchantOutlet.setQrTxnCountry(country);

        List<MerchantOutlet> merchantOutletArrayList = new ArrayList<>();
        merchantOutletArrayList.add(merchantOutlet);

        CashbackPoint cashbackPoint = new CashbackPoint();
        cashbackPoint.setIsIgnoreDecimal(true);
        cashbackPoint.setMinSpend(min_amount);
        cashbackPoint.setPaymentCbPercentage("1.0");
        cashbackPoint.setPointsExpiryDays(points_expires);
        cashbackPoint.setPointsPerCurrUnit(points_unit);

        List<CashbackPoint> cashbackPointArrayList = new ArrayList<>();
        cashbackPointArrayList.add(cashbackPoint);

        DevicesReq devicesReq = new DevicesReq();
        devicesReq.setDeviceHolderName(contact_name);
        devicesReq.setDeviceHolderRole("A");

        List<DevicesReq> devicesReqArrayList = new ArrayList<>();
        devicesReqArrayList.add(devicesReq);

        body.setCountryCd(country_code);
        body.setMerCurSymbol(currency);
        body.setMerCompetitors(competitors_list);

        body.setMerCategoryId(buss_type_index);
        body.setCountryName(country);
        body.setIsforceMerscantopay(false);

        body.setBizDisplayName(disp_name);
        body.setShortDescription(intro_buss);
        body.setContactName(contact_name);

        body.setContactMobile(dial_code + "" + mob_no);
        body.setContactAdd(add1+" , "+add2+" , "+add3);
        body.setBizNamelegal(business_name);

        body.setDaysWalletexpiry(points_expires);
        body.setEmailAdd(email);
        body.setBizRegnno(reg_no);

        body.setPostCode(postal_code);
        body.setMonthlyPayday("1");
        body.setWebPath(website);

        body.setDevicesReq(devicesReqArrayList);
        body.setSessiontoken("No Token");
        body.setCashbackPoints(cashbackPointArrayList);
        body.setMerchantOutlets(merchantOutletArrayList);
        body.setRedemptionList(list);

        Log.d("Merchant--->", competitors_list + "," + country_code + "," + currency + "," + arrayList_biz_competitors + "," + buss_type_index + "," + country + "," + disp_name + "," + intro_buss + "," + contact_name + "," + mob_no + "," +
                add1 + "," + business_name + "," + points_expires + "," + email + "," + reg_no + "," + postal_code + "," + devicesReqArrayList + "," + "FJHH900D" + "," +
                cashbackPointArrayList + "," + merchantOutletArrayList);

        Log.d("ReviewBody", "getRequestBody: " + body);
    }

    private void callApi() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            getRequestBody();
            try {
                Log.d("Body--->", "" + body.toString() + ",File :" + logo + "," + logo2);
                registerDevice(body);
            } catch (Exception e) {
                Log.d("Exception--->", "" + e.getMessage().toString());
            }
        } else {
            Snackbar.make(linearLayout, getResources().getString(R.string.offline), Snackbar.LENGTH_LONG).show();
        }
    }

    private void uploadImage(ImageBody imageBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(Review_Activity.this));

        WebMerchantApisApi webMerchantApisApi = new WebMerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.webMerchantImageUploadPostAsync(imageBody, new ApiCallback<InlineResponse200>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("ImageFailed--->", "Code : 1 " + e.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant Registration Failed.Please Try Again!", "YES", "OK", false, false, true,false,true) {
                            @Override
                            public void onOKButtonClick() {

                            }

                            @Override
                            public void onCancelButtonClick() {

                            }
                        };

                    }
                });
            }

            @Override
            public void onSuccess(InlineResponse200 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("ImageFailed--->", "Code : 2 " + result.getStatusCode());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant Registered Successfully!", "OK", "OK", false, true, false,true,false) {
                                @Override
                                public void onOKButtonClick() {

                                    Intent in=new Intent(Review_Activity.this,Success_Activity.class);
                                    startActivity(in);
                                    finish();
                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };

                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant Registered Failed.Please try again!", "YES", "OK", false, false, true,false,true) {
                                @Override
                                public void onOKButtonClick() {

                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };

                        }
                    });
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }

    private void registerDevice(Body requestBody) throws ApiException {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage("Merchant registering...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });

        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(Review_Activity.this));

        WebMerchantApisApi webMerchantApisApi = new WebMerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.webMerchantOnboardingPostAsync(requestBody, new ApiCallback<InlineResponse200>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Log.d("Failed--->", "Code : 1 " + e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //showAlertDialog("Merchant Registration Failed.Please Try Again!", false);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant Registration Failed.Please Try Again!", "YES", "OK", false, false, true,false,true) {
                                    @Override
                                    public void onOKButtonClick() {

                                    }

                                    @Override
                                    public void onCancelButtonClick() {

                                    }
                                };

                            }
                        });

                    }
                });
                Log.d("onFailure--->", "" + e.getMessage());
                //Toast.makeText(Review_Activity.this, ""+e.getMessage().toString()+","+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(InlineResponse200 result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("Failed--->", "Code :" + result.getStatusCode());

                if (Integer.parseInt(result.getStatusCode()) == 406) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Failed--->", "Code : 2");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant already exist!", "YES", "OK", false, false, true,false,true) {
                                        @Override
                                        public void onOKButtonClick() {

                                        }

                                        @Override
                                        public void onCancelButtonClick() {

                                        }
                                    };

                                }
                            });
                        }
                    });
                } else if (Integer.parseInt(result.getStatusCode()) == 200) {
                    final InlineResponse200 inlineResponse200 = result;
                    merId = inlineResponse200.getMerchantId();
                    Log.d("Failed--->", "MerId : " + merId + " , " + logo + " , " + logo2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if (!TextUtils.isEmpty(merId) && !TextUtils.isEmpty(logo) && !TextUtils.isEmpty(logo2)) {
                                    ImageBody imageBody = new ImageBody();
                                    imageBody.setMerId(merId);
                                    imageBody.setBigImage(logo);
                                    imageBody.setSmallImage(logo2);
                                    uploadImage(imageBody);
                                } else {
                                    if (progressDialog != null) {
                                        progressDialog.dismiss();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant Registration Failed.Please Try Again!", "YES", "OK", false, false, true,false,true) {
                                                @Override
                                                public void onOKButtonClick() {

                                                }

                                                @Override
                                                public void onCancelButtonClick() {

                                                }
                                            };

                                        }
                                    });

                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Failed--->", "Code : 3");
                            //showAlertDialog("Merchant R.egistration Failed.Please Try Again!", false);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogYesNo = new AlertDialogYesNo(Review_Activity.this, "", "Merchant Registration Failed.Please Try Again!", "YES", "OK", false, false, true,false,true) {
                                        @Override
                                        public void onOKButtonClick() {

                                        }

                                        @Override
                                        public void onCancelButtonClick() {

                                        }
                                    };

                                }
                            });

                        }
                    });

                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "11");
        editor.commit();

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "11");
        editor.apply();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = sharedpreferences_open_screen.edit();
        editor.putString(Welcome_Activity.OPEN_SCREEN, "11");
        editor.apply();

    }


    @Override
    public void onBackPressed() {
        Intent in = new Intent(Review_Activity.this, Add_Redemption_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);


        double lat=Double.parseDouble(location_lat);
        double lon=Double.parseDouble(location_lon);

        Log.d("Review_location--->",""+lat+" , "+lon);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
    }





}
