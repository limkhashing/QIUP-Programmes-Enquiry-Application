package com.qiup.programmeenquiry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.qiup.POJO.RulePojo;
import com.qiup.entryrules.BAC;
import com.qiup.entryrules.BBA;
import com.qiup.entryrules.BBS;
import com.qiup.entryrules.BCE;
import com.qiup.entryrules.BCS;
import com.qiup.entryrules.BEM;
import com.qiup.entryrules.BET;
import com.qiup.entryrules.BFI;
import com.qiup.entryrules.BHT;
import com.qiup.entryrules.BIS;
import com.qiup.entryrules.BIT;
import com.qiup.entryrules.BSNE;
import com.qiup.entryrules.BS_ActuarialSciences;
import com.qiup.entryrules.BioTech;
import com.qiup.entryrules.CorporateComm;
import com.qiup.entryrules.DAC;
import com.qiup.entryrules.DBM;
import com.qiup.entryrules.DCA;
import com.qiup.entryrules.DCE;
import com.qiup.entryrules.DET;
import com.qiup.entryrules.DHM;
import com.qiup.entryrules.DIS;
import com.qiup.entryrules.DIT;
import com.qiup.entryrules.DME;
import com.qiup.entryrules.ECE;
import com.qiup.entryrules.FIA;
import com.qiup.entryrules.FIB;
import com.qiup.entryrules.FIS;
import com.qiup.entryrules.MBBS;
import com.qiup.entryrules.MassCommAdvertising;
import com.qiup.entryrules.MassCommJournalism;
import com.qiup.entryrules.Pharmacy;
import com.qiup.entryrules.TESL;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterestProgramme extends AppCompatActivity {
    TextView maxTextView;
    InstantAutoComplete interestedProgrammeAutoComplete, newInterestedProgrammeAutoComplete;
    Button generateButton, addInterestedProgramme, deleteInterestedProgramme;
    EditText editOtherProgramme;
    CheckBox interestCheckBox;
    LinearLayout interestProgrammeParentLayout;
    ArrayAdapter<String> programmeListAdapter;
    Bundle extras;
    boolean flagForNewField;
    String[] arrayOfProgramme, interestedProgrammeArray;
    MaterialDialog.Builder builder;
    MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_programme);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        builder = new MaterialDialog.Builder(this)
                .title("Generating")
                .progress(true, 0)
                .autoDismiss(false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .content("Please wait...");
        dialog = builder.build();

        extras = getIntent().getExtras();
        addInterestedProgramme = findViewById(R.id.addInterestedProgrammeText);
        deleteInterestedProgramme = findViewById(R.id.deleteInterestedProgrammeText);
        maxTextView = findViewById(R.id.maxTextView);
        interestedProgrammeAutoComplete = findViewById(R.id.interestedProgrammeAutoComplete);
        generateButton = findViewById(R.id.generateResultButton);
        interestProgrammeParentLayout = findViewById(R.id.interestProgrammeParentLayout);
        editOtherProgramme = findViewById(R.id.editOtherProgramme);
        interestCheckBox = findViewById(R.id.interestCheckBox);

        // Setting the list of programmes array adapter
        arrayOfProgramme = getResources().getStringArray(R.array.programme_list);
        programmeListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayOfProgramme);

        interestCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    addInterestedProgramme.setEnabled(false);
                    deleteInterestedProgramme.setEnabled(false);
                    editOtherProgramme.setEnabled(false);
                    interestedProgrammeAutoComplete.setEnabled(false);
                    interestedProgrammeAutoComplete.setText("");
                    interestedProgrammeAutoComplete.dismissDropDown();

                    editOtherProgramme.setText("");
                    // Reset the layout to default
                    for(int i = interestProgrammeParentLayout.getChildCount(); i != 6; i--)
                        interestProgrammeParentLayout.removeViewAt(interestProgrammeParentLayout.getChildCount() - 4);

                    maxTextView.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else {
                    maxTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                    addInterestedProgramme.setEnabled(true);
                    deleteInterestedProgramme.setEnabled(false);
                    interestedProgrammeAutoComplete.setEnabled(true);
                    editOtherProgramme.setEnabled(true);
                }
            }
        });

        addInterestedProgramme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.new_interest_programme_field, null);

                // Add the new row before the add field button.
                interestProgrammeParentLayout.addView(rowView, interestProgrammeParentLayout.getChildCount() - 3);
                newInterestedProgrammeAutoComplete = rowView.findViewById(R.id.newInterestedProgrammeAutoComplete);
                if(interestProgrammeParentLayout.getChildCount() != 6) // if added new field, make the delete field text enable
                {
                    deleteInterestedProgramme.setEnabled(true);
                }
                if(interestProgrammeParentLayout.getChildCount() == 8) // max 3 interested programmes // if got Other = 10
                {
                    addInterestedProgramme.setEnabled(false);
                }

                try {
                    loadAutoCompleteData(newInterestedProgrammeAutoComplete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        deleteInterestedProgramme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interestProgrammeParentLayout.getChildCount() != 6) // if added new field
                {
                    interestProgrammeParentLayout.removeViewAt(interestProgrammeParentLayout.getChildCount() - 4);
                    addInterestedProgramme.setEnabled(true);
                }
                if(interestProgrammeParentLayout.getChildCount() == 6) // if deleted and back to original layout // if Other 8
                {
                    deleteInterestedProgramme.setEnabled(false);
                    flagForNewField = false;
                }
            }
        });

        editOtherProgramme.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editOtherProgramme, InputMethodManager.SHOW_IMPLICIT);
                    editOtherProgramme.setHint("Leave blank if none");
                }
                else
                    editOtherProgramme.setHint("");
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final StringBuilder allInterestedProgramme = new StringBuilder();
                // Start validate inputted programme. If the key-in programme is not exist in programme list, return
                // If checkbox is not checked, meaning that got interest programme
                if(!interestCheckBox.isChecked()) {
                    // Copy all the array and make all Upper case for case insensitive
                    String [] foundationArray = new String[3], diplomaArray = new String[8], degreeArray = new String[21];

                    for(int i = 0; i  < arrayOfProgramme.length; i++)
                        arrayOfProgramme[i] = arrayOfProgramme[i].toUpperCase();

                    System.arraycopy(arrayOfProgramme, 0, foundationArray, 0, 3);
                    System.arraycopy(arrayOfProgramme, 3, diplomaArray, 0, 8);
                    System.arraycopy(arrayOfProgramme, 11, degreeArray, 0, 21);

                    // Check interested programme is blank or not. If blank then return
                    if(Objects.equals(interestedProgrammeAutoComplete.getText().toString().trim(), "")) {
                        new MaterialDialog.Builder(InterestProgramme.this)
                                .content("Please key-in interest pragramme")
                                .positiveText("Return")
                                .build()
                                .show();
                        dialog.dismiss();
                        return;
                    }

                    // Based on qualification, check programmes valid or not
                    // UEC can from foundation to degree
                    // If is SPM, O-Level only foundation to diploma
                    if(Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "SPM")
                            || Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "O-Level")) {
                        if(Arrays.asList(degreeArray).contains(interestedProgrammeAutoComplete.getText().toString().toUpperCase().trim()))
                        {
                            MaterialDialog md = new MaterialDialog.Builder(InterestProgramme.this)
                                    .title("Sorry. SPM / O-Level can only enquiry until Diploma level")
                                    .customView(R.layout.image_layout, false)
                                    .positiveText("Return")
                                    .build();
                            md.show();
                            dialog.dismiss();
                            return;
                        }
                        if(!Arrays.asList(foundationArray).contains(interestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())
                                && !Arrays.asList(diplomaArray).contains(interestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())) {
                            new MaterialDialog.Builder(InterestProgramme.this)
                                    .content("Please key-in valid interest pragramme")
                                    .positiveText("Return")
                                    .build()
                                    .show();
                            dialog.dismiss();
                            return;
                        }
                    }

                    // If is STPM, A-Level, STAM only diploma to degree
                    if(!Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "SPM")
                            && !Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "O-Level")
                            && !Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "UEC")) {
                        if(Arrays.asList(foundationArray).contains(interestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())) {
                            new MaterialDialog.Builder(InterestProgramme.this)
                                    .content("Above SPM / O-Level qualification cannot enquiry Foundation level")
                                    .positiveText("Return")
                                    .build()
                                    .show();
                            dialog.dismiss();
                            return;
                        }
                        if(!Arrays.asList(diplomaArray).contains(interestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())
                                && !Arrays.asList(degreeArray).contains(interestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())) {
                            new MaterialDialog.Builder(InterestProgramme.this)
                                    .content("Please key-in valid interest pragramme")
                                    .positiveText("Return")
                                    .build()
                                    .show();
                            dialog.dismiss();
                            return;
                        }
                    }

                    // For new added interest programme, do the same checking
                    for (int i = 0; i < interestProgrammeParentLayout.getChildCount(); i++) {
                        view = interestProgrammeParentLayout.getChildAt(i);
                        if(view instanceof LinearLayout) {
                            if(view.getId() == R.id.newProgrammes) {
                                newInterestedProgrammeAutoComplete = (InstantAutoComplete) ((LinearLayout) view).getChildAt(0);

                                if(Objects.equals(newInterestedProgrammeAutoComplete.getText().toString().trim(), "")) {
                                    new MaterialDialog.Builder(InterestProgramme.this)
                                            .content("Please key-in interest pragramme")
                                            .positiveText("Return")
                                            .build()
                                            .show();
                                    dialog.dismiss();
                                    return;
                                }

                                if(Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "SPM")
                                        || Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "O-Level")) {
                                    if(Arrays.asList(degreeArray).contains(newInterestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())) {
                                        MaterialDialog md = new MaterialDialog.Builder(InterestProgramme.this)
                                                .title("Sorry. SPM / O-Level can only enquiry until Diploma level")
                                                .customView(R.layout.image_layout, false)
                                                .positiveText("Return")
                                                .build();
                                        md.show();
                                        return;
                                    }
                                    if(!Arrays.asList(foundationArray).contains(newInterestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())
                                            && !Arrays.asList(diplomaArray).contains(newInterestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())) {
                                        new MaterialDialog.Builder(InterestProgramme.this)
                                                .content("Please key-in valid interest pragramme")
                                                .positiveText("Return")
                                                .build()
                                                .show();
                                        dialog.dismiss();
                                        return;
                                    }
                                }

                                if(!Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "SPM")
                                        && !Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "O-Level")
                                        && !Objects.equals(extras.getString("QUALIFICATION_LEVEL"), "UEC")) {
                                    if(Arrays.asList(foundationArray).contains(newInterestedProgrammeAutoComplete.getText().toString().toUpperCase())) {
                                        new MaterialDialog.Builder(InterestProgramme.this)
                                                .content("Above SPM / O-Level qualification cannot enquiry Foundation level")
                                                .positiveText("Return")
                                                .build()
                                                .show();
                                        dialog.dismiss();
                                        return;
                                    }
                                    if(!Arrays.asList(diplomaArray).contains(newInterestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())
                                            && !Arrays.asList(degreeArray).contains(newInterestedProgrammeAutoComplete.getText().toString().toUpperCase().trim())) {
                                        new MaterialDialog.Builder(InterestProgramme.this)
                                                .content("Above SPM / O-Level qualification cannot enquiry Foundation level")
                                                .positiveText("Return")
                                                .build()
                                                .show();
                                        dialog.dismiss();
                                        return;
                                    }
                                }
                            }
                        }
                    }

                    // Get all the new added interest programme view value and add to a List
                    ArrayList<String> interestedProgrammeList = new ArrayList<>();
                    for (int i = 0; i < interestProgrammeParentLayout.getChildCount(); i++) {
                        view = interestProgrammeParentLayout.getChildAt(i);
                        if(view instanceof LinearLayout) {
                            if(view.getId() == R.id.newProgrammes) {
                                newInterestedProgrammeAutoComplete = (InstantAutoComplete) ((LinearLayout) view).getChildAt(0);
                                interestedProgrammeList.add(newInterestedProgrammeAutoComplete.getText().toString());
                            }
                        }
                    }

                    //initialize the array and set first index as interest programme spinner
                    interestedProgrammeArray = new String[interestedProgrammeList.size() + 1];
                    interestedProgrammeArray[0] = interestedProgrammeAutoComplete.getText().toString();

                    //if added new field of interest programme
                    if(interestProgrammeParentLayout.getChildCount() == 7) { // only added 1
                        interestedProgrammeArray[1] = interestedProgrammeList.get(0);
                    }
                    else if (interestProgrammeParentLayout.getChildCount() == 8) { // added 2
                        interestedProgrammeArray[1] = interestedProgrammeList.get(0);
                        interestedProgrammeArray[2] = interestedProgrammeList.get(1);
                    }

                    // Check if there are duplicated interest programme. if got then return
                    for(int i = 0; i < interestedProgrammeArray.length; i++) {
                        for(int j = i + 1; j < interestedProgrammeArray.length; j++) {
                            if(Objects.equals(interestedProgrammeArray[i], interestedProgrammeArray[j])) {
                                new MaterialDialog.Builder(InterestProgramme.this)
                                        .content("There is a duplicate interest programme")
                                        .positiveText("Return")
                                        .build()
                                        .show();
                                dialog.dismiss();
                                return;
                            }
                        }
                    }

                    for(int i = 0; i < interestedProgrammeArray.length; i++) {
                        if(i != interestedProgrammeArray.length-1) // apepend "," for every programme
                        {
                            allInterestedProgramme.append(interestedProgrammeArray[i]).append(", ");
                        }
                        else // if is last index / programme, no need append ","
                        {
                            allInterestedProgramme.append(interestedProgrammeArray[i]);
                        }
                    }
                    if(editOtherProgramme.getText().length() != 0) // if editOtherProgramme is not blank, append it
                    {
                        allInterestedProgramme.append("\nOther Programme: ").append(editOtherProgramme.getText().toString());
                    }

                    // Put intent of string interest programme then pass to next activity
                    extras.putStringArray("STUDENT_INTERESTED_PROGRAMME_LIST", interestedProgrammeArray);
                    extras.putBoolean("STUDENT_IS_GOT_INTERESTED_PROGRAMME", true);
                }
                else // no interest programme, pass false for key STUDENT_IS_GOT_INTERESTED_PROGRAMME
                {
                    allInterestedProgramme.append("None");
                    extras.putBoolean("STUDENT_IS_GOT_INTERESTED_PROGRAMME", false);
                }

                //final String baseURL = "https://kslim5703.000webhostapp.com";
                final String baseURL = "https://firebasestorage.googleapis.com/";
                Retrofit ruleRetrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final GetRuleAPI getRule_api = ruleRetrofit.create(GetRuleAPI.class);
                Call<RulePojo> call = getRule_api.getRule();
                call.enqueue(new Callback<RulePojo>() {
                    @Override
                    public void onResponse(Call<RulePojo> call, Response<RulePojo> response) {
                        Log.d("Call request", call.request().toString());
                        Log.d("Call request header", call.request().headers().toString());
                        Log.d("Response raw header", response.headers().toString());
                        Log.d("Response raw", String.valueOf(response.raw().body()));
                        Log.d("Response code", String.valueOf(response.code()));
                        Gson gson = new Gson();
                        Log.d("JSON", gson.toJson(response.body()));
                        new RulePojo(response);
                        fireRule();
                        dialog.dismiss();
                        postToMainSpreadsheet(allInterestedProgramme);
                    }

                    @Override
                    public void onFailure(Call<RulePojo> call, Throwable t) {
                        t.printStackTrace();
                        t.getCause();
                        Log.d("JSON", t.getMessage());
                        Log.d("JSON", "onFailure: "+t.getCause());
                        dialog.dismiss();
                        new MaterialDialog.Builder(InterestProgramme.this)
                                .title("Unable to generate")
                                .content("Please check ur network connectivity")
                                .positiveText("Return")
                                .show();
                    }
                });
            }
        });

        programmeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestedProgrammeAutoComplete.setAdapter(programmeListAdapter);
        interestedProgrammeAutoComplete.setEnabled(false);
        interestedProgrammeAutoComplete.setText("");
        editOtherProgramme.setEnabled(false);
        editOtherProgramme.setText("");
        addInterestedProgramme.setEnabled(false);
        deleteInterestedProgramme.setEnabled(false);
    }

    private void postToMainSpreadsheet(StringBuilder allInterestedProgramme) {
        StringBuilder eligibleProgramme = new StringBuilder();
        StringBuilder notEligibleProgramme = new StringBuilder();

        if(!Objects.equals(allInterestedProgramme.toString(), "None")) {
            for(int i = 0; i < interestedProgrammeArray.length; i++) {
                switch(interestedProgrammeArray[i])
                {
                    // Foundation
                    case "Foundation in Arts":
                    {
                        if(FIA.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Foundation in Arts");
                            }
                            else {
                                eligibleProgramme.append(", Foundation in Arts");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Foundation in Arts");
                            }
                            else {
                                notEligibleProgramme.append(", Foundation in Arts");
                            }
                        }
                    }
                    break;
                    case "Foundation in Business":
                    {
                        if(FIA.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Foundation in Business");
                            }
                            else {
                                eligibleProgramme.append(", Foundation in Business");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Foundation in Business");
                            }
                            else {
                                notEligibleProgramme.append(", Foundation in Business");
                            }
                        }
                    }
                    break;
                    case "Foundation in Sciences":
                    {
                        if(FIS.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Foundation in Sciences");
                            }
                            else {
                                eligibleProgramme.append(", Foundation in Sciences");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Foundation in Sciences");
                            }
                            else {
                                notEligibleProgramme.append(", Foundation in Sciences");
                            }
                        }
                    }
                    break;

                    // Diploma
                    case "Diploma in Business Management":
                    {
                        if(DBM.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Business Management");
                            }
                            else
                            {
                                eligibleProgramme.append(", Diploma in Business Management");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Business Management");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Business Management");
                            }
                        }
                    }
                    break;
                    case "Diploma in Hotel Management":
                    {
                        if(DHM.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Hotel Management");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Hotel Management");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Hotel Management");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Hotel Management");
                            }
                        }
                    }
                    break;
                    case "Diploma of Accountancy":
                    {
                        if(DAC.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma of Accountancy");
                            }
                            else {
                                eligibleProgramme.append(", Diploma of Accountancy");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma of Accountancy");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma of Accountancy");
                            }
                        }
                    }
                    break;
                    case "Diploma in Early Childhood Education":
                    {
                        if(DCE.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Early Childhood Education");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Early Childhood Education");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Early Childhood Education");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Early Childhood Education");
                            }
                        }
                    }
                    break;
                    case "Diploma in Information Technology":
                    {
                        if(DIT.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Information Technology");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Information Technology");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Information Technology");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Information Technology");
                            }
                        }
                    }
                    break;
                    case "Diploma in Business Information System":
                    {
                        if(DIS.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Business Information System");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Business Information System");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Business Information System");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Business Information System");
                            }
                        }
                    }
                    break;
                    case "Diploma in Mechatronics Engineering":
                    {
                        if(DME.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Mechatronics Engineering");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Mechatronics Engineering");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Mechatronics Engineering");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Mechatronics Engineering");
                            }
                        }
                    }
                    break;
                    case "Diploma in Environmental Technology":
                    {
                        if(DET.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Environmental Technology");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Environmental Technology");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Environmental Technology");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Environmental Technology");
                            }
                        }
                    }
                    break;
                    case "Diploma in Culinary Arts":
                    {
                        if(DCA.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Diploma in Culinary Arts");
                            }
                            else {
                                eligibleProgramme.append(", Diploma in Culinary Arts");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Diploma in Culinary Arts");
                            }
                            else {
                                notEligibleProgramme.append(", Diploma in Culinary Arts");
                            }
                        }
                    }

                    //Degree
                    case "Bachelor of Business Administration (Hons)":
                    {
                        if(BBA.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Business Administration (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Business Administration (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Business Administration (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Business Administration (Hons)");
                            }
                        }
                    }
                    break;
                    case "BBA (Hons) in Hospitality & Tourism Management":
                    {
                        if(BHT.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("BBA (Hons) in Hospitality & Tourism Management");
                            }
                            else {
                                eligibleProgramme.append(", BBA (Hons) in Hospitality & Tourism Management");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("BBA (Hons) in Hospitality & Tourism Management");
                            }
                            else {
                                notEligibleProgramme.append(", BBA (Hons) in Hospitality & Tourism Management");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Accountancy (Hons)":
                    {
                        if(BAC.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Accountancy (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Accountancy (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Accountancy (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Accountancy (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Finance (Hons)":
                    {
                        if(BFI.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Finance (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Finance (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Finance (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Finance (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Arts (Hons) TESL":
                    {
                        if(TESL.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Arts (Hons) TESL");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Arts (Hons) TESL");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Arts (Hons) TESL");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Arts (Hons) TESL");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Corporate Communication (Hons)":
                    {
                        if(CorporateComm.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Corporate Communication (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Corporate Communication (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Corporate Communication (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Corporate Communication (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Mass Communication (Hons) Journalism":
                    {
                        if(MassCommJournalism.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Mass Communication (Hons) Journalism");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Mass Communication (Hons) Journalism");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Mass Communication (Hons) Journalism");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Mass Communication (Hons) Journalism");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Mass Communication (Hons) Advertising":
                    {
                        if(MassCommAdvertising.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Mass Communication (Hons) Advertising");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Mass Communication (Hons) Advertising");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Mass Communication (Hons) Advertising");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Mass Communication (Hons) Advertising");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Early Childhood Education (Hons)":
                    {
                        if(BCE.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Early Childhood Education (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Early Childhood Education (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Early Childhood Education (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Early Childhood Education (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Special Needs Education (Hons)":
                    {
                        if(BSNE.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Special Needs Education (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Special Needs Education (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Special Needs Education (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Special Needs Education (Hons)");
                            }
                        }
                    }
                    case "Bachelor of Computer Science (Hons)":
                    {
                        if(BCS.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Computer Science (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Computer Science (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Computer Science (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Computer Science (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Information Technology (Hons)":
                    {
                        if(BIT.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Information Technology (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Information Technology (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Information Technology (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Information Technology (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Business Information System (Hons)":
                    {
                        if(BIS.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Business Information System (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Business Information System (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Business Information System (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Business Information System (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Engineering (Hons) Electronics & Communications Engineering":
                    {
                        if(ECE.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Engineering (Hons) Electronics & Communications Engineering");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Engineering (Hons) Electronics & Communications Engineering");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Engineering (Hons) Electronics & Communications Engineering");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Engineering (Hons) Electronics & Communications Engineering");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Engineering (Hons) in Mechatronics":
                    {
                        if(BEM.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Engineering (Hons) in Mechatronics");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Engineering (Hons) in Mechatronics");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Engineering (Hons) in Mechatronics");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Engineering (Hons) in Mechatronics");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Science (Hons) in Biotechnology":
                    {
                        if(BioTech.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Science (Hons) in Biotechnology");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Science (Hons) in Biotechnology");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Science (Hons) in Biotechnology");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Science (Hons) in Biotechnology");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Environmental Technology (Hons)":
                    {
                        if(BET.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Environmental Technology (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Environmental Technology (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Environmental Technology (Hons)");
                            }
                            else
                            {
                                notEligibleProgramme.append(", Bachelor of Environmental Technology (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Science (Hons) Actuarial Sciences":
                    {
                        if(BS_ActuarialSciences.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Science (Hons) Actuarial Sciences");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Science (Hons) Actuarial Sciences");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Science (Hons) Actuarial Sciences");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Science (Hons) Actuarial Sciences");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Pharmacy (Hons)":
                    {
                        if(Pharmacy.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Pharmacy (Hons)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Pharmacy (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Pharmacy (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Pharmacy (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Biomedical Sciences (Hons)":
                    {
                        if(BBS.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Biomedical Sciences (Hons)");
                            }
                            else
                            {
                                eligibleProgramme.append(", Bachelor of Biomedical Sciences (Hons)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Biomedical Sciences (Hons)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Biomedical Sciences (Hons)");
                            }
                        }
                    }
                    break;
                    case "Bachelor of Medicine & Bachelor of Surgery (MBBS)":
                    {
                        if(MBBS.isJoinProgramme()) {
                            if(i == 0) {
                                eligibleProgramme.append("Bachelor of Medicine & Bachelor of Surgery (MBBS)");
                            }
                            else {
                                eligibleProgramme.append(", Bachelor of Medicine & Bachelor of Surgery (MBBS)");
                            }
                        }
                        else {
                            if(i == 0) {
                                notEligibleProgramme.append("Bachelor of Medicine & Bachelor of Surgery (MBBS)");
                            }
                            else {
                                notEligibleProgramme.append(", Bachelor of Medicine & Bachelor of Surgery (MBBS)");
                            }
                        }
                    }
                    break;
                }
            }
        }

        // Use Retrofit library to make post
        // Post to capture students info spreadsheet
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/u/1/d/e/")
                .build();
        final SpreadsheetsAPI mainSpreadsheetAPI = retrofit.create(SpreadsheetsAPI.class);
        Call<Void> postToSpreadsheet = mainSpreadsheetAPI.postToSpreadsheet(
                extras.getString("NAME"),
                extras.getString("IC"),
                extras.getString("SCHOOL_NAME"),
                extras.getString("QUALIFICATION_LEVEL"),
                extras.getString("CONTACT_NUMBER"),
                extras.getString("EMAIL"),
                allInterestedProgramme.toString(),
                eligibleProgramme.toString(),
                notEligibleProgramme.toString(),
                extras.getString("REMARK")
        );
        postToSpreadsheet.enqueue(new EmptyCallback<Void>());

        Intent resultsOfFiltering = new Intent(InterestProgramme.this, ResultsOfFiltering.class);
        resultsOfFiltering.putExtras(extras);
        startActivity(resultsOfFiltering);

        // Reset back to default upon click the button
        editOtherProgramme.setText("");
        editOtherProgramme.clearFocus();
        while(interestProgrammeParentLayout.getChildCount() != 6) {
            interestProgrammeParentLayout.removeViewAt(interestProgrammeParentLayout.getChildCount() - 4);
        }
        interestCheckBox.setChecked(true);
        interestedProgrammeAutoComplete.setText("");
    }

    private void fireRule() {
        // Create facts
        Facts facts = new Facts();
        facts.put("Qualification Level",  extras.getString("QUALIFICATION_LEVEL"));
        facts.put("Student's Subjects", extras.getStringArray("STUDENT_SUBJECTS_LIST"));
        facts.put("Student's Grades", extras.getIntArray("STUDENT_GRADES_LIST"));
        facts.put("Student's SPM or O-Level", extras.getString("STUDENT_SECONDARY_QUALIFICATION"));
        facts.put("Student's Supportive Grades", extras.getIntArray("STUDENT_SUPPORTIVE_GRADES_LIST"));

        // Create and define rules
        Rules rules = new Rules(new FIA(), new FIB(), new FIS(),
                new DBM(), new DHM(), new DCE(), new DAC(),
                new DIT(), new DIS(), new DME(), new DET(), new DCA(),
                new BBA(), new BAC(), new BFI(), new BCS(), new BIT(),
                new BIS(), new BHT(), new BCE(), new BSNE(),
                new MassCommAdvertising(), new MassCommJournalism(), new CorporateComm(), new TESL(),
                new ECE(), new BioTech(), new MBBS(), new Pharmacy(), new BS_ActuarialSciences(),
                new BEM(), new BET(), new BBS()
        );

        // Create a rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    private void loadAutoCompleteData(AutoCompleteTextView programme) throws IOException {
        programme.setAdapter(programmeListAdapter);
    }

    // For back arrow button
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public class EmptyCallback<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) { }
        @Override
        public void onFailure(Call<T> call, Throwable t) { }
    }

}
