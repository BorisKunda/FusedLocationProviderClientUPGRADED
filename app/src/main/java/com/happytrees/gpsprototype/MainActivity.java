package com.happytrees.gpsprototype;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity  {// interface LocationListener used for receiving notifications from the FusedLocationProviderApi when the location has changed.
    //GPS CHECK --!!!!!!!!!!!!!!!!!!!
    //PERMISSIONS--!!!!!!!!!!!!!!!!!!!
    //GOOGLE PLAY CHECK--!!!!!!!!!!!!!!!!!!!
    //CHECK ANDROID 6.0 OR HIGHER IN RUNTIME --!!!!!!!!!!!!!!!!!!!

    //VARIABLES
    public FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTV, longitudeTV;
    public LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;
    public static final int REQUEST_CODE_LOCATION = 4;
    //private boolean mRequestingLocationUpdates = true;//boolean used to track whether the user has turned location updates on or off.meanwhile we set it true by default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//VERSION_CODES.M = Android 6.0  --> we check if our minimum sdk greater or equal to 6.0 (this when runtime permissions first took place)
        {
            checkLocationPermission();

        }


        //create an instance of the Fused Location Provider Client
       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }
   ////////////////////////////////////////////////////////////////METHODS//////////////////////////////////////////////////////////////////////////////


    //DON'T ASK ME AGAIN OPTION WILL APPEAR IF USER INITIALLY DECLINED PERMISSION
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//if there wasn't already permission granted
            // Should we show an explanation?--> we will give user an explanation if user  previously denied permission.How we know if he previously denied it  --> if shouldShowRequestPermissionRationale returned true then we know he previously denied it
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {// shouldShowRequestPermissionRationale() -->  this method returns true if the app has requested this permission previously and the user denied the request.
                //HERE WE WILL WRITE CODE EXPLAINED WHY GRANTING THIS SPECIFIC PERMISSION IS WORTH IT
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION );
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {//received permission result for location permission

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {// If request is cancelled, the result arrays are empty.
                // permission was granted, yay! Do the
                // location-related task you need to do.
            }else{
         Log.e("LOCATION PERMISSION","DENIED");
            }
        }
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
