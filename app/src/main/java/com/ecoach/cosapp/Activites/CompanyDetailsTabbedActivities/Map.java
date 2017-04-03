package com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;

public class Map extends Activity implements OnMapReadyCallback {
    private GoogleMap googleMap;

    MapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



         fragment= (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        fragment.getMapAsync(this);
               // .add(R.id.map, fragment).commit();



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng point;

        try{

            point = new LatLng(Double.parseDouble(Application.getSelectedCompanyObbject().getCompanyLat()),
                    Double.parseDouble(Application.getSelectedCompanyObbject().getCompanyLong()));

            googleMap.addMarker(new MarkerOptions().position(point)
                    .title(Application.getSelectedCompanyObbject().getCompanyName()).icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point,17));

        }catch (Exception ex){

            ex.printStackTrace();

        }


        //googleMap.set
    }
}
