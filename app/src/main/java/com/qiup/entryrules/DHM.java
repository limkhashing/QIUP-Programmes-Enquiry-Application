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

@Rule(name = "DHM", description = "Entry rule to join Diploma in Hotel Management")
public class DHM
{
    private static RuleAttribute dhmRuleAttribute;

    public DHM() {
        dhmRuleAttribute = new RuleAttribute();
    }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades) {

        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (dhmRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < dhmRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], dhmRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= dhmRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            dhmRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                        if (Objects.equals("Mathematics", dhmRuleAttribute.getSubjectRequired().get(j)))
                        {
                            if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                            {
                                for(int k = 0; k < studentSubjects.length; k++)
                                {
                                    if(studentGrades[k] <= dhmRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                    {
                                        dhmRuleAttribute.incrementCountCorrectSubjectRequired();
                                    }
                                }
                            }
                        }
                        if (Objects.equals("Science / Technical / Vocational", dhmRuleAttribute.getSubjectRequired().get(j)))
                        {
                            if (Arrays.asList(dhmRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                            {
                                if (studentGrades[i] <= dhmRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    dhmRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if (dhmRuleAttribute.isNeedSupportiveQualification()) {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < dhmRuleAttribute.getSupportiveSubjectRequired().size(); j++) {
                switch (dhmRuleAttribute.getSupportiveSubjectRequired().get(j)) {
                    case "Bahasa Malaysia": {
                        if (supportiveGrades[0] <= dhmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dhmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English": {
                        if (supportiveGrades[1] <= dhmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dhmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics": {
                        if (supportiveGrades[2] <= dhmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dhmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics": {
                        if (supportiveGrades[3] <= dhmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dhmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= dhmRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dhmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= dhmRuleAttribute.getMinimumCreditGrade())
                dhmRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (dhmRuleAttribute.isGotRequiredSubject()) {
            // Check subject required is fulfill or not
            if (dhmRuleAttribute.getCountCorrectSubjectRequired()>= dhmRuleAttribute.getAmountOfSubjectRequired()) {
                // Check need supportive qualification or not
                if (dhmRuleAttribute.isNeedSupportiveQualification()) {
                    // If need, check whether it fulfill the supportive grade or not
                    if (dhmRuleAttribute.getCountSupportiveSubjectRequired() >= dhmRuleAttribute.getAmountOfSupportiveSubjectRequired()) {
                        // Check enough amount of credit or not
                        if (dhmRuleAttribute.getCountCredit() >= dhmRuleAttribute.getAmountOfCreditRequired()) {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else {
                    // Check enough amount of credit or not
                    if (dhmRuleAttribute.getCountCredit() >= dhmRuleAttribute.getAmountOfCreditRequired()) {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else { // No subject required
            // Check need supportive qualification or not
            if (dhmRuleAttribute.isNeedSupportiveQualification()) {
                // If need, check whether it fulfill the supportive grade or not
                if (dhmRuleAttribute.getCountSupportiveSubjectRequired() >= dhmRuleAttribute.getAmountOfSupportiveSubjectRequired()) {
                    // Check enough amount of credit or not
                    if (dhmRuleAttribute.getCountCredit() >= dhmRuleAttribute.getAmountOfCreditRequired()) {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else {
                // Check enough amount of credit or not
                if (dhmRuleAttribute.getCountCredit() >= dhmRuleAttribute.getAmountOfCreditRequired()) {
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
        dhmRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplomaHotelManagement", "Joined");
    }

    public static boolean isJoinProgramme() {
        return dhmRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch (mainQualificationLevel) {
            case "SPM": {
                dhmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSPM().getAmountOfCreditRequired());
                dhmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getSPM().getMinimumCreditGrade());
                dhmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDhm().getSPM().isGotRequiredSubject());

                if (dhmRuleAttribute.isGotRequiredSubject()) {
                    dhmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSPM().getWhatSubjectRequired().getSubject());
                    dhmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    dhmRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    dhmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level": {
                dhmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getOLevel().getAmountOfCreditRequired());
                dhmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getOLevel().getMinimumCreditGrade());
                dhmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDhm().getOLevel().isGotRequiredSubject());

                if (dhmRuleAttribute.isGotRequiredSubject()) {
                    dhmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getOLevel().getWhatSubjectRequired().getSubject());
                    dhmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    dhmRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    dhmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC": {
                dhmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getUEC().getAmountOfCreditRequired());
                dhmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getUEC().getMinimumCreditGrade());
                dhmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDhm().getUEC().isGotRequiredSubject());

                if (dhmRuleAttribute.isGotRequiredSubject()) {
                    dhmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getUEC().getWhatSubjectRequired().getSubject());
                    dhmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    dhmRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    dhmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM": {
                dhmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getAmountOfCreditRequired());
                dhmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getMinimumCreditGrade());
                dhmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().isGotRequiredSubject());

                if (dhmRuleAttribute.isGotRequiredSubject()) {
                    dhmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getWhatSubjectRequired().getSubject());
                    dhmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    dhmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dhmRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().isNeedSupportiveQualification());
                if (dhmRuleAttribute.isNeedSupportiveQualification()) {
                    dhmRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getWhatSupportiveSubject().getSubject());
                    dhmRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dhmRuleAttribute.initializeIntegerSupportiveGrade();
                    dhmRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dhmRuleAttribute.getSupportiveGradeRequired());
                    dhmRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level": {
                dhmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getAmountOfCreditRequired());
                dhmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getMinimumCreditGrade());
                dhmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().isGotRequiredSubject());

                if (dhmRuleAttribute.isGotRequiredSubject()) {
                    dhmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getWhatSubjectRequired().getSubject());
                    dhmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    dhmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dhmRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().isNeedSupportiveQualification());
                if (dhmRuleAttribute.isNeedSupportiveQualification()) {
                    dhmRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getWhatSupportiveSubject().getSubject());
                    dhmRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dhmRuleAttribute.initializeIntegerSupportiveGrade();
                    dhmRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dhmRuleAttribute.getSupportiveGradeRequired());
                    dhmRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getALevel().getAmountOfSupportiveSubjectRequired());

                }
            }
            break;
            case "STAM": {
                dhmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getAmountOfCreditRequired());
                dhmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getMinimumCreditGrade());
                dhmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().isGotRequiredSubject());

                if (dhmRuleAttribute.isGotRequiredSubject()) {
                    dhmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getWhatSubjectRequired().getSubject());
                    dhmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    dhmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dhmRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().isNeedSupportiveQualification());
                if (dhmRuleAttribute.isNeedSupportiveQualification()) {
                    dhmRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getWhatSupportiveSubject().getSubject());
                    dhmRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dhmRuleAttribute.initializeIntegerSupportiveGrade();
                    dhmRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dhmRuleAttribute.getSupportiveGradeRequired());
                    dhmRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDhm().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
