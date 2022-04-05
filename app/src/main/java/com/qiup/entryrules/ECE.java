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

@Rule(name = "ECE", description = "Entry rule to join Bachelor of Bachelor of Engineering Electronics & Communications Engineering")
public class ECE
{
    private static RuleAttribute eceRuleAttribute;

    public ECE() { eceRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (eceRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < eceRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], eceRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= eceRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            eceRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", eceRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= eceRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    eceRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", eceRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(eceRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= eceRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                eceRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(eceRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < eceRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(eceRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= eceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= eceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= eceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= eceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= eceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(eceRuleAttribute.isExempted())
        {
            for(int i = 0; i < eceRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(eceRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(eceRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(eceRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(eceRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            eceRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= eceRuleAttribute.getMinimumCreditGrade())
                eceRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (eceRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(eceRuleAttribute.getCountCorrectSubjectRequired() >= eceRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(eceRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(eceRuleAttribute.getCountSupportiveSubjectRequired() >= eceRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(eceRuleAttribute.getCountCredit() >= eceRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(eceRuleAttribute.getCountCredit() >= eceRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(eceRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(eceRuleAttribute.getCountSupportiveSubjectRequired() >= eceRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(eceRuleAttribute.getCountCredit() >= eceRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(eceRuleAttribute.getCountCredit() >= eceRuleAttribute.getAmountOfCreditRequired())
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
        eceRuleAttribute.setJoinProgrammeTrue();
        Log.d("ECEjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() { return eceRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                eceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getUEC().getAmountOfCreditRequired());
                eceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getEce().getUEC().getMinimumCreditGrade());
                eceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getEce().getUEC().isGotRequiredSubject());

                if(eceRuleAttribute.isGotRequiredSubject()) {
                    eceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getUEC().getWhatSubjectRequired().getSubject());
                    eceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getEce().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    eceRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    eceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                eceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getAmountOfCreditRequired());
                eceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getMinimumCreditGrade());
                eceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().isGotRequiredSubject());
                eceRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().isExempted());

                if(eceRuleAttribute.isGotRequiredSubject()) {
                    eceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getWhatSubjectRequired().getSubject());
                    eceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    eceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                eceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().isNeedSupportiveQualification());
                if(eceRuleAttribute.isNeedSupportiveQualification()) {
                    eceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getWhatSupportiveSubject().getSubject());
                    eceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getWhatSupportiveGrade().getGrade());
                    eceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    eceRuleAttribute.initializeIntegerSupportiveGrade();
                    eceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, eceRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                eceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getAmountOfCreditRequired());
                eceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getMinimumCreditGrade());
                eceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().isGotRequiredSubject());
                eceRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().isExempted());

                if(eceRuleAttribute.isGotRequiredSubject()) {
                    eceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getWhatSubjectRequired().getSubject());
                    eceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    eceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                eceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().isNeedSupportiveQualification());
                if(eceRuleAttribute.isNeedSupportiveQualification()) {
                    eceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getWhatSupportiveSubject().getSubject());
                    eceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    eceRuleAttribute.initializeIntegerSupportiveGrade();
                    eceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, eceRuleAttribute.getSupportiveGradeRequired());
                    eceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getEce().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }

}
