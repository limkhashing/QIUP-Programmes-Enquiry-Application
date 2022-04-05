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

@Rule(name = "MBBS", description = "Entry rule to join Bachelor of Medicine & Bachelor of Surgery ")
public class MBBS
{
    private static RuleAttribute mbbsRuleAttribute;

    public MBBS() { mbbsRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (mbbsRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < mbbsRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], mbbsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= mbbsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            mbbsRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", mbbsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= mbbsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    mbbsRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", mbbsRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(mbbsRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= mbbsRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                mbbsRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(mbbsRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < mbbsRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(mbbsRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= mbbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= mbbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= mbbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= mbbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= mbbsRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(mbbsRuleAttribute.isExempted())
        {
            for(int i = 0; i < mbbsRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(mbbsRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(mbbsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(mbbsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(mbbsRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            mbbsRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= mbbsRuleAttribute.getMinimumCreditGrade())
                mbbsRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (mbbsRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(mbbsRuleAttribute.getCountCorrectSubjectRequired() >= mbbsRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(mbbsRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(mbbsRuleAttribute.getCountSupportiveSubjectRequired() >= mbbsRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(mbbsRuleAttribute.getCountCredit() >= mbbsRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(mbbsRuleAttribute.getCountCredit() >= mbbsRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(mbbsRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(mbbsRuleAttribute.getCountSupportiveSubjectRequired() >= mbbsRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(mbbsRuleAttribute.getCountCredit() >= mbbsRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(mbbsRuleAttribute.getCountCredit() >= mbbsRuleAttribute.getAmountOfCreditRequired())
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
        mbbsRuleAttribute.setJoinProgrammeTrue();
        Log.d("MBBS", "Joined");
    }

    public static boolean isJoinProgramme() { return mbbsRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                mbbsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getUEC().getAmountOfCreditRequired());
                mbbsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMbbs().getUEC().getMinimumCreditGrade());
                mbbsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMbbs().getUEC().isGotRequiredSubject());

                if(mbbsRuleAttribute.isGotRequiredSubject()) {
                    mbbsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getUEC().getWhatSubjectRequired().getSubject());
                    mbbsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMbbs().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    mbbsRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    mbbsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                mbbsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getAmountOfCreditRequired());
                mbbsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getMinimumCreditGrade());
                mbbsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().isGotRequiredSubject());
                mbbsRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().isExempted());

                if(mbbsRuleAttribute.isGotRequiredSubject()) {
                    mbbsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getWhatSubjectRequired().getSubject());
                    mbbsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    mbbsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                mbbsRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().isNeedSupportiveQualification());
                if(mbbsRuleAttribute.isNeedSupportiveQualification()) {
                    mbbsRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getWhatSupportiveSubject().getSubject());
                    mbbsRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getWhatSupportiveGrade().getGrade());
                    mbbsRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    mbbsRuleAttribute.initializeIntegerSupportiveGrade();
                    mbbsRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, mbbsRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                mbbsRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getAmountOfCreditRequired());
                mbbsRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getMinimumCreditGrade());
                mbbsRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().isGotRequiredSubject());
                mbbsRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().isExempted());

                if(mbbsRuleAttribute.isGotRequiredSubject()) {
                    mbbsRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getWhatSubjectRequired().getSubject());
                    mbbsRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    mbbsRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                mbbsRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().isNeedSupportiveQualification());
                if(mbbsRuleAttribute.isNeedSupportiveQualification()) {
                    mbbsRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getWhatSupportiveSubject().getSubject());
                    mbbsRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    mbbsRuleAttribute.initializeIntegerSupportiveGrade();
                    mbbsRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, mbbsRuleAttribute.getSupportiveGradeRequired());
                    mbbsRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getMbbs().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
