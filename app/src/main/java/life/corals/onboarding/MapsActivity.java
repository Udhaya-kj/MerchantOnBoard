package life.corals.onboarding;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import life.corals.onboarding.Activity.Operating_Hours_Activity;
import life.corals.onboarding.Activity.SplashActivity;
import life.corals.onboarding.Utils.MyApplication;
import life.corals.onboarding.Utils.NetworkUtil;
import life.corals.onboarding.receiver.ConnectivityReceiver;
import life.corals.onboarding.receiver.NetworkStateChangeReceiver;

import static life.corals.onboarding.receiver.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectivityReceiver.ConnectivityReceiverListener {

    private GoogleMap mMap;

    private TextView location;
    private Button confirmBtn;
    private ProgressDialog dialog;

    private Double lat_;
    private Double lon_;


    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 300;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final String TAG = MapsActivity.class.getSimpleName();

    private ProgressDialog pd;
    FrameLayout frameLayout_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pd = new ProgressDialog(this);
        pd.setMessage("Getting location...");
        pd.show();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        location = findViewById(R.id.text_map_address);
        confirmBtn = findViewById(R.id.button_map_next);
        frameLayout_map=(FrameLayout)findViewById(R.id.layout_map);
        dialog = new ProgressDialog(MapsActivity.this);

        checkConnection();
    /*    IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "connected" : "disconnected";

                if(networkStatus.equals("connected")) {
                    if(checkPlayServices()) {
                        init();
                        startLocationButtonClick();
                    }
                    else {
                        Toast.makeText(MapsActivity.this, "This device is not supported for required Goole Play Services", Toast.LENGTH_SHORT).show();
                    }
                }
                //Toast.makeText(SplashActivity.this, ""+networkStatus, Toast.LENGTH_SHORT).show();

            }
        }, intentFilter);*/


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("setOnClickListener", "onClick: ");

                if (lat_ == null && lon_ == null) {
                    Toast.makeText(MapsActivity.this, "Could not get location.please try again!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MapsActivity.this, Operating_Hours_Activity.class);
                    intent.putExtra("location_lat", String.valueOf(lat_));
                    intent.putExtra("location_lon",String.valueOf(lon_));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();

                }
            }
        });

     /*   linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location locationn) {
                        // GPS location can be null if GPS is switched off
                        if (locationn != null) {
                            lat_ = locationn.getLatitude();
                            lon_ = locationn.getLongitude();
                            location.setText(lat_ + " , " + lon_);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat_, lon_), 18));

                        }

                        //Toast.makeText(MapsActivity.this, "lat " + location.getLatitude() + "\nlong " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

            }
        });*/
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;
                double lat = latLng.latitude;
                double lon = latLng.longitude;

                lat_ = Double.valueOf(new DecimalFormat(".######").format(lat));
                lon_ = Double.valueOf(new DecimalFormat(".######").format(lon));

                location.setText(lat_ + " , " + lon_);
                // Toast.makeText(MapsActivity.this, lat_+" , "+lon_, Toast.LENGTH_SHORT).show();

            }
        });
        //locationUpdate();

      /*  double mylat = mMap.getCameraPosition().target.latitude;
        double mylon = mMap.getCameraPosition().target.longitude;

        Toast.makeText(this, mylat+","+mylon, Toast.LENGTH_SHORT).show();*/

        /*googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

         *//*       LatLng latLng=mMap.getCameraPosition().target;
                double lat_ = latLng.latitude;
                double lon_ = latLng.longitude;
*//*
                lat_ = mMap.getCameraPosition().target.latitude;
                lon_ =  mMap.getCameraPosition().target.longitude;

                Log.d("Location---->",lat_+","+lon_);
                String new_lat = new DecimalFormat(".######").format(lat_);
                String new_lon = new DecimalFormat(".######").format(lon_);


                LatLng sydney = new LatLng(lat_, lon_);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                if ((!TextUtils.isEmpty(new_lat)) && (!TextUtils.isEmpty(new_lon))) {
                    //String address = getAddress(lat_, lon_);
                        location.setText(new_lat+" , " + new_lon);
                }
            }
        });*/

    }

    private void updateLocationUI() {

        if (mCurrentLocation != null) {
            pd.dismiss();
            location.setVisibility(View.VISIBLE);
            LatLng sydney = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            if (sydney != null) {
                try {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));
                    location.setText(mCurrentLocation.getLatitude() + " , " + mCurrentLocation.getLongitude());
                } catch (Exception e) {
                    Log.d("Map_Exception--->", "" + e.getMessage().toString());
                    Toast.makeText(this, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            location.setText("Could not get location");
        }
    }

/*    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        String address = null;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        } else {
            address = "No address found for the location";
        }
            *//*   String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL*//*


        return address;
    }*/


    private void sendResultsToReceiver(String message) {
        location.setText(message);
    }

   /* private void showLcation(double latitude, double longitude) {
        String msg = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size() == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(msg);
        } else {
            Address address = addresses.get(0);
            StringBuilder addressDetails = new StringBuilder();

            if (address.getFeatureName() != null) {
                addressDetails.append(address.getFeatureName());
            }

            if ((address.getThoroughfare() != null)) {
                addressDetails.append(",");
                addressDetails.append(address.getThoroughfare());
            }

            String local = "";
            String subAdmin = "";
            if (address.getLocality() != null) {
                addressDetails.append(",");
                addressDetails.append(address.getLocality());
                local = address.getLocality();
            }
            if (address.getSubAdminArea() != null) {
                subAdmin = address.getSubAdminArea();
            }
            if (!local.equals(subAdmin)) {
                addressDetails.append(",");
                addressDetails.append(address.getSubAdminArea());
            }

            if (address.getAdminArea() != null) {
                addressDetails.append(",");
                addressDetails.append(address.getAdminArea());
            }

            if (address.getCountryName() != null) {
                addressDetails.append(",");
                addressDetails.append(address.getCountryName());
                addressDetails.append("\n");
            }

            if (address.getPostalCode() != null) {
                addressDetails.append("Postal Code: ");
                addressDetails.append(address.getPostalCode());
                addressDetails.append("\n");
            }

            sendResultsToReceiver(addressDetails.toString());
        }
    }
*/

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //  Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library

        //Toast.makeText(this, "Locatoin Request...", Toast.LENGTH_SHORT).show();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            mRequestingLocationUpdates = true;
                            startLocationUpdates();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).onSameThread().check();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        //mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();


    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 2404)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        Log.i("TAG---->", "This device is  supported.");
        return true;
    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(final boolean isConnected) {


        if (isConnected) {

            if (checkPlayServices()) {
                init();
                startLocationButtonClick();
            } else {
                Toast.makeText(this, "This device is not supported for required Goole Play Services", Toast.LENGTH_SHORT).show();
            }


        } else {

            Snackbar snackbar = Snackbar
                    .make(frameLayout_map, "No internet connection.Make sure WI-Fi or Cellular data is turned on.", Snackbar.LENGTH_LONG);
            snackbar.show();
            // Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);


    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFusedLocationClient = null;
        mMap = null;
    }
}
