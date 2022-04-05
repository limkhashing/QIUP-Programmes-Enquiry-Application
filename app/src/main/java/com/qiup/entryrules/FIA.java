package com.qiup.entryrules;

import android.util.Log;

import com.qiup.POJO.RulePojo;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.util.Arrays;
import java.util.Objects;

@Rule(name = "FIA", description = "Entry rule to join Foundation in Arts")
public class FIA
{
    private static RuleAttribute fiaRuleAttribute;

    public FIA() { fiaRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades) {

        setJSONAttribute(qualificationLevel); // First set json attribute to the rule

        // First check got required subject or not.
        // If got then check whether the subject's grade is smaller or equal to the required subject's grade
        if (fiaRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < fiaRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], fiaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= fiaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            fiaRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", fiaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= fiaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    fiaRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= fiaRuleAttribute.getMinimumCreditGrade())
                fiaRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether will return false or not
        if (fiaRuleAttribute.isGotRequiredSubject()) // If got required subject
        {
            // If credit is not enough or required subject's grade is not fulfill, return false
            if (fiaRuleAttribute.getCountCredit() < fiaRuleAttribute.getAmountOfCreditRequired()
                    || fiaRuleAttribute.getCountCorrectSubjectRequired() < fiaRuleAttribute.getSubjectRequired().size())
            {
                return false;
            }
        }
        else // No required subject
        {
            // If credit is not enough, return false
            if (fiaRuleAttribute.getCountCredit() < fiaRuleAttribute.getAmountOfCreditRequired())
                return false;
        }

        // If requirements is satiafied, return true
        return true;
    }

    @Action
    public void joinProgramme() throws Exception {
        // If rule is satisfied (return true), this action will be executed
        fiaRuleAttribute.setJoinProgrammeTrue();
        Log.d("FIA joinProgramme", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return fiaRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String qualificationLevel) {
        switch(qualificationLevel)
        {
            case "SPM":
            {
                fiaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFia().getSPM().getAmountOfCreditRequired());
                fiaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFia().getSPM().getMinimumCreditGrade());
                fiaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFia().getSPM().isGotRequiredSubject());
                if(fiaRuleAttribute.isGotRequiredSubject())
                {
                    fiaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFia().getSPM().getWhatSubjectRequired().getSubject());
                    fiaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFia().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                }
            }
            break;
            case "O-Level":
            {
                fiaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFia().getOLevel().getAmountOfCreditRequired());
                fiaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFia().getOLevel().getMinimumCreditGrade());
                fiaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFia().getOLevel().isGotRequiredSubject());
                if(fiaRuleAttribute.isGotRequiredSubject())
                {
                    fiaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFia().getOLevel().getWhatSubjectRequired().getSubject());
                    fiaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFia().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                }
            }
            break;
            case "UEC":
            {
                fiaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFia().getUEC().getAmountOfCreditRequired());
                fiaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFia().getUEC().getMinimumCreditGrade());
                fiaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFia().getUEC().isGotRequiredSubject());
                if(fiaRuleAttribute.isGotRequiredSubject())
                {
                    fiaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFia().getUEC().getWhatSubjectRequired().getSubject());
                    fiaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFia().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                }
            }
        }
    }
}
