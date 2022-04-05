package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class STPM{

	@SerializedName("minimum_subject_required_grade")
	private MinimumSubjectRequiredGrade minimumSubjectRequiredGrade;

	@SerializedName("minimum_credit_grade")
	private int minimumCreditGrade;

	@SerializedName("what_supportive_subject")
	private WhatSupportiveSubject whatSupportiveSubject;

	@SerializedName("what_supportive_grade")
	private WhatSupportiveGrade whatSupportiveGrade;

	@SerializedName("what_supportive_qualification")
	private String whatSupportiveQualification;

	@SerializedName("what_subject_required")
	private WhatSubjectRequired whatSubjectRequired;

	@SerializedName("amount_of_credit_required")
	private int amountOfCreditRequired;

	@SerializedName("got_required_subject")
	private boolean gotRequiredSubject;

	@SerializedName("need_supportive_qualification")
	private boolean needSupportiveQualification;

	@SerializedName("IsScienceStream")
	private IsScienceStream isScienceStream;

	@SerializedName("NotScienceStream")
	private NotScienceStream notScienceStream;

	@SerializedName("amount_of_subject_required")
	private int amountOfSubjectRequired;

	@SerializedName("amount_of_supportive_subject_required")
	private int amountOfSupportiveSubjectRequired;

	@SerializedName("exempted")
	private boolean exempted;

	public IsScienceStream getIsScienceStream() {
		return isScienceStream;
	}

	public NotScienceStream getNotScienceStream() {
		return notScienceStream;
	}

	public MinimumSubjectRequiredGrade getMinimumSubjectRequiredGrade(){
		return minimumSubjectRequiredGrade;
	}

	public int getMinimumCreditGrade(){
		return minimumCreditGrade;
	}

	public WhatSupportiveSubject getWhatSupportiveSubject(){
		return whatSupportiveSubject;
	}

	public WhatSupportiveGrade getWhatSupportiveGrade(){
		return whatSupportiveGrade;
	}

	public String getWhatSupportiveQualification(){
		return whatSupportiveQualification;
	}

	public WhatSubjectRequired getWhatSubjectRequired(){
		return whatSubjectRequired;
	}

	public int getAmountOfCreditRequired(){
		return amountOfCreditRequired;
	}

	public boolean isGotRequiredSubject(){
		return gotRequiredSubject;
	}

	public boolean isNeedSupportiveQualification(){
		return needSupportiveQualification;
	}

	public int getAmountOfSubjectRequired() {
		return amountOfSubjectRequired;
	}

	public int getAmountOfSupportiveSubjectRequired() {
		return amountOfSupportiveSubjectRequired;
	}

	public boolean isExempted() { return exempted; }
}