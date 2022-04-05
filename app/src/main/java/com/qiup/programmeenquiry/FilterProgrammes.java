package com.qiup.programmeenquiry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FilterProgrammes extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MaterialSpinner qualificationSpinner, gradesSpinner, gradesSpinner1,
            spmOLevelSpinner, mathematicsSpinner, englishSpinner,
            addMathSpinner, bahasaMalaysiaSpinner, scienceTechnicalVocationalGradeSpinner;
    InstantAutoComplete subjectsAutoCompleteTextView, newSubjectsAutoCompleteTextView,
            scienceTechnicalVocationalAutoCompleteTextView;
    ArrayAdapter<String> subjectsAdapter, gradesAdapter;
    Button filterButton, addNewField, deleteFieldText;
    LinearLayout parentLinearLayout;
    TextView subjectsText, gradesText, englishText, mathematicsText, spmOlevelText, addMathText, bahasaMalaysiaText;
    Bundle extras;
    boolean flagForNewField;
    String [] subjectsList, scienceTechnicalVocationalSubjectArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_programme);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extras = getIntent().getExtras(); // get the data from previous activity

        initializeLayoutItem(); //find view by ID...

        // Setting the results type Adapter
        String[] stringsResultsType = getResources().getStringArray(R.array.qualification_type);
        ArrayAdapter<String> resultsArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, stringsResultsType) {
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v;
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    v = super.getDropDownView(position, null, parent);
                }
                return v;
            }
        };
        resultsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // setting the results type Adapter
        final String[] stringsSPMOLevelType = getResources().getStringArray(R.array.spm_or_oLevel_type);
        ArrayAdapter<String> SPMOLevelArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, stringsSPMOLevelType) {
            // If is position 0(the initial dummy entry), make it hidden
            // else, pass convertView as null to prevent reuse of special case views
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View v;
                if (position == 0) {
                    CheckedTextView tv = new CheckedTextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    v = super.getDropDownView(position, null, parent);
                }
                return v;
            }
        };
        SPMOLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        addNewField.setEnabled(false);
        deleteFieldText.setEnabled(false);
        subjectsText.setTextColor(Color.GRAY);
        gradesText.setTextColor(Color.GRAY);
        flagForNewField = false;
        subjectsAutoCompleteTextView.setEnabled(false);
        gradesSpinner.setEnabled(false);
        filterButton.setEnabled(false);
        englishText.setVisibility(View.GONE);
        mathematicsText.setVisibility(View.GONE);
        spmOlevelText.setVisibility(View.GONE);
        spmOLevelSpinner.setVisibility(View.GONE);
        mathematicsSpinner.setVisibility(View.GONE);
        englishSpinner.setVisibility(View.GONE);
        addMathText.setVisibility(View.GONE);
        addMathSpinner.setVisibility(View.GONE);
        bahasaMalaysiaText.setVisibility(View.GONE);
        bahasaMalaysiaSpinner.setVisibility(View.GONE);
        scienceTechnicalVocationalAutoCompleteTextView.setVisibility(View.GONE);
        scienceTechnicalVocationalGradeSpinner.setVisibility(View.GONE);

        spmOLevelSpinner.setAdapter(SPMOLevelArrayAdapter);
        spmOLevelSpinner.setOnItemSelectedListener(this);
        qualificationSpinner.setAdapter(resultsArrayAdapter);
        qualificationSpinner.setOnItemSelectedListener(this);
        setSpinnerScrollbar(); //set spinner scrollbar for resultsSpinner, subjectsSpinner and gradesSpinner

        scienceTechnicalVocationalAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(Objects.equals(scienceTechnicalVocationalAutoCompleteTextView.getText().toString().trim(), "")) {
                    scienceTechnicalVocationalGradeSpinner.setSelection(0);
                    scienceTechnicalVocationalGradeSpinner.setEnabled(false);
                }
                else {
                    scienceTechnicalVocationalGradeSpinner.setEnabled(true);
                }
            }
        });

        //add new dropdown field
        addNewField.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.new_dropdown_field, null);
                // Add the new row before the add field button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 9);

                if(parentLinearLayout.getChildCount() != 12) // if added new field, make the delete field text enable
                    deleteFieldText.setEnabled(true);

                newSubjectsAutoCompleteTextView = rowView.findViewById(R.id.newSubjectsAutoCompleteTextView);
                gradesSpinner1 = rowView.findViewById(R.id.gradesSpinner1);
                String selectedItem = qualificationSpinner.getSelectedItem().toString();
                if(Objects.equals(selectedItem, "SPM"))
                {
                    if(parentLinearLayout.getChildCount() == 23) // max 12
                        addNewField.setEnabled(false);
                }
                else if(Objects.equals(selectedItem, "O-Level")) // max 10
                {
                    if(parentLinearLayout.getChildCount() == 21)
                        addNewField.setEnabled(false);
                }
                else if(Objects.equals(selectedItem, "A-Level")) // max 5
                {
                    if(parentLinearLayout.getChildCount() == 16)
                        addNewField.setEnabled(false);
                }
                else if(Objects.equals(selectedItem, "STPM")) // max 5
                {
                    if(parentLinearLayout.getChildCount() == 16)
                        addNewField.setEnabled(false);
                }
                else if(Objects.equals(selectedItem, "STAM")) // max 11
                {
                    if(parentLinearLayout.getChildCount() == 22)
                        addNewField.setEnabled(false);
                }
                else if(Objects.equals(selectedItem, "UEC"))
                {
                    if(parentLinearLayout.getChildCount() == 21)
                        addNewField.setEnabled(false);
                }

                try {
                    loadSpinnerData(newSubjectsAutoCompleteTextView, gradesSpinner1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        deleteFieldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parentLinearLayout.getChildCount() != 12) { // if added new field
                    parentLinearLayout.removeViewAt(parentLinearLayout.getChildCount() - 10);
                    addNewField.setEnabled(true);
                }
                if(parentLinearLayout.getChildCount() == 12) { // if deleted and back to original layout
                    deleteFieldText.setEnabled(false);
                    flagForNewField = false;
                }
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> addedSubjectsList = new ArrayList<>();
                ArrayList<TextView> addedGradesList = new ArrayList<>();
                String[] supportiveStringGrade = new String[5];
                String spmOLevelString="", mathematicsGrade="", englishGrade="",
                        addMathGrade="", bahasaMalaysiaGrade="", scienceTechnicalVocationalGrade = "";
                boolean isSupportive = false;

                // Validate Subjects First
                // first convert all the subjects into UpperCase for case-insensitive validation
                for(int i = 0; i  < subjectsList.length; i++)
                    subjectsList[i] = subjectsList[i].toUpperCase();

                // If the key-in subject is not exist in subjects list according to qualification, return
                if(!Arrays.asList(subjectsList).contains(subjectsAutoCompleteTextView.getText().toString().toUpperCase())
                        && !Objects.equals(subjectsAutoCompleteTextView.getText().toString(), ""))
                {
                    new MaterialDialog.Builder(FilterProgrammes.this)
                            .title("Invalid subject name")
                            .content("Please key-in valid subjects name")
                            .positiveText("Return")
                            .build()
                            .show();
                    return;
                }

                // Get all the new added view value and add to a List
                for (int i = 0; i < parentLinearLayout.getChildCount(); i++)
                {
                    view = parentLinearLayout.getChildAt(i);
                    if(view instanceof RelativeLayout)
                    {
                        if(view.getId() == R.id.newField)
                        {
                            newSubjectsAutoCompleteTextView = (InstantAutoComplete) ((RelativeLayout) view).getChildAt(0);
                            // If the key-in subject is not exist in subjects list according to qualification, return
                            if(!Arrays.asList(subjectsList).contains(newSubjectsAutoCompleteTextView.getText().toString().toUpperCase())
                                    && !Objects.equals(newSubjectsAutoCompleteTextView.getText().toString(), ""))
                            {
                                new MaterialDialog.Builder(FilterProgrammes.this)
                                        .title("Invalid subject name")
                                        .content("Please key-in valid subjects name")
                                        .positiveText("Return")
                                        .build()
                                        .show();
                                return;
                            }
                            gradesSpinner1 = (MaterialSpinner) ((RelativeLayout) view).getChildAt(1);
                            addedSubjectsList.add(newSubjectsAutoCompleteTextView.getText().toString());
                            addedGradesList.add((TextView)gradesSpinner1.getSelectedView());
                        }
                    }
                }

                // copy all subjects list to arrays
                String[] arrayStringSubjects = new String[addedSubjectsList.size() + 1];
                String selectedItem = subjectsAutoCompleteTextView.getText().toString();
                arrayStringSubjects[0] = selectedItem;
                for(int i = 1; i < arrayStringSubjects.length; i++)
                    arrayStringSubjects[i] = addedSubjectsList.get(i-1);

                // copy all grades list to arrays
                String[] arrayStringGrades = new String[addedGradesList.size() + 1];
                TextView selection = (TextView)gradesSpinner.getSelectedView();
                selectedItem = selection.getText().toString();
                arrayStringGrades[0] = selectedItem;
                for(int i = 1; i <  arrayStringGrades.length; i++)
                    arrayStringGrades[i] = addedGradesList.get(i-1).getText().toString();

                // selectedItem Lastly is assigned as Qualification Level
                selectedItem = qualificationSpinner.getSelectedItem().toString();

                //check got compulsory subjects or not
                if(Objects.equals(selectedItem, "SPM"))
                {
                    if(!Arrays.asList(arrayStringSubjects).contains("Bahasa Malaysia")
                            || !Arrays.asList(arrayStringSubjects).contains("Bahasa Inggeris")
                            || !Arrays.asList(arrayStringSubjects).contains("Sejarah")
                            || !Arrays.asList(arrayStringSubjects).contains("Mathematics")
                            || (!Arrays.asList(arrayStringSubjects).contains("Pendidikan Moral")
                            && !Arrays.asList(arrayStringSubjects).contains("Pendidikan Islam")))

                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .title("Compulsory Subject Needed")
                                .content("Subjects must include Bahasa Malaysia, Bahasa Inggeris, Sejarah, Mathematics, Pendidikan Moral or Pendidikan islam")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                    for(int i = 0; i < arrayStringSubjects.length; i++)
                    {
                        if(Objects.equals(arrayStringSubjects[i], "Bahasa Malaysia")
                                || Objects.equals(arrayStringSubjects[i], "Bahasa Inggeris")
                                || Objects.equals(arrayStringSubjects[i], "Sejarah"))
                        {
                            if(Objects.equals(arrayStringGrades[i], "G"))
                            {
                                new MaterialDialog.Builder(FilterProgrammes.this)
                                        .title("SPM Failed")
                                        .content("Sorry. We are unable to match any programmes for you.\nFor more information, please contact +605 249 0500")
                                        .positiveText("Return")
                                        .build()
                                        .show();
                                return;
                            }
                        }
                    }
                }
                else if(Objects.equals(selectedItem, "STPM"))
                {
                    if(!Arrays.asList(arrayStringSubjects).contains("Pengajian Am"))
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .title("Compulsory Subject Needed")
                                .content("Subjects must include Pengajiam Am")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                    for(int i = 0; i < arrayStringSubjects.length; i++)
                    {
                        if(Objects.equals(arrayStringSubjects[i], "Pengajiam Am"))
                        {
                            if(Objects.equals(arrayStringGrades[i], "F"))
                            {
                                new MaterialDialog.Builder(FilterProgrammes.this)
                                        .title("STPM Failed")
                                        .content("Sorry. We are unable to match any programmes for you.\nFor more information, please contact +605 249 0500")
                                        .positiveText("Return")
                                        .build()
                                        .show();
                                return;
                            }
                        }
                    }
                }
                else if(Objects.equals(selectedItem, "UEC"))
                {
                    if(!Arrays.asList(arrayStringSubjects).contains("Bahasa Malaysia")
                            || !Arrays.asList(arrayStringSubjects).contains("Chinese")
                            || !Arrays.asList(arrayStringSubjects).contains("English Language"))
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .title("Compulsory Subject Needed")
                                .content("Subjects must include Bahasa Malaysia, English Language and Chinese")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }

                // If there are subjects is not key-in / selected
                for(int i = 0; i < arrayStringSubjects.length; i++)
                {
                    if(arrayStringSubjects[i].isEmpty())
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .title("Subjects Not Selected")
                                .content("Please make sure all subject is selected")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }

                // If there are duplicated subjects, return
                for(int i = 0; i < arrayStringSubjects.length; i++)
                {
                    for(int j = i + 1; j < arrayStringSubjects.length; j++)
                    {
                        if(Objects.equals(arrayStringSubjects[i], arrayStringSubjects[j]))
                        {
                            new MaterialDialog.Builder(FilterProgrammes.this)
                                    .title("Duplicated Subject")
                                    .content("Please make sure there is no subject duplicated")
                                    .positiveText("Return")
                                    .build()
                                    .show();
                            return;
                        }
                    }
                }

                // if there are Grades is not selected
                for(int i = 0; i < arrayStringGrades.length; i++)
                {
                    if(arrayStringGrades[i].isEmpty())
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .title("Grades Not Selected")
                                .content("Please make sure all grade are selected")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }

                // After validation, change the string value to proper subject and match with Rules
                // English Language is for UEC, O-Level, SPM, English - First Language is for O-Level
                // Advanced Mathematics is for UEC
                for(int i = 0; i < arrayStringSubjects.length; i++)
                {
                    if(Objects.equals(arrayStringSubjects[i], "Bahasa Inggeris")
                            || Objects.equals(arrayStringSubjects[i], "English Language")
                            || Objects.equals(arrayStringSubjects[i], "English - First Language")
                            || Objects.equals(arrayStringSubjects[i], "English - Literature")
                            || Objects.equals(arrayStringSubjects[i], "Kesusasteraan Inggeris")
                            || Objects.equals(arrayStringSubjects[i], "Literature in English"))
                    {
                        arrayStringSubjects[i] = "English";
                    }
                    if(Objects.equals(arrayStringSubjects[i], "Mathematics - Additional")
                            || Objects.equals(arrayStringSubjects[i], "Advanced Mathematics")
                            || Objects.equals(arrayStringSubjects[i], "Advanced Mathematics I")
                            || Objects.equals(arrayStringSubjects[i], "Advanced Mathematics II")
                            || Objects.equals(arrayStringSubjects[i], "Matematik (T)")
                            || Objects.equals(arrayStringSubjects[i], "Further Mathematics"))
                    {
                        arrayStringSubjects[i] = "Additional Mathematics";
                    }
                    if(Objects.equals(arrayStringSubjects[i], "Mathematics D")
                            || Objects.equals(arrayStringSubjects[i], "International Mathematics")
                            || Objects.equals(arrayStringSubjects[i], "Matematik (M)"))
                    {
                        arrayStringSubjects[i] = "Mathematics";
                    }
                    if(Objects.equals(arrayStringSubjects[i], "Science - Combined")
                            || Objects.equals(arrayStringSubjects[i], "Sciences - Co-ordinated (Double)")
                            || Objects.equals(arrayStringSubjects[i], "Additional Science"))
                    {
                        arrayStringSubjects[i] = "Science";
                    }
                    if(Objects.equals(arrayStringSubjects[i], "Fizik"))
                    {
                        arrayStringSubjects[i] = "Physics";
                    }
                    if(Objects.equals(arrayStringSubjects[i], "Kimia"))
                    {
                        arrayStringSubjects[i] = "Chemistry";
                    }
                }

                // Spm or o-level
                if(spmOLevelSpinner.getSelectedView() != null) {
                    isSupportive = true;
                    TextView spmOLevelText = (TextView)spmOLevelSpinner.getSelectedView();
                    spmOLevelString = spmOLevelText.getText().toString();
                }

                if(isSupportive) {
                    // Bahasa malaysia
                    if(bahasaMalaysiaSpinner.getSelectedView() != null) {
                        TextView bahasaMalaysiaText = (TextView)bahasaMalaysiaSpinner.getSelectedView();
                        bahasaMalaysiaGrade = bahasaMalaysiaText.getText().toString();
                    }

                    // English
                    if(englishSpinner.getSelectedView() != null) {
                        TextView englishText = (TextView)englishSpinner.getSelectedView();
                        englishGrade = englishText.getText().toString();
                    }

                    // Maths
                    if(mathematicsSpinner.getSelectedView() != null) {
                        TextView mathematicsText = (TextView)mathematicsSpinner.getSelectedView();
                        mathematicsGrade = mathematicsText.getText().toString();
                    }

                    // Add maths
                    if(addMathSpinner.getSelectedView() != null) {
                        TextView addMathText = (TextView)addMathSpinner.getSelectedView();
                        addMathGrade = addMathText.getText().toString();
                    }

                    // Science technical vocational grade
                    if(scienceTechnicalVocationalGradeSpinner.getSelectedView() != null) {
                        TextView scienceTechnicalVocationalGradeSelectedView = (TextView)scienceTechnicalVocationalGradeSpinner.getSelectedView();
                        scienceTechnicalVocationalGrade = scienceTechnicalVocationalGradeSelectedView.getText().toString();
                    }

                    // Check second qualification grades error if the qualification is STPM, STAM or A-Level
                    if(checkSecondQualificationError(spmOLevelString, mathematicsGrade, englishGrade, addMathGrade, bahasaMalaysiaGrade, scienceTechnicalVocationalGrade, selectedItem))
                        return; // if error is true, dont do anything

                    supportiveStringGrade[0] = bahasaMalaysiaGrade;
                    supportiveStringGrade[1] = englishGrade;
                    supportiveStringGrade[2] = mathematicsGrade;
                    supportiveStringGrade[3] = addMathGrade;
                    supportiveStringGrade[4] = scienceTechnicalVocationalGrade;
                }

                // Validate minimum input subjects
                if(Objects.equals(selectedItem, "SPM")) {
                    if(parentLinearLayout.getChildCount() < 18) // min 7
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .content("SPM need minimum 7 subjects")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }
                else if(Objects.equals(selectedItem, "O-Level")) { // min 7
                    if(parentLinearLayout.getChildCount() < 18)
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .content("O-Level need minimum 7 subjects")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }
                else if(Objects.equals(selectedItem, "A-Level")) { // min 4
                    if(parentLinearLayout.getChildCount() < 15)
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .content("A-Level need minimum 4 subjects")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }
                else if(Objects.equals(selectedItem, "STPM")) { // min 4
                    if(parentLinearLayout.getChildCount() < 15)
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .content("STPM need minimum 4 subjects")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }
                else if(Objects.equals(selectedItem, "UEC")) {  // min 8
                    if(parentLinearLayout.getChildCount() < 19)
                    {
                        new MaterialDialog.Builder(FilterProgrammes.this)
                                .content("UEC need minimum 8 subjects")
                                .positiveText("Return")
                                .build()
                                .show();
                        return;
                    }
                }

                // Change grade to number
                int[] arrayIntegerGrades = new int[arrayStringGrades.length], arraySupportiveGrade = new int[5];
                convertToIntegerGrade(selectedItem, arrayIntegerGrades, arrayStringGrades);
                convertSupportiveGradeToInteger(spmOLevelString, arraySupportiveGrade, supportiveStringGrade);

                // Put together with previous activity's data and send to next activity
                extras.putString("QUALIFICATION_LEVEL", selectedItem);
                extras.putStringArray("STUDENT_SUBJECTS_LIST", arrayStringSubjects);
                extras.putStringArray("STUDENT_STRING_GRADES_LIST", arrayStringGrades);
                extras.putIntArray("STUDENT_GRADES_LIST", arrayIntegerGrades);

                extras.putString("STUDENT_SECONDARY_QUALIFICATION", spmOLevelString);
                extras.putString("STUDENT_STV_SUBJECT", scienceTechnicalVocationalAutoCompleteTextView.getText().toString().trim());
                extras.putString("STUDENT_SECONDARY_BM", supportiveStringGrade[0]);
                extras.putString("STUDENT_SECONDARY_ENG", supportiveStringGrade[1]);
                extras.putString("STUDENT_SECONDARY_MATH", supportiveStringGrade[2]);
                extras.putString("STUDENT_SECONDARY_ADDMATH", supportiveStringGrade[3]);
                extras.putString("STUDENT_STV_GRADE", supportiveStringGrade[4]);

                // Upon submit, reset back to default
                qualificationSpinner.setSelection(0);
                while ( parentLinearLayout.getChildCount() != 12) {
                    parentLinearLayout.removeViewAt(parentLinearLayout.getChildCount() - 10);
                }
                subjectsAutoCompleteTextView.setEnabled(false);
                subjectsAutoCompleteTextView.setText("");
                subjectsAutoCompleteTextView.dismissDropDown();
                gradesSpinner.setSelection(0);
                gradesText.setTextColor(Color.GRAY);
                gradesSpinner.setEnabled(false);
                addNewField.setEnabled(false);
                deleteFieldText.setEnabled(false);
                subjectsText.setTextColor(Color.GRAY);

                // Secondary qualification reset
                scienceTechnicalVocationalAutoCompleteTextView.setText("");
                spmOLevelSpinner.setSelection(0);
                bahasaMalaysiaSpinner.setSelection(0);
                englishSpinner.setSelection(0);
                mathematicsSpinner.setSelection(0);
                addMathSpinner.setSelection(0);
                addMathText.setVisibility(View.GONE);
                addMathSpinner.setVisibility(View.GONE);
                englishText.setVisibility(View.GONE);
                englishSpinner.setVisibility(View.GONE);
                mathematicsText.setVisibility(View.GONE);
                mathematicsSpinner.setVisibility(View.GONE);
                spmOlevelText.setVisibility(View.GONE);
                spmOLevelSpinner.setVisibility(View.GONE);
                bahasaMalaysiaText.setVisibility(View.GONE);
                bahasaMalaysiaSpinner.setVisibility(View.GONE);
                scienceTechnicalVocationalAutoCompleteTextView.setVisibility(View.GONE);
                scienceTechnicalVocationalGradeSpinner.setVisibility(View.GONE);
                bahasaMalaysiaSpinner.setEnabled(false);
                englishSpinner.setEnabled(false);
                mathematicsSpinner.setEnabled(false);
                addMathSpinner.setEnabled(false);
                scienceTechnicalVocationalAutoCompleteTextView.setEnabled(false);
                scienceTechnicalVocationalGradeSpinner.setEnabled(false);
                filterButton.setEnabled(false);

                Intent nextActivity = new Intent(FilterProgrammes.this, InterestProgramme.class);
                nextActivity.putExtras(extras);
                startActivity(nextActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void initializeLayoutItem() {
        parentLinearLayout = findViewById(R.id.parent_layout);
        filterButton = findViewById(R.id.filterButton);
        deleteFieldText = findViewById(R.id.deleteFieldText);
        addNewField = findViewById(R.id.addNewSpinnerText);
        subjectsText = findViewById(R.id.subjectsText);
        gradesText  = findViewById(R.id.gradesText);
        englishText = findViewById(R.id.englishText);
        mathematicsText = findViewById(R.id.mathematicsText);
        qualificationSpinner = findViewById(R.id.qualificationSpinner);
        subjectsAutoCompleteTextView = findViewById(R.id.subjectsAutoCompleteTextView);
        newSubjectsAutoCompleteTextView = findViewById(R.id.newSubjectsAutoCompleteTextView);
        gradesSpinner = findViewById(R.id.gradesSpinner);
        spmOlevelText = findViewById(R.id.spmOlevelText);
        spmOLevelSpinner = findViewById(R.id.spmOLevelSpinner);
        mathematicsSpinner = findViewById(R.id.mathematicsSpinner);
        englishSpinner = findViewById(R.id.englishSpinner);
        addMathText = findViewById(R.id.addMathText);
        addMathSpinner = findViewById(R.id.addMathSpinner);
        bahasaMalaysiaSpinner = findViewById(R.id.bahasaMalaysiaSpinner);
        bahasaMalaysiaText = findViewById(R.id.bahasaMalaysiaText);
        scienceTechnicalVocationalAutoCompleteTextView = findViewById(R.id.scienceTechnicalVocationalAutoCompleteTextView);
        scienceTechnicalVocationalGradeSpinner = findViewById(R.id.scienceTechnicalVocationalGrade);
    }

    private void convertToIntegerGrade(String qualificationLevel, int[] arrayIntegerGrades, String[] arrayStringGrades) {
        switch(qualificationLevel)
        {
            case "SPM":
            {
                for(int i = 0; i < arrayIntegerGrades.length; i++)
                {
                    if(Objects.equals(arrayStringGrades[i], "A+"))
                    {
                        arrayIntegerGrades[i] = 1;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "A"))
                    {
                        arrayIntegerGrades[i] = 2;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "A-"))
                    {
                        arrayIntegerGrades[i] = 3;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B+"))
                    {
                        arrayIntegerGrades[i] = 4;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B"))
                    {
                        arrayIntegerGrades[i] = 5;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C+"))
                    {
                        arrayIntegerGrades[i] = 6;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C"))
                    {
                        arrayIntegerGrades[i] = 7;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "D"))
                    {
                        arrayIntegerGrades[i] = 8;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "E"))
                    {
                        arrayIntegerGrades[i] = 9;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "G"))
                    {
                        arrayIntegerGrades[i] = 10;
                    }
                }
            }
            break;
            case "O-Level":
            {
                for(int i = 0; i < arrayIntegerGrades.length; i++)
                {
                    if(Objects.equals(arrayStringGrades[i], "A"))
                    {
                        arrayIntegerGrades[i] = 1;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B"))
                    {
                        arrayIntegerGrades[i] = 2;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C"))
                    {
                        arrayIntegerGrades[i] = 3;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "D"))
                    {
                        arrayIntegerGrades[i] = 4;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "E"))
                    {
                        arrayIntegerGrades[i] = 5;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "F"))
                    {
                        arrayIntegerGrades[i] = 6;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "G"))
                    {
                        arrayIntegerGrades[i] = 7;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "U"))
                    {
                        arrayIntegerGrades[i] = 8;
                    }
                }
            }
            break;
            case "STPM":
            {
                for(int i = 0; i < arrayIntegerGrades.length; i++)
                {
                    if(Objects.equals(arrayStringGrades[i], "A"))
                    {
                        arrayIntegerGrades[i] = 1;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "A-"))
                    {
                        arrayIntegerGrades[i] = 2;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B+"))
                    {
                        arrayIntegerGrades[i] = 3;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B"))
                    {
                        arrayIntegerGrades[i] = 4;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B-"))
                    {
                        arrayIntegerGrades[i] = 5;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C+"))
                    {
                        arrayIntegerGrades[i] = 6;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C"))
                    {
                        arrayIntegerGrades[i] = 7;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C-"))
                    {
                        arrayIntegerGrades[i] = 8;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "D+"))
                    {
                        arrayIntegerGrades[i] = 9;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "D"))
                    {
                        arrayIntegerGrades[i] = 10;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "F"))
                    {
                        arrayIntegerGrades[i] = 11;
                    }
                }
            }
            break;
            case "A-Level":
            {
                for(int i = 0; i < arrayIntegerGrades.length; i++)
                {
                    if(Objects.equals(arrayStringGrades[i], "A*"))
                    {
                        arrayIntegerGrades[i] = 1;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "A"))
                    {
                        arrayIntegerGrades[i] = 2;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B"))
                    {
                        arrayIntegerGrades[i] = 3;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C"))
                    {
                        arrayIntegerGrades[i] = 4;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "D"))
                    {
                        arrayIntegerGrades[i] = 5;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "E"))
                    {
                        arrayIntegerGrades[i] = 6;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "U"))
                    {
                        arrayIntegerGrades[i] = 7;
                    }
                }
            }
            break;
            case "UEC":
            {
                for(int i = 0; i < arrayIntegerGrades.length; i++)
                {
                    if(Objects.equals(arrayStringGrades[i], "A1"))
                    {
                        arrayIntegerGrades[i] = 1;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "A2"))
                    {
                        arrayIntegerGrades[i] = 2;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B3"))
                    {
                        arrayIntegerGrades[i] = 3;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B4"))
                    {
                        arrayIntegerGrades[i] = 4;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B5"))
                    {
                        arrayIntegerGrades[i] = 5;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "B6"))
                    {
                        arrayIntegerGrades[i] = 6;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C7"))
                    {
                        arrayIntegerGrades[i] = 7;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "C8"))
                    {
                        arrayIntegerGrades[i] = 8;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "F9"))
                    {
                        arrayIntegerGrades[i] = 9;
                    }
                }
            }
            break;
            case "STAM":
            {
                for(int i = 0; i < arrayIntegerGrades.length; i++)
                {
                    if(Objects.equals(arrayStringGrades[i], "Mumtaz"))
                    {
                        arrayIntegerGrades[i] = 1;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "Jayyid Jiddan"))
                    {
                        arrayIntegerGrades[i] = 2;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "Jayyid"))
                    {
                        arrayIntegerGrades[i] = 3;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "Maqbul"))
                    {
                        arrayIntegerGrades[i] = 4;
                    }
                    else if(Objects.equals(arrayStringGrades[i], "Rasib"))
                    {
                        arrayIntegerGrades[i] = 5;
                    }
                }
            }
            break;
        }
    }

    private void convertSupportiveGradeToInteger(String supportiveQualificationLevel, int[] supportiveIntegerGrade, String[] supportiveStringGrade) {
        switch(supportiveQualificationLevel)
        {
            case "SPM":
            {
                for(int i = 0; i < supportiveStringGrade.length; i++)
                {
                    if(Objects.equals(supportiveStringGrade[i], "A+"))
                    {
                        supportiveIntegerGrade[i] = 1;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "A"))
                    {
                        supportiveIntegerGrade[i] = 2;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "A-"))
                    {
                        supportiveIntegerGrade[i] = 3;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "B+"))
                    {
                        supportiveIntegerGrade[i] = 4;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "B"))
                    {
                        supportiveIntegerGrade[i] = 5;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "C+"))
                    {
                        supportiveIntegerGrade[i] = 6;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "C"))
                    {
                        supportiveIntegerGrade[i] = 7;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "D"))
                    {
                        supportiveIntegerGrade[i] = 8;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "E"))
                    {
                        supportiveIntegerGrade[i] = 9;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "G"))
                    {
                        supportiveIntegerGrade[i] = 10;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "None"))
                    {
                        supportiveIntegerGrade[i] = 100; // For indicate as error
                    }
                }
            }
            break;
            case "O-Level":
            {
                for(int i = 0; i < supportiveStringGrade.length; i++)
                {
                    if(Objects.equals(supportiveStringGrade[i], "A"))
                    {
                        supportiveIntegerGrade[i] = 1;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "B"))
                    {
                        supportiveIntegerGrade[i] = 2;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "C"))
                    {
                        supportiveIntegerGrade[i] = 3;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "D"))
                    {
                        supportiveIntegerGrade[i] = 4;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "E"))
                    {
                        supportiveIntegerGrade[i] = 5;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "F"))
                    {
                        supportiveIntegerGrade[i] = 6;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "G"))
                    {
                        supportiveIntegerGrade[i] = 7;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "U"))
                    {
                        supportiveIntegerGrade[i] = 8;
                    }
                    else if(Objects.equals(supportiveStringGrade[i], "None"))
                    {
                        supportiveIntegerGrade[i] = 100; // For indicate as error
                    }
                }
            }
            break;
        }
        extras.putIntArray("STUDENT_SUPPORTIVE_GRADES_LIST", supportiveIntegerGrade);
    }

    private boolean checkSecondQualificationError(String spmOLevelQualification, String secondMath, String secondEng, String secondAddMath, String secondBahasaMalaysia, String scienceTechnicalVocationalGrade, String qualificationLevel) {
        if(Objects.equals(qualificationLevel, "STPM") || Objects.equals(qualificationLevel, "STAM") || Objects.equals(qualificationLevel, "A-Level"))
        {
            if(spmOLevelQualification.isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose SPM or O-Level Qualification")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }

            if(scienceTechnicalVocationalAutoCompleteTextView.getText().toString().trim().isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose " + spmOLevelQualification + " Science/Technical/Vocational subject")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }

            if(scienceTechnicalVocationalGrade.isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose " + spmOLevelQualification + " Science/Technical/Vocational grade")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }

            if(secondBahasaMalaysia.isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose " + spmOLevelQualification + " Bahasa Malaysia grade")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }
            if(secondEng.isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose " + spmOLevelQualification + " English grade")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }
            if(secondMath.isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose " + spmOLevelQualification + " Mathematics grade")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }

            if(secondAddMath.isEmpty())
            {
                new MaterialDialog.Builder(FilterProgrammes.this)
                        .content("Please choose " + spmOLevelQualification + " Additional Mathematics grade")
                        .positiveText("Return")
                        .build()
                        .show();
                return true;
            }

        }
        return false;
    }

    private void loadSpinnerData(AutoCompleteTextView subject, MaterialSpinner grade) throws IOException {
        subject.setAdapter(subjectsAdapter);
        grade.setAdapter(gradesAdapter);
        flagForNewField = true; // to only set spinner scrollbar for newly added field
        setSpinnerScrollbar();
    }

    public void setSpinnerScrollbar() {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            if(flagForNewField) {
                android.widget.ListPopupWindow newGradesListPopupWindow = (android.widget.ListPopupWindow) popup.get(gradesSpinner1);
                newGradesListPopupWindow.setHeight(500);
                return;
            }

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow bahasaMalaysiaPopupWindow = (android.widget.ListPopupWindow) popup.get(bahasaMalaysiaSpinner);
            android.widget.ListPopupWindow addMathPopupWindow = (android.widget.ListPopupWindow) popup.get(addMathSpinner);
            android.widget.ListPopupWindow mathematicsPopupWindow = (android.widget.ListPopupWindow) popup.get(mathematicsSpinner);
            android.widget.ListPopupWindow englishPopupWindow = (android.widget.ListPopupWindow) popup.get(englishSpinner);
            android.widget.ListPopupWindow scienceTechnicalVocationaPopupWindow = (android.widget.ListPopupWindow) popup.get(scienceTechnicalVocationalGradeSpinner);
            android.widget.ListPopupWindow qualificationTypePopupWindow = (android.widget.ListPopupWindow) popup.get(qualificationSpinner);
            android.widget.ListPopupWindow gradesListPopupWindow = (android.widget.ListPopupWindow) popup.get(gradesSpinner);

            // Set popupWindow height to 500px
            bahasaMalaysiaPopupWindow.setHeight(500);
            addMathPopupWindow.setHeight(500);
            mathematicsPopupWindow.setHeight(500);
            englishPopupWindow.setHeight(500);
            gradesListPopupWindow.setHeight(500);
            qualificationTypePopupWindow.setHeight(500);
            scienceTechnicalVocationaPopupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //Results and SPMOLevel Listener
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String [] gradesList;

        switch(adapterView.getId()) {
            case R.id.qualificationSpinner: {
                switch(position) {
                    case -1: // if select back to hint
                    {
                        filterButton.setEnabled(false);
                        subjectsAutoCompleteTextView.setEnabled(false);
                        gradesSpinner.setEnabled(false);
                        addNewField.setEnabled(false);
                        deleteFieldText.setEnabled(false);
                        subjectsText.setTextColor(Color.GRAY);
                        gradesText.setTextColor(Color.GRAY);
                    }
                    break;

                    case 1: // SPM selected
                    {
                        subjectsList = getResources().getStringArray(R.array.spm_subjects);
                        gradesList = getResources().getStringArray(R.array.spm_grades);

                        //subject list
                        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);

                        //grade list
                        gradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                                View v;
                                if (position == 0) {
                                    CheckedTextView tv = new CheckedTextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectsAutoCompleteTextView.setAdapter(subjectsAdapter);
                        gradesSpinner.setAdapter(gradesAdapter);
                    }
                    break;

                    case 2: // STPM selected
                    {
                        subjectsList = getResources().getStringArray(R.array.stpm_subjects);
                        gradesList = getResources().getStringArray(R.array.stpm_grades);

                        // subject list
                        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);

                        //grade list
                        gradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectsAutoCompleteTextView.setAdapter(subjectsAdapter);
                        gradesSpinner.setAdapter(gradesAdapter);
                    }
                    break;

                    case 3: // UEC selected
                    {
                        subjectsList = getResources().getStringArray(R.array.uec_subjects);
                        gradesList = getResources().getStringArray(R.array.uec_grades);

                        //subject list
                        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);

                        //grade list
                        gradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectsAutoCompleteTextView.setAdapter(subjectsAdapter);
                        gradesSpinner.setAdapter(gradesAdapter);
                    }
                    break;

                    case 4: // O-Level selected
                    {
                        subjectsList = getResources().getStringArray(R.array.oLevel_subjects);
                        gradesList = getResources().getStringArray(R.array.oLevel_grades);

                        //subject list
                        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);

                        //grade list
                        gradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectsAutoCompleteTextView.setAdapter(subjectsAdapter);
                        gradesSpinner.setAdapter(gradesAdapter);
                    }
                    break;

                    case 5: // A-Level selected
                    {
                        subjectsList = getResources().getStringArray(R.array.aLevel_subjects);
                        gradesList = getResources().getStringArray(R.array.aLevel_grades);

                        //subject list
                        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);

                        //grade list
                        gradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
                            {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectsAutoCompleteTextView.setAdapter(subjectsAdapter);
                        gradesSpinner.setAdapter(gradesAdapter);
                    }
                    break;

                    case 6: // STAM selected
                    {
                        subjectsList = getResources().getStringArray(R.array.STAM_subjects);
                        gradesList = getResources().getStringArray(R.array.STAM_grades);

                        //subject list
                        subjectsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subjectsList);

                        //grade list
                        gradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
                            {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subjectsAutoCompleteTextView.setAdapter(subjectsAdapter);
                        gradesSpinner.setAdapter(gradesAdapter);
                    }
                    break;
                }
            }
            break;

            case R.id.spmOLevelSpinner:
            {
                ArrayAdapter<String> spmOLevelGradesAdapter, spmOLevelAddMathGradesAdapter, scienceTechnicalVocationalAdapter;
                String [] addMathGradeList;

                switch(position) {
                    case 1: // spm
                    {
                        gradesList = getResources().getStringArray(R.array.spm_grades);
                        addMathGradeList = getResources().getStringArray(R.array.add_math_spm_grades);
                        scienceTechnicalVocationalSubjectArrays = getResources().getStringArray(R.array.spm_science_technical_vocational_subject);

                        //grade list
                        spmOLevelGradesAdapter = new ArrayAdapter<String>(FilterProgrammes.this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
                            {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        spmOLevelAddMathGradesAdapter = new ArrayAdapter<String>(FilterProgrammes.this, R.layout.spinner_text, addMathGradeList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
                            {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        scienceTechnicalVocationalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, scienceTechnicalVocationalSubjectArrays);
                        scienceTechnicalVocationalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spmOLevelGradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spmOLevelAddMathGradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bahasaMalaysiaSpinner.setAdapter(spmOLevelGradesAdapter);
                        mathematicsSpinner.setAdapter(spmOLevelGradesAdapter);
                        englishSpinner.setAdapter(spmOLevelGradesAdapter);
                        scienceTechnicalVocationalGradeSpinner.setAdapter(spmOLevelGradesAdapter);
                        scienceTechnicalVocationalAutoCompleteTextView.setAdapter(scienceTechnicalVocationalAdapter);
                        addMathSpinner.setAdapter(spmOLevelAddMathGradesAdapter);
                    }
                    break;

                    case 2: // o-level
                    {
                        gradesList = getResources().getStringArray(R.array.oLevel_grades);
                        addMathGradeList = getResources().getStringArray(R.array.add_math_oLevel_grades);
                        scienceTechnicalVocationalSubjectArrays = getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject);

                        //grade list
                        spmOLevelGradesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gradesList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
                            {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        spmOLevelAddMathGradesAdapter = new ArrayAdapter<String>(FilterProgrammes.this, R.layout.spinner_text, addMathGradeList) {
                            @Override
                            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
                            {
                                View v;
                                if (position == 0) {
                                    TextView tv = new TextView(getContext());
                                    tv.setHeight(0);
                                    tv.setVisibility(View.GONE);
                                    v = tv;
                                }
                                else {
                                    v = super.getDropDownView(position, null, parent);
                                }
                                return v;
                            }
                        };
                        scienceTechnicalVocationalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, scienceTechnicalVocationalSubjectArrays);
                        scienceTechnicalVocationalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spmOLevelGradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spmOLevelAddMathGradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bahasaMalaysiaSpinner.setAdapter(spmOLevelGradesAdapter);
                        mathematicsSpinner.setAdapter(spmOLevelGradesAdapter);
                        englishSpinner.setAdapter(spmOLevelGradesAdapter);
                        scienceTechnicalVocationalGradeSpinner.setAdapter(spmOLevelGradesAdapter);
                        scienceTechnicalVocationalAutoCompleteTextView.setAdapter(scienceTechnicalVocationalAdapter);
                        addMathSpinner.setAdapter(spmOLevelAddMathGradesAdapter);
                    }
                    break;
                }
            }
            break;
        }

        if(adapterView.getId() == R.id.qualificationSpinner) {

            if(position == 2 || position == 5 || position == 6) {
                spmOlevelText.setVisibility(View.VISIBLE);
                spmOLevelSpinner.setVisibility(View.VISIBLE);
                englishText.setVisibility(View.VISIBLE);
                englishSpinner.setVisibility(View.VISIBLE);
                mathematicsText.setVisibility(View.VISIBLE);
                mathematicsSpinner.setVisibility(View.VISIBLE);
                addMathText.setVisibility(View.VISIBLE);
                addMathSpinner.setVisibility(View.VISIBLE);
                bahasaMalaysiaText.setVisibility(View.VISIBLE);
                bahasaMalaysiaSpinner.setVisibility(View.VISIBLE);
                scienceTechnicalVocationalAutoCompleteTextView.setVisibility(View.VISIBLE);
                scienceTechnicalVocationalGradeSpinner.setVisibility(View.VISIBLE);
                mathematicsSpinner.setEnabled(false);
                englishSpinner.setEnabled(false);
                addMathSpinner.setEnabled(false);
                bahasaMalaysiaSpinner.setEnabled(false);
                scienceTechnicalVocationalAutoCompleteTextView.setEnabled(false);
                scienceTechnicalVocationalGradeSpinner.setEnabled(false);
            }
            else {
                addMathText.setVisibility(View.GONE);
                addMathSpinner.setVisibility(View.GONE);
                englishText.setVisibility(View.GONE);
                englishSpinner.setVisibility(View.GONE);
                mathematicsText.setVisibility(View.GONE);
                mathematicsSpinner.setVisibility(View.GONE);
                spmOlevelText.setVisibility(View.GONE);
                spmOLevelSpinner.setVisibility(View.GONE);
                bahasaMalaysiaText.setVisibility(View.GONE);
                bahasaMalaysiaSpinner.setVisibility(View.GONE);
                scienceTechnicalVocationalAutoCompleteTextView.setVisibility(View.GONE);
                scienceTechnicalVocationalGradeSpinner.setVisibility(View.GONE);
                mathematicsSpinner.setEnabled(false);
                englishSpinner.setEnabled(false);
                addMathSpinner.setEnabled(false);
                bahasaMalaysiaSpinner.setEnabled(false);
                scienceTechnicalVocationalAutoCompleteTextView.setEnabled(false);
                scienceTechnicalVocationalGradeSpinner.setEnabled(false);
            }
        }
        else if(adapterView.getId() == R.id.spmOLevelSpinner) {

            if(position != 0) {
                scienceTechnicalVocationalAutoCompleteTextView.setEnabled(true);
                scienceTechnicalVocationalGradeSpinner.setEnabled(true);
                mathematicsSpinner.setEnabled(true);
                englishSpinner.setEnabled(true);
                addMathSpinner.setEnabled(true);
                bahasaMalaysiaSpinner.setEnabled(true);
                scienceTechnicalVocationalAutoCompleteTextView.setText("");
                scienceTechnicalVocationalAutoCompleteTextView.dismissDropDown();
            }
        }

        // after choose results type and set the adapters, make subjects and grades spinner enable
        // if both subject and grades spinner is disabled, make them both enable back
        if(adapterView.getId() == R.id.qualificationSpinner) {

            if(position != -1) {
                filterButton.setEnabled(true);
                subjectsAutoCompleteTextView.setEnabled(true);
                gradesSpinner.setEnabled(true);
                addNewField.setEnabled(true);
                subjectsText.setTextColor(getResources().getColor(R.color.colorAccent));
                gradesText.setTextColor(getResources().getColor(R.color.colorAccent));
                subjectsAutoCompleteTextView.setText("");
                scienceTechnicalVocationalAutoCompleteTextView.setText("");
                subjectsAutoCompleteTextView.dismissDropDown();
                spmOLevelSpinner.setSelection(0);
                mathematicsSpinner.setSelection(0);
                englishSpinner.setSelection(0);
                addMathSpinner.setSelection(0);

                if(parentLinearLayout.getChildCount() != 12 ) // if added new view
                {
                    deleteFieldText.setEnabled(false);
                    for(int i = parentLinearLayout.getChildCount(); i != 12; i--)  // if switch result, reset back to default
                    {
                        parentLinearLayout.removeViewAt(parentLinearLayout.getChildCount() - 10);
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}
