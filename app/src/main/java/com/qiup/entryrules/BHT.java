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

@Rule(name = "BHT", description = "Entry rule to join BBA in Hospitality & Tourism Management")
public class BHT
{
    private static RuleAttribute bhtRuleAttribute;
    public BHT() {
        bhtRuleAttribute = new RuleAttribute();
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
        if (bhtRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bhtRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bhtRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bhtRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bhtRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bhtRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bhtRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bhtRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bhtRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bhtRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bhtRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bhtRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bhtRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bhtRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bhtRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bhtRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bhtRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bhtRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bhtRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bhtRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bhtRuleAttribute.isExempted())
        {
            for(int i = 0; i < bhtRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bhtRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bhtRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bhtRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bhtRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bhtRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bhtRuleAttribute.getMinimumCreditGrade())
                bhtRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bhtRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bhtRuleAttribute.getCountCorrectSubjectRequired() >= bhtRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bhtRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bhtRuleAttribute.getCountSupportiveSubjectRequired() >= bhtRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bhtRuleAttribute.getCountCredit() >= bhtRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bhtRuleAttribute.getCountCredit() >= bhtRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bhtRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bhtRuleAttribute.getCountSupportiveSubjectRequired() >= bhtRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bhtRuleAttribute.getCountCredit() >= bhtRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bhtRuleAttribute.getCountCredit() >= bhtRuleAttribute.getAmountOfCreditRequired())
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
        // if rule is satisfied (return true), this action will be executed
        bhtRuleAttribute.setJoinProgrammeTrue();
        Log.d("bhtJoin ", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return bhtRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bhtRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getUEC().getAmountOfCreditRequired());
                bhtRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getUEC().getMinimumCreditGrade());
                bhtRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBht().getUEC().isGotRequiredSubject());

                if(bhtRuleAttribute.isGotRequiredSubject()) {
                    bhtRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getUEC().getWhatSubjectRequired().getSubject());
                    bhtRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bhtRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bhtRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bhtRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getAmountOfCreditRequired());
                bhtRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getMinimumCreditGrade());
                bhtRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().isGotRequiredSubject());
                bhtRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().isExempted());

                if(bhtRuleAttribute.isGotRequiredSubject()) {
                    bhtRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getWhatSubjectRequired().getSubject());
                    bhtRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bhtRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bhtRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().isNeedSupportiveQualification());
                if(bhtRuleAttribute.isNeedSupportiveQualification()) {
                    bhtRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getWhatSupportiveSubject().getSubject());
                    bhtRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getWhatSupportiveGrade().getGrade());
                    bhtRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bhtRuleAttribute.initializeIntegerSupportiveGrade();
                    bhtRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bhtRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bhtRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getAmountOfCreditRequired());
                bhtRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getMinimumCreditGrade());
                bhtRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().isGotRequiredSubject());
                bhtRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().isExempted());

                if(bhtRuleAttribute.isGotRequiredSubject()) {
                    bhtRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getWhatSubjectRequired().getSubject());
                    bhtRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bhtRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bhtRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().isNeedSupportiveQualification());
                if(bhtRuleAttribute.isNeedSupportiveQualification()) {
                    bhtRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getWhatSupportiveSubject().getSubject());
                    bhtRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bhtRuleAttribute.initializeIntegerSupportiveGrade();
                    bhtRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bhtRuleAttribute.getSupportiveGradeRequired());
                    bhtRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bhtRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getAmountOfCreditRequired());
                bhtRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getMinimumCreditGrade());
                bhtRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().isGotRequiredSubject());
                bhtRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().isExempted());

                if(bhtRuleAttribute.isGotRequiredSubject()) {
                    bhtRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getWhatSubjectRequired().getSubject());
                    bhtRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bhtRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bhtRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().isNeedSupportiveQualification());
                if(bhtRuleAttribute.isNeedSupportiveQualification()) {
                    bhtRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getWhatSupportiveSubject().getSubject());
                    bhtRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bhtRuleAttribute.initializeIntegerSupportiveGrade();
                    bhtRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bhtRuleAttribute.getSupportiveGradeRequired());
                    bhtRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBht().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
    
}
