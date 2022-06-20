package com.example.beproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class QuestionnaireActivity extends AppCompatActivity {

    //Constants
    private static final int NUM_PAGES = 4;

    //Android built-in classes
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        viewPager = findViewById(R.id.viewPager2);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true , new CustomPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else{
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSliderPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new FoodFragment();

                case 1:
                    return new TravelFragment();

                case 2:
                    return new AccommodationFragment();

                case 3:
                    return new InterestsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}