package unit;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
// importing java.lang.Character.* imports all static methods from this class
// not a good idea to do this unless you know what ur doing since a bunch of method names are added
import static java.lang.Character.isWhitespace;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import framework.Token;
import framework.TokenName;

// lexer == tokenizer
public class Lexer {
	
	/*Character.is*, String.equals*, and String.substring methods are still allowed.*/
	
	
	public static void main(String[] args) {
		
//		String input = "12 + 345 - 6 ** 78 * 9$ 000A";
//		System.out.println(input);
//		input = input.toLowerCase();
//		System.out.println(input);
//		
//
//		
//		for(int i = 0; i< input.length(); i++) {
//			//whitespace guard condition 
//			if(isWhitespace(input.charAt(i)))	// tabs and other whitespace arent recognized by ' '
//				
//				continue;	// skip
//			
//		// if here, we're not looking at whitespace
//			if(input.charAt(i) == '+') {
//				System.out.println("PLUS token");
//			}
//			
//			else if(input.charAt(i) == '-')
//				System.out.println("MINUS token");
//			
//			else if(input.charAt(i) == '*') {
//				
//				if(i+1 < input.length() && input.charAt(i+1) == '*') {
//					//exp token
//					System.out.println("EXP token");
//					i += 1;
//				}else {
//					//times token
//					System.out.println("TIMES token");
//				}
//			}
//		
//			else if(isDigit(input.charAt(i))) {
//				int len = 1;	// one digit counted already
//				while(i+len < input.length() && isDigit(input.charAt(i+len)))	// also need to ensure next index is valid
//					len++;
//				String lexeme = input.substring(i,i+len);	// start index to first index u don't want
//				int number = Integer.parseInt(lexeme);	// this dips a bit into semantics but
//				
//				System.out.println("INT_LITERAL token with value " + number);
//				
//			}
//			//is symbol is not in our FSA
//			else {
//				System.err.println("UNRECOGNIZED token: " + input.charAt(i));
//				
//				return;
//			}
//			
//		}
//		
//		///
//		
//		System.out.println("END OF INPUT token");
		
		
		
		Queue<Token> tokens = new Lexer().tokenize("ddoddodfforki f iiffllett ppeeekkpeekkpushh pufh ppopppeek rruunnrunn "
				+ "dof def dif dee fop for led fdr ssizee sipe wwhilee whike wjile whele 12938 291730"
				+ "+++<<=>>=|&--[[%/]]");
		
		while (!tokens.isEmpty()) {
			System.out.println(tokens.remove());
			//System.out.println("hello");
		}
	}

	//Tokenize the input string using minimal characters of lookahead and no lookbehind
	//Terminate the queue with either an end of input token or unexpected input token
	//Return a queue of framework.Token objects with the result of the lexical analysis
	public Queue<Token> tokenize(String input) {
		char[] keywords = {'d','f','i','l','p','r','s','w'};
		char[] ops = {'[',']','+','-','*','/','%','&','|','~','=','<','>'};
		
		input = input.toLowerCase();	// return lowercase equivalent of the character, if any; otherwise, the character itself.
										// case insensitive

		
		var tokens = new LinkedList<Token>();
		
		
		
		for(int i = 0; i< input.length(); i++) {
			int current = 1; 					// counting current index
			//if(Character.isLowerCase(input.charAt(i)))
			
			if(isWhitespace(input.charAt(i)))		// tabs and other whitespace arent recognized by ' '
				continue;							// skip
			
			if(isDigit(input.charAt(i))) {			// if character is a digit
				
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
				
				
				// run check to see if possible keyword
				boolean keywordChar = false;
				for(var c: keywords) {
					if(input.charAt(i) == c) {
						keywordChar = true;
						break;
					}	
				}
				
				//if(Arrays.asList(keywords).contains(input.charAt(i))
				if(keywordChar){//and there are more chars
					//int current = 1;
					String lexeme = input.substring(i, i+current);// obtain single char
					boolean wordCheck = false;
					
					switch(lexeme) {		// {'d','f','i','l','p','r','s','w'};
					case "d" ->{
						if( i+current < input.length() &&input.charAt(i+current) == 'o') {
							current++;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.DO_KW, lexeme));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else if( i+current < input.length() &&input.charAt(i+current) == 'e') {
							if(input.charAt(i+current+1) == 'f') {
								current++;
								lexeme = input.substring(i,i+current);
								tokens.add(new Token(TokenName.DEF_KW, lexeme));
								i += current -1;
							}else
								tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}else {
							tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}		
					}case "f" ->{
						if( i+current < input.length() &&input.charAt(i+current) == 'o') {
							current++;
							if( i+current < input.length() &&input.charAt(i+current) == 'r') {
								current++;
								lexeme = input.substring(i,i+current);
								tokens.add(new Token(TokenName.FOR_KW, lexeme));
								i += current -1;
							}else
								tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}else {
							tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}
						
//						tokens.add(new Token(TokenName.DEF_KW, "def"));
//						j = current;
						
					}case "i" ->{
						if( i+current < input.length() &&input.charAt(i+current) == 'f') {
							current++;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.IF_KW, lexeme));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else {
							tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}
						
						
						//wordCheck = true;
//						tokens.add(new Token(TokenName.PUSH_KW, "push"));
//						j = current;
//						
					}case "l" ->{
						if( i+current < input.length() &&input.charAt(i+current) == 'e') {
							current++;
							if( i+current < input.length() &&input.charAt(i+current) == 't') {
								current++;
								lexeme = input.substring(i,i+current);
								tokens.add(new Token(TokenName.LET_KW, lexeme));
								i += current -1;
							}else
								tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}else {
							tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}
						
						//wordCheck = true;
//						tokens.add(new Token(TokenName.POP_KW, "pop"));
						
						
					}case "p" ->{
						if( (i+current+1 < input.length() || i+current+2 < input.length())
								&& (input.charAt(i+current) =='e' 
								|| input.charAt(i+current) =='o'
								|| input.charAt(i+current) =='u')){ // if there are at least 3 - 4 chars remaining, check
							switch(input.charAt(i+current)) {
							case 'e'->{
								if(input.charAt(i+current+1) == 'e') {
									if(input.charAt(i+current+2) == 'k') {
										current+=3;
										lexeme = input.substring(i,i+current);
										tokens.add(new Token(TokenName.PEEK_KW, lexeme));
										i += current -1;
									}else {
										tokens.add(new Token(TokenName.VAR_ID, lexeme));
									}
								}else {
									tokens.add(new Token(TokenName.VAR_ID, lexeme));
								}
								
							}case 'o'->{
								if(input.charAt(i+current+1) == 'p') {
									current+=2;
									lexeme = input.substring(i,i+current);
									tokens.add(new Token(TokenName.POP_KW, lexeme));
									i += current -1;
								}else {
									tokens.add(new Token(TokenName.VAR_ID, lexeme));
								}
								
							}case 'u'->{
								if(input.charAt(i+current+1) == 's') {
									if(input.charAt(i+current+2) == 'h') {
										current+=3;
										lexeme = input.substring(i,i+current);
										tokens.add(new Token(TokenName.PUSH_KW, lexeme));
										i += current -1;
									}else {
										tokens.add(new Token(TokenName.VAR_ID, lexeme));
									}		
								}else {
									tokens.add(new Token(TokenName.VAR_ID, lexeme));
								}
							 }
							
							}
						}else {
							tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}
					
					}case "r" ->{
						if( i+current < input.length() &&input.charAt(i+current) == 'u') {
							current++;
							if( i+current < input.length() &&input.charAt(i+current) == 'n') {
								current++;
								lexeme = input.substring(i,i+current);
								tokens.add(new Token(TokenName.RUN_KW, lexeme));
								i += current -1;
							}else
								tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}else {
							tokens.add(new Token(TokenName.VAR_ID, lexeme));
						}

					}case "s" ->{
						String keyword = "size";
						String found = "";
						for(int j =0; j < keyword.length(); j++) {
							found += input.charAt(i+j);
						}
						if(found.equals(keyword)) {
							current+=keyword.length()-1;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.SIZE_KW, lexeme));
							i += current -1;
						}else
							tokens.add(new Token(TokenName.VAR_ID, lexeme));;

					}case "w" ->{
						String keyword = "while";
						String found = "";
						for(int j =0; j < keyword.length(); j++) {
							found += input.charAt(i+j);
						}
						if(found.equals(keyword)) {
							current+=keyword.length()-1;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.WHILE_KW, lexeme));
							i += current -1;
						}else
							tokens.add(new Token(TokenName.VAR_ID, lexeme));;
					}
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
//					boolean wordCheck = false;	// init check
					
					// while loop will continue to run if next char is a part of a keyword
					// regardless of whether or not there is an actual Keyword token
					// after the loop, the result is checked and if no keyword is found
					// the captured substring will be iterated through and ea. individual
					// VAR_ID will be added to queue. switch will redundantly set bool to true
					// this could have been avoided by implementing switch inside loop and...
					
					
					
					
					
					/////////////////////////////////////////////////////////////////////////////////////////
//					int j = i;
//					while(i+current < input.length()// while there are more chars and the next char is in kw array
//							&& isLowerCase(input.charAt(i+current))) {
//						current++;					// keep track of char count/index offset
//						
//						
//						lexeme = input.substring(j, i+current);	// capture possible keyword
//						
//						
//						switch(lexeme) {		// {'d','f','i','l','p','r','s','w'};
//						case "let" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.LET_KW, lexeme));
//							j = current;	// adjust index to after keyword to find more possible keywords
//							
//						}case "def" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.DEF_KW, "def"));
//							j = current;
//							
//						}case "push" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.PUSH_KW, "push"));
//							j = current;
//							
//						}case "pop" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.POP_KW, "pop"));
//							j = current;
//							
//						}case "peek" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.PEEK_KW, "peek"));
//							j = current;
//							
//						}case "size" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.SIZE_KW, "size"));
//							j = current;
//							
//						}case "run" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.RUN_KW, "run"));
//							j = current;
//							
//						}case "if" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.IF_KW, "if"));
//							j = current;
//							
//						}case "while" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.WHILE_KW, "while"));
//							j = current;
//							
//						}case "do" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.DO_KW, "do"));
//							j = current;
//							
//						}case "for" ->{
//							wordCheck = true;
//							tokens.add(new Token(TokenName.FOR_KW, "for"));
//							j = current;
//							
//						}
//						}
//						
//					}
					////////////////////////////////////////////////////////////////////////////
					
					
					//if(!wordCheck) {		// if no keyword was found
//						for(var ch: lexeme.toCharArray()) {	// for every VAR_ID captured
//							tokens.add(new Token(TokenName.VAR_ID, ch));// add to queue
//						}
					//}
					
//					i += current -1; // compensate index for captured values 
				}else {
					tokens.add(new Token(TokenName.VAR_ID, input.charAt(i)));
				}
				
			}else {
				String lexeme = input.substring(i, i+current);
				// run check to see if possible keyword
				boolean opChar = false;
				for(var c: ops) {
					if(input.charAt(i) == c) {
						opChar = true;
						break;
					}	
				}
				
				if(opChar) {
					switch(input.charAt(i)) {
					case '[' ->{
						//tokens.add(new Token(TokenName.OPEN_BLOCK, input.charAt(i)));
						tokens.add(new Token(TokenName.OPEN_BLOCK, null));
						
					}case ']' ->{
						//tokens.add(new Token(TokenName.CLOSE_BLOCK, input.charAt(i)));
						tokens.add(new Token(TokenName.CLOSE_BLOCK, null));
						
					}case '+' ->{
						if( i+current < input.length() &&input.charAt(i+current) == '+') {
							current++;
							lexeme = input.substring(i,i+current);
							//tokens.add(new Token(TokenName.INC_OP, lexeme));
							tokens.add(new Token(TokenName.INC_OP, null));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else {
							tokens.add(new Token(TokenName.PLUS_OP, null));
							//tokens.add(new Token(TokenName.PLUS_OP, lexeme));
						}
						
						
						
					}case '-' ->{
						if( i+current < input.length() &&input.charAt(i+current) == '-') {
							current++;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.DEC_OP, lexeme));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else {
							tokens.add(new Token(TokenName.MINUS_OP, lexeme));
						}
						
						
					}case '*' ->{
						tokens.add(new Token(TokenName.TIMES_OP, input.charAt(i)));
						
					}case '/' ->{
						tokens.add(new Token(TokenName.DIV_OP, input.charAt(i)));
						
					}case '%' ->{
						tokens.add(new Token(TokenName.MOD_OP, input.charAt(i)));
						
					}case '&' ->{
						tokens.add(new Token(TokenName.AND_OP, input.charAt(i)));
						
					}case '|' ->{
						tokens.add(new Token(TokenName.OR_OP, input.charAt(i)));
						
					}case '~' ->{
						if( i+current < input.length() &&input.charAt(i+current) == '=') {
							current++;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.NE_OP, lexeme));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else {
							tokens.add(new Token(TokenName.NOT_OP, lexeme));
						}
						
						
					}case '=' ->{
						tokens.add(new Token(TokenName.EQ_OP, input.charAt(i)));
						
					}case '<' ->{
						if( i+current < input.length() &&input.charAt(i+current) == '=') {
							current++;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.LE_OP, lexeme));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else {
							tokens.add(new Token(TokenName.LT_OP, lexeme));
						}
						
					}case '>' ->{
						if( i+current < input.length() &&input.charAt(i+current) == '=') {
							current++;
							lexeme = input.substring(i,i+current);
							tokens.add(new Token(TokenName.GE_OP, lexeme));
							i += current -1;
							//i += current;
							//wordCheck = true;
						}else {
							tokens.add(new Token(TokenName.GT_OP, lexeme));
						}	
					}
					
					}	
				}else {
					tokens.add(new Token(TokenName.UNEXPECTED_INPUT, lexeme));
				}
				
			}
			
			//System.out.println("hi");
			
		}
		
		tokens.add(new Token(TokenName.END_OF_INPUT));
		
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
