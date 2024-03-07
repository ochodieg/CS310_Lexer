package grade;

import static framework.TokenName.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import framework.Token;
import framework.TokenName;
import unit.Lexer;

/**
 * Do not modify or submit this class.
 */
class LexerTests {
	static Object[][] data() {
		return new Object[][] {
			{ "[", tokens(OPEN_BLOCK, END_OF_INPUT) },
			{ "]", tokens(CLOSE_BLOCK, END_OF_INPUT) },
			{ "[]", tokens(OPEN_BLOCK, CLOSE_BLOCK, END_OF_INPUT) },
			{ "] [", tokens(CLOSE_BLOCK, OPEN_BLOCK, END_OF_INPUT) },
			{ " [[", tokens(OPEN_BLOCK, OPEN_BLOCK, END_OF_INPUT) },
			{ "]] ", tokens(CLOSE_BLOCK, CLOSE_BLOCK, END_OF_INPUT) },

			{ "", tokens(END_OF_INPUT) },
			{ "     ", tokens(END_OF_INPUT) },
			{ "$", tokens(valued(UNEXPECTED_INPUT, '$')) },
			{ "$; ", tokens(valued(UNEXPECTED_INPUT, '$')) },
			{ " ;$", tokens(valued(UNEXPECTED_INPUT, ';')) },

			{ "+", tokens(PLUS_OP, END_OF_INPUT) },
			{ "++", tokens(INC_OP, END_OF_INPUT) },
			{ " ++", tokens(INC_OP, END_OF_INPUT) },
			{ "+ +", tokens(PLUS_OP, PLUS_OP, END_OF_INPUT) },
			{ "++ ", tokens(INC_OP, END_OF_INPUT) },
			{ "+++", tokens(INC_OP, PLUS_OP, END_OF_INPUT) },

			{ "-", tokens(MINUS_OP, END_OF_INPUT) },
			{ "--", tokens(DEC_OP, END_OF_INPUT) },
			{ " -  --   ", tokens(MINUS_OP, DEC_OP, END_OF_INPUT) },
			{ "   --  - ", tokens(DEC_OP, MINUS_OP, END_OF_INPUT) },
			{ "-----", tokens(DEC_OP, DEC_OP, MINUS_OP, END_OF_INPUT) },
			{ " ---- ", tokens(DEC_OP, DEC_OP, END_OF_INPUT) },

			{ "*", tokens(TIMES_OP, END_OF_INPUT) },
			{ "/", tokens(DIV_OP, END_OF_INPUT) },
			{ "%", tokens(MOD_OP, END_OF_INPUT) },
			{ "*/ %% ", tokens(TIMES_OP, DIV_OP, MOD_OP, MOD_OP, END_OF_INPUT) },
			{ " ** /%", tokens(TIMES_OP, TIMES_OP, DIV_OP, MOD_OP, END_OF_INPUT) },
			{ "***///%%%", tokens(TIMES_OP, TIMES_OP, TIMES_OP, DIV_OP, DIV_OP, DIV_OP, MOD_OP, MOD_OP, MOD_OP, END_OF_INPUT) },

			{ "&", tokens(AND_OP, END_OF_INPUT) },
			{ "|", tokens(OR_OP, END_OF_INPUT) },
			{ "&& ||", tokens(AND_OP, AND_OP, OR_OP, OR_OP, END_OF_INPUT) },
			{ "&|&|", tokens(AND_OP, OR_OP, AND_OP, OR_OP, END_OF_INPUT) },

			{ "~", tokens(NOT_OP, END_OF_INPUT) },
			{ "=", tokens(EQ_OP, END_OF_INPUT) },
			{ "~=", tokens(NE_OP, END_OF_INPUT) },
			{ "~ =", tokens(NOT_OP, EQ_OP, END_OF_INPUT) },
			{ "~=~=", tokens(NE_OP, NE_OP, END_OF_INPUT) },
			{ "~~==", tokens(NOT_OP, NE_OP, EQ_OP, END_OF_INPUT) },

			{ "<", tokens(LT_OP, END_OF_INPUT) },
			{ "<=", tokens(LE_OP, END_OF_INPUT) },
			{ "< = ", tokens(LT_OP, EQ_OP, END_OF_INPUT) },
			{ "=<", tokens(EQ_OP, LT_OP, END_OF_INPUT) },
			{ "<<==", tokens(LT_OP, LE_OP, EQ_OP, END_OF_INPUT) },

			{ ">", tokens(GT_OP, END_OF_INPUT) },
			{ ">=", tokens(GE_OP, END_OF_INPUT) },
			{ " > =", tokens(GT_OP, EQ_OP, END_OF_INPUT) },
			{ "=>", tokens(EQ_OP, GT_OP, END_OF_INPUT) },
			{ ">>==", tokens(GT_OP, GE_OP, EQ_OP, END_OF_INPUT) },

			{ "<=>=", tokens(LE_OP, GE_OP, END_OF_INPUT) },
			{ ">=<=", tokens(GE_OP, LE_OP, END_OF_INPUT) },
			{ "< =>=", tokens(LT_OP, EQ_OP, GE_OP, END_OF_INPUT) },
			{ ">=< =", tokens(GE_OP, LT_OP, EQ_OP, END_OF_INPUT) },
			{ "~==<<=>>=", tokens(NE_OP, EQ_OP, LT_OP, LE_OP, GT_OP, GE_OP, END_OF_INPUT) },
			{ ">=><=<=~=", tokens(GE_OP, GT_OP, LE_OP, LE_OP, NE_OP, END_OF_INPUT) },

			{ "let", tokens(LET_KW, END_OF_INPUT) },
			{ "def", tokens(DEF_KW, END_OF_INPUT) },
			{ "let def", tokens(LET_KW, DEF_KW, END_OF_INPUT) },
			{ "def+", tokens(DEF_KW, PLUS_OP, END_OF_INPUT) },
			{ "-let", tokens(MINUS_OP, LET_KW, END_OF_INPUT) },
			{ "&def|let~", tokens(AND_OP, DEF_KW, OR_OP, LET_KW, NOT_OP, END_OF_INPUT) },

			{ "push", tokens(PUSH_KW, END_OF_INPUT) },
			{ "pop", tokens(POP_KW, END_OF_INPUT) },
			{ "peek", tokens(PEEK_KW, END_OF_INPUT) },
			{ "size", tokens(SIZE_KW, END_OF_INPUT) },
			{ "push pop  peek   size", tokens(PUSH_KW, POP_KW, PEEK_KW, SIZE_KW, END_OF_INPUT) },

			{ "run", tokens(RUN_KW, END_OF_INPUT) },
			{ "run run run", tokens(RUN_KW, RUN_KW, RUN_KW, END_OF_INPUT) },
			{ "if", tokens(IF_KW, END_OF_INPUT) },
			{ "while", tokens(WHILE_KW, END_OF_INPUT) },
			{ "do", tokens(DO_KW, END_OF_INPUT) },
			{ "for", tokens(FOR_KW, END_OF_INPUT) },
			{ "for*do/while%if", tokens(FOR_KW, TIMES_OP, DO_KW, DIV_OP, WHILE_KW, MOD_OP, IF_KW, END_OF_INPUT) },

			{ "a", tokens(valued(VAR_ID, 'a'), END_OF_INPUT) },
			{ "A", tokens(valued(VAR_ID, 'a'), END_OF_INPUT) },
			{ "b C ", tokens(valued(VAR_ID, 'b'), valued(VAR_ID, 'c'), END_OF_INPUT) },
			{ " B c", tokens(valued(VAR_ID, 'b'), valued(VAR_ID, 'c'), END_OF_INPUT) },
			{ " d e f ", tokens(valued(VAR_ID, 'd'), valued(VAR_ID, 'e'), valued(VAR_ID, 'f'), END_OF_INPUT) },
			{ "X  Y  Z", tokens(valued(VAR_ID, 'x'), valued(VAR_ID, 'y'), valued(VAR_ID, 'z'), END_OF_INPUT) },

			{ "0", tokens(valued(UINT_LIT, 0), END_OF_INPUT) },
			{ "1", tokens(valued(UINT_LIT, 1), END_OF_INPUT) },
			{ "12", tokens(valued(UINT_LIT, 12), END_OF_INPUT) },
			{ "123", tokens(valued(UINT_LIT, 123), END_OF_INPUT) },
			{ "123.45", tokens(valued(UINT_LIT, 123), valued(UNEXPECTED_INPUT, '.')) },
			{ "123 45a z67", tokens(valued(UINT_LIT, 123), valued(UINT_LIT, 45), valued(VAR_ID, 'a'), valued(VAR_ID, 'z'), valued(UINT_LIT, 67), END_OF_INPUT) },
			{ "+01234", tokens(PLUS_OP, valued(UINT_LIT, 1234), END_OF_INPUT) },
			{ "-98765", tokens(MINUS_OP, valued(UINT_LIT, 98765), END_OF_INPUT) },
			{ "2147483647", tokens(valued(UINT_LIT, Integer.MAX_VALUE), END_OF_INPUT) },

			{ "{}", tokens(END_OF_INPUT) },
			{ "{comment}", tokens(END_OF_INPUT) },
			{ "{comment", tokens(END_OF_INPUT) },
			{ "comment}", tokens(valued(UNEXPECTED_INPUT, 'c')) },
			{ "{{comment}}", tokens(valued(UNEXPECTED_INPUT, '}')) },
			{ "{{{comment}", tokens(END_OF_INPUT) },

			{ "[]+{abc}", tokens(OPEN_BLOCK, CLOSE_BLOCK, PLUS_OP, END_OF_INPUT) },
			{ "[<{123}=]", tokens(OPEN_BLOCK, LT_OP, EQ_OP, CLOSE_BLOCK, END_OF_INPUT) },
			{ "{xyz}-[]", tokens(MINUS_OP, OPEN_BLOCK, CLOSE_BLOCK, END_OF_INPUT) },
			{ "{let}*{def}/{push}%{pop}", tokens(TIMES_OP, DIV_OP, MOD_OP, END_OF_INPUT) },
			{ "let{*}def{/}push{%}pop", tokens(LET_KW, DEF_KW, PUSH_KW, POP_KW, END_OF_INPUT) },
			{ "      {abcdefg}     {hijklmnop}    {qrs}   {tuv}  {wx} {yz}", tokens(END_OF_INPUT) },
		};
	}

	static Queue<Token> tokens(Object... tokens) {
		Queue<Token> expected = new LinkedList<Token>();
		for (Object it: tokens) {
			if (it instanceof TokenName name)
				expected.add(new Token(name));
			else if (it instanceof Token token)
				expected.add(token);
		}
		return expected;
	}

	static Token valued(TokenName name, Object value) {
		return new Token(name, value);
	}

	@DisplayName("Inputs")
	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("data")
	void testTokens(String input, Queue<Token> expected) {
		Queue<Token> actual = new Lexer().tokenize(input);
		assertNotNull(
			actual,
			"Must return non-null queue of tokens (unimplemented stub likely)"
		);

		int elen = expected.size();
		int alen = actual.size();
		int len = Math.min(elen, alen);

		for (int i = 1; i <= len; i++) {
			Token e = expected.poll();
			Token a = actual.poll();

			assertEquals(
				e.name(),
				a.name(),
				"Token " + i + " of " + len + " must have expected name"
			);

			assertEquals(
				e.value(),
				a.value(),
				"Token " + i + " of " + len + " with name " + e.name() + " must have expected value"
			);
		}

		assertEquals(
			elen,
			alen,
			"Must return queue with expected number of tokens"
		);
	}
}
