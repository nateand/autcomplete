package keyboard.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import keyboard.MyCandidate;

public class AutocompleteTrie {
	
	private ACNode root; // root element in the trie
	
	
	public AutocompleteTrie() {
		root = new ACNode();
		
	}
	
	// method to add a word to the trie
	public void insert(String word) {
		
		ACNode current = root;
		
		for(int i = 0; i < word.length(); i++) {
			// if there's no child for this character, add it
			if(!current.getChildren().containsKey(word.charAt(i))) {
				current.addChild(word.charAt(i));
			} 
			// advance to the child for this character
			current = current.getChildren().get(word.charAt(i));
		}
		
		// because we're at the end of a word, we need to set the word flag
		current.setWord(true);
		// we also need to update how many times this word has shown up
		current.increment();
		
		
	}
	
	public List<MyCandidate> getSortedChildWords(String fragment, ACNode current){
		
		List<MyCandidate> candidates = new ArrayList<MyCandidate>();
		
		candidates.addAll(getChildWords(fragment, current));
		Collections.sort(candidates);
		
		Integer currentConfidence = 1;
		Integer currentConfidenceRelative = 1;
		
		for(MyCandidate candidate : candidates) {
			if(candidate.getConfidence() > currentConfidence) {
				currentConfidence = candidate.getConfidence();
				// skip if the first one is over 1, because that'll start us above 1
				if(!candidate.equals(candidates.get(0))) {
					currentConfidenceRelative++;
				}
				
			} 
			candidate.setConfidence(currentConfidenceRelative);	
		}
		
		Collections.sort(candidates, Collections.reverseOrder());
		
		return candidates;
		
		
		
	}
	
	public List<MyCandidate> getChildWords(String fragment, ACNode current) {
		
		List<MyCandidate> candidates = new ArrayList<MyCandidate>();
		if(current == null) {
			return candidates;
		}
				
		// juts because it's a word doesn't mean we don't need to evaluate children!!
		if(current.isWord()) {
			candidates.add(new MyCandidate(fragment,current.getCount()));
			//System.out.println("Got a word ("+fragment+") with count: "+current.getCount());
		}
		
		Iterator<Character> iter = current.getChildren().keySet().iterator();
		while(iter.hasNext()) {
			
			Character c = iter.next();
			ACNode next = current.getChildren().get(c);
			
			candidates.addAll(getChildWords(fragment+c,next));
			
		}

		return candidates;
	}

	public boolean isEmpty() {
		return root.getChildren().isEmpty();
	}
	
	public ACNode findFragment(String frag) {
		ACNode current = root;
		for(int i=0; i < frag.length(); i++) {
			Character c = frag.charAt(i);
			current = current.getChildren().get(c);
			if(current == null) {
				return null;
			}
		}
		return current;
	}

}
