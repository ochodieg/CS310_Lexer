package framework;

/**
 * Do not modify or submit this class.
 */
public record Token(TokenName name, Object value) {
	public Token(TokenName name) {
		this(name, null);
	}
}