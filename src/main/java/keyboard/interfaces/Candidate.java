package keyboard.interfaces;

public interface Candidate {
	
	
	// Method to return the candidate word
	public String getWord();
	// Method to return the confidence value for the candidate
	// Note that confidence is relative to other candidates and not just a count
	public Integer getConfidence();
	

}
