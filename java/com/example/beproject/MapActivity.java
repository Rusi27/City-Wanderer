package com.example.beproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Constants
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final float DEFAULT_ZOOM = 9f;
    private boolean bLocationPermissionGranted = false;
    private double CURRENT_LAT = 0, CURRENT_LONG = 0;
    private static final String SHARED_PREF = "SharedPrefs";
    private static final String KEY_BUDGET = "Budget";

    //Custom variables
    private String strSharedBudget = "";

    //Android built-in class objects;
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mAuth = FirebaseAuth.getInstance();
        String strUserID = mAuth.getUid();
        //Toast.makeText(MapActivity.this , "Firebase User ID : " + strUserID , Toast.LENGTH_LONG).show();

        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        strSharedBudget = sharedPreferences.getString(KEY_BUDGET, null);
        Toast.makeText(getApplicationContext(), strSharedBudget, Toast.LENGTH_LONG).show();

        //Invoking dialog boxes for location permissions
        getLocationPermission();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    //Initialize map
    private void initMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(MapActivity.this);
        getDeviceLocation();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if(location != null){
//                    CURRENT_LAT = location.getLatitude();
//                    CURRENT_LONG = location.getLongitude();
//                }
//            }
//        });
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (bLocationPermissionGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            CURRENT_LAT = currentLocation.getLatitude();
                            CURRENT_LONG = currentLocation.getLongitude();
                            //moveCamera(new LatLng(currentLocation.getLatitude() , currentLocation.getLongitude()) , DEFAULT_ZOOM , "My Location");
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CURRENT_LAT, CURRENT_LONG), DEFAULT_ZOOM));
                        } else {
                            Toast.makeText(MapActivity.this, "Unable to get current location", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Toast.makeText(MapActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Checking permissions when map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (bLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CURRENT_LAT , CURRENT_LONG) , 10));
        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        init();
    }

    private void init() {
        geoLocate();
    }

    //Invoking dialog boxes for location permissions
    private void getLocationPermission() {
        String[] strPermissions = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                bLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, strPermissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, strPermissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    //Checking whether all required permissions are granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        bLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            bLocationPermissionGranted = false;
                            return;
                        }
                    }
                    bLocationPermissionGranted = true;
                    initMap();
                }
        }
    }

    //    private class PlaceTask extends AsyncTask<String , Integer , String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            String strData = null;
//            try {
//                strData = downloadUrl(strings[0]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return strData;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            new ParserTask().execute(s);
//        }
//    }
//
//    private String downloadUrl(String string) throws IOException {
//        URL url = new URL(string);
//        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//        connection.connect();
//        InputStream stream = connection.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        StringBuilder builder = new StringBuilder();
//        String strLine = "";
//        while((strLine = reader.readLine()) != null){
//            builder.append(strLine);
//        }
//        String strData = builder.toString();
//        reader.close();
////        Toast.makeText(MapActivity.this , strData , Toast.LENGTH_LONG).show();
//        return strData;
//    }
//
//    private class ParserTask extends AsyncTask<String , Integer , List<HashMap<String , String>>>{
//
//        @Override
//        protected List<HashMap<String, String>> doInBackground(String... strings) {
//            JsonParser jsonParser = new JsonParser();
//            List<HashMap<String , String>> mapList = null;
//            JSONObject object = null;
//            try {
//                object = new JSONObject(strings[0]);
//                mapList = jsonParser.parseResult(object);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return mapList;
//        }
//
//        @Override
//        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
//            mMap.clear();
//            for(int i = 0 ; i < hashMaps.size() ; i++){
//                HashMap<String , String> hashMapList = hashMaps.get(i);
//                double lat = Double.parseDouble(hashMapList.get("lat"));
//                double lng = Double.parseDouble(hashMapList.get("lng"));
//                String strName = hashMapList.get("name");
//                LatLng latLng = new LatLng(lat , lng);
//                MarkerOptions options = new MarkerOptions();
//                options.position(latLng);
//                options.title(strName);
//                mMap.addMarker(options);
//            }
//        }
//    }
    private void geoLocate() {
        String[] str24 = {"Army Institute of Technology" , "Sarasbag", "Shaniwar Wada",
                "OSHO International Meditation Resort", "National War Memorial Pune",
                "Hotel Ganaraj Bajirao Road", "Shreemant Dagdusheth Ganpati Mandir", "Laxmi Road", "Hotel Darshan Deccan"};

        String[] str46 = {"Army Institute of Technology", "Vetal Hill", "Katakir Misal Dr Ketkar Lane", "Kamala Nehru Park Pune", "J M Road",
                "The Ritz-Carlton, Pune", "JW Marriott Hotel Pune", "Sheraton Grand Pune Bund Garden Hotel",
                "Sukanta Thali Pune", "Pavilion Pune", "German Bakery Law College Road"};

        String[] str68 = {"Army Institute of Technology", "Shakahari", "Paasha", "Coriander Kitchen", "Crowne Plaza Pune City Centre, an IHG Hotel",
                // "Taljai Jogging Park" , "O Hotel Pune" , "Relax Pure Veg Sarang Society" ,
                "Sweet Home Pune", "Chaturshringi Mandir", "Kasba Ganapati Mandir", "Pune Central Karve Road", "Hotel Abhishek Pune"};

        String[] str810 = {"Army Institute of Technology", "Hotel Darshan Deccan", "Baan Tao", "The Pride Hotel Pune", "Conrad Pune", "Marriott Suites Pune",
                "Hotel Blue Diamond Koregaon Park Road Pune", "Joshi Railway Museum", "Lal Mahal", "Amanora Mall Pune", "M G Road Pune", "East Street Pune"};

        String[] str5 = {"Army Institute of Technology", "Whispering Bamboo Restaurant", "Baan Tao", "The Lotus Deck",
                "Taj Hotel", "NK Restaurant", "Spice Kitchen - J.W. Marriott Hotel", "Alto Vino",
                "Ramee Grand Hotel and Spa, Pune", "Aquarius Resort & Lawns", "Empress Garden", "Hotel RamKrishna Camp Pune", "Race Course Pune"};

        Address address = null;

        if (strSharedBudget.equals("2000-5000")) {
            for (int i = 0; i < str24.length; i++) {
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(str24[i], 1);
                } catch (IOException e) {
                }
                if (list.size() > 0) {
                    address = list.get(0);
                    addCustomMarker(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, str24[i]);
                }
                //moveCamera(new LatLng(list.get(0).getLatitude() , list.get(0).getLongitude()) , DEFAULT_ZOOM , str24[i]);
            }
        } else if (strSharedBudget.equals("6000-10000")) {
            for (int i = 0; i < str46.length; i++) {
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(str46[i], 1);
                } catch (IOException e) {
                }
                if (list.size() > 0) {
                    address = list.get(0);
                    addCustomMarker(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, str46[i]);
                }
                //moveCamera(new LatLng(list.get(0).getLatitude() , list.get(0).getLongitude()) , DEFAULT_ZOOM , str24[i]);
            }
        } else if (strSharedBudget.equals("11000-15000")) {
            for (int i = 0; i < str68.length; i++) {
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(str68[i], 1);
                } catch (IOException e) {
                }
                if (list.size() > 0) {
                    address = list.get(0);
                    addCustomMarker(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, str68[i]);
                }
                //moveCamera(new LatLng(list.get(0).getLatitude() , list.get(0).getLongitude()) , DEFAULT_ZOOM , str24[i]);
            }
        } else if (strSharedBudget.equals("16000-20000")) {
            for (int i = 0; i < str810.length; i++) {
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(str810[i], 1);
                } catch (IOException e) {
                }
                if (list.size() > 0) {
                    address = list.get(0);
                    addCustomMarker(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, str810[i]);
                }
                //moveCamera(new LatLng(list.get(0).getLatitude() , list.get(0).getLongitude()) , DEFAULT_ZOOM , str24[i]);
            }
        } else if (strSharedBudget.equals("21000-30000")) {
            for (int i = 0; i < str5.length; i++) {
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = new ArrayList<>();
                try {
                    list = geocoder.getFromLocationName(str5[i], 1);
                } catch (IOException e) {
                }
                if (list.size() > 0) {
                    address = list.get(0);
                    addCustomMarker(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, str5[i]);
                }
                //moveCamera(new LatLng(list.get(0).getLatitude() , list.get(0).getLongitude()) , DEFAULT_ZOOM , str24[i]);
            }
        }
    }

//    private void moveCamera(LatLng latLng, float fZoom, String strTitle){
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , fZoom));
//    }

    private void addCustomMarker(LatLng latLng, float fZoom, String strTitle) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(strTitle);
        if (strTitle.equals("Army Institute of Technology")) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
        } else
            mMap.addMarker(markerOptions);
        if (strTitle.equals("OSHO International Meditation Resort") || strTitle.equals("The Ritz-Carlton, Pune") || strTitle.equals("JW Marriott Hotel Pune") || strTitle.equals("Sheraton Grand Pune Bund Garden Hotel") || strTitle.equals("Hotel Abhishek Pune") || strTitle.equals("Hotel Blue Diamond Koregaon Park Road Pune") || strTitle.equals("The Pride Hotel Pune") || strTitle.equals("Marriott Suites Pune") || strTitle.equals("Conrad Pune") || strTitle.equals("Taj Hotel") || strTitle.equals("Ramee Grand Hotel and Spa, Pune") || strTitle.equals("Aquarius Resort & Lawns") || strTitle.equals("Hotel RamKrishna Camp Pune") || strTitle.equals("Sweet Home Pune")) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
        }
        if (strTitle.equals("Hotel Darshan Deccan") || strTitle.equals("Hotel Ganaraj Bajirao Road") || strTitle.equals("Sukanta Thali Pune")  || strTitle.equals("German Bakery Law College Road")  || strTitle.equals("Shakahari")  || strTitle.equals("Paasha")  || strTitle.equals("Coriander Kitchen")  || strTitle.equals("Whispering Bamboo Restaurant") || strTitle.equals("Baan Tao") || strTitle.equals("Spice Kitchen - J.W. Marriott Hotel")) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            mMap.addMarker(markerOptions);
        }
   /* private void addCustomMarker(LatLng latLng, float fZoom, String strTitle) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(strTitle);
        if(strTitle.equals("OSHO International Meditation Resort") || strTitle.equals("The Ritz-Carlton, Pune") || strTitle.equals("JW Marriott Hotel Pune") || strTitle.equals("Sheraton Grand Pune Bund Garden Hotel") || strTitle.equals("Hotel Abhishek Pune") || strTitle.equals("Hotel Blue Diamond Koregaon Park Road Pune") || strTitle.equals("The Pride Hotel Pune") || strTitle.equals("Marriott Suites Pune") || strTitle.equals("Conrad Pune") || strTitle.equals("Taj Hotel") || strTitle.equals("Ramee Grand Hotel and Spa, Pune") || strTitle.equals("Aquarius Resort & Lawns") || strTitle.equals("Hotel RamKrishna Camp Pune")|| strTitle.equals("Sweet Home Pune")) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
        }
        else
            mMap.addMarker(markerOptions);
    }*/
    }
}