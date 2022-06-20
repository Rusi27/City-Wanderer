package com.example.beproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class OnBoardingFragment1 extends Fragment {

    //Constants
    private float START_ALPHA = 0f , END_ALPHA = 1f , START_TRANSLATION = 150f , END_TRANSLATION = 0f;
    private long DURATION = 700;

    //XML Components
    LottieAnimationView lot_Anim_Bus , lot_Anim_Burger;
    TextView tv_Travel , tv_Restaurants1 , tv_Restaurants2;

    //Android built-in class objects
    ViewGroup onBoardingFragment1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onBoardingFragment1 = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding1 , container , false);

        //Connecting XML components
        connectXmlComponents();

        //Animating components
//        componentAnimation();

        return onBoardingFragment1;
    }

    //Connecting XML components
    private void connectXmlComponents(){
        lot_Anim_Bus = onBoardingFragment1.findViewById(R.id.lotAnimTravel);
        tv_Travel = onBoardingFragment1.findViewById(R.id.tvTravel);
        lot_Anim_Burger = onBoardingFragment1.findViewById(R.id.lotAnimBurger);
        tv_Restaurants1 = onBoardingFragment1.findViewById(R.id.tvRestaurants1);
        tv_Restaurants2 = onBoardingFragment1.findViewById(R.id.tvRestaurants2);
    }

//    private void componentAnimation(){
//        Translation
//        lot_Anim_Bus.setTranslationY(START_TRANSLATION);
//        tv_Travel.setTranslationX(-START_TRANSLATION);
//        lot_Anim_Burger.setTranslationY(START_TRANSLATION);
//        tv_Restaurants1.setTranslationX(-START_TRANSLATION);
//        tv_Restaurants2.setTranslationX(-START_TRANSLATION);
//
//        Opacity
//        tv_Travel.setAlpha(START_ALPHA);
//        tv_Restaurants1.setAlpha(START_ALPHA);
//        tv_Restaurants2.setAlpha(START_ALPHA);
//
//        tv_Travel.animate().translationX(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(2700).start();
//        lot_Anim_Burger.animate().translationY(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(2700).start();
//        tv_Restaurants1.animate().translationX(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(2700).start();
//        tv_Restaurants2.animate().translationX(END_TRANSLATION).alpha(END_ALPHA).setDuration(DURATION).setStartDelay(2750).start();
//    }
}