package com.qiup.programmeenquiry;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// CustomAdapter for Material Dialog on Student's Academic Information
public class CustomAdapter extends ArrayAdapter<String>
{
    private Context context;
    private String [] subject ={};
    private String [] grade ={};

    public CustomAdapter(Context context, String[] subject, String[] grade)
    {
        super(context, R.layout.dialog_list_view);
        this.subject = subject;
        this.grade = grade;
        this.context = context;
    }

    public class ViewHolder { TextView subject, grade; }

    @Override
    public int getCount() { return subject.length; } // needed for getView

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.dialog_list_view_item, null);
        }

        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.subject = v.findViewById(R.id.subjectsTextDialog);
        viewHolder.grade = v.findViewById(R.id.gradesTextDialog);
        viewHolder.subject.setText(subject[position]);
        viewHolder.grade.setText(grade[position]);
        return v ;
    }
}