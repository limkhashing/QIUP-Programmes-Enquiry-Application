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

@Rule(name = "BFI", description = "Entry rule to join Bachelor of Finance")
public class BFI
{
    private static RuleAttribute bfiRuleAttribute;

    public BFI() { bfiRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bfiRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bfiRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bfiRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bfiRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bfiRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bfiRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bfiRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bfiRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bfiRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bfiRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bfiRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bfiRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bfiRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bfiRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bfiRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bfiRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bfiRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bfiRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bfiRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bfiRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bfiRuleAttribute.isExempted())
        {
            for(int i = 0; i < bfiRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bfiRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bfiRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bfiRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bfiRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bfiRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bfiRuleAttribute.getMinimumCreditGrade())
                bfiRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bfiRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bfiRuleAttribute.getCountCorrectSubjectRequired() >= bfiRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bfiRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bfiRuleAttribute.getCountSupportiveSubjectRequired() >= bfiRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bfiRuleAttribute.getCountCredit() >= bfiRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bfiRuleAttribute.getCountCredit() >= bfiRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bfiRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bfiRuleAttribute.getCountSupportiveSubjectRequired() >= bfiRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bfiRuleAttribute.getCountCredit() >= bfiRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bfiRuleAttribute.getCountCredit() >= bfiRuleAttribute.getAmountOfCreditRequired())
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
        bfiRuleAttribute.setJoinProgrammeTrue();
        Log.d("BFIjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return bfiRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bfiRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getUEC().getAmountOfCreditRequired());
                bfiRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getUEC().getMinimumCreditGrade());
                bfiRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBfi().getUEC().isGotRequiredSubject());

                if(bfiRuleAttribute.isGotRequiredSubject()) {
                    bfiRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getUEC().getWhatSubjectRequired().getSubject());
                    bfiRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bfiRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bfiRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bfiRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getAmountOfCreditRequired());
                bfiRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getMinimumCreditGrade());
                bfiRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().isGotRequiredSubject());
                bfiRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().isExempted());

                if(bfiRuleAttribute.isGotRequiredSubject()) {
                    bfiRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getWhatSubjectRequired().getSubject());
                    bfiRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bfiRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bfiRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().isNeedSupportiveQualification());
                if(bfiRuleAttribute.isNeedSupportiveQualification()) {
                    bfiRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getWhatSupportiveSubject().getSubject());
                    bfiRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getWhatSupportiveGrade().getGrade());
                    bfiRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bfiRuleAttribute.initializeIntegerSupportiveGrade();
                    bfiRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bfiRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bfiRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getAmountOfCreditRequired());
                bfiRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getMinimumCreditGrade());
                bfiRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().isGotRequiredSubject());
                bfiRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().isExempted());

                if(bfiRuleAttribute.isGotRequiredSubject()) {
                    bfiRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getWhatSubjectRequired().getSubject());
                    bfiRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bfiRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bfiRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().isNeedSupportiveQualification());
                if(bfiRuleAttribute.isNeedSupportiveQualification()) {
                    bfiRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getWhatSupportiveSubject().getSubject());
                    bfiRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bfiRuleAttribute.initializeIntegerSupportiveGrade();
                    bfiRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bfiRuleAttribute.getSupportiveGradeRequired());
                    bfiRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bfiRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getAmountOfCreditRequired());
                bfiRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getMinimumCreditGrade());
                bfiRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().isGotRequiredSubject());
                bfiRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().isExempted());

                if(bfiRuleAttribute.isGotRequiredSubject()) {
                    bfiRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getWhatSubjectRequired().getSubject());
                    bfiRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bfiRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bfiRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().isNeedSupportiveQualification());
                if(bfiRuleAttribute.isNeedSupportiveQualification()) {
                    bfiRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getWhatSupportiveSubject().getSubject());
                    bfiRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bfiRuleAttribute.initializeIntegerSupportiveGrade();
                    bfiRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bfiRuleAttribute.getSupportiveGradeRequired());
                    bfiRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBfi().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }    
}
