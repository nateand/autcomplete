package keyboard;

import keyboard.interfaces.Candidate;

public class MyCandidate implements Candidate, Comparable {

	private String word;
	private Integer confidence;
	
	@Override
	public String getWord() {
		return word;
	}
	
	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	@Override
	public Integer getConfidence() {
		return confidence;
	}
	
	public MyCandidate(String word, Integer confidence) {
		this.word = word;
		this.confidence = confidence;
	}

	@Override
	public int compareTo(Object arg0) {

		return this.getConfidence().compareTo( ((MyCandidate) arg0).getConfidence());
	}

}
