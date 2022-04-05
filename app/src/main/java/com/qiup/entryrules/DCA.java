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

@Rule(name = "DCA", description = "Entry rule to join Diploma in Culinary Arts")
public class DCA 
{
    private static RuleAttribute dcaRuleAttribute;

    public DCA() { dcaRuleAttribute = new RuleAttribute(); }

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (dcaRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < dcaRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], dcaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= dcaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            dcaRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", dcaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= dcaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    dcaRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", dcaRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(dcaRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= dcaRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                dcaRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(dcaRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < dcaRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(dcaRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= dcaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= dcaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= dcaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= dcaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= dcaRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(dcaRuleAttribute.isExempted())
        {
            for(int i = 0; i < dcaRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(dcaRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(dcaRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(dcaRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(dcaRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            dcaRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= dcaRuleAttribute.getMinimumCreditGrade())
                dcaRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (dcaRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(dcaRuleAttribute.getCountCorrectSubjectRequired() >= dcaRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(dcaRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(dcaRuleAttribute.getCountSupportiveSubjectRequired() >= dcaRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(dcaRuleAttribute.getCountCredit() >= dcaRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(dcaRuleAttribute.getCountCredit() >= dcaRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(dcaRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(dcaRuleAttribute.getCountSupportiveSubjectRequired() >= dcaRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(dcaRuleAttribute.getCountCredit() >= dcaRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(dcaRuleAttribute.getCountCredit() >= dcaRuleAttribute.getAmountOfCreditRequired())
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
        dcaRuleAttribute.setJoinProgrammeTrue();
        Log.d("DCAjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() {
        return dcaRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "SPM":
            {
                dcaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getSPM().getAmountOfCreditRequired());
                dcaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDca().getSPM().getMinimumCreditGrade());
                dcaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDca().getSPM().isGotRequiredSubject());

                if(dcaRuleAttribute.isGotRequiredSubject()) {
                    dcaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getSPM().getWhatSubjectRequired().getSubject());
                    dcaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDca().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    dcaRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    dcaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level":
            {
                dcaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getOLevel().getAmountOfCreditRequired());
                dcaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDca().getOLevel().getMinimumCreditGrade());
                dcaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDca().getOLevel().isGotRequiredSubject());

                if(dcaRuleAttribute.isGotRequiredSubject()) {
                    dcaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getOLevel().getWhatSubjectRequired().getSubject());
                    dcaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDca().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    dcaRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    dcaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;            
            case "UEC":
            {
                dcaRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getUEC().getAmountOfCreditRequired());
                dcaRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDca().getUEC().getMinimumCreditGrade());
                dcaRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDca().getUEC().isGotRequiredSubject());

                if(dcaRuleAttribute.isGotRequiredSubject()) {
                    dcaRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getUEC().getWhatSubjectRequired().getSubject());
                    dcaRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDca().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    dcaRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    dcaRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDca().getUEC().getAmountOfSubjectRequired());
                }
            }
            break;
        }
    }
}
