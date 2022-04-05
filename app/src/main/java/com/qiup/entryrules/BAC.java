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

@Rule(name = "BAC", description = "Entry rule to join Bachelor of Accountancy")
public class BAC
{
    private static RuleAttribute bacRuleAttribute;

    public BAC() { bacRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bacRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bacRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bacRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bacRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bacRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bacRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bacRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bacRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bacRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bacRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bacRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bacRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bacRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bacRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bacRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bacRuleAttribute.isExempted())
        {
            for(int i = 0; i < bacRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bacRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bacRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bacRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bacRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bacRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bacRuleAttribute.getMinimumCreditGrade())
                bacRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bacRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bacRuleAttribute.getCountCorrectSubjectRequired() >= bacRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bacRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bacRuleAttribute.getCountSupportiveSubjectRequired() >= bacRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bacRuleAttribute.getCountCredit() >= bacRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bacRuleAttribute.getCountCredit() >= bacRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bacRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bacRuleAttribute.getCountSupportiveSubjectRequired() >= bacRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bacRuleAttribute.getCountCredit() >= bacRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bacRuleAttribute.getCountCredit() >= bacRuleAttribute.getAmountOfCreditRequired())
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
        bacRuleAttribute.setJoinProgrammeTrue();
        Log.d("BACjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() {
        return bacRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getUEC().getAmountOfCreditRequired());
                bacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getUEC().getMinimumCreditGrade());
                bacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBac().getUEC().isGotRequiredSubject());

                if(bacRuleAttribute.isGotRequiredSubject()) {
                    bacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getUEC().getWhatSubjectRequired().getSubject());
                    bacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bacRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getUEC().getAmountOfSubjectRequired());
                }
            }
            break;
            case "STPM":
            {
                bacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getAmountOfCreditRequired());
                bacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getMinimumCreditGrade());
                bacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().isGotRequiredSubject());
                bacRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().isExempted());

                if(bacRuleAttribute.isGotRequiredSubject()) {
                    bacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getWhatSubjectRequired().getSubject());
                    bacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bacRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().isNeedSupportiveQualification());
                if(bacRuleAttribute.isNeedSupportiveQualification()) {
                    bacRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getWhatSupportiveSubject().getSubject());
                    bacRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getWhatSupportiveGrade().getGrade());
                    bacRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bacRuleAttribute.initializeIntegerSupportiveGrade();
                    bacRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bacRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getAmountOfCreditRequired());
                bacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getMinimumCreditGrade());
                bacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().isGotRequiredSubject());
                bacRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().isExempted());

                if(bacRuleAttribute.isGotRequiredSubject()) {
                    bacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getWhatSubjectRequired().getSubject());
                    bacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bacRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().isNeedSupportiveQualification());
                if(bacRuleAttribute.isNeedSupportiveQualification()) {
                    bacRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getWhatSupportiveSubject().getSubject());
                    bacRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bacRuleAttribute.initializeIntegerSupportiveGrade();
                    bacRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bacRuleAttribute.getSupportiveGradeRequired());
                    bacRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getAmountOfCreditRequired());
                bacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getMinimumCreditGrade());
                bacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().isGotRequiredSubject());
                bacRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().isExempted());

                if(bacRuleAttribute.isGotRequiredSubject()) {
                    bacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getWhatSubjectRequired().getSubject());
                    bacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bacRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().isNeedSupportiveQualification());
                if(bacRuleAttribute.isNeedSupportiveQualification()) {
                    bacRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getWhatSupportiveSubject().getSubject());
                    bacRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bacRuleAttribute.initializeIntegerSupportiveGrade();
                    bacRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bacRuleAttribute.getSupportiveGradeRequired());
                    bacRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBac().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
