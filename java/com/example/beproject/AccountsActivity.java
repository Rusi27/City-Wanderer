package com.example.beproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class AccountsActivity extends AppCompatActivity {

    //Flag
    boolean boolOnBoard = false;

    //XML components
    TabLayout tab_Layout;
    ViewPager view_Pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        boolOnBoard = true;

        //Connecting XML components
        connectXmlComponents();

        //Setting up viewpager and tab layout
        setUpPagerAndTabLayout();
    }

    //Connecting XML components
    private void connectXmlComponents(){
        tab_Layout = findViewById(R.id.tabLayout);
        view_Pager = findViewById(R.id.viewPager);
    }

    //Setting up viewpager and tab layout
    private void setUpPagerAndTabLayout(){
        final CustomTabAdapter customTabAdapter = new CustomTabAdapter(getSupportFragmentManager());
        customTabAdapter.addFragment(new LoginFragment() , "Login");
        customTabAdapter.addFragment(new SignUpFragment() , "Sign Up");
        view_Pager.setAdapter(customTabAdapter);
        tab_Layout.setupWithViewPager(view_Pager);
    }
}