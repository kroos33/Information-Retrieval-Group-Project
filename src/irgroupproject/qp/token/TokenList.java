package irgroupproject.qp.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TokenList implements Iterable<Token> {
	private List<Token> tokens = new ArrayList<Token>();

	@Override
	public Iterator<Token> iterator() {
		return tokens.iterator();
	}

	public boolean add(Token e) {
		return tokens.add(e);
	}

}
