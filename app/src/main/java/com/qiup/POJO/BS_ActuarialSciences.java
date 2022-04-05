package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class BS_ActuarialSciences {

	@SerializedName("UEC")
	private UEC uEC;

	@SerializedName("ALevel")
	private ALevel aLevel;

	@SerializedName("STPM")
	private STPM sTPM;

	@SerializedName("STAM")
	private STAM sTAM;

	public UEC getUEC(){
		return uEC;
	}

	public ALevel getALevel(){
		return aLevel;
	}

	public STPM getSTPM(){
		return sTPM;
	}

	public STAM getSTAM(){
		return sTAM;
	}
}