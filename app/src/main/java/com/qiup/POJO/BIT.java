package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class BIT{

	@SerializedName("UEC")
	private UEC uEC;

	@SerializedName("ALevel")
	private ALevel aLevel;

	@SerializedName("STPM")
	private STPM sTPM;

	public UEC getUEC(){
		return uEC;
	}

	public ALevel getALevel(){
		return aLevel;
	}

	public STPM getSTPM(){
		return sTPM;
	}
}