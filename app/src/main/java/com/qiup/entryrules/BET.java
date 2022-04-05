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

@Rule(name = "BET", description = "Entry rule to join Bachelor of Environmental Technology")

public class BET
{
    private static RuleAttribute betRuleAttribute;
    public BET() {
        betRuleAttribute = new RuleAttribute();
    }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (betRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < betRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], betRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= betRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            betRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", betRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= betRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    betRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", betRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(betRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= betRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                betRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(betRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < betRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(betRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= betRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= betRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= betRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= betRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= betRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(betRuleAttribute.isExempted())
        {
            for(int i = 0; i < betRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(betRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(betRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(betRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(betRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            betRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= betRuleAttribute.getMinimumCreditGrade())
                betRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (betRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(betRuleAttribute.getCountCorrectSubjectRequired() >= betRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(betRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(betRuleAttribute.getCountSupportiveSubjectRequired() >= betRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(betRuleAttribute.getCountCredit() >= betRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(betRuleAttribute.getCountCredit() >= betRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(betRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(betRuleAttribute.getCountSupportiveSubjectRequired() >= betRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(betRuleAttribute.getCountCredit() >= betRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(betRuleAttribute.getCountCredit() >= betRuleAttribute.getAmountOfCreditRequired())
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
        // if rule is satisfied (return true), this action will be executed
        betRuleAttribute.setJoinProgrammeTrue();
        Log.d("BET", "Joined");
    }

    public static boolean isJoinProgramme() { return betRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                betRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getUEC().getAmountOfCreditRequired());
                betRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getUEC().getMinimumCreditGrade());
                betRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBet().getUEC().isGotRequiredSubject());

                if(betRuleAttribute.isGotRequiredSubject()) {
                    betRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getUEC().getWhatSubjectRequired().getSubject());
                    betRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    betRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    betRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                betRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getAmountOfCreditRequired());
                betRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getMinimumCreditGrade());
                betRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().isGotRequiredSubject());
                betRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().isExempted());

                if(betRuleAttribute.isGotRequiredSubject()) {
                    betRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getWhatSubjectRequired().getSubject());
                    betRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    betRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                betRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().isNeedSupportiveQualification());
                if(betRuleAttribute.isNeedSupportiveQualification()) {
                    betRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getWhatSupportiveSubject().getSubject());
                    betRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getWhatSupportiveGrade().getGrade());
                    betRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    betRuleAttribute.initializeIntegerSupportiveGrade();
                    betRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, betRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                betRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getAmountOfCreditRequired());
                betRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getMinimumCreditGrade());
                betRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().isGotRequiredSubject());
                betRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().isExempted());

                if(betRuleAttribute.isGotRequiredSubject()) {
                    betRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getWhatSubjectRequired().getSubject());
                    betRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    betRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                betRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().isNeedSupportiveQualification());
                if(betRuleAttribute.isNeedSupportiveQualification()) {
                    betRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getWhatSupportiveSubject().getSubject());
                    betRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    betRuleAttribute.initializeIntegerSupportiveGrade();
                    betRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, betRuleAttribute.getSupportiveGradeRequired());
                    betRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                betRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getAmountOfCreditRequired());
                betRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getMinimumCreditGrade());
                betRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().isGotRequiredSubject());
                betRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().isExempted());

                if(betRuleAttribute.isGotRequiredSubject()) {
                    betRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getWhatSubjectRequired().getSubject());
                    betRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    betRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                betRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().isNeedSupportiveQualification());
                if(betRuleAttribute.isNeedSupportiveQualification()) {
                    betRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getWhatSupportiveSubject().getSubject());
                    betRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    betRuleAttribute.initializeIntegerSupportiveGrade();
                    betRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, betRuleAttribute.getSupportiveGradeRequired());
                    betRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBet().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }

}