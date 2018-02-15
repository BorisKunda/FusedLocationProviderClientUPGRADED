package com.happytrees.gpsprototype;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements LocationListener {// interface LocationListener used for receiving notifications from the FusedLocationProviderApi when the location has changed.
    //GPS CHECK --!!!!!!!!!!!!!!!!!!!
    //PERMISSIONS--!!!!!!!!!!!!!!!!!!!
    //GOOGLE PLAY CHECK--!!!!!!!!!!!!!!!!!!!
    //CHECK ANDROID 6.0 OR HIGHER IN RUNTIME --!!!!!!!!!!!!!!!!!!!

    //VARIABLES
    public FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTV, longitudeTV;
    public LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = true;//boolean used to track whether the user has turned location updates on or off.meanwhile we set it true by default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CHECK IF THERE ALREADY WAS  LOCATION PERMISSION GRANTED
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //create an instance of the Fused Location Provider Client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //create location request + set requirements for it
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);//10 seconds
        mLocationRequest.setFastestInterval(5000);//5 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //set TextView
        latitudeTV = (TextView) findViewById(R.id.latitudeID);
        longitudeTV = (TextView) findViewById(R.id.longitudeID);


        //getting last recorded(cashed) location
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                   latitudeTV.setText(" " + location.getLatitude() );
                   longitudeTV.setText(" " + location.getLongitude());
                }else{
                    Log.e("TAG","no location recorded");    //there wasn't recorded location  which can be due to turned off GPS,new device,previously turned off GPS or other reasons
                }
            }
        });

        //getting result from location update
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {//callback on request location updates by requestLocationUpdates()
                for (Location location : locationResult.getLocations()) {
                    Log.e("TAG"," " + location.getLatitude() + " " + location.getLongitude() );
                }
            };
        };


    }

    @Override
    public void onLocationChanged(Location location) {
        //code
    }

    //request location updates when activity(app) is active
    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    //stop location updates when activity(app) is inactive
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    @SuppressLint("MissingPermission")//tells system to chill out cause there already performed permission check so there no need in additional one
    //request location updates method
    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null );//requestLocationUpdates() --> Requests location updates with a callback
    }
    //stop location updates method
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}







/*
SAVING STATE
@Override
protected void onSaveInstanceState(Bundle outState) {
    outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
            mRequestingLocationUpdates);
    // ...
    super.onSaveInstanceState(outState);
}
 */
/*
GETTING LAST KNOWN LOCATION
mFusedLocationClient.getLastLocation()
        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                }
            }
        });
 */
/*
CHANGING SETTINGS
protected void createLocationRequest() {
    LocationRequest mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
}
 */