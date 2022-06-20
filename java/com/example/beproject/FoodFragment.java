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

public class FoodFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //XML Components
    Spinner spinner_Cuisine , spinner_Restaurant_Rating;

    //Android built-in classes
    ViewGroup foodFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        foodFragment = (ViewGroup) inflater.inflate(R.layout.fragment_food_preference , container , false);

        //Connecting XML components
        connectXmlComponents();

        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.cuisine_list , android.R.layout.simple_spinner_dropdown_item);
        spinner_Cuisine.setAdapter(cuisineAdapter);
        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_restaurant_rating , android.R.layout.simple_spinner_dropdown_item);
        spinner_Restaurant_Rating.setAdapter(ratingAdapter);

        return foodFragment;
    }

    //Connecting XML components
    private void connectXmlComponents(){
        spinner_Cuisine = foodFragment.findViewById(R.id.spinnerCuisine);
        spinner_Cuisine.setOnItemSelectedListener(this);
        spinner_Restaurant_Rating = foodFragment.findViewById(R.id.spinnerRestaurantRating);
        spinner_Restaurant_Rating.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
