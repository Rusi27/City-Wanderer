package com.example.beproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OnBoardingFragment3 extends Fragment {

    //XML Components
    Button bt_Get_Started;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup onBoardingFragment3 = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding3, container, false);

        bt_Get_Started = onBoardingFragment3.findViewById(R.id.btGetStarted);
        bt_Get_Started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AccountsActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return onBoardingFragment3;
    }
}