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

@Rule(name = "BCE", description = "Entry rule to join Bachelor of Early Childhood Education")
public class BCE
{
    private static RuleAttribute bceRuleAttribute;
    
    public BCE()
    {
        bceRuleAttribute = new RuleAttribute();
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
        if (bceRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bceRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bceRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bceRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bceRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bceRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bceRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bceRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bceRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bceRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bceRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bceRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bceRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bceRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bceRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bceRuleAttribute.isExempted())
        {
            for(int i = 0; i < bceRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bceRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bceRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bceRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bceRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bceRuleAttribute.getMinimumCreditGrade())
                bceRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bceRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bceRuleAttribute.getCountCorrectSubjectRequired() >= bceRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bceRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bceRuleAttribute.getCountSupportiveSubjectRequired() >= bceRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bceRuleAttribute.getCountCredit() >= bceRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bceRuleAttribute.getCountCredit() >= bceRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bceRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bceRuleAttribute.getCountSupportiveSubjectRequired() >= bceRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bceRuleAttribute.getCountCredit() >= bceRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bceRuleAttribute.getCountCredit() >= bceRuleAttribute.getAmountOfCreditRequired())
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
        bceRuleAttribute.setJoinProgrammeTrue();
        Log.d("EarlyChildhoodEdu", "Joined");
    }

    public static boolean isJoinProgramme() { return bceRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getUEC().getAmountOfCreditRequired());
                bceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getUEC().getMinimumCreditGrade());
                bceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBce().getUEC().isGotRequiredSubject());

                if(bceRuleAttribute.isGotRequiredSubject()) {
                    bceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getUEC().getWhatSubjectRequired().getSubject());
                    bceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bceRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getAmountOfCreditRequired());
                bceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getMinimumCreditGrade());
                bceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().isGotRequiredSubject());
                bceRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().isExempted());

                if(bceRuleAttribute.isGotRequiredSubject()) {
                    bceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getWhatSubjectRequired().getSubject());
                    bceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().isNeedSupportiveQualification());
                if(bceRuleAttribute.isNeedSupportiveQualification()) {
                    bceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getWhatSupportiveSubject().getSubject());
                    bceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getWhatSupportiveGrade().getGrade());
                    bceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bceRuleAttribute.initializeIntegerSupportiveGrade();
                    bceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bceRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getAmountOfCreditRequired());
                bceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getMinimumCreditGrade());
                bceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().isGotRequiredSubject());
                bceRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().isExempted());

                if(bceRuleAttribute.isGotRequiredSubject()) {
                    bceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getWhatSubjectRequired().getSubject());
                    bceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().isNeedSupportiveQualification());
                if(bceRuleAttribute.isNeedSupportiveQualification()) {
                    bceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getWhatSupportiveSubject().getSubject());
                    bceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bceRuleAttribute.initializeIntegerSupportiveGrade();
                    bceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bceRuleAttribute.getSupportiveGradeRequired());
                    bceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getAmountOfCreditRequired());
                bceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getMinimumCreditGrade());
                bceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().isGotRequiredSubject());
                bceRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().isExempted());

                if(bceRuleAttribute.isGotRequiredSubject()) {
                    bceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getWhatSubjectRequired().getSubject());
                    bceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().isNeedSupportiveQualification());
                if(bceRuleAttribute.isNeedSupportiveQualification()) {
                    bceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getWhatSupportiveSubject().getSubject());
                    bceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bceRuleAttribute.initializeIntegerSupportiveGrade();
                    bceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bceRuleAttribute.getSupportiveGradeRequired());
                    bceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBce().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
    
}
