package com.example.leehs.yottaproject06.AboutMaps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.leehs.yottaproject06.R;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

/**
 * Created by User on 2016-01-07.
 */

/*
    ShowLocation Activity는
    InfoActivity로부터 전달 받은 제품이 있는 매장의 위치와 현재 사용자의 위치를 전달받아
    Daum Map을 통해 MapView에 위치 표시 및 주변 반경에 Circle을 표시해주는 Activity입니다.
 */
public class ShowLocation extends Activity implements MapView.MapViewEventListener, MapView.POIItemEventListener {

    /////// Circle 여백 주기
    int level;
    int paddingCircle;

    private MapView mMapView;
    private MapPOIItem mUserMarker;
    private MapPOIItem mDestMarker;

    public MapPoint userPoint;
    public MapPoint destPoint;
    double uDistance;
    boolean yRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_location);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        double uLatidude = intent.getDoubleExtra("ULAT", -1);
        double uLongitude = intent.getDoubleExtra("ULNG", -1);
        double dLatidude = intent.getDoubleExtra("DLAT", -1);
        double dLongitude = intent.getDoubleExtra("DLNG", -1);
        uDistance = intent.getDoubleExtra("DISTANCE", -1);


        Log.d("ULAT", "" + uLatidude);
        Log.d("ULNG", "" + uLongitude);
        Log.d("DLAT", "" + dLatidude);
        Log.d("ULNG", "" + dLongitude);
        Log.d("DISTANCE", "" + uDistance);

        ///// setting MapPoint
        userPoint = MapPoint.mapPointWithGeoCoord(uLatidude, uLongitude);
        destPoint = MapPoint.mapPointWithGeoCoord(dLatidude, dLongitude);

        ///// setting MapView
        if (yRun) {
            MapLayout mapLayout = new MapLayout(this);
            mMapView = mapLayout.getMapView();

            mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
            mMapView.setMapViewEventListener(this);
            mMapView.setPOIItemEventListener(this);
            mMapView.setMapType(MapView.MapType.Standard);

            ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
            mapViewContainer.addView(mapLayout);
            createDefaultMarker(mMapView);

            showAll();
        }
    }

    //// User, 목적지 Marker 표시 Setting Method
    private void createDefaultMarker(MapView mapView) {
        Log.d("createDefaultMarker", "시작");

        mUserMarker = new MapPOIItem();
        String uName = "Start";
        mUserMarker.setItemName(uName);
        mUserMarker.setTag(0);
        mUserMarker.setMapPoint(userPoint);
        mUserMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mUserMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mDestMarker = new MapPOIItem();
        String dName = "Destination";
        mDestMarker.setItemName(dName);
        mDestMarker.setTag(0);
        mDestMarker.setMapPoint(destPoint);
        mDestMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        mDestMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mUserMarker);
        mapView.addPOIItem(mDestMarker);
        mapView.selectPOIItem(mUserMarker, true);
        mapView.selectPOIItem(mDestMarker, true);
        mapView.setMapCenterPoint(userPoint, false);
        mapView.setMapCenterPoint(destPoint, false);

        level = 5;
        paddingCircle = 100;


//        if(uDistance >= 0.0 && uDistance <=1000.0){
//            Log.d("MapInitial","1번");
//            level = 5;
//            paddingCircle = 20;
//        }else if(uDistance >1000.0 && uDistance <= 2000.0){
//            Log.d("MapInitial","2번");
//            level = 5;
//            paddingCircle = 50;
//        }else if(uDistance >2000.0 && uDistance <= 4000.0){
//            Log.d("MapInitial","3번");
//            level = 5;
//            paddingCircle = 100;
//        }else{
//            Log.d("MapInitial","4번");
//            level = 4;
//            paddingCircle = 200;
//        }
        Log.d("createDefaultMarker", "종료");

    }

    private void showAll() {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
        MapPointBounds bounds = new MapPointBounds(destPoint, userPoint);
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }

    ////// Circle 추가
    private void drawCircle() {

        MapCircle mCircle = new MapCircle(
                MapPoint.mapPointWithCONGCoord(userPoint.getMapPointCONGCoord().x, userPoint.getMapPointCONGCoord().y),
                (int) uDistance + paddingCircle,
                Color.argb(150, 9, 93, 116),
                Color.argb(110, 9, 93, 116)
        );
        Log.d("Circle 크기 : ", "" + (int) uDistance + paddingCircle);
        mCircle.setTag(0001);
        mMapView.addCircle(mCircle);
    }

    ////// Map 초기화
    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.d("onMapViewInitialized", "시작/종료");

        ///////Map의 Zoom Level 설정
        mapView.setMapCenterPointAndZoomLevel(userPoint, level, true);
        drawCircle();

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewCenterPtMoved", "시작/종료");

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        Log.d("onMapViewZoomLevelCh", "시작/종료");

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewSingleTapped", "시작/종료");

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewDoubleTapped", "시작/종료");

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewLongPressed", "시작/종료");

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewDragStarted", "시작/종료");

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewDragEnded", "시작/종료");

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Log.d("onMapViewMoveFinished", "시작/종료");

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d("onPOIItemSelected", "시작/종료");

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d("onCBOfPOIItemTouched", "시작/종료");

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.d("onCBOfPOIItemTouched", "시작/종료");

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Log.d("onDraggablePOIItemMoved", "시작/종료");

    }

}
