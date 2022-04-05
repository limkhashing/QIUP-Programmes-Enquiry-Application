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

@Rule(name = "MassCommAdvertising", description = "Entry rule to join Bachelor of Mass Communication Advertising")
public class MassCommAdvertising
{
    private static RuleAttribute advertisingRuleAttribute;

    public MassCommAdvertising() { advertisingRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (advertisingRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < advertisingRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], advertisingRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= advertisingRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            advertisingRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", advertisingRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= advertisingRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    advertisingRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", advertisingRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(advertisingRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= advertisingRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                advertisingRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(advertisingRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < advertisingRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(advertisingRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= advertisingRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= advertisingRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= advertisingRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= advertisingRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= advertisingRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(advertisingRuleAttribute.isExempted())
        {
            for(int i = 0; i < advertisingRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(advertisingRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(advertisingRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(advertisingRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(advertisingRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            advertisingRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= advertisingRuleAttribute.getMinimumCreditGrade())
                advertisingRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (advertisingRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(advertisingRuleAttribute.getCountCorrectSubjectRequired() >= advertisingRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(advertisingRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(advertisingRuleAttribute.getCountSupportiveSubjectRequired() >= advertisingRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(advertisingRuleAttribute.getCountCredit() >= advertisingRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(advertisingRuleAttribute.getCountCredit() >= advertisingRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(advertisingRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(advertisingRuleAttribute.getCountSupportiveSubjectRequired() >= advertisingRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(advertisingRuleAttribute.getCountCredit() >= advertisingRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(advertisingRuleAttribute.getCountCredit() >= advertisingRuleAttribute.getAmountOfCreditRequired())
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
        advertisingRuleAttribute.setJoinProgrammeTrue();
        Log.d("MassCommAdvertising", "Joined");
    }

    public static boolean isJoinProgramme() { return advertisingRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                advertisingRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getUEC().getAmountOfCreditRequired());
                advertisingRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getUEC().getMinimumCreditGrade());
                advertisingRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getUEC().isGotRequiredSubject());

                if(advertisingRuleAttribute.isGotRequiredSubject()) {
                    advertisingRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getUEC().getWhatSubjectRequired().getSubject());
                    advertisingRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    advertisingRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    advertisingRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                advertisingRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getAmountOfCreditRequired());
                advertisingRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getMinimumCreditGrade());
                advertisingRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().isGotRequiredSubject());
                advertisingRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().isExempted());

                if(advertisingRuleAttribute.isGotRequiredSubject()) {
                    advertisingRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getWhatSubjectRequired().getSubject());
                    advertisingRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    advertisingRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                advertisingRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().isNeedSupportiveQualification());
                if(advertisingRuleAttribute.isNeedSupportiveQualification()) {
                    advertisingRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getWhatSupportiveSubject().getSubject());
                    advertisingRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getWhatSupportiveGrade().getGrade());
                    advertisingRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    advertisingRuleAttribute.initializeIntegerSupportiveGrade();
                    advertisingRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, advertisingRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                advertisingRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getAmountOfCreditRequired());
                advertisingRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getMinimumCreditGrade());
                advertisingRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().isGotRequiredSubject());
                advertisingRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().isExempted());

                if(advertisingRuleAttribute.isGotRequiredSubject()) {
                    advertisingRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getWhatSubjectRequired().getSubject());
                    advertisingRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    advertisingRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                advertisingRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().isNeedSupportiveQualification());
                if(advertisingRuleAttribute.isNeedSupportiveQualification()) {
                    advertisingRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getWhatSupportiveSubject().getSubject());
                    advertisingRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    advertisingRuleAttribute.initializeIntegerSupportiveGrade();
                    advertisingRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, advertisingRuleAttribute.getSupportiveGradeRequired());
                    advertisingRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                advertisingRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getAmountOfCreditRequired());
                advertisingRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getMinimumCreditGrade());
                advertisingRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().isGotRequiredSubject());
                advertisingRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().isExempted());

                if(advertisingRuleAttribute.isGotRequiredSubject()) {
                    advertisingRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getWhatSubjectRequired().getSubject());
                    advertisingRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    advertisingRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                advertisingRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().isNeedSupportiveQualification());
                if(advertisingRuleAttribute.isNeedSupportiveQualification()) {
                    advertisingRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getWhatSupportiveSubject().getSubject());
                    advertisingRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    advertisingRuleAttribute.initializeIntegerSupportiveGrade();
                    advertisingRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, advertisingRuleAttribute.getSupportiveGradeRequired());
                    advertisingRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommAdvertising().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }

}
