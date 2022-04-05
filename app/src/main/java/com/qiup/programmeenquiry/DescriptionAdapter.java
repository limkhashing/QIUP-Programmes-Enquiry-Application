package com.qiup.programmeenquiry;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DescriptionAdapter extends ArrayAdapter<String>
{
    private String qualificationLevel;
    private String[] subjects;
    private Context context;
    private boolean eligibility;
    private int descriptionPosition;
    private List<String> programmesDescriptionList;

    DescriptionAdapter(@NonNull Context context, List<String> programmesDescriptionList,
                              String qualificationLevel, boolean eligibility, String programmeLevel, String[] subjects)
    {
        super(context, R.layout.dialog_programmes_list_description);
        this.context = context;
        this.qualificationLevel = qualificationLevel;
        this.eligibility = eligibility;
        this.programmesDescriptionList = programmesDescriptionList;
        this.subjects = subjects;
        setDescriptionPosition(programmeLevel);
    }

    DescriptionAdapter(@NonNull Context context, List<String> programmesDescriptionList,
                              String qualificationLevel, boolean eligibility, String programmeLevel)
    {
        super(context, R.layout.dialog_programmes_list_description, programmesDescriptionList);
        this.context = context;
        this.qualificationLevel = qualificationLevel;
        this.eligibility = eligibility;
        this.programmesDescriptionList = programmesDescriptionList;
        setDescriptionPosition(programmeLevel);
    }

    public class ViewHolder
    {
        TextView listChild, schorlarshipEligibility;
        ImageView correctImage, crossImage;
        View descriptionBar;
        ViewHolder(View view)
        {
            listChild = view.findViewById(R.id.programmesDescriptionListItem);
            correctImage = view.findViewById(R.id.correctImage);
            crossImage = view.findViewById(R.id.crossImage);
        }
    }

    private void setDescriptionPosition(String programmeLevel)
    {
        if(Objects.equals(programmeLevel, "Diploma in Environmental Technology"))
        {
            if(!Arrays.asList(subjects).contains("Science")) // is Science stream
            {
                descriptionPosition = 0;
            }
            else // not science stream
            {
                descriptionPosition = 1;
            }
        }
        else if(programmeLevel.contains("Foundation"))
        {
            if(Objects.equals(qualificationLevel, "SPM") || Objects.equals(qualificationLevel, "O-Level"))
            {
                descriptionPosition = 0;
            }
            else if(Objects.equals(qualificationLevel, "UEC"))
            {
                descriptionPosition = 1;
            }
        }
        else if(programmeLevel.contains("Diploma"))
        {
            if(Objects.equals(qualificationLevel, "SPM") || Objects.equals(qualificationLevel, "O-Level"))
            {
                descriptionPosition = 0;
            }
            else if(Objects.equals(qualificationLevel, "STPM") || Objects.equals(qualificationLevel, "A-Level"))
            {
                descriptionPosition = 1;
            }
        }
        else // is degree
        {
            if(Objects.equals(qualificationLevel, "STPM"))
            {
                descriptionPosition = 0;
            }
            else if(Objects.equals(qualificationLevel, "A-Level"))
            {
                descriptionPosition = 1;
            }
            if(Objects.equals(qualificationLevel, "UEC"))
            {
                descriptionPosition = 2;
            }
            else if(Objects.equals(qualificationLevel, "STAM"))
            {
                descriptionPosition = 3;
            }
        }
    }


    @Override
    public int getCount() { return programmesDescriptionList.size(); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View customView = convertView;
        ViewHolder viewHolder;

        if(customView == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customView= vi.inflate(R.layout.dialog_programmes_description_row, parent, false);
            viewHolder = new ViewHolder(customView);
            customView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) customView.getTag();
        }

        if(eligibility)
        {
            if(position == descriptionPosition)
            {
                viewHolder.correctImage.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.correctImage.setVisibility(View.GONE);
            }
        }
        else
        {
            if(position == descriptionPosition)
            {
                viewHolder.crossImage.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.crossImage.setVisibility(View.GONE);
            }
        }
        viewHolder.listChild.setText(programmesDescriptionList.get(position));
        return customView;
    }
}
