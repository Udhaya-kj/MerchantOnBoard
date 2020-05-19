package life.corals.onboarding.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import life.corals.onboarding.Dialogs.AlertDialogYesNo;
import life.corals.onboarding.MapsActivity;
import life.corals.onboarding.R;
import life.corals.onboarding.Utils.MyApplication;
import life.corals.onboarding.Utils.NetworkUtil;
import life.corals.onboarding.receiver.ConnectivityReceiver;

public class Operating_Hours_Activity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static final int REQUEST_CODE = 1;
    private LinearLayout button_next;
    private EditText editText_hours;

    public static final String MyPREFERENCES_OPERATING_HRS = "MyPrefs_Operating_Hours";
    public static final String Operating_Hours = "Operating_Hours";
    public static final String Location_LAT = "Location_LAT";
    public static final String Location_LONG = "Location_LONG";
    private TextView text_show_location;
    private SharedPreferences sharedpreferences;
    private SharedPreferences sharedpreferences_open_screen;
    private ImageView imageView_back;
    private FrameLayout framelayout;

    //check permission
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;
    private String latt, lonn;
    private TextView textView_lat,textView_lon;
    private LinearLayout linearLayout_location,layout_change_location;
    private AlertDialogYesNo alertDialogYesNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operating__hours_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);

        framelayout = findViewById(R.id.framelayout_operating_hours);
        editText_hours = (EditText) findViewById(R.id.edit_operating_hours);
        button_next = (LinearLayout) findViewById(R.id.button_next_operating_hours);

        text_show_location = (TextView) findViewById(R.id.text_show_location);
        layout_change_location = (LinearLayout) findViewById(R.id.layout_change_location);
        linearLayout_location = (LinearLayout) findViewById(R.id.layout_location );

        textView_lat = (TextView) findViewById(R.id.text_lat);
        textView_lon = (TextView) findViewById(R.id.text_long);

        layout_change_location.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(MyPREFERENCES_OPERATING_HRS, Context.MODE_PRIVATE);
        String hours = sharedpreferences.getString(Operating_Hours, "");
        String loc_lat = sharedpreferences.getString(Location_LAT, "");
        String loc_lon = sharedpreferences.getString(Location_LONG, "");

        editText_hours.setText(hours);
        editText_hours.setSelection(editText_hours.getText().length());
        text_show_location.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Click here" + "</u>  </font>"));

        if ((!TextUtils.isEmpty(loc_lat)) && (!TextUtils.isEmpty(loc_lon)) && (!StringUtils.isEmpty(loc_lat)) && (!StringUtils.isEmpty(loc_lon))) {
           // Toast.makeText(this, loc_lat+"," + loc_lon, Toast.LENGTH_SHORT).show();

            text_show_location.setVisibility(View.GONE);
            linearLayout_location.setVisibility(View.VISIBLE);
            textView_lat.setText(String.valueOf(loc_lat));
            textView_lon.setText(String.valueOf(loc_lon));
            //text_show_location.setText("Lat :" + new DecimalFormat(".######").format(lat_) + " , Long :" + new DecimalFormat(".######").format(lon_));
            layout_change_location.setVisibility(View.VISIBLE);
            //layout_change_location.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Change Location" + "</u>  </font>"));
        }
        else {
            text_show_location.setVisibility(View.VISIBLE);
            linearLayout_location.setVisibility(View.GONE);
            layout_change_location.setVisibility(View.GONE);
        }


        if (getIntent().getExtras() != null) {
            String cur_loc_lat = getIntent().getStringExtra("location_lat");
            String cur_loc_lon = getIntent().getStringExtra("location_lon");
            Log.d("Locaion--->",cur_loc_lat+","+cur_loc_lon);
            if (!TextUtils.isEmpty(cur_loc_lat) && !TextUtils.isEmpty(cur_loc_lon)) {
                double lat_ = Double.parseDouble(cur_loc_lat);
                double lon_ = Double.parseDouble(cur_loc_lon);

                text_show_location.setVisibility(View.GONE);
                linearLayout_location.setVisibility(View.VISIBLE);
                textView_lat.setText(String.valueOf(lat_));
                textView_lon.setText(String.valueOf(lon_));
                //text_show_location.setText("Lat :" + new DecimalFormat(".######").format(lat_) + " , Long :" + new DecimalFormat(".######").format(lon_));
                layout_change_location.setVisibility(View.VISIBLE);
                //layout_change_location.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Change Location" + "</u>  </font>"));
            } else {
                Toast.makeText(this, "Could not get location.please try again!", Toast.LENGTH_SHORT).show();
            }
        }

        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        text_show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    //checkLocationPermission();
                    locationStatusCheck();
                } else {
                    Snackbar snackbar = Snackbar.make(framelayout, getResources().getString(R.string.offline), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        layout_change_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    //checkLocationPermission();
                     locationStatusCheck();
                } else {
                    Snackbar snackbar = Snackbar.make(framelayout, getResources().getString(R.string.offline), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String et_hours = editText_hours.getText().toString();
                String location_lat = textView_lat.getText().toString();
                String location_long = textView_lon.getText().toString();

                if (!TextUtils.isEmpty(et_hours)) {

                        if (!TextUtils.isEmpty(location_lat) && !TextUtils.isEmpty(location_long) && !StringUtils.isEmpty(location_lat) && !StringUtils.isEmpty(location_long) && !location_lat.equals("0.0")&& !location_long.equals("0.0") ) {

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Operating_Hours, et_hours);
                            editor.putString(Location_LAT, location_lat);
                            editor.putString(Location_LONG, location_long);
                            editor.commit();

                            Intent in = new Intent(getApplicationContext(), Upload_Photo_Activity.class);
                            //overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
                            startActivity(in);
                            finish();
                        }
                    else {
                        Toast.makeText(Operating_Hours_Activity.this, "Select Location of your place on the Map", Toast.LENGTH_LONG).show();
                    }


                } else {
                    editText_hours.setError("Enter valid operating hours");
                    editText_hours.requestFocus();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "6");
        editor_os.commit();

        String et_hours = editText_hours.getText().toString();
        String location_lat = textView_lat.getText().toString();
        String location_long = textView_lon.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Operating_Hours, et_hours);
        editor.putString(Location_LAT, location_lat);
        editor.putString(Location_LONG, location_long);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "6");
        editor_os.commit();

        String et_hours = editText_hours.getText().toString();
        String location_lat = textView_lat.getText().toString();
        String location_long = textView_lon.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Operating_Hours, et_hours);
        editor.putString(Location_LAT, location_lat);
        editor.putString(Location_LONG, location_long);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor_os = sharedpreferences_open_screen.edit();
        editor_os.putString(Welcome_Activity.OPEN_SCREEN, "6");
        editor_os.commit();

        String et_hours = editText_hours.getText().toString();
        String location_lat = textView_lat.getText().toString();
        String location_long = textView_lon.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Operating_Hours, et_hours);
        editor.putString(Location_LAT, location_lat);
        editor.putString(Location_LONG, location_long);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Operating_Hours_Activity.this, Intro_Business_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //Success Perform Task Here
                    openMap();
                    //Toast.makeText(this, "Permission Enabled Result OK", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.e("GPS", "User denied to access location");
                    openGpsEnableSetting();
                    break;
            }
        } else if (requestCode == REQUEST_ENABLE_GPS) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGpsEnabled) {
                openGpsEnableSetting();
            } else {
                openMap();
                //Toast.makeText(this, "Permission Enabled gps enabled", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void openGpsEnableSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_ENABLE_GPS);
    }

    private void sendResultsToReceiver(String message) {
        text_show_location.setText(message);
        //layout_change_location.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Change Location" + "</u>  </font>"));
    }



    private void openMap() {

        text_show_location.setClickable(false);
        Intent intent = new Intent(Operating_Hours_Activity.this, MapsActivity.class);
        startActivity(intent);
        finish();

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(final boolean isConnected) {
        if (!isConnected) {
            Snackbar snackbar = Snackbar.make(framelayout, "You are offline.please check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
        checkConnection();
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    public void locationStatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //buildAlertMessageNoGps();
            //9-1-2020
            //showLocationAlert("Your GPS seems to be disabled, do you want to enable it?");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialogYesNo = new AlertDialogYesNo(Operating_Hours_Activity.this, "Location Service is disabled", "Your GPS seems to be disabled, do you want to enable it?", "YES", "NO", true, true, true,false,true) {
                        @Override
                        public void onOKButtonClick() {

                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }

                        @Override
                        public void onCancelButtonClick() {

                        }
                    };

                }
            });


        } else {
            enableGpsLocation();
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable GPS").setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        Toast.makeText(Operating_Hours_Activity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void checkLocationPermission() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        mSettingsClient = LocationServices.getSettingsClient(Operating_Hours_Activity.this);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success Perform Task Here
                        openMap();
                        // Toast.makeText(Operating_Hours_Activity.this, "Permission already Enabled", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(Operating_Hours_Activity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("GPS", "Unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.e("GPS", "Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.e("GPS", "checkLocationSettings -> onCanceled");
                    }
                });

    }


    void enableGpsLocation() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    openMap();
                                }
                            },500);

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            enableGpsLocation();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}
