package com.qiup.entryrules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RuleAttribute
{
    // For general usage
    private int countCredit, countCorrectRequiredScienceSubject, countCorrectSubjectRequired,
            countSupportiveSubjectRequired;
    // private float minimumCGPA, minimumGP; // TODO for future matriculation / foundation
    private boolean joinProgramme;

    // JSON Attribute
    private String[] scienceTechnicalVocationalSubjectArrays;
    private int amountOfCreditRequired, minimumCreditGrade, minimumRequiredScienceSubject,
    amountOfSubjectRequired, amountOfSupportiveSubjectRequired;
    private boolean gotRequiredSubject, needSupportiveQualification, exempted;
    private List<String> subjectRequired; // WhatSubjectRequired
    private List<Integer> minimumSubjectRequiredGrade; // MinimumSubjectRequiredGrade
    private List<String> supportiveSubjectRequired; // WhatSupportiveSubject
    private List<String> supportiveGradeRequired; // WhatSupportiveGrade
    private List<Integer> supportiveIntegerGradeRequired; // Integer WhatSupportiveGrade
    private List<String> scienceSubjectRequired; // WhatScienceSubjectRequired
    private List<Integer> minimumScienceSubjectGradeRequired; // MinimumScienceSubjectGrade

    RuleAttribute() {
        joinProgramme = false;
        countCredit = 0;
        countCorrectRequiredScienceSubject = 0;
        countCorrectSubjectRequired = 0;
        countSupportiveSubjectRequired = 0;
    }

    public boolean isJoinProgramme() {
        return joinProgramme;
    }

    public void setJoinProgrammeTrue() {
        this.joinProgramme = true;
    }

    int getCountCredit() {
        return countCredit;
    }

    void incrementCountCredit() {
        this.countCredit++;
    }

    int getCountCorrectRequiredScienceSubject() {
        return countCorrectRequiredScienceSubject;
    }

    void incrementCountCorrectRequiredScienceSubject() {
        this.countCorrectRequiredScienceSubject++;
    }

    // JSON attribute
    public int getAmountOfCreditRequired() {
        return amountOfCreditRequired;
    }

    public void setAmountOfCreditRequired(int amountOfCreditRequired) {
        this.amountOfCreditRequired = amountOfCreditRequired;
    }

    public int getMinimumCreditGrade() {
        return minimumCreditGrade;
    }

    public void setMinimumCreditGrade(int minimumCreditGrade) {
        this.minimumCreditGrade = minimumCreditGrade;
    }

    public boolean isGotRequiredSubject() {
        return gotRequiredSubject;
    }

    public void setGotRequiredSubject(boolean gotRequiredSubject) {
        this.gotRequiredSubject = gotRequiredSubject;
    }

    public boolean isNeedSupportiveQualification() {
        return needSupportiveQualification;
    }

    public void setNeedSupportiveQualification(boolean needSupportiveQualification) {
        this.needSupportiveQualification = needSupportiveQualification;
    }

    int getMinimumRequiredScienceSubject() {
        return minimumRequiredScienceSubject;
    }

    void setMinimumRequiredScienceSubject(int minimumRequiredScienceSubject) {
        this.minimumRequiredScienceSubject = minimumRequiredScienceSubject;
    }

    public List<String> getSubjectRequired() {
        return subjectRequired;
    }

    public void setSubjectRequired(List<String> subjectRequired) {
        this.subjectRequired = subjectRequired;
    }

    public List<Integer> getMinimumSubjectRequiredGrade() {
        return minimumSubjectRequiredGrade;
    }

    public void setMinimumSubjectRequiredGrade(List<Integer> gradeRequired) {
        this.minimumSubjectRequiredGrade = gradeRequired;
    }

    public List<String> getSupportiveSubjectRequired() {
        return supportiveSubjectRequired;
    }

    public void setSupportiveSubjectRequired(List<String> supportiveSubjectRequired) {
        this.supportiveSubjectRequired = supportiveSubjectRequired;
    }

    public List<String> getSupportiveGradeRequired() {
        return supportiveGradeRequired;
    }

    public void setSupportiveGradeRequired(List<String> supportiveGradeRequired) {
        this.supportiveGradeRequired = supportiveGradeRequired;
    }

    List<String> getScienceSubjectRequired() {
        return scienceSubjectRequired;
    }

    void setScienceSubjectRequired(List<String> scienceSubjectRequired) {
        this.scienceSubjectRequired = scienceSubjectRequired;
    }

    List<Integer> getMinimumScienceSubjectGradeRequired() {
        return minimumScienceSubjectGradeRequired;
    }

    void setMinimumScienceSubjectGradeRequired(List<Integer> minimumScienceSubjectGradeRequired) {
        this.minimumScienceSubjectGradeRequired = minimumScienceSubjectGradeRequired;
    }

    public List<Integer> getSupportiveIntegerGradeRequired() {
        return supportiveIntegerGradeRequired;
    }

    private void setSupportiveIntegrGradeRequired(int gradeNumber) {
        supportiveIntegerGradeRequired.add(gradeNumber);
    }

    public String[] getScienceTechnicalVocationalSubjectArrays() {
        return scienceTechnicalVocationalSubjectArrays;
    }

    public void setScienceTechnicalVocationalSubjectArrays(String[] scienceTechnicalVocationalSubjectArrays) {
        this.scienceTechnicalVocationalSubjectArrays = scienceTechnicalVocationalSubjectArrays;
    }

    public int getAmountOfSubjectRequired() {
        return amountOfSubjectRequired;
    }

    public void setAmountOfSubjectRequired(int amountOfSubjectRequired) {
        this.amountOfSubjectRequired = amountOfSubjectRequired;
    }

    public int getAmountOfSupportiveSubjectRequired() {
        return amountOfSupportiveSubjectRequired;
    }

    public void setAmountOfSupportiveSubjectRequired(int amountOfSupportiveSubjectRequired) {
        this.amountOfSupportiveSubjectRequired = amountOfSupportiveSubjectRequired;
    }

    public boolean isExempted() { return exempted; }

    public void setExempted(boolean exempted) { this.exempted = exempted; }

    // For the length of required things
    public int getCountCorrectSubjectRequired() {
        return countCorrectSubjectRequired;
    }

    public void incrementCountCorrectSubjectRequired() {
        this.countCorrectSubjectRequired++;
    }

    public int getCountSupportiveSubjectRequired() {
        return countSupportiveSubjectRequired;
    }

    public void incrementCountSupportiveSubjectRequired() {
        this.countSupportiveSubjectRequired++;
    }

    // Convert supportive grade to Integer
    public void convertSupportiveGradeToInteger(String qualificationLevel, List<String> supportiveStringGrades) {
        if(Objects.equals(qualificationLevel, "SPM"))
        {
            for(int i = 0; i < supportiveStringGrades.size(); i++) {
                if(Objects.equals(supportiveStringGrades.get(i), "Credit")) {
                    setSupportiveIntegrGradeRequired(7); // If required supportive grade is credit, set it to 7
                }
                else if(Objects.equals(supportiveStringGrades.get(i), "Pass")) {
                    setSupportiveIntegrGradeRequired(9); // If required supportive grade is pass, set it to 9
                }
            }
        }
        else // is O-Level
        {
            for(int i = 0; i < supportiveStringGrades.size(); i++) {
                if(Objects.equals(supportiveStringGrades.get(i), "Credit")) {
                    setSupportiveIntegrGradeRequired(3); // If required supportive grade is credit, set it to 1
                }
                else if(Objects.equals(supportiveStringGrades.get(i), "Pass")) {
                    setSupportiveIntegrGradeRequired(7); // If required supportive grade is pass, set it to 2
                }
            }
        }
    }

   public void initializeIntegerSupportiveGrade() {
       supportiveIntegerGradeRequired = new ArrayList<>();
   }

}
