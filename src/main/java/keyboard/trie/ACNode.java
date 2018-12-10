package keyboard.trie;

import java.util.HashMap;

// Class used to represent each node in the trie
public class ACNode {
	
	private HashMap<Character,ACNode> children; // children nodes
	private Integer count; // count (used to control frequency if it's a word)
	private boolean isWord; // is it a word?
	
	
	public ACNode() {
		count = 0;
		isWord = false;
		children = new HashMap<Character,ACNode>();
	}
	

	public void increment() {
		count++;
	}
	
	public void addChild(Character c) {
		children.put(c, new ACNode());
	}
	
	
	public HashMap<Character, ACNode> getChildren() {
		return children;
	}


	public void setChildren(HashMap<Character, ACNode> children) {
		this.children = children;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public boolean isWord() {
		return isWord;
	}


	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}

}
