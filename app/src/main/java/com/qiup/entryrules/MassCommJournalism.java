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

@Rule(name = "MassCommJournalism", description = "Entry rule to join Bachelor of Mass Communication Journalism")
public class MassCommJournalism
{
    private static RuleAttribute journalismRuleAttribute;

    public MassCommJournalism() { journalismRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (journalismRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < journalismRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], journalismRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= journalismRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            journalismRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", journalismRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= journalismRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    journalismRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", journalismRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(journalismRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= journalismRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                journalismRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(journalismRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < journalismRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(journalismRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= journalismRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= journalismRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= journalismRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= journalismRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= journalismRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(journalismRuleAttribute.isExempted())
        {
            for(int i = 0; i < journalismRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(journalismRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(journalismRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(journalismRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(journalismRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            journalismRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= journalismRuleAttribute.getMinimumCreditGrade())
                journalismRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (journalismRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(journalismRuleAttribute.getCountCorrectSubjectRequired() >= journalismRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(journalismRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(journalismRuleAttribute.getCountSupportiveSubjectRequired() >= journalismRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(journalismRuleAttribute.getCountCredit() >= journalismRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(journalismRuleAttribute.getCountCredit() >= journalismRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(journalismRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(journalismRuleAttribute.getCountSupportiveSubjectRequired() >= journalismRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(journalismRuleAttribute.getCountCredit() >= journalismRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(journalismRuleAttribute.getCountCredit() >= journalismRuleAttribute.getAmountOfCreditRequired())
                {
                    return true; // return true as requirements is satisfied
                }
            }
        }

        // Return false as requirements not satisfied
        return false;
    }
    
    @Action
    public void joinProgramme() throws Exception  {
        // If rule is satisfied (return true), this action will be executed
        journalismRuleAttribute.setJoinProgrammeTrue();
        Log.d("MassCommJournalism", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return journalismRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                journalismRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getUEC().getAmountOfCreditRequired());
                journalismRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getUEC().getMinimumCreditGrade());
                journalismRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getUEC().isGotRequiredSubject());

                if(journalismRuleAttribute.isGotRequiredSubject()) {
                    journalismRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getUEC().getWhatSubjectRequired().getSubject());
                    journalismRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    journalismRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    journalismRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                journalismRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getAmountOfCreditRequired());
                journalismRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getMinimumCreditGrade());
                journalismRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().isGotRequiredSubject());
                journalismRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().isExempted());

                if(journalismRuleAttribute.isGotRequiredSubject()) {
                    journalismRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getWhatSubjectRequired().getSubject());
                    journalismRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    journalismRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                journalismRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().isNeedSupportiveQualification());
                if(journalismRuleAttribute.isNeedSupportiveQualification()) {
                    journalismRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getWhatSupportiveSubject().getSubject());
                    journalismRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getWhatSupportiveGrade().getGrade());
                    journalismRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    journalismRuleAttribute.initializeIntegerSupportiveGrade();
                    journalismRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, journalismRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                journalismRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getAmountOfCreditRequired());
                journalismRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getMinimumCreditGrade());
                journalismRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().isGotRequiredSubject());
                journalismRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().isExempted());

                if(journalismRuleAttribute.isGotRequiredSubject()) {
                    journalismRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getWhatSubjectRequired().getSubject());
                    journalismRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    journalismRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                journalismRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().isNeedSupportiveQualification());
                if(journalismRuleAttribute.isNeedSupportiveQualification()) {
                    journalismRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getWhatSupportiveSubject().getSubject());
                    journalismRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    journalismRuleAttribute.initializeIntegerSupportiveGrade();
                    journalismRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, journalismRuleAttribute.getSupportiveGradeRequired());
                    journalismRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                journalismRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getAmountOfCreditRequired());
                journalismRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getMinimumCreditGrade());
                journalismRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().isGotRequiredSubject());
                journalismRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().isExempted());

                if(journalismRuleAttribute.isGotRequiredSubject()) {
                    journalismRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getWhatSubjectRequired().getSubject());
                    journalismRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    journalismRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                journalismRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().isNeedSupportiveQualification());
                if(journalismRuleAttribute.isNeedSupportiveQualification()) {
                    journalismRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getWhatSupportiveSubject().getSubject());
                    journalismRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    journalismRuleAttribute.initializeIntegerSupportiveGrade();
                    journalismRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, journalismRuleAttribute.getSupportiveGradeRequired());
                    journalismRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMassCommJournalism().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }


}
