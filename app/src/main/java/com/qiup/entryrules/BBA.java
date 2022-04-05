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

@Rule(name = "BBA", description = "Entry rule to join Bachelor of Business Administration")
public class BBA
{
    private static RuleAttribute bbaRuleAttribute;

    public BBA() { bbaRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bbaRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bbaRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bbaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bbaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bbaRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bbaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bbaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bbaRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bbaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bbaRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bbaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bbaRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bbaRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bbaRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bbaRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bbaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bbaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bbaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bbaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bbaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bbaRuleAttribute.isExempted())
        {
            for(int i = 0; i < bbaRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bbaRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bbaRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bbaRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bbaRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bbaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bbaRuleAttribute.getMinimumCreditGrade())
                bbaRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bbaRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bbaRuleAttribute.getCountCorrectSubjectRequired() >= bbaRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bbaRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bbaRuleAttribute.getCountSupportiveSubjectRequired() >= bbaRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bbaRuleAttribute.getCountCredit() >= bbaRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bbaRuleAttribute.getCountCredit() >= bbaRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bbaRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bbaRuleAttribute.getCountSupportiveSubjectRequired() >= bbaRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bbaRuleAttribute.getCountCredit() >= bbaRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bbaRuleAttribute.getCountCredit() >= bbaRuleAttribute.getAmountOfCreditRequired())
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
        bbaRuleAttribute.setJoinProgrammeTrue();
        Log.d("BBAjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return bbaRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bbaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getUEC().getAmountOfCreditRequired());
                bbaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getUEC().getMinimumCreditGrade());
                bbaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBba().getUEC().isGotRequiredSubject());

                if(bbaRuleAttribute.isGotRequiredSubject()) {
                    bbaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getUEC().getWhatSubjectRequired().getSubject());
                    bbaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bbaRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bbaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bbaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getAmountOfCreditRequired());
                bbaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getMinimumCreditGrade());
                bbaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().isGotRequiredSubject());
                bbaRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().isExempted());

                if(bbaRuleAttribute.isGotRequiredSubject()) {
                    bbaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getWhatSubjectRequired().getSubject());
                    bbaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bbaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bbaRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().isNeedSupportiveQualification());
                if(bbaRuleAttribute.isNeedSupportiveQualification()) {
                    bbaRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getWhatSupportiveSubject().getSubject());
                    bbaRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getWhatSupportiveGrade().getGrade());
                    bbaRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bbaRuleAttribute.initializeIntegerSupportiveGrade();
                    bbaRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bbaRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bbaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getAmountOfCreditRequired());
                bbaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getMinimumCreditGrade());
                bbaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().isGotRequiredSubject());
                bbaRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().isExempted());

                if(bbaRuleAttribute.isGotRequiredSubject()) {
                    bbaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getWhatSubjectRequired().getSubject());
                    bbaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bbaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bbaRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().isNeedSupportiveQualification());
                if(bbaRuleAttribute.isNeedSupportiveQualification()) {
                    bbaRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getWhatSupportiveSubject().getSubject());
                    bbaRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bbaRuleAttribute.initializeIntegerSupportiveGrade();
                    bbaRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bbaRuleAttribute.getSupportiveGradeRequired());
                    bbaRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bbaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getAmountOfCreditRequired());
                bbaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getMinimumCreditGrade());
                bbaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().isGotRequiredSubject());
                bbaRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().isExempted());

                if(bbaRuleAttribute.isGotRequiredSubject()) {
                    bbaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getWhatSubjectRequired().getSubject());
                    bbaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bbaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bbaRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().isNeedSupportiveQualification());
                if(bbaRuleAttribute.isNeedSupportiveQualification()) {
                    bbaRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getWhatSupportiveSubject().getSubject());
                    bbaRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bbaRuleAttribute.initializeIntegerSupportiveGrade();
                    bbaRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bbaRuleAttribute.getSupportiveGradeRequired());
                    bbaRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBba().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
