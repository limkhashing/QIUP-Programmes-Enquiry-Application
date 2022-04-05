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

@Rule(name = "DCE", description = "Entry rule to join Diploma in Early Childhood Education")
public class DCE
{
    private static RuleAttribute dceRuleAttribute;

    public DCE() {
        dceRuleAttribute = new RuleAttribute();
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
        if (dceRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < dceRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], dceRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= dceRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            dceRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                        if (Objects.equals("Mathematics", dceRuleAttribute.getSubjectRequired().get(j)))
                        {
                            if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                            {
                                for(int k = 0; k < studentSubjects.length; k++)
                                {
                                    if(studentGrades[k] <= dceRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                    {
                                        dceRuleAttribute.incrementCountCorrectSubjectRequired();
                                    }
                                }
                            }
                        }
                        if (Objects.equals("Science / Technical / Vocational", dceRuleAttribute.getSubjectRequired().get(j)))
                        {
                            if (Arrays.asList(dceRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                            {
                                if (studentGrades[i] <= dceRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    dceRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if (dceRuleAttribute.isNeedSupportiveQualification()) {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < dceRuleAttribute.getSupportiveSubjectRequired().size(); j++) {
                switch (dceRuleAttribute.getSupportiveSubjectRequired().get(j)) {
                    case "Bahasa Malaysia": {
                        if (supportiveGrades[0] <= dceRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English": {
                        if (supportiveGrades[1] <= dceRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics": {
                        if (supportiveGrades[2] <= dceRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics": {
                        if (supportiveGrades[3] <= dceRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= dceRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dceRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= dceRuleAttribute.getMinimumCreditGrade())
                dceRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (dceRuleAttribute.isGotRequiredSubject()) {
            // Check subject required is fulfill or not
            if (dceRuleAttribute.getCountCorrectSubjectRequired() >= dceRuleAttribute.getAmountOfSubjectRequired()) {
                // Check need supportive qualification or not
                if (dceRuleAttribute.isNeedSupportiveQualification()) {
                    // If need, check whether it fulfill the supportive grade or not
                    if (dceRuleAttribute.getCountSupportiveSubjectRequired() >= dceRuleAttribute.getAmountOfSupportiveSubjectRequired()) {
                        // Check enough amount of credit or not
                        if (dceRuleAttribute.getCountCredit() >= dceRuleAttribute.getAmountOfCreditRequired()) {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else {
                    // Check enough amount of credit or not
                    if (dceRuleAttribute.getCountCredit() >= dceRuleAttribute.getAmountOfCreditRequired()) {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else { // No subject required
            // Check need supportive qualification or not
            if (dceRuleAttribute.isNeedSupportiveQualification()) {
                // If need, check whether it fulfill the supportive grade or not
                if (dceRuleAttribute.getCountSupportiveSubjectRequired() >= dceRuleAttribute.getAmountOfSupportiveSubjectRequired()) {
                    // Check enough amount of credit or not
                    if (dceRuleAttribute.getCountCredit() >= dceRuleAttribute.getAmountOfCreditRequired()) {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else {
                // Check enough amount of credit or not
                if (dceRuleAttribute.getCountCredit() >= dceRuleAttribute.getAmountOfCreditRequired()) {
                    return true; // Return true as requirements is satisfied
                }
            }
        }

        // Return false as requirements not satisfied
        return false;
    }

    @Action
    public void joinProgramme() throws Exception {
        // if rule is satisfied (return true), this action will be executed
        dceRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplEarlyChildhood", "Joined");
    }

    public static boolean isJoinProgramme() {
        return dceRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch (mainQualificationLevel) {
            case "SPM": {
                dceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSPM().getAmountOfCreditRequired());
                dceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getSPM().getMinimumCreditGrade());
                dceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDce().getSPM().isGotRequiredSubject());

                if (dceRuleAttribute.isGotRequiredSubject()) {
                    dceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSPM().getWhatSubjectRequired().getSubject());
                    dceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    dceRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    dceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level": {
                dceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getOLevel().getAmountOfCreditRequired());
                dceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getOLevel().getMinimumCreditGrade());
                dceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDce().getOLevel().isGotRequiredSubject());

                if (dceRuleAttribute.isGotRequiredSubject()) {
                    dceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getOLevel().getWhatSubjectRequired().getSubject());
                    dceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    dceRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    dceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC": {
                dceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getUEC().getAmountOfCreditRequired());
                dceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getUEC().getMinimumCreditGrade());
                dceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDce().getUEC().isGotRequiredSubject());

                if (dceRuleAttribute.isGotRequiredSubject()) {
                    dceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getUEC().getWhatSubjectRequired().getSubject());
                    dceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    dceRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    dceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM": {
                dceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getAmountOfCreditRequired());
                dceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getMinimumCreditGrade());
                dceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().isGotRequiredSubject());

                if (dceRuleAttribute.isGotRequiredSubject()) {
                    dceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getWhatSubjectRequired().getSubject());
                    dceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    dceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().isNeedSupportiveQualification());
                if (dceRuleAttribute.isNeedSupportiveQualification()) {
                    dceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getWhatSupportiveSubject().getSubject());
                    dceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dceRuleAttribute.initializeIntegerSupportiveGrade();
                    dceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dceRuleAttribute.getSupportiveGradeRequired());
                    dceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level": {
                dceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getAmountOfCreditRequired());
                dceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getMinimumCreditGrade());
                dceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().isGotRequiredSubject());

                if (dceRuleAttribute.isGotRequiredSubject()) {
                    dceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getWhatSubjectRequired().getSubject());
                    dceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    dceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().isNeedSupportiveQualification());
                if (dceRuleAttribute.isNeedSupportiveQualification()) {
                    dceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getWhatSupportiveSubject().getSubject());
                    dceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dceRuleAttribute.initializeIntegerSupportiveGrade();
                    dceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dceRuleAttribute.getSupportiveGradeRequired());
                    dceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM": {
                dceRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getAmountOfCreditRequired());
                dceRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getMinimumCreditGrade());
                dceRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().isGotRequiredSubject());

                if (dceRuleAttribute.isGotRequiredSubject()) {
                    dceRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getWhatSubjectRequired().getSubject());
                    dceRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    dceRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dceRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().isNeedSupportiveQualification());
                if(dceRuleAttribute.isNeedSupportiveQualification()) {
                    dceRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getWhatSupportiveSubject().getSubject());
                    dceRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dceRuleAttribute.initializeIntegerSupportiveGrade();
                    dceRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dceRuleAttribute.getSupportiveGradeRequired());
                    dceRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDce().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
