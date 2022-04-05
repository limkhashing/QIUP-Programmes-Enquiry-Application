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

@Rule(name = "DME", description = "Entry rule to join Diploma in Mechatronics Engineering")
public class DME
{
    private static RuleAttribute dmeRuleAttribute;

    public DME() { dmeRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (dmeRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < dmeRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], dmeRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= dmeRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            dmeRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", dmeRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= dmeRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    dmeRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", dmeRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (Arrays.asList(dmeRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                        {
                            if (studentGrades[i] <= dmeRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                            {
                                dmeRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(dmeRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < dmeRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(dmeRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= dmeRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dmeRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= dmeRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dmeRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= dmeRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dmeRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                        else if (supportiveGrades[3] <= dmeRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dmeRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= dmeRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dmeRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= dmeRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dmeRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }

        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= dmeRuleAttribute.getMinimumCreditGrade())
                dmeRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (dmeRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(dmeRuleAttribute.getCountCorrectSubjectRequired()>= dmeRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(dmeRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(dmeRuleAttribute.getCountSupportiveSubjectRequired() >= dmeRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(dmeRuleAttribute.getCountCredit() >= dmeRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(dmeRuleAttribute.getCountCredit() >= dmeRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(dmeRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(dmeRuleAttribute.getCountSupportiveSubjectRequired() >= dmeRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(dmeRuleAttribute.getCountCredit() >= dmeRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(dmeRuleAttribute.getCountCredit() >= dmeRuleAttribute.getAmountOfCreditRequired())
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
        dmeRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplMechaEngineering", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return dmeRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "SPM":
            {
                dmeRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSPM().getAmountOfCreditRequired());
                dmeRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getSPM().getMinimumCreditGrade());
                dmeRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDme().getSPM().isGotRequiredSubject());
                if(dmeRuleAttribute.isGotRequiredSubject())
                {
                    dmeRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSPM().getWhatSubjectRequired().getSubject());
                    dmeRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    dmeRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSPM().getAmountOfSubjectRequired());
                }
                dmeRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
            }
            break;
            case "O-Level":
            {
                dmeRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getOLevel().getAmountOfCreditRequired());
                dmeRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getOLevel().getMinimumCreditGrade());
                dmeRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDme().getOLevel().isGotRequiredSubject());
                if(dmeRuleAttribute.isGotRequiredSubject())
                {
                    dmeRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getOLevel().getWhatSubjectRequired().getSubject());
                    dmeRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    dmeRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getOLevel().getAmountOfSubjectRequired());
                }
                dmeRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
            }
            break;
            case "UEC":
            {
                dmeRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getUEC().getAmountOfCreditRequired());
                dmeRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getUEC().getMinimumCreditGrade());
                dmeRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDme().getUEC().isGotRequiredSubject());
                if(dmeRuleAttribute.isGotRequiredSubject())
                {
                    dmeRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getUEC().getWhatSubjectRequired().getSubject());
                    dmeRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    dmeRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getUEC().getAmountOfSubjectRequired());
                }
                dmeRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
            }
            case "STPM":
            {
                dmeRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getAmountOfCreditRequired());
                dmeRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getMinimumCreditGrade());
                dmeRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().isGotRequiredSubject());
                
                if(dmeRuleAttribute.isGotRequiredSubject())
                {
                    dmeRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getWhatSubjectRequired().getSubject());
                    dmeRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    dmeRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dmeRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().isNeedSupportiveQualification());
                if(dmeRuleAttribute.isNeedSupportiveQualification())
                {
                    dmeRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getWhatSupportiveSubject().getSubject());
                    dmeRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dmeRuleAttribute.initializeIntegerSupportiveGrade();
                    dmeRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dmeRuleAttribute.getSupportiveGradeRequired());
                    dmeRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level":
            {
                dmeRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getAmountOfCreditRequired());
                dmeRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getMinimumCreditGrade());
                dmeRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().isGotRequiredSubject());
                
                if(dmeRuleAttribute.isGotRequiredSubject())
                {
                    dmeRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getWhatSubjectRequired().getSubject());
                    dmeRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    dmeRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dmeRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().isNeedSupportiveQualification());
                if(dmeRuleAttribute.isNeedSupportiveQualification())
                {
                    dmeRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getWhatSupportiveSubject().getSubject());
                    dmeRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dmeRuleAttribute.initializeIntegerSupportiveGrade();
                    dmeRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dmeRuleAttribute.getSupportiveGradeRequired());
                    dmeRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDme().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
