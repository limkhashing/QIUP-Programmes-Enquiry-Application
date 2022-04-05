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

@Rule(name = "BEM", description = "Entry rule to join Bachelor of Engineering in Mechatronics")
public class BEM
{
    private static RuleAttribute bemRuleAttribute;

    public BEM() { bemRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bemRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bemRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bemRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bemRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bemRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bemRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bemRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bemRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bemRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bemRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bemRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bemRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bemRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bemRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bemRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bemRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bemRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bemRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bemRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bemRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bemRuleAttribute.isExempted())
        {
            for(int i = 0; i < bemRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bemRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bemRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bemRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bemRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bemRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bemRuleAttribute.getMinimumCreditGrade())
                bemRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bemRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bemRuleAttribute.getCountCorrectSubjectRequired() >= bemRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bemRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bemRuleAttribute.getCountSupportiveSubjectRequired() >= bemRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bemRuleAttribute.getCountCredit() >= bemRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bemRuleAttribute.getCountCredit() >= bemRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bemRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bemRuleAttribute.getCountSupportiveSubjectRequired() >= bemRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bemRuleAttribute.getCountCredit() >= bemRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bemRuleAttribute.getCountCredit() >= bemRuleAttribute.getAmountOfCreditRequired())
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
        bemRuleAttribute.setJoinProgrammeTrue();
        Log.d("bemjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() { return bemRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bemRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getUEC().getAmountOfCreditRequired());
                bemRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBem().getUEC().getMinimumCreditGrade());
                bemRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBem().getUEC().isGotRequiredSubject());

                if(bemRuleAttribute.isGotRequiredSubject()) {
                    bemRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getUEC().getWhatSubjectRequired().getSubject());
                    bemRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBem().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bemRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bemRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bemRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getAmountOfCreditRequired());
                bemRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getMinimumCreditGrade());
                bemRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().isGotRequiredSubject());
                bemRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().isExempted());

                if(bemRuleAttribute.isGotRequiredSubject()) {
                    bemRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getWhatSubjectRequired().getSubject());
                    bemRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bemRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bemRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().isNeedSupportiveQualification());
                if(bemRuleAttribute.isNeedSupportiveQualification()) {
                    bemRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getWhatSupportiveSubject().getSubject());
                    bemRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getWhatSupportiveGrade().getGrade());
                    bemRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bemRuleAttribute.initializeIntegerSupportiveGrade();
                    bemRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bemRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bemRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getAmountOfCreditRequired());
                bemRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getMinimumCreditGrade());
                bemRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().isGotRequiredSubject());
                bemRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().isExempted());

                if(bemRuleAttribute.isGotRequiredSubject()) {
                    bemRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getWhatSubjectRequired().getSubject());
                    bemRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bemRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bemRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().isNeedSupportiveQualification());
                if(bemRuleAttribute.isNeedSupportiveQualification()) {
                    bemRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getWhatSupportiveSubject().getSubject());
                    bemRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bemRuleAttribute.initializeIntegerSupportiveGrade();
                    bemRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bemRuleAttribute.getSupportiveGradeRequired());
                    bemRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBem().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
