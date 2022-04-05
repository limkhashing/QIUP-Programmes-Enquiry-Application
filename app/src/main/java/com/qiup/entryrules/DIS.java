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

@Rule(name = "DIS", description = "Entry rule to join Diploma in Business Information System")
public class DIS
{
    private static RuleAttribute disRuleAttribute;

    public DIS() { disRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (disRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < disRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], disRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= disRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            disRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", disRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= disRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    disRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", disRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (Arrays.asList(disRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                        {
                            if (studentGrades[i] <= disRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                            {
                                disRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(disRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < disRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(disRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= disRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            disRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= disRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            disRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= disRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            disRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                        else if (supportiveGrades[3] <= disRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            disRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= disRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            disRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= disRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            disRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }

        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= disRuleAttribute.getMinimumCreditGrade())
                disRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (disRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(disRuleAttribute.getCountCorrectSubjectRequired() >= disRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(disRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(disRuleAttribute.getCountSupportiveSubjectRequired() >= disRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(disRuleAttribute.getCountCredit() >= disRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(disRuleAttribute.getCountCredit() >= disRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(disRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(disRuleAttribute.getCountSupportiveSubjectRequired() >= disRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(disRuleAttribute.getCountCredit() >= disRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(disRuleAttribute.getCountCredit() >= disRuleAttribute.getAmountOfCreditRequired())
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
        disRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplomaBusiInfoSystem", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return disRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "SPM":
            {
                disRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSPM().getAmountOfCreditRequired());
                disRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getSPM().getMinimumCreditGrade());
                disRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDis().getSPM().isGotRequiredSubject());

                if(disRuleAttribute.isGotRequiredSubject()) {
                    disRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSPM().getWhatSubjectRequired().getSubject());
                    disRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    disRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    disRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level":
            {
                disRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getOLevel().getAmountOfCreditRequired());
                disRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getOLevel().getMinimumCreditGrade());
                disRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDis().getOLevel().isGotRequiredSubject());

                if(disRuleAttribute.isGotRequiredSubject()) {
                    disRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getOLevel().getWhatSubjectRequired().getSubject());
                    disRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    disRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    disRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC":
            {
                disRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getUEC().getAmountOfCreditRequired());
                disRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getUEC().getMinimumCreditGrade());
                disRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDis().getUEC().isGotRequiredSubject());

                if(disRuleAttribute.isGotRequiredSubject()) {
                    disRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getUEC().getWhatSubjectRequired().getSubject());
                    disRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    disRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    disRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                disRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getAmountOfCreditRequired());
                disRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getMinimumCreditGrade());
                disRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().isGotRequiredSubject());

                if(disRuleAttribute.isGotRequiredSubject()) {
                    disRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getWhatSubjectRequired().getSubject());
                    disRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    disRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                disRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().isNeedSupportiveQualification());
                if(disRuleAttribute.isNeedSupportiveQualification()) {
                    disRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getWhatSupportiveSubject().getSubject());
                    disRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    disRuleAttribute.initializeIntegerSupportiveGrade();
                    disRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, disRuleAttribute.getSupportiveGradeRequired());
                    disRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level":
            {
                disRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getAmountOfCreditRequired());
                disRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getMinimumCreditGrade());
                disRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().isGotRequiredSubject());

                if(disRuleAttribute.isGotRequiredSubject()) {
                    disRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getWhatSubjectRequired().getSubject());
                    disRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    disRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                disRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().isNeedSupportiveQualification());
                if(disRuleAttribute.isNeedSupportiveQualification()) {
                    disRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getWhatSupportiveSubject().getSubject());
                    disRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    disRuleAttribute.initializeIntegerSupportiveGrade();
                    disRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, disRuleAttribute.getSupportiveGradeRequired());
                    disRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                disRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getAmountOfCreditRequired());
                disRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getMinimumCreditGrade());
                disRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().isGotRequiredSubject());

                if(disRuleAttribute.isGotRequiredSubject()) {
                    disRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getWhatSubjectRequired().getSubject());
                    disRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    disRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                disRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().isNeedSupportiveQualification());
                if(disRuleAttribute.isNeedSupportiveQualification()) {
                    disRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getWhatSupportiveSubject().getSubject());
                    disRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    disRuleAttribute.initializeIntegerSupportiveGrade();
                    disRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, disRuleAttribute.getSupportiveGradeRequired());
                    disRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDis().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
