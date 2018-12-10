# Mobile Device Keyboard
A command line Java program that can be trained and tested continuously. For this application, Spring Boot and Spring Shell were used
to provide the framework and testing. This application will accept words to train an Autocompletion model, and then accept word fragments to
receive a list of candidates that could be used to auto-complete the fragment. The first result is the result with the highest confidence of being
correct.

## Explanation of Functionality
This application is run from the command-line and accepts two commands (outside of those built-in to Spring Shell):
- train: This command takes one string argument (use quotes for more words) and will take each word input and use them to 
train the model.
- input: This command takes one string argument (ideally a string fragment) and will output a list of words that match it
based on the trained model so far.

## How it works
This application uses Spring Shell to run as a fully featured command-line application with input/output. Using a Trie (prefix tree)
data object, there is an AutocompleteTrie class that is further trained every time an input is provided to it. This Trie allows
for the searching of fragments (prefix only) of any words trained. It will order the results by which are used most often, so each
candidate contains the word itself as well as a "confidence" value that is relative to the other strings. 

For example:

    $ train "The third thing that I need to tell you is that this thing does not think thoroughly." 
    Trained: The third thing that I need to tell you is that this thing does not think thoroughly.
    $ input thi
    "thi" --> "thing" (2), "third" (1), "this" (1), "think" (1)
    $ input "nee"
    "nee" --> "need" (1)
    $ input "th" 
    "th" --> "that" (2), "thing" (2), "the" (1), "third" (1), "this" (1), "think" (1), "thoroughly" (1)


##  Instructions

This build requires a valid install of JDK8.

    $ git clone https:/repo-path /path/to/repository
    $ cd /path/to/repository
    $ ./mvnw spring-boot:run
    
This will open a command-line in which the commands explained above can be entered.    

