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

@Rule(name = "DBM", description = "Entry rule to join Diploma in Business Management")
public class DBM
{
    private static RuleAttribute dbmRuleAttribute;

    public DBM() { dbmRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (dbmRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < dbmRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], dbmRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= dbmRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            dbmRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                        if (Objects.equals("Mathematics", dbmRuleAttribute.getSubjectRequired().get(j)))
                        {
                            if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                            {
                                for(int k = 0; k < studentSubjects.length; k++)
                                {
                                    if(studentGrades[k] <= dbmRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                    {
                                        dbmRuleAttribute.incrementCountCorrectSubjectRequired();
                                    }
                                }
                            }
                        }
                        if (Objects.equals("Science / Technical / Vocational", dbmRuleAttribute.getSubjectRequired().get(j)))
                        {
                            if (Arrays.asList(dbmRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                            {
                                if (studentGrades[i] <= dbmRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    dbmRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if (dbmRuleAttribute.isNeedSupportiveQualification()) {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < dbmRuleAttribute.getSupportiveSubjectRequired().size(); j++) {
                switch (dbmRuleAttribute.getSupportiveSubjectRequired().get(j)) {
                    case "Bahasa Malaysia": {
                        if (supportiveGrades[0] <= dbmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dbmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English": {
                        if (supportiveGrades[1] <= dbmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dbmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics": {
                        if (supportiveGrades[2] <= dbmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dbmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics": {
                        if (supportiveGrades[3] <= dbmRuleAttribute.getSupportiveIntegerGradeRequired().get(j)) {
                            dbmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= dbmRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dbmRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= dbmRuleAttribute.getMinimumCreditGrade())
                dbmRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (dbmRuleAttribute.isGotRequiredSubject()) {
            // Check subject required is fulfill or not
            if (dbmRuleAttribute.getCountCorrectSubjectRequired() >= dbmRuleAttribute.getAmountOfSubjectRequired()) {
                // Check need supportive qualification or not
                if (dbmRuleAttribute.isNeedSupportiveQualification()) {
                    // If need, check whether it fulfill the supportive grade or not
                    if (dbmRuleAttribute.getCountSupportiveSubjectRequired() >= dbmRuleAttribute.getAmountOfSupportiveSubjectRequired()) {
                        // Check enough amount of credit or not
                        if (dbmRuleAttribute.getCountCredit() >= dbmRuleAttribute.getAmountOfCreditRequired()) {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else {
                    // Check enough amount of credit or not
                    if (dbmRuleAttribute.getCountCredit() >= dbmRuleAttribute.getAmountOfCreditRequired()) {
                        return true; // Return true as requirements is satisfied
                    }
                }
            }
        }
        else { // No subject required
            // Check need supportive qualification or not
            if (dbmRuleAttribute.isNeedSupportiveQualification()) {
                // If need, check whether it fulfill the supportive grade or not
                if (dbmRuleAttribute.getCountSupportiveSubjectRequired() >= dbmRuleAttribute.getAmountOfSupportiveSubjectRequired()) {
                    // Check enough amount of credit or not
                    if (dbmRuleAttribute.getCountCredit() >= dbmRuleAttribute.getAmountOfCreditRequired()) {
                        return true; // Return true as requirements is satisfied
                    }
                }
            }
            else {
                // Check enough amount of credit or not
                if (dbmRuleAttribute.getCountCredit() >= dbmRuleAttribute.getAmountOfCreditRequired()) {
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
        dbmRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplBusinessManagement", "Joined");
    }

    public static boolean isJoinProgramme() {
        return dbmRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch (mainQualificationLevel) {
            case "SPM": {
                dbmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSPM().getAmountOfCreditRequired());
                dbmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getSPM().getMinimumCreditGrade());
                dbmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDbm().getSPM().isGotRequiredSubject());

                if (dbmRuleAttribute.isGotRequiredSubject()) {
                    dbmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSPM().getWhatSubjectRequired().getSubject());
                    dbmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    dbmRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    dbmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level": {
                dbmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getOLevel().getAmountOfCreditRequired());
                dbmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getOLevel().getMinimumCreditGrade());
                dbmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDbm().getOLevel().isGotRequiredSubject());

                if (dbmRuleAttribute.isGotRequiredSubject()) {
                    dbmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getOLevel().getWhatSubjectRequired().getSubject());
                    dbmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    dbmRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    dbmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC": {
                dbmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getUEC().getAmountOfCreditRequired());
                dbmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getUEC().getMinimumCreditGrade());
                dbmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDbm().getUEC().isGotRequiredSubject());

                if (dbmRuleAttribute.isGotRequiredSubject()) {
                    dbmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getUEC().getWhatSubjectRequired().getSubject());
                    dbmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    dbmRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    dbmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM": {
                dbmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getAmountOfCreditRequired());
                dbmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getMinimumCreditGrade());
                dbmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().isGotRequiredSubject());

                if (dbmRuleAttribute.isGotRequiredSubject()) {
                    dbmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getWhatSubjectRequired().getSubject());
                    dbmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    dbmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dbmRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().isNeedSupportiveQualification());
                if (dbmRuleAttribute.isNeedSupportiveQualification()) {
                    dbmRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getWhatSupportiveSubject().getSubject());
                    dbmRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dbmRuleAttribute.initializeIntegerSupportiveGrade();
                    dbmRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dbmRuleAttribute.getSupportiveGradeRequired());
                    dbmRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level": {
                dbmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getAmountOfCreditRequired());
                dbmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getMinimumCreditGrade());
                dbmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().isGotRequiredSubject());

                if (dbmRuleAttribute.isGotRequiredSubject()) {
                    dbmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getWhatSubjectRequired().getSubject());
                    dbmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    dbmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dbmRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().isNeedSupportiveQualification());
                if (dbmRuleAttribute.isNeedSupportiveQualification()) {
                    dbmRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getWhatSupportiveSubject().getSubject());
                    dbmRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dbmRuleAttribute.initializeIntegerSupportiveGrade();
                    dbmRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dbmRuleAttribute.getSupportiveGradeRequired());
                    dbmRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM": {
                dbmRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getAmountOfCreditRequired());
                dbmRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getMinimumCreditGrade());
                dbmRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().isGotRequiredSubject());

                if (dbmRuleAttribute.isGotRequiredSubject()) {
                    dbmRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getWhatSubjectRequired().getSubject());
                    dbmRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    dbmRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                dbmRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().isNeedSupportiveQualification());
                if (dbmRuleAttribute.isNeedSupportiveQualification()) {
                    dbmRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getWhatSupportiveSubject().getSubject());
                    dbmRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    dbmRuleAttribute.initializeIntegerSupportiveGrade();
                    dbmRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, dbmRuleAttribute.getSupportiveGradeRequired());
                    dbmRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDbm().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
