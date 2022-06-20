package com.example.beproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TravelFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //XML Components
    Spinner spinner_Own_Vehicle , spinner_Vehicle_Prefernce;

    //Android built-in classes
    ViewGroup travelFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        travelFragment = (ViewGroup) inflater.inflate(R.layout.fragment_travel_preference , container , false);

        //Connecting XML components
        connectXmlComponents();

        ArrayAdapter<CharSequence> ownAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_own_vehicle , android.R.layout.simple_spinner_dropdown_item);
        spinner_Own_Vehicle.setAdapter(ownAdapter);
        ArrayAdapter<CharSequence> vehicleAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_vehicle_preference , android.R.layout.simple_spinner_dropdown_item);
        spinner_Vehicle_Prefernce.setAdapter(vehicleAdapter);

        return travelFragment;
    }

    //Connecting XML components
    private void connectXmlComponents(){
        spinner_Own_Vehicle = travelFragment.findViewById(R.id.spinnerOwnVehicle);
        spinner_Own_Vehicle.setOnItemSelectedListener(this);
        spinner_Vehicle_Prefernce = travelFragment.findViewById(R.id.spinnerVehiclePreference);
        spinner_Vehicle_Prefernce.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
