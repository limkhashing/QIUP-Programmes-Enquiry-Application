package com.qiup.entryrules;

import android.util.Log;

import com.qiup.POJO.RulePojo;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.util.Objects;

@Rule(name = "FIS", description = "Entry rule to join Foundation in Sciences")
public class FIS
{
    private static RuleAttribute fisRuleAttribute;

    public FIS() { fisRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades)
    {
        setJSONAttribute(qualificationLevel); // First set json attribute to the rule

        // First check got required subject or not.
        if (fisRuleAttribute.isGotRequiredSubject()) {
            // Check whether the subject's grade is smaller or equal to the required subject's grade
            if (Objects.equals(qualificationLevel, "UEC"))
            {
                for (int i = 0; i < studentSubjects.length; i++)
                {
                    for (int j = 0; j < fisRuleAttribute.getSubjectRequired().size(); j++)
                    {
                        if (Objects.equals(fisRuleAttribute.getSubjectRequired().get(j), "Biology"))
                        {
                            if(Objects.equals(studentSubjects[i], "Biology"))
                            {
                                if (studentGrades[i] > fisRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    return false;
                                }
                            }
                        }
                        if (Objects.equals(fisRuleAttribute.getSubjectRequired().get(j), "Chemistry"))
                        {
                            if(Objects.equals(studentSubjects[i], "Chemistry"))
                            {
                                if (studentGrades[i] > fisRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < fisRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], fisRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= fisRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            fisRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                }
            }
        }

        // Check got required science subject or not.
        // If got then check student's subject's grade whether it fulfill the grade or not
        if(fisRuleAttribute.getMinimumRequiredScienceSubject() != 0)
        {
            for(int i = 0; i < studentSubjects.length; i++)
            {
                for(int j = 0; j < fisRuleAttribute.getScienceSubjectRequired().size(); j++)
                {
                    if(Objects.equals(studentSubjects[i], fisRuleAttribute.getScienceSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= fisRuleAttribute.getMinimumScienceSubjectGradeRequired().get(j))
                        {
                            fisRuleAttribute.incrementCountCorrectRequiredScienceSubject();
                        }
                    }
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= fisRuleAttribute.getMinimumCreditGrade())
                fisRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (fisRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(fisRuleAttribute.getCountCorrectSubjectRequired() >= fisRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check enough amount of credit or not
                if(fisRuleAttribute.getCountCredit() >= fisRuleAttribute.getAmountOfCreditRequired())
                {
                    // If got / no required science subject, check count required subject correct enough or not
                    if(fisRuleAttribute.getCountCorrectRequiredScienceSubject() >= fisRuleAttribute.getMinimumRequiredScienceSubject())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // no subject required
        {
            // Check enough amount of credit or not
            if(fisRuleAttribute.getCountCredit() >= fisRuleAttribute.getAmountOfCreditRequired())
            {
                // If got / no required science subject, check count required subject correct enough or not
                if(fisRuleAttribute.getCountCorrectRequiredScienceSubject() >= fisRuleAttribute.getMinimumRequiredScienceSubject()) {
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
        fisRuleAttribute.setJoinProgrammeTrue();
        Log.d("FIS joinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() {
        return fisRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String qualificationLevel) {
        switch(qualificationLevel) {
            case "SPM":
            {
                fisRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getAmountOfCreditRequired());
                fisRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getMinimumCreditGrade());
                fisRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getMinimumRequiredScienceSubject());
                fisRuleAttribute.setScienceSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getWhatScienceSubjectRequired().getSubject());
                fisRuleAttribute.setMinimumScienceSubjectGradeRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getMinimumScienceSubjectGrade().getGrade());
                fisRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().isGotRequiredSubject());
                if(fisRuleAttribute.isGotRequiredSubject())
                {
                    fisRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getWhatSubjectRequired().getSubject());
                    fisRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    fisRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level":
            {
                fisRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getAmountOfCreditRequired());
                fisRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getMinimumCreditGrade());
                fisRuleAttribute.setMinimumRequiredScienceSubject(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getMinimumRequiredScienceSubject());
                fisRuleAttribute.setScienceSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getWhatScienceSubjectRequired().getSubject());
                fisRuleAttribute.setMinimumScienceSubjectGradeRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getMinimumScienceSubjectGrade().getGrade());
                fisRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().isGotRequiredSubject());
                if(fisRuleAttribute.isGotRequiredSubject())
                {
                    fisRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getWhatSubjectRequired().getSubject());
                    fisRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    fisRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC":
            {
                fisRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getUEC().getAmountOfCreditRequired());
                fisRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getFis().getUEC().getMinimumCreditGrade());
                fisRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getFis().getUEC().isGotRequiredSubject());
                if(fisRuleAttribute.isGotRequiredSubject())
                {
                    fisRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getUEC().getWhatSubjectRequired().getSubject());
                    fisRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getFis().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    fisRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getFis().getUEC().getAmountOfSubjectRequired());
                }
            }
            break;
        }
    }
}
