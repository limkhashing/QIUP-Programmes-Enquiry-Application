package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WhatSupportiveGrade{

	@SerializedName("grade")
	private List<String> grade;

	public List<String> getGrade(){
		return grade;
	}

}