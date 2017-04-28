package ycp.cs320.teamProject.model;

import java.util.ArrayList;
import java.util.List;

public class Position {

	private int positionIdS;
	private int positionIdU;
	private String positionName; 
	private String positionDuty;
	
	// is this needed with the way we are linking them in the DB
	private List<SOP> regulatingSOPs = new ArrayList<SOP>();

	//find a key for finding the required level of training

	public Position() {

	}

	public void setPositionName(String PositionName) {
		positionName = PositionName;
	}

	public String getPositionName() {
		return positionName;
	}
	public void setPositionIDS(int PositionId) {
		this.positionIdS = PositionId;
	}
	
	public int getPositionIDS() {
		return positionIdS;
	}
	public void setPositionIDU(int PositionId) {
		this.positionIdU = PositionId;
	}
	
	public int getPositionIDU() {
		return positionIdU;
	}

	public List<SOP> getRegulatingSOPs() {
		return regulatingSOPs;
	}

	public void setRegulatingSOPs(List<SOP> regulatingSOPs) {
		this.regulatingSOPs = regulatingSOPs;
	}

	public void addRegulatedSOP(SOP sop) {
		regulatingSOPs.add(sop);
	}

	public int findRelevantSOP(int sopID) {
		int referenceID = 0; 
		for(int i = 0; i < regulatingSOPs.size(); i++) {
			if(regulatingSOPs.get(i).getSopIdNumber() == sopID) {
				referenceID = regulatingSOPs.get(i).getSopIdNumber();
				break;
			}
		}

		return referenceID;
	}

	public ArrayList<SOP> findRelevantSOPs(Position position) {
		ArrayList<SOP> sops = new ArrayList<SOP>();

		for(int i = 0; i < regulatingSOPs.size(); i++) {
			if(position.getPositionIDS() == regulatingSOPs.get(i).getPositionsAffected().get(i).getPositionIDS()) {
				sops.add(regulatingSOPs.get(i));
			}
		}

		return sops;
	}
	
	public void setPositionDuty(String PositionDuty) {
		positionDuty = PositionDuty;
	}

	public String getPositionDuty() {
		return positionDuty;
	}



}
