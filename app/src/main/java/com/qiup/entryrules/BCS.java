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

@Rule(name = "BCS", description = "Entry rule to join Bachelor of Computer Sciences")
public class BCS
{
    // Advanced math is additional maths
    private static RuleAttribute bcsRuleAttribute;

    public BCS() { bcsRuleAttribute = new RuleAttribute(); }

    // when
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bcsRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bcsRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bcsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bcsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bcsRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bcsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bcsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bcsRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bcsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bcsRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bcsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bcsRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bcsRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bcsRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bcsRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bcsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bcsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bcsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bcsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bcsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bcsRuleAttribute.isExempted())
        {
            for(int i = 0; i < bcsRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bcsRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bcsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bcsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bcsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bcsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bcsRuleAttribute.getMinimumCreditGrade())
                bcsRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bcsRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bcsRuleAttribute.getCountCorrectSubjectRequired() >= bcsRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bcsRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bcsRuleAttribute.getCountSupportiveSubjectRequired() >= bcsRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bcsRuleAttribute.getCountCredit() >= bcsRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bcsRuleAttribute.getCountCredit() >= bcsRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bcsRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bcsRuleAttribute.getCountSupportiveSubjectRequired() >= bcsRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bcsRuleAttribute.getCountCredit() >= bcsRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bcsRuleAttribute.getCountCredit() >= bcsRuleAttribute.getAmountOfCreditRequired())
                {
                    return true; // return true as requirements is satisfied
                }
            }
        }

        // Return false as requirements not satisfied
        return false;
    }

    //then
    @Action
    public void joinProgramme() throws Exception
    {
        // if rule is satisfied (return true), this action will be executed
        bcsRuleAttribute.setJoinProgrammeTrue();
        Log.d("BCSjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return bcsRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bcsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getUEC().getAmountOfCreditRequired());
                bcsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBcs().getUEC().getMinimumCreditGrade());
                bcsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBcs().getUEC().isGotRequiredSubject());

                if(bcsRuleAttribute.isGotRequiredSubject()) {
                    bcsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getUEC().getWhatSubjectRequired().getSubject());
                    bcsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBcs().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bcsRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bcsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bcsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getAmountOfCreditRequired());
                bcsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getMinimumCreditGrade());
                bcsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().isGotRequiredSubject());
                bcsRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().isExempted());

                if(bcsRuleAttribute.isGotRequiredSubject()) {
                    bcsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getWhatSubjectRequired().getSubject());
                    bcsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bcsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bcsRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().isNeedSupportiveQualification());
                if(bcsRuleAttribute.isNeedSupportiveQualification()) {
                    bcsRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getWhatSupportiveSubject().getSubject());
                    bcsRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getWhatSupportiveGrade().getGrade());
                    bcsRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bcsRuleAttribute.initializeIntegerSupportiveGrade();
                    bcsRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bcsRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bcsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getAmountOfCreditRequired());
                bcsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getMinimumCreditGrade());
                bcsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().isGotRequiredSubject());
                bcsRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().isExempted());

                if(bcsRuleAttribute.isGotRequiredSubject()) {
                    bcsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getWhatSubjectRequired().getSubject());
                    bcsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bcsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bcsRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().isNeedSupportiveQualification());
                if(bcsRuleAttribute.isNeedSupportiveQualification()) {
                    bcsRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getWhatSupportiveSubject().getSubject());
                    bcsRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bcsRuleAttribute.initializeIntegerSupportiveGrade();
                    bcsRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bcsRuleAttribute.getSupportiveGradeRequired());
                    bcsRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBcs().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
