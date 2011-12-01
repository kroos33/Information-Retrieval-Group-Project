package irgroupproject.qp;

import irgroupproject.qp.client.RmiClient;
import irgroupproject.qp.token.Term;
import irgroupproject.tbn.Concept;
import irgroupproject.tbn.Relationship;

import java.util.HashSet;
import java.util.Set;

public class TokenExpander {
	private RmiClient client;

	/**
	 * Default Constructor.
	 * @param client - {@link irgroupproject.qp.client.RmiClient} used to communicate with the TBN.
	 * @author Ben Tse
	 */
	public TokenExpander(RmiClient client) {
		this.client = client;
	}

	
	/**
	 * Uses our TBN to expand a concept and relationship pair using our {@link irgroupproject.qp.client.RmiClient} 
	 * <BR><BR>
	 * Depth of expansion is determined by the configuration of the TBN.
	 * @param term {@link irgroupproject.qp.token.Term} object we want to expand.
	 * @return Expanded version of our concepts as provided.
	 * @author Ben Tse
	 */
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
