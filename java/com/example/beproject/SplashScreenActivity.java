package com.example.beproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    //Constants
    private static final int NUM_PAGES = 3;

    //XML components
    private ImageView iv_Background , iv_AppLogo;
    private TextView tv_AppName;
    private ViewPager vp_LiquidPager;

    //Custom class objects
    private ScreenSliderPagerAdapter screenSliderPagerAdapter;

    //Android built-in class objects
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Connecting XML components
        connectXmlComponents();

        //Splash screen animation
        splash();

        //Liquid swipe animation
        liquidSwipeAnimation();
    }

    //Connecting XML components
    private void connectXmlComponents(){
        iv_Background = findViewById(R.id.ivBackground);
        iv_AppLogo = findViewById(R.id.ivAppLogo);
        tv_AppName = findViewById(R.id.tvAppName);
        vp_LiquidPager = findViewById(R.id.liquidPager);
    }

    //Splash screen animation
    private void splash(){
        iv_Background.animate().translationY(-2000).setDuration(700).setStartDelay(1700).start();
        iv_AppLogo.animate().translationY(2000).setDuration(700).setStartDelay(2000).start();
        tv_AppName.animate().translationY(2000).setDuration(700).setStartDelay(1700).start();
    }

    //Liquid swipe animation
    private void liquidSwipeAnimation(){
        screenSliderPagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        vp_LiquidPager.setAdapter(screenSliderPagerAdapter);
        animation = AnimationUtils.loadAnimation(this , R.anim.onboarding_animation);
        vp_LiquidPager.startAnimation(animation);
    }

    //Liquid swipe pager adapter class
    private class ScreenSliderPagerAdapter extends FragmentPagerAdapter{
        public ScreenSliderPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;

                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;

                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;

                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
