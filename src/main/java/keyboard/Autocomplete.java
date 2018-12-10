package keyboard;

import java.util.ArrayList;
import java.util.List;

import keyboard.interfaces.AutocompleteProvider;
import keyboard.interfaces.Candidate;
import keyboard.trie.ACNode;
import keyboard.trie.AutocompleteTrie;

public class Autocomplete implements AutocompleteProvider {
	
	private static AutocompleteTrie trie;

	@Override
	public List<Candidate> getWords(String fragment) {
		
		List<Candidate> candidates = new ArrayList<Candidate>();
		
		// strip out punctuation and set it to lower
		fragment = fragment.replaceAll("\\p{Punct}", "");
		
		ACNode node = trie.findFragment(fragment.toLowerCase());
		
		if(node == null) {
			//System.out.println("Nothing found...");
			return candidates;
		}
		
		candidates.addAll(trie.getSortedChildWords(fragment, node));
		
		return candidates;
	}

	@Override
	public void train(String passage) {
		passage = passage.replaceAll("\\p{Punct}", "");
		trie.insert(passage.toLowerCase());
		//System.out.println("Just trained "+passage);

	}
	
	public Autocomplete() {
		trie = new AutocompleteTrie();
	}
	
	public boolean isTrained() {
		// inverted because it's not trained if it's empty
		return !trie.isEmpty();
	}

}
