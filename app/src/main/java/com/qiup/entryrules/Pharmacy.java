package com.qiup.entryrules;

import android.util.Log;

import com.qiup.POJO.RulePojo;
import com.qiup.programmeenquiry.MyContext;
import com.qiup.programmeenquiry.R;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.util.Arrays;
import java.util.Objects;

@Rule(name = "Pharmacy", description = "Entry rule to join Bachelor of Pharmacy")
public class Pharmacy
{
    private static RuleAttribute pharmacyRuleAttribute;

    public Pharmacy() { pharmacyRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (pharmacyRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < pharmacyRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], pharmacyRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= pharmacyRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            pharmacyRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", pharmacyRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= pharmacyRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    pharmacyRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", pharmacyRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(pharmacyRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= pharmacyRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                pharmacyRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(pharmacyRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < pharmacyRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(pharmacyRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= pharmacyRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= pharmacyRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= pharmacyRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= pharmacyRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= pharmacyRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(pharmacyRuleAttribute.isExempted())
        {
            for(int i = 0; i < pharmacyRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(pharmacyRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(pharmacyRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 6) // a-level pass grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 7) // stpm credit grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 4) // a-level credit grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(pharmacyRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 6) // a-level pass grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 7) // stpm credit grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 4) // a-level credit grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(pharmacyRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 6) // a-level pass grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 7) // stpm credit grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 4) // a-level credit grade
                                        {
                                            pharmacyRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= pharmacyRuleAttribute.getMinimumCreditGrade())
                pharmacyRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (pharmacyRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(pharmacyRuleAttribute.getCountCorrectSubjectRequired() >= pharmacyRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(pharmacyRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(pharmacyRuleAttribute.getCountSupportiveSubjectRequired() >= pharmacyRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(pharmacyRuleAttribute.getCountCredit() >= pharmacyRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(pharmacyRuleAttribute.getCountCredit() >= pharmacyRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(pharmacyRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(pharmacyRuleAttribute.getCountSupportiveSubjectRequired() >= pharmacyRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(pharmacyRuleAttribute.getCountCredit() >= pharmacyRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(pharmacyRuleAttribute.getCountCredit() >= pharmacyRuleAttribute.getAmountOfCreditRequired())
                {
                    return true; // return true as requirements is satisfied
                }
            }
        }
        // Return false as requirements not satisfied
        return false;
    }

    @Action
    public void joinProgramme() throws Exception {
        // If rule is satisfied (return true), this action will be executed
        pharmacyRuleAttribute.setJoinProgrammeTrue();
        Log.d("Pharmacy", "Joined");
    }

    public static boolean isJoinProgramme() { return pharmacyRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                pharmacyRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getUEC().getAmountOfCreditRequired());
                pharmacyRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getUEC().getMinimumCreditGrade());
                pharmacyRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getUEC().isGotRequiredSubject());

                if(pharmacyRuleAttribute.isGotRequiredSubject()) {
                    pharmacyRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getUEC().getWhatSubjectRequired().getSubject());
                    pharmacyRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    pharmacyRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    pharmacyRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                pharmacyRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getAmountOfCreditRequired());
                pharmacyRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getMinimumCreditGrade());
                pharmacyRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().isGotRequiredSubject());
                pharmacyRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().isExempted());

                if(pharmacyRuleAttribute.isGotRequiredSubject()) {
                    pharmacyRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getWhatSubjectRequired().getSubject());
                    pharmacyRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    pharmacyRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                pharmacyRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().isNeedSupportiveQualification());
                if(pharmacyRuleAttribute.isNeedSupportiveQualification()) {
                    pharmacyRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getWhatSupportiveSubject().getSubject());
                    pharmacyRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getWhatSupportiveGrade().getGrade());
                    pharmacyRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    pharmacyRuleAttribute.initializeIntegerSupportiveGrade();
                    pharmacyRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, pharmacyRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                pharmacyRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getAmountOfCreditRequired());
                pharmacyRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getMinimumCreditGrade());
                pharmacyRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().isGotRequiredSubject());
                pharmacyRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().isExempted());

                if(pharmacyRuleAttribute.isGotRequiredSubject()) {
                    pharmacyRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getWhatSubjectRequired().getSubject());
                    pharmacyRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    pharmacyRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                pharmacyRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().isNeedSupportiveQualification());
                if(pharmacyRuleAttribute.isNeedSupportiveQualification()) {
                    pharmacyRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getWhatSupportiveSubject().getSubject());
                    pharmacyRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    pharmacyRuleAttribute.initializeIntegerSupportiveGrade();
                    pharmacyRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, pharmacyRuleAttribute.getSupportiveGradeRequired());
                    pharmacyRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getPharmacy().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
