package com.qiup.programmeenquiry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.qiup.entryrules.Pharmacy;
import com.qiup.entryrules.TESL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultsOfFiltering extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    MenuItem eligibleIcon, notEligibleIcon;
    ListView eligibleListView, notEligibleListView;
    List<String> eligibleProgrammesList, notEligibleProgrammesList,
            //Requirements Description
            fiaRequirements, fibRequirements, fisRequirements,
            dbmRequirements, dhmRequirements, dacRequirements, dceRequirements,
            ditRequirements, disRequirements, dmeRequirements, detRequirements, dcaRequirements,
            bbaRequirements, bbaHospitalityRequirements, bacRequirements, bfiRequirements, teslRequirements,
            corporateCommRequirements, bmcAdvertisingRequirements, bmcJournalismRequirements,
            bceRequirements, bsneRequirements, bcsRequirements, bitRequirements, bisRequirements,
            eceRequirements, bemRequirements, biotechRequirements, betRequirements,
            actuarialScienceRequirementsm, pharmacyRequirements, bbsRequirements, mbbsRequirements;
    String qualificationLevel, otherInterestedProgramme;
    Bundle extras;
    TextView eligibleTextView, notEligibleTextView, schorlarshipEligibility;
    String[] interestedProgramme, subjectsStringArray, gradesStringArray;
    int[] gradesIntegerArray;
    boolean isGotInterestedProgramme;
    DescriptionAdapter descriptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_of_filtering);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get student's academic data
        extras = getIntent().getExtras();
        qualificationLevel = extras.getString("QUALIFICATION_LEVEL");
        isGotInterestedProgramme = extras.getBoolean("STUDENT_IS_GOT_INTERESTED_PROGRAMME");
        interestedProgramme = extras.getStringArray("STUDENT_INTERESTED_PROGRAMME_LIST");
        subjectsStringArray = extras.getStringArray("STUDENT_SUBJECTS_LIST");
        gradesStringArray = extras.getStringArray("STUDENT_STRING_GRADES_LIST");
        gradesIntegerArray = extras.getIntArray("STUDENT_GRADES_LIST");
        otherInterestedProgramme = extras.getString("STUDENT_OTHER_INTERESTED_PROGRAMME");

        // Reference to view
        eligibleTextView = findViewById(R.id.eligibleTextView);
        notEligibleTextView = findViewById(R.id.notEligibleTextView);
        eligibleListView = findViewById(R.id.eligibleProgrammesList);
        notEligibleListView = findViewById(R.id.notEligibleProgrammesList);

        // Foundation
        fiaRequirements = new ArrayList<>();
        fibRequirements = new ArrayList<>();
        fisRequirements = new ArrayList<>();
        // Diploma
        dbmRequirements = new ArrayList<>();
        dhmRequirements = new ArrayList<>();
        dacRequirements = new ArrayList<>();
        dceRequirements = new ArrayList<>();
        ditRequirements = new ArrayList<>();
        disRequirements = new ArrayList<>();
        dmeRequirements = new ArrayList<>();
        detRequirements = new ArrayList<>();
        dcaRequirements = new ArrayList<>();

        // Degree
        bbaRequirements = new ArrayList<>();
        bbaHospitalityRequirements = new ArrayList<>();
        bacRequirements = new ArrayList<>();
        bfiRequirements = new ArrayList<>();
        teslRequirements = new ArrayList<>();
        corporateCommRequirements = new ArrayList<>();
        bmcAdvertisingRequirements = new ArrayList<>();
        bmcJournalismRequirements = new ArrayList<>();
        bceRequirements = new ArrayList<>();
        bsneRequirements = new ArrayList<>();
        bcsRequirements = new ArrayList<>();
        bitRequirements = new ArrayList<>();
        bisRequirements = new ArrayList<>();
        eceRequirements = new ArrayList<>();
        bemRequirements = new ArrayList<>();
        biotechRequirements = new ArrayList<>();
        betRequirements = new ArrayList<>();
        actuarialScienceRequirementsm = new ArrayList<>();
        pharmacyRequirements = new ArrayList<>();
        bbsRequirements = new ArrayList<>();
        mbbsRequirements = new ArrayList<>();

        // List of programmes
        eligibleProgrammesList = new ArrayList<>();
        notEligibleProgrammesList = new ArrayList<>();

        initalizeProgrammesDescription();
        initializeListItem(); // After initialize, ListView will set adapter

        // Set the adapter to list view
        ArrayAdapter<String> eligibleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eligibleProgrammesList);
        ArrayAdapter<String> notEligibleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notEligibleProgrammesList);
        eligibleListView.setAdapter(eligibleAdapter);
        notEligibleListView.setAdapter(notEligibleAdapter);

        // Set Listener
        eligibleListView.setOnItemClickListener(this);
        notEligibleListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        eligibleIcon = menu.findItem(R.id.eligibleIcon);
        notEligibleIcon = menu.findItem(R.id.notEligibleIcon);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home: // for back arrow
            {
                this.finish();
            }
            break;
            case R.id.studentsAcademicQualification:
            {
                MaterialDialog materialDialog = new MaterialDialog.Builder(ResultsOfFiltering.this)
                        .title("Student's Academic Qualification Info")
                        .customView(R.layout.dialog_list_view, false)
                        .positiveText("OK")
                        .build();
                View view = materialDialog.getCustomView();

                //reference to list view in dialog
                ListView listView = view.findViewById(R.id.dialogListView);
                CustomAdapter customAdapter = new CustomAdapter(this, subjectsStringArray, gradesStringArray);
                listView.setAdapter(customAdapter);

                //reference to the qualification level and set text
                TextView resultTypeText = view.findViewById(R.id.resultTypeDialog);
                resultTypeText.setText(qualificationLevel);

                //reference to the vertical bar
                View topbar = view.findViewById(R.id.topBar);
                View bottomBar = view.findViewById(R.id.bottomBar);

                //reference to the second qualification level and set text
                TextView secondQualificationLevelText = view.findViewById(R.id.supportiveQualificationLevelText);
                TextView secondQualificationLevel = view.findViewById(R.id.supportiveQualificationLevel);
                TextView scienceTechnicalVocationalSubject = view.findViewById(R.id.scienceTechnicalVocationalSubject);
                TextView scienceTechnicalVocationalGrade = view.findViewById(R.id.scienceTechnicalVocationalGrade);
                TextView bahasaMalaysiaText = view.findViewById(R.id.bahasaMalaysiaText);
                TextView bahasaMalaysiaGrade = view.findViewById(R.id.bahasaMalaysiaGrade);
                TextView secondEngText = view.findViewById(R.id.secondEngText);
                TextView secondEngGrade = view.findViewById(R.id.englishGrade);
                TextView secondMathText = view.findViewById(R.id.secondMathText);
                TextView secondMathGrade = view.findViewById(R.id.mathGrade);
                TextView secondAddMathText = view.findViewById(R.id.secondAddMathText);
                TextView secondAddMathGrade = view.findViewById(R.id.addMathGrade);

                if(Objects.equals(qualificationLevel, "STPM")
                        || Objects.equals(qualificationLevel, "A-Level")
                        || Objects.equals(qualificationLevel, "STAM"))
                {
                    // Set the secondary qualification data
                    secondQualificationLevel.setText(extras.getString("STUDENT_SECONDARY_QUALIFICATION"));
                    scienceTechnicalVocationalSubject.setText(extras.getString("STUDENT_STV_SUBJECT"));
                    scienceTechnicalVocationalGrade.setText(extras.getString("STUDENT_STV_GRADE"));
                    bahasaMalaysiaGrade.setText(extras.getString("STUDENT_SECONDARY_BM"));
                    secondEngGrade.setText(extras.getString("STUDENT_SECONDARY_ENG"));
                    secondMathGrade.setText(extras.getString("STUDENT_SECONDARY_ENG"));
                    secondAddMathGrade.setText(extras.getString("STUDENT_SECONDARY_ADDMATH"));

                    materialDialog.getWindow().setLayout(1000, 1250);
                }
                else
                {
                    //textView
                    secondQualificationLevelText.setVisibility(View.GONE);
                    secondMathText.setVisibility(View.GONE);
                    secondEngText.setVisibility(View.GONE);
                    secondAddMathText.setVisibility(View.GONE);
                    bahasaMalaysiaText.setVisibility(View.GONE);
                    scienceTechnicalVocationalSubject.setVisibility(View.GONE);
                    //grade
                    secondQualificationLevel.setVisibility(View.GONE);
                    secondMathGrade.setVisibility(View.GONE);
                    secondEngGrade.setVisibility(View.GONE);
                    secondAddMathGrade.setVisibility(View.GONE);
                    bahasaMalaysiaGrade.setVisibility(View.GONE);
                    scienceTechnicalVocationalGrade.setVisibility(View.GONE);
                    topbar.setVisibility(View.GONE);
                    bottomBar.setVisibility(View.GONE);

                    materialDialog.getWindow().setLayout(1000, 800);
                }
                materialDialog.show();
                materialDialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            }
            break;
            // Switching between not eligible and eligible programme
            case R.id.eligibleIcon: // switch to eligible
            {
                eligibleTextView.setVisibility(View.VISIBLE);
                eligibleListView.setVisibility(View.VISIBLE);
                notEligibleTextView.setVisibility(View.GONE);
                notEligibleListView.setVisibility(View.GONE);
                eligibleIcon.setVisible(false);
                notEligibleIcon.setVisible(true);
            }
            break;
            case R.id.notEligibleIcon: // switch to not eligible
            {
                eligibleTextView.setVisibility(View.GONE);
                eligibleListView.setVisibility(View.GONE);
                notEligibleTextView.setVisibility(View.VISIBLE);
                notEligibleListView.setVisibility(View.VISIBLE);
                notEligibleIcon.setVisible(false);
                eligibleIcon.setVisible(true);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeListItem()
    {
        // If no interest programme, do checking for all programmes
        if(!isGotInterestedProgramme)
        {
            // For SPM and O-Level add programme
            if(Objects.equals(qualificationLevel, "SPM") || Objects.equals(qualificationLevel, "O-Level"))
            {
                addFoundationJoinProgramme();
                addDiplomaJoinProgramme();
            }

            // For STPM, A-Level, STAM add programme
            if(!Objects.equals(qualificationLevel, "SPM")
                    && !Objects.equals(qualificationLevel, "O-Level")
                    && !Objects.equals(qualificationLevel, "UEC"))
            {
                addDiplomaJoinProgramme();
                addDegreeJoinProgramme();
            }

            // For UEC add programme
            if(Objects.equals(qualificationLevel, "UEC"))
            {
                addFoundationJoinProgramme();
                addDiplomaJoinProgramme();
                addDegreeJoinProgramme();
            }
        }
        else // Got interested programme
        {
            enquiryInterestedProgramme(); // Add the programme name into List item
        }
    }

    private void enquiryInterestedProgramme()
    {
        for(int i = 0; i < interestedProgramme.length; i++)
        {
            switch(interestedProgramme[i])
            {
                // Add specific programme according to user interest
                // Foundation
                case "Foundation in Arts":
                {
                    if(FIA.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Foundation in Arts");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Foundation in Arts");
                    }
                }
                break;
                case "Foundation in Business":
                {
                    if(FIA.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Foundation in Business");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Foundation in Business");
                    }
                }
                break;
                case "Foundation in Sciences":
                {
                    if(FIS.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Foundation in Sciences");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Foundation in Sciences");
                    }
                }
                break;

                // Diploma
                case "Diploma in Business Management":
                {
                    if(DBM.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Business Management");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Business Management");
                    }
                }
                break;
                case "Diploma in Hotel Management":
                {
                    if(DHM.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Hotel Management");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Hotel Management");
                    }
                }
                break;
                case "Diploma of Accountancy":
                {
                    if(DAC.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma of Accountancy");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma of Accountancy");
                    }
                }
                break;
                case "Diploma in Early Childhood Education":
                {
                    if(DCE.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Early Childhood Education");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Early Childhood Education");
                    }
                }
                break;
                case "Diploma in Information Technology":
                {
                    if(DIT.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Information Technology");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Information Technology");
                    }
                }
                break;
                case "Diploma in Business Information System":
                {
                    if(DIS.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Business Information System");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Business Information System");
                    }
                }
                break;
                case "Diploma in Mechatronics Engineering":
                {
                    if(DME.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Mechatronics Engineering");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Mechatronics Engineering");
                    }
                }
                break;
                case "Diploma in Environmental Technology":
                {
                    if(DET.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Environmental Technology");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Environmental Technology");
                    }
                }
                break;
                case "Diploma in Culinary Arts":
                {
                    if(DCA.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Diploma in Culinary Arts");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Diploma in Culinary Arts");
                    }
                }
                break;

                //Degree
                case "Bachelor of Business Administration (Hons)":
                {
                    if(BBA.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Business Administration (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Business Administration (Hons)");
                    }
                }
                break;
                case "BBA (Hons) in Hospitality & Tourism Management":
                {
                    if(BHT.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("BBA (Hons) in Hospitality & Tourism Management");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("BBA (Hons) in Hospitality & Tourism Management");
                    }
                }
                break;
                case "Bachelor of Accountancy (Hons)":
                {
                    if(BAC.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Accountancy (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Accountancy (Hons)");
                    }
                }
                break;
                case "Bachelor of Finance (Hons)":
                {
                    if(BFI.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Finance (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Finance (Hons)");
                    }
                }
                break;
                case "Bachelor of Arts (Hons) TESL":
                {
                    if(TESL.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Arts (Hons) TESL");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Arts (Hons) TESL");
                    }
                }
                break;
                case "Bachelor of Corporate Communication (Hons)":
                {
                    if(CorporateComm.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Corporate Communication (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Corporate Communication (Hons)");
                    }
                }
                break;
                case "Bachelor of Mass Communication (Hons) Journalism":
                {
                    if(MassCommAdvertising.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Journalism");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Journalism");
                    }
                }
                break;
                case "Bachelor of Mass Communication (Hons) Advertising":
                {
                    if(MassCommAdvertising.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Advertising");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Advertising");
                    }
                }
                break;
                case "Bachelor of Early Childhood Education (Hons)":
                {
                    if(BCE.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Early Childhood Education (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Early Childhood Education (Hons)");
                    }
                }
                break;
                case "Bachelor of Special Needs Education (Hons)":
                {
                    if(BSNE.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Special Needs Education (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Special Needs Education (Hons)");
                    }
                }
                case "Bachelor of Computer Science (Hons)":
                {
                    if(BCS.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Computer Science (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Computer Science (Hons)");
                    }
                }
                break;
                case "Bachelor of Information Technology (Hons)":
                {
                    if(BIT.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Information Technology (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Information Technology (Hons)");
                    }
                }
                break;
                case "Bachelor of Business Information System (Hons)":
                {
                    if(BIS.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Business Information System (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Business Information System (Hons)");
                    }
                }
                break;
                case "Bachelor of Engineering (Hons) Electronics & Communications Engineering":
                {
                    if(ECE.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Engineering (Hons) Electronics & Communications Engineering");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Engineering (Hons) Electronics & Communications Engineering");
                    }
                }
                break;
                case "Bachelor of Engineering (Hons) in Mechatronics":
                {
                    if(BEM.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Engineering (Hons) in Mechatronics");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Engineering (Hons) in Mechatronics");
                    }
                }
                break;
                case "Bachelor of Science (Hons) in Biotechnology":
                {
                    if(BioTech.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Science (Hons) in Biotechnology");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Science (Hons) in Biotechnology");
                    }
                }
                break;
                case "Bachelor of Environmental Technology (Hons)":
                {
                    if(BET.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Environmental Technology (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Environmental Technology (Hons)");
                    }
                }
                break;
                case "Bachelor of Science (Hons) Actuarial Sciences":
                {
                    if(BS_ActuarialSciences.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Science (Hons) Actuarial Sciences");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Science (Hons) Actuarial Sciences");
                    }
                }
                break;
                case "Bachelor of Pharmacy (Hons)":
                {
                    if(Pharmacy.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Pharmacy (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Pharmacy (Hons)");
                    }
                }
                break;
                case "Bachelor of Biomedical Sciences (Hons)":
                {
                    if(BBS.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Biomedical Sciences (Hons)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Biomedical Sciences (Hons)");
                    }
                }
                break;
                case "Bachelor of Medicine & Bachelor of Surgery (MBBS)":
                {
                    if(MBBS.isJoinProgramme())
                    {
                        eligibleProgrammesList.add("Bachelor of Medicine & Bachelor of Surgery (MBBS)");
                    }
                    else
                    {
                        notEligibleProgrammesList.add("Bachelor of Medicine & Bachelor of Surgery (MBBS)");
                    }
                }
                break;
            }
        }
    }

    // For List View Listener to show Material Dialog for the requirements description
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        switch(adapterView.getId())
        {
            case R.id.eligibleProgrammesList:
            {
                String selectedProgrammeFromList =(eligibleListView.getItemAtPosition(position).toString());
                MaterialDialog materialDialog = new MaterialDialog.Builder(ResultsOfFiltering.this)
                        .title(selectedProgrammeFromList)
                        .customView(R.layout.dialog_programmes_list_description, false)
                        .positiveText("OK")
                        .build();
                View v = materialDialog.getCustomView();
                schorlarshipEligibility = v.findViewById(R.id.schorlarshipEligibility);

                // Reference to list view in dialog
                ListView listView = v.findViewById(R.id.programmesDescriptionList);

                // See which programme click, then show relavant requirements description
                switch(selectedProgrammeFromList)
                {
                    // Foundation
                    case "Foundation in Arts":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,fiaRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Foundation in Business":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,fibRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Foundation in Sciences":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,fisRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;

                    // Diploma
                    case "Diploma in Business Management":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dbmRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Hotel Management":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dhmRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma of Accountancy":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dacRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Early Childhood Education":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dceRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Information Technology":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,ditRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Business Information System":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,disRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Mechatronics Engineering":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dmeRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Environmental Technology":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,detRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList,
                                subjectsStringArray);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Culinary Arts":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dcaRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList,
                                subjectsStringArray);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;

                    //Degree
                    case "Bachelor of Business Administration (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bbaRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "BBA (Hons) in Hospitality & Tourism Management":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bbaHospitalityRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Accountancy (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bacRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Finance (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bfiRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Arts (Hons) TESL":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,teslRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Corporate Communication (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,corporateCommRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Mass Communication (Hons) Journalism":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bmcJournalismRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Mass Communication (Hons) Advertising":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bmcAdvertisingRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Early Childhood Education (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bceRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Special Needs Education (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bsneRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    case "Bachelor of Computer Science (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bcsRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Information Technology (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bitRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Business Information System (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bisRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Engineering (Hons) Electronics & Communications Engineering":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,eceRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Engineering (Hons) in Mechatronics":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bemRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Science (Hons) in Biotechnology":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,biotechRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Environmental Technology (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,betRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Science (Hons) Actuarial Sciences":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,actuarialScienceRequirementsm
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Pharmacy (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,pharmacyRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Biomedical Sciences (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bitRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Medicine & Bachelor of Surgery (MBBS)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,mbbsRequirements
                                ,qualificationLevel
                                ,true,
                                selectedProgrammeFromList);
                        setSchorlarshipEligibility(selectedProgrammeFromList);
                    }
                    break;
                }
                listView.setAdapter(descriptionAdapter);
                materialDialog.show();
                materialDialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                materialDialog.getWindow().setLayout(1000, 750);
            }
            break;
            case R.id.notEligibleProgrammesList:
            {
                String selectedProgrammeFromList =(notEligibleListView.getItemAtPosition(position).toString());
                MaterialDialog materialDialog = new MaterialDialog.Builder(ResultsOfFiltering.this)
                        .title(selectedProgrammeFromList)
                        .customView(R.layout.dialog_programmes_list_description, false)
                        .positiveText("OK")
                        .build();
                View v = materialDialog.getCustomView();

                // Reference to list view in dialog
                ListView listView = v.findViewById(R.id.programmesDescriptionList);

                // See which programme click, then show relavant requirements description
                switch(selectedProgrammeFromList)
                {
                    // Foundation
                    case "Foundation in Arts":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,fiaRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Foundation in Business":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,fibRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Foundation in Sciences":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,fisRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;

                    // Diploma
                    case "Diploma in Business Management":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dbmRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Hotel Management":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dhmRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma of Accountancy":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dacRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Early Childhood Education":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dceRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Information Technology":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,ditRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Business Information System":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,disRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Mechatronics Engineering":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,dmeRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Diploma in Environmental Technology":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,detRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList,
                                subjectsStringArray);
                    }
                    break;

                    //Degree
                    case "Bachelor of Business Administration (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bbaRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "BBA (Hons) in Hospitality & Tourism Management":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bbaHospitalityRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Accountancy (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bacRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Finance (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bfiRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Arts (Hons) TESL":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,teslRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Corporate Communication (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,corporateCommRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Mass Communication (Hons) Journalism":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bmcJournalismRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Mass Communication (Hons) Advertising":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bmcAdvertisingRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Early Childhood Education (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bceRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Special Needs Education (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bsneRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    case "Bachelor of Computer Science (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bcsRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Information Technology (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bitRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Business Information System (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bisRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Engineering (Hons) Electronics & Communications Engineering":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,eceRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Engineering (Hons) in Mechatronics":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bemRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Science (Hons) in Biotechnology":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,biotechRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Environmental Technology (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,betRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Science (Hons) Actuarial Sciences":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,actuarialScienceRequirementsm
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Pharmacy (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,pharmacyRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Biomedical Sciences (Hons)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,bitRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                    case "Bachelor of Medicine & Bachelor of Surgery (MBBS)":
                    {
                        descriptionAdapter = new DescriptionAdapter(this
                                ,mbbsRequirements
                                ,qualificationLevel
                                ,false,
                                selectedProgrammeFromList);
                    }
                    break;
                }
                listView.setAdapter(descriptionAdapter);
                materialDialog.show();
                materialDialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                materialDialog.getWindow().setLayout(1000, 750);
            }
            break;
        }
    }

    private void initalizeProgrammesDescription()
    {
        //Foundation requirements description
        fiaRequirements.add("SPM / O-Level \n Minimum 5 credits in any subjects");
        fiaRequirements.add("United Examination Certificate (UEC) \n - Minimum Grade B in at least 3 subjects");

        fibRequirements.add("SPM / O-Level \n Minimum 5 credits in any subjects");
        fibRequirements.add("United Examination Certificate (UEC) \n - Minimum Grade B in at least 3 subjects");

        fisRequirements.add("SPM / O-Level \n "
                + "- Minimum 5 credits including Mathematics, two Sciences subjects and any other two subjects \n "
                + "- Pass in Bahasa Malaysia and English Language ");
        fisRequirements.add("UEC \n "
                + "- Minimum B4 in 3 subjects : \n "
                + " - Biology and Chemistry \n "
                + " - Physics or Mathematics or Advanced Maths");

        // Diploma requirements description
        dbmRequirements.add("SPM / O-Level \n - Minimum 3 credits in any subjects");
        dbmRequirements.add("STPM / A-Level \n - Minimum Grade C(GP 2.0) in any 1 subject");
        dbmRequirements.add("UEC \n - Minimum Grade B in any 3 subjects");
        dbmRequirements.add("STAM \n - Minimum Grade of Maqbul in any 1 subject");

        dhmRequirements.add("SPM / O-Level \n - Minimum 3 credits in any subjects");
        dhmRequirements.add("STPM / A-Level \n - Minimum Grade C(GP 2.0) in any 1 subject");
        dhmRequirements.add("UEC \n - Minimum Grade B in any 3 subjects");
        dhmRequirements.add("STAM \n - Minimum Grade of Maqbul in any 1 subject");

        dacRequirements.add("SPM / O-Level \n "
                + "- Minimum 3 credits in any subjects \n "
                + "- Including credit in Mathematics and pass in English");
        dacRequirements.add("STPM / A-Level \n "
                + "- Minimum Grade C(GP 2.0) in any 1 subject \n "
                + "- Credit in Mathematics and pass in English at SPM / O-Level");
        dacRequirements.add("UEC \n "
                + "- Minimum Grade B in any 3 subjects \n "
                + "- Including Grade B in Mathematics and pass in English");
        dacRequirements.add("STAM \n "
                + "- Minimum Grade of Maqbul in any 1 subject \n "
                + "- Credit in Mathematics and pass in English at SPM / O-level");

        dceRequirements.add("SPM / O-Level \n - Minimum 3 credits in any subjects");
        dceRequirements.add("STPM / A-Level \n - Minimum Grade C(GP 2.0) in any 1 subject");
        dceRequirements.add("UEC \n - Minimum Grade B in any 3 subjects");
        dceRequirements.add("STAM \n - Minimum Grade of Maqbul in any 1 subject");

        ditRequirements.add("SPM / O-Level \n - Minimum 3 credits in any subjects including Mathematics");
        ditRequirements.add("STPM / A-Level \n "
                + "- Minimum Grade C(GP 2.0) in any 1 subject \n "
                + "- Credit in Mathematics and pass in English at SPM / O-Level");
        ditRequirements.add("UEC \n - Minimum Grade B in any 3 subjects including Mathematics");
        ditRequirements.add("STAM \n "
                + "- Minimum Grade of Maqbul in any 1 subject \n "
                + "- Credit in Mathematics");

        disRequirements.add("SPM / O-Level \n - Minimum 3 credits in any subjects including Mathematics");
        disRequirements.add("STPM / A-Level \n "
                + "- Minimum Grade C(GP 2.0) in any 1 subject \n "
                + "- Credit in Mathematics and pass in English at SPM / O-Level");
        disRequirements.add("UEC \n - Minimum Grade B in any 3 subjects including Mathematics");
        disRequirements.add("STAM \n "
                + "- Minimum Grade of Maqbul in any 1 subject \n "
                + "- Credit in Mathematics");

        dmeRequirements.add("SPM / O-Level \n "
                + "- Minimum 3 credits in any subjects \n "
                + "- Including credit in Mathematics and 1 subject from Science/Technical/Vocational \n"
                + "- Pass in English");
        dmeRequirements.add("STPM / A-Level \n "
                + "- Minimum Grade C(GP 2.0) in any 1 subject \n "
                + "- Pass in Mathematics, English and any 1 Science/Technical/Vocational subject at SPM / O-Level");
        dmeRequirements.add("UEC \n "
                + "- Minimum Grade B in any 3 subjects \n "
                + "- Including Mathematics and any 1 Science/Technical/Vocational subject at SPM / O-Level \n "
                + "- Pass in English");

        detRequirements.add("Science stream student \n "
                + "- SPM / O-Level \n "
                + "  - At least 3 credits in any subjects \n "
                + "  - Pass in 2 Science subjects \n "
                + "- UEC \n "
                + "  - Minimum Grade B in any 3 subjects \n "
                + "  - Including 2 Science subjects");
        detRequirements.add("Non-Science stream student \n "
                + "- SPM / O-Level \n "
                + "  - Minimum 3 credits in any subjects \n "
                + "  - Including credit in 1 science subject \n "
                + "- UEC \n "
                + "  - Minimum Grade B in any 3 subjects \n "
                + "  - Including 1 Science subjects \n "
                + "- STPM \n "
                + "  - Minimum Grade C(GP 2.0) in any 1 subject \n "
                + "- A-Level \n "
                + "  - Minimum Grade C(GP 2.0) in any 1 subject \n "
                + "- STAM \n "
                + "  - Minimum Grade of Maqbul in any 1 subject");

        dcaRequirements.add("SPM / O-Level \n "
                + "- Minimum 3 credits in any subjects");
        dcaRequirements.add("UEC \n "
                + "- Minimum Grade B in any 3 subjects");

        // Degree requirements description
        bbaRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects \n "
                + "- Pass in Mathematics and English at SPM / O-Level / STPM");
        bbaRequirements.add("A-Level \n "
                + "- Minimum pass in 2 subjects \n "
                + "- Pass in Mathematics and English at SPM / O-Level / A-Level");
        bbaRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects \n "
                + "- Pass Mathematics and English");
        bbaRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Pass in Mathematics and English at SPM / O-Level / STAM");

        bbaHospitalityRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects");
        bbaHospitalityRequirements.add("A-Level \n "
                + "- Minimum pass in 2 subjects");
        bbaHospitalityRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects");
        bbaHospitalityRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject");

        bacRequirements.add("STPM \n "
                + "- Minimum Grade C+ (GP 2.33) in any 2 subjects  \n "
                + "- Credit in Mathematics at SPM / O-Level / STPM");
        bacRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level / A Level");
        bacRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including Mathematics");
        bacRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Credit in Mathematics at SPM / O-Level / STAM ");

        bfiRequirements.add("STPM \n "
                + "- Minimum Grade C+ (GP 2.33) in any 2 subjects \n "
                + "- Credit in Mathematics and Pass in English at SPM / O-Level / STPM");
        bfiRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 subjects \n "
                + "- Credit in Mathematics and Pass in English at SPM / O-Level / A-Level");
        bfiRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects "
                + "- Including credit in Mathematics and pass in English");
        bfiRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Credit in Mathematics and Pass in English at SPM / O-Level / STAM");

        teslRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects  \n "
                + "- Pass in English at SPM / O-Level");
        teslRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 subjects \n "
                + "- Pass in English at SPM / O-Level");
        teslRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects \n "
                + "- Pass in English");
        teslRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Pass in English at SPM / O-Level");

        corporateCommRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects  \n "
                + "- Credit in English at SPM / O-Level");
        corporateCommRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 subjects \n "
                + "- Pass in English at SPM / O-Level");
        corporateCommRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including English");
        corporateCommRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Credit in English at SPM / O-Level");

        bmcAdvertisingRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects  \n "
                + "- Credit in English at SPM / O-Level");
        bmcAdvertisingRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 subjects \n "
                + "- Pass in English at SPM / O-Level");
        bmcAdvertisingRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including English");
        bmcAdvertisingRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Credit in English at SPM / O-Level");

        bmcJournalismRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects  \n "
                + "- Credit in English at SPM / O-Level");
        bmcJournalismRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 subjects \n "
                + "- Pass in English at SPM / O-Level");
        bmcJournalismRequirements.add("UEC "
                + "- Minimum Grade B in 5 subjects including English");
        bmcJournalismRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject \n "
                + "- Credit in English at SPM / O-Level");

        bceRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects");
        bceRequirements.add("A-Level \n "
                + "- Minimum pass in any 2 subjects");
        bceRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects");
        bceRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject");

        bsneRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.00) in any 2 subjects");
        bsneRequirements.add("A-Level \n "
                + "- Minimum pass in any 2 subjects");
        bsneRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects");
        bsneRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in any 1 subject");

        bcsRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.0) in two (2) subjects \n "
                + "- Credit in Additional Mathematics at SPM / O-Level / STPM");
        bcsRequirements.add("A-Level \n "
                + "- Minimum passes in 2 subjects \n "
                + "- Credit in Additional Mathematics at SPM / O-Level / A-Level");
        bcsRequirements.add("UEC \n "
                + "- Minimum Grade B in five (5) subjects including Additional Mathematics");

        bitRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.0) in any 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level / STPM");
        bitRequirements.add("A-Level \n "
                + "- Minimum pass in 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level / STPM");
        bitRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including Mathematics");

        bisRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.0) in any 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level");
        bisRequirements.add("A-Level \n "
                + "- Minimum pass in 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level");
        bisRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including Mathematics ");

        eceRequirements.add("STPM \n "
                + "- Minimum Grade C in Mathematics and Physics");
        eceRequirements.add("A-Level \n "
                + "- Minimum Grade C in Mathematics and Physics");
        eceRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including Mathematics and Physic");

        bemRequirements.add("STPM \n "
                + "- Minimum Grade C in 3 subjects including Mathematics & Physics");
        bemRequirements.add("A-Level \n "
                + "- Minimum Grade C in 3 subjects including Mathematics & Physics");
        bemRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including Mathematics and Physic");

        biotechRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.0) in 2 Science subjects");
        biotechRequirements.add("A-Level \n "
                + "- Minimum Grade C (GP 2.0) in 2 Science subjects");
        biotechRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects");

        betRequirements.add("STPM \n "
                + "- Minimum Grade C(GP 2.0) in 2 subjects \n "
                + "- Credit in Mathematics and any Science subject at SPM / O-Level");
        betRequirements.add("A-Level \n "
                + "- Minimum Grade C in 2 subjects "
                + "- Including Mathematics and any Science subject");
        betRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects "
                + "- Including Mathematics and any Science subject");
        betRequirements.add("STAM \n "
                + "- Minimum Grade of Jayyid in two 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level");

        actuarialScienceRequirementsm.add("STPM \n "
                + "- Minimum Grade C (GP 2.0) in 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level");
        actuarialScienceRequirementsm.add("A-Level \n "
                + "- Minimum Grade C in 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level");
        actuarialScienceRequirementsm.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including Mathematics");
        actuarialScienceRequirementsm.add("STAM \n "
                + "- Minimum Grade of Jayyid in 2 subjects \n "
                + "- Credit in Mathematics at SPM / O-Level");

        bbsRequirements.add("STPM \n "
                + "- Minimum Grade C (GP 2.33) in 2 of the following subjects \n "
                + " - Biology \n "
                + " - Chemistry \n "
                + " - Physics or Mathematics");
        bbsRequirements.add("A-Level \n "
                + "- Minimum Grade D in 2 of the following subjects \n "
                + " - Biology \n "
                + " - Chemistry \n "
                + " - Physics or Mathematics");
        bbsRequirements.add("UEC \n "
                + "- Minimum Grade B in 5 subjects including \n "
                + " - Biology \n "
                + " - Chemistry \n "
                + " - Physics or Mathematics");

        mbbsRequirements.add("STPM \n "
                + "- Minimum Grades B in Biology \n "
                + "- Minimum Grade B in Chemistry \n "
                + "- Minimum Grade C Physics or Mathematics");
        mbbsRequirements.add("A-Level \n "
                + "- Minimum Grades B in Biology \n "
                + "- Minimum Grade B in Chemistry \n "
                + "- Minimum Grade C Physics or Mathematics");
        mbbsRequirements.add("UEC \n "
                + "- Minimum B4 in following 5 subjects \n "
                + " - Biology \n "
                + " - Chemistry \n "
                + " - Physics \n "
                + " - Mathematics \n "
                + " - Additional Mathematics");

        pharmacyRequirements.add("STPM \n "
                + "- Minimum Grades B in Biology \n "
                + "- Minimum Grade B in Chemistry \n "
                + "- Minimum Grade C Physics or Mathematics");
        pharmacyRequirements.add("A-Level \n "
                + "- Minimum Grades B in Biology \n "
                + "- Minimum Grade B in Chemistry \n "
                + "- Minimum Grade C Physics or Mathematics");
        pharmacyRequirements.add("UEC \n "
                + "- Minimum B4 in following 5 subjects \n "
                + " - Biology \n "
                + " - Chemistry \n "
                + " - Physics \n "
                + " - Mathematics \n "
                + " - Additional Mathematics");
    }

    private void addFoundationJoinProgramme()
    {
        if(FIA.isJoinProgramme())
        {
            eligibleProgrammesList.add("Foundation in Arts");
        }
        else
        {
            notEligibleProgrammesList.add("Foundation in Arts");
        }
        if(FIB.isJoinProgramme())
        {
            eligibleProgrammesList.add("Foundation in Business");
        }
        else
        {
            notEligibleProgrammesList.add("Foundation in Business");
        }
        if(FIS.isJoinProgramme())
        {
            eligibleProgrammesList.add("Foundation in Sciences");
        }
        else
        {
            notEligibleProgrammesList.add("Foundation in Sciences");
        }
    }

    private void addDiplomaJoinProgramme()
    {
        if(DBM.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Business Management");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Business Management");
        }

        if(DHM.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Hotel Management");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Hotel Management");
        }

        if(DAC.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma of Accountancy");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma of Accountancy");
        }

        if(DCE.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Early Childhood Education");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Early Childhood Education");
        }

        if(DIT.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Information Technology");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Information Technology");
        }

        if(DIS.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Business Information System");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Business Information System");
        }

        if(DME.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Mechatronics Engineering");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Mechatronics Engineering");
        }

        if(DET.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Environmental Technology");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Environmental Technology");
        }
        if(DCA.isJoinProgramme())
        {
            eligibleProgrammesList.add("Diploma in Culinary Arts");
        }
        else
        {
            notEligibleProgrammesList.add("Diploma in Culinary Arts");
        }
    }

    private void addDegreeJoinProgramme()
    {
        // Faculty of Business, management & Social Sciences
        if(BBA.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Business Administration (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Business Administration (Hons)");
        }

        if(BHT.isJoinProgramme())
        {
            eligibleProgrammesList.add("BBA (Hons) in Hospitality & Tourism Management");
        }
        else
        {
            notEligibleProgrammesList.add("BBA (Hons) in Hospitality & Tourism Management");
        }

        if(BAC.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Accountancy (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Accountancy (Hons)");
        }

        if(BFI.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Finance (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Finance (Hons)");
        }

        if(TESL.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Arts (Hons) TESL");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Arts (Hons) TESL");
        }

        if(CorporateComm.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Corporate Communication (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Corporate Communication (Hons)");
        }

        if(MassCommAdvertising.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Journalism");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Journalism");
        }

        if(MassCommAdvertising.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Advertising");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Mass Communication (Hons) Advertising");
        }

        if(BCE.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Early Childhood Education (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Early Childhood Education (Hons)");
        }

        if(BSNE.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Special Needs Education (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Special Needs Education (Hons)");
        }

        //Faculty of Integrative Sciences & Technology
        if(BCS.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Computer Science (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Computer Science (Hons)");
        }

        if(BIT.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Information Technology (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Information Technology (Hons)");
        }

        if(BIS.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Business Information System (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Business Information System (Hons)");
        }

        if(ECE.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Engineering (Hons) Electronics & Communications Engineering");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Engineering (Hons) Electronics & Communications Engineering");
        }

        if(BEM.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Engineering (Hons) in Mechatronics");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Engineering (Hons) in Mechatronics");
        }

        if(BioTech.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Science (Hons) in Biotechnology");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Science (Hons) in Biotechnology");
        }

        if(BS_ActuarialSciences.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Science (Hons) Actuarial Sciences");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Science (Hons) Actuarial Sciences");
        }

        //Faculty of Medicine
        if(MBBS.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Medicine & Bachelor of Surgery (MBBS)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Medicine & Bachelor of Surgery (MBBS)");
        }

        if(BBS.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Biomedical Sciences (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Biomedical Sciences (Hons)");
        }

        //Faculty of Pharmacy
        if(Pharmacy.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Pharmacy (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Pharmacy (Hons)");
        }

        if(BET.isJoinProgramme())
        {
            eligibleProgrammesList.add("Bachelor of Environmental Technology (Hons)");
        }
        else
        {
            notEligibleProgrammesList.add("Bachelor of Environmental Technology (Hons)");
        }
    }

    private void setSchorlarshipEligibility(String selectedProgrammeFromList)
    {
        int schorlarshipCount = 0;
        boolean gotSchorlarship = true;

        // SPM / O-Level Foundation to Diploma
        if(Objects.equals(qualificationLevel, "SPM"))
        {
            for(int i = 0; i < gradesIntegerArray.length; i++)
            {
                if(gradesIntegerArray[i] <= 2)
                {
                    schorlarshipCount++;
                }
            }

            if(schorlarshipCount >= 6)
            {
                if(selectedProgrammeFromList.contains("Foundation"))
                {
                    schorlarshipEligibility.setText("You are eligible for 100% schorlarship of "+selectedProgrammeFromList);
                }
                else if(selectedProgrammeFromList.contains("Diploma"))
                {
                    schorlarshipEligibility.setText("You are eligible for 50% schorlarship of "+selectedProgrammeFromList);
                }
            }
            else if(schorlarshipCount >= 3)
            {
                if(selectedProgrammeFromList.contains("Foundation"))
                {
                    schorlarshipEligibility.setText("You are eligible for 50% schorlarship of "+selectedProgrammeFromList);
                }
                else if(selectedProgrammeFromList.contains("Diploma"))
                {
                    schorlarshipEligibility.setText("You are eligible for 25% schorlarship of "+selectedProgrammeFromList);
                }
            }
            else
            {
                schorlarshipEligibility.setText("Sorry. You are not eligible for schorlarship");
                gotSchorlarship = false;
            }
        }
        else if(Objects.equals(qualificationLevel, "O-Level"))
        {
            for(int i = 0; i < gradesIntegerArray.length; i++)
            {
                if(gradesIntegerArray[i] == 1)
                {
                    schorlarshipCount++;
                }
            }

            if(schorlarshipCount >= 3)
            {
                if(selectedProgrammeFromList.contains("Foundation"))
                {
                    schorlarshipEligibility.setText("You are eligible for 50% schorlarship of "+selectedProgrammeFromList);
                }
                else if(selectedProgrammeFromList.contains("Diploma"))
                {
                    schorlarshipEligibility.setText("You are eligible for 25% schorlarship of "+selectedProgrammeFromList);
                }
            }
            else if(schorlarshipCount >= 6)
            {
                if(selectedProgrammeFromList.contains("Foundation"))
                {
                    schorlarshipEligibility.setText("You are eligible for 100% schorlarship of "+selectedProgrammeFromList);
                }
                else if(selectedProgrammeFromList.contains("Diploma"))
                {
                    schorlarshipEligibility.setText("You are eligible for 50% schorlarship of "+selectedProgrammeFromList);
                }
            }
            else
            {
                schorlarshipEligibility.setText("Sorry. You are not eligible for schorlarship");
                gotSchorlarship = false;
            }
        }
        else if(Objects.equals(qualificationLevel, "STPM")) // For STPM / A-level for Degree Programme
        {
            for(int i = 0; i < gradesIntegerArray.length; i++)
            {
                if(gradesIntegerArray[i] <= 4)
                {
                    schorlarshipCount++;
                }
            }

            if(schorlarshipCount >= 3)
            {
                schorlarshipEligibility.setText("You are eligible for schorlarship of "+selectedProgrammeFromList);
            }
            else
            {
                schorlarshipEligibility.setText("Sorry. You are not eligible for schorlarship");
                gotSchorlarship = false;
            }
        }
        else if(Objects.equals(qualificationLevel, "A-Level"))
        {
            for(int i = 0; i < gradesIntegerArray.length; i++)
            {
                if(gradesIntegerArray[i] <= 3)
                {
                    schorlarshipCount++;
                }
            }

            if(schorlarshipCount >= 3)
            {
                schorlarshipEligibility.setText("You are eligible for schorlarship of "+selectedProgrammeFromList);
            }
            else
            {
                schorlarshipEligibility.setText("Sorry. You are not eligible for schorlarship");
                gotSchorlarship = false;
            }
        }
        else if(Objects.equals(qualificationLevel, "UEC"))
        {
            for(int i = 0; i < gradesIntegerArray.length; i++)
            {
                if(gradesIntegerArray[i] <= 6)
                {
                    schorlarshipCount++;
                }
            }

            if(schorlarshipCount >= 5)
            {
                schorlarshipEligibility.setText("You are eligible for schorlarship of "+selectedProgrammeFromList);
            }
            else
            {
                schorlarshipEligibility.setText("Sorry. You are not eligible for schorlarship");
                gotSchorlarship = false;
            }
        }

        if(gotSchorlarship)
        {
            schorlarshipEligibility.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        schorlarshipEligibility.setVisibility(View.VISIBLE);
    }
}
