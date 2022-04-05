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

@Rule(name = "CorporateComm", description = "Entry rule to join Bachelor of Corporate Communication")
public class CorporateComm
{
    private static RuleAttribute corporateCommRuleAttribute;

    public CorporateComm() { corporateCommRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (corporateCommRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < corporateCommRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], corporateCommRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= corporateCommRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            corporateCommRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", corporateCommRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= corporateCommRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    corporateCommRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", corporateCommRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(corporateCommRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= corporateCommRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                corporateCommRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(corporateCommRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < corporateCommRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(corporateCommRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= corporateCommRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= corporateCommRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= corporateCommRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= corporateCommRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= corporateCommRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(corporateCommRuleAttribute.isExempted())
        {
            for(int i = 0; i < corporateCommRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(corporateCommRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(corporateCommRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(corporateCommRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(corporateCommRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            corporateCommRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= corporateCommRuleAttribute.getMinimumCreditGrade())
                corporateCommRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (corporateCommRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(corporateCommRuleAttribute.getCountCorrectSubjectRequired() >= corporateCommRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(corporateCommRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(corporateCommRuleAttribute.getCountSupportiveSubjectRequired() >= corporateCommRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(corporateCommRuleAttribute.getCountCredit() >= corporateCommRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(corporateCommRuleAttribute.getCountCredit() >= corporateCommRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(corporateCommRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(corporateCommRuleAttribute.getCountSupportiveSubjectRequired() >= corporateCommRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(corporateCommRuleAttribute.getCountCredit() >= corporateCommRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(corporateCommRuleAttribute.getCountCredit() >= corporateCommRuleAttribute.getAmountOfCreditRequired())
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
        corporateCommRuleAttribute.setJoinProgrammeTrue();
        Log.d("CorporateComm", "Joined");
    }

    public static boolean isJoinProgramme() { return corporateCommRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                corporateCommRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getUEC().getAmountOfCreditRequired());
                corporateCommRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getUEC().getMinimumCreditGrade());
                corporateCommRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getUEC().isGotRequiredSubject());

                if(corporateCommRuleAttribute.isGotRequiredSubject()) {
                    corporateCommRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getUEC().getWhatSubjectRequired().getSubject());
                    corporateCommRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    corporateCommRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    corporateCommRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                corporateCommRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getAmountOfCreditRequired());
                corporateCommRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getMinimumCreditGrade());
                corporateCommRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().isGotRequiredSubject());
                corporateCommRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().isExempted());

                if(corporateCommRuleAttribute.isGotRequiredSubject()) {
                    corporateCommRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getWhatSubjectRequired().getSubject());
                    corporateCommRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    corporateCommRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                corporateCommRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().isNeedSupportiveQualification());
                if(corporateCommRuleAttribute.isNeedSupportiveQualification()) {
                    corporateCommRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getWhatSupportiveSubject().getSubject());
                    corporateCommRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getWhatSupportiveGrade().getGrade());
                    corporateCommRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    corporateCommRuleAttribute.initializeIntegerSupportiveGrade();
                    corporateCommRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, corporateCommRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                corporateCommRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getAmountOfCreditRequired());
                corporateCommRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getMinimumCreditGrade());
                corporateCommRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().isGotRequiredSubject());
                corporateCommRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().isExempted());

                if(corporateCommRuleAttribute.isGotRequiredSubject()) {
                    corporateCommRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getWhatSubjectRequired().getSubject());
                    corporateCommRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    corporateCommRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                corporateCommRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().isNeedSupportiveQualification());
                if(corporateCommRuleAttribute.isNeedSupportiveQualification()) {
                    corporateCommRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getWhatSupportiveSubject().getSubject());
                    corporateCommRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    corporateCommRuleAttribute.initializeIntegerSupportiveGrade();
                    corporateCommRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, corporateCommRuleAttribute.getSupportiveGradeRequired());
                    corporateCommRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                corporateCommRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getAmountOfCreditRequired());
                corporateCommRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getMinimumCreditGrade());
                corporateCommRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().isGotRequiredSubject());
                corporateCommRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().isExempted());

                if(corporateCommRuleAttribute.isGotRequiredSubject()) {
                    corporateCommRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getWhatSubjectRequired().getSubject());
                    corporateCommRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    corporateCommRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                corporateCommRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().isNeedSupportiveQualification());
                if(corporateCommRuleAttribute.isNeedSupportiveQualification()) {
                    corporateCommRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getWhatSupportiveSubject().getSubject());
                    corporateCommRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    corporateCommRuleAttribute.initializeIntegerSupportiveGrade();
                    corporateCommRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, corporateCommRuleAttribute.getSupportiveGradeRequired());
                    corporateCommRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getCorporateComm().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
