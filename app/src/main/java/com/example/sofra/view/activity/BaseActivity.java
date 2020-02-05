package com.example.sofra.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sofra.view.fragment.BaSeFragment;

public class BaseActivity extends AppCompatActivity {

    public BaSeFragment baseFragment;
//    ​

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
//​


    public void superBackPressed() {
        super.onBackPressed();
    }
}

