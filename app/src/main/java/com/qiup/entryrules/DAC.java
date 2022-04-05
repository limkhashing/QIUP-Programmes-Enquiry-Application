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

@Rule(name = "DAC", description = "Entry rule to join Diploma in Accountancy")
public class DAC
{
    private static RuleAttribute dacRuleAttribute;

    public DAC() { dacRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (dacRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < dacRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], dacRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= dacRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            dacRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", dacRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= dacRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    dacRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", dacRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (Arrays.asList(dacRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                        {
                            if (studentGrades[i] <= dacRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                            {
                                dacRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(dacRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < dacRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(dacRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= dacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= dacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= dacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= dacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= dacRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dacRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }

        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= dacRuleAttribute.getMinimumCreditGrade())
                dacRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (dacRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(dacRuleAttribute.getCountCorrectSubjectRequired() >= dacRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(dacRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(dacRuleAttribute.getCountSupportiveSubjectRequired() >= dacRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(dacRuleAttribute.getCountCredit() >= dacRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(dacRuleAttribute.getCountCredit() >= dacRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(dacRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(dacRuleAttribute.getCountSupportiveSubjectRequired() >= dacRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(dacRuleAttribute.getCountCredit() >= dacRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(dacRuleAttribute.getCountCredit() >= dacRuleAttribute.getAmountOfCreditRequired())
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
        // If requirements is satisfied (return true), this action will be executed
        dacRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplomaInAccountancy", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return dacRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "SPM":
            {
                dacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSPM().getAmountOfCreditRequired());
                dacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getSPM().getMinimumCreditGrade());
                dacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDac().getSPM().isGotRequiredSubject());

                if(dacRuleAttribute.isGotRequiredSubject()) {
                    dacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSPM().getWhatSubjectRequired().getSubject());
                    dacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    dacRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    dacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level":
            {
                dacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getOLevel().getAmountOfCreditRequired());
                dacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getOLevel().getMinimumCreditGrade());
                dacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDac().getOLevel().isGotRequiredSubject());

                if(dacRuleAttribute.isGotRequiredSubject()) {
                    dacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getOLevel().getWhatSubjectRequired().getSubject());
                    dacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    dacRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    dacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC":
            {
                dacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getUEC().getAmountOfCreditRequired());
                dacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getUEC().getMinimumCreditGrade());
                dacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDac().getUEC().isGotRequiredSubject());

                if(dacRuleAttribute.isGotRequiredSubject()) {
                    dacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getUEC().getWhatSubjectRequired().getSubject());
                    dacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    dacRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    dacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                dacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getAmountOfCreditRequired());
                dacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getMinimumCreditGrade());
                dacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().isGotRequiredSubject());

                if(dacRuleAttribute.isGotRequiredSubject()) {
                    dacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getWhatSubjectRequired().getSubject());
                    dacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    dacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dacRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().isNeedSupportiveQualification());
                if(dacRuleAttribute.isNeedSupportiveQualification()) {
                    dacRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getWhatSupportiveSubject().getSubject());
                    dacRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getWhatSupportiveGrade().getGrade());
                    dacRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    dacRuleAttribute.initializeIntegerSupportiveGrade();
                    dacRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dacRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                dacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getAmountOfCreditRequired());
                dacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getMinimumCreditGrade());
                dacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().isGotRequiredSubject());

                if(dacRuleAttribute.isGotRequiredSubject()) {
                    dacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getWhatSubjectRequired().getSubject());
                    dacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    dacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dacRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().isNeedSupportiveQualification());
                if(dacRuleAttribute.isNeedSupportiveQualification()) {
                    dacRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getWhatSupportiveSubject().getSubject());
                    dacRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dacRuleAttribute.initializeIntegerSupportiveGrade();
                    dacRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dacRuleAttribute.getSupportiveGradeRequired());
                    dacRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                dacRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getAmountOfCreditRequired());
                dacRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getMinimumCreditGrade());
                dacRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().isGotRequiredSubject());

                if(dacRuleAttribute.isGotRequiredSubject()) {
                    dacRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getWhatSubjectRequired().getSubject());
                    dacRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    dacRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dacRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().isNeedSupportiveQualification());
                if(dacRuleAttribute.isNeedSupportiveQualification()) {
                    dacRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getWhatSupportiveSubject().getSubject());
                    dacRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dacRuleAttribute.initializeIntegerSupportiveGrade();
                    dacRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dacRuleAttribute.getSupportiveGradeRequired());
                    dacRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDac().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
