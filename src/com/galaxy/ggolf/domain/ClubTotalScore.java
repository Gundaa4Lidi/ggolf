package com.galaxy.ggolf.domain;

public class ClubTotalScore {
	private String ClubID;
	private String DesignScore;
	private String GrassScore;
	private String FacilityScore;
	private String ServiceScore;
	public ClubTotalScore(String clubID, String designScore, String grassScore, String facilityScore,
			String serviceScore) {
		ClubID = clubID;
		DesignScore = designScore;
		GrassScore = grassScore;
		FacilityScore = facilityScore;
		ServiceScore = serviceScore;
	}
	public String getClubID() {
		return ClubID;
	}
	public void setClubID(String clubID) {
		ClubID = clubID;
	}
	public String getDesignScore() {
		return DesignScore;
	}
	public void setDesignScore(String designScore) {
		DesignScore = designScore;
	}
	public String getGrassScore() {
		return GrassScore;
	}
	public void setGrassScore(String grassScore) {
		GrassScore = grassScore;
	}
	public String getFacilityScore() {
		return FacilityScore;
	}
	public void setFacilityScore(String facilityScore) {
		FacilityScore = facilityScore;
	}
	public String getServiceScore() {
		return ServiceScore;
	}
	public void setServiceScore(String serviceScore) {
		ServiceScore = serviceScore;
	}
	
	
}
