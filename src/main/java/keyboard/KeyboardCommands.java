package keyboard;

import java.util.Iterator;
import java.util.List;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import keyboard.interfaces.Candidate;

@ShellComponent
public class KeyboardCommands {
	
	private Autocomplete ac;
	
	@ShellMethod("Train the autocomplete model")
	public String train(String a) {		
		
		if(a.trim().length() == 0) {
			return "Nothing trained.";
		}
		
		String[] words = a.trim().split(" ");
		
		for(String word : words) {
			if(word.length() > 0) {
				ac.train(word);
			}
		}
		
		return "Trained: "+a;
		
	}
	
	@ShellMethod("Test the autocompletion")
	@ShellMethodAvailability("trainCheck")
	public String input(String a) {
		//System.out.println("Got an input command on "+a);
		List<Candidate> candidates = ac.getWords(a);
		String output = "\""+ a + "\" --> ";
		if(candidates.isEmpty()){
			output += "No candidates for autocompletion";
		} else {
			Iterator<Candidate> iter = candidates.iterator();
			while(iter.hasNext()) {
				Candidate candidate = iter.next();
				output += "\"" + candidate.getWord() + "\" ("+candidate.getConfidence()+")";
				if(iter.hasNext()) {
					output += ", ";
				}
			}
		}	

		return output;
		
	}
	
	public Availability trainCheck() {
		return ac.isTrained() /// this must be changed to a check for if the trie is set up somehow
				? Availability.available()
				: Availability.unavailable("Autocomplete not trained.");
	}
	
	public KeyboardCommands() {
		ac = new Autocomplete();
	}
	

}
