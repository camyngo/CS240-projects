package com.example.login.List;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.models.Events;
import com.example.login.models.Model;
import com.example.login.models.Persons;

/** SearchHolder
 * This contains all layout information used by the SearchAdapter in the Search Activity
 */
public class SearchHolder extends RecyclerView.ViewHolder {

    private View convertView;

    private LinearLayout mLinearLayout;
    private TextView mFirstLine;
    private TextView mDescription;
    private ImageView mIcon;

    // ========================== Constructor ========================================
    public SearchHolder(View itemView)
    {
        super(itemView);
        mFirstLine = itemView.findViewById(R.id.event_list_info);
        mDescription = itemView.findViewById(R.id.event_list_person);
        mIcon = itemView.findViewById(R.id.list_item_icon);
        mLinearLayout = itemView.findViewById(R.id.linear_layout_click_area);
        mLinearLayout.setClickable(true);
        convertView = itemView;
    }

    public LinearLayout getLinearLayout()
    {
        return mLinearLayout;
    }

    //--****************-- Bind the Event Holders --***************--
    public void bindEvent(Object currObject) {

        final Events event = (Events) currObject;
        String eventInfo = event.getEventType() + ", " + event.getEventCity() + ", "
                + event.getEventCountry() + " " + event.getEventYear();
        mFirstLine.setText(eventInfo);

        Model model = Model.initialize();
        Persons currPerson = model.getPeople().get(event.getEventPersonID());
        String personInfo = currPerson.getPersonFirstName() + " " + currPerson.getPersonLastName();
        mDescription.setText(personInfo);
        mIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.pointer_icon));

    }

    //--****************-- Bind the Person Holders --***************--
    public void bindPerson(Object currObject)
    {
        Persons currPerson = (Persons) currObject;
        String personInfo = currPerson.getPersonFirstName() + " " + currPerson.getPersonLastName();
        mFirstLine.setText(personInfo);
        Model model = Model.initialize();
        mDescription.setVisibility(View.INVISIBLE);
        if (currPerson.getPersonGender().toLowerCase().equals("m")) {
            mIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.male_icon));
        } else {
            mIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.female_icon));
        }
    }

}