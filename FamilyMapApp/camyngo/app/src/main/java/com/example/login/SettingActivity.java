package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.models.Model;
import com.example.login.models.Settings;
import com.example.login.server.Task.DataTask;
/** SettingActivity
 * Contains all information for the Settings Activity, and Settings Recycler View
 */
public class SettingActivity extends AppCompatActivity implements DataTask.DataContext {

    private Switch mLifeStory;
    private Switch mFamilyTree;
    private Switch mSpouseLines;
    private Switch mFatherSides;
    private Switch mMaleEvents;
    private Switch mMotherSides;
    private Switch mFemaleEvents;

    private TextView mLogout;

    private Settings currSettings;
    private Model model = Model.initialize();

    //------------------------onCreate and other Activity functions ------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLifeStory = findViewById(R.id.life_switch);
        mLifeStory.setChecked(model.getSettings().isStoryLines());

        mFamilyTree = findViewById(R.id.tree_switch);
        mFamilyTree.setChecked(model.getSettings().isFamilyLines());

        mSpouseLines = findViewById(R.id.spouse_switch);
        mSpouseLines.setChecked(model.getSettings().isSpouseLines());

        mFatherSides = findViewById(R.id.father_events_switch);
        mFatherSides.setChecked(model.getFilter().isFathersSide());

        mMotherSides = findViewById(R.id.mother_events_switch);
        mMotherSides.setChecked(model.getFilter().isMothersSide());

        mMaleEvents = findViewById(R.id.male_events_switch);
        mMaleEvents.setChecked(model.getFilter().isMales());

        mFemaleEvents = findViewById(R.id.female_events_switch);
        mFemaleEvents.setChecked(model.getFilter().isFemales());

        mLogout = findViewById(R.id.logout_text);
        mLogout.setLinksClickable(true);


        //------ This Switch Listeners making and saving changes on the button being clicked--------
        mLifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getSettings().setStoryLines(isChecked);
            }
        });

        mFamilyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getSettings().setFamilyLines(isChecked);
            }
        });

        mSpouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getSettings().setSpouseLines(isChecked);
            }
        });

        mFatherSides.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getFilter().setFathersSide(isChecked);
            }
        });

        mMotherSides.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getFilter().setMothersSide(isChecked);
            }
        });

        mMaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getFilter().setMales(isChecked);
            }
        });

        mFemaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                model.getFilter().setFemales(isChecked);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 0);
            }
        });

    }

    //--****************-- Overriding the up Button --***************--
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


    //--****************************-- display the message on the screen--****************************--
    @Override
    public void onExecuteCompleteData(String message)
    {
        if (message.equals("Welcome, " + model.getUsers().getPersonFirstName() + " " + model.getUsers().getPersonLastName())){
            model.setSettings(currSettings);

            Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }
        else {
            Toast.makeText(this, "Connection failed",Toast.LENGTH_SHORT).show();
        }
    }
}