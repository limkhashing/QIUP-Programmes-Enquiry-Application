package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class RulePojo{

	private static RulePojo rulePojo;

	@SerializedName("AllProgramme")
	private AllProgramme allProgramme;

	public RulePojo(Response response) { rulePojo = (RulePojo) response.body(); }

	public AllProgramme getAllProgramme(){ return allProgramme; }

	public static RulePojo getRulePojo() {
		return rulePojo;
	}
}