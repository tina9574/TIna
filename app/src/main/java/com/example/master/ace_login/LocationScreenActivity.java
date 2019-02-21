package com.example.master.ace_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationScreenActivity extends AppCompatActivity implements OnMapReadyCallback {

    private AlertDialog dialog;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_screen);

        //Google Map Settings
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //구글 맵 객체를 불러온다
        mMap = googleMap;

        // UCSD에 대한 위치 선정
        LatLng ucsd = new LatLng(32.880200, -117.233924);
        LatLng atkinson_hall = new LatLng(32.882472, -117.234819);
        LatLng geisel_library = new LatLng(32.881382, -117.237441);
        LatLng price_center = new LatLng(32.879822, -117.236201);
        LatLng book_store = new LatLng(32.879489, -117.236942);

        // 구글 맵에 표시할 마커에 대한 옵션 설정 및 마커 생성
        mMap.addMarker(new MarkerOptions().position(ucsd).title("UCSD").snippet("Tem：　/ SO₂：　/ O₃：　/ CO：　/ NO₂：　/ Fine Dust："));
        mMap.addMarker(new MarkerOptions().position(atkinson_hall).title("Atkinson Hall").snippet("Tem：　/ SO₂：　/ O₃：　/ CO：　/ NO₂：　/ Fine Dust："));
        mMap.addMarker(new MarkerOptions().position(geisel_library).title("Geisel Library").snippet("Tem：　/ SO₂：　/ O₃：　/ CO：　/ NO₂：　/ Fine Dust："));
        mMap.addMarker(new MarkerOptions().position(price_center).title("UCSD Price Center").snippet("Tem：　/ SO₂：　/ O₃：　/ CO：　/ NO₂：　/ Fine Dust："));
        mMap.addMarker(new MarkerOptions().position(book_store).title("UCSD Book Store").snippet("Tem：　/ SO₂：　/ O₃：　/ CO：　/ NO₂：　/ Fine Dust："));


        // 카메라를 0000 위치로 옮긴다
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ucsd));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atkinson_hall));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(geisel_library));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(price_center));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(book_store));

        // zoom 하기
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsd, zoomLevel));

    }

    public void onClick_signout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocationScreenActivity.this);
        dialog = builder.setMessage("Are you sure to want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(LocationScreenActivity.this, SignInActivity.class);
                        startActivity(intent);

                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                })
                .create();
        dialog.show();
    }

}
