package com.maciekgrzela.aplikacjaklubu.ui.navi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.maciekgrzela.aplikacjaklubu.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HowToGetFragment extends Fragment {

    private HowToGetViewModel howToGetViewModel;
    private WebView howToGetWebView;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double latitude = 0;
    private double longitude = 0;
    private String addressLine;
    private String countryName;
    private String locality;


    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        howToGetViewModel =
                ViewModelProviders.of(this).get(HowToGetViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_how_to_get, container, false);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(root.getContext());

        howToGetWebView = root.findViewById(R.id.how_to_get_view);

        howToGetWebView.setWebViewClient(new WebViewClient());
        howToGetWebView.setFocusable(true);
        howToGetWebView.setFocusableInTouchMode(true);
        howToGetWebView.getSettings().setJavaScriptEnabled(true);
        howToGetWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        howToGetWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        howToGetWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        howToGetWebView.getSettings().setDomStorageEnabled(true);
        howToGetWebView.getSettings().setDatabaseEnabled(true);
        howToGetWebView.getSettings().setAppCacheEnabled(true);
        howToGetWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        howToGetViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String news) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            new FetchLocationAndShowDirectionsTask().execute();
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocationAndShowDirections(final Context context) {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    try {
                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                        countryName = addresses.get(0).getCountryName();
                        locality = addresses.get(0).getLocality();
                        addressLine = addresses.get(0).getAddressLine(0);

                        if(latitude != 0 && longitude != 0){

                            String navigationUrl = getString(R.string.how_to_get_url)+
                                    "?fc="+"51.0788643,17.0635224"+
                                    "&tc="+getString(R.string.stadium_longitude)+":"+getString(R.string.stadium_latitude)+
                                    "&fn="+encodeValue(addressLine)+
                                    "&tn="+encodeValue(getString(R.string.stadium_name))+
                                    "&ia=true&n=0"+
                                    "&tsn="+encodeValue(getString(R.string.stadium_stop_name))+
                                    "&fsn="+encodeValue(addressLine)+
                                    "&ft=LOCATION_TYPE_STOP"+
                                    "&tt=LOCATION_TYPE_STOP&t=1&rc=3&ri=1&r=0";

                            Log.d("URL", navigationUrl);

                            howToGetWebView.loadUrl(navigationUrl);
                        }else {
                            Toast.makeText(getContext(), "UNABLE TO SET WEBVIEW", Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException e) {
                        Toast.makeText(context, "Unable to fetch your location", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private class FetchLocationAndShowDirectionsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getLocationAndShowDirections(getContext());
            return null;
        }
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }


}
