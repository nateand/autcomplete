package keyboard;



import java.util.List;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import keyboard.interfaces.Candidate;
import keyboard.trie.ACNode;
import keyboard.trie.AutocompleteTrie;

import org.springframework.shell.jline.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class KeyboardApplicationTests {

	@Test
	public void testTrieContent() {
		AutocompleteTrie trie = new AutocompleteTrie();
		Assert.assertNull(trie.findFragment("test"));
		
		List<MyCandidate> candidates = trie.getChildWords("test", null);
		Assert.assertTrue(candidates.isEmpty());
		
		trie.insert("test");
		trie.insert("tent");
		trie.insert("tree");
		
		ACNode node = trie.findFragment("t");
		Assert.assertNotNull(node);
		
		candidates = trie.getChildWords("t", node);
		Assert.assertTrue(candidates.size() == 3);
		
		node = trie.findFragment("te");
		Assert.assertNotNull(node);
		
		candidates = trie.getChildWords("te", node);
		Assert.assertTrue(candidates.size() == 2);
		
		node = trie.findFragment("tr");
		Assert.assertNotNull(node);
		
		candidates = trie.getChildWords("tr", node);
		Assert.assertTrue(candidates.size() == 1);
		
		node = trie.findFragment("z");
		Assert.assertNull(node);
		
		candidates = trie.getChildWords("z", node);
		Assert.assertTrue(candidates.size() == 0);
		
	}
	
	@Test 
	public void testAutocompleteTrie(){
		// test punctuation stripping and confidence with Autocomplete
		Autocomplete ac = new Autocomplete();
		Assert.assertFalse(ac.isTrained());
		Assert.assertTrue(ac.getWords("c").isEmpty());
		
		ac.train("c.");
		ac.train("c?");
		ac.train("c@");
		ac.train("cello!");		
		
		List<Candidate> candidates = ac.getWords("c");
		Assert.assertFalse(candidates.isEmpty());
		
		Assert.assertTrue(candidates.size() == 2);
		Assert.assertTrue(candidates.get(0).getConfidence() == 2);
		Assert.assertTrue(candidates.get(0).getWord().equals("c"));
		Assert.assertTrue(candidates.get(1).getConfidence() == 1);
		Assert.assertTrue(candidates.get(1).getWord().equals("cello"));
				
			
	}
	
	@Test
	public void testEmptyTrie() {
	    AutocompleteTrie trie = new AutocompleteTrie();
	    Assert.assertTrue(trie.isEmpty());
	}
	
	@Test
	public void testNonEmptyTrie() {
	    AutocompleteTrie trie = createExampleTrie();
	    Assert.assertFalse(trie.isEmpty());
	}
	
	
	private AutocompleteTrie createExampleTrie() {
	    AutocompleteTrie trie = new AutocompleteTrie();
	 
	    trie.insert("Hello");
	    trie.insert("test");
	    trie.insert("1");
	 
	    return trie;
	}

}
