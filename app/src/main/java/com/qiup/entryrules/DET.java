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

@Rule(name = "DET", description = "Entry rule to join Diploma in Environmental Technology")
public class DET
{
    private static RuleAttribute detRuleAttribute;

    public DET() { detRuleAttribute = new RuleAttribute();}
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, studentSubjects, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (detRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < detRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], detRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= detRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            detRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", detRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= detRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    detRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", detRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (Arrays.asList(detRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                        {
                            if (studentGrades[i] <= detRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                            {
                                detRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(detRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < detRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(detRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= detRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            detRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= detRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            detRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= detRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            detRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= detRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            detRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= detRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            detRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }

        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= detRuleAttribute.getMinimumCreditGrade())
                detRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (detRuleAttribute.isGotRequiredSubject())
        {
            // Check minimum required science subject grade required is fulfill or not. Based on is science stream or not
            if(detRuleAttribute.getCountCorrectSubjectRequired() >= detRuleAttribute.getMinimumRequiredScienceSubject())
            {
                // Check need supportive qualification or not
                if(detRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(detRuleAttribute.getCountSupportiveSubjectRequired() >= detRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(detRuleAttribute.getCountCredit() >= detRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(detRuleAttribute.getCountCredit() >= detRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(detRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(detRuleAttribute.getCountSupportiveSubjectRequired() >= detRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(detRuleAttribute.getCountCredit() >= detRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(detRuleAttribute.getCountCredit() >= detRuleAttribute.getAmountOfCreditRequired())
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
        detRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplEnvironmentalTech", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return detRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String[] studentSubjects, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "SPM":
            {
                detRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getAmountOfCreditRequired());
                detRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getMinimumCreditGrade());
                detRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().isGotRequiredSubject());
                if(detRuleAttribute.isGotRequiredSubject()) {
                    detRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getWhatSubjectRequired().getSubject());
                    detRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));

                    if (Arrays.asList(studentSubjects).contains("Science")) { // Not science stream student
                        detRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getNotScienceStream().getMinimumRequiredScienceSubject());
                        detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getNotScienceStream().getMinimumSubjectRequiredGrade().getGrade());
                    }
                    else { // Is science stream student
                        detRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getIsScienceStream().getMinimumRequiredScienceSubject());
                        detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSPM().getIsScienceStream().getMinimumSubjectRequiredGrade().getGrade());
                    }
                }
            }
            break;
            case "O-Level":
            {
                detRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getAmountOfCreditRequired());
                detRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getMinimumCreditGrade());
                detRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().isGotRequiredSubject());
                if(detRuleAttribute.isGotRequiredSubject()) {
                    detRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getWhatSubjectRequired().getSubject());
                    detRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));

                    if (!Arrays.asList(studentSubjects).contains("Biology")) { // Not science stream student
                        detRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getNotScienceStream().getMinimumRequiredScienceSubject());
                        detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getNotScienceStream().getMinimumSubjectRequiredGrade().getGrade());
                    }
                    else { // Is science stream student
                        detRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getIsScienceStream().getMinimumRequiredScienceSubject());
                        detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getOLevel().getIsScienceStream().getMinimumSubjectRequiredGrade().getGrade());
                    }
                }
            }
            break;
            case "UEC":
            {
                detRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getAmountOfCreditRequired());
                detRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getMinimumCreditGrade());
                detRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().isGotRequiredSubject());
                if(detRuleAttribute.isGotRequiredSubject()) {
                    detRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getWhatSubjectRequired().getSubject());
                    detRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));

                    if (!Arrays.asList(studentSubjects).contains("Biology")) { // Not science stream student because UEC science stream need Biology
                        detRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getNotScienceStream().getMinimumRequiredScienceSubject());
                        detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getNotScienceStream().getMinimumSubjectRequiredGrade().getGrade());

                    }
                    else { // Is science stream student
                        detRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getIsScienceStream().getMinimumRequiredScienceSubject());
                        detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getUEC().getIsScienceStream().getMinimumSubjectRequiredGrade().getGrade());
                    }
                }
            }
            case "STPM":
            {
                detRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getAmountOfCreditRequired());
                detRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getMinimumCreditGrade());
                detRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().isGotRequiredSubject());
                if(detRuleAttribute.isGotRequiredSubject()) {
                    detRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getWhatSubjectRequired().getSubject());
                    detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                }

                // Get supportive things
                detRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().isNeedSupportiveQualification());
                if(detRuleAttribute.isNeedSupportiveQualification()) {
                    detRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getWhatSupportiveSubject().getSubject());
                    detRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    detRuleAttribute.initializeIntegerSupportiveGrade();
                    detRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, detRuleAttribute.getSupportiveGradeRequired());
                    detRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level":
            {
                detRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().getAmountOfCreditRequired());
                detRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().getMinimumCreditGrade());
                detRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().isGotRequiredSubject());
                if(detRuleAttribute.isGotRequiredSubject()) {
                    detRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().getWhatSubjectRequired().getSubject());
                    detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                }

                // Get supportive things
                detRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().isNeedSupportiveQualification());
                if(detRuleAttribute.isNeedSupportiveQualification()) {
                    detRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().getWhatSupportiveSubject().getSubject());
                    detRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    detRuleAttribute.initializeIntegerSupportiveGrade();
                    detRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, detRuleAttribute.getSupportiveGradeRequired());
                    detRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                detRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getAmountOfCreditRequired());
                detRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getMinimumCreditGrade());
                detRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().isGotRequiredSubject());
                if(detRuleAttribute.isGotRequiredSubject()) {
                    detRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getWhatSubjectRequired().getSubject());
                    detRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                }

                // Get supportive things
                detRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().isNeedSupportiveQualification());
                if(detRuleAttribute.isNeedSupportiveQualification()) {
                    detRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getWhatSupportiveSubject().getSubject());
                    detRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    detRuleAttribute.initializeIntegerSupportiveGrade();
                    detRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, detRuleAttribute.getSupportiveGradeRequired());
                    detRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDet().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }    
}
