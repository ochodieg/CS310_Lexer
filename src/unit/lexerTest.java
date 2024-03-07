package unit;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
// importing java.lang.Character.* imports all static methods from this class
// not a good idea to do this unless you know what ur doing since a bunch of method names are added
import static java.lang.Character.isWhitespace;


import java.util.LinkedList;
import java.util.Queue;

import framework.Token;
import framework.TokenName;

// lexer == tokenizer
public class lexerTest {
	
	/*Character.is*, String.equals*, and String.substring methods are still allowed.*/
	
	
	public static void main(String[] args) {

		
		
		Queue<Token> tokens = new lexerTest().tokenize("[");
		
		while (!tokens.isEmpty()) {
			System.out.println(tokens.remove());
			System.out.println("piss");
		}
	}

	//Tokenize the input string using minimal characters of lookahead and no lookbehind
	//Terminate the queue with either an end of input token or unexpected input token
	//Return a queue of framework.Token objects with the result of the lexical analysis
	public Queue<Token> tokenize(String input) {
		var tokens = new LinkedList<Token>();
		char[] keywords = {'d','f','i','l','p','r','s','w'};
		
		input = input.toLowerCase();	// return lowercase equivalent of the character, if any; otherwise, the character itself.
										// case insensitive

		
		
		
		
		for(int i = 0; i< input.length(); i++) {
			
			//if(Character.isLowerCase(input.charAt(i)))
			
			if(isWhitespace(input.charAt(i)))		// tabs and other whitespace arent recognized by ' '
				continue;							// skip
			
			if(isDigit(input.charAt(i))) {			// if character is a digit
				int current = 1; 					// counting current index
				while(i+current < input.length() && // if not last index
						isDigit(input.charAt(i+current)))// and next character is also a digit
					current++;						// keep count
				//capture substring of the token (start index, first unwanted index)
				String intToken = input.substring(i, i+current);
				// create new Token with derived lexeme value. add to queue
				tokens.add(new Token(TokenName.UINT_LIT, Integer.parseInt(intToken)));
				i += current -1;					// increase by # symbols consumed minus the one that the loop already processes
				
			
			}else if(isLowerCase(input.charAt(i))) {// checks only a-z ascii
				// contains() iterates through chars
				
				int current = 1;
				String lexeme = input.substring(i, i+current);// obtain single char
				
				// run check to see if possible keyword
				boolean keywordChar = false;
				for(var c: keywords) {
					if(input.charAt(i) == c) {
						keywordChar = true;
						break;
					}	
				}
				
				//if(Arrays.asList(keywords).contains(input.charAt(i))
				if(keywordChar							// if char is in keyword array
						&& i+current < input.length()){//and there are more chars
					
					boolean wordCheck = false;	// init check
					
					// while loop will continue to run if next char is a part of a keyword
					// regardless of whether or not there is an actual Keyword token
					// after the loop, the result is checked and if no keyword is found
					// the captured substring will be iterated through and ea. individual
					// VAR_ID will be added to queue. switch will redundantly set bool to true
					// this could have been avoided by implementing switch inside loop and...
					
					int j = i;
					while(i+current < input.length()// while there are more chars and the next char is in kw array
							&& isLowerCase(input.charAt(i+current))) {
						current++;					// keep track of char count/index offset
						
						
						lexeme = input.substring(j, i+current);	// capture possible keyword
						
						
						switch(lexeme) {		// {'d','f','i','l','p','r','s','w'};
						case "let" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.LET_KW, lexeme));
							j = current;	// adjust index to after keyword to find more possible keywords
							
						}case "def" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.DEF_KW, "def"));
							j = current;
							
						}case "push" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.PUSH_KW, "push"));
							j = current;
							
						}case "pop" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.POP_KW, "pop"));
							j = current;
							
						}case "peek" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.PEEK_KW, "peek"));
							j = current;
							
						}case "size" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.SIZE_KW, "size"));
							j = current;
							
						}case "run" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.RUN_KW, "run"));
							j = current;
							
						}case "if" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.IF_KW, "if"));
							j = current;
							
						}case "while" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.WHILE_KW, "while"));
							j = current;
							
						}case "do" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.DO_KW, "do"));
							j = current;
							
						}case "for" ->{
							wordCheck = true;
							tokens.add(new Token(TokenName.FOR_KW, "for"));
							j = current;
							
						}
						}
						
					}
					
					
					//if(!wordCheck) {		// if no keyword was found
						for(var ch: lexeme.toCharArray()) {	// for every VAR_ID captured
							tokens.add(new Token(TokenName.VAR_ID, ch));// add to queue
						}
					//}
					
					i += current -1; // compensate index for captured values 
				}
				
			}
			
			
			switch(input.charAt(i)) {
				case '[' ->{
					System.out.println("poo");
					tokens.add(new Token(TokenName.OPEN_BLOCK, '['));
					continue;
					
				}case ']' ->{
					System.out.println("poo");
					continue;
					
				}case '%' ->{
					System.out.println("poo");
					continue;
					
				}case '/' ->{
					System.out.println("poo");
					continue;
					
				}case '*' ->{
					System.out.println("poo");
					continue;
					
				}case '=' ->{
					System.out.println("poo");
					continue;
					
				}case '&' ->{
					System.out.println("poo");
					continue;
					
				}case '|' ->{
					System.out.println("poo");
					continue;
					
				}
				
			}
			System.out.println("fuck");
			
		}
		
		
		return tokens;
	}
}

/*
 * single char:
 * 	[
 * 	]
 * 	%
 *	/
 *	*
 *	=
 *	&
 *	|
 *	
 */
