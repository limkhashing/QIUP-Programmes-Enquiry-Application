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

@Rule(name = "BS_ActuarialSciences", description = "Entry rule to join Bachelor of Science Actuarial Sciences")
public class BS_ActuarialSciences
{
    private static RuleAttribute bsActuarialSciencesRuleAttribute;

    public BS_ActuarialSciences() { bsActuarialSciencesRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bsActuarialSciencesRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bsActuarialSciencesRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bsActuarialSciencesRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bsActuarialSciencesRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bsActuarialSciencesRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bsActuarialSciencesRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bsActuarialSciencesRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bsActuarialSciencesRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bsActuarialSciencesRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bsActuarialSciencesRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bsActuarialSciencesRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bsActuarialSciencesRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bsActuarialSciencesRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bsActuarialSciencesRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bsActuarialSciencesRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bsActuarialSciencesRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bsActuarialSciencesRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bsActuarialSciencesRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bsActuarialSciencesRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bsActuarialSciencesRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bsActuarialSciencesRuleAttribute.isExempted())
        {
            for(int i = 0; i < bsActuarialSciencesRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bsActuarialSciencesRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bsActuarialSciencesRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bsActuarialSciencesRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bsActuarialSciencesRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bsActuarialSciencesRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bsActuarialSciencesRuleAttribute.getMinimumCreditGrade())
                bsActuarialSciencesRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bsActuarialSciencesRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bsActuarialSciencesRuleAttribute.getCountCorrectSubjectRequired() >= bsActuarialSciencesRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bsActuarialSciencesRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bsActuarialSciencesRuleAttribute.getCountSupportiveSubjectRequired() >= bsActuarialSciencesRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bsActuarialSciencesRuleAttribute.getCountCredit() >= bsActuarialSciencesRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bsActuarialSciencesRuleAttribute.getCountCredit() >= bsActuarialSciencesRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bsActuarialSciencesRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bsActuarialSciencesRuleAttribute.getCountSupportiveSubjectRequired() >= bsActuarialSciencesRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bsActuarialSciencesRuleAttribute.getCountCredit() >= bsActuarialSciencesRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bsActuarialSciencesRuleAttribute.getCountCredit() >= bsActuarialSciencesRuleAttribute.getAmountOfCreditRequired())
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
        bsActuarialSciencesRuleAttribute.setJoinProgrammeTrue();
        Log.d("ActuarialScience", "Joined");
    }

    public static boolean isJoinProgramme() { return bsActuarialSciencesRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bsActuarialSciencesRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getUEC().getAmountOfCreditRequired());
                bsActuarialSciencesRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getUEC().getMinimumCreditGrade());
                bsActuarialSciencesRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getUEC().isGotRequiredSubject());

                if(bsActuarialSciencesRuleAttribute.isGotRequiredSubject()) {
                    bsActuarialSciencesRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getUEC().getWhatSubjectRequired().getSubject());
                    bsActuarialSciencesRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bsActuarialSciencesRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bsActuarialSciencesRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bsActuarialSciencesRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getAmountOfCreditRequired());
                bsActuarialSciencesRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getMinimumCreditGrade());
                bsActuarialSciencesRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().isGotRequiredSubject());
                bsActuarialSciencesRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().isExempted());

                if(bsActuarialSciencesRuleAttribute.isGotRequiredSubject()) {
                    bsActuarialSciencesRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getWhatSubjectRequired().getSubject());
                    bsActuarialSciencesRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bsActuarialSciencesRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bsActuarialSciencesRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().isNeedSupportiveQualification());
                if(bsActuarialSciencesRuleAttribute.isNeedSupportiveQualification()) {
                    bsActuarialSciencesRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getWhatSupportiveSubject().getSubject());
                    bsActuarialSciencesRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getWhatSupportiveGrade().getGrade());
                    bsActuarialSciencesRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bsActuarialSciencesRuleAttribute.initializeIntegerSupportiveGrade();
                    bsActuarialSciencesRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bsActuarialSciencesRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bsActuarialSciencesRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getAmountOfCreditRequired());
                bsActuarialSciencesRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getMinimumCreditGrade());
                bsActuarialSciencesRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().isGotRequiredSubject());
                bsActuarialSciencesRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().isExempted());

                if(bsActuarialSciencesRuleAttribute.isGotRequiredSubject()) {
                    bsActuarialSciencesRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getWhatSubjectRequired().getSubject());
                    bsActuarialSciencesRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bsActuarialSciencesRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bsActuarialSciencesRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().isNeedSupportiveQualification());
                if(bsActuarialSciencesRuleAttribute.isNeedSupportiveQualification()) {
                    bsActuarialSciencesRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getWhatSupportiveSubject().getSubject());
                    bsActuarialSciencesRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bsActuarialSciencesRuleAttribute.initializeIntegerSupportiveGrade();
                    bsActuarialSciencesRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bsActuarialSciencesRuleAttribute.getSupportiveGradeRequired());
                    bsActuarialSciencesRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                bsActuarialSciencesRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getAmountOfCreditRequired());
                bsActuarialSciencesRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getMinimumCreditGrade());
                bsActuarialSciencesRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().isGotRequiredSubject());
                bsActuarialSciencesRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().isExempted());

                if(bsActuarialSciencesRuleAttribute.isGotRequiredSubject()) {
                    bsActuarialSciencesRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getWhatSubjectRequired().getSubject());
                    bsActuarialSciencesRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    bsActuarialSciencesRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bsActuarialSciencesRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().isNeedSupportiveQualification());
                if(bsActuarialSciencesRuleAttribute.isNeedSupportiveQualification()) {
                    bsActuarialSciencesRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getWhatSupportiveSubject().getSubject());
                    bsActuarialSciencesRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bsActuarialSciencesRuleAttribute.initializeIntegerSupportiveGrade();
                    bsActuarialSciencesRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bsActuarialSciencesRuleAttribute.getSupportiveGradeRequired());
                    bsActuarialSciencesRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBs_actuarialSciences().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }

}
