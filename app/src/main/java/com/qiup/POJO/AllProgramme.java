package com.qiup.POJO;

import com.google.gson.annotations.SerializedName;

public class AllProgramme {

	// Foundation
	@SerializedName("FIS")
	private FIS fis;

	@SerializedName("FIB")
	private FIB fib;

	@SerializedName("FIA")
	private FIA fia;

	// Diploma
	@SerializedName("DAC")
	private DAC dac;

	@SerializedName("DBM")
	private DBM dbm;

	@SerializedName("DHM")
	private DHM dhm;

	@SerializedName("DCE")
	private DCE dce;

	@SerializedName("DIT")
	private DIT dit;

	@SerializedName("DIS")
	private DIS dis;

	@SerializedName("DET")
	private DET det;

	@SerializedName("DME")
	private DME dme;

	@SerializedName("DCA")
	private DCA dca;

	// Degree
	@SerializedName("BHT")
	private BHT bht;

	@SerializedName("BCE")
	private BCE bce;

	@SerializedName("BSNE")
	private BSNE bsne;

	@SerializedName("BBA")
	private BBA bba;

	@SerializedName("BAC")
	private BAC bac;

	@SerializedName("BFI")
	private BFI bfi;

	@SerializedName("BCS")
	private BCS bcs;

	@SerializedName("BIT")
	private BIT bit;

	@SerializedName("BIS")
	private BIS bis;

	@SerializedName("CorporateComm")
	private CorporateComm corporateComm;

	@SerializedName("MassCommJournalism")
	private MassCommJournalism massCommJournalism;

	@SerializedName("MassCommAdvertising")
	private MassCommAdvertising massCommAdvertising;

	@SerializedName("TESL")
	private TESL tesl;

	@SerializedName("ECE")
	private ECE ece;

	@SerializedName("BioTech")
	private BioTech bioTech;

	@SerializedName("BS_ActuarialSciences")
	private BS_ActuarialSciences bs_actuarialSciences;

	@SerializedName("MBBS")
	private MBBS mbbs;

	@SerializedName("Pharmacy")
	private Pharmacy pharmacy;

	@SerializedName("BBS")
	private BBS bbs;

	@SerializedName("BET")
	private BET bet;

	@SerializedName("BEM")
	private BEM bem;

	// Foundation
	public FIS getFis() {
		return fis;
	}

	public FIB getFib() {
		return fib;
	}

	public FIA getFia() {
		return fia;
	}

	// Diploma
	public DME getDme() { return dme; }

	public DET getDet() {
		return det;
	}

	public DAC getDac() {
		return dac;
	}

	public DBM getDbm() {
		return dbm;
	}

	public DHM getDhm() {
		return dhm;
	}

	public DCE getDce() {
		return dce;
	}

	public DIT getDit() {
		return dit;
	}

	public DIS getDis() {
		return dis;
	}

	public DCA getDca() { return dca; }

	// Degree
	public BHT getBht() { return bht; }

	public BCE getBce() { return bce; }

	public BSNE getBsne() { return bsne; }

	public BBA getBba() { return bba; }

	public BAC getBac() { return bac; }

	public BFI getBfi() {
		return bfi;
	}

	public BCS getBcs() { return bcs; }

	public BIT getBit() { return bit; }

	public BIS getBis() { return bis; }

	public CorporateComm getCorporateComm() { return corporateComm; }

	public MassCommJournalism getMassCommJournalism() { return massCommJournalism; }

	public MassCommAdvertising getMassCommAdvertising() { return massCommAdvertising; }

	public TESL getTesl() { return tesl; }

	public ECE getEce() { return ece; }

	public BioTech getBioTech() { return bioTech; }

	public BS_ActuarialSciences getBs_actuarialSciences() { return bs_actuarialSciences; }

	public MBBS getMbbs() { return mbbs; }

	public Pharmacy getPharmacy() { return pharmacy; }

	public BBS getBbs() { return bbs; }

	public BET getBet() { return bet; }

	public BEM getBem() { return bem; }
}