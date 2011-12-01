package irgroupproject.qp;

import java.util.Iterator;
import java.util.Set;

import irgroupproject.qp.token.Operator;
import irgroupproject.qp.token.Term;
import irgroupproject.qp.token.Token;
import irgroupproject.qp.token.TokenList;

/**
 * Generates the search query from a set of tokens and operators.
 * <BR><BR>
 * Uses the {@link TokenExpander} class to parse the individual tokens and the {@link irgroupproject.qp.client.RmiClient} to connect to the TBN.
 * 
 * @author Ben Tse
 *
 */
public class QueryGenerator {
	private TokenExpander expander;

	/**
	 * Default Constructor
	 * @param expander {@link TokenExpander} instance to use to parse our query tokens.
	 */
	public QueryGenerator(TokenExpander expander) {
		this.expander = expander;
	}

	/**
	 * Query generator.  Takes a list of parsed Tokens, expands them, gets the expansion results and re-writes the query.
	 * @param tokens List of tokens input by the user, unexpanded.
	 * @return TBN expanded query to send to Google.
	 * @throws QueryParserException
	 */
	public String generate(TokenList tokens) throws QueryParserException {
		StringBuffer query = new StringBuffer();
		for (Iterator<Token> iterator = tokens.iterator(); iterator.hasNext();) {
			Token token = iterator.next();

			if (token instanceof Term) {
				Set<String> concepts = expander.expand((Term) token);
				if (concepts.size() == 1) {
					query.append(concepts.iterator().next());
				} else {
					String subquery = getExpandedConceptsQuery(concepts);
					query.append(subquery);
				}
			} else if (token instanceof Operator) {
				query.append(((Operator) token).getOperatorType());
			}
			if (iterator.hasNext())
				query.append(" ");
		}
		return query.toString();
	}

	
	/**
	 * Expands the query using our TBN results.
	 * @param concepts - List of expansions.
	 * @return New, Expanded query.
	 */
	private String getExpandedConceptsQuery(Set<String> concepts) {
		StringBuffer query = new StringBuffer();
		query.append("(");
		for (Iterator<String> iterator = concepts.iterator(); iterator
				.hasNext();) {
			String concept = iterator.next();
			query.append(concept);
			if (iterator.hasNext())
				query.append(" or ");
		}
		query.append(")");
		return query.toString();
	}
}
