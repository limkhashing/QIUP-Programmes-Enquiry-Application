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

@Rule(name = "BIT", description = "Entry rule to join Bachelor of Information Technology")
public class BIT
{
    private static RuleAttribute bitRuleAttribute;

    public BIT() { bitRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bitRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bitRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bitRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bitRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bitRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bitRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bitRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bitRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bitRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bitRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bitRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bitRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bitRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bitRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bitRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bitRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bitRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bitRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bitRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bitRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bitRuleAttribute.isExempted())
        {
            for(int i = 0; i < bitRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bitRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bitRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bitRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bitRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bitRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bitRuleAttribute.getMinimumCreditGrade())
                bitRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bitRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bitRuleAttribute.getCountCorrectSubjectRequired() >= bitRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bitRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bitRuleAttribute.getCountSupportiveSubjectRequired() >= bitRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bitRuleAttribute.getCountCredit() >= bitRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bitRuleAttribute.getCountCredit() >= bitRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bitRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bitRuleAttribute.getCountSupportiveSubjectRequired() >= bitRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bitRuleAttribute.getCountCredit() >= bitRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bitRuleAttribute.getCountCredit() >= bitRuleAttribute.getAmountOfCreditRequired())
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
        bitRuleAttribute.setJoinProgrammeTrue();
        Log.d("BITjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() { return bitRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bitRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getUEC().getAmountOfCreditRequired());
                bitRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBit().getUEC().getMinimumCreditGrade());
                bitRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBit().getUEC().isGotRequiredSubject());

                if(bitRuleAttribute.isGotRequiredSubject()) {
                    bitRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getUEC().getWhatSubjectRequired().getSubject());
                    bitRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBit().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bitRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bitRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bitRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getAmountOfCreditRequired());
                bitRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getMinimumCreditGrade());
                bitRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().isGotRequiredSubject());
                bitRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().isExempted());

                if(bitRuleAttribute.isGotRequiredSubject()) {
                    bitRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getWhatSubjectRequired().getSubject());
                    bitRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bitRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bitRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().isNeedSupportiveQualification());
                if(bitRuleAttribute.isNeedSupportiveQualification()) {
                    bitRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getWhatSupportiveSubject().getSubject());
                    bitRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getWhatSupportiveGrade().getGrade());
                    bitRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bitRuleAttribute.initializeIntegerSupportiveGrade();
                    bitRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bitRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bitRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getAmountOfCreditRequired());
                bitRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getMinimumCreditGrade());
                bitRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().isGotRequiredSubject());
                bitRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().isExempted());

                if(bitRuleAttribute.isGotRequiredSubject()) {
                    bitRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getWhatSubjectRequired().getSubject());
                    bitRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bitRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bitRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().isNeedSupportiveQualification());
                if(bitRuleAttribute.isNeedSupportiveQualification()) {
                    bitRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getWhatSupportiveSubject().getSubject());
                    bitRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bitRuleAttribute.initializeIntegerSupportiveGrade();
                    bitRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bitRuleAttribute.getSupportiveGradeRequired());
                    bitRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBit().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
