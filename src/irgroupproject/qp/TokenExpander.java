package irgroupproject.qp;

import irgroupproject.qp.client.RmiClient;
import irgroupproject.qp.token.Term;
import irgroupproject.tbn.Concept;
import irgroupproject.tbn.Relationship;

import java.util.HashSet;
import java.util.Set;

public class TokenExpander {
	private RmiClient client;

	public TokenExpander(RmiClient client) {
		this.client = client;
	}

	public Set<String> expand(Term term) throws QueryParserException {
		Concept concept = term.getConcept();
		Set<String> concepts = new HashSet<String>();
		concepts.add(concept.getConcept());

		for (Relationship relationship : term.getRelationships()) {
			Set<String> returnedConcepts = client.getConceptsByRelationship(
					concept, relationship);
			concepts.addAll(returnedConcepts);
		}
		return concepts;
	}
}
