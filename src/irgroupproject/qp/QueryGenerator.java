package irgroupproject.qp;

import java.util.Iterator;
import java.util.Set;

import irgroupproject.qp.token.Operator;
import irgroupproject.qp.token.Term;
import irgroupproject.qp.token.Token;
import irgroupproject.qp.token.TokenList;

public class QueryGenerator {
	private TokenExpander expander;

	public QueryGenerator(TokenExpander expander) {
		this.expander = expander;
	}

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
