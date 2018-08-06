package com.hfad.odometer;

import android.app.LoaderManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class OdometerService extends Service {

    private static double distanceInMeters;
    private static Location lastLocation = null;
    private final IBinder binder = new OdometerBinder();

    public class OdometerBinder extends Binder{
        OdometerService getOdometer(){
            return OdometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public double getMiles(){
        return this.distanceInMeters / 1609.344;
    }

    @Override
    public void onCreate(){
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null){
                    lastLocation = location;
                }

                distanceInMeters += location.distanceTo(lastLocation);
                lastLocation = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
        }catch(SecurityException ex){
            ex.printStackTrace();
        }
    }

}
