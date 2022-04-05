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

@Rule(name = "BIS", description = "Entry rule to join Bachelor of Business Information System")
public class BIS
{
    private static RuleAttribute bisRuleAttribute;
    public BIS() { bisRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bisRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bisRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bisRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bisRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bisRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bisRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bisRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bisRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bisRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bisRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bisRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bisRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bisRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bisRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bisRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bisRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bisRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bisRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bisRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bisRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bisRuleAttribute.isExempted())
        {
            for(int i = 0; i < bisRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bisRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bisRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bisRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bisRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bisRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bisRuleAttribute.getMinimumCreditGrade())
                bisRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bisRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bisRuleAttribute.getCountCorrectSubjectRequired() >= bisRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bisRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bisRuleAttribute.getCountSupportiveSubjectRequired() >= bisRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bisRuleAttribute.getCountCredit() >= bisRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bisRuleAttribute.getCountCredit() >= bisRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bisRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bisRuleAttribute.getCountSupportiveSubjectRequired() >= bisRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bisRuleAttribute.getCountCredit() >= bisRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bisRuleAttribute.getCountCredit() >= bisRuleAttribute.getAmountOfCreditRequired())
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
        bisRuleAttribute.setJoinProgrammeTrue();
        Log.d("BIS", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return bisRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bisRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getUEC().getAmountOfCreditRequired());
                bisRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBis().getUEC().getMinimumCreditGrade());
                bisRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBis().getUEC().isGotRequiredSubject());

                if(bisRuleAttribute.isGotRequiredSubject()) {
                    bisRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getUEC().getWhatSubjectRequired().getSubject());
                    bisRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBis().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bisRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bisRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bisRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getAmountOfCreditRequired());
                bisRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getMinimumCreditGrade());
                bisRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().isGotRequiredSubject());
                bisRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().isExempted());

                if(bisRuleAttribute.isGotRequiredSubject()) {
                    bisRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getWhatSubjectRequired().getSubject());
                    bisRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bisRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bisRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().isNeedSupportiveQualification());
                if(bisRuleAttribute.isNeedSupportiveQualification()) {
                    bisRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getWhatSupportiveSubject().getSubject());
                    bisRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getWhatSupportiveGrade().getGrade());
                    bisRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bisRuleAttribute.initializeIntegerSupportiveGrade();
                    bisRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bisRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bisRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getAmountOfCreditRequired());
                bisRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getMinimumCreditGrade());
                bisRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().isGotRequiredSubject());
                bisRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().isExempted());

                if(bisRuleAttribute.isGotRequiredSubject()) {
                    bisRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getWhatSubjectRequired().getSubject());
                    bisRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bisRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bisRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().isNeedSupportiveQualification());
                if(bisRuleAttribute.isNeedSupportiveQualification()) {
                    bisRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getWhatSupportiveSubject().getSubject());
                    bisRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bisRuleAttribute.initializeIntegerSupportiveGrade();
                    bisRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bisRuleAttribute.getSupportiveGradeRequired());
                    bisRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBis().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}

