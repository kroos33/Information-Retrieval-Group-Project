package irgroupproject.qp;

import irgroupproject.qp.client.RmiClient;
import irgroupproject.qp.token.TokenList;

/**
 * Simple class for test-cases.  Should not be invoked.
 * @author Ben Tse
 *
 */
public class QueryParserMain {

	public static void main(String[] args) throws Exception {
		TokenList tokens = new QueryParser()
				.parse(" Babe Ruth[RN] AND Sport or Baseball[AR] near Mets ");

		TokenExpander expander = new TokenExpander(new RmiClient());
		QueryGenerator generator = new QueryGenerator(expander);
		System.out.println(generator.generate(tokens));
	}

}
