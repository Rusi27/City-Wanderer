package com.example.beproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InterestsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //XML Components
    Spinner spinner_Person_Type , spinner_Activity_Type;
    Button btn_Submit;

    //Android built-in classes
    ViewGroup interestFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        interestFragment = (ViewGroup) inflater.inflate(R.layout.fragment_interests , container , false);

        //Connecting XML components
        connectXmlComponents();

        ArrayAdapter<CharSequence> personAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_person_type , android.R.layout.simple_spinner_dropdown_item);
        spinner_Person_Type.setAdapter(personAdapter);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(getActivity() , R.array.spinner_activity_type , android.R.layout.simple_spinner_dropdown_item);
        spinner_Activity_Type.setAdapter(activityAdapter);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , TripGenerator.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return interestFragment;
    }

    //Connecting XML components
    private void connectXmlComponents(){
        spinner_Person_Type = interestFragment.findViewById(R.id.spinnerPersonType);
        spinner_Person_Type.setOnItemSelectedListener(this);
        spinner_Activity_Type = interestFragment.findViewById(R.id.spinnerActivityType);
        spinner_Activity_Type.setOnItemSelectedListener(this);
        btn_Submit = interestFragment.findViewById(R.id.btSubmit);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
