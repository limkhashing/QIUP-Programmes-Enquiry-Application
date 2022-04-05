package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class DAC{

	@SerializedName("SPM")
	private SPM sPM;

	@SerializedName("UEC")
	private UEC uEC;

	@SerializedName("OLevel")
	private OLevel oLevel;

	@SerializedName("STPM")
	private STPM sTPM;

	@SerializedName("ALevel")
	private ALevel aLevel;

	@SerializedName("STAM")
	private STAM sTAM;

	public SPM getSPM(){
		return sPM;
	}

	public UEC getUEC(){
		return uEC;
	}

	public OLevel getOLevel(){
		return oLevel;
	}

	public STPM getSTPM(){
		return sTPM;
	}

	public ALevel getALevel(){
		return aLevel;
	}

	public STAM getSTAM(){
		return sTAM;
	}

}