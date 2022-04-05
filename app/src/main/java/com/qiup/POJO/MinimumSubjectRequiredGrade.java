package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MinimumSubjectRequiredGrade{

	@SerializedName("grade")
	private List<Integer> grade;

	public List<Integer> getGrade(){
		return grade;
	}

}