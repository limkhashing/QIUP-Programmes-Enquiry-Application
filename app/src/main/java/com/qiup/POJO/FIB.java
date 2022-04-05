package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class FIB{

	@SerializedName("SPM")
	private SPM sPM;

	@SerializedName("UEC")
	private UEC uEC;

	@SerializedName("OLevel")
	private OLevel oLevel;

	public SPM getSPM(){
		return sPM;
	}

	public UEC getUEC(){
		return uEC;
	}

	public OLevel getOLevel(){
		return oLevel;
	}

}