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

@Rule(name = "TESL", description = "Entry rule to join TESL")
public class TESL
{
    private static RuleAttribute teslRuleAttribute;

    public TESL() { teslRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (teslRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < teslRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], teslRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= teslRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            teslRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", teslRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= teslRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    teslRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", teslRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(teslRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= teslRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                teslRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(teslRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < teslRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(teslRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= teslRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= teslRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= teslRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= teslRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= teslRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(teslRuleAttribute.isExempted())
        {
            for(int i = 0; i < teslRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(teslRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(teslRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(teslRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(teslRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            teslRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= teslRuleAttribute.getMinimumCreditGrade())
                teslRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (teslRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(teslRuleAttribute.getCountCorrectSubjectRequired() >= teslRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(teslRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(teslRuleAttribute.getCountSupportiveSubjectRequired() >= teslRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(teslRuleAttribute.getCountCredit() >= teslRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(teslRuleAttribute.getCountCredit() >= teslRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(teslRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(teslRuleAttribute.getCountSupportiveSubjectRequired() >= teslRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(teslRuleAttribute.getCountCredit() >= teslRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(teslRuleAttribute.getCountCredit() >= teslRuleAttribute.getAmountOfCreditRequired())
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
        teslRuleAttribute.setJoinProgrammeTrue();
        Log.d("TESLjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return teslRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                teslRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getUEC().getAmountOfCreditRequired());
                teslRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getUEC().getMinimumCreditGrade());
                teslRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getTesl().getUEC().isGotRequiredSubject());

                if(teslRuleAttribute.isGotRequiredSubject()) {
                    teslRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getUEC().getWhatSubjectRequired().getSubject());
                    teslRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    teslRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    teslRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                teslRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getAmountOfCreditRequired());
                teslRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getMinimumCreditGrade());
                teslRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().isGotRequiredSubject());
                teslRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().isExempted());

                if(teslRuleAttribute.isGotRequiredSubject()) {
                    teslRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getWhatSubjectRequired().getSubject());
                    teslRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    teslRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                teslRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().isNeedSupportiveQualification());
                if(teslRuleAttribute.isNeedSupportiveQualification()) {
                    teslRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getWhatSupportiveSubject().getSubject());
                    teslRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getWhatSupportiveGrade().getGrade());
                    teslRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    teslRuleAttribute.initializeIntegerSupportiveGrade();
                    teslRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, teslRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                teslRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getAmountOfCreditRequired());
                teslRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getMinimumCreditGrade());
                teslRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().isGotRequiredSubject());
                teslRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().isExempted());

                if(teslRuleAttribute.isGotRequiredSubject()) {
                    teslRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getWhatSubjectRequired().getSubject());
                    teslRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    teslRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                teslRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().isNeedSupportiveQualification());
                if(teslRuleAttribute.isNeedSupportiveQualification()) {
                    teslRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getWhatSupportiveSubject().getSubject());
                    teslRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    teslRuleAttribute.initializeIntegerSupportiveGrade();
                    teslRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, teslRuleAttribute.getSupportiveGradeRequired());
                    teslRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                teslRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getAmountOfCreditRequired());
                teslRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getMinimumCreditGrade());
                teslRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().isGotRequiredSubject());
                teslRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().isExempted());

                if(teslRuleAttribute.isGotRequiredSubject()) {
                    teslRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getWhatSubjectRequired().getSubject());
                    teslRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    teslRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                teslRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().isNeedSupportiveQualification());
                if(teslRuleAttribute.isNeedSupportiveQualification()) {
                    teslRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getWhatSupportiveSubject().getSubject());
                    teslRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    teslRuleAttribute.initializeIntegerSupportiveGrade();
                    teslRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, teslRuleAttribute.getSupportiveGradeRequired());
                    teslRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getTesl().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }

}
