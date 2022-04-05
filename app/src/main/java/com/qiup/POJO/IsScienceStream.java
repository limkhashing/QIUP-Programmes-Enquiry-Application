package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class IsScienceStream{

	@SerializedName("minimum_subject_required_grade")
	private MinimumSubjectRequiredGrade minimumSubjectRequiredGrade;

	@SerializedName("minimum_required_science_subject")
	private int minimumRequiredScienceSubject;

	public MinimumSubjectRequiredGrade getMinimumSubjectRequiredGrade(){
		return minimumSubjectRequiredGrade;
	}

	public int getMinimumRequiredScienceSubject(){
		return minimumRequiredScienceSubject;
	}
}