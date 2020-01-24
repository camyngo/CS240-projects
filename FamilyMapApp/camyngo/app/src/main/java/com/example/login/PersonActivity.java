package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.List.PersonActivityListAdapter;
import com.example.login.models.Events;
import com.example.login.models.Model;
import com.example.login.models.Persons;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** PersonActivity
 * Contains all information regarding the Person Activity
 */
public class PersonActivity extends AppCompatActivity {

    private Persons currPerson;

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mGender;
    private Map<Marker, Events> mMarkerMap;
    private GoogleMap mMap;

    private ExpandableListView mListView;
    private ExpandableListAdapter mListAdapter;

    private Model model = Model.initialize();

    //________________________ onCreate and other Activity functions ____________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FamilyMap: Person Details");
        currPerson = model.getSelectedPerson();

        mFirstName = findViewById(R.id.person_first_name);
        mLastName = findViewById(R.id.person_last_name);
        mGender = findViewById(R.id.person_gender);

        mFirstName.setText(currPerson.getPersonFirstName());
        mLastName.setText(currPerson.getPersonLastName());
        mGender.setText(currPerson.getPersonGender().toUpperCase());

        mListView = findViewById(R.id.expandable_list_person_activity);

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                if (groupPosition == 0){
                    // making the person activity calling the event activity class if they have a family tree
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    intent.putExtra("Event", "Event");
                    model.setSelectedEvent((Events) mListAdapter.getChild(groupPosition, childPosition));
                    startActivity(intent);
                }
                else {
                    // otherwise DO NOT call anything
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    model.setSelectedPerson((Persons) mListAdapter.getChild(groupPosition, childPosition));
                    startActivity(intent);
                }
                return false;
            }
        });

        updateUI();
    }

    //--****************-- Initialize the PersonActivity Adapter --***************--
    private void updateUI()
    {
        List<Persons> relatives = new ArrayList<>(model.findRelatives(currPerson.getPersonID()));

        List<Events> eventsArrayList = new ArrayList<>(model.getAllPersonEvents().get(currPerson.getPersonID()));
        eventsArrayList = model.sortEventsByYear(eventsArrayList);

        List<String> headers = new ArrayList<>();
        // calling the set groups for person details
        headers.add("Life Events");
        headers.add("Family Members");

        eventsArrayList = filterEvents(eventsArrayList);
        relatives = filterPersons(relatives);

        mListAdapter = new PersonActivityListAdapter(this, headers, eventsArrayList, relatives, currPerson);
        mListView.setAdapter(mListAdapter);
    }

    //--****************-- Overriding the up Button --***************--
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    //--****************-- Filter Event based on Filters --***************--
    private List<Events> filterEvents(List<Events> eventsList)
    {
        List<Events> testEventList = new ArrayList<>();

        for (Events currEvent: eventsList) {
            if (model.getDisplayedEvents().containsValue(currEvent)){
                // after adding the event .. need to get the event color(not necessary)
                testEventList.add(currEvent);
            }
        }
        return testEventList;
    }

    //--****************-- Filter People based on Filters --***************--
    private List<Persons> filterPersons(List<Persons> personsList)
    {
        List<Persons> filteredPersonsList = new ArrayList<>();

        for (Persons person: personsList) {
            if (model.isPersonDisplayed(person)){
                filteredPersonsList.add(person);
            }
        }
        return filteredPersonsList;
    }
}