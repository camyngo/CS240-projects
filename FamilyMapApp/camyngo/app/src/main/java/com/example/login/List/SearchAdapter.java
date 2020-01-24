package com.example.login.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.login.EventActivity;
import com.example.login.PersonActivity;
import com.example.login.R;
import com.example.login.models.Events;
import com.example.login.models.Model;
import com.example.login.models.Persons;

import java.util.List;

/** SearchAdapter
 * Contains all information about the Search Adapter for the Search Recycler View
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    private List<Object> mObjects;
    private Context context;
    private LayoutInflater inflater;

    // ========================== Constructor ========================================
    public SearchAdapter(List<Object> objects, Context context)
    {
        this.context = context;
        this.mObjects = objects;
        inflater = LayoutInflater.from(context);
    }

    //--****************-- Creates the View Holder --***************--
    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.list_item_event, parent, false);
        return new SearchHolder(view);
    }

    //--****************-- Binds the View Holder to a SearchHolder --***************--
    @Override
    public void onBindViewHolder(SearchHolder holder, int position)
    {
        final Object currObject = mObjects.get(position);
        if (currObject instanceof Persons){
            holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    personsClicked((Persons) currObject);
                }
            });
            holder.bindPerson(currObject);
        }
        else{
            holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    eventClicked((Events) currObject);
                }
            });
            holder.bindEvent(currObject);
        }
    }

    //--****************-- Gets size of items --***************--
    @Override
    public int getItemCount()
    {
        return mObjects.size();
    }

    //--****************-- Switch to Event Activity --***************--
    private void eventClicked(Events event)
    {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra("Event", "Event");
        Model.initialize().setSelectedEvent(event);
        context.startActivity(intent);
    }

    //--****************-- Switch to Person Activity --***************--
    private void personsClicked(Persons person)
    {
        Intent intent = new Intent(context, PersonActivity.class);
        Model.initialize().setSelectedPerson(person);
        context.startActivity(intent);
    }
}