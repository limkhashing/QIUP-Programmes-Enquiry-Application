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

@Rule(name = "BioTech", description = "Entry rule to join Bachelor of Science in Biotechnology")
public class BioTech
{
    private static RuleAttribute bioTechRuleAttribute;

    public BioTech() { bioTechRuleAttribute = new RuleAttribute(); }
    
    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (bioTechRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < bioTechRuleAttribute.getSubjectRequired().size(); i++)
            {
                for (int j = 0; j < studentSubjects.length; j++)
                {
                    if (Objects.equals(studentSubjects[j], bioTechRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (studentGrades[j] <= bioTechRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                        {
                            bioTechRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", bioTechRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= bioTechRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                                {
                                    bioTechRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", bioTechRuleAttribute.getSubjectRequired().get(i)))
                    {
                        if (Arrays.asList(bioTechRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[j]))
                        {
                            if (studentGrades[j] <= bioTechRuleAttribute.getMinimumSubjectRequiredGrade().get(i))
                            {
                                bioTechRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(bioTechRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < bioTechRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(bioTechRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= bioTechRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= bioTechRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= bioTechRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= bioTechRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= bioTechRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }
        }

        // If can see higher qualification to waive subject or not
        if(bioTechRuleAttribute.isExempted())
        {
            for(int i = 0; i < bioTechRuleAttribute.getSupportiveSubjectRequired().size(); i++)
            {
                switch(bioTechRuleAttribute.getSupportiveSubjectRequired().get(i))
                {
                    case "English":
                    {
                        if(Objects.equals(bioTechRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "English"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if(Objects.equals(bioTechRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Mathematics") || Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if(Objects.equals(bioTechRuleAttribute.getSupportiveGradeRequired().get(i), "Pass"))
                        {
                            if(Objects.equals(qualificationLevel, "STPM"))
                            {
                                for(int j = 0; j < studentSubjects.length; j++)
                                {
                                    if(Objects.equals(studentSubjects[j], "Additional Mathematics"))
                                    {
                                        if(studentGrades[j] <= 10) // stpm pass grade
                                        {
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
                                            bioTechRuleAttribute.incrementCountSupportiveSubjectRequired();
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
            if (studentGrades[i] <= bioTechRuleAttribute.getMinimumCreditGrade())
                bioTechRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (bioTechRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(bioTechRuleAttribute.getCountCorrectSubjectRequired() >= bioTechRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(bioTechRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(bioTechRuleAttribute.getCountSupportiveSubjectRequired() >= bioTechRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(bioTechRuleAttribute.getCountCredit() >= bioTechRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(bioTechRuleAttribute.getCountCredit() >= bioTechRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(bioTechRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(bioTechRuleAttribute.getCountSupportiveSubjectRequired() >= bioTechRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(bioTechRuleAttribute.getCountCredit() >= bioTechRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(bioTechRuleAttribute.getCountCredit() >= bioTechRuleAttribute.getAmountOfCreditRequired())
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
        bioTechRuleAttribute.setJoinProgrammeTrue();
        Log.d("BioTechjoinProgramme", "Joined");
    }

    public static boolean isJoinProgramme() { return bioTechRuleAttribute.isJoinProgramme(); }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "UEC":
            {
                bioTechRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getUEC().getAmountOfCreditRequired());
                bioTechRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBioTech().getUEC().getMinimumCreditGrade());
                bioTechRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBioTech().getUEC().isGotRequiredSubject());

                if(bioTechRuleAttribute.isGotRequiredSubject()) {
                    bioTechRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getUEC().getWhatSubjectRequired().getSubject());
                    bioTechRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBioTech().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    bioTechRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    bioTechRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getUEC().getAmountOfSubjectRequired());
                }
            }
            break;
            case "STPM":
            {
                bioTechRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getAmountOfCreditRequired());
                bioTechRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getMinimumCreditGrade());
                bioTechRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().isGotRequiredSubject());
                bioTechRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().isExempted());

                if(bioTechRuleAttribute.isGotRequiredSubject()) {
                    bioTechRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getWhatSubjectRequired().getSubject());
                    bioTechRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    bioTechRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bioTechRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().isNeedSupportiveQualification());
                if(bioTechRuleAttribute.isNeedSupportiveQualification()) {
                    bioTechRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getWhatSupportiveSubject().getSubject());
                    bioTechRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getWhatSupportiveGrade().getGrade());
                    bioTechRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getSTPM().getAmountOfSupportiveSubjectRequired());

                    // Convert supportive grade to Integer
                    bioTechRuleAttribute.initializeIntegerSupportiveGrade();
                    bioTechRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bioTechRuleAttribute.getSupportiveGradeRequired());
                }
            }
            break;
            case "A-Level":
            {
                bioTechRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getAmountOfCreditRequired());
                bioTechRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getMinimumCreditGrade());
                bioTechRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().isGotRequiredSubject());
                bioTechRuleAttribute.setExempted(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().isExempted());

                if(bioTechRuleAttribute.isGotRequiredSubject()) {
                    bioTechRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getWhatSubjectRequired().getSubject());
                    bioTechRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    bioTechRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                bioTechRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().isNeedSupportiveQualification());
                if(bioTechRuleAttribute.isNeedSupportiveQualification()) {
                    bioTechRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getWhatSupportiveSubject().getSubject());
                    bioTechRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    bioTechRuleAttribute.initializeIntegerSupportiveGrade();
                    bioTechRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, bioTechRuleAttribute.getSupportiveGradeRequired());
                    bioTechRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getBioTech().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
