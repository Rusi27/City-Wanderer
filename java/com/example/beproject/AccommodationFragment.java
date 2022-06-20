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

public class AccommodationFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //XML Components
    Spinner spinner_Accommodation_Choice , spinner_Accommodation_Rating;

    //Android built-in classes
    ViewGroup accommodationFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accommodationFragment = (ViewGroup) inflater.inflate(R.layout.fragment_accommodation_preference , container , false);

        //Connecting XML components
        connectXmlComponents();

        ArrayAdapter<CharSequence> accommodationAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_accommodation_choice , android.R.layout.simple_spinner_dropdown_item);
        spinner_Accommodation_Choice.setAdapter(accommodationAdapter);
        ArrayAdapter<CharSequence> accommodationRatingAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_accommodation_rating , android.R.layout.simple_spinner_dropdown_item);
        spinner_Accommodation_Rating.setAdapter(accommodationRatingAdapter);

        return accommodationFragment;
    }

    //Connecting XML components
    private void connectXmlComponents(){
        spinner_Accommodation_Choice = accommodationFragment.findViewById(R.id.spinnerAccommodationChoice);
        spinner_Accommodation_Choice.setOnItemSelectedListener(this);
        spinner_Accommodation_Rating = accommodationFragment.findViewById(R.id.spinnerAccommodationRating);
        spinner_Accommodation_Rating.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
