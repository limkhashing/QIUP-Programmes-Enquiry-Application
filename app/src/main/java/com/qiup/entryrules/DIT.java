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

@Rule(name = "DIT", description = "Entry rule to join Diploma in Information Technology")
public class DIT
{
    private static RuleAttribute ditRuleAttribute;

    public DIT() { ditRuleAttribute = new RuleAttribute();}

    @Condition
    public boolean allowToJoin(@Fact("Qualification Level") String qualificationLevel
            , @Fact("Student's Subjects")String[] studentSubjects
            , @Fact("Student's Grades") int[] studentGrades
            , @Fact("Student's SPM or O-Level") String supportiveQualificationLevel
            , @Fact("Student's Supportive Grades") int[] supportiveGrades)
    {
        setJSONAttribute(qualificationLevel, supportiveQualificationLevel); // First set json attribute to the rule

        // Check got required subject or not.
        if (ditRuleAttribute.isGotRequiredSubject())
        {
            // If got, check whether the subject's grade is smaller or equal to the required subject's grade
            for (int i = 0; i < studentSubjects.length; i++)
            {
                for (int j = 0; j < ditRuleAttribute.getSubjectRequired().size(); j++)
                {
                    if (Objects.equals(studentSubjects[i], ditRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (studentGrades[i] <= ditRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                        {
                            ditRuleAttribute.incrementCountCorrectSubjectRequired();
                        }
                    }
                    if (Objects.equals("Mathematics", ditRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if(Arrays.asList(studentSubjects).contains("Additional Mathematics"))
                        {
                            for(int k = 0; k < studentSubjects.length; k++)
                            {
                                if(studentGrades[k] <= ditRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                                {
                                    ditRuleAttribute.incrementCountCorrectSubjectRequired();
                                }
                            }
                        }
                    }
                    if (Objects.equals("Science / Technical / Vocational", ditRuleAttribute.getSubjectRequired().get(j)))
                    {
                        if (Arrays.asList(ditRuleAttribute.getScienceTechnicalVocationalSubjectArrays()).contains(studentSubjects[i]))
                        {
                            if (studentGrades[i] <= ditRuleAttribute.getMinimumSubjectRequiredGrade().get(j))
                            {
                                ditRuleAttribute.incrementCountCorrectSubjectRequired();
                            }
                        }
                    }
                }
            }
        }

        // Check need supportive qualification or not
        if(ditRuleAttribute.isNeedSupportiveQualification())
        {
            // If need, check whether the supportive subject's grade is smaller or equal to the required supportive subject's grade
            for (int j = 0; j < ditRuleAttribute.getSupportiveSubjectRequired().size(); j++)
            {
                switch(ditRuleAttribute.getSupportiveSubjectRequired().get(j))
                {
                    case "Bahasa Malaysia":
                    {
                        if (supportiveGrades[0] <= ditRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            ditRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "English":
                    {
                        if (supportiveGrades[1] <= ditRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            ditRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Mathematics":
                    {
                        if (supportiveGrades[2] <= ditRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            ditRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                        else if (supportiveGrades[3] <= ditRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            ditRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Additional Mathematics":
                    {
                        if (supportiveGrades[3] <= ditRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            ditRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                    case "Science / Technical / Vocational":
                    {
                        if (supportiveGrades[4] <= ditRuleAttribute.getSupportiveIntegerGradeRequired().get(j))
                        {
                            ditRuleAttribute.incrementCountSupportiveSubjectRequired();
                        }
                    }
                    break;
                }
            }

        }

        // For every grade, check whether the grade is smaller or equal to minimum credit grade
        // Smaller the number the better the grade
        for (int i = 0; i < studentGrades.length; i++) {
            if (studentGrades[i] <= ditRuleAttribute.getMinimumCreditGrade())
                ditRuleAttribute.incrementCountCredit();
        }

        // Checking Requirements see whether can return true or not
        if (ditRuleAttribute.isGotRequiredSubject())
        {
            // Check subject required is fulfill or not
            if(ditRuleAttribute.getCountCorrectSubjectRequired() >= ditRuleAttribute.getAmountOfSubjectRequired())
            {
                // Check need supportive qualification or not
                if(ditRuleAttribute.isNeedSupportiveQualification())
                {
                    // If need, check whether it fulfill the supportive grade or not
                    if(ditRuleAttribute.getCountSupportiveSubjectRequired() >= ditRuleAttribute.getAmountOfSupportiveSubjectRequired())
                    {
                        // Check enough amount of credit or not
                        if(ditRuleAttribute.getCountCredit() >= ditRuleAttribute.getAmountOfCreditRequired())
                        {
                            return true; // return true as requirements is satisfied
                        }
                    }
                }
                else
                {
                    // Check enough amount of credit or not
                    if(ditRuleAttribute.getCountCredit() >= ditRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
        }
        else // No subject required
        {
            // Check need supportive qualification or not
            if(ditRuleAttribute.isNeedSupportiveQualification())
            {
                // If need, check whether it fulfill the supportive grade or not
                if(ditRuleAttribute.getCountSupportiveSubjectRequired() >= ditRuleAttribute.getAmountOfSupportiveSubjectRequired())
                {
                    // Check enough amount of credit or not
                    if(ditRuleAttribute.getCountCredit() >= ditRuleAttribute.getAmountOfCreditRequired())
                    {
                        return true; // return true as requirements is satisfied
                    }
                }
            }
            else
            {
                // Check enough amount of credit or not
                if(ditRuleAttribute.getCountCredit() >= ditRuleAttribute.getAmountOfCreditRequired())
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
        // if rule is satisfied (return true), then this action will be executed
        ditRuleAttribute.setJoinProgrammeTrue();
        Log.d("DiplomaIT", "Joined");
    }

    public static boolean isJoinProgramme()
    {
        return ditRuleAttribute.isJoinProgramme();
    }

    private void setJSONAttribute(String mainQualificationLevel, String supportiveQualificationLevel) {
        switch(mainQualificationLevel)
        {
            case "SPM":
            {
                ditRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSPM().getAmountOfCreditRequired());
                ditRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getSPM().getMinimumCreditGrade());
                ditRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDit().getSPM().isGotRequiredSubject());

                if(ditRuleAttribute.isGotRequiredSubject()) {
                    ditRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSPM().getWhatSubjectRequired().getSubject());
                    ditRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getSPM().getMinimumSubjectRequiredGrade().getGrade());
                    ditRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.spm_science_technical_vocational_subject));
                    ditRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSPM().getAmountOfSubjectRequired());
                }
            }
            break;
            case "O-Level":
            {
                ditRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getOLevel().getAmountOfCreditRequired());
                ditRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getOLevel().getMinimumCreditGrade());
                ditRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDit().getOLevel().isGotRequiredSubject());

                if(ditRuleAttribute.isGotRequiredSubject()) {
                    ditRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getOLevel().getWhatSubjectRequired().getSubject());
                    ditRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getOLevel().getMinimumSubjectRequiredGrade().getGrade());
                    ditRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.oLevel_science_technical_vocational_subject));
                    ditRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getOLevel().getAmountOfSubjectRequired());
                }
            }
            break;
            case "UEC":
            {
                ditRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getUEC().getAmountOfCreditRequired());
                ditRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getUEC().getMinimumCreditGrade());
                ditRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDit().getUEC().isGotRequiredSubject());

                if(ditRuleAttribute.isGotRequiredSubject()) {
                    ditRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getUEC().getWhatSubjectRequired().getSubject());
                    ditRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getUEC().getMinimumSubjectRequiredGrade().getGrade());
                    ditRuleAttribute.setScienceTechnicalVocationalSubjectArrays(MyContext.getContext().getResources().getStringArray(R.array.uecLevel_science_technical_vocational_subject));
                    ditRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getUEC().getAmountOfSubjectRequired());
                }
            }
            case "STPM":
            {
                ditRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getAmountOfCreditRequired());
                ditRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getMinimumCreditGrade());
                ditRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().isGotRequiredSubject());

                if(ditRuleAttribute.isGotRequiredSubject()) {
                    ditRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getWhatSubjectRequired().getSubject());
                    ditRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getMinimumSubjectRequiredGrade().getGrade());
                    ditRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                ditRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().isNeedSupportiveQualification());
                if(ditRuleAttribute.isNeedSupportiveQualification()) {
                    ditRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getWhatSupportiveSubject().getSubject());
                    ditRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    ditRuleAttribute.initializeIntegerSupportiveGrade();
                    ditRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, ditRuleAttribute.getSupportiveGradeRequired());
                    ditRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTPM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "A-Level":
            {
                ditRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getAmountOfCreditRequired());
                ditRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getMinimumCreditGrade());
                ditRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().isGotRequiredSubject());

                if(ditRuleAttribute.isGotRequiredSubject()) {
                    ditRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getWhatSubjectRequired().getSubject());
                    ditRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getMinimumSubjectRequiredGrade().getGrade());
                    ditRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getAmountOfSubjectRequired());
                }

                // Get supportive things
                ditRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().isNeedSupportiveQualification());
                if(ditRuleAttribute.isNeedSupportiveQualification()) {
                    ditRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getWhatSupportiveSubject().getSubject());
                    ditRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    ditRuleAttribute.initializeIntegerSupportiveGrade();
                    ditRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, ditRuleAttribute.getSupportiveGradeRequired());
                    ditRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getALevel().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
            case "STAM":
            {
                ditRuleAttribute.setAmountOfCreditRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getAmountOfCreditRequired());
                ditRuleAttribute.setMinimumCreditGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getMinimumCreditGrade());
                ditRuleAttribute.setGotRequiredSubject(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().isGotRequiredSubject());

                if(ditRuleAttribute.isGotRequiredSubject()) {
                    ditRuleAttribute.setSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getWhatSubjectRequired().getSubject());
                    ditRuleAttribute.setMinimumSubjectRequiredGrade(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getMinimumSubjectRequiredGrade().getGrade());
                    ditRuleAttribute.setAmountOfSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getAmountOfSubjectRequired());
                }

                // Get supportive things
                ditRuleAttribute.setNeedSupportiveQualification(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().isNeedSupportiveQualification());
                if(ditRuleAttribute.isNeedSupportiveQualification()) {
                    ditRuleAttribute.setSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getWhatSupportiveSubject().getSubject());
                    ditRuleAttribute.setSupportiveGradeRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getWhatSupportiveGrade().getGrade());

                    // Convert supportive grade to Integer
                    ditRuleAttribute.initializeIntegerSupportiveGrade();
                    ditRuleAttribute.convertSupportiveGradeToInteger(supportiveQualificationLevel, ditRuleAttribute.getSupportiveGradeRequired());
                    ditRuleAttribute.setAmountOfSupportiveSubjectRequired(RulePojo.getRulePojo().getAllProgramme().getDit().getSTAM().getAmountOfSupportiveSubjectRequired());
                }
            }
            break;
        }
    }
}
