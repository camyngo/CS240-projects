package com.example.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.login.fragments.LoginFragment;
import com.example.login.fragments.MyMapFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener {

    private FragmentManager fm = getSupportFragmentManager();

    //________________________ onCreate and other Activity functions ____________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        if ((getIntent()!= null) && (getIntent().getData() != null)){

            Fragment mapFragment = new MyMapFragment();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.add(R.id.fragment_container, mapFragment).commit();
        }
        else if (fragment == null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            fragment = new LoginFragment();
            ((LoginFragment) fragment).setLoginListener(this);
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    //--****************-- On Login success --***************--
    @Override
    public void loginComplete()
    {
        Fragment mapFragment = new MyMapFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, mapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}