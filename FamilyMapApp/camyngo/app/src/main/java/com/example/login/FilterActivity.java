package com.example.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import com.example.login.models.Model;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    private Model model = Model.initialize();

    //--****************-onCreate and other Activity functions --****************-
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updateUI();
    }

    //--****************-- Initializing the Filter Adapter --***************--
    private void updateUI()
    {
        List<String> defaultFilter = new ArrayList<>();
        defaultFilter.add("Father's Side");
        defaultFilter.add("Mother's Side");
        defaultFilter.add("Male Events");
        defaultFilter.add("Female Events");

        List<String> eventTypes = model.getEventTypes();
        defaultFilter.addAll(eventTypes);
    }

    //--****************-- Overriding the up Button and creating the Options Menu --***************--
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

}