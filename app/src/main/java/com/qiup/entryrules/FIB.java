package com.qiup.entryrules;

import android.util.Log;

import com.qiup.POJO.RulePojo;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.util.Arrays;
import java.util.Objects;

@Rule(name = "FIB", description = "Entry rule to join Foundation in Business")
public class FIB
{
    private static RuleAttribute fibRuleAttribute;

    public FIB() {
        fibRuleAttribute = new RuleAttribute();
    }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades) {

        setJSONAttribute(qualificationLevel); // First set json attribute to the rule

        // First check got required subject or not.
        // If got then check whether the subject's grade is smaller or equal to the required subject's grade
        if (fibRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < fibRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], fibRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= fibRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            fibRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", fibRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= fibRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    fibRuleAttribute.incrementCountCorrectSubjectRequired();
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
            if (studentGrades[i] <= fibRuleAttribute.getMinimumCreditGrade())
                fibRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether will return false or not
        if (fibRuleAttribute.isGotRequiredSubject()) // If got required subject
        {
            // If credit is not enough or required subject's grade is not fulfill, return false
            if (fibRuleAttribute.getCountCredit() < fibRuleAttribute.getAmountOfCreditRequired()
                    || fibRuleAttribute.getCountCorrectSubjectRequired() < fibRuleAttribute.getSubjectRequired().size())
            {
                return false;
            }
        }
        else {
            // If credit is not enough, return false
            if (fibRuleAttribute.getCountCredit() < fibRuleAttribute.getAmountOfCreditRequired())
                return false;
        }

        // If requirements is satiafied, return true
        return true;
    }

    @Action
    public void joinProgramme() throws Exception {
        // If rule is satisfied (return true), this action will be executed
        fibRuleAttribute.setJoinProgrammeTrue();
        Log.d("FIB joinProgramme", "Joined");
    }

    public static boolean isJoinProgramme()  {
        return fibRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String qualificationLevel) {
        switch(qualificationLevel)
        {
            case "SPM":
            {
                fibRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFib().getSPM().getAmountOfCreditRequired());
                fibRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFib().getSPM().getMinimumCreditGrade());
                fibRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFib().getSPM().isGotRequiredSubject());
                if(fibRuleAttribute.isGotRequiredSubject())
                {
                    fibRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFib().getSPM().getWhatSubjectRequired().getSubject());
                    fibRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFib().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                }
            }
            break;
            case "O-Level":
            {
                fibRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFib().getOLevel().getAmountOfCreditRequired());
                fibRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFib().getOLevel().getMinimumCreditGrade());
                fibRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFib().getOLevel().isGotRequiredSubject());
                if(fibRuleAttribute.isGotRequiredSubject())
                {
                    fibRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFib().getOLevel().getWhatSubjectRequired().getSubject());
                    fibRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFib().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                }
            }
            break;
            case "UEC":
            {
                fibRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFib().getUEC().getAmountOfCreditRequired());
                fibRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFib().getUEC().getMinimumCreditGrade());
                fibRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFib().getUEC().isGotRequiredSubject());
                if(fibRuleAttribute.isGotRequiredSubject())
                {
                    fibRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFib().getUEC().getWhatSubjectRequired().getSubject());
                    fibRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFib().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                }
            }
        }
    }
}
