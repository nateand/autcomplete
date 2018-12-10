package keyboard.interfaces;

import java.util.List;

public interface AutocompleteProvider {
	
	// Method to return list of candidates
	public List<Candidate> getWords(String fragment);
	// Method to train the model
	public void train(String passage);
	

}
