package com.example.beproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OnBoardingFragment2 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup onBoardingFragment2 = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding2, container, false);
        return onBoardingFragment2;
    }
}