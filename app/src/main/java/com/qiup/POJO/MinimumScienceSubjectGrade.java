package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MinimumScienceSubjectGrade{

	@SerializedName("grade")
	private List<Integer> grade;

	public List<Integer> getGrade(){
		return grade;
	}

}