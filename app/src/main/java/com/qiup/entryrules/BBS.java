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

@Rule(name = "BBS", description = "Entry rule to join Bachelor of Biomedical Sciences")
public class BBS
{
    private static RuleAttribute bbsRuleAttribute;

    public BBS() { bbsRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects") String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bbsRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bbsRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bbsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bbsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bbsRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bbsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bbsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bbsRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bbsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bbsRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bbsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bbsRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bbsRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bbsRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bbsRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bbsRuleAttribute.isExempted())
        {
            for(int i = 0; i < bbsRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bbsRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bbsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 6) // a-level pass grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 7) // stpm credit grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 4) // a-level credit grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bbsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 6) // a-level pass grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 7) // stpm credit grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 4) // a-level credit grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bbsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 6) // a-level pass grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 7) // stpm credit grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                            else if(Objects.equals(qualificationLevel, "A-Level"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 4) // a-level credit grade
                                        {
                                            bbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= bbsRuleAttribute.getMinimumCreditGrade())
                bbsRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bbsRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bbsRuleAttribute.getCountCorrectSubjectRequired() >= bbsRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bbsRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bbsRuleAttribute.getCountSupportiveSubjectRequired() >= bbsRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bbsRuleAttribute.getCountCredit() >= bbsRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bbsRuleAttribute.getCountCredit() >= bbsRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bbsRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bbsRuleAttribute.getCountSupportiveSubjectRequired() >= bbsRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bbsRuleAttribute.getCountCredit() >= bbsRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bbsRuleAttribute.getCountCredit() >= bbsRuleAttribute.getAmountOfCreditRequired())
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
        // If rule is satisfied (return true), this action will be executed
        bbsRuleAttribute.setJoinProgrammeTrue();
        Log.d("BBS", "Joined");
    }

    public static boolean isJoinProgramme() { return bbsRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bbsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getUEC().getAmountOfCreditRequired());
                bbsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBbs().getUEC().getMinimumCreditGrade());
                bbsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBbs().getUEC().isGotRequiredSubject());

                if(bbsRuleAttribute.isGotRequiredSubject()) {
                    bbsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getUEC().getWhatSubjectRequired().getSubject());
                    bbsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBbs().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bbsRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bbsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                bbsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getAmountOfCreditRequired());
                bbsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getMinimumCreditGrade());
                bbsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().isGotRequiredSubject());
                bbsRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().isExempted());

                if(bbsRuleAttribute.isGotRequiredSubject()) {
                    bbsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getWhatSubjectRequired().getSubject());
                    bbsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bbsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bbsRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().isNeedSupportiveQualification());
                if(bbsRuleAttribute.isNeedSupportiveQualification()) {
                    bbsRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getWhatSupportiveSubject().getSubject());
                    bbsRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getWhatSupportiveGrade().getGrade());
                    bbsRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bbsRuleAttribute.initializeIntegerSupportiveGrade();
                    bbsRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bbsRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bbsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getAmountOfCreditRequired());
                bbsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getMinimumCreditGrade());
                bbsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().isGotRequiredSubject());
                bbsRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().isExempted());

                if(bbsRuleAttribute.isGotRequiredSubject()) {
                    bbsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getWhatSubjectRequired().getSubject());
                    bbsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bbsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bbsRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().isNeedSupportiveQualification());
                if(bbsRuleAttribute.isNeedSupportiveQualification()) {
                    bbsRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getWhatSupportiveSubject().getSubject());
                    bbsRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bbsRuleAttribute.initializeIntegerSupportiveGrade();
                    bbsRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bbsRuleAttribute.getSupportiveGradeRequired());
                    bbsRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBbs().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
