package unit;
import static java.lang.Character.isDigit;
//importing java.lang.Character.* imports all static methods from this class
//not a good idea to do this unless you know what ur doing since a bunch of method names are added
import static java.lang.Character.isWhitespace;
import java.util.Queue;
import framework.Token;

//lexer == tokenizer
public class LexerExample {
	
/*Character.is*, String.equals*, and String.substring methods are still allowed.*/
	
	
	public static void main(String[] args) {
		// length of int limit is a java responsibility here
		// so not our problem. When designing in lower level langs, it will need to be addressed
		String input = "12 + 345 - 6 ** 78 * 9$ 000";
		//first discard whitespace and discard any internal whitespace
		// if current symbol is whitespace, loop till index that isnt whitespace is found

		
		for(int i = 0; i< input.length(); i++) {
			//whitespace guard condition 
			if(isWhitespace(input.charAt(i)))	// tabs and other whitespace arent recognized by ' '
				// java static methods can be imported
				// so as to not call helper classes every time (ie Character.isWhitespace == isWhitespace)
				// right click on Character --> source --> add import
				// creates a static import (specify a static method via import)
				continue;	// skip
		// if here, we're not looking at whitespace
			if(input.charAt(i) == '+') {
				System.out.println("PLUS token");
			}
			else if(input.charAt(i) == '-')
				System.out.println("MINUS token");
			
			else if(input.charAt(i) == '*') {
				// peek ahead - make sure index is left in correct position afterwards
				// make sure the peeked index is a valid index (not outOfBounds)
				if(i+1 < input.length() && input.charAt(i+1) == '*') {
					//exp token
					System.out.println("EXP token");
					// loop needs to advance for every symbol processed
					// so that the second Asterisk isn't processed again 
					// therefore manually increase index by symbols processed
					i += 1;
				}else {
					//times token
					System.out.println("TIMES token");
				}
			}
			// checking if digit
			// lexeme = "3456" as a string
			// number = 3456 as an integer
			else if(isDigit(input.charAt(i))) {
				int len = 1;	// one digit counted already
				while(i+len < input.length() && isDigit(input.charAt(i+len)))	// also need to ensure next index is valid
					len++;
				String lexeme = input.substring(i,i+len);	// start index to first index u don't want
				int number = Integer.parseInt(lexeme);	// this dips a bit into semantics but
				// this would be static semantics
				// if this was a real lexer, we would need to right some denotational semantics
				// code that converts a string into a number
				System.out.println("INT_LITERAL token with value " + number);
				// the rest of the tokens don't have a value to them 
				// index must then be adjusted based on characters consumed
				i += len -1;	// increase by # symbols consumed minus the one that the loop already processes
			}
			//is symbol is not in our FSA
			else {
				System.err.println("UNRECOGNIZED token: " + input.charAt(i));
				// warning: sys out and sys err do not promise to run in right order
				// need an object that can hold type of token and its value
				// instead of printing error, throw an exception
				return;
			}
			
		}
		
		System.out.println("END OF INPUT token");
		
//		Queue<Token> tokens = new Lexer().tokenize(input);
//		while (!tokens.isEmpty())
//			System.out.println(tokens.remove());
	}

	//Tokenize the input string using minimal characters of lookahead and no lookbehind
	//Terminate the queue with either an end of input token or unexpected input token
	//Return a queue of framework.Token objects with the result of the lexical analysis
	public Queue<framework.Token> tokenize(String input) {
		return null;
	}

}
