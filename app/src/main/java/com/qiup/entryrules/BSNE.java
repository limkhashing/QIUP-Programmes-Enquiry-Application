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

@Rule(name = "BSNE", description = "Entry rule to join Bachelor of Special Needs Education")
public class BSNE
{
    private static RuleAttribute bsneRuleAttribute;

    public BSNE() {
        bsneRuleAttribute = new RuleAttribute();
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
        if (bsneRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bsneRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bsneRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bsneRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bsneRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bsneRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bsneRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bsneRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bsneRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bsneRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bsneRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bsneRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bsneRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bsneRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bsneRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bsneRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bsneRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bsneRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bsneRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bsneRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bsneRuleAttribute.isExempted())
        {
            for(int i = 0; i < bsneRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bsneRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bsneRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bsneRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bsneRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsneRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bsneRuleAttribute.getMinimumCreditGrade())
                bsneRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bsneRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bsneRuleAttribute.getCountCorrectSubjectRequired() >= bsneRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bsneRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bsneRuleAttribute.getCountSupportiveSubjectRequired() >= bsneRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bsneRuleAttribute.getCountCredit() >= bsneRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bsneRuleAttribute.getCountCredit() >= bsneRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bsneRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bsneRuleAttribute.getCountSupportiveSubjectRequired() >= bsneRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bsneRuleAttribute.getCountCredit() >= bsneRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bsneRuleAttribute.getCountCredit() >= bsneRuleAttribute.getAmountOfCreditRequired())
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
        bsneRuleAttribute.setJoinProgrammeTrue();
        Log.d("SpecialNeedsEdu", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return bsneRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bsneRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getUEC().getAmountOfCreditRequired());
                bsneRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getUEC().getMinimumCreditGrade());
                bsneRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBsne().getUEC().isGotRequiredSubject());

                if(bsneRuleAttribute.isGotRequiredSubject()) {
                    bsneRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getUEC().getWhatSubjectRequired().getSubject());
                    bsneRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bsneRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bsneRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bsneRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getAmountOfCreditRequired());
                bsneRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getMinimumCreditGrade());
                bsneRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().isGotRequiredSubject());
                bsneRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().isExempted());

                if(bsneRuleAttribute.isGotRequiredSubject()) {
                    bsneRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getWhatSubjectRequired().getSubject());
                    bsneRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bsneRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bsneRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().isNeedSupportiveQualification());
                if(bsneRuleAttribute.isNeedSupportiveQualification()) {
                    bsneRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getWhatSupportiveSubject().getSubject());
                    bsneRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getWhatSupportiveGrade().getGrade());
                    bsneRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bsneRuleAttribute.initializeIntegerSupportiveGrade();
                    bsneRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bsneRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bsneRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getAmountOfCreditRequired());
                bsneRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getMinimumCreditGrade());
                bsneRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().isGotRequiredSubject());
                bsneRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().isExempted());

                if(bsneRuleAttribute.isGotRequiredSubject()) {
                    bsneRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getWhatSubjectRequired().getSubject());
                    bsneRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bsneRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bsneRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().isNeedSupportiveQualification());
                if(bsneRuleAttribute.isNeedSupportiveQualification()) {
                    bsneRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getWhatSupportiveSubject().getSubject());
                    bsneRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bsneRuleAttribute.initializeIntegerSupportiveGrade();
                    bsneRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bsneRuleAttribute.getSupportiveGradeRequired());
                    bsneRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bsneRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getAmountOfCreditRequired());
                bsneRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getMinimumCreditGrade());
                bsneRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().isGotRequiredSubject());
                bsneRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().isExempted());

                if(bsneRuleAttribute.isGotRequiredSubject()) {
                    bsneRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getWhatSubjectRequired().getSubject());
                    bsneRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bsneRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bsneRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().isNeedSupportiveQualification());
                if(bsneRuleAttribute.isNeedSupportiveQualification()) {
                    bsneRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getWhatSupportiveSubject().getSubject());
                    bsneRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bsneRuleAttribute.initializeIntegerSupportiveGrade();
                    bsneRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bsneRuleAttribute.getSupportiveGradeRequired());
                    bsneRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBsne().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }

}
