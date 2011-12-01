package irgroupproject.qp.token;

/**
 * Generic Token definition.<BR><BR>
 * Tokens can be either a term (world, baseball, etc) or a operator (and, or, near).
 * 
 * @author Ben Tse
 *
 */
public class Token {
	protected Type type;

	protected enum Type {
		TERM, OPERATOR
	}
}
