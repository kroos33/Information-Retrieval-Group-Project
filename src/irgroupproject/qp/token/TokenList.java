package irgroupproject.qp.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Simple list used to keep track of our located tokens in the user supplied query.
 * @author Ben Tse
 *
 */
public class TokenList implements Iterable<Token> {
	private List<Token> tokens = new ArrayList<Token>();

	@Override
	public Iterator<Token> iterator() {
		return tokens.iterator();
	}

	/**
	 * Allows {@link irgroupproject.qp.token.Token} objects to be added to the list.
	 */
	public boolean add(Token e) {
		return tokens.add(e);
	}

}
